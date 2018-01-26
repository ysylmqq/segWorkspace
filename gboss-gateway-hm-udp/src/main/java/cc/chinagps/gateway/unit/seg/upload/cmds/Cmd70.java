package cc.chinagps.gateway.unit.seg.upload.cmds;

import cc.chinagps.gateway.unit.UnitSocketSession;

import org.apache.log4j.Logger;

import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.seg.pkg.SegPackage;
import cc.chinagps.gateway.unit.seg.upload.SegUploadUtil;
import cc.chinagps.gateway.unit.seg.upload.beans.SegGPSInfo;
import cc.chinagps.gateway.unit.seg.util.SegPkgUtil;

/**
 * 报警
 * (ONE
 * (ACC
 */
public class Cmd70 extends CheckLoginHandler{
	private static Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	@Override
	public boolean handlerPackageSessionChecked(SegPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception{
		byte[] body = pkg.getBody();
		String strBody = new String(body, SegPkgUtil.PKG_CHAR_ENCODING);
		if(strBody.startsWith("(ONE")){
			//报警
			SegGPSInfo gps = SegGPSInfo.parse(strBody, 4);
			logger_debug.debug("[seg]Cmd70 status:"+SegUploadUtil.statusListToStr(gps.getStatus()));
			SegUploadUtil.handlerAlarm(pkg, server, session, gps);
			
			SegUploadUtil.handleGPS(pkg, server, session, gps);
			return true;
		}else if(strBody.startsWith("(ACC")){
			//点火熄火上报(警情?)
			SegGPSInfo gps = SegGPSInfo.parse(strBody, 4);
			SegUploadUtil.handlerAlarm(pkg, server, session, gps);
			
			SegUploadUtil.handleGPS(pkg, server, session, gps);
			return true;
		}
		
		return false;
	}
}