package cc.chinagps.gateway.unit.leopaard.upload.cmds;

import org.apache.log4j.Logger;
import org.seg.lib.util.Util;

import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.memcache.MemcacheManager;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.leopaard.pkg.LeopaardPackage;
import cc.chinagps.gateway.unit.leopaard.util.LeopaardUploadUtil;
import cc.chinagps.gateway.util.HexUtil;

/**
 * 信息绑定，为第1个包
 */
public class Cmd84 implements UploadHandler{
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);

	private void ackBindFail(LeopaardPackage pkg, UnitSocketSession session) throws Exception {
		byte[] respData = Util.getShortByte((short)0xFF);
		//绑定失败
		LeopaardUploadUtil.commonAck(session, pkg, (byte) 0x01, respData);
	}

	private void ackBindSuccess(LeopaardPackage pkg, UnitSocketSession session) throws Exception {
		byte[] respData = Util.getShortByte((short)0);
		//回应绑定成功
		LeopaardUploadUtil.commonAck(session, pkg, (byte) 0x01, respData);
	}
	@Override
	public boolean handlerPackage(LeopaardPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception{
		logger_debug.debug("[Leopaard] recev binding data:" + HexUtil.byteToHexStr(pkg.getSource()));
		String imei = pkg.getHead().getIc();
		imei = imei.substring(0,14);
		Object objCall = MemcacheManager.getMemcachedClient().get(MemcacheManager.IMEI_CALLLETTER_KEY_HEAD + imei);
		//objCall ="18101891253";
		if (objCall == null) {
			ackBindFail(pkg, session);

			if (logger_debug.isDebugEnabled()) {
				StringBuilder logInfo = new StringBuilder();
				logInfo.append("[Leopaard]unit bind failed, imei:").append(imei);
				logInfo.append(", source code:").append(HexUtil.byteToHexStr(pkg.getSource()));
				logger_debug.debug(logInfo.toString());
			}
			return true;
		}
		
		String callLetter = objCall.toString();
		UnitSocketSession existSession = server.searchUnitByCallLetter(callLetter);
		if (existSession != null && existSession != session) {
			logger_debug.debug("[Leopaard][cmd84]existSession:" + existSession + " is not same as the current session :" + session
					+ " then close the existed!");
			existSession.close();
		}
		
		// 回复
		ackBindSuccess(pkg, session);
		return true;
	}
}