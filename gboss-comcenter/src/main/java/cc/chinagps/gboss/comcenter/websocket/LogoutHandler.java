/*
********************************************************************************************
Discription:  退录
			  
			  
Written By:   ZXZ
Date:         2014-05-09
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.websocket;

import com.google.protobuf.ByteString;

import cc.chinagps.gboss.alarmarray.AlarmManager;
import cc.chinagps.gboss.alarmarray.SeatClientInfo;
import cc.chinagps.gboss.comcenter.WebsocketClientInfo;
import cc.chinagps.gboss.comcenter.CenterClientManager;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;

public class LogoutHandler extends ClientBaseHandler {

	public LogoutHandler(ComCenterBaseMessage basemsg, WebsocketClientInfo info) {
		super(basemsg, info);
	}

	@Override
	public int decode() {
		return ResultCode.OK;
	}

	@Override
	public void run() {
		if (retcode == ResultCode.OK) {
			//添加到客户端管理容器
	    	CenterClientManager.clientManager.removeClient(clientinfo);
			//如果客户端是坐席
			if (clientinfo instanceof SeatClientInfo) {
				SeatClientInfo seatinfo = (SeatClientInfo)clientinfo;
				seatinfo.setstatus(SeatClientInfo.LOGOUTSTATUS);
				AlarmManager.alarmmanager.removeSeat(seatinfo);
			}
		}
	}

	/*
	 * 返回退录应答
	 */
	@Override
	public byte[] response() {
		//打登录返回的报文
		return Serialize(MessageType.Logout_ACK, ByteString.EMPTY);
	}
}
