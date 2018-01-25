package com.gboss.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ldap.mysql.IdCreater;
import ldap.objectClasses.CommonCompany;
import ldap.objectClasses.CommonOperator;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.gboss.comm.SystemConfig;
import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.dao.BackupDao;
import com.gboss.dao.BarcodeDao;
import com.gboss.dao.CollectionDao;
import com.gboss.dao.CustVehicleDao;
import com.gboss.dao.CustomerDao;
import com.gboss.dao.CustphoneDao;
import com.gboss.dao.DatalockDao;
import com.gboss.dao.DriverDao;
import com.gboss.dao.FeeInfoDao;
import com.gboss.dao.MidCustDao;
import com.gboss.dao.OperatorUnitDao;
import com.gboss.dao.PreloadDao;
import com.gboss.dao.UnitDao;
import com.gboss.dao.VehicleDao;
import com.gboss.pojo.Backup;
import com.gboss.pojo.Barcode;
import com.gboss.pojo.Collection;
import com.gboss.pojo.Collectionbk;
import com.gboss.pojo.CustSales;
import com.gboss.pojo.CustVehicle;
import com.gboss.pojo.Customer;
import com.gboss.pojo.Customerbk;
import com.gboss.pojo.Driver;
import com.gboss.pojo.FeeInfo;
import com.gboss.pojo.FeeInfobk;
import com.gboss.pojo.Linkman;
import com.gboss.pojo.MidCust;
import com.gboss.pojo.OperatorUnit;
import com.gboss.pojo.Org;
import com.gboss.pojo.Preload;
import com.gboss.pojo.Unit;
import com.gboss.pojo.Unitbk;
import com.gboss.pojo.Vehicle;
import com.gboss.pojo.Vehiclebk;
import com.gboss.pojo.web.CustInfo;
import com.gboss.pojo.web.LargeCustMngEntry;
import com.gboss.pojo.web.LargeCustNetworkEntry;
import com.gboss.pojo.web.PrivateNetworkEntry;
import com.gboss.service.MaxidService;
import com.gboss.service.NetworkService;
import com.gboss.service.OrgService;
import com.gboss.util.DESPlus;
import com.gboss.util.DateUtil;
import com.gboss.util.HibernateUtil;
import com.gboss.util.HttpClientUtils;
import com.gboss.util.SpringContext;
import com.gboss.util.StringUtils;
import com.gboss.util.Utils;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:NetworkServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-8-1 下午3:27:35
 */
@Service("NetworkService")
@Transactional
public class NetworkServiceImpl extends BaseServiceImpl implements NetworkService {

	static Logger logger = Logger.getLogger(NetworkServiceImpl.class);

	@Autowired
	@Qualifier("CustomerDao")
	private CustomerDao customerDao;

	@Autowired
	@Qualifier("VehicleDao")
	private VehicleDao vehicleDao;

	@Autowired
	@Qualifier("UnitDao")
	private UnitDao unitDao;

	@Autowired
	@Qualifier("CustVehicleDao")
	private CustVehicleDao custVehicleDao;

	@Autowired
	@Qualifier("CustphoneDao")
	private CustphoneDao custphoneDao;

	@Autowired
	@Qualifier("CollectionDao")
	private CollectionDao collectionDao;

	@Autowired
	@Qualifier("BarcodeDao")
	private BarcodeDao barcodeDao;

	@Autowired
	@Qualifier("FeeInfoDao")
	private FeeInfoDao feeInfoDao;

	@Autowired
	@Qualifier("BackupDao")
	private BackupDao backupDao;

	@Autowired
	@Qualifier("DriverDao")
	private DriverDao driverDao;

	@Autowired
	@Qualifier("DatalockDao")
	private DatalockDao datalockDao;

	@Autowired
	@Qualifier("MidCustDao")
	private MidCustDao midCustDao;

	@Autowired
	@Qualifier("preloadDao")
	private PreloadDao preloadDao;

	@Autowired
	@Qualifier("OperatorUnitDao")
	private OperatorUnitDao operatorUnitDao;

	@Autowired
	@Qualifier("OrgService")
	private OrgService orgSer;

	@Autowired
	@Qualifier("MaxidService")
	private MaxidService maxidService;

	@Autowired
	private SystemConfig systemConfig;

