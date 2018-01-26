package cc.chinagps.gateway.unit.kelx.upload.cmd;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.kelx.pkg.KlxPackage;
import cc.chinagps.gateway.unit.kelx.upload.UploadHandler;
import cc.chinagps.gateway.unit.kelx.util.KlxPkgUtil;

/**
 * ÐÄÌø°ü
 */
public class Cmd01 implements UploadHandler{
	@Override
	public boolean handlerPackage(KlxPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception {
//		KlxAck ack = new KlxAck();
//		ack.setReceivedCmdId((short) 1);
//		ack.setReceivedSn(pkg.getSn());
//		ack.setSuccess((byte) 0);
//		
//		byte[] response_body = ack.encode();
//		byte[] response_id = KlxPkgUtil.getIdByCallLetter(session.getUnitInfo().getCallLetter());
//		
//		KlxPackage rpkg = new KlxPackage();
//		rpkg.setBody(response_body);
//		rpkg.setId(response_id);
//		rpkg.setSn(KlxPkgUtil.getSn(session));
//		
//		byte[] source = rpkg.encode();
		
		//ack
		byte[] source = KlxPkgUtil.getCommonResponseToUnit(session, (short) 1, pkg.getSn(), (byte) 0);
		session.sendData(source);
		return true;
	}
}