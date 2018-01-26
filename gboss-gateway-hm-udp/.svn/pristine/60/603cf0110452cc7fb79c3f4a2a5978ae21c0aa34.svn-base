package cc.chinagps.gateway.unit.db44.upload.cmd;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.db44.pkg.DB44Package;
import cc.chinagps.gateway.unit.db44.util.DB44PkgUtil;
import cc.chinagps.gateway.util.Constants;

/**
 * ÍËÂ¼
 */
public class Cmd81 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(DB44Package pkg,
			UnitServer server, UnitSocketSession session) throws Exception {
		byte[] data = DB44PkgUtil.encode(pkg.getLoop(), (byte) 0x81, Constants.ZERO_BYTES_DATA);
		session.sendData(data);
		session.close();
		
		return true;
	}
}