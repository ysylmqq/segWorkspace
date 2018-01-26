package cc.chinagps.gateway.unit.beforemarket.yidongwang.upload.cmd;

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
import cc.chinagps.gateway.unit.beforemarket.yidongwang.upload.YdwUploadUtil;
import cc.chinagps.gateway.unit.beforemarket.yidongwang.upload.beans.YdwGPSInfo;
import cc.chinagps.gateway.unit.beforemarket.yidongwang.upload.beans.YdwStatus;
import cc.chinagps.gateway.unit.beforemarket.yidongwang.util.YdwConstants;
import cc.chinagps.gateway.unit.beforemarket.yidongwang.util.YdwProtobufUtil;
import cc.chinagps.gateway.util.Constants;

/**
 * 查询指令应答
 */
public class Cmd03 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session) throws Exception {
		byte[] body = ppkg.getBody();
		int sumCmdId = body[0] & 0xFF;
		
		if(sumCmdId == 0x01){
			//查询网络参数
			queryNetSettingAck(ppkg, server, session);
			return true;
		}
		
		if(sumCmdId == 0x2){
			//查询呼叫中心号码
			queryTelAck(ppkg, server, session);
			return true;
		}
		
		if(sumCmdId == 0x3){
			//查询短信业务中心号码
			querySMCenterAck(ppkg, server, session);
			return true;
		}
		
		if(sumCmdId == 0x12){
			//查询定时上报参数
			queryUploadParamAck(ppkg, server, session);
			return true;
		}
		
		if(sumCmdId == 0x21){
			//查车
			queryPositionAck(ppkg, server, session);
			return true;
		}
		
		//一动网增加
		if(sumCmdId == 0x18){
			//查询超速参数
			queryOverSpeedParamAck(ppkg, server, session);
			return true;
		}
		
		if(sumCmdId == 0x19){
			//查询用户密码
			queryUserPasswordAck(ppkg, server, session);
			return true;
		}
		
		if(sumCmdId == 0x20){
			//查询报警手机号码
			queryAlarmTelAck(ppkg, server, session);
			return true;
		}
		
		if(sumCmdId == 0x22){
			//查询一动网报警参数
			queryAlarmParamAck(ppkg, server, session);
			return true;
		}
		
		return false;
	}
	
	/**
	 * 查询超速参数应答
	 */
	private void queryOverSpeedParamAck(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session){
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = BeforeMarketPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSn());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);
		
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
			int speed = body[startPosition] & 0xFF;
			int time = body[startPosition + 1] & 0xFF;
			builder.addParams(String.valueOf(speed));
			builder.addParams(String.valueOf(time));
		}else{
			builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
			builder.addParams(String.valueOf(unit_ack_result));
		}
		
		//byte[] data = builder.build().toByteArray();
		CmdResponseUtil.simpleCommandResponse(cache, builder);
	}
	
	/**
	 * 查询用户密码应答
	 * @throws IOException 
	 */
	private void queryUserPasswordAck(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session) throws IOException{
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = BeforeMarketPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSn());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);
		
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
			String pwd = APlanUtil.getCString(body, startPosition, 10, YdwConstants.CN_CHAR_ENCODING);
			builder.addParams(pwd);
		}else{
			builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
			builder.addParams(String.valueOf(unit_ack_result));
		}
		
		//byte[] data = builder.build().toByteArray();
		CmdResponseUtil.simpleCommandResponse(cache, builder);
	}
	
	/**
	 * 查询报警手机号参数
	 * @throws IOException 
	 */
	private void queryAlarmTelAck(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session) throws IOException{
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = BeforeMarketPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSn());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);
		
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
			String tel1 = APlanUtil.getCString(body, startPosition, 16);
			String tel2 = APlanUtil.getCString(body, startPosition + 16, 16);
			String tel3 = APlanUtil.getCString(body, startPosition + 32, 16);
			
			builder.addParams(tel1);
			builder.addParams(tel2);
			builder.addParams(tel3);
		}else{
			builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
			builder.addParams(String.valueOf(unit_ack_result));
		}
		
		//byte[] data = builder.build().toByteArray();
		CmdResponseUtil.simpleCommandResponse(cache, builder);
	}
	
	/**
	 * 查询一动网报警参数
	 * @throws IOException 
	 */
	private void queryAlarmParamAck(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session) throws IOException{
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = BeforeMarketPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSn());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);
		
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
			int door = body[startPosition] & 0xFF;
			int type = body[startPosition + 1] & 0xFF;
			builder.addParams(String.valueOf(door));
			builder.addParams(String.valueOf(type));
		}else{
			builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
			builder.addParams(String.valueOf(unit_ack_result));
		}
		
		//byte[] data = builder.build().toByteArray();
		CmdResponseUtil.simpleCommandResponse(cache, builder);
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
				
				builder.addParams("");
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
			CmdResponseUtil.simpleCommandResponse(cache, builder);
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
				YdwGPSInfo gps = YdwGPSInfo.parse(body, startPosition);
				YdwStatus status = YdwStatus.parse(body, startPosition + 20);
				
				GpsInfo pgps = YdwProtobufUtil.transformGPSInfo(callLetter, ppkg, gps, status, null, null, false);
				builder.addGpsInfos(pgps);
				
				//保存gps
				YdwUploadUtil.handleGPS(ppkg, server, session, pgps);
			}else{
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}
			
			//byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		}
	}
}