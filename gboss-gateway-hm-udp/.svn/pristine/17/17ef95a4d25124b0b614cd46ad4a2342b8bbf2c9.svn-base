package cc.chinagps.gateway.unit.db44.upload.cmd;

import org.apache.log4j.Logger;

import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beans.UnitInfo;
import cc.chinagps.gateway.unit.db44.pkg.DB44Package;
import cc.chinagps.gateway.unit.db44.upload.UploadHandler;
import cc.chinagps.gateway.util.HexUtil;

public abstract class CheckLoginHandler implements UploadHandler{
	private Logger logger_others = Logger.getLogger(LogManager.LOGGER_NAME_OTHERS);
	
	@Override
	public boolean handlerPackage(DB44Package pkg, UnitServer server,
			UnitSocketSession session) throws Exception {
		
		UnitInfo unitInfo = session.getUnitInfo();
		if(unitInfo == null){
			//Î´µÇÂ¼
			logger_others.info("(db44)³µÌ¨Î´µÇÂ¼, Ô´Âë:" + HexUtil.byteToHexStr(pkg.getSource()));
			session.close();
			return true;
		}
		
		return handlerPackageSessionChecked(pkg, server, session);
	}
	
	public abstract boolean handlerPackageSessionChecked(DB44Package pkg, UnitServer server,
			UnitSocketSession session) throws Exception;
}