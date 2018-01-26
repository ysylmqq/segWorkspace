package cc.chinagps.gateway.unit.kelong.upload.cmds;

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
import cc.chinagps.gateway.unit.kelong.pkg.KeLongPackage;
import cc.chinagps.gateway.unit.kelong.upload.bean.KeLongOBDAdaptInfo;
import cc.chinagps.gateway.unit.kelong.util.KeLongPkgUtil;
import cc.chinagps.gateway.util.Constants;

/**
 * 查询OBD适配信息
 */
public class Cmd0106 extends CheckLoginHandler {
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);

	@Override
	public boolean handlerPackageSessionChecked(KeLongPackage pkg, UnitServer server, UnitSocketSession session)
			throws Exception {
		byte[] data = pkg.getData();
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = KeLongPkgUtil.getCmdCacheSn(callLetter, pkg.getHead().getSerialNo());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);
		Builder builder = CommandBuff.CommandResponse.newBuilder();
		builder.setCallLetter(callLetter);
		int unit_ack_result = -1;
		int len = data.length;
		KeLongOBDAdaptInfo keLongOBDAdaptInfo = new KeLongOBDAdaptInfo();
		int pos = 0;
		while (pos < len) {
			byte itemId = data[pos++];
			byte itemLen = data[pos++];
			switch (itemId) {
			case 0x01:
				keLongOBDAdaptInfo.setProtocolType(Util.getShort(data, pos));
				break;
			case 0x02:
				keLongOBDAdaptInfo.setReadFaultCodeWay(Byte.valueOf(data[pos]));
				break;
			case 0x03:
				keLongOBDAdaptInfo.setAntiTheftProtocol(Util.getInt(data, pos));
				break;
			case 0x04:
				keLongOBDAdaptInfo.setVehicleBrandId(Util.getInt(data, pos));
				break;
			case 0x05:
				keLongOBDAdaptInfo.setFrameInterval(Util.getShort(data, pos));
				break;
			case 0x06:
				keLongOBDAdaptInfo.setEcuAddress(Util.getInt(data, pos));
				break;
			case 0x07:
				keLongOBDAdaptInfo.setOilCoefficient(Util.getShort(data, pos));
				break;
			case 0x08:
				keLongOBDAdaptInfo.setDistanceCoefficient(Util.getShort(data, pos));
				break;
			case 0x09:
				keLongOBDAdaptInfo.setDisplacement(Byte.valueOf(data[pos]));
				break;
			case 0x0A:
				keLongOBDAdaptInfo.setOilDensity(Util.getShort(data, pos));
				break;
			case 0x0B:
				keLongOBDAdaptInfo.setOilComputeWay(Byte.valueOf(data[pos]));
				break;
			case 0x0C:
				keLongOBDAdaptInfo.setDataStreamReadTime(Byte.valueOf(data[pos]));
				break;
			case 0x0D:
				keLongOBDAdaptInfo.setChangeVehicleFlag(Byte.valueOf(data[pos]));
				break;
			case 0x0E:
				keLongOBDAdaptInfo.setVehiclePowerType(Byte.valueOf(data[pos]));
				break;
			case 0x0F:
				break;
			case 0x10:
				break;
			case 0x11:
				break;
			case 0x12:
				break;
			default:
				break;
			}
			pos += itemLen;
		}
		logger_debug.debug("[KeLong] cmd0106 query obd adapt info:"+keLongOBDAdaptInfo);
		unit_ack_result = 0;
		if (unit_ack_result == 0) {
			builder.setResult(Constants.RESULT_SUCCESS);
			fillParamsWithOBDAdaptInfo(keLongOBDAdaptInfo, builder);
		} else {
			builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
			builder.addParams(String.valueOf(unit_ack_result));
		}

		if (cache != null) {
			Command usercmd = cache.getUserCommand();
			logger_debug.debug("[KeLong] cmd0106 get command from cache,cmd[" + usercmd.getCmdId()
					+ "] unit_ack_result:" + unit_ack_result);
			builder.setSn(usercmd.getSn());
			builder.setCmdId(usercmd.getCmdId());
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		} else {
			// 未找到缓存，可能是唤醒后的数据
			builder.setSn("");
			builder.setCmdId(0x007D);
			builder.setUnitsn(pkg.getHead().getSerialNo() & 0xFFFF);
			server.getExportMQ().addCommandResponse(builder.build());
		}
		return true;
	}

	private void fillParamsWithOBDAdaptInfo(KeLongOBDAdaptInfo keLongOBDAdaptInfo, Builder builder) {
		builder.addParams(String.valueOf(keLongOBDAdaptInfo.getProtocolType()));
		builder.addParams(String.valueOf(keLongOBDAdaptInfo.getReadFaultCodeWay()));
		builder.addParams(String.valueOf(keLongOBDAdaptInfo.getAntiTheftProtocol()));
		builder.addParams(String.valueOf(keLongOBDAdaptInfo.getVehicleBrandId()));
		builder.addParams(String.valueOf(keLongOBDAdaptInfo.getFrameInterval()));
		builder.addParams(String.valueOf(keLongOBDAdaptInfo.getEcuAddress()));
		builder.addParams(String.valueOf(keLongOBDAdaptInfo.getOilCoefficient()));
		builder.addParams(String.valueOf(keLongOBDAdaptInfo.getDistanceCoefficient()));
		builder.addParams(String.valueOf(keLongOBDAdaptInfo.getDisplacement()));
		builder.addParams(String.valueOf(keLongOBDAdaptInfo.getOilDensity()));
		builder.addParams(String.valueOf(keLongOBDAdaptInfo.getOilComputeWay()));
		builder.addParams(String.valueOf(keLongOBDAdaptInfo.getDataStreamReadTime()));
		builder.addParams(String.valueOf(keLongOBDAdaptInfo.getChangeVehicleFlag()));
		builder.addParams(String.valueOf(keLongOBDAdaptInfo.getVehiclePowerType()));
	}

}