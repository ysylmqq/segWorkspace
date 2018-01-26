package cc.chinagps.gateway.unit.kelx.upload.cmd;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.kelx.pkg.KlxPackage;
import cc.chinagps.gateway.unit.kelx.upload.KlxUploadUtil;
import cc.chinagps.gateway.unit.kelx.upload.UploadHandler;
import cc.chinagps.gateway.unit.kelx.upload.beans.KlxTravelInfo;
import cc.chinagps.gateway.unit.kelx.util.KlxPkgUtil;

/**
 * ÐÐ³Ì
 */
public class Cmd07 implements UploadHandler{
	@Override
	public boolean handlerPackage(KlxPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception {
		byte[] body = pkg.getBody();
		KlxTravelInfo travelInfo = KlxTravelInfo.parse(body, 2);
		KlxUploadUtil.handleTravelInfo(server, session, pkg, travelInfo);
		
		//ack
		byte[] source = KlxPkgUtil.getCommonResponseToUnit(session, (short) 7, pkg.getSn(), (byte) 0);
		session.sendData(source);
		
		return true;
	}
}