package cc.chinagps.gateway.unit.kelong.upload;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.seg.lib.util.Util;

import cc.chinagps.gateway.aplan.pkg.APlanUtil;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.common.UnitCommon;
import cc.chinagps.gateway.unit.kelong.pkg.KeLongPackage;
import cc.chinagps.gateway.unit.kelong.upload.cmds.Cmd0000;
import cc.chinagps.gateway.unit.kelong.upload.cmds.Cmd0002;
import cc.chinagps.gateway.unit.kelong.upload.cmds.Cmd0003;
import cc.chinagps.gateway.unit.kelong.upload.cmds.Cmd0004;
import cc.chinagps.gateway.unit.kelong.upload.cmds.Cmd0007;
import cc.chinagps.gateway.unit.kelong.upload.cmds.Cmd0010;
import cc.chinagps.gateway.unit.kelong.upload.cmds.Cmd0011;
import cc.chinagps.gateway.unit.kelong.upload.cmds.Cmd0012;
import cc.chinagps.gateway.unit.kelong.upload.cmds.Cmd0021;
import cc.chinagps.gateway.unit.kelong.upload.cmds.Cmd0106;
import cc.chinagps.gateway.unit.kelong.upload.cmds.Cmd0165;
import cc.chinagps.gateway.unit.kelong.upload.cmds.Cmd0300;
import cc.chinagps.gateway.unit.kelong.upload.cmds.UploadHandler;
import cc.chinagps.gateway.unit.kelong.util.KeLongUploadUtil;
import cc.chinagps.gateway.util.HexUtil;
import cc.chinagps.gateway.util.SystemConfig;

public class KeLongUploadCmdHandlers {
	private Map<Short, UploadHandler> handlers = new HashMap<Short, UploadHandler>();
	private Logger logger_unknown = Logger.getLogger(LogManager.LOGGER_NAME_UNKNOWN_CMD);	
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	
	public KeLongUploadCmdHandlers(){
		initHandlers();
	}
	
	private void initHandlers(){
		handlers.put((short) 0x0000, new Cmd0000());
		handlers.put((short) 0x0002, new Cmd0002());
		handlers.put((short) 0x0003, new Cmd0003());
		handlers.put((short) 0x0004, new Cmd0004());
		handlers.put((short) 0x0007, new Cmd0007());
		handlers.put((short) 0x0010, new Cmd0010());
		handlers.put((short) 0x0106, new Cmd0106());
		handlers.put((short) 0x0021, new Cmd0021());
		handlers.put((short) 0x0300, new Cmd0300());
		handlers.put((short) 0x0011, new Cmd0011());//2017-05-23 by dy
		handlers.put((short) 0x0012, new Cmd0012());//2017-05-23 by dy
		handlers.put((short) 0x0165, new Cmd0165());//2017-05-23 by dy
	}
	
	public void handleUpload(byte[] source, UnitServer server, UnitSocketSession session) throws Exception{		
			KeLongPackage pkg = KeLongPackage.parse(source);
			short cmdId = pkg.getHead().getMsgId();
			String callLetter = null;
			if(session.getUnitInfo()!=null){
				callLetter = session.getUnitInfo().getCallLetter();
			}
			logger_debug.debug("[KeLong]recev cmdId:"+HexUtil.byteToHexStr(Util.getShortByte(cmdId))+";callLetter:"+callLetter+";source:"+HexUtil.byteToHexStr(source));
			
			if(cmdId != 0x0002){
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
					logger_unknown.info("[KeLong]没有子处理函数, 呼号:" + call + ", 源码:" + HexUtil.byteToHexStr(source));
				}
			}else{
				String call = null;
				if(session.getUnitInfo() != null){
					call = session.getUnitInfo().getCallLetter();
				}
				
				logger_unknown.info("[KeLong]没有处理函数, 呼号:" + call + ", 源码:" + HexUtil.byteToHexStr(source));
				
				KeLongUploadUtil.commonMsgAck(session, pkg, (short) 0x8000, cmdId, (byte) 0x00);
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