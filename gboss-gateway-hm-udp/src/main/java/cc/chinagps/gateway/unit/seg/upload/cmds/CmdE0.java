package cc.chinagps.gateway.unit.seg.upload.cmds;

import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.seg.pkg.SegPackage;
import cc.chinagps.gateway.unit.seg.upload.SegUploadUtil;

/**
 * …œ∑¢∂Ã–≈
 */
public class CmdE0 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(SegPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception {
		String info = new String(pkg.getBody(), "unicode");
		SegUploadUtil.handleUploadShortMessage(pkg, server, session, info);
		return true;
	}
}