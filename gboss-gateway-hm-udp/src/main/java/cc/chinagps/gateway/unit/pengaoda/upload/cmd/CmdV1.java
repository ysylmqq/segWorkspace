package cc.chinagps.gateway.unit.pengaoda.upload.cmd;

import java.util.Date;

import org.apache.log4j.Logger;
import org.seg.lib.util.SegPackageUtil;

import cc.chinagps.gateway.buff.InnerDataBuff;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.memcache.MemcacheManager;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beans.Loginout;
import cc.chinagps.gateway.unit.beans.UnitInfo;
import cc.chinagps.gateway.unit.common.UnitCommon;
import cc.chinagps.gateway.unit.common.UploadUtil;
import cc.chinagps.gateway.unit.pengaoda.pkg.PengAoDaAck;
import cc.chinagps.gateway.unit.pengaoda.pkg.PengAoDaPackage;
import cc.chinagps.gateway.unit.pengaoda.upload.PengAoDaUploadUtil;
import cc.chinagps.gateway.unit.pengaoda.upload.UploadHandler;
import cc.chinagps.gateway.unit.pengaoda.upload.beans.PengAoDaGPSInfo;
import cc.chinagps.gateway.unit.pengaoda.util.PengAoDaPkgUtil;
import cc.chinagps.gateway.unit.pengaoda.util.PengAoDaTimeFormatUtil;
import cc.chinagps.gateway.util.Constants;
import cc.chinagps.gateway.util.HexUtil;

/**
 * 终端登录
 */
public class CmdV1 implements UploadHandler{
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	
	@Override
	public boolean handlerPackage(PengAoDaPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception {
		byte[] body = pkg.getBody();
		PengAoDaGPSInfo gps = PengAoDaGPSInfo.parse(body);
		
		String terminalId = gps.getTerminalId();
		Object objCall = MemcacheManager.getMemcachedClient().get(MemcacheManager.IMEI_CALLLETTER_KEY_HEAD + terminalId);
		
		if(objCall == null){
			ackLoginFail(session, terminalId);
			
			if(logger_debug.isDebugEnabled()){
				StringBuilder logInfo = new StringBuilder();
				logInfo.append("车台登录失败(pengAoDa), terminalId:").append(terminalId);
				logInfo.append(", 源码:").append(HexUtil.byteToHexStr(pkg.getSource()));
				logger_debug.debug(logInfo.toString());
			}
			
			return true;
		}
		
		String callLetter = objCall.toString();			
		UnitSocketSession existSession = server.searchUnitByCallLetter(callLetter);
		if(existSession != null && existSession != session){
			existSession.close();
		}
		
		//设置session信息
		boolean isNew;
		if(session.getUnitInfo() != null){
			isNew = false;
			String oldCall = session.getUnitInfo().getCallLetter();
			
			session.getUnitInfo().setCallLetter(callLetter);
			
			server.getSessionManager().removeCallSession(oldCall);
			server.getSessionManager().addCallSession(callLetter, session);
		}else{
			isNew = true;
			UnitInfo unitInfo = new UnitInfo();
			unitInfo.setCallLetter(callLetter);
			session.setUnitInfo(unitInfo);
			session.setLoginTime(System.currentTimeMillis() + 1);	//防止key重复 +1 ms
			
			server.getSessionManager().addCallSession(callLetter, session);
		}
		
		//Trace功能(单独)
		UnitCommon.sendUploadTrace(pkg.getSource(), server, session);
		//登录退录信息记录
		Loginout loginout = new Loginout();
		loginout.setIsLogin(isNew? Loginout.LOGIN: Loginout.CHANGE_LOGIN);
		//loginout.setLoginTime(isNew? session.getCreateTime(): System.currentTimeMillis());
		loginout.setLoginTime(session.getLoginTime());
		
		loginout.setCallLetter(callLetter);
		UploadUtil.handleLoginout(server, session, loginout);
		
		//回复
		ackLogin(session, terminalId);
		
		//通知内部客户端
		byte[] bodyData = InnerDataBuff.Unit.newBuilder().setCallLetter(callLetter).build().toByteArray();
		server.getSeatServer().getTCPServer().broadcastPackage(false, false, false,
				Constants.INNER_CMD_ID_VEHICLE_ONLINE_NOTIFY, SegPackageUtil.getSerialNumber(),
				bodyData, UnitCommon.unitLoginChangeFilter);
		
		//更新memcache
		//升级服务器不更新routing
		if(!Constants.IS_UPDATE_SERVER){
			long now = System.currentTimeMillis();
			Date expDate = new Date(now + MemcacheManager.ROUTING_EXPIRED);
			boolean success = MemcacheManager.getMemcachedClient().set(MemcacheManager.ROUTING_KEY_HEAD + callLetter, Constants.SYSTEM_ID, expDate);
			if(success){
				session.getUnitInfo().setLastUpdateRouteTime(now);
			}
		}
		
		if(logger_debug.isDebugEnabled()){
			StringBuilder logInfo = new StringBuilder();
			logInfo.append("车台登录, 呼号:").append(callLetter);
			logInfo.append(", 源码:").append(HexUtil.byteToHexStr(pkg.getSource()));
			logger_debug.debug(logInfo.toString());
		}
		
		//gps
		PengAoDaUploadUtil.handleGPS(server, session, pkg, gps);
		if(gps.isAlarm()){
			PengAoDaUploadUtil.handlerAlarm(server, session, pkg, gps);
		}
		
		return true;
	}
	
	private void ackLoginFail(UnitSocketSession session, String terminalId) throws Exception{
		PengAoDaAck ack = new PengAoDaAck();
		ack.setTerminalId(terminalId);
		ack.setParam("V1");
		ack.setTime(PengAoDaTimeFormatUtil.getTime(System.currentTimeMillis()));
		
		byte[] res = ack.encode();
		session.sendData(res);
	}
	
	private void ackLogin(UnitSocketSession session, String terminalId) throws Exception{
		byte[] res = PengAoDaPkgUtil.getCommonResponseToUnit(session, terminalId, "V1");
		session.sendData(res);
	}
}