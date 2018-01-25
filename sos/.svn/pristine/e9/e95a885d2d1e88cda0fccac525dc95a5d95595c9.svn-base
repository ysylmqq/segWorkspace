package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Record;

/**
 * @Package:com.gboss.dao
 * @ClassName:RecordDao
 * @Description:TODO
 * @author:bzhang
 * @date:2014-8-13 下午3:07:19
 */
public interface RecordDao extends BaseDao{
	
	public List<HashMap<String, Object>> findRecord(Long companyno, Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;

	public int countRecord(Long companyno, Map<String, Object> conditionMap) throws SystemException;
	
	public boolean is_exist(Record record);
	
	public int delete(List<Long> ids) throws SystemException;
}

