package com.gboss.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.gboss.service.CustomerService;
import com.gboss.util.JsonUtil;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.Utils;
import com.gboss.util.page.Page;

@Controller
public class CustomerController extends BaseController {
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(CustomerController.class);
	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "/customer/add", method = RequestMethod.POST)
	@LogOperation(description = "添加客户", type = SystemConst.OPERATELOG_ADD, model_id = 2)
	public @ResponseBody
	HashMap add(@RequestBody Customer customer, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {

		String userId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ID);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		if (post.equals(request.getMethod())) {

			// 将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY,
					JsonUtil.oToJ(customer, true));

			if (customerService.is_repeat(customer)) {
				resultMap.put("success", false);
				resultMap.put("msg", "添加客户失败，注册名已存在!");
				return resultMap;
			}
			customer.setOp_id(userId == null ? null : Long.valueOf(userId));
			customerService.add(customer);
			resultMap.put("success", true);
			resultMap.put("msg", "添加客户成功!");
			return resultMap;
		}
		return resultMap;
	}

	@RequestMapping(value = "/customer/del", method = RequestMethod.POST)
	@LogOperation(description = "删除客户", type = SystemConst.OPERATELOG_DEL, model_id = 2)
	public @ResponseBody
	HashMap del(@RequestBody Customer customer, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		if (post.equals(request.getMethod())) {
			// 将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY,
					JsonUtil.oToJ(customer, true));

			customerService.deleteObject(customer);
			resultMap.put("success", true);
			resultMap.put("msg", "删除客户成功!");
			return resultMap;
		}
		return resultMap;
	}

	@RequestMapping(value = "/customer/update", method = RequestMethod.POST)
	@LogOperation(description = "修改客户", type = SystemConst.OPERATELOG_UPDATE, model_id = 2)
	public @ResponseBody
	HashMap update(@RequestBody Customer customer, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {

		String userId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ID);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		if (post.equals(request.getMethod())) {
			// 将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY,
					JsonUtil.oToJ(customer, true));

			if (customerService.is_repeat(customer)) {
				resultMap.put("success", false);
				resultMap.put("msg", "修改客户失败，注册名已存在!");
				return resultMap;
			}
			customer.setOp_id(userId == null ? null : Long.valueOf(userId));
			customerService.update(customer);
			resultMap.put("success", true);
			resultMap.put("msg", "修改客户成功!");
			return resultMap;
		}
		return resultMap;
	}

	@RequestMapping(value = "/customer/list", method = RequestMethod.POST)
	public @ResponseBody
	Page<Customer> list(@RequestBody PageSelect<Customer> pageSelect,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String companyid = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		Page<Customer> list = null;
		try {
			list = customerService.search(pageSelect, Long.valueOf(companyid));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping(value = "/customer/file", method = RequestMethod.POST)
	public @ResponseBody
	HashMap file(@RequestBody Customer customer, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		String userId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ID);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		/*
		 * customer.setClerk(userId);
		 * customer.setFile_time(Utils.getNowTimeString());
		 */
		customerService.update(customer);
		resultMap.put("success", true);
		resultMap.put("msg", "记录归档成功!");
		return resultMap;
	}

	@RequestMapping(value = "/customer/getFileNo", method = RequestMethod.POST)
	public @ResponseBody
	HashMap getFileNo(@RequestBody Customer customer,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("msg", Utils.getFileNo());
		return resultMap;
	}

	/**
	 * 
	 * @Title:getTaskNo
	 * @Description:获取客户电话号码（用,拼接）
	 * @param request
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	@RequestMapping(value = "/customer/getCustomerPhone")
	public @ResponseBody
	String getCustomerPhone(Long id, HttpServletRequest request)
			throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得客户电话号码开始");
		}
		String results = null;
		try {
			results = customerService.getCustomerPhone(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得客户电话号码结束");
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
	@RequestMapping(value = "/customer/getDetailMsg")
	public @ResponseBody
	HashMap<String, Object> getDetailMsg(Long id, HttpServletRequest request)
			throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得客户车辆详细信息开始");
		}
		HashMap<String, Object> results = null;
		try {
			results = customerService.getDetailMsg(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得客户车辆详细信息结束");
		}
		return results;
	}

	@RequestMapping(value = "/customer/getDetailMsgBycl")
	public @ResponseBody
	HashMap<String, Object> getDetailMsgBycl(String call_letter,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得客户车辆详细信息开始");
		}
		HashMap<String, Object> results = null;
		try {
			results = customerService.getDetailMsgBycl(call_letter);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得客户车辆详细信息结束");
		}
		return results;
	}

	@RequestMapping(value = "/customer/findLargeVehicles")
	public @ResponseBody
	Page<HashMap<String, Object>> findLargeVehicles(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询集团客户车辆列表开始");
		}
		Page<HashMap<String, Object>> result = null;
		try {
			if (pageSelect != null) {
				Map map = pageSelect.getFilter();
				if (map == null) {
					map = new HashMap<String, Object>();
				}
				pageSelect.setFilter(map);
			}
			/* result = custVehicleService.findLargeVehicles(pageSelect); */
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询查询集团客户车辆列表结束");
		}
		return result;
	}

	/*  *//**
	 * 
	 * @Title:getTaskNo
	 * @Description:获取客户车辆信息
	 * @param request
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	/*
	 * @RequestMapping(value = "/customer/getDetailMsg") public @ResponseBody
	 * HashMap<String, Object> getCustomerInfo(HttpServletRequest request)
	 * throws SystemException { if (LOGGER.isDebugEnabled()) {
	 * LOGGER.debug("获得客户信息开始"); } HashMap<String, Object> results = null; try {
	 * String userId =
	 * (String)request.getSession().getAttribute(SystemConst.CUST_INFO);
	 * results= customerService.getDetailMsg(0L); } catch (Exception e) {
	 * LOGGER.error(e.getMessage(), e); e.printStackTrace(); // 同时把异常抛到前台 throw
	 * new SystemException(); } if (LOGGER.isDebugEnabled()) {
	 * LOGGER.debug("获得客户信息结束"); } return results; }
	 */

}