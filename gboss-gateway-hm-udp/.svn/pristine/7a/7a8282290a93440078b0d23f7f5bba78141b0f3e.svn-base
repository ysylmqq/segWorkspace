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

/**
 * 定时报告(需应答)
 * 监听应答
 * 设置监听电话号码应答
 * 空重车变化上报(GPS)
 */
public class Cmd72 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(SegPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception {
		byte[] body = pkg.getBody();
		String strBody = new String(body, SegPkgUtil.PKG_CHAR_ENCODING);
		
		if(strBody.startsWith("(ITV")){
			//定时报告(需应答)
			SegGPSInfo gps = SegGPSInfo.parse(strBody, 4);
			SegUploadUtil.handleGPS(pkg, server, session, gps);
			
			//应答
			SegPkgUtil.commonAckUnit((byte)0x72, session, pkg);
			return true;
		}else if(strBody.startsWith("(FNS,003,")){
			//监听应答
			String tel = strBody.substring(9, strBody.length() - 1);
			String callLetter = session.getUnitInfo().getCallLetter();
			String sn = CmdUtil.getCmdCacheSn(callLetter, CmdUtil.CMD_ID_MONITOR);
			ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(sn);
			if(cache != null) {
				Command usercmd = cache.getUserCommand();
				Builder builder = CommandBuff.CommandResponse.newBuilder();
				//通用部分
				CmdResponseUtil.updateResponseProtoSuccessByUserCommand(builder, callLetter, usercmd);
				//params
				builder.addParams(tel);
				
				//byte[] data = builder.build().toByteArray();
				CmdResponseUtil.simpleCommandResponse(cache, builder);
			}
			
			return true;
		}else if(strBody.startsWith("(FNS,033,")){
			//设置监听电话号码应答
			handlerSetMonitorTelAck(pkg, server, session, strBody);
			return true;
		}else if(strBody.startsWith("(LDR")){
			//空重车变化上报
			SegGPSInfo gps = SegGPSInfo.parse(strBody, 4);
			SegUploadUtil.handleGPS(pkg, server, session, gps);
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * 设置监听电话号码应答
	 */
	public static void handlerSetMonitorTelAck(SegPackage pkg, UnitServer server,
			UnitSocketSession session, String strBody){
		String callLetter = session.getUnitInfo().getCallLetter();
		String sn = CmdUtil.getCmdCacheSn(callLetter, CmdUtil.CMD_ID_SET_MONITOR_TEL);
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(sn);
		if(cache != null) {
			Command usercmd = cache.getUserCommand();
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			//通用部分
			CmdResponseUtil.updateResponseProtoSuccessByUserCommand(builder, callLetter, usercmd);
			//params
			String[] calls = strBody.substring(9, strBody.length() - 1).split(",");
			for(String call: calls){
				builder.addParams(call);
			}
			
			//byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		}
	}
}