package com.gboss.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.BorrowDetails;
import com.gboss.pojo.SellPerformance;
import com.gboss.service.SellPerformanceService;
import com.gboss.util.StringUtils;

/**
 * @Package:com.gboss.controller
 * @ClassName:SellPerformanceController
 * @Description:销售业绩控制类(销售经理、部门经理、销售总监主页)
 * @author:zfy
 * @date:2013-8-2 上午10:01:09
 */
@Controller
public class SellPerformanceController extends BaseController {

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(SellPerformanceController.class);

	@Autowired
	@Qualifier("sellPerformanceService")
	private SellPerformanceService sellPerformanceService;

	/**
	 * @Title:findSellPerformances
	 * @Description:销售经理、部门经理、销售总监主页：销售业绩查询
	 * @param sellPerformance
	 *            参数例子:{"year":"2013","month":"07"}
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	@RequestMapping(value = "/sell/findSellPerformances")
	public @ResponseBody
	List<SellPerformance> findSellPerformances(
			@RequestBody SellPerformance sellPerformance,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		List<SellPerformance> result = null;
		try {
			List<String> roleids = (List<String>)request.getSession().getAttribute(SystemConst.ACCOUNT_ROLEIDS);
			Integer type = 1;
			if(StringUtils.isNotNullOrEmpty(roleids)){
			// 根据用户角色ID判断 用户是销售经理、部门经理、销售总监
				if (roleids.contains(SystemConst.ROLEID_SALES_MANAGER)) {// 销售经理
					String userId=(String) request.getSession()
							.getAttribute(SystemConst.ACCOUNT_ID);
					sellPerformance.setUserOrgId(userId==null?null:Long.valueOf(userId));
					type = 1;

				} else if (roleids.contains(SystemConst.ROLEID_ORG_MANAGER)) {// 部门经理
					String orgId=(String) request.getSession()
							.getAttribute(SystemConst.ACCOUNT_ORGID);
					sellPerformance.setUserOrgId(orgId==null?null:Long.valueOf(orgId));
					type = 2;

				} else if (roleids.contains(SystemConst.ROLEID_SALES_DIRECTOR)) {// 销售总监
					String companyId=(String) request.getSession()
							.getAttribute(SystemConst.ACCOUNT_COMPANYID);
					sellPerformance.setUserOrgId(companyId==null?null:Long.valueOf(companyId));
					type = 3;
				}
				sellPerformance.setType(type);
			}
			result = sellPerformanceService
					.findSellPerformances(sellPerformance);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return result;
	}

	/**
	 * @Title:findSellPerformances
	 * @Description:销售经理、部门经理、销售总监主页：销售明细查询
	 * @param sellPerformance
	 *            参数例子:{"yearMonth":"2013-07"},如果是查询全年的销售明细，
	 *            则传入{"yearMonth":"2013"}
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	@RequestMapping(value = "/sell/getSellProductDetail")
	public @ResponseBody
	List<Map<String, Object>> getSellProductDetail(
			@RequestBody HashMap<String, String> conditionMap,
			HttpServletRequest request) throws SystemException {
		List<Map<String, Object>> result = null;
		try {
			// 从session中得到用户角色ID
			List<String> roleids = (List<String>)request.getSession().getAttribute(SystemConst.ACCOUNT_ROLEIDS);
			
			// 根据用户角色ID判断 用户是销售经理、部门经理、销售总监
			if(StringUtils.isNotNullOrEmpty(roleids)){
				if (roleids.contains(SystemConst.ROLEID_SALES_MANAGER)) {// 销售经理
					conditionMap.put("userId", (String) request.getSession()
							.getAttribute(SystemConst.ACCOUNT_ID));

				} else if (roleids.contains(SystemConst.ROLEID_ORG_MANAGER)) {// 部门经理
					conditionMap.put("orgId", (String) request.getSession()
							.getAttribute(SystemConst.ACCOUNT_ORGID));

				} else {// 销售总监
					conditionMap.put("companyId", (String) request.getSession()
							.getAttribute(SystemConst.ACCOUNT_COMPANYID));
				}
			}
			result = sellPerformanceService.getSellProductDetail(conditionMap);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return result;
	}

	/**
	 * @Title:findBorrows
	 * @Description:查询销售经理、部门、分公司借用情况
	 * @param borrow
	 *            参数可选（可不传）
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	@RequestMapping(value = "/sell/findBorrows")
	public @ResponseBody
	List<Map<String, Object>> findBorrows(@RequestBody BorrowDetails borrowDetails,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		List<Map<String, Object>> result = null;
		try {
			// 从session中得到用户角色ID
			List<String> roleids = (List<String>)request.getSession().getAttribute(SystemConst.ACCOUNT_ROLEIDS);
			// 根据用户角色ID判断 用户是销售经理、部门经理、销售总监
			if(StringUtils.isNotNullOrEmpty(roleids)){
				if (roleids.contains(SystemConst.ROLEID_SALES_MANAGER)
						&& borrowDetails.getBorrowerId()==null) {// 销售经理
					String userId=(String) request.getSession()
							.getAttribute(SystemConst.ACCOUNT_ID);
					borrowDetails.setBorrowerId(userId==null?null:Long.valueOf(userId));

				} else if (roleids.contains(SystemConst.ROLEID_ORG_MANAGER)
						&& borrowDetails.getOrgId()==null) {// 部门经理
					String orgId=(String) request.getSession()
							.getAttribute(SystemConst.ACCOUNT_ORGID);
					borrowDetails.setOrgId(orgId==null?null:Long.valueOf(orgId));

				} else if (roleids.contains(SystemConst.ROLEID_SALES_DIRECTOR)
						&& borrowDetails.getCompanyId()==null) {// 销售总监
					String companyId=(String) request.getSession()
							.getAttribute(SystemConst.ACCOUNT_COMPANYID);
					borrowDetails.setCompanyId(companyId==null?null:Long.valueOf(companyId));
				}
			}
			result = sellPerformanceService.findBorrows(borrowDetails);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return result;
	}

	/**
	 * @Title:getSellProductAction
	 * @Description:获得销售商品明细的动作情况（出库、入库、销账）,用于销售经理
	 * @param conditionMap
	 *            参数例子:{"yearMonth":"2013-07"}
	 * @param request
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	@RequestMapping(value = "/sell/getSellProductAction")
	public @ResponseBody
	List<Map<String, Object>> getSellProductAction(
			@RequestBody HashMap<String, String> conditionMap,
			HttpServletRequest request) throws SystemException {
		List<Map<String, Object>> result = null;
		try {
			// 从session中得到用户角色ID
			List<String> roleids = (List<String>)request.getSession().getAttribute(SystemConst.ACCOUNT_ROLEIDS);
			
			// 根据用户角色ID判断 用户是销售经理
			if(StringUtils.isNotNullOrEmpty(roleids)){
				if (roleids.contains(SystemConst.ROLEID_SALES_MANAGER)) {// 销售经理
					conditionMap.put("userId", (String) request.getSession()
							.getAttribute(SystemConst.ACCOUNT_ID));
				}
			}
			result = sellPerformanceService.getSellProductAction(conditionMap);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return result;
	}

	/**
	 * @Title:statSellPerformance
	 * @Description:统计部门或者分公司的销售完成比例,用于部门经理、销售总监
	 * @param year
	 * @param month
	 *            参数例子:{"year":"2013","month":"07"}
	 * @param request
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	@RequestMapping(value = "/sell/statSellPerformance")
	public @ResponseBody
	List<Map<String, Object>> statSellPerformance(@RequestParam String year,
			@RequestParam String month, HttpServletRequest request)
			throws SystemException {
		List<Map<String, Object>> result = null;
		try {
			HashMap<String, Object> conditionMap = new HashMap<String, Object>();
			// 从session中得到用户角色ID
			List<String> roleids = (List<String>)request.getSession().getAttribute(SystemConst.ACCOUNT_ROLEIDS);
			
			String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);

			OpenLdap ldap = OpenLdapManager.getInstance();

			// 根据用户角色ID判断 用户是部门经理还是销售总监
			if(StringUtils.isNotNullOrEmpty(roleids)){
				if (roleids.contains(SystemConst.ROLEID_ORG_MANAGER)) {// 部门经理
					conditionMap.put("type", 2);
					conditionMap.put("userOrgIds",
							ldap.getManagersByOrgId(orgId));

				} else if (roleids.contains(SystemConst.ROLEID_SALES_DIRECTOR)) {
					conditionMap.put("type", 3);
					conditionMap.put("userOrgIds",
							ldap.getOrgIdsByCompanyId(companyId));

				}
			}
			result = sellPerformanceService.statSellPerformance(conditionMap);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return result;
	}
	
	@RequestMapping(value = "/sell/statAllSellPerformance")
	public @ResponseBody
	List<SellPerformance> statAllSellPerformance(@Validated SellPerformance sellPerformance, HttpServletRequest request)
			throws SystemException {
		String year = sellPerformance.getYear();
		return sellPerformanceService.statAllSellPerformance(year);
	}
}
