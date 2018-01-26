package cc.chinagps.gateway.unit.beforemarket.kaiyi.upload.cmd;

import java.io.IOException;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.aplan.pkg.APlanUtil;
import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.buff.CommandBuff.CommandResponse.Builder;
import cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo;
import cc.chinagps.gateway.seat.cmd.CmdResponseUtil;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beforemarket.common.CheckLoginHandler;
import cc.chinagps.gateway.unit.beforemarket.common.pkg.BeforeMarketPackage;
import cc.chinagps.gateway.unit.beforemarket.common.util.BeforeMarketPkgUtil;
import cc.chinagps.gateway.unit.beforemarket.kaiyi.upload.KaiyiUploadUtil;
import cc.chinagps.gateway.unit.beforemarket.kaiyi.upload.beans.KaiyiGPSInfo;
import cc.chinagps.gateway.unit.beforemarket.kaiyi.upload.beans.KaiyiStatus;
import cc.chinagps.gateway.unit.beforemarket.kaiyi.util.KaiyiProtobufUtil;
import cc.chinagps.gateway.util.Constants;

/**
 * 查询指令应答
 */
public class Cmd03 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session) throws Exception {
		byte[] body = ppkg.getBody();
		int subCmdId = body[0] & 0xFF;
		
		if(subCmdId == 0x01){
			//查询网络参数应答
			queryNetSettingAck(ppkg, server, session);
			return true;
		}
		
		if(subCmdId == 0x2){
			//查询呼叫中心号码应答
			queryTelAck(ppkg, server, session);
			return true;
		}
		
		if(subCmdId == 0x3){
			//查询短信业务中心号码应答
			querySMCenterAck(ppkg, server, session);
			return true;
		}
		
		if(subCmdId == 0x05){
			//查询故障/救援服务号码应答
			queryTelAck(ppkg, server, session);
			return true;
		}
		
		if(subCmdId == 0x12){
			//查询定时上报参数应答
			queryUploadParamAck(ppkg, server, session);
			return true;
		}
		
		if(subCmdId == 0x13){
			//查询ACC变化是否上报应答
			ackOneByte(ppkg, server, session);
			return true;
		}
		
		if(subCmdId == 0x16){
			//查询车身状态变化是否上报应答
			ackOneByte(ppkg, server, session);
			return true;
		}
		
		if(subCmdId == 0x21){
			//查车应答
			queryPositionAck(ppkg, server, session);
			return true;
		}
		
