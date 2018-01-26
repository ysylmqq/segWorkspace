package cc.chinagps.gateway.unit.pengaoda.upload;

import cc.chinagps.gateway.buff.GBossDataBuff.AlarmInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.FaultInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.OBDInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.TravelInfo;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.pengaoda.pkg.PengAoDaPackage;
import cc.chinagps.gateway.unit.pengaoda.upload.beans.PengAoDaFaultInfo;
import cc.chinagps.gateway.unit.pengaoda.upload.beans.PengAoDaGPSInfo;
import cc.chinagps.gateway.unit.pengaoda.upload.beans.PengAoDaOBDInfo;
import cc.chinagps.gateway.unit.pengaoda.upload.beans.PengAoDaTimeGPSInfo;
import cc.chinagps.gateway.unit.pengaoda.upload.beans.PengAoDaTravelInfo;
import cc.chinagps.gateway.unit.pengaoda.util.PengAoDaProtobufUtil;

public class PengAoDaUploadUtil {
	/**
	 * 处理车台上传的GPS信息
	 */
	public static void handleGPS(UnitServer server, UnitSocketSession session, 
			PengAoDaPackage pkg, PengAoDaGPSInfo gps){
		String callLetter = session.getUnitInfo().getCallLetter();
		
		GpsInfo gpsInfo = PengAoDaProtobufUtil.transformGPSInfo(callLetter, pkg.getSource(), gps);
		GpsBaseInfo gpsBase = gpsInfo.getBaseInfo();
		
		server.getExportMQ().addGPS(gpsInfo);
		server.getExportHBase().addGPS(callLetter, gpsBase, pkg.getSource());
	}
	
	/**
	 * 处理车台上传的警情信息
	 */
	public static void handlerAlarm(UnitServer server,
			UnitSocketSession session, PengAoDaPackage pkg, PengAoDaGPSInfo gps){
		String callLetter = session.getUnitInfo().getCallLetter();
		AlarmInfo alarm = PengAoDaProtobufUtil.transformAlarmInfo(callLetter, pkg.getSource(), gps);
		server.getExportMQ().addCommonAlarm(alarm);
	}
	
	/**
	 * 处理行程记录(行程开始)
	 */
	public static void handleTravelStart(UnitServer server, UnitSocketSession session, 
			PengAoDaGPSInfo startGPS){
		TravelInfo value = PengAoDaProtobufUtil.transformTravelStart(session.getUnitInfo().getCallLetter(), startGPS);
		server.getExportMQ().addTravelInfo(value);
	}
	
	/**
	 * 处理行程记录(行程结束)
	 */
	public static void handleTravelInfo(UnitServer server, UnitSocketSession session, 
			PengAoDaGPSInfo endGPS, PengAoDaTravelInfo travel){
		TravelInfo value = PengAoDaProtobufUtil.transformTravelInfo(session.getUnitInfo().getCallLetter(), endGPS, travel);
		server.getExportMQ().addTravelInfo(value);
	}
	
	/**
	 * 定时上传部分
	 */
	/**
	 * 处理车台上传的GPS信息
	 */
	public static void handleTimeGPS(UnitServer server, UnitSocketSession session, 
			PengAoDaPackage pkg, PengAoDaTimeGPSInfo gps){
		String callLetter = session.getUnitInfo().getCallLetter();
		
		GpsInfo gpsInfo = PengAoDaProtobufUtil.transformTimeGPSInfo(callLetter, pkg.getSource(), gps);
		GpsBaseInfo gpsBase = gpsInfo.getBaseInfo();
		
		server.getExportMQ().addGPS(gpsInfo);
		server.getExportHBase().addGPS(callLetter, gpsBase, pkg.getSource());
	}
	
	/**
	 * 处理车台上传的警情信息
	 */
	public static void handlerTimeAlarm(UnitServer server,
			UnitSocketSession session, PengAoDaPackage pkg, PengAoDaTimeGPSInfo gps){
		String callLetter = session.getUnitInfo().getCallLetter();
		AlarmInfo alarm = PengAoDaProtobufUtil.transformTimeAlarmInfo(callLetter, pkg.getSource(), gps);
		server.getExportMQ().addCommonAlarm(alarm);
	}
	
	/**
	 * 处理车台上传的OBD信息(车况信息)
	 */
	public static void handleOBD(UnitServer server, String callLetter, long gpsTime, PengAoDaOBDInfo padOBDInfo, boolean isHistory){
		OBDInfo obdInfo = PengAoDaProtobufUtil.transOBDInfo(callLetter, gpsTime, padOBDInfo, isHistory);
		server.getExportMQ().addOBDInfo(obdInfo);
	}
	
	/**
	 * 处理模块故障信息(OBD)
	 */
	public static void handleFaultInfo(UnitServer server, String callLetter, PengAoDaFaultInfo faultInfo, long gpsTime, boolean isHistory){
		FaultInfo value = PengAoDaProtobufUtil.transformFaultInfo(callLetter, faultInfo, gpsTime, isHistory);
		server.getExportMQ().addFaultInfo(value);
	}
}