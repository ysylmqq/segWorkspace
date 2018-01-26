package cc.chinagps.gateway.unit.seg.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.protobuf.ByteString;

import cc.chinagps.gateway.buff.GBossDataBuff;
import cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.OBDInfo;
import cc.chinagps.gateway.unit.seg.pkg.SegPackage;
import cc.chinagps.gateway.unit.seg.upload.MapEntry;
import cc.chinagps.gateway.unit.seg.upload.beans.BeiJingInfo;
import cc.chinagps.gateway.unit.seg.upload.beans.SegBaseStationInfo;
import cc.chinagps.gateway.unit.seg.upload.beans.SegFaultInfo;
import cc.chinagps.gateway.unit.seg.upload.beans.SegGPSInfo;
import cc.chinagps.gateway.unit.seg.upload.beans.SegOBDInfo;
import cc.chinagps.gateway.unit.seg.upload.beans.SegTravelInfo;
import cc.chinagps.gateway.unit.seg.upload.beans.service.ServiceData;
import cc.chinagps.gateway.unit.seg.upload.beans.service.ServiceDetail;
import cc.chinagps.gateway.util.UnitProtocolTypes;

public class SegProtobufUtil {
	/**
	 * gpsInfo转protobuf gpsInfo
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo transformGPSInfo(String callLetter, SegGPSInfo gps, SegPackage pkg){
		cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo.Builder builder = GBossDataBuff.GpsInfo.newBuilder();
		builder.setCallLetter(callLetter);
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo baseBuilder = transGPSBaseInfo(callLetter, gps);
		builder.setBaseInfo(baseBuilder);
		builder.setContent(ByteString.copyFrom(pkg.getSource()));
		builder.setHistory(gps.getHistory());
		
		return builder.build();
	}
	
	public static cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo.Builder transformGPSInfoBuilder(String callLetter, SegGPSInfo gps, SegPackage pkg){
		cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo.Builder builder = GBossDataBuff.GpsInfo.newBuilder();
		builder.setCallLetter(callLetter);
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo baseBuilder = transGPSBaseInfo(callLetter, gps);
		builder.setBaseInfo(baseBuilder);
		builder.setContent(ByteString.copyFrom(pkg.getSource()));
		builder.setHistory(gps.getHistory());
		
		return builder;
	}
	
	/**
	 * alarmInfo转protobuf alarmInfo
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.AlarmInfo transformAlarmInfo(String callLetter, SegGPSInfo gps, SegPackage pkg){
		cc.chinagps.gateway.buff.GBossDataBuff.AlarmInfo.Builder builder = GBossDataBuff.AlarmInfo.newBuilder();
		builder.setCallLetter(callLetter);
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo baseBuilder = transGPSBaseInfo(callLetter, gps);
		builder.setBaseInfo(baseBuilder);
		builder.setContent(ByteString.copyFrom(pkg.getSource()));
		builder.setHistory(gps.getHistory());
		builder.setUnittype(UnitProtocolTypes.UNIT_PROTOCOL_TYPE_T3688);
		
		return builder.build();
	}
	
	/**
	 * gpsInfo转protobuf gpsBaseInfo
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo transGPSBaseInfo(String callLetter, SegGPSInfo gps){
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo.Builder baseBuilder = GBossDataBuff.GpsBaseInfo.newBuilder();
		baseBuilder.setGpsTime(gps.getGpsTime().getTime());
		baseBuilder.setLoc(gps.isLoc());
		baseBuilder.setLat((int) (gps.getLat() * 1000000));
		baseBuilder.setLng((int) (gps.getLng() * 1000000));
		baseBuilder.setSpeed((int) Math.round(gps.getSpeed() * 10));
		baseBuilder.setCourse(gps.getCourse());
		baseBuilder.addAllStatus(gps.getStatus());
		if(gps.getTotalDistance() != null){
			baseBuilder.setTotalDistance(gps.getTotalDistance().intValue());
		}
		
		if(gps.getHeight() != null){
			baseBuilder.setHeight(gps.getHeight().intValue());
		}
		
		if (gps.getAccTimeLen() != null) {
			baseBuilder.setAccTimeLen(gps.getAccTimeLen().intValue());
		}
		
		if(gps.getOilPercent() != null){
			baseBuilder.setOilPercent(gps.getOilPercent());
		}
		
		if(gps.getTemperature1() != null){
			baseBuilder.setTemperature1(gps.getTemperature1());
		}
		
		if(gps.getTemperature2() != null){
			baseBuilder.setTemperature2(gps.getTemperature2());
		}
		
		List<MapEntry> ex = gps.getExtendsStatus();
		for(int i = 0; i < ex.size(); i++){
			MapEntry status = ex.get(i);
			baseBuilder.addAppendParams(cc.chinagps.gateway.buff.GBossDataBuff.MapEntry
					.newBuilder().setKey(status.getKey()).setValue(status.getValue()));
		}
		
		// 北汽gps信息
		BeiJingInfo beiJingInfo = gps.getBeiJingInfo();
		if (beiJingInfo != null) {
			GBossDataBuff.BeiJingInfo.Builder beiJingInfoBuilder = GBossDataBuff.BeiJingInfo.newBuilder();
			beiJingInfoBuilder.setDevid(beiJingInfo.getDevid());
			beiJingInfoBuilder.setElectricity(beiJingInfo.getElectricity());
			beiJingInfoBuilder.setGsmvalue(beiJingInfo.getGsmvalue());
			beiJingInfoBuilder.setRfid(beiJingInfo.getRfid());
			beiJingInfoBuilder.setSatellitecount(beiJingInfo.getSatellitecount());
			beiJingInfoBuilder.setVoltage(beiJingInfo.getVoltage());
			beiJingInfoBuilder.setMileage(beiJingInfo.getMileage());
			if (beiJingInfo.getChargeStatus() != null)
				beiJingInfoBuilder.setChargeStatus(beiJingInfo.getChargeStatus());
			if (beiJingInfo.getFillGunStatus() != null)
				beiJingInfoBuilder.setFillGunStatus(beiJingInfo.getFillGunStatus());
			if (beiJingInfo.getAccStatus() != null)
				beiJingInfoBuilder.setAccStatus(beiJingInfo.getAccStatus());
			if (beiJingInfo.getOilStatus() != null)
				beiJingInfoBuilder.setOilStatus(beiJingInfo.getOilStatus());
			if (beiJingInfo.getDefendStatus() != null)
				beiJingInfoBuilder.setDefendStatus(beiJingInfo.getDefendStatus());
			if (beiJingInfo.getCentralLockStatus() != null)
				beiJingInfoBuilder.setCentralLockStatus(beiJingInfo.getCentralLockStatus());
			if (beiJingInfo.getDoorStatus() != null)
				beiJingInfoBuilder.setDoorStatus(beiJingInfo.getDoorStatus());
			if (beiJingInfo.getLightStatus() != null)
				beiJingInfoBuilder.setLightStatus(beiJingInfo.getLightStatus());
			if (beiJingInfo.getWindowStatus() != null)
				beiJingInfoBuilder.setWindowStatus(beiJingInfo.getWindowStatus());
				beiJingInfoBuilder.setSmallVoltage(beiJingInfo.getSmallVoltage());
			baseBuilder.setBeijingInfo(beiJingInfoBuilder);
		}

		// 基站信息
		List<SegBaseStationInfo> baseStationInfos = gps.getBaseStationInfos();
		if (baseStationInfos != null && baseStationInfos.size() > 0) {
			for (SegBaseStationInfo segBaseStationInfo : baseStationInfos) {
				GBossDataBuff.BaseStationInfo.Builder baseStationBuilder = GBossDataBuff.BaseStationInfo.newBuilder();
				baseStationBuilder.setBsss(segBaseStationInfo.getDbm());
				baseStationBuilder.setCid(segBaseStationInfo.getCellId());
				baseStationBuilder.setLac(segBaseStationInfo.getLac());
				baseStationBuilder.setMcc(segBaseStationInfo.getMcc() + "");
				baseStationBuilder.setMnc(segBaseStationInfo.getMnc() + "");
				baseBuilder.addBaseStations(baseStationBuilder);
			}
		}
		
		SegOBDInfo obdInfo = gps.getObdInfo();
		if(obdInfo != null){
			cc.chinagps.gateway.buff.GBossDataBuff.OBDInfo.Builder obdBuilder = OBDInfo.newBuilder();
			obdBuilder.setHourOil(obdInfo.getHourOil() * 10);
			obdBuilder.setAverageOil(obdInfo.getAverageOil() * 10);
			obdBuilder.setTotalDistance(obdInfo.getTotalDistance() * 1000);
			
			if(obdInfo.isOilPercent()){
				obdBuilder.setRemainPercentOil(obdInfo.getRemainOil());
			}else{
				obdBuilder.setRemainOil(obdInfo.getRemainOil() * 10);
			}

			obdBuilder.setWaterTemperature(obdInfo.getWaterTemperature());
			obdBuilder.setReviseOil(obdInfo.getReviseOil() * 10);
			obdBuilder.setRotationSpeed(obdInfo.getRotationSpeed() / 4);
			obdBuilder.setIntakeAirTemperature(obdInfo.getIntakeAirTemperature());
			obdBuilder.setAirDischange(obdInfo.getAirDischange());
			
			Map<String, String> others = obdInfo.getOtherInfoes();
			if(others != null){
				Iterator<Entry<String, String>> ito = others.entrySet().iterator();
				while(ito.hasNext()){
					Entry<String, String> other = ito.next();
					obdBuilder.addOtherInfo(cc.chinagps.gateway.buff.GBossDataBuff.MapEntry
							.newBuilder().setKey(other.getKey()).setValue(other.getValue()));
				}
			}
			
			obdBuilder.setCallLetter(callLetter);
			obdBuilder.setGpsTime(gps.getGpsTime().getTime());
			obdBuilder.setUnitType(UnitProtocolTypes.UNIT_PROTOCOL_TYPE_T3688);
			
			baseBuilder.setObdInfo(obdBuilder);
		}
		
		return baseBuilder.build();
	}
	
	/**
	 * serviceData转protobuf
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.OperateData transformServiceData(String callLetter, ServiceData serviceData
			, ServiceDetail serviceDetail, SegPackage pkg){
		cc.chinagps.gateway.buff.GBossDataBuff.OperateData.Builder builder = GBossDataBuff.OperateData.newBuilder();
		builder.setCallLetter(callLetter);
		
		cc.chinagps.gateway.buff.GBossDataBuff.OperateDataBaseInfo.Builder baseBuilder = GBossDataBuff.OperateDataBaseInfo.newBuilder();
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo.Builder startGpsBaseBuilder = GBossDataBuff.GpsBaseInfo.newBuilder();
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo.Builder stopGpsBaseBuilder = GBossDataBuff.GpsBaseInfo.newBuilder();
		if(serviceDetail == null){
			baseBuilder.setPrice(0);
			baseBuilder.setCountTime(0);
			baseBuilder.setOperateMoney(0);
			baseBuilder.setOperateMile(0);
			
			//gps time
			startGpsBaseBuilder.setGpsTime(0);
			stopGpsBaseBuilder.setGpsTime(0);
		}else{
			baseBuilder.setPrice(serviceDetail.getServiceMoney());
			baseBuilder.setCountTime(serviceDetail.getTimeLength());
			baseBuilder.setOperateMoney(serviceDetail.getServiceMoney());
			baseBuilder.setOperateMile(serviceDetail.getDistance());
			baseBuilder.setNullMile(serviceDetail.getEmptyDistance());
			baseBuilder.setOverSpeedMile(serviceDetail.getOverSpeedDistance());
			baseBuilder.setOverSpeedCount(serviceDetail.getOverSpeedCount());
			baseBuilder.setPowerOffCount(serviceDetail.getEmptyPowerOffCount());
			baseBuilder.setPowerOffTime(serviceDetail.getEmptyPowerOffTime());
			baseBuilder.setSequenceNo(serviceData.getServiceSn() & 0xFF);
			
			//add
			baseBuilder.setPerprice(serviceDetail.getUnitPrice());
			baseBuilder.setLicenseno(serviceDetail.getPermitNo());
			
			//gps time
			startGpsBaseBuilder.setGpsTime(serviceDetail.getStartTime().getTime());
			stopGpsBaseBuilder.setGpsTime(serviceDetail.getEndTime().getTime());
		}
		builder.setBaseInfo(baseBuilder);
		
		//startGps
		startGpsBaseBuilder.setLoc(serviceData.isOnLoc());
		startGpsBaseBuilder.setLat((int) (serviceData.getOnLat() * 1000000));
		startGpsBaseBuilder.setLng((int) (serviceData.getOnLng() * 1000000));
		startGpsBaseBuilder.setSpeed(0);
		startGpsBaseBuilder.setCourse(0);
		builder.setStartGps(startGpsBaseBuilder);
		
		//stopGps
		stopGpsBaseBuilder.setLoc(serviceData.isOffLoc());
		stopGpsBaseBuilder.setLat((int) (serviceData.getOffLat() * 1000000));
		stopGpsBaseBuilder.setLng((int) (serviceData.getOffLng() * 1000000));
		stopGpsBaseBuilder.setSpeed(0);
		stopGpsBaseBuilder.setCourse(0);
		builder.setStopGps(stopGpsBaseBuilder);
		
		builder.setContent(ByteString.copyFrom(pkg.getSource()));
		
		return builder.build();
	}
	
	/**
	 * TravelInfo转protobuf
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.TravelInfo transformTravelInfo(String callLetter, SegTravelInfo travelInfo, SegGPSInfo gps){
		cc.chinagps.gateway.buff.GBossDataBuff.TravelInfo.Builder builder =  cc.chinagps.gateway.buff.GBossDataBuff.TravelInfo.newBuilder();
		builder.setCallLetter(callLetter);
		builder.setStartTime(travelInfo.getStartTime().getTime());
		builder.setEndTime(travelInfo.getEndTime().getTime());
		builder.setDistance(travelInfo.getDistance() * 100);
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
		builder.setTiredDrivingTime(travelInfo.getTiredDrivingTime() * 60);
		builder.setAverageRotationSpeed(travelInfo.getAverageRotationSpeed());
		builder.setMaxOil(travelInfo.getMaxOil());
		builder.setIdleTime(travelInfo.getIdleTime() * 60);
		
		GpsBaseInfo gpsBase = transGPSBaseInfo(callLetter, gps);
		builder.setEndGps(gpsBase);
		 
		return builder.build();
	}
	
	/**
	 * FaultInfo转protobuf
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.FaultInfo transformFaultInfo(String callLetter, SegFaultInfo faultInfo){
		cc.chinagps.gateway.buff.GBossDataBuff.FaultInfo.Builder builder = cc.chinagps.gateway.buff.GBossDataBuff.FaultInfo.newBuilder();
		builder.setCallLetter(callLetter);
		builder.setFaultTime(faultInfo.getFaultTime().getTime());
		List<String> list = faultInfo.getFaultCode();
		/*
		if(list != null){
			for(int i = 0; i < list.size(); i++){
				builder.addFaultCode(list.get(i));
			}
		}*/
		builder.setUnitType(UnitProtocolTypes.UNIT_PROTOCOL_TYPE_T3688);
		if(list != null && list.size() > 0){
			cc.chinagps.gateway.buff.GBossDataBuff.FaultDefine.Builder fdBuilder = cc.chinagps.gateway.buff.GBossDataBuff.FaultDefine.newBuilder();
			fdBuilder.addAllFaultCode(list);
			
			builder.addFaults(fdBuilder);
		}
		
		return builder.build();
	}
}