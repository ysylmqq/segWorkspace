package cc.chinagps.gateway.unit.seg.upload;

import java.util.List;

import org.apache.log4j.Logger;

import cc.chinagps.gateway.StartServer;
import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.buff.CommandBuff.CommandResponse.Builder;
import cc.chinagps.gateway.buff.GBossDataBuff;
import cc.chinagps.gateway.buff.GBossDataBuff.AlarmInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.FaultInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.OBDInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.OperateData;
import cc.chinagps.gateway.buff.GBossDataBuff.TravelInfo;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.seat.cmd.CmdResponseUtil;
import cc.chinagps.gateway.seat.cmd.CmdUtil;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beans.GbossGpsInfo;
import cc.chinagps.gateway.unit.common.BaseStationManager;
import cc.chinagps.gateway.unit.seg.pkg.SegPackage;
import cc.chinagps.gateway.unit.seg.upload.beans.SegFaultInfo;
import cc.chinagps.gateway.unit.seg.upload.beans.SegGPSInfo;
import cc.chinagps.gateway.unit.seg.upload.beans.SegTravelInfo;
import cc.chinagps.gateway.unit.seg.upload.beans.service.ServiceData;
import cc.chinagps.gateway.unit.seg.upload.beans.service.ServiceDetail;
import cc.chinagps.gateway.unit.seg.util.SegPkgUtil;
import cc.chinagps.gateway.unit.seg.util.SegProtobufUtil;
import cc.chinagps.gateway.util.HexUtil;
import cc.chinagps.gateway.util.UnitProtocolTypes;

public class SegUploadUtil {
	
	/**
	 * 设置指令回应的GPS部分
	 */
	public static void setUpResponseByGPSInfo(String calLetter, Builder builder, SegGPSInfo gps){
		/*
		builder.addParams(CmdResponseUtil.formatToLocal(gps.getGpsTime()));
		builder.addParams(String.valueOf(gps.isLoc()));
		builder.addParams(String.valueOf(gps.getLat()));
		builder.addParams(String.valueOf(gps.getLng()));
		builder.addParams(String.valueOf(gps.getSpeed()));
		builder.addParams(String.valueOf(gps.getCourse()));
		builder.addParams(gps.getStatus().toString());
		builder.addParams(gps.getTotalDistance() == null? "": gps.getTotalDistance().toString());
		builder.addParams(gps.getOilPercent() == null? "": gps.getOilPercent().toString());
		builder.addParams(gps.getTemperature1() == null? "": gps.getTemperature1().toString());
		builder.addParams(gps.getTemperature2() == null? "": gps.getTemperature2().toString());
		
		//appendParams
		List<MapEntry> extendStatus = gps.getExtendsStatus();
		for(int i = 0; i < extendStatus.size(); i++){
			MapEntry entry = extendStatus.get(i);
			builder.addAppendParams(CommandBuff.MapEntry.newBuilder().setKey(entry.getKey()).setValue(entry.getValue()));
		}*/
		
		//GpsBaseInfo base = SegProtobufUtil.transGPSBaseInfo(gps);
		//builder.addGpsInfos(base);
		
		cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo.Builder gpsBuilder = GBossDataBuff.GpsInfo.newBuilder();
		gpsBuilder.setCallLetter(calLetter);
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo baseBuilder = SegProtobufUtil.transGPSBaseInfo(calLetter, gps);
		gpsBuilder.setBaseInfo(baseBuilder);

		builder.addGpsInfos(gpsBuilder);
	}
	
	/**
	 * 处理车台上传的短消息
	 */
	public static void handleUploadShortMessage(SegPackage pkg, UnitServer server,
			UnitSocketSession session, String shortMessage){
		server.getExportMQ().addShortMessage(session.getUnitInfo().getCallLetter(), shortMessage);
	}
	
	/**
	 * 处理车台上传的GPS信息
	 */
	public static void handleGPS(SegPackage pkg, UnitServer server,
			UnitSocketSession session, SegGPSInfo gps){
		String callLetter = session.getUnitInfo().getCallLetter();
		//GpsInfo gpsInfo = SegProtobufUtil.transformGPSInfo(callLetter, gps, pkg);
		GpsInfo.Builder builder = SegProtobufUtil.transformGPSInfoBuilder(callLetter, gps, pkg);
		GpsInfo gpsInfo = builder.build();
		if (StartServer.isParseBaseStation && !gps.isLoc()) {
			BaseStationManager.getInstance()
					.addBaseStationGps(new GbossGpsInfo(builder, gpsInfo, UnitProtocolTypes.UNIT_PROTOCOL_TYPE_T3688));
		} else {
			GpsBaseInfo gpsBase = gpsInfo.getBaseInfo();
			
			server.getExportMQ().addGPS(gpsInfo);
			server.getExportHBase().addGPS(callLetter, gpsBase, pkg.getSource());
			
			//车况信息
			if(gpsBase.hasObdInfo()){
				OBDInfo obdInfo = gpsBase.getObdInfo();
				server.getExportMQ().addOBDInfo(obdInfo);
			}
		}
	}
	
