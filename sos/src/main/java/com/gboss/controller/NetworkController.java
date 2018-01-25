package com.gboss.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ldap.objectClasses.CommonCompany;
import ldap.objectClasses.CommonOperator;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gboss.comm.SystemConfig;
import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.Barcode;
import com.gboss.pojo.Collection;
import com.gboss.pojo.Combo;
import com.gboss.pojo.CustSales;
import com.gboss.pojo.CustVehicle;
import com.gboss.pojo.Customer;
import com.gboss.pojo.Documents;
import com.gboss.pojo.Driver;
import com.gboss.pojo.FeeInfo;
import com.gboss.pojo.Insurer;
import com.gboss.pojo.LargeCustLock;
import com.gboss.pojo.Linkman;
import com.gboss.pojo.MidCust;
import com.gboss.pojo.Operatelog;
import com.gboss.pojo.Pack;
import com.gboss.pojo.Preload;
import com.gboss.pojo.Stop;
import com.gboss.pojo.UbiSales;
import com.gboss.pojo.Unit;
import com.gboss.pojo.Vehicle;
import com.gboss.pojo.web.CustInfo;
import com.gboss.pojo.web.DocumentCustEntry;
import com.gboss.pojo.web.LargeCustMngEntry;
import com.gboss.pojo.web.LargeCustNetworkEntry;
import com.gboss.pojo.web.PrivateNetworkEntry;
import com.gboss.service.BarcodeService;
import com.gboss.service.CollectionService;
import com.gboss.service.ComboService;
import com.gboss.service.CustVehicleService;
import com.gboss.service.CustomerService;
import com.gboss.service.CustphoneService;
import com.gboss.service.DatalockService;
import com.gboss.service.DocumentService;
import com.gboss.service.DriverService;
import com.gboss.service.FeeInfoService;
import com.gboss.service.FeePaymentService;
import com.gboss.service.MidCustService;
import com.gboss.service.ModelService;
import com.gboss.service.NetworkService;
import com.gboss.service.OperatelogService;
import com.gboss.service.PackService;
import com.gboss.service.PreloadService;
import com.gboss.service.StopService;
import com.gboss.service.UnitService;
import com.gboss.service.UnitTypeService;
import com.gboss.service.VehicleService;
import com.gboss.util.DateUtil;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.Utils;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.controller
 * @ClassName:NetworkController
 * @Description:入网控制层
 * @author:xiaoke
 * @date:2014-3-24
 */
@Controller
public class NetworkController extends BaseController {
	protected static final Logger LOGGER = LoggerFactory.getLogger(NetworkController.class);

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustphoneService custphoneService;

	@Autowired
	private VehicleService vehicleService;

	@Autowired
	private CustVehicleService custVehicleService;

	@Autowired
	private UnitService unitService;

	@Autowired
	private UnitTypeService unitTypeService;

	@Autowired
	private BarcodeService barcodeService;

	@Autowired
	private FeeInfoService feeInfoService;

	@Autowired
	private CollectionService collectionService;

	@Autowired
	private DriverService driverService;

	@Autowired
	private ModelService modelService;

	@Autowired
	private StopService stopService;

	@Autowired
	private NetworkService networkService;

	@Autowired
	private DatalockService datalockService;

	@Autowired
	private DocumentService documentService;

	@Autowired
	private MidCustService midCustService;

	@Autowired
	private OperatelogService operatelogService;

	@Autowired
	private PackService packService;

	@Autowired
	private ComboService comboService;

	@Autowired
	private PreloadService preloadService;

	@Autowired
	private SystemConfig systemconfig;

	private OpenLdap ldap = OpenLdapManager.getInstance();

	@Autowired
	@Qualifier("FeePaymentService")
	private FeePaymentService feePaymentService;

