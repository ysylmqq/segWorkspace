package cc.chinagps.gateway.unit.seg.upload.cmds;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.seg.pkg.SegPackage;
import cc.chinagps.gateway.unit.seg.util.SegPkgUtil;
import cc.chinagps.gateway.unit.seg.util.SegTimeFormatUtil;

/**
 * 时钟校验
 */
public class Cmd82 extends CheckLoginHandler{
	private static final String DEFAULT_CMD_ENCODE_CHARSET = "ascii";
	@Override
	public boolean handlerPackageSessionChecked(SegPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception {
		/*String strBody = new String(pkg.getBody(), SegPkgUtil.PKG_CHAR_ENCODING);
		String sn = SegPkgUtil.getsn();
		byte[] data;
		String sbody;
		if(strBody.startsWith("SAF,CLK")){
			//回应终端当前时间
			sbody = "(CMD,CLK,";
			String timeStr = SegTimeFormatUtil.getCurrentTimeGMT8();
			sbody += timeStr;
			sbody += ")";
			data = SegPkgUtil.encode((byte) 0x10, sn, sbody.getBytes(DEFAULT_CMD_ENCODE_CHARSET));
			session.sendData(data);
			return true;
		}*/
		
		return false;
	}
}