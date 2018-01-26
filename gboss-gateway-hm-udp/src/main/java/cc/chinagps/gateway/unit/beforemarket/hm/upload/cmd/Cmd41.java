package cc.chinagps.gateway.unit.beforemarket.hm.upload.cmd;

import org.apache.log4j.Logger;
import org.seg.lib.util.Util;

import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beforemarket.common.CheckLoginHandler;
import cc.chinagps.gateway.unit.beforemarket.common.pkg.BeforeMarketPackage;
import cc.chinagps.gateway.unit.beforemarket.common.util.BeforeMarketPkgUtil;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.HMUploadUtil;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.beans.HMFaultInfo;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.beans.HMFaultLightInfo;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.beans.HMGPSInfo;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.beans.HMOBDInfo;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.beans.HMStatus;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.beans.HMTravelInfo;
import cc.chinagps.gateway.util.HexUtil;

/**
 * 位置单条上报
 */
public class Cmd41 extends CheckLoginHandler{
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	@Override
	public boolean handlerPackageSessionChecked(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session) throws Exception {
		String callLetter =null;
		if (session.getUnitInfo() != null)
			callLetter = session.getUnitInfo().getCallLetter();
		logger_debug.debug("[hm][cmd41]["+callLetter+"]recev data:"+HexUtil.byteToHexStr(ppkg.getSource()));
		byte[] body = ppkg.getBody();
		byte flag0 = body[0];
		int subCmdId = body[1] & 0xFF;
		
		boolean needAnswer = (flag0 & 0x80) != 0;
		boolean isHistory = (flag0 & 0x1) != 0;
		
		byte dataFlag = body[2];
		//boolean hasPosition = (dataFlag & 0x80) != 0;
		//boolean hasStatus = (dataFlag & 0x40) != 0;
		boolean hasOBD = (dataFlag & 0x20) != 0;
		boolean hasTravel = (dataFlag & 0x10) != 0;		
		boolean hasFault = (dataFlag & 0x8) != 0;
		boolean hasFaultLight = (dataFlag & 0x4) != 0;
		
		int position = 3;

		HMGPSInfo gpsInfo = HMGPSInfo.parse(body, position);
		position += HMGPSInfo.DATA_LENGTH;
		
		HMStatus status = HMStatus.parse(body, position);
		position += HMStatus.DATA_LENGTH;
		
		//额外状态
		addStatusBySubCmdId(status, subCmdId);
		
		HMOBDInfo obdInfo = null;
		HMTravelInfo travelInfo = null;
		HMFaultInfo faultInfo = null;
		HMFaultLightInfo faultLightInfo = null;
		
		if(hasOBD){
			//int len = Util.getShort(body, position) & 0xFFFF;
			//obdInfo = HMOBDInfo.parse(body, position);
			//position += len;
			
			obdInfo = HMOBDInfo.parse(body, position);
			position += obdInfo.getDataLength();
		}
		
		if(hasTravel){
			//int len = Util.getShort(body, position) & 0xFFFF;
			//travelInfo = HMTravelInfo.parse(body, position);
			//position += len;
			
			travelInfo = HMTravelInfo.parse(body, position);
			position += travelInfo.getDataLength();
		}
		
		if(hasFault){
			faultInfo = HMFaultInfo.parse(body, position);
			position += faultInfo.getDataLength();
		}
		
		if(hasFaultLight){
			faultLightInfo = HMFaultLightInfo.parse(body, position);
			position += faultLightInfo.getDataLength();
		}
		
		//保存gps
		HMUploadUtil.handleGPS(ppkg, server, session, gpsInfo, status, 
				obdInfo, faultLightInfo, isHistory);
		
		//保存警情 
		int result = 0;
		if(isAlarmSubCmdId(subCmdId)){
			result = HMUploadUtil.handleAlarm(ppkg, server, session, 
					gpsInfo, status, obdInfo, faultLightInfo, isHistory, subCmdId);
		}
		
		//String callLetter = session.getUnitInfo().getCallLetter();
		//车况信息(OBD)
		if(obdInfo != null){
			HMUploadUtil.handleOBD(server, callLetter, gpsInfo.getGpsTime().getTime(), obdInfo, isHistory);
		}
		
		//行程
		if(travelInfo != null){
			HMUploadUtil.handleTravelInfo(server, callLetter, travelInfo, gpsInfo, status, isHistory);
		}
		
		//故障码
		if(faultInfo != null){
			HMUploadUtil.handleFaultInfo(server, callLetter, faultInfo, gpsInfo.getGpsTime().getTime(), isHistory);
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
	
	private static final int[] ALARM_SUB_CMD_IDS = { 0x21, 0x22, 0x23, 0x24, 0x25, 0x28, 0x2A,0x2B, 0x2C, 0x2D, 0x2E, 0x2F,
			0x30, 0x31, 0x32, 0x33 };
	
	private boolean isAlarmSubCmdId(int subCmdId){
		for(int i = 0; i < ALARM_SUB_CMD_IDS.length; i++){
			if(subCmdId == ALARM_SUB_CMD_IDS[i]){
				return true;
			}
		}
		
		return false;
	}
	
	private void addStatusBySubCmdId(HMStatus status, int subCmdId){
		switch (subCmdId) {
			case 0x21:	//ACC ON上报
				status.addStatus(200);
				break;
			case 0x22:	//ACC OFF上报
				status.addStatus(201);
				break;
			case 0x23:	//休眼上报
				status.addStatus(68);
				break;
			case 0x24:	//关机上报
				status.addStatus(69);
				break;
			case 0x28:	//SOS紧急救援服务请求上报
				status.addStatus(211);
				break;
			case 0x2A:	//OBD故障码上报
				status.addStatus(212);
				break;
			case 0x2B:	//熄火未关灯上报
				status.addStatus(202);
				break;
			case 0x2C:	//熄火未关门上报
				status.addStatus(203);
				break;
			case 0x2D:	//熄火未锁门上报
				status.addStatus(204);
				break;
			case 0x2E: //发动机点火上报
				status.addStatus(206);
				break;
			case 0x2F:	//发动机熄火上报
				status.addStatus(205);
				break;
			case 0x30:	//故障灯上报
				status.addStatus(207);
				break;
			case 0x31:	//车辆防盗异常熄火上报
				status.addStatus(208);
				break;
			case 0x32:	//车辆定时熄火上报
				status.addStatus(209);
				break;
			case 0x33:	//退出远程启动模式上报
				status.addStatus(210);
				break;
			default:
				break;
		}
	}
}