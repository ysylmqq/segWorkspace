package cc.chinagps.gateway.unit.oem.upload.cmd;

import org.apache.log4j.Logger;

import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.buff.CommandBuff.CommandResponse.Builder;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.seat.cmd.CmdResponseUtil;
import cc.chinagps.gateway.seat.cmd.CmdUtil;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.oem.pkg.OEMPackage;
import cc.chinagps.gateway.unit.oem.upload.OEMUploadUtil;
import cc.chinagps.gateway.unit.oem.upload.UploadHandler;
import cc.chinagps.gateway.unit.udp.UdpServer;

/**
 * 指令应答
 */
public class Cmd85 implements UploadHandler {
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	@Override
	public boolean handlerPackage(OEMPackage pkg, UdpServer server,
			UnitSocketSession session) throws Exception {
		// TODO Auto-generated method stub
		byte[] data = pkg.getData();
		byte mainCmdId = data[0];
		byte result = data[2];
		int cmdId = 0;
		logger_debug.debug("[oem] 0x85 cmd response:"+pkg);
		switch (mainCmdId) {
		/*
		 * case CmdUtil.CMD_ID_POSITION://清除报警状态位 return
		 * getQueryPositionCmd(userCmd, unitSession,(byte)0x22);
		 */
		case (byte) 0x32:// 车台复位
			cmdId = CmdUtil.CMD_ID_RESET;
			break;
		case (byte) 0x16:// 设置定时上报
			cmdId = CmdUtil.CMD_ID_START_UPLOAD_BY_INTERVAL;
			logger_debug.debug("[oem] cmd 0x16 response:"+pkg);
			break;
		case (byte) 0x70:// 设置熄火定时上报
			cmdId = CmdUtil.CMD_ID_START_ACC_OFF_DELIVERY;
			logger_debug.debug("[oem] cmd 0x70 response:"+pkg);
			break;
		/*
		 * case CmdUtil.CMD_ID_RESET://开启防盗 return getQueryPositionCmd(userCmd,
		 * unitSession,(byte)0x63); case CmdUtil.CMD_ID_RESET://关闭防盗 return
		 * getQueryPositionCmd(userCmd, unitSession,(byte)0x64);
		 */
		case (byte) 0x67:// 远程控制开车门
			cmdId = CmdUtil.CMD_ID_OPEN_DOOR;
			break;
		case (byte) 0x68:// 远程控制锁车门
			cmdId = CmdUtil.CMD_ID_LOCK_DOOR;
			break;
		case (byte) 0x39:// 远程控制终端断油
			cmdId = CmdUtil.CMD_ID_CUT_OFF_OIL;
			break;
		case (byte) 0x38:// 远程控制终端恢复油路
			cmdId = CmdUtil.CMD_ID_RESUME_OIL;
			break;
		default:
			cmdId = 0;
		}
		if (cmdId != 0) {
			String callLetter = session.getUnitInfo().getCallLetter();
			String sn = CmdUtil.getCmdCacheSn(callLetter, cmdId);
			ServerToUnitCommand cache = server.getServerToUnitCommandCache()
					.getAndRemoveCommand(sn);
			if (result == 1) {
				if (cache != null) {
					Command usercmd = cache.getUserCommand();

					Builder builder = CommandBuff.CommandResponse.newBuilder();
					// 设置回应的通用部分
					CmdResponseUtil.updateResponseProtoSuccessByUserCommand(
							builder, callLetter, usercmd);
					// gpsInfo
					//OEMUploadUtil.setUpResponseByGPSInfo(callLetter, builder,
					//		null);
					
					//回应到MQ
					cache.getUdpServer().getExportMQ().addCommandResponse(builder.build());
					logger_debug.debug("[oem] cmdResponse success TO MQ cmdId:"+usercmd.getCmdId()+";callLetter:"+callLetter+";sn:"+usercmd.getSn());
				}
			} else {
				if (cache != null) {
					Command usercmd = cache.getUserCommand();

					Builder builder = CommandBuff.CommandResponse.newBuilder();
					// 设置回应的通用部分
					CmdResponseUtil.updateResponseProtoFailedByUserCommand(
							builder, callLetter, usercmd);
					// gpsInfo
					//OEMUploadUtil.setUpResponseByGPSInfo(callLetter, builder,
					//		null);

					//回应到MQ
					cache.getUdpServer().getExportMQ().addCommandResponse(builder.build());
					logger_debug.debug("[oem] cmdResponse failed TO MQ cmdId:"+usercmd.getCmdId()+";callLetter:"+callLetter+";sn:"+usercmd.getSn());
				}
			}
		}
		return true;
	}

}