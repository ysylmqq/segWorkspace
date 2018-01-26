package cc.chinagps.gateway.unit.kelx.util;

import java.util.List;

import cc.chinagps.gateway.buff.GBossDataBuff;
import cc.chinagps.gateway.buff.GBossDataBuff.BaseStationInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.OBDInfo;
import cc.chinagps.gateway.unit.kelx.upload.beans.KlxBaseStation;
import cc.chinagps.gateway.unit.kelx.upload.beans.KlxFaultInfo;
import cc.chinagps.gateway.unit.kelx.upload.beans.KlxGPSInfo;
import cc.chinagps.gateway.unit.kelx.upload.beans.KlxTravelInfo;
import cc.chinagps.gateway.util.UnitProtocolTypes;

import com.google.protobuf.ByteString;

public class KlxProtobufUtil {
	/**
	 * gpsInfo转protobuf gpsInfo
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo transformGPSInfo(String callLetter, byte[] source, KlxGPSInfo gps){
		cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo.Builder builder = GBossDataBuff.GpsInfo.newBuilder();
		builder.setCallLetter(callLetter);
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo baseBuilder = transGPSBaseInfo(callLetter, gps);
		builder.setBaseInfo(baseBuilder);
		builder.setContent(ByteString.copyFrom(source));
		builder.setHistory(gps.isHistory()? 1: 0);
		
		return builder.build();
	}
	
	/**
	 * alarmInfo转protobuf alarmInfo
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.AlarmInfo transformAlarmInfo(String callLetter, byte[] source, KlxGPSInfo gps){
		cc.chinagps.gateway.buff.GBossDataBuff.AlarmInfo.Builder builder = GBossDataBuff.AlarmInfo.newBuilder();
		builder.setCallLetter(callLetter);
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo baseBuilder = transGPSBaseInfo(callLetter, gps);
		builder.setBaseInfo(baseBuilder);
		builder.setContent(ByteString.copyFrom(source));
		builder.setHistory(gps.isHistory()? 1: 0);
		builder.setUnittype(UnitProtocolTypes.UNIT_PROTOCOL_TYPE_KLX);
		
		return builder.build();
	}
	
	/**
	 * gpsBaseInfo转protobuf 
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo transGPSBaseInfo(String callLetter, KlxGPSInfo gps){
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo.Builder baseBuilder = GBossDataBuff.GpsBaseInfo.newBuilder();
		baseBuilder.setGpsTime(gps.getGpsTime().getTime());
		
		boolean isLoc = gps.isLoc();
		baseBuilder.setLoc(isLoc);
		
		if(isLoc){
			//卫星定位
			baseBuilder.setLat((int) (gps.getLat() * 1000000));
			baseBuilder.setLng((int) (gps.getLng() * 1000000));
			baseBuilder.setSpeed((int) Math.round(gps.getSpeed() * 10));
			baseBuilder.setCourse(gps.getCourse());
		}else{
			baseBuilder.setLat(0);
			baseBuilder.setLng(0);
			baseBuilder.setSpeed(0);
			baseBuilder.setCourse(0);
			
			//基站定位
			KlxBaseStation baseStation = gps.getBaseStation();
			
			BaseStationInfo.Builder baseStationBuilder = BaseStationInfo.newBuilder();
			baseStationBuilder.setMcc(String.valueOf(baseStation.getMcc()));
			baseStationBuilder.setMnc(String.valueOf(baseStation.getMnc()));
			baseStationBuilder.setLac(baseStation.getLac());
			baseStationBuilder.setCid(baseStation.getCellId());
			
			baseBuilder.addBaseStations(baseStationBuilder);
		}
		
		//status
		baseBuilder.addAllStatus(gps.getStatus());

		//obd
		cc.chinagps.gateway.buff.GBossDataBuff.OBDInfo.Builder obdBuilder = OBDInfo.newBuilder();
		obdBuilder.setWaterTemperature(gps.getWaterTemperature());
		obdBuilder.setSpeed(gps.getObdSpeed());
		obdBuilder.setCallLetter(callLetter);
		obdBuilder.setGpsTime(gps.getGpsTime().getTime());
		obdBuilder.setUnitType(UnitProtocolTypes.UNIT_PROTOCOL_TYPE_KLX);
		obdBuilder.setHistory(0);
		baseBuilder.setObdInfo(obdBuilder);
		
		return baseBuilder.build();
	}
	
	/**
	 * FaultInfo转protobuf
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.FaultInfo transformFaultInfo(String callLetter, KlxFaultInfo faultInfo){
		cc.chinagps.gateway.buff.GBossDataBuff.FaultInfo.Builder builder = cc.chinagps.gateway.buff.GBossDataBuff.FaultInfo.newBuilder();
		builder.setCallLetter(callLetter);
		builder.setFaultTime(faultInfo.getFaultTime().getTime());
		List<String> list = faultInfo.getFaultCode();

		builder.setUnitType(UnitProtocolTypes.UNIT_PROTOCOL_TYPE_KLX);
		if(list != null){
			cc.chinagps.gateway.buff.GBossDataBuff.FaultDefine.Builder fdBuilder = cc.chinagps.gateway.buff.GBossDataBuff.FaultDefine.newBuilder();
			fdBuilder.addAllFaultCode(list);
			
			builder.addFaults(fdBuilder);
		}
		
		return builder.build();
	}
	
	/**
	 * TravelInfo转protobuf
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.TravelInfo transformTravelInfo(String callLetter, KlxTravelInfo travelInfo){
		cc.chinagps.gateway.buff.GBossDataBuff.TravelInfo.Builder builder =  cc.chinagps.gateway.buff.GBossDataBuff.TravelInfo.newBuilder();
		builder.setCallLetter(callLetter);
		builder.setStartTime(travelInfo.getStartTime().getTime());
		builder.setEndTime(travelInfo.getEndTime().getTime());
		builder.setDistance(travelInfo.getDistance() * 1000);
		builder.setMaxSpeed(travelInfo.getMaxSpeed() * 10);
		builder.setOverSpeedTime(travelInfo.getOverSpeedTime());
		builder.setQuickBrakeCount(travelInfo.getQuickBrakeCount());
		builder.setEmergencyBrakeCount(travelInfo.getEmergencyBrakeCount());
		builder.setQuickSpeedUpCount(travelInfo.getQuickSpeedUpCount());
		builder.setEmergencySpeedUpCount(travelInfo.getEmergencySpeedUpCount());
		builder.setAverageSpeed(travelInfo.getAverageSpeed() * 10);
		builder.setMaxWaterTemperature(travelInfo.getMaxWaterTemperature());
		builder.setMaxRotationSpeed(travelInfo.getMaxRotationSpeed());
		builder.setVoltage(travelInfo.getVoltage());
		builder.setTotalOil(travelInfo.getTotalOil());
		builder.setAverageOil(travelInfo.getAverageOil());
		builder.setTiredDrivingTime(travelInfo.getTiredDrivingTime() * 600);
		builder.setSerialNumber(travelInfo.getIndex());
		 
		return builder.build();
	}
}