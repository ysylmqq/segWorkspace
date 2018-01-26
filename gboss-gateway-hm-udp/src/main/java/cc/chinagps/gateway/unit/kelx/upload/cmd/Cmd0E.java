package cc.chinagps.gateway.unit.kelx.upload.cmd;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.kelx.pkg.KlxPackage;
import cc.chinagps.gateway.unit.kelx.upload.UploadHandler;
import cc.chinagps.gateway.unit.kelx.util.KlxPkgUtil;

/**
 * 终端发送本地AGPS数据包时间戳
 */
public class Cmd0E implements UploadHandler{
	@Override
	public boolean handlerPackage(KlxPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception {
		//ack
		byte[] source = KlxPkgUtil.getCommonResponseToUnit(session, (short) 0xE, pkg.getSn(), (byte) 0);
		session.sendData(source);
		return true;
	}
}
