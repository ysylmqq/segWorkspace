package cc.chinagps.gateway.unit.beforemarket.hm.upload.cmd;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beforemarket.common.CheckLoginHandler;
import cc.chinagps.gateway.unit.beforemarket.common.pkg.BeforeMarketPackage;
import cc.chinagps.gateway.unit.beforemarket.common.util.BeforeMarketPkgUtil;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.HMUploadUtil;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.beans.HMGPSInfo;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.beans.HMStatus;

/**
 * 位置批量补报
 */
public class Cmd42 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session) throws Exception {
		byte[] body = ppkg.getBody();
		byte flag0 = body[0];
		boolean needAnswer = (flag0 & 0x80) != 0;
		boolean isHistory = true;
		byte subCmdId = body[2];
		
		int count = body[1] & 0xFF;
		int position = 3;
		for(int i = 0; i < count; i++){
			HMGPSInfo gpsInfo = HMGPSInfo.parse(body, position);
			position += HMGPSInfo.DATA_LENGTH;
			
			HMStatus status = HMStatus.parse(body, position);
			position += HMStatus.DATA_LENGTH;
			
			HMUploadUtil.handleGPS(ppkg, server, session, gpsInfo, status, null, null, isHistory);
		}
		
		if(needAnswer){
			byte[] rbody = new byte[3];
			rbody[0] = subCmdId;
			
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
}