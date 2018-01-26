package cc.chinagps.gateway.unit.pengaoda.upload;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.pengaoda.pkg.PengAoDaPackage;

public interface UploadHandler {
	public boolean handlerPackage(PengAoDaPackage pkg, UnitServer server, UnitSocketSession session) throws Exception;
}