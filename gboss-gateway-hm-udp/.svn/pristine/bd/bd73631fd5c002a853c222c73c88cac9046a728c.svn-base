package cc.chinagps.gateway.unit.pengaoda.util;

import java.util.List;

import cc.chinagps.gateway.buff.GBossDataBuff;
import cc.chinagps.gateway.buff.GBossDataBuff.BaseStationInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.FaultInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.OBDInfo;
import cc.chinagps.gateway.unit.pengaoda.upload.beans.PengAoDaBaseStation;
import cc.chinagps.gateway.unit.pengaoda.upload.beans.PengAoDaFaultCode;
import cc.chinagps.gateway.unit.pengaoda.upload.beans.PengAoDaFaultInfo;
import cc.chinagps.gateway.unit.pengaoda.upload.beans.PengAoDaGPSInfo;
import cc.chinagps.gateway.unit.pengaoda.upload.beans.PengAoDaOBDInfo;
import cc.chinagps.gateway.unit.pengaoda.upload.beans.PengAoDaTimeGPSInfo;
import cc.chinagps.gateway.unit.pengaoda.upload.beans.PengAoDaTravelInfo;
import cc.chinagps.gateway.util.UnitProtocolTypes;

import com.google.protobuf.ByteString;

public class PengAoDaProtobufUtil {
	/**
	 * gpsInfo转protobuf gpsInfo
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo transformGPSInfo(String callLetter, byte[] source, PengAoDaGPSInfo gps){
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
	public static cc.chinagps.gateway.buff.GBossDataBuff.AlarmInfo transformAlarmInfo(String callLetter, byte[] source, PengAoDaGPSInfo gps){
		cc.chinagps.gateway.buff.GBossDataBuff.AlarmInfo.Builder builder = GBossDataBuff.AlarmInfo.newBuilder();
		builder.setCallLetter(callLetter);
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo baseBuilder = transGPSBaseInfo(callLetter, gps);
		builder.setBaseInfo(baseBuilder);
		builder.setContent(ByteString.copyFrom(source));
		builder.setHistory(gps.isHistory()? 1: 0);
		builder.setUnittype(UnitProtocolTypes.UNIT_PROTOCOL_TYPE_PENGAODA);
		
		return builder.build();
	}
	
	/**
	 * gpsBaseInfo转protobuf 
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo transGPSBaseInfo(String callLetter, PengAoDaGPSInfo gps){
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo.Builder baseBuilder = GBossDataBuff.GpsBaseInfo.newBuilder();
		baseBuilder.setGpsTime(gps.getGpsTime().getTime());
		
		boolean isLoc = gps.isLoc();
		baseBuilder.setLoc(isLoc);
		
		baseBuilder.setLat((int) (gps.getLat() * 1000000));
		baseBuilder.setLng((int) (gps.getLng() * 1000000));
		baseBuilder.setSpeed((int) Math.round(gps.getSpeed() * 10));
		baseBuilder.setCourse(gps.getCourse());
		
		//基站定位
		PengAoDaBaseStation baseStation = gps.getBaseStation();
		BaseStationInfo.Builder baseStationBuilder = BaseStationInfo.newBuilder();
		baseStationBuilder.setMcc(String.valueOf(baseStation.getMcc()));
		baseStationBuilder.setMnc(String.valueOf(baseStation.getMnc()));
		baseStationBuilder.setLac(baseStation.getLac());
		baseStationBuilder.setCid(baseStation.getCellId());
		
		baseBuilder.addBaseStations(baseStationBuilder);
		
		//status
		baseBuilder.addAllStatus(gps.getStatus());
		
		return baseBuilder.build();
	}
	
	/**
	 * TravelInfo转protobuf
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.TravelInfo transformTravelInfo(String callLetter, 
			PengAoDaGPSInfo endGPS, PengAoDaTravelInfo travelInfo){
		cc.chinagps.gateway.buff.GBossDataBuff.TravelInfo.Builder builder =  cc.chinagps.gateway.buff.GBossDataBuff.TravelInfo.newBuilder();
		builder.setCallLetter(callLetter);
		builder.setStartTime(travelInfo.getStartTime().getTime());
		builder.setEndTime(travelInfo.getEndTime().getTime());
		builder.setDistance(travelInfo.getDistance());
		builder.setTotalOil(travelInfo.getTotalOil() * 10);
		builder.setMaxSpeed(travelInfo.getMaxSpeed() * 10);
		builder.setAverageSpeed(travelInfo.getAverageSpeed() * 10);
		builder.setIdleTime(travelInfo.getIdleTime());
		builder.setQuickSpeedUpCount(travelInfo.getQuickSpeedUpCount());
		builder.setQuickBrakeCount(travelInfo.getQuickBrakeCount());
		builder.setQuickTurnCount(travelInfo.getQuickTurnCount());
		
		GpsBaseInfo gpsBase = transGPSBaseInfo(callLetter, endGPS);
		builder.setEndGps(gpsBase);
		 
		return builder.build();
	}
	
	/**
	 * TravelInfo转protobuf(起始行程)
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.TravelInfo transformTravelStart(String callLetter, 
			PengAoDaGPSInfo startGPS){
		cc.chinagps.gateway.buff.GBossDataBuff.TravelInfo.Builder builder =  cc.chinagps.gateway.buff.GBossDataBuff.TravelInfo.newBuilder();
		builder.setCallLetter(callLetter);
		builder.setStartTime(startGPS.getGpsTime().getTime());
		builder.setEndTime(0);
		
		GpsBaseInfo gpsBase = transGPSBaseInfo(callLetter, startGPS);
		builder.setStartGps(gpsBase);
		 
		return builder.build();
	}
	
	/**
	 * 定时上报部分
	 */
	/**
	 * gpsInfo转protobuf gpsInfo
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo transformTimeGPSInfo(String callLetter, 
			byte[] source, PengAoDaTimeGPSInfo gps){
		cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo.Builder builder = GBossDataBuff.GpsInfo.newBuilder();
		builder.setCallLetter(callLetter);
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo baseBuilder = transTimeGPSBaseInfo(callLetter, gps);
		builder.setBaseInfo(baseBuilder);
		builder.setContent(ByteString.copyFrom(source));
		builder.setHistory(gps.isHistory()? 1: 0);
		
		return builder.build();
	}
	
	/**
	 * alarmInfo转protobuf alarmInfo
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.AlarmInfo transformTimeAlarmInfo(String callLetter, byte[] source, PengAoDaTimeGPSInfo gps){
		cc.chinagps.gateway.buff.GBossDataBuff.AlarmInfo.Builder builder = GBossDataBuff.AlarmInfo.newBuilder();
		builder.setCallLetter(callLetter);
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo baseBuilder = transTimeGPSBaseInfo(callLetter, gps);
		builder.setBaseInfo(baseBuilder);
		builder.setContent(ByteString.copyFrom(source));
		builder.setHistory(gps.isHistory()? 1: 0);
		builder.setUnittype(UnitProtocolTypes.UNIT_PROTOCOL_TYPE_PENGAODA);
		
		return builder.build();
	}
	
	/**
	 * gpsBaseInfo转protobuf 
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo transTimeGPSBaseInfo(String callLetter, PengAoDaTimeGPSInfo gps){
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo.Builder baseBuilder = GBossDataBuff.GpsBaseInfo.newBuilder();
		baseBuilder.setGpsTime(gps.getGpsTime().getTime());
		
		boolean isLoc = gps.isLoc();
		baseBuilder.setLoc(isLoc);
		
		baseBuilder.setLat((int) (gps.getLat() * 1000000));
		baseBuilder.setLng((int) (gps.getLng() * 1000000));
		baseBuilder.setSpeed((int) Math.round(gps.getSpeed() * 10));
		baseBuilder.setCourse(gps.getCourse());
		baseBuilder.setTotalDistance((int) (gps.getDistance() * 1000));
		
		//基站定位
		PengAoDaBaseStation baseStation = gps.getBaseStation();
		BaseStationInfo.Builder baseStationBuilder = BaseStationInfo.newBuilder();
		baseStationBuilder.setMcc(String.valueOf(baseStation.getMcc()));
		baseStationBuilder.setMnc(String.valueOf(baseStation.getMnc()));
		baseStationBuilder.setLac(baseStation.getLac());
		baseStationBuilder.setCid(baseStation.getCellId());
		
		baseBuilder.addBaseStations(baseStationBuilder);
		
		PengAoDaOBDInfo obdInfo = gps.getObdInfo();
		if(obdInfo != null){
			baseBuilder.setObdInfo(transOBDInfo(callLetter, gps.getGpsTime().getTime(), obdInfo, gps.isHistory()));
		}
		
		//status
		baseBuilder.addAllStatus(gps.getStatus());
		
		return baseBuilder.build();
	}
	
	/**
	 * OBDInfo转protobuf OBDInfo
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.OBDInfo transOBDInfo(String callLetter, long gpsTime, PengAoDaOBDInfo obdInfo, boolean isHistory){
		cc.chinagps.gateway.buff.GBossDataBuff.OBDInfo.Builder obdBuilder = OBDInfo.newBuilder();
		//Double engineLoad = obdInfo.getEngineLoad();
		Integer warterTemperature = obdInfo.getWarterTemperature();
		Double rotationSpeed = obdInfo.getRotationSpeed();
		Integer speed = obdInfo.getSpeed();
		Integer intakeAirTemperature = obdInfo.getIntakeAirTemperature();
		//Integer runTime = obdInfo.getRunTime();
		//Double voltage = obdInfo.getVoltage();
		//Double airFuelRatio = obdInfo.getAirFuelRatio();		
		//Integer outsideTemperature = obdInfo.getOutsideTemperature();	
		//Double throttle = obdInfo.getThrottle();
		Long oilPerHour = obdInfo.getOilPerHour();
		Double oilPerHundredKm = obdInfo.getOilPerHundredKm();
		//Long distance = obdInfo.getDistance();
		Double remainOil = obdInfo.getRemainOil();
		Double remainPercentOil = obdInfo.getRemainPercentOil();
		
		if(warterTemperature != null){
			obdBuilder.setWaterTemperature(warterTemperature);
		}
		
		if(rotationSpeed != null){
			obdBuilder.setRotationSpeed((int) (double)rotationSpeed);
		}
		
		if(speed != null){
			obdBuilder.setSpeed(speed * 10);
		}
		
		if(intakeAirTemperature != null){
			obdBuilder.setIntakeAirTemperature(intakeAirTemperature);
		}

		if(oilPerHour != null){
			obdBuilder.setHourOil((int) (oilPerHour * 100));
		}
		
		if(oilPerHundredKm != null){
			obdBuilder.setAverageOil((int) (oilPerHundredKm * 100));
		}
		
		if(remainOil != null){
			obdBuilder.setRemainOil((int) (double)(remainOil * 10));
		}
		
		if(remainPercentOil != null){
			obdBuilder.setRemainPercentOil((int) (double)(remainPercentOil * 10));
		}
		
		obdBuilder.setCallLetter(callLetter);
		obdBuilder.setGpsTime(gpsTime);
		obdBuilder.setUnitType(UnitProtocolTypes.UNIT_PROTOCOL_TYPE_PENGAODA);
		obdBuilder.setHistory(isHistory? 1: 0);
		
		return obdBuilder.build();
	}
	
	/**
	 * FaultInfo转protobuf
	 */
	public static FaultInfo transformFaultInfo(String callLetter, PengAoDaFaultInfo faultInfo, long gpsTime, boolean isHistory){
		cc.chinagps.gateway.buff.GBossDataBuff.FaultInfo.Builder builder = cc.chinagps.gateway.buff.GBossDataBuff.FaultInfo.newBuilder();
		builder.setCallLetter(callLetter);
		builder.setFaultTime(gpsTime);
		builder.setUnitType(UnitProtocolTypes.UNIT_PROTOCOL_TYPE_PENGAODA);
		builder.setHistory(isHistory? 1: 0);
		
		byte faultType = faultInfo.getFaultType();
		List<PengAoDaFaultCode> list = faultInfo.getFaultCodeList();
		if(list != null && list.size() > 0){
			cc.chinagps.gateway.buff.GBossDataBuff.FaultDefine.Builder fdBuilder = cc.chinagps.gateway.buff.GBossDataBuff.FaultDefine.newBuilder();
			fdBuilder.setFaultType(faultType);			
			for(int i = 0; i < list.size(); i++){
				PengAoDaFaultCode listi = list.get(i);
				String code = listi.getFaultCode();
				fdBuilder.addFaultCode(code);
			}
			
			builder.addFaults(fdBuilder);
		}
		
		return builder.build();
	}
}