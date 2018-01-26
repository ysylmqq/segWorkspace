package cc.chinagps.gateway.unit.leopaard.upload.cmds;

import java.util.Date;

import org.apache.log4j.Logger;
import org.seg.lib.util.SegPackageUtil;
import org.seg.lib.util.Util;

import cc.chinagps.gateway.buff.InnerDataBuff;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.memcache.MemcacheManager;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beans.Loginout;
import cc.chinagps.gateway.unit.beans.UnitInfo;
import cc.chinagps.gateway.unit.common.UnitCommon;
import cc.chinagps.gateway.unit.common.UploadUtil;
import cc.chinagps.gateway.unit.leopaard.pkg.LeopaardPackage;
import cc.chinagps.gateway.unit.leopaard.util.LeopaardTimeFormatUtil;
import cc.chinagps.gateway.unit.leopaard.util.LeopaardUploadUtil;
import cc.chinagps.gateway.util.Constants;
import cc.chinagps.gateway.util.HexUtil;

/**
 * 登录包
 */
public class Cmd06 implements UploadHandler {
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);

	private void ackLoginFail(LeopaardPackage pkg, UnitSocketSession session) throws Exception {
		LeopaardUploadUtil.commonAck(session, pkg, (byte) 0x02);
	}

	private void ackLoginSuccess(LeopaardPackage pkg, UnitSocketSession session) throws Exception {
		byte[] dt = LeopaardTimeFormatUtil.getCurrentTime8();
		byte[] response = new byte[8];
		int pos = 0;
		System.arraycopy(dt, 0, response, pos, dt.length);
		pos += 6;
		byte[] result = Util.getShortByte((short) 0);
		System.arraycopy(result, 0, response, pos, result.length);
		pos += 2;
		LeopaardUploadUtil.commonAck(session, pkg, (byte) 0x01, response);
	}

	@Override
	public boolean handlerPackage(LeopaardPackage pkg, UnitServer server, UnitSocketSession session) throws Exception {
		// TODO Auto-generated method stub
		// 登录
		logger_debug.debug("[Leopaard] recev login data:" + HexUtil.byteToHexStr(pkg.getSource())+";session:"+session);
		String imei = pkg.getHead().getIc();
		imei = imei.substring(0,14);
		Object objCall = MemcacheManager.getMemcachedClient().get(MemcacheManager.IMEI_CALLLETTER_KEY_HEAD + imei);
		//objCall ="18101891253";
		if (objCall == null) {
			ackLoginFail(pkg, session);

			if (logger_debug.isDebugEnabled()) {
				StringBuilder logInfo = new StringBuilder();
				logInfo.append("[Leopaard]unit login failed, imei:").append(imei);
				logInfo.append(", source code:").append(HexUtil.byteToHexStr(pkg.getSource()));
				logger_debug.debug(logInfo.toString());
			}

			return true;
		}

		String callLetter = objCall.toString();
		UnitSocketSession existSession = server.searchUnitByCallLetter(callLetter);
		if (existSession != null && existSession != session) {
			logger_debug.debug("[Leopaard][cmd06]existSession:" + existSession + " is not same as the current session :" + session
					+ " then close the existed!");
			existSession.close();
		}

		// 设置session信息
		boolean isNew;
		if (session.getUnitInfo() != null) {
			isNew = false;
			String oldCall = session.getUnitInfo().getCallLetter();

			session.getUnitInfo().setCallLetter(callLetter);
			session.getUnitInfo().setIMEI(imei);

			server.getSessionManager().removeCallSession(oldCall);
			server.getSessionManager().addCallSession(callLetter, session);
		} else {
			isNew = true;
			UnitInfo unitInfo = new UnitInfo();
			unitInfo.setCallLetter(callLetter);
			unitInfo.setIMEI(imei);
			session.setUnitInfo(unitInfo);
			session.setLoginTime(System.currentTimeMillis() + 1); // 防止key重复 +1
																	// ms

			server.getSessionManager().addCallSession(callLetter, session);
		}

		// Trace功能(单独)
		UnitCommon.sendUploadTrace(pkg.getSource(), server, session);

		// 登录退录信息记录
		Loginout loginout = new Loginout();
		loginout.setIsLogin(isNew ? Loginout.LOGIN : Loginout.CHANGE_LOGIN);
		loginout.setLoginTime(session.getLoginTime());

		loginout.setCallLetter(callLetter);
		UploadUtil.handleLoginout(server, session, loginout);

		// 回复
		ackLoginSuccess(pkg, session);

		// 通知内部客户端
		byte[] bodyData = InnerDataBuff.Unit.newBuilder().setCallLetter(callLetter).build().toByteArray();
		server.getSeatServer().getTCPServer().broadcastPackage(false, false, false,
				Constants.INNER_CMD_ID_VEHICLE_ONLINE_NOTIFY, SegPackageUtil.getSerialNumber(), bodyData,
				UnitCommon.unitLoginChangeFilter);

		// 更新memcache
		// 升级服务器不更新routing
		if (!Constants.IS_UPDATE_SERVER) {
			long now = System.currentTimeMillis();
			Date expDate = new Date(now + MemcacheManager.ROUTING_EXPIRED);
			boolean success = MemcacheManager.getMemcachedClient().set(MemcacheManager.ROUTING_KEY_HEAD + callLetter,
					Constants.SYSTEM_ID, expDate);
			if (success) {
				session.getUnitInfo().setLastUpdateRouteTime(now);
			}
		}

		if (logger_debug.isDebugEnabled()) {
			StringBuilder logInfo = new StringBuilder();
			logInfo.append("[Leopaard]Unit login, callLetter:").append(callLetter);
			logInfo.append(", Source code:").append(HexUtil.byteToHexStr(pkg.getSource()));
			logger_debug.debug(logInfo.toString());
		}
		
		return true;
	}

}
