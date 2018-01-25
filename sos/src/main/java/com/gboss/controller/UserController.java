package com.gboss.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ldap.objectClasses.CommonCompany;
import ldap.objectClasses.CommonModule;
import ldap.objectClasses.CommonOperator;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;
import ldap.util.LDAPSecurityUtils;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.Collection;
import com.gboss.pojo.Customer;
import com.gboss.pojo.Insurer;
import com.gboss.pojo.Linkman;
import com.gboss.pojo.Maxid;
import com.gboss.pojo.Model;
import com.gboss.pojo.Operatelog;
import com.gboss.pojo.Task;
import com.gboss.pojo.UbiSales;
import com.gboss.pojo.web.CustInfo;
import com.gboss.pojo.web.VerifyPOJO;
import com.gboss.service.CarTypeService;
import com.gboss.service.CollectionService;
import com.gboss.service.CustomerService;
import com.gboss.service.CustphoneService;
import com.gboss.service.FeeInfoService;
import com.gboss.service.InsurerService;
import com.gboss.service.MaxidService;
import com.gboss.service.OperatelogService;
import com.gboss.service.TaskService;
import com.gboss.util.DesEncrypt;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.Utils;
import com.gboss.util.excel.CreateExcel_PDF_CSV;
import com.gboss.util.page.Page;

@Controller
public class UserController extends BaseController {

	@Autowired
	private OperatelogService operatelogService;

	@Autowired
	private CustphoneService custphoneService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CarTypeService carTypeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private FeeInfoService feeInfoService;

	@Autowired
	private MaxidService maxidService;

	@Autowired
	private InsurerService insurerService;

	@Autowired
	private CollectionService collectionService;

	private OpenLdap ldap = OpenLdapManager.getInstance();

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> register(@RequestBody CommonOperator user, BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
		String username = "";
		if (principal != null) {
			username = principal.getName();
		} else {
			map.put("success", false);
			return map;
		}
		Object oper = request.getSession().getAttribute(SystemConst.ACCOUNT_USER);
		if (oper != null) {
			map.put("success", true);
			return map;
		}
		CommonOperator operator = ldap.getOperator(username);
		String companyId = ldap.getCompanyByOrgId(operator.getCompanynos().get(0)).getCompanyno();
		// 写日志,因为要做判断所以不通过注释方式写了
		Operatelog log = new Operatelog();
		log.setUser_id(operator.getOpid() == null ? null : Long.valueOf(operator.getOpid()));
		log.setSubco_no(Long.valueOf(companyId));
		log.setOp_type(Integer.valueOf(SystemConst.OPERATELOG_LOGIN));
		log.setRemark(operator.getOpname());
		operatelogService.add(log);
		// 写Session
		request.getSession().setAttribute(SystemConst.ACCOUNT_USER, operator);
		request.getSession().setAttribute(SystemConst.ACCOUNT_ID, operator.getOpid());
		request.getSession().setAttribute(SystemConst.ACCOUNT_USERNAME, operator.getOpname());
		// 如果一个用户属于多个机构就不好办了，所以暂时屏蔽掉一个用户可能属于多个机构的情况
		request.getSession().setAttribute(SystemConst.ACCOUNT_ORGID, operator.getCompanynos().get(0));
		request.getSession().setAttribute(SystemConst.ACCOUNT_COMPANYID, companyId);
		request.getSession().setAttribute(SystemConst.ACCOUNT_ROLEIDS, operator.getRoleid());
		map.put("success", true);
		return map;
	}

	@RequestMapping(value = "/getManagers", method = RequestMethod.POST)
	public @ResponseBody List<CommonOperator> getManagers(@RequestBody VerifyPOJO verify, BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String opname = verify.getParameter();
		List<CommonOperator> list = ldap.getManagersByName(opname);
		return list;
	}

