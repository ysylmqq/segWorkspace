package cc.chinagps.gateway.unit.leopaard.upload.cmds;

import org.apache.log4j.Logger;
import org.seg.lib.util.Util;

import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.buff.CommandBuff.CommandResponse.Builder;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.seat.cmd.CmdResponseUtil;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.leopaard.pkg.LeopaardPackage;
import cc.chinagps.gateway.unit.leopaard.util.LeopaardPkgUtil;
import cc.chinagps.gateway.util.Constants;

/**
 * …Ë÷√÷∏¡Ó”¶¥
 */
public class Cmd81 extends CheckLoginHandler {
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);

	@Override
	public boolean handlerPackageSessionChecked(LeopaardPackage ppkg, UnitServer server, UnitSocketSession session)
			throws Exception {
		byte[] body = ppkg.getDataUnit();
		int pos = 0;
		String responseTime = cc.chinagps.gateway.util.Util.getDateTime(body, pos, 6);
		logger_debug.debug("[leopaard] cmd81 responseTime:" + responseTime);
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = LeopaardPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSerialNo());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);
		if (cache != null) {
			Command usercmd = cache.getUserCommand();
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn(usercmd.getSn());
			builder.setCallLetter(callLetter);
			builder.setCmdId(usercmd.getCmdId());
			builder.setResult(Constants.RESULT_SUCCESS);
			builder.addParams(String.valueOf(responseTime));
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		}
		return true;
	}

}