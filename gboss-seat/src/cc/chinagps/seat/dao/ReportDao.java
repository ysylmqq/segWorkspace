package cc.chinagps.seat.dao;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.client.HConnection;

import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsInfo;
import cc.chinagps.seat.bean.ReportCommonQuery;
import cc.chinagps.seat.bean.table.AlarmTable;
import cc.chinagps.seat.bean.table.BriefTable;
import cc.chinagps.seat.bean.table.SendCmdTable;
import cc.chinagps.seat.bean.table.StolenVehicleTable;

public interface ReportDao {

	List<Object[]> getAlarmResponseRatio(ReportCommonQuery query);

	List<Object[]> getBriefUnit(BriefTable brief, 
			ReportCommonQuery query, Map<String, String> param);

	long getBriefCount(BriefTable brief, 
			ReportCommonQuery query, Map<String, String> param);

	List<Object[]> getStolenVehicle(StolenVehicleTable stolenVehicle,
			Map<String, Object> paramMap, 
			ReportCommonQuery query);

	long getStolenVehicleCount(StolenVehicleTable stolenVehicle, 
			Map<String, Object> paramMap, 
			ReportCommonQuery query);

	long getUnreportStatCount(Map<String, Object> paramMap, ReportCommonQuery query);

	List<Object[]> getUnreportStat(Map<String, Object> paramMap, ReportCommonQuery query);

    long getLocateFaultCount(Map<String, Object> paramMap, ReportCommonQuery query);
    
    List<Object[]> getLocateFault(Map<String, Object> paramMap, ReportCommonQuery query);
    
    long getSendCmdCount(SendCmdTable sendCmd, 
			ReportCommonQuery query, Map<String, String> param);
    
    List<Object[]> getSendCmd(SendCmdTable sendCmd, 
			ReportCommonQuery query, Map<String, String> param);
    
    long getAlarmAllCount(AlarmTable alarm, 
			ReportCommonQuery query, Map<String, String> param);
    
    List<Object[]> getAlarmAll(AlarmTable alarm, 
			ReportCommonQuery query, Map<String, String> param);
    
    int getManyGpsCount(ReportCommonQuery query,Map<String, String>paprm);

	List<HashMap<String, Object>> getManyGps(ReportCommonQuery query,
			Map<String, String> param);
	List<GpsInfo> getUnitCom(ReportCommonQuery query,
			Map<String, String> param,HConnection conn) throws IOException;
	List<HashMap<String, Object>> getBaseInfo(String callLetter);
}
