package cc.chinagps.gateway.unit.beforemarket.yidongwang.util;

import cc.chinagps.gateway.buff.GBossDataBuff;
import cc.chinagps.gateway.unit.beans.BaseStationAddress;
import cc.chinagps.gateway.unit.beans.BaseStationInfo;
import cc.chinagps.gateway.unit.beforemarket.common.pkg.BeforeMarketPackage;
import cc.chinagps.gateway.unit.beforemarket.yidongwang.upload.beans.YdwGPSInfo;
import cc.chinagps.gateway.unit.beforemarket.yidongwang.upload.beans.YdwInfo;
import cc.chinagps.gateway.unit.beforemarket.yidongwang.upload.beans.YdwStatus;
import cc.chinagps.gateway.util.BaseStationUtil;
import cc.chinagps.gateway.util.UnitProtocolTypes;

import com.google.protobuf.ByteString;

public class YdwProtobufUtil {
	/**
	 * gpsInfo转protobuf gpsInfo
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo transformGPSInfo(String callLetter, BeforeMarketPackage pkg, 
			YdwGPSInfo gps, YdwStatus status, BaseStationInfo baseStationInfo, YdwInfo ydwInfo, boolean isHistory){
		cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo.Builder builder = GBossDataBuff.GpsInfo.newBuilder();
		builder.setCallLetter(callLetter);
		
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo baseBuilder = transGPSBaseInfo(callLetter, gps, status, baseStationInfo, ydwInfo);
		builder.setBaseInfo(baseBuilder);
		builder.setContent(ByteString.copyFrom(pkg.getSource()));
		builder.setHistory(isHistory? 1: 0);
		
		return builder.build();
	}
	
	/**
	 * alarmInfo转protobuf alarmInfo
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.AlarmInfo transformAlarmInfo(String callLetter, BeforeMarketPackage pkg, 
			YdwGPSInfo gps, YdwStatus status, BaseStationInfo baseStationInfo, YdwInfo ydwInfo, boolean isHistory, int trigger){
		cc.chinagps.gateway.buff.GBossDataBuff.AlarmInfo.Builder builder = GBossDataBuff.AlarmInfo.newBuilder();
		builder.setCallLetter(callLetter);
		
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo baseBuilder = transGPSBaseInfo(callLetter, gps, status, baseStationInfo, ydwInfo);
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
			YdwGPSInfo gps, YdwStatus status, BaseStationInfo baseStationInfo, YdwInfo ydwInfo){
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
		
		if(baseStationInfo != null){
			baseBuilder.addBaseStations(transformBaseStationInfo(baseStationInfo));
			
			//基站定位
			BaseStationAddress baseStationAddress = BaseStationUtil.getBaseStationAddress(baseStationInfo);
			if(baseStationAddress != null){
				cc.chinagps.gateway.buff.GBossDataBuff.BaseStationAddress paddress = transformBaseStationAddress(baseStationAddress);
				baseBuilder.setAddress(paddress);
			}
		}
		
		if(ydwInfo != null){
			baseBuilder.setYdwInfo(transformYDWInfo(ydwInfo));
		}
		
		return baseBuilder.build();
	}
	
	/**
	 * 基站信息转protobuf
	 */
	private static cc.chinagps.gateway.buff.GBossDataBuff.BaseStationInfo transformBaseStationInfo(BaseStationInfo baseStationInfo){
		cc.chinagps.gateway.buff.GBossDataBuff.BaseStationInfo.Builder builder = cc.chinagps.gateway.buff.GBossDataBuff.BaseStationInfo.newBuilder();
		builder.setMcc(baseStationInfo.getMcc());
		builder.setMnc(baseStationInfo.getMnc());
		builder.setLac(baseStationInfo.getLac());
		builder.setCid(baseStationInfo.getCid());
		
		return builder.build();
	}
	
	/**
	 * 基站定位信息转protobuf
	 */
	private static cc.chinagps.gateway.buff.GBossDataBuff.BaseStationAddress transformBaseStationAddress(BaseStationAddress baseStationAddress){
		cc.chinagps.gateway.buff.GBossDataBuff.BaseStationAddress.Builder builder = cc.chinagps.gateway.buff.GBossDataBuff.BaseStationAddress.newBuilder();
		builder.setLng((int) (baseStationAddress.getLon() * 1000000));
		builder.setLat((int) (baseStationAddress.getLat() * 1000000));
		
		if(baseStationAddress.getPrecision() != null){
			builder.setPrecision(baseStationAddress.getPrecision());
		}
		
		if(baseStationAddress.getAddress() != null){
			builder.setAddressDesc(baseStationAddress.getAddress());
		}
		
		return builder.build();
	}
	
	/**
	 * 一动网信息转protobuf
	 */
	private static cc.chinagps.gateway.buff.GBossDataBuff.YDWInfo transformYDWInfo(YdwInfo ydwInfo){
		cc.chinagps.gateway.buff.GBossDataBuff.YDWInfo.Builder builder = cc.chinagps.gateway.buff.GBossDataBuff.YDWInfo.newBuilder();
		builder.setTemperature(ydwInfo.getTemprature());
		builder.setDrivingTime(ydwInfo.getDrivingTime());
		builder.setTotalDistance(ydwInfo.getTotalDistance());
		builder.setUnitPower(ydwInfo.getUnitPower());
		builder.setBluetoothPower(ydwInfo.getBluetoothPower());
		
		return builder.build();
	}
}