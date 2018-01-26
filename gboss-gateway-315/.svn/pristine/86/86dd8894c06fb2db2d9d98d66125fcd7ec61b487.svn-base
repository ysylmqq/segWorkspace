package cc.chinagps.gateway.unit.kelong.upload.cmds;

import org.apache.log4j.Logger;

import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.kelong.define.KeLongDefines;
import cc.chinagps.gateway.unit.kelong.pkg.KeLongPackage;
import cc.chinagps.gateway.unit.kelong.upload.bean.DataStorage;
import cc.chinagps.gateway.unit.kelong.upload.bean.KeLongCANInfo;
import cc.chinagps.gateway.unit.kelong.upload.bean.KeLongGPSInfo;
import cc.chinagps.gateway.unit.kelong.upload.bean.KelongBaseStationPkg;
import cc.chinagps.gateway.unit.kelong.util.KeLongUploadUtil;
import cc.chinagps.gateway.util.HexUtil;

/**
 * 
* @ClassName: Cmd0011
* @Description: 基站定位数据包
* @author dy
* @date 2017年5月23日 下午12:23:39
*
 */
public class Cmd0011 extends CheckLoginHandler {
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	
	/**
	 * 
	 * @todo:解析基站包基站信息;
	 * @author:cj
	 * @param:
	 * @return:
	 * @remark:
	 */
	@Override
	public boolean handlerPackageSessionChecked(KeLongPackage pkg, UnitServer server, UnitSocketSession session)
			throws Exception {
		try {
			byte data[] = pkg.getData();
			int pos = 0 ;
			int gpsNum = data[pos++];

			for (int i = 0; i < gpsNum; i++) {
				KelongBaseStationPkg klBaseStationPkg = KelongBaseStationPkg.parse(data, pos);
				pos += 55;
				logger_debug.debug("[KeLong][cmd0011]KeLongBasestationPkg:" + klBaseStationPkg.toString()+",source:"+ HexUtil.byteToHexStr(pkg.getSource()));
				//一.保存baseStation到缓存				
				String callLetter = session.getUnitInfo().getCallLetter();
				if(null!=callLetter && !"".equals(callLetter)){
					DataStorage.getBaseStationMap().put(callLetter, klBaseStationPkg.getKeLongBaseStationInfo());
				}
				//二.科隆基站包转科隆gps信息包   作用:为防止车辆长时间不定位，无gps上报
				//1.组装科隆gps信息
				KeLongGPSInfo klGpsInfo= KelongBaseStationPkg.makeupGpsInfoPkg(klBaseStationPkg);				
				boolean isHistory = klGpsInfo.isHistory();
				//2.从缓存中取CAN中的数据 
				if(null != DataStorage.getCanMap().get(callLetter)){					
					KeLongCANInfo can= DataStorage.getCanMap().get(callLetter);
					//can缓存中的时间和GPS时间相差3分钟之内
					if(Math.abs(klGpsInfo.getGpsTime()-can.getCanTime())<=KeLongDefines.CONSTANT){						
						klGpsInfo.getKeLongOBDInfo().setRemainOil(can.getRemainingFuel());
					}
				}
				logger_debug.debug("[KeLong][cmd0011]makeupKlGpsInfo:" + klGpsInfo);
				//3.处理转化后的科隆gps信息包				
				KeLongUploadUtil.handleGPS(pkg, server, session, klGpsInfo, isHistory);
			}
			//4.发送响应;
			KeLongUploadUtil.commonMsgAck(session, pkg, (short) 0x8000, (short) 0x0011, (byte) 0x00);
		} catch (Exception e) {
			System.out.println("e:" + e.getMessage());
			// TODO: handle exception
			KeLongUploadUtil.commonMsgAck(session, pkg, (short) 0x8000, (short) 0x0011, (byte) 0x01);
			throw e;
		}
		return true;
	}
	
}