package cc.chinagps.gateway.unit.oem.upload.cmd;

import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.oem.pkg.OEMPackage;
import cc.chinagps.gateway.unit.oem.upload.UploadHandler;
import cc.chinagps.gateway.unit.oem.util.OEMPkgUtil;
import cc.chinagps.gateway.unit.udp.UdpServer;

/**
 * 启动、未关门、未布防提醒
 */
public class Cmd94 implements UploadHandler {

	@Override
	public boolean handlerPackage(OEMPackage pkg, UdpServer server,
			UnitSocketSession session) throws Exception {
		// TODO Auto-generated method stub
		// ack
		byte[] source = OEMPkgUtil.getCommonResponseToUnit(session,
				(byte) 0x20, pkg);
		session.sendData(source);

		return true;
	}

}