package com.gboss.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ldap.objectClasses.CommonCompany;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gboss.util.StringUtils;
import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.dao.CompanyDao;

@Controller
public class CompanyController extends BaseController{
	
	@Autowired
	private CompanyDao companyDao;
	
	@RequestMapping(value = "/company/delete", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> delete(@RequestBody CommonCompany company, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		CommonCompany commonCompany = companyDao.getById(company.getCompanyno());
		boolean result = companyDao.deleteCompany(company.getCompanyno());
		String msg = result==true?SystemConst.OP_SUCCESS:SystemConst.OP_FAILURE;
		map.put("success", result);
		map.put(SystemConst.MSG, msg);
		return map;
	}
	
	@RequestMapping(value = "/company/add", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> add(@RequestBody CommonCompany company, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		OpenLdap ldap = OpenLdapManager.getInstance();
		CommonCompany checkcompany = ldap.getCompanyByname(company.getCompanyname());
		if(checkcompany!=null){
			map.put("success", false);
			map.put(SystemConst.MSG, "操作失败，公司名称已存在!");
			return map;
		}
		String parentcompanyno = company.getParentcompanyno();
		List<CommonCompany> list = companyDao.getCompanysByPid(parentcompanyno);
		CommonCompany pcompany = companyDao.getById(parentcompanyno);
		int codeint = 0;
		if(list.size()>0){
			for(CommonCompany c:list){
				if(StringUtils.isBlank(c.getCompanycode())){
					continue;
				}
				int swap = 0;
				if(c.getCompanycode().length()>2){
					swap = Integer.valueOf(c.getCompanycode().substring(c.getCompanycode().length()-2));
				}else{
					swap = Integer.valueOf(c.getCompanycode());
				}
				if(swap > codeint){
					codeint = swap;
				}
			}
		}
		String companycode = String.valueOf((codeint+1));
		if(companycode.length()==1){
			companycode = "0"+companycode;
		}
		String pcompanycode = "";
		if(StringUtils.isNotBlank(pcompany.getCompanycode())){
			pcompanycode = pcompany.getCompanycode();
		}
		company.setCompanycode(pcompanycode+companycode);
		boolean result = companyDao.addCompany(company);
		String msg = result==true?SystemConst.OP_SUCCESS:SystemConst.OP_FAILURE;
		map.put("success", result);
		map.put(SystemConst.MSG, msg);
		return map;
	}
	
	@RequestMapping(value = "/company/update", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> update(@RequestBody CommonCompany company, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		OpenLdap ldap = OpenLdapManager.getInstance();
		CommonCompany checkcompany = ldap.getCompanyByname(company.getCompanyname());
		if(checkcompany !=null){
			if(!checkcompany.getCompanyno().equals(company.getCompanyno())){
				map.put("success", false);
				map.put(SystemConst.MSG, "操作失败，公司名称已存在");
				return map;
			}
		}
		boolean result = companyDao.updateCompany(company);
		String msg = result==true?SystemConst.OP_SUCCESS:SystemConst.OP_FAILURE;
		map.put("success", result);
		map.put(SystemConst.MSG, msg);
		return map;
	}
	
	@RequestMapping(value = "/company/tree")
	public @ResponseBody List<HashMap<String, Object>> tree
			(@RequestBody CommonCompany company, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		String companyname = company.getCompanyname();
		List<HashMap<String, Object>> list = companyDao.getCompanyTree(companyname);
		return list;
	}
	
	@RequestMapping(value = "/company/tree2")
	public @ResponseBody List<HashMap<String, Object>> tree2
			(CommonCompany company, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		String companyname = company.getCompanyname();
		List<HashMap<String, Object>> list = companyDao.getCompanyTree2(companyname);
		return list;
	}
	
}
