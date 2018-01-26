package cc.chinagps.gateway.unit.seg.upload.cmds;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.seg.upload.UploadHandler;
import cc.chinagps.gateway.unit.seg.util.SegPkgUtil;

/**
 * 链路检测(上海通大)
 */
public class CmdF4 implements UploadHandler{
	@Override
	public boolean handlerPackage(byte[] source,
			UnitServer server, UnitSocketSession session) throws Exception {
		if(source.length == 5){
			if(source[2] == 0x4C && source[3] == 0x55){
				byte[] res = new byte[5];
				res[0] = SegPkgUtil.START_FLAG;
				res[1] = (byte) 0xF4;
				res[2] = 0x4C;
				res[3] = 0x44;
				res[4] = SegPkgUtil.END_FLAG;
				
				byte[] es = SegPkgUtil.escape(res);
				session.sendData(es);
				return true;
			}
		}
		
		return false;
	}
}