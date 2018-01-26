package cc.chinagps.gateway.unit.leopaard.upload.cmds;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.leopaard.pkg.LeopaardPackage;
import cc.chinagps.gateway.unit.leopaard.upload.bean.LeopaardGPSInfo;
import cc.chinagps.gateway.unit.leopaard.util.LeopaardUploadUtil;

/**
 * 补发信息上报
 */
public class Cmd05 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(LeopaardPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception{
		try {
			byte data[] = pkg.getDataUnit();
			LeopaardGPSInfo leopaardGPSInfo = LeopaardGPSInfo.parse(data, 0);

			boolean isHistory = true;
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