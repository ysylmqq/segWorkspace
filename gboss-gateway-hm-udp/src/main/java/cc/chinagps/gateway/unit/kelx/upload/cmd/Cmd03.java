package cc.chinagps.gateway.unit.kelx.upload.cmd;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.kelx.pkg.KlxPackage;
import cc.chinagps.gateway.unit.kelx.upload.UploadHandler;

/**
 * 参数查询应答(不处理)
 */
public class Cmd03 implements UploadHandler{
	@Override
	public boolean handlerPackage(KlxPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception {
		return true;
	}
}