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

import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverGPS;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsInfo;

public class DeliverGPSHandler extends ClientBaseHandler {

	public int gatewayid = 0;  		//网关编号
	public int gatewaytype = 0;		//网关类型：0或无:表示NET网关，1:短信网关
	public GpsInfo gpsinfo = null;

	public DeliverGPSHandler(ComCenterBaseMessage basemsg, CenterClientHandler handler) {
		super(basemsg, handler);
	}

	@Override
	public int decode() {
		try {
			DeliverGPS delivergps = DeliverGPS.parseFrom(msgcontent);
			if (delivergps.hasGatewayid())
				gatewayid = delivergps.getGatewayid();
			if (delivergps.hasGatewaytype())
				gatewaytype = delivergps.getGatewaytype();
			gpsinfo = delivergps.getGpsinfo();
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
