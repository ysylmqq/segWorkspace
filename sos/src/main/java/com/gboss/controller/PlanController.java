package com.gboss.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
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
import com.gboss.pojo.Plan;
import com.gboss.service.PlanService;
import com.gboss.util.JsonUtil;
import com.gboss.util.LogOperation;
import com.gboss.util.StringUtils;

/**
 * @Package:com.gboss.controller
 * @ClassName:PlanController
 * @Description:销售计划控制层
 * @author:zfy
 * @date:2013-8-20 下午2:08:00
 */
@Controller
public class PlanController extends BaseController {

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(PlanController.class);

	@Autowired
	@Qualifier("planService")
	private PlanService planService;

	/**
	 * @Title:findPlans
	 * @Description:查询销售计划
	 * @param plan
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	@RequestMapping(value = "/sell/findPlans")
	public @ResponseBody
	List<Plan> findPlans(@RequestBody Plan plan, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		List<Plan> result = null;
		try {
			List<String> roleids = (List<String>)request.getSession().getAttribute(SystemConst.ACCOUNT_ROLEIDS);
			Integer type = 1;
			if(StringUtils.isNotNullOrEmpty(roleids)){
			// 根据用户角色ID判断 用户是销售经理、部门经理、销售总监
				if (roleids.contains(SystemConst.ROLEID_SALES_MANAGER)) {// 销售经理
					String userId=(String) request.getSession()
							.getAttribute(SystemConst.ACCOUNT_ID);
					plan.setOrgUserId(userId==null?null:Long.valueOf(userId));
					type = 1;

				} else if (roleids.contains(SystemConst.ROLEID_ORG_MANAGER)) {// 部门经理
					String orgId=(String) request.getSession()
							.getAttribute(SystemConst.ACCOUNT_ORGID);
					plan.setOrgUserId(orgId==null?null:Long.valueOf(orgId));
					type = 2;

				} else if (roleids.contains(SystemConst.ROLEID_SALES_DIRECTOR)) {// 销售总监
					String companyId=(String) request.getSession()
							.getAttribute(SystemConst.ACCOUNT_COMPANYID);
					plan.setOrgUserId(companyId==null?null:Long.valueOf(companyId));
					type = 3;
				}
				plan.setLevel(type);
			}
			result = planService.findPlans(plan);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return result;
	}

	@RequestMapping(value = "/sell/addPlans")
	@LogOperation(description = "添加销售计划", type = SystemConst.OPERATELOG_ADD, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> addPlans(@RequestBody List<Plan> plans,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("添加销售计划 开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(plans,true));
			
			if (plans != null) {
				String userId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_COMPANYID);
				planService.insertPlans(plans, userId==null?null:Long.valueOf(userId));
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
			LOGGER.debug("添加销售计划 结束");
		}
		return resultMap;
	}
}
