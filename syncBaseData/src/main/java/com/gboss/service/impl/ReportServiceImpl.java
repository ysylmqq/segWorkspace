package com.gboss.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ldap.objectClasses.CommonCompany;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.dao.ReportDao;
import com.gboss.service.ReportService;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

@Service("reportService")
 
public class ReportServiceImpl extends BaseServiceImpl implements ReportService {

	@Autowired
	@Qualifier("reportDao")
	private ReportDao rptDao;

	private OpenLdap ldap = OpenLdapManager.getInstance();
	
	private Map<String, String> getFcShopMap(){
		Map<String, String> map = new HashMap<String, String>();
		List<CommonCompany> list = ldap.getChildsByCompanyId("201");
		for(CommonCompany company : list){
			map.put(company.getCompanyno(), company.getCompanyname());
		}
		return map;
	}

	 
	public Page<Map<String, Object>> findJoinNetSumPage(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		Map<String, String> companyMap = getFcShopMap();
		int total = rptDao.countJoinNetSum(pageSelect.getFilter());
		List<Map<String, Object>> list = rptDao.listJoinNetSum(pageSelect.getFilter(),pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		list = getConpanyName(list);
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

	 
	public Page<Map<String, Object>> findJoinNetDetailPage(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total = rptDao.countJoinNetDetail(pageSelect.getFilter());
		List<Map<String, Object>> list = rptDao.listJoinNetDetail(pageSelect.getFilter(),pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		list = getConpanyName(list);
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

	 
	public Page<Map<String, Object>> findOffNetSumPage(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total = rptDao.countOffNetSum(pageSelect.getFilter());
		List<Map<String, Object>> list = rptDao.listOffNetSum(pageSelect.getFilter(),pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		list = getConpanyName(list);
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

	 
	public Page<Map<String, Object>> findOffNetDetailPage(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total = rptDao.countOffNetDetail(pageSelect.getFilter());
		List<Map<String, Object>> list = rptDao.listOffNetDetail(pageSelect.getFilter(),pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		for(Map<String, Object> map : list){
			Object a = map.get("s_id");
			String companyName ="未知";
			if(StringUtils.isNotNullOrEmpty(a)){
				CommonCompany company = ldap.getCompanyById(a.toString());
				if(null != company)
					companyName = company.getCompanyname();
			}	
			map.put("companyName",companyName);
			map.put("reason", SystemConst.stopReasonMap.get(map.get("type").toString()));
		}
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

	 
	public Page<Map<String, Object>> findUnServiceSumPage(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total = rptDao.countUnServiceSum(pageSelect.getFilter());
		List<Map<String, Object>> list = rptDao.listUnServiceSum(pageSelect.getFilter(),pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		list = getConpanyName(list);
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

	 
	public Page<Map<String, Object>> findUnServiceDetailPage(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total = rptDao.countUnServiceDetail(pageSelect.getFilter());
		List<Map<String, Object>> list = rptDao.listUnServiceDetail(pageSelect.getFilter(),pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		list = getConpanyName(list);
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

	 
	public List<Map<String, Object>> findModelList(Map<String, Object> param)
			throws SystemException {
		return rptDao.findModelList(param);
	}

	 
	public Page<Map<String, Object>> findStockStatisticsPage(Long companyId,
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total = rptDao.countStockStatistics(companyId,pageSelect.getFilter());
		List<Map<String, Object>> list = rptDao.listStockStatistics(companyId,pageSelect.getFilter(),pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

	 
	public Page<Map<String, Object>> findUnfixedStatisticsPage(Long companyId,
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total = rptDao.countUnfixedStatistics(companyId,pageSelect.getFilter());
		List<Map<String, Object>> list = rptDao.listUnfixedStatistics(companyId,pageSelect.getFilter(),pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}
	
	public List<Map<String, Object>> getConpanyName(List<Map<String, Object>> list){
		for(Map<String, Object> map : list){
			Object a = map.get("s_id");
			String companyName ="未知";
			if(StringUtils.isNotNullOrEmpty(a)){
				CommonCompany company = ldap.getCompanyById(a.toString());
				if(null != company)
					companyName = company.getCompanyname();
			}	
			map.put("companyName",companyName);
		}
		return list;
	}
}
