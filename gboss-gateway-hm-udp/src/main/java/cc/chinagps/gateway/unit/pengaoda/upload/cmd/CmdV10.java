package cc.chinagps.gateway.unit.pengaoda.upload.cmd;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.pengaoda.pkg.PengAoDaPackage;
import cc.chinagps.gateway.unit.pengaoda.upload.PengAoDaUploadUtil;
import cc.chinagps.gateway.unit.pengaoda.upload.beans.PengAoDaGPSInfo;
import cc.chinagps.gateway.unit.pengaoda.upload.beans.PengAoDaTravelInfo;
import cc.chinagps.gateway.unit.pengaoda.util.PengAoDaPkgUtil;

/**
 * ÐÐ³Ì½áÊø
 */
public class CmdV10 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(PengAoDaPackage pkg,
			UnitServer server, UnitSocketSession session) throws Exception {
		byte[] body = pkg.getBody();
		String str = new String(body, 0, body.length - 1, PengAoDaPkgUtil.EN_CHAR_ENCODING);
		String[] params = str.split(",");
		
		PengAoDaGPSInfo endGPS = PengAoDaGPSInfo.parse(params);
		PengAoDaTravelInfo travel = PengAoDaTravelInfo.parse(endGPS.getGpsTime(), params, 20);
		
		//gps
		PengAoDaUploadUtil.handleGPS(server, session, pkg, endGPS);
		if(endGPS.isAlarm()){
			PengAoDaUploadUtil.handlerAlarm(server, session, pkg, endGPS);
		}
		
		//travel
		PengAoDaUploadUtil.handleTravelInfo(server, session, endGPS, travel);
		
		//ack
		byte[] res = PengAoDaPkgUtil.getCommonResponseToUnit(session, endGPS.getTerminalId(), "V10");
		session.sendData(res);
		
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		String sstr = "*HQ,8856000001,V10,015358,A,2233.8843,N,11405.7480,E,000.50,000,130514,EFE7FBFF,E5,3000,460,00,9786,4192,C6,27376,0,170,168,10,574,0,0,0#";
	
		byte[] body = sstr.getBytes(PengAoDaPkgUtil.EN_CHAR_ENCODING);
		String str = new String(body, 0, body.length - 1, PengAoDaPkgUtil.EN_CHAR_ENCODING);
		
		String[] params = str.split(",");
		
		PengAoDaGPSInfo endGPS = PengAoDaGPSInfo.parse(params);
		PengAoDaTravelInfo travel = PengAoDaTravelInfo.parse(endGPS.getGpsTime(), params, 20);
		
		System.out.println("gps:" + endGPS);
		System.out.println("travel:" + travel);
	}
}