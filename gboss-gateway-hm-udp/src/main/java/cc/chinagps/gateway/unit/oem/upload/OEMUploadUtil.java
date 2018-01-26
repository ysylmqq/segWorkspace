package cc.chinagps.gateway.unit.oem.upload;

import cc.chinagps.gateway.buff.CommandBuff.CommandResponse.Builder;
import cc.chinagps.gateway.buff.GBossDataBuff;
import cc.chinagps.gateway.buff.GBossDataBuff.AlarmInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.oem.pkg.OEMPackage;
import cc.chinagps.gateway.unit.oem.upload.bean.OEMGPSInfo;
import cc.chinagps.gateway.unit.oem.util.OEMProtobufUtil;
import cc.chinagps.gateway.unit.udp.UdpServer;

public class OEMUploadUtil {
	
	/**
	 * ����ָ���Ӧ��GPS����
	 */
	public static void setUpResponseByGPSInfo(String calLetter, Builder builder, OEMGPSInfo gps){
		
		cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo.Builder gpsBuilder = GBossDataBuff.GpsInfo.newBuilder();
		gpsBuilder.setCallLetter(calLetter);
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo baseBuilder = OEMProtobufUtil.transGPSBaseInfo(calLetter, gps);
		if(baseBuilder!=null)
			gpsBuilder.setBaseInfo(baseBuilder);
		builder.addGpsInfos(gpsBuilder);
	}
	
	/**
	 * ������̨�ϴ���GPS��Ϣ
	 */
	public static void handleGPS(UdpServer server, UnitSocketSession session, 
			OEMPackage pkg, OEMGPSInfo gps){
		String callLetter = session.getUnitInfo().getCallLetter();
		
		GpsInfo gpsInfo = OEMProtobufUtil.transformGPSInfo(callLetter, pkg.getSource(), gps);
		GpsBaseInfo gpsBase = gpsInfo.getBaseInfo();
		server.getExportMQ().addGPS(gpsInfo);
		server.getExportHBase().addGPS(callLetter, gpsBase, pkg.getSource());
	}
	
	/**
	 * ������̨�ϴ��ľ�����Ϣ
	 */
	public static void handlerAlarm(UdpServer server,
			UnitSocketSession session, OEMPackage pkg, OEMGPSInfo gps){
		String callLetter = session.getUnitInfo().getCallLetter();
		AlarmInfo alarm = OEMProtobufUtil.transformAlarmInfo(callLetter, pkg.getSource(), gps);
		server.getExportMQ().addCommonAlarm(alarm);
	}
}