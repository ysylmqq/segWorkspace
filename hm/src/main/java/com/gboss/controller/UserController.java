package com.gboss.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ldap.objectClasses.CommonCompany;
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
import com.gboss.pojo.Customer;
import com.gboss.pojo.Operatelog;
import com.gboss.pojo.web.VerifyPOJO;
import com.gboss.service.CustomerService;
import com.gboss.service.OperatelogService;
import com.gboss.util.DesEncrypt;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.page.Page;

@Controller
public class UserController extends BaseController {

	@Autowired
	private OperatelogService operatelogService;

	@Autowired
	private CustomerService customerService;

	private OpenLdap ldap = OpenLdapManager.getInstance();

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> register(@RequestBody CommonOperator user,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		AttributePrincipal principal = (AttributePrincipal) request
				.getUserPrincipal();
		String username = "";
		if (principal != null) {
			username = principal.getName();
		} else {
			map.put("success", false);
			return map;
		}
		Object oper = request.getSession().getAttribute(
				SystemConst.ACCOUNT_USER);
		if (oper != null) {
			map.put("success", true);
			return map;
		}
		CommonOperator operator = ldap.getOperator(username);
		String companyId = ldap.getCompanyByOrgId(
				operator.getCompanynos().get(0)).getCompanyno();
		// 写日志,因为要做判断所以不通过注释方式写了
		Operatelog log = new Operatelog();
		log.setUser_id(operator.getOpid() == null ? null : Long
				.valueOf(operator.getOpid()));
		log.setSubco_no(Long.valueOf(companyId));
		log.setOp_type(Integer.valueOf(SystemConst.OPERATELOG_LOGIN));
		log.setRemark(operator.getOpname());
		operatelogService.add(log);
		// 写Session
		request.getSession().setAttribute(SystemConst.ACCOUNT_USER, operator);
		request.getSession().setAttribute(SystemConst.ACCOUNT_ID,
				operator.getOpid());
		request.getSession().setAttribute(SystemConst.ACCOUNT_USERNAME,
				operator.getOpname());
		// 如果一个用户属于多个机构就不好办了，所以暂时屏蔽掉一个用户可能属于多个机构的情况
		request.getSession().setAttribute(SystemConst.ACCOUNT_ORGID,
				operator.getCompanynos().get(0));
		request.getSession().setAttribute(SystemConst.ACCOUNT_COMPANYID,
				companyId);
		request.getSession().setAttribute(SystemConst.ACCOUNT_ROLEIDS,
				operator.getRoleid());
		map.put("success", true);
		return map;
	}

	@RequestMapping(value = "/getManagers", method = RequestMethod.POST)
	public @ResponseBody
	List<CommonOperator> getManagers(@RequestBody VerifyPOJO verify,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String opname = verify.getParameter();
		List<CommonOperator> list = ldap.getManagersByName(opname);
		return list;
	}

	@RequestMapping(value = "/getCurrentCompany_cl", method = RequestMethod.POST)
	public @ResponseBody
	CommonCompany getCurrentCompany(String conpanyId, HttpServletRequest request)
			throws SystemException {
		CommonCompany company = new CommonCompany();
		if (conpanyId != null) {
			company = ldap.getCompanyById(conpanyId);
		}
		return company;
	}

	@RequestMapping(value = "/getCompanySaleManager_cl", method = RequestMethod.POST)
	public @ResponseBody
	Page<CommonOperator> getCompanySaleManager_cl(
			@RequestBody PageSelect<HashMap<String, Object>> pageSelect,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String companyno = "";
		if (pageSelect != null) {
			Map map = pageSelect.getFilter();
			if (map != null) {
				companyno = map.get("companyno").toString();
			}
			// map.put("tStatus", -1);
			pageSelect.setFilter(map);
		}

		Page<CommonOperator> result = ldap.getPageSaleManager(pageSelect,
				companyno);
		return result;
	}

	@RequestMapping(value = "/getCompanySaleManager", method = RequestMethod.POST)
	public @ResponseBody
	Page<CommonOperator> getCompanySaleManager(
			@RequestBody PageSelect<HashMap<String, Object>> pageSelect,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String companyno = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		Page<CommonOperator> result = ldap.getPageSaleManager(pageSelect,
				companyno);
		return result;
	}

