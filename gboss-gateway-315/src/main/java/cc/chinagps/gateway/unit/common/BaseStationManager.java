package cc.chinagps.gateway.unit.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.unit.beans.GbossGpsInfo;
import cc.chinagps.gateway.util.SystemConfig;

public class BaseStationManager {
	public static BaseStationManager instance;
	private String url = "http://minigps.net/cw?p=1&needaddress=0&mt=0";
	public int handleThreadNum = 10;
	private int agpsCacheSize = 5000;
	private long agpsCount = 0l;
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);

	public List<BaseStationHandler> baseStationHandlers = new ArrayList<BaseStationHandler>();

	public void init() {
		String num = SystemConfig.getSystemProperty("basestation_handler_num");
		if (num != null) {
			handleThreadNum = Integer.valueOf(num);
		}
		String gpsUrl = SystemConfig.getSystemProperty("minigps_basestation_url");
		if (gpsUrl != null) {
			url = gpsUrl;
		}
		String agpsSize = SystemConfig.getSystemProperty("agps_cache_size");
		if (agpsSize != null) {
			agpsCacheSize = Integer.valueOf(agpsSize);
		}
		for (int i = 0; i < handleThreadNum; i++) {
			BaseStationHandler baseStationHandler = new BaseStationHandler(i,url,agpsCacheSize);
			baseStationHandler.startService();
			baseStationHandlers.add(baseStationHandler);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("[" + sdf.format(new Date()) + "]init BaseStationManager  finished!Handler Num:"+handleThreadNum);
	}

	public BaseStationHandler getBaseStationHandler(int index) {
		return baseStationHandlers.get(index);
	}

	public synchronized static BaseStationManager getInstance() {
		if (instance == null) {
			instance = new BaseStationManager();
		}
		return instance;
	}

	public boolean addBaseStationGps(GbossGpsInfo gbossGpsInfo) {
		if (agpsCount >= Long.MAX_VALUE) {
			agpsCount = 0l;
		}
		agpsCount++;
		logger_debug.debug("agps total count is :" + agpsCount);
		int hashCode = gbossGpsInfo.getGps().getCallLetter().hashCode();
		hashCode = Math.abs(hashCode);
		int threadIndex = hashCode % handleThreadNum;
		getBaseStationHandler(threadIndex).addGps(gbossGpsInfo);
		return true;
	}

}
