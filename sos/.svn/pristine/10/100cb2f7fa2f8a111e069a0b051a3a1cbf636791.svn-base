package com.gboss.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.gboss.pojo.Combo;
import com.gboss.pojo.Simpack;
import com.gboss.pojo.Subcoft;
import com.gboss.pojo.web.VerifyPOJO;
import com.gboss.service.SimpackService;

/**
 * @Package:com.gboss.controller
 * @ClassName:SimpackController
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-12-11 下午5:27:31
 */
@Controller
public class SimpackController extends BaseController {
	
	@Autowired
	@Qualifier("SimpackService")
	private SimpackService simpackService;
	
	@RequestMapping(value = "/listSubcoft", method = RequestMethod.POST)
	public @ResponseBody List<Subcoft> listSubcoft
		(@RequestBody VerifyPOJO verifyPOJO, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		String companyid = (String)request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		List<Subcoft> list = simpackService.getSubsofts(Long.valueOf(companyid));
		return list;
	}
	
	@RequestMapping(value = "/getSimpack", method = RequestMethod.POST)
	public @ResponseBody List<Simpack> getSimpack
		(@RequestBody VerifyPOJO verifyPOJO, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		String companyid = (String)request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		String feetype_id = verifyPOJO.getParameter();
		List<Simpack> simpacks = simpackService.getSimpacks(Long.valueOf(companyid), Integer.valueOf(feetype_id));
		return simpacks;
	}
	
	@RequestMapping(value = "/getComboByid", method = RequestMethod.POST)
	public @ResponseBody Combo getComboByid
		(@RequestBody VerifyPOJO verifyPOJO, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		String combo_id = verifyPOJO.getParameter();
		Combo combo = simpackService.getComboByid(Long.valueOf(combo_id));
		return combo;
	}
	
	@RequestMapping(value = "/getSimpackByid", method = RequestMethod.POST)
	public @ResponseBody Simpack getSimpackByid
		(@RequestBody VerifyPOJO verifyPOJO, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		String pack_id = verifyPOJO.getParameter();
		Simpack simpack = simpackService.getSimpackByid(Long.valueOf(pack_id));
		return simpack;
	}

}