	@RequestMapping(value = "/getCurrentOperator", method = RequestMethod.POST)
	public @ResponseBody
	CommonOperator getCurrentOperator(@RequestBody VerifyPOJO verify,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		CommonOperator operator = (CommonOperator) request.getSession()
				.getAttribute(SystemConst.ACCOUNT_USER);
		if (operator == null) {
			AttributePrincipal principal = (AttributePrincipal) request
					.getUserPrincipal();
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

	@RequestMapping(value = "/getCurrentOrg", method = RequestMethod.POST)
	public @ResponseBody
	CommonCompany getCurrentOrg(@RequestBody VerifyPOJO verify,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		CommonCompany company = new CommonCompany();
		String orgId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ORGID);
		if (orgId != null) {
			company = ldap.getCompanyById(orgId);
		}
		return company;
	}

	@RequestMapping(value = "/getCurrentCompany", method = RequestMethod.POST)
	public @ResponseBody
	CommonCompany getCurrentCompany(@RequestBody VerifyPOJO verify,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		CommonCompany company = new CommonCompany();
		String orgId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		if (orgId != null) {
			company = ldap.getCompanyById(orgId);
		}
		return company;
	}

	@RequestMapping(value = "/getOperators", method = RequestMethod.POST)
	public @ResponseBody
	List<CommonOperator> getOperators(@RequestBody VerifyPOJO verify,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String opname = verify.getParameter();
		String companyno = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		List<CommonOperator> list = ldap.getOperators(opname, companyno);
		return list;
	}

	@RequestMapping(value = "/updateOperator", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> updateOperator(
			@RequestBody CommonOperator commonOperator,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		CommonOperator operator = (CommonOperator) request.getSession()
				.getAttribute(SystemConst.ACCOUNT_USER);
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
			url = "<" + SystemConst.SYSTEMID + ":"
					+ commonOperator.getMainUrl() + ":" + SystemConst.SYSTEMID
					+ ">";
		} else if (url.indexOf(SystemConst.SYSTEMID) != -1) {
			url = url.substring(0, url.indexOf(str) + str.length())
					+ commonOperator.getMainUrl()
					+ url.substring(
							url.indexOf(":" + SystemConst.SYSTEMID + ">"),
							url.length());
		} else {
			url = url + "<" + SystemConst.SYSTEMID + ":"
					+ commonOperator.getMainUrl() + ":" + SystemConst.SYSTEMID
					+ ">";
		}
		if (moduleid == null) {
			moduleid = "<" + SystemConst.SYSTEMID + ":"
					+ commonOperator.getMainModuleid() + ":"
					+ SystemConst.SYSTEMID + ">";
		} else if (moduleid.indexOf(SystemConst.SYSTEMID) != -1) {
			moduleid = moduleid.substring(0,
					moduleid.indexOf(str) + str.length())
					+ commonOperator.getMainModuleid()
					+ moduleid.substring(
							moduleid.indexOf(":" + SystemConst.SYSTEMID + ">"),
							moduleid.length());
		} else {
			moduleid = moduleid + "<" + SystemConst.SYSTEMID + ":"
					+ commonOperator.getMainModuleid() + ":"
					+ SystemConst.SYSTEMID + ">";
		}
		String mainUrl = commonOperator.getMainUrl() == null ? operator
				.getMainUrl() : url;
		String mainModuleId = commonOperator.getMainModuleid() == null ? operator
				.getMainModuleid() : moduleid;
		String idcard = commonOperator.getIdcard() == null ? operator
				.getIdcard() : commonOperator.getIdcard();
		String jobnumber = commonOperator.getJobnumber() == null ? operator
				.getJobnumber() : commonOperator.getJobnumber();
		String phone = commonOperator.getPhone() == null ? operator.getPhone()
				: commonOperator.getPhone();
		String sex = commonOperator.getSex() == null ? operator.getSex()
				: commonOperator.getSex();
		String fax = commonOperator.getFax() == null ? operator.getFax()
				: commonOperator.getFax();
		String mail = commonOperator.getMail() == null ? operator.getMail()
				: commonOperator.getMail();
		String post = commonOperator.getPost() == null ? operator.getPost()
				: commonOperator.getPost();
		String mobile = commonOperator.getMobile() == null ? operator
				.getMobile() : commonOperator.getMobile();
		String remark = commonOperator.getRemark() == null ? operator
				.getRemark() : commonOperator.getRemark();
		String[] keys = { "mainUrl", "mainModuleId", "idcard", "jobnumber",
				"phone", "sex", "fax", "mail", "post", "mobile", "remark" };
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
	public @ResponseBody
	Map<String, Object> updatePassword(
			@RequestBody Map<String, String> mapInfo,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		CommonOperator operator = (CommonOperator) request.getSession()
				.getAttribute(SystemConst.ACCOUNT_USER);
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
		} else if (!LDAPSecurityUtils.getOpenLDAPSHA(oldPassword).equals(
				operator.getUserPassword())) {
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
	public @ResponseBody
	Map<String, Object> delSaleManagerRole(
			@RequestBody CommonOperator commonOperator,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
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
	public @ResponseBody
	Map<String, Object> delElctricianRole(
			@RequestBody CommonOperator commonOperator,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
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
	public @ResponseBody
	Page<CommonOperator> getOrgOperators(
			@RequestBody PageSelect<HashMap<String, Object>> pageSelect,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		Map filter = pageSelect.getFilter();
		// 查询机构下的的所有操作人
		String companyno = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ORGID);
		if (filter != null) {
			// 查询分公司下的所有操作人
			if (filter.get("isCompany") != null
					&& Boolean.valueOf(filter.get("isCompany").toString())) {
				companyno = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_COMPANYID);
			}
		} else {
			companyno = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
		}
		Page<CommonOperator> result = ldap.getPageOperators(pageSelect,
				companyno);
		return result;
	}

	@RequestMapping(value = "/getElctrician", method = RequestMethod.POST)
	public @ResponseBody
	List<CommonOperator> getElctrician(HttpServletRequest request)
			throws SystemException {
		String companyno = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ORGID);
		List<CommonOperator> list = ldap.getElctricianByOrgId(companyno);
		return list;
	}

	@RequestMapping(value = "/getCustomers", method = RequestMethod.POST)
	public @ResponseBody
	Page<Customer> getCustomers(@RequestBody PageSelect<Customer> pageSelect,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String searchValue = pageSelect.getSearchValue();
		String companyid = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		String ishq = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ISHQ);
		if ("0".equals(searchValue) && "true".equals(ishq)) {
			companyid = "2";
		}
		Page<Customer> result = customerService.search(pageSelect,
				Long.valueOf(companyid));
		return result;
	}

	@RequestMapping(value = "/getSalesOffices", method = RequestMethod.POST)
	public @ResponseBody
	List<CommonCompany> getSalesOffices(@RequestBody CommonCompany company,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String companyno = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		List<CommonCompany> list = ldap.getSalesOfficeByOrgId(companyno,
				company.getCompanyname());
		return list;
	}

	@RequestMapping(value = "/getBranchs", method = RequestMethod.POST)
	public @ResponseBody
	List<CommonCompany> getBranchs(@RequestBody CommonCompany company,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String companyno = SystemConst.TOPCOMPANYNO;
		List<CommonCompany> list = ldap.getChildsByCompanyId(companyno);
		return list;
	}

	@RequestMapping(value = "/getElctricianByOrgId", method = RequestMethod.POST)
	public @ResponseBody
	List<CommonOperator> getElctricianByOrgId(
			@RequestBody CommonOperator commonOperator,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String companyno = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ORGID);
		List<CommonOperator> list = ldap.getElctricianByOrgId(companyno);
		return list;
	}

