package cc.chinagps.gateway.unit.seg.upload.cmds;

import org.apache.log4j.Logger;

import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.beans.UnitInfo;
import cc.chinagps.gateway.unit.seg.pkg.SegPackage;
import cc.chinagps.gateway.unit.seg.upload.UploadHandler;
import cc.chinagps.gateway.util.HexUtil;

/**
 * 处理前统一检测是否已登录
 */
public abstract class CheckLoginHandler implements UploadHandler{
	private Logger logger_others = Logger.getLogger(LogManager.LOGGER_NAME_OTHERS);
	
	@Override
	public boolean handlerPackage(byte[] source, UnitServer server,
			UnitSocketSession session) throws Exception {
		UnitInfo unitInfo = session.getUnitInfo();
		if(unitInfo == null){
			//未登录
			logger_others.info("(seg)车台未登录, 远程地址:" + session.getRemoteAddress() + ", 源码:" + HexUtil.byteToHexStr(source));
			session.close();
			return true;
		}
		SegPackage p = SegPackage.parse(source);
		return handlerPackageSessionChecked(p, server, session);
	}
	
	public abstract boolean handlerPackageSessionChecked(SegPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception;
}