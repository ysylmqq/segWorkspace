package com.gboss.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.gboss.pojo.Onduty;
import com.gboss.service.OndutyService;

/**
 * @Package:com.gboss.controller
 * @ClassName:OndutyController
 * @Description:电工实际值班控制层
 * @author:bzhang
 * @date:2014-3-26 下午4:30:17
 */
@Controller
public class OndutyController extends BaseController {
	
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(OndutyController.class);
	@Autowired
	private OndutyService ondutyService;
	
	private OpenLdap ldap = OpenLdapManager.getInstance();

	/**
	 * 
	 * @Title:getOntutyDetail
	 * @Description:从ldp根据机构取出电工状态明细
	 * @param map
	 * @param request
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	@RequestMapping(value = "/onduty/getOntutyDetail")
	public @ResponseBody List<HashMap<String, Object>> getOntutyDetail(@RequestBody Map<String, Object> map,HttpServletRequest request) throws SystemException {
		String companyno = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_ORGID);
		//Timestamp time = new Timestamp(System.currentTimeMillis()) ;
		List<CommonOperator> list = ldap.getElctricianByOrgId(companyno);
		List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
		for (CommonOperator commonOperator : list) {
			HashMap<String, Object> hashmap = new HashMap<String, Object>();
			Onduty onduty = ondutyService.getOndutyByIdAndTime(commonOperator.getOpid(), new Date());
			hashmap.put("id", commonOperator.getOpid());
			hashmap.put("name", commonOperator.getOpname());
			hashmap.put("status", SystemConst.ONDUTY_REST_STR);
			hashmap.put("is_busy",  "<a href='javascript:void(0)' id=" + commonOperator.getOpid()+" style='color:green'>" +
					SystemConst.ONDUTY_FREE_STR + "</a>");
			if(null != onduty){
				hashmap.put("status", onduty.getStatus());
				if(onduty.getIs_busy() == SystemConst.ONDUTY_BUSY)
					hashmap.put("is_busy", "<a href='javascript:void(0)' id=" + commonOperator.getOpid()+" style='color:red'>" + SystemConst.ONDUTY_BUSY_STR + "</a>");
			}
			result.add(hashmap);
		}
		return result;
	
	}
}

