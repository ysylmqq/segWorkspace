package cc.chinagps.gateway.unit.leopaard.upload.cmds;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.leopaard.pkg.LeopaardPackage;

/**
 * ÍËÂ¼°ü
 */
public class Cmd07 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(LeopaardPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception{
		session.close();
		return true;
	}
}