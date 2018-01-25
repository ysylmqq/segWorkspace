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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.Combo;
import com.gboss.service.ComboService;
import com.gboss.util.JsonUtil;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.controller
 * @ClassName:ComboController
 * @Description:TODO
 * @author:bzhang
 * @date:2014-9-18 下午3:20:13
 */
@Controller
public class ComboController extends BaseController {
	
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(ComboController.class);
	
	@Autowired
	@Qualifier("comboService")
	private ComboService comboService; 
	
	
	
	@RequestMapping(value = "/combo/addCombo", method = RequestMethod.POST)
	@LogOperation(description = "新增套餐", type = SystemConst.OPERATELOG_ADD, model_id = 20080)
	public @ResponseBody Map<String, Object> add(@RequestBody  Combo combo, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
		
			String compannyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			Long id = compannyId == null ? null : Long.valueOf(compannyId);
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			if (StringUtils.isNotBlank(userId)) {
				combo.setOp_id(Long.valueOf(userId));
			}
			combo.setSubco_no(id);
			int result = comboService.addCombo(combo);
			if (result == 2) {
				flag = false;
				msg = "套餐名称为[" + combo.getCombo_name() + "]的已存在";
			} 
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg = SystemConst.OP_FAILURE;
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		return resultMap;
	}
	
	
	 /**
     * 
     * getcomboPrice
     * @Description:获取客户电话号码（用,拼接）
     * @param request
     * @return
     * @throws SystemException
     * @throws
     */
	@RequestMapping(value = "/combo/getcomboPrice")
	public @ResponseBody Float getcomboPrice(Long id, HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得套餐价格开始");
		}
		Float results = null;
		try {
			results= comboService.get(Combo.class, id).getMonth_fee();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得套餐价格结束");
		}
		return results;
	}
	
	

	@RequestMapping(value = "/combo/getcomboList")
	public @ResponseBody
	List<HashMap<String, Object>> getcomboList(Integer telco,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得套餐列表开始");
		}
		String compannyId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		Long id = compannyId == null ? null : Long.valueOf(compannyId);
		List<HashMap<String, Object>> results = null;
		 Map map = new HashMap();
		 if(null != telco)
		 map.put("telco", telco);
		try {
			results = comboService.findComboes(id, map , null, false, 0, 0);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得套餐列表结束");
		}
		return results;
	}
	
	
	
	/*@RequestMapping(value = "/combo/updateCombo")
	@LogOperation(description = "修改套餐", type = SystemConst.OPERATELOG_UPDATE, model_id=20081)
	public @ResponseBody
	 HashMap<String, Object> updateCombo(
			@RequestBody  Combo combo, BindingResult bindingResult,
		  HttpServletRequest request) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(combo,true));
			if (comboService.isExist(combo)) {
				flag = false;
				msg = "套餐名称为[" + combo.getCombo_name() + "]的已存在";
			}else{
				Combo cb = comboService.get(Combo.class, combo.getCombo_id());
				if(cb != null){
					cb.setMonth_fee(combo.getMonth_fee());
					cb.setFlow(combo.getFlow());
					cb.setCode(combo.getCode());
					cb.setName(combo.getName());
					cb.setTalk_time(combo.getTalk_time());
					cb.setType(combo.getType());
					comboService.update(cb);
				}
			} 
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg = SystemConst.OP_FAILURE;
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		return resultMap;
	}*/
	
	
	@RequestMapping(value = "/combo/findComboByPage")
	public @ResponseBody
	Page<HashMap<String, Object>> findComboByPage(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询套餐开始");
		}
		
		Page<HashMap<String, Object>> result = null;
		try {
			String compannyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
					Long id = compannyId == null ? null : Long.valueOf(compannyId);
			if (pageSelect != null) {
				Map map = pageSelect.getFilter();
				if (map == null) {
					map = new HashMap<String, Object>();
				}
				// map.put("tStatus", -1);
				pageSelect.setFilter(map);
			}
			result = comboService.findComboes(id, pageSelect);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询套餐结束");
		}
		return result;
	}
	
	@RequestMapping(value = "/combo/delete")
	@LogOperation(description = "删除套餐", type = SystemConst.OPERATELOG_DEL,model_id=20082)
	public @ResponseBody
	HashMap<String, Object> delete(@RequestParam(value="ids[]",required=false) List<Long> ids,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("删除套餐开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			int result = comboService.delete(ids);
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
			LOGGER.debug("删除套餐结束");
		}
		return resultMap;
	}
	
	
	@RequestMapping(value = "/combo/getComboMsg")
	public @ResponseBody HashMap<String, Object> getComboMsg(Long id, HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得套餐详细信息开始");
		}
		HashMap<String, Object> results = new HashMap<String, Object>();
		try {
			Combo combo = comboService.get(Combo.class, id);
			results.put("combo_name", combo.getCombo_name());
			results.put("month_fee", combo.getMonth_fee());
			results.put("data", combo.getData());
			results.put("voice_time", combo.getVoice_time());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得套餐详细信息结束");
		}
		return results;
	}

}

