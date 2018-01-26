/*
********************************************************************************************
Discription:  性能测试时，指令发送和接收处理用单线程处理时，性能都不高，
 			    更新为多线程, 本线程是发送命令线程
			  
Written By:   ZXZ
Date:         2015-04-02
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.whalin.MemCached.MemCachedClient;

import cc.chinagps.gboss.activemq.ActiveMQManager;
import cc.chinagps.gboss.comcenter.CenterClientManager;
import cc.chinagps.gboss.comcenter.UnitInfoManager;
import cc.chinagps.gboss.comcenter.UnitInfoManager.UnitInfo;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.SendCommand_ACK;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage;

public class CommandSendThread extends Thread {
	public CommandSendThread() {
		super("CommandSendThread");
	}
	
	// [start] 线程主函数，从等待队列一次性全部取出，再一个一个发送，写到ActiveMQ
	@Override
	public void run() {
		while (true) {
			// 先把等待队列的命令写到MQ, 把等待队列同步到临时变量
			ArrayList<SendCommandInfo> presendlist = CommandManager.commandmanager.getPreSendCommand();
			if (presendlist == null || presendlist.isEmpty()) {// 如果没有命令发送,则休息5毫秒
				try {
					sleep(5);
				} catch (Throwable e) {
					e.printStackTrace();
				}
				continue;
			}
			//再批量写入ActiveMQ
			try {
				WriteCommandToMQ(presendlist);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
	// [end] 线程主函数
	
	// [start] 把一个客户端指令（可能有多个呼号）,写到ActiveMQ, 并保存到等待回应队列
	private void WriteCommandToMQ(ArrayList<SendCommandInfo> presendlist) {
		HashMap<Short, ArrayList<SendCommandInfo>> gatewaycmdmap = new HashMap<Short, ArrayList<SendCommandInfo>>();
		MemCachedClient memcacheclient = new MemCachedClient();
		short gatewayid = 0;
		//先把命令按网关编号分组
		for(SendCommandInfo sendcmdinfo : presendlist) {
			gatewayid = getCommandGatewayid(sendcmdinfo, memcacheclient);
			//如果没有找到网关，则保存到
			if (gatewayid > 0) {
				ArrayList<SendCommandInfo> cmdlist = gatewaycmdmap.get(gatewayid);
				if (cmdlist == null) {
					cmdlist = new ArrayList<SendCommandInfo>();
					gatewaycmdmap.put(gatewayid, cmdlist);
				}
				sendcmdinfo.setGatewayId(gatewayid);
				cmdlist.add(sendcmdinfo);
			}
		} //for
		//按网关编号，批量写入MQ
		Iterator<Entry<Short, ArrayList<SendCommandInfo>>> iter = gatewaycmdmap.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<Short, ArrayList<SendCommandInfo>> keyvalue = iter.next();
			ArrayList<SendCommandInfo> sendcmdinfolist = keyvalue.getValue();
			//先插入队列
			if (!ActiveMQManager.activemq.writeCommand(keyvalue.getKey(), sendcmdinfolist)) {
				String retmsg = "发送失败"; 
				for(SendCommandInfo sendcmdinfo : sendcmdinfolist) {
					ackSendCommandSendFail(sendcmdinfo, ResultCode.Send_Error, retmsg);
				}
			}
		}
		gatewaycmdmap = null;
	}
	
	//按网关编号分组
	private short getCommandGatewayid(SendCommandInfo sendcmdinfo, MemCachedClient memcacheclient) {
		try {
			short gatewayid = -1;
			UnitInfo unitinfo = UnitInfoManager.unitinfomanager.getUnitInfo(sendcmdinfo.callletter, memcacheclient);
			if (unitinfo == null) {
				ackSendCommandSendFail(sendcmdinfo, ResultCode.UnitNoExist_Error1, "车台不存在");
				//System.out.println(sendcmdinfo.callletter + " 车台不存在");
				return gatewayid;
			}
			
			sendcmdinfo.plateno = unitinfo.plateno;
			sendcmdinfo.unitid = unitinfo.unitid;
			sendcmdinfo.unittype = unitinfo.unittype;
			sendcmdinfo.customerid = unitinfo.customerid;
			
			if (unitinfo.isshutdown()) {
				ackSendCommandSendFail(sendcmdinfo, ResultCode.Shutdowm_Error, "终端已经关机");
				return gatewayid;
			}
			if (sendcmdinfo.expiretime < System.currentTimeMillis()) {
				ackSendCommandSendFail(sendcmdinfo, ResultCode.Timeout_Error, "指令超时没回应");
				return gatewayid;
			}
			if (sendcmdinfo.channelId > 0) {	//如果客户端指定网关
				return sendcmdinfo.channelId;
			}
			
			// [start] 分析指令的网关编号
			if (sendcmdinfo.cmdId == 0x0292) {
				// 导航指令，优先走短信
				if ((unitinfo.unitmode & SendCommandInfo.SMSMODE) > 0) {
					gatewayid = CommandManager.smsproxygatewayid;
					//如果终端有指定的短信网关,则不走代理网关
					if (unitinfo.smsgatewayid > 0) {
						gatewayid = unitinfo.smsgatewayid;
					}
				} else {
					gatewayid = unitinfo.netgatewayid;
				}
			} else {
				// 其他优先网络网关, 如果终端断开连接，则netgatewayid == 0
				if ((unitinfo.unitmode & SendCommandInfo.NETMODE) > 0 && (unitinfo.netgatewayid > 0)) {
					gatewayid = unitinfo.netgatewayid;
				} else if ((unitinfo.unitmode & SendCommandInfo.SMSMODE) > 0) { //终端没登录
					gatewayid = CommandManager.smsproxygatewayid;
					if (unitinfo.smsgatewayid > 0) {
						gatewayid = unitinfo.smsgatewayid;
					}
				}
			}
			if (gatewayid <= 0) {	//没有找到网关,即只有网络模式的终端，并且不在线
				ackSendCommandSendFail(sendcmdinfo, ResultCode.Shutdowm_Error, "终端未登录");
			}
			return gatewayid;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	//要保存到数据库
	public static void ackSendCommandSendFail(SendCommandInfo sendcmdinfo, short retcode, String retmsg) {
		if (sendcmdinfo.clientinfo == null) { // 人工发透传命令
			return;
		}
		sendcmdinfo.setSendCode(retcode, retmsg);
		CommandManager.savedbthread.addSendCommandInfo(sendcmdinfo);
		CommandManager.commandmanager.removeSendCommandMap(sendcmdinfo);
		
		//返回客户端, 发送失败也要用SendCommand_ACK, 不能用SendCommandSend_ACK
		if (!sendcmdinfo.clientinfo.isClosed()) {
			SendCommand_ACK.Builder cmdack = SendCommand_ACK.newBuilder();
			cmdack.setCallLetter(sendcmdinfo.callletter);
			cmdack.setRetcode(retcode);
			cmdack.setRetmsg(retmsg);
			cmdack.setSn(sendcmdinfo.cmdsn);
			cmdack.setCmdId(sendcmdinfo.cmdId);
			ComCenterMessage.ComCenterBaseMessage.Builder basemsgbuilder = ComCenterMessage.ComCenterBaseMessage.newBuilder();
			basemsgbuilder.setId(MessageType.SendCommand_ACK);
			basemsgbuilder.setContent(cmdack.build().toByteString());
			sendcmdinfo.clientinfo.appendSendMessage(basemsgbuilder.build());
		} //else if (cmdinfo.clientcommand.clientinfo.isMobileApp()) {
		//如果是手机客户端, 断开连接后发送到时通知中心
		CenterClientManager.clientManager.addAppNoticeInfo(sendcmdinfo);
	}
	// [end] 用线程把指令写到ActiveM, 并且同时判断指令是否超时
}
