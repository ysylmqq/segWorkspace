/*
 ********************************************************************************************
Discription:  管理客户端命令, 通过一个线程发送到MQ，并且从MQ读指令返回
			  
Written By:   ZXZ
Date:         2014-06-06
Version:      1.0

Modified by:  
Modified Date:
Version:
 ********************************************************************************************
 */
package cc.chinagps.gboss.comcenter.command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import cc.chinagps.gboss.comcenter.CenterClientManager;
import cc.chinagps.gboss.comcenter.UnitInfoManager;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.SendCommandSend_ACK;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.SendCommand_ACK;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.websocket.SendCommandHandler;
import cc.chinagps.gboss.database.DBUtil;
import cc.chinagps.lib.util.SystemConfig;

public class CommandManager extends Thread {
	
	public static CommandManager commandmanager = null;

	public static AtomicLong    commandtotalcount = null; //指令总数
	public static AtomicInteger commandsendindex = null;  //发送指令平均分发到各个发送线程处理
	
	private ArrayList<SendCommandInfo> presendcmdlist = null;	//未发送到MQ命令队列
	private ConcurrentHashMap<String, SendCommandInfo> allsendcmdmap = null;	//全部命令

	public static short smsproxygatewayid = 10001; // 短信网关代理编号
	public static short mobilesmscmdid = 0x4035;   // 一般手机短信指令
	
	public static CommandSendThread sendthread = null;
	public static CommandResponseThread responsethread = null;
	public static SaveCommandToDBThread savedbthread = null;
	
	//指明那些网关ID是短信网关
	public static ConcurrentHashMap<Short, Boolean> smsgatewaymap = null;
	
	public CommandManager(String name) {
		super(name);
		//三个数量原子操作
		commandtotalcount = new AtomicLong ();
		commandsendindex = new AtomicInteger();
		
		presendcmdlist = new ArrayList<SendCommandInfo>(); 
		//保存已经写到ActiveMQ的命令，为超时判断及命令返回用，Key=Callletter + "-" + "sn" + cmdtime
		allsendcmdmap = new ConcurrentHashMap<String, SendCommandInfo>();
		
		//发送线程
		sendthread = new CommandSendThread();
		//处理应答线程
		responsethread = new CommandResponseThread();
		//保存命令到数据库的线程
		savedbthread = new SaveCommandToDBThread();
		
		smsgatewaymap = new ConcurrentHashMap<Short, Boolean>();
		String strconfig = SystemConfig.getSystemProperties("smsproxy_id");
		if (strconfig != null) {
			smsproxygatewayid = Short.valueOf(strconfig);
		}
		smsgatewaymap.put(smsproxygatewayid, true);
		strconfig = SystemConfig.getSystemProperties("command_timeout");
		if (strconfig != null) {
			SendCommandInfo.commandsendtimeout = Integer.valueOf(strconfig);
		}
		if (SendCommandInfo.commandsendtimeout <= 0) {
			SendCommandInfo.commandsendtimeout = 30000; // 30秒
		}
		strconfig = SystemConfig.getSystemProperties("smcommand_timeout");
		if (strconfig != null) {
			SendCommandInfo.smcommandsendtimeout = Integer.valueOf(strconfig);
		}
		if (SendCommandInfo.smcommandsendtimeout <= 0) {
			SendCommandInfo.smcommandsendtimeout = 120000; // 120秒
		}
		// 短信网关编号
		strconfig = SystemConfig.getSystemProperties("smsgatewayids");
		if (strconfig != null) {
			String[] smsgatewayidlist = strconfig.split(";");
			for (int j = 0; j < smsgatewayidlist.length; j++) {
				smsgatewaymap.put(Short.valueOf(smsgatewayidlist[j]), true);
			}
		}
	}

	//开始线程
	@Override
	public void start() {
		super.start();
		//发送线程
		sendthread.start();
		//处理应答线程
		responsethread.start();
		//存储线程
		savedbthread.start();
	}

	//未处理命令数量
	public int getCommandCount() {
		return allsendcmdmap.size();
	}
	
	// [start] 添加客户端指令到发送线程
	//从客户端指令带伤生成指令结构
	private ArrayList<SendCommandInfo> getSendCommandInfo(SendCommandHandler commandhandler) {
		ArrayList<SendCommandInfo> retlist = new ArrayList<SendCommandInfo>();
		for(String callletter : commandhandler.callletterlist) {
			SendCommandInfo sendcmdinfo = new SendCommandInfo(commandhandler);
			sendcmdinfo.callletter = callletter;
			//海马TBOX设置电控单元配置，没有带参数，则从数据库读参数
			if ((sendcmdinfo.cmdId == 0xB3) && 
				((sendcmdinfo.sendparams == null) || sendcmdinfo.sendparams.size() < 1)) {
				sendcmdinfo.sendparams = QueryHMConfigParam(callletter);
				//如果没有参数，则不执行
				if (sendcmdinfo.sendparams == null) {
					ackSendCommandFail(sendcmdinfo, ResultCode.Format_Error, "指令参数错误");
					continue;
				}
			}
			//只有客户端指令才保存到map队列, 但必须发送
			if (commandhandler.clientinfo != null) {
				appendSendCommandMap(sendcmdinfo);
			}
			retlist.add(sendcmdinfo);
		}
		return retlist;
	}
	
