package cc.chinagps.gateway.unit.beforemarket.hm.upload;

import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.buff.CommandBuff.CommandResponse.Builder;
import cc.chinagps.gateway.buff.GBossDataBuff.AlarmInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.FaultInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.OBDInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.TravelInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.UnitVersion;
import cc.chinagps.gateway.seat.cmd.CmdResponseUtil;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beforemarket.common.pkg.BeforeMarketPackage;
import cc.chinagps.gateway.unit.beforemarket.common.util.BeforeMarketPkgUtil;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.beans.HMECUConfig;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.beans.HMFaultInfo;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.beans.HMFaultLightInfo;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.beans.HMGPSInfo;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.beans.HMOBDInfo;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.beans.HMStatus;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.beans.HMTravelInfo;
import cc.chinagps.gateway.unit.beforemarket.hm.util.HMProtobufUtil;
import cc.chinagps.gateway.util.Constants;

public class HMUploadUtil {
	/**
	 * ������̨�ϴ���GPS��Ϣ
	 */
	public static void handleGPS(BeforeMarketPackage pkg, UnitServer server,
			UnitSocketSession session, HMGPSInfo gps, HMStatus status, 
			HMOBDInfo obdInfo, HMFaultLightInfo faultLight, boolean isHistory){
		String callLetter = session.getUnitInfo().getCallLetter();
		GpsInfo gpsInfo = HMProtobufUtil.transformGPSInfo(callLetter, pkg, gps, 
				status, obdInfo, faultLight, isHistory);

		handleGPS(pkg, server, session, gpsInfo);
	}
	
	/**
	 * ������̨�ϴ���GPS��Ϣ
	 */
	public static void handleGPS(BeforeMarketPackage pkg, UnitServer server,
			UnitSocketSession session, GpsInfo gpsInfo){
		String callLetter = session.getUnitInfo().getCallLetter();
		GpsBaseInfo gpsBase = gpsInfo.getBaseInfo();
		
		server.getExportMQ().addGPS(gpsInfo);
		server.getExportHBase().addGPS(callLetter, gpsBase, pkg.getSource());
	}
	
	public static int handleAlarm(BeforeMarketPackage pkg, UnitServer server,
			UnitSocketSession session, HMGPSInfo gps, HMStatus status,
			HMOBDInfo obdInfo, HMFaultLightInfo faultLight, boolean isHistory, int trigger){
		int result = 0;
		String callLetter = session.getUnitInfo().getCallLetter();
		AlarmInfo alarmInfo = HMProtobufUtil.transformAlarmInfo(callLetter, pkg, gps, status, 
				obdInfo, faultLight, isHistory, trigger);
		//���鴦��
		if(status != null && status.isSpecialAlarm()){
			//һ�ྯ��
			boolean success = server.getExportMQ().addSpecialAlarm(alarmInfo);
			if(!success){
				result = 0xFFFF;
			}
		}else{
			//����
			server.getExportMQ().addCommonAlarm(alarmInfo);
		}
		
		return result;
	}
	
	/**
	 * ������̨�ϴ���OBD��Ϣ(������Ϣ)
	 */
	public static void handleOBD(UnitServer server, String callLetter, long gpsTime, HMOBDInfo hmOBDInfo, boolean isHistory){
		OBDInfo obdInfo = HMProtobufUtil.transOBDInfo(callLetter, gpsTime, hmOBDInfo, isHistory);
		server.getExportMQ().addOBDInfo(obdInfo);
	}
	
	/**
	 * �����г̼�¼��Ϣ(OBD)
	 */
	public static void handleTravelInfo(UnitServer server, String callLetter, HMTravelInfo travelInfo, 
			HMGPSInfo endGPS, HMStatus endStatus, boolean isHistory){
		TravelInfo value = HMProtobufUtil.transformTravelInfo(callLetter, travelInfo, endGPS, endStatus, isHistory);
		server.getExportMQ().addTravelInfo(value);
	}
	
	/**
	 * ����ģ�������Ϣ(OBD)
	 */
	public static void handleFaultInfo(UnitServer server, String callLetter, HMFaultInfo faultInfo, long gpsTime, boolean isHistory){
		FaultInfo value = HMProtobufUtil.transformFaultInfo(callLetter, faultInfo, gpsTime, isHistory);
		server.getExportMQ().addFaultInfo(value);
	}
	
