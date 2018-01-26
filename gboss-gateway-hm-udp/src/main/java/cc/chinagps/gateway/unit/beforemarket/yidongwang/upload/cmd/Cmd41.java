package cc.chinagps.gateway.unit.beforemarket.yidongwang.upload.cmd;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.aplan.pkg.APlanUtil;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beans.BaseStationInfo;
import cc.chinagps.gateway.unit.beforemarket.common.CheckLoginHandler;
import cc.chinagps.gateway.unit.beforemarket.common.pkg.BeforeMarketPackage;
import cc.chinagps.gateway.unit.beforemarket.common.util.BeforeMarketPkgUtil;
import cc.chinagps.gateway.unit.beforemarket.yidongwang.upload.YdwUploadUtil;
import cc.chinagps.gateway.unit.beforemarket.yidongwang.upload.beans.YdwGPSInfo;
import cc.chinagps.gateway.unit.beforemarket.yidongwang.upload.beans.YdwInfo;
import cc.chinagps.gateway.unit.beforemarket.yidongwang.upload.beans.YdwStatus;

/**
 * 位置单条上报
 */
public class Cmd41 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session) throws Exception {
		byte[] body = ppkg.getBody();
		byte flag0 = body[0];
		int subCmdId = body[1] & 0xFF;
		
		boolean needAnswer = (flag0 & 0x80) != 0;
		boolean isHistory = (flag0 & 0x1) != 0;
		
		byte dataFlag = body[2];
		boolean hasBaseStation = (dataFlag & 0x4) != 0;
		boolean hasYdwInfo = (dataFlag & 0x2) != 0;
		
		int position = 3;
		YdwGPSInfo gpsInfo = YdwGPSInfo.parse(body, position);
		position += 20;
		
		YdwStatus status = YdwStatus.parse(body, position);
		position += 7;
		
		BaseStationInfo baseStationInfo = null;
		if(hasBaseStation){
			String plmn = APlanUtil.getCString(body, position, 5);
			position += 5;
			int lac = Util.getShort(body, position) & 0xFFFF;
			position += 2;
			int cid = Util.getShort(body, position) & 0xFFFF;
			position += 2;
			
			baseStationInfo = new BaseStationInfo();
			baseStationInfo.setMcc(plmn.substring(0, 3));
			baseStationInfo.setMnc(plmn.substring(3));
			baseStationInfo.setLac(lac);
			baseStationInfo.setCid(cid);
		}
		
		YdwInfo ydwInfo = null;
		if(hasYdwInfo){
			ydwInfo = YdwInfo.parse(body, position);
			position += 11;
		}
		
		//保存gps
		YdwUploadUtil.handleGPS(ppkg, server, session, gpsInfo, status, baseStationInfo, ydwInfo, isHistory);
		
		//保存警情 
		int result = 0;
		if(isAlarmSubCmdId(subCmdId)){
			result = YdwUploadUtil.handleAlarm(ppkg, server, session, gpsInfo, status, baseStationInfo, ydwInfo, isHistory, subCmdId);
		}
		
		//应答
		if(needAnswer){
			byte[] rbody = new byte[3];
			rbody[0] = (byte) subCmdId;
			byte[] b_result = Util.getShortByte((short) result);
			System.arraycopy(b_result, 0, rbody, 1, 2);
			
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
	
	private static final int[] ALARM_SUB_CMD_IDS = {0x25, 0x29};
	
	private boolean isAlarmSubCmdId(int subCmdId){
		for(int i = 0; i < ALARM_SUB_CMD_IDS.length; i++){
			if(subCmdId == ALARM_SUB_CMD_IDS[i]){
				return true;
			}
		}
		
		return false;
	}
}