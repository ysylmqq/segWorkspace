package cc.chinagps.gateway.unit.seg.upload.cmds;

import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.buff.CommandBuff.CommandResponse.Builder;
import cc.chinagps.gateway.seat.cmd.CmdResponseUtil;
import cc.chinagps.gateway.seat.cmd.CmdUtil;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.seg.pkg.SegPackage;
import cc.chinagps.gateway.unit.seg.upload.SegUploadUtil;
import cc.chinagps.gateway.unit.seg.upload.beans.SegGPSInfo;
import cc.chinagps.gateway.unit.seg.util.SegPkgUtil;
import cc.chinagps.gateway.util.Constants;

/**
 * 查车应答
 * 找车应答
 * 及点火熄火上报
 * 锁车门应答
 * 开车门应答
 * 
 * 关车窗应答
 * 开车窗应答
 */
public class Cmd90 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(SegPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception {
		String strBody = new String(pkg.getBody(), SegPkgUtil.PKG_CHAR_ENCODING);
		if(strBody.startsWith("(ONE") || strBody.startsWith("(SCR")){
			//查车应答 或 跟踪上报
			SegUploadUtil.commonResponseWithGPS(pkg, server, session, strBody, CmdUtil.CMD_ID_POSITION);
			return true;
		}else if(strBody.startsWith("(FDC")){
			//找车
			SegUploadUtil.commonResponseWithGPS(pkg, server, session, strBody, CmdUtil.CMD_ID_SEARCH_VEHICLE);
			return true;
		}else if(strBody.startsWith("(ACC")){
			//点火熄火上报
			SegGPSInfo gps = SegGPSInfo.parse(strBody, 4);
			SegUploadUtil.handleGPS(pkg, server, session, gps);
			
			return true;
		}else if(strBody.startsWith("(DCC")){
			//锁车门应答
			handlerLockDoorAck(pkg, server, session, strBody);
			return true;
		}else if(strBody.startsWith("(DCE")){
			//锁车门失败应答
			handlerLockDoorFailAck(pkg, server, session, strBody);
			return true;
		}else if(strBody.startsWith("(DOC")){
			//开车门应答
			handlerOpenDoorAck(pkg, server, session, strBody);
			return true;
		}else if(strBody.startsWith("(DOE")){
			//开车门失败应答
			handlerOpenDoorFailAck(pkg, server, session, strBody);
			return true;
		}else if(strBody.startsWith("(WCC")){
			//关车窗成功
			SegUploadUtil.commonResponseWithGPS(pkg, server, session, strBody, CmdUtil.CMD_ID_CLOSE_WINDOW);
			return true;
		}else if(strBody.startsWith("(WCE")){
			//关车窗失败
			String callLetter = session.getUnitInfo().getCallLetter();
			String sn = CmdUtil.getCmdCacheSn(callLetter, CmdUtil.CMD_ID_CLOSE_WINDOW);
			ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(sn);
			if(cache != null) {
				Command usercmd = cache.getUserCommand();
				Builder builder = CommandBuff.CommandResponse.newBuilder();
				CmdResponseUtil.updateResponseProtoByUserCommand(builder, callLetter, usercmd, Constants.RESULT_UNIT_ACK_FAIL);
				//失败原因
				String unitResult = strBody.substring(5, 7);
				builder.addParams(unitResult);
				
				//byte[] data = builder.build().toByteArray();
				CmdResponseUtil.simpleCommandResponse(cache, builder);
			}
			
			return true;
		}else if(strBody.startsWith("(WOC")){
			//开车窗成功
			SegUploadUtil.commonResponseWithGPS(pkg, server, session, strBody, CmdUtil.CMD_ID_OPEN_WINDOW);
			return true;
		}else if(strBody.startsWith("(WOE")){
			//开车窗失败
			String callLetter = session.getUnitInfo().getCallLetter();
			String sn = CmdUtil.getCmdCacheSn(callLetter, CmdUtil.CMD_ID_CLOSE_WINDOW);
			ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(sn);
			if(cache != null) {
				Command usercmd = cache.getUserCommand();
				Builder builder = CommandBuff.CommandResponse.newBuilder();
				CmdResponseUtil.updateResponseProtoByUserCommand(builder, callLetter, usercmd, Constants.RESULT_UNIT_ACK_FAIL);
				//失败原因
				String unitResult = strBody.substring(5, 7);
				builder.addParams(unitResult);
				
				//byte[] data = builder.build().toByteArray();
				CmdResponseUtil.simpleCommandResponse(cache, builder);
			}
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * 锁车门应答
	 * @throws Exception 
	 */
	public static void handlerLockDoorAck(SegPackage pkg, UnitServer server,
			UnitSocketSession session, String strBody) throws Exception{
		//if(logger_debug.isDebugEnabled()){
		//	logger_debug.debug("锁车门应答...:" + pkg.getSerialNumber());
		//}
		
		//String sn = pkg.getSerialNumber();
		String callLetter = session.getUnitInfo().getCallLetter();
		String sn = CmdUtil.getCmdCacheSn(callLetter, CmdUtil.CMD_ID_LOCK_DOOR);
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(sn);
		if(cache != null) {
			Command usercmd = cache.getUserCommand();
			SegGPSInfo gps = SegGPSInfo.parse(strBody, 4);
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			//设置回应的通用部分
			CmdResponseUtil.updateResponseProtoSuccessByUserCommand(builder, callLetter, usercmd);
			
			//gpsInfo
			SegUploadUtil.setUpResponseByGPSInfo(callLetter, builder, gps);
			
			//byte[] data = builder.build().toByteArray();		
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		}
	}
	
	/**
	 * 锁车门失败应答
	 * @throws Exception 
	 */
	private static void handlerLockDoorFailAck(SegPackage pkg, UnitServer server,
			UnitSocketSession session, String strBody) throws Exception{
		String callLetter = session.getUnitInfo().getCallLetter();
		String sn = CmdUtil.getCmdCacheSn(callLetter, CmdUtil.CMD_ID_LOCK_DOOR);
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(sn);
		if(cache != null) {
			Command usercmd = cache.getUserCommand();
			
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			CmdResponseUtil.updateResponseProtoByUserCommand(builder, callLetter, usercmd, Constants.RESULT_UNIT_ACK_FAIL);
			//失败原因
			String unitResult = strBody.substring(5, 7);
			builder.addParams(unitResult);
			//byte[] data = builder.build().toByteArray();		
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		}
	}
	
	/**
	 * 开车门应答
	 * @throws Exception 
	 */
	public static void handlerOpenDoorAck(SegPackage pkg, UnitServer server,
			UnitSocketSession session, String strBody) throws Exception{
		//if(logger_debug.isDebugEnabled()){
		//	logger_debug.debug("开车门应答...:" + pkg.getSerialNumber());
		//}
		
		//String sn = pkg.getSerialNumber();
		String callLetter = session.getUnitInfo().getCallLetter();
		String sn = CmdUtil.getCmdCacheSn(callLetter, CmdUtil.CMD_ID_OPEN_DOOR);
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(sn);
		if(cache != null) {
			Command usercmd = cache.getUserCommand();
			SegGPSInfo gps = SegGPSInfo.parse(strBody, 4);
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			//设置回应的通用部分
			CmdResponseUtil.updateResponseProtoSuccessByUserCommand(builder, callLetter, usercmd);
			
			//gpsInfo
			SegUploadUtil.setUpResponseByGPSInfo(callLetter, builder, gps);
			
			//byte[] data = builder.build().toByteArray();			
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		}
	}
	
	/**
	 * 开车门失败应答
	 * @throws Exception 
	 */
	private static void handlerOpenDoorFailAck(SegPackage pkg, UnitServer server,
			UnitSocketSession session, String strBody) throws Exception{
		String callLetter = session.getUnitInfo().getCallLetter();
		String sn = CmdUtil.getCmdCacheSn(callLetter, CmdUtil.CMD_ID_OPEN_DOOR);
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(sn);
		if(cache != null) {
			Command usercmd = cache.getUserCommand();
			
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			CmdResponseUtil.updateResponseProtoByUserCommand(builder, callLetter, usercmd, Constants.RESULT_UNIT_ACK_FAIL);
			//失败原因
			String unitResult = strBody.substring(5, 7);
			builder.addParams(unitResult);
			//byte[] data = builder.build().toByteArray();			
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		}
	}
}