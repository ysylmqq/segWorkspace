package cc.chinagps.gateway.unit.kelong.util;

import java.util.ArrayList;

import com.google.protobuf.ByteString;

import cc.chinagps.gateway.buff.GBossDataBuff;
import cc.chinagps.gateway.buff.GBossDataBuff.BaseStationInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.KeLongInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.OBDInfo;
import cc.chinagps.gateway.unit.kelong.pkg.KeLongPackage;
import cc.chinagps.gateway.unit.kelong.upload.bean.KeLongBaseStationInfo;
import cc.chinagps.gateway.unit.kelong.upload.bean.KeLongExtendInfo;
import cc.chinagps.gateway.unit.kelong.upload.bean.KeLongGPSInfo;
import cc.chinagps.gateway.unit.kelong.upload.bean.KeLongOBDInfo;
import cc.chinagps.gateway.util.UnitProtocolTypes;

public class KeLongProtobufUtil {
	/**
	 * gpsInfo转protobuf gpsInfo
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo transformGPSInfo(String callLetter, KeLongPackage pkg,
			KeLongGPSInfo gps, KeLongOBDInfo obdInfo, KeLongExtendInfo keLongExtendInfo,KeLongBaseStationInfo baseStation, boolean isHistory) {
		cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo.Builder builder = GBossDataBuff.GpsInfo.newBuilder();
		builder.setCallLetter(callLetter);

		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo baseBuilder = transGPSBaseInfo(callLetter, gps, obdInfo,
				keLongExtendInfo,baseStation,isHistory);
		builder.setBaseInfo(baseBuilder);
		builder.setContent(ByteString.copyFrom(pkg.getSource()));
		builder.setHistory(isHistory ? 1 : 0);

		return builder.build();
	}
	
	/**
	 * @todo:科隆gpsInfo转protobuf gpsinfo
	 * @author:cj
	 * @param:
	 * @return:
	 * @remark:
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo.Builder transformGPSInfoBuilder(String callLetter, KeLongPackage pkg,
			KeLongGPSInfo gps, KeLongOBDInfo obdInfo, KeLongExtendInfo keLongExtendInfo,KeLongBaseStationInfo baseStation, boolean isHistory) {
		cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo.Builder builder = GBossDataBuff.GpsInfo.newBuilder();
		builder.setCallLetter(callLetter);

		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo baseBuilder = transGPSBaseInfo(callLetter, gps, obdInfo,
				keLongExtendInfo,baseStation,isHistory);
		builder.setBaseInfo(baseBuilder);
		builder.setContent(ByteString.copyFrom(pkg.getSource()));
		builder.setHistory(isHistory ? 1 : 0);		

		return builder;
	}

	/**
	 * gpsInfo转protobuf gpsBaseInfo
	 */
	private static cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo transGPSBaseInfo(String callLetter,
			KeLongGPSInfo gps, KeLongOBDInfo obdInfo, KeLongExtendInfo keLongExtendInfo,KeLongBaseStationInfo baseStation, boolean isHistory) {
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo.Builder baseBuilder = GBossDataBuff.GpsBaseInfo.newBuilder();
		baseBuilder.setGpsTime(gps.getGpsTime());
		baseBuilder.setLoc(gps.isLoc());
		baseBuilder.setLat((int) gps.getLat());
		baseBuilder.setLng((int) gps.getLng());
		baseBuilder.setSpeed(gps.getSpeed());
		baseBuilder.setCourse(gps.getCourse()/10);
		baseBuilder.setHeight(gps.getHeight()/10);
		baseBuilder.setSatelliteCount(gps.getSatelliteCount());
		baseBuilder.setHdop(gps.getHdop());
		baseBuilder.setPdop(gps.getPdop());
		baseBuilder.setVdop(gps.getVdop());

		baseBuilder.addAllStatus(gps.getStatus());

		if (obdInfo != null) {
			OBDInfo bobd = transOBDInfo(callLetter, gps.getGpsTime(), obdInfo, isHistory);
			baseBuilder.setObdInfo(bobd);
			baseBuilder.setTotalDistance(obdInfo.getTotalDistance());
		}

		if (keLongExtendInfo != null) {
			KeLongInfo keLongInfo = transKeLongInfo(callLetter, keLongExtendInfo, isHistory);
			baseBuilder.setKeLongInfo(keLongInfo);
		}
        
		if (baseStation != null){
			ArrayList<BaseStationInfo.Builder> list = transBaseStationInfo(callLetter, baseStation);
			for(BaseStationInfo.Builder builder:list){
				baseBuilder.addBaseStations(builder);
			}
		}
		return baseBuilder.build();
	}

