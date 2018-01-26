package cc.chinagps.gateway.unit.kelong.upload.cmds;

import org.apache.log4j.Logger;

import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.kelong.define.KeLongDefines;
import cc.chinagps.gateway.unit.kelong.pkg.KeLongPackage;
import cc.chinagps.gateway.unit.kelong.upload.bean.DataStorage;
import cc.chinagps.gateway.unit.kelong.upload.bean.KeLongBaseStationInfo;
import cc.chinagps.gateway.unit.kelong.upload.bean.KeLongCANInfo;
import cc.chinagps.gateway.unit.kelong.upload.bean.KeLongGPSInfo;
import cc.chinagps.gateway.unit.kelong.util.KeLongUploadUtil;
import cc.chinagps.gateway.util.HexUtil;

/**
 * GPS上报
 */
public class Cmd0010 extends CheckLoginHandler {
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	
	@Override
	public boolean handlerPackageSessionChecked(KeLongPackage pkg, UnitServer server, UnitSocketSession session)
			throws Exception {
		try {
			byte data[] = pkg.getData();
			int pos = 0 ;
			int gpsNum = data[pos++];			
			for (int i = 0; i < gpsNum; i++) {
				KeLongGPSInfo keLongGPSInfo = KeLongGPSInfo.parse(data, pos);
				pos += 49;
				
				boolean isHistory = keLongGPSInfo.isHistory();
				String callLetter = session.getUnitInfo().getCallLetter();
				//从缓存中取CAN中的数据 2017-05-23 by dy
				if(null != DataStorage.getCanMap().get(callLetter)){					
					KeLongCANInfo can= DataStorage.getCanMap().get(callLetter);
					//can缓存中的时间和GPS时间相差3分钟之内
					if(Math.abs(keLongGPSInfo.getGpsTime()-can.getCanTime())<=KeLongDefines.CONSTANT){						
					    keLongGPSInfo.getKeLongOBDInfo().setRemainOil(can.getRemainingFuel());
					}
				}
				//从缓存中取基站数据 2017-05-23 by dy
				if(null != DataStorage.getBaseStationMap().get(callLetter)){
					KeLongBaseStationInfo baseStationInfo = DataStorage.getBaseStationMap().get(callLetter);
					//基站缓存数据中的时间和GPS时间相差3分钟之内
					if(Math.abs(keLongGPSInfo.getGpsTime()-baseStationInfo.getBaseStationTime())<=KeLongDefines.CONSTANT){
					    keLongGPSInfo.setKeLongBaseStationInfo(baseStationInfo);
					}
				}
				logger_debug.debug("[KeLong][cmd0010]KeLongGPSInfo:" + keLongGPSInfo + ",source:" + HexUtil.byteToHexStr(pkg.getSource()));
				
				// 保存gps
				KeLongUploadUtil.handleGPS(pkg, server, session, keLongGPSInfo, isHistory);
				
				// 车况信息(OBD)
				if (keLongGPSInfo.getKeLongOBDInfo() != null) {
					KeLongUploadUtil.handleOBD(server, callLetter, keLongGPSInfo.getGpsTime(),
							keLongGPSInfo.getKeLongOBDInfo(), isHistory);
				}
			}

			KeLongUploadUtil.commonMsgAck(session, pkg, (short) 0x8000, (short) 0x0010, (byte) 0x00);
		} catch (Exception e) {
			// TODO: handle exception
			KeLongUploadUtil.commonMsgAck(session, pkg, (short) 0x8000, (short) 0x0010, (byte) 0x01);
			throw e;
		}
		return true;
	}
}