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
 * 恢复油电应答
 */
public class CmdA1 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(SegPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception {
		byte[] body = pkg.getBody();
		String strBody = new String(body, SegPkgUtil.PKG_CHAR_ENCODING);
		
		if(strBody.startsWith("(ULC")){
			//if(logger_debug.isDebugEnabled()){
			//	logger_debug.debug("恢复油电应答...:" + pkg.getSerialNumber());
			//}
			
			//String sn = pkg.getSerialNumber();
			String callLetter = session.getUnitInfo().getCallLetter();
			String sn = CmdUtil.getCmdCacheSn(callLetter, CmdUtil.CMD_ID_RESUME_OIL);
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
			
			return true;
		}else if(strBody.startsWith("(FNS,BT,")){
			String callLetter = session.getUnitInfo().getCallLetter();
			int cmdId =  CmdUtil.CMD_ID_SET_TRG_INTERVAL;
			String sn = CmdUtil.getCmdCacheSn(callLetter, cmdId);
			ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(sn);
			if(cache != null) {
				Command usercmd = cache.getUserCommand();
				
				String shour = strBody.substring(8, 10);
				String sminus = strBody.substring(10, 12);
				String sseconds = strBody.substring(12, 14);
				
				int seconds = Integer.valueOf(shour, 16) * 3600 + Integer.valueOf(sminus, 16) * 60 + Integer.valueOf(sseconds, 16);
				Builder builder = CommandBuff.CommandResponse.newBuilder();
				//通用部分
				CmdResponseUtil.updateResponseProtoSuccessByUserCommand(builder, callLetter, usercmd);
				//params
				builder.addParams(String.valueOf(seconds));
				CmdResponseUtil.simpleCommandResponse(cache, builder);
			}
			return true;
		}
		
		return false;
	}
}