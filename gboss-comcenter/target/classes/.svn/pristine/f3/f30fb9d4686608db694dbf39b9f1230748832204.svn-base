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

import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverOperateData;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.OperateData;

public class DeliverOperateDataHandler extends ClientBaseHandler {

	public int gatewayid = 0;  		//网关编号
	public int gatewaytype = 0;		//网关类型：0或无:表示NET网关，1:短信网关
	public OperateData operatedata;

	public DeliverOperateDataHandler(ComCenterBaseMessage basemsg, CenterClientHandler handler) {
		super(basemsg, handler);
	}

	@Override
	public int decode() {
		try {
			DeliverOperateData deliveroperatedata = DeliverOperateData.parseFrom(msgcontent);
			if (deliveroperatedata.hasGatewayid())
				gatewayid = deliveroperatedata.getGatewayid();
			if (deliveroperatedata.hasGatewaytype())
				gatewaytype = deliveroperatedata.getGatewaytype();
			operatedata = deliveroperatedata.getData();
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
