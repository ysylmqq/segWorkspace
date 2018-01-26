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
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverUnitLoginOut;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.testtools.StressTestDlg;
import cc.chinagps.gboss.comcenter.testtools.WebSocketClientHandler;

public class DeliverUnitLoginOutHandler extends serverBaseHandler {

	public String callLetter = "";	//车辆呼号
	public int gatewayid = 0;  		//网关编号
	public int inorout = 1;         //0:退录，1:登录

	public DeliverUnitLoginOutHandler(ComCenterBaseMessage basemsg, WebSocketClientHandler handler) {
		super(basemsg, handler);
	}

	@Override
	public int decode() {
		try {
			DeliverUnitLoginOut unitloginout = DeliverUnitLoginOut.parseFrom(msgcontent);
			if (unitloginout.hasGatewayid())
				gatewayid = unitloginout.getGatewayid();
			if (unitloginout.hasInorout())
				inorout = unitloginout.getInorout();
			callLetter = unitloginout.getCallLetter();
        } catch(Exception e) {
			e.printStackTrace();
			retcode = ResultCode.Decode_Error;
			retmsg = "解码失败";
		}
		return retcode;
	}

	@Override
	public void run() {
		StressTestDlg.delivercount.incrementAndGet();
	}

}
