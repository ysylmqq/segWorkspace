package com.gboss.controller;

import java.util.HashMap;

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
import com.gboss.pojo.Reservation;
import com.gboss.pojo.Task;
import com.gboss.service.ReservationService;
import com.gboss.service.TaskService;
import com.gboss.util.LogOperation;

/**
 * @Package:com.gboss.controller
 * @ClassName:Reservation
 * @Description:预约控制类
 * @author:bzhang
 * @date:2014-4-3 下午4:04:35
 */
@Controller
public class ReservationController extends BaseController {

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(ReservationController.class);

	@Autowired
	@Qualifier("reservationService")
	private ReservationService reservationService;
	
	@Autowired
	@Qualifier("taskService")
	private TaskService taskService;

	/**
	 * 
	 * @Title:add
	 * @Description:新增工单预约
	 * @param reservation
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/reservation/add", method = RequestMethod.POST)
	@LogOperation(description = "工单预约", type = SystemConst.OPERATELOG_ADD, model_id =2)
	public @ResponseBody HashMap add(@RequestBody Reservation reservation,
			BindingResult bindingResult, HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("工单预约开始");
		}
		boolean flag = false;
		String msg = SystemConst.OP_FAILURE;
		String userId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ID);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			//更改任务单状态
			Task task = taskService.get(Task.class, reservation.getTask_id());
			task.setStatus(SystemConst.HAS_RES_TASK);	
			//reservation.setStamp(new Timestamp(System.currentTimeMillis()));
			reservation.setUser_id(userId == null ? null : Long.valueOf(userId));
			reservationService.save(reservation);
			if (reservation.getId() != null) {
				flag = true;
				msg = SystemConst.OP_SUCCESS;
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("工单预约结束");
		}
		resultMap.put("success", flag);
		resultMap.put("msg", msg);
		return resultMap;
	}
	
	
	@RequestMapping(value = "/reservation/getReservationMsg")
	public @ResponseBody HashMap<String, Object> getReservationMsg(Long id, HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得预信息开始");
		}
		HashMap<String, Object> results = null;
		try {
			results = reservationService.getReservationBytask(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得预约信息结束");
		}
		return results;
	}
	

}
