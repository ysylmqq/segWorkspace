package cc.chinagps.gateway.unit.db44.upload.cmd;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.db44.pkg.DB44Package;
import cc.chinagps.gateway.unit.db44.upload.DB44GPSInfo;
import cc.chinagps.gateway.unit.db44.upload.DB44UploadUtil;
import cc.chinagps.gateway.util.HexUtil;
/**
 * 定时上传
 */
public class Cmd00 extends CheckLoginHandler{
	private static final int ONE_GPS_LENGTH = 30;
	
	@Override
	public boolean handlerPackageSessionChecked(DB44Package pkg,
			UnitServer server, UnitSocketSession session) throws Exception {
		byte[] protocol = pkg.getProtocol();
		int gpsCount = protocol.length / ONE_GPS_LENGTH;
		for(int i = 0; i < gpsCount; i++){
			DB44GPSInfo gps = DB44GPSInfo.parse(protocol, ONE_GPS_LENGTH * i);
			
			DB44UploadUtil.handleGPS(pkg, server, session, gps);
			if(gps.isAlarm()){
				DB44UploadUtil.handlerAlarm(pkg, server, session, gps);
			}
		}
		
		return true;
	}
	
	public static void main(String[] args) {
		String hex = "7e527755574451737a48714d414141414951554a445241416773384246565a4c6c4f6b73626263454e6865354b5949673458695a76474f7a7a357061326b495a6b5a53397974773d3d7f";
		byte[] bs = HexUtil.hexToBytes(hex);
		try {
			DB44Package pkg = DB44Package.parse(bs);
			System.out.println("pkg:" + pkg);
			byte[] protocol = pkg.getProtocol();
			int gpsCount = protocol.length / ONE_GPS_LENGTH;
			for(int i = 0; i < gpsCount; i++){
				DB44GPSInfo gps = DB44GPSInfo.parse(protocol, ONE_GPS_LENGTH * i);
				
				System.out.println("gps:" + gps);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}