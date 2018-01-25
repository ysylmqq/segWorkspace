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

import com.gboss.comm.SystemException;
import com.gboss.pojo.Serviceitem;
import com.gboss.service.ServiceItemService;

/**
 * @Package:com.gboss.controller
 * @ClassName:ServiceitemController
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-7-2 下午4:44:27
 */
@Controller
public class ServiceitemController extends BaseController {

	@Autowired
	@Qualifier("ServiceItemService")
	private ServiceItemService serviceItemService;
	
	@RequestMapping(value = "/serviceitem/listServiceItem", method = RequestMethod.POST)
	public @ResponseBody List<Serviceitem> listServiceItem
		(@RequestBody Serviceitem serviceitem, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		List<Serviceitem> list = serviceItemService.findServiceitem(serviceitem);
		return list;
	}	
	
}

