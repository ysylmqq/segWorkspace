package cc.chinagps.gateway.unit.kelx;

import java.nio.ByteBuffer;
import java.util.Date;

import org.apache.log4j.Logger;
import org.seg.lib.util.SegPackageUtil;
import org.seg.lib.util.Util;

import cc.chinagps.gateway.buff.InnerDataBuff;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.memcache.MemcacheManager;
import cc.chinagps.gateway.stream.InputStreamController;
import cc.chinagps.gateway.stream.decoders.transition.InnerTransitionDecoder;
import cc.chinagps.gateway.stream.decoders.transition.InnerTransitionPackageHandler;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beans.Loginout;
import cc.chinagps.gateway.unit.beans.UnitInfo;
import cc.chinagps.gateway.unit.common.UnitCommon;
import cc.chinagps.gateway.unit.common.UploadUtil;
import cc.chinagps.gateway.unit.kelx.pkg.KlxPackage;
import cc.chinagps.gateway.unit.kelx.upload.KlxStreamHandler;
import cc.chinagps.gateway.unit.kelx.util.KlxPkgUtil;
import cc.chinagps.gateway.util.Constants;
import cc.chinagps.gateway.util.HexUtil;

public class KlxTransitionPackageMaker implements InputStreamController, InnerTransitionPackageHandler{
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	
	private UnitServer server;
	private UnitSocketSession session;
	
	private InnerTransitionDecoder decoder = new InnerTransitionDecoder(this, KlxPkgUtil.START_FLAG, KlxPkgUtil.START_FLAG);
	
	public KlxTransitionPackageMaker(UnitServer server, UnitSocketSession session){
		this.server = server;
		this.session = session;
	}
	
	public static void main(String[] args) {
		byte[] id = {00, 00, 00, 0, 0x01, 0x36, (byte) 0x84, 4, (byte) 0x97, (byte) 0x84};
		String str = Util.bcd2str(id, 4).substring(1);
		System.out.println(str);
	}
	
	@Override
	public void firstPackageReceived(byte[] firstPKG, byte[] content)
			throws Exception {
		KlxPackage pkg = KlxPackage.parse(firstPKG);
		
		//处理登录start=====================
		byte[] id = pkg.getId();
		String callLetter = Util.bcd2str(id, 4).substring(1);
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
		
		//登录退录信息记录
		Loginout loginout = new Loginout();
		loginout.setIsLogin(isNew? Loginout.LOGIN: Loginout.CHANGE_LOGIN);
		//loginout.setLoginTime(isNew? session.getCreateTime(): System.currentTimeMillis());
		loginout.setLoginTime(session.getLoginTime());
		
		loginout.setCallLetter(callLetter);
		UploadUtil.handleLoginout(server, session, loginout);
		
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
			logInfo.append(", 源码:").append(HexUtil.byteToHexStr(firstPKG));
			logger_debug.debug(logInfo.toString());
		}
		
		//处理登录end=====================
		InputStreamController pm = new KlxStreamHandler(server, session);
		session.setPackageMaker(pm);
		
		pm.dealData(ByteBuffer.wrap(content), content.length);
	}

	@Override
	public void dealData(ByteBuffer buff, int len) throws Exception {
		decoder.handleStream(buff, len);
	}

	@Override
	public byte[] getCachedData() {
		return decoder.getCachedData();
	}
}