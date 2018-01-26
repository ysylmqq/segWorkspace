package cc.chinagps.gateway.unit.seg.upload.cmds;

import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.buff.CommandBuff.CommandResponse.Builder;
import cc.chinagps.gateway.seat.cmd.CmdResponseUtil;
import cc.chinagps.gateway.seat.cmd.CmdUtil;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.seg.pkg.SegPackage;
import cc.chinagps.gateway.unit.seg.upload.SegUploadUtil;
import cc.chinagps.gateway.unit.seg.upload.beans.SegGPSInfo;
import cc.chinagps.gateway.unit.seg.util.SegPkgUtil;

/**
 * 定时报告及
 * 多个指令应答(cmdId=93)
 */
public class Cmd93 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(SegPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception{
		byte[] body = pkg.getBody();
		String strBody = new String(body, SegPkgUtil.PKG_CHAR_ENCODING);
		
		if(strBody.startsWith("(ITV")){
			//定时上报
			SegGPSInfo gps = SegGPSInfo.parse(strBody, 4);
			SegUploadUtil.handleGPS(pkg, server, session, gps);
			
			return true;
		}else if(strBody.startsWith("(FNS,ITV,FF") || strBody.startsWith("(FNS,ITV,00")){
			//开启/关闭定时报告应答
			handlerUploadGPSByIntervalSetting(pkg, server, session, strBody);
			return true;
		}else if(strBody.startsWith("(FNS,DUT,FF") || strBody.startsWith("(FNS,DUT,00")){
			//开启/关闭定距报告应答
			handlerUploadGPSByDistanceSetting(pkg, server, session, strBody);
			return true;
		}else if(strBody.startsWith("(FNS,006,")){
			//更改服务电话应答
			int cmdId = CmdUtil.CMD_ID_SET_SERRVICE_TEL;
			responseTelFunc(server, session, strBody, cmdId);
			return true;
		}else if(strBody.startsWith("(FNS,007,")){
			//设置短信呼入特服号应答
			int cmdId = CmdUtil.CMD_ID_SET_SM_IN_NUM;
			responseTelFunc(server, session, strBody, cmdId);
			return true;
		}else if(strBody.startsWith("(FNS,008,")){
			//设置短信呼出特服号应答
			int cmdId = CmdUtil.CMD_ID_SET_SM_OUT_NUM;
			responseTelFunc(server, session, strBody, cmdId);
			return true;
		}else if(strBody.startsWith("(FNS,009,")){
			//设置短消息服务中心号码应答
			int cmdId = CmdUtil.CMD_ID_SET_SM_CENTER_NUM;
			responseTelFunc(server, session, strBody, cmdId);
			return true;
		}else if(strBody.startsWith("(FNS,033,")){
			//设置监听电话号码应答
			Cmd72.handlerSetMonitorTelAck(pkg, server, session, strBody);
			return true;
		}
		
		return false;
	}
	
	/**
	 * 开启/关闭定时报告应答
	 */
	public static void handlerUploadGPSByIntervalSetting(SegPackage pkg, UnitServer server,
			UnitSocketSession session, String strBody){
		//if(logger_debug.isDebugEnabled()){
		//	logger_debug.debug("开启/关闭定时报告应答...:" + pkg.getSerialNumber());
		//}
		
		//String sn = pkg.getSerialNumber();
		String callLetter = session.getUnitInfo().getCallLetter();
		int flag = Integer.valueOf(strBody.substring(9, 11), 16);
		int cmdId = flag == 0? CmdUtil.CMD_ID_STOP_UPLOAD_BY_INTERVAL: CmdUtil.CMD_ID_START_UPLOAD_BY_INTERVAL;
		String sn = CmdUtil.getCmdCacheSn(callLetter, cmdId);
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(sn);
		if(cache != null) {
			Command usercmd = cache.getUserCommand();
			
			String shour = strBody.substring(12, 14);
			String sminus = strBody.substring(14, 16);
			String sseconds = strBody.substring(16, 18);
			
			int seconds = Integer.valueOf(shour, 16) * 3600 + Integer.valueOf(sminus, 16) * 60 + Integer.valueOf(sseconds, 16);
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			//通用部分
			CmdResponseUtil.updateResponseProtoSuccessByUserCommand(builder, callLetter, usercmd);
			//params
			builder.addParams(String.valueOf(seconds));
			//byte[] data = builder.build().toByteArray();			
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		}
	}
	
	/**
	 * 开启/关闭定距报告应答
	 */
	public static void handlerUploadGPSByDistanceSetting(SegPackage pkg, UnitServer server,
			UnitSocketSession session, String strBody){
		//if(logger_debug.isDebugEnabled()){
		//	logger_debug.debug("开启/关闭定距报告应答...:" + pkg.getSerialNumber());
		//}
		
		//String sn = pkg.getSerialNumber();
		String callLetter = session.getUnitInfo().getCallLetter();
		int flag = Integer.valueOf(strBody.substring(9, 11), 16);
		int cmdId = flag == 0? CmdUtil.CMD_ID_STOP_UPLOAD_BY_DISTANCE: CmdUtil.CMD_ID_START_UPLOAD_BY_DISTANCE;
		String sn = CmdUtil.getCmdCacheSn(callLetter, cmdId);
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(sn);
		if(cache != null) {
			Command usercmd = cache.getUserCommand();
			String p1 = strBody.substring(12, 15);
			String p2 = strBody.substring(16, 19);
			
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			//通用部分
			CmdResponseUtil.updateResponseProtoSuccessByUserCommand(builder, callLetter, usercmd);
			//params
			builder.addParams(Integer.valueOf(p2, 16).toString());
			builder.addParams(Integer.valueOf(p1, 16).toString());
			
			//byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		}
	}
	
	public static void main(String[] args) {
		String pp = "00a";
		Integer i = Integer.valueOf(pp, 16);
		System.out.println(i.toString());
	}
	
	/**
	 * 回复号码
	 */
	private void responseTelFunc(UnitServer server, UnitSocketSession session, String strBody, int cmdId){
		String callLetter = session.getUnitInfo().getCallLetter();
		String sn = CmdUtil.getCmdCacheSn(callLetter, cmdId);
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(sn);
		if(cache != null) {
			Command usercmd = cache.getUserCommand();
			String tel = strBody.substring(9, strBody.length() - 1);
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			//通用部分
			CmdResponseUtil.updateResponseProtoSuccessByUserCommand(builder, callLetter, usercmd);
			//params
			builder.addParams(tel);
			//byte[] data = builder.build().toByteArray();			
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		}
	}
}