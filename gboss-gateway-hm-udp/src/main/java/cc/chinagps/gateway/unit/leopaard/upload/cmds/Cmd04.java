package cc.chinagps.gateway.unit.leopaard.upload.cmds;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.leopaard.pkg.LeopaardPackage;
import cc.chinagps.gateway.unit.leopaard.util.LeopaardUploadUtil;

/**
 * ÐÄÌø°ü
 */
public class Cmd04 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(LeopaardPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception{
		LeopaardUploadUtil.commonSuccessAck(session, pkg);
		return true;
	}
}