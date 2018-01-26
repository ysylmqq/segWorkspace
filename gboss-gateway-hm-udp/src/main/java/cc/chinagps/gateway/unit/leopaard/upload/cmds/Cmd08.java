package cc.chinagps.gateway.unit.leopaard.upload.cmds;

import org.apache.log4j.Logger;
import org.seg.lib.util.Util;

import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.buff.CommandBuff.CommandResponse.Builder;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.seat.cmd.CmdResponseUtil;
import cc.chinagps.gateway.seat.cmd.CmdUtil;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.leopaard.pkg.LeopaardPackage;
import cc.chinagps.gateway.unit.leopaard.util.LeopaardPkgUtil;
import cc.chinagps.gateway.unit.leopaard.util.LeopaardUploadUtil;
import cc.chinagps.gateway.util.Constants;

/**
 * 远程升级
 */
public class Cmd08 extends CheckLoginHandler {
	private Logger logger_debug =Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	public Cmd08() {
	}

	@Override
	public boolean handlerPackageSessionChecked(LeopaardPackage pkg, UnitServer server, UnitSocketSession session)
			throws Exception {
		byte[] body = pkg.getDataUnit();
		int pos = 0;
		String responseTime = cc.chinagps.gateway.util.Util.getDateTime(body, pos, 6);
		pos += 6;
		byte cmdId = body[pos++];
		short res = -1;
		switch (cmdId) {
		case 0x11:
			res = Util.getShort(body, pos);
			pos +=2;
			logger_debug.debug("[leopaard]unit response upgrade request result:"+res);
			break;
		case 0x12:
			short upgradeStatus = Util.getShort(body, pos);
			pos +=2;
			logger_debug.debug("[leopaard]unit  upgrade status:"+upgradeStatus);
			LeopaardUploadUtil.commonAck(session, pkg, (byte)0x01, new byte[]{0x02,0x00,0x00});
			return true;
		default:
			break;
		}
		if(res != -1){
			String callLetter = session.getUnitInfo().getCallLetter();
			String cacheSN = LeopaardPkgUtil.getCmdCacheSn(callLetter, pkg.getHead().getSerialNo());
			ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setCallLetter(callLetter);
			if (res == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);
				builder.addParams(Constants.RESULT_SUCCESS+"");
			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(res));
			}
			
			if (cache != null) {
				Command usercmd = cache.getUserCommand();
				logger_debug.debug("[leopaard] cmd08 get command from cache,cmd[" + usercmd.getCmdId()
						+ "] unit_ack_result:" + res);
				builder.setSn(usercmd.getSn());
				builder.setCmdId(usercmd.getCmdId());
				CmdResponseUtil.simpleCommandResponse(cache, builder);
			} else {
				// 未找到缓存，可能是唤醒后的数据
				builder.setSn("");
				builder.setCmdId((byte)CmdUtil.CMD_ID_UNIT_UPDATE_FTP);
				builder.setUnitsn(pkg.getHead().getSerialNo() & 0xFFFF);
				server.getExportMQ().addCommandResponse(builder.build());
			}
		}

		return false;
	}

}