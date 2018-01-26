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
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverUnitVersion;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;

public class DeliverUnitVersionHandler extends ClientBaseHandler {

	public String callLetter = "";	//车辆呼号
	public String version = "";		//终端软件版本
	public int gatewayid = 0;  		//网关编号
	public int result = 0;			//版本升级结果 0:成功  其他失败

	public DeliverUnitVersionHandler(ComCenterBaseMessage basemsg, CenterClientHandler handler) {
		super(basemsg, handler);
	}

	@Override
	public int decode() {
		try {
			DeliverUnitVersion unitversion = DeliverUnitVersion.parseFrom(msgcontent);
			if (unitversion.hasGatewayid())
				gatewayid = unitversion.getGatewayid();
			callLetter = unitversion.getUnitVersion().getCallLetter();
			version = unitversion.getUnitVersion().getVersion();
			if (unitversion.getUnitVersion().hasResult()) {
				result = unitversion.getUnitVersion().getResult();
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
	}
}
