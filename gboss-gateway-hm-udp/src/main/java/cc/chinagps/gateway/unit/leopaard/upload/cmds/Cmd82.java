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
import cc.chinagps.gateway.unit.leopaard.upload.bean.LeopaardGPSInfo;
import cc.chinagps.gateway.unit.leopaard.upload.bean.LeopaardOBDInfo;
import cc.chinagps.gateway.unit.leopaard.upload.bean.LeopaardStatusInfo;
import cc.chinagps.gateway.unit.leopaard.util.LeopaardPkgUtil;
import cc.chinagps.gateway.unit.leopaard.util.LeopaardUploadUtil;
import cc.chinagps.gateway.util.Constants;

/**
 * 控制指令应答
 */
public class Cmd82 extends CheckLoginHandler {
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);

	@Override
	public boolean handlerPackageSessionChecked(LeopaardPackage ppkg, UnitServer server, UnitSocketSession session)
			throws Exception {
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = LeopaardPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSerialNo());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);
		byte[] body = ppkg.getDataUnit();
		int pos = 0;

		String responseTime = cc.chinagps.gateway.util.Util.getDateTime(body, pos, 6);
		logger_debug.debug("[leopaard] cmd82 responseTime:" + responseTime);
		pos += 6;
		int cmdId = body[pos++] & 0xFF;
		int subCmdId = -1;
		int param1 = -1, param2 = -1;
		byte[] result;
		int unit_ack_result = -1;
		switch (cmdId) {
		case 0x80:
			subCmdId = body[pos++] & 0xFF;
			switch (subCmdId) {
			case 0x1:
				param1 = body[pos++] & 0xFF;
				result = new byte[2];
				System.arraycopy(body, pos, result, 0, 2);
				pos += 2;
				unit_ack_result = Util.getShort(result)& 0xFFFF;
				break;
			case 0x2:
				param1 = body[pos++] & 0xFF;
				param2 = body[pos++] & 0xFF;
				result = new byte[2];
				System.arraycopy(body, pos, result, 0, 2);
				pos += 2;
				unit_ack_result = Util.getShort(result) & 0xFFFF;
				break;
			case 0x3:
				param1 = body[pos++] & 0xFF;
				result = new byte[2];
				System.arraycopy(body, pos, result, 0, 2);
				pos += 2;
				unit_ack_result = Util.getShort(result)& 0xFFFF;
				break;
			case 0x4:
				param1 = body[pos++] & 0xFF;
				result = new byte[2];
				System.arraycopy(body, pos, result, 0, 2);
				pos += 2;
				unit_ack_result = Util.getShort(result)& 0xFFFF;
				break;
			case 0x5:
				param1 = body[pos++] & 0xFF;
				result = new byte[2];
				System.arraycopy(body, pos, result, 0, 2);
				pos += 2;
				unit_ack_result = Util.getShort(result)& 0xFFFF;
				break;
			case 0x7:
				param1 = body[pos++] & 0xFF;
				param2 = body[pos++] & 0xFF;
				result = new byte[2];
				System.arraycopy(body, pos, result, 0, 2);
				pos += 2;
				unit_ack_result = Util.getShort(result) & 0xFFFF;
				break;
			case 0x8:
				param1 = body[pos++] & 0xFF;
				result = new byte[2];
				System.arraycopy(body, pos, result, 0, 2);
				pos += 2;
				unit_ack_result = Util.getShort(result)& 0xFFFF;
				break;
			case 0x9://查车返回
				param1 = body[pos++] & 0xFF;
				pos++;
				result = new byte[2];
				System.arraycopy(body, pos, result, 0, 2);
				pos += 2;
				unit_ack_result = Util.getShort(result)& 0xFFFF;
				/*boolean isHistory = false;
				// 保存gps
				LeopaardGPSInfo leopaardGPSInfo = null;
				LeopaardStatusInfo leopaardStatusInfo = null;
				LeopaardOBDInfo leopaardOBDInfo = null;
				leopaardGPSInfo = LeopaardGPSInfo.parseAckGps(leopaardGPSInfo, leopaardStatusInfo, leopaardOBDInfo, body, 7);
				LeopaardUploadUtil.handleGPS(ppkg, server, session, leopaardGPSInfo, isHistory);

				// 车况信息(OBD)
				if (leopaardGPSInfo.getLeopaardOBDInfo() != null) {
					LeopaardUploadUtil.handleOBD(server, callLetter, leopaardGPSInfo.getGpsTime(),
							leopaardGPSInfo.getLeopaardOBDInfo(), isHistory);
				}

				// 故障码
				if (leopaardGPSInfo.getLeopaardFaultInfo() != null) {
					LeopaardUploadUtil.handleFaultInfo(server, callLetter, leopaardGPSInfo.getLeopaardFaultInfo(),
							leopaardGPSInfo.getGpsTime(), isHistory);
				}*/
				break;
			default:
				break;
			}
			break;

		default:
			break;
		}
		
		Builder builder = CommandBuff.CommandResponse.newBuilder();
		builder.setCallLetter(callLetter);
		if (unit_ack_result == 0) {
			builder.setResult(Constants.RESULT_SUCCESS);
			builder.addParams(responseTime);
			if (param1 != -1) {
				builder.addParams(param1 + "");
			}
			if (param2 != -1) {
				builder.addParams(param2 + "");
			}
		} else {
			builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
			builder.addParams(String.valueOf(unit_ack_result));
		}
		
		if (cache != null) {
			Command usercmd = cache.getUserCommand();
			logger_debug.debug("[leopaard] cmd82 get command from cache,cmd[" + usercmd.getCmdId()
					+ "] unit_ack_result:" + unit_ack_result);
			builder.setSn(usercmd.getSn());
			builder.setCmdId(usercmd.getCmdId());
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		} else {
			// 未找到缓存，可能是唤醒后的数据
			builder.setSn("");
			builder.setCmdId(getInnerCmdId(cmdId));
			builder.setUnitsn(ppkg.getHead().getSerialNo() & 0xFFFF);
			server.getExportMQ().addCommandResponse(builder.build());
		}

		return true;
	}

	private static int getInnerCmdId(int cmdId) {
		switch (cmdId) {
		case 0x80:
			return 0x0321;
		default:
			return 0;
		}
	}
}