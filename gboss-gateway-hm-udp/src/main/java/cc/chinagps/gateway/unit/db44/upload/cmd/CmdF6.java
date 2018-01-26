package cc.chinagps.gateway.unit.db44.upload.cmd;

import java.io.IOException;
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
import cc.chinagps.gateway.unit.db44.pkg.DB44Package;
import cc.chinagps.gateway.unit.db44.upload.DB44GPSInfo;
import cc.chinagps.gateway.unit.db44.upload.DB44UploadUtil;
import cc.chinagps.gateway.unit.db44.upload.UploadHandler;
import cc.chinagps.gateway.unit.db44.util.DB44PkgUtil;
import cc.chinagps.gateway.util.Constants;

/**
 * 车台登录
 */
public class CmdF6 implements UploadHandler{
	private Logger logger_exception = Logger.getLogger(LogManager.LOGGER_NAME_EXCEPTION);
	
	@Override
	public boolean handlerPackage(DB44Package pkg, UnitServer server,
			UnitSocketSession session) throws Exception {
		byte[] protocol = pkg.getProtocol();
		DB44GPSInfo gps = DB44GPSInfo.parse(protocol, 0);
		String callLetter = DB44PkgUtil.readString(protocol, 30, 15);
		
		if(callLetter == null){
			logger_exception.info("call is null, pkg is:" + pkg);
			return true;
		}
		
		//处理登录
		handlerLogin(pkg, server, session, callLetter, (byte) 0xF6);
		
		//处理GPS
		DB44UploadUtil.handleGPS(pkg, server, session, gps);
		
		return true;
	}
	
	public static void handlerLogin(DB44Package pkg, UnitServer server, UnitSocketSession session, String callLetter, byte ackCmdId) throws IOException{
		//判断呼号是否重复
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
		byte[] data = DB44PkgUtil.encode(pkg.getLoop(), ackCmdId, Constants.ZERO_BYTES_DATA);
		session.sendData(data);
			
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
	}
}