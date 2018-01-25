package com.gboss.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.gboss.pojo.DispatchElectrician;
import com.gboss.pojo.DispatchTask;
import com.gboss.pojo.Task;
import com.gboss.pojo.TaskFlow;
import com.gboss.service.DispatchElectricianService;
import com.gboss.service.DispatchTaskService;
import com.gboss.service.TaskFlowService;
import com.gboss.service.TaskService;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.Utils;
import com.gboss.util.page.Page;

/**
 * 
 * @ClassName:TaskController.java
 * @Description: 任务单管理控制层
 * @author: bzhang
 * @date :2014-3-21
 */
@Controller
public class TaskController extends BaseController {
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(TaskController.class);

	@Autowired
	@Qualifier("taskService")
	private TaskService taskService;
	@Autowired
	@Qualifier("dispatchTaskService")
	private DispatchTaskService dispatchTaskService;
	@Autowired
	@Qualifier("dispatchElectricianService")
	private DispatchElectricianService dispatchElectricianService;
	
	@Autowired
	@Qualifier("taskFlowService")
	private TaskFlowService taskFlowService;

	/**
	 * 
	 * @Title:add
	 * @Description:添加装单（新装、维修、出警和其他）
	 * @param @param task
	 * @param @param bindingResult
	 * @param @param request
	 * @param @return
	 * @param @throws SystemException
	 * @return HashMap
	 *//*
	@RequestMapping(value = "/task/add", method = RequestMethod.POST)
	@LogOperation(description = "添加接单", type = SystemConst.OPERATELOG_ADD)
	public @ResponseBody
	HashMap add(@RequestBody Task task, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("添加接单开始");
		}

		String electricianIds = task.getElectricianIds();
		String electricianPhones = task.getElectrician_phone();
		//Timestamp start_time = task.getStart_time();
		String duration = task.getDuration();
		String electricianNameds = task.getElectricianNames();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		
		String userId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ID);
		String orgId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ORGID);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {

			if (task != null) {
				task.setSponsor(userId == null ? null : Long.valueOf(userId));
				task.setOrgId(orgId == null ? null : Long.valueOf(orgId));
				//task.setTime(new Timestamp(System.currentTimeMillis()));
				task.setStatus(SystemConst.WAIT_RES_TASK);
				taskService.save(task);
				// 派工单
				if (StringUtils.isNotNullOrEmpty(electricianIds)) {
					String[] userIds = electricianIds.split(",");
					String[] phones = electricianPhones.split(",");
					String[] names = electricianNameds.split(",");
					DispatchTask dTask = new DispatchTask();
					//dTask.setDispatch_time(new Timestamp(System.currentTimeMillis()));
					dTask.setDuration(duration);
					dTask.setOrg_id(orgId == null ? null : Long.valueOf(orgId));
					//dTask.setStamp(new Timestamp(System.currentTimeMillis()));
					//dTask.setStart_time(start_time);
					dTask.setStatus(SystemConst.WAIT_RES_TASK);
					dTask.setTask_id(task.getId());
					dTask.setUser_id(task.getSponsor());
					dispatchTaskService.save(dTask);
					// 派工直接更改任务单状态
					task.setStatus(SystemConst.HAS_DISPATCH_TASK);
					taskService.save(task);
					// 派工单与电工绑定
					for (int i = 0; i < userIds.length; i++) {
						DispatchElectrician de = new DispatchElectrician();
						de.setDispatch_id(dTask.getId());
						de.setElectrician(userIds[i] == null ? null : Long
								.valueOf(userIds[i]));
						de.setPhone(phones[i]);
						de.setElectrician_name(names[i]);
						de.setTask_id(task.getId());
						dispatchElectricianService.save(de);
					}
				}
				if(StringUtils.isNotNullOrEmpty(task.getDispatcher())){
					TaskFlow taskFlow = new TaskFlow();
					taskFlow.setCharger(task.getCharger());
					taskFlow.setDispatcher(task.getDispatcher());
					taskFlow.setSponsor(task.getSponsor());
					taskFlow.setTask_id(task.getId());
					taskFlowService.save(taskFlow);
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
			LOGGER.debug("添加工单 结束");
		}
		return resultMap;
	}
	*/
	
