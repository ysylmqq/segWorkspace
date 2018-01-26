package cc.chinagps.gateway.unit.beforemarket.common;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beforemarket.common.pkg.BeforeMarketPackage;

public interface UploadHandler {
	public boolean handlerPackage(BeforeMarketPackage pkg, UnitServer server, UnitSocketSession session) throws Exception;
}
