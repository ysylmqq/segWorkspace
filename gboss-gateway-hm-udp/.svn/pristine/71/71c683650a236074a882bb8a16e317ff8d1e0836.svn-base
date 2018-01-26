package cc.chinagps.gateway.unit.seg.upload.cmds;

import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.seg.pkg.SegPackage;
import cc.chinagps.gateway.unit.seg.util.SegPkgUtil;
/**
 * ÍËÂ¼
 */
public class Cmd81 extends BaseUploadHandler{
	@Override
	public boolean handlerPackage(SegPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception{
		byte[] data = SegPkgUtil.encode((byte) 0x01, pkg.getSerialNumberBytes(), pkg.getBody());
		session.sendData(data);
		session.close();
		
		return true;
	}
}