package com.gboss.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.gboss.comm.SystemException;
import com.gboss.dao.HMReportDao;
import com.gboss.service.HMReportService;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

@Service("hmReportService")
public class HMReportServiceImpl extends BaseServiceImpl implements HMReportService {
	
	@Autowired
	@Qualifier("hmReportDao")
	private HMReportDao hmReportDao;

	@Override
	public Page<Map<String, Object>> findPeriodPage(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total=hmReportDao.countPeriod(pageSelect.getFilter());
		List<Map<String,Object>> repairs=hmReportDao.findPeriodList(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), repairs, pageSelect.getPageSize());
	}

	@Override
	public Page<Map<String, Object>> findSimHistoryPage(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total=hmReportDao.countSimHistory(pageSelect.getFilter());
		List<Map<String,Object>> repairs=hmReportDao.findSimHistoryList(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), repairs, pageSelect.getPageSize());
	}

	@Override
	public Page<Map<String, Object>> findSimCurrentPage(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total=hmReportDao.countSimCurrent(pageSelect.getFilter());
		List<Map<String,Object>> repairs=hmReportDao.findSimCurrentList(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), repairs, pageSelect.getPageSize());
	}

	@Override
	public List<Map<String, Object>> findPeriodList(Map<String, Object> params)throws SystemException {
		return hmReportDao.findPeriodList(params, null, false, 0, 0);
	}

	@Override
	public List<Map<String, Object>> findHistoryList(Map<String, Object> params)throws SystemException {
		return hmReportDao.findSimHistoryList(params, null, false, 0, 0);
	}

	@Override
	public List<Map<String, Object>> findCurrentList(Map<String, Object> params)throws SystemException {
		return hmReportDao.findSimCurrentList(params, null, false, 0, 0);
	}

}
