package cc.chinagps.gateway.unit.pengaoda.upload.cmd;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.pengaoda.pkg.PengAoDaPackage;
import cc.chinagps.gateway.unit.pengaoda.upload.PengAoDaUploadUtil;
import cc.chinagps.gateway.unit.pengaoda.upload.beans.PengAoDaGPSInfo;
import cc.chinagps.gateway.unit.pengaoda.util.PengAoDaPkgUtil;

/**
 * 行程开始
 */
public class CmdV9 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(PengAoDaPackage pkg,
			UnitServer server, UnitSocketSession session) throws Exception {
		byte[] body = pkg.getBody();
		PengAoDaGPSInfo gps = PengAoDaGPSInfo.parse(body);
		
		//gps
		PengAoDaUploadUtil.handleGPS(server, session, pkg, gps);
		if(gps.isAlarm()){
			PengAoDaUploadUtil.handlerAlarm(server, session, pkg, gps);
		}
		
		//travel start
		PengAoDaUploadUtil.handleTravelStart(server, session, gps);
				
		//ack
		byte[] res = PengAoDaPkgUtil.getCommonResponseToUnit(session, gps.getTerminalId(), "V9");
		session.sendData(res);
		
		return true;
	}
}