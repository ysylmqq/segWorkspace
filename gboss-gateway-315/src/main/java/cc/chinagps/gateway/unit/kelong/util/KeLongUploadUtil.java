package cc.chinagps.gateway.unit.kelong.util;

import org.apache.log4j.Logger;
import org.seg.lib.util.Util;

import cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.OBDInfo;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beans.GbossGpsInfo;
import cc.chinagps.gateway.unit.common.BaseStationManager;
import cc.chinagps.gateway.unit.kelong.pkg.KeLongPackage;
import cc.chinagps.gateway.unit.kelong.upload.bean.KeLongGPSInfo;
import cc.chinagps.gateway.unit.kelong.upload.bean.KeLongOBDInfo;
import cc.chinagps.gateway.util.UnitProtocolTypes;
import cc.chinagps.gateway.log.LogManager;

public class KeLongUploadUtil {
	private static Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	/**
	 * 处理车台上传的GPS信息
	 */
	public static void handleGPS(KeLongPackage pkg, UnitServer server, UnitSocketSession session, KeLongGPSInfo gps,
			boolean isHistory) {
		String callLetter = session.getUnitInfo().getCallLetter();
		GpsInfo.Builder builder = KeLongProtobufUtil.transformGPSInfoBuilder(callLetter, pkg, gps, gps.getKeLongOBDInfo(),
				gps.getKeLongExtendInfo(),gps.getKeLongBaseStationInfo(), isHistory);
		GpsInfo gpsInfo = builder.build();
		if(!gps.isLoc()){//不定位或者基站信息转化的gps信息处理
			logger_debug.debug("[KeLong]KeLongUploadUtil-->handleGPS:" + gps);
			gps.setSpeed(gps.getKeLongOBDInfo().getSpeed());//将gps的速度补为obd的速度
			BaseStationManager.getInstance().addBaseStationGps(new GbossGpsInfo(builder, gpsInfo, UnitProtocolTypes.UNIT_PROTOCOL_TYPE_KELONG));			
		}else{
			handleGPS(pkg, server, session, gpsInfo);
		}		
	}
	
	/**
	 * 处理车台上传的GPS信息
	 */
	public static void handleGPS(KeLongPackage pkg, UnitServer server, UnitSocketSession session, GpsInfo gpsInfo) {
		String callLetter = session.getUnitInfo().getCallLetter();
		GpsBaseInfo gpsBase = gpsInfo.getBaseInfo();

		server.getExportMQ().addGPS(gpsInfo);
		server.getExportHBase().addGPS(callLetter, gpsBase, pkg.getSource());
	}

	/**
	 * 处理车台上传的OBD信息(车况信息)
	 */
	public static void handleOBD(UnitServer server, String callLetter, long gpsTime, KeLongOBDInfo OBDInfo,
			boolean isHistory) {
		OBDInfo obdInfo = KeLongProtobufUtil.transOBDInfo(callLetter, gpsTime, OBDInfo, isHistory);
		server.getExportMQ().addOBDInfo(obdInfo);
	}
	

	public static void msgAck(UnitSocketSession session, KeLongPackage pkg, short msgId, byte[] responseData)
			throws Exception {
		// TODO Auto-generated method stub
		KeLongPackage rpkg = new KeLongPackage();
		rpkg.setHead(pkg.getHead());
		if (responseData != null) {
			rpkg.getHead().setMsgLen((short) (responseData.length + 11));
			rpkg.getHead().setDataLen((short) responseData.length);
		}

		rpkg.getHead().setMsgId((short) (msgId & 0xFFFF));
		rpkg.setData(responseData);
		session.sendData(rpkg.encode());
	}

	public static void commonMsgAck(UnitSocketSession session, KeLongPackage pkg, short msgId, short responseMsgId,
			byte result) throws Exception {
		// TODO Auto-generated method stub
		KeLongPackage rpkg = new KeLongPackage();
		rpkg.setHead(pkg.getHead());

		byte[] responseData = new byte[3];
		System.arraycopy(Util.getShortByte(responseMsgId), 0, responseData, 0, 2);
		responseData[2] = result;
		rpkg.getHead().setDataLen((short) responseData.length);
		rpkg.getHead().setMsgLen((short) (responseData.length + 11));
		rpkg.getHead().setMsgId((short) (msgId & 0xFFFF));
		rpkg.setData(responseData);
		session.sendData(rpkg.encode());
	}
}