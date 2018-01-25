package com.gboss.service;

import java.util.HashMap;
import java.util.List;

import ldap.objectClasses.CommonCompany;

public interface CompanyService extends BaseService{
	
	public List<HashMap<String, Object>> getCompanyTree(String companyname) ;
	
	public List<HashMap<String, Object>> getCompanyTree2(String companyname);
	
	public boolean addCompany(CommonCompany company);
	
	public boolean updateCompany(CommonCompany company);
	
	public boolean modifyCompany(CommonCompany company) ;
	
	public boolean deleteCompany(String companyno);
	
	public CommonCompany getById(String id);
	
	public List<CommonCompany> getCompanysByPid(String pid);
	
	
}
