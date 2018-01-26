package cc.chinagps.gateway.unit.seg.upload.cmds;

import org.apache.log4j.Logger;

import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.buff.CommandBuff.CommandResponse.Builder;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.seat.cmd.CmdResponseUtil;
import cc.chinagps.gateway.seat.cmd.CmdUtil;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.seg.pkg.SegPackage;
import cc.chinagps.gateway.unit.seg.upload.SegUploadUtil;
import cc.chinagps.gateway.unit.seg.upload.beans.SegGPSInfo;
import cc.chinagps.gateway.unit.seg.util.SegPkgUtil;
import cc.chinagps.gateway.util.Constants;

/**
 * 北汽远程控制车辆应答
 */
public class CmdB2 extends CheckLoginHandler{
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	@Override
	public boolean handlerPackageSessionChecked(SegPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception {
		byte[] body = pkg.getBody();
		String strBody = new String(body, SegPkgUtil.PKG_CHAR_ENCODING);
		int len = strBody.length();
		if(strBody.startsWith("(FNS,CTL,")){
			String callLetter = session.getUnitInfo().getCallLetter();
			String cmd = strBody.substring(9, 10);
			int cmdId = CmdUtil.CMD_ID_REMOTE_CONTROL_VEHICLE;
			String sn = CmdUtil.getCmdCacheSn(callLetter, cmdId);
			ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(sn);
			if(cache != null) {
				Command usercmd = cache.getUserCommand();
				
				Builder builder = CommandBuff.CommandResponse.newBuilder();
				String res = strBody.substring(10, 11);
				
				if ("0".equals(res))
					// 设置回应的通用部分
					CmdResponseUtil.updateResponseProtoSuccessByUserCommand(builder, callLetter, usercmd);
				else {
					if (res != null)
						CmdResponseUtil.updateResponseProtoByUserCommand(builder, callLetter, usercmd,
								Integer.valueOf(res));
					else
						CmdResponseUtil.updateResponseProtoFailedByUserCommand(builder, callLetter, usercmd);
				}
				builder.addParams(cmd);
				if (len > 11) {
					SegGPSInfo gps = SegGPSInfo.parse(strBody, 11);
					logger_debug.debug("[seg][B2] CmdResponse gps:" + gps);
					// gpsInfo
					SegUploadUtil.setUpResponseByGPSInfo(callLetter, builder, gps);
				}
				CmdResponseUtil.simpleCommandResponse(cache, builder);
				logger_debug.debug("[seg][B2] CmdResponse:"+builder.build());
			}
			
			return true;
		}
		
		return false;
	}
}