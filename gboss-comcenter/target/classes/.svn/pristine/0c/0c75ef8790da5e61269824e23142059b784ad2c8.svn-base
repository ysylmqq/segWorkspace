/*
********************************************************************************************
Discription:  通信中心单元测试工具用
			  
			  
Written By:   ZXZ
Date:         2014-05-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.serverHandler;

import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.Login_ACK;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.testtools.WebSocketClientHandler;

public class LoginACKHandler extends serverBaseHandler {

	protected String username = "";

	public LoginACKHandler(ComCenterBaseMessage basemsg, WebSocketClientHandler handler) {
		super(basemsg, handler);
	}

	@Override
	public int decode() {
		try {
			Login_ACK loginack = Login_ACK.parseFrom(msgcontent);
			retcode = loginack.getRetcode();
			retmsg = loginack.getRetmsg();
			username = loginack.getUsername();
        } catch(Exception e) {
			e.printStackTrace();
			retcode = ResultCode.Decode_Error;
			retmsg = "解码失败";
		}
		return retcode;
	}

	@Override
	public void run() {
		if (this.handler != null) {
			handler.islogined = (retcode == ResultCode.OK);
			if (handler.islogined) {
				handler.AddMonitor();
			}
		}
	}
}