		return false;
	}
	
	/**
	 * 查询网络参数应答
	 * @throws IOException 
	 */
	private void queryNetSettingAck(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session) throws IOException{
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = BeforeMarketPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSn());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);
		
		if(cache != null){
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			//make protobuf
			Command usercmd = cache.getUserCommand();
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn(usercmd.getSn());
			builder.setCallLetter(callLetter);
			builder.setCmdId(usercmd.getCmdId());
			if(unit_ack_result == 0){
				builder.setResult(Constants.RESULT_SUCCESS);
				
				int startPosition = 3;
				String ip = cc.chinagps.gateway.util.Util.getIP(body, startPosition);
				int port = Util.getShort(body, startPosition + 4) & 0xFFFF;
				int interval = Util.getShort(body, startPosition + 6) & 0xFFFF;
				String apn = APlanUtil.getCString(body, startPosition + 8, body.length - 11);
				
				builder.addParams("1");
				builder.addParams(apn);
				builder.addParams(ip);
				builder.addParams(String.valueOf(port));
				builder.addParams("");
				builder.addParams("");
				builder.addParams("");
				builder.addParams(String.valueOf(interval));
			}else{
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}
			
			builder.setUnitsn(ppkg.getHead().getSn() & 0xFFFF);
			//byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		}else{
			//未找到缓存，可能是唤醒后的数据
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			int subCmdId = body[0] & 0xFF;
			//make protobuf
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn("");
			builder.setCallLetter(callLetter);
			builder.setCmdId(getInnerCmdId(subCmdId));
			if(unit_ack_result == 0){
				builder.setResult(Constants.RESULT_SUCCESS);
				
				int startPosition = 3;
				String ip = cc.chinagps.gateway.util.Util.getIP(body, startPosition);
				int port = Util.getShort(body, startPosition + 4) & 0xFFFF;
				int interval = Util.getShort(body, startPosition + 6) & 0xFFFF;
				String apn = APlanUtil.getCString(body, startPosition + 8, body.length - 11);
				
				builder.addParams("1");
				builder.addParams(apn);
				builder.addParams(ip);
				builder.addParams(String.valueOf(port));
				builder.addParams("");
				builder.addParams("");
				builder.addParams("");
				builder.addParams(String.valueOf(interval));
			}else{
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}
			
			//byte[] data = builder.build().toByteArray();
			server.getExportMQ().addCommandResponse(builder.build());
		}
	}
	
	/**
	 * 查询号码应答
	 * @throws IOException 
	 */
	private void queryTelAck(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session) throws IOException{
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = BeforeMarketPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSn());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);
		
		if(cache != null){
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			//make protobuf
			Command usercmd = cache.getUserCommand();
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn(usercmd.getSn());
			builder.setCallLetter(callLetter);
			builder.setCmdId(usercmd.getCmdId());
			if(unit_ack_result == 0){
				builder.setResult(Constants.RESULT_SUCCESS);
				
				int startPosition = 3;
				String call = APlanUtil.getCString(body, startPosition, 16);
				
				builder.addParams(call);
			}else{
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}
			
			//byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		}else{
			//未找到缓存，可能是唤醒后的数据
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			int subCmdId = body[0] & 0xFF;
			
			//make protobuf
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn("");
			builder.setCallLetter(callLetter);
			builder.setCmdId(getInnerCmdId(subCmdId));
			if(unit_ack_result == 0){
				builder.setResult(Constants.RESULT_SUCCESS);
				
				int startPosition = 3;
				String call = APlanUtil.getCString(body, startPosition, 16);
				
				builder.addParams(call);
			}else{
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}
			
			builder.setUnitsn(ppkg.getHead().getSn() & 0xFFFF);
			//byte[] data = builder.build().toByteArray();
			server.getExportMQ().addCommandResponse(builder.build());
		}
	}
	
	/**
	 * 查询短信业务中心号码应答
	 * @throws IOException 
	 */
	private void querySMCenterAck(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session) throws IOException{
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = BeforeMarketPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSn());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);
		
		if(cache != null){
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			//make protobuf
			Command usercmd = cache.getUserCommand();
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn(usercmd.getSn());
			builder.setCallLetter(callLetter);
			builder.setCmdId(usercmd.getCmdId());
			if(unit_ack_result == 0){
				builder.setResult(Constants.RESULT_SUCCESS);
				
				int startPosition = 3;
				String call1 = APlanUtil.getCString(body, startPosition, 25);
				startPosition += 25;
				String call2 = APlanUtil.getCString(body, startPosition, 25);
				
				builder.addParams(call1);
				builder.addParams(call2);
			}else{
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}
			
			//byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		}else{
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			int subCmdId = body[0] & 0xFF;
			//make protobuf
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn("");
			builder.setCallLetter(callLetter);
			builder.setCmdId(getInnerCmdId(subCmdId));
			if(unit_ack_result == 0){
				builder.setResult(Constants.RESULT_SUCCESS);
				
				int startPosition = 3;
				String call1 = APlanUtil.getCString(body, startPosition, 25);
				startPosition += 25;
				String call2 = APlanUtil.getCString(body, startPosition, 25);
				
				builder.addParams(call1);
				builder.addParams(call2);
			}else{
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}
			
			builder.setUnitsn(ppkg.getHead().getSn() & 0xFFFF);
			//byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
			server.getExportMQ().addCommandResponse(builder.build());
		}
	}
	
	/**
	 * 查询定时上报参数应答
	 */
	private void queryUploadParamAck(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session){
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = BeforeMarketPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSn());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);
		
		if(cache != null){
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			//make protobuf
			Command usercmd = cache.getUserCommand();
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn(usercmd.getSn());
			builder.setCallLetter(callLetter);
			builder.setCmdId(usercmd.getCmdId());
			if(unit_ack_result == 0){
				builder.setResult(Constants.RESULT_SUCCESS);
				
				int startPosition = 3;
				int interval = Util.getShort(body, startPosition) & 0xFFFF;
				startPosition += 2;
				int count = Util.getShort(body, startPosition) & 0xFFFF;
				
				builder.addParams(String.valueOf(interval));
				builder.addParams(String.valueOf(count));
			}else{
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}
			
			//byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		}else{
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			//make protobuf
			int subCmdId = body[0] & 0xFF;
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn("");
			builder.setCallLetter(callLetter);
			builder.setCmdId(getInnerCmdId(subCmdId));
			
			if(unit_ack_result == 0){
				builder.setResult(Constants.RESULT_SUCCESS);
				
				int startPosition = 3;
				int interval = Util.getShort(body, startPosition) & 0xFFFF;
				startPosition += 2;
				int count = Util.getShort(body, startPosition) & 0xFFFF;
				
				builder.addParams(String.valueOf(interval));
				builder.addParams(String.valueOf(count));
			}else{
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}
			
			builder.setUnitsn(ppkg.getHead().getSn() & 0xFFFF);
			//byte[] data = builder.build().toByteArray();
			server.getExportMQ().addCommandResponse(builder.build());
		}
	}
	
	/**
	 * 应答1byte
	 */
	private static void ackOneByte(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session){
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = BeforeMarketPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSn());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);
		
		if(cache != null){
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			//make protobuf
			Command usercmd = cache.getUserCommand();
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn(usercmd.getSn());
			builder.setCallLetter(callLetter);
			builder.setCmdId(usercmd.getCmdId());
			if(unit_ack_result == 0){
				builder.setResult(Constants.RESULT_SUCCESS);
				
				int startPosition = 3;
				int upload = body[startPosition] & 0xFF;
				builder.addParams(String.valueOf(upload));
			}else{
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}
			
			//byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		}else{
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			//make protobuf
			int subCmdId = body[0] & 0xFF;
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn("");
			builder.setCallLetter(callLetter);
			builder.setCmdId(getInnerCmdId(subCmdId));
			
			if(unit_ack_result == 0){
				builder.setResult(Constants.RESULT_SUCCESS);
				
				int startPosition = 3;
				int upload = body[startPosition] & 0xFF;
				builder.addParams(String.valueOf(upload));
			}else{
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}
			
			builder.setUnitsn(ppkg.getHead().getSn() & 0xFFFF);
			//byte[] data = builder.build().toByteArray();
			server.getExportMQ().addCommandResponse(builder.build());
		}
	}
	
	/**
	 * 查车应答
	 * @throws Exception 
	 */
	private void queryPositionAck(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session) throws Exception{
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = BeforeMarketPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSn());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);
		
		if(cache != null){
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			//make protobuf
			Command usercmd = cache.getUserCommand();
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn(usercmd.getSn());
			builder.setCallLetter(callLetter);
			builder.setCmdId(usercmd.getCmdId());
			if(unit_ack_result == 0){
				builder.setResult(Constants.RESULT_SUCCESS);
				
				int startPosition = 3;
				KaiyiGPSInfo gps = KaiyiGPSInfo.parse(body, startPosition);
				KaiyiStatus status = KaiyiStatus.parse(body, startPosition + KaiyiGPSInfo.DATA_LENGTH);
				
				GpsInfo pgps = KaiyiProtobufUtil.transformGPSInfo(callLetter, ppkg, gps, status, null, false);
				builder.addGpsInfos(pgps);
				
				//保存gps
				KaiyiUploadUtil.handleGPS(ppkg, server, session, pgps);
			}else{
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}
			
			//byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		}else{
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			//make protobuf
			int subCmdId = body[0] & 0xFF;
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn("");
			builder.setCallLetter(callLetter);
			builder.setCmdId(getInnerCmdId(subCmdId));
			
			if(unit_ack_result == 0){
				builder.setResult(Constants.RESULT_SUCCESS);
				
				int startPosition = 3;
				KaiyiGPSInfo gps = KaiyiGPSInfo.parse(body, startPosition);
				KaiyiStatus status = KaiyiStatus.parse(body, startPosition + KaiyiGPSInfo.DATA_LENGTH);
				
				GpsInfo pgps = KaiyiProtobufUtil.transformGPSInfo(callLetter, ppkg, gps, status, null, false);
				builder.addGpsInfos(pgps);
				
				//保存gps
				KaiyiUploadUtil.handleGPS(ppkg, server, session, pgps);
			}else{
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}
			
			builder.setUnitsn(ppkg.getHead().getSn() & 0xFFFF);
			server.getExportMQ().addCommandResponse(builder.build());
		}		
	}
	
	private static int getInnerCmdId(int subCmdId){
		switch (subCmdId) {
			case 0x1:		
				return 0x0058;
			case 0x2:
				return 0x0027;
			case 0x3:
				return 0x0030;
			case 0x5:
				return 0x00A7;
			case 0x12:
				return 0x0070;
			case 0x13:	
				return 0x0071;
			case 0x16:
				return 0x0074;
			case 0x21:
				return 0x0001;
			default:
				return 0;
		}
	}
}