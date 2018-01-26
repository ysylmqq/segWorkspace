package cc.chinagps.gateway.unit.seg.upload.cmds;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.seg.upload.UploadHandler;
import cc.chinagps.gateway.unit.seg.util.SegPkgUtil;

/**
 * 加强型安防GPRS握手包
 */
public class CmdF7 implements UploadHandler{
	@Override
	public boolean handlerPackage(byte[] source,
			UnitServer server, UnitSocketSession session) throws Exception {
		byte[] unes = SegPkgUtil.unescape(source);
		if(unes.length == 5 && unes[2] == 1){
			//加强型安防GPRS握手包
			byte[] es = SegPkgUtil.escape(unes);
			session.sendData(es);
			return true;
		}
		
		return false;
	}
}