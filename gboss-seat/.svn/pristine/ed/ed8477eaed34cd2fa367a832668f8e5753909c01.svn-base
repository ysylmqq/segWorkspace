package cc.chinagps.seat.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.chinagps.seat.auth.AuthHelper;
import cc.chinagps.seat.auth.CompanyInfo;
import cc.chinagps.seat.auth.Organization;
import cc.chinagps.seat.bean.BasicInfoBean;
import cc.chinagps.seat.bean.FeeInfo;
import cc.chinagps.seat.bean.PhoneInfo;
import cc.chinagps.seat.bean.ReportCommonQuery;
import cc.chinagps.seat.bean.ReportCommonResponse;
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
import cc.chinagps.seat.bean.table.VehicleTable;
import cc.chinagps.seat.dao.InfoDao;
import cc.chinagps.seat.service.InfoService;
import cc.chinagps.seat.service.IvrPortType;
import cc.chinagps.seat.util.Consts;
import cc.chinagps.seat.util.DESPlus;

@Service
public class InfoServiceImpl extends BasicService implements InfoService {
	
	private final Log LOGGER = LogFactory.getLog(getClass());
	
	@Autowired
	private InfoDao infoDao;
	
	@Autowired
	private IvrPortType service;
	
	public void setInfoDao(InfoDao infoDao) {
		this.infoDao = infoDao;
	}
	
	public BasicInfoBean getBasicInfo(String param, int type,
			List<CompanyInfo> companyList) {
		BigInteger[] companyNo = getCompanyNo(companyList, true);
		BasicInfoBean basicInfo=null;
		if (type == Consts.TYPE_PHONENO) {
			basicInfo = infoDao.getBasicInfoByCallLetter(param, companyNo);
			if (basicInfo == null) {
				basicInfo = infoDao.getBasicInfoByPhoneNo(param, companyNo);
			}
			return basicInfo;
		} else if (type == Consts.TYPE_VEHICLEID) {
			try {
				if(param!=null && !"".equals(param)){
					BigInteger vehicleId = new BigInteger(param);
					basicInfo = infoDao.getBasicInfoByVehicleId(vehicleId, companyNo);
					if (basicInfo != null) {
						// 解密密码再传
						VehicleTable vehicle = basicInfo.getVehicle();
						String privatePwd = vehicle.getPrivatePwd();
						if (privatePwd == null) {
							privatePwd = "";
						}
						vehicle.setPrivatePwd(DESPlus.getDecPWD(privatePwd).trim());
						String servicePwd = vehicle.getServicePwd();
						if (servicePwd == null) {
							servicePwd = "";
						}
						vehicle.setServicePwd(DESPlus.getDecPWD(servicePwd).trim());
					}
					return basicInfo;
				}
			} catch (Exception e) {
				LOGGER.error("VehicleId is not a valid number!!", e);
				return null;
			}
		} else {
			LOGGER.error("Wrong type!!!");
			return null;
		}
		return null;
	}

	@Override
	public List<VehicleUnitInfo> getVehicleInfo(String platNoOrCallLetter,
			int limit, List<CompanyInfo> companyList) {
		if (limit <= 0) {
			LOGGER.warn("Limit is less than or equal to 0!!");
			return new ArrayList<VehicleUnitInfo>();
		}
		return infoDao.getVehicleInfo(platNoOrCallLetter, limit, 
				getCompanyNo(companyList, true));
	}
	
	@Override
	public List<PhoneInfo> getPhoneInfo(BigInteger customerId,
			BigInteger vehicleId) {
		List<PhoneInfo> phoneInfoList = new ArrayList<PhoneInfo>();
		if (customerId == null) {
			LOGGER.warn("Customer Id is null");
		} else {
			phoneInfoList.addAll(getCustomerPhones(customerId, 
					vehicleId));
			phoneInfoList.addAll(getVehiclePhones(customerId,
					vehicleId));
		}
		return phoneInfoList;
	}
	
