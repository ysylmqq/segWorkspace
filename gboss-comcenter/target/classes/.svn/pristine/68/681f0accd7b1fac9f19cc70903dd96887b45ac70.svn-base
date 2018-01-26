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
package cc.chinagps.gboss.comcenter.interprotocol.clienttest;

import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.AddMonitor_ACK;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;

public class AddMonitorACKHandler extends ClientBaseHandler {

	public AddMonitorACKHandler(ComCenterBaseMessage basemsg, CenterClientHandler handler) {
		super(basemsg, handler);
	}

	@Override
	public int decode() {
		try {
			AddMonitor_ACK addmonitorack = AddMonitor_ACK.parseFrom(msgcontent);
			retcode = addmonitorack.getRetcode();
			retmsg = addmonitorack.getRetmsg();
			//List<String> callletterlist = addmonitorack.getCallLettersList();
        } catch(Exception e) {
			e.printStackTrace();
			retcode = ResultCode.Decode_Error;
			retmsg = "解码失败";
		}
		return retcode;
	}

	@Override
	public void run() {
		System.out.println("AddMonitorACK RetCode: " + retcode);
	}
}
