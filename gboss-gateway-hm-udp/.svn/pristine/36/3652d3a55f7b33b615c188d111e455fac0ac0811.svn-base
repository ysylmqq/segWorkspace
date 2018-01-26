package cc.chinagps.gateway.unit.seg.upload.cmds;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.seg.pkg.SegPackage;
import cc.chinagps.gateway.unit.seg.upload.SegUploadUtil;
import cc.chinagps.gateway.unit.seg.upload.beans.SegGPSInfo;
import cc.chinagps.gateway.unit.seg.util.SegPkgUtil;

/**
 * 拐点上报
 */
public class Cmd56 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(SegPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception {
		byte[] body = pkg.getBody();
		String strBody = new String(body, SegPkgUtil.PKG_CHAR_ENCODING);
		if(strBody.startsWith("(GDR")){
			//拐点上报
			SegGPSInfo gps = SegGPSInfo.parse(strBody, 4);
			SegUploadUtil.handleGPS(pkg, server, session, gps);
			
			//应答
			SegPkgUtil.commonAckUnit((byte)0x56, session, pkg);
			return true;
		}
		
		return false;
	}
}