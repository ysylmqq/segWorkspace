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

import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverFault;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.FaultInfo;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.testtools.StressTestDlg;
import cc.chinagps.gboss.comcenter.testtools.WebSocketClientHandler;

public class DeliverFaultHandler extends serverBaseHandler {

	public int gatewayid = 0;  		//网关编号
	public int gatewaytype = 0;		//网关类型：0或无:表示NET网关，1:短信网关
	public FaultInfo faultinfo;

	public DeliverFaultHandler(ComCenterBaseMessage basemsg, WebSocketClientHandler handler) {
		super(basemsg, handler);
	}

	@Override
	public int decode() {
		try {
			DeliverFault deliverfault = DeliverFault.parseFrom(msgcontent);
			if (deliverfault.hasGatewayid())
				gatewayid = deliverfault.getGatewayid();
			if (deliverfault.hasGatewaytype())
				gatewaytype = deliverfault.getGatewaytype();
			faultinfo = deliverfault.getFaultinfo();
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
