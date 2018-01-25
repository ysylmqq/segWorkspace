package com.gboss.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.Task;
import com.gboss.pojo.TaskFlow;
import com.gboss.service.TaskFlowService;
import com.gboss.service.TaskService;
import com.gboss.util.LogOperation;

/**
 * @Package:com.gboss.controller
 * @ClassName:TaskFlowController
 * @Description:TODO
 * @author:bzhang
 * @date:2014-5-8 上午10:34:27
 */
@Controller
public class TaskFlowController extends BaseController {

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(TaskFlowController.class);
	
	
	@Autowired
	@Qualifier("taskService")
	private TaskService taskService;
	
	@Autowired
	@Qualifier("taskFlowService")
	private TaskFlowService taskFlowService;
	
	private OpenLdap ldap = OpenLdapManager.getInstance();
	
	
	/**
	 * 
	 * @Title:save
	 * @Description:转单
	 * @param taskFlow
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	@RequestMapping(value = "/taskFellow/save", method = RequestMethod.POST)
	@LogOperation(description = "转单", type = SystemConst.OPERATELOG_ADD, model_id =2)
	public @ResponseBody
	HashMap save(@RequestBody TaskFlow taskFlow, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("转单开始");
		}
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		String userId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ID);
		String username = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_USERNAME);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (taskFlow != null) {
				Task task = taskService.get(Task.class, taskFlow.getTask_id());
				taskFlow.setOperator(username);
				taskFlow.setOp_id(userId == null ? null : Long.valueOf(userId));
				taskFlowService.save(taskFlow);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			 flag = false;
			 msg = SystemConst.OP_FAILURE;
			// 同时把异常抛到前台
			throw new SystemException();
		}
		resultMap.put("success", flag);
		resultMap.put("msg", msg);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("转单结束");
		}
		return resultMap;
	}
	
	
	@RequestMapping(value = "/taskFlow/getTaskFlowsDetail")
	public @ResponseBody
	List<HashMap<String, Object>> getTaskFlowsDetail(Long taskId, HttpServletRequest request)
			throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得转单明细开始");
		}
		List<HashMap<String, Object>> results = null;
		try {
			results = taskFlowService.getTaskFlowsDetail(taskId);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得转单明细结束");
		}
		return results;
	}

}

