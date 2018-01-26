package cc.chinagps.gateway.unit.kelx.upload.cmd;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.kelx.pkg.KlxPackage;
import cc.chinagps.gateway.unit.kelx.upload.UploadHandler;
import cc.chinagps.gateway.unit.kelx.util.KlxPkgUtil;

/**
 * ÉÏ´«OBD×´Ì¬
 */
public class Cmd08 implements UploadHandler{
	@Override
	public boolean handlerPackage(KlxPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception {
		//ack
		byte[] source = KlxPkgUtil.getCommonResponseToUnit(session, (short) 8, pkg.getSn(), (byte) 0);
		session.sendData(source);
		return true;
	}
}