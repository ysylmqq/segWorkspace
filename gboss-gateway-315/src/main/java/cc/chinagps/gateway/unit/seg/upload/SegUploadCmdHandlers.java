package cc.chinagps.gateway.unit.seg.upload;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import cc.chinagps.gateway.aplan.pkg.APlanUtil;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.common.UnitCommon;
import cc.chinagps.gateway.unit.seg.upload.cmds.Cmd10;
import cc.chinagps.gateway.unit.seg.upload.cmds.Cmd11;
import cc.chinagps.gateway.unit.seg.upload.cmds.Cmd12;
import cc.chinagps.gateway.unit.seg.upload.cmds.Cmd38;
import cc.chinagps.gateway.unit.seg.upload.cmds.Cmd39;
import cc.chinagps.gateway.unit.seg.upload.cmds.Cmd4A;
import cc.chinagps.gateway.unit.seg.upload.cmds.Cmd4B;
import cc.chinagps.gateway.unit.seg.upload.cmds.Cmd56;
import cc.chinagps.gateway.unit.seg.upload.cmds.Cmd68;
import cc.chinagps.gateway.unit.seg.upload.cmds.Cmd70;
import cc.chinagps.gateway.unit.seg.upload.cmds.Cmd72;
import cc.chinagps.gateway.unit.seg.upload.cmds.Cmd73;
import cc.chinagps.gateway.unit.seg.upload.cmds.Cmd74;
import cc.chinagps.gateway.unit.seg.upload.cmds.Cmd7B;
import cc.chinagps.gateway.unit.seg.upload.cmds.Cmd80;
import cc.chinagps.gateway.unit.seg.upload.cmds.Cmd81;
import cc.chinagps.gateway.unit.seg.upload.cmds.Cmd82;
import cc.chinagps.gateway.unit.seg.upload.cmds.Cmd83;
import cc.chinagps.gateway.unit.seg.upload.cmds.Cmd90;
import cc.chinagps.gateway.unit.seg.upload.cmds.Cmd92;
import cc.chinagps.gateway.unit.seg.upload.cmds.Cmd93;
import cc.chinagps.gateway.unit.seg.upload.cmds.Cmd96;
import cc.chinagps.gateway.unit.seg.upload.cmds.CmdA0;
import cc.chinagps.gateway.unit.seg.upload.cmds.CmdA1;
import cc.chinagps.gateway.unit.seg.upload.cmds.CmdB0;
import cc.chinagps.gateway.unit.seg.upload.cmds.CmdB1;
import cc.chinagps.gateway.unit.seg.upload.cmds.CmdB2;
import cc.chinagps.gateway.unit.seg.upload.cmds.CmdB3;
import cc.chinagps.gateway.unit.seg.upload.cmds.CmdB4;
import cc.chinagps.gateway.unit.seg.upload.cmds.CmdB6;
import cc.chinagps.gateway.unit.seg.upload.cmds.CmdC6;
import cc.chinagps.gateway.unit.seg.upload.cmds.CmdC7;
import cc.chinagps.gateway.unit.seg.upload.cmds.CmdE0;
import cc.chinagps.gateway.unit.seg.upload.cmds.CmdE8;
import cc.chinagps.gateway.unit.seg.upload.cmds.CmdF0;
import cc.chinagps.gateway.unit.seg.upload.cmds.CmdF1;
import cc.chinagps.gateway.unit.seg.upload.cmds.CmdF3;
import cc.chinagps.gateway.unit.seg.upload.cmds.CmdF4;
import cc.chinagps.gateway.unit.seg.upload.cmds.CmdF7;
import cc.chinagps.gateway.util.HexUtil;
import cc.chinagps.gateway.util.SystemConfig;

public class SegUploadCmdHandlers {
	public SegUploadCmdHandlers(){
		initHandlers();
	}
	
	private Map<Byte, UploadHandler> handlers = new HashMap<Byte, UploadHandler>();
	
