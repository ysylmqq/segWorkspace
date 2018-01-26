package cc.chinagps.gateway.unit.kelong.upload.cmds;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.kelong.pkg.KeLongPackage;
import cc.chinagps.gateway.unit.kelong.util.KeLongTimeFormatUtil;
import cc.chinagps.gateway.unit.kelong.util.KeLongUploadUtil;

/**
 * 时间同步
 */
public class Cmd0004 extends CheckLoginHandler {
	@Override
	public boolean handlerPackageSessionChecked(KeLongPackage pkg, UnitServer server, UnitSocketSession session)
			throws Exception {
		byte[] time = KeLongTimeFormatUtil.getCurrentTime0();
		KeLongUploadUtil.msgAck(session, pkg, (short) 0x8004, time);
		return true;
	}
}