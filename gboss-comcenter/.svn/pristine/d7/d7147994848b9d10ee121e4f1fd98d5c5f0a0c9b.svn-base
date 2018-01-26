/*
********************************************************************************************
Discription: 终端遥控指令信息
 			    
			  
Written By:   ZXZ
Date:         2015-06-05
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.command;

import java.util.List;

import cc.chinagps.gboss.comcenter.WebsocketClientInfo;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.websocket.SendCommandHandler;

public class SendCommandInfo {
	//终端通信模式
	public static short SMSMODE = 1;
	public static short NETMODE = 2;
	public static int commandsendtimeout = 30000; 	  //网络下发指令超时毫秒数（默认30秒）
	public static int smcommandsendtimeout = 120000; //短信下发指令超时毫秒数（默认2分钟）
	
	public List<String> sendparams;		//发送参数
	public WebsocketClientInfo clientinfo;	//指令发送客户端信息，处理过程中要保留
	public String callletter;			//终端呼号
	public String cmdsn;				//命令唯一ID
	public String resmsg;				//返回错误
	public String plateno;				//车牌号
	public long unitid;					//终端ID
	public long cmdtime1;				//指令时间
	public long expiretime;				//指令时间
	public long sendtime;				//发送时间
	public long restime;				//回应时间
	public long customerid;				//用户ID
	public int cmdId;					//指令ID
	public int unitsn;     				//网关发送给终端通信的流水号, 海马有可能, 是短信下发, 流量应答
	public short channelId; 			//通道号(网关编号)
	public short sendcode;				//发送结果
	public short rescode;				//回应结果
	public short mode;	    			//通信模式, 1=短信, 2=数据流量
	public short unittype; 				//终端协议类型, 2:T3688, 8:DB44, 9:龙翰, 10:科联星,
	
	public SendCommandInfo(SendCommandHandler commandhandler) {
		this.sendparams = commandhandler.params;
		this.clientinfo = commandhandler.clientinfo;
		this.cmdsn = commandhandler.cmdsn;
		this.resmsg = "";
		this.plateno = "";
		this.unitid = 0;
		this.customerid = 0;
		this.cmdtime1 = System.currentTimeMillis();
		this.sendtime = 0;
		this.restime = 0;
		this.cmdId = commandhandler.cmdId;
		this.sendcode = 0;
		this.rescode = 0;
		this.unitsn = 0;
		this.unittype = 2;
		setGatewayId((short)(commandhandler.channelId));
	}

	//设置网关编号
	public void setGatewayId(short gatewayid) {
		this.channelId = gatewayid;
		if (gatewayid > 0) {
			//流量还是短信模式?
			this.mode = CommandManager.smsgatewaymap.containsKey(channelId) ? SMSMODE : NETMODE;
			//朝野时间
			this.expiretime = this.cmdtime1 + (mode == SMSMODE ? smcommandsendtimeout : commandsendtimeout);
		} else {
			this.mode = NETMODE;
			this.expiretime = this.cmdtime1 + commandsendtimeout; 
		}
	}
	
	//设置发送结果
	public void setSendCode(short code, String strmsg) {
		this.sendtime = System.currentTimeMillis();
		this.sendcode = code;
		if (code != ResultCode.OK) {
			this.rescode = code;
			if (strmsg != null) {
				this.resmsg = strmsg;
			}
		}
	}
	
	//设置终端回应结果
	public void setResCode(short code, String strmsg) {
		this.restime = System.currentTimeMillis();
		this.rescode = code;
		if (strmsg != null) {
			this.resmsg += strmsg;
		}
	}
}
