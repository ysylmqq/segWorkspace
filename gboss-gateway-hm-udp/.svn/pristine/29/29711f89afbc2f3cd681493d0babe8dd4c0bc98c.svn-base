package cc.chinagps.gateway.unit.leopaard.upload;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import cc.chinagps.gateway.aplan.pkg.APlanUtil;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.common.UnitCommon;
import cc.chinagps.gateway.unit.leopaard.pkg.LeopaardPackage;
import cc.chinagps.gateway.unit.leopaard.upload.cmds.Cmd02;
import cc.chinagps.gateway.unit.leopaard.upload.cmds.Cmd04;
import cc.chinagps.gateway.unit.leopaard.upload.cmds.Cmd05;
import cc.chinagps.gateway.unit.leopaard.upload.cmds.Cmd06;
import cc.chinagps.gateway.unit.leopaard.upload.cmds.Cmd07;
import cc.chinagps.gateway.unit.leopaard.upload.cmds.Cmd08;
import cc.chinagps.gateway.unit.leopaard.upload.cmds.Cmd80;
import cc.chinagps.gateway.unit.leopaard.upload.cmds.Cmd81;
import cc.chinagps.gateway.unit.leopaard.upload.cmds.Cmd82;
import cc.chinagps.gateway.unit.leopaard.upload.cmds.Cmd83;
import cc.chinagps.gateway.unit.leopaard.upload.cmds.Cmd84;
import cc.chinagps.gateway.unit.leopaard.upload.cmds.Cmd85;
import cc.chinagps.gateway.unit.leopaard.upload.cmds.Cmd86;
import cc.chinagps.gateway.unit.leopaard.upload.cmds.UploadHandler;
import cc.chinagps.gateway.util.HexUtil;
import cc.chinagps.gateway.util.SystemConfig;

public class LeopaardUploadCmdHandlers {
	public LeopaardUploadCmdHandlers(){
		initHandlers();
	}
	
	private Map<Byte, UploadHandler> handlers = new HashMap<Byte, UploadHandler>();
	
	private void initHandlers(){
		//handlers.put((byte) 0x01, new Cmd01());
		handlers.put((byte) 0x02, new Cmd02());
		//handlers.put((byte) 0x03, new Cmd03());
		handlers.put((byte) 0x04, new Cmd04());
		handlers.put((byte) 0x05, new Cmd05());
		handlers.put((byte) 0x06, new Cmd06());
		handlers.put((byte) 0x07, new Cmd07());
		handlers.put((byte) 0x08, new Cmd08());
		handlers.put((byte) 0x80, new Cmd80());
		handlers.put((byte) 0x81, new Cmd81());
		handlers.put((byte) 0x82, new Cmd82());
		handlers.put((byte) 0x83, new Cmd83());
		handlers.put((byte) 0x84, new Cmd84());
		handlers.put((byte) 0x85, new Cmd85());
		handlers.put((byte) 0x86, new Cmd86());
	}
	
	private Logger logger_unknown = Logger.getLogger(LogManager.LOGGER_NAME_UNKNOWN_CMD);
	
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	
	public void handleUpload(byte[] source, UnitServer server, UnitSocketSession session) throws Exception{
		logger_debug.debug("[Leopaard] recev data:"+HexUtil.byteToHexStr(source));
		LeopaardPackage pkg = LeopaardPackage.parse(source);
		byte cmdId = pkg.getHead().getCmdId();
		String callLetter = null;
		if(session.getUnitInfo()!=null){
			callLetter = session.getUnitInfo().getCallLetter();
		}
		logger_debug.debug("[Leopaard] recev  cmdId:"+HexUtil.byteToHexStr(new byte[]{cmdId})+";callLetter:"+callLetter+";data:"+HexUtil.byteToHexStr(source));
		
		//0x80(登录包)在解析出呼号后发送trace数据
		if(cmdId != (byte) 0x06){
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
				
				logger_unknown.info("[Leopaard]没有子处理函数, 呼号:" + call + ", 源码:" + HexUtil.byteToHexStr(source));
			}
		}else{
			String call = null;
			if(session.getUnitInfo() != null){
				call = session.getUnitInfo().getCallLetter();
			}
			
			logger_unknown.info("[Leopaard]没有处理函数, 呼号:" + call + ", 源码:" + HexUtil.byteToHexStr(source));
		}
		
		//A计划
		sendDataToAPlanClients(source, server, session);
		
		//警情
		sendDataToAPlanAlarmClients(source, server, session);
	}
	
	/**
	 * 群发给A计划客户端
	 */
	private void sendDataToAPlanClients(byte[] pkg, UnitServer server, UnitSocketSession session){
		//佛山网关过滤链路检测
		String isFoshanGw = SystemConfig.getSystemProperty("is_foshan_gw");
		if (StringUtils.isNotBlank(isFoshanGw)) {
			boolean flag = Boolean.valueOf(isFoshanGw);
			if (flag) {
				if (pkg.length >= 3 && pkg[1] == HEARBEAT_START) {
					// 过滤心跳包
					return;
				}
			}
		}
		
		byte[] encode = APlanUtil.encodeAPlanDeliverData(pkg, server.getAPlanServer(), session);
		server.getAPlanServer().broadcastData(encode);
	}
	
	/**
	 * 群发给A计划警情客户端
	 */
	private static final byte _k = 0x28;	//(
	private static final byte _I = 0x49;	//I
	private static final byte _T = 0x54;	//T
	private static final byte _V = 0x56;	//V
	
	private static final byte HEARBEAT_START = (byte) 0xF0;
	private static final byte LOGIN_START = (byte) 0x80;
	
	private void sendDataToAPlanAlarmClients(byte[] pkg, UnitServer server, UnitSocketSession session){
		if(pkg.length >= 17 && pkg[13] == _k && pkg[14] == _I && pkg[15] == _T && pkg[16] == _V){
			//过滤(ITV
			return;
		}
		
		if(pkg.length >= 3 && pkg[1] == HEARBEAT_START){
			//过滤心跳包
			return;
		}
		
		if(pkg.length >= 3 && pkg[1] == LOGIN_START){
			//过滤登录包
			return;
		}
		
		byte[] encode = APlanUtil.encodeAPlanDeliverData(pkg, server.getAPlanAlarmServer(), session);
		server.getAPlanAlarmServer().broadcastData(encode);
	}
}