package cc.chinagps.seat.dao.impl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import cc.chinagps.seat.bean.BasicInfoBean;
import cc.chinagps.seat.bean.ReportCommonQuery;
import cc.chinagps.seat.bean.VehicleUnitInfo;
import cc.chinagps.seat.bean.table.AppContact;
import cc.chinagps.seat.bean.table.ChinaCityTable;
import cc.chinagps.seat.bean.table.CollectionTable;
import cc.chinagps.seat.bean.table.DriverTable;
import cc.chinagps.seat.bean.table.FeeInfoTable;
import cc.chinagps.seat.bean.table.FeePaytxtTable;
import cc.chinagps.seat.bean.table.InsuranceTable;
import cc.chinagps.seat.bean.table.LinkmanTable;
import cc.chinagps.seat.bean.table.S4ShopTable;
import cc.chinagps.seat.bean.table.UnitTable;
import cc.chinagps.seat.bean.table.VehicleTable;
import cc.chinagps.seat.dao.InfoDao;
import cc.chinagps.seat.util.Consts;

@Repository("infoDao")
public class InfoDaoImpl extends BasicDao implements InfoDao {

	private final Logger LOGGER = Logger.getLogger(getClass());

	@Override
	public BasicInfoBean getBasicInfoByCallLetter(String phoneNo, BigInteger... companyNo) {
		Session session = getSession();
		Query query = session.getNamedQuery("SelectBasicInfoByCallLetter");
		// if (companyNo.length > 0) {
		// String queryString = query.getQueryString();
		// queryString = queryString + " AND unit.vehicle.companyNo=" +
		// companyNo[0];
		// query = session.createQuery(queryString);
		// }

		// TODO 相同的地方肯定需要类似的修改
		// 2016年3月29日08:41:41 @金星
		String queryString = query.getQueryString();
		if (companyNo.length == 1) {
			queryString = queryString + " AND unit.vehicle.companyNo=" + companyNo[0];
		} else if (companyNo.length > 1) {
			// 设置用户分配多个公司数据权限的查询
			// TODO 使用HQL 查询 IN可以能会影响性能
			StringBuffer sb = new StringBuffer();
			for (BigInteger bigInteger : companyNo) {
				sb.append(bigInteger).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			queryString = queryString + " AND unit.vehicle.companyNo in(" + sb.toString() + ")";
		}
		query = session.createQuery(queryString);

		@SuppressWarnings("unchecked")
		List<Object[]> list = query.setParameter("callLetter", phoneNo).list();
		if (list.size() <= 0) {
			String msg = "There is no unit from db where call letter is " + phoneNo;
			if (companyNo.length > 0) {
				msg = msg + " and companyNo is " + companyNo[0];
			}
			LOGGER.warn(msg);
			return null;
		} else {
			if (list.size() > 1) {
				String msg = "There is " + list.size() + " units from db where call letter is " + phoneNo + " and return the first one";
				LOGGER.warn(msg);
			}
			Object[] objs = list.get(0);
			return extractBasicInfoBean(objs);
		}
	}

	@Override
	public BasicInfoBean getBasicInfoByPhoneNo(String phoneNo, BigInteger... companyNo) {
		Session session = getSession();
		Query query = session.getNamedQuery("SelectBasicInfoByPhoneNum");
		// if (companyNo.length > 0) {
		// String queryString = query.getQueryString();
		// queryString = queryString + " AND unit.vehicle.companyNo=" +
		// companyNo[0];
		// query = session.createQuery(queryString);
		// }

		// TODO 相同的地方肯定需要类似的修改
		// 2016年3月29日08:41:41 @金星
		String queryString = query.getQueryString();
		if (companyNo.length == 1) {
			queryString = queryString + " AND unit.vehicle.companyNo=" + companyNo[0];
		} else if (companyNo.length > 1) {
			// 设置用户分配多个公司数据权限的查询
			// TODO 使用HQL 查询 IN可以能会影响性能
			StringBuffer sb = new StringBuffer();
			for (BigInteger bigInteger : companyNo) {
				sb.append(bigInteger).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			queryString = queryString + " AND unit.vehicle.companyNo in(" + sb.toString() + ")";
		}
		query = session.createQuery(queryString);

		// 电话号码比较不能在数据库的数据中使用concat，否则会导致sql查询缓慢。需要去掉电话号码的0
		// 如果电话号码前有0，则去掉
		if (phoneNo.startsWith("0")) {
			phoneNo = phoneNo.substring(1);
		}
		@SuppressWarnings("unchecked")
		List<Object[]> list = query.setParameter("callLetter", phoneNo).list();
		if (list.size() <= 0) {
			String msg = "There is no unit from db where call letter is " + phoneNo;
			if (companyNo.length > 0) {
				msg = msg + " and companyNo is " + companyNo[0];
			}
			LOGGER.warn(msg);
			return null;
		} else {
			if (list.size() > 1) {
				String msg = "There is " + list.size() + " units from db where call letter is " + phoneNo + " and return the first one";
				LOGGER.warn(msg);
			}
			Object[] objs = list.get(0);
			return extractBasicInfoBean(objs);
		}
	}

	private BasicInfoBean extractBasicInfoBean(Object[] objs) {
		UnitTable unit = (UnitTable) objs[0];
		VehicleTable vehicle = unit.getVehicle();
		vehicle.setType((String) objs[1]);
		vehicle.setStatus((String) objs[2]);
		unit.setRegStatus((String) objs[3]);
		return new BasicInfoBean(unit);
	}

	@Override
	public BasicInfoBean getBasicInfoByVehicleId(BigInteger vehicleId, BigInteger... companyNo) {
		Session session = getSession();
		Query query = session.getNamedQuery("SelectBasicInfoByVehicleId");
		// if (companyNo.length > 0) {
		// String queryString = query.getQueryString();
		// queryString = queryString + " AND unit.vehicle.companyNo=" +
		// companyNo[0];
		// query = session.createQuery(queryString);
		// }

		// TODO 相同的地方肯定需要类似的修改
		// 2016年3月29日08:41:41 @金星
		String queryString = query.getQueryString();
		if (companyNo.length == 1) {
			queryString = queryString + " AND unit.vehicle.companyNo=" + companyNo[0];
		} else if (companyNo.length > 1) {
			// 设置用户分配多个公司数据权限的查询
			// TODO 使用HQL 查询 IN可以能会影响性能
			StringBuffer sb = new StringBuffer();
			for (BigInteger bigInteger : companyNo) {
				sb.append(bigInteger).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			queryString = queryString + " AND unit.vehicle.companyNo in(" + sb.toString() + ")";
		}
		query = session.createQuery(queryString);

		@SuppressWarnings("unchecked")
		List<Object[]> list = query.setParameter("vehicleId", vehicleId).list();

		if (list.size() <= 0) {
			String msg = "There is no unit from db where vehicleId is " + vehicleId;
			if (companyNo.length > 0) {
				msg += " and companyNo is " + companyNo[0];
			}
			LOGGER.warn(msg);
			return null;
		} else {
			if (list.size() > 1) {
				String msg = "There is " + list.size() + " units from db where vehicleId is " + vehicleId + " and return the first one";
				LOGGER.warn(msg);
			}
			Object[] objs = list.get(0);
			return extractBasicInfoBean(objs);
		}
	}

	@Override
	public List<VehicleUnitInfo> getVehicleInfo(String platNoOrCallLetter, int limit, BigInteger... companyNo) {
		Session session = getSession();
		// @SuppressWarnings("unchecked")
		// List<UnitTable> list = session
		// .createCriteria(UnitTable.class)
		// .createAlias("vehicle", "v")
		// .add(Restrictions.or(
		// Restrictions.like("callLetter",
		// platNoOrCallLetter, MatchMode.ANYWHERE),
		// Restrictions.like("v.plateNo",
		// platNoOrCallLetter, MatchMode.ANYWHERE)))
		// .setMaxResults(limit).list();

		Query query = session.getNamedQuery("SelectVehicleInfo");
		// if (companyNo.length > 0) {
		// String queryString = query.getQueryString();
		// queryString = queryString + " AND unit.vehicle.companyNo=" +
		// companyNo[0];
		// query = session.createQuery(queryString);
		// }

		// TODO 相同的地方肯定需要类似的修改
		// 2016年3月29日08:41:41 @金星
		String queryString = query.getQueryString();
		if (companyNo.length == 1) {
			queryString = queryString + " AND unit.vehicle.companyNo=" + companyNo[0];
		} else if (companyNo.length > 1) {
			// 设置用户分配多个公司数据权限的查询
			// TODO 使用HQL 查询 IN可以能会影响性能
			StringBuffer sb = new StringBuffer();
			for (BigInteger bigInteger : companyNo) {
				sb.append(bigInteger).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			queryString = queryString + " AND unit.vehicle.companyNo in(" + sb.toString() + ")";
		}
		query = session.createQuery(queryString);

		String likeStr = "%" + platNoOrCallLetter + "%";
		@SuppressWarnings("unchecked")
		List<Object[]> list = query.setParameter("callLetter", likeStr).setParameter("platNo", likeStr).setMaxResults(limit).list();
		List<VehicleUnitInfo> infoList;
		if (list.size() <= 0) {
			String msg = "There is no VehicleUnitInfo " + "where callLetter or platNo like " + likeStr;
			if (companyNo.length > 0) {
				msg += " and companyNo is " + companyNo[0];
			}
			LOGGER.warn(msg);
			return new ArrayList<VehicleUnitInfo>();
		} else {
			infoList = new ArrayList<VehicleUnitInfo>(list.size());
		}
		for (Object[] obj : list) {
			VehicleUnitInfo info = new VehicleUnitInfo((BigInteger) obj[0], (String) obj[1], (String) obj[2]);
			infoList.add(info);
		}
		return infoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LinkmanTable> getLinkmanTable(BigInteger customerId, BigInteger vehicleId) {
		// return getSession().createCriteria(LinkmanTable.class).add(
		// Restrictions.eq("customerId", customerId)).list();

		List<Object[]> list = getSession().getNamedQuery("SelectLinkmanByCustomerId").setParameter("customerId", customerId)
				.setParameter("vehicleId", vehicleId).list();
		List<LinkmanTable> linkmanList = new ArrayList<LinkmanTable>(list.size());
		for (Object[] obj : list) {
			LinkmanTable linkman = (LinkmanTable) obj[0];
			linkman.setLinkmanTypeString((String) obj[1]);
			linkmanList.add(linkman);
		}
		return linkmanList;
	}

	@Override
	public BigInteger saveLinkman(LinkmanTable linkman) {
		return (BigInteger) getWriteSession().save(linkman);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DriverTable> getDriverTableList(BigInteger customerId, BigInteger vehicleId) {
		// return getSession().createCriteria(DriverTable.class).add(
		// Restrictions.eq("customerId", customerId)).list();
		return getSession().getNamedQuery("SelectDriverByCustomerId").setParameter("customerId", customerId).setParameter("vehicleId", vehicleId)
				.list();
	}

	@Override
	public List<InsuranceTable> getInsuranceByCustomerId(BigInteger customerId) {

		@SuppressWarnings("unchecked")
		List<Object[]> list = getSession().getNamedQuery("SelectInsuranceByCustomerId").setParameter("customerId", customerId).list();
		List<InsuranceTable> insuranceList = extractInsuranceTable(list);
		return insuranceList;
	}

	private List<InsuranceTable> extractInsuranceTable(List<Object[]> list) {
		List<InsuranceTable> insuranceList = new ArrayList<InsuranceTable>(list.size());
		for (Object[] obj : list) {
			InsuranceTable table = (InsuranceTable) obj[0];
			if (table != null) {
				table.setName((String) obj[1]);
			}
			insuranceList.add(table);
		}
		return insuranceList;
	}

	@Override
	public List<S4ShopTable> get4sShopTableList(BigInteger customerId) {
		// TODO:There is no 4s shop yet
		return new ArrayList<S4ShopTable>();
	}

	@Override
	public int modifyPrivatePwd(BigInteger vehicleId, String privatePwdOld, String privatePwdNew) {
		Session session = getWriteSession();
		VehicleTable vehicle = (VehicleTable) session.load(VehicleTable.class, vehicleId);
		if (vehicle == null) {
			return Consts.MODIFY_PRIVATE_PWD_ERROR_NO_VEHICLE;
		} else {
			if (vehicle.getPrivatePwd() == null) {
				vehicle.setPrivatePwd("");
			}
			if (!vehicle.getPrivatePwd().equals(privatePwdOld)) {
				return Consts.MODIFY_PRIVATE_PWD_ERROR_PRIVATEPWD_ERROR;
			} else {
				vehicle.setPrivatePwd(privatePwdNew);
				session.flush();
				return Consts.SUCCEED;
			}
		}
	}

	@Override
	public int modifyVehicleRemark(BigInteger vehicleId, String remark) {
		Session session = getWriteSession();
		VehicleTable vehicle = (VehicleTable) session.load(VehicleTable.class, vehicleId);
		if (vehicle == null) {
			return Consts.MODIFY_VEHICLE_REMARK_ERROR_NO_VEHICLE;
		} else {
			vehicle.setRemark(remark);
			session.flush();
			return Consts.SUCCEED;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<FeeInfoTable> getFeeInfoByPhoneNo(String phoneNum) {
		// 如果电话号码前有0，则去掉
		if (phoneNum.startsWith("0")) {
			phoneNum = phoneNum.substring(1);
		}
		List<Object[]> list = getSession().getNamedQuery("SelectFeeInfoByPhoneNum").setParameter("callLetter", phoneNum).list();
		return extractFeeInfoTableList(list);
	}

	@Override
	public FeePaytxtTable getFeePaytxtByPhoneNo(String phoneNum) {
		// 如果电话号码前有0，则去掉
		if (phoneNum.startsWith("0")) {
			phoneNum = phoneNum.substring(1);
		}
		Object[] objs = (Object[]) getSession().getNamedQuery("SelectFeePaytxtByPhoneNum").setParameter("callLetter", phoneNum).uniqueResult();
		return extractFeePayTxtTable(objs);
	}

	@Override
	public CollectionTable getCollectionByPhoneNo(String phoneNum) {
		// 如果电话号码前有0，则去掉
		if (phoneNum.startsWith("0")) {
			phoneNum = phoneNum.substring(1);
		}
		return (CollectionTable) getSession().getNamedQuery("SelectCollectionByPhoneNum").setParameter("callLetter", phoneNum).uniqueResult();
	}

	@Override
	public InsuranceTable getInsuranceByPhoneNo(String phoneNum) {
		// 如果电话号码前有0，则去掉
		if (phoneNum.startsWith("0")) {
			phoneNum = phoneNum.substring(1);
		}
		@SuppressWarnings("unchecked")
		List<Object[]> list = getSession().getNamedQuery("SelectInsuranceByPhoneNum").setParameter("callLetter", phoneNum).list();
		List<InsuranceTable> insuranceTable = extractInsuranceTable(list);
		if (insuranceTable.size() > 0) {
			return insuranceTable.get(0);
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<FeeInfoTable> getFeeInfoByVehicleId(BigInteger vehicleId) {
		List<Object[]> list = getSession().getNamedQuery("SelectFeeInfoByVehicleId").setParameter("vehicleId", vehicleId).list();
		return extractFeeInfoTableList(list);
	}

	@Override
	public InsuranceTable getInsuranceByVehicleId(BigInteger vehicleId) {
		@SuppressWarnings("unchecked")
		List<Object[]> list = getSession().getNamedQuery("SelectInsuranceByVehicleId").setParameter("vehicleId", vehicleId).list();
		List<InsuranceTable> insuranceTable = extractInsuranceTable(list);
		if (insuranceTable.size() > 0) {
			return insuranceTable.get(0);
		}
		return null;
	}

	@Override
	public FeePaytxtTable getFeePaytxtByVehicleId(BigInteger vehicleId) {
		Object[] objs = (Object[]) getSession().getNamedQuery("SelectFeePaytxtByVehicleId").setParameter("vehicleId", vehicleId).uniqueResult();
		return extractFeePayTxtTable(objs);
	}

	@Override
	public CollectionTable getCollectionByVehicleId(BigInteger vehicleId) {
		return (CollectionTable) getSession().getNamedQuery("SelectCollectionByVehicleId").setParameter("vehicleId", vehicleId).uniqueResult();
	}

	private List<FeeInfoTable> extractFeeInfoTableList(List<Object[]> list) {
		List<FeeInfoTable> feeInfoTableList = new ArrayList<FeeInfoTable>(list.size());
		Object[] objs;
		for (int i = 0; i < list.size(); i++) {
			objs = list.get(i);
			if (objs != null) {
				FeeInfoTable feeInfoTable = (FeeInfoTable) objs[0];
				if (feeInfoTable != null) {
					String name = (String) objs[1];
					feeInfoTable.setType(name);
					feeInfoTableList.add(feeInfoTable);
				}
			}
		}
		return feeInfoTableList;
	}

	private FeePaytxtTable extractFeePayTxtTable(Object[] objs) {
		FeePaytxtTable feePaytxtTable = null;
		if (objs != null) {
			feePaytxtTable = (FeePaytxtTable) objs[0];
			if (feePaytxtTable != null) {
				String name = (String) objs[1];
				feePaytxtTable.setAgencyName(name);
				feePaytxtTable.setPayTagString((String) objs[2]);
				feePaytxtTable.setAmount((Double) objs[3]);
				feePaytxtTable.setPrintTime((Timestamp) objs[4]);
			}
		}
		return feePaytxtTable;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Cacheable(value = "chinaCities")
	public Map<String, Map<String, ChinaCityTable>> getChinaCities() {
		List<ChinaCityTable> cityList = getSession().getNamedQuery("SelectChinaCity").list();
		Map<String, Map<String, ChinaCityTable>> cityMap = new HashMap<String, Map<String, ChinaCityTable>>();
		for (ChinaCityTable city : cityList) {
			if (!cityMap.containsKey(city.getProvinceName())) {
				cityMap.put(city.getProvinceName(), new HashMap<String, ChinaCityTable>());
			}
			Map<String, ChinaCityTable> map = cityMap.get(city.getProvinceName());
			map.put(city.getName(), city);
		}
		return cityMap;
	}

	@Override
	public UnitTable getUnit(BigInteger unitId) {
		return (UnitTable) getSession().get(UnitTable.class, unitId);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<AppContact> getAppContactList(String customerId, String queryString, ReportCommonQuery query) {
		Session session = getSession();
		StringBuilder queryStr = new StringBuilder(session.getNamedQuery("SelectContact").getQueryString());
		if (queryString != null && !queryString.trim().equals("")) {
			queryStr.append("AND (contact.contactName LIKE '%").append(queryString).append("%' ");
			queryStr.append("OR contact.contactInfo LIKE '%").append(queryString).append("%')");
		}
		Query sessionQuery = session.createQuery(queryStr.toString()).setParameter("customerId", customerId).setFirstResult(query.getStart());
		if (query.getLength() > 0) {
			sessionQuery.setMaxResults(query.getLength());
		}
		return sessionQuery.list();
	}

	@Override
	public long getAppContactCount(String customerId, String queryString) {
		Session session = getSession();
		StringBuilder queryStr = new StringBuilder(session.getNamedQuery("SelectContactCount").getQueryString());
		if (queryString != null && !queryString.trim().equals("")) {
			queryStr.append("AND (contact.contactName LIKE '%").append(queryString).append("%' ");
			queryStr.append("OR contact.contactInfo LIKE '%").append(queryString).append("%')");
		}
		return (Long) session.createQuery(queryStr.toString()).setParameter("customerId", customerId).uniqueResult();
	}
}
