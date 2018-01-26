package cc.chinagps.gateway.unit.pengaoda.upload.cmd;

import org.apache.log4j.Logger;

import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beans.UnitInfo;
import cc.chinagps.gateway.unit.pengaoda.pkg.PengAoDaPackage;
import cc.chinagps.gateway.unit.pengaoda.upload.UploadHandler;
import cc.chinagps.gateway.util.HexUtil;

public abstract class CheckLoginHandler implements UploadHandler{
	private Logger logger_others = Logger.getLogger(LogManager.LOGGER_NAME_OTHERS);
	
	@Override
	public boolean handlerPackage(PengAoDaPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception {
		UnitInfo unitInfo = session.getUnitInfo();
		if(unitInfo == null){
			//Î´µÇÂ¼
			logger_others.info("(pengAoDa)³µÌ¨Î´µÇÂ¼, Ô¶³ÌµØÖ·:" + session.getRemoteAddress() + ", Ô´Âë:" + HexUtil.byteToHexStr(pkg.getSource()));
			session.close();
			return true;
		}
		
		return handlerPackageSessionChecked(pkg, server, session);
	}
	
	public abstract boolean handlerPackageSessionChecked(PengAoDaPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception;
}