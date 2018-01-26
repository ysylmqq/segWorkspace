package cc.chinagps.gateway.unit.leopaard.upload.cmds;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.leopaard.pkg.LeopaardPackage;
import cc.chinagps.gateway.unit.leopaard.util.LeopaardUploadUtil;

/**
 * POI数据查询
 */
public class Cmd86 extends CheckLoginHandler {
	@Override
	public boolean handlerPackageSessionChecked(LeopaardPackage pkg, UnitServer server, UnitSocketSession session)
			throws Exception {
		byte[] respData = new byte[] { 1 };
		//直接回应已经开卡
		LeopaardUploadUtil.commonAck(session, pkg, (byte) 0x01, respData);
		return true;
	}
}