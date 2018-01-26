package cc.chinagps.gateway.unit.beforemarket.kaiyi.upload.cmd;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beforemarket.common.CheckLoginHandler;
import cc.chinagps.gateway.unit.beforemarket.common.pkg.BeforeMarketPackage;
import cc.chinagps.gateway.unit.beforemarket.common.util.BeforeMarketPkgUtil;
import cc.chinagps.gateway.unit.beforemarket.kaiyi.upload.KaiyiUploadUtil;
import cc.chinagps.gateway.unit.beforemarket.kaiyi.upload.beans.KaiyiFaultInfo;
import cc.chinagps.gateway.unit.beforemarket.kaiyi.upload.beans.KaiyiGPSInfo;
import cc.chinagps.gateway.unit.beforemarket.kaiyi.upload.beans.KaiyiOBDInfo;
import cc.chinagps.gateway.unit.beforemarket.kaiyi.upload.beans.KaiyiStatus;
import cc.chinagps.gateway.unit.beforemarket.kaiyi.upload.beans.KaiyiTravelInfo;

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
		boolean hasOBD = (dataFlag & 0x20) != 0;
		boolean hasTravel = (dataFlag & 0x10) != 0;		
		boolean hasFault = (dataFlag & 0x8) != 0;
		
		int position = 3;

		KaiyiGPSInfo gpsInfo = KaiyiGPSInfo.parse(body, position);
		position += KaiyiGPSInfo.DATA_LENGTH;
		
		KaiyiStatus status = KaiyiStatus.parse(body, position);
		position += KaiyiStatus.DATA_LENGTH;
		
		//额外状态
		addStatusBySubCmdId(status, subCmdId);
		
		KaiyiOBDInfo obdInfo = null;
		KaiyiTravelInfo travelInfo = null;
		KaiyiFaultInfo faultInfo = null;
		
		if(hasOBD){
			obdInfo = KaiyiOBDInfo.parse(body, position);
			position += obdInfo.getDataLength();
		}
		
		if(hasTravel){
			travelInfo = KaiyiTravelInfo.parse(body, position);
			position += travelInfo.getDataLength();
		}
		
		if(hasFault){
			faultInfo = KaiyiFaultInfo.parse(body, position);
			position += faultInfo.getDataLength();
		}
		
		//保存gps
		KaiyiUploadUtil.handleGPS(ppkg, server, session, gpsInfo, status, 
				obdInfo, isHistory);
		
		//保存警情 
		int result = 0;
		if(isAlarmSubCmdId(subCmdId)){
			result = KaiyiUploadUtil.handleAlarm(ppkg, server, session, 
					gpsInfo, status, obdInfo, isHistory, subCmdId);
		}
		
		String callLetter = session.getUnitInfo().getCallLetter();
		//车况信息(OBD)
		if(obdInfo != null){
			KaiyiUploadUtil.handleOBD(server, callLetter, gpsInfo.getGpsTime().getTime(), obdInfo, isHistory);
		}
		
		//行程
		if(travelInfo != null){
			KaiyiUploadUtil.handleTravelInfo(server, callLetter, travelInfo, gpsInfo, status, isHistory);
		}
		
		//故障码
		if(faultInfo != null){
			KaiyiUploadUtil.handleFaultInfo(server, callLetter, faultInfo, gpsInfo.getGpsTime().getTime(), isHistory);
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
	
	private static final int[] ALARM_SUB_CMD_IDS = {0x21, 0x22, 0x28, 0x29};
	
	private boolean isAlarmSubCmdId(int subCmdId){
		for(int i = 0; i < ALARM_SUB_CMD_IDS.length; i++){
			if(subCmdId == ALARM_SUB_CMD_IDS[i]){
				return true;
			}
		}
		
		return false;
	}
	
	private void addStatusBySubCmdId(KaiyiStatus status, int subCmdId){
		switch (subCmdId) {
			case 0x21:	//ACC ON上报
				status.addStatus(200);
				break;
			case 0x22:	//ACC OFF上报
				status.addStatus(201);
				break;
			default:
				break;
		}
	}
}