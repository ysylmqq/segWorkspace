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

import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.ShortMessage;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverSMS;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;

public class DeliverSMSHandler extends ClientBaseHandler {

	public int gatewayid = 0;  		//网关编号
	public int gatewaytype = 0;		//网关类型：0或无:表示NET网关，1:短信网关
	public ShortMessage msg = null;

	public DeliverSMSHandler(ComCenterBaseMessage basemsg, CenterClientHandler handler) {
		super(basemsg, handler);
	}

	@Override
	public int decode() {
		try {
			DeliverSMS deliversms = DeliverSMS.parseFrom(msgcontent);
			if (deliversms.hasGatewayid())
				gatewayid = deliversms.getGatewayid();
			if (deliversms.hasGatewaytype())
				gatewaytype = deliversms.getGatewaytype();
			msg = deliversms.getMsg();
        } catch(Exception e) {
			e.printStackTrace();
			retcode = ResultCode.Decode_Error;
			retmsg = "解码失败";
		}
		return retcode;
	}

	@Override
	public void run() {
	}

}