	@RequestMapping(value = "/fastNetwork", method = RequestMethod.POST)
	@LogOperation(description = "办理快速入网", type = SystemConst.OPERATELOG_ADD, model_id = 20011)
	public @ResponseBody HashMap fastNetwork(@RequestBody CustInfo custInfo, BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String userId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_ID);
		String companyid = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		String companycode = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYCODE);
		String companyname = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYNAME);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		try {
			resultMap = networkService.fastNetwork(custInfo, userId, companyid, companycode, companyname);
		} catch (Exception e) {
			resultMap = networkService.getErrorMap(null, custInfo.getVehicle(), custInfo.getUnit(), null, null, null, null);
		}
		return resultMap;
	}

	@RequestMapping(value = "/largeCustMng", method = RequestMethod.POST)
	// @LogOperation(description = "新增或修改集团客户", type =
	// SystemConst.OPERATELOG_ADD, model_id = 20014)
	public @ResponseBody HashMap largeCustMng(@RequestBody LargeCustMngEntry largeCustMngEntry, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		String userId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_ID);
		String companyid = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		String companycode = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYCODE);
		String companyname = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYNAME);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Long cust_id = largeCustMngEntry.getCust_id();
		try {
			if (cust_id == null) {
				resultMap = networkService.addLargeCustMng(largeCustMngEntry, userId, companyid, companycode, companyname);
			} else {
				resultMap = networkService.updateLargeCustMng(largeCustMngEntry, userId, companyid, companycode, companyname);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Customer customer = new Customer();
			customer.setCustomer_id(largeCustMngEntry.getCust_id());
			customer.setCustomer_name(largeCustMngEntry.getCustomer_name());
			resultMap = networkService.getErrorMap(customer, null, null, null, null, null, null);
		}
		return resultMap;
	}

	@RequestMapping(value = "/largeCustPhone", method = RequestMethod.POST)
	public @ResponseBody HashMap largeCustPhone(@RequestBody LargeCustNetworkEntry largeCustNetworkEntry, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		String userId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_ID);
		String companyid = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		driverService.deleteByCustomerid(largeCustNetworkEntry.getCust_id());
		HashMap<String, Object> resultMap = networkService.largeCustPhone(largeCustNetworkEntry, userId, companyid);
		return resultMap;
	}

	@RequestMapping(value = "/addChildlargeCust", method = RequestMethod.POST)
	@LogOperation(description = "新增机构子账号", type = SystemConst.OPERATELOG_ADD, model_id = 20014)
	public @ResponseBody HashMap addChildlargeCust(@RequestBody LargeCustMngEntry largeCustMngEntry, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		String userId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_ID);
		String companyid = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		String companycode = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYCODE);
		String companyname = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYNAME);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap = networkService.addChildlargeCust(largeCustMngEntry, userId, companyid, companycode, companyname);
		} catch (Exception e) {
			Customer customer = new Customer();
			customer.setCustomer_id(largeCustMngEntry.getCust_id());
			customer.setCustomer_name(largeCustMngEntry.getCustomer_name());
			resultMap = networkService.getErrorMap(customer, null, null, null, null, null, null);
		}
		return resultMap;
	}

	@RequestMapping(value = "/checkImei", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> checkImei(@RequestBody Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws SystemException {
		Map<String, Object> map = networkService.checkImei(param);
		return map;
	}

	@RequestMapping(value = "/getlargeCustMngEntry", method = RequestMethod.POST)
	public @ResponseBody LargeCustMngEntry getlargeCustMngEntry(@RequestBody LargeCustMngEntry largeCustMngEntry, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		Long cust_id = largeCustMngEntry.getCust_id();
		if (cust_id == null) {
			return null;
		}
		Customer customer = customerService.getCustomer(cust_id);
		CustSales custSales = customerService.getCustSales(cust_id);
		List<Linkman> list = custphoneService.listCustphone(cust_id);
		Collection collection = collectionService.getCollectionByCustId(cust_id);
		List<Collection> collections = collectionService.getCollections(cust_id);
		OpenLdap ldap = OpenLdapManager.getInstance();
		CommonOperator operator = ldap.getOperatorBycustId(String.valueOf(cust_id));
		CommonCompany company = ldap.getCompanyByname(customer.getCustomer_name());
		LargeCustMngEntry result = new LargeCustMngEntry();
		result.setOpid(Long.valueOf(operator.getOpid()));
		result.setCompanyno(Long.valueOf(company.getCompanyno()));
		result.setCust_id(cust_id);
		result.setLoginname(operator.getLoginname());
		result.setServer_pwd(operator.getUserPassword());
		result.setCustomer_name(customer.getCustomer_name());
		Integer cust_type = customer.getCust_type();
		if (cust_type == 1) {// 集团客户
			result.setIs_guarantee(1);
		} else if (cust_type == 2) {
			result.setIs_guarantee(0);
		}
		result.setCust_address(customer.getAddress());
		result.setTrade(customer.getTrade());
		result.setEmail(customer.getEmail());
		result.setVip(customer.getVip());
		result.setFileno(customer.getContract_no());
		result.setLocation(customer.getLocation());
		result.setRemark(customer.getRemark());
		result.setCustphones(list);
		if (custSales != null) {
			result.setManager(custSales.getManager_name());
			result.setManagerid(String.valueOf(custSales.getManager_id()));
		}
		if (collection != null) {
			result.setCollection_id(collection.getCollection_id());
			result.setAccount(collection.getAc_no());
			result.setCollection_name(collection.getAc_name());
			result.setAgency(collection.getAgency());
			result.setBank(collection.getBank());
			result.setBankcode(collection.getBank_code());
			result.setType(collection.getAc_type());
			result.setTel(collection.getTel());
			result.setIs_delivery(collection.getIs_delivery());
			result.setAddressee(collection.getAddressee());
			result.setPost_code(collection.getPost_code());
			result.setPay_ct_no(collection.getPay_ct_no());
			result.setProvince(collection.getProvince());
			result.setCity(collection.getCity());
			result.setArea(collection.getArea());
			result.setAddress(collection.getAddress());
			result.setAc_id_no(collection.getAc_id_no());
			result.setTaxPayerId(collection.getTax_payer_seq());
		}
		if (collections != null) {
			result.setCollections(collections);
		}
		return result;
	}

	/**
	 * 私家车添加或者保存(私家车入网，资料变更(私家车))
	 * 
	 * @param privateNetworkEntry
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/privateNetwork", method = RequestMethod.POST)
	public @ResponseBody HashMap privateNetwork(@RequestBody PrivateNetworkEntry privateNetworkEntry, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		String userId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_ID);
		String username = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_USERNAME);
		String companyid = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		String companycode = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYCODE);
		String companyname = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYNAME);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Long vehicle_id = privateNetworkEntry.getVehicle_id();
		Long cust_id = privateNetworkEntry.getCust_id();
		try {
			System.out.println("*****************  privateNetworkEntry.ubi_sales_id=" + privateNetworkEntry.getUbi_sales_id());
			if (vehicle_id == null) {
				resultMap = networkService.addPrivateCust(privateNetworkEntry, userId, companyid, companycode, companyname);
				Operatelog log = new Operatelog();
				log.setUser_id(Long.valueOf(userId));
				log.setUser_name(username);
				log.setModel_id(20012);
				log.setOp_type(Integer.valueOf(SystemConst.OPERATELOG_ADD));
				log.setSubco_no(Long.valueOf(companyid));
				log.setRemark("操作ip地址:" + Utils.getIpAddr(request) + ";" + "私家车客户入网:" + privateNetworkEntry.getCustomer_name() + ","
						+ privateNetworkEntry.getNumber_plate() + "," + privateNetworkEntry.getCall_letter());
				operatelogService.add(log);
			} else {
				resultMap = networkService.updatePrivateCust(privateNetworkEntry, userId, username, companyid, companycode, companyname);
				Operatelog log = new Operatelog();
				log.setUser_id(Long.valueOf(userId));
				log.setUser_name(username);
				log.setModel_id(20016);
				log.setOp_type(Integer.valueOf(SystemConst.OPERATELOG_UPDATE));
				log.setSubco_no(Long.valueOf(companyid));
				log.setRemark("操作ip地址:" + Utils.getIpAddr(request) + ";" + privateNetworkEntry.getBk_remark());
				operatelogService.add(log);
			}
		} catch (Exception e) {

			Customer customer = new Customer();
			Vehicle vehicle = new Vehicle();
			Unit unit = new Unit();
			if (cust_id == null) {
				customer = null;
			} else {
				customer.setCustomer_id(privateNetworkEntry.getCust_id());
			}
			vehicle.setVehicle_id(privateNetworkEntry.getVehicle_id());
			vehicle.setPlate_no(privateNetworkEntry.getNumber_plate());
			vehicle.setVin(privateNetworkEntry.getCode());
			unit.setUnit_id(privateNetworkEntry.getUnit_id());
			unit.setCall_letter(privateNetworkEntry.getCall_letter());
			resultMap = networkService.getErrorMap(customer, vehicle, unit, privateNetworkEntry.getServiceInfo(),
					privateNetworkEntry.getSimfeeInfo(), privateNetworkEntry.getInsuranceInfo(), privateNetworkEntry.getWebgisInfo());
		}
		return resultMap;
	}

	@RequestMapping(value = "/getprivateNetworkEntry", method = RequestMethod.POST)
	public @ResponseBody PrivateNetworkEntry getprivateNetworkEntry(@RequestBody PrivateNetworkEntry privateNetworkEntry,
			BindingResult bindingResult, HttpServletRequest request) throws SystemException {
		Long vehicle_id = privateNetworkEntry.getVehicle_id();
		Long unit_id = privateNetworkEntry.getUnit_id();
		Long cust_id = privateNetworkEntry.getCust_id();
		if (vehicle_id == null && unit_id == null && cust_id == null) {
			return null;
		}
		Vehicle vehicle = null;
		Customer customer = null;
		Unit unit = null;
		List<Barcode> barcodes = null;
		List<Linkman> list = null;
		Collection collection = null;
		CommonOperator operator = null;
		FeeInfo serviceInfo = null;
		FeeInfo simfeeInfo = null;
		FeeInfo webgisInfo = null;
		FeeInfo insuranceInfo = null;
		if (unit_id != null) {
			unit = unitService.getUnitByid(unit_id);
			vehicle = vehicleService.getVehicleByid(unit.getVehicle_id());
			customer = customerService.getCustomer(unit.getCustomer_id());
			barcodes = barcodeService.getByUnit_id(unit_id);
			list = custphoneService.getLinkmanList(customer.getCustomer_id());
			collection = collectionService.getCollectionByCustId(customer.getCustomer_id());
			OpenLdap ldap = OpenLdapManager.getInstance();
			operator = ldap.getOperatorBycustId(String.valueOf(customer.getCustomer_id()));
			serviceInfo = feeInfoService.getFeeInfo(unit_id, 1);
			simfeeInfo = feeInfoService.getFeeInfo(unit_id, 2);
			insuranceInfo = feeInfoService.getFeeInfo(unit_id, 3);
			webgisInfo = feeInfoService.getFeeInfo(unit_id, 4);
		} else if (vehicle_id != null) {
			vehicle = vehicleService.getVehicleByid(vehicle_id);
			List<CustVehicle> custVehicles = custVehicleService.getByVehicleid(vehicle_id);
			for (CustVehicle cv : custVehicles) {
				Long cid = cv.getCustomer_id();
				Customer cust = customerService.getCustomer(cid);
				if (cust != null && cust.getCust_type() == 0) {
					customer = cust;
					break;
				}
			}
			unit = unitService.getByCustAndVehicle(customer.getCustomer_id(), vehicle_id);
			barcodes = barcodeService.getByUnit_id(unit.getUnit_id());
			list = custphoneService.listCustphone(customer.getCustomer_id());
			collection = collectionService.getCollectionByCustId(customer.getCustomer_id());
			OpenLdap ldap = OpenLdapManager.getInstance();
			operator = ldap.getOperatorBycustId(String.valueOf(customer.getCustomer_id()));
			serviceInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 1);
			simfeeInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 2);
			insuranceInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 3);
			webgisInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 4);
		} else if (cust_id != null) {
			customer = customerService.getCustomer(cust_id);
			OpenLdap ldap = OpenLdapManager.getInstance();
			operator = ldap.getOperatorBycustId(String.valueOf(cust_id));
			collection = collectionService.getCollectionByCustId(customer.getCustomer_id());
			list = custphoneService.listCustphone(customer.getCustomer_id());
			List<Vehicle> vehicles = vehicleService.getVehiclesByCustid(cust_id);
			if (vehicles.size() > 0) {
				vehicle = vehicles.get(0);
				List<Unit> unitList = unitService.getByVehicle_id(vehicle.getVehicle_id());
				if (unitList.size() > 0) {
					unit = unitList.get(0);
					barcodes = barcodeService.getByUnit_id(unit.getUnit_id());
					serviceInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 1);
					simfeeInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 2);
					insuranceInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 3);
					webgisInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 4);
				}
			}
		}
		// 中间客户
		MidCust midCust = midCustService.getMidCustByUnitid(unit.getUnit_id());
		PrivateNetworkEntry result = new PrivateNetworkEntry();
		result.setOpid(Long.valueOf(operator.getOpid()));
		result.setCust_id(customer.getCustomer_id());
		result.setLoginname(operator.getLoginname());
		result.setServer_pwd(operator.getUserPassword());
		result.setCustomer_name(customer.getCustomer_name());
		result.setIdcard(customer.getId_no());
		result.setBirthday(DateUtil.format(customer.getBirthday(), DateUtil.YMD_DASH));
		result.setSex(customer.getSex());
		result.setEmail(customer.getEmail());
		result.setVip(customer.getVip());
		// result.setFileno(customer.getContract_no());
		// result.setLocation(customer.getLocation());
		result.setRemark(customer.getRemark());
		result.setCustphones(list);
		result.setVehicle_id(vehicle.getVehicle_id());
		result.setNumber_plate(vehicle.getPlate_no());
		result.setPlate_color(vehicle.getPlate_color());
		result.setVehicle_type(vehicle.getVehicle_type());
		result.setBrand(vehicle.getBrand());
		result.setSeries(vehicle.getSeries());
		result.setCartype(vehicle.getModel());
		// String cartype_name = modelService.getModelByid(vehicle.getModel());
		result.setCartype_name(vehicle.getModel_name());
		result.setCode(vehicle.getVin());
		result.setEngine_no(vehicle.getEngine_no());
		result.setChassis_no(vehicle.getVin());
		result.setPlate_no(vehicle.getChassis_no());
		result.setFactory(vehicle.getFactory());
		result.setColor(vehicle.getColor());
		result.setBuy_time(DateUtil.format(vehicle.getBuy_date(), DateUtil.YMD_DASH));
		result.setSeatremark(vehicle.getRemark());
		result.setUnit_id(unit.getUnit_id());
		result.setUnittype_id(unit.getUnittype_id());
		String unittype = unitTypeService.getUnittypeByid(unit.getUnittype_id());
		result.setUnittype(unittype);
		result.setName(unit.getProduct_name());
		result.setMode(unit.getMode());
		result.setCall_letter(unit.getCall_letter());
		result.setOperators(unit.getTelecom());
		result.setSimtype(unit.getSim_type());
		result.setState(unit.getReg_status());
		result.setTime(DateUtil.format(unit.getFix_time(), DateUtil.YMD_DASH));
		result.setManager_id(unit.getSales_id());
		result.setManager_name(unit.getSales());
		result.setManager_id(unit.getSales_id());
		result.setUnitarea(unit.getArea());
		result.setBranch(unit.getBranch());
		result.setPack_id(unit.getPack_id());
		result.setTamper_code(unit.getTamper_code());
		result.setSpecial(unit.getSpecial_no());
		result.setNode(unit.getSms_node());
		result.setFileno(unit.getContract_no());
		result.setLocation(unit.getLocation());
		if (midCust != null) {
			Long guarantee_id = midCust.getCustomer_id();
			Customer midCustomer = customerService.getCustomer(guarantee_id);
			String guarantee = midCustomer.getCustomer_name();
			result.setGuarantee(guarantee);
			result.setGuarantee_id(guarantee_id);
		}
		result.setBarcodes(barcodes);
		result.setServiceInfo(serviceInfo);
		result.setSimfeeInfo(simfeeInfo);
		result.setWebgisInfo(webgisInfo);
		result.setInsuranceInfo(insuranceInfo);
		if (collection != null) {
			result.setCollection_id(collection.getCollection_id());
			result.setAccount(collection.getAc_no());
			result.setCollection_name(collection.getAc_name());
			result.setAgency(collection.getAgency());
			result.setBank(collection.getBank());
			result.setBankcode(collection.getBank_code());
			result.setAccount_type(collection.getAc_type());
			result.setTel(collection.getTel());
			result.setIs_delivery(collection.getIs_delivery());
			result.setAddressee(collection.getAddressee());
			result.setPost_code(collection.getPost_code());
			result.setPay_ct_no(collection.getPay_ct_no());
			result.setProvince(collection.getProvince());
			result.setCity(collection.getCity());
			result.setArea(collection.getArea());
			result.setAddress(collection.getAddress());
			result.setAc_id_no(collection.getAc_id_no());
		}
		CustInfo custInfo = new CustInfo();
		custInfo.setUnit(unit);
		custInfo.setCustomer(customer);
		custInfo.setVehicle(vehicle);
		request.getSession().setAttribute(SystemConst.CUST_INFO, custInfo);
		return result;
	}
	
//////////////////////////////////////////////////////////////////////////
	private MessageSourceAccessor messages;
	@Autowired
	public void setMessages(MessageSource messageSource) {
		messages = new MessageSourceAccessor(messageSource);
	}
	
	public String getText(String msgKey, Locale locale, Object... args) {
        if (args.length == 0) {
        	return messages.getMessage(msgKey, locale);
		}
        return messages.getMessage(msgKey, args, locale);
    }
	
	public String getText(MessageSourceResolvable resolvable, Locale locale) {
		return messages.getMessage(resolvable);
	}
	
///////////////////////////////////////////////////////////////////////////////////
	/**
	 * @Desc 查询资料车辆
	 * @param pageSelect
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws SystemException
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/getVehicles", method = RequestMethod.POST)
	public @ResponseBody Page<Vehicle> getVehicles(@RequestBody PageSelect<Vehicle> pageSelect, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException, UnsupportedEncodingException {
		
		String searchValue = pageSelect.getSearchValue();
		String companyid = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		String ishq = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_ISHQ);
		// System.out.println("******************************");
		// System.out.println(searchValue+" ^^^^^^^^^^  " + companyid);
		// 2016年3月25日18:58:16 @金星 门店查询页面判断选择机构失败后默认查询全部数据
		// 修改后代码
		// if(org.apache.commons.lang.StringUtils.isBlank(companyid)){
		// companyid = "2";
		// }
		if ("0".equals(searchValue)) {
			companyid = "2";
		}
		Page<Vehicle> result = vehicleService.search(pageSelect, Long.valueOf(companyid));
		return result;
	}

	@RequestMapping(value = "/getlargecustVehicles", method = RequestMethod.POST)
	public @ResponseBody Page<Vehicle> getlargecustVehicles(@RequestBody PageSelect<Vehicle> pageSelect, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		String companyid = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		Page<Vehicle> result = vehicleService.searchlargecustVehicle(pageSelect, Long.valueOf(companyid));
		return result;
	}

	@RequestMapping(value = "/getprivateVehicles", method = RequestMethod.POST)
	public @ResponseBody Page<Vehicle> getprivateVehicles(@RequestBody PageSelect<Vehicle> pageSelect, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		String companyid = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		Page<Vehicle> result = vehicleService.searchprivateVehicle(pageSelect, Long.valueOf(companyid));
		return result;
	}

	/**
	 * @Desc 门店系统查询页面，车载呼号查询
	 * @param pageSelect
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/getUnits", method = RequestMethod.POST)
	public @ResponseBody Page<Unit> getUnits(@RequestBody PageSelect<Unit> pageSelect, BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String searchValue = pageSelect.getSearchValue();
		String companyid = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		String ishq = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_ISHQ);
		// System.out.println("******************************");
		// System.out.println(searchValue+" ^^^^^^^^^^  " + companyid);
		// 2016年3月25日18:58:16 @金星 门店查询页面判断选择机构失败后默认查询全部数据
		// 修改后代码
		// if(org.apache.commons.lang.StringUtils.isBlank(companyid)){
		// companyid = "2";
		// }
		if ("0".equals(searchValue)) {
			companyid = "2";
		}
		Page<Unit> result = unitService.search(pageSelect, Long.valueOf(companyid));
		return result;
	}

	@RequestMapping(value = "/getlcmakeupUnits", method = RequestMethod.POST)
	public @ResponseBody Page<Unit> getlcmuUnits(@RequestBody PageSelect<Unit> pageSelect, BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String companyid = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		Page<Unit> result = unitService.search2(pageSelect, Long.valueOf(companyid));
		return result;
	}

	@RequestMapping(value = "/getpamakeupUnits", method = RequestMethod.POST)
	public @ResponseBody Page<Unit> getpamuUnits(@RequestBody PageSelect<Unit> pageSelect, BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String companyid = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		Page<Unit> result = unitService.search3(pageSelect, Long.valueOf(companyid));
		return result;
	}

	@RequestMapping(value = "/getlcupdateUnits", method = RequestMethod.POST)
	public @ResponseBody Page<Unit> getlcudUnits(@RequestBody PageSelect<Unit> pageSelect, BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String companyid = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		Page<Unit> result = unitService.search4(pageSelect, Long.valueOf(companyid));
		return result;
	}

	@RequestMapping(value = "/getpaupdateUnits", method = RequestMethod.POST)
	public @ResponseBody Page<Unit> getprivateUnits(@RequestBody PageSelect<Unit> pageSelect, BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String companyid = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		Page<Unit> result = unitService.search5(pageSelect, Long.valueOf(companyid));
		return result;
	}

	/**
	 * @Desc 门店查询页面，客户电话查询
	 * @param pageSelect
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/getCompanyPhone", method = RequestMethod.POST)
	public @ResponseBody Page<Linkman> getCompanyPhone(@RequestBody PageSelect<Linkman> pageSelect, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		String searchValue = pageSelect.getSearchValue();
		String companyid = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		String ishq = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_ISHQ);
		// System.out.println("******************************");
		// System.out.println(searchValue+" ^^^^^^^^^^  " + companyid);
		// 2016年3月25日18:58:16 @金星 门店查询页面判断选择机构失败后默认查询全部数据
		// 修改后代码
		// if(org.apache.commons.lang.StringUtils.isBlank(companyid)){
		// companyid = "2";
		// }
		if ("0".equals(searchValue)) {
			companyid = "2";
		}
		Map filter = pageSelect.getFilter();
		filter.put("subco_no", companyid);
		pageSelect.setFilter(filter);
		Page<Linkman> result = custphoneService.findLinkman(pageSelect);
		return result;
	}

	@RequestMapping(value = "/getCollections", method = RequestMethod.POST)
	public @ResponseBody List<Collection> getCollections(@RequestBody Collection collection, BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		Long customerid = collection.getCustomer_id();
		List<Collection> result = collectionService.getCollections(customerid);
		return result;
	}

	@RequestMapping(value = "/largecustNetwork", method = RequestMethod.POST)
	public @ResponseBody HashMap largecustNetwork(@RequestBody LargeCustNetworkEntry largeCustNetworkEntry, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		String userId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_ID);
		String username = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_USERNAME);
		String companyid = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		String companycode = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYCODE);
		String companyname = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYNAME);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Long vehicle_id = largeCustNetworkEntry.getVehicle_id();
		Long cust_id = largeCustNetworkEntry.getCust_id();
		try {
			if (vehicle_id == null) { // 新增
				resultMap = networkService.addLargecust(largeCustNetworkEntry, userId, companyid, companycode, companyname);
				Operatelog log = new Operatelog();
				log.setUser_id(Long.valueOf(userId));
				log.setUser_name(username);
				log.setModel_id(20013);
				log.setOp_type(Integer.valueOf(SystemConst.OPERATELOG_ADD));
				log.setSubco_no(Long.valueOf(companyid));
				log.setRemark("操作ip地址:" + Utils.getIpAddr(request) + ";" + "集团客户入网:" + largeCustNetworkEntry.getCustomer_name() + ","
						+ largeCustNetworkEntry.getNumber_plate() + "," + largeCustNetworkEntry.getCall_letter());
				operatelogService.add(log);
			} else {  //更新
				resultMap = networkService.updateLargecust(largeCustNetworkEntry, userId, username, companyid, companycode, companyname);
				Operatelog log = new Operatelog();
				log.setUser_id(Long.valueOf(userId));
				log.setUser_name(username);
				log.setModel_id(20016);
				log.setOp_type(Integer.valueOf(SystemConst.OPERATELOG_UPDATE));
				log.setSubco_no(Long.valueOf(companyid));
				log.setRemark("操作ip地址:" + Utils.getIpAddr(request) + ";" + largeCustNetworkEntry.getBk_remark());
				operatelogService.add(log);
			}
		} catch (Exception e) {
			Customer customer = new Customer();
			Vehicle vehicle = new Vehicle();
			Unit unit = new Unit();
			if (cust_id == null) {
				customer = null;
			} else {
				customer.setCustomer_id(largeCustNetworkEntry.getCust_id());
			}
			vehicle.setVehicle_id(largeCustNetworkEntry.getVehicle_id());
			vehicle.setPlate_no(largeCustNetworkEntry.getNumber_plate());
			vehicle.setVin(largeCustNetworkEntry.getCode());
			unit.setUnit_id(largeCustNetworkEntry.getUnit_id());
			unit.setCall_letter(largeCustNetworkEntry.getCall_letter());
			resultMap = networkService.getErrorMap(customer, vehicle, unit, largeCustNetworkEntry.getServiceInfo(),
					largeCustNetworkEntry.getSimfeeInfo(), largeCustNetworkEntry.getInsuranceInfo(), largeCustNetworkEntry.getWebgisInfo());
		}
		return resultMap;
	}

	@RequestMapping(value = "/getlargecustNetworkEntry", method = RequestMethod.POST)
	public @ResponseBody LargeCustNetworkEntry getlargecustNetworkEntry(@RequestBody LargeCustNetworkEntry largeCustNetworkEntry,
			BindingResult bindingResult, HttpServletRequest request) throws SystemException {
		Long vehicle_id = largeCustNetworkEntry.getVehicle_id();
		Long unit_id = largeCustNetworkEntry.getUnit_id();
		Long cust_id = largeCustNetworkEntry.getCust_id();
		if (vehicle_id == null && unit_id == null && cust_id == null) {
			return null;
		}
		Vehicle vehicle = null;
		Customer customer = null;
		Unit unit = null;
		List<Barcode> barcodes = null;
		List<Driver> drivers = null;
		FeeInfo serviceInfo = null;
		FeeInfo simfeeInfo = null;
		FeeInfo webgisInfo = null;
		FeeInfo insuranceInfo = null;
		if (unit_id != null) {
			unit = unitService.getUnitByid(unit_id);
			vehicle = vehicleService.getVehicleByid(unit.getVehicle_id());
			customer = customerService.getCustomer(unit.getCustomer_id());
			barcodes = barcodeService.getByUnit_id(unit_id);
			drivers = driverService.getDrivers(unit.getVehicle_id());
			serviceInfo = feeInfoService.getFeeInfo(unit_id, 1);
			simfeeInfo = feeInfoService.getFeeInfo(unit_id, 2);
			insuranceInfo = feeInfoService.getFeeInfo(unit_id, 3);
			webgisInfo = feeInfoService.getFeeInfo(unit_id, 4);
		} else if (vehicle_id != null) {
			vehicle = vehicleService.getVehicleByid(vehicle_id);
			List<CustVehicle> custVehicles = custVehicleService.getByVehicleid(vehicle_id);
			for (CustVehicle cv : custVehicles) {
				Long cid = cv.getCustomer_id();
				Customer cust = customerService.getCustomer(cid);
				if (cust.getCust_type() != 0) {
					customer = cust;
					break;
				}
			}
			unit = unitService.getByCustAndVehicle(customer.getCustomer_id(), vehicle_id);
			barcodes = barcodeService.getByUnit_id(unit.getUnit_id());
			drivers = driverService.getDrivers(vehicle_id);
			serviceInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 1);
			simfeeInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 2);
			insuranceInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 3);
			webgisInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 4);
		} else if (cust_id != null) {
			customer = customerService.getCustomer(cust_id);
			List<Vehicle> vehicles = vehicleService.getVehiclesByCustid(cust_id);
			if (vehicles.size() > 0) {
				vehicle = vehicles.get(0);
				List<Unit> unitList = unitService.getByVehicle_id(vehicle.getVehicle_id());
				if (unitList.size() > 0) {
					unit = unitList.get(0);
					serviceInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 1);
					simfeeInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 2);
					insuranceInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 3);
					webgisInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 4);
				}
			}
		}
		LargeCustNetworkEntry result = new LargeCustNetworkEntry();
		result.setCust_id(customer.getCustomer_id());
		result.setCustomer_name(customer.getCustomer_name());
		if (vehicle != null) {
			result.setVehicle_id(vehicle.getVehicle_id());
			result.setNumber_plate(vehicle.getPlate_no());
			result.setPlate_color(vehicle.getPlate_color());
			result.setVehicle_type(vehicle.getVehicle_type());
			result.setEngine_no(vehicle.getEngine_no());
			result.setPlate_no(vehicle.getChassis_no());
			result.setCode(vehicle.getVin());
			result.setChassis_no(vehicle.getVin());
			result.setBrand(vehicle.getBrand());
			result.setSeries(vehicle.getSeries());
			result.setCartype(vehicle.getModel());
			// String cartype_name =
			// modelService.getModelByid(vehicle.getModel());
			result.setCartype_name(vehicle.getModel_name());
			result.setColor(vehicle.getColor());
			result.setSince_no(vehicle.getDef_no());
			result.setBuy_time(DateUtil.format(vehicle.getBuy_date(), DateUtil.YMD_DASH));
			result.setMoney(vehicle.getBuy_money());
			result.setFactory(vehicle.getFactory());
			result.setRegister_time(DateUtil.format(vehicle.getRegister_date(), DateUtil.YMD_DASH));
			result.setDriving_no(vehicle.getVehicle_license());
			result.setGrant_time(DateUtil.format(vehicle.getVl_bdate(), DateUtil.YMD_DASH));
			result.setValid_time(DateUtil.format(vehicle.getVl_edate(), DateUtil.YMD_DASH));
			result.setSeatremark(vehicle.getRemark());
		}
		if (unit != null) {
			result.setIndustry(unit.getTrade());
			result.setState(unit.getReg_status());
			result.setUnit_id(unit.getUnit_id());
			result.setUnittype_id(unit.getUnittype_id());
			String unittype = unitTypeService.getUnittypeByid(unit.getUnittype_id());
			result.setUnittype(unittype);
			result.setName(unit.getProduct_name());
			result.setMode(unit.getMode());
			result.setCall_letter(unit.getCall_letter());
			result.setOperators(unit.getTelecom());
			result.setSimtype(unit.getSim_type());
			result.setArea(unit.getArea());
			result.setFileno(unit.getContract_no());
			result.setLocation(unit.getLocation());

			result.setManager_id(unit.getSales_id());
			result.setManager_name(unit.getSales());
			result.setBranch(unit.getBranch());
			// result.setPack_id(unit.getPack_id());
			result.setTamper_code(unit.getTamper_code());
			result.setSpecial(unit.getSpecial_no());
			result.setTime(DateUtil.format(unit.getFix_time(), DateUtil.YMD_DASH));
			result.setNode(unit.getSms_node());
			result.setSpecial(unit.getSpecial_no());
		}
		result.setBarcodes(barcodes);
		result.setDrivers(drivers);
		// 计费信息
		result.setServiceInfo(serviceInfo);
		result.setSimfeeInfo(simfeeInfo);
		result.setWebgisInfo(webgisInfo);
		result.setInsuranceInfo(insuranceInfo);

		// 存些session信息
		CustInfo custInfo = new CustInfo();
		custInfo.setUnit(unit);
		custInfo.setCustomer(customer);
		custInfo.setVehicle(vehicle);
		request.getSession().setAttribute(SystemConst.CUST_INFO, custInfo);
		return result;
	}

	@RequestMapping(value = "/getdocumentCustEntry", method = RequestMethod.POST)
	public @ResponseBody DocumentCustEntry getdocumentCustEntry(@RequestBody DocumentCustEntry documentCustEntry, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		Long cust_id = documentCustEntry.getCust_id();
		Long vehicle_id = documentCustEntry.getVehicle_id();
		Long unit_id = documentCustEntry.getUnit_id();
		Customer cust = null;
		Vehicle vehicle = null;
		Unit unit = null;
		Documents documents = null;
		DocumentCustEntry result = new DocumentCustEntry();
		if (unit_id != null) {
			unit = unitService.getUnitByid(unit_id);
			vehicle = vehicleService.getVehicleByid(unit.getVehicle_id());
			cust = customerService.getCustomer(unit.getCustomer_id());
			List<Documents> list = documentService.search(unit_id);
			if (list != null && list.size() > 0) {
				documents = list.get(0);
			}
		} else if (vehicle_id != null) {
			vehicle = vehicleService.getVehicleByid(vehicle_id);
			List<Unit> unitList = unitService.getByVehicle_id(vehicle_id);
			if (unitList.size() > 0) {
				unit = unitList.get(0);
				cust = customerService.getCustomer(unit.getCustomer_id());
				List<Documents> list = documentService.search(unit.getUnit_id());
				if (list != null && list.size() > 0) {
					documents = list.get(0);
				}
			}
		} else if (cust_id != null) {
			cust = customerService.getCustomer(cust_id);
			List<Vehicle> vehicles = vehicleService.getVehiclesByCustid(cust_id);
			if (vehicles.size() > 0) {
				vehicle = vehicles.get(0);
				List<Unit> unitList = unitService.getByVehicle_id(vehicle.getVehicle_id());
				if (unitList.size() > 0) {
					unit = unitList.get(0);
					List<Documents> list = documentService.search(unit.getUnit_id());
					if (list != null && list.size() > 0) {
						documents = list.get(0);
					}
				}
			}
		}
		result.setCustomer(cust);
		result.setVehicle(vehicle);
		result.setUnit(unit);
		result.setDocuments(documents);
		Long search_unitid = unit == null ? 0L : unit.getUnit_id();
		request.getSession().setAttribute(SystemConst.SEARCH_UNITID, search_unitid);
		return result;
	}

	/**
	 * @Desc 门店资料查询
	 * @param custInfo
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/getCustInfo", method = RequestMethod.POST)
	@LogOperation(description = "资料查询", type = SystemConst.OPERATELOG_SEARCHKEY, model_id = 20010)
	public @ResponseBody CustInfo getCustInfo(@RequestBody CustInfo custInfo, BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		OpenLdap ldap = OpenLdapManager.getInstance();
		Long cust_id = custInfo.getCust_id();
		Long vehicle_id = custInfo.getVehicle_id();
		Long unit_id = custInfo.getUnit_id();
		CommonOperator operator = null;
		Customer cust = null;
		Vehicle vehicle = null;
		Unit unit = null;
		Collection collection = null;
		List<Collection> collections = null;
		List<Linkman> list = null;
		List<Barcode> barcodes = null;
		List<Driver> drivers = null;
		FeeInfo serviceInfo = null;// 服务费
		FeeInfo simfeeInfo = null;// SIM卡费
		FeeInfo insuranceInfo = null;// 保险费
		FeeInfo webgisInfo = null;// 网上查车计费
		FeeInfo preloadInfo = null;// 前装服务费
		CustInfo result = new CustInfo();
		if (unit_id != null) {
			unit = unitService.getUnitByid(unit_id);
			vehicle = vehicleService.getVehicleByid(unit.getVehicle_id());
			cust = customerService.getCustomer(unit.getCustomer_id());
			operator = ldap.getOperatorBycustId(String.valueOf(cust.getCustomer_id()));
			collection = collectionService.getCollectionByUnit(unit.getUnit_id(), unit.getCustomer_id());
			list = custphoneService.listCustphone(cust.getCustomer_id());
			barcodes = barcodeService.getByUnit_id(unit_id);
			drivers = driverService.getDrivers(unit.getVehicle_id());
			serviceInfo = feeInfoService.getFeeInfo(unit_id, 1);
			simfeeInfo = feeInfoService.getFeeInfo(unit_id, 2);
			insuranceInfo = feeInfoService.getFeeInfo(unit_id, 3);
			webgisInfo = feeInfoService.getFeeInfo(unit_id, 4);
			preloadInfo = feeInfoService.getFeeInfo(unit_id, 101);
		} else if (vehicle_id != null) {
			vehicle = vehicleService.getVehicleByid(vehicle_id);
			List<Unit> unitList = unitService.getByVehicle_id(vehicle_id);
			if (unitList.size() > 0) {
				unit = unitList.get(0);
				barcodes = barcodeService.getByUnit_id(unit.getUnit_id());
				drivers = driverService.getDrivers(vehicle_id);
				cust = customerService.getCustomer(unit.getCustomer_id());
				operator = ldap.getOperatorBycustId(String.valueOf(cust.getCustomer_id()));
				collection = collectionService.getCollectionByUnit(unit.getUnit_id(), unit.getCustomer_id());
				list = custphoneService.listCustphone(cust.getCustomer_id());
				serviceInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 1);
				simfeeInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 2);
				// insuranceInfo = feeInfoService.getFeeInfo(unit.getUnit_id(),
				// 3);
				insuranceInfo = feeInfoService.getinsuranceInfo(vehicle_id);
				webgisInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 4);
				preloadInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 101);
			} else {
				List<CustVehicle> cvList = custVehicleService.getByVehicleid(vehicle_id);
				if (cvList.size() > 0) {
					CustVehicle cv = cvList.get(0);
					drivers = driverService.getDrivers(vehicle_id);
					cust = customerService.getCustomer(cv.getCustomer_id());
					operator = ldap.getOperatorBycustId(String.valueOf(cv.getCustomer_id()));
					list = custphoneService.listCustphone(cv.getCustomer_id());
					insuranceInfo = feeInfoService.getinsuranceInfo(vehicle_id);
				}
			}
		} else if (cust_id != null) {
			cust = customerService.getCustomer(cust_id);
			operator = ldap.getOperatorBycustId(String.valueOf(cust_id));
			collection = collectionService.getCollectionByCustId(cust.getCustomer_id());
			list = custphoneService.listCustphone(cust.getCustomer_id());
			List<Vehicle> vehicles = vehicleService.getVehiclesByCustid(cust_id);
			if (vehicles.size() > 0) {
				vehicle = vehicles.get(0);
				List<Unit> unitList = unitService.getByVehicle_id(vehicle.getVehicle_id());
				if (unitList.size() > 0) {
					unit = unitList.get(0);
					collection = collectionService.getCollectionByUnit(unit.getUnit_id(), unit.getCustomer_id());
					collections = collectionService.getCollections(unit.getCustomer_id());
					barcodes = barcodeService.getByUnit_id(unit.getUnit_id());
					drivers = driverService.getDrivers(unit.getVehicle_id());
					serviceInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 1);
					simfeeInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 2);
					insuranceInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 3);
					webgisInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 4);
					preloadInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 101);
				} else {
					List<CustVehicle> cvList = custVehicleService.getByCustId(cust_id);
					collections = collectionService.getCollections(cust_id);
					if (cvList.size() > 0) {
						drivers = driverService.getDrivers(cvList.get(0).getVehicle_id());
					}
				}
			} else {
				result.setCustomer(cust);
				result.setCollection(collection);
				result.setCustphones(list);
				request.getSession().setAttribute(SystemConst.CUST_INFO, result);
				return result;
			}
		}
		// String model = modelService.getModelByid(vehicle.getModel());
		String unittype = null;
		if (unit != null) {
			unittype = unitTypeService.getUnittypeByid(unit.getUnittype_id());
		}

		// 填充UBI保险销售员 @2016年4月21日17:11:52
		if (vehicle.getUbi_sales_id() > 0) {
			UbiSales sales = this.customerService.getUbiSales(vehicle.getUbi_sales_id());
			if (sales != null) {
				result.setSales(sales);
			}
		}

		List<Stop> stops = stopService.getListByid(vehicle.getVehicle_id());
		String sname = "";// 4s店名称
		CommonCompany company = ldap.getCompanyById(String.valueOf(vehicle.getId_4s()));
		if (company != null) {
			sname = company.getCompanyname();
		}
		String s_name = "";// 保险公司id
		Insurer insurer = null;
		if (vehicle.getInsurance_id() == null) {
			insurer = customerService.get(Insurer.class, 0);
		} else {
			insurer = customerService.get(Insurer.class, vehicle.getInsurance_id());
		}
		if (insurer != null) {
			s_name = insurer.getS_name();
		}
		// result.setModel(model);
		result.setUnittype(unittype);
		result.setUnit(unit);
		if (unit != null) {
			Preload sim = preloadService.getPreloadByCl(unit.getCall_letter());
			result.setSim(sim);
		}
		result.setVehicle(vehicle);
		if (operator != null) {
			result.setLoginname(operator.getLoginname());
			result.setMobile(operator.getMobile());
		}
		result.setCustomer(cust);
		result.setCollection(collection);
		result.setCollections(collections);
		result.setCustphones(list);
		result.setBarcodes(barcodes);
		result.setDrivers(drivers);
		result.setServiceInfo(serviceInfo);
		result.setSimfeeInfo(simfeeInfo);
		result.setInsuranceInfo(insuranceInfo);
		result.setWebgisInfo(webgisInfo);
		result.setHmfeeInfo(preloadInfo);
		result.setStops(stops);
		result.setSname(sname);
		result.setS_name(s_name);
		// 中间客户
		MidCust midCust = null;
		if (unit != null) {
			midCust = midCustService.getMidCustByUnitid(unit.getUnit_id());
		}
		if (midCust != null) {
			Long guarantee_id = midCust.getCustomer_id();
			Customer midCustomer = customerService.getCustomer(guarantee_id);
			String guarantee = midCustomer.getCustomer_name();
			result.setGuarantee(guarantee);
			result.setGuarantee_id(guarantee_id);
		}
		request.getSession().setAttribute(SystemConst.CUST_INFO, result);
		return result;
	}

	@RequestMapping(value = "/getLargeCustInfo", method = RequestMethod.POST)
	public @ResponseBody CustInfo getLargeCustInfo(@RequestBody CustInfo custInfo, BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		Long cust_id = custInfo.getCust_id();
		Long unit_id = custInfo.getUnit_id();
		Long vehicle_id = custInfo.getVehicle_id();
		Vehicle vehicle = new Vehicle();
		Unit unit = new Unit();
		Customer cust = new Customer();
		Collection collection = null;
		CustInfo result = new CustInfo();
		if (unit_id != null) {
			unit = unitService.getUnitByid(unit_id);
			vehicle = vehicleService.getVehicleByid(unit.getVehicle_id());
			cust = customerService.getCustomer(unit.getCustomer_id());
		} else if (vehicle_id != null) {
			vehicle = vehicleService.getVehicleByid(vehicle_id);
			List<Unit> unitList = unitService.getByVehicle_id(vehicle_id);
			if (unitList.size() > 0) {
				unit = unitList.get(0);
				cust = customerService.getCustomer(unit.getCustomer_id());
			}
		} else if (cust_id != null) {
			cust = customerService.getCustomer(cust_id);
			List<Vehicle> vehicles = vehicleService.getVehiclesByCustid(cust_id);
			if (vehicles.size() > 0) {
				vehicle = vehicles.get(0);
				List<Unit> unitList = unitService.getByVehicle_id(vehicle.getVehicle_id());
				if (unitList.size() > 0) {
					unit = unitList.get(0);
				}
			}
		}
		cust_id = cust.getCustomer_id();
		collection = collectionService.getCollectionByCustId(cust.getCustomer_id());
		CustSales custSales = customerService.getCustSales(cust_id);
		LargeCustLock cust_lock = customerService.get(LargeCustLock.class, cust_id);
		result.setCustomer(cust);
		result.setCollection(collection);
		result.setLargeCustLock(cust_lock);
		if (null != custSales)
			result.setManagerName(custSales.getManager_name());
		request.getSession().setAttribute(SystemConst.CUST_INFO, result);
		return result;
	}

	@RequestMapping(value = "/saveCollection", method = RequestMethod.POST)
	public @ResponseBody HashMap saveCollection(@RequestBody Collection collection, BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String userId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_ID);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		// 查询托收计费是否锁定
		List<Long> list = datalockService.getLockCustomer();
		Long customer_id = collection.getCustomer_id();
		if (list.contains((Object) customer_id)) {
			resultMap.put(SystemConst.SUCCESS, false);
			resultMap.put(SystemConst.MSG, "操作失败，该客户正在进行托收计费不能修改托收资料!");
			return resultMap;
		}
		if (collection.getCollection_id() != null) {
			Collection oldcollection = collectionService.get(Collection.class, collection.getCollection_id());
			oldcollection.setAc_no(collection.getAc_no());
			oldcollection.setAc_name(collection.getAc_name());
			oldcollection.setAgency(collection.getAgency());
			oldcollection.setBank(collection.getBank());
			oldcollection.setBank_code(collection.getBank_code());
			oldcollection.setPost_code(collection.getPost_code());
			oldcollection.setAc_type(collection.getAc_type());
			oldcollection.setIs_delivery(collection.getIs_delivery());
			oldcollection.setAddressee(collection.getAddressee());
			oldcollection.setPost_code(collection.getPost_code());
			oldcollection.setPay_ct_no(collection.getPay_ct_no());
			oldcollection.setProvince(collection.getProvince());
			oldcollection.setCity(collection.getCity());
			oldcollection.setArea(collection.getArea());
			oldcollection.setAddress(collection.getAddress());
			oldcollection.setAc_id_no(collection.getAc_id_no());
			collection.setOp_id(Long.valueOf(userId));
			collectionService.update(oldcollection);
		} else if (StringUtils.isNotBlank(collection.getAc_no()) && StringUtils.isNotBlank(collection.getAc_name())) {
			Collection oldCollection = collectionService.getCollectionByCustId(customer_id);
			if (oldCollection == null) {
				collection.setOp_id(Long.valueOf(userId));
				collection.setAc_id_no("");
				collection.setFee_cycle(1);
				collection.setPrint_freq(1);
				collection.setCreate_date(new Date());
				collectionService.add(collection);
			}
		}
		resultMap.put(SystemConst.SUCCESS, true);
		resultMap.put(SystemConst.MSG, "操作成功");
		return resultMap;
	}

	@RequestMapping(value = "/getCustInfoForPolicy", method = RequestMethod.POST)
	public @ResponseBody CustInfo getCustInfoForPolicy(@RequestBody CustInfo custInfo, BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		Long cust_id = custInfo.getCust_id();
		Long vehicle_id = custInfo.getVehicle_id();
		Long unit_id = custInfo.getUnit_id();
		Customer cust = null;
		Vehicle vehicle = null;
		Unit unit = null;

		CustInfo result = new CustInfo();
		if (unit_id != null) {
			unit = unitService.getUnitByid(unit_id);
			vehicle = vehicleService.getVehicleByid(unit.getVehicle_id());
			cust = customerService.getCustomer(unit.getCustomer_id());
		} else if (vehicle_id != null) {
			vehicle = vehicleService.getVehicleByid(vehicle_id);
			List<Unit> unitList = unitService.getByVehicle_id(vehicle_id);
			if (unitList.size() > 0) {
				unit = unitList.get(0);
				cust = customerService.getCustomer(unit.getCustomer_id());
			}
		} else if (cust_id != null) {
			cust = customerService.getCustomer(cust_id);
			List<Vehicle> vehicles = vehicleService.getVehiclesByCustid(cust_id);
			if (vehicles.size() > 0) {
				vehicle = vehicles.get(0);
				String model = modelService.getModelByid(vehicle.getModel());
				result.setModel(model);
				List<Unit> unitList = unitService.getByVehicle_id(vehicle.getVehicle_id());
				if (unitList.size() > 0) {
					unit = unitList.get(0);
					String unittype = unitTypeService.getUnittypeByid(unit.getUnittype_id());
					result.setUnittype(unittype);
					Barcode barcode = barcodeService.getByUnit_idAndType(unit.getUnit_id(), 1);
					result.setTbox_code(barcode.getContent());
				}
			}
		}

		result.setUnit(unit);
		result.setVehicle(vehicle);
		result.setCustomer(cust);
		request.getSession().setAttribute(SystemConst.CUST_INFO, result);
		return result;
	}

	@RequestMapping(value = "/getCustInfoForHm", method = RequestMethod.POST)
	public @ResponseBody CustInfo getCustInfoForHm(@RequestBody CustInfo custInfo, BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		Long cust_id = custInfo.getCust_id();
		Long vehicle_id = custInfo.getVehicle_id();
		Long unit_id = custInfo.getUnit_id();
		Customer cust = null;
		Vehicle vehicle = null;
		Unit unit = null;
		FeeInfo hmfeeInfo = null;// 服务费
		Pack pack = null;
		Combo combo = null;

		CustInfo result = new CustInfo();
		if (unit_id != null) {
			unit = unitService.getUnitByid(unit_id);
			vehicle = vehicleService.getVehicleByid(unit.getVehicle_id());
			cust = customerService.getCustomer(unit.getCustomer_id());
		} else if (vehicle_id != null) {
			vehicle = vehicleService.getVehicleByid(vehicle_id);
			List<Unit> unitList = unitService.getByVehicle_id(vehicle_id);
			if (unitList.size() > 0) {
				unit = unitList.get(0);
				cust = customerService.getCustomer(unit.getCustomer_id());
			}
		} else if (cust_id != null) {
			cust = customerService.getCustomer(cust_id);
			List<Vehicle> vehicles = vehicleService.getVehiclesByCustid(cust_id);
			if (vehicles.size() > 0) {
				vehicle = vehicles.get(0);
				String model = modelService.getModelByid(vehicle.getModel());
				result.setModel(model);
				List<Unit> unitList = unitService.getByVehicle_id(vehicle.getVehicle_id());
				if (unitList.size() > 0) {
					unit = unitList.get(0);
				}
			}
		}

		if (null != unit) {
			String unittype = unitTypeService.getUnittypeByid(unit.getUnittype_id());
			result.setUnittype(unittype);
			hmfeeInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 101);
			Barcode barcode = barcodeService.getByUnit_idAndType(unit.getUnit_id(), 1);
			if (null != barcode)
				result.setTbox_code(barcode.getContent());
		}
		if (null != vehicle && vehicle.getId_4s() != null) {
			CommonCompany company = ldap.getCompanyById(vehicle.getId_4s().toString());
			if (null != company) {
				result.setSname(company.getCompanyname());
			}
		}
		if (null != hmfeeInfo) {
			pack = packService.get(Pack.class, hmfeeInfo.getItem_id());
			if (null != pack)
				combo = comboService.get(Combo.class, pack.getCombo_id());
		}
		result.setPack(pack);
		result.setCombo(combo);
		result.setUnit(unit);
		result.setVehicle(vehicle);
		result.setCustomer(cust);
		result.setHmfeeInfo(hmfeeInfo);
		/*
		 * result.setServiceInfo(serviceInfo); result.setSimfeeInfo(simfeeInfo);
		 * result.setInsuranceInfo(insuranceInfo);
		 * result.setWebgisInfo(webgisInfo);
		 */
		request.getSession().setAttribute(SystemConst.CUST_INFO, result);
		return result;
	}

	@RequestMapping(value = "/getCustInfoFeeMsg", method = RequestMethod.POST)
	public @ResponseBody CustInfo getCustInfoFeeMsg(@RequestBody CustInfo custInfo, BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		Long cust_id = custInfo.getCust_id();
		Long vehicle_id = custInfo.getVehicle_id();
		Long unit_id = custInfo.getUnit_id();
		Customer cust = null;
		Vehicle vehicle = null;
		Unit unit = null;
		Collection collection = null;
		FeeInfo serviceInfo = null;// 服务费
		FeeInfo simfeeInfo = null;// SIM卡费
		FeeInfo insuranceInfo = null;// 保险费
		FeeInfo webgisInfo = null;// 网上查车计费
		FeeInfo hmfeeInfo = null;// 前装服务费
		CustInfo result = new CustInfo();
		if (unit_id != null) {
			unit = unitService.getUnitByid(unit_id);
			vehicle = vehicleService.getVehicleByid(unit.getVehicle_id());
			cust = customerService.getCustomer(unit.getCustomer_id());
			collection = collectionService.getCollectionByCustId(unit.getCustomer_id());
		} else if (vehicle_id != null) {
			vehicle = vehicleService.getVehicleByid(vehicle_id);
			List<Unit> unitList = unitService.getByVehicle_id(vehicle_id);
			if (unitList.size() > 0) {
				unit = unitList.get(0);
				cust = customerService.getCustomer(unit.getCustomer_id());
				collection = collectionService.getCollectionByCustId(unit.getCustomer_id());
			}
		} else if (cust_id != null) {
			cust = customerService.getCustomer(cust_id);
			collection = collectionService.getCollectionByCustId(cust.getCustomer_id());
			List<Vehicle> vehicles = vehicleService.getVehiclesByCustid(cust_id);
			if (vehicles.size() > 0) {
				vehicle = vehicles.get(0);
				String model = modelService.getModelByid(vehicle.getModel());
				result.setModel(model);
				List<Unit> unitList = unitService.getByVehicle_id(vehicle.getVehicle_id());
				if (unitList.size() > 0) {
					unit = unitList.get(0);
				}
			}
		}

		if (null != unit) {
			String unittype = unitTypeService.getUnittypeByid(unit.getUnittype_id());
			result.setUnittype(unittype);
			serviceInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 1);
			simfeeInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 2);
			insuranceInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 3);
			webgisInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 4);
			hmfeeInfo = feeInfoService.getFeeInfo(unit.getUnit_id(), 101);
		}

		serviceInfo = getMonthAndDay(serviceInfo);
		simfeeInfo = getMonthAndDay(simfeeInfo);
		insuranceInfo = getMonthAndDay(insuranceInfo);
		webgisInfo = getMonthAndDay(webgisInfo);
		hmfeeInfo = getMonthAndDay(hmfeeInfo);

		result.setUnit(unit);
		result.setVehicle(vehicle);
		result.setCustomer(cust);
		result.setCollection(collection);
		result.setServiceInfo(serviceInfo);
		result.setSimfeeInfo(simfeeInfo);
		result.setInsuranceInfo(insuranceInfo);
		result.setWebgisInfo(webgisInfo);
		result.setHmfeeInfo(hmfeeInfo);
		request.getSession().setAttribute(SystemConst.CUST_INFO, result);
		return result;
	}

	public FeeInfo getArrearageMag(FeeInfo info) {
		if (null != info) {
			// FeePayment pay_info =
			// feePaymentService.getLastFeePayment(info.getUnit_id(),
			// info.getFeetype_id());
			Date date = new Date();
			if (info.getFee_sedate().getTime() < date.getTime()) {
				long total_days = (date.getTime() - info.getFee_sedate().getTime()) / (3600 * 24 * 1000);
				Integer a_days = (int) (total_days % 30);
				Integer a_months = (int) (total_days / 30);
				Integer arrearage_fee = (int) (total_days * info.getMonth_fee() / 30);
				info.setA_days(a_days);
				info.setA_months(a_months);
				info.setArrearage_fee(arrearage_fee);
			}
		}
		return info;
	}

	public FeeInfo getMonthAndDay(FeeInfo info) {
		if (null != info) {
			Date startDate = info.getFee_sedate();
			Date endDate = new Date();
			if (startDate.getTime() < endDate.getTime()) {
				int monthday = 0;
				int arrearage_fee = 0;
				try {
					Calendar starCal = Calendar.getInstance();
					starCal.setTime(startDate);
					int sYear = starCal.get(Calendar.YEAR);
					int sMonth = starCal.get(Calendar.MONTH);
					int sDay = starCal.get(Calendar.DATE);

					Calendar endCal = Calendar.getInstance();
					endCal.setTime(endDate);
					int eYear = endCal.get(Calendar.YEAR);
					int eMonth = endCal.get(Calendar.MONTH);
					int eDay = endCal.get(Calendar.DATE);

					monthday = ((eYear - sYear) * 12 + (eMonth - sMonth));

					if (sDay > eDay && monthday >= 1) {
						monthday = monthday - 1;
					}
					starCal.add(starCal.MONTH, monthday);
					int total = 0;
					while (startDate.getTime() < endDate.getTime()) {
						starCal.add(starCal.DATE, 1);// 月份加一}
						startDate = starCal.getTime();
						total++;
					}
					if (total > 0)
						total = total - 1;
					// 封装欠费数据
					info.setA_days(total);
					info.setA_months(monthday);
					arrearage_fee = (int) (monthday * info.getMonth_fee() + info.getMonth_fee() * total / 30);
					info.setArrearage_fee(arrearage_fee);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		return info;
	}

	@RequestMapping(value = "/getCollectionByctno", method = RequestMethod.POST)
	public @ResponseBody HashMap getCollectionByctno(@RequestBody Collection collection, BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		Collection result = collectionService.getCollectionByctno(collection);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		if (result != null) {
			resultMap.put("success", true);
		} else {
			resultMap.put("success", false);
		}
		return resultMap;
	}

	public String getHttpResponse(String path, Map<String, String> m) throws Exception {
		String obdUrl = systemconfig.getObdconnectUrl();
		ObjectMapper mapper = new ObjectMapper();
		URL url = new URL(obdUrl + path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");// 提交模式
		conn.setRequestProperty("Content-Type", "text/plain");
		conn.setConnectTimeout(3000);// 连接超时 单位毫秒
		conn.setReadTimeout(3000);// 读取超时 单位毫秒
		conn.setDoOutput(true);// 是否输入参数
		conn.setDoInput(true);
		conn.connect();
		byte[] bytes = mapper.writeValueAsString(m).getBytes("UTF-8");
		conn.getOutputStream().write(bytes);// 输入参数
		conn.getOutputStream().flush();
		conn.getOutputStream().close();

		InputStream in = conn.getInputStream();
		String returnValue = "";
		byte[] buffer = new byte[512];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		for (int len = 0; (len = in.read(buffer)) > 0;) {
			baos.write(buffer, 0, len);
		}
		returnValue = new String(baos.toByteArray(), "UTF-8");
		baos.flush();
		baos.close();
		in.close();
		System.out.println("服务端返回的内容：" + returnValue);
		conn.disconnect();
		return returnValue;
	}

	public static HashMap<String, Object> parseJSON2Map(String jsonStr) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		// 最外层解析
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			// 如果内层还是数组的话，继续解析
			if (v instanceof JSONArray) {
				List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(parseJSON2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}

	public static List<HashMap<String, Object>> parseJSON2List(Object jsonStr) {
		JSONArray jsonArr = JSONArray.fromObject(jsonStr);
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		Iterator<JSONObject> it = jsonArr.iterator();
		while (it.hasNext()) {
			JSONObject json2 = it.next();
			list.add(parseJSON2Map(json2.toString()));
		}
		return list;
	}

	@RequestMapping(value = "/checkConnect")
	@LogOperation(description = "检查网络连接", type = SystemConst.OPERATELOG_SEARCHKEY, model_id = 2)
	public @ResponseBody HashMap<String, Object> checkConnect(HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("检查网络连接开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String ip = systemconfig.getObdconnectIp();
		boolean flag = false;
		String msg = "obd故障查询系统网络异常！";
		String obd_port = systemconfig.getObdconnectPort();
		int port = Integer.valueOf(obd_port);
		Socket my = new Socket(); // 实例化socket对象
		try {
			InetSocketAddress mySock = new InetSocketAddress(InetAddress.getByName(ip), port);// 假设连接目标的80端口
			my.connect(mySock, 100);// 100毫秒超时
			System.out.println("连接成功!");
			flag = true;
			msg = SystemConst.OP_SUCCESS;
		} catch (SocketTimeoutException e) {// 捉到了!
			System.out.println("cannot connect ...");
		} catch (Exception e) {// 其他有IOException等
			System.out.println("Exception,cannot connect ...");
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("检查网络连接结束");
		}
		return resultMap;
	}

	@RequestMapping(value = "/getObdErrorMsg")
	public @ResponseBody HashMap<String, Object> getObdErrorMsg(String call_letter, HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得车辆最新故障码信息开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String path = "obd/getObdErrorMsg";
			boolean flag = false;
			String obdErrorMsg = "暂无故障信息";
			Object datas = new Object();
			Map<String, String> m = new HashMap<String, String>();
			// m.put("call_letter", "13912345001");
			m.put("call_letter", call_letter);
			String msg = getHttpResponse(path, m);
			HashMap<String, Object> map = null;
			if (StringUtils.isNotBlank(msg)) {
				map = parseJSON2Map(msg);
				if (null != map) {
					Object mark = map.get(SystemConst.SUCCESS);
					String remark = null;
					if (null != mark) {
						remark = mark.toString();
					}
					if (null != remark && remark.equals("true")) {
						flag = true;
						// 故障码信息
						datas = map.get("datas");
						if (null != datas && !datas.toString().equals("null")) {
							List<HashMap<String, Object>> results = parseJSON2List(datas);
							obdErrorMsg = packObdErrorMsg(results);
						}
					}
				}
			}
			resultMap.put(SystemConst.SUCCESS, flag);
			resultMap.put("obdErrorMsg", obdErrorMsg);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得车辆最新故障码信息结束");
		}
		return resultMap;
	}

	/**
	 * 
	 * @Title:getObdMsg
	 * @Description:获取车辆最新obd信息
	 * @param call_letter
	 * @param request
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	@RequestMapping(value = "/getObdMsg")
	public @ResponseBody HashMap<String, Object> getObdMsg(String call_letter, HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得车辆最新OBD信息开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String path = "obd/getObdMsg";
			boolean flag = false;
			String k_msg = SystemConst.OP_FAILURE;
			Object datas = new Object();
			Map<String, String> m = new HashMap<String, String>();
			// m.put("call_letter", "13912345001");
			m.put("call_letter", call_letter);
			String msg = getHttpResponse(path, m);
			HashMap<String, Object> map = null;
			if (StringUtils.isNotBlank(msg)) {
				map = parseJSON2Map(msg);
				if (null != map) {
					Object mark = map.get(SystemConst.SUCCESS);
					String remark = null;
					if (null != mark) {
						remark = mark.toString();
					}
					if (null != remark && remark.equals("true")) {
						flag = true;
						datas = map.get("datas");
						k_msg = SystemConst.SUCCESS;
					}
				}
			}
			resultMap.put("datas", datas);
			resultMap.put(SystemConst.SUCCESS, flag);
			resultMap.put("msg", k_msg);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得车辆最新OBD码信息结束");
		}
		return resultMap;
	}

	public String packObdErrorMsg(List<HashMap<String, Object>> results) {
		String msg = "暂无故障信息";
		if (results != null && results.size() > 0) {
			msg = "";
			for (HashMap<String, Object> map : results) {
				Object sysId = map.get("sysId");
				Object obdErrorMsg = map.get("obdErrorMsg");
				if (StringUtils.isNotNullOrEmpty(sysId) && StringUtils.isNotNullOrEmpty(obdErrorMsg)) {
					msg += SystemConst.errorMap.get(sysId);
					msg = msg + ":" + obdErrorMsg.toString() + ";";
				}
			}
		}
		if (msg.endsWith(";") && msg.length() > 2) {
			msg = msg.substring(0, msg.length() - 2);
		} else if (StringUtils.isBlank(msg)) {
			msg = "暂无故障信息";
		}
		return msg;
	}

	@RequestMapping(value = "/getObdErrorMsgList")
	public @ResponseBody Page<HashMap<String, Object>> getObdErrorMsgList(@RequestBody PageSelect<Map<String, Object>> pageSelect,
			BindingResult bindingResult, HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得故障码列表开始");
		}
		Map conditionMap = pageSelect.getFilter();
		int total = 0;
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> results = null;
		try {
			String path = "obd/getObdErrorMsgList";
			HashMap<String, String> m = new HashMap<String, String>();
			if (!StringUtils.isNotNullOrEmpty(conditionMap.get("startDate"))) {
				conditionMap.put("startDate", DateUtil.getBeforeTenDay());
			}
			if (!StringUtils.isNotNullOrEmpty(conditionMap.get("endDate"))) {
				conditionMap.put("endDate", DateUtil.formatlastBeforeday());
			}
			if (StringUtils.isNotNullOrEmpty(conditionMap.get("call_letter")) && StringUtils.isNotNullOrEmpty(conditionMap.get("startDate"))
					&& StringUtils.isNotNullOrEmpty(conditionMap.get("endDate"))) {
				m.put("call_letter", conditionMap.get("call_letter").toString());
				m.put("start_date", DateUtil.dateforSearch(conditionMap, "startDate"));
				m.put("end_date", DateUtil.dateforSearch(conditionMap, "endDate"));
			}
			/*
			 * m.put("call_letter", "13912345001"); m.put("start_date",
			 * "2014-12-10 08:00:00"); m.put("end_date", "2014-12-22 08:00:00");
			 */
			String msg = getHttpResponse(path, m);
			HashMap<String, Object> map = null;
			if (StringUtils.isNotBlank(msg)) {
				map = parseJSON2Map(msg);
				if (null != map) {
					Object flag = map.get(SystemConst.SUCCESS);
					String remark = null;
					if (null != flag) {
						remark = flag.toString();
					}
					Object datas = map.get("datas");
					if (null != remark && remark.equals("true") && null != datas && !datas.toString().equals("null")) {
						results = parseJSON2List(datas);
						if (results != null && results.size() < 1) {
							return PageUtil.getPage(0, pageSelect.getPageNo(), null, pageSelect.getPageSize());
						} else {
							total = results.size();
							int start = (pageSelect.getPageNo() - 1) * pageSelect.getPageSize();
							int end = pageSelect.getPageNo() * pageSelect.getPageSize();
							int num = total < end ? total : end;
							for (int i = start; i < num; i++) {
								HashMap<String, Object> errormap = new HashMap<String, Object>();
								String sysName = SystemConst.errorMap.get(results.get(i).get("sysId"));
								errormap.put("sysName", sysName);
								errormap.put("obdErrorMsg", results.get(i).get("obdErrorMsg"));
								errormap.put("date", results.get(i).get("date"));
								list.add(errormap);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得故障码列表结束");
		}
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

	/**
	 * 更新入网车辆密码
	 * 
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/updateNetworkPwd", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateNetworkPwd(@RequestBody Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		String userId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_ID);
		String username = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_USERNAME);
		try {
			map = networkService.updateNetworkPwd(param, userId, username);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "更新密码错误，原因:" + e.getMessage());
		}
		return map;
	}

	/**
	 * 查询登录名/电话是否重复
	 * 
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/checkOperator", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> checkOperator(@RequestBody Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = networkService.checkOperator(param);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", e.getMessage());
		}
		return map;
	}

	/**
	 * 清除用户及车辆隐私密码
	 * 
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/clearPrivatePwd", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> clearPrivatePwd(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws SystemException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Long customerId = Utils.isNullOrEmpty(params.get("customerId")) ? null : Long.valueOf(params.get("customerId").toString());
			Long vehicleId = Utils.isNullOrEmpty(params.get("vehicleId")) ? null : Long.valueOf(params.get("vehicleId").toString());
			if (customerId == null || vehicleId == null) {
				result.put("success", false);
				result.put("msg", "参数为空!");
				return result;
			}
			result = networkService.updatePrivatePwd(customerId, vehicleId);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}

	/**
	 * 同步车辆服务密码到海马汽车
	 * 
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "changeServicePwdT2Hm", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> changeServicePwdT2Hm(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws SystemException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Long customerId = Utils.isNullOrEmpty(params.get("customerId")) ? null : Long.valueOf(params.get("customerId").toString());
			Long vehicleId = Utils.isNullOrEmpty(params.get("vehicleId")) ? null : Long.valueOf(params.get("vehicleId").toString());
			if (customerId == null || vehicleId == null) {
				result.put("success", false);
				result.put("msg", "参数为空!");
				return result;
			}
			result = networkService.changeServicePwd2Hm(customerId, vehicleId);
		} catch (Exception e) {

		}
		return result;
	}
}