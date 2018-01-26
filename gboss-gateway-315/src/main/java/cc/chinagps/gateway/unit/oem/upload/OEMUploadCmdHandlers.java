package cc.chinagps.gateway.unit.oem.upload;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.common.UnitCommon;
import cc.chinagps.gateway.unit.oem.pkg.OEMPackage;
import cc.chinagps.gateway.unit.oem.upload.cmd.Cmd85;
import cc.chinagps.gateway.unit.oem.upload.cmd.Cmd8E;
import cc.chinagps.gateway.unit.oem.upload.cmd.Cmd90;
import cc.chinagps.gateway.unit.oem.upload.cmd.Cmd91;
import cc.chinagps.gateway.unit.oem.upload.cmd.Cmd92;
import cc.chinagps.gateway.unit.oem.upload.cmd.Cmd94;
import cc.chinagps.gateway.unit.oem.upload.cmd.Cmd99;
import cc.chinagps.gateway.unit.oem.upload.cmd.CmdB1;
import cc.chinagps.gateway.unit.udp.UdpServer;
import cc.chinagps.gateway.util.HexUtil;

public class OEMUploadCmdHandlers {
private Logger logger_unkown = Logger.getLogger(LogManager.LOGGER_NAME_UNKNOWN_CMD);
private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);	
	public OEMUploadCmdHandlers(){
		initHandlers();
	}
	
	private Map<Integer, UploadHandler> handlers = new HashMap<Integer, UploadHandler>();
	
	private void initHandlers(){
		handlers.put(0x85, new Cmd85());
		handlers.put(0x90, new Cmd90());
		handlers.put(0x8E, new Cmd8E());
		handlers.put(0x91, new Cmd91());
		handlers.put(0x92, new Cmd92());
		handlers.put(0x94, new Cmd94());
		handlers.put(0x99, new Cmd99());
		handlers.put(0xB1, new CmdB1());
	}
	
	public void handleUpload(OEMPackage pkg, UdpServer server, UnitSocketSession session) throws Exception{
		String call = null;
		if(session.getUnitInfo() != null){
			call = session.getUnitInfo().getCallLetter();
		}
		logger_debug.debug("oem recv [" +call + "] pkg:"+pkg);
		UnitCommon.sendUploadTrace(pkg.getSource(), server, session);
		
		int cmdId = (int) pkg.getMainCmdId() & 0xFF;
		UploadHandler handler = handlers.get(cmdId);
		if(handler != null){
			boolean canHandle = handler.handlerPackage(pkg, server, session);
			if(!canHandle){
				logger_unkown.info("[oem]没有子处理函数, 呼号:" + call + ", 源码:" + HexUtil.byteToHexStr(pkg.getSource()));
			}
		}else{
			logger_unkown.info("[oem]没有处理函数, 呼号:" + call + ", 源码:" + HexUtil.byteToHexStr(pkg.getSource()));
		}
		
		//A计划
		UnitCommon.sendDataToAPlanClientsFromUdpServer(pkg.getSource(), server, session);
		
		//警情
		UnitCommon.sendDataToAPlanAlarmClientsFromUdpServer(pkg.getSource(), server, session);
	}
}