	@RequestMapping(value = "/getOrdersRecipient", method = RequestMethod.POST)
	public @ResponseBody
	List<CommonOperator> getOrdersRecipient(
			@RequestBody CommonOperator commonOperator,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String opname = commonOperator.getOpname();
		if (opname == null) {
			opname = "";
		}
		String filter = "(&(objectclass=CommonOperator)(roleid="
				+ SystemConst.ROLEID_ORDERS_RECIPIENT + "))";
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
	public @ResponseBody
	Map<String, Object> getUserCompanyLevel(
			@RequestBody CommonOperator commonOperator,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		String companyno = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ORGID);
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
	public @ResponseBody
	Map<String, Object> getCaptcha(@RequestBody VerifyPOJO verifyPOJO,
			BindingResult bindingResult, HttpServletRequest request)
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
	public @ResponseBody
	List<HashMap<String, Object>> tree(@RequestBody CommonOperator user,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String companyno = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		String companyname = "";
		// companyno="5";

		List<HashMap<String, Object>> userList = new ArrayList<HashMap<String, Object>>();
		try {
			List<CommonCompany> CommonCompanyList = ldap.getSalesOfficeByOrgId(
					companyno, companyname);
			HashMap<String, Object> map = null;
			HashMap<String, Object> child = null;
			for (CommonCompany company : CommonCompanyList) {
				map = new HashMap<String, Object>();
				map.put("id", company.getCompanyno());
				map.put("name", company.getCompanyname());
				List<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
				List<CommonOperator> CommonOperatorList = ldap.getOperators("",
						company.getCompanyno());
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
	public void logout(HttpServletRequest request, HttpServletResponse response)
			throws SystemException {
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
	public @ResponseBody
	Map<String, Object> isSaleManager(CommonOperator commonOperator,
			HttpServletRequest request) throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> roleids = (List<String>) request.getSession()
				.getAttribute(SystemConst.ACCOUNT_ROLEIDS);
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

	@RequestMapping(value = "/getAllCompanys", method = RequestMethod.POST)
	public @ResponseBody
	List<CommonCompany> getAllCompanys(@RequestBody CommonCompany company,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		List<CommonCompany> list = ldap
				.getChildsByCompanyId(SystemConst.TOPCOMPANYNO);
		return list;
	}

	@RequestMapping(value = "/getCurrentCompanys", method = RequestMethod.POST)
	public @ResponseBody
	List<Map<String, Object>> getCurrentCompanys(
			@RequestBody VerifyPOJO verify, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<String> list2 = new ArrayList<String>();
		CommonOperator user = (CommonOperator) request.getSession()
				.getAttribute(SystemConst.ACCOUNT_USER);
		List<String> companys = user.getCompanynos();
		OpenLdap ldap = OpenLdapManager.getInstance();
		for (String str : companys) {
			Map<String, Object> map = new HashMap<String, Object>();
			CommonCompany company = ldap.getCompanyByOrgId(str);
			if (!list2.contains(company.getCompanyno())) {
				map.put("companyno", company.getCompanyno());
				map.put("companyname", company.getCompanyname());
				list.add(map);
				list2.add(company.getCompanyno());
			}
		}
		return list;
	}

	@RequestMapping(value = "/setSessionCompany", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> setSession(@RequestBody VerifyPOJO verify,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		HttpServletRequest httpRequest = request;
		String companyno = verify.getParameter();
		if ("2".equals(companyno) || "3".equals(companyno)) {
			companyno = "101";
			// 有地方要特殊处理用到，还是需要判断是否是总部
			httpRequest.getSession().setAttribute(SystemConst.ACCOUNT_ISHQ,
					"true");
		} else {
			httpRequest.getSession().setAttribute(SystemConst.ACCOUNT_ISHQ,
					"false");
		}
		OpenLdap ldap = OpenLdapManager.getInstance();
		CommonCompany company = ldap.getCompanyById(companyno);
		httpRequest.getSession().setAttribute(SystemConst.ACCOUNT_COMPANYID,
				companyno);
		httpRequest.getSession().setAttribute(SystemConst.ACCOUNT_COMPANYCODE,
				company.getCompanycode());
		httpRequest.getSession().setAttribute(SystemConst.ACCOUNT_COMPANYNAME,
				company.getCompanyname());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		return map;
	}

	@RequestMapping(value = "/getHaima4s", method = RequestMethod.POST)
	public @ResponseBody
	List<CommonCompany> getHaima4s(@RequestBody CommonCompany company,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String companyno = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		String companyType = company.getCompanytype();
		String companyname = company.getCompanyname() == null ? "" : company
				.getCompanyname();
		String filter = "(&(objectclass=CommonCompany)(parentcompanyno="
				+ companyno + ")(companytype=" + companyType + "))";
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

}
