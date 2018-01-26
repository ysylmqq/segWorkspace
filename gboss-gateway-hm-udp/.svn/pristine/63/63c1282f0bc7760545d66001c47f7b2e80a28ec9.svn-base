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
import cc.chinagps.gateway.unit.oem.upload.bean.OEMGPSInfo;
import cc.chinagps.gateway.unit.oem.util.OEMPkgUtil;
import cc.chinagps.gateway.unit.udp.UdpServer;

/**
 * 点名上报
 */
public class Cmd91 implements UploadHandler {
	private Logger logger_debug = Logger
			.getLogger(LogManager.LOGGER_NAME_DEBUG);
	@Override
	public boolean handlerPackage(OEMPackage pkg, UdpServer server,
			UnitSocketSession session) throws Exception {
		// TODO Auto-generated method stub
		
		byte[] body = pkg.getData();
		OEMGPSInfo gps = OEMGPSInfo.parse(body, 0,false);
		logger_debug.debug("OEM Cmd91 "+gps);
		String callLetter = session.getUnitInfo().getCallLetter();
		int cmdId = CmdUtil.CMD_ID_POSITION;
		String sn = CmdUtil.getCmdCacheSn(callLetter, cmdId);
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(sn);
		if(cache != null) {
			try {
				Command usercmd = cache.getUserCommand();
				
				Builder builder = CommandBuff.CommandResponse.newBuilder();
				//设置回应的通用部分
				CmdResponseUtil.updateResponseProtoSuccessByUserCommand(builder, callLetter, usercmd);
				//gpsInfo
				OEMUploadUtil.setUpResponseByGPSInfo(callLetter, builder, gps);
				
				//回应到MQ
				cache.getUdpServer().getExportMQ().addCommandResponse(builder.build());
			} catch (Exception e) {
				// TODO: handle exception
				logger_debug.debug("cmd91 response to MQ error:"+e.getMessage());
			}
			
		}
		
		OEMUploadUtil.handleGPS(server, session, pkg, gps);
		// ack
		byte[] source = OEMPkgUtil.getCommonResponseToUnit(session,
				(byte) 0x20, pkg);
		session.sendData(source);

		return true;
	}

}