	/**
	 * 处理车台上传的警情信息
	 */
	private static Logger logger_alarm = Logger.getLogger(LogManager.LOGGER_NAME_ALARM);
	
	public static void handlerAlarm(SegPackage pkg, UnitServer server,
			UnitSocketSession session, SegGPSInfo gps){
		String callLetter = session.getUnitInfo().getCallLetter();
		if(gps.isSpecialAlarm()){
			//一类警情
			//GpsInfo specialAlarm = SegProtobufUtil.transformGPSInfo(callLetter, gps, pkg);
			AlarmInfo specialAlarm = SegProtobufUtil.transformAlarmInfo(callLetter, gps, pkg);
			boolean success = server.getExportMQ().addSpecialAlarm(specialAlarm);
			
			if(success){
				//回应车台
				SegPkgUtil.commonAckUnit((byte) 0x70, session, pkg);
				logger_alarm.info("(" + callLetter + ")" + HexUtil.byteToHexStr(pkg.getSource()));
			}
		}else{
			//GpsInfo commonAlarm = SegProtobufUtil.transformGPSInfo(callLetter, gps, pkg);
			AlarmInfo commonAlarm = SegProtobufUtil.transformAlarmInfo(callLetter, gps, pkg);
			server.getExportMQ().addCommonAlarm(commonAlarm);
			//回应车台
			
			SegPkgUtil.commonAckUnit((byte) 0x70, session, pkg);
		}
	}
	
	/**
	 * 处理车台上传的运营数据
	 */
	public static void handleServiceData(SegPackage pkg, UnitServer server,
			UnitSocketSession session, ServiceData serviceData, ServiceDetail serviceDetail){
		//server.getExportMQ().addServiceData(session.getUnitInfo().getCallLetter(), serviceData, serviceDetail, pkg);
		
		String callLetter = session.getUnitInfo().getCallLetter();
		OperateData operateData = SegProtobufUtil.transformServiceData(callLetter, serviceData, serviceDetail, pkg);
		server.getExportMQ().addOperateData(operateData);
	}
	
	/**
	 * 处理行程记录信息(OBD)
	 */
	public static void handleTravelInfo(SegPackage pkg, UnitServer server,
			UnitSocketSession session, SegTravelInfo travelInfo, SegGPSInfo gps){
		TravelInfo value = SegProtobufUtil.transformTravelInfo(session.getUnitInfo().getCallLetter(), travelInfo, gps);
		server.getExportMQ().addTravelInfo(value);
	}
	
	/**
	 * 处理模块故障信息(OBD)
	 */
	public static void handleFaultInfo(SegPackage pkg, UnitServer server,
			UnitSocketSession session, SegFaultInfo faultInfo){
		FaultInfo value = SegProtobufUtil.transformFaultInfo(session.getUnitInfo().getCallLetter(), faultInfo);
		server.getExportMQ().addFaultInfo(value);
	}
	
	private static Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	public static String statusListToStr(List<Integer> status) {
		if (status == null)
			return "";
		String s = "";
		for (int i = 0; i < status.size(); i++) {
			if (i == 0) {
				s = s + status.get(i);
			}else{
				s = s + "," + status.get(i);
			}
		}
		return s;
	}
	/**
	 * 通用带GPS应答(XYZ+GPSInfo
	 * @throws Exception 
	 */
	public static void commonResponseWithGPS(SegPackage pkg, UnitServer server,
			UnitSocketSession session, String strBody, int cmdId) throws Exception{
		SegGPSInfo gps = SegGPSInfo.parse(strBody, 4);
		logger_debug.debug("commonResponseWithGPS status:"+statusListToStr(gps.getStatus()));
		//String sn = pkg.getSerialNumber();
		String callLetter = session.getUnitInfo().getCallLetter();
		String sn = CmdUtil.getCmdCacheSn(callLetter, cmdId);
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(sn);
		if(cache != null) {
			Command usercmd = cache.getUserCommand();
			
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			//设置回应的通用部分
			CmdResponseUtil.updateResponseProtoSuccessByUserCommand(builder, callLetter, usercmd);
			//gpsInfo
			SegUploadUtil.setUpResponseByGPSInfo(callLetter, builder, gps);
			
			//byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		}
		
		SegUploadUtil.handleGPS(pkg, server, session, gps);
	}
}