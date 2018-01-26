package cc.chinagps.gateway.unit.seg.upload.cmds;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.seg.pkg.SegPackage;
import cc.chinagps.gateway.unit.seg.upload.SegUploadUtil;
import cc.chinagps.gateway.unit.seg.upload.beans.SegGPSInfo;
import cc.chinagps.gateway.util.HexUtil;

/**
 * 上传行车记录应答
 */
public class Cmd92 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(SegPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception {
		/*
		String sn = CmdUtil.getCmdCacheSn(session.getUnitInfo().getCallLetter(), CmdUtil.CMD_ID_UPLOAD_HISTORY);
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(sn);
		if(cache != null) {
			Command usercmd = cache.getUserCommand();
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			//通用部分
			CmdResponseUtil.updateResponseProtoSuccessByUserCommand(builder, usercmd);
			//params
			int lenPerGps = 20;
			byte[] body = pkg.getBody();
			int gpsCount = body.length / lenPerGps;
			for(int i = 0; i < gpsCount; i++){
				//SegGPSInfo gps = SegGPSInfo.parseGPSfromHis(body, lenPerGps * i);
				//String str = GPSInfosToProtoString(gps);
				//builder.addParams(str);
				
				SegGPSInfo gps = SegGPSInfo.parseGPSfromHis(body, lenPerGps * i);
				GpsBaseInfo base = SegProtobufUtil.transGPSBaseInfo(gps);
				builder.addGpsInfos(base);
			}
			
			byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, data);
		}
		
		return true;
		*/
		
		int lenPerGps = 20;
		byte[] body = pkg.getBody();
		int gpsCount = body.length / lenPerGps;
		for(int i = 0; i < gpsCount; i++){
			SegGPSInfo gps = SegGPSInfo.parseGPSfromHis(body, lenPerGps * i);
			SegUploadUtil.handleGPS(pkg, server, session, gps);
		}
		
		return true;
	}
	
	public static void main(String[] args) {
		//5B92303030303030303030300CA8060731081E251E2068054C3602074E00000000A8060731081E251E2068054C3602074E00000000A8060731081E251E2068054C3602074E00000000A8060731081E251E2068054C3602074E00000000A8060731081E251E2068054C3602074E00000000A8060731081E251E2068054C3602074E00000000A8060731081E251E2068054C3602074E00000000A8060731081E251E2068054C3602074E00000000A8060731081E251E2068054C3602074E00000000A8060731081E251E2068054C3602074E00000000A8060731081E251E2068054C3602074E00000000A8060731081E251E2068054C3602074E000000005D
		byte[] data = HexUtil.hexToBytes("5B92303030303030303030300CA8060731081E251E2068054C3602074E00000000A8060731081E251E2068054C3602074E00000000A8060731081E251E2068054C3602074E00000000A8060731081E251E2068054C3602074E00000000A8060731081E251E2068054C3602074E00000000A8060731081E251E2068054C3602074E00000000A8060731081E251E2068054C3602074E00000000A8060731081E251E2068054C3602074E00000000A8060731081E251E2068054C3602074E00000000A8060731081E251E2068054C3602074E00000000A8060731081E251E2068054C3602074E00000000A8060731081E251E2068054C3602074E000000005D");
		try {
			SegPackage pkg = SegPackage.parse(data);
			
			int lenPerGps = 20;
			byte[] body = pkg.getBody();
			int gpsCount = body.length / lenPerGps;
			System.out.println("gpsCount:" + gpsCount);
			for(int i = 0; i < gpsCount; i++){
				SegGPSInfo gps = SegGPSInfo.parseGPSfromHis(body, lenPerGps * i);
				System.out.println("[GPS(" + i + ")]" + gps);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * GPSInfos对象转成一个字符串
	 */
//	private String GPSInfosToProtoString(SegGPSInfo gps){
//		StringBuilder sb = new StringBuilder();
//		sb.append(CmdResponseUtil.formatToLocal(gps.getGpsTime())).append("~");
//		sb.append(String.valueOf(gps.isLoc())).append("~");
//		sb.append(String.valueOf(gps.getLat())).append("~");
//		sb.append(String.valueOf(gps.getLng())).append("~");
//		sb.append(String.valueOf(gps.getSpeed())).append("~");
//		sb.append(String.valueOf(gps.getCourse())).append("~");
//		sb.append(gps.getStatus().toString());
//		
//		return sb.toString();
//	}
}