	private void initHandlers(){
		handlers.put((byte) 0x10, new Cmd10());
		handlers.put((byte) 0x11, new Cmd11());
		handlers.put((byte) 0x12, new Cmd12());
		
		handlers.put((byte) 0x38, new Cmd38());
		handlers.put((byte) 0x39, new Cmd39());
		
		handlers.put((byte) 0x4A, new Cmd4A());
		handlers.put((byte) 0x4B, new Cmd4B());
		
		handlers.put((byte) 0x56, new Cmd56());
		
		handlers.put((byte) 0x68, new Cmd68());
		
		handlers.put((byte) 0x70, new Cmd70());
		handlers.put((byte) 0x72, new Cmd72());
		handlers.put((byte) 0x73, new Cmd73());
		handlers.put((byte) 0x74, new Cmd74());
		handlers.put((byte) 0x7B, new Cmd7B());
		
		handlers.put((byte) 0x80, new Cmd80());
		handlers.put((byte) 0x81, new Cmd81());
		handlers.put((byte) 0x82, new Cmd82());
		handlers.put((byte) 0x83, new Cmd83());
		
		handlers.put((byte) 0x90, new Cmd90());
		handlers.put((byte) 0x92, new Cmd92());
		handlers.put((byte) 0x93, new Cmd93());
		handlers.put((byte) 0x96, new Cmd96());
		
		handlers.put((byte) 0xA0, new CmdA0());
		handlers.put((byte) 0xA1, new CmdA1());
		
		handlers.put((byte) 0xB0, new CmdB0());
		handlers.put((byte) 0xB1, new CmdB1());
		handlers.put((byte) 0xB2, new CmdB2());
		handlers.put((byte) 0xB3, new CmdB3());
		handlers.put((byte) 0xB4, new CmdB4());
		handlers.put((byte) 0xB6, new CmdB6());
		
		handlers.put((byte) 0xC6, new CmdC6());
		handlers.put((byte) 0xC7, new CmdC7());
		
		handlers.put((byte) 0xE0, new CmdE0());
		handlers.put((byte) 0xE8, new CmdE8());
		
		handlers.put((byte) 0xF0, new CmdF0());
		handlers.put((byte) 0xF1, new CmdF1());
		handlers.put((byte) 0xF3, new CmdF3());
		handlers.put((byte) 0xF4, new CmdF4());
		handlers.put((byte) 0xF7, new CmdF7());
	}
	
	private Logger logger_unknown = Logger.getLogger(LogManager.LOGGER_NAME_UNKNOWN_CMD);
	
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	
	public void handleUpload(byte[] source, UnitServer server, UnitSocketSession session) throws Exception{
		//byte[] unescape = SegPkgUtil.unescape(source);
		//byte cmdId = unescape[1];
		byte cmdId = source[1];
		String callLetter = null;
		if(session.getUnitInfo()!=null){
			callLetter = session.getUnitInfo().getCallLetter();
		}
		logger_debug.debug("[seg] recev  cmdId:"+HexUtil.byteToHexStr(new byte[]{cmdId})+";callLetter:"+callLetter+";data:"+HexUtil.byteToHexStr(source));
		//0x80(登录包)在解析出呼号后发送trace数据
		if(cmdId != (byte) 0x80){
			//Trace功能(处理中会回复车台,放在处理前面)
			UnitCommon.sendUploadTrace(source, server, session);
		}
		
		UploadHandler handler = handlers.get(cmdId);
		if(handler != null){
			boolean canHandle = handler.handlerPackage(source, server, session);
			if(!canHandle){
				String call = null;
				if(session.getUnitInfo() != null){
					call = session.getUnitInfo().getCallLetter();
				}
				
				logger_unknown.info("[seg]没有子处理函数, 呼号:" + call + ", 源码:" + HexUtil.byteToHexStr(source));
			}
		}else{
			String call = null;
			if(session.getUnitInfo() != null){
				call = session.getUnitInfo().getCallLetter();
			}
			
			logger_unknown.info("[seg]没有处理函数, 呼号:" + call + ", 源码:" + HexUtil.byteToHexStr(source));
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