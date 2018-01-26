package cc.chinagps.gateway.unit.leopaard.upload.cmds;

import org.apache.log4j.Logger;

import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.beans.UnitInfo;
import cc.chinagps.gateway.unit.leopaard.pkg.LeopaardPackage;
import cc.chinagps.gateway.util.HexUtil;

public abstract class CheckLoginHandler implements UploadHandler{
	private Logger logger_others = Logger.getLogger(LogManager.LOGGER_NAME_OTHERS);
	
	@Override
	public boolean handlerPackage(LeopaardPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception {
		UnitInfo unitInfo = session.getUnitInfo();
		if(unitInfo == null){
			//Î´µÇÂ¼
			logger_others.info("[leopaard]unit not login, remote address:" + session.getRemoteAddress() + ", source code:" + HexUtil.byteToHexStr(pkg.getSource()));
			session.close();
			return true;
		}
		return handlerPackageSessionChecked(pkg, server, session);
	}
	
	public abstract boolean handlerPackageSessionChecked(LeopaardPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception;
}