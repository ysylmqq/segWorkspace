package cc.chinagps.gateway.unit.beforemarket.kaiyi.upload;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beforemarket.common.UploadHandler;
import cc.chinagps.gateway.unit.beforemarket.common.pkg.BeforeMarketPackage;
import cc.chinagps.gateway.unit.beforemarket.common.util.BeforeMarketPkgUtil;
import cc.chinagps.gateway.unit.beforemarket.kaiyi.upload.cmd.Cmd01;
import cc.chinagps.gateway.unit.beforemarket.kaiyi.upload.cmd.Cmd02;
import cc.chinagps.gateway.unit.beforemarket.kaiyi.upload.cmd.Cmd03;
import cc.chinagps.gateway.unit.beforemarket.kaiyi.upload.cmd.Cmd05;
import cc.chinagps.gateway.unit.beforemarket.kaiyi.upload.cmd.Cmd11;
import cc.chinagps.gateway.unit.beforemarket.kaiyi.upload.cmd.Cmd1A;
import cc.chinagps.gateway.unit.beforemarket.kaiyi.upload.cmd.Cmd41;
import cc.chinagps.gateway.unit.beforemarket.kaiyi.upload.cmd.Cmd42;
import cc.chinagps.gateway.unit.common.UnitCommon;
import cc.chinagps.gateway.util.HexUtil;

public class KaiyiUploadCmdHandlers {
	private Logger logger_unkown = Logger.getLogger(LogManager.LOGGER_NAME_UNKNOWN_CMD);
	
	public KaiyiUploadCmdHandlers(){
		initHandlers();
	}
	
	private Map<Byte, UploadHandler> handlers = new HashMap<Byte, UploadHandler>();
	
	private void initHandlers(){
		handlers.put((byte) 0x01, new Cmd01());
		handlers.put((byte) 0x02, new Cmd02());
		handlers.put((byte) 0x03, new Cmd03());
		handlers.put((byte) 0x05, new Cmd05());
		
		handlers.put((byte) 0x11, new Cmd11());
		handlers.put((byte) 0x1A, new Cmd1A());
		
		handlers.put((byte) 0x41, new Cmd41());
		handlers.put((byte) 0x42, new Cmd42());
	}
	
	private static final byte CMD_ID_CONNECT = 0x1;
	private static final byte SUB_CMD_ID_LOGIN = 0x1;
	//是否为登录包
	private boolean isLogin(BeforeMarketPackage pkg){
		byte cmdId = pkg.getHead().getMsgType();
		if(cmdId != CMD_ID_CONNECT){
			return false;
		}
		
		byte[] body = pkg.getBody();
		byte sub_cmdId = body[0];
		if(sub_cmdId == SUB_CMD_ID_LOGIN){
			return true;
		}
		
		return false;
	}
	
	public void handleUpload(byte[] source, UnitServer server, UnitSocketSession session) throws Exception{
		int c1 = BeforeMarketPkgUtil.getC1(session);
		int d1 = BeforeMarketPkgUtil.getD1(session);
		
		BeforeMarketPackage pkg = BeforeMarketPackage.parse(source, c1, d1);
		//(登录包)在解析出呼号后发送trace数据
		if(!isLogin(pkg)){
			//Trace功能(处理中会回复车台,放在处理前面)
			UnitCommon.sendUploadTrace(source, server, session);
		}
		
		byte cmdId = pkg.getHead().getMsgType();
		UploadHandler handler = handlers.get(cmdId);
		if(handler != null){
			boolean canHandle = handler.handlerPackage(pkg, server, session);
			if(!canHandle){
				String call = null;
				if(session.getUnitInfo() != null){
					call = session.getUnitInfo().getCallLetter();
				}
				
				logger_unkown.info("[kaiyi]没有子处理函数, 呼号:" + call + ", 源码:" + HexUtil.byteToHexStr(source));
			}
		}else{
			String call = null;
			if(session.getUnitInfo() != null){
				call = session.getUnitInfo().getCallLetter();
			}
			
			logger_unkown.info("[kaiyi]没有处理函数, 呼号:" + call + ", 源码:" + HexUtil.byteToHexStr(source));
		}
		
		//A计划
		UnitCommon.sendDataToAPlanClients(source, server, session);
		
		//警情
		UnitCommon.sendDataToAPlanAlarmClients(source, server, session);
	}
}
