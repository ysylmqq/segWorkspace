package cc.chinagps.seat.dao.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.client.HConnection;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsInfo;
import cc.chinagps.seat.bean.ReportCommonQuery;
import cc.chinagps.seat.bean.table.AlarmTable;
import cc.chinagps.seat.bean.table.BriefTable;
import cc.chinagps.seat.bean.table.SendCmdTable;
import cc.chinagps.seat.bean.table.StolenVehicleTable;
import cc.chinagps.seat.dao.ReportDao;
import cc.chinagps.seat.hbase.GpsTable;

@Repository
public class ReportDaoImpl extends BasicDao implements ReportDao {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getBriefUnit(BriefTable brief, 
			ReportCommonQuery query, Map<String, String> param) {
		Session session = getSession();
		Query namedQuery = getBriefNamedQueryWithParams(
				session, "SelectBriefReport", brief, query, param);
		if (query.getLength() > 0) {
			namedQuery.setMaxResults(query.getLength());
		}
		return namedQuery.setFirstResult(query.getStart()).list();
	}
	
	@Override
	public long getBriefCount(BriefTable brief, 
			ReportCommonQuery query, Map<String, String> param) {
		Session session = getSession();
		Query namedQuery =  getBriefNamedQueryWithParams(
				session, "SelectBriefReportCount", brief, query, param);
		return (Long) namedQuery.uniqueResult();
	}
	
