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

import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.GetLastInfo_ACK;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.testtools.StressTestDlg;
import cc.chinagps.gboss.comcenter.testtools.WebSocketClientHandler;

public class GetLastInfoACKHandler extends serverBaseHandler {

	public GetLastInfoACKHandler(ComCenterBaseMessage basemsg, WebSocketClientHandler handler) {
		super(basemsg, handler);
	}

	@Override
	public int decode() {
		try {
			GetLastInfo_ACK lastack = GetLastInfo_ACK.parseFrom(msgcontent);
			retcode = lastack.getRetcode();
			retmsg = lastack.getRetmsg();
        } catch(Exception e) {
			e.printStackTrace();
			retcode = ResultCode.Decode_Error;
			retmsg = "解码失败";
		}
		return retcode;
	}

	@Override
	public void run() {
		if (retcode == ResultCode.OK) {
			StressTestDlg.lastoktimes.incrementAndGet();
		} else {
			StressTestDlg.lasterrortimes.incrementAndGet();
		}
	}

}
