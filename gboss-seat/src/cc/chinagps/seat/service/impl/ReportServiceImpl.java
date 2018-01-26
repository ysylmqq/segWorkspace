package cc.chinagps.seat.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsInfo;
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
import cc.chinagps.seat.bean.VehicleUnitInfo;
import cc.chinagps.seat.bean.table.AlarmStatusTable;
import cc.chinagps.seat.bean.table.AlarmTable;
import cc.chinagps.seat.bean.table.BriefTable;
import cc.chinagps.seat.bean.table.CustomerTable;
import cc.chinagps.seat.bean.table.LastPositionTable;
import cc.chinagps.seat.bean.table.SendCmdTable;
import cc.chinagps.seat.bean.table.StatusTable;
import cc.chinagps.seat.bean.table.StolenVehicleTable;
import cc.chinagps.seat.bean.table.UnitTable;
import cc.chinagps.seat.bean.table.VehicleTable;
import cc.chinagps.seat.dao.AlarmDao;
import cc.chinagps.seat.dao.HBaseDao;
import cc.chinagps.seat.dao.ReportDao;
import cc.chinagps.seat.dao.UtilDao;
import cc.chinagps.seat.hbase.GpsTable;
import cc.chinagps.seat.service.ReportService;
import cc.chinagps.seat.util.Consts;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ReportDao reportDao;
	@Autowired
	private AlarmDao alarmDao;
	@Autowired
	private UtilDao utilDao;
	@Autowired
	private HBaseDao exportDao;
	@Autowired
	private HConnection conn;
	
	private final Log LOGGER = LogFactory.getLog(getClass());
	
	@Override
	public List<ReportBrief> getBrief(BriefTable brief, 
			ReportCommonQuery query, Map<String, String> param) {
		List<Object[]> briefUnitList = 
				reportDao.getBriefUnit(brief, query, param);
		List<ReportBrief> rbList = new ArrayList<ReportBrief>(
				briefUnitList.size());
		int start = query.getStart();
		for (Object[] briefUnit : briefUnitList) {
			ReportBrief rb = new ReportBrief();
			rb.setBrief((BriefTable) briefUnit[0]);
			rb.setCustomerName((String) briefUnit[1]);
			UnitTable unit = (UnitTable) briefUnit[2];
			VehicleUnitInfo vuInfo = new VehicleUnitInfo(
					unit.getVehicle().getId(), 
					unit.getVehicle().getPlateNo(), 
					unit.getCallLetter());
			rb.setVuInfo(vuInfo);
			
			rb.setSn(++start);
			rbList.add(rb);
		}
		return rbList;
	}
	
	@Override
	public ReportCommonResponse getBriefCommonResp(BriefTable brief, 
			ReportCommonQuery query, Map<String, String> param) {
		long count = reportDao.getBriefCount(brief, query, param);
		ReportCommonResponse commResp = new ReportCommonResponse();
		commResp.setRecordsFiltered(count);
		commResp.setRecordsTotal(count);
		return commResp;
	}
	
	@Override
	public List<ReportStolenVehicle> getStolenVehicle(
			StolenVehicleTable stolenVehicle, Map<String, Object> paramMap,
			ReportCommonQuery query) {
		List<Object[]> svList = reportDao.getStolenVehicle(stolenVehicle, paramMap, query);
		List<ReportStolenVehicle> rsvList = new ArrayList<ReportStolenVehicle>(svList.size());
		int start = query.getStart();
		for (Object[] sv : svList) {
			UnitTable unit = (UnitTable) sv[1];
			ReportStolenVehicle rsv = new ReportStolenVehicle();
			rsv.setStolenVehicle((StolenVehicleTable) sv[0]);
			rsv.setUnit(unit);
			rsv.setSn(++start);
			rsvList.add(rsv);
		}
		return rsvList;
	}
	
	@Override
	public ReportCommonResponse getStolenVehicleCommonResp(
			StolenVehicleTable stolenVehicle, Map<String, Object> paramMap,
			ReportCommonQuery query) {
		long count = reportDao.getStolenVehicleCount(
				stolenVehicle, paramMap, query);
		ReportCommonResponse commResp = new ReportCommonResponse();
		commResp.setRecordsFiltered(count);
		commResp.setRecordsTotal(count);
		return commResp;
	}
	
	@Override
	public List<ReportResponseRatio> getAlarmResponseRatio(ReportCommonQuery query, int interval) {
		Date queryBeginTime = query.getBeginTime();
		Date queryEndTime = query.getEndTime();
		
		Calendar beginTime = Calendar.getInstance();
		beginTime.setTime(queryBeginTime);
		Calendar endTime = Calendar.getInstance();
		endTime.setTime(queryEndTime);
		
		int minute = translateToMinutes(interval);
		
		// 序号
		int sn = 1;
		
		// 开始结束索引
		int beginIndex = query.getStart();
		int endIndex = query.getLength() + beginIndex;
		
		// 计算查询的开始结束时间
		for (int i = 0; i < beginIndex; i++) {
			beginTime.add(Calendar.MINUTE, minute);
			sn++;
		}
		query.setBeginTime(beginTime.getTime());
		for (int i = beginIndex; i < endIndex; i++) {
			beginTime.add(Calendar.MINUTE, minute);
		}
		if (beginTime.compareTo(endTime) < 0 && (beginIndex < endIndex)) {
			query.setEndTime(beginTime.getTime());
		}
		
		List<Object[]> arrList = reportDao.getAlarmResponseRatio(query);

		List<ReportResponseRatio> respRatioList = new ArrayList<ReportResponseRatio>();
		
		boolean breakWhile = false;
		int index = 0;
		
		// 统计时段内总条数
		int sum = 0;
		
		beginTime.setTime(query.getBeginTime());
		endTime.setTime(query.getEndTime());
		while (true) {
			
			ReportResponseRatio respRatio = new ReportResponseRatio();
			respRatio.setSn(sn++);
			respRatio.setBeginTime(beginTime.getTime());
			beginTime.add(Calendar.MINUTE, minute);
			
			if (beginTime.compareTo(endTime) >= 0) {
				respRatio.setEndTime(endTime.getTime());
				breakWhile = true;
			} else {
				respRatio.setEndTime(beginTime.getTime());
			}
			for (int i = index; i < arrList.size(); i++) {
				Object[] arr = arrList.get(i);
				long acceptTimeSpan = (Long) arr[0];
				Date acceptTime = (Date) arr[1];
				
				// 响应时间在统计时段内,进行数量的统计
				if (acceptTime.compareTo(respRatio.getBeginTime()) >= 0 && 
						acceptTime.compareTo(respRatio.getEndTime()) < 0) {
					for (Entry<String, long[]> entry : Consts.RESP_RATIO.entrySet()) {
						long[] value = entry.getValue();
						if (acceptTimeSpan >= value[0] && acceptTimeSpan < value[1]) {
							Long statItem = (Long) respRatio.getStatItem(entry.getKey());
							if (statItem == null) {
								statItem = 1L;
							} else {
								statItem++;
							}
							respRatio.putStatisticItem(entry.getKey(), statItem);
						}
					}
					sum++;
				} else {
					// 下次统计计算开始的索引
					index = i;
					break;
				}
			}
			// 计算统计时间段内，各统计项个数的百分比
			for (Entry<String, long[]> entry : Consts.RESP_RATIO.entrySet()) {
				Long statItem = (Long) respRatio.getStatItem(entry.getKey());
				if (statItem == null) {
					statItem = 0L;
					respRatio.putStatisticItem(entry.getKey(), statItem);
				}
				long percentage = 0;
				if (sum > 0) {
					percentage = statItem * 100 / sum;
				}
				respRatio.putStatisticItem(entry.getKey() + "%", percentage);
			}
			respRatio.setSum(sum);
			respRatioList.add(respRatio);
			
			// 重置统计总数
			sum = 0;
			
			if (breakWhile) {
				break;
			}
		}
		
		// 重新设置开始结束时间
		query.setBeginTime(queryBeginTime);
		query.setEndTime(queryEndTime);
		return respRatioList;
	}
	
	@Override
	public ReportCommonResponse getAlarmCommonResp(ReportCommonQuery query, int interval) {
		Calendar beginTime = Calendar.getInstance();
		beginTime.setTime(query.getBeginTime());
		Calendar endTime = Calendar.getInstance();
		endTime.setTime(query.getEndTime());
		long count = 0;
		int minute = translateToMinutes(interval);
		while (beginTime.before(endTime)) {
			beginTime.add(Calendar.MINUTE, minute);
			count++;
		}
		ReportCommonResponse commResp = new ReportCommonResponse();
		commResp.setRecordsFiltered(count);
		commResp.setRecordsTotal(count);
		return commResp;
	}

	private int translateToMinutes(int interval) {
		int minute;
		switch (interval) {
			case 1:
				minute = 30;
				break;
			case 2:
				minute = 60;
				break;
			case 3:
				minute = 24 * 60;
				break;
			case 4:
				minute = 30 * 24 * 60;
				break;
			default:
				minute = 30;
				break;
		}
		return minute;
	}
	
	@Override
	public List<ReportUnreportStat> getUnreportStat(
			Map<String, Object> paramMap, ReportCommonQuery query) {
		extractDate(paramMap, query);
		List<Object[]> usList = reportDao.getUnreportStat(
				paramMap, query);
		List<ReportUnreportStat> rusList = new ArrayList<ReportUnreportStat>(
				usList.size());
		Map<Long, AlarmStatusTable> alarmStatus = alarmDao.getAlarmStatus();
		int start = query.getStart();
		for (Object[] sv : usList) {
			UnitTable unit = (UnitTable) sv[1];
			ReportUnreportStat rus = new ReportUnreportStat();
			LastPositionTable lastPosition = (LastPositionTable) sv[0];
			String status = lastPosition.getStatus();
			lastPosition.setStatus(translateLastPositionStatus(alarmStatus, status));
			rus.setLastPosition(lastPosition);
			rus.setUnit(unit);
			rus.setPhone((String) sv[2]);
			rus.setSn(++start);
			rusList.add(rus);
		}
		return rusList;
	}

	@Override
	public ReportCommonResponse getUnreportStatCommResp(
			Map<String, Object> paramMap, ReportCommonQuery query) {
		extractDate(paramMap, query);
		long count = reportDao.getUnreportStatCount(
				paramMap, query);
		ReportCommonResponse commResp = new ReportCommonResponse();
		commResp.setRecordsFiltered(count);
		commResp.setRecordsTotal(count);
		return commResp;
	}
	
	private void extractDate(Map<String, Object> paramMap,
			ReportCommonQuery query) {
		Date beginTime = query.getBeginTime();
		Date endTime = query.getEndTime();
		int report;
		try {
			report = Integer.parseInt((String) paramMap.get("report"));
		} catch (NumberFormatException e) {
			report = 0;
		}
		if (report == 0) {
			String intervalStr = (String) paramMap.get("interval");
			if (intervalStr != null) {
				try {
					int interval = Integer.parseInt(intervalStr);
					endTime = alarmDao.getCurrentTime();
					Calendar tmp = Calendar.getInstance();
					
					// 避免数据库时间与服务器时间不一致，时间也设置为数据库时间
					tmp.setTime(endTime);
					tmp.set(Calendar.HOUR_OF_DAY, 0);
					tmp.set(Calendar.MINUTE, 0);
					tmp.set(Calendar.SECOND, 0);
					tmp.set(Calendar.MILLISECOND, 0);
					tmp.add(Calendar.DAY_OF_MONTH, -interval + 1);
					beginTime = tmp.getTime();
				} catch (NumberFormatException e) {
					LOGGER.warn("", e);
				}
			}
		} 
		paramMap.put("beginTime", beginTime);
		paramMap.put("endTime", endTime);
	}
	
	@Override
	public List<ReportLocateFault> getLocateFault(
			Map<String, Object> paramMap, ReportCommonQuery query) {
		extractDate(paramMap, query);
		List<Object[]> usList = reportDao.getLocateFault(
				paramMap, query);
		Map<Long, AlarmStatusTable> alarmStatus = alarmDao.getAlarmStatus();
		List<ReportLocateFault> rusList = new ArrayList<ReportLocateFault>(
				usList.size());
		int start = query.getStart();
		for (Object[] sv : usList) {
			UnitTable unit = (UnitTable) sv[1];
			ReportLocateFault rus = new ReportLocateFault();
			LastPositionTable lastPosition = (LastPositionTable) sv[0];
			String status = lastPosition.getStatus();
			lastPosition.setStatus(translateLastPositionStatus(alarmStatus, status));
			rus.setLastPosition(lastPosition);
			rus.setUnit(unit);
			rus.setPhone((String) sv[2]);
			rus.setSn(++start);
			rusList.add(rus);
		}
		return rusList;
	}

	private String translateLastPositionStatus(
			Map<Long, AlarmStatusTable> alarmStatus, String status) {
		if (status != null && status.length() > 0) {
			String[] split = status.split(",");
			StringBuilder sBuilder = new StringBuilder();
			for (int i = 0; i < split.length; i++) {
				AlarmStatusTable statusTable = alarmStatus.get(Long.parseLong(split[i]));
				if (statusTable != null) {
					sBuilder.append(statusTable.getName());
					sBuilder.append(",");
				}
			}
			sBuilder.deleteCharAt(sBuilder.length() - 1);
			status = sBuilder.toString();
		}
		return status;
	}

	@Override
	public ReportCommonResponse getLocateFaultCommResp(
			Map<String, Object> paramMap, ReportCommonQuery query) {
		extractDate(paramMap, query);
		long count = reportDao.getLocateFaultCount(
				paramMap, query);
		ReportCommonResponse commResp = new ReportCommonResponse();
		commResp.setRecordsFiltered(count);
		commResp.setRecordsTotal(count);
		return commResp;
	}

	@Override
	public List<ReportSendCmd> getSendCmd(SendCmdTable sendCmd,
			ReportCommonQuery query, Map<String, String> param) {
		List<Object[]> sendCmdList = 
				reportDao.getSendCmd(sendCmd, query, param);
		List<ReportSendCmd> scList = new ArrayList<ReportSendCmd>(
				sendCmdList.size());
		int start = query.getStart();
		for (Object[] sc : sendCmdList) {
			ReportSendCmd rsc = new ReportSendCmd();
			SendCmdTable temp = (SendCmdTable) sc[0];
			if(temp.getOpIp()!=null&&!temp.equals("")){
				temp.setOpIp(temp.getOpIp().substring(1));
			}
			temp.setCompanyName(sc[2]==null?"":sc[2].toString());
			rsc.setSendCmd(temp);
			rsc.setSn(++start);
			scList.add(rsc);
		}
		return scList;
	
	}

	@Override
	public ReportCommonResponse getSendCmdCommonResp(SendCmdTable sendCmd,
			ReportCommonQuery query, Map<String, String> param) {
		long count = reportDao.getSendCmdCount(sendCmd, query, param);
		ReportCommonResponse commResp = new ReportCommonResponse();
		commResp.setRecordsFiltered(count);
		commResp.setRecordsTotal(count);
		return commResp;
	}
	
	@Override
	public ReportCommonResponse getAlarmAllCommonResp(AlarmTable alarm,
			ReportCommonQuery query, Map<String, String> param) {
		long count = reportDao.getAlarmAllCount(alarm, query, param);
		ReportCommonResponse commResp = new ReportCommonResponse();
		commResp.setRecordsFiltered(count);
		commResp.setRecordsTotal(count);
		return commResp;
	}

	@Override
	public List<ReportAlarm> getAlarmAll(AlarmTable alarm,
			ReportCommonQuery query, Map<String, String> param) {
		List<StatusTable> status = utilDao.getStatusList();
		Map<String,String> map = new HashMap<String,String>();
		for(StatusTable entity:status){
			map.put(entity.getStatusId().toString(), entity.getStatusName());
		}
		List<Object[]> alarmList = reportDao.getAlarmAll(alarm, query, param);
		List<ReportAlarm> raList = new ArrayList<ReportAlarm>(alarmList.size());
		int start = query.getStart();
		for (Object[] entity : alarmList) {
			ReportAlarm ralarm = new ReportAlarm();
			AlarmTable temp = (AlarmTable) entity[0];
			if(temp.getStatusIds()!=null&&!temp.getStatusIds().equals("")){
			    String[] statusids=temp.getStatusIds().split(",");
			    StringBuffer sBuffer=new StringBuffer();
			    for(int i=0;i<statusids.length;i++){
					if (map.get(statusids[i])!=null&&!map.get(statusids[i]).equals("null")) {
						sBuffer.append(map.get(statusids[i]));
						if (i < statusids.length - 1) {
							sBuffer.append(",");
						}
					}
			    }
			    ralarm.setStatus(sBuffer.toString());
			}
			ralarm.setCompanyName(entity[1]==null?"":entity[1].toString());
			ralarm.setVehicleType(entity[2]==null?"":entity[2].toString());
			if(entity[3]!=null){
				UnitTable unitTable = (UnitTable)entity[3];
				ralarm.setMode(unitTable.getMode());
			}
			if(entity[4]!=null){
				CustomerTable customerTable = (CustomerTable)entity[4];
				ralarm.setCustomer(customerTable.getCustomerName());
			}
			if(entity[5]!=null){
				VehicleTable vehicleTable = (VehicleTable)entity[5];
				ralarm.setPlateNo(vehicleTable.getPlateNo());
			}
			
			ralarm.setAlarm(temp);
			ralarm.setSn(++start);
			raList.add(ralarm);
		}
		return raList;
	
	}

	@Override
	public ReportCommonResponse getManyGpsCommonResp(ReportCommonQuery query,
			Map<String, String> param) {
		long count = reportDao.getManyGpsCount(query, param);
		ReportCommonResponse commResp = new ReportCommonResponse();
		commResp.setRecordsFiltered(count);
		commResp.setRecordsTotal(count);
		return commResp;
	}

	@Override
	public List<ReportManyGps> getManyGps(ReportCommonQuery query,
			Map<String, String> param) {
		List<HashMap<String, Object>> list = reportDao.getManyGps(query, param);
		List<ReportManyGps> resultList = new ArrayList<ReportManyGps>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int sn = 0;
		for(HashMap<String, Object> entity:list){
			ReportManyGps manyGps = new ReportManyGps();
			sn++;
			String call_letter = entity.get("callLetter").toString();
			manyGps.setSn(sn);
			manyGps.setCallLetter(call_letter);
			manyGps.setFix_time(entity.get("fix_time")+"");
			manyGps.setMode(entity.get("mode")+"");
			manyGps.setPlateNo(entity.get("plateNo")+"");
			manyGps.setAlarmcount(entity.get("alarmcount")+"");
			try {
				GpsTable gpsTable = new GpsTable(conn);
				GpsInfo gpsInfo = gpsTable.GetFirstGpsInfo(call_letter, System.currentTimeMillis() + 24*3600000l, 24l*3600000l*40*365,true);
			    manyGps.setGps_time(sdf.format(gpsInfo.getBaseInfo().getGpsTime()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			resultList.add(manyGps);
			
		}
		return resultList;
	}

	@Override
	public List<ReportUnitCom> getUnitCom(ReportCommonQuery query,
			Map<String, String> param) throws IOException {
		List<ReportUnitCom> resultList= new ArrayList<ReportUnitCom>();
		List<GpsInfo> list = new ArrayList<GpsInfo>();		
		list = reportDao.getUnitCom(query, param, conn);
		if(list!=null && list.size()>0){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    List<HashMap<String, Object>> baseInfo = reportDao.getBaseInfo(param.get("callLetter"));
	    List<StatusTable> status = utilDao.getStatusList();
	    int sn = 0;
		for(GpsInfo entity:list){
			sn++;
			ReportUnitCom unitCom = new ReportUnitCom();			
			unitCom.setSn(sn);
			unitCom.setCallLetter(entity.getCallLetter());
			unitCom.setStatus(getStatus(status,entity.getBaseInfo().getStatusList()));
			unitCom.setGpstime(sdf.format(entity.getBaseInfo().getGpsTime()));
			unitCom.setCompanyName(baseInfo.get(0).get("sname")+"");
			unitCom.setMode(baseInfo.get(0).get("model")+"");
			unitCom.setCustomer(baseInfo.get(0).get("customer_name")+"");
			unitCom.setPlateNo(baseInfo.get(0).get("plate_no")+"");
			resultList.add(unitCom);
		}
		};
		return resultList;
	}	
	
	public String getStatus(List<StatusTable> status, List<Integer> statsuidList){		
		Map<String,String> map = new HashMap<String,String>();
		for(StatusTable entity:status){
			map.put(entity.getStatusId().toString(), entity.getStatusName());
		}
		
	    StringBuffer sBuffer=new StringBuffer();
	    for(Integer id:statsuidList){
			if (map.get(id.toString())!=null&&!map.get(id.toString()).equals("null")) {
				sBuffer.append(map.get(id.toString()));
				sBuffer.append(",");

			}
	    }	
	    return sBuffer.toString();
	}
}
