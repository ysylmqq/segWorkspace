package cc.chinagps.gateway.unit.pengaoda.upload.cmd;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.pengaoda.pkg.PengAoDaPackage;
import cc.chinagps.gateway.unit.pengaoda.upload.PengAoDaUploadUtil;
import cc.chinagps.gateway.unit.pengaoda.upload.beans.PengAoDaFaultInfo;
import cc.chinagps.gateway.unit.pengaoda.upload.beans.PengAoDaOBDInfo;
import cc.chinagps.gateway.unit.pengaoda.upload.beans.PengAoDaTimeGPSInfo;
import cc.chinagps.gateway.unit.pengaoda.util.PengAoDaPkgUtil;

/**
 * 定时上报
 */
public class CmdTimeUpload extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(PengAoDaPackage pkg,
			UnitServer server, UnitSocketSession session) throws Exception {
		byte[] body = pkg.getBody();
		PengAoDaTimeGPSInfo gps = PengAoDaTimeGPSInfo.parse(body, 0);
		PengAoDaUploadUtil.handleTimeGPS(server, session, pkg, gps);
		if(gps.isAlarm()){
			PengAoDaUploadUtil.handlerTimeAlarm(server, session, pkg, gps);
		}
		
		//obd
		PengAoDaOBDInfo obdInfo = gps.getObdInfo();
		if(obdInfo != null){
			PengAoDaUploadUtil.handleOBD(server, session.getUnitInfo().getCallLetter(), 
					gps.getGpsTime().getTime(), obdInfo, gps.isHistory());
		}
		
		//fault
		PengAoDaFaultInfo faultInfo = gps.getFaultInfo();
		if(faultInfo != null){
			PengAoDaUploadUtil.handleFaultInfo(server, session.getUnitInfo().getCallLetter(),
					faultInfo, gps.getGpsTime().getTime(), gps.isHistory());
		}
		
		byte[] res = PengAoDaPkgUtil.getCommonResponseToUnit(session, gps.getTerminalId(), "V1");
		session.sendData(res);
		return true;
	}
}