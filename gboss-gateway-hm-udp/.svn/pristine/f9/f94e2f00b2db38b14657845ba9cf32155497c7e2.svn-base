package cc.chinagps.gateway.unit.kelx.upload.cmd;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.kelx.pkg.KlxPackage;
import cc.chinagps.gateway.unit.kelx.upload.KlxUploadUtil;
import cc.chinagps.gateway.unit.kelx.upload.UploadHandler;
import cc.chinagps.gateway.unit.kelx.upload.beans.KlxGPSInfo;
import cc.chinagps.gateway.unit.kelx.util.KlxPkgUtil;

/**
 * Œª÷√–≈œ¢
 */
public class Cmd04 implements UploadHandler{
	@Override
	public boolean handlerPackage(KlxPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception {
		byte[] body = pkg.getBody();
		KlxGPSInfo gps = KlxGPSInfo.parse(body, 2);
		KlxUploadUtil.handleGPS(server, session, pkg, gps);
		
		if(gps.isAlarm()){
			KlxUploadUtil.handlerAlarm(server, session, pkg, gps);
		}
		
		//ack
		byte[] source = KlxPkgUtil.getCommonResponseToUnit(session, (short) 4, pkg.getSn(), (byte) 0);
		session.sendData(source);
		
		return true;
	}
}