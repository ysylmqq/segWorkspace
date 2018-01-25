package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;

/**
 * @Package:com.gboss.dao
 * @ClassName:AnswerDao
 * @Description:TODO
 * @author:bzhang
 * @date:2014-4-28 下午3:53:14
 */
public interface AnswerDao extends BaseDao {
	
	
	public List<HashMap<String, Object>> findAnswers(Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;

	public int countAnswers(Map<String, Object> conditionMap) throws SystemException;
	

}

