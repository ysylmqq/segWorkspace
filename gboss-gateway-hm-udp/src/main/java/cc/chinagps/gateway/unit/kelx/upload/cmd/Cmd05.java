package cc.chinagps.gateway.unit.kelx.upload.cmd;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.kelx.pkg.KlxPackage;
import cc.chinagps.gateway.unit.kelx.upload.KlxUploadUtil;
import cc.chinagps.gateway.unit.kelx.upload.UploadHandler;
import cc.chinagps.gateway.unit.kelx.upload.beans.KlxFaultInfo;
import cc.chinagps.gateway.unit.kelx.util.KlxPkgUtil;

/**
 * π ’œ–≈œ¢
 */
public class Cmd05 implements UploadHandler{
	@Override
	public boolean handlerPackage(KlxPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception {
		byte[] body = pkg.getBody();
		KlxFaultInfo faultInfo = KlxFaultInfo.parse(body, 2);
		KlxUploadUtil.handleFaultInfo(server, session, pkg, faultInfo);
		
		//ack
		byte[] source = KlxPkgUtil.getCommonResponseToUnit(session, (short) 5, pkg.getSn(), (byte) 0);
		session.sendData(source);
		
		return true;
	}
}