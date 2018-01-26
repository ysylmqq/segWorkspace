package cc.chinagps.gateway.unit.beforemarket.hm.util;

import java.util.List;

import cc.chinagps.gateway.buff.GBossDataBuff;
import cc.chinagps.gateway.buff.GBossDataBuff.ECUConfig;
import cc.chinagps.gateway.buff.GBossDataBuff.FaultInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.FaultLightStatus;
import cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.OBDInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.TravelInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.UnitVersion;
import cc.chinagps.gateway.unit.beforemarket.common.pkg.BeforeMarketPackage;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.beans.HMFaultDefine;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.beans.HMFaultInfo;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.beans.HMFaultLightInfo;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.beans.HMGPSInfo;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.beans.HMOBDInfo;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.beans.HMStatus;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.beans.HMTravelInfo;
import cc.chinagps.gateway.util.UnitProtocolTypes;
import cc.chinagps.gateway.util.Util;

import com.google.protobuf.ByteString;

public class HMProtobufUtil {
	/**
	 * gpsInfo转protobuf gpsInfo
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo transformGPSInfo(String callLetter, BeforeMarketPackage pkg, 
			HMGPSInfo gps, HMStatus status, HMOBDInfo obdInfo, HMFaultLightInfo faultLight, boolean isHistory){
		cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo.Builder builder = GBossDataBuff.GpsInfo.newBuilder();
		builder.setCallLetter(callLetter);
		
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo baseBuilder = transGPSBaseInfo(callLetter, gps, status, obdInfo, faultLight, isHistory);
		builder.setBaseInfo(baseBuilder);
		builder.setContent(ByteString.copyFrom(pkg.getSource()));
		builder.setHistory(isHistory? 1: 0);
		
		return builder.build();
	}
	
	/**
	 * alarmInfo转protobuf alarmInfo
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.AlarmInfo transformAlarmInfo(String callLetter, BeforeMarketPackage pkg, 
			HMGPSInfo gps, HMStatus status, HMOBDInfo obdInfo, HMFaultLightInfo faultLight, boolean isHistory, int trigger){
		cc.chinagps.gateway.buff.GBossDataBuff.AlarmInfo.Builder builder = GBossDataBuff.AlarmInfo.newBuilder();
		builder.setCallLetter(callLetter);
		
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo baseBuilder = transGPSBaseInfo(callLetter, gps, status, obdInfo, faultLight, isHistory);
		builder.setBaseInfo(baseBuilder);
		builder.setContent(ByteString.copyFrom(pkg.getSource()));
		builder.setHistory(isHistory? 1: 0);
		builder.setUnittype(UnitProtocolTypes.UNIT_PROTOCOL_TYPE_HM);
		builder.setTrigger(trigger);
		
		return builder.build();
	}
	
	/**
	 * gpsInfo转protobuf gpsBaseInfo
	 */
	private static cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo transGPSBaseInfo(String callLetter, 
			HMGPSInfo gps, HMStatus status, HMOBDInfo obdInfo, HMFaultLightInfo faultLight, boolean isHistory){
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo.Builder baseBuilder = GBossDataBuff.GpsBaseInfo.newBuilder();
		baseBuilder.setGpsTime(gps.getGpsTime().getTime());
		baseBuilder.setLoc(gps.isLoc());
		baseBuilder.setLat((int) (gps.getLat() * 1000000));
		baseBuilder.setLng((int) (gps.getLng() * 1000000));
		baseBuilder.setSpeed(gps.getSpeed());
		baseBuilder.setCourse(gps.getCourse());
		
		if(status != null){
			baseBuilder.addAllStatus(status.getStatusList());
		}
		
		if(obdInfo != null){
			OBDInfo bobd = transOBDInfo(callLetter, gps.getGpsTime().getTime(), obdInfo, isHistory);
			baseBuilder.setObdInfo(bobd);
			
			if(obdInfo.getSignal() != null){
				baseBuilder.setSignal(obdInfo.getSignal());
			}
		}
		
		if(faultLight != null){
			FaultLightStatus faultLightStatus = transformFaultLightStatus(faultLight);
			baseBuilder.setFaultLightStatus(faultLightStatus);
		}
		
		return baseBuilder.build();
	}
	
