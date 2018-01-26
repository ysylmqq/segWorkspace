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
 * 断油电应答
 */
public class CmdA0 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(SegPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception {
		byte[] body = pkg.getBody();
		String strBody = new String(body, SegPkgUtil.PKG_CHAR_ENCODING);
		
		if(strBody.startsWith("(LKC")){
			//if(logger_debug.isDebugEnabled()){
			//	logger_debug.debug("断油电应答...:" + pkg.getSerialNumber());
			//}
			
			//String sn = pkg.getSerialNumber();
			String callLetter = session.getUnitInfo().getCallLetter();
			String sn = CmdUtil.getCmdCacheSn(callLetter, CmdUtil.CMD_ID_CUT_OFF_OIL);
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
		}else if(strBody.startsWith("(FNS,BS")){
			//设置基站开关应答
			String res = strBody.substring(7, strBody.length() - 1);
			String callLetter = session.getUnitInfo().getCallLetter();
			int cmdId = "1".equals(res) ? CmdUtil.CMD_ID_OPEN_BASESTATION_UPLOAD
					: CmdUtil.CMD_ID_CLOSE_BASESTATION_UPLOAD;
			String sn = CmdUtil.getCmdCacheSn(callLetter, cmdId);
			ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(sn);
			if (cache != null) {
				Command usercmd = cache.getUserCommand();
				Builder builder = CommandBuff.CommandResponse.newBuilder();
				// 通用部分
				CmdResponseUtil.updateResponseProtoSuccessByUserCommand(builder, callLetter, usercmd);

				builder.addParams(res);
				// byte[] data = builder.build().toByteArray();
				CmdResponseUtil.simpleCommandResponse(cache, builder);
			}
			return true;
		}
		
		return false;
	}
}