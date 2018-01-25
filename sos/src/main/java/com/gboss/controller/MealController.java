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
import com.gboss.pojo.Meal;
import com.gboss.service.ComboService;
import com.gboss.service.MealService;
import com.gboss.util.JsonUtil;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.controller
 * @ClassName:MealController
 * @Description:私家车入网获取套餐名称
 * @author:hansm
 * @date:2016-6-08 下午3:20:13
 */
@Controller
public class MealController extends BaseController {
	
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(MealController.class);
	
	@Autowired
	@Qualifier("mealService")
	private MealService mealService; 
	
	@RequestMapping(value = "/meal/getmealList", method = RequestMethod.POST)
	public @ResponseBody
	List<HashMap<String, Object>> getmealList(Integer telco,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得套餐列表开始");
		}
		String companyId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);//companyId
		Long id = companyId == null ? null : Long.valueOf(companyId);//compannyId转换为long型
		List<HashMap<String, Object>> results = null;
		 Map map = new HashMap();
		 if(null != telco){
			 map.put("telco", telco);
		 }
		try {
			results = mealService.findMeales(id, map , null, false, 0, 0);//查询
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
	
	/*
	@RequestMapping(value = "/meal/findMealByPage")
	public @ResponseBody
	Page<HashMap<String, Object>> findMealByPage(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询套餐开始");
		}
		
		Page<HashMap<String, Object>> result = null;
		try {
			String compannyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);//companyId
					Long id = compannyId == null ? null : Long.valueOf(compannyId);
			if (pageSelect != null) {
				Map map = pageSelect.getFilter();
				if (map == null) {
					map = new HashMap<String, Object>();
				}
				// map.put("tStatus", -1);
				pageSelect.setFilter(map);
			}
			result = mealService.findMeales(id, pageSelect);
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
*/
}