	@Override
	public HashMap fastNetwork(CustInfo custInfo, String userId, String companyid, String companycode, String companyname) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// 如果是私家车客户类型
			Long cust_id;
			String same_opid = custInfo.getOpid();
			boolean is_addVehicle = true;
			boolean is_addCust = true;
			Vehicle vehicle = custInfo.getVehicle();
			String service_pwd = custInfo.getCustomer().getService_pwd();
			if (custInfo.getCustomer().getCust_type() == 0 && StringUtils.isBlank(same_opid)) {
				// 客戶信息
				Customer customer = custInfo.getCustomer();
				customer.setSubco_no(Long.valueOf(companyid));
				customer.setSubco_code(companycode);
				customer.setSubco_name(companyname);
				customer.setCustco_no(0L);
				customer.setCustco_code("0");
				customer.setOp_id(Long.valueOf(userId));
				if (StringUtils.isNotBlank(custInfo.getCustomer().getService_pwd())) {
					customer.setService_pwd(DESPlus.getEncPWD(custInfo.getCustomer().getService_pwd()));
				} else {
					customer.setService_pwd(DESPlus.getEncPWD("123456"));
				}
				customer.setSex(1);
				customer.setIdtype(1);
				customer.setTrade(0);
				customer.setVip(1);
				customer.setFlag(0);
				customer.setPay_model(0);
				cust_id = customerDao.add(customer);
			} else if (StringUtils.isBlank(same_opid)) {
				is_addCust = false;
				cust_id = custInfo.getCustomer().getCustomer_id();
				vehicle.setDef_no("");
			} else {
				is_addCust = false;
				cust_id = custInfo.getCustomer().getCustomer_id();
				vehicle.setDef_no("");
			}
			// 车辆信息
			vehicle.setSubco_no(Long.valueOf(companyid));
			vehicle.setPlate_color(1);
			vehicle.setVehicle_type(5);
			vehicle.setVehicle_status(0);
			vehicle.setFlag(0);
			vehicle.setVin("");
			vehicle.setEngine_no("");
			vehicle.setVload(0);
			vehicle.setId_4s(0L);
			vehicle.setInsurance_id(0);
			vehicle.setOp_id(Long.valueOf(userId));
			if (StringUtils.isNotBlank(service_pwd)) {
				vehicle.setService_pwd(DESPlus.getEncPWD(service_pwd));
			} else {
				vehicle.setService_pwd(DESPlus.getEncPWD("123456"));
			}
			if (vehicleDao.is_repeat(vehicle)) {
				throw new Exception("添加车辆失败，车牌号码已存在!");
			}
			Long vehicle_id = vehicleDao.add(vehicle);
			// 客户车辆关系
			CustVehicle custVehicle = new CustVehicle();
			custVehicle.setCustomer_id(cust_id);
			custVehicle.setVehicle_id(vehicle_id);
			custVehicleDao.save(custVehicle);
			// 车台信息
			Unit unit = custInfo.getUnit();
			unit.setSubco_no(Long.valueOf(companyid));
			unit.setCustomer_id(cust_id);
			unit.setVehicle_id(vehicle_id);
			unit.setData_node(0);
			unit.setTelecom(1);
			unit.setSim_type(1);
			unit.setFlag(0);
			unit.setPay_model(0);
			unit.setReg_status(0);
			unit.setTrade(0);
			unit.setService_date(new Date());
			unit.setCreate_date(new Date());
			if (unit.getSales_id() == null) {
				unit.setSales_id(0L);
			}
			if (unitDao.is_repeat(unit)) {
				throw new Exception("添加车台失败，车载电话已存在!");
			}
			Long unit_id = unitDao.add(unit);
			// 车台月租费信息
			// 客户电话信息
			String mobile = "";
			if (StringUtils.isBlank(same_opid)) {
				List<Linkman> list = custInfo.getCustphones();
				for (Linkman phone : list) {
					if (phone.getPhone().equals("")) {
						continue;
					}
					if (phone.getAppsign() == 1) {
						mobile = phone.getPhone();
					}
					phone.setCustomer_id(cust_id);
					phone.setVehicle_id(vehicle_id);
					custphoneDao.save(phone);
				}
			}
			// 如果是私家车快速入网则要LDAP中添加用户对象
			if (custInfo.getCustomer().getCust_type() == 0 && StringUtils.isBlank(same_opid)) {
				// Ldap中用戶用户对象
				CommonOperator operator = new CommonOperator();
				String opid = IdCreater.getOperatorId();
				// 添加关系(APP需求)
				OperatorUnit operatorUnit = new OperatorUnit();
				operatorUnit.setOp_id(Long.valueOf(opid));
				operatorUnit.setVehicle_id(vehicle_id);
				operatorUnit.setUnit_id(unit_id);
				operatorUnitDao.save(operatorUnit);
				operator.setOpid(opid);
				operator.setLoginname(custInfo.getVehicle().getPlate_no());
				operator.setOpname(custInfo.getCustomer().getCustomer_name());
				if (StringUtils.isNotBlank(service_pwd)) {
					operator.setUserPassword(service_pwd);
				} else {
					operator.setUserPassword("123456");
				}
				operator.setStatus("11");// 属于客户类型
				operator.setCustomerid(String.valueOf(cust_id));
				operator.setNumberplate(custInfo.getVehicle().getPlate_no());
				operator.setMobile(mobile);
				OpenLdap ldap = OpenLdapManager.getInstance();
				ldap.add(operator);
			} else {
				// 添加关系(APP需求)
				OpenLdap ldap = OpenLdapManager.getInstance();
				CommonOperator operator = ldap.getOperatorBycustId(String.valueOf(cust_id));
				OperatorUnit operatorUnit = new OperatorUnit();
				operatorUnit.setOp_id(Long.valueOf(operator.getOpid()));
				operatorUnit.setVehicle_id(vehicle_id);
				operatorUnit.setUnit_id(unit_id);
				operatorUnitDao.save(operatorUnit);
			}
			resultMap.put("success", true);
			resultMap.put("msg", "入网成功!");
		} catch (Exception e) {
			System.out.println("出错了" + e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultMap;
	}

	@Override
	public HashMap addLargeCustMng(LargeCustMngEntry largeCustMngEntry, String userId, String companyid, String companycode, String companyname) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String companyno = IdCreater.getCompanyId();
			Long company_id =  Long.valueOf(companyno);
			// 客戶信息
			Customer customer = new Customer();
			customer.setCustomer_name(largeCustMngEntry.getCustomer_name());
			if (StringUtils.isNotBlank(largeCustMngEntry.getServer_pwd())) {
				customer.setService_pwd(DESPlus.getEncPWD(largeCustMngEntry.getServer_pwd()));
			} else {
				customer.setService_pwd(DESPlus.getEncPWD("123456"));
			}
			customer.setSubco_no(Long.valueOf(companyid));
			customer.setSubco_code(companycode);
			customer.setSubco_name(companyname);
			customer.setCustco_no(company_id);
			customer.setCustco_code("0");
			Integer is_guarantee = largeCustMngEntry.getIs_guarantee();
			if (is_guarantee == 0) {// 是担保公司
				customer.setCust_type(2);
			} else {
				customer.setCust_type(1);
			}
			customer.setAddress(largeCustMngEntry.getCust_address());
			customer.setEmail(largeCustMngEntry.getEmail());
			customer.setVip(largeCustMngEntry.getVip());
			customer.setContract_no(largeCustMngEntry.getFileno());
			customer.setLocation(largeCustMngEntry.getLocation());
			customer.setRemark(largeCustMngEntry.getRemark());
			customer.setOp_id(Long.valueOf(userId));
			customer.setIdtype(99);
			customer.setTrade(largeCustMngEntry.getTrade());
			customer.setFlag(0);
			customer.setPay_model(0);
			customer.setSex(1);
			Long cust_id = customerDao.add(customer);
			// 客户电话信息
			String mobile = "";
			List<Linkman> list = largeCustMngEntry.getCustphones();
			for (Linkman phone : list) {
				if (phone.getPhone().equals("")) {
					continue;
				}
				if ("".equals(mobile)) {
					mobile = phone.getPhone();
				}
				phone.setCustomer_id(cust_id);
				phone.setVehicle_id(0L);
				custphoneDao.save(phone);
			}
			// 保存机构信息到t_ba_org表
			Org org = new Org();
			org.setOrgId(company_id);
			org.setOrgNo(company_id);
			org.setOrgName(largeCustMngEntry.getCustomer_name());
			org.setOrgType(1);
			org.setOpId(Long.valueOf(userId));
			org.setOrgCode("0");
			orgSer.saveOrg(org);
			// 托收資料
			Long collection_id = 0L;
			if (StringUtils.isNotBlank(largeCustMngEntry.getAccount()) && StringUtils.isNotBlank(largeCustMngEntry.getCollection_name())) {
				Collection collection = new Collection();
				collection.setCustomer_id(cust_id);
				collection.setSubco_no(Long.valueOf(companyid));
				collection.setAc_no(largeCustMngEntry.getAccount());
				collection.setAc_name(largeCustMngEntry.getCollection_name());
				collection.setAgency(largeCustMngEntry.getAgency());
				collection.setBank(largeCustMngEntry.getBank());
				collection.setAc_type(largeCustMngEntry.getType());
				collection.setIs_delivery(largeCustMngEntry.getIs_delivery());
				collection.setTel(largeCustMngEntry.getTel());
				collection.setAddressee(largeCustMngEntry.getAddressee());
				collection.setPost_code(largeCustMngEntry.getPost_code());
				collection.setProvince(largeCustMngEntry.getProvince());
				collection.setCity(largeCustMngEntry.getCity());
				collection.setArea(largeCustMngEntry.getArea());
				collection.setAddress(largeCustMngEntry.getAddress());
				collection.setOp_id(Long.valueOf(userId));
				collection.setPay_ct_no(largeCustMngEntry.getPay_ct_no());
				collection.setAc_id_no("");
				collection.setBank_code(largeCustMngEntry.getBankcode());
				collection.setFee_cycle(1);
				collection.setPrint_freq(1);
				collection.setCreate_date(new Date());
				collection.setAc_id_no(largeCustMngEntry.getAc_id_no());
				//纳税人识别号
				collection.setTax_payer_seq(largeCustMngEntry.getTaxPayerId());
				collectionDao.save(collection);
			}
			// 销售经理客户关系
			if (StringUtils.isNotBlank(largeCustMngEntry.getManager())) {
				CustSales custSales = new CustSales();
				custSales.setCustomer_id(cust_id);
				custSales.setManager_id(Long.valueOf(largeCustMngEntry.getManagerid()));
				custSales.setManager_name(largeCustMngEntry.getManager());
				custSales.setIsdel(0);
				customerDao.save(custSales);
			}
			// Ldap中用戶用户对象
			OpenLdap ldap = OpenLdapManager.getInstance();
			// 需要验证入网用户登录名以及设置的登录电话是否重复
			// if(isOperatorExsit(ldap, largeCustMngEntry.getLoginname(), null,
			// null)){
			// throw new Exception("登录名重复");
			// }
			// if(isOperatorExsit(ldap, null, mobile, null)){
			// throw new Exception("登录电话重复");
			// }
			CommonOperator operator = new CommonOperator();
			String opid = IdCreater.getOperatorId();
			operator.setOpid(opid);
			operator.setLoginname(largeCustMngEntry.getLoginname());
			operator.setOpname(largeCustMngEntry.getCustomer_name());
			operator.setUserPassword(largeCustMngEntry.getServer_pwd());
			operator.setStatus("10");// 属于客户类型
			operator.setCustomerid(String.valueOf(cust_id));
			operator.setMobile(mobile);
			CommonCompany company = new CommonCompany();
			company.setCompanyno(companyno);
			company.setCompanyname(largeCustMngEntry.getCustomer_name());
			company.setCompanylevel("3");
			company.setOrder("0");
			company.setParentcompanyno(SystemConst.GROUPCOMPANYNO);
			company.setCompanytype("10");// 大于等于10表示是外部机构
			if (ldap.getCompanyByname(largeCustMngEntry.getCustomer_name()) != null) {
				throw new Exception("添加客户失败，公司名称已存在");
			}
			ldap.add(company);
			ldap.add(operator);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultMap.put("success", false);
			resultMap.put("msg", e.getMessage());
			return resultMap;
		}
		resultMap.put("success", true);
		resultMap.put("msg", "操作成功!");
		return resultMap;
	}

	@Override
	public HashMap addChildlargeCust(LargeCustMngEntry largeCustMngEntry, String userId, String companyid, String companycode, String companyname) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Long company_id = largeCustMngEntry.getCompanyno();
			OpenLdap ldap = OpenLdapManager.getInstance();
			CommonCompany company = ldap.getCompanyById(String.valueOf(company_id));
			// 客戶信息
			Customer customer = new Customer();
			customer.setCustomer_name(largeCustMngEntry.getCustomer_name());
			customer.setService_pwd(DESPlus.getEncPWD(largeCustMngEntry.getServer_pwd()));
			customer.setSubco_no(Long.valueOf(companyid));
			customer.setSubco_code(companycode);
			customer.setSubco_name(companyname);
			customer.setCustco_no(Long.valueOf(company.getParentcompanyno()));
			customer.setCustco_code(company.getCompanycode());
			Integer is_guarantee = largeCustMngEntry.getIs_guarantee();
			if (is_guarantee == 0) {// 是担保公司
				customer.setCust_type(2);
			} else {
				customer.setCust_type(1);
			}
			customer.setAddress(largeCustMngEntry.getCust_address());
			customer.setEmail(largeCustMngEntry.getEmail());
			customer.setVip(largeCustMngEntry.getVip());
			customer.setContract_no(largeCustMngEntry.getFileno());
			customer.setLocation(largeCustMngEntry.getLocation());
			customer.setRemark(largeCustMngEntry.getRemark());
			customer.setOp_id(Long.valueOf(userId));
			customer.setIdtype(99);
			customer.setTrade(largeCustMngEntry.getTrade());
			customer.setFlag(0);
			customer.setPay_model(0);
			if (customerDao.is_repeat(customer)) {
				throw new Exception("添加客户失败，客户名称已存在!");
			}
			Long cust_id = customerDao.add(customer);
			// 客户电话信息
			String mobile = "";
			List<Linkman> list = largeCustMngEntry.getCustphones();
			for (Linkman phone : list) {
				if (phone.getPhone().equals("")) {
					continue;
				}
				if ("".equals(mobile)) {
					mobile = phone.getPhone();
				}
				phone.setCustomer_id(cust_id);
				phone.setVehicle_id(0L);
				custphoneDao.save(phone);
			}
			// 托收資料
			Long collection_id = 0L;
			if (StringUtils.isNotBlank(largeCustMngEntry.getAccount()) && StringUtils.isNotBlank(largeCustMngEntry.getCollection_name())) {
				Collection collection = new Collection();
				collection.setCustomer_id(cust_id);
				collection.setSubco_no(Long.valueOf(companyid));
				collection.setAc_no(largeCustMngEntry.getAccount());
				collection.setAc_name(largeCustMngEntry.getCollection_name());
				collection.setAgency(largeCustMngEntry.getAgency());
				collection.setBank(largeCustMngEntry.getBank());
				collection.setAc_type(largeCustMngEntry.getType());
				collection.setIs_delivery(largeCustMngEntry.getIs_delivery());
				collection.setTel(largeCustMngEntry.getTel());
				collection.setAddressee(largeCustMngEntry.getAddressee());
				collection.setPost_code(largeCustMngEntry.getPost_code());
				collection.setProvince(largeCustMngEntry.getProvince());
				collection.setCity(largeCustMngEntry.getCity());
				collection.setArea(largeCustMngEntry.getArea());
				collection.setAddress(largeCustMngEntry.getAddress());
				collection.setOp_id(Long.valueOf(userId));
				collection.setPay_ct_no(largeCustMngEntry.getPay_ct_no());
				collection.setAc_id_no("");
				collection.setBank_code(largeCustMngEntry.getBankcode());
				collection.setFee_cycle(1);
				collection.setPrint_freq(1);
				collection.setCreate_date(new Date());
				collection.setAc_id_no(largeCustMngEntry.getAc_id_no());
				collectionDao.save(collection);
			}
			// 销售经理客户关系
			if (StringUtils.isNotBlank(largeCustMngEntry.getManager())) {
				CustSales custSales = new CustSales();
				custSales.setCustomer_id(cust_id);
				custSales.setManager_id(Long.valueOf(largeCustMngEntry.getManagerid()));
				custSales.setManager_name(largeCustMngEntry.getManager());
				custSales.setIsdel(0);
				customerDao.save(custSales);
			}
			// Ldap中用戶用户对象
			CommonOperator operator = new CommonOperator();
			String opid = IdCreater.getOperatorId();
			operator.setOpid(opid);
			operator.setLoginname(largeCustMngEntry.getLoginname());
			operator.setOpname(largeCustMngEntry.getCustomer_name());
			operator.setUserPassword(largeCustMngEntry.getServer_pwd());
			operator.setStatus("10");// 属于客户类型
			operator.setCustomerid(String.valueOf(cust_id));
			operator.setMobile(mobile);
			if (ldap.getOperatorByopname(largeCustMngEntry.getCustomer_name()) != null) {
				throw new Exception("添加客户失败，客户名称已存在!");
			}
			ldap.add(operator);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		resultMap.put("success", true);
		resultMap.put("msg", "操作成功!");
		return resultMap;
	}

	@Override
	public HashMap updateLargeCustMng(LargeCustMngEntry largeCustMngEntry, String userId, String companyid, String companycode, String companyname) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Long cust_id = largeCustMngEntry.getCust_id();
		Long company_id = largeCustMngEntry.getCompanyno();
		try {
			// 客戶信息
			Customer customer = customerDao.get(Customer.class, largeCustMngEntry.getCust_id());
			customer.setCustomer_name(largeCustMngEntry.getCustomer_name());
			if (largeCustMngEntry.getServer_pwd().indexOf("{SH") == -1) {
				customer.setService_pwd(DESPlus.getEncPWD(largeCustMngEntry.getServer_pwd()));
			}
			customer.setSubco_no(Long.valueOf(companyid));
			customer.setSubco_code(companycode);
			customer.setSubco_name(companyname);
			// customer.setCustco_no(company_id);
			Integer is_guarantee = largeCustMngEntry.getIs_guarantee();
			if (is_guarantee == 0) {// 是担保公司
				customer.setCust_type(2);
			} else {
				customer.setCust_type(1);
			}
			customer.setAddress(largeCustMngEntry.getCust_address());
			customer.setEmail(largeCustMngEntry.getEmail());
			customer.setVip(largeCustMngEntry.getVip());
			customer.setContract_no(largeCustMngEntry.getFileno());
			customer.setLocation(largeCustMngEntry.getLocation());
			customer.setTrade(largeCustMngEntry.getTrade());
			customer.setRemark(largeCustMngEntry.getRemark());
			customer.setOp_id(Long.valueOf(userId));
			// 销售经理客户关系
			CustSales oldCustSales = customerDao.getCustSales(cust_id);
			if (StringUtils.isNotBlank(largeCustMngEntry.getManager())) {
				if (oldCustSales == null) {
					CustSales custSales = new CustSales();
					custSales.setCustomer_id(cust_id);
					custSales.setManager_id(Long.valueOf(largeCustMngEntry.getManagerid()));
					custSales.setManager_name(largeCustMngEntry.getManager());
					custSales.setIsdel(0);
					customerDao.save(custSales);
				} else {
					// 如果先后销售经理不同
					if (!largeCustMngEntry.getManager().equals(oldCustSales.getManager_name())) {
						customerDao.updateCustSales(cust_id);
						CustSales custSales = new CustSales();
						custSales.setCustomer_id(cust_id);
						custSales.setManager_id(Long.valueOf(largeCustMngEntry.getManagerid()));
						custSales.setManager_name(largeCustMngEntry.getManager());
						custSales.setIsdel(0);
						customerDao.save(custSales);
					}
				}
			} else if (oldCustSales != null) {
				customerDao.updateCustSales(cust_id);
			}
			// 修改
			// Ldap中用戶用户对象
			OpenLdap ldap = OpenLdapManager.getInstance();
			// 需要验证入网用户登录名以及设置的登录电话是否重复
			// if(isOperatorExsit(ldap, largeCustMngEntry.getLoginname(), null,
			// null)){
			// throw new Exception("登录名重复");
			// }
			String opid = String.valueOf(largeCustMngEntry.getOpid());
			CommonOperator operator = ldap.getOperatorById(opid);
			String dn = operator.getDn();
			String[] keys = new String[3];
			Object[] values = new Object[3];
			keys[0] = "loginname";
			keys[1] = "opname";
			keys[2] = "userpassword";
			values[0] = largeCustMngEntry.getLoginname();
			values[1] = largeCustMngEntry.getCustomer_name();
			values[2] = largeCustMngEntry.getServer_pwd();
			ldap.modifyInformations(dn, keys, values);
			// 机构信息
			CommonCompany checkCompany = ldap.getCompanyByname(largeCustMngEntry.getLoginname());
			String companyno = String.valueOf(largeCustMngEntry.getCompanyno());
			if (checkCompany != null) {
				if (!checkCompany.getCompanyno().equals(companyno)) {
					throw new Exception("修改客户失败，公司名称已存在!");
				}
			}
			CommonCompany company = ldap.getCompanyById(companyno);
			String companydn = company.getDn();
			ldap.modifyInformation(companydn, "companyname", largeCustMngEntry.getCustomer_name());
			customer.setCustco_code(company.getCompanycode());
			customerDao.update(customer);
			// 客户电话信息
			List<Linkman> list = largeCustMngEntry.getCustphones();
			custphoneDao.deleteByCust_id(cust_id);
			for (Linkman phone : list) {
				if (phone.getPhone().equals("")) {
					continue;
				}
				phone.setCustomer_id(cust_id);
				phone.setVehicle_id(0L);
				custphoneDao.save(phone);
			}
			// 托收資料
			if (largeCustMngEntry.getCollection_id() != null) {
				Collection collection = collectionDao.get(Collection.class, largeCustMngEntry.getCollection_id());
				// 查询正在托收计费的客户id列表
				List<Long> lockcustids = datalockDao.getLockCustomer();
				if (lockcustids.contains(cust_id)) {
					if (!(collection.getAc_no().equals(largeCustMngEntry.getAccount())
							&& collection.getAc_name().equals(largeCustMngEntry.getCollection_name())
							&& collection.getAgency().equals(largeCustMngEntry.getAgency())
							&& collection.getBank().equals(largeCustMngEntry.getBank())
							&& collection.getBank_code().equals(largeCustMngEntry.getBankcode())
							&& collection.getPost_code().equals(largeCustMngEntry.getPost_code())
							&& collection.getPay_ct_no().equals(largeCustMngEntry.getPay_ct_no())
							&& collection.getAc_type().equals(largeCustMngEntry.getType())
							&& collection.getIs_delivery().equals(largeCustMngEntry.getIs_delivery())
							&& collection.getTel().equals(largeCustMngEntry.getTel())
							&& collection.getAddressee().equals(largeCustMngEntry.getAddressee())
							&& collection.getProvince().equals(largeCustMngEntry.getProvince())
							&& collection.getCity().equals(largeCustMngEntry.getCity())
							&& collection.getArea().equals(largeCustMngEntry.getArea())
							&& collection.getAddress().equals(largeCustMngEntry.getAddress()) 
							&& collection.getAc_id_no().equals(largeCustMngEntry.getAc_id_no()) )) {
						resultMap.put("success", true);
						resultMap.put("msg", "操作失败，该客户正在进行托收计费不能修改托收资料!");
						return resultMap;
					}
				}

				collection.setAc_no(largeCustMngEntry.getAccount());
				collection.setAc_name(largeCustMngEntry.getCollection_name());
				collection.setAgency(largeCustMngEntry.getAgency());
				collection.setBank(largeCustMngEntry.getBank());
				collection.setBank_code(largeCustMngEntry.getBankcode());
				collection.setPost_code(largeCustMngEntry.getPost_code());
				collection.setPay_ct_no(largeCustMngEntry.getPay_ct_no());
				collection.setAc_type(largeCustMngEntry.getType());
				collection.setIs_delivery(largeCustMngEntry.getIs_delivery());
				collection.setTel(largeCustMngEntry.getTel());
				collection.setAddressee(largeCustMngEntry.getAddressee());
				collection.setProvince(largeCustMngEntry.getProvince());
				collection.setCity(largeCustMngEntry.getCity());
				collection.setArea(largeCustMngEntry.getArea());
				collection.setAddress(largeCustMngEntry.getAddress());
				collection.setOp_id(Long.valueOf(userId));
				collection.setAc_id_no(largeCustMngEntry.getAc_id_no());
				//纳税人识别号
				collection.setTax_payer_seq(largeCustMngEntry.getTaxPayerId());
				collectionDao.update(collection);
			} else if (StringUtils.isNotBlank(largeCustMngEntry.getAccount()) && StringUtils.isNotBlank(largeCustMngEntry.getCollection_name())) {
				Collection collection = new Collection();
				collection.setCustomer_id(cust_id);
				collection.setSubco_no(Long.valueOf(companyid));
				collection.setAc_no(largeCustMngEntry.getAccount());
				collection.setAc_name(largeCustMngEntry.getCollection_name());
				collection.setAgency(largeCustMngEntry.getAgency());
				collection.setBank(largeCustMngEntry.getBank());
				collection.setAc_type(largeCustMngEntry.getType());
				collection.setIs_delivery(largeCustMngEntry.getIs_delivery());
				collection.setAddressee(largeCustMngEntry.getAddressee());
				collection.setPost_code(largeCustMngEntry.getPost_code());
				collection.setPay_ct_no(largeCustMngEntry.getPay_ct_no());
				collection.setTel(largeCustMngEntry.getTel());
				collection.setProvince(largeCustMngEntry.getProvince());
				collection.setCity(largeCustMngEntry.getCity());
				collection.setArea(largeCustMngEntry.getArea());
				collection.setAddress(largeCustMngEntry.getAddress());
				collection.setOp_id(Long.valueOf(userId));
				collection.setAc_id_no("");
				collection.setBank_code(largeCustMngEntry.getBankcode());
				collection.setFee_cycle(1);
				collection.setPrint_freq(1);
				collection.setCreate_date(new Date());
				collection.setAc_id_no(largeCustMngEntry.getAc_id_no());
				collectionDao.save(collection);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		resultMap.put("success", true);
		resultMap.put("msg", "操作成功!");
		return resultMap;
	}

	/**
	 * 新增私家车入网资料
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public HashMap addPrivateCust(PrivateNetworkEntry privateNetworkEntry, String userId, String companyid, String companycode, String companyname) {
		HashMap map = new HashMap<String, Object>();
		try {
			Long cust_id = privateNetworkEntry.getCust_id();
			Long same_opid = privateNetworkEntry.getOpid();
			// 新增
			// 客戶信息
			if (same_opid == 0) {
				Customer customer = new Customer();
				customer.setSubco_no(Long.valueOf(companyid));
				customer.setSubco_code(companycode);
				customer.setSubco_name(companyname);
				customer.setCustco_no(0L);
				customer.setCustco_code("0");
				customer.setCustomer_name(privateNetworkEntry.getCustomer_name());
				if (StringUtils.isNotBlank(privateNetworkEntry.getServer_pwd())) {
					customer.setService_pwd(DESPlus.getEncPWD(privateNetworkEntry.getServer_pwd()));
				} else {
					customer.setService_pwd(DESPlus.getEncPWD("123456"));
				}
				if (StringUtils.isNotBlank(privateNetworkEntry.getPrivate_pwd())) {
					customer.setPrivate_pwd(DESPlus.getEncPWD(privateNetworkEntry.getPrivate_pwd()));
				}
				customer.setCust_type(0);
				customer.setId_no(privateNetworkEntry.getIdcard());
				customer.setBirthday(DateUtil.parse(privateNetworkEntry.getBirthday(), DateUtil.YMD_DASH));
				customer.setSex(privateNetworkEntry.getSex());
				customer.setEmail(privateNetworkEntry.getEmail());
				customer.setVip(privateNetworkEntry.getVip());
				customer.setContract_no(privateNetworkEntry.getFileno());
				customer.setLocation(privateNetworkEntry.getLocation());
				customer.setRemark(privateNetworkEntry.getRemark());
				customer.setAddress(privateNetworkEntry.getAddress());
				customer.setOp_id(Long.valueOf(userId));
				customer.setIdtype(1);
				customer.setTrade(0);
				customer.setFlag(0);
				customer.setPay_model(0);
				cust_id = customerDao.add(customer);
			}
			// 车辆资料
			Vehicle vehicle = new Vehicle();
			vehicle.setSubco_no(Long.valueOf(companyid));
			vehicle.setPlate_no(privateNetworkEntry.getNumber_plate());
			vehicle.setPlate_color(privateNetworkEntry.getPlate_color());
			vehicle.setVehicle_type(privateNetworkEntry.getVehicle_type());
			vehicle.setBrand(privateNetworkEntry.getBrand());
			vehicle.setSeries(privateNetworkEntry.getSeries());
			vehicle.setModel(privateNetworkEntry.getCartype());
			vehicle.setModel_name(privateNetworkEntry.getCartype_name());
			vehicle.setVin(privateNetworkEntry.getCode());
			vehicle.setEngine_no(privateNetworkEntry.getEngine_no());
			vehicle.setChassis_no(privateNetworkEntry.getPlate_no());
			vehicle.setFactory(privateNetworkEntry.getFactory());
			vehicle.setColor(privateNetworkEntry.getColor());
			vehicle.setBuy_date(DateUtil.parse(privateNetworkEntry.getBuy_time(), DateUtil.YMD_DASH));
			vehicle.setProduction_date(DateUtil.parse(privateNetworkEntry.getProduction_date(), DateUtil.YMD_DASH));
			vehicle.setOp_id(Long.valueOf(userId));
			vehicle.setVehicle_status(0);
			vehicle.setFlag(1);
			vehicle.setEquip_code(privateNetworkEntry.getEquip_code());
			vehicle.setVl_owner(privateNetworkEntry.getVl_owner());
			vehicle.setVl_type(privateNetworkEntry.getVl_type());
			vehicle.setVload(privateNetworkEntry.getVload());
			vehicle.setVl_use(privateNetworkEntry.getVl_use());
			vehicle.setVl_quality(privateNetworkEntry.getVl_quality());
			vehicle.setVl_qt_quality(privateNetworkEntry.getVl_qt_quality());
			vehicle.setVl_ap_quality(privateNetworkEntry.getVl_ap_quality());
			vehicle.setVl_vsize(privateNetworkEntry.getVl_vsize());
			vehicle.setVl_doc_no(privateNetworkEntry.getVl_doc_no());
			vehicle.setVl_remark(privateNetworkEntry.getVl_remark());
			vehicle.setIs_bdate(DateUtil.parse(privateNetworkEntry.getIs_bdate(), DateUtil.YMD_DASH));
			vehicle.setIs_edate(DateUtil.parse(privateNetworkEntry.getIs_edate(), DateUtil.YMD_DASH));
			vehicle.setIs_corp(privateNetworkEntry.getIs_corp());
			vehicle.setIs_pilfer(privateNetworkEntry.getIs_pilfer());
			//添加UBI保险销售经理
			vehicle.setUbi_sales_id(privateNetworkEntry.getUbi_sales_id());
			
			System.out.println(" +++++++++++++ vehicle.getUbi_sales_id()= " + vehicle.getUbi_sales_id());
			
			if (StringUtils.isNotBlank(privateNetworkEntry.getServer_pwd())) {
				vehicle.setService_pwd(DESPlus.getEncPWD(privateNetworkEntry.getServer_pwd()));
			} else {
				vehicle.setService_pwd(DESPlus.getEncPWD("123456"));
			}
			if (StringUtils.isNotBlank(privateNetworkEntry.getPrivate_pwd())) {
				vehicle.setPrivate_pwd(DESPlus.getEncPWD(privateNetworkEntry.getPrivate_pwd()));
			}
			vehicle.setVehicle_license(privateNetworkEntry.getDriving_no());
			vehicle.setVl_bdate(DateUtil.parse(privateNetworkEntry.getGrant_time(), DateUtil.YMD_DASH));
			vehicle.setVl_edate(DateUtil.parse(privateNetworkEntry.getValid_time(), DateUtil.YMD_DASH));
			vehicle.setId_4s(privateNetworkEntry.getId_4s());
			vehicle.setInsurance_id(privateNetworkEntry.getInsurance_id());
			/*
			 * Long vehicle_id = 0L; if(vehicleService.is_repeat(vehicle)){
			 * is_addVehicle = false; vehicle_id =
			 * vehicleService.getIdByPlate(custInfo.getVehicle().getPlate_no());
			 * }else{ vehicle_id = vehicleService.add(vehicle); }
			 */
			if (vehicleDao.is_repeat(vehicle)) {
				throw new Exception("添加车辆失败，车牌号码已存在!");
			}
			Long vehicle_id = vehicleDao.add(vehicle);
			// 客户车辆关系
			CustVehicle custVehicle = new CustVehicle();
			custVehicle.setCustomer_id(cust_id);
			custVehicle.setVehicle_id(vehicle_id);
			custVehicleDao.save(custVehicle);
			// 客户电话信息
			String mobile = "";
			if (same_opid == 0) {
				List<Linkman> list = privateNetworkEntry.getCustphones();
				for (Linkman phone : list) {
					if (phone.getPhone().equals("")) {
						continue;
					}
					if (phone.getAppsign() == 1) {
						mobile = phone.getPhone();
					}
					phone.setCustomer_id(cust_id);
					phone.setVehicle_id(vehicle_id);
					custphoneDao.save(phone);
				}
			}
			// 车台资料
			Unit unit = new Unit();
			unit.setSubco_no(Long.valueOf(companyid));
			unit.setCustomer_id(cust_id);
			unit.setVehicle_id(vehicle_id);
			unit.setUnittype_id(privateNetworkEntry.getUnittype_id());
			unit.setProduct_name(privateNetworkEntry.getName());
			unit.setMode(privateNetworkEntry.getMode());
			unit.setCall_letter(privateNetworkEntry.getCall_letter());
			unit.setTelecom(privateNetworkEntry.getOperators());
			unit.setSim_type(privateNetworkEntry.getSimtype());
			unit.setBranch(privateNetworkEntry.getBranch());
			if (privateNetworkEntry.getManager_id() == null) {
				unit.setSales_id(0L);
			} else {
				unit.setSales_id(privateNetworkEntry.getManager_id());
			}
			unit.setSales(privateNetworkEntry.getManager_name());
			unit.setWorker(privateNetworkEntry.getWorker());
			unit.setArea(privateNetworkEntry.getUnitarea());
			unit.setTamper_box(privateNetworkEntry.getTamper_box());
			unit.setTamper_smart(privateNetworkEntry.getTamper_smart());
			unit.setTamper_wireless(privateNetworkEntry.getTamper_wireless());
			unit.setIs_sz(privateNetworkEntry.getIs_sz());
			unit.setImei(privateNetworkEntry.getImei());
			unit.setTamper_code(privateNetworkEntry.getTamper_code());
			unit.setData_node(0);
			unit.setSms_node(privateNetworkEntry.getNode());
			unit.setSpecial_no(privateNetworkEntry.getSpecial());
			unit.setFix_time(DateUtil.parse(privateNetworkEntry.getTime(), DateUtil.YMD_DASH));
			unit.setService_date(new Date());
			unit.setContract_no(privateNetworkEntry.getFileno());
			unit.setLocation(privateNetworkEntry.getLocation());
			unit.setArchive_time(new Date());
			unit.setFlag(0);
			unit.setPay_model(0);
			unit.setReg_status(0);
			unit.setTrade(0);
			unit.setCreate_date(new Date());
			unit.setOp_id(Long.valueOf(userId));
			if (unitDao.is_repeat(unit)) {
				throw new Exception("添加车台失败，车载电话已存在!");
			}
			Long unit_id = unitDao.add(unit);
			// 更新SIM卡信息
			Long sim_id = privateNetworkEntry.getSim_id();
			if (sim_id != null && sim_id != 0) {
				Preload preload = preloadDao.get(Preload.class, sim_id);
				preload.setUnittype_id(privateNetworkEntry.getUnittype_id());
				preload.setVin(privateNetworkEntry.getCode());
				preload.setEngine_no(privateNetworkEntry.getEngine_no());
				preload.setColor(privateNetworkEntry.getColor());
				preload.setProduction_date(DateUtil.parse(privateNetworkEntry.getProduction_date(), DateUtil.YMD_DASH));
				preload.setFlag(1);
				preload.setNew_combo_id(privateNetworkEntry.getCombo_id());
				preload.setPack_id(privateNetworkEntry.getPack_id());
				preload.setOp_id(Long.valueOf(userId));
				preload.setUnit_id(unit_id);
				preload.setCombo_status(1);
				preloadDao.save(preload);
			}
			// 中间客户
			if (privateNetworkEntry.getGuarantee_id() != null) {
				MidCust midCust = new MidCust();
				midCust.setSubco_no(Long.valueOf(companyid));
				midCust.setCust_type(2);
				midCust.setCustomer_id(privateNetworkEntry.getGuarantee_id());
				midCust.setUnit_id(unit_id);
				midCustDao.save(midCust);
			}
			// 条形码
			List<Barcode> barList = privateNetworkEntry.getBarcodes();
			for (Barcode barcode : barList) {
				if (barcode.getContent().equals("")) {
					continue;
				}
				barcode.setUnit_id(unit_id);
				barcodeDao.save(barcode);
			}
			// 托收資料
			Long collection_id = 0L;
			if (same_opid == 0 && StringUtils.isNotBlank(privateNetworkEntry.getAccount())
					&& StringUtils.isNotBlank(privateNetworkEntry.getCollection_name())) {
				Collection collection = collectionDao.getCollectionByCustId(cust_id);
				if (collection == null) {
					collection = new Collection();
					collection.setCustomer_id(cust_id);
					collection.setSubco_no(Long.valueOf(companyid));
					collection.setAc_no(privateNetworkEntry.getAccount());
					collection.setAc_name(privateNetworkEntry.getCollection_name());
					collection.setAgency(privateNetworkEntry.getAgency());
					collection.setBank(privateNetworkEntry.getBank());
					collection.setAc_type(privateNetworkEntry.getType());
					collection.setIs_delivery(privateNetworkEntry.getIs_delivery());
					collection.setAddressee(privateNetworkEntry.getAddressee());
					collection.setPost_code(privateNetworkEntry.getPost_code());
					collection.setTel(privateNetworkEntry.getTel());
					collection.setProvince(privateNetworkEntry.getProvince());
					collection.setCity(privateNetworkEntry.getCity());
					collection.setArea(privateNetworkEntry.getArea());
					collection.setAddress(privateNetworkEntry.getAddress());
					collection.setOp_id(Long.valueOf(userId));
					collection.setPay_ct_no(privateNetworkEntry.getPay_ct_no());
					collection.setAc_id_no("");
					collection.setBank_code(privateNetworkEntry.getBankcode());
					collection.setFee_cycle(1);
					collection.setPrint_freq(1);
					collection.setCreate_date(new Date());
					collection.setAc_id_no(privateNetworkEntry.getAc_id_no());
					collection.setTax_payer_seq(privateNetworkEntry.getTaxPayerId());
					collection_id = collectionDao.addCollection(collection);
				}
			}
			// 托收合同号
			String pay_ct_no = privateNetworkEntry.getPay_ct_no();
			// 服务费
			FeeInfo serviceInfo = privateNetworkEntry.getServiceInfo();
			if (serviceInfo.getMonth_fee() != null) {
				serviceInfo.setSubco_no(Long.valueOf(companyid));
				serviceInfo.setCustomer_id(cust_id);
				serviceInfo.setVehicle_id(vehicle_id);
				serviceInfo.setUnit_id(unit_id);
				serviceInfo.setFeetype_id(1);
				serviceInfo.setItem_id(0L);
				serviceInfo.setPay_ct_no(pay_ct_no);
				serviceInfo.setCollection_id(collection_id);
				if (serviceInfo.getFee_date() == null) {
					serviceInfo.setFee_date(new Date());
					serviceInfo.setFee_sedate(new Date());
				} else {
					serviceInfo.setFee_sedate(serviceInfo.getFee_date());
				}
				if (serviceInfo.getFee_cycle() == null) {
					serviceInfo.setFee_cycle(1);
				}
				serviceInfo.setAc_amount(serviceInfo.getMonth_fee() * serviceInfo.getFee_cycle());
				serviceInfo.setReal_amount(serviceInfo.getMonth_fee() * serviceInfo.getFee_cycle());
				serviceInfo.setOp_id(Long.valueOf(userId));
				feeInfoDao.save(serviceInfo);
			}
			// SIM卡月租费
			FeeInfo simfeeInfo = privateNetworkEntry.getSimfeeInfo();
			if (simfeeInfo.getMonth_fee() != null) {
				simfeeInfo.setSubco_no(Long.valueOf(companyid));
				simfeeInfo.setCustomer_id(cust_id);
				simfeeInfo.setVehicle_id(vehicle_id);
				simfeeInfo.setUnit_id(unit_id);
				simfeeInfo.setFeetype_id(2);
				simfeeInfo.setItem_id(0L);
				simfeeInfo.setPay_ct_no(pay_ct_no);
				simfeeInfo.setCollection_id(collection_id);
				if (simfeeInfo.getFee_date() == null) {
					simfeeInfo.setFee_date(new Date());
					simfeeInfo.setFee_sedate(new Date());
				} else {
					simfeeInfo.setFee_sedate(simfeeInfo.getFee_date());
				}
				if (simfeeInfo.getFee_cycle() == null) {
					simfeeInfo.setFee_cycle(1);
				}
				simfeeInfo.setAc_amount(simfeeInfo.getMonth_fee() * simfeeInfo.getFee_cycle());
				simfeeInfo.setReal_amount(simfeeInfo.getMonth_fee() * simfeeInfo.getFee_cycle());
				simfeeInfo.setOp_id(Long.valueOf(userId));
				feeInfoDao.save(simfeeInfo);
			}
			// 网上查车费
			FeeInfo webgisInfo = privateNetworkEntry.getWebgisInfo();
			if (webgisInfo.getMonth_fee() != null) {
				webgisInfo.setSubco_no(Long.valueOf(companyid));
				webgisInfo.setCustomer_id(cust_id);
				webgisInfo.setVehicle_id(vehicle_id);
				webgisInfo.setUnit_id(unit_id);
				webgisInfo.setFeetype_id(4);
				webgisInfo.setItem_id(0L);
				webgisInfo.setPay_ct_no(pay_ct_no);
				webgisInfo.setCollection_id(collection_id);
				if (webgisInfo.getFee_date() == null) {
					webgisInfo.setFee_date(new Date());
					webgisInfo.setFee_sedate(new Date());
				} else {
					webgisInfo.setFee_sedate(webgisInfo.getFee_date());
				}
				if (webgisInfo.getFee_cycle() == null) {
					webgisInfo.setFee_cycle(1);
				}
				webgisInfo.setAc_amount(webgisInfo.getMonth_fee() * webgisInfo.getFee_cycle());
				webgisInfo.setReal_amount(webgisInfo.getMonth_fee() * webgisInfo.getFee_cycle());
				webgisInfo.setOp_id(Long.valueOf(userId));
				feeInfoDao.save(webgisInfo);
			}
			// 盗抢险费用
			FeeInfo insuranceInfo = privateNetworkEntry.getInsuranceInfo();
			if (insuranceInfo.getMonth_fee() != null) {
				insuranceInfo.setSubco_no(Long.valueOf(companyid));
				insuranceInfo.setCustomer_id(cust_id);
				insuranceInfo.setVehicle_id(vehicle_id);
				insuranceInfo.setUnit_id(unit_id);
				insuranceInfo.setFeetype_id(3);
				insuranceInfo.setItem_id(0L);
				insuranceInfo.setPay_ct_no(pay_ct_no);
				insuranceInfo.setCollection_id(collection_id);
				if (insuranceInfo.getFee_date() == null) {
					insuranceInfo.setFee_date(new Date());
					insuranceInfo.setFee_sedate(new Date());
				} else {
					insuranceInfo.setFee_sedate(insuranceInfo.getFee_date());
				}
				if (insuranceInfo.getFee_cycle() == null) {
					insuranceInfo.setFee_cycle(1);
				}
				insuranceInfo.setAc_amount(insuranceInfo.getMonth_fee() * insuranceInfo.getFee_cycle());
				insuranceInfo.setReal_amount(insuranceInfo.getMonth_fee() * insuranceInfo.getFee_cycle());
				insuranceInfo.setOp_id(Long.valueOf(userId));
				feeInfoDao.save(insuranceInfo);
			}
			// 前装服务费
			FeeInfo preloadInfo = privateNetworkEntry.getPreloadInfo();
			if (preloadInfo.getMonth_fee() != null) {
				preloadInfo.setSubco_no(Long.valueOf(companyid));
				preloadInfo.setCustomer_id(cust_id);
				preloadInfo.setVehicle_id(vehicle_id);
				preloadInfo.setUnit_id(unit_id);
				preloadInfo.setFeetype_id(101);
				preloadInfo.setItem_id(privateNetworkEntry.getPrePack_id());
				preloadInfo.setPay_ct_no("");
				preloadInfo.setCollection_id(0L);
				if (preloadInfo.getFee_date() == null) {
					preloadInfo.setFee_date(new Date());
					preloadInfo.setFee_sedate(new Date());
				} else {
					preloadInfo.setFee_sedate(preloadInfo.getFee_date());
				}
				if (preloadInfo.getFee_cycle() == null) {
					preloadInfo.setFee_cycle(1);
				}
				preloadInfo.setAc_amount(preloadInfo.getMonth_fee() * preloadInfo.getFee_cycle());
				preloadInfo.setReal_amount(preloadInfo.getMonth_fee() * preloadInfo.getFee_cycle());
				preloadInfo.setOp_id(Long.valueOf(userId));
				feeInfoDao.save(preloadInfo);
			}
			// 司机资料
			List<Driver> drivers = privateNetworkEntry.getDrivers();
			for (Driver driver : drivers) {
				if (StringUtils.isBlank(driver.getDriver_name())) {
					continue;
				}
				driver.setSubco_no(Long.valueOf(companyid));
				driver.setCustomer_id(cust_id);
				driver.setIdtype(1);
				driver.setVehicle_id(vehicle_id);
				driver.setOp_id(Long.valueOf(userId));
				driverDao.save(driver);
			}
			//向 LDAP中增加用户对象
			if (same_opid == 0) {
				String loginname = privateNetworkEntry.getLoginname();
				if (StringUtils.isBlank(loginname)) {
					loginname = privateNetworkEntry.getPlate_no();
				}
				// LDAP中用户对象
				OpenLdap ldap = OpenLdapManager.getInstance();
				// 需要验证入网用户登录名以及设置的登录电话是否重复
				// if(isOperatorExsit(ldap, loginname, null, null)){
				// throw new Exception("登录名重复");
				// }
				// if(isOperatorExsit(ldap, null, mobile, null)){
				// throw new Exception("登录电话重复");
				// }
				CommonOperator operator = new CommonOperator();
				String opid = IdCreater.getOperatorId();
				// 添加关系(APP需求)
				OperatorUnit operatorUnit = new OperatorUnit();
				operatorUnit.setOp_id(Long.valueOf(opid));
				operatorUnit.setVehicle_id(vehicle_id);
				operatorUnit.setUnit_id(unit_id);
				operatorUnitDao.save(operatorUnit);
				operator.setOpid(opid);
				operator.setLoginname(loginname);
				operator.setOpname(privateNetworkEntry.getCustomer_name());
				operator.setUserPassword(privateNetworkEntry.getServer_pwd());
				operator.setStatus("11");// 属于客户类型
				operator.setCustomerid(String.valueOf(cust_id));
				operator.setNumberplate(privateNetworkEntry.getNumber_plate());
				operator.setMobile(mobile);
				ldap.add(operator);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			map.put("success", false);
			map.put("msg", e.getMessage());
			return map;
		}
		map.put("success", true);
		map.put("msg", "入网成功");
		return map;
	}

	/**
	 * 验证数据是否在ldap中存在
	 * 
	 * @param loginName
	 * @param opId
	 * @return
	 */
	private boolean isOperatorExsit(String loginName, String mobile, String opId) throws SystemException {
		String filter = "(&(objectclass=commonOperator)";
		if (StringUtils.isNotNullOrEmpty(loginName)) {
			filter += "(loginname=" + loginName + ")";
		}
		if (StringUtils.isNotNullOrEmpty(mobile)) {
			filter += "(mobile=" + mobile + ")";
		}
		if (StringUtils.isNotNullOrEmpty(opId)) {
			filter += "(!(opid=" + opId + "))";
		}
		filter += ")";
		OpenLdap ldap = OpenLdapManager.getInstance();
		List<CommonOperator> opList = ldap.searchOperator("", filter);
		if (opList.size() > 0) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public HashMap updatePrivateCust(PrivateNetworkEntry privateNetworkEntry, String userId, String username, String companyid, String companycode,
			String companyname) {
		HashMap map = new HashMap<String, Object>();
		try {
			Long cust_id = privateNetworkEntry.getCust_id();
			// 修改
			// Ldap中用戶用户对象
			String bk_type = privateNetworkEntry.getBk_type();
			Backup backup = new Backup();
			Customerbk customerbk = null;
			Vehiclebk vehiclebk = null;
			Unitbk unitbk = null;
			Collectionbk collectionbk = null;
			String bkf_ids = "";
			// 是否转网
			boolean isTurn = false;
			Long subco_no = 0L;
			String subco_code = "";
			String subco_name = "";
			OpenLdap ldap = OpenLdapManager.getInstance();
			if (StringUtils.isNotBlank(bk_type) && bk_type.indexOf("10,") != -1) {
				isTurn = true;
				subco_no = privateNetworkEntry.getSubcoNo();
				if (subco_no == 2L || subco_no == 3L) {
					subco_no = 101L;
				}
				if (subco_no != 0L) {
					CommonCompany company = ldap.getCompanyById(String.valueOf(subco_no));
					if ("4".equals(company.getCompanylevel())) { // 市级公司下属机构显示分公司名称
						subco_no = Long.valueOf(company.getParentcompanyno());
						CommonCompany tmp = ldap.getCompanyById(String.valueOf(subco_no));
						subco_name = tmp.getCompanyname();
					} else {
						subco_name = company.getCompanyname();
					}
					subco_code = company.getCompanycode();
				}
			}
			// 客戶信息
			Customer customer = customerDao.getCustomer(privateNetworkEntry.getCust_id());
			if (StringUtils.isNotBlank(bk_type)) {
				// 如果是业务办理
				customerbk = backupDao.getCustomerbk(customer);
				//xxx
				Long bkc_id = backupDao.addCustomerbk(customerbk);
				backup.setBkc_id(bkc_id);
			}
			customer.setCustomer_name(privateNetworkEntry.getCustomer_name());
			/*
			 * //服务密码 //des加密，超过8位明文加密后成32为密文，不超过8位明文加密后为16位密文
			 * if(StringUtils.isNotBlank(privateNetworkEntry.getServer_pwd())){
			 * //新密码不为空 //原密文与新密码是否一致，不一致，则加密
			 * if(!org.apache.commons.lang.StringUtils
			 * .equals(customer.getService_pwd(),
			 * privateNetworkEntry.getServer_pwd())){
			 * customer.setService_pwd(DESPlus
			 * .getEncPWD(privateNetworkEntry.getServer_pwd())); } }else{
			 * //新密码置空，原密码不为空时
			 * if(!org.apache.commons.lang.StringUtils.equals(customer
			 * .getService_pwd(), privateNetworkEntry.getServer_pwd())){
			 * customer.setService_pwd(DESPlus.getEncPWD("")); //密码为null des加密出错
			 * } } //隐私密码
			 * if(StringUtils.isNotBlank(privateNetworkEntry.getPrivate_pwd())){
			 * //新密码不为空 //原密文与新密码是否一致，不一致，则加密
			 * if(!org.apache.commons.lang.StringUtils
			 * .equals(customer.getPrivate_pwd(),
			 * privateNetworkEntry.getPrivate_pwd())){
			 * customer.setPrivate_pwd(DESPlus
			 * .getEncPWD(privateNetworkEntry.getPrivate_pwd())); } }else{
			 * //新密码置空，原密码不为空时
			 * if(!org.apache.commons.lang.StringUtils.equals(customer
			 * .getPrivate_pwd(), privateNetworkEntry.getPrivate_pwd())){
			 * customer.setPrivate_pwd(DESPlus.getEncPWD("")); //密码为null des加密出错
			 * } }
			 */
			customer.setId_no(privateNetworkEntry.getIdcard());
			customer.setBirthday(DateUtil.parse(privateNetworkEntry.getBirthday(), DateUtil.YMD_DASH));
			customer.setSex(privateNetworkEntry.getSex());
			customer.setEmail(privateNetworkEntry.getEmail());
			customer.setVip(privateNetworkEntry.getVip());
			customer.setContract_no(privateNetworkEntry.getFileno());
			customer.setLocation(privateNetworkEntry.getLocation());
			customer.setRemark(privateNetworkEntry.getRemark());
			customer.setOp_id(Long.valueOf(userId));
			customer.setAddress(privateNetworkEntry.getAddress());
			if (isTurn) {
				customer.setSubco_no(subco_no);
				customer.setSubco_code(subco_code);
				customer.setSubco_name(subco_name);
			}
			//xxx
			//customerDao.update(customer);
			// 车辆资料
			Vehicle vehicle = vehicleDao.getVehicleByid(privateNetworkEntry.getVehicle_id());
			if (StringUtils.isNotBlank(bk_type)) {
				// 如果是业务办理
				vehiclebk = backupDao.getVehiclebk(vehicle);
				Long bkv_id = backupDao.addVehiclebk(vehiclebk);
				backup.setBkv_id(bkv_id);
			}
			Long oldSubco_no = vehicle.getSubco_no();
			CommonCompany old_company = ldap.getCompanyById(String.valueOf(oldSubco_no));
			vehicle.setPlate_no(privateNetworkEntry.getNumber_plate());
			vehicle.setPlate_color(privateNetworkEntry.getPlate_color());
			vehicle.setVehicle_type(privateNetworkEntry.getVehicle_type());
			vehicle.setModel(privateNetworkEntry.getCartype());
			vehicle.setModel_name(privateNetworkEntry.getCartype_name());
			vehicle.setVin(privateNetworkEntry.getCode());
			vehicle.setEngine_no(privateNetworkEntry.getEngine_no());
			vehicle.setChassis_no(privateNetworkEntry.getPlate_no());
			vehicle.setFactory(privateNetworkEntry.getFactory());
			vehicle.setColor(privateNetworkEntry.getColor());
			vehicle.setBuy_date(DateUtil.parse(privateNetworkEntry.getBuy_time(), DateUtil.YMD_DASH));
			vehicle.setProduction_date(DateUtil.parse(privateNetworkEntry.getProduction_date(), DateUtil.YMD_DASH));
			vehicle.setOp_id(Long.valueOf(userId));
			vehicle.setFlag(1);
			vehicle.setVehicle_license(privateNetworkEntry.getDriving_no());
			vehicle.setVl_bdate(DateUtil.parse(privateNetworkEntry.getGrant_time(), DateUtil.YMD_DASH));
			vehicle.setVl_edate(DateUtil.parse(privateNetworkEntry.getValid_time(), DateUtil.YMD_DASH));
			vehicle.setId_4s(privateNetworkEntry.getId_4s());
			vehicle.setInsurance_id(privateNetworkEntry.getInsurance_id());
			vehicle.setIs_bdate(DateUtil.parse(privateNetworkEntry.getIs_bdate(), DateUtil.YMD_DASH));
			vehicle.setIs_edate(DateUtil.parse(privateNetworkEntry.getIs_edate(), DateUtil.YMD_DASH));
			vehicle.setIs_corp(privateNetworkEntry.getIs_corp());
			vehicle.setIs_pilfer(privateNetworkEntry.getIs_pilfer());
			vehicle.setEquip_code(privateNetworkEntry.getEquip_code());
			vehicle.setVl_owner(privateNetworkEntry.getVl_owner());
			vehicle.setVl_type(privateNetworkEntry.getVl_type());
			vehicle.setVload(privateNetworkEntry.getVload());
			vehicle.setVl_use(privateNetworkEntry.getVl_use());
			vehicle.setVl_quality(privateNetworkEntry.getVl_quality());
			vehicle.setVl_qt_quality(privateNetworkEntry.getVl_qt_quality());
			vehicle.setVl_ap_quality(privateNetworkEntry.getVl_ap_quality());
			vehicle.setVl_vsize(privateNetworkEntry.getVl_vsize());
			vehicle.setVl_doc_no(privateNetworkEntry.getVl_doc_no());
			vehicle.setVl_remark(privateNetworkEntry.getVl_remark());
			/*
			 * //服务密码 //des加密，超过8位明文加密后成32为密文，不超过8位明文加密后为16位密文
			 * if(StringUtils.isNotBlank(privateNetworkEntry.getServer_pwd())){
			 * //新密码不为空 //原密文与新密码是否一致，不一致，则加密
			 * if(!org.apache.commons.lang.StringUtils
			 * .equals(vehicle.getService_pwd(),
			 * privateNetworkEntry.getServer_pwd())){
			 * vehicle.setService_pwd(DESPlus
			 * .getEncPWD(privateNetworkEntry.getServer_pwd())); } }else{
			 * //新密码置空，原密码不为空时
			 * if(!org.apache.commons.lang.StringUtils.equals(vehicle
			 * .getService_pwd(), privateNetworkEntry.getServer_pwd())){
			 * vehicle.setService_pwd(DESPlus.getEncPWD("")); //密码为null des加密出错
			 * } } //隐私密码
			 * if(StringUtils.isNotBlank(privateNetworkEntry.getPrivate_pwd())){
			 * //新密码不为空 //原密文与新密码是否一致，不一致，则加密
			 * if(!org.apache.commons.lang.StringUtils
			 * .equals(vehicle.getPrivate_pwd(),
			 * privateNetworkEntry.getPrivate_pwd())){
			 * vehicle.setPrivate_pwd(DESPlus
			 * .getEncPWD(privateNetworkEntry.getPrivate_pwd())); } }else{
			 * //新密码置空，原密码不为空时
			 * if(!org.apache.commons.lang.StringUtils.equals(vehicle
			 * .getPrivate_pwd(), privateNetworkEntry.getPrivate_pwd())){
			 * vehicle.setPrivate_pwd(DESPlus.getEncPWD("")); //密码为null des加密出错
			 * } }
			 */
			if (vehicleDao.is_repeat(vehicle)) {
				throw new Exception("修改车辆失败，车牌号码已存在!");
			}
			if (isTurn) {
				vehicle.setSubco_no(subco_no);
			}
			vehicleDao.update(vehicle);
			// 客户电话信息
			String mobile = "";
			List<Linkman> list = privateNetworkEntry.getCustphones();
			custphoneDao.deleteByCust_id(cust_id);
			for (Linkman phone : list) {
				if (phone.getPhone().equals("")) {
					continue;
				}
				if (phone.getAppsign() == 1) {
					mobile = phone.getPhone();
				}
				phone.setCustomer_id(cust_id);
				phone.setVehicle_id(vehicle.getVehicle_id());
				custphoneDao.save(phone);
			}
			// 车台资料
			Unit unit = unitDao.getUnitByid(privateNetworkEntry.getUnit_id());
			if (StringUtils.isNotBlank(bk_type)) {
				// 如果是业务办理
				unitbk = backupDao.getUnitbk(unit);
				Long bku_id = backupDao.addUnitbk(unitbk);
				backup.setBku_id(bku_id);
				// 更新升级时间
				if (bk_type.indexOf("4,") != -1) {
					unit.setUpgrade_date(new Date());
				}
			}
			unit.setUnittype_id(privateNetworkEntry.getUnittype_id());
			unit.setReg_status(privateNetworkEntry.getState());
			unit.setMode(privateNetworkEntry.getMode());
			unit.setCall_letter(privateNetworkEntry.getCall_letter());
			unit.setTelecom(privateNetworkEntry.getOperators());
			unit.setSim_type(privateNetworkEntry.getSimtype());
			unit.setBranch(privateNetworkEntry.getBranch());
			unit.setSales_id(privateNetworkEntry.getManager_id());
			unit.setSales(privateNetworkEntry.getManager_name());
			unit.setWorker(privateNetworkEntry.getWorker());
			unit.setArea(privateNetworkEntry.getUnitarea());
			unit.setTamper_box(privateNetworkEntry.getTamper_box());
			unit.setTamper_smart(privateNetworkEntry.getTamper_smart());
			unit.setTamper_wireless(privateNetworkEntry.getTamper_wireless());
			unit.setIs_sz(privateNetworkEntry.getIs_sz());
			unit.setImei(privateNetworkEntry.getImei());
			unit.setTamper_code(privateNetworkEntry.getTamper_code());
			unit.setOp_id(Long.valueOf(userId));
			unit.setSms_node(privateNetworkEntry.getNode());
			unit.setSpecial_no(privateNetworkEntry.getSpecial());
			unit.setFix_time(DateUtil.parse(privateNetworkEntry.getTime(), DateUtil.YMD_DASH));
			unit.setService_date(DateUtil.parse(privateNetworkEntry.getService_date(), DateUtil.YMD_DASH));
			unit.setProduct_name(privateNetworkEntry.getName());
			unit.setContract_no(privateNetworkEntry.getFileno());
			unit.setLocation(privateNetworkEntry.getLocation());
			if (StringUtils.isNotBlank(privateNetworkEntry.getLocation())) {
				unit.setArchive_time(new Date());
			}
			if (unitDao.is_repeat(unit)) {
				throw new Exception("修改车台失败，车载电话已存在!");
			}
			if (isTurn) {
				unit.setSubco_no(subco_no);
			}
			unitDao.update(unit);
			// 更新SIM卡信息
			Long sim_id = privateNetworkEntry.getSim_id();
			if (sim_id != null && sim_id != 0) {
				Preload preload = preloadDao.get(Preload.class, sim_id);
				preload.setUnittype_id(privateNetworkEntry.getUnittype_id());
				preload.setVin(privateNetworkEntry.getCode());
				preload.setEngine_no(privateNetworkEntry.getEngine_no());
				preload.setColor(privateNetworkEntry.getColor());
				preload.setProduction_date(DateUtil.parse(privateNetworkEntry.getProduction_date(), DateUtil.YMD_DASH));
				preload.setNew_combo_id(privateNetworkEntry.getCombo_id());
				preload.setPack_id(privateNetworkEntry.getPack_id());
				preload.setOp_id(Long.valueOf(userId));
				preloadDao.save(preload);
			}
			// 中间客户
			if (privateNetworkEntry.getGuarantee_id() != null) {
				MidCust midCust = midCustDao.getMidCustByUnitid(privateNetworkEntry.getUnit_id());
				if (midCust == null) {
					midCust = new MidCust();
					if (isTurn) {
						midCust.setSubco_no(subco_no);
					} else {
						midCust.setSubco_no(Long.valueOf(companyid));
					}
					midCust.setCust_type(2);
					midCust.setCustomer_id(privateNetworkEntry.getGuarantee_id());
					midCust.setUnit_id(privateNetworkEntry.getUnit_id());
					midCustDao.save(midCust);
				} else {
					midCust.setCustomer_id(privateNetworkEntry.getGuarantee_id());
					if (isTurn) {
						midCust.setSubco_no(subco_no);
					}
					midCustDao.update(midCust);
				}
			} else {
				midCustDao.deleteMidCust(privateNetworkEntry.getUnit_id());
			}
			// 条形码
			List<Barcode> barList = privateNetworkEntry.getBarcodes();
			barcodeDao.deleteByUnit_id(privateNetworkEntry.getUnit_id());
			for (Barcode barcode : barList) {
				if (barcode.getContent().equals("")) {
					continue;
				}
				barcode.setUnit_id(privateNetworkEntry.getUnit_id());
				barcodeDao.save(barcode);
			}
			// 计费信息
			List<FeeInfo> feeInfos = feeInfoDao.getListByid(privateNetworkEntry.getUnit_id());
			FeeInfo serviceInfo = privateNetworkEntry.getServiceInfo();
			FeeInfo simfeeInfo = privateNetworkEntry.getSimfeeInfo();
			FeeInfo insuranceInfo = privateNetworkEntry.getInsuranceInfo();
			FeeInfo webgisInfo = privateNetworkEntry.getWebgisInfo();
			// 查询正在托收计费的客户id列表
			List<Long> lockcustids = datalockDao.getLockCustomer();
			if (lockcustids.contains(cust_id)) {
				for (FeeInfo feeInfo : feeInfos) {
					if (feeInfo.getFeetype_id() == 1 && serviceInfo != null) {
						if (Float.floatToIntBits(feeInfo.getMonth_fee()) != Float.floatToIntBits(serviceInfo.getMonth_fee())
								|| feeInfo.getFee_cycle() != serviceInfo.getFee_cycle() || !feeInfo.getItems_id().equals(serviceInfo.getItems_id())) {
							throw new Exception("操作失败，该客户正在进行托收计费不能修改服务费!");
						}
					} else if (feeInfo.getFeetype_id() == 2 && simfeeInfo != null) {
						if (Float.floatToIntBits(feeInfo.getMonth_fee()) != Float.floatToIntBits(simfeeInfo.getMonth_fee())
								|| feeInfo.getFee_cycle() != simfeeInfo.getFee_cycle()) {
							throw new Exception("操作失败，该客户正在进行托收计费不能修改SIM卡费!");
						}
					} else if (feeInfo.getFeetype_id() == 3 && insuranceInfo != null) {
						if (Float.floatToIntBits(feeInfo.getMonth_fee()) != Float.floatToIntBits(insuranceInfo.getMonth_fee())
								|| feeInfo.getFee_cycle() != insuranceInfo.getFee_cycle()) {
							throw new Exception("操作失败，该客户正在进行托收计费不能修改盗抢险费!");
						}
					} else if (feeInfo.getFeetype_id() == 4 && webgisInfo != null) {
						if (Float.floatToIntBits(feeInfo.getMonth_fee()) != Float.floatToIntBits(webgisInfo.getMonth_fee())
								|| feeInfo.getFee_cycle() != webgisInfo.getFee_cycle()) {
							throw new Exception("操作失败，该客户正在进行托收计费不能修改网上查车费!");
						}
					}
				}
			}
			// 托收資料
			Long collection_id = 0L;
			if (privateNetworkEntry.getCollection_id() != null) {
				Collection collection = collectionDao.get(Collection.class, privateNetworkEntry.getCollection_id());
				if (lockcustids.contains(cust_id)) {
					if (!((collection.getAc_no() == null ? "" : collection.getAc_no()).equals(privateNetworkEntry.getAccount())
							&& (collection.getAc_name() == null ? "" : collection.getAc_name()).equals(privateNetworkEntry.getCollection_name())
							&& (collection.getAgency() == null ? "" : collection.getAgency()).equals(privateNetworkEntry.getAgency())
							&& (collection.getBank() == null ? "" : collection.getBank()).equals(privateNetworkEntry.getBank())
							&& (collection.getBank_code() == null ? "" : collection.getBank_code()).equals(privateNetworkEntry.getBankcode())
							&& (collection.getPost_code() == null ? "" : collection.getPost_code()).equals(privateNetworkEntry.getPost_code())
							&& (collection.getPay_ct_no() == null ? "" : collection.getPay_ct_no()).equals(privateNetworkEntry.getPay_ct_no())
							&& (collection.getAc_type() == null ? "" : collection.getAc_type()).equals(privateNetworkEntry.getType())
							&& (collection.getIs_delivery() == null ? "" : collection.getIs_delivery()).equals(privateNetworkEntry.getIs_delivery())
							&& (collection.getTel() == null ? "" : collection.getTel()).equals(privateNetworkEntry.getTel())
							&& (collection.getAddressee() == null ? "" : collection.getAddressee()).equals(privateNetworkEntry.getAddressee())
							&& (collection.getProvince() == null ? "" : collection.getProvince()).equals(privateNetworkEntry.getProvince())
							&& (collection.getCity() == null ? "" : collection.getCity()).equals(privateNetworkEntry.getCity())
							&& (collection.getArea() == null ? "" : collection.getArea()).equals(privateNetworkEntry.getArea())
							&& (collection.getAddress() == null ? "" : collection.getAddress()).equals(privateNetworkEntry.getAddress()) && (collection
								.getAc_id_no() == null ? "" : collection.getAc_id_no()).equals(privateNetworkEntry.getAc_id_no()))) {
						throw new Exception("操作失败，该客户正在进行托收计费不能修改托收资料!");
					}
				}

				if (StringUtils.isNotBlank(bk_type)) {
					collectionbk = backupDao.getCollectionbk(collection);
					Long bkfc_id = backupDao.addCollectionbk(collectionbk);
					backup.setBkfc_id(bkfc_id);
				}
				collection.setAc_no(privateNetworkEntry.getAccount());
				collection.setAc_name(privateNetworkEntry.getCollection_name());
				collection.setAgency(privateNetworkEntry.getAgency());
				collection.setBank(privateNetworkEntry.getBank());
				collection.setBank_code(privateNetworkEntry.getBankcode());
				collection.setAc_type(privateNetworkEntry.getType());
				collection.setIs_delivery(privateNetworkEntry.getIs_delivery());
				collection.setAddressee(privateNetworkEntry.getAddressee());
				collection.setPost_code(privateNetworkEntry.getPost_code());
				collection.setPay_ct_no(privateNetworkEntry.getPay_ct_no());
				collection.setProvince(privateNetworkEntry.getProvince());
				collection.setCity(privateNetworkEntry.getCity());
				collection.setArea(privateNetworkEntry.getArea());
				collection.setAddress(privateNetworkEntry.getAddress());
				collection.setOp_id(Long.valueOf(userId));
				collection.setAc_id_no(privateNetworkEntry.getAc_id_no());
				collection.setTel(privateNetworkEntry.getTel());
				collection.setTax_payer_seq(privateNetworkEntry.getTaxPayerId());
				if (isTurn) {
					collection.setSubco_no(subco_no);
				}
				collectionDao.update(collection);
				collection_id = privateNetworkEntry.getCollection_id();
			} else if (StringUtils.isNotBlank(privateNetworkEntry.getAccount()) && StringUtils.isNotBlank(privateNetworkEntry.getCollection_name())) {
				Collection collection = collectionDao.getCollectionByCustId(cust_id);
				if (collection == null) {
					collection = new Collection();
					collection.setCustomer_id(cust_id);
					if (isTurn) {
						collection.setSubco_no(subco_no);
					} else {
						collection.setSubco_no(Long.valueOf(companyid));
					}
					collection.setAc_no(privateNetworkEntry.getAccount());
					collection.setAc_name(privateNetworkEntry.getCollection_name());
					collection.setAgency(privateNetworkEntry.getAgency());
					collection.setBank(privateNetworkEntry.getBank());
					collection.setAc_type(privateNetworkEntry.getType());
					collection.setIs_delivery(privateNetworkEntry.getIs_delivery());
					collection.setAddressee(privateNetworkEntry.getAddressee());
					collection.setPost_code(privateNetworkEntry.getPost_code());
					collection.setTel(privateNetworkEntry.getTel());
					collection.setProvince(privateNetworkEntry.getProvince());
					collection.setCity(privateNetworkEntry.getCity());
					collection.setArea(privateNetworkEntry.getArea());
					collection.setAddress(privateNetworkEntry.getAddress());
					collection.setOp_id(Long.valueOf(userId));
					collection.setPay_ct_no(privateNetworkEntry.getPay_ct_no());
					collection.setAc_id_no("");
					collection.setBank_code(privateNetworkEntry.getBankcode());
					collection.setFee_cycle(1);
					collection.setPrint_freq(1);
					collection.setCreate_date(new Date());
					collection.setAc_id_no(privateNetworkEntry.getAc_id_no());
					collection_id = collectionDao.addCollection(collection);
				}
			}
			Map edateMap = new HashMap<Integer, Object>();
			for (FeeInfo feeInfo : feeInfos) {
				FeeInfobk fbk = backupDao.getFeeInfobk(feeInfo);
				Long fbk_id = backupDao.addFeeInfobk(fbk);
				bkf_ids += fbk_id + ",";
				// 如果计费信息里的开始时间和截止时间不同则证明缴费过了,写入map供后面调用
				if (feeInfo.getFee_date().getTime() != feeInfo.getFee_sedate().getTime()) {
					edateMap.put(feeInfo.getFeetype_id(), feeInfo.getFee_sedate());
				}
			}
			if (StringUtils.isNotBlank(bk_type)) {
				backup.setBkf_ids(bkf_ids);
			}
			// 先删除之前的计费信息
			feeInfoDao.deleteByUnitid(privateNetworkEntry.getUnit_id());
			// 托收合同号
			String pay_ct_no = privateNetworkEntry.getPay_ct_no();
			if (serviceInfo.getMonth_fee() != null) {
				if (isTurn) {
					serviceInfo.setSubco_no(subco_no);
				} else {
					serviceInfo.setSubco_no(Long.valueOf(companyid));
				}
				serviceInfo.setCustomer_id(cust_id);
				serviceInfo.setVehicle_id(vehicle.getVehicle_id());
				serviceInfo.setUnit_id(unit.getUnit_id());
				serviceInfo.setFeetype_id(1);
				serviceInfo.setItem_id(0L);
				serviceInfo.setPay_ct_no(pay_ct_no);
				serviceInfo.setCollection_id(collection_id);
				Date edate = (Date) edateMap.get(1);
				if (serviceInfo.getFee_date() == null) {
					serviceInfo.setFee_date(new Date());
					if (edate == null) {
						edate = new Date();
					}
				} else {
					if (edate == null) {
						edate = serviceInfo.getFee_date();
					}
				}
				serviceInfo.setFee_sedate(edate);
				if (serviceInfo.getFee_cycle() == null) {
					serviceInfo.setFee_cycle(1);
				}
				serviceInfo.setAc_amount(serviceInfo.getMonth_fee() * serviceInfo.getFee_cycle());
				serviceInfo.setReal_amount(serviceInfo.getMonth_fee() * serviceInfo.getFee_cycle());
				serviceInfo.setOp_id(Long.valueOf(userId));
				feeInfoDao.save(serviceInfo);
			}
			// SIM卡月租费
			if (simfeeInfo.getMonth_fee() != null) {
				if (isTurn) {
					simfeeInfo.setSubco_no(subco_no);
				} else {
					simfeeInfo.setSubco_no(Long.valueOf(companyid));
				}
				simfeeInfo.setCustomer_id(cust_id);
				simfeeInfo.setVehicle_id(vehicle.getVehicle_id());
				simfeeInfo.setUnit_id(unit.getUnit_id());
				simfeeInfo.setFeetype_id(2);
				simfeeInfo.setItem_id(0L);
				simfeeInfo.setPay_ct_no(pay_ct_no);
				simfeeInfo.setCollection_id(collection_id);
				Date edate = (Date) edateMap.get(2);
				if (simfeeInfo.getFee_date() == null) {
					simfeeInfo.setFee_date(new Date());
					if (edate == null) {
						edate = new Date();
					}
				} else {
					if (edate == null) {
						edate = simfeeInfo.getFee_date();
					}
				}
				simfeeInfo.setFee_sedate(edate);
				if (simfeeInfo.getFee_cycle() == null) {
					simfeeInfo.setFee_cycle(1);
				}
				simfeeInfo.setAc_amount(simfeeInfo.getMonth_fee() * simfeeInfo.getFee_cycle());
				simfeeInfo.setReal_amount(simfeeInfo.getMonth_fee() * simfeeInfo.getFee_cycle());
				simfeeInfo.setOp_id(Long.valueOf(userId));
				feeInfoDao.save(simfeeInfo);
			}
			// 盗抢险
			if (insuranceInfo.getMonth_fee() != null) {
				if (isTurn) {
					insuranceInfo.setSubco_no(subco_no);
				} else {
					insuranceInfo.setSubco_no(Long.valueOf(companyid));
				}
				insuranceInfo.setCustomer_id(cust_id);
				insuranceInfo.setVehicle_id(vehicle.getVehicle_id());
				insuranceInfo.setUnit_id(unit.getUnit_id());
				insuranceInfo.setFeetype_id(3);
				insuranceInfo.setItem_id(0L);
				insuranceInfo.setPay_ct_no(pay_ct_no);
				insuranceInfo.setCollection_id(collection_id);
				Date edate = (Date) edateMap.get(3);
				if (insuranceInfo.getFee_date() == null) {
					insuranceInfo.setFee_date(new Date());
					if (edate == null) {
						edate = new Date();
					}
				} else {
					if (edate == null) {
						edate = insuranceInfo.getFee_date();
					}
				}
				insuranceInfo.setFee_sedate(edate);
				if (insuranceInfo.getFee_cycle() == null) {
					insuranceInfo.setFee_cycle(12);
				}
				insuranceInfo.setAc_amount(insuranceInfo.getMonth_fee() * insuranceInfo.getFee_cycle());
				insuranceInfo.setReal_amount(insuranceInfo.getMonth_fee() * insuranceInfo.getFee_cycle());
				insuranceInfo.setOp_id(Long.valueOf(userId));
				feeInfoDao.save(insuranceInfo);
			}
			// 网上查车费
			if (webgisInfo.getMonth_fee() != null) {
				if (isTurn) {
					webgisInfo.setSubco_no(subco_no);
				} else {
					webgisInfo.setSubco_no(Long.valueOf(companyid));
				}
				webgisInfo.setCustomer_id(cust_id);
				webgisInfo.setVehicle_id(vehicle.getVehicle_id());
				webgisInfo.setUnit_id(unit.getUnit_id());
				webgisInfo.setFeetype_id(4);
				webgisInfo.setItem_id(0L);
				webgisInfo.setPay_ct_no(pay_ct_no);
				webgisInfo.setCollection_id(collection_id);
				Date edate = (Date) edateMap.get(4);
				if (webgisInfo.getFee_date() == null) {
					webgisInfo.setFee_date(new Date());
					if (edate == null) {
						edate = new Date();
					}
				} else {
					if (edate == null) {
						edate = webgisInfo.getFee_date();
					}
				}
				webgisInfo.setFee_sedate(edate);
				if (webgisInfo.getFee_cycle() == null) {
					webgisInfo.setFee_cycle(1);
				}
				webgisInfo.setAc_amount(webgisInfo.getMonth_fee() * webgisInfo.getFee_cycle());
				webgisInfo.setReal_amount(webgisInfo.getMonth_fee() * webgisInfo.getFee_cycle());
				webgisInfo.setOp_id(Long.valueOf(userId));
				feeInfoDao.save(webgisInfo);
			}
			// 前装服务费
			FeeInfo preloadInfo = privateNetworkEntry.getPreloadInfo();
			if (preloadInfo.getMonth_fee() != null) {
				preloadInfo.setSubco_no(Long.valueOf(companyid));
				preloadInfo.setCustomer_id(cust_id);
				preloadInfo.setVehicle_id(vehicle.getVehicle_id());
				preloadInfo.setUnit_id(unit.getUnit_id());
				preloadInfo.setFeetype_id(101);
				preloadInfo.setItem_id(privateNetworkEntry.getPrePack_id());
				preloadInfo.setPay_ct_no("");
				preloadInfo.setCollection_id(0L);
				if (preloadInfo.getFee_date() == null) {
					preloadInfo.setFee_date(new Date());
					preloadInfo.setFee_sedate(new Date());
				} else {
					preloadInfo.setFee_sedate(preloadInfo.getFee_date());
				}
				if (preloadInfo.getFee_cycle() == null) {
					preloadInfo.setFee_cycle(1);
				}
				preloadInfo.setAc_amount(preloadInfo.getMonth_fee() * preloadInfo.getFee_cycle());
				preloadInfo.setReal_amount(preloadInfo.getMonth_fee() * preloadInfo.getFee_cycle());
				preloadInfo.setOp_id(Long.valueOf(userId));
				feeInfoDao.save(preloadInfo);
			}
			// 司机资料
			driverDao.deleteByVehicleid(vehicle.getVehicle_id());
			List<Driver> drivers = privateNetworkEntry.getDrivers();
			for (Driver driver : drivers) {
				if (StringUtils.isBlank(driver.getDriver_name())) {
					continue;
				}
				driver.setSubco_no(Long.valueOf(companyid));
				driver.setCustomer_id(cust_id);
				driver.setIdtype(1);
				driver.setVehicle_id(vehicle.getVehicle_id());
				driver.setOp_id(Long.valueOf(userId));
				driverDao.save(driver);
			}
			if (StringUtils.isNotBlank(bk_type)) {
				backup.setUnit_id(privateNetworkEntry.getUnit_id());
				backup.setVehicle_id(privateNetworkEntry.getVehicle_id());
				backup.setBk_type(bk_type);
				backup.setOp_id(Long.valueOf(userId));
				backup.setOp_name(username);
				if (isTurn) {
					String oldName = "";
					if (old_company != null) {
						oldName = old_company.getCompanyname();
					}
					backup.setRemark(oldName + "转网到" + subco_name + "." + privateNetworkEntry.getBk_remark());
				} else {
					backup.setRemark(privateNetworkEntry.getBk_remark());
				}
				backupDao.addBackup(backup);
			}
			String loginname = privateNetworkEntry.getLoginname();
			if (StringUtils.isBlank(loginname)) {
				loginname = privateNetworkEntry.getNumber_plate();
			}
			CommonOperator operator = ldap.getOperatorBycustId(String.valueOf(cust_id));
			String dn = "";
			if (operator != null) {
				// if(isOperatorExsit(ldap, loginname, null,
				// operator.getOpid())){
				// throw new Exception("登录名重复");
				// }
				// if(isOperatorExsit(ldap, null, mobile, operator.getOpid())){
				// throw new Exception("登录电话重复");
				// }
				dn = operator.getDn();
				String[] keys = new String[5];
				Object[] values = new Object[5];
				if (StringUtils.isBlank(operator.getUserPassword())) {
					keys = new String[4];
					values = new Object[4];
				}
				keys[0] = "loginname";
				keys[1] = "opname";
				keys[2] = "numberplate";
				keys[3] = "mobile";
				values[0] = loginname;
				values[1] = privateNetworkEntry.getCustomer_name();
				values[2] = privateNetworkEntry.getNumber_plate();
				values[3] = mobile;
				if (StringUtils.isNotBlank(operator.getUserPassword())) {
					keys[4] = "userpassword";
					values[4] = operator.getUserPassword();
				}
				ldap.modifyInformations(dn, keys, values);
			}

			// 过户处理
			if (StringUtils.isNotBlank(bk_type) && bk_type.indexOf("2,") != -1) {
				if (StringUtils.isNotBlank(dn)) {
					ldap.delete(dn); // 删除ldap中原有的用户数据
				}
				Integer ts_type = privateNetworkEntry.getTs_type();
				Long custid_ts = 0l;
				if (ts_type == 0) {// 私家车之间过户处理，在界面上客户是新添加的，如果是车辆原来收费是托收，而过户后的客户不是同一个人，需要新加托收资料
					Customer customer_ts = privateNetworkEntry.getCustomer_ts();
					String service_pwd_ts = customer_ts.getService_pwd();
					String private_pwd_ts = customer_ts.getPrivate_pwd();
					DESPlus m = new DESPlus("seg12345");
					String vpwd = vehicle.getService_pwd() == null ? "" : vehicle.getService_pwd();
					String vpwd2 = vehicle.getPrivate_pwd() == null ? "" : vehicle.getPrivate_pwd();
					if (StringUtils.isBlank(service_pwd_ts)) {
						service_pwd_ts = m.Decode(vpwd).toString().trim();
					} else if (service_pwd_ts.equals(vpwd)) {
						service_pwd_ts = m.Decode(vpwd).toString().trim();
					}
					if (StringUtils.isBlank(private_pwd_ts)) {
						private_pwd_ts = m.Decode(vpwd2).toString().trim();
					} else if (private_pwd_ts.equals(vpwd2)) {
						private_pwd_ts = m.Decode(vpwd2).toString().trim();
					}
					customer_ts.setSubco_no(Long.valueOf(companyid));
					customer_ts.setSubco_code(companycode);
					customer_ts.setSubco_name(companyname);
					customer_ts.setCustco_no(0L);
					customer_ts.setCustco_code("0");
					customer_ts.setOp_id(Long.valueOf(userId));
					if (StringUtils.isNotBlank(service_pwd_ts)) {
						customer_ts.setService_pwd(DESPlus.getEncPWD(service_pwd_ts));
						vehicle.setService_pwd(DESPlus.getEncPWD(service_pwd_ts));
					} else {
						customer_ts.setService_pwd(DESPlus.getEncPWD("123456"));
						vehicle.setService_pwd(DESPlus.getEncPWD("123456"));
					}
					// 设置隐私密码，隐私密码有为空的情况
					if (StringUtils.isNotBlank(private_pwd_ts)) {
						customer_ts.setPrivate_pwd(DESPlus.getEncPWD(private_pwd_ts));
						vehicle.setPrivate_pwd(DESPlus.getEncPWD(private_pwd_ts));
					}
					custid_ts = customerDao.add(customer_ts);
					vehicleDao.update(vehicle);
					CustVehicle custVehicle = custVehicleDao.getByVehicleid(vehicle.getVehicle_id()).get(0);
					custVehicle.setCustomer_id(custid_ts);
					custVehicleDao.update(custVehicle);
					unit.setCustomer_id(custid_ts);
					unitDao.update(unit);
					if (privateNetworkEntry.getCollection_id() != null) { // 先保存托收资料，再更新
						Collection collection = collectionDao.get(Collection.class, privateNetworkEntry.getCollection_id());
						Collection newCollection = new Collection();
						newCollection.setCustomer_id(custid_ts);
						newCollection.setSubco_no(collection.getSubco_no());
						newCollection.setAc_no(collection.getAc_no());
						newCollection.setAc_name(collection.getAc_name());
						newCollection.setAgency(collection.getAgency());
						newCollection.setBank(collection.getBank());
						newCollection.setAc_type(collection.getAc_type());
						newCollection.setIs_delivery(collection.getIs_delivery());
						newCollection.setAddressee(collection.getAddressee());
						newCollection.setPost_code(collection.getPost_code());
						newCollection.setTel(collection.getTel());
						newCollection.setProvince(collection.getProvince());
						newCollection.setCity(collection.getCity());
						newCollection.setArea(collection.getArea());
						newCollection.setAddress(collection.getAddress());
						newCollection.setOp_id(Long.valueOf(userId));
						newCollection.setPay_ct_no(maxidService.getPayCtNo(subco_no));
						newCollection.setAc_id_no(collection.getAc_id_no());
						newCollection.setBank_code(collection.getBank_code());
						newCollection.setFee_cycle(collection.getFee_cycle());
						newCollection.setPrint_freq(collection.getPrint_freq());
						newCollection.setCreate_date(new Date());
						newCollection.setAc_id_no(collection.getAc_id_no());
						collectionDao.addCollection(newCollection);
					}
					feeInfoDao.updateFeeInfo(custid_ts, cust_id);
					List<Linkman> custphones_ts = privateNetworkEntry.getCustphones_ts();
					if (custphones_ts == null || custphones_ts.size() == 0
							|| (custphones_ts.size() == 1 && custphones_ts.get(0).getPhone().equals(""))) {
						custphones_ts = custphoneDao.getLinkmanList(cust_id);
					}
					for (Linkman phone : custphones_ts) {
						if (phone.getPhone().equals("")) {
							continue;
						}
						if (phone.getAppsign() == 1) {
							mobile = phone.getPhone();
						}
						phone.setCustomer_id(custid_ts);
						phone.setVehicle_id(vehicle.getVehicle_id());
						custphoneDao.save(phone);
					}
					CommonOperator operator_ts = new CommonOperator();
					String opid = IdCreater.getOperatorId();
					// 更新关系(APP需求) 过户给私家车客户，改客户可以通过任何归属于客户的车查询app，可以切换车辆，类似于集团客户
					OperatorUnit operatorUnit = new OperatorUnit();
					operatorUnit.setOp_id(Long.valueOf(opid));
					operatorUnit.setUnit_id(privateNetworkEntry.getUnit_id());
					operatorUnitDao.updateOperatorUnit(operatorUnit);
					operator_ts.setOpid(opid);
					operator_ts.setLoginname(loginname);
					operator_ts.setOpname(customer_ts.getCustomer_name());
					if (StringUtils.isNotBlank(service_pwd_ts)) {
						operator_ts.setUserPassword(service_pwd_ts);
					} else {
						operator_ts.setUserPassword("123456");
					}
					operator_ts.setStatus("10");// 属于客户类型
					operator_ts.setCustomerid(String.valueOf(custid_ts));
					operator_ts.setNumberplate(vehicle.getPlate_no());
					operator_ts.setMobile(mobile);
					ldap.add(operator_ts);
				} else { // 过户给集客，直接将计费托收信息更新为集客客户计费资料信息
					custid_ts = privateNetworkEntry.getPcustid();
					CommonOperator operator_ts = ldap.getOperatorBycustId(String.valueOf(custid_ts));
					// 更新关系(APP需求)
					OperatorUnit operatorUnit = new OperatorUnit();
					operatorUnit.setOp_id(Long.valueOf(operator_ts.getOpid()));
					operatorUnit.setUnit_id(privateNetworkEntry.getUnit_id());
					operatorUnitDao.updateOperatorUnit(operatorUnit);
					CustVehicle custVehicle = custVehicleDao.getByVehicleid(vehicle.getVehicle_id()).get(0);
					custVehicle.setCustomer_id(custid_ts);
					custVehicleDao.update(custVehicle);
					unit.setCustomer_id(custid_ts);
					unitDao.update(unit);
					feeInfoDao.updateFeeInfo(custid_ts, cust_id);
					List<Linkman> custphones_ts = custphoneDao.getLinkmanList(cust_id);
					for (Linkman phone : custphones_ts) {
						if (phone.getPhone().equals("")) {
							continue;
						}
						Driver driver = new Driver();
						driver.setSubco_no(subco_no);
						driver.setCustomer_id(custid_ts);
						driver.setVehicle_id(privateNetworkEntry.getVehicle_id());
						driver.setDriver_name(phone.getLinkman());
						driver.setDriver_code(String.valueOf(phone.getLinkman_type()));
						driver.setPhone(phone.getPhone());
						driver.setIdtype(1);
						driver.setOp_id(Long.valueOf(userId));
						driverDao.save(driver);
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			map.put("success", false);
			map.put("msg", e.getMessage());
			return map;
		}
		map.put("success", true);
		map.put("msg", "操作成功");
		return map;
	}

	@Override
	public HashMap addLargecust(LargeCustNetworkEntry largeCustNetworkEntry, String userId, String companyid, String companycode, String companyname) {
		try {
			// 新增
			// 车辆资料
			Vehicle vehicle = new Vehicle();
			vehicle.setSubco_no(Long.valueOf(companyid));
			vehicle.setPlate_no(largeCustNetworkEntry.getNumber_plate());
			vehicle.setPlate_color(largeCustNetworkEntry.getPlate_color());
			vehicle.setVehicle_type(largeCustNetworkEntry.getVehicle_type());
			vehicle.setEngine_no(largeCustNetworkEntry.getEngine_no());
			vehicle.setVin(largeCustNetworkEntry.getCode());
			vehicle.setChassis_no(largeCustNetworkEntry.getPlate_no());
			vehicle.setBrand(largeCustNetworkEntry.getBrand());
			vehicle.setSeries(largeCustNetworkEntry.getSeries());
			vehicle.setModel(largeCustNetworkEntry.getCartype());
			vehicle.setModel_name(largeCustNetworkEntry.getCartype_name());
			vehicle.setColor(largeCustNetworkEntry.getColor());
			vehicle.setDef_no(largeCustNetworkEntry.getSince_no());
			vehicle.setBuy_date(DateUtil.parse(largeCustNetworkEntry.getBuy_time(), DateUtil.YMD_DASH));
			vehicle.setProduction_date(DateUtil.parse(largeCustNetworkEntry.getProduction_date(), DateUtil.YMD_DASH));
			vehicle.setBuy_money(largeCustNetworkEntry.getMoney());
			vehicle.setFactory(largeCustNetworkEntry.getFactory());
			vehicle.setRegister_date(DateUtil.parse(largeCustNetworkEntry.getRegister_time(), DateUtil.YMD_DASH));
			vehicle.setVehicle_license(largeCustNetworkEntry.getDriving_no());
			vehicle.setVl_bdate(DateUtil.parse(largeCustNetworkEntry.getGrant_time(), DateUtil.YMD_DASH));
			vehicle.setVl_edate(DateUtil.parse(largeCustNetworkEntry.getValid_time(), DateUtil.YMD_DASH));
			vehicle.setOp_id(Long.valueOf(userId));
			vehicle.setVehicle_status(0);
			vehicle.setFlag(1);
			vehicle.setEquip_code(largeCustNetworkEntry.getEquip_code());
			vehicle.setVl_owner(largeCustNetworkEntry.getVl_owner());
			vehicle.setVl_type(largeCustNetworkEntry.getVl_type());
			vehicle.setVload(largeCustNetworkEntry.getVload());
			vehicle.setVl_use(largeCustNetworkEntry.getVl_use());
			vehicle.setVl_quality(largeCustNetworkEntry.getVl_quality());
			vehicle.setVl_qt_quality(largeCustNetworkEntry.getVl_qt_quality());
			vehicle.setVl_ap_quality(largeCustNetworkEntry.getVl_ap_quality());
			vehicle.setVl_vsize(largeCustNetworkEntry.getVl_vsize());
			vehicle.setVl_doc_no(largeCustNetworkEntry.getVl_doc_no());
			vehicle.setVl_remark(largeCustNetworkEntry.getVl_remark());
			vehicle.setId_4s(0L);
			vehicle.setInsurance_id(0);
			if (StringUtils.isNotBlank(largeCustNetworkEntry.getServer_pwd())) {
				vehicle.setService_pwd(DESPlus.getEncPWD(largeCustNetworkEntry.getServer_pwd()));
			} else {
				vehicle.setService_pwd(DESPlus.getEncPWD("123456"));
			}
			if (StringUtils.isNotBlank(largeCustNetworkEntry.getPrivate_pwd())) {
				vehicle.setPrivate_pwd(DESPlus.getEncPWD(largeCustNetworkEntry.getPrivate_pwd()));
			}
			vehicle.setIs_bdate(DateUtil.parse(largeCustNetworkEntry.getIs_bdate(), DateUtil.YMD_DASH));
			vehicle.setIs_edate(DateUtil.parse(largeCustNetworkEntry.getIs_edate(), DateUtil.YMD_DASH));
			vehicle.setIs_corp(largeCustNetworkEntry.getIs_corp());
			vehicle.setIs_pilfer(largeCustNetworkEntry.getIs_pilfer());
			/*
			 * if(vehicleService.is_repeat(vehicle)){ vehicle_id =
			 * vehicleService
			 * .getIdByPlate(largeCustNetworkEntry.getNumber_plate()); flag =
			 * false; }else{ vehicle_id = vehicleService.add(vehicle); }
			 */
			if (vehicleDao.is_repeat(vehicle)) {
				throw new Exception("添加车辆失败，车牌号码已存在!");
			}
			Long vehicle_id = vehicleDao.add(vehicle);
			// 车辆客户关系
			CustVehicle custVehicle = new CustVehicle();
			custVehicle.setCustomer_id(largeCustNetworkEntry.getCust_id());
			custVehicle.setVehicle_id(vehicle_id);
			custVehicleDao.save(custVehicle);
			// 车台资料
			Unit unit = new Unit();
			unit.setSubco_no(Long.valueOf(companyid));
			unit.setCustomer_id(largeCustNetworkEntry.getCust_id());
			unit.setVehicle_id(vehicle_id);
			unit.setUnittype_id(largeCustNetworkEntry.getUnittype_id());
			unit.setProduct_name(largeCustNetworkEntry.getName());
			unit.setMode(largeCustNetworkEntry.getMode());
			unit.setCall_letter(largeCustNetworkEntry.getCall_letter());
			unit.setTelecom(largeCustNetworkEntry.getOperators());
			unit.setSim_type(largeCustNetworkEntry.getSimtype());
			unit.setBranch(largeCustNetworkEntry.getBranch());
			if (largeCustNetworkEntry.getManager_id() == null) {
				unit.setSales_id(0L);
			} else {
				unit.setSales_id(largeCustNetworkEntry.getManager_id());
			}
			unit.setSales(largeCustNetworkEntry.getManager_name());
			unit.setWorker(largeCustNetworkEntry.getWorker());
			unit.setCreate_date(new Date());
			unit.setArea(largeCustNetworkEntry.getArea());
			unit.setTamper_box(largeCustNetworkEntry.getTamper_box());
			unit.setTamper_smart(largeCustNetworkEntry.getTamper_smart());
			unit.setTamper_wireless(largeCustNetworkEntry.getTamper_wireless());
			unit.setIs_sz(largeCustNetworkEntry.getIs_sz());
			unit.setImei(largeCustNetworkEntry.getImei());
			unit.setTamper_code(largeCustNetworkEntry.getTamper_code());
			unit.setOp_id(Long.valueOf(userId));
			unit.setData_node(0);
			unit.setSms_node(largeCustNetworkEntry.getNode());
			unit.setSpecial_no(largeCustNetworkEntry.getSpecial());
			unit.setFix_time(DateUtil.parse(largeCustNetworkEntry.getTime(), DateUtil.YMD_DASH));
			unit.setService_date(new Date());
			unit.setContract_no(largeCustNetworkEntry.getFileno());
			unit.setLocation(largeCustNetworkEntry.getLocation());
			unit.setArchive_time(new Date());
			unit.setFlag(0);
			unit.setPay_model(0);
			unit.setReg_status(0);
			unit.setTrade(largeCustNetworkEntry.getIndustry());
			if (unitDao.is_repeat(unit)) {
				throw new Exception("添加车台失败，车载电话已存在!");
			}
			Long unit_id = unitDao.add(unit);
			// 条形码
			List<Barcode> barList = largeCustNetworkEntry.getBarcodes();
			for (Barcode barcode : barList) {
				if (barcode.getContent().equals("")) {
					continue;
				}
				barcode.setUnit_id(unit_id);
				barcodeDao.save(barcode);
			}
			// 获得托收合同号
			Long collection_id = largeCustNetworkEntry.getCollection_id();
			Collection collection = null;
			if (collection_id == null) {
				collection = collectionDao.getCollectionByCustId(largeCustNetworkEntry.getCust_id());
			} else {
				collection = collectionDao.get(Collection.class, collection_id);
			}
			String pay_ct_no = "";
			if (collection != null) {
				pay_ct_no = collection.getPay_ct_no();
				collection_id = collection.getCollection_id();
			}
			// 服务费
			FeeInfo serviceInfo = largeCustNetworkEntry.getServiceInfo();
			if (serviceInfo.getMonth_fee() != null) {
				serviceInfo.setSubco_no(Long.valueOf(companyid));
				serviceInfo.setCustomer_id(largeCustNetworkEntry.getCust_id());
				serviceInfo.setVehicle_id(vehicle_id);
				serviceInfo.setUnit_id(unit_id);
				serviceInfo.setFeetype_id(1);
				serviceInfo.setItem_id(0L);
				if (serviceInfo.getFee_date() == null) {
					serviceInfo.setFee_date(new Date());
					serviceInfo.setFee_sedate(new Date());
				} else {
					serviceInfo.setFee_sedate(serviceInfo.getFee_date());
				}
				if (serviceInfo.getFee_cycle() == null) {
					serviceInfo.setFee_cycle(1);
				}
				serviceInfo.setAc_amount(serviceInfo.getMonth_fee() * serviceInfo.getFee_cycle());
				serviceInfo.setReal_amount(serviceInfo.getMonth_fee() * serviceInfo.getFee_cycle());
				serviceInfo.setPay_ct_no(pay_ct_no);
				serviceInfo.setCollection_id(collection_id);
				serviceInfo.setOp_id(Long.valueOf(userId));
				feeInfoDao.save(serviceInfo);
			}
			// SIM卡月租费
			FeeInfo simfeeInfo = largeCustNetworkEntry.getSimfeeInfo();
			if (simfeeInfo.getMonth_fee() != null) {
				simfeeInfo.setSubco_no(Long.valueOf(companyid));
				simfeeInfo.setCustomer_id(largeCustNetworkEntry.getCust_id());
				simfeeInfo.setVehicle_id(vehicle_id);
				simfeeInfo.setUnit_id(unit_id);
				simfeeInfo.setFeetype_id(2);
				simfeeInfo.setItem_id(0L);
				if (simfeeInfo.getFee_date() == null) {
					simfeeInfo.setFee_date(new Date());
					simfeeInfo.setFee_sedate(new Date());
				} else {
					simfeeInfo.setFee_sedate(simfeeInfo.getFee_date());
				}
				if (simfeeInfo.getFee_cycle() == null) {
					simfeeInfo.setFee_cycle(1);
				}
				simfeeInfo.setAc_amount(simfeeInfo.getMonth_fee() * simfeeInfo.getFee_cycle());
				simfeeInfo.setReal_amount(simfeeInfo.getMonth_fee() * simfeeInfo.getFee_cycle());
				simfeeInfo.setPay_ct_no(pay_ct_no);
				simfeeInfo.setCollection_id(collection_id);
				simfeeInfo.setOp_id(Long.valueOf(userId));
				feeInfoDao.save(simfeeInfo);
			}
			// 网上查车费
			FeeInfo webgisInfo = largeCustNetworkEntry.getWebgisInfo();
			if (webgisInfo.getMonth_fee() != null) {
				webgisInfo.setSubco_no(Long.valueOf(companyid));
				webgisInfo.setCustomer_id(largeCustNetworkEntry.getCust_id());
				webgisInfo.setVehicle_id(vehicle_id);
				webgisInfo.setUnit_id(unit_id);
				webgisInfo.setFeetype_id(4);
				webgisInfo.setItem_id(0L);
				if (webgisInfo.getFee_date() == null) {
					webgisInfo.setFee_date(new Date());
					webgisInfo.setFee_sedate(new Date());
				} else {
					webgisInfo.setFee_sedate(webgisInfo.getFee_date());
				}
				if (webgisInfo.getFee_cycle() == null) {
					webgisInfo.setFee_cycle(1);
				}
				webgisInfo.setAc_amount(webgisInfo.getMonth_fee() * webgisInfo.getFee_cycle());
				webgisInfo.setReal_amount(webgisInfo.getMonth_fee() * webgisInfo.getFee_cycle());
				webgisInfo.setPay_ct_no(pay_ct_no);
				webgisInfo.setCollection_id(collection_id);
				webgisInfo.setOp_id(Long.valueOf(userId));
				feeInfoDao.save(webgisInfo);
			}
			// 盗抢险费
			FeeInfo insuranceInfo = largeCustNetworkEntry.getInsuranceInfo();
			if (insuranceInfo.getMonth_fee() != null) {
				insuranceInfo.setSubco_no(Long.valueOf(companyid));
				insuranceInfo.setCustomer_id(largeCustNetworkEntry.getCust_id());
				insuranceInfo.setVehicle_id(vehicle_id);
				insuranceInfo.setUnit_id(unit_id);
				insuranceInfo.setFeetype_id(3);
				insuranceInfo.setItem_id(0L);
				if (insuranceInfo.getFee_date() == null) {
					insuranceInfo.setFee_date(new Date());
					insuranceInfo.setFee_sedate(new Date());
				} else {
					insuranceInfo.setFee_sedate(insuranceInfo.getFee_date());
				}
				if (insuranceInfo.getFee_cycle() == null) {
					insuranceInfo.setFee_cycle(1);
				}
				insuranceInfo.setAc_amount(insuranceInfo.getMonth_fee() * insuranceInfo.getFee_cycle());
				insuranceInfo.setReal_amount(insuranceInfo.getMonth_fee() * insuranceInfo.getFee_cycle());
				insuranceInfo.setPay_ct_no(pay_ct_no);
				insuranceInfo.setCollection_id(collection_id);
				insuranceInfo.setOp_id(Long.valueOf(userId));
				feeInfoDao.save(insuranceInfo);
			}
			// 司机资料
			List<Driver> drivers = largeCustNetworkEntry.getDrivers();
			for (Driver driver : drivers) {
				if (StringUtils.isBlank(driver.getDriver_name())) {
					continue;
				}
				driver.setSubco_no(Long.valueOf(companyid));
				driver.setCustomer_id(largeCustNetworkEntry.getCust_id());
				driver.setIdtype(1);
				driver.setVehicle_id(vehicle_id);
				driver.setOp_id(Long.valueOf(userId));
				driverDao.save(driver);
			}
			// 添加关系(APP需求)
			OperatorUnit operatorUnit = new OperatorUnit();
			OpenLdap ldap = OpenLdapManager.getInstance();
			CommonOperator operator = ldap.getOperatorBycustId(String.valueOf(largeCustNetworkEntry.getCust_id()));
			operatorUnit.setOp_id(Long.valueOf(operator.getOpid()));
			operatorUnit.setVehicle_id(vehicle_id);
			operatorUnit.setUnit_id(unit_id);
			operatorUnitDao.save(operatorUnit);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		HashMap map = new HashMap<String, Object>();
		map.put("success", true);
		map.put("msg", "入网成功!");
		return map;
	}

	@Override
	public HashMap updateLargecust(LargeCustNetworkEntry largeCustNetworkEntry, String userId, String username, String companyid, String companycode,
			String companyname) {
		try {
			Long vehicle_id = largeCustNetworkEntry.getVehicle_id();
			// 修改
			// 车辆资料
			String bk_type = largeCustNetworkEntry.getBk_type();
			Backup backup = new Backup();
			Customerbk customerbk = null;
			Vehiclebk vehiclebk = null;
			Unitbk unitbk = null;
			String bkf_ids = "";
			// 客戶信息
			Customer customer = customerDao.getCustomer(largeCustNetworkEntry.getCust_id());
			if (StringUtils.isNotBlank(bk_type)) {
				// 如果是业务办理
				customerbk = backupDao.getCustomerbk(customer);
				Long bkc_id = backupDao.addCustomerbk(customerbk);
				backup.setBkc_id(bkc_id);
			}
			Vehicle vehicle = vehicleDao.getVehicleByid(largeCustNetworkEntry.getVehicle_id());
			if (StringUtils.isNotBlank(bk_type)) {
				// 如果是业务办理
				vehiclebk = backupDao.getVehiclebk(vehicle);
				Long bkv_id = backupDao.addVehiclebk(vehiclebk);
				backup.setBkv_id(bkv_id);
			}
			vehicle.setPlate_no(largeCustNetworkEntry.getNumber_plate());
			vehicle.setPlate_color(largeCustNetworkEntry.getPlate_color());
			vehicle.setVehicle_type(largeCustNetworkEntry.getVehicle_type());
			vehicle.setEngine_no(largeCustNetworkEntry.getEngine_no());
			vehicle.setVin(largeCustNetworkEntry.getCode());
			vehicle.setChassis_no(largeCustNetworkEntry.getPlate_no());
			vehicle.setBrand(largeCustNetworkEntry.getBrand());
			vehicle.setSeries(largeCustNetworkEntry.getSeries());
			vehicle.setModel(largeCustNetworkEntry.getCartype());
			vehicle.setModel_name(largeCustNetworkEntry.getCartype_name());
			vehicle.setColor(largeCustNetworkEntry.getColor());
			vehicle.setDef_no(largeCustNetworkEntry.getSince_no());
			vehicle.setBuy_date(DateUtil.parse(largeCustNetworkEntry.getBuy_time(), DateUtil.YMD_DASH));
			vehicle.setProduction_date(DateUtil.parse(largeCustNetworkEntry.getProduction_date(), DateUtil.YMD_DASH));
			vehicle.setBuy_money(largeCustNetworkEntry.getMoney());
			vehicle.setFactory(largeCustNetworkEntry.getFactory());
			vehicle.setRegister_date(DateUtil.parse(largeCustNetworkEntry.getRegister_time(), DateUtil.YMD_DASH));
			vehicle.setVehicle_license(largeCustNetworkEntry.getDriving_no());
			vehicle.setVl_bdate(DateUtil.parse(largeCustNetworkEntry.getGrant_time(), DateUtil.YMD_DASH));
			vehicle.setVl_edate(DateUtil.parse(largeCustNetworkEntry.getValid_time(), DateUtil.YMD_DASH));
			vehicle.setOp_id(Long.valueOf(userId));
			vehicle.setIs_bdate(DateUtil.parse(largeCustNetworkEntry.getIs_bdate(), DateUtil.YMD_DASH));
			vehicle.setIs_edate(DateUtil.parse(largeCustNetworkEntry.getIs_edate(), DateUtil.YMD_DASH));
			vehicle.setIs_corp(largeCustNetworkEntry.getIs_corp());
			vehicle.setIs_pilfer(largeCustNetworkEntry.getIs_pilfer());
			vehicle.setEquip_code(largeCustNetworkEntry.getEquip_code());
			vehicle.setVl_owner(largeCustNetworkEntry.getVl_owner());
			vehicle.setVl_type(largeCustNetworkEntry.getVl_type());
			vehicle.setVload(largeCustNetworkEntry.getVload());
			vehicle.setVl_use(largeCustNetworkEntry.getVl_use());
			vehicle.setVl_quality(largeCustNetworkEntry.getVl_quality());
			vehicle.setVl_qt_quality(largeCustNetworkEntry.getVl_qt_quality());
			vehicle.setVl_ap_quality(largeCustNetworkEntry.getVl_ap_quality());
			vehicle.setVl_vsize(largeCustNetworkEntry.getVl_vsize());
			vehicle.setVl_doc_no(largeCustNetworkEntry.getVl_doc_no());
			vehicle.setVl_remark(largeCustNetworkEntry.getVl_remark());

			/*
			 * //服务密码 //des加密，超过8位明文加密后成32为密文，不超过8位明文加密后为16位密文
			 * if(StringUtils.isNotBlank
			 * (largeCustNetworkEntry.getServer_pwd())){ //新密码不为空
			 * //原密文与新密码是否一致，不一致，则加密
			 * if(!org.apache.commons.lang.StringUtils.equals
			 * (vehicle.getService_pwd(),
			 * largeCustNetworkEntry.getServer_pwd())){
			 * vehicle.setService_pwd(DESPlus
			 * .getEncPWD(largeCustNetworkEntry.getServer_pwd())); } }else{
			 * //新密码置空，原密码不为空时
			 * if(!org.apache.commons.lang.StringUtils.equals(vehicle
			 * .getService_pwd(), largeCustNetworkEntry.getServer_pwd())){
			 * vehicle.setService_pwd(DESPlus.getEncPWD("")); //密码为null des加密出错
			 * } } //隐私密码
			 * if(StringUtils.isNotBlank(largeCustNetworkEntry.getPrivate_pwd
			 * ())){ //新密码不为空 //原密文与新密码是否一致，不一致，则加密
			 * if(!org.apache.commons.lang.StringUtils
			 * .equals(vehicle.getPrivate_pwd(),
			 * largeCustNetworkEntry.getPrivate_pwd())){
			 * vehicle.setPrivate_pwd(DESPlus
			 * .getEncPWD(largeCustNetworkEntry.getPrivate_pwd())); } }else{
			 * //新密码置空，原密码不为空时
			 * if(!org.apache.commons.lang.StringUtils.equals(vehicle
			 * .getPrivate_pwd(), largeCustNetworkEntry.getPrivate_pwd())){
			 * vehicle.setPrivate_pwd(DESPlus.getEncPWD("")); //密码为null des加密出错
			 * } }
			 */
			if (vehicleDao.is_repeat(vehicle)) {
				throw new Exception("修改车辆失败，车牌号码已存在!");
			}
			vehicleDao.update(vehicle);
			// 车台资料
			Unit unit = unitDao.getUnitByid(largeCustNetworkEntry.getUnit_id());
			if (StringUtils.isNotBlank(bk_type)) {
				// 如果是业务办理
				unitbk = backupDao.getUnitbk(unit);
				Long bku_id = backupDao.addUnitbk(unitbk);
				backup.setBku_id(bku_id);
				// 更新升级时间
				if (bk_type.indexOf("4,") != -1) {
					unit.setUpgrade_date(new Date());
				}
			}
			unit.setUnittype_id(largeCustNetworkEntry.getUnittype_id());
			unit.setProduct_name(largeCustNetworkEntry.getName());
			unit.setMode(largeCustNetworkEntry.getMode());
			unit.setCall_letter(largeCustNetworkEntry.getCall_letter());
			unit.setTelecom(largeCustNetworkEntry.getOperators());
			unit.setSim_type(largeCustNetworkEntry.getSimtype());
			unit.setBranch(largeCustNetworkEntry.getBranch());
			unit.setSales_id(largeCustNetworkEntry.getManager_id());
			unit.setSales(largeCustNetworkEntry.getManager_name());
			unit.setWorker(largeCustNetworkEntry.getWorker());
			unit.setReg_status(largeCustNetworkEntry.getState());
			unit.setTamper_box(largeCustNetworkEntry.getTamper_box());
			unit.setTamper_smart(largeCustNetworkEntry.getTamper_smart());
			unit.setTamper_wireless(largeCustNetworkEntry.getTamper_wireless());
			unit.setIs_sz(largeCustNetworkEntry.getIs_sz());
			unit.setImei(largeCustNetworkEntry.getImei());
			unit.setTamper_code(largeCustNetworkEntry.getTamper_code());
			unit.setArea(largeCustNetworkEntry.getArea());
			unit.setOp_id(Long.valueOf(userId));
			unit.setTrade(largeCustNetworkEntry.getIndustry());
			unit.setSms_node(largeCustNetworkEntry.getNode());
			unit.setSpecial_no(largeCustNetworkEntry.getSpecial());
			unit.setFix_time(DateUtil.parse(largeCustNetworkEntry.getTime(), DateUtil.YMD_DASH));
			unit.setService_date(DateUtil.parse(largeCustNetworkEntry.getService_date(), DateUtil.YMD_DASH));
			unit.setProduct_name(largeCustNetworkEntry.getName());
			unit.setContract_no(largeCustNetworkEntry.getFileno());
			unit.setLocation(largeCustNetworkEntry.getLocation());
			unit.setArchive_time(new Date());
			if (unitDao.is_repeat(unit)) {
				throw new Exception("修改车台失败，车载号码已存在!");
			}
			unitDao.update(unit);
			// 条形码
			List<Barcode> barList = largeCustNetworkEntry.getBarcodes();
			barcodeDao.deleteByUnit_id(largeCustNetworkEntry.getUnit_id());
			for (Barcode barcode : barList) {
				if (barcode.getContent().equals("")) {
					continue;
				}
				barcode.setUnit_id(largeCustNetworkEntry.getUnit_id());
				barcodeDao.save(barcode);
			}
			// 计费信息
			List<FeeInfo> feeInfos = feeInfoDao.getListByid(largeCustNetworkEntry.getUnit_id());
			FeeInfo serviceInfo = largeCustNetworkEntry.getServiceInfo();
			FeeInfo simfeeInfo = largeCustNetworkEntry.getSimfeeInfo();
			FeeInfo insuranceInfo = largeCustNetworkEntry.getInsuranceInfo();
			FeeInfo webgisInfo = largeCustNetworkEntry.getWebgisInfo();
			// 查询正在托收计费的客户id列表
			List<Long> lockcustids = datalockDao.getLockCustomer();
			if (lockcustids.contains(largeCustNetworkEntry.getCust_id())) {
				for (FeeInfo feeInfo : feeInfos) {
					if (feeInfo.getFeetype_id() == 1 && serviceInfo != null) {
						if (Float.floatToIntBits(feeInfo.getMonth_fee()) != Float.floatToIntBits(serviceInfo.getMonth_fee())
								|| feeInfo.getFee_cycle() != serviceInfo.getFee_cycle() || !feeInfo.getItems_id().equals(serviceInfo.getItems_id())) {
							throw new Exception("操作失败，该客户正在进行托收计费不能修改服务费!");
						}
					} else if (feeInfo.getFeetype_id() == 2 && simfeeInfo != null) {
						if (Float.floatToIntBits(feeInfo.getMonth_fee()) != Float.floatToIntBits(simfeeInfo.getMonth_fee())
								|| feeInfo.getFee_cycle() != simfeeInfo.getFee_cycle()) {
							throw new Exception("操作失败，该客户正在进行托收计费不能修改SIM卡费!");
						}
					} else if (feeInfo.getFeetype_id() == 3 && insuranceInfo != null) {
						if (Float.floatToIntBits(feeInfo.getMonth_fee()) != Float.floatToIntBits(insuranceInfo.getMonth_fee())
								|| feeInfo.getFee_cycle() != insuranceInfo.getFee_cycle()) {
							throw new Exception("操作失败，该客户正在进行托收计费不能修改盗抢险费!");
						}
					} else if (feeInfo.getFeetype_id() == 4 && webgisInfo != null) {
						if (Float.floatToIntBits(feeInfo.getMonth_fee()) != Float.floatToIntBits(webgisInfo.getMonth_fee())
								|| feeInfo.getFee_cycle() != webgisInfo.getFee_cycle()) {
							throw new Exception("操作失败，该客户正在进行托收计费不能修改网上查车费!");
						}
					}
				}
			}
			Map edateMap = new HashMap<Integer, Object>();
			for (FeeInfo feeInfo : feeInfos) {
				FeeInfobk fbk = backupDao.getFeeInfobk(feeInfo);
				Long fbk_id = backupDao.addFeeInfobk(fbk);
				bkf_ids += fbk_id + ",";
				// 如果计费信息里的开始时间和截止时间不同则证明缴费过了,写入map供后面调用
				if (feeInfo.getFee_date().getTime() != feeInfo.getFee_sedate().getTime()) {
					edateMap.put(feeInfo.getFeetype_id(), feeInfo.getFee_sedate());
				}
			}
			if (StringUtils.isNotBlank(bk_type)) {
				backup.setBkf_ids(bkf_ids);
			}
			// 先删除之前的计费信息
			feeInfoDao.deleteByUnitid(largeCustNetworkEntry.getUnit_id());
			// 获得托收合同号
			Collection collection = collectionDao.getCollectionByCustId(largeCustNetworkEntry.getCust_id());
			String pay_ct_no = "";
			Long collection_id = 0L;
			if (collection != null) {
				pay_ct_no = collection.getPay_ct_no();
				collection_id = collection.getCollection_id();
			}
			// 服务费
			if (serviceInfo.getMonth_fee() != null) {
				serviceInfo.setSubco_no(Long.valueOf(companyid));
				serviceInfo.setCustomer_id(largeCustNetworkEntry.getCust_id());
				serviceInfo.setVehicle_id(vehicle.getVehicle_id());
				serviceInfo.setUnit_id(unit.getUnit_id());
				serviceInfo.setFeetype_id(1);
				serviceInfo.setItem_id(0L);
				Date edate = (Date) edateMap.get(1);
				if (serviceInfo.getFee_date() == null) {
					serviceInfo.setFee_date(new Date());
					if (edate == null) {
						edate = new Date();
					}
				} else {
					if (edate == null) {
						edate = serviceInfo.getFee_date();
					}
				}
				serviceInfo.setFee_sedate(edate);
				if (serviceInfo.getFee_cycle() == null) {
					serviceInfo.setFee_cycle(1);
				}
				serviceInfo.setAc_amount(serviceInfo.getMonth_fee() * serviceInfo.getFee_cycle());
				serviceInfo.setReal_amount(serviceInfo.getMonth_fee() * serviceInfo.getFee_cycle());
				serviceInfo.setPay_ct_no(pay_ct_no);
				serviceInfo.setCollection_id(collection_id);
				serviceInfo.setOp_id(Long.valueOf(userId));
				feeInfoDao.save(serviceInfo);
			}
			// SIM卡月租费
			if (simfeeInfo.getMonth_fee() != null) {
				simfeeInfo.setSubco_no(Long.valueOf(companyid));
				simfeeInfo.setCustomer_id(largeCustNetworkEntry.getCust_id());
				simfeeInfo.setVehicle_id(vehicle.getVehicle_id());
				simfeeInfo.setUnit_id(unit.getUnit_id());
				simfeeInfo.setFeetype_id(2);
				simfeeInfo.setItem_id(0L);
				Date edate = (Date) edateMap.get(2);
				if (simfeeInfo.getFee_date() == null) {
					simfeeInfo.setFee_date(new Date());
					if (edate == null) {
						edate = new Date();
					}
				} else {
					if (edate == null) {
						edate = simfeeInfo.getFee_date();
					}
				}
				simfeeInfo.setFee_sedate(edate);
				if (simfeeInfo.getFee_cycle() == null) {
					simfeeInfo.setFee_cycle(1);
				}
				simfeeInfo.setAc_amount(simfeeInfo.getMonth_fee() * simfeeInfo.getFee_cycle());
				simfeeInfo.setReal_amount(simfeeInfo.getMonth_fee() * simfeeInfo.getFee_cycle());
				simfeeInfo.setPay_ct_no(pay_ct_no);
				simfeeInfo.setCollection_id(collection_id);
				simfeeInfo.setOp_id(Long.valueOf(userId));
				feeInfoDao.save(simfeeInfo);
			}
			// 盗抢险
			if (insuranceInfo.getMonth_fee() != null) {
				insuranceInfo.setSubco_no(Long.valueOf(companyid));
				insuranceInfo.setCustomer_id(largeCustNetworkEntry.getCust_id());
				insuranceInfo.setVehicle_id(vehicle.getVehicle_id());
				insuranceInfo.setUnit_id(unit.getUnit_id());
				insuranceInfo.setFeetype_id(3);
				insuranceInfo.setItem_id(0L);
				Date edate = (Date) edateMap.get(3);
				if (insuranceInfo.getFee_date() == null) {
					insuranceInfo.setFee_date(new Date());
					if (edate == null) {
						edate = new Date();
					}
				} else {
					if (edate == null) {
						edate = insuranceInfo.getFee_date();
					}
				}
				insuranceInfo.setFee_sedate(edate);
				if (insuranceInfo.getFee_cycle() == null) {
					insuranceInfo.setFee_cycle(12);
				}
				insuranceInfo.setAc_amount(insuranceInfo.getMonth_fee() * insuranceInfo.getFee_cycle());
				insuranceInfo.setReal_amount(insuranceInfo.getMonth_fee() * insuranceInfo.getFee_cycle());
				insuranceInfo.setPay_ct_no(pay_ct_no);
				insuranceInfo.setCollection_id(collection_id);
				insuranceInfo.setOp_id(Long.valueOf(userId));
				feeInfoDao.save(insuranceInfo);
			}
			// 网上查车费
			if (webgisInfo.getMonth_fee() != null) {
				webgisInfo.setSubco_no(Long.valueOf(companyid));
				webgisInfo.setCustomer_id(largeCustNetworkEntry.getCust_id());
				webgisInfo.setVehicle_id(vehicle.getVehicle_id());
				webgisInfo.setUnit_id(unit.getUnit_id());
				webgisInfo.setFeetype_id(4);
				webgisInfo.setItem_id(0L);
				Date edate = (Date) edateMap.get(4);
				if (webgisInfo.getFee_date() == null) {
					webgisInfo.setFee_date(new Date());
					if (edate == null) {
						edate = new Date();
					}
				} else {
					if (edate == null) {
						edate = webgisInfo.getFee_date();
					}
				}
				webgisInfo.setFee_sedate(edate);
				if (webgisInfo.getFee_cycle() == null) {
					webgisInfo.setFee_cycle(1);
				}
				webgisInfo.setAc_amount(webgisInfo.getMonth_fee() * webgisInfo.getFee_cycle());
				webgisInfo.setReal_amount(webgisInfo.getMonth_fee() * webgisInfo.getFee_cycle());
				webgisInfo.setPay_ct_no(pay_ct_no);
				webgisInfo.setCollection_id(collection_id);
				webgisInfo.setOp_id(Long.valueOf(userId));
				feeInfoDao.save(webgisInfo);
			}
			// 司机资料
			driverDao.deleteByVehicleid(vehicle_id);
			List<Driver> drivers = largeCustNetworkEntry.getDrivers();
			for (Driver driver : drivers) {
				if (StringUtils.isBlank(driver.getDriver_name())) {
					continue;
				}
				driver.setSubco_no(Long.valueOf(companyid));
				driver.setCustomer_id(largeCustNetworkEntry.getCust_id());
				driver.setIdtype(1);
				driver.setVehicle_id(vehicle_id);
				driver.setOp_id(Long.valueOf(userId));
				driverDao.save(driver);
			}
			if (StringUtils.isNotBlank(bk_type)) {
				backup.setUnit_id(largeCustNetworkEntry.getUnit_id());
				backup.setVehicle_id(largeCustNetworkEntry.getVehicle_id());
				backup.setBk_type(bk_type);
				backup.setOp_id(Long.valueOf(userId));
				backup.setOp_name(username);
				backup.setRemark(largeCustNetworkEntry.getBk_remark());
				backupDao.addBackup(backup);
			}

			// 过户处理
			if (StringUtils.isNotBlank(bk_type) && bk_type.indexOf("2,") != -1) {
				Integer ts_type = largeCustNetworkEntry.getTs_type();
				Long custid_ts = 0l;
				if (ts_type == 0) { // 过户给私家车，需要确认托收资料是否是同一个客户所有，再次保存可能会有问题
					Customer customer_ts = largeCustNetworkEntry.getCustomer_ts();
					String service_pwd_ts = customer_ts.getService_pwd();
					String private_pwd_ts = customer_ts.getPrivate_pwd();
					DESPlus m = new DESPlus("seg12345");
					String vpwd = vehicle.getService_pwd() == null ? "" : vehicle.getService_pwd();
					String vpwd2 = vehicle.getPrivate_pwd() == null ? "" : vehicle.getPrivate_pwd();
					if (StringUtils.isBlank(service_pwd_ts)) {
						service_pwd_ts = m.Decode(vpwd).toString().trim();
					} else if (service_pwd_ts.equals(vpwd)) {
						service_pwd_ts = m.Decode(vpwd).toString().trim();
					}
					if (StringUtils.isBlank(private_pwd_ts)) {
						private_pwd_ts = m.Decode(vpwd2).toString().trim();
					} else if (private_pwd_ts.equals(vpwd2)) {
						private_pwd_ts = m.Decode(vpwd2).toString().trim();
					}
					customer_ts.setSubco_no(Long.valueOf(companyid));
					customer_ts.setSubco_code(companycode);
					customer_ts.setSubco_name(companyname);
					customer_ts.setCustco_no(0L);
					customer_ts.setCustco_code("0");
					customer_ts.setOp_id(Long.valueOf(userId));
					if (StringUtils.isNotBlank(service_pwd_ts)) {
						customer_ts.setService_pwd(DESPlus.getEncPWD(service_pwd_ts));
						vehicle.setService_pwd(DESPlus.getEncPWD(service_pwd_ts));
					} else {
						customer_ts.setService_pwd(DESPlus.getEncPWD("123456"));
						vehicle.setService_pwd(DESPlus.getEncPWD("123456"));
					}
					// 设置隐私密码，隐私密码有为空的情况
					if (StringUtils.isNotBlank(private_pwd_ts)) {
						customer_ts.setPrivate_pwd(DESPlus.getEncPWD(private_pwd_ts));
						vehicle.setPrivate_pwd(DESPlus.getEncPWD(private_pwd_ts));
					}
					custid_ts = customerDao.add(customer_ts);
					vehicleDao.update(vehicle);
					CustVehicle custVehicle = custVehicleDao.getByVehicleid(vehicle.getVehicle_id()).get(0);
					custVehicle.setCustomer_id(custid_ts);
					custVehicleDao.update(custVehicle);
					unit.setCustomer_id(custid_ts);
					unitDao.update(unit);
					if (largeCustNetworkEntry.getCust_id() != null) {// 过户前托收资料复制，再重新赋给新客户
						Collection bkCollection = collectionDao.getCollectionByCustId(largeCustNetworkEntry.getCust_id());
						if (bkCollection != null) {
							Collection newCollection = new Collection();
							newCollection.setCustomer_id(custid_ts);
							newCollection.setSubco_no(Long.valueOf(companyid));
							newCollection.setAc_no(bkCollection.getAc_no());
							newCollection.setAc_name(bkCollection.getAc_name());
							newCollection.setAgency(bkCollection.getAgency());
							newCollection.setBank(bkCollection.getBank());
							newCollection.setAc_type(bkCollection.getAc_type());
							newCollection.setIs_delivery(bkCollection.getIs_delivery());
							newCollection.setAddressee(bkCollection.getAddressee());
							newCollection.setPost_code(bkCollection.getPost_code());
							newCollection.setTel(bkCollection.getTel());
							newCollection.setProvince(bkCollection.getProvince());
							newCollection.setCity(bkCollection.getCity());
							newCollection.setArea(bkCollection.getArea());
							newCollection.setAddress(bkCollection.getAddress());
							newCollection.setOp_id(Long.valueOf(userId));
							newCollection.setPay_ct_no(maxidService.getPayCtNo(Long.valueOf(companyid)));
							newCollection.setAc_id_no(bkCollection.getAc_id_no());
							newCollection.setBank_code(bkCollection.getBank_code());
							newCollection.setFee_cycle(bkCollection.getFee_cycle());
							newCollection.setPrint_freq(bkCollection.getPrint_freq());
							newCollection.setCreate_date(new Date());
							newCollection.setAc_id_no(bkCollection.getAc_id_no());
							newCollection.setTax_payer_seq(largeCustNetworkEntry.getTaxPayerId());
							collection_id = collectionDao.addCollection(newCollection);
						}
					}
					feeInfoDao.updateFeeInfo(custid_ts, largeCustNetworkEntry.getCust_id());
					List<Linkman> custphones_ts = largeCustNetworkEntry.getCustphones_ts();
					for (Linkman phone : custphones_ts) {
						if (phone.getPhone().equals("")) {
							continue;
						}
						phone.setCustomer_id(custid_ts);
						phone.setVehicle_id(vehicle.getVehicle_id());
						custphoneDao.save(phone);
					}
					List<Linkman> list = custphoneDao.getLinkmanList(custid_ts);
					if (list.size() == 0) {
						List<Driver> driverList = driverDao.getDrivers(vehicle_id);
						for (Driver driver : driverList) {
							if (driver.getPhone().equals("")) {
								continue;
							}
							Linkman linkman = new Linkman();
							linkman.setCustomer_id(custid_ts);
							linkman.setLinkman(driver.getDriver_name());
							linkman.setLinkman_type(1);
							linkman.setPhone(driver.getPhone());
							linkman.setVehicle_id(vehicle_id);
							custphoneDao.save(linkman);
						}
					}
					String loginname = largeCustNetworkEntry.getLoginname();
					if (StringUtils.isBlank(loginname)) {
						loginname = largeCustNetworkEntry.getPlate_no();
					}

					CommonOperator operator_ts = new CommonOperator();
					String opid = IdCreater.getOperatorId();
					// 更新关系(APP需求)
					OperatorUnit operatorUnit = new OperatorUnit();
					operatorUnit.setOp_id(Long.valueOf(opid));
					operatorUnit.setUnit_id(largeCustNetworkEntry.getUnit_id());
					operatorUnitDao.updateOperatorUnit(operatorUnit);
					operator_ts.setOpid(opid);
					operator_ts.setLoginname(loginname);
					operator_ts.setOpname(customer_ts.getCustomer_name());
					if (StringUtils.isNotBlank(service_pwd_ts)) {
						operator_ts.setUserPassword(service_pwd_ts);
					} else {
						operator_ts.setUserPassword("123456");
					}
					operator_ts.setStatus("10");// 属于客户类型
					operator_ts.setCustomerid(String.valueOf(custid_ts));
					operator_ts.setNumberplate(vehicle.getPlate_no());
					OpenLdap ldap = OpenLdapManager.getInstance();
					ldap.add(operator_ts);
				} else {
					custid_ts = largeCustNetworkEntry.getPcustid();
					OpenLdap ldap = OpenLdapManager.getInstance();
					CommonOperator operator_ts = ldap.getOperatorBycustId(String.valueOf(custid_ts));
					// 更新关系(APP需求)
					OperatorUnit operatorUnit = new OperatorUnit();
					operatorUnit.setOp_id(Long.valueOf(operator_ts.getOpid()));
					operatorUnit.setUnit_id(largeCustNetworkEntry.getUnit_id());
					operatorUnitDao.updateOperatorUnit(operatorUnit);
					CustVehicle custVehicle = custVehicleDao.getByVehicleid(vehicle.getVehicle_id()).get(0);
					System.out.println(largeCustNetworkEntry.getCust_id());
					custVehicle.setCustomer_id(custid_ts);
					custVehicleDao.update(custVehicle);
					unit.setCustomer_id(custid_ts);
					unitDao.update(unit);
					feeInfoDao.updateFeeInfo(custid_ts, largeCustNetworkEntry.getCust_id());
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		HashMap map = new HashMap<String, Object>();
		map.put("success", true);
		map.put("msg", "操作成功");
		return map;
	}

	@Override
	public HashMap getErrorMap(Customer customer, Vehicle vehicle, Unit unit, FeeInfo serviceInfo, FeeInfo simfeeInfo, FeeInfo insuranceInfo,
			FeeInfo webgisInfo) throws SystemException {
		HashMap map = new HashMap<String, Object>();
		if (customer != null) {
			if (customerDao.is_repeat(customer)) {
				map.put("success", false);
				map.put("msg", "操作失败，客户名称已存在!");
				return map;
			}
		}
		if (vehicle != null) {
			if (vehicleDao.is_repeat(vehicle)) {
				map.put("success", false);
				map.put("msg", "操作失败，车牌号码已存在!");
				return map;
			}
		}
		if (unit != null) {
			if (unitDao.is_repeat(unit)) {
				map.put("success", false);
				map.put("msg", "操作失败，车载电话已存在!");
				return map;
			}
		}
		if (unit != null && customer != null) {
			// 计费信息
			List<FeeInfo> feeInfos = feeInfoDao.getListByid(unit.getUnit_id());
			// 查询正在托收计费的客户id列表
			List<Long> lockcustids = datalockDao.getLockCustomer();
			if (lockcustids.contains(customer.getCustomer_id())) {
				for (FeeInfo feeInfo : feeInfos) {
					if (feeInfo.getFeetype_id() == 1 && serviceInfo != null) {
						if (Float.floatToIntBits(feeInfo.getMonth_fee()) != Float.floatToIntBits(serviceInfo.getMonth_fee())
								|| feeInfo.getFee_cycle() != serviceInfo.getFee_cycle() || !feeInfo.getItems_id().equals(serviceInfo.getItems_id())) {
							map.put("success", false);
							map.put("msg", "操作失败，该客户正在进行计费不能修改服务费!");
							return map;
						}
					} else if (feeInfo.getFeetype_id() == 2 && simfeeInfo != null) {
						if (Float.floatToIntBits(feeInfo.getMonth_fee()) != Float.floatToIntBits(simfeeInfo.getMonth_fee())
								|| feeInfo.getFee_cycle() != simfeeInfo.getFee_cycle()) {
							map.put("success", false);
							map.put("msg", "操作失败，该客户正在进行计费不能修改SIM卡费!");
							return map;
						}
					} else if (feeInfo.getFeetype_id() == 3 && insuranceInfo != null) {
						if (Float.floatToIntBits(feeInfo.getMonth_fee()) != Float.floatToIntBits(insuranceInfo.getMonth_fee())
								|| feeInfo.getFee_cycle() != insuranceInfo.getFee_cycle()) {
							map.put("success", false);
							map.put("msg", "操作失败，该客户正在进行计费不能修改盗抢险费!");
							return map;
						}
					} else if (feeInfo.getFeetype_id() == 4 && webgisInfo != null) {
						if (Float.floatToIntBits(feeInfo.getMonth_fee()) != Float.floatToIntBits(webgisInfo.getMonth_fee())
								|| feeInfo.getFee_cycle() != webgisInfo.getFee_cycle()) {
							map.put("success", false);
							map.put("msg", "操作失败，该客户正在进行计费不能修改网上查车费!");
							return map;
						}
					}
				}
			}
		}
		if (customer != null) {
			List<Long> list = datalockDao.getLockCustomer();
			Long cust_id = customer.getCustomer_id();
			if (list.contains(cust_id)) {
				map.put("success", false);
				map.put("msg", "操作失败，该客户正在进行计费不能修改托收资料!");
				return map;
			}
		}
		map.put("success", false);
		map.put("msg", "操作失败，程序内部错误!");
		return map;
	}

	@Override
	public HashMap largeCustPhone(LargeCustNetworkEntry largeCustNetworkEntry, String userId, String companyid) {
		HashMap map = new HashMap<String, Object>();
		Long customer_id = largeCustNetworkEntry.getCust_id();
		List<Driver> drivers = largeCustNetworkEntry.getDrivers();
		if (customer_id == null) {
			map.put("success", false);
			map.put("msg", "请先选择一个集团客户!");
		}
		try {
			HibernateUtil hibernateUtil = (HibernateUtil) SpringContext.getBean("HibernateUtil");
			Session session = hibernateUtil.getSession(); // 获取Session
			session.beginTransaction(); // 开启事物
			List<CustVehicle> list = custVehicleDao.getByCustomerid(customer_id);
			for (Driver driver : drivers) {
				for (int i = 0; i < list.size(); i++) {
					Driver newDriver = new Driver();
					CustVehicle custVehicle = list.get(i);
					newDriver.setDriver_code(driver.getDriver_code());
					newDriver.setDriver_name(driver.getDriver_name());
					newDriver.setPhone(driver.getPhone());
					newDriver.setSubco_no(Long.valueOf(companyid));
					newDriver.setCustomer_id(custVehicle.getCustomer_id());
					newDriver.setVehicle_id(custVehicle.getVehicle_id());
					newDriver.setIdtype(1);
					newDriver.setOp_id(Long.valueOf(userId));
					session.save(newDriver);
					if (i % 100 == 0 || i == list.size() - 1) {
						session.flush();
						session.clear();
						session.getTransaction().commit();// 提交事物
						session.beginTransaction();
					}
				}
			}
			hibernateUtil.closeSession(session);
			Customer customer = customerDao.getCustomer(customer_id);
			customer.setOp_id(Long.valueOf(userId));
			customerDao.update(customer);
			map.put("success", true);
			map.put("msg", "操作成功!");
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "出错了!");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return map;
	}

	@Override
	public Map<String, Object> checkImei(Map<String, Object> param) throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (Utils.isNullOrEmpty(param.get("imei"))) {
				map.put("msg", null);
			} else {
				Unit unit = unitDao.getUnitByImei(param);
				map.put("msg", unit);
			}
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", e.getMessage());
		}
		return map;
	}

	@Override
	public Map<String, Object> updateNetworkPwd(Map<String, Object> param, String userId, String userName) throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Long customerId = param.get("customerId") == null ? 0l : Long.valueOf(param.get("customerId").toString());
			Long vehicleId = param.get("vehicleId") == null ? 0l : Long.valueOf(param.get("vehicleId").toString());
			Long unitId = param.get("unitId") == null ? 0l : Long.valueOf(param.get("unitId").toString());
			Long custType = param.get("custType") == null ? 0l : Long.valueOf(param.get("custType").toString());
			String servicePwd = param.get("servicePwd") == null ? "" : param.get("servicePwd").toString();
			String privatePwd = param.get("privatePwd") == null ? "" : param.get("privatePwd").toString();
			String updateType = param.get("updateType") == null ? "" : param.get("updateType").toString();
			servicePwd = DESPlus.getEncPWD(servicePwd);
			privatePwd = DESPlus.getEncPWD(privatePwd);
			Backup backup = new Backup();
			Customerbk customerbk = null;
			Vehiclebk vehiclebk = null;
			Unitbk unitbk = null;
			// 客戶信息
			Customer customer = customerDao.getCustomer(customerId);
			if (customer != null) {
				// 如果是业务办理
				customerbk = backupDao.getCustomerbk(customer);
				Long bkc_id = backupDao.addCustomerbk(customerbk);
				backup.setBkc_id(bkc_id);
			}

			// 车辆资料
			Vehicle vehicle = vehicleDao.getVehicleByid(vehicleId);
			if (vehicle != null) {
				vehiclebk = backupDao.getVehiclebk(vehicle);
				Long bkv_id = backupDao.addVehiclebk(vehiclebk);
				backup.setBkv_id(bkv_id);
			}

			// 车台资料
			Unit unit = unitDao.getUnitByid(unitId);
			if (unit != null) {
				// 如果是业务办理
				unitbk = backupDao.getUnitbk(unit);
				Long bku_id = backupDao.addUnitbk(unitbk);
				backup.setBku_id(bku_id);
			}
			backup.setUnit_id(unitId);
			backup.setVehicle_id(vehicleId);
			backup.setBk_type("5");
			backup.setOp_id(Long.valueOf(userId));
			backup.setOp_name(userName);
			if ("1".equals(updateType)) {
				backup.setRemark("资料变更：服务密码 " + vehicle.getService_pwd() + "->" + servicePwd);
				if (customer.getSubco_no() == 201L) { // 海马客户
					String url = systemConfig.getHmServicePwdUrl();
					String hmPwd = param.get("servicePwd") == null ? "" : param.get("servicePwd").toString();
					url += "?vin=" + vehicle.getVin() + "&password=" + hmPwd;
					String jsonStr = HttpClientUtils.post(url);
					JSONObject json = JSONObject.fromObject(jsonStr);
					String code = json.getString("code");
					String hmMsg = "失败";
					if ("200".equals(code)) {
						hmMsg = "成功";
					}
					backup.setRemark("资料变更：服务密码 " + vehicle.getService_pwd() + "->" + servicePwd + "，同步海马服务密码:" + hmMsg);
				}
			} else {
				backup.setRemark("资料变更：隐私密码 " + vehicle.getPrivate_pwd() + "->" + privatePwd);
			}
			backupDao.addBackup(backup);
			if (custType == 0l) {
				customerDao.updateCustomerPwd(customerId, servicePwd, privatePwd, updateType);
				vehicleDao.updateVehiclePwd(vehicleId, servicePwd, privatePwd, updateType);
				if ("1".equals(updateType)) {
					OpenLdap ldap = OpenLdapManager.getInstance();
					CommonOperator operator = ldap.getOperatorBycustId(String.valueOf(customerId));
					if (operator != null) {
						String dn = operator.getDn();
						ldap.modifyInformation(dn, "userPassword", param.get("servicePwd"));
					}
				}
			} else {
				vehicleDao.updateVehiclePwd(vehicleId, servicePwd, privatePwd, updateType);
			}

			map.put("success", true);
			map.put("msg", "更新密码成功！");
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "更新密码错误，原因:" + e.getMessage());
		}
		return map;
	}

	@Override
	public Map<String, Object> checkOperator(Map<String, Object> param) throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			OpenLdap ldap = OpenLdapManager.getInstance();
			String loginname = param.get("loginname") == null ? "" : param.get("loginname").toString();
			String mobile = param.get("mobile") == null ? "" : param.get("mobile").toString();
			String customerId = param.get("customerId") == null ? "" : param.get("customerId").toString();
			String filter = "(&(objectclass=commonOperator)";
			if (StringUtils.isNotBlank(loginname)) {
				filter += "(loginname=" + loginname + ")";
				if (StringUtils.isNotBlank(customerId)) {
					filter += "(!(customerid=" + customerId + "))";
				}
			}
			List<CommonOperator> opList = ldap.searchOperator("", filter + ")");
			if (opList.size() > 0) {
				map.put("success", false);
				map.put("msg", "登录名重复！");
				return map;
			}
			if (StringUtils.isNotBlank(mobile)) {
				filter += "(mobile=" + mobile + ")";
				if (StringUtils.isNotBlank(customerId)) {
					filter += "(!(customerid=" + customerId + "))";
				}
			}
			opList = ldap.searchOperator("", filter + ")");
			if (opList.size() > 0) {
				map.put("success", false);
				map.put("msg", "登录手机重复！");
				return map;
			}
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "查询用户错误，原因:" + e.getMessage());
		}
		return map;
	}

	@Override
	public Map<String, Object> updatePrivatePwd(Long customerId, Long vehicleId) throws SystemException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (customerId != null) {
				customerDao.updateCustomerPwd(customerId, null, null, "2");
			}
			if (vehicleId != null) {
				vehicleDao.updateVehiclePwd(vehicleId, null, null, "2");
			}
			result.put("success", true);
			result.put("msg", "清除隐私密码成功!");
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "查询用户错误，原因:" + e.getMessage());
		}
		return result;
	}

	@Override
	public Map<String, Object> changeServicePwd2Hm(Long customerId, Long vehicleId) throws SystemException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Vehicle vehicle = vehicleDao.getVehicleByid(vehicleId);
			if (vehicle == null) {
				result.put("success", false);
				result.put("msg", "同步失败，无此车辆!");
				return result;
			}
			if (vehicle.getSubco_no() != 201L) { // 非海马机构车辆
				result.put("success", false);
				result.put("msg", "同步失败，该车辆非海马车辆!");
				return result;
			}
			String vin = vehicle.getVin();
			String pwd = vehicle.getService_pwd();
			if (StringUtils.isBlank(vin)) {
				result.put("success", false);
				result.put("msg", "同步失败，该车辆vin码(车架号)为空!");
				return result;
			}
			if (vin.length() != 17) {
				result.put("success", false);
				result.put("msg", "同步失败，该车辆vin码(车架号)长度不准确!");
				return result;
			}
			if (StringUtils.isNotBlank(pwd)) {
				pwd = DESPlus.getDecPWD(pwd);
			}
			String url = systemConfig.getHmServicePwdUrl();
			url += "?vin=" + vin + "&password=" + pwd;
			String jsonStr = HttpClientUtils.post(url);
			JSONObject json = JSONObject.fromObject(jsonStr);
			String code = json.getString("code");
			if ("200".equals(code)) {
				result.put("success", true);
				result.put("msg", "同步成功!");
			} else {
				String msg = json.getString("message");
				result.put("success", false);
				result.put("msg", msg);
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "同步服务密码到海马错误，原因:" + e.getMessage());
		}
		return result;
	}
}
