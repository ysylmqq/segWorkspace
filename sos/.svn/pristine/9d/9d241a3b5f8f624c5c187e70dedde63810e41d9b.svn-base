package com.gboss.service.impl;

import java.util.ArrayList;
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
import com.gboss.util.Utils;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

@Service("reportService")
@Transactional
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

	@Override
	public Page<Map<String, Object>> findJoinNetSumPage(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		Map<String, String> companyMap = getFcShopMap();
		int total = rptDao.countJoinNetSum(pageSelect.getFilter());
		List<Map<String, Object>> list = rptDao.listJoinNetSum(pageSelect.getFilter(),pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		list = getConpanyName(list);
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

	@Override
	public Page<Map<String, Object>> findJoinNetDetailPage(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total = rptDao.countJoinNetDetail(pageSelect.getFilter());
		List<Map<String, Object>> list = rptDao.listJoinNetDetail(pageSelect.getFilter(),pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		list = getConpanyName(list);
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

	@Override
	public Page<Map<String, Object>> findOffNetSumPage(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total = rptDao.countOffNetSum(pageSelect.getFilter());
		List<Map<String, Object>> list = rptDao.listOffNetSum(pageSelect.getFilter(),pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		list = getConpanyName(list);
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

	@Override
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

	@Override
	public Page<Map<String, Object>> findUnServiceSumPage(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total = rptDao.countUnServiceSum(pageSelect.getFilter());
		List<Map<String, Object>> list = rptDao.listUnServiceSum(pageSelect.getFilter(),pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		list = getConpanyName(list);
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

	@Override
	public Page<Map<String, Object>> findUnServiceDetailPage(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total = rptDao.countUnServiceDetail(pageSelect.getFilter());
		List<Map<String, Object>> list = rptDao.listUnServiceDetail(pageSelect.getFilter(),pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		list = getConpanyName(list);
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

	@Override
	public List<Map<String, Object>> findModelList(Map<String, Object> param)
			throws SystemException {
		return rptDao.findModelList(param);
	}

	@Override
	public Page<Map<String, Object>> findStockStatisticsPage(Long companyId,
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total = rptDao.countStockStatistics(companyId,pageSelect.getFilter());
		List<Map<String, Object>> list = rptDao.listStockStatistics(companyId,pageSelect.getFilter(),pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

	@Override
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

	@Override
	public Page<Map<String, Object>> findPrivateWeekPage(Map<String, Object> params, Integer pn, Integer pageSize)throws SystemException {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		OpenLdap ldap = OpenLdapManager.getInstance();
		List<CommonCompany> companylist = ldap.getChildsByCompanyId("3");//获得所有分公司
		int total = companylist.size();
		int start = (pn-1)*pageSize;
		int end = pn*pageSize;
		int num = total<end?total:end;
		String dateStr = params.get("startDate").toString()+"至"+ params.get("endDate").toString();
		for(int i = start; i < num; i++){
			CommonCompany company = companylist.get(i);
			params.put("subco_no", company.getCompanyno());
			Map<String, Object> map = rptDao.findPrivateWeekList(params);
			map.put("subco_name", company.getCompanyname());
			//净增长
			Long increase = 0l;
			//时间段前在网总数
			Long lastinnet = 0l;
			Long join = map.get("newjoin") == null? 0 : Long.valueOf(map.get("newjoin").toString());//新增入网
			Long off = map.get("nowoff") == null? 0 : Long.valueOf(map.get("nowoff").toString());	//新增离网
			Long innet = map.get("innet") == null? 0 : Long.valueOf(map.get("innet").toString());	//在网总数
			Long offnet = map.get("offnet") == null? 0 : Long.valueOf(map.get("offnet").toString());//离网总数
			increase = join - off;
			lastinnet = innet - increase;
			map.put("increase", increase);
			map.put("last", lastinnet);
			map.put("date_str", dateStr);
			map.put("in_off", innet+"/"+offnet);
			list.add(map);
		}
		return PageUtil.getPage(total, pn, list, pageSize);
	}

	@Override
	public List<Map<String, Object>> listPrivateWeek(Map<String, Object> params)
			throws SystemException {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		OpenLdap ldap = OpenLdapManager.getInstance();
		List<CommonCompany> companylist = ldap.getChildsByCompanyId("3");//获得所有分公司
		String dateStr = params.get("startDate").toString()+"至"+ params.get("endDate").toString();
		for(CommonCompany company : companylist){
			params.put("subco_no", company.getCompanyno());
			Map<String, Object> map = rptDao.findPrivateWeekList(params);
			map.put("subco_name", company.getCompanyname());
			//净增长
			Long increase = 0l;
			//时间段前在网总数
			Long lastinnet = 0l;
			Long join = map.get("newjoin") == null? 0 : Long.valueOf(map.get("newjoin").toString());//新增入网
			Long off = map.get("nowoff") == null? 0 : Long.valueOf(map.get("nowoff").toString());	//新增离网
			Long innet = map.get("innet") == null? 0 : Long.valueOf(map.get("innet").toString());	//在网总数
			Long offnet = map.get("offnet") == null? 0 : Long.valueOf(map.get("offnet").toString());//离网总数
			increase = join - off;
			lastinnet = innet - increase;
			map.put("increase", increase);
			map.put("last", lastinnet);
			map.put("date_str", dateStr);
			map.put("in_off", innet+"/"+offnet);
			list.add(map);
		}
		return list;
	}
}
