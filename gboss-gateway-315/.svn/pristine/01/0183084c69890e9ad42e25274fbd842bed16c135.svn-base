package cc.chinagps.gateway.unit.kelong.upload.cmds;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.kelong.define.KeLongDefines;
import cc.chinagps.gateway.unit.kelong.pkg.KeLongPackage;
import cc.chinagps.gateway.unit.kelong.upload.bean.DataStorage;
import cc.chinagps.gateway.unit.kelong.upload.bean.KeLongBaseStationInfo;
import cc.chinagps.gateway.unit.kelong.upload.bean.KeLongCANInfo;
import cc.chinagps.gateway.unit.kelong.upload.bean.KeLongExtendInfo;
import cc.chinagps.gateway.unit.kelong.upload.bean.KeLongGPSInfo;
import cc.chinagps.gateway.unit.kelong.util.KeLongUploadUtil;
import cc.chinagps.gateway.util.Util;

/**
 * 事件上报
 */
public class Cmd0021 extends CheckLoginHandler {
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);

	@Override
	public boolean handlerPackageSessionChecked(KeLongPackage pkg, UnitServer server, UnitSocketSession session)
			throws Exception {
		try {
			byte data[] = pkg.getData();
			int pos = 0;
			int eventNum = data[pos++];
			Map<Long, KeLongGPSInfo> gpses = new HashMap<Long, KeLongGPSInfo>();
			for (int i = 0; i < eventNum; i++) {
				int locType = data[pos++];
				KeLongGPSInfo keLongGPSInfo = KeLongGPSInfo.parse(data, pos);
				Long gpsTime = keLongGPSInfo.getGpsTime();
				if (!gpses.containsKey(gpsTime)) {
					gpses.put(gpsTime, keLongGPSInfo);
				} else {
					keLongGPSInfo = gpses.get(gpsTime);
				}
				pos += 49;
				int eventType = Util.getShort(data, pos) & 0xFFFF;
				pos += 2;
				int eventDataLen = Util.getShort(data, pos) & 0xFFFF;
				pos += 2;
				byte eventData[] = new byte[eventDataLen];
				System.arraycopy(data, pos, eventData, 0, eventDataLen);
				int offset = 0;
				KeLongExtendInfo keLongExtendInfo = keLongGPSInfo.getKeLongExtendInfo();
				switch (eventType) {
				case 0x0001:// 终端插入报警
					keLongGPSInfo.getStatus().add(234);
					break;
				case 0x0002:// 终端拔出报警
					keLongGPSInfo.getStatus().add(235);
					break;
				case 0x0003:// 汽车电瓶低电压报警
					keLongGPSInfo.getStatus().add(3);
					break;
				case 0x0004:// 终端主电断电报警
					keLongGPSInfo.getStatus().add(4);
					break;
				case 0x0008:// GPS定位时间过长报警
					keLongGPSInfo.getStatus().add(10);
					break;
				case 0x0010:// 汽车点火上报
					keLongGPSInfo.getStatus().add(206);
					keLongExtendInfo = KeLongExtendInfo.parseThreeUrgent(keLongExtendInfo, eventData, offset);
					keLongGPSInfo.setKeLongExtendInfo(keLongExtendInfo);
					break;
				case 0x0011:// 汽车熄火上报
					keLongGPSInfo.getStatus().add(205);
					keLongExtendInfo = KeLongExtendInfo.parseThreeUrgent(keLongExtendInfo, eventData, offset);
					keLongGPSInfo.setKeLongExtendInfo(keLongExtendInfo);
					break;
				case 0x0023:// 碰撞报警
					keLongGPSInfo.getStatus().add(12);

					break;
				case 0x0024:// 拖车报警
					keLongGPSInfo.getStatus().add(17);

					break;
				case 0x0025:// 翻车报警
					keLongGPSInfo.getStatus().add(78);

					break;
				case 0x0027:// 超速报警
					keLongGPSInfo.getStatus().add(14);

					break;
				case 0x0028:// 疲劳报警
					keLongGPSInfo.getStatus().add(36);

					break;
				case 0x0032:// 怠速时间过长报警
					keLongGPSInfo.getStatus().add(236);
					keLongExtendInfo = KeLongExtendInfo.parseIdleEvent(keLongExtendInfo, eventData, offset);
					keLongGPSInfo.setKeLongExtendInfo(keLongExtendInfo);
					break;
				default:
					break;
				}
				pos += eventDataLen;
				
				String callLetter = session.getUnitInfo().getCallLetter();
				//从缓存中取CAN中的数据 2017-05-27 by cj
				if(null != DataStorage.getCanMap().get(callLetter)){					
					KeLongCANInfo can= DataStorage.getCanMap().get(callLetter);
					//can缓存中的时间和GPS时间相差3分钟之内
					if(Math.abs(keLongGPSInfo.getGpsTime()-can.getCanTime())<=KeLongDefines.CONSTANT){						
					    keLongGPSInfo.getKeLongOBDInfo().setRemainOil(can.getRemainingFuel());
					}
				}
				//从缓存中取基站数据 2017-05-27 by cj
				if(null != DataStorage.getBaseStationMap().get(callLetter)){
					KeLongBaseStationInfo baseStationInfo = DataStorage.getBaseStationMap().get(callLetter);
					//基站缓存数据中的时间和GPS时间相差3分钟之内
					if(Math.abs(keLongGPSInfo.getGpsTime()-baseStationInfo.getBaseStationTime())<=KeLongDefines.CONSTANT){
					    keLongGPSInfo.setKeLongBaseStationInfo(baseStationInfo);
					}
				}
			}

			for (KeLongGPSInfo gps : gpses.values()) {
				logger_debug.debug("[KeLong][cmd0021]KeLongGPSInfo:" + gps);
				boolean isHistory = gps.isHistory();
				// 保存gps
				KeLongUploadUtil.handleGPS(pkg, server, session, gps, isHistory);

				String callLetter = session.getUnitInfo().getCallLetter();
				// 车况信息(OBD)
				if (gps.getKeLongOBDInfo() != null) {
					KeLongUploadUtil.handleOBD(server, callLetter, gps.getGpsTime(), gps.getKeLongOBDInfo(), isHistory);
				}
			}

			KeLongUploadUtil.commonMsgAck(session, pkg, (short) 0x8000, (short) 0x0021, (byte) 0x00);
		} catch (Exception e) {
			// TODO: handle exception
			KeLongUploadUtil.commonMsgAck(session, pkg, (short) 0x8000, (short) 0x0021, (byte) 0x01);
			throw e;
		}
		return true;
	}
}