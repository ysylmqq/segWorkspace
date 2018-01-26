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
import cc.chinagps.gateway.unit.seg.util.SegPkgUtil;

/**
 * 下发订单应答
 */
public class CmdB0 extends CheckLoginHandler {
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	@Override
	public boolean handlerPackageSessionChecked(SegPackage pkg, UnitServer server, UnitSocketSession session)
			throws Exception {
		byte[] body = pkg.getBody();
		String strBody = new String(body, SegPkgUtil.PKG_CHAR_ENCODING);
		if (strBody.startsWith("(FNS,CRD,")) {
			String callLetter = session.getUnitInfo().getCallLetter();
			String tempStr = strBody.substring(1,strBody.length()-1);
			String[] response= tempStr.split(",");
			String rfid = null;
			String validTime = null;
			String res = null;
			if(response.length >=5){
				rfid = response[2];
				validTime = response[3];
				res = response[4];
			}
			int cmdId = CmdUtil.CMD_ID_DELIVER_ORDER;
			String sn = CmdUtil.getCmdCacheSn(callLetter, cmdId);
			ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(sn);
			if (cache != null) {
				Command usercmd = cache.getUserCommand();
				Builder builder = CommandBuff.CommandResponse.newBuilder();
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
				builder.addParams(rfid);
				builder.addParams(validTime);

				CmdResponseUtil.simpleCommandResponse(cache, builder);
				logger_debug.debug("[seg][B0] CmdResponse:"+builder.build());
			}

			return true;
		}

		return false;
	}
}