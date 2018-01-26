package cc.chinagps.gateway.unit.db44.upload;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.common.UnitCommon;
import cc.chinagps.gateway.unit.db44.pkg.DB44Package;
import cc.chinagps.gateway.unit.db44.upload.cmd.Cmd00;
import cc.chinagps.gateway.unit.db44.upload.cmd.Cmd01;
import cc.chinagps.gateway.unit.db44.upload.cmd.Cmd02;
import cc.chinagps.gateway.unit.db44.upload.cmd.Cmd04;
import cc.chinagps.gateway.unit.db44.upload.cmd.Cmd06;
import cc.chinagps.gateway.unit.db44.upload.cmd.Cmd0F;
import cc.chinagps.gateway.unit.db44.upload.cmd.Cmd13;
import cc.chinagps.gateway.unit.db44.upload.cmd.Cmd15;
import cc.chinagps.gateway.unit.db44.upload.cmd.Cmd16;
import cc.chinagps.gateway.unit.db44.upload.cmd.Cmd17;
import cc.chinagps.gateway.unit.db44.upload.cmd.Cmd19;
import cc.chinagps.gateway.unit.db44.upload.cmd.Cmd1B;
import cc.chinagps.gateway.unit.db44.upload.cmd.Cmd1C;
import cc.chinagps.gateway.unit.db44.upload.cmd.Cmd80;
import cc.chinagps.gateway.unit.db44.upload.cmd.Cmd81;
import cc.chinagps.gateway.unit.db44.upload.cmd.Cmd82;
import cc.chinagps.gateway.unit.db44.upload.cmd.CmdF6;
import cc.chinagps.gateway.unit.db44.upload.cmd.CmdFD;
import cc.chinagps.gateway.util.HexUtil;

public class DB44UploadCmdHandlers {
	private Logger logger_unkown = Logger.getLogger(LogManager.LOGGER_NAME_UNKNOWN_CMD);
	
	public DB44UploadCmdHandlers(){
		initHandlers();
	}
	
	private Map<Byte, UploadHandler> handlers = new HashMap<Byte, UploadHandler>();
	
	private void initHandlers(){
		handlers.put((byte) 0x00, new Cmd00());
		handlers.put((byte) 0x01, new Cmd01());
		handlers.put((byte) 0x02, new Cmd02());
		handlers.put((byte) 0x04, new Cmd04());
		handlers.put((byte) 0x06, new Cmd06());
		handlers.put((byte) 0x0F, new Cmd0F());
		
		handlers.put((byte) 0x13, new Cmd13());
		handlers.put((byte) 0x15, new Cmd15());
		handlers.put((byte) 0x16, new Cmd16());
		handlers.put((byte) 0x17, new Cmd17());
		handlers.put((byte) 0x19, new Cmd19());
		handlers.put((byte) 0x1B, new Cmd1B());
		handlers.put((byte) 0x1C, new Cmd1C());
		
		handlers.put((byte) 0x80, new Cmd80());
		handlers.put((byte) 0x81, new Cmd81());
		handlers.put((byte) 0x82, new Cmd82());
		
		handlers.put((byte) 0xF6, new CmdF6());
		handlers.put((byte) 0xFD, new CmdFD());
	}
	
	private static final byte MODE_UPLOAD = 0x55;
	
	public void handleUpload(byte[] source, UnitServer server, UnitSocketSession session) throws Exception{
		DB44Package pkg = DB44Package.parse(source);
		
		byte mode = pkg.getProtocolIdMode();
		if(mode != MODE_UPLOAD){
			String call = null;
			if(session.getUnitInfo() != null){
				call = session.getUnitInfo().getCallLetter();
			}
			
			logger_unkown.info("[db44]没有处理函数, 呼号:" + call + ", 源码:" + HexUtil.byteToHexStr(source));
			return;
		}
		
		byte cmdId = pkg.getProtocolIdNumber();
		
		//0x80(登录包)在解析出呼号后发送trace数据
		if(cmdId != (byte) 0x80){
			//Trace功能(处理中会回复车台,放在处理前面)
			UnitCommon.sendUploadTrace(source, server, session);
		}
		
		UploadHandler handler = handlers.get(cmdId);
		if(handler != null){
			boolean canHandle = handler.handlerPackage(pkg, server, session);
			if(!canHandle){
				String call = null;
				if(session.getUnitInfo() != null){
					call = session.getUnitInfo().getCallLetter();
				}
				
				logger_unkown.info("[db44]没有子处理函数, 呼号:" + call + ", 源码:" + HexUtil.byteToHexStr(source));
			}
		}else{
			String call = null;
			if(session.getUnitInfo() != null){
				call = session.getUnitInfo().getCallLetter();
			}
			
			logger_unkown.info("[db44]没有处理函数, 呼号:" + call + ", 源码:" + HexUtil.byteToHexStr(source));
		}
		
		//A计划
		UnitCommon.sendDataToAPlanClients(source, server, session);
		
		//警情
		UnitCommon.sendDataToAPlanAlarmClients(source, server, session);
	}
}