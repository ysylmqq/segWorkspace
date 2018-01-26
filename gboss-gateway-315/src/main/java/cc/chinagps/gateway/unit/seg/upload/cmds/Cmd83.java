package cc.chinagps.gateway.unit.seg.upload.cmds;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.seg.pkg.SegPackage;
import cc.chinagps.gateway.unit.seg.util.SegPkgUtil;
import cc.chinagps.gateway.unit.seg.util.SegTimeFormatUtil;

/**
 * 90 超长待机
 * 发送系统时间给终端
 */
public class Cmd83 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(SegPackage pkg,
			UnitServer server, UnitSocketSession session) throws Exception {
		byte[] body = pkg.getBody();
		String strBody = new String(body, SegPkgUtil.PKG_CHAR_ENCODING);
		if(strBody.startsWith("SAF,CLK")){
			StringBuilder resBody = new StringBuilder();
			resBody.append("(CMD,CLK,");
			resBody.append(SegTimeFormatUtil.getCurrentTimeGMT8());
			resBody.append(")");
			
			byte[] data = SegPkgUtil.encode((byte) 0x10, pkg.getSerialNumberBytes(), resBody.toString().getBytes(SegPkgUtil.PKG_CHAR_ENCODING));
			session.sendData(data);
			return true;
		}
		
		return false;
	}
}
