package cc.chinagps.gateway.unit.oem.util;

import cc.chinagps.gateway.buff.GBossDataBuff;
import cc.chinagps.gateway.unit.oem.upload.bean.OEMGPSInfo;
import cc.chinagps.gateway.util.UnitProtocolTypes;

import com.google.protobuf.ByteString;

public class OEMProtobufUtil {
	/**
	 * gpsInfo转protobuf gpsInfo
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo transformGPSInfo(
			String callLetter, byte[] source, OEMGPSInfo gps) {
		cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo.Builder builder = GBossDataBuff.GpsInfo
				.newBuilder();
		builder.setCallLetter(callLetter);
		builder.setHistory(gps.getHistory());
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo baseBuilder = transGPSBaseInfo(
				callLetter, gps);
		builder.setBaseInfo(baseBuilder);
		builder.setContent(ByteString.copyFrom(source));
		// builder.setHistory(gps.isHistory()? 1: 0);

		return builder.build();
	}

	/**
	 * alarmInfo转protobuf alarmInfo
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.AlarmInfo transformAlarmInfo(
			String callLetter, byte[] source, OEMGPSInfo gps) {
		cc.chinagps.gateway.buff.GBossDataBuff.AlarmInfo.Builder builder = GBossDataBuff.AlarmInfo
				.newBuilder();
		builder.setCallLetter(callLetter);
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo baseBuilder = transGPSBaseInfo(
				callLetter, gps);
		builder.setBaseInfo(baseBuilder);
		builder.setContent(ByteString.copyFrom(source));
		// builder.setHistory(gps.isHistory()? 1: 0);
		builder.setUnittype(UnitProtocolTypes.UNIT_PROTOCOL_TYPE_OEM);

		return builder.build();
	}

	/**
	 * gpsBaseInfo转protobuf
	 */
	public static cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo transGPSBaseInfo(
			String callLetter, OEMGPSInfo gps) {
		if (gps == null) {
			return null;
		}
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo.Builder baseBuilder = GBossDataBuff.GpsBaseInfo
				.newBuilder();
		baseBuilder.setGpsTime(gps.getGpsTime().getTime());

		boolean isLoc = gps.isLoc();
		baseBuilder.setLoc(isLoc);
		boolean isBaseStationLoc = gps.isBaseStationLoc();
		if (isLoc) {
			// 卫星定位
			baseBuilder.setLat((int) (gps.getLat() * 1000000));
			baseBuilder.setLng((int) (gps.getLng() * 1000000));
		} else if(isBaseStationLoc){
			//基站定位
			baseBuilder.setLat(0);
			baseBuilder.setLng(0);
			GBossDataBuff.BaseStationAddress.Builder address = GBossDataBuff.BaseStationAddress.newBuilder();
			address.setLat((int) (gps.getLat() * 1000000));
			address.setLng((int) (gps.getLng() * 1000000));
			baseBuilder.setAddress(address);
		}else{
			baseBuilder.setLat(0);
			baseBuilder.setLng(0);
		}
		baseBuilder.setSpeed((int) Math.round(gps.getSpeed() * 10));
		baseBuilder.setCourse(gps.getCourse());

		// status
		baseBuilder.addAllStatus(gps.getStatus());

		return baseBuilder.build();
	}

}