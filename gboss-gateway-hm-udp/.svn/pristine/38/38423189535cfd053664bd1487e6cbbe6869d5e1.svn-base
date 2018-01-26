package cc.chinagps.gateway.unit.db44.util;

import cc.chinagps.gateway.buff.GBossDataBuff;
import cc.chinagps.gateway.unit.db44.pkg.DB44Package;
import cc.chinagps.gateway.unit.db44.upload.DB44GPSInfo;
import cc.chinagps.gateway.util.UnitProtocolTypes;

import com.google.protobuf.ByteString;

public class DB44ProtobufUtil {
	/**
	 * GPS转成protobuf格式
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo transformGPSInfo(String callLetter, DB44GPSInfo gps, DB44Package pkg){
		cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo.Builder builder = GBossDataBuff.GpsInfo.newBuilder();
		builder.setCallLetter(callLetter);
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo baseBuilder = transGPSBaseInfo(gps);
		builder.setBaseInfo(baseBuilder);
		builder.setContent(ByteString.copyFrom(pkg.getSource()));
		builder.setHistory(gps.getHistory());
		
		return builder.build();
	}
	
	/**
	 * alarmInfo转protobuf alarmInfo
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.AlarmInfo transformAlarmInfo(String callLetter, DB44GPSInfo gps, DB44Package pkg){
		cc.chinagps.gateway.buff.GBossDataBuff.AlarmInfo.Builder builder = GBossDataBuff.AlarmInfo.newBuilder();
		builder.setCallLetter(callLetter);
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo baseBuilder = transGPSBaseInfo(gps);
		builder.setBaseInfo(baseBuilder);
		builder.setContent(ByteString.copyFrom(pkg.getSource()));
		builder.setHistory(gps.getHistory());
		builder.setUnittype(UnitProtocolTypes.UNIT_PROTOCOL_TYPE_DB44);
		
		return builder.build();
	}
	
	public static cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo transGPSBaseInfo(DB44GPSInfo gps){
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo.Builder baseBuilder = GBossDataBuff.GpsBaseInfo.newBuilder();
		baseBuilder.setGpsTime(gps.getGpsTime().getTime());
		baseBuilder.setLoc(gps.isLoc());
		baseBuilder.setLat((int) (gps.getLat() * 1000000));
		baseBuilder.setLng((int) (gps.getLng() * 1000000));
		baseBuilder.setSpeed((int) Math.round(gps.getSpeed() * 10));
		baseBuilder.setCourse(gps.getCourse());
		baseBuilder.addAllStatus(gps.getStatus());

		baseBuilder.addAppendParams(cc.chinagps.gateway.buff.GBossDataBuff.MapEntry
				.newBuilder().setKey("height").setValue(String.valueOf(gps.getHeight())));
		return baseBuilder.build();
	}
}