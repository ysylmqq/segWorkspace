package cc.chinagps.gateway.unit.beforemarket.yidongwang.upload.cmd;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beforemarket.common.CheckLoginHandler;
import cc.chinagps.gateway.unit.beforemarket.common.pkg.BeforeMarketPackage;
import cc.chinagps.gateway.unit.beforemarket.yidongwang.upload.YdwUploadUtil;

/**
 * …Ë÷√÷∏¡Ó”¶¥
 */
public class Cmd02 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session) throws Exception {
		byte[] body = ppkg.getBody();
		int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;
		YdwUploadUtil.commonResponseUseHeadSN(ppkg, server, session, unit_ack_result);
		
		return true;
	}
}