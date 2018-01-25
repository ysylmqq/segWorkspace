package com.gboss.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Dict;
import com.gboss.service.DictService;

@Controller
public class DictController extends BaseController{
	
	@Autowired
	private DictService dictService;
	
	@RequestMapping(value = "/getDicts", method = RequestMethod.POST)
	public @ResponseBody List<Dict> getDicts(@RequestBody Dict dict, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		return dictService.getDicts(dict.getType(), dict.getName());
	}
	
}