	/**
	 * OBDInfo转protobuf OBDInfo
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.OBDInfo transOBDInfo(String callLetter, long gpsTime, HMOBDInfo obdInfo, boolean isHistory){
		cc.chinagps.gateway.buff.GBossDataBuff.OBDInfo.Builder obdBuilder = OBDInfo.newBuilder();
		obdBuilder.setHourOil(obdInfo.getHourOil() * 10);
		obdBuilder.setAverageOil(obdInfo.getAverageOil() * 10);
		obdBuilder.setTotalDistance(obdInfo.getTotalDistance() * 100);
		
		obdBuilder.setRemainOil(obdInfo.getRemainOil() * 10);

		obdBuilder.setWaterTemperature(obdInfo.getWaterTemperature());
		obdBuilder.setReviseOil(obdInfo.getReviseOil() * 10);
		obdBuilder.setRotationSpeed(obdInfo.getRotationSpeed());
		obdBuilder.setIntakeAirTemperature(obdInfo.getIntakeAirTemperature());
		obdBuilder.setSpeed(obdInfo.getSpeed());
		obdBuilder.setRemainDistance(obdInfo.getRemainDistance() * 100);
		
		obdBuilder.setCallLetter(callLetter);
		obdBuilder.setGpsTime(gpsTime);
		obdBuilder.setUnitType(UnitProtocolTypes.UNIT_PROTOCOL_TYPE_HM);
		obdBuilder.setHistory(isHistory? 1: 0);
		
		return obdBuilder.build();
	}
	
	/**
	 * travelInfo转protobuf
	 */
	public static TravelInfo transformTravelInfo(String callLetter, HMTravelInfo travelInfo, 
			HMGPSInfo endGPS, HMStatus endStatus, boolean isHistory){
		cc.chinagps.gateway.buff.GBossDataBuff.TravelInfo.Builder builder =  cc.chinagps.gateway.buff.GBossDataBuff.TravelInfo.newBuilder();
		HMGPSInfo startGPS = travelInfo.getStartGPS();
		
		builder.setCallLetter(callLetter);
		builder.setStartTime(startGPS.getGpsTime().getTime());
		builder.setEndTime(endGPS.getGpsTime().getTime());
		builder.setDistance(travelInfo.getDistance() * 100);
		builder.setMaxSpeed(travelInfo.getMaxSpeed());
		builder.setOverSpeedTime(travelInfo.getOverSpeedTime());
		builder.setQuickBrakeCount(travelInfo.getQuickBrakeCount());
		//builder.setEmergencyBrakeCount(0);		//无
		builder.setQuickSpeedUpCount(travelInfo.getQuickSpeedUpCount());
		//builder.setEmergencySpeedUpCount(0);		//无
		builder.setAverageSpeed(travelInfo.getAverageSpeed());
		builder.setMaxWaterTemperature(travelInfo.getMaxWaterTemperature());
		builder.setMaxRotationSpeed(travelInfo.getMaxRotationSpeed());
		builder.setVoltage(travelInfo.getVoltage());
		builder.setTotalOil(travelInfo.getTotalOil());
		builder.setAverageOil(travelInfo.getAverageOil());
		//builder.setTiredDrivingTime(0);						//无
		//builder.setAverageRotationSpeed(0);					//无
		//builder.setMaxOil(0);									//无
		//builder.setIdleTime(0);								//无
		
		GpsBaseInfo startGPSBase = transGPSBaseInfo(callLetter, startGPS, null, null, null, isHistory);
		builder.setStartGps(startGPSBase);
		
		GpsBaseInfo endGPSBase = transGPSBaseInfo(callLetter, endGPS, endStatus, null, null, isHistory);
		builder.setEndGps(endGPSBase);
		
		builder.setHistory(isHistory? 1: 0);

		return builder.build();
	}
	
