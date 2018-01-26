package cc.chinagps.gateway.unit.seg.upload.cmds;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.seg.pkg.SegPackage;
import cc.chinagps.gateway.unit.seg.upload.SegUploadUtil;
import cc.chinagps.gateway.unit.seg.upload.beans.SegFaultInfo;
import cc.chinagps.gateway.unit.seg.util.SegPkgUtil;

/**
 * 故障码上报
 */
public class Cmd4B extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(SegPackage pkg,
			UnitServer server, UnitSocketSession session) throws Exception {
		byte[] body = pkg.getBody();
		String strBody = new String(body, SegPkgUtil.PKG_CHAR_ENCODING);
		if(strBody.startsWith("(SAF,146,")){
			//故障码上报
			String sStatus = strBody.substring(9, 11);
			if("01".equals(sStatus)){
				SegFaultInfo faultInfo = new SegFaultInfo();
				faultInfo.setFaultTime(new Date());
				List<String> codeList = new ArrayList<String>();
				faultInfo.setFaultCode(codeList);
				
				String sCount = strBody.substring(12, 14);
				int count = Integer.valueOf(sCount, 16);
				for(int i = 0; i < count; i++){
					String code = strBody.substring(14 + i * 5, 19 + i * 5);
					codeList.add(code);
				}
				
				SegUploadUtil.handleFaultInfo(pkg, server, session, faultInfo);
			}else{
				//无故障
				SegFaultInfo faultInfo = new SegFaultInfo();
				faultInfo.setFaultTime(new Date());
				SegUploadUtil.handleFaultInfo(pkg, server, session, faultInfo);
			}
			
			SegPkgUtil.commonAckUnit((byte) 0x4B, session, pkg);
			return true;
		}
		
		return false;
	}
}