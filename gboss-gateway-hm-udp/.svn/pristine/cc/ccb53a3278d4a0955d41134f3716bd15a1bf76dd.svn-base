package cc.chinagps.gateway.unit.kelx.upload.cmd;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.kelx.pkg.KlxPackage;
import cc.chinagps.gateway.unit.kelx.upload.UploadHandler;
import cc.chinagps.gateway.unit.kelx.util.KlxPkgUtil;

/**
 * 车台上传OBD故障状态
 */
public class Cmd0B implements UploadHandler{
	@Override
	public boolean handlerPackage(KlxPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception {
		//ack
		byte[] source = KlxPkgUtil.getCommonResponseToUnit(session, (short) 0x0B, pkg.getSn(), (byte) 0);
		session.sendData(source);
		return true;
	}
}