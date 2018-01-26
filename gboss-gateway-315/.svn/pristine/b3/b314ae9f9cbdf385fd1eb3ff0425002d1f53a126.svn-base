package cc.chinagps.gateway.unit.kelong.upload.cmds;

import org.apache.log4j.Logger;

import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.buff.CommandBuff.CommandResponse.Builder;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.seat.cmd.CmdResponseUtil;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.kelong.pkg.KeLongPackage;
import cc.chinagps.gateway.unit.kelong.util.KeLongPkgUtil;
import cc.chinagps.gateway.util.Constants;

/**
 * 远程升级应答
 */
public class Cmd0300 extends CheckLoginHandler {
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	@Override
	public boolean handlerPackageSessionChecked(KeLongPackage pkg, UnitServer server, UnitSocketSession session)
			throws Exception {
		byte[] data = pkg.getData();
		short msgId = pkg.getHead().getMsgId();
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = KeLongPkgUtil.getCmdCacheSn(callLetter, pkg.getHead().getSerialNo());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);
		Builder builder = CommandBuff.CommandResponse.newBuilder();
		builder.setCallLetter(callLetter);
		int unit_ack_result = -1;
		unit_ack_result = data[0];
		if (unit_ack_result == 0) {
			builder.setResult(Constants.RESULT_SUCCESS);
		} else {
			builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
			builder.addParams(String.valueOf(unit_ack_result));
		}
		
		if (cache != null) {
			Command usercmd = cache.getUserCommand();
			logger_debug.debug("[KeLong] cmd0300 get command from cache,cmd[" + usercmd.getCmdId()
					+ "] unit_ack_result:" + unit_ack_result);
			builder.setSn(usercmd.getSn());
			builder.setCmdId(usercmd.getCmdId());
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		} else {
			// 未找到缓存，可能是唤醒后的数据
			builder.setSn("");
			builder.setCmdId(getInnerCmdId(msgId));
			builder.setUnitsn(pkg.getHead().getSerialNo() & 0xFFFF);
			server.getExportMQ().addCommandResponse(builder.build());
		}
		return true;
	}
	
	private static int getInnerCmdId(int cmdId) {
		switch (cmdId) {
		case 0x0300:
			return 0x00B9;
		default:
			return 0;
		}
	}
}