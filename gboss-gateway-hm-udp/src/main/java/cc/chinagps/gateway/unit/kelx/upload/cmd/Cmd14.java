package cc.chinagps.gateway.unit.kelx.upload.cmd;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.kelx.pkg.KlxPackage;
import cc.chinagps.gateway.unit.kelx.upload.UploadHandler;
import cc.chinagps.gateway.unit.kelx.util.KlxPkgUtil;
import cc.chinagps.gateway.unit.kelx.util.KlxTimeFormatUtil;

/**
 *  ±º‰≤È—Ø
 */
public class Cmd14 implements UploadHandler{
	@Override
	public boolean handlerPackage(KlxPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception {
		byte[] response_body = new byte[8];
		response_body[0] = (byte) 0x80;
		response_body[1] = 0x1B;
		byte[] bs = KlxTimeFormatUtil.getCurrentTimeBCD();
		System.arraycopy(bs, 0, response_body, 2, bs.length);
		
		byte[] response_id = KlxPkgUtil.getIdByCallLetter(session.getUnitInfo().getCallLetter());
		KlxPackage rpkg = new KlxPackage();
		rpkg.setBody(response_body);
		rpkg.setId(response_id);
		rpkg.setSn(KlxPkgUtil.getSn(session));
		
		byte[] source = rpkg.encode();
		session.sendData(source);
		return true;
	}
}