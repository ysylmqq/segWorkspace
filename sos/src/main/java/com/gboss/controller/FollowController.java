package com.gboss.controller;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

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
import com.gboss.pojo.Follow;
import com.gboss.pojo.Task;
import com.gboss.service.DispatchElectricianService;
import com.gboss.service.FollowService;
import com.gboss.service.TaskService;
import com.gboss.util.LogOperation;

/**
 * @Package:com.gboss.controller
 * @ClassName:FollowController
 * @Description:TODO
 * @author:bzhang
 * @date:2014-4-22 下午2:43:27
 */
@Controller
public class FollowController extends BaseController {

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(FollowController.class);

	@Autowired
	@Qualifier("followService")
	private FollowService followService;

	@Autowired
	@Qualifier("taskService")
	private TaskService taskService;

	@Autowired
	@Qualifier("dispatchElectricianService")
	private DispatchElectricianService dispatchElectricianService;

	@RequestMapping(value = "/follow/add", method = RequestMethod.POST)
	@LogOperation(description = "添加跟单", type = SystemConst.OPERATELOG_ADD,model_id=2)
	public @ResponseBody
	HashMap add(@RequestBody Follow follow, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("添加跟单开始");
		}
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		String userId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ID);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (follow != null) {
				// follow.setStamp(new Timestamp(System.currentTimeMillis()));
				follow.setUser_id(userId == null ? null : Long.valueOf(userId));
				Task task = taskService.get(Task.class, follow.getTask_id());
				// 结束工单
				if (task.getStatus() <= SystemConst.HAS_DISPATCH_TASK) {
					task.setStatus(SystemConst.DELING_TASK);
					taskService.save(task);
				}
				Long workerId = follow.getWorker_id();
				if (workerId < 0) {
					List<HashMap<String, Object>> electricians = dispatchElectricianService
							.getElectriciansByDispatchId(follow
									.getDispatch_id());
					for (HashMap<String, Object> map : electricians) {
						Follow follow1 = new Follow();
						follow1.setArrival_time(follow.getArrival_time());
						follow1.setDeparture_time(follow.getDeparture_time());
						follow1.setDuration(follow.getDuration());
						follow1.setIs_end(follow.getIs_end());
						follow1.setNum(follow.getNum());
						follow1.setOutset_time(follow.getOutset_time());
						follow1.setRemark(follow.getRemark());
						// follow1.setStamp(new
						// Timestamp(System.currentTimeMillis()));
						follow1.setTask_id(follow.getTask_id());
						follow1.setUser_id(follow.getUser_id());
						follow1.setWorker((String) map.get("name"));
						follow1.setWorker_id(((BigInteger) (map.get("id")))
								.longValue());
						followService.save(follow1);
					}
				} else {
					followService.save(follow);
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
			LOGGER.debug("添加跟单结束");
		}
		return resultMap;
	}

}
