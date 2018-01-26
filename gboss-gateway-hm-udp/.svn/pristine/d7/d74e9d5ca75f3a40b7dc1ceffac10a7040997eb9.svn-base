package cc.chinagps.gateway.unit.db44.upload;

import org.apache.log4j.Logger;

import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.buff.GBossDataBuff;
import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.buff.CommandBuff.CommandResponse.Builder;
import cc.chinagps.gateway.buff.GBossDataBuff.AlarmInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.seat.cmd.CmdResponseUtil;
import cc.chinagps.gateway.seat.cmd.CmdUtil;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.db44.pkg.DB44Package;
import cc.chinagps.gateway.unit.db44.util.DB44ProtobufUtil;
import cc.chinagps.gateway.util.HexUtil;

public class DB44UploadUtil {
	private static Logger logger_alarm = Logger.getLogger(LogManager.LOGGER_NAME_ALARM);
	
	/**
	 * 处理车台上传的短消息
	 */
	public static void handleUploadShortMessage(DB44Package pkg, UnitServer server,
			UnitSocketSession session, String shortMessage){
		server.getExportMQ().addShortMessage(session.getUnitInfo().getCallLetter(), shortMessage);
	}
	
	/**
	 * 处理车台上传的GPS信息
	 */
	public static void handleGPS(DB44Package pkg, UnitServer server,
			UnitSocketSession session, DB44GPSInfo gps){
		String callLetter = session.getUnitInfo().getCallLetter();
		GpsInfo gpsInfo = DB44ProtobufUtil.transformGPSInfo(callLetter, gps, pkg);
		GpsBaseInfo gpsBase = gpsInfo.getBaseInfo();
		
		server.getExportMQ().addGPS(gpsInfo);
		server.getExportHBase().addGPS(callLetter, gpsBase, pkg.getSource());
	}
	
	/**
	 * 处理车台上传的警情信息
	 */
	public static void handlerAlarm(DB44Package pkg, UnitServer server,
			UnitSocketSession session, DB44GPSInfo gps){
		String callLetter = session.getUnitInfo().getCallLetter();
		if(gps.isSpecialAlarm()){
			//一类警情
			//GpsInfo specialAlarm = DB44ProtobufUtil.transformGPSInfo(callLetter, gps, pkg);
			AlarmInfo specialAlarm = DB44ProtobufUtil.transformAlarmInfo(callLetter, gps, pkg);
			boolean success = server.getExportMQ().addSpecialAlarm(specialAlarm);
			
			if(success){
				//回应车台
				logger_alarm.info("(" + callLetter + ")" + HexUtil.byteToHexStr(pkg.getSource()));
			}
		}else{
			//GpsInfo commonAlarm = DB44ProtobufUtil.transformGPSInfo(callLetter, gps, pkg);
			AlarmInfo commonAlarm = DB44ProtobufUtil.transformAlarmInfo(callLetter, gps, pkg);
			server.getExportMQ().addCommonAlarm(commonAlarm);
		}
	}
	
	/**
	 * 通用带GPS应答
	 * @throws Exception 
	 */
	public static void commonResponseWithGPS(DB44Package pkg, UnitServer server,
			UnitSocketSession session, int cmdId) throws Exception{
		byte[] protocol = pkg.getProtocol();
		DB44GPSInfo gps = DB44GPSInfo.parse(protocol, 0);
		
		String callLetter = session.getUnitInfo().getCallLetter();
		String sn = CmdUtil.getCmdCacheSn(callLetter, cmdId);
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(sn);
		if(cache == null){
			//debug
		}else{
			Command usercmd = cache.getUserCommand();
			
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			//设置回应的通用部分
			CmdResponseUtil.updateResponseProtoSuccessByUserCommand(builder, callLetter, usercmd);
			//gpsInfo
			setUpResponseByGPSInfo(builder, gps);
			
			//byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		}
		
		DB44UploadUtil.handleGPS(pkg, server, session, gps);
	}
	
	public static void setUpResponseByGPSInfo(Builder builder, DB44GPSInfo gps){
		/*
		builder.addParams(CmdResponseUtil.formatToLocal(gps.getGpsTime()));
		builder.addParams(String.valueOf(gps.isLoc()));
		builder.addParams(String.valueOf(gps.getLat()));
		builder.addParams(String.valueOf(gps.getLng()));
		builder.addParams(String.valueOf(gps.getSpeed()));
		builder.addParams(String.valueOf(gps.getCourse()));
		builder.addParams(gps.getStatus().toString());
		builder.addParams("");//totalDistance
		builder.addParams("");//oilPercent
		builder.addParams("");//temperature1
		builder.addParams("");//temperature2
		//no appendParams
		 */
		//GpsBaseInfo base = DB44ProtobufUtil.transGPSBaseInfo(gps);
		//builder.addGpsInfos(base);
		
		cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo.Builder gpsBuilder = GBossDataBuff.GpsInfo.newBuilder();
		gpsBuilder.setCallLetter("");
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo baseBuilder = DB44ProtobufUtil.transGPSBaseInfo(gps);
		gpsBuilder.setBaseInfo(baseBuilder);

		builder.addGpsInfos(gpsBuilder);
	}
}