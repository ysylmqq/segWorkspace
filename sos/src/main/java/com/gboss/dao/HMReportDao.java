package com.gboss.dao;

import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;

public interface HMReportDao extends BaseDao {

	Integer countPeriod(Map<String, Object> params)throws SystemException;

	List<Map<String, Object>> findPeriodList(Map<String, Object> params, String order, boolean is_desc, int pageNo, int pageSize)throws SystemException;

	Integer countSimHistory(Map<String, Object> params)throws SystemException;

	List<Map<String, Object>> findSimHistoryList(Map<String, Object> params, String order, boolean is_desc, int pageNo, int pageSize)throws SystemException;

	Integer countSimCurrent(Map<String, Object> params)throws SystemException;

	List<Map<String, Object>> findSimCurrentList(Map<String, Object> params, String order, boolean is_desc, int pageNo, int pageSize)throws SystemException;

}
