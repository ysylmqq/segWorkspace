package cc.chinagps.gateway.unit.leopaard.util;

import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beforemarket.common.util.BeforeMarketConstants;

public class LeopaardPkgUtil {
	/**
	 * 获取指令sn
	 */
	public static short getSn(UnitSocketSession unitSession){
		synchronized (unitSession) {
			Object obj = unitSession.getAttribute(BeforeMarketConstants.BM_SESSION_KEY_SN);
			Short v;
			if(obj == null){
				v = 1;
			}else{
				v = (Short) obj;
				v++;
			}
			
			unitSession.setAttribute(BeforeMarketConstants.BM_SESSION_KEY_SN, v);
			return v;
		}
	}
	/**
	 * 获取下发指令缓存sn
	 */
	public static String getCmdCacheSn(String callLetter, short headSN){
		return callLetter + "_" + headSN;
	}
}
