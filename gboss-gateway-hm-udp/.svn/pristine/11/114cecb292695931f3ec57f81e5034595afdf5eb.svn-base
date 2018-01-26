package cc.chinagps.gateway.unit.kelx.upload;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.seg.lib.util.Util;

import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;

import cc.chinagps.gateway.unit.common.UnitCommon;
import cc.chinagps.gateway.unit.kelx.pkg.KlxPackage;
import cc.chinagps.gateway.unit.kelx.upload.cmd.Cmd01;
import cc.chinagps.gateway.unit.kelx.upload.cmd.Cmd03;
import cc.chinagps.gateway.unit.kelx.upload.cmd.Cmd04;
import cc.chinagps.gateway.unit.kelx.upload.cmd.Cmd05;
import cc.chinagps.gateway.unit.kelx.upload.cmd.Cmd07;
import cc.chinagps.gateway.unit.kelx.upload.cmd.Cmd08;
import cc.chinagps.gateway.unit.kelx.upload.cmd.Cmd0B;
import cc.chinagps.gateway.unit.kelx.upload.cmd.Cmd0E;
import cc.chinagps.gateway.unit.kelx.upload.cmd.Cmd14;
import cc.chinagps.gateway.util.HexUtil;

public class KlxUploadCmdHandlers {
private Logger logger_unkown = Logger.getLogger(LogManager.LOGGER_NAME_UNKNOWN_CMD);
	
	public KlxUploadCmdHandlers(){
		initHandlers();
	}
	
	private Map<Integer, UploadHandler> handlers = new HashMap<Integer, UploadHandler>();
	
	private void initHandlers(){
		handlers.put(0x01, new Cmd01());
		handlers.put(0x03, new Cmd03());
		handlers.put(0x04, new Cmd04());
		handlers.put(0x05, new Cmd05());
		handlers.put(0x07, new Cmd07());
		handlers.put(0x08, new Cmd08());
		handlers.put(0x0B, new Cmd0B());
		handlers.put(0x0E, new Cmd0E());
		handlers.put(0x14, new Cmd14());
	}
	
	private boolean isLogin(KlxPackage pkg){
		return false;
	}
	
	public void handleUpload(byte[] source, UnitServer server, UnitSocketSession session) throws Exception{
		KlxPackage pkg = KlxPackage.parse(source);
		
		//(登录包)在解析出呼号后发送trace数据
		if(!isLogin(pkg)){
			//Trace功能(处理中会回复车台,放在处理前面)
			UnitCommon.sendUploadTrace(source, server, session);
		}
		
		int cmdId = Util.getShort(pkg.getBody()) & 0xFFFF;
		UploadHandler handler = handlers.get(cmdId);
		if(handler != null){
			boolean canHandle = handler.handlerPackage(pkg, server, session);
			if(!canHandle){
				String call = null;
				if(session.getUnitInfo() != null){
					call = session.getUnitInfo().getCallLetter();
				}
				
				logger_unkown.info("[klx]没有子处理函数, 呼号:" + call + ", 源码:" + HexUtil.byteToHexStr(source));
			}
		}else{
			String call = null;
			if(session.getUnitInfo() != null){
				call = session.getUnitInfo().getCallLetter();
			}
			
			logger_unkown.info("[klx]没有处理函数, 呼号:" + call + ", 源码:" + HexUtil.byteToHexStr(source));
		}
		
		//A计划
		UnitCommon.sendDataToAPlanClients(source, server, session);
		
		//警情
		UnitCommon.sendDataToAPlanAlarmClients(source, server, session);
	}
}