	@Override
	public int saveLinkman(LinkmanTable linkman) {
		if (linkman.getLinkmanType().equals( 
				LinkmanTable.LINKMANTYPE_FIRST_CONTACT)) {
			List<LinkmanTable> linkmanList = infoDao.getLinkmanTable(
					linkman.getCustomerId(), linkman.getVehicleId());
			for (LinkmanTable linkmanTable : linkmanList) {
				if (linkmanTable.getLinkmanType().equals( 
						LinkmanTable.LINKMANTYPE_FIRST_CONTACT)) {
					return Consts.SAVE_LINKMAN_ERROR_FIRST_CONTACT_UNIQUE;
				}
			}
		}
		
		BigInteger id = infoDao.saveLinkman(linkman);
		linkman.setId(id);
		return Consts.SUCCEED;
	}
	
	/**
	 * 按客户获取电话信息
	 * @param customerId
	 * @return
	 */
	private List<PhoneInfo> getCustomerPhones(BigInteger customerId,
			BigInteger vehicleId) {
		List<LinkmanTable> list = infoDao.getLinkmanTable(customerId,
				vehicleId);
		List<PhoneInfo> phoneInfoList = new ArrayList<PhoneInfo>(list.size());
		for (LinkmanTable linkman : list) {
			PhoneInfo phoneInfo = PhoneInfo.newCustomerPhoneInfo(
					linkman);
			phoneInfoList.add(phoneInfo);
		}
		
		return phoneInfoList;
	}
	
	/**
	 * 按车辆获取电话信息
	 * @param customerId
	 * @return
	 */
	private List<PhoneInfo> getVehiclePhones(BigInteger customerId,
			BigInteger vehicleId) {
		List<PhoneInfo> phoneInfoList = new ArrayList<PhoneInfo>();
		List<DriverTable> list = infoDao.getDriverTableList(customerId,
				vehicleId);
		for (DriverTable table : list) {
			PhoneInfo phoneInfo = PhoneInfo.newDriverPhoneInfo(table);
			phoneInfoList.add(phoneInfo);
		}
		List<InsuranceTable> list2 = infoDao.getInsuranceByCustomerId(
				customerId);
		for (InsuranceTable table : list2) {
			PhoneInfo phoneInfo = PhoneInfo.newInsuranceAgentPhoneInfo(
					table);
			phoneInfoList.add(phoneInfo);
		}
		List<S4ShopTable> list3 = infoDao.get4sShopTableList(
				customerId);
		for (S4ShopTable table : list3) {
			PhoneInfo phoneInfo = PhoneInfo.new4sShopPhoneInfo(table);
			phoneInfoList.add(phoneInfo);
		}
		return phoneInfoList;
	}
	
	@Override
	public int modifyPrivatePwd(BigInteger vehicleId, String privatePwdOld,
			String privatePwdNew) {
		if (vehicleId == null) {
			LOGGER.warn("Vehicle Id is null");
			return Consts.MODIFY_PRIVATE_PWD_ERROR_NO_VEHICLE;
		}
		privatePwdOld = DESPlus.getEncPWD(privatePwdOld);
		privatePwdNew = DESPlus.getEncPWD(privatePwdNew);
		return infoDao.modifyPrivatePwd(vehicleId, privatePwdOld, 
				privatePwdNew);
	}
	
	@Override
	public int modifyVehicleRemark(BigInteger vehicleId, String remark) {
		if (vehicleId == null) {
			LOGGER.warn("Vehicle Id is null");
			return Consts.MODIFY_VEHICLE_REMARK_ERROR_NO_VEHICLE;
		}
		return infoDao.modifyVehicleRemark(vehicleId, remark);
	}
	
