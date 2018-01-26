/*
********************************************************************************************
Discription:  客户端发送链路检测报文，回复链路检测应答
			  
			  
Written By:   ZXZ
Date:         2014-04-28
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.websocket;

import java.util.HashSet;
import cc.chinagps.gboss.alarmarray.AlarmManager;
import cc.chinagps.gboss.alarmarray.SeatClientInfo;
import cc.chinagps.gboss.comcenter.WebsocketClientInfo;
import cc.chinagps.gboss.comcenter.CenterClientManager;
import cc.chinagps.gboss.comcenter.ComCenter;
import cc.chinagps.gboss.comcenter.UnitInfoManager;
import cc.chinagps.gboss.comcenter.UnitInfoManager.CustomerInfo;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.Login;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.Login_ACK;

public class LoginHandler extends ClientBaseHandler {
	
	private HashSet<String> callletterlist = new HashSet<String>();	//要添加的监控列表
	private String username = "";	//登录名
	public String password = "";	//登录密码
	private String seatid = "";			//坐席编号（坐席登录时用）
	public String usertype = "";		//用户类型（应用程序类型）
	public String userversion = "";		//用户版本
	
	public LoginHandler(ComCenterMessage.ComCenterBaseMessage basemsg, WebsocketClientInfo info) {
		super(basemsg, info);
	}

	@Override
	public int decode() {
		try {
			Login login = Login.parseFrom(msgcontent);
			username = login.getUsername();
			username = username.replace('/', '_');	//名称不能带/字符
			password = login.getPassword();
			if (login.hasSeatid()) {
				seatid = login.getSeatid();
			}
			if (login.hasUsertype()) {
				usertype = login.getUsertype();
			}
			if (login.hasUserversion()) {
				userversion = login.getUserversion();
			}
			for(String callLetter : login.getCallLettersList()) {
				if (callLetter.equals(WebsocketClientInfo.ALLUNIT) || callLetter == null || callLetter.isEmpty()) {
					continue;
				}
				callletterlist.add(callLetter);
			}
        } catch(Exception e) {
			e.printStackTrace();
			retcode = ResultCode.Decode_Error;
			retmsg = "解码失败";
		}
		return retcode;
	}

	@Override
	public void run() {
		if (ComCenter.verifypassword) {
			if (username.toLowerCase().equals("zhangxz")) {
				retcode = ResultCode.UserName_Error;
				retmsg = "登录用户名错误";
				return;
			}
			CustomerInfo customerinfo = UnitInfoManager.unitinfomanager.getCustomerInfo(username);
			if (customerinfo == null) {
				System.out.println("Memcached没有客户[" + username + "]的资料");
				retcode = ResultCode.UserName_Error;
				retmsg = "登录用户名错误";
				return;
			}
			if (!customerinfo.password.equals(password)) {
				retcode = ResultCode.Password_Error;
				retmsg = "密码错误";
				return;
			}
		}
		//如果登录成功，并且带有呼号，则 添加监控列表
		clientinfo.setLogin(retcode == ResultCode.OK);
		if (retcode == ResultCode.OK) {
			if (!usertype.isEmpty()) {
				clientinfo.clienttype1 = usertype;
			}
			if (!userversion.isEmpty()) {
				clientinfo.clientversion = userversion;
			}
			clientinfo.username = username;
			clientinfo.userid = seatid;
			//添加到客户端管理容器
	    	CenterClientManager.clientManager.addClient(clientinfo);
			//如果客户端是坐席
			if (clientinfo instanceof SeatClientInfo) {
				SeatClientInfo seat = (SeatClientInfo)clientinfo;
				//因为序列化，下面几个字段从基类定重定义
				seat.setstatus(SeatClientInfo.LOGINSTATUS);
				retcode = AlarmManager.alarmmanager.appendSeat(seat);
				clientinfo.setLogin(retcode == ResultCode.OK);
				switch(retcode) {
				case ResultCode.SeatExist_Error: 
					retmsg = "坐席已经登录" + AlarmManager.alarmmanager.getSeatIpaddr(username);
					return;
				case ResultCode.ComCenter_Error:
					retmsg = "通信中心处理坐席登录错误";
					return;
				case ResultCode.ZooKeeper_Error:
					retmsg = "坐席登录分布式处理错误";
					return;
				}
			}
			
			if (callletterlist != null) {
				if (callletterlist.size() > 0) {
					this.clientinfo.addMonitor(callletterlist, null, false);
				}
			}
		}
	}

	@Override
	public byte[] response() {
		//打登录返回的报文
		Login_ACK.Builder loginack = Login_ACK.newBuilder();
		loginack.setRetcode(retcode);
		loginack.setRetmsg(retmsg);
		loginack.setUsername(username);
		return Serialize(MessageType.Login_ACK, loginack.build().toByteString()); 
	}

}
