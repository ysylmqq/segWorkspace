package cc.chinagps.gateway.unit.beforemarket.hm.upload.cmd;

import org.apache.log4j.Logger;
import org.seg.lib.util.Util;

import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beforemarket.common.CheckLoginHandler;
import cc.chinagps.gateway.unit.beforemarket.common.pkg.BeforeMarketPackage;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.HMUploadUtil;

/**
 * ����ָ��Ӧ��
 */
public class Cmd02 extends CheckLoginHandler{
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	@Override
	public boolean handlerPackageSessionChecked(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session) throws Exception {
//		byte[] body = ppkg.getBody();
//		int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;
//		HMUploadUtil.commonResponseUseHeadSN(ppkg, server, session, unit_ack_result);
//		
//		return true;
		
		byte[] body = ppkg.getBody();
		int subCmdId = body[0] & 0xFF;
		int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;
		logger_debug.debug("[hm] cmd02 subCmd["+subCmdId+"] unit_ack_result:"+unit_ack_result);
		int innerCmdId = getInnerCmdId(subCmdId);
		HMUploadUtil.commonResponseUseHeadSN(ppkg, server, session, unit_ack_result, innerCmdId);
		
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
			case 0x14:
				return 0x004C;
			case 0x15:
				return 0x004A;
			case 0x16:
				return 0x0048;
			case 0x18:	
				return 0x00A6;
			case 0x19:
				return 0x00AA;
			case 0x1A:
				return 0x000A;
			case 0x22:
				return 0x00B3;
			case 0xFA:
				return 0x0002;
			case 0x25:
				return 0x00B6;
			case 0xF9:
				return 0x00F0;
			case 0xF8:
				return 0x00D3;
			default:
				return 0;
		}
	}
}