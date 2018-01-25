package com.gboss.controller;

import java.util.HashMap;

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
import com.gboss.pojo.DispatchElectrician;
import com.gboss.pojo.DispatchTask;
import com.gboss.pojo.Task;
import com.gboss.service.DispatchElectricianService;
import com.gboss.service.DispatchTaskService;
import com.gboss.service.TaskService;
import com.gboss.util.LogOperation;
import com.gboss.util.StringUtils;

/**
 * @Package:com.gboss.controller
 * @ClassName:DispatchTaskController
 * @Description:TODO
 * @author:bzhang
 * @date:2014-3-28 上午8:31:32
 */
@Controller
public class DispatchTaskController extends BaseController {
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(DispatchTaskController.class);
	@Autowired
	private DispatchTaskService dispatchTaskService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private DispatchElectricianService dispatchElectricianService;

	@RequestMapping(value = "/dispatchTask/add", method = RequestMethod.POST)
	@LogOperation(description = "添加接单", type = SystemConst.OPERATELOG_ADD, model_id = 2)
	public @ResponseBody
	HashMap add(@RequestBody DispatchTask dispatchTask,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("派工开始");
		}
		String electricianIds = dispatchTask.getElectricianIds();
		String electricianPhones = dispatchTask.getElectrician_phone();
		String electricianNameds = dispatchTask.getElectricianNames();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;

		String userId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ID);
		String orgId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ORGID);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (null != dispatchTask) {
				dispatchTask.setUser_id(userId == null ? null : Long
						.valueOf(userId));
				dispatchTask.setOrg_id(orgId == null ? null : Long
						.valueOf(orgId));
				dispatchTask.setStatus(SystemConst.DISPATCHTASK_NORMAL);
				// dispatchTask.setStamp(new
				// Timestamp(System.currentTimeMillis()));
				// dispatchTask.setDispatch_time(new
				// Timestamp(System.currentTimeMillis()));
				dispatchTaskService.save(dispatchTask);
				// 派工后更改任务单
				Task task = taskService.get(Task.class,
						dispatchTask.getTask_id());
				task.setStatus(SystemConst.HAS_DISPATCH_TASK);
				taskService.save(task);
				if (StringUtils.isNotNullOrEmpty(electricianIds)) {
					String[] userIds = electricianIds.split(",");
					String[] phones = electricianPhones.split(",");
					String[] names = electricianNameds.split(",");
					// 派工单与电工绑定
					for (int i = 0; i < userIds.length; i++) {
						DispatchElectrician de = new DispatchElectrician();
						de.setDispatch_id(dispatchTask.getId());
						de.setElectrician(userIds[i] == null ? null : Long
								.valueOf(userIds[i]));
						de.setPhone(phones[i]);
						de.setElectrician_name(names[i]);
						de.setTask_id(task.getTask_id());
						dispatchElectricianService.save(de);
					}
				}
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
			LOGGER.debug("派工 结束");
		}
		return resultMap;
	}

}
