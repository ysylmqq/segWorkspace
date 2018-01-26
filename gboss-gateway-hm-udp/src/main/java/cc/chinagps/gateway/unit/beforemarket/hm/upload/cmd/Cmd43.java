package cc.chinagps.gateway.unit.beforemarket.hm.upload.cmd;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beforemarket.common.CheckLoginHandler;
import cc.chinagps.gateway.unit.beforemarket.common.pkg.BeforeMarketPackage;
import cc.chinagps.gateway.unit.beforemarket.common.util.BeforeMarketPkgUtil;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.HMUploadUtil;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.beans.HMECUConfig;

/**
 * 电控单元配置上报
 */
public class Cmd43 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session) throws Exception {
		byte[] body = ppkg.getBody();
		byte flag0 = body[0];
		boolean needAnswer = (flag0 & 0x80) != 0;
		byte subCmdId = body[1];
		
		if((subCmdId & 0xFF) == 0x1){
			//电控单元配置上报
			int startPosition = 2;
			HMECUConfig ecuConfig = HMECUConfig.parse(body, startPosition);
			
			long updateTime = System.currentTimeMillis();
			HMUploadUtil.handleECUConfig(server, session.getUnitInfo().getCallLetter(), 
					updateTime, ecuConfig);
			
			//应答
			if(needAnswer){
				byte[] rbody = new byte[3];
				rbody[0] = (byte) subCmdId;
				
				BeforeMarketPackage rpkg = new BeforeMarketPackage();
				rpkg.setHead(ppkg.getHead());
				rpkg.setBody(rbody);			
				int c1 = BeforeMarketPkgUtil.getC1(session);
				int d1 = BeforeMarketPkgUtil.getD1(session);
				byte[] source = rpkg.encode(c1, d1);
				
				session.sendData(source);
			}
			
			return true;
		}
		
		return false;
	}
}