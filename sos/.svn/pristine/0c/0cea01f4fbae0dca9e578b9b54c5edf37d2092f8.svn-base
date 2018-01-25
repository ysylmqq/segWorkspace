package com.gboss.service;

import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

public interface HMReportService extends BaseService {

	/**
	 * 分页查询阶段1情况
	 * @param pageSelect
	 * @return
	 * @throws SystemException
	 */
	Page<Map<String, Object>> findPeriodPage(PageSelect<Map<String, Object>> pageSelect)throws SystemException;

	/**
	 * 分页查询sim卡月使用情况
	 * @param pageSelect
	 * @return
	 * @throws SystemException
	 */
	Page<Map<String, Object>> findSimHistoryPage(PageSelect<Map<String, Object>> pageSelect)throws SystemException;
	
	/**
	 * 分页查询当前sim卡使用情况
	 * @param pageSelect
	 * @return
	 * @throws SystemException
	 */
	Page<Map<String, Object>> findSimCurrentPage(PageSelect<Map<String, Object>> pageSelect)throws SystemException;

	/**
	 * 阶段查询
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	List<Map<String, Object>> findPeriodList(Map<String, Object> params)throws SystemException;

	/**
	 * 阶段查询
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	List<Map<String, Object>> findHistoryList(Map<String, Object> params)throws SystemException;

	/**
	 * 阶段查询
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	List<Map<String, Object>> findCurrentList(Map<String, Object> params)throws SystemException;
}