	/**
	 * FaultInfo转protobuf
	 */
	public static FaultInfo transformFaultInfo(String callLetter, HMFaultInfo faultInfo, long gpsTime, boolean isHistory){
		/*
		cc.chinagps.gateway.buff.GBossDataBuff.FaultInfo.Builder builder = cc.chinagps.gateway.buff.GBossDataBuff.FaultInfo.newBuilder();
		builder.setCallLetter(callLetter);
		builder.setFaultTime(faultInfo.getFaultTime().getTime());
		
		List<String> list = faultInfo.getFaultCodeList();
		if(list != null){
			for(int i = 0; i < list.size(); i++){
				builder.addFaultCode(list.get(i));
			}
		}
		
		return builder.build();
		*/
		
		
		cc.chinagps.gateway.buff.GBossDataBuff.FaultInfo.Builder builder = cc.chinagps.gateway.buff.GBossDataBuff.FaultInfo.newBuilder();
		builder.setCallLetter(callLetter);
		builder.setFaultTime(gpsTime);
		builder.setUnitType(UnitProtocolTypes.UNIT_PROTOCOL_TYPE_HM);
		builder.setHistory(isHistory? 1: 0);
		
		List<HMFaultDefine> list = faultInfo.getFaults();
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				HMFaultDefine fd = list.get(i);
				int type = fd.getFaultType();
				List<String> fc = fd.getFaultCode();
				
				cc.chinagps.gateway.buff.GBossDataBuff.FaultDefine.Builder fdBuilder = cc.chinagps.gateway.buff.GBossDataBuff.FaultDefine.newBuilder();
				fdBuilder.setFaultType(type);
				fdBuilder.addAllFaultCode(fc);
				
				builder.addFaults(fdBuilder);
			}
		}
		
		return builder.build();
	}
	
	/**
	 * version转protobuf
	 */
	public static UnitVersion transformVersion(String callLetter, String version, int result){
		cc.chinagps.gateway.buff.GBossDataBuff.UnitVersion.Builder builder = cc.chinagps.gateway.buff.GBossDataBuff.UnitVersion.newBuilder();
		builder.setCallLetter(callLetter);
		builder.setVersion(version);
		builder.setResult(result);
		
		return builder.build();
	}
	
	public static UnitVersion transformVersion(String callLetter, String version, int result ,int canId){
		cc.chinagps.gateway.buff.GBossDataBuff.UnitVersion.Builder builder = cc.chinagps.gateway.buff.GBossDataBuff.UnitVersion.newBuilder();
		builder.setCallLetter(callLetter);
		builder.setVersion(version);
		builder.setResult(result);
		builder.setCanId(canId);
		
		return builder.build();
	}
	
	/**
	 * ecuConfig转protobuf
	 */
	public static ECUConfig transformECUConfig(String callLetter, long updateTime,
			cc.chinagps.gateway.unit.beforemarket.hm.upload.beans.HMECUConfig ecuConfig){
		cc.chinagps.gateway.buff.GBossDataBuff.ECUConfig.Builder builder = cc.chinagps.gateway.buff.GBossDataBuff.ECUConfig.newBuilder();
		builder.setCallLetter(callLetter);
		builder.setUpdateTime(updateTime);
		builder.setCheckFlag(Util.booleaToInt(ecuConfig.isCheckEnabled()));
		builder.setAbs(Util.booleaToInt(ecuConfig.isHasABS()));
		builder.setEsp(Util.booleaToInt(ecuConfig.isHasESP()));
		builder.setPeps(Util.booleaToInt(ecuConfig.isHasPEPS()));
		builder.setTpms(Util.booleaToInt(ecuConfig.isHasTPMS()));		
		builder.setSrs(Util.booleaToInt(ecuConfig.isHasSRS()));
		builder.setEps(Util.booleaToInt(ecuConfig.isHasEPS()));		
		builder.setEms(Util.booleaToInt(ecuConfig.isHasEMS()));
		builder.setImmo(Util.booleaToInt(ecuConfig.isHasIMMO()));	
		builder.setBcm(Util.booleaToInt(ecuConfig.isHasBCM()));
		builder.setTcu(Util.booleaToInt(ecuConfig.isHasTCU()));
		builder.setIcm(Util.booleaToInt(ecuConfig.isHasICM()));
		builder.setApm(Util.booleaToInt(ecuConfig.isHasAPM()));	
		builder.setConfigContent(ByteString.copyFrom(ecuConfig.getConfigs()));
		
		return builder.build();
	}
	
	/**
	 * FaultLightStatus转protobuf
	 */
	public static FaultLightStatus transformFaultLightStatus(HMFaultLightInfo faultLight){
		cc.chinagps.gateway.buff.GBossDataBuff.FaultLightStatus.Builder builder = 
				cc.chinagps.gateway.buff.GBossDataBuff.FaultLightStatus.newBuilder();
		
		cc.chinagps.gateway.buff.GBossDataBuff.NodeLostInfo.Builder nodeLostBuilder = 
				cc.chinagps.gateway.buff.GBossDataBuff.NodeLostInfo.newBuilder();
		nodeLostBuilder.setAbs(Util.booleaToInt(faultLight.isABSLost()));
		nodeLostBuilder.setEsp(Util.booleaToInt(faultLight.isESPLost()));
		nodeLostBuilder.setEms(Util.booleaToInt(faultLight.isEMSLost()));
		nodeLostBuilder.setPeps(Util.booleaToInt(faultLight.isPEPSLost()));
		nodeLostBuilder.setTcu(Util.booleaToInt(faultLight.isTCULost()));
		nodeLostBuilder.setBcm(Util.booleaToInt(faultLight.isBCMLost()));
		nodeLostBuilder.setIcm(Util.booleaToInt(faultLight.isICMLost()));
		
		cc.chinagps.gateway.buff.GBossDataBuff.NodeFaultInfo.Builder nodeFaultBuilder = 
				cc.chinagps.gateway.buff.GBossDataBuff.NodeFaultInfo.newBuilder();
		nodeFaultBuilder.setEbd(Util.booleaToInt(faultLight.isHasEBDFaults()));
		nodeFaultBuilder.setAbs(Util.booleaToInt(faultLight.isHasABSFaults()));
		nodeFaultBuilder.setEsp(Util.booleaToInt(faultLight.isHasESPFaults()));
		nodeFaultBuilder.setSvs(Util.booleaToInt(faultLight.isHasSVSFaults()));
		nodeFaultBuilder.setMil(Util.booleaToInt(faultLight.isHasMILFaults()));
		nodeFaultBuilder.setTcu(Util.booleaToInt(faultLight.isHasTCUFaults()));
		nodeFaultBuilder.setPeps(Util.booleaToInt(faultLight.isHasPEPSFaults()));
		nodeFaultBuilder.setTbox(Util.booleaToInt(faultLight.isHasTBOXFaults()));
		
		builder.setNodeLostInfo(nodeLostBuilder);
		builder.setNodeFaultInfo(nodeFaultBuilder);
		
		return builder.build();
	}
}