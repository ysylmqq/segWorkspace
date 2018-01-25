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
import com.gboss.pojo.Servicepack;
import com.gboss.pojo.web.VerifyPOJO;
import com.gboss.service.ServicepackService;

/**
 * @Package:com.gboss.controller
 * @ClassName:ServicepackController
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-6-6 下午4:34:21
 */
@Controller
public class ServicepackController extends BaseController {
	
	@Autowired
	@Qualifier("ServicepackService")
	private ServicepackService servicepackService;
	
	@RequestMapping(value = "/servicepack/listServicePack", method = RequestMethod.POST)
	public @ResponseBody List<Servicepack> listServicePack
		(@RequestBody VerifyPOJO verifyPOJO, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		String companyid = (String)request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		List<Servicepack> list = servicepackService.getServicepacks(Long.valueOf(companyid));
		return list;
	}

}

