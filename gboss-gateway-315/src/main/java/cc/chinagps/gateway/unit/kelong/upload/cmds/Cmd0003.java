package cc.chinagps.gateway.unit.kelong.upload.cmds;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.kelong.pkg.KeLongPackage;
import cc.chinagps.gateway.unit.kelong.util.KeLongUploadUtil;

/**
 * ÐÄÌø°ü
 */
public class Cmd0003 extends CheckLoginHandler {
	@Override
	public boolean handlerPackageSessionChecked(KeLongPackage pkg, UnitServer server, UnitSocketSession session)
			throws Exception {
		KeLongUploadUtil.commonMsgAck(session, pkg, (short) 0x8000, (short) 0x0003, (byte) 0x00);
		return true;
	}
}