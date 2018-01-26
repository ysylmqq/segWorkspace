/*
********************************************************************************************
Discription:  下发终端指令
			  
			  
Written By:   ZXZ
Date:         2014-05-09
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.websocket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;

import cc.chinagps.gboss.comcenter.WebsocketClientInfo;
import cc.chinagps.gboss.comcenter.command.CommandManager;
import cc.chinagps.gboss.comcenter.outinterface.OutInterfaceClientInfo;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.SendCommand;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.SendCommand_ACK;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.database.DBUtil;

public class SendCommandHandler extends ClientBaseHandler {

	public String cmdsn;
	public HashSet<String> callletterlist;
	public List<String> params;
	public int cmdId;
	public int channelId;	//通道号
	public boolean addmonitor;
	public long cmdtime;

	public SendCommandHandler(ComCenterBaseMessage basemsg, WebsocketClientInfo info) {
		super(basemsg, info);
		cmdsn = "";
		callletterlist = new HashSet<String>();
		params = null;
		cmdId = 0;
		channelId = 0;	//通道号
		addmonitor = false;
		cmdtime = System.currentTimeMillis();
	}

	@Override
	public int decode() {
		try {
			SendCommand sendcommand = SendCommand.parseFrom(msgcontent);
			cmdsn = sendcommand.getSn();
			for(String callLetter : sendcommand.getCallLettersList()) {
				callletterlist.add(callLetter.trim());
			}
			cmdId = sendcommand.getCmdId();
			params = sendcommand.getParamsList();
			if (sendcommand.hasChannelId()) {
				channelId = sendcommand.getChannelId();
			}
			if (sendcommand.hasAddmonitor()) {
				addmonitor = sendcommand.getAddmonitor();
			}
		} catch (Exception e) {
			e.printStackTrace();
			retcode = ResultCode.Decode_Error;
			retmsg = "解码失败";
		}
		return retcode;
	}

	@Override
	public void run() {
		if (!clientinfo.isLogin()) {
			retcode = ResultCode.NoLogin_Error;
			retmsg = "未登录";
			return;
		}
		//外部接口程序，要判断呼号是否属于这家公司
		if (clientinfo instanceof OutInterfaceClientInfo) {
			for(String callletter : callletterlist) {
				if (!((OutInterfaceClientInfo)clientinfo).hasCallLetter(callletter)) {
					retcode = ResultCode.CallLetterExist_Error;
					retmsg = "不存在车载终端呼号";
					return;
				}
			}
		}
		try {
			//如果是海马读车辆配置简码的命令，不用发送到终端，直接从数据库读，在response中返回
			if (this.cmdId != 0x80B4) { 
				CommandManager.commandmanager.appendNewCommand(this);
			}
			//如果要添加到监控列表
			if (addmonitor) {
				this.clientinfo.addMonitor(callletterlist, null, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			retcode = ResultCode.Decode_Error;
			retmsg = "命令执行失败";
		}
	}

	@Override
	public byte[] response() {
		//不直接返回，等待命令线程处理，先按网关写到MQ，再从MQ读返回结果，30秒超时失败
		if (this.cmdId == 0x80B4) { 
			//如果是海马读车辆配置简码的命令，不用发送到终端，直接从数据库读，在此中返回
			ComCenterMessage.ComCenterBaseMessage.Builder basemsgbuilder = ComCenterMessage.ComCenterBaseMessage.newBuilder();
			basemsgbuilder.setId(MessageType.SendCommand_ACK);
			
			SendCommand_ACK.Builder ackbuilder = SendCommand_ACK.newBuilder();
			ackbuilder.setSn(this.cmdsn);
			ackbuilder.setCmdId(this.cmdId);
			ackbuilder.setRetcode(ResultCode.OK);
			for(String callletter : callletterlist) {
				ackbuilder.setCallLetter(callletter);
				ackbuilder.clearParams();
				ackbuilder.addParams(QueryHMConfigCode(callletter));
				basemsgbuilder.setContent(ackbuilder.build().toByteString());
				clientinfo.appendSendMessage(basemsgbuilder.build());
			}
		}
		return null;
	}

	/**
	 * 从数据库读海马车辆配置简码
	 * @throws Exception 
	 */
	private String QueryHMConfigCode(String callletter) {
		//查询sql
		String sql = "SELECT t.equip_code, c.code1 FROM t_ba_sim t LEFT JOIN t_ba_vehicle_conf c ON t.call_letter=c.call_letter WHERE t.call_letter=?";
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
				StringBuilder sb = new StringBuilder();
				//按序号取数据
				sb.append(DBUtil.GetStringFromColumn(rs, 1));
				int code1 = DBUtil.GetIntegerFromColumn(rs, 2);
				//子系统配置, 每1位表示1个子系统, 1=有, 0=无, 从低位1到高位64依次为ABS,ESP/DCU,PEPS,TPMS,SRS,EPS,EMS,IMMO,BCM,TCU,ICM,APM',
				String codename[] = "ABS;ESP/DCU;PEPS;TPMS;SRS;EPS;EMS;IMMO;BCM;TCU;ICM;APM".split(";");
				int ior = 1;
				for(int i=0; i<codename.length; i++) {
					if ((code1 & ior) != 0) {
						sb.append(", ");
						sb.append(codename[i]);
					}
					ior <<= 1;
				}
				return sb.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//关闭数据库资源
			DBUtil.closeDB(rs, pst, con);
		}
		return "";
	}
}