	/**
	 * OBDInfo转protobuf OBDInfo
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.KeLongInfo transKeLongInfo(String callLetter,
			KeLongExtendInfo keLongExtendInfo, boolean isHistory) {
		cc.chinagps.gateway.buff.GBossDataBuff.KeLongInfo.Builder keLongBuilder = KeLongInfo.newBuilder();
		Integer collideCount = keLongExtendInfo.getCollideCount();
		if (collideCount != null) {
			keLongBuilder.setCollideCount(keLongExtendInfo.getCollideCount());
		}

		Integer idleAlarmProperty = keLongExtendInfo.getIdleAlarmProperty();
		if (idleAlarmProperty != null) {
			keLongBuilder.setIdleAlarmProperty(idleAlarmProperty);
		}

		Integer quickBrakeCount = keLongExtendInfo.getQuickBrakeCount();
		if (quickBrakeCount != null) {
			keLongBuilder.setQuickBrakeCount(quickBrakeCount);
		}

		Integer quickSpeedUpCount = keLongExtendInfo.getQuickSpeedUpCount();
		if (quickSpeedUpCount != null) {
			keLongBuilder.setQuickSpeedUpCount(quickSpeedUpCount);
		}

		Integer quickTurnCount = keLongExtendInfo.getQuickTurnCount();
		if (quickTurnCount != null) {
			keLongBuilder.setQuickTurnCount(quickTurnCount);
		}

		Integer idleTime = keLongExtendInfo.getIdleTime();
		if (idleTime != null) {
			keLongBuilder.setIdleTime(idleTime);
		}

		Integer idleTotalOil = keLongExtendInfo.getIdleTotalOil();
		if (idleTotalOil != null) {
			keLongBuilder.setIdleTotalOil(idleTotalOil);
		}

		return keLongBuilder.build();
	}

	/**
	 * OBDInfo转protobuf OBDInfo
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.OBDInfo transOBDInfo(String callLetter, long gpsTime,
			KeLongOBDInfo obdInfo, boolean isHistory) {
		cc.chinagps.gateway.buff.GBossDataBuff.OBDInfo.Builder obdBuilder = OBDInfo.newBuilder();
		obdBuilder.setTotalDistance(obdInfo.getTotalDistance());
		obdBuilder.setRotationSpeed(obdInfo.getRotationSpeed());
		obdBuilder.setSpeed(obdInfo.getSpeed());
		obdBuilder.setBatterytotalvoltage(obdInfo.getBatteryVoltage());
		obdBuilder.setTotalOil(obdInfo.getTotalOil());
		obdBuilder.setTotalDriveTime(obdInfo.getTotalDriveTime());
		obdBuilder.setCallLetter(callLetter);
		obdBuilder.setGpsTime(gpsTime);
		obdBuilder.setUnitType(UnitProtocolTypes.UNIT_PROTOCOL_TYPE_KELONG);
		obdBuilder.setRemainOil(obdInfo.getRemainOil());//2017-5-24 by dy
		obdBuilder.setHistory(isHistory ? 1 : 0);

		return obdBuilder.build();
	}

	public static ArrayList<cc.chinagps.gateway.buff.GBossDataBuff.BaseStationInfo.Builder> transBaseStationInfo(String callLetter,
			KeLongBaseStationInfo keLongBaseStationInfo) {
		ArrayList<BaseStationInfo.Builder> list = new ArrayList<BaseStationInfo.Builder>();
		
		if(keLongBaseStationInfo.getServerLAC()>0 && keLongBaseStationInfo.getServerCellID()>0){
			cc.chinagps.gateway.buff.GBossDataBuff.BaseStationInfo.Builder keLongBuilder = BaseStationInfo.newBuilder();
			keLongBuilder.setLac(keLongBaseStationInfo.getServerLAC());
			keLongBuilder.setCid(keLongBaseStationInfo.getServerCellID());
			keLongBuilder.setMcc("460");
			keLongBuilder.setMnc(""+keLongBaseStationInfo.getOperator());
			list.add(keLongBuilder);
		}
		if(keLongBaseStationInfo.getN1CellID()>0 && keLongBaseStationInfo.getN1LAC()>0){
			cc.chinagps.gateway.buff.GBossDataBuff.BaseStationInfo.Builder keLongBuilder = BaseStationInfo.newBuilder();
			keLongBuilder.setLac(keLongBaseStationInfo.getN1LAC());			
			keLongBuilder.setCid(keLongBaseStationInfo.getN1CellID());
			keLongBuilder.setMcc("460");
			keLongBuilder.setMnc(""+(keLongBaseStationInfo.getOperator()));
			list.add(keLongBuilder);
		}
		if(keLongBaseStationInfo.getN2CellID()>0 && keLongBaseStationInfo.getN2LAC()>0){
			cc.chinagps.gateway.buff.GBossDataBuff.BaseStationInfo.Builder keLongBuilder = BaseStationInfo.newBuilder();
			keLongBuilder.setLac(keLongBaseStationInfo.getN2LAC());
			keLongBuilder.setCid(keLongBaseStationInfo.getN2CellID());
			keLongBuilder.setMcc("460");
			keLongBuilder.setMnc(""+(keLongBaseStationInfo.getOperator()));
			list.add(keLongBuilder);
		}
		if(keLongBaseStationInfo.getN3CellID()>0 && keLongBaseStationInfo.getN3LAC()>0){
			cc.chinagps.gateway.buff.GBossDataBuff.BaseStationInfo.Builder keLongBuilder = BaseStationInfo.newBuilder();
			keLongBuilder.setLac(keLongBaseStationInfo.getN3LAC());
			keLongBuilder.setCid(keLongBaseStationInfo.getN3CellID());
			keLongBuilder.setMcc("460");
			keLongBuilder.setMnc(""+(keLongBaseStationInfo.getOperator()));
			list.add(keLongBuilder);
		}
		if(keLongBaseStationInfo.getN4CellID()>0 && keLongBaseStationInfo.getN4LAC()>0){
			cc.chinagps.gateway.buff.GBossDataBuff.BaseStationInfo.Builder keLongBuilder = BaseStationInfo.newBuilder();
			keLongBuilder.setLac(keLongBaseStationInfo.getN4LAC());
			keLongBuilder.setCid(keLongBaseStationInfo.getN4CellID());
			keLongBuilder.setMcc("460");
			keLongBuilder.setMnc(""+(keLongBaseStationInfo.getOperator()));
			list.add(keLongBuilder);
		}
		if(keLongBaseStationInfo.getN5CellID()>0 && keLongBaseStationInfo.getN5LAC()>0){
			cc.chinagps.gateway.buff.GBossDataBuff.BaseStationInfo.Builder keLongBuilder = BaseStationInfo.newBuilder();
			keLongBuilder.setLac(keLongBaseStationInfo.getN5LAC());
			keLongBuilder.setCid(keLongBaseStationInfo.getN5CellID());
			keLongBuilder.setMcc("460");
			keLongBuilder.setMnc(""+(keLongBaseStationInfo.getOperator()));
			list.add(keLongBuilder);
		}
		if(keLongBaseStationInfo.getN6CellID()>0 && keLongBaseStationInfo.getN6LAC()>0){
			cc.chinagps.gateway.buff.GBossDataBuff.BaseStationInfo.Builder keLongBuilder = BaseStationInfo.newBuilder();
			keLongBuilder.setLac(keLongBaseStationInfo.getN6LAC());
			keLongBuilder.setCid(keLongBaseStationInfo.getN6CellID());
			keLongBuilder.setMcc("460");
			keLongBuilder.setMnc(""+(keLongBaseStationInfo.getOperator()));
			list.add(keLongBuilder);
		}
		return list;
	}
}