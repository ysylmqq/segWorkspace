package cc.chinagps.gateway.unit.db44.upload.cmd;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.db44.pkg.DB44Package;
import cc.chinagps.gateway.unit.db44.upload.DB44GPSInfo;
import cc.chinagps.gateway.unit.db44.upload.DB44UploadUtil;
import cc.chinagps.gateway.unit.db44.util.DB44PkgUtil;
import cc.chinagps.gateway.util.Constants;

/**
 * 上发短信
 */
public class Cmd16 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(DB44Package pkg,
			UnitServer server, UnitSocketSession session) throws Exception {
		byte[] protocol = pkg.getProtocol();
		DB44GPSInfo gps = DB44GPSInfo.parse(protocol, 0);
		String msg = new String(protocol, 30, protocol.length - 30, DB44PkgUtil.CN_CHAR_ENCODING);

		DB44UploadUtil.handleUploadShortMessage(pkg, server, session, msg);
		DB44UploadUtil.handleGPS(pkg, server, session, gps);
		
		//回复车台
		byte[] data = DB44PkgUtil.encode(pkg.getLoop(), (byte) 0x16, Constants.ZERO_BYTES_DATA);
		session.sendData(data);
		return true;
	}
}