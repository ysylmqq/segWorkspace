package cc.chinagps.gateway.unit.seg.upload.cmds;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.seg.pkg.SegPackage;
import cc.chinagps.gateway.unit.seg.upload.SegUploadUtil;
import cc.chinagps.gateway.unit.seg.upload.beans.SegGPSInfo;
import cc.chinagps.gateway.unit.seg.util.SegPkgUtil;
import cc.chinagps.gateway.util.HexUtil;

/**
 * 里程报告
 */
public class CmdC6 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(SegPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception {
		byte[] body = pkg.getBody();
		String strBody = new String(body, SegPkgUtil.PKG_CHAR_ENCODING);
		if(strBody.startsWith("(MIL")){
			//里程报告
			//(MIL00,GPS1,GPS2,&W,&B,1,K1,K2,K6,K6)
			String[] params = strBody.split(",");
			String gpsStr = params[2];
			String totalDistanceStr = params[6].substring(2);
			
			SegGPSInfo gps = SegGPSInfo.parse(gpsStr, 0);
			long totalDistance = Long.valueOf(totalDistanceStr, 16);
			gps.setTotalDistance(totalDistance);
			SegUploadUtil.handleGPS(pkg, server, session, gps);
			
			//回复车台
			byte[] res = new byte[13];
			res[0] = SegPkgUtil.START_FLAG;
			res[1] = (byte) 0xC6;
			System.arraycopy(pkg.getSerialNumberBytes(), 0, res, 2, 10);
			res[12] = SegPkgUtil.END_FLAG;
			
			byte[] es = SegPkgUtil.escape(res);
			session.sendData(es);
			return true;
		}
		
		return false;
	}
	
	public static void main(String[] args) {
		String ee = "5bc630303030303030303030ad284d494c30302c31303536343656333733342e363333344e31303834392e35373630453030302e32303531373038313330303030303030302c31313138343041333733352e393038324e31303834382e33343637453030302e31303031373038313330303030303430302c2657303030303030303030302c2642303141372c312c4b3130323341354143442c4b3230303030313541412c4b3630303030303030302c4b363030303030303030295d";
		String txt = HexUtil.hexToString(ee);
		int start = txt.indexOf("(");
		String str = txt.substring(start, txt.length() - 1);
		//System.out.println(str);
		String[] params = str.split(",");
		String gpsStr = params[2];
		//System.out.println(gpsStr);
		String totalDistanceStr = params[6].substring(2);
		
		SegGPSInfo gps;
		try {
			gps = SegGPSInfo.parse(gpsStr, 0);
			long totalDistance = Long.valueOf(totalDistanceStr, 16);
			gps.setTotalDistance(totalDistance);
			System.out.println(gps);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}