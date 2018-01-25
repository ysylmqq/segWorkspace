package com.chinagps.driverbook.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chinagps.driverbook.pojo.Feedback;

public interface FeedbackMapper extends BaseSqlMapper<Feedback> {
	
	public int countAll(Map<String, String> params);

	public List<Feedback> findByPage(Map<String, String> params, RowBounds rowBounds);

	public List<Feedback> findForFeedbackTask();

	public int updateStatus(Long id);
	
}
