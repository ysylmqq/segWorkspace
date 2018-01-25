package com.gboss.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.UserRemark;
import com.gboss.service.UserRemarkService;
import com.gboss.util.JsonUtil;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.controller
 * @ClassName:UserRemarkController
 * @Description:备忘录控制类
 * @author:zfy
 * @date:2013-11-18 上午10:52:34
 */
@Controller
public class UserRemarkController extends BaseController {
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(UserRemarkController.class);

	@Autowired
	@Qualifier("userRemarkService")
	private UserRemarkService userRemarkService;

	private ObjectMapper objectMapper = new ObjectMapper();
	private JsonGenerator jsonGenerator = null;

	@RequestMapping(value = "/sys/addUserRemark")
	@LogOperation(description = "添加用户备忘录", type = SystemConst.OPERATELOG_ADD, model_id = 30040)
	public @ResponseBody
	HashMap<String, Object> addUserRemark(@RequestBody UserRemark userRemark,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("添加用户备忘录 开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(userRemark,true));
			
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(userRemark);
			}
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			userRemark.setUserId(userId==null?null:Long.valueOf(userId));
			int result = userRemarkService.addUserRemark(userRemark);
			if (result == -1) {
				flag = false;
				msg = "参数不合法";
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
			LOGGER.debug("添加用户备忘录 结束");
		}
		return resultMap;
	}

	@RequestMapping(value = "/sys/updateUserRemark")
	@LogOperation(description = "修改用户备忘录", type = SystemConst.OPERATELOG_UPDATE, model_id = 30040)
	public @ResponseBody
	HashMap<String, Object> updateUserRemark(@RequestBody UserRemark userRemark,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("修改用户备忘录 开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(userRemark,true));
			
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			userRemark.setUserId(userId==null?null:Long.valueOf(userId));
			int result = userRemarkService.updateUserRemark(userRemark);
			if (result == -1) {
				flag = false;
				msg = "参数不合法";
			} else if (result == 0) {
				flag = false;
				msg = "要操作的对象不存在";
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
			LOGGER.debug("修改用户备忘录 结束");
		}
		return resultMap;
	}

	@RequestMapping(value = "/sys/findUserRemarks")
	public @ResponseBody
	Page<HashMap<String, Object>> findUserRemarks(
			@RequestBody PageSelect<HashMap<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		Page<HashMap<String, Object>> results = null;
		try {
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(pageSelect);
			}
			if (pageSelect != null) {
				Map conditionMap = pageSelect.getFilter();
				if (conditionMap == null) {
					conditionMap = new HashMap();
					pageSelect.setFilter(conditionMap);
				}
				String userId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ID);
				conditionMap.put("userId", userId);
			}
			results = userRemarkService.findUserRemarks(pageSelect);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印日志
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return results;
	}

	@RequestMapping(value = "/sys/getUserRemark")
	public @ResponseBody
	UserRemark getUserRemark(@RequestBody UserRemark userRemark, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		return userRemarkService.getUserRemarkById(userRemark.getId());
	}
	
	@RequestMapping(value = "/sys/deleteUserRemarks")
	@LogOperation(description = "批量删除用户备忘录", type =  SystemConst.OPERATELOG_DEL, model_id = 30040)
	public @ResponseBody HashMap<String, Object> deleteUserRemarks(@RequestBody List<Long> list,HttpServletRequest request) throws SystemException{
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("批量删除用户备忘录 开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(list,true));
			
			if(LOGGER.isDebugEnabled()){
				jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(list);
			}
			int result = userRemarkService.deleteUserRemarks(list);
			if (result == -1) {
				flag = false;
				msg = "参数不合法";
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("批量删除用户备忘录 结束");
		}
		return resultMap;
	}
	
}