	@Override
	public FeeInfo getFeeInfo(String param, int type) {
		List<FeeInfoTable> feeInfoTableList = 
				new ArrayList<FeeInfoTable>(0);
		FeePaytxtTable feePaytxtTable = null;
		InsuranceTable insuranceTable = null;
		CollectionTable collectionTable = null;
		if (type == Consts.TYPE_PHONENO) {
			feeInfoTableList = infoDao.getFeeInfoByPhoneNo(param);
			feePaytxtTable = infoDao.getFeePaytxtByPhoneNo(param);
			collectionTable = infoDao.getCollectionByPhoneNo(param);
			insuranceTable = infoDao.getInsuranceByPhoneNo(param);
		} else if (type == Consts.TYPE_VEHICLEID) {
			try {
				BigInteger vehicleId = new BigInteger(param);
				feeInfoTableList = infoDao.getFeeInfoByVehicleId(
						vehicleId);
				feePaytxtTable = infoDao.getFeePaytxtByVehicleId(
						vehicleId);
				collectionTable = infoDao.getCollectionByVehicleId(vehicleId);
				insuranceTable = infoDao.getInsuranceByVehicleId(
						vehicleId);
			} catch (Exception e) {
				LOGGER.error("VehicleId is not a valid number!!", e);
				return null;
			}
		} else {
			LOGGER.error("Wrong type!!!");
			return null;
		}
		
		if (feePaytxtTable == null) {
			feePaytxtTable = new FeePaytxtTable();
		}
		if (collectionTable != null) {
			feePaytxtTable.setAddress(collectionTable.getAddress());
			feePaytxtTable.setBank(collectionTable.getBank());
			feePaytxtTable.setStamp(collectionTable.getStamp());
		}
		
		FeeInfo feeInfo = new FeeInfo(feePaytxtTable, feeInfoTableList, 
				insuranceTable);
		List<FeeInfoTable> feeInfoList = feeInfo.getFeeInfoList();
		BigDecimal sumMonthly = new BigDecimal(0);
		BigDecimal sumAnnually = new BigDecimal(0);
		BigDecimal monthNum = new BigDecimal(12);
		for (FeeInfoTable feeInfoTable : feeInfoList) {
			Float fiAmount = feeInfoTable.getAmount();
			if (fiAmount == null) {
				fiAmount = 0F; 
			}
			BigDecimal amount = BigDecimal.valueOf(fiAmount);
			sumMonthly = sumMonthly.add(amount);
			sumAnnually = sumAnnually.add(amount.multiply(monthNum));
		}
		feeInfo.setSumAnnually(sumAnnually.floatValue());
		feeInfo.setSumMonthly(sumMonthly.floatValue());
		
		return feeInfo;
	}
	
	@Override
	public String getAreaCode(String province, String city) {
		Map<String, Map<String, ChinaCityTable>> cityMap = infoDao.getChinaCities();
		Map<String, ChinaCityTable> map = cityMap.get(province);
		if (map != null) {
			ChinaCityTable cityTable = map.get(city);
			if (cityTable != null) {
				return cityTable.getPhoneArea();
			}
		}
		LOGGER.warn("There is no area code info for province:" + province
				+ " and city:" + city);
		return "";
	}
	
	@Override
	public List<AppContact> getAppContactList(String customerId,
			String queryString, ReportCommonQuery query) {
		return infoDao.getAppContactList(customerId, queryString, query);
	}
	
	@Override
	public ReportCommonResponse getCommonResponse(String customerId,
			String queryString) {
		ReportCommonResponse resp = new ReportCommonResponse();
		long recordsTotal = infoDao.getAppContactCount(customerId, null);
		resp.setRecordsTotal(recordsTotal);
		long recordsFiltered = recordsTotal;
		if (queryString != null && !queryString.trim().equals("")) {
			recordsFiltered = infoDao.getAppContactCount(customerId, queryString);
		}
		resp.setRecordsFiltered(recordsFiltered);
		return resp;
	}
	
	@Override
	public String setPhoneToAgent(String phone, String seatNo) {
		return this.service.setPhoneToAgent(phone, seatNo);
	}
	
	public Organization get4sShop(BigInteger s4Id) {
		Organization org = AuthHelper.getOrg(s4Id);
		if (org != null && org.isGarage4SShop()) {
			return org;
		} else {
			LOGGER.warn("There is no garage or 4s shop for 4s id:" + s4Id);
			return null;
		}
	}
}
