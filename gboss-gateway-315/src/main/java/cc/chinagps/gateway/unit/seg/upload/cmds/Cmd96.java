package cc.chinagps.gateway.unit.seg.upload.cmds;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.seg.pkg.SegPackage;
import cc.chinagps.gateway.unit.seg.upload.SegUploadUtil;
import cc.chinagps.gateway.unit.seg.upload.beans.service.ServiceData;
import cc.chinagps.gateway.unit.seg.upload.beans.service.ServiceDetail;
import cc.chinagps.gateway.unit.seg.upload.beans.service.ShenZhenService;
import cc.chinagps.gateway.unit.seg.util.SegPkgUtil;

/**
 * 运营数据
 */
public class Cmd96 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(SegPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception {
		byte[] body = pkg.getBody();
		
		ServiceData serviceData = ServiceData.parse(body);
		if(serviceData.getCompanyCode() == 0x44){
			ServiceDetail serviceDetail = ShenZhenService.parse(body, serviceData.getServiceDataOffset());
			SegUploadUtil.handleServiceData(pkg, server, session, serviceData, serviceDetail);
			
			ackServiceData(pkg, serviceData, session);
			return true;
		}else{
			SegUploadUtil.handleServiceData(pkg, server, session, serviceData, null);
			
			ackServiceData(pkg, serviceData, session);
			return false;
		}
	}
	
	private void ackServiceData(SegPackage pkg, ServiceData serviceData, UnitSocketSession session){
		byte cmdId = 0x15;
		byte serviceSn = serviceData.getServiceSn();
		byte[] resBody = new byte[]{serviceSn};
		byte[] res = SegPkgUtil.encode(cmdId, pkg.getSerialNumberBytes(), resBody);
		session.sendData(res);
		
		if(serviceSn == 0x5B || serviceSn == 0x5C || serviceSn == 0x5D){
			byte[] old = SegPkgUtil.encodeOld(cmdId, pkg.getSerialNumberBytes(), resBody);
			session.sendData(old);
		}
	}
}