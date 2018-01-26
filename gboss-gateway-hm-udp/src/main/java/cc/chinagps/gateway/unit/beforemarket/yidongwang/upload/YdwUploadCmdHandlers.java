package cc.chinagps.gateway.unit.beforemarket.yidongwang.upload;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beforemarket.common.UploadHandler;
import cc.chinagps.gateway.unit.beforemarket.common.pkg.BeforeMarketPackage;
import cc.chinagps.gateway.unit.beforemarket.common.util.BeforeMarketPkgUtil;
import cc.chinagps.gateway.unit.beforemarket.yidongwang.upload.cmd.Cmd01;
import cc.chinagps.gateway.unit.beforemarket.yidongwang.upload.cmd.Cmd02;
import cc.chinagps.gateway.unit.beforemarket.yidongwang.upload.cmd.Cmd03;
import cc.chinagps.gateway.unit.beforemarket.yidongwang.upload.cmd.Cmd41;
import cc.chinagps.gateway.unit.common.UnitCommon;
import cc.chinagps.gateway.util.HexUtil;

public class YdwUploadCmdHandlers {
	private Logger logger_unkown = Logger.getLogger(LogManager.LOGGER_NAME_UNKNOWN_CMD);
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	public YdwUploadCmdHandlers(){
		initHandlers();
	}
	
	private Map<Byte, UploadHandler> handlers = new HashMap<Byte, UploadHandler>();
	
	private void initHandlers(){
		handlers.put((byte) 0x01, new Cmd01());
		handlers.put((byte) 0x02, new Cmd02());
		handlers.put((byte) 0x03, new Cmd03());
		
		handlers.put((byte) 0x41, new Cmd41());
	}
	
	private static final byte CMD_ID_CONNECT = 0x1;
	private static final byte SUB_CMD_ID_LOGIN = 0x1;
	//�Ƿ�Ϊ��¼��
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
		String callLetter =null;
		if(session.getUnitInfo() != null){
			callLetter = session.getUnitInfo().getCallLetter();
		}
		logger_debug.debug("[ydw] recev data ["+callLetter+"]:"+HexUtil.byteToHexStr(source));
		BeforeMarketPackage pkg = BeforeMarketPackage.parse(source, c1, d1);
		//(��¼��)�ڽ��������ź���trace����
		if(!isLogin(pkg)){
			//Trace����(�����л�ظ���̨,���ڴ���ǰ��)
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
				
				logger_unkown.info("[ydw]û���Ӵ�������, ����:" + call + ", Դ��:" + HexUtil.byteToHexStr(source));
			}
		}else{
			String call = null;
			if(session.getUnitInfo() != null){
				call = session.getUnitInfo().getCallLetter();
			}
			
			logger_unkown.info("[ydw]û�д�������, ����:" + call + ", Դ��:" + HexUtil.byteToHexStr(source));
		}
		
		//A�ƻ�
		UnitCommon.sendDataToAPlanClients(source, server, session);
		
		//����
		UnitCommon.sendDataToAPlanAlarmClients(source, server, session);
	}
}