package cc.chinagps.seat.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import cc.chinagps.seat.bean.ReportAlarm;
import cc.chinagps.seat.bean.ReportBrief;
import cc.chinagps.seat.bean.ReportCommonQuery;
import cc.chinagps.seat.bean.ReportCommonResponse;
import cc.chinagps.seat.bean.ReportLocateFault;
import cc.chinagps.seat.bean.ReportManyGps;
import cc.chinagps.seat.bean.ReportResponseRatio;
import cc.chinagps.seat.bean.ReportSendCmd;
import cc.chinagps.seat.bean.ReportStolenVehicle;
import cc.chinagps.seat.bean.ReportUnitCom;
import cc.chinagps.seat.bean.ReportUnreportStat;
import cc.chinagps.seat.bean.table.AlarmTable;
import cc.chinagps.seat.bean.table.BriefTable;
import cc.chinagps.seat.bean.table.SendCmdTable;
import cc.chinagps.seat.bean.table.StolenVehicleTable;

public interface ReportService {

	ReportCommonResponse getAlarmCommonResp(ReportCommonQuery query, int interval);
	
	List<ReportResponseRatio> getAlarmResponseRatio(ReportCommonQuery query, int interval);

	List<ReportBrief> getBrief(BriefTable brief, 
			ReportCommonQuery query, Map<String, String> param);

	ReportCommonResponse getBriefCommonResp(BriefTable brief, 
			ReportCommonQuery query, Map<String, String> param);

	ReportCommonResponse getStolenVehicleCommonResp(
			StolenVehicleTable stolenVehicle, Map<String, Object> paramMap,
			ReportCommonQuery query);

	List<ReportStolenVehicle> getStolenVehicle(
			StolenVehicleTable stolenVehicle, Map<String, Object> paramMap,
			ReportCommonQuery query);

    ReportCommonResponse getUnreportStatCommResp(Map<String, Object> paramMap, 
    		ReportCommonQuery query);
    
    List<ReportUnreportStat> getUnreportStat(Map<String, Object> paramMap, 
    		ReportCommonQuery query);

    ReportCommonResponse getLocateFaultCommResp(Map<String, Object> paramMap, ReportCommonQuery query);
    
    /**
     * 获取定位故障统计
     * @param paramMap
     * @param query
     * @return
     */
    List<ReportLocateFault> getLocateFault(Map<String, Object> paramMap, ReportCommonQuery query);

    List<ReportSendCmd> getSendCmd(SendCmdTable sendCmd, 
			ReportCommonQuery query, Map<String, String> param);

	ReportCommonResponse getSendCmdCommonResp(SendCmdTable sendCmd, 
			ReportCommonQuery query, Map<String, String> param);
	
	ReportCommonResponse getAlarmAllCommonResp(AlarmTable alarm, 
			ReportCommonQuery query, Map<String, String> param);
	
	List<ReportAlarm> getAlarmAll(AlarmTable alarm, 
			ReportCommonQuery query, Map<String, String> param);
	
	ReportCommonResponse getManyGpsCommonResp(ReportCommonQuery query, Map<String, String> param);
	
	List<ReportManyGps> getManyGps(ReportCommonQuery query, Map<String, String> param);
		
	List<ReportUnitCom> getUnitCom(ReportCommonQuery query, Map<String, String> param)throws IOException;
}
