package com.gboss.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ldap.objectClasses.CommonOperator;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.TaskType;
import com.gboss.service.DispatchElectricianService;
import com.gboss.util.Utils;

/**
 * @Package:com.gboss.controller
 * @ClassName:DispatchElectricianController
 * @Description:TODO
 * @author:bzhang
 * @date:2014-3-27 下午7:51:03
 */
@Controller
public class DispatchElectricianController extends BaseController {
	
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(DispatchElectricianController.class);

	@Autowired
	private DispatchElectricianService dispatchElectricianService;
	
	private OpenLdap ldap = OpenLdapManager.getInstance();

	@RequestMapping(value = "/dispatchElectrician/getDispatchElectricia")
	public @ResponseBody
	List<HashMap<String, Object>> getDispatchElectricia(
			@RequestBody Map<String, Object> map, HttpServletRequest request)
			throws SystemException {
		String companyno = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_ORGID);
		String time = Utils.getDateString();
		
		List<CommonOperator> list = ldap.getElctricianByOrgId(companyno);
		List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
		for (CommonOperator commonOperator : list) {
			HashMap<String, Object> hashmap = new HashMap<String, Object>();
			hashmap.put("id", commonOperator.getOpid());
			hashmap.put("name", commonOperator.getOpname());
			hashmap.put("phone", commonOperator.getMobile());
			hashmap.put("company", commonOperator.getCompanyname());
			hashmap.put("describe", "");
			hashmap.put("check", "<input type='radio' disabled='disabled'  class="+commonOperator.getOpid()+" name='betreuer' />");
			HashMap<String, Object> dmap = dispatchElectricianService.getElectriciansDetail(Long.valueOf(commonOperator.getOpid()), time);
			if(null != dmap){
				String describe = TaskType.getTaskType(Integer.valueOf(dmap.get("type").toString())).getDescribe() +
						"(" + dmap.get("place").toString() + "&nbsp;&nbsp;" + dmap.get("time").toString() + "-" +
						Utils.getEndTime(dmap.get("time").toString(),dmap.get("duration").toString())+")";
				hashmap.put("describe", describe);
			}
			result.add(hashmap);
		}
		return result;
	}
	
	
	
	@RequestMapping(value = "/dispatchElectrician/getElectriciansBytaskId")
	public @ResponseBody
	List<HashMap<String, Object>> getElectriciansBytaskId(Long id,
			HttpServletRequest request) throws SystemException {
		List<HashMap<String, Object>> results = null;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得电工开始");
		}
		try {
			results = dispatchElectricianService.getElectriciansBytaskId(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得电工结束");
		}
		return results;
	}

	
	
	
	
	
	
}