	/**
	 * �����汾����
	 */
	public static void handleVersion(UnitServer server, String callLetter, String version, int result){
		UnitVersion pversion = HMProtobufUtil.transformVersion(callLetter, version, result);
		server.getExportMQ().addUnitVersion(pversion);
	}
	
	public static void handleVersion(UnitServer server, String callLetter, String version, int result ,int canId){
		UnitVersion pversion = HMProtobufUtil.transformVersion(callLetter, version, result ,canId);
		server.getExportMQ().addUnitVersion(pversion);
	}
	
	/**
	 * ����ECUConfig��Ϣ
	 */
	public static void handleECUConfig(UnitServer server, String callLetter, 
			long updateTime, HMECUConfig ecuConfig){
		cc.chinagps.gateway.buff.GBossDataBuff.ECUConfig pecuConfig = HMProtobufUtil.transformECUConfig(callLetter, updateTime, ecuConfig);
		server.getExportMQ().addECUConfig(pecuConfig);
	}
	
	/**
	 * ͳһ������̨Ӧ��(����head�е�sn)
	 */
//	public static void commonResponseUseHeadSN(BeforeMarketPackage ppkg,
//			UnitServer server, UnitSocketSession session, int unit_ack_result){
//		String callLetter = session.getUnitInfo().getCallLetter();
//		String cacheSN = BeforeMarketPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSn());
//		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);
//		if(cache != null){
//			Command usercmd = cache.getUserCommand();
//			Builder builder = CommandBuff.CommandResponse.newBuilder();
//			builder.setSn(usercmd.getSn());
//			builder.setCallLetter(callLetter);
//			builder.setCmdId(usercmd.getCmdId());
//			if(unit_ack_result == 0){
//				builder.setResult(Constants.RESULT_SUCCESS);
//			}else{
//				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
//				builder.addParams(String.valueOf(unit_ack_result));
//			}
//			
//			byte[] data = builder.build().toByteArray();
//			CmdResponseUtil.simpleCommandResponse(cache, data);
//		}
//	}
	
	/**
	 * �ն�Ӧ������
	 * @param ppkg
	 * @param server
	 * @param session
	 * @param unit_ack_result Ӧ����
	 * @param innerCmdId   0x00A5֪ͨ�ն�����
	 */
	public static void commonResponseUpgrade(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session, int unit_ack_result, int innerCmdId,int canId){
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = BeforeMarketPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSn()); 
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);
		if(cache != null){ 
			Command usercmd = cache.getUserCommand();
			Builder builder = CommandBuff.CommandResponse.newBuilder(); //ָ���Ӧ  
			builder.setSn(usercmd.getSn());
			builder.setCallLetter(callLetter);
			builder.setCmdId(usercmd.getCmdId());
			if(unit_ack_result == 0){
				builder.setResult(Constants.RESULT_SUCCESS);
			}else{
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
				builder.addParams(String.valueOf(canId));
			}
			
			//byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		}else{
			//δ�ҵ����棬�����ǻ��Ѻ������
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn("");
			builder.setCallLetter(callLetter);
			builder.setCmdId(innerCmdId);
			
			if(unit_ack_result == 0){
				builder.setResult(Constants.RESULT_SUCCESS);
			}else{
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
				builder.addParams(String.valueOf(canId));
			}
			
			builder.setUnitsn(ppkg.getHead().getSn() & 0xFFFF);
			//byte[] data = builder.build().toByteArray();
			server.getExportMQ().addCommandResponse(builder.build());
		}
	}
	
	/**
	 * commonResponseUseHeadSN
	 */
	public static void commonResponseUseHeadSN(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session, int unit_ack_result, int innerCmdId){
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
		}else{
			//δ�ҵ����棬�����ǻ��Ѻ������
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn("");
			builder.setCallLetter(callLetter);
			builder.setCmdId(innerCmdId);
			
			if(unit_ack_result == 0){
				builder.setResult(Constants.RESULT_SUCCESS);
			}else{
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}
			
			builder.setUnitsn(ppkg.getHead().getSn() & 0xFFFF);
			//byte[] data = builder.build().toByteArray();
			server.getExportMQ().addCommandResponse(builder.build());
		}
	}
}