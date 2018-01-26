package cc.chinagps.gateway.unit.kelx.upload;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.kelx.pkg.KlxPackage;

public interface UploadHandler {
	public boolean handlerPackage(KlxPackage pkg, UnitServer server, UnitSocketSession session) throws Exception;
}