package cc.chinagps.gateway.unit.seg.upload.cmds;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.seg.pkg.SegPackage;
import cc.chinagps.gateway.unit.seg.upload.SegUploadUtil;
import cc.chinagps.gateway.unit.seg.upload.beans.SegGPSInfo;
import cc.chinagps.gateway.unit.seg.upload.beans.SegTravelInfo;
import cc.chinagps.gateway.unit.seg.util.SegPkgUtil;

/**
 * OBD行程记录信息
 */
public class Cmd4A extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(SegPackage pkg,
			UnitServer server, UnitSocketSession session) throws Exception {
		byte[] body = pkg.getBody();
		String strBody = new String(body, SegPkgUtil.PKG_CHAR_ENCODING);
		if(strBody.startsWith("(ACC")){
			//OBD行程记录信息
			SegGPSInfo gps = SegGPSInfo.parse(strBody, 4);
			SegUploadUtil.handleGPS(pkg, server, session, gps);
			
			SegTravelInfo travelInfo = gps.getTravelInfo();
			if(travelInfo != null){
				SegUploadUtil.handleTravelInfo(pkg, server, session, travelInfo, gps);
			}
			
			SegPkgUtil.commonAckUnit((byte) 0x4A, session, pkg);
			return true;
		}
		
		return false;
	}
}