	@RequestMapping(value = "/getCustInfoMsg")
	public @ResponseBody List<HashMap<String, Object>> getCustInfoMsg(HttpServletRequest request) throws SystemException {
		List<HashMap<String, Object>> results = new ArrayList<HashMap<String, Object>>();
		try {
			CustInfo custInfo = (CustInfo) request.getSession().getAttribute(SystemConst.CUST_INFO);
			Boolean flag = true;
			Boolean remark = true;
			Boolean mark = true;
			if (null == custInfo || (custInfo != null && custInfo.getVehicle() == null))
				flag = false;
			if (null == custInfo || (custInfo != null && custInfo.getCustomer() == null))
				remark = false;
			if (null == custInfo || (custInfo != null && custInfo.getUnit() == null))
				mark = false;

			// 获取客户基本信息
			HashMap<String, Object> map1 = new HashMap<String, Object>();
			List<HashMap<String, Object>> items1 = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> map11 = new HashMap<String, Object>();
			HashMap<String, Object> map12 = new HashMap<String, Object>();
			HashMap<String, Object> map13 = new HashMap<String, Object>();
			map1.put("title", "基本资料");
			map11.put("key", "客户名");
			String name = "";
			if (remark) {
				name = custInfo.getCustomer().getCustomer_name();
			}
			map11.put("value", name);
			items1.add(map11);
			map12.put("key", "客户性别");
			String sex = "";
			if (remark) {
				Integer sex_value = custInfo.getCustomer().getSex();
				if (sex_value != null && sex_value == 0) {
					sex = "男";
				}
				if (sex_value != null && sex_value == 1) {
					sex = "女";
				}
			}
			map12.put("value", sex);
			items1.add(map12);
			map13.put("key", "联系电话");
			String phone = "";
			if (remark) {
				List<Linkman> custphones = custInfo.getCustphones();
				if (null != custphones && custphones.size() > 0) {
					phone = custphones.get(0).getPhone();
				}
			}
			map13.put("value", phone);
			items1.add(map13);
			map1.put("items", items1);

			results.add(map1);
			String mode_name = "";

			// 获取车辆信息
			HashMap<String, Object> map2 = new HashMap<String, Object>();
			List<HashMap<String, Object>> items2 = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> map21 = new HashMap<String, Object>();
			HashMap<String, Object> map22 = new HashMap<String, Object>();
			HashMap<String, Object> map23 = new HashMap<String, Object>();
			HashMap<String, Object> map24 = new HashMap<String, Object>();
			map2.put("title", "车辆资料");
			map21.put("key", "车牌号码");
			String car_num = "";
			if (flag) {
				car_num = custInfo.getVehicle().getPlate_no();
			}
			map21.put("value", car_num);
			items2.add(map21);

			map22.put("key", "车型");
			if (flag) {
				Long mode_id = custInfo.getVehicle().getModel();
				if (null != mode_id) {
					Model model = carTypeService.get(Model.class, mode_id);
					mode_name = model.getName();
				}
			}
			map22.put("value", mode_name);
			items2.add(map22);

			map23.put("key", "终端型号");
			String code = "";
			if (mark) {
				code = custInfo.getUnit().getProduct_code();
			}
			map23.put("value", code);
			items2.add(map23);

			map24.put("key", "车载号码");
			String call_letter = "";
			if (mark) {
				call_letter = custInfo.getUnit().getCall_letter();
			}
			map24.put("value", call_letter);
			items2.add(map24);
			map2.put("items", items2);
			results.add(map2);

			// 获取车辆服务套餐
			HashMap<String, Object> map3 = new HashMap<String, Object>();
			List<HashMap<String, Object>> items3 = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> map31 = new HashMap<String, Object>();
			HashMap<String, Object> map32 = new HashMap<String, Object>();
			HashMap<String, Object> map33 = new HashMap<String, Object>();

			map3.put("title", "服务信息");
			map3.put("type", "2");
			if (flag) {
				List<HashMap<String, Object>> list = feeInfoService.getFeeInfoList(custInfo.getVehicle().getVehicle_id());
				if (null != list && list.size() > 0) {
					for (HashMap<String, Object> hmap : list) {
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("content", hmap.get("remark").toString());
						map.put("isurl", false);
						items3.add(map);
					}
				}
			}
			/*
			 * 
			 * map31.put("key", "服务情况"); String service = ""; if(flag){ service
			 * = "即将到期"; } map31.put("value", service); items3.add(map31);
			 * map32.put("key", "服务套餐"); String tc = ""; if(flag){ tc =
			 * "吉祥188元套餐"; } map32.put("value", tc); items3.add(map32);
			 * 
			 * map33.put("key", "定位情况"); String place =""; if(flag){ place =
			 * "2013-08-06 14:30:45"; } map33.put("value", place);
			 * items3.add(map33);
			 */

			map3.put("items", items3);
			results.add(map3);

			// 获取维修记录
			HashMap<String, Object> map4 = new HashMap<String, Object>();
			List<HashMap<String, Object>> items4 = new ArrayList<HashMap<String, Object>>();
			map4.put("title", "维修记录");
			map4.put("type", "2");

			if (flag) {
				List<Task> taskList = taskService.getTaskByVId(custInfo.getVehicle().getVehicle_id(), SystemConst.REPAIR_TASK);
				for (Task task : taskList) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("content", task.getStamp());
					map.put("isurl", true);
					map.put("id", task.getTask_id());
					items4.add(map);
				}
			}

			map4.put("items", items4);
			results.add(map4);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException();
		}
		return results;
	}

	/**
	 * 
	 * @Title:getTaskNo
	 * @Description:获取客户车辆信息
	 * @param request
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	@RequestMapping(value = "/getLinkMan", method = RequestMethod.POST)
	public @ResponseBody List<Linkman> getLinkMan(Long customer_id, HttpServletRequest request) throws SystemException {
		List<Linkman> LinkmanList = custphoneService.getLinkmanList(customer_id);
		return LinkmanList;
	}

	@RequestMapping(value = "/getCurrentCompany_cl", method = RequestMethod.POST)
	public @ResponseBody CommonCompany getCurrentCompany(String conpanyId, HttpServletRequest request) throws SystemException {
		CommonCompany company = new CommonCompany();
		if (conpanyId != null) {
			company = ldap.getCompanyById(conpanyId);
		}
		return company;
	}

	/**
	 * @Description 获取销售经理列表
	 * @param pageSelect
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/getCompanySaleManager_cl", method = RequestMethod.POST)
	public @ResponseBody Page<CommonOperator> getCompanySaleManager_cl(@RequestBody PageSelect<HashMap<String, Object>> pageSelect,
			BindingResult bindingResult, HttpServletRequest request) throws SystemException {
		String companyno = "";
		if (pageSelect != null) {
			Map map = pageSelect.getFilter();
			if (map != null) {
				companyno = map.get("companyno").toString();
			}
			// map.put("tStatus", -1);
			pageSelect.setFilter(map);
		}

		Page<CommonOperator> result = ldap.getPageSaleManager(pageSelect, companyno);
		return result;
	}

	@RequestMapping(value = "/getCompanySaleManager", method = RequestMethod.POST)
	public @ResponseBody Page<CommonOperator> getCompanySaleManager(@RequestBody PageSelect<HashMap<String, Object>> pageSelect,
			BindingResult bindingResult, HttpServletRequest request) throws SystemException {
		String companyno = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		Page<CommonOperator> result = ldap.getPageSaleManager(pageSelect, companyno);
		return result;
	}

	/**
	 * @Desc 获取UBI保险销售员
	 * @param pageSelect
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/getUbiSales", method = RequestMethod.POST)
	public @ResponseBody Page<UbiSales> getUbiSales(@RequestBody PageSelect<UbiSales> pageSelect,
			BindingResult bindingResult, HttpServletRequest request) throws SystemException {
		Page<UbiSales> result = this.customerService.listUbiSales(pageSelect);
		return result;
	}
	
	@RequestMapping(value = "/getCurrentOperator", method = RequestMethod.POST)
	public @ResponseBody CommonOperator getCurrentOperator(@RequestBody VerifyPOJO verify, BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		CommonOperator operator = (CommonOperator) request.getSession().getAttribute(SystemConst.ACCOUNT_USER);
		if (operator == null) {
			AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
			String username = "";
			if (principal != null) {
				username = principal.getName();
			} else {
				return null;
			}
			operator = ldap.getOperator(username);
		}
		return operator;
	}

	@RequestMapping(value = "/getCurrentModule", method = RequestMethod.POST)
	public @ResponseBody CommonModule getCurrentModule(@RequestBody VerifyPOJO verify, BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String url = verify.getParameter();
		if (StringUtils.isNotBlank(url)) {
			CommonModule module = ldap.getModuleByURL(url);
			return module;
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/getCurrentOrg", method = RequestMethod.POST)
	public @ResponseBody CommonCompany getCurrentOrg(@RequestBody VerifyPOJO verify, BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		CommonCompany company = new CommonCompany();
		String orgId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_ORGID);
		if (orgId != null) {
			company = ldap.getCompanyById(orgId);
		}
		return company;
	}

	@RequestMapping(value = "/getCurrentCompany", method = RequestMethod.POST)
	public @ResponseBody CommonCompany getCurrentCompany(@RequestBody VerifyPOJO verify, BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		CommonCompany company = new CommonCompany();
		String orgId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		if (orgId != null) {
			company = ldap.getCompanyById(orgId);
		}
		return company;
	}

	@RequestMapping(value = "/getOperators", method = RequestMethod.POST)
	public @ResponseBody List<CommonOperator> getOperators(@RequestBody VerifyPOJO verify, BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String opname = verify.getParameter();
		String companyno = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		List<CommonOperator> list = ldap.getOperators(opname, companyno);
		return list;
	}

	@RequestMapping(value = "/updateOperator", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateOperator(@RequestBody CommonOperator commonOperator, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		CommonOperator operator = (CommonOperator) request.getSession().getAttribute(SystemConst.ACCOUNT_USER);
		Map<String, Object> map = new HashMap<String, Object>();
		String dn = operator.getDn();
		// List companynos =
		// commonOperator.getCompanynos()==null?operator.getCompanynos():commonOperator.getCompanynos();
		// List roleid =
		// commonOperator.getRoleid()==null?operator.getRoleid():commonOperator.getRoleid();
		String url = operator.getMainUrl();
		String moduleid = operator.getMainModuleid();
		String str = "<" + SystemConst.SYSTEMID + ":";
		if (url == null) {
			url = "<" + SystemConst.SYSTEMID + ":" + commonOperator.getMainUrl() + ":" + SystemConst.SYSTEMID + ">";
		} else if (url.indexOf(SystemConst.SYSTEMID) != -1) {
			url = url.substring(0, url.indexOf(str) + str.length()) + commonOperator.getMainUrl()
					+ url.substring(url.indexOf(":" + SystemConst.SYSTEMID + ">"), url.length());
		} else {
			url = url + "<" + SystemConst.SYSTEMID + ":" + commonOperator.getMainUrl() + ":" + SystemConst.SYSTEMID + ">";
		}
		if (moduleid == null) {
			moduleid = "<" + SystemConst.SYSTEMID + ":" + commonOperator.getMainModuleid() + ":" + SystemConst.SYSTEMID + ">";
		} else if (moduleid.indexOf(SystemConst.SYSTEMID) != -1) {
			moduleid = moduleid.substring(0, moduleid.indexOf(str) + str.length()) + commonOperator.getMainModuleid()
					+ moduleid.substring(moduleid.indexOf(":" + SystemConst.SYSTEMID + ">"), moduleid.length());
		} else {
			moduleid = moduleid + "<" + SystemConst.SYSTEMID + ":" + commonOperator.getMainModuleid() + ":" + SystemConst.SYSTEMID + ">";
		}
		String mainUrl = commonOperator.getMainUrl() == null ? operator.getMainUrl() : url;
		String mainModuleId = commonOperator.getMainModuleid() == null ? operator.getMainModuleid() : moduleid;
		String idcard = commonOperator.getIdcard() == null ? operator.getIdcard() : commonOperator.getIdcard();
		String jobnumber = commonOperator.getJobnumber() == null ? operator.getJobnumber() : commonOperator.getJobnumber();
		String phone = commonOperator.getPhone() == null ? operator.getPhone() : commonOperator.getPhone();
		String sex = commonOperator.getSex() == null ? operator.getSex() : commonOperator.getSex();
		String fax = commonOperator.getFax() == null ? operator.getFax() : commonOperator.getFax();
		String mail = commonOperator.getMail() == null ? operator.getMail() : commonOperator.getMail();
		String post = commonOperator.getPost() == null ? operator.getPost() : commonOperator.getPost();
		String mobile = commonOperator.getMobile() == null ? operator.getMobile() : commonOperator.getMobile();
		String remark = commonOperator.getRemark() == null ? operator.getRemark() : commonOperator.getRemark();
		String[] keys = { "mainUrl", "mainModuleId", "idcard", "jobnumber", "phone", "sex", "fax", "mail", "post", "mobile", "remark" };
		Object[] values = new Object[11];
		values[0] = mainUrl;
		values[1] = mainModuleId;
		values[2] = idcard;
		values[3] = jobnumber;
		values[4] = phone;
		values[5] = sex;
		values[6] = fax;
		values[7] = mail;
		values[8] = post;
		values[9] = mobile;
		values[10] = remark;
		ldap.modifyInformations(dn, keys, values);
		CommonOperator oper = ldap.getOperator(operator.getLoginname());
		// 重新写session
		request.getSession().setAttribute(SystemConst.ACCOUNT_USER, oper);
		map.put("success", true);
		map.put("msg", "修改用户资料成功");
		return map;
	}

	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updatePassword(@RequestBody Map<String, String> mapInfo, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		CommonOperator operator = (CommonOperator) request.getSession().getAttribute(SystemConst.ACCOUNT_USER);
		Map<String, Object> map = new HashMap<String, Object>();
		String dn = operator.getDn();
		// List companynos =
		// commonOperator.getCompanynos()==null?operator.getCompanynos():commonOperator.getCompanynos();
		// List roleid =
		// commonOperator.getRoleid()==null?operator.getRoleid():commonOperator.getRoleid();
		String oldPassword = mapInfo.get("oldPassword");
		String newPassword = mapInfo.get("newPassword");
		boolean flag = true;
		String msg = "";
		if (StringUtils.isBlank(oldPassword)) {
			flag = false;
			msg = "密码验证失败,不允许修改";
		} else if (!LDAPSecurityUtils.getOpenLDAPSHA(oldPassword).equals(operator.getUserPassword())) {
			flag = false;
			msg = "密码验证失败,不允许修改";
		} else if (StringUtils.isBlank(newPassword)) {
			flag = false;
			msg = "新密码不能为空";
		} else {
			flag = true;
			msg = "修改密码成功";
		}
		if (flag) {
			ldap.modifyInformation(dn, "userPassword", newPassword);
		}
		map.put("success", flag);
		map.put("msg", msg);
		return map;
	}

	@RequestMapping(value = "/delSaleManagerRole", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> delSaleManagerRole(@RequestBody CommonOperator commonOperator, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		String dn = commonOperator.getDn();
		String[] keys = { "roleid" };
		Object[] values = new Object[1];
		values[0] = SystemConst.ROLEID_SALES_MANAGER;
		ldap.deleteInformations(dn, keys, values);
		map.put("success", true);
		return map;
	}

	@RequestMapping(value = "/delElctricianRole", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> delElctricianRole(@RequestBody CommonOperator commonOperator, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		String dn = commonOperator.getDn();
		String[] keys = { "roleid" };
		Object[] values = new Object[1];
		values[0] = SystemConst.ROLEID_ELCTRICIAN;
		ldap.deleteInformations(dn, keys, values);
		map.put("success", true);
		return map;
	}

	@RequestMapping(value = "/getOrgOperators", method = RequestMethod.POST)
	public @ResponseBody Page<CommonOperator> getOrgOperators(@RequestBody PageSelect<HashMap<String, Object>> pageSelect,
			BindingResult bindingResult, HttpServletRequest request) throws SystemException {
		Map filter = pageSelect.getFilter();
		// 查询机构下的的所有操作人
		String companyno = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_ORGID);
		if (filter != null) {
			// 查询分公司下的所有操作人
			if (filter.get("isCompany") != null && Boolean.valueOf(filter.get("isCompany").toString())) {
				companyno = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
			}
		} else {
			companyno = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_ORGID);
		}
		Page<CommonOperator> result = ldap.getPageOperators(pageSelect, companyno);
		return result;
	}

	@RequestMapping(value = "/getElctrician", method = RequestMethod.POST)
	public @ResponseBody List<CommonOperator> getElctrician(HttpServletRequest request) throws SystemException {
		String companyno = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_ORGID);
		List<CommonOperator> list = ldap.getElctricianByOrgId(companyno);
		return list;
	}

	/**
	 * @Desc 门店系统查询页面，客户名称查找
	 * @param pageSelect
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/getCustomers", method = RequestMethod.POST)
	public @ResponseBody Page<Customer> getCustomers(@RequestBody PageSelect<Customer> pageSelect, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		String searchValue = pageSelect.getSearchValue();
		String companyid = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		String ishq = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_ISHQ);
		System.out.println("******************************");
		System.out.println(searchValue + " ^^^^^^^^^^  " + companyid);
		// 2016年3月25日18:58:16 @金星 门店查询页面判断选择机构失败后默认查询全部数据
		/*
		 * if ("0".equals(searchValue)) { companyid = "2"; }
		 */
		// 修改后代码
		if (org.apache.commons.lang.StringUtils.isBlank(companyid)) {
			companyid = "2";
		}
		Page<Customer> result = customerService.search(pageSelect, Long.valueOf(companyid));
		return result;
	}

	@RequestMapping(value = "/getSalesOffices", method = RequestMethod.POST)
	public @ResponseBody List<CommonCompany> getSalesOffices(@RequestBody CommonCompany company, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		String companyno = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		List<CommonCompany> list = ldap.getSalesOfficeByOrgId(companyno, company.getCompanyname());
		return list;
	}

	@RequestMapping(value = "/getBranchs", method = RequestMethod.POST)
	public @ResponseBody List<CommonCompany> getBranchs(@RequestBody CommonCompany company, BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String companyno = SystemConst.TOPCOMPANYNO;
		List<CommonCompany> list = ldap.getChildsByCompanyId(companyno);
		return list;
	}

	@RequestMapping(value = "/getElctricianByOrgId", method = RequestMethod.POST)
	public @ResponseBody List<CommonOperator> getElctricianByOrgId(@RequestBody CommonOperator commonOperator, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		String companyno = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_ORGID);
		List<CommonOperator> list = ldap.getElctricianByOrgId(companyno);
		return list;
	}

	@RequestMapping(value = "/getOrdersRecipient", method = RequestMethod.POST)
	public @ResponseBody List<CommonOperator> getOrdersRecipient(@RequestBody CommonOperator commonOperator, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		String opname = commonOperator.getOpname();
		if (opname == null) {
			opname = "";
		}
		String filter = "(&(objectclass=CommonOperator)(roleid=" + SystemConst.ROLEID_ORDERS_RECIPIENT + "))";
		List<CommonOperator> list = ldap.searchOperator("", filter);
		List<CommonOperator> result = new ArrayList<CommonOperator>();
		for (CommonOperator oper : list) {
			if (oper.getOpname().indexOf(opname) != -1) {
				result.add(oper);
			}
		}
		return result;
	}

	@RequestMapping(value = "/getUserCompanyLevel", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getUserCompanyLevel(@RequestBody CommonOperator commonOperator, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		String companyno = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_ORGID);
		CommonCompany company = ldap.getCompanyById(companyno);
		String dn = company.getDn();
		String type = company.getCompanytype();
		String msg = "";
		String result = "";
		if (dn.indexOf("companyno=" + SystemConst.HEADQUARTERS + ",") != -1) {
			// 所属总部机构
			msg = "总部机构";
			result = "0";
		} else if (dn.indexOf("companyno=" + SystemConst.TOPCOMPANYNO + ",") != -1) {
			// 所属分公司
			if ("2".equals(type) || "4".equals(type)) {// type=2代表是营业处,type=4代表仓库
				msg = "营业处";
				result = "2";
			} else {
				msg = "分公司";
				result = "1";
			}
		}
		map.put("msg", msg);
		map.put("result", result);
		map.put("success", true);
		return map;
	}

	@RequestMapping(value = "/getCaptcha", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getCaptcha(@RequestBody VerifyPOJO verifyPOJO, BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		String vehicletype = verifyPOJO.getParameter();
		// 此处生成加密的密文，以防修改后验证
		DesEncrypt des = new DesEncrypt(); // 实例化一个对像
		des.getKey("防拆码"); // 生成密匙
		String cryptograph = des.getEncString(vehicletype); // 加密字符串,返回String的密文
		map.put("captcha", cryptograph);
		map.put("success", true);
		return map;
	}

	@RequestMapping(value = "/company/users", method = RequestMethod.POST)
	public @ResponseBody List<HashMap<String, Object>> tree(@RequestBody CommonOperator user, BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String companyno = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		String companyname = "";
		// companyno="5";

		List<HashMap<String, Object>> userList = new ArrayList<HashMap<String, Object>>();
		try {
			List<CommonCompany> CommonCompanyList = ldap.getSalesOfficeByOrgId(companyno, companyname);
			HashMap<String, Object> map = null;
			HashMap<String, Object> child = null;
			for (CommonCompany company : CommonCompanyList) {
				map = new HashMap<String, Object>();
				map.put("id", company.getCompanyno());
				map.put("name", company.getCompanyname());
				List<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
				List<CommonOperator> CommonOperatorList = ldap.getOperators("", company.getCompanyno());
				for (CommonOperator operator : CommonOperatorList) {
					child = new HashMap<String, Object>();
					child.put("id", operator.getOpid());
					child.put("name", operator.getOpname());
					child.put("companyno", company.getCompanyno());
					child.put("companyname", company.getCompanyname());
					items.add(child);
				}
				map.put("items", items);
				userList.add(map);

			}
		} catch (Exception e) {
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return userList;
	}

	@RequestMapping(value = "/logout")
	@LogOperation(description = "用户退录", type = SystemConst.OPERATELOG_ADD, model_id = 20001)
	public void logout(HttpServletRequest request, HttpServletResponse response) throws SystemException {
		/**
		 * CommonOperator user =
		 * (CommonOperator)request.getSession().getAttribute
		 * (SystemConst.ACCOUNT_USER); //写LDAP日志 SimpleDateFormat sdf = new
		 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); String opertime =
		 * sdf.format(new Date()); CommonLog log = new CommonLog();
		 * log.setLogloginname(user.getLoginname());
		 * log.setOpname(user.getOpname()); log.setOperobject("4");
		 * log.setOpertime(opertime); log.setOpertype("退出系统");
		 * log.setMessage("从sos门店系统退录,"+user.toString()); ldap.add(log);
		 **/
		try {
			response.sendRedirect("logout.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Title:isSaleManager
	 * @Description:判断登录用户是否是销售经理
	 * @param commonOperator
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/isSaleManager", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> isSaleManager(CommonOperator commonOperator, HttpServletRequest request) throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> roleids = (List<String>) request.getSession().getAttribute(SystemConst.ACCOUNT_ROLEIDS);
		boolean isTrue = false;
		if (StringUtils.isNotNullOrEmpty(roleids)) {
			// 根据用户角色ID判断 用户是销售经理、部门经理、销售总监
			if (roleids.contains(SystemConst.ROLEID_SALES_MANAGER)) {// 销售经理
				isTrue = true;
			} else {// 部门经理、销售总监
				isTrue = false;
			}
		}
		map.put("success", isTrue);
		return map;
	}

	/**
	 * 获取公司子机构
	 * 
	 * @param company
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/getAllCompanys", method = RequestMethod.POST)
	public @ResponseBody List<CommonCompany> getAllCompanys(@RequestBody CommonCompany company, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		List<CommonCompany> list = ldap.getChildsByCompanyId(SystemConst.TOPCOMPANYNO);
		return list;
	}

	/**
	 * 托收合同号生成
	 * 
	 * @param maxid
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/getMaxid", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getMaxid(@RequestBody Maxid maxid, BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		String companyno = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		Long subco_no = Long.valueOf(companyno);
		Long ct_no = maxidService.getAndAddMaxid(subco_no);
		if (ct_no != 0L) {
			String pay_ct_no = String.valueOf(ct_no);
			while (pay_ct_no.length() < 10) {
				pay_ct_no = "0" + pay_ct_no;
			}
			map.put("success", true);
			map.put("pay_ct_no", pay_ct_no);
		} else {
			map.put("success", false);
		}
		return map;
	}

	/**
	 * 核查托收合同号是否重复
	 * 
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/checkPayCtNo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> checkPayCtNo(@RequestBody Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws SystemException {
		Map<String, Object> map = collectionService.checkPayCtNo(param);
		return map;
	}

	/**
	 * 获取用户归属公司
	 * 
	 * @param verify
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/getCurrentCompanys", method = RequestMethod.POST)
	public @ResponseBody List<Map<String, Object>> getCurrentCompanys(@RequestBody VerifyPOJO verify, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<String> list2 = new ArrayList<String>();
		CommonOperator user = (CommonOperator) request.getSession().getAttribute(SystemConst.ACCOUNT_USER);
		List<String> companys = user.getCompanynos();
		OpenLdap ldap = OpenLdapManager.getInstance();
		for (String str : companys) {
			Map<String, Object> map = new HashMap<String, Object>();
			CommonCompany company = ldap.getCompanyByCompanyno(str);
			if (company == null) {
				continue;
			}
			if (!list2.contains(company.getCompanyno())) {
				map.put("id", company.getCompanyno());
				map.put("pId", company.getParentcompanyno());
				map.put("name", company.getCompanyname());
				map.put("companyno", company.getCompanyno());
				map.put("companyname", company.getCompanyname());
				list.add(map);
				list2.add(company.getCompanyno());
			}
		}
		return list;
	}

	/**
	 * 设置查询操作分公司
	 * 
	 * @param verify
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/setSessionCompany", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> setSession(@RequestBody VerifyPOJO verify, BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String companyno = verify.getParameter();
		if ("2".equals(companyno) || "3".equals(companyno)) {
			companyno = "101";
			// 有地方要特殊处理用到，还是需要判断是否是总部
			httpRequest.getSession().setAttribute(SystemConst.ACCOUNT_ISHQ, "true");
		} else {
			httpRequest.getSession().setAttribute(SystemConst.ACCOUNT_ISHQ, "false");
		}
		OpenLdap ldap = OpenLdapManager.getInstance();
		CommonCompany company = ldap.getCompanyById(companyno);
		if (!company.getCompanylevel().equals("3")) {
			httpRequest.getSession().setAttribute(SystemConst.ACCOUNT_COMPANYID, company.getParentcompanyno());
		} else {
			httpRequest.getSession().setAttribute(SystemConst.ACCOUNT_COMPANYID, companyno);
		}
		httpRequest.getSession().setAttribute(SystemConst.ACCOUNT_COMPANYCODE, company.getCompanycode());
		httpRequest.getSession().setAttribute(SystemConst.ACCOUNT_COMPANYNAME, company.getCompanyname());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		return map;
	}

	/**
	 * 获取海马4S店列表
	 * 
	 * @param company
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/getHaima4s", method = RequestMethod.POST)
	public @ResponseBody List<CommonCompany> getHaima4s(@RequestBody CommonCompany company, BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String companyno = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		String companyType = company.getCompanytype();
		String companyname = company.getCompanyname() == null ? "" : company.getCompanyname();
		String filter = "(&(objectclass=CommonCompany)(parentcompanyno=" + companyno + ")(companytype=" + companyType + "))";
		List<CommonCompany> list = ldap.searchCompany("", filter);
		List<CommonCompany> result = new ArrayList<CommonCompany>();
		for (CommonCompany commonCompany : list) {
			// 如果机构名称能够匹配
			if (commonCompany.getCompanyname().indexOf(companyname) != -1) {
				result.add(commonCompany);
			}
		}
		return result;
	}

	/**
	 * 获取保险公司列表
	 * 
	 * @param insurer
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/getInsurers", method = RequestMethod.POST)
	public @ResponseBody List<Insurer> getInsurers(@RequestBody Insurer insurer, BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String insurername = insurer.getS_name() == null ? "" : insurer.getS_name();
		List<Insurer> list = insurerService.getInsurersByname(insurername);
		return list;
	}

	/**
	 * 通过app登录手机号码获取用户信息(托收资料、联系人、客户)
	 * 
	 * @param param
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/getCustomerByPhone", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getCustomerByPhone(@RequestBody Map<String, Object> param, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		String phone = Utils.isNullOrEmpty(param.get("parameter")) ? "" : param.get("parameter").toString();
		String customerId = Utils.isNullOrEmpty(param.get("customerId")) ? "" : param.get("customerId").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		OpenLdap ldap = OpenLdapManager.getInstance();
		CommonOperator operator = ldap.getOperatorByMobile(phone);
		if (operator == null) {
			// 该手机号码没有在系统中注册的
			result.put("success", false);
		} else {
			if (Utils.isNotNullOrEmpty(customerId) && customerId.equals(operator.getCustomerid())) { // 该用户原有的登录手机号不做判断
				result.put("success", false);
			} else {
				// 已注册
				String customerid = operator.getCustomerid();
				Customer customer = customerService.getCustomer(Long.valueOf(customerid));
				List<Linkman> list = custphoneService.listCustphone(Long.valueOf(customerid));
				Collection collection = collectionService.getCollectionByCustId(Long.valueOf(customerid));
				result.put("success", true);
				result.put("operator", operator);
				result.put("customer", customer);
				result.put("custphones", list);
				result.put("collection", collection);
			}
		}
		return result;
	}

	/**
	 * app账号查询
	 * 
	 * @param pageSelect
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/getAccounts", method = RequestMethod.POST)
	@LogOperation(description = "APP账号查询", type = SystemConst.OPERATELOG_SEARCHKEY, model_id = 20081)
	public @ResponseBody Page<HashMap<String, Object>> getAccounts(@RequestBody PageSelect<HashMap<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		String companyId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		pageSelect.getFilter().put("companyId", companyId);
		Page<HashMap<String, Object>> results = customerService.findUserPage(pageSelect);
		return results;
	}

	/**
	 * app账号导出
	 * 
	 * @param request
	 * @param response
	 * @throws SystemException
	 */
	@RequestMapping(value = "/exportAccounts")
	@LogOperation(description = "APP账号报表导出", type = SystemConst.OPERATELOG_SEARCHKEY, model_id = 20081)
	public void exportAccounts(HttpServletRequest request, HttpServletResponse response) throws SystemException {
		String companyId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		// 条件
		Map returnMap = parseReqParam2(request);
		returnMap.put("companyId", companyId);
		List<CommonOperator> results = customerService.listAccount(returnMap);
		String[][] title = { { "序号", "10" }, { "姓名", "25" }, { "登录名", "25" }, { "联系电话", "25" }, { "车牌号码", "25" }, { "邮箱", "25" } };
		int columnIndex = 0;
		List valueList = new ArrayList();
		String[] values = null;
		int listLenth = results.size();
		int titleLength = title.length;
		for (int i = 0; i < listLenth; i++) {
			values = new String[titleLength];
			CommonOperator operator = results.get(i);
			columnIndex = 0;
			values[columnIndex] = (i + 1) + "";
			columnIndex++;
			values[columnIndex] = operator.getOpname();
			columnIndex++;
			values[columnIndex] = operator.getLoginname();
			columnIndex++;
			values[columnIndex] = operator.getMobile();
			columnIndex++;
			values[columnIndex] = operator.getNumberplate();
			columnIndex++;
			values[columnIndex] = operator.getMail();
			valueList.add(values);
		}
		// 获得分公司中英文名称
		CommonCompany commonCompany = ldap.getCompanyById(companyId);
		CreateExcel_PDF_CSV.createExcel(valueList, response, "APP账号报表", title, commonCompany.getCnfullname(), commonCompany.getEnfullname(), false);
	}

	@RequestMapping(value = "/updateAccount", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateAccount(@RequestBody CommonOperator commonOperator, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		String dn = commonOperator.getDn();
		String opname = commonOperator.getOpname();
		String loginname = commonOperator.getLoginname();
		String mobile = commonOperator.getMobile();
		String numberplate = commonOperator.getNumberplate();
		String mail = commonOperator.getMail();
		String[] keys = new String[5];
		String[] values = new String[5];
		keys[0] = "opname";
		keys[1] = "loginname";
		keys[2] = "mobile";
		keys[3] = "numberplate";
		keys[4] = "mail";
		values[0] = opname;
		values[1] = loginname;
		values[2] = mobile;
		values[3] = numberplate;
		values[4] = mail;
		boolean b = ldap.modifyInformations(dn, keys, values);
		if (b) {
			map.put("success", true);
			map.put("msg", "操作成功");
		} else {
			map.put("success", false);
			map.put("msg", "操作失败");
		}
		return map;
	}

}
