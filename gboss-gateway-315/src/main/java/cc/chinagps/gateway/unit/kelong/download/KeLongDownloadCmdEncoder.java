package cc.chinagps.gateway.unit.kelong.download;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.seg.lib.util.Util;

import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.exceptions.WrongFormatException;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.seat.cmd.CmdUtil;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.stream.OutputPackageEncoder;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.kelong.define.KeLongDefines;
import cc.chinagps.gateway.unit.kelong.pkg.KeLongHead;
import cc.chinagps.gateway.unit.kelong.pkg.KeLongPackage;
import cc.chinagps.gateway.unit.kelong.util.KeLongPkgUtil;
import cc.chinagps.gateway.unit.seg.download.SegServerToUnitCommand;
import cc.chinagps.gateway.util.HexUtil;

public class KeLongDownloadCmdEncoder implements OutputPackageEncoder {
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);

	@Override
	public ServerToUnitCommand encode(CommandBuff.Command userCmd, UnitSocketSession unitSession) throws Exception {
		int cmdIdx = userCmd.getCmdId();
		switch (cmdIdx) {
		case CmdUtil.CMD_ID_RESTART:
			return makeCmd(userCmd, unitSession, (short) 0x8201, new byte[0]);
		case CmdUtil.CMD_ID_RESET:
			return makeCmd(userCmd, unitSession, (short) 0x8202, new byte[0]);
		case CmdUtil.CMD_ID_SET_HEARTBEAT_INTERVAL:
			return getHeartBeatCmd(userCmd, unitSession);
		case CmdUtil.CMD_ID_SET_SLEEP_WAKEUP_INTERVAL:
			return getSleepWakeupCmd(userCmd, unitSession);
		case CmdUtil.CMD_ID_SET_TOTAL_DISTANCE_OIL:
			return getTotalDistanceOilCmd(userCmd, unitSession);
		case CmdUtil.CMD_ID_SET_TCP_UDP_PARAMS:
			return getSetTCPParamCmd(userCmd, unitSession);
		case CmdUtil.CMD_ID_UPGRADE_FTP_MULTIFILE:
			return getUpgradeFtpCmd(userCmd, unitSession);
		case CmdUtil.CMD_ID_SET_GPS_DELIVER_PARAM:
			return getSetGpsDeliverCmd(userCmd, unitSession);
		case CmdUtil.CMD_ID_SET_INFLECTION_PARAM:
			return getSetInflectionCmd(userCmd, unitSession);
		case CmdUtil.CMD_ID_SET_OVERSPEED_PARAM:
			return getSetOverSpeedCmd(userCmd, unitSession);
		case CmdUtil.CMD_ID_QUERY_OBD_ADAPT_PARAM:
			return getQueryOBDAdaptCmd(userCmd, unitSession);
		case CmdUtil.CMD_ID_SET_OBD_ADAPT_PARAM:
			return getSetOBDAdaptCmd(userCmd, unitSession);
		case CmdUtil.CMD_ID_POSITION:
			return makeCmd(userCmd,unitSession,(short) 0x8165, new byte[0]);
		default:
			throw new WrongFormatException("[keLong] no encoder, cmdId:" + cmdIdx);
		}
	}

	private ServerToUnitCommand getSetOBDAdaptCmd(Command userCmd, UnitSocketSession unitSession) throws Exception {
		// TODO Auto-generated method stub
		List<String> params = userCmd.getParamsList();
		int size = params.size();
		if (size < 14) {
			throw new WrongFormatException("[keLong] not enough params, min: 14, current:" + size);
		}
		List<byte[]> data = new ArrayList<byte[]>();
		int len = 0;
		int index = 0;
		byte[] protocolType = new byte[4];
		protocolType[0] = 0x01;
		protocolType[1] = 2;
		System.arraycopy(Util.getShortByte(Short.valueOf(params.get(index++))), 0, protocolType, 2, 2);
		data.add(protocolType);
		len += 4;

		byte[] readFaultCodeWay = new byte[3];
		readFaultCodeWay[0] = 0x02;
		readFaultCodeWay[1] = 1;
		readFaultCodeWay[2] = Byte.valueOf(params.get(index++));
		data.add(readFaultCodeWay);
		len += 3;

		byte[] antiTheftProtocol = new byte[6];
		antiTheftProtocol[0] = 0x03;
		antiTheftProtocol[1] = 4;
		System.arraycopy(Util.getIntByte(Integer.valueOf(params.get(index++))), 0, antiTheftProtocol, 2, 4);
		data.add(antiTheftProtocol);
		len += 6;

		byte[] vehicleBrandId = new byte[6];
		vehicleBrandId[0] = 0x04;
		vehicleBrandId[1] = 4;
		System.arraycopy(Util.getIntByte(Integer.valueOf(params.get(index++))), 0, vehicleBrandId, 2, 4);
		data.add(vehicleBrandId);
		len += 6;

		byte[] frameInterval = new byte[4];
		frameInterval[0] = 0x05;
		frameInterval[1] = 2;
		System.arraycopy(Util.getShortByte(Short.valueOf(params.get(index++))), 0, frameInterval, 2, 2);
		data.add(frameInterval);
		len += 4;

		byte[] ecuAddress = new byte[6];
		ecuAddress[0] = 0x06;
		ecuAddress[1] = 4;
		System.arraycopy(Util.getIntByte(Integer.valueOf(params.get(index++))), 0, ecuAddress, 2, 4);
		data.add(ecuAddress);
		len += 6;

		byte[] oilCoefficient = new byte[4];
		oilCoefficient[0] = 0x07;
		oilCoefficient[1] = 2;
		System.arraycopy(Util.getShortByte(Short.valueOf(params.get(index++))), 0, oilCoefficient, 2, 2);
		data.add(oilCoefficient);
		len += 4;

		byte[] distanceCoefficient = new byte[4];
		distanceCoefficient[0] = 0x08;
		distanceCoefficient[1] = 2;
		System.arraycopy(Util.getShortByte(Short.valueOf(params.get(index++))), 0, distanceCoefficient, 2, 2);
		data.add(distanceCoefficient);
		len += 4;

		byte[] displacement = new byte[3];
		displacement[0] = 0x09;
		displacement[1] = 1;
		displacement[2] = Byte.valueOf(params.get(index++));
		data.add(displacement);
		len += 3;

		byte[] oilDensity = new byte[4];
		oilDensity[0] = 0x0A;
		oilDensity[1] = 2;
		System.arraycopy(Util.getShortByte(Short.valueOf(params.get(index++))), 0, oilDensity, 2, 2);
		data.add(oilDensity);
		len += 4;

		byte[] oilComputeWay = new byte[3];
		oilComputeWay[0] = 0x0B;
		oilComputeWay[1] = 1;
		oilComputeWay[2] = Byte.valueOf(params.get(index++));
		data.add(oilComputeWay);
		len += 3;

		byte[] dataStreamReadTime = new byte[3];
		dataStreamReadTime[0] = 0x0C;
		dataStreamReadTime[1] = 1;
		dataStreamReadTime[2] = Byte.valueOf(params.get(index++));
		data.add(dataStreamReadTime);
		len += 3;

		byte[] changeVehicleFlag = new byte[3];
		changeVehicleFlag[0] = 0x0D;
		changeVehicleFlag[1] = 1;
		changeVehicleFlag[2] = Byte.valueOf(params.get(index++));
		data.add(changeVehicleFlag);
		len += 3;

		byte[] vehiclePowerType = new byte[3];
		vehiclePowerType[0] = 0x0E;
		vehiclePowerType[1] = 1;
		vehiclePowerType[2] = Byte.valueOf(params.get(index++));
		data.add(vehiclePowerType);
		len += 3;

		byte[] obdAdaptData = new byte[len];
		int pos = 0;
		for (byte[] b : data) {
			System.arraycopy(b, 0, obdAdaptData, pos, b.length);
			pos += b.length;
		}

		return makeCmd(userCmd, unitSession, (short) 0x8206, obdAdaptData);
	}

	private ServerToUnitCommand getQueryOBDAdaptCmd(Command userCmd, UnitSocketSession unitSession) throws Exception {
		// TODO Auto-generated method stub
		byte[] data = new byte[0];
		return makeCmd(userCmd, unitSession, (short) 0x8106, data);
	}

	private ServerToUnitCommand getUpgradeFtpCmd(Command userCmd, UnitSocketSession unitSession) throws Exception {
		// TODO Auto-generated method stub
		List<String> params = userCmd.getParamsList();
		int size = params.size();
		if (params.size() < 8) {
			throw new WrongFormatException("[keLong] not enough params, min: 8, current:" + params.size());
		}
		if ((size - 5) % 3 != 0) {
			throw new WrongFormatException("[keLong] wrong params num, current num:" + params.size());
		}
		String asciiStr = "";
		String upgradeVersion = params.get(0);
		asciiStr += upgradeVersion + ",";
		String ip = params.get(1);
		asciiStr += ip + ",";
		String port = params.get(2);
		asciiStr += port + ",";
		String name = params.get(3);
		asciiStr += name + ",";
		String password = params.get(4);
		asciiStr += password + ",";

		int offset = 5;
		int fileNum = (size - 5) / 3;
		for (int i = 0; i < fileNum; i++) {
			String fileName = params.get(offset++);
			asciiStr += fileName + ",";
			String fileLen = params.get(offset++);
			// asciiStr += fileLen + ",";
			String filePath = params.get(offset++);
			asciiStr += filePath + ",";
		}

		byte[] data = asciiStr.getBytes(KeLongDefines.CHARSET);
		return makeCmd(userCmd, unitSession, (short) 0x8300, data);
	}

	private ServerToUnitCommand getSetOverSpeedCmd(Command userCmd, UnitSocketSession unitSession) throws Exception {
		// TODO Auto-generated method stub
		List<String> params = userCmd.getParamsList();
		if (params.size() < 3) {
			throw new WrongFormatException("[keLong] not enough params, min: 3, current:" + params.size());
		}
		byte[] data = new byte[3];
		byte isOpen = Byte.valueOf(params.get(0));
		data[0] = isOpen;
		byte speed = Byte.valueOf(params.get(1));
		data[1] = speed;
		byte lastTime = Byte.valueOf(params.get(2));
		data[2] = lastTime;
		return makeCmd(userCmd, unitSession, (short) 0x8240, data);
	}

	private ServerToUnitCommand getSetInflectionCmd(Command userCmd, UnitSocketSession unitSession) throws Exception {
		// TODO Auto-generated method stub
		List<String> params = userCmd.getParamsList();
		if (params.size() < 1) {
			throw new WrongFormatException("[keLong] not enough params, min: 1, current:" + params.size());
		}
		byte angle = Byte.valueOf(params.get(0));
		return makeCmd(userCmd, unitSession, (short) 0x8213, new byte[] { angle });
	}

	private ServerToUnitCommand getSetGpsDeliverCmd(Command userCmd, UnitSocketSession unitSession) throws Exception {
		// TODO Auto-generated method stub
		List<String> params = userCmd.getParamsList();
		if (params.size() < 3) {
			throw new WrongFormatException("[keLong] not enough params, min: 3, current:" + params.size());
		}
		byte[] data = new byte[4];
		byte isOpen = Byte.valueOf(params.get(0));
		data[0] = isOpen;
		byte deliverWay = Byte.valueOf(params.get(1));
		data[1] = deliverWay;
		short interval = Short.valueOf(params.get(2));
		System.arraycopy(Util.getShortByte(interval), 0, data, 2, 2);
		return makeCmd(userCmd, unitSession, (short) 0x8210, data);
	}

	private ServerToUnitCommand getTotalDistanceOilCmd(Command userCmd, UnitSocketSession unitSession)
			throws Exception {
		// TODO Auto-generated method stub
		List<String> params = userCmd.getParamsList();
		if (params.size() < 2) {
			throw new WrongFormatException("[keLong] not enough params, min: 2, current:" + params.size());
		}
		byte[] data = new byte[8];
		int distance = Integer.valueOf(params.get(0));
		byte[] distanceByte = Util.getIntByte(distance);
		System.arraycopy(distanceByte, 0, data, 0, 4);
		int oil = Integer.valueOf(params.get(1));
		System.arraycopy(Util.getIntByte(oil), 0, data, 4, 4);
		return makeCmd(userCmd, unitSession, (short) 0x8207, data);
	}

	/**
	 * 设置TCP通信参数
	 * 
	 * @throws Exception
	 */
	private ServerToUnitCommand getSetTCPParamCmd(Command userCmd, UnitSocketSession unitSession) throws Exception {

		List<String> params = userCmd.getParamsList();
		if (params.size() < 10) {
			throw new WrongFormatException("[keLong] not enough params, min: 10, current:" + params.size());
		}

		String index = params.get(0);
		String sapn = params.get(1);
		String sip = params.get(2);
		String sport = params.get(3);
		String protocolType = params.get(5);
		String userName = params.get(8);
		String password = params.get(9);

		String asciiStr = index + "," + "0" + "," + sip + "," + sport + "," + protocolType + "," + sapn + "," + userName
				+ "," + password + "\r\n";
		byte[] ascii = asciiStr.getBytes(KeLongDefines.CHARSET);
		return makeCmd(userCmd, unitSession, (short) 0x8205, ascii);

	}

	private ServerToUnitCommand getHeartBeatCmd(Command userCmd, UnitSocketSession unitSession) throws Exception {
		// TODO Auto-generated method stub
		List<String> params = userCmd.getParamsList();
		if (params.size() < 1) {
			throw new WrongFormatException("[keLong] not enough params, need: 1, current:" + params.size());
		}
		short sec = Short.valueOf(params.get(0));

		return makeCmd(userCmd, unitSession, (short) 0x8203, Util.getShortByte(sec));
	}

	private ServerToUnitCommand getSleepWakeupCmd(Command userCmd, UnitSocketSession unitSession) throws Exception {
		// TODO Auto-generated method stub
		List<String> params = userCmd.getParamsList();
		if (params.size() < 1) {
			throw new WrongFormatException("[keLong] not enough params, need: 1, current:" + params.size());
		}
		byte hour = Byte.valueOf(params.get(0));
		return makeCmd(userCmd, unitSession, (short) 0x8204, new byte[] { hour });
	}

	private ServerToUnitCommand makeCmd(Command userCmd, UnitSocketSession unitSession, short msgId, byte[] body)
			throws Exception {
		KeLongPackage pkg = new KeLongPackage();
		KeLongHead head = new KeLongHead();
		head.setDeviceId(unitSession.getUnitInfo().getIMEI());
		head.setMsgId((short) (msgId & 0xFFFF));
		if (body != null) {
			head.setMsgLen((short) (body.length + 11));
			head.setDataLen((short) body.length);
		}

		head.setSerialNo(KeLongPkgUtil.getSn(unitSession));

		pkg.setHead(head);
		pkg.setData(body);
		byte[] source = pkg.encode();

		SegServerToUnitCommand serverToUnitCommand = new SegServerToUnitCommand();
		serverToUnitCommand.setUserCommand(userCmd);
		String cachedSn = KeLongPkgUtil.getCmdCacheSn(unitSession.getUnitInfo().getCallLetter(), head.getSerialNo());
		serverToUnitCommand.setCachedSn(cachedSn);

		serverToUnitCommand.setData(source);
		serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
		logger_debug.debug("[keLong] cmd encoder makeCmd data:" + HexUtil.byteToHexStr(source) + ";cmd:" + userCmd);
		return serverToUnitCommand;
	}
	
}