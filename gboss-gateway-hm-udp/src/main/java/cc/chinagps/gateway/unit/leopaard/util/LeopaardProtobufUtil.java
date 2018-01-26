package cc.chinagps.gateway.unit.leopaard.util;

import java.util.List;

import com.google.protobuf.ByteString;

import cc.chinagps.gateway.buff.GBossDataBuff;
import cc.chinagps.gateway.buff.GBossDataBuff.FaultInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.FaultLightStatus;
import cc.chinagps.gateway.buff.GBossDataBuff.OBDInfo;
import cc.chinagps.gateway.unit.leopaard.pkg.LeopaardPackage;
import cc.chinagps.gateway.unit.leopaard.upload.bean.LeopaardFaultDefine;
import cc.chinagps.gateway.unit.leopaard.upload.bean.LeopaardFaultInfo;
import cc.chinagps.gateway.unit.leopaard.upload.bean.LeopaardFaultLightInfo;
import cc.chinagps.gateway.unit.leopaard.upload.bean.LeopaardGPSInfo;
import cc.chinagps.gateway.unit.leopaard.upload.bean.LeopaardOBDInfo;
import cc.chinagps.gateway.unit.leopaard.upload.bean.LeopaardStatusInfo;
import cc.chinagps.gateway.util.UnitProtocolTypes;
import cc.chinagps.gateway.util.Util;

public class LeopaardProtobufUtil {
	/**
	 * gpsInfoתprotobuf gpsInfo
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo transformGPSInfo(String callLetter, LeopaardPackage pkg, 
			LeopaardGPSInfo gps, LeopaardStatusInfo status, LeopaardOBDInfo obdInfo, LeopaardFaultLightInfo faultLight, boolean isHistory){
		cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo.Builder builder = GBossDataBuff.GpsInfo.newBuilder();
		builder.setCallLetter(callLetter);
		
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo baseBuilder = transGPSBaseInfo(callLetter, gps, status, obdInfo, faultLight, isHistory);
		builder.setBaseInfo(baseBuilder);
		builder.setContent(ByteString.copyFrom(pkg.getSource()));
		builder.setHistory(isHistory? 1: 0);
		
		return builder.build();
	}
	
	/**
	 * gpsInfoתprotobuf gpsBaseInfo
	 */
	private static cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo transGPSBaseInfo(String callLetter, 
			LeopaardGPSInfo gps, LeopaardStatusInfo status, LeopaardOBDInfo obdInfo, LeopaardFaultLightInfo faultLight, boolean isHistory){
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo.Builder baseBuilder = GBossDataBuff.GpsBaseInfo.newBuilder();
		baseBuilder.setGpsTime(gps.getGpsTime());
		baseBuilder.setLoc(gps.isLoc());
		baseBuilder.setLat((int) gps.getLat());
		baseBuilder.setLng((int) gps.getLng());
		baseBuilder.setSpeed(gps.getSpeed());
		baseBuilder.setCourse(gps.getCourse());
		
		if(status != null){
			baseBuilder.addAllStatus(status.getStatusList());
		}
		
		if(obdInfo != null){
			OBDInfo bobd = transOBDInfo(callLetter, gps.getGpsTime(), obdInfo, isHistory);
			baseBuilder.setObdInfo(bobd);
		}
		
		if(faultLight != null){
			FaultLightStatus faultLightStatus = transformFaultLightStatus(faultLight);
			baseBuilder.setFaultLightStatus(faultLightStatus);
		}
		
		return baseBuilder.build();
	}
	
	/**
	 * OBDInfoתprotobuf OBDInfo
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.OBDInfo transOBDInfo(String callLetter, long gpsTime, LeopaardOBDInfo obdInfo, boolean isHistory){
		cc.chinagps.gateway.buff.GBossDataBuff.OBDInfo.Builder obdBuilder = OBDInfo.newBuilder();
		obdBuilder.setHourOil(obdInfo.getHourOil());
		obdBuilder.setAverageOil(obdInfo.getAverageOil());
		obdBuilder.setTotalDistance(obdInfo.getTotalDistance());
		
		//obdBuilder.setRemainOil(obdInfo.getRemainOil() * 10);
		obdBuilder.setRemainPercentOil(obdInfo.getRemainPercentOil());
		obdBuilder.setWaterTemperature(obdInfo.getWaterTemperature());
		obdBuilder.setReviseOil(obdInfo.getReviseOil());
		obdBuilder.setRotationSpeed(obdInfo.getRotationSpeed());
		obdBuilder.setIntakeAirTemperature(obdInfo.getIntakeAirTemperature());
		obdBuilder.setSpeed(obdInfo.getSpeed());
		obdBuilder.setRemainDistance(obdInfo.getRemainDistance());
		
		obdBuilder.setCallLetter(callLetter);
		obdBuilder.setGpsTime(gpsTime);
		obdBuilder.setUnitType(UnitProtocolTypes.UNIT_PROTOCOL_TYPE_LEOPAARD);
		obdBuilder.setHistory(isHistory? 1: 0);
		
		return obdBuilder.build();
	}
	
	/**
	 * FaultInfoתprotobuf
	 */
	public static FaultInfo transformFaultInfo(String callLetter, LeopaardFaultInfo faultInfo, long gpsTime, boolean isHistory){
		
		cc.chinagps.gateway.buff.GBossDataBuff.FaultInfo.Builder builder = cc.chinagps.gateway.buff.GBossDataBuff.FaultInfo.newBuilder();
		builder.setCallLetter(callLetter);
		builder.setFaultTime(gpsTime);
		builder.setUnitType(UnitProtocolTypes.UNIT_PROTOCOL_TYPE_LEOPAARD);
		builder.setHistory(isHistory? 1: 0);
		
		List<LeopaardFaultDefine> list = faultInfo.getFaults();
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				LeopaardFaultDefine fd = list.get(i);
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
	 * FaultLightStatusתprotobuf
	 */
	public static FaultLightStatus transformFaultLightStatus(LeopaardFaultLightInfo faultLight){
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