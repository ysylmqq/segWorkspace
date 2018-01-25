package com.gboss.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.Bankcode;
import com.gboss.pojo.web.VerifyPOJO;
import com.gboss.service.BankcodeService;
import com.gboss.util.StringUtils;

/**
 * @Package:com.gboss.controller
 * @ClassName:BankcodeController
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-6-4 下午4:38:05
 */
@Controller
public class BankcodeController extends BaseController {
	
	@Autowired
	@Qualifier("BankcodeService")
	private BankcodeService bankcodeService;
	
	@RequestMapping(value = "bankcode/getBankcode", method = RequestMethod.POST)
	public @ResponseBody List<Bankcode> getBankcode(@RequestBody VerifyPOJO verifyPOJO, HttpServletRequest request) throws SystemException {
		String parameter = null;
		if(verifyPOJO!=null){
			parameter = verifyPOJO.getParameter();
		}
		String companyid = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		List<Bankcode> result = null;
		if(StringUtils.isBlank(parameter)){
			result = bankcodeService.getBankcodeList(Long.valueOf(companyid));
		}else{
			result = bankcodeService.getBankcodeList(Long.valueOf(companyid), Integer.valueOf(parameter));
		}
		return result;
	}

}

