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
import com.gboss.pojo.Answer;
import com.gboss.pojo.AnswerDetails;
import com.gboss.pojo.DispatchTask;
import com.gboss.service.AnswerDetailsService;
import com.gboss.service.AnswerService;
import com.gboss.service.DispatchTaskService;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.controller
 * @ClassName:AnswerController
 * @Description:TODO
 * @author:bzhang
 * @date:2014-4-28 下午4:06:27
 */
@Controller
public class AnswerController extends BaseController {
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(AnswerController.class);

	@Autowired
	@Qualifier("answerService")
	private AnswerService answerService;
	
	@Autowired
	@Qualifier("answerDetailsService")
	private AnswerDetailsService answerDetailsService;
	
	@Autowired
	@Qualifier("dispatchTaskService")
	private DispatchTaskService dispatchTaskService;
	
	@RequestMapping(value = "/answer/add", method = RequestMethod.POST)
	@LogOperation(description = "添加回单", type = SystemConst.OPERATELOG_ADD, model_id = 2)
	public @ResponseBody
	HashMap add(@RequestBody Answer answer, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("添加回单开始");
		}

		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		
		String userId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ID);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (answer != null) {
				Long task_id = answer.getTask_id();
				if(null != task_id){
				DispatchTask dTask = dispatchTaskService.findDistachByTask(task_id);
				if(null != dTask)
					answer.setDispatch_id(dTask.getId());	
				}else{
					flag=false;
					msg="参数不正确!";
				}
				//answer.setStamp(new Timestamp(System.currentTimeMillis()));
				answer.setUser_id(userId == null ? null : Long.valueOf(userId));
				resultMap = answerService.addAnswer(answer);
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
			LOGGER.debug("添加回单结束");
		}
		return resultMap;
	}
	
	
	@RequestMapping(value = "/answer/findAnswersByPage")
	public @ResponseBody
	Page<HashMap<String, Object>> findAnswersByPage(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询回单开始");
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
			result = answerService.findAnswer(pageSelect);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询回单结束");
		}
		return result;
	}
	
	@RequestMapping(value = "/answer/findAnswerDetails")
	public @ResponseBody
	List<AnswerDetails> findAnswerDetails(
			@RequestBody Answer answer,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("根据回单id查询回单配件明细开始");
		}
		List<AnswerDetails> result = null;
		try {
			Long answer_id = answer.getId();
			if (answer_id == null) {
				answer_id = Long.valueOf(0);
			}
			result = answerDetailsService.getByAnswerid(answer_id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("根据回单id查询回单配件明细结束");
		}
		return result;
	}

}

