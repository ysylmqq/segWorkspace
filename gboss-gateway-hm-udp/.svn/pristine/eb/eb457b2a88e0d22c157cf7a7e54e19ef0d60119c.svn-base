package cc.chinagps.gateway.unit.pengaoda.upload;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.common.UnitCommon;
import cc.chinagps.gateway.unit.pengaoda.pkg.PengAoDaPackage;
import cc.chinagps.gateway.unit.pengaoda.upload.cmd.CmdTimeUpload;
import cc.chinagps.gateway.unit.pengaoda.upload.cmd.CmdV1;
import cc.chinagps.gateway.unit.pengaoda.upload.cmd.CmdV10;
import cc.chinagps.gateway.unit.pengaoda.upload.cmd.CmdV8;
import cc.chinagps.gateway.unit.pengaoda.upload.cmd.CmdV9;
import cc.chinagps.gateway.unit.pengaoda.util.PengAoDaPkgUtil;
import cc.chinagps.gateway.util.HexUtil;

public class PengAoDaUploadCmdHandlers {
	private Logger logger_unkown = Logger.getLogger(LogManager.LOGGER_NAME_UNKNOWN_CMD);
	
	public PengAoDaUploadCmdHandlers(){
		initHandlers();
	}
	
	private Map<String, UploadHandler> handlers = new HashMap<String, UploadHandler>();
	
	private CmdTimeUpload cmdTimeUpload = new CmdTimeUpload();
	
	private void initHandlers() {
		handlers.put("V1", new CmdV1());
		handlers.put("V8", new CmdV8());
		handlers.put("V9", new CmdV9());
		handlers.put("V10", new CmdV10());
	}
	
	private boolean isLogin(PengAoDaPackage pkg) throws IOException{
		//*HQ,8801001970,V1,...
		byte[] body = pkg.getBody();
		byte flag = body[0];
		if(flag != 0x2A){
			return false;
		}
		
		int idx2 = searchByte(body, (byte) ',', 2);
		int idx3 = searchByte(body, (byte) ',', 3);
		if(idx2 == -1 || idx3 == -1){
			return false;
		}
		
		String cmdId = new String(body, idx2 + 1, idx3 - idx2 - 1, PengAoDaPkgUtil.EN_CHAR_ENCODING);
		return "V1".equals(cmdId);
	}
	
	private int searchByte(byte[] data, byte compare, int count){
		int c = 0;
		for(int i = 0; i < data.length; i++){
			if(data[i] == compare){
				c ++;
				if(c == count){
					return i;
				}
			}
		}
		
		return -1;
	}
	
	public static void main(String[] args) throws IOException {
		String str = "*HQ,8801000297,V8,054743,V,2256.7686,N,11313.1467,E,000.00,000,171013,FFFFFBFF,FF,7505,460,01,9619,31503,C6#";
		//String str = "*HQ, 8856000001,V10,015358,A,2233.8843,N,11405.7480,E,000.50,000,130514,EFE7FBFF,E5,3000,460,00,9786,4192,C6,27376,0,170,168,10, 574,0,0,0# ";
		//String str = "";
		byte[] bs = str.getBytes(PengAoDaPkgUtil.EN_CHAR_ENCODING);
		
		PengAoDaUploadCmdHandlers h = new PengAoDaUploadCmdHandlers();
		
		int idx2 = h.searchByte(bs, (byte) ',', 2);
		int idx3 = h.searchByte(bs, (byte) ',', 3);
		if(idx2 == -1 || idx3 == -1){
			System.out.println("-1-1");
			return;
		}
		String cmdId = new String(bs, idx2 + 1, idx3 - idx2 - 1, PengAoDaPkgUtil.EN_CHAR_ENCODING);
		System.out.println("cmdId:" + cmdId);
	}
	
	public void handleUpload(byte[] source, UnitServer server, UnitSocketSession session) throws Exception{
		PengAoDaPackage pkg = PengAoDaPackage.parse(source);
		
		//(登录包)在解析出呼号后发送trace数据
		if(!isLogin(pkg)){
			//Trace功能(处理中会回复车台,放在处理前面)
			UnitCommon.sendUploadTrace(source, server, session);
		}
		
		boolean result = handlePackage(pkg, server, session);
		if(!result){
			String call = null;
			if(session.getUnitInfo() != null){
				call = session.getUnitInfo().getCallLetter();
			}
			
			logger_unkown.info("[pengAoDa]没有处理函数, 呼号:" + call + ", 源码:" + HexUtil.byteToHexStr(source));
		}
		
		//A计划
		UnitCommon.sendDataToAPlanClients(source, server, session);
		
		//警情
		UnitCommon.sendDataToAPlanAlarmClients(source, server, session);
	}
	
	private boolean handlePackage(PengAoDaPackage pkg, UnitServer server, UnitSocketSession session) throws Exception{
		byte[] body = pkg.getBody();
		byte flag = body[0];
		
		if(flag == 0x24){
			//(1) $开头,定时上报
			cmdTimeUpload.handlerPackage(pkg, server, session);
			return true;
		}
		
		if(flag == 0x2A){
			//(2) *开头,cmdId类
			int idx2 = searchByte(body, (byte) ',', 2);
			int idx3 = searchByte(body, (byte) ',', 3);
			if(idx2 == -1 || idx3 == -1){
				return false;
			}
			
			String cmdId = new String(body, idx2 + 1, idx3 - idx2 - 1, PengAoDaPkgUtil.EN_CHAR_ENCODING);		
			UploadHandler handler = handlers.get(cmdId);
			if(handler == null){
				return false;
			}
			
			return handler.handlerPackage(pkg, server, session);
		}
		
		return false;
	}
}