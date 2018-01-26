package cc.chinagps.gateway.unit.leopaard.upload.cmds;

import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.leopaard.pkg.LeopaardPackage;
import cc.chinagps.gateway.unit.UnitServer;

public interface UploadHandler {
	/**
	 * @return flag true:能处理  false:不能处理
	 */
	public boolean handlerPackage(LeopaardPackage pkg, UnitServer server, UnitSocketSession session) throws Exception;
}