	/**
	 * 
	 * @Title:save
	 * @Description:新需求（保存工单）
	 * @param task
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	@RequestMapping(value = "/task/save", method = RequestMethod.POST)
	@LogOperation(description = "添加工单", type = SystemConst.OPERATELOG_ADD, model_id =2)
	public @ResponseBody
	HashMap save(@RequestBody Task task, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("添加工单开始");
		}
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		
		String userId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ID);
		String orgId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ORGID);
		String companyId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (task != null) {
				task.setOp_id(userId == null ? null : Long.valueOf(userId));
				task.setOrg_id(orgId == null ? null : Long.valueOf(orgId));
				task.setSubco_no(companyId == null ? null : Long.valueOf(companyId));
				task.setStatus(SystemConst.NORMAL_RES_TASK_STATUS);
				taskService.save(task);

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
			LOGGER.debug("添加工单结束");
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/task/save_cl", method = RequestMethod.POST)
	@LogOperation(description = "添加工单", type = SystemConst.OPERATELOG_ADD, model_id =2)
	public @ResponseBody
	HashMap save_cl(@RequestBody Task task, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("添加工单开始");
		}
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (task != null) {
				task.setStatus(SystemConst.NORMAL_RES_TASK_STATUS);
				taskService.save(task);
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
			LOGGER.debug("添加工单结束");
		}
		return resultMap;
	}

	/**
	 * 
	 * @Title:getOrderNo
	 * @Description: 生成装单单号
	 * @param @param request
	 * @param @return
	 * @param @throws SystemException
	 * @return String
	 */
	@RequestMapping(value = "/task/getTaskNo")
	public @ResponseBody
	String getTaskNo(HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得接单号 开始");
		}
		String results = null;
		try {
			String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			results = taskService.getTaskNo(orgId == null ? null : Long
					.valueOf(orgId));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得接单号 结束");
		}
		return results;
	}

	@RequestMapping(value = "/task/findTasksByPage")
	public @ResponseBody
	Page<HashMap<String, Object>> findTasksByPage(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询工单开始");
		}
		Page<HashMap<String, Object>> result = null;
		try {
			if (pageSelect != null) {
				Map map = pageSelect.getFilter();
				if (map == null) {
					map = new HashMap<String, Object>();
				}
				// map.put("tStatus", -1);
				pageSelect.setFilter(map);
			}
			result = taskService.findTasks(pageSelect);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询工单结束");
		}
		return result;
	}
	
	
	
	
	@RequestMapping(value = "/task/findTasksByPageNew")
	public @ResponseBody
	Page<HashMap<String, Object>> findTasksByPageNew(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询工单开始");
		}
		Page<HashMap<String, Object>> result = null;
		try {
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			Long company_id = companyId == null ? 0L:Long.valueOf(companyId);
			Long user_id = userId == null ? 0L:Long.valueOf(userId);
			if (pageSelect != null) {
				Map map = pageSelect.getFilter();
				if (map == null) {
					map = new HashMap<String, Object>();
				}
				// map.put("tStatus", -1);
				pageSelect.setFilter(map);
			}
			result = taskService.findTasksPage(pageSelect, company_id, user_id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询工单结束");
		}
		return result;
	}
	
	

	@RequestMapping(value = "/task/findTasksBycl")
	public @ResponseBody
	Page<HashMap<String, Object>> findTasksBycl(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("外部接口查询工单开始");
		}
		Boolean flag = false;
		Page<HashMap<String, Object>> result =new Page<HashMap<String,Object>>();
		try {
			if (pageSelect != null) {
				Map map = pageSelect.getFilter();
				if(StringUtils.isNotNullOrEmpty(map.get("call_letter"))){
					flag = true;
				}
				if (map == null) {
					map = new HashMap<String, Object>();
				}
				pageSelect.setFilter(map);
			}
			if(flag)
			result = taskService.findTasksBycl(pageSelect);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("外部接口查询工单结束");
		}
		return result;
	}
	
	
	@RequestMapping(value = "/task/findTasksByPageForReservation")
	public @ResponseBody
	Page<HashMap<String, Object>> findTasksByPageForReservation(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询预约单开始");
		}
		Page<HashMap<String, Object>> result = null;
		try {
			if (pageSelect != null) {
				Map map = pageSelect.getFilter();
				if (map == null) {
					map = new HashMap<String, Object>();
				}
				// map.put("tStatus", -1);
				pageSelect.setFilter(map);
			}
			result = taskService.findTasksforReservation(pageSelect);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询预约单结束");
		}
		return result;
	}

	@RequestMapping(value = "/task/getTaskReHistory")
	public @ResponseBody
	List<HashMap<String, Object>> getTaskReHistory(Long id,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得工单预约历史开始");
		}
		List<HashMap<String, Object>> results = null;
		try {
			results = taskService.getTaskReHistory(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得工单预约历史结束");
		}
		return results;
	}

	@RequestMapping(value = "/task/deleteTasks")
	@LogOperation(description = "删除任务单", type = SystemConst.OPERATELOG_DEL, model_id =2)
	public @ResponseBody
	HashMap<String, Object> deleteTasks(@RequestBody List<Long> ids,
			HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("任务单删除开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			int result = taskService.deleteTasks(ids);
			if (result == -1) {
				flag = false;
				msg = "操作对象为空";
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg = SystemConst.OP_FAILURE;
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("任务单删除结束");
		}
		return resultMap;
	}
	

	@RequestMapping(value = "/task/endTasks")
	@LogOperation(description = "结束任务单", type = SystemConst.OPERATELOG_UPDATE, model_id =2)
	public @ResponseBody
	HashMap<String, Object> endTasks(@RequestBody List<Long> ids,
			HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("任务单结束开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			int result = taskService.endTasks(ids);
			if (result == -1) {
				flag = false;
				msg = "操作对象为空";
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg = SystemConst.OP_FAILURE;
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("任务单结束");
		}
		return resultMap;
	}
	
	
	@RequestMapping(value = "/task/getTaskList")
	public @ResponseBody
	List<Task> getTaskList(Long id,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得工单开始");
		}
		List<Task> results = null;
		try {
			results = taskService.getTaskByElctrician(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得工单结束");
		}
		return results;
	}
	
	@RequestMapping(value = "/task/getTaskDetailMsg")
	public @ResponseBody HashMap<String, Object> getTaskDetailMsg(Long id, HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得工单详细信息开始");
		}
		HashMap<String, Object> results = null;
		try {
			results= taskService.getTaskDetailMsg(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得工单详细信息结束");
		}
		return results;
	}

	
	@RequestMapping(value = "/task/getTasklMsg")
	public @ResponseBody HashMap<String, Object> getTasklMsg(Long task_id, HttpServletRequest request) throws SystemException {
		HashMap<String, Object> results = new HashMap<String, Object>();
		try {
			Task task= taskService.get(Task.class, task_id);
			if(null != task){
				results.put("bill_num", task.getBill_no());
				results.put("symptom", task.getSymptom());
				results.put("note", task.getNote());
				results.put("place", task.getPlace());
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return results;
	}

	
	
	@RequestMapping(value = "/taskFlow/getDispatchers", method = RequestMethod.POST)
	public @ResponseBody List<String> getDispatchers(@RequestBody Task task, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		Long taskId = task.getTask_id();
		List<String> list = taskFlowService.getDispatchers(taskId);
		return list;
	}

}
