package com.gboss.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.Combo;
import com.gboss.pojo.PaymentSim;
import com.gboss.pojo.Preload;
import com.gboss.pojo.PreloadBk;
import com.gboss.service.PaymentSimService;
import com.gboss.service.PreloadService;
import com.gboss.util.DateUtil;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.controller
 * @ClassName:PaymentSimController
 * @Description:TODO
 * @author:bzhang
 * @date:2014-12-29 下午4:34:11
 */
@Controller
public class PaymentSimController extends BaseController {

	@Autowired
	@Qualifier("paymentSimService")
	private PaymentSimService paymentSimService;
	

	@Autowired
	@Qualifier("preloadService")
	private PreloadService preloadService;

	@RequestMapping(value = "/paymentSim/add")
	@LogOperation(description = "添加SIM卡缴费", type = SystemConst.OPERATELOG_ADD, model_id = 20100)
	public @ResponseBody
	HashMap<String, Object> add(@RequestBody PaymentSim paymentSim,
			BindingResult bindingResult, HttpServletRequest request) {
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
				paymentSim.setOp_id(Long.valueOf(userId));
			}
			Date endDate = DateUtil.getEndDate(paymentSim.getE_date(),
					paymentSim.getS_months(), paymentSim.getS_days());
			paymentSim.setS_date(paymentSim.getE_date());
			paymentSim.setE_date(endDate);
			paymentSim.setSubco_no(id);
			paymentSim.setPay_time(new Date());
			paymentSimService.save(paymentSim);
			
			Preload sim = preloadService.get(Preload.class, paymentSim.getSim_id());
			//SIM卡历史表
			/*PreloadBk bk = new PreloadBk();
		    BeanUtils.copyProperties(bk,sim);
		    preloadService.save(bk);*/
			sim.setE_date(paymentSim.getE_date());
			preloadService.update(sim);
		} catch (Exception e) {
			flag = false;
			msg = SystemConst.OP_FAILURE;
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		return resultMap;
	}
	
	
	
	
	@RequestMapping(value = "/paymentSim/findRecordsPage")
	public @ResponseBody
	Page<HashMap<String, Object>> findPaymentRecordsPage(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		Page<HashMap<String, Object>> result = null;
		Map map = null;
		try {
			if (pageSelect != null) {
				map = pageSelect.getFilter();
				if (map == null) {
					map = new HashMap<String, Object>();
				}
				pageSelect.setFilter(map);
			}
			result = paymentSimService.findRecordsPage(pageSelect);
		} catch (Exception e) {
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return result;
	}
	
	
	/**
	 * 
	 * @Title:findPolicysByPage
	 * @Description:分页查询保单
	 * @param pageSelect
	 * @param request
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	@RequestMapping(value = "/paymentSim/findPaymentSimPage")
	@LogOperation(description = "缴费账单统计", type = SystemConst.OPERATELOG_SEARCHKEY, model_id =20061)
	public @ResponseBody
	Page<HashMap<String, Object>> findPolicysByPage(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		boolean flag = false;
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

			Object startDate = 	map.get("startDate");
			Object endDate = map.get("endDate");
			if(com.gboss.util.StringUtils.isNullOrEmpty(startDate) && com.gboss.util.StringUtils.isNullOrEmpty(endDate)){
				flag = true;
			}
				pageSelect.setFilter(map);
			}
			if(flag){
				return PageUtil.getPage(0, pageSelect.getPageNo(), null, pageSelect.getPageSize());
			}else{
				result = paymentSimService.findPaymentSimPage(id, pageSelect);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return result;
	}
	
	

}
