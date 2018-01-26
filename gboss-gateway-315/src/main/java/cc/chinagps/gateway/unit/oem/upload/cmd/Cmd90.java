package cc.chinagps.gateway.unit.oem.upload.cmd;

import org.apache.log4j.Logger;

import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.oem.pkg.OEMPackage;
import cc.chinagps.gateway.unit.oem.upload.OEMUploadUtil;
import cc.chinagps.gateway.unit.oem.upload.UploadHandler;
import cc.chinagps.gateway.unit.oem.upload.bean.OEMGPSInfo;
import cc.chinagps.gateway.unit.oem.util.OEMPkgUtil;
import cc.chinagps.gateway.unit.udp.UdpServer;

/**
 * ��ʱ�ϱ�
 */
public class Cmd90 implements UploadHandler {
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	@Override
	public boolean handlerPackage(OEMPackage pkg, UdpServer server,
			UnitSocketSession session) throws Exception {
		// TODO Auto-generated method stub
		byte[] body = pkg.getData();
		OEMGPSInfo gps = OEMGPSInfo.parse(body, 0,false);
		gps.setHistory(0);
		logger_debug.debug("OEM Cmd90 "+gps);
		
		OEMUploadUtil.handleGPS(server, session, pkg, gps);
		
		// ack
		byte[] source = OEMPkgUtil.getCommonResponseToUnit(session, (byte)0x20, pkg);
		session.sendData(source);

		return true;
	}

}