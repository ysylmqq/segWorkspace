package cc.chinagps.gateway.web.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.util.SystemConfig;

public class WebUtil {
	public static Map<String, Object> getUnitServerBaseInfo(UnitServer unitServer){
		int unitCount = unitServer.getSessionManager().getSocketSessionMap().size();
		int unitCount2 = unitServer.getSessionManager().getCallSocketSessionMap().size();
		
		int aPlanCount = unitServer.getAPlanServer().getSessionManager().getSocketSessionMap().size();
		int aPlanAlarmCount = unitServer.getAPlanAlarmServer().getSessionManager().getSocketSessionMap().size();
		int seatCount = unitServer.getSeatServer().getTCPServer().getConnectionCount();
		
		Runtime runTime = Runtime.getRuntime();
		long maxMemory = runTime.maxMemory();
		long freeMemory = runTime.freeMemory();
		long totalMemory = runTime.totalMemory();
		
		long startTime = unitServer.getServerStartTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTimeStr = sdf.format(new Date(startTime));
		
		NumberFormat nf = new DecimalFormat("#.##");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("version", cc.chinagps.gateway.util.Constants.VERSION);
		map.put("id", cc.chinagps.gateway.util.Constants.SYSTEM_ID);
		map.put("port", SystemConfig.getSystemProperty("unit_server_port"));
		map.put("unitCount", unitCount);
		map.put("unitCount2", unitCount2);
		map.put("aPlanCount", aPlanCount);
		map.put("aPlanAlarmCount", aPlanAlarmCount);
		map.put("seatCount", seatCount);
		map.put("maxMemory", nf.format(maxMemory / 1048576.0));
		map.put("freeMemory", nf.format(freeMemory/ 1048576.0));
		map.put("totalMemory", nf.format(totalMemory/ 1048576.0));
		map.put("startTime", startTimeStr);
		
		return map;
	}
}