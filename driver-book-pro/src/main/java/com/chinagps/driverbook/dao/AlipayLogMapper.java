package com.chinagps.driverbook.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chinagps.driverbook.pojo.AlipayLog;

public interface AlipayLogMapper extends BaseSqlMapper<AlipayLog> {

	public int countAll(Map<String, String> params);
	
	public List<AlipayLog> findByPage(Map<String, String> params, RowBounds rowBounds);
	
}
