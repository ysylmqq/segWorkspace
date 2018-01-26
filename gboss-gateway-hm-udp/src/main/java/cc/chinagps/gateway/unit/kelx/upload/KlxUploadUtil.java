package cc.chinagps.gateway.unit.kelx.upload;

import cc.chinagps.gateway.buff.GBossDataBuff.AlarmInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.FaultInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.OBDInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.TravelInfo;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.kelx.pkg.KlxPackage;
import cc.chinagps.gateway.unit.kelx.upload.beans.KlxFaultInfo;
import cc.chinagps.gateway.unit.kelx.upload.beans.KlxGPSInfo;
import cc.chinagps.gateway.unit.kelx.upload.beans.KlxTravelInfo;
import cc.chinagps.gateway.unit.kelx.util.KlxProtobufUtil;

public class KlxUploadUtil {
	/**
	 * 处理车台上传的GPS信息
	 */
	public static void handleGPS(UnitServer server, UnitSocketSession session, 
			KlxPackage pkg, KlxGPSInfo gps){
		String callLetter = session.getUnitInfo().getCallLetter();
		
		GpsInfo gpsInfo = KlxProtobufUtil.transformGPSInfo(callLetter, pkg.getSource(), gps);
		GpsBaseInfo gpsBase = gpsInfo.getBaseInfo();
		
		server.getExportMQ().addGPS(gpsInfo);
		server.getExportHBase().addGPS(callLetter, gpsBase, pkg.getSource());
		
		//车况信息
		if(gpsBase.hasObdInfo()){
			OBDInfo obdInfo = gpsBase.getObdInfo();
			server.getExportMQ().addOBDInfo(obdInfo);
		}
	}
	
	/**
	 * 处理车台上传的警情信息
	 */
	public static void handlerAlarm(UnitServer server,
			UnitSocketSession session, KlxPackage pkg, KlxGPSInfo gps){
		String callLetter = session.getUnitInfo().getCallLetter();
		AlarmInfo alarm = KlxProtobufUtil.transformAlarmInfo(callLetter, pkg.getSource(), gps);
		server.getExportMQ().addCommonAlarm(alarm);
	}
	
	/**
	 * 处理模块故障信息(OBD)
	 */
	public static void handleFaultInfo(UnitServer server, UnitSocketSession session, 
			KlxPackage pkg, KlxFaultInfo faultInfo){
		FaultInfo value = KlxProtobufUtil.transformFaultInfo(session.getUnitInfo().getCallLetter(), faultInfo);
		server.getExportMQ().addFaultInfo(value);
	}
	
	/**
	 * 处理行程记录信息(OBD)
	 */
	public static void handleTravelInfo(UnitServer server, UnitSocketSession session, 
			KlxPackage pkg, KlxTravelInfo travelInfo){
		TravelInfo value = KlxProtobufUtil.transformTravelInfo(session.getUnitInfo().getCallLetter(), travelInfo);
		server.getExportMQ().addTravelInfo(value);
	}
}
