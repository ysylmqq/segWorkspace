package com.gboss.service.impl;

import java.util.HashMap;
import java.util.List;

import ldap.objectClasses.CommonCompany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gboss.dao.CompanyDao;
import com.gboss.service.CompanyService;

@Service("companyService")
public class CompanyServiceImpl extends BaseServiceImpl implements CompanyService {

	@Autowired
	private CompanyDao companyDao;
	
	public List<HashMap<String, Object>> getCompanyTree(String companyname) {
		return companyDao.getCompanyTree(companyname);
	}

	public List<HashMap<String, Object>> getCompanyTree2(String companyname) {
		return companyDao.getCompanyTree2(companyname);
	}

	 
	public boolean addCompany(CommonCompany company) {
		return companyDao.addCompany(company);
	}

	 
	public boolean updateCompany(CommonCompany company) {
		return companyDao.updateCompany(company);
	}

	 
	public boolean modifyCompany(CommonCompany company) {
		return companyDao.modifyCompany(company);
	}

	 
	public boolean deleteCompany(String companyno) {
		return companyDao.deleteCompany(companyno);
	}

	public CommonCompany getById(String id) {
		return companyDao.getById(id);
	}

	public List<CommonCompany> getCompanysByPid(String pid) {
		return companyDao.getCompanysByPid(pid);
	}

}
