package com.gboss.service;

import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

public interface ReportService extends BaseService {

	Page<Map<String, Object>> findJoinNetSumPage(
			PageSelect<Map<String, Object>> pageSelect)throws SystemException;

	Page<Map<String, Object>> findJoinNetDetailPage(
			PageSelect<Map<String, Object>> pageSelect)throws SystemException;

	Page<Map<String, Object>> findOffNetSumPage(
			PageSelect<Map<String, Object>> pageSelect)throws SystemException;

	Page<Map<String, Object>> findOffNetDetailPage(
			PageSelect<Map<String, Object>> pageSelect)throws SystemException;

	Page<Map<String, Object>> findUnServiceSumPage(
			PageSelect<Map<String, Object>> pageSelect)throws SystemException;

	Page<Map<String, Object>> findUnServiceDetailPage(
			PageSelect<Map<String, Object>> pageSelect)throws SystemException;
	
	Page<Map<String, Object>> findStockStatisticsPage(Long companyId,
			PageSelect<Map<String, Object>> pageSelect)throws SystemException;

	Page<Map<String, Object>> findUnfixedStatisticsPage(Long companyId,
			PageSelect<Map<String, Object>> pageSelect)throws SystemException;

	List<Map<String, Object>> findModelList(Map<String, Object> param)throws SystemException;

}
