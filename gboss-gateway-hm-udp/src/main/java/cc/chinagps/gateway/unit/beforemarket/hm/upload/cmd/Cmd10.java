package cc.chinagps.gateway.unit.beforemarket.hm.upload.cmd;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beforemarket.common.CheckLoginHandler;
import cc.chinagps.gateway.unit.beforemarket.common.pkg.BeforeMarketPackage;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.HMUploadUtil;

public class Cmd10 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session) throws Exception {
		byte[] body = ppkg.getBody();
		int unit_ack_result = Util.getShort(body, 0) & 0xFFFF;
		HMUploadUtil.commonResponseUseHeadSN(ppkg, server, session, unit_ack_result, 0xFF);
		
		return true;
	}
}