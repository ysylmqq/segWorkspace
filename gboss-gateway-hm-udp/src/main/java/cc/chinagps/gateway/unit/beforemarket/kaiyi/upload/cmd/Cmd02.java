package cc.chinagps.gateway.unit.beforemarket.kaiyi.upload.cmd;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beforemarket.common.CheckLoginHandler;
import cc.chinagps.gateway.unit.beforemarket.common.pkg.BeforeMarketPackage;
import cc.chinagps.gateway.unit.beforemarket.kaiyi.upload.KaiyiUploadUtil;

/**
 * …Ë÷√÷∏¡Ó”¶¥
 */
public class Cmd02 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session) throws Exception {
		byte[] body = ppkg.getBody();
		int subCmdId = body[0] & 0xFF;
		int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;
		int innerCmdId = getInnerCmdId(subCmdId);
		KaiyiUploadUtil.commonResponseUseHeadSN(ppkg, server, session, unit_ack_result, innerCmdId);
		
		return true;
	}
	
	private static int getInnerCmdId(int subCmdId){
		switch (subCmdId) {
			case 0x1:		
				return 0x0057;
			case 0x2:
				return 0x0025;
			case 0x3:
				return 0x002F;
			case 0x12:
				return 0x0038;
			case 0x13:	
				return 0x0042;
			case 0x16:
				return 0x0048;
			case 0x17:
			case 0x18:
				return 0x00A6;
			case 0x19:
				return 0x00AA;
			default:
				return 0;
		}
	}
}