	private Query getBriefNamedQueryWithParams(Session session, 
			String queryName, BriefTable brief, 
			ReportCommonQuery query, Map<String, String> param) {
		
		String queryStr = session.getNamedQuery(queryName)
				.getQueryString();
		StringBuilder sBuilder = new StringBuilder(queryStr);
		String vehicleId = param.get("vehicleId");
		if (vehicleId != null) {
			sBuilder.append(" AND unit.vehicleId = ").append(vehicleId);
		}
		String partNo = param.get("partNo");
		if (partNo != null && !partNo.equals("")) {
			sBuilder.append(" AND (unit.callLetter LIKE ")
				.append("'%").append(partNo).append("%' ")
				.append("OR unit.vehicle.plateNo LIKE ")
				.append("'%").append(partNo).append("%') ");
		}
		String phoneNo = brief.getPhone();
		if (phoneNo != null && !phoneNo.equals("")) {
			sBuilder.append(" AND brief.phoneNo LIKE '%")
				.append(brief.getPhone()).append("%'");
		}
		
		// 服务类型需要模糊匹配
		String[] types = brief.getServiceType();
		boolean containTypes = types != null && types.length > 0;
		if (containTypes) {
			sBuilder.append(" AND (");
			for (String type : types) {
				sBuilder.append(" brief.serviceContent LIKE '%")
					.append(type).append("%' OR");
			}
			sBuilder.delete(sBuilder.length() - 2, sBuilder.length());
			sBuilder.append(" )");
		}
		String serviceTypeOther = brief.getServiceTypeOther();
		if (serviceTypeOther != null) {
			if (containTypes) {
				sBuilder.deleteCharAt(sBuilder.length() - 1);
				sBuilder.append(" OR brief.serviceContent LIKE '%")
					.append(serviceTypeOther).append("%')");
			} else {
				sBuilder.append(" AND brief.serviceContent LIKE '%")
					.append(serviceTypeOther).append("%'");
			}
		}
		Byte type = brief.getType();
		if (type != null && type != 0) {
			sBuilder.append(" AND brief.type=").append(type);
		}
		String opId = param.get("opId");
		if (opId != null && !opId.equals("")) {
			sBuilder.append(" AND brief.opId=").append(opId);
		}
		if (query.getCompanyNo().length > 0) {
			sBuilder.append(" AND unit.companyNo IN (:companyNo) ");
		}
		sBuilder.append(" ORDER BY brief.stamp ");
		
		Query tmpQuery = session.createQuery(sBuilder.toString())
			.setParameter("startTime", query.getBeginTime())
			.setParameter("endTime", query.getEndTime());
		if (query.getCompanyNo().length > 0) {
			tmpQuery.setParameterList("companyNo", query.getCompanyNo());
		}
		return tmpQuery;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAlarmResponseRatio(ReportCommonQuery query) {
		
		Session session = getSession();
		String queryStr = session.getNamedQuery("SelectResponseRatioReport").getQueryString();
		StringBuilder sBuilder = new StringBuilder(queryStr);
		if (query.getCompanyNo().length > 0) {
			sBuilder.insert(sBuilder.lastIndexOf("AND"), 
					" AND unit.companyNo IN (:companyNo) ");
		}
		Query namedQuery = session.createQuery(sBuilder.toString())
				.setParameter("startTime", query.getBeginTime())
				.setParameter("endTime", query.getEndTime());
		if (query.getCompanyNo().length > 0) {
			namedQuery.setParameterList("companyNo", query.getCompanyNo());
		}
		return namedQuery.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getStolenVehicle(StolenVehicleTable stolenVehicle,
			Map<String, Object> paramMap, 
			ReportCommonQuery query) {
		Session session = getSession();
		Query namedQuery = getStolenVehicleNamedQueryWithParams(
				session, "SelectStolenVehicleReport", stolenVehicle, paramMap, query);
		if (query.getLength() > 0) {
			namedQuery.setMaxResults(query.getLength());
		}
		List<Object[]> list = namedQuery.setFirstResult(query.getStart()).list();
//		for (Object[] obj : list) {
//			UnitTable unit = (UnitTable) obj[1];
//			// fetchType为lazy的类型，需要提前加载，
//			// 否则后续再加载时会报session已经关闭异常
//			if (!Hibernate.isInitialized(unit.getCustomer())) {
//				Hibernate.initialize(unit.getCustomer());
//			}
//			if (!Hibernate.isInitialized(unit.getVehicle())) {
//				Hibernate.initialize(unit.getVehicle());
//			}
//		}
		
		return list;
	}
	
	@Override
	public long getStolenVehicleCount(StolenVehicleTable stolenVehicle,
			Map<String, Object> paramMap,
			ReportCommonQuery query) {
		Session session = getSession();
		Query namedQuery =  getStolenVehicleNamedQueryWithParams(
				session, "SelectStolenVehicleReportCount", stolenVehicle, paramMap, query);
		return (Long) namedQuery.uniqueResult();
	}
	
	private Query getStolenVehicleNamedQueryWithParams(Session session, 
			String queryName, StolenVehicleTable stolenVehicle,
			Map<String, Object> paramMap,
			ReportCommonQuery query) {
		
		String queryStr = session.getNamedQuery(queryName)
				.getQueryString();
		StringBuilder sBuilder = new StringBuilder(queryStr);
		String buyTp = (String)paramMap.get("buyTp");
		if (buyTp != null && !buyTp.equals("") && !buyTp.equals("0")) {
			sBuilder.append(" AND unit.vehicle.insurance.buyTp =").append(buyTp);
		}
		
		if (stolenVehicle.getVehicleId() != null) {
			sBuilder.append(" AND stolenVehicle.vehicleId = ")
				.append(stolenVehicle.getVehicleId());
		}
		if (stolenVehicle.getCaseType() != null && stolenVehicle.getCaseType() != 0) {
			sBuilder.append(" AND stolenVehicle.caseType = ")
				.append(stolenVehicle.getCaseType());
		}
		if (stolenVehicle.isCallPolice()) {
			sBuilder.append(" AND stolenVehicle.isCallPolice = 1");
		}
		String partNo = (String) paramMap.get("partNo");
		if (partNo != null && !partNo.equals("")) {
			sBuilder.append(" AND (unit.callLetter LIKE ")
				.append("'%").append(partNo).append("%' ")
				.append("OR unit.vehicle.plateNo LIKE ")
				.append("'%").append(partNo).append("%') ");
		}
		
		String phone = (String) paramMap.get("phone");
		if (phone != null && !phone.equals("")) {
			sBuilder.append(" AND linkman.phone LIKE '%")
				.append(phone).append("%'");
		}
		
		String name = (String) paramMap.get("name");
		if (name != null && !name.equals("")) {
			sBuilder.append(" AND unit.customer.customerName LIKE '%")
				.append(name).append("%'");
		}
		String tamperBox = (String) paramMap.get("tamperBox");
		if (tamperBox != null && !tamperBox.equals("")) {
			sBuilder.append(" AND unit.tamperBox=").append(tamperBox);
		}
		String tamperWireless = (String) paramMap.get("tamperWireless");
		if (tamperWireless != null && !tamperWireless.equals("")) {
			sBuilder.append(" AND unit.tamperWireless=").append(tamperWireless);
		}
		String tamperSmart = (String) paramMap.get("tamperSmart");
		if (tamperSmart != null && !tamperSmart.equals("")) {
			sBuilder.append(" AND unit.tamperSmart=").append(tamperSmart);
		}
		if (query.getCompanyNo().length > 0) {
			sBuilder.append(" AND unit.companyNo IN (:companyNo) ");
		}
		sBuilder.append(" ORDER BY stolenVehicle.beginTime ");
		
		Query namedQuery = session.createQuery(sBuilder.toString())
			.setParameter("beginTime", query.getBeginTime())
			.setParameter("endTime", query.getEndTime());
		if (query.getCompanyNo().length > 0) {
			namedQuery.setParameterList("companyNo", query.getCompanyNo());
		}
		return namedQuery;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> getUnreportStat(Map<String, Object> paramMap,
			ReportCommonQuery query) {
		Session session = getSession();
		Query namedQuery = getUnreportStatNamedQueryWithParams(
				session, "SelectUnreportStat", paramMap, query);
		if (query.getLength() > 0) {
			namedQuery.setMaxResults(query.getLength());
		}
		return namedQuery.setFirstResult(query.getStart()).list();
	}
	
	@Override
	public long getUnreportStatCount(Map<String, Object> paramMap,
			ReportCommonQuery query) {
		Session session = getSession();
		Query namedQuery =  getUnreportStatNamedQueryWithParams(
				session, "SelectUnreportStatCount", paramMap, query);
		return (Long) namedQuery.uniqueResult();
	}
	
	private Query getUnreportStatNamedQueryWithParams(Session session, 
			String queryName, Map<String, Object> paramMap,
			ReportCommonQuery query) {
		String queryStr = session.getNamedQuery(queryName)
				.getQueryString();
		StringBuilder sBuilder = new StringBuilder(queryStr);
		String name = (String) paramMap.get("name");
		if (name != null && !name.equals("")) {
			sBuilder.append(" AND unit.customer.customerName LIKE '%")
				.append(name).append("%'");
		}
		int simType = 0;
		try {
			simType = Integer.parseInt((String) paramMap.get("simType"));
		} catch (NumberFormatException e) {
		}
		if (simType != 0) {
			sBuilder.append(" AND unit.simType=").append(simType);
		}
		int mode = 0;
		try {
			mode = Integer.parseInt((String) paramMap.get("mode"));
		} catch (NumberFormatException e) {
		}
		if (mode != 0) {
			sBuilder.append(" AND unit.mode=").append(mode);
		}
		int report = 0;
		try {
			report = Integer.parseInt((String) paramMap.get("report"));
		} catch (NumberFormatException e) {
		}
		Date beginTime = (Date) paramMap.get("beginTime");
		Date endTime = (Date) paramMap.get("endTime");
		
		// 未上报
		if (report == 0) {
			sBuilder.append(" AND (lastPosition.gpsTime<:beginTime")
				.append(" OR lastPosition.gpsTime>:endTime)");
		} else {
			sBuilder.append(" AND lastPosition.gpsTime>=:beginTime")
				.append(" AND lastPosition.gpsTime<=:endTime)");
		}
		
		if (query.getCompanyNo().length > 0) {
			sBuilder.append(" AND unit.companyNo IN (:companyNo) ");
		}
		Query namedQuery = session.createQuery(sBuilder.toString())
				.setParameter("beginTime", beginTime)
				.setParameter("endTime", endTime);
		if (query.getCompanyNo().length > 0) {
			namedQuery.setParameterList("companyNo", query.getCompanyNo());
		}
		return namedQuery;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> getLocateFault(Map<String, Object> paramMap,
			ReportCommonQuery query) {
		Session session = getSession();
		Query namedQuery = getLocateFaultNamedQueryWithParams(
				session, "SelectLocateFault", paramMap, query);
		if (query.getLength() > 0) {
			namedQuery.setMaxResults(query.getLength());
		}
		return namedQuery.setFirstResult(query.getStart()).list();
	}
	
	@Override
	public long getLocateFaultCount(Map<String, Object> paramMap,
			ReportCommonQuery query) {
		Session session = getSession();
		Query namedQuery =  getLocateFaultNamedQueryWithParams(
				session, "SelectLocateFaultCount", paramMap, query);
		return (Long) namedQuery.uniqueResult();
	}
	
	private Query getLocateFaultNamedQueryWithParams(Session session, 
			String queryName, Map<String, Object> paramMap,
			ReportCommonQuery query) {
		String queryStr = session.getNamedQuery(queryName)
				.getQueryString();
		StringBuilder sBuilder = new StringBuilder(queryStr);
		String name = (String) paramMap.get("name");
		if (name != null && !name.equals("")) {
			sBuilder.append(" AND unit.customer.customerName LIKE '%")
				.append(name).append("%'");
		}
		String[] status = (String[]) paramMap.get("status");
		if (status != null) {
			if (status.length > 0) {
				sBuilder.append(" AND (");
			}
			for (String tmp : status) {
				sBuilder.append(" lastPosition.status LIKE '%").append(tmp).append("%'");
				sBuilder.append(" OR ");
			}
			if (status.length > 0) {
				// 删除多余的OR语句，并增加右括号
				sBuilder.delete(sBuilder.length() - 3, sBuilder.length());
				sBuilder.append(")");
			}
		}
		
		int diff = 0;
		try {
			diff = Integer.parseInt((String) paramMap.get("diff"));
		} catch (NumberFormatException e) {
		}
		//TODO:由于需要时间比较，使用了MySQL的DATEDIFF函数
		if (diff != 0) {
			sBuilder.append(" AND DATEDIFF(lastPosition.stamp, lastPosition.gpsTime)>=")
				.append(diff);
		}
		String lon = (String) paramMap.get("lon");
		if (lon != null && !lon.equals("")) {
			sBuilder.append(" AND lastPosition.lon=").append(lon);
		}		
		String lat = (String) paramMap.get("lat");
		if (lat != null && !lat.equals("")) {
			sBuilder.append(" AND lastPosition.lat=").append(lat);
		}
		if (query.getCompanyNo().length > 0) {
			sBuilder.append(" AND unit.companyNo IN (:companyNo) ");
		}
		Date beginTime = (Date) paramMap.get("beginTime");
		Date endTime = (Date) paramMap.get("endTime");
		Query namedQuery = session.createQuery(sBuilder.toString())
				.setParameter("beginTime", beginTime)
				.setParameter("endTime", endTime);
		if (query.getCompanyNo().length > 0) {
			namedQuery.setParameterList("companyNo", query.getCompanyNo());
		}
		return namedQuery;
	}

	@Override
	public long getSendCmdCount(SendCmdTable sendCmd, ReportCommonQuery query,
			Map<String, String> param) {
		Session session = getSession();
		Query namedQuery =  getSendCmdNamedQueryWithParams(
				session, "SelectSendCmdCount", sendCmd, query, param);		
		return (Long)namedQuery.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getSendCmd(SendCmdTable sendCmd,
			ReportCommonQuery query, Map<String, String> param) {
		Session session = getSession();
		Query namedQuery = getSendCmdNamedQueryWithParams(
				session, "SelectSendCmd", sendCmd, query, param);
		if (query.getLength() > 0) {
			namedQuery.setMaxResults(query.getLength());
		}
		return namedQuery.setFirstResult(query.getStart()).list();
	
	}
	
	private Query getSendCmdNamedQueryWithParams(Session session, 
			String queryName, SendCmdTable sendCmd, 
			ReportCommonQuery query, Map<String, String> param) {
		
		String queryStr = session.getNamedQuery(queryName)
				.getQueryString();
		StringBuilder sBuilder = new StringBuilder(queryStr);
		
		String partNo = param.get("partNo");		
		if (partNo != null && !partNo.equals("")) {
			sBuilder.append(" AND (cmd.callLetter LIKE ")
				.append("'%").append(partNo).append("%' ")
				.append("OR cmd.plateNo LIKE ")
				.append("'%").append(partNo).append("%') ");
		}
		String opName = sendCmd.getOpName();
		if (opName != null && !opName.equals("")) {
			sBuilder.append(" AND cmd.opName LIKE '%")
				.append(opName).append("%'");
		}
		String cmdName = param.get("cmdName"); //sendCmd.getCmdType().getCmdName();
		if (cmdName != null && !cmdName.equals("")) {
			sBuilder.append(" AND cmd.cmdType.cmdName LIKE '%")
				.append(cmdName).append("%'");
		}
		
		Query tmpQuery = session.createQuery(sBuilder.toString())
			.setParameter("beginTime", query.getBeginTime())
			.setParameter("endTime", query.getEndTime());		
		return tmpQuery;
	}

	@Override
	public long getAlarmAllCount(AlarmTable alarm, ReportCommonQuery query,
			Map<String, String> param) {
		Session session = getSession();
		Query namedQuery =  getAlarmsNamedQueryWithParams(
				session, "SelectAlarmAllCount", alarm, query, param);		
		return (Long)namedQuery.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAlarmAll(AlarmTable alarm,
			ReportCommonQuery query, Map<String, String> param) {
		Session session = getSession();
		Query namedQuery = getAlarmsNamedQueryWithParams(
				session, "SelectAlarmAll", alarm, query, param);
		if (query.getLength() > 0) {
			namedQuery.setMaxResults(query.getLength());
		}
		return namedQuery.setFirstResult(query.getStart()).list();
	
	}
	
	private Query getAlarmsNamedQueryWithParams(Session session, 
			String queryName, AlarmTable alarm, 
			ReportCommonQuery query, Map<String, String> param) {
		
		String queryStr = session.getNamedQuery(queryName)
				.getQueryString();
		StringBuilder sBuilder = new StringBuilder(queryStr);
		
		String partNo = param.get("partNo");
		if (partNo != null && !partNo.equals("")) {
			sBuilder.append(" AND (alarm.callLetter LIKE ")
				.append("'%").append(partNo).append("%' ")
				.append("OR unit.vehicle.plateNo LIKE ")
				.append("'%").append(partNo).append("%') ");
		}
		String opName = alarm.getOpName();
		if(opName!=null&&!opName.equals("")){
			sBuilder.append(" AND alarm.opName LIKE")
			.append("'%").append(opName).append("%' ");
		}
		String statusIds = alarm.getStatusIds();
		if(statusIds!=null&&!statusIds.equals("")){
			sBuilder.append(" AND (alarm.statusIds LIKE")
			.append("'").append(statusIds).append(",%' ")
			.append("OR alarm.statusIds LIKE")
			.append("'%,").append(statusIds).append(",%' ")
			.append("OR alarm.statusIds LIKE")
			.append("'%,").append(statusIds).append("') ");
		}
		Query tmpQuery = session.createQuery(sBuilder.toString())
			.setParameter("beginTime", query.getBeginTime())
			.setParameter("endTime", query.getEndTime());		
		return tmpQuery;
	}

	@Override
	public int getManyGpsCount(ReportCommonQuery query,
			Map<String, String> param) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(*) ");
		sb.append(" FROM (select sa.* from ");
		sb.append(" (select calllater,sum(alarmcount) alarmcount from t_seat_alarmcount ");
		sb.append(" where countdate>=:beginTime");
		sb.append(" and countdate<=:endTime");
		sb.append(" GROUP BY calllater) sa");
		sb.append(" left  join t_ba_unit u on sa.calllater = u.call_letter");
		sb.append(" left  join t_ba_vehicle v on u.vehicle_id = v.vehicle_id");
		String mode = param.get("mode");
		if(mode!=null&&!mode.equals("")){
			sb.append(" where u.mode=").append(mode);
		}
		sb.append(" limit 50)as  a");
		Query manyGpsCountQuery = getSession().createSQLQuery(
				sb.toString())
				.setParameter("beginTime", query.getBeginTime())
				.setParameter("endTime", query.getEndTime());;
		return ((BigInteger) manyGpsCountQuery.uniqueResult()).intValue();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<HashMap<String, Object>> getManyGps(ReportCommonQuery query,
			Map<String, String> param) {
		StringBuffer sb = new StringBuffer();
		sb.append("select v.plate_no as plateNo,u.call_letter as callLetter,u.fix_time,u.`mode`,sa.alarmcount");
        sb = getManyGpsHql(sb, param, query);        
        Query manyGpsQuery = getSession().createSQLQuery(
				sb.toString())
			.setParameter("beginTime", query.getBeginTime())
			.setParameter("endTime", query.getEndTime());
        if (query.getLength() > 0) {
        	manyGpsQuery.setMaxResults(query.getLength());
		}manyGpsQuery.setFirstResult(query.getStart());
		manyGpsQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return manyGpsQuery.list();
	}
	
	private StringBuffer getManyGpsHql(StringBuffer sb,Map<String,String> param,ReportCommonQuery query){
		sb.append(" FROM");
		sb.append(" (select calllater,sum(alarmcount) alarmcount from t_seat_alarmcount ");
		sb.append(" where countdate>=:beginTime");
		sb.append(" and countdate<=:endTime");
		sb.append(" GROUP BY calllater ORDER BY alarmcount desc limit 50) sa");
		sb.append(" left  join t_ba_unit u on sa.calllater = u.call_letter");
		sb.append(" left  join t_ba_vehicle v on u.vehicle_id = v.vehicle_id");
		String mode = param.get("mode");
		if(mode!=null&&!mode.equals("")){
			sb.append(" where u.mode=").append(mode);
		}		
		return sb;
		}

	@Override
	public List<GpsInfo> getUnitCom(ReportCommonQuery query,
			Map<String, String> param, HConnection conn) throws IOException{
		String callLetter = param.get("callLetter");
		GpsTable gpsTable = new GpsTable(conn);
		List<GpsInfo> list = new ArrayList<GpsInfo>();
		list = gpsTable.GetGpsInfoList(callLetter, query.getEndTime().getTime(), query.getBeginTime().getTime(), true);
		return list;
	}

	@Override
	public List<HashMap<String, Object>> getBaseInfo(String callLetter) {
		StringBuffer sb = new StringBuffer();
		sb.append("select c.customer_name,v.plate_no,v.model,s.sname from t_ba_unit u ");
		sb.append("left join t_ba_vehicle v on u.vehicle_id = v.vehicle_id ");
		sb.append("left join t_ba_customer c on u.customer_id = c.customer_id ");
		sb.append("left join t_sys_value s on u.subco_no = s.svalue ");
		sb.append("where s.stype=1010 and u.call_letter=:callLetter ");
		Query baseInfo = getSession().createSQLQuery(sb.toString())
				.setParameter("callLetter", callLetter);
		baseInfo.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return baseInfo.list();
	}

	
}