	/**
	 * 从数据库读海马车辆配置简码, 并组成配置参数字符串
	 * @throws Exception 
	 */
	private ArrayList<String> QueryHMConfigParam(String callletter) {
		//查询sql
		String sql = "SELECT e.code1 FROM t_ba_sim t, t_ba_equip e WHERE t.call_letter=? AND t.equip_code=e.equip_code";
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try{
			//获取数据库连接
			con = DBUtil.getConnection();
			//创建PreparedStatement
			pst = con.prepareStatement(sql);
			//设置参数
			pst.setString(1, callletter);
			//查询
			rs = pst.executeQuery();
			
			if (rs.next()){
				int code1 = DBUtil.GetIntegerFromColumn(rs, 1);
				//子系统配置, 每1位表示1个子系统, 1=有, 0=无, 从低位1到高位64依次为ABS,ESP/DCU,PEPS,TPMS,SRS,EPS,EMS,IMMO,BCM,TCU,ICM,APM',
				//配置1
				//(各配置格式:长度为8的字符串，每一个字符为0或1)
				//00000000: 体检关
				//00000001: 体检开
				ArrayList<String> listparam = new ArrayList<String>();
				listparam.add("00000001");	//体检开
				StringBuilder sb = new StringBuilder();
				//配置2
				//( ABS, ESP/DCU, PEPS, TPMS, SRS, EPS, EMS, IMMO)
				//00000000: 全没有配置
				//11111111:  全配置
				int ior = 1;
				for(int i=0; i<8; i++) {
					if ((code1 & ior) != 0) {
						sb.append("1");
					} else {
						sb.append("0");
					}
					ior <<= 1;
				}
				listparam.add(sb.toString());
				//配置3
				//(BCM,TCU,ICM,APM)
				//00000000: 全没有配置
				//11110000: 全配置
				sb.delete(0, 8);
				for(int i=0; i<8; i++) {
					if ((code1 & ior) != 0) {
						sb.append("1");
					} else {
						sb.append("0");
					}
					ior <<= 1;
				}
				listparam.add(sb.toString());
				return listparam;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//关闭数据库资源
			DBUtil.closeDB(rs, pst, con);
		}
		return null;
	}
	

	//添加命令到等待队列, 等待线程生理，不在客户端连接处理
	public void appendNewCommand(SendCommandHandler commandhandler) {
		try {
			ArrayList<SendCommandInfo> tmplist = getSendCommandInfo(commandhandler);
			commandtotalcount.getAndAdd(tmplist.size());
			//添加到等待队列
			synchronized(presendcmdlist) {
				presendcmdlist.addAll(tmplist);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	// 发送透敛传指令
	public void appendNewCommand(String callletters, String content, int gatewayid) {
		try {
			SendCommandHandler sendcommand = new SendCommandHandler(null, null);
			sendcommand.cmdsn = UUID.randomUUID().toString();
			String[] tmparray = callletters.split(";");
			for (String callletter : tmparray) {
				sendcommand.callletterlist.add(callletter);
			}
			sendcommand.channelId = gatewayid;
			sendcommand.cmdId = 0xFFFF;
			sendcommand.params.add(content);
			if (smsgatewaymap.containsKey(gatewayid))
				sendcommand.params.add("1");
			else
				sendcommand.params.add("0");
			appendNewCommand(sendcommand);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	// [end] 添加客户端指令

	//取等待发送的命令队列
	public ArrayList<SendCommandInfo> getPreSendCommand() {
		try {
			//添加到等待队列
			synchronized(presendcmdlist) {
				if (presendcmdlist.size() == 0) {
					return null;
				} 
				ArrayList<SendCommandInfo> retlist = new ArrayList<SendCommandInfo>();
				retlist.addAll(presendcmdlist);
				presendcmdlist.clear();
				return retlist; 
			}
		} catch (Throwable e) {
		}
		return null;
	}

	// 保存已经发送的命令到map, 为了超时判断用
	//添加已经发送命令队列
	public void appendSendCommandMap(SendCommandInfo sendcmdinfo) {
		String key = sendcmdinfo.callletter + sendcmdinfo.cmdsn + sendcmdinfo.cmdtime1;
		allsendcmdmap.put(key, sendcmdinfo);
	}
	//发送失败时，从队列中删除
	public void removeSendCommandMap(SendCommandInfo sendcmdinfo) {
		String key = sendcmdinfo.callletter + sendcmdinfo.cmdsn + sendcmdinfo.cmdtime1;
		allsendcmdmap.remove(key);		
	}
	
			
	// 处理网关发送回应，是在线程中处理，不过调用此方法而已
	// SendCommandSend_ACK一般都是成功，如果发送失败，则直接是SendCommand_ACK
	public void handleRresponseCommand(SendCommandSend_ACK cmdsendack) {
		short retcode = (short)(cmdsendack.getRetcode());
		//如果网关返回终端不在线，则先删除终端流量网关缓存
		if (retcode == ResultCode.UnitNoExist_Error1) {
			UnitInfoManager.unitinfomanager.clearGatewayId(cmdsendack.getCallLetter());
		}
		String cmdsn = cmdsendack.getSn();	//cmdsn已经包含了时间
		String callletter = cmdsendack.getCallLetter();
		//int cmdId = cmdsendack.getCmdId();
		//从cmdsendedlist中搜索是否有此回应的对应命令，有可能已经超时删除了
		String key=callletter + cmdsn;	
		SendCommandInfo sendcmdinfo = allsendcmdmap.get(key);
		//开关门下发时，网关有时回应都是关门
		if (sendcmdinfo == null) { // || 
			//(sendcmdinfo.cmdId != cmdId && cmdId != 4 && sendcmdinfo.cmdId != 5 )) {
			return;
		}
		if (retcode != ResultCode.OK) {// 如果发送失败
			CommandSendThread.ackSendCommandSendFail(sendcmdinfo, retcode, cmdsendack.getRetmsg());
			return;
		}
		//更新发送时间
		sendcmdinfo.sendtime = System.currentTimeMillis();
		if (cmdsendack.hasUnitsn()) {
			//海马终端短信下发命令，应答是由网络返回，此处保留网关到终端的SN
			sendcmdinfo.unitsn = cmdsendack.getUnitsn();
		}
		
		//如果客户端没有断开连接, 返回客户端
		if (!sendcmdinfo.clientinfo.isClosed()) {
			//返回客户端网关指令发送消息
			ComCenterMessage.ComCenterBaseMessage.Builder basemsgbuilder = ComCenterMessage.ComCenterBaseMessage.newBuilder();
			basemsgbuilder.setId(MessageType.SendCommandSend_ACK);
			//因为发送到网关的SN已经加上了命令时间，所以不能用
			SendCommandSend_ACK.Builder sendackbuilder = SendCommandSend_ACK.newBuilder(cmdsendack);
			sendackbuilder.setSn(sendcmdinfo.cmdsn);
			basemsgbuilder.setContent(sendackbuilder.build().toByteString());
			sendcmdinfo.clientinfo.appendSendMessage(basemsgbuilder.build());
		}
	}
	
	// 处理终端应答，是在线程中处理，不过调用此方法而已
	public void handleRresponseCommand(SendCommand_ACK cmdack) {
		//如果网关返回终端不在线，则先删除终端流量网关缓存
		short retcode = (short)(cmdack.getRetcode());
		if (retcode == ResultCode.UnitNoExist_Error1) {
			UnitInfoManager.unitinfomanager.clearGatewayId(cmdack.getCallLetter());
		}
		String cmdsn = cmdack.getSn();	//已经包含时间
		String callletter = cmdack.getCallLetter();
		int cmdId = cmdack.getCmdId();
		String retmsg = cmdack.getRetmsg();
		if (cmdId == 0x0292) {
			System.out.println("导航指令应答: call=" + callletter + ", retcode=" + retcode + ", retmsg=" + retmsg);
		}
		
		SendCommandInfo sendcmdinfo = null;
		String key = callletter + cmdsn;	//请参考发送线程调用的saveCommandSendedInfo
		if (cmdsn.isEmpty()) {	//如果SN是空，应答由不同的网关, 可能是短信下发，流量回应的指令，要找unitsn
			int unitsn = cmdack.getUnitsn();
			boolean bfind = false;
			Iterator<Entry<String, SendCommandInfo>> iter = allsendcmdmap.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, SendCommandInfo> keyvalue = iter.next();
				sendcmdinfo = keyvalue.getValue();
				if (sendcmdinfo.unitsn == unitsn &&
					//(sendcmdinfo.cmdId == cmdId || (sendcmdinfo.cmdId == 5 && cmdId == 4)) &&
					sendcmdinfo.callletter.equals(callletter)) {
					key = callletter + sendcmdinfo.cmdsn + sendcmdinfo.cmdtime1;	//请参考发送线程调用的saveCommandSendedInfo
					bfind = true;
					break;
				}
			}
			if (!bfind) { //如果没有找到对应的下发指令, 如果找到则cmdsendedinfo不为空
				sendcmdinfo = null;
			}
		} else {
			//从cmdsendedlist中搜索是否有此回应的对应命令，有可能已经超时删除了
			sendcmdinfo = allsendcmdmap.get(key);
		}

		//开关门下发时，网关有时回应都是关门
		if (sendcmdinfo == null ) {// || 
			//((sendcmdinfo.cmdId) != cmdId && (sendcmdinfo.cmdId != 5)  && (cmdId != 4))) {
			//没找到对应的下发指令
			return;
		}
		
		//设置返回结果
		if (retcode == ResultCode.OK) {
			StringBuilder sb = new StringBuilder();
			for (String param : cmdack.getParamsList()) {
				if (sb.length() > 0) {
					sb.append('~');
				}
				sb.append(param);
			}
			retmsg = sb.toString();
			sb = null;
		}

		sendcmdinfo.setResCode(retcode, retmsg);
		savedbthread.addSendCommandInfo(sendcmdinfo);
		removeSendCommandMap(sendcmdinfo);
		if (!sendcmdinfo.clientinfo.isClosed()) {
			//如果客户端没有断开连接，返回客户端终端应答消息
			ComCenterMessage.ComCenterBaseMessage.Builder basemsgbuilder = ComCenterMessage.ComCenterBaseMessage.newBuilder();
			basemsgbuilder.setId(MessageType.SendCommand_ACK);
			//因为发送到网关的SN已经加上了命令时间，所以不能用
			SendCommand_ACK.Builder ackbuilder = SendCommand_ACK.newBuilder(cmdack);
			ackbuilder.setSn(sendcmdinfo.cmdsn);
			basemsgbuilder.setContent(ackbuilder.build().toByteString());
			sendcmdinfo.clientinfo.appendSendMessage(basemsgbuilder.build());
		} //else if (sendcmdinfo.clientcommand.clientinfo.isMobileApp()) {
		//如果客户端是手机APP，并且断开连接，则要发通知信息
		CenterClientManager.clientManager.addAppNoticeInfo(sendcmdinfo);
	}
	// [end] 从ActiveMQ读网关发送指令消息及终端应答消息

	// [start] 用线程判断指令是否超时, 1秒钟执行一次
	@Override
	public void run() {
		SendCommandInfo sendcmdinfo = null;
		while (true) {
			try {
				sleep(1000);
			} catch (Throwable e) {
				e.printStackTrace();
			}
			Iterator<Entry<String, SendCommandInfo>> iter = allsendcmdmap.entrySet().iterator();
			while (iter.hasNext()) {
				try {
					Entry<String, SendCommandInfo> keyvalue = iter.next();
					sendcmdinfo = keyvalue.getValue();
					if (sendcmdinfo.expiretime >= System.currentTimeMillis()) {
						continue;
					}
					// 超过30秒没有回应，则返回超时失败
					ackSendCommandFail(sendcmdinfo, ResultCode.Timeout_Error, "指令超时没回应");
					iter.remove();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
	}
	// 发送失败，返回错误
	private void ackSendCommandFail(SendCommandInfo sendcmdinfo, short retcode, String retmsg) {
		//返回客户端
		if (sendcmdinfo.sendtime == 0) {
			sendcmdinfo.setSendCode(retcode, retmsg);
		} else {
			sendcmdinfo.setResCode(retcode, retmsg);
		}
		savedbthread.addSendCommandInfo(sendcmdinfo);
		removeSendCommandMap(sendcmdinfo);
		
		if (!sendcmdinfo.clientinfo.isClosed()) {
			SendCommand_ACK.Builder cmdack = SendCommand_ACK.newBuilder();
			cmdack.setRetcode(retcode);
			cmdack.setRetmsg(retmsg);
			cmdack.setSn(sendcmdinfo.cmdsn);
			cmdack.setCmdId(sendcmdinfo.cmdId);
			cmdack.setCallLetter(sendcmdinfo.callletter);
			ComCenterMessage.ComCenterBaseMessage.Builder basemsgbuilder = ComCenterMessage.ComCenterBaseMessage.newBuilder();
			basemsgbuilder.setId(MessageType.SendCommand_ACK);
			basemsgbuilder.setContent(cmdack.build().toByteString());
			sendcmdinfo.clientinfo.appendSendMessage(basemsgbuilder.build());
		} //else if (sendcmdinfo.clientcommand.clientinfo.isMobileApp()) {
		//如果是手机客户端, 断开连接后发送到时通知中心
		CenterClientManager.clientManager.addAppNoticeInfo(sendcmdinfo);
	}
	// [end] 用线程判断指令是否超时
}
