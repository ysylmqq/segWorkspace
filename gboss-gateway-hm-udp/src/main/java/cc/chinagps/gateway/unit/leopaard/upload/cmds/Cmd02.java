package cc.chinagps.gateway.unit.leopaard.upload.cmds;

import org.apache.log4j.Logger;

import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.leopaard.pkg.LeopaardPackage;
import cc.chinagps.gateway.unit.leopaard.upload.bean.LeopaardGPSInfo;
import cc.chinagps.gateway.unit.leopaard.util.LeopaardUploadUtil;

/**
 * 实时信息上报
 */
public class Cmd02 extends CheckLoginHandler {
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	@Override
	public boolean handlerPackageSessionChecked(LeopaardPackage pkg, UnitServer server, UnitSocketSession session)
			throws Exception {
		try {
			byte data[] = pkg.getDataUnit();
			LeopaardGPSInfo leopaardGPSInfo = LeopaardGPSInfo.parse(data, 0);
			logger_debug.debug("[leopaard][cmd02]LeopaardGPSInfo:" + leopaardGPSInfo);
			boolean isHistory = false;
			// 保存gps
			LeopaardUploadUtil.handleGPS(pkg, server, session, leopaardGPSInfo, isHistory);

			String callLetter = session.getUnitInfo().getCallLetter();
			// 车况信息(OBD)
			if (leopaardGPSInfo.getLeopaardOBDInfo() != null) {
				LeopaardUploadUtil.handleOBD(server, callLetter, leopaardGPSInfo.getGpsTime(),
						leopaardGPSInfo.getLeopaardOBDInfo(), isHistory);
			}

			// 故障码
			if (leopaardGPSInfo.getLeopaardFaultInfo() != null) {
				LeopaardUploadUtil.handleFaultInfo(server, callLetter, leopaardGPSInfo.getLeopaardFaultInfo(),
						leopaardGPSInfo.getGpsTime(), isHistory);
			}

			LeopaardUploadUtil.commonSuccessAck(session, pkg);
		} catch (Exception e) {
			// TODO: handle exception
			LeopaardUploadUtil.commonFailedAck(session, pkg);
			throw e;
		}
		return true;
	}
}