package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;

public interface ReportDao extends BaseDao {

	int countJoinNetSum(Map<String, Object> param)throws SystemException;

	List<Map<String, Object>> listJoinNetSum(Map<String, Object> param, String order,
			boolean is_desc, int pageNo, int pageSize)throws SystemException;

	int countJoinNetDetail(Map<String, Object> param)throws SystemException;

	List<Map<String, Object>> listJoinNetDetail(Map<String, Object> param, String order,
			boolean is_desc, int pageNo, int pageSize)throws SystemException;

	int countOffNetSum(Map<String, Object> param)throws SystemException;

	List<Map<String, Object>> listOffNetSum(Map<String, Object> param, String order,
			boolean is_desc, int pageNo, int pageSize)throws SystemException;

	int countOffNetDetail(Map<String, Object> param)throws SystemException;

	List<Map<String, Object>> listOffNetDetail(Map<String, Object> param, String order,
			boolean is_desc, int pageNo, int pageSize)throws SystemException;

	int countUnServiceSum(Map<String, Object> param)throws SystemException;

	List<Map<String, Object>> listUnServiceSum(Map<String, Object> param, String order,
			boolean is_desc, int pageNo, int pageSize)throws SystemException;

	int countUnServiceDetail(Map<String, Object> param)throws SystemException;

	List<Map<String, Object>> listUnServiceDetail(Map<String, Object> param, String order,
			boolean is_desc, int pageNo, int pageSize)throws SystemException;

	int countStockStatistics(Long companyId,Map<String, Object> param)throws SystemException;

	List<Map<String, Object>> listStockStatistics(Long companyId, Map<String, Object> param, String order,
			boolean is_desc, int pageNo, int pageSize)throws SystemException;
	
	int countUnfixedStatistics(Long companyId,Map<String, Object> param)throws SystemException;

	List<Map<String, Object>> listUnfixedStatistics(Long companyId, Map<String, Object> param, String order,
			boolean is_desc, int pageNo, int pageSize)throws SystemException;

	Integer getOnNetCount(Integer fcId, Map<String, Object> param)throws SystemException;

	Integer getOffCount(Integer fcId, Map<String, Object> param)throws SystemException;

	List<Map<String, Object>> findModelList(Map<String, Object> param)throws SystemException;

	Map<String, Object> findPrivateWeekList(Map<String, Object> params)throws SystemException;

}
