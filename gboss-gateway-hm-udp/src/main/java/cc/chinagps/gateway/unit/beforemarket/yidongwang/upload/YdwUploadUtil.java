package cc.chinagps.gateway.unit.beforemarket.yidongwang.upload;

import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.buff.CommandBuff.CommandResponse.Builder;
import cc.chinagps.gateway.buff.GBossDataBuff.AlarmInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo;
import cc.chinagps.gateway.seat.cmd.CmdResponseUtil;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beans.BaseStationInfo;
import cc.chinagps.gateway.unit.beforemarket.common.pkg.BeforeMarketPackage;
import cc.chinagps.gateway.unit.beforemarket.common.util.BeforeMarketPkgUtil;
import cc.chinagps.gateway.unit.beforemarket.yidongwang.upload.beans.YdwGPSInfo;
import cc.chinagps.gateway.unit.beforemarket.yidongwang.upload.beans.YdwInfo;
import cc.chinagps.gateway.unit.beforemarket.yidongwang.upload.beans.YdwStatus;
import cc.chinagps.gateway.unit.beforemarket.yidongwang.util.YdwProtobufUtil;
import cc.chinagps.gateway.util.Constants;

public class YdwUploadUtil {
	/**
	 * 处理车台上传的GPS信息
	 */
	public static void handleGPS(BeforeMarketPackage pkg, UnitServer server,
			UnitSocketSession session, YdwGPSInfo gps, YdwStatus status, 
			BaseStationInfo baseStationInfo, YdwInfo ydwInfo,  boolean isHistory){
		String callLetter = session.getUnitInfo().getCallLetter();
		GpsInfo gpsInfo = YdwProtobufUtil.transformGPSInfo(callLetter, pkg, gps, status, 
				baseStationInfo, ydwInfo, isHistory);
		//GpsBaseInfo gpsBase = gpsInfo.getBaseInfo();
		//server.getExportMQ().addGPS(callLetter, gpsInfo);
		//server.getExportHBase().addGPS(callLetter, gpsBase, pkg.getSource());
		handleGPS(pkg, server, session, gpsInfo);
	}
	
	/**
	 * 处理车台上传的GPS信息
	 */
	public static void handleGPS(BeforeMarketPackage pkg, UnitServer server,
			UnitSocketSession session, GpsInfo gpsInfo){
		String callLetter = session.getUnitInfo().getCallLetter();
		GpsBaseInfo gpsBase = gpsInfo.getBaseInfo();
		
		server.getExportMQ().addGPS(gpsInfo);
		server.getExportHBase().addGPS(callLetter, gpsBase, pkg.getSource());
	}
	
	public static int handleAlarm(BeforeMarketPackage pkg, UnitServer server,
			UnitSocketSession session, YdwGPSInfo gps, YdwStatus status,
			BaseStationInfo baseStationInfo, YdwInfo ydwInfo, 
			boolean isHistory, int trigger){
		int result = 0;
		String callLetter = session.getUnitInfo().getCallLetter();
		AlarmInfo alarmInfo = YdwProtobufUtil.transformAlarmInfo(callLetter, pkg, gps, status, 
				baseStationInfo, ydwInfo, isHistory, trigger);
		//警情处理
		if(status != null && status.isSpecialAlarm()){
			//一类警情
			boolean success = server.getExportMQ().addSpecialAlarm(alarmInfo);
			if(!success){
				result = 0xFFFF;
			}
		}else{
			//警情
			server.getExportMQ().addCommonAlarm(alarmInfo);
		}
		
		return result;
	}
	
	/**
	 * 统一处理车台应答(根据head中的sn)
	 */
	public static void commonResponseUseHeadSN(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session, int unit_ack_result){
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = BeforeMarketPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSn());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);
		if(cache != null){
			Command usercmd = cache.getUserCommand();
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn(usercmd.getSn());
			builder.setCallLetter(callLetter);
			builder.setCmdId(usercmd.getCmdId());
			if(unit_ack_result == 0){
				builder.setResult(Constants.RESULT_SUCCESS);
			}else{
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}
			
			//byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		}
	}
}