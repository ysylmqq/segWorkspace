package cc.chinagps.gateway.unit.leopaard.util;

import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.buff.CommandBuff.CommandResponse.Builder;
import cc.chinagps.gateway.buff.GBossDataBuff.FaultInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.OBDInfo;
import cc.chinagps.gateway.seat.cmd.CmdResponseUtil;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beforemarket.common.pkg.BeforeMarketPackage;
import cc.chinagps.gateway.unit.beforemarket.common.util.BeforeMarketPkgUtil;
import cc.chinagps.gateway.unit.leopaard.pkg.LeopaardPackage;
import cc.chinagps.gateway.unit.leopaard.upload.bean.LeopaardFaultInfo;
import cc.chinagps.gateway.unit.leopaard.upload.bean.LeopaardGPSInfo;
import cc.chinagps.gateway.unit.leopaard.upload.bean.LeopaardOBDInfo;
import cc.chinagps.gateway.util.Constants;

public class LeopaardUploadUtil {
	
	/**
	 * 处理车台上传的GPS信息
	 */
	public static void handleGPS(LeopaardPackage pkg, UnitServer server,
			UnitSocketSession session, LeopaardGPSInfo gps,boolean isHistory){
		String callLetter = session.getUnitInfo().getCallLetter();
		GpsInfo gpsInfo = LeopaardProtobufUtil.transformGPSInfo(callLetter, pkg, gps, 
				gps.getLeopaardStatusInfo(), gps.getLeopaardOBDInfo(), gps.getLeopaardFaultLightInfo(), isHistory);

		handleGPS(pkg, server, session, gpsInfo);
	}
	
	/**
	 * 处理车台上传的GPS信息
	 */
	public static void handleGPS(LeopaardPackage pkg, UnitServer server,
			UnitSocketSession session, GpsInfo gpsInfo){
		String callLetter = session.getUnitInfo().getCallLetter();
		GpsBaseInfo gpsBase = gpsInfo.getBaseInfo();
		
		server.getExportMQ().addGPS(gpsInfo);
		server.getExportHBase().addGPS(callLetter, gpsBase, pkg.getSource());
	}
	
	/**
	 * 处理车台上传的OBD信息(车况信息)
	 */
	public static void handleOBD(UnitServer server, String callLetter, long gpsTime, LeopaardOBDInfo OBDInfo, boolean isHistory){
		OBDInfo obdInfo = LeopaardProtobufUtil.transOBDInfo(callLetter, gpsTime, OBDInfo, isHistory);
		server.getExportMQ().addOBDInfo(obdInfo);
	}
	
	
	/**
	 * 处理模块故障信息(OBD)
	 */
	public static void handleFaultInfo(UnitServer server, String callLetter,  LeopaardFaultInfo faultInfo, long gpsTime, boolean isHistory){
		FaultInfo value = LeopaardProtobufUtil.transformFaultInfo(callLetter, faultInfo, gpsTime, isHistory);
		server.getExportMQ().addFaultInfo(value);
	}
	
	/**
	 * commonResponseUseHeadSN
	 */
	public static void commonResponseUseHeadSN(BeforeMarketPackage ppkg, UnitServer server, UnitSocketSession session,
			int unit_ack_result, int innerCmdId) {
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = BeforeMarketPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSn());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);
		if (cache != null) {
			Command usercmd = cache.getUserCommand();
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn(usercmd.getSn());
			builder.setCallLetter(callLetter);
			builder.setCmdId(usercmd.getCmdId());
			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);
			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			CmdResponseUtil.simpleCommandResponse(cache, builder);
		} else {
			// 未找到缓存，可能是唤醒后的数据
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn("");
			builder.setCallLetter(callLetter);
			builder.setCmdId(innerCmdId);

			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);
			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			builder.setUnitsn(ppkg.getHead().getSn() & 0xFFFF);
			server.getExportMQ().addCommandResponse(builder.build());
		}
	}

	public static byte[] getCommonAck(LeopaardPackage pkg, byte cmdId, byte responseFlag, byte[] responseData)
			throws Exception {
		LeopaardPackage rpkg = new LeopaardPackage();
		rpkg.setHead(pkg.getHead());
		if (responseData != null)
			rpkg.getHead().setDataUnitLen(responseData.length);
		rpkg.getHead().setResponseFlag(responseFlag);
		rpkg.getHead().setCmdId(cmdId);
		rpkg.setDataUnit(responseData);
		return rpkg.encode();
	}

	public static byte[] getCommonAck(LeopaardPackage pkg, byte responseFlag, byte[] responseData) throws Exception {
		LeopaardPackage rpkg = new LeopaardPackage();
		rpkg.setHead(pkg.getHead());
		if (responseData != null)
			rpkg.getHead().setDataUnitLen(responseData.length);
		rpkg.getHead().setResponseFlag(responseFlag);
		rpkg.getHead().setCmdId(pkg.getHead().getCmdId());
		rpkg.setDataUnit(responseData);
		return rpkg.encode();
	}

	public static byte[] getCommonAck(LeopaardPackage pkg, byte responseFlag) throws Exception {
		LeopaardPackage rpkg = new LeopaardPackage();
		rpkg.setHead(pkg.getHead());
		rpkg.getHead().setResponseFlag(responseFlag);
		rpkg.getHead().setCmdId(pkg.getHead().getCmdId());
		return rpkg.encode();
	}

	public static void commonAck(UnitSocketSession session, LeopaardPackage pkg, byte responseFlag) throws Exception {
		byte response = pkg.getHead().getResponseFlag();
		if (response == (byte) 0xFE) {
			session.sendData(LeopaardUploadUtil.getCommonAck(pkg, responseFlag));
		}
	}

	public static void commonAck(UnitSocketSession session, LeopaardPackage pkg, byte responseFlag, byte[] responseData)
			throws Exception {
		byte response = pkg.getHead().getResponseFlag();
		if (response == (byte) 0xFE) {
			session.sendData(LeopaardUploadUtil.getCommonAck(pkg, responseFlag, responseData));
		}
	}

	public static void commonSuccessAck(UnitSocketSession session, LeopaardPackage pkg) throws Exception {
		byte response = pkg.getHead().getResponseFlag();
		if (response == (byte) 0xFE) {
			session.sendData(LeopaardUploadUtil.getCommonAck(pkg, (byte) 0x01));
		}
	}
	
	public static void commonFailedAck(UnitSocketSession session, LeopaardPackage pkg) throws Exception {
		byte response = pkg.getHead().getResponseFlag();
		if (response == (byte) 0xFE) {
			session.sendData(LeopaardUploadUtil.getCommonAck(pkg, (byte) 0x02));
		}
	}
}