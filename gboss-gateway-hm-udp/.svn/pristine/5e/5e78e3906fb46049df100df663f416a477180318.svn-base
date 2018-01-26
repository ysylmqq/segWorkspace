package cc.chinagps.gateway.unit.db44.upload.cmd;

import cc.chinagps.gateway.seat.cmd.CmdUtil;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.db44.pkg.DB44Package;
import cc.chinagps.gateway.unit.db44.upload.DB44UploadUtil;

/**
 * ²é³µÓ¦´ð
 */
public class Cmd01 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(DB44Package pkg,
			UnitServer server, UnitSocketSession session) throws Exception {
		DB44UploadUtil.commonResponseWithGPS(pkg, server, session, CmdUtil.CMD_ID_POSITION);
		return true;
	}
}