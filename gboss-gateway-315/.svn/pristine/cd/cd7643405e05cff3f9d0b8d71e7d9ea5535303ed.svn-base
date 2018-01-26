package cc.chinagps.gateway.unit.kelong.upload.cmds;

import org.apache.log4j.Logger;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.memcache.MemcacheManager;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.kelong.pkg.KeLongPackage;
import cc.chinagps.gateway.unit.kelong.upload.bean.DataStorage;
import cc.chinagps.gateway.unit.kelong.upload.bean.KeLongCANInfo;
import cc.chinagps.gateway.unit.kelong.util.KeLongUploadUtil;

/**
 * 
* @ClassName: Cmd0012
* @Description: CAN上报
* @author dy
* @date 2017年5月23日 上午9:04:33
*
 */
public class Cmd0012 extends CheckLoginHandler {
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	@Override
	public boolean handlerPackageSessionChecked(KeLongPackage pkg, UnitServer server, UnitSocketSession session)
			throws Exception {
		try {
			byte data[] = pkg.getData();
			int pos = 0 ;
			int gpsNum = data[pos++];
			for (int i = 0; i < gpsNum; i++) {
				String callLetter = session.getUnitInfo().getCallLetter();				
				if(null!=callLetter && !"".equals(callLetter)){
					Integer capacity = 50;
					Object baseInfo = MemcacheManager.getMemcachedClient().get(MemcacheManager.BASE_INFO_KEY_HEAD + callLetter);
					if(baseInfo != null){
						try{
							String[] params = baseInfo.toString().split(",", -1);
							capacity = Integer.valueOf(params[14]);							
						}catch (Throwable e) {
							server.exceptionCaught(e);
						}
					}
					KeLongCANInfo keLongCANInfo = KeLongCANInfo.parse(data, pos,capacity);
					logger_debug.debug("[KeLong][cmd0012]KeLongCANInfo:" + keLongCANInfo);
					pos += 83;
					// 保存can到缓存
					DataStorage.getCanMap().put(callLetter, keLongCANInfo);
				}				
			}
			KeLongUploadUtil.commonMsgAck(session, pkg, (short) 0x8000, (short) 0x0012, (byte) 0x00);
		} catch (Exception e) {
			// TODO: handle exception
			KeLongUploadUtil.commonMsgAck(session, pkg, (short) 0x8000, (short) 0x0012, (byte) 0x01);
			throw e;
		}
		return true;
	}
}