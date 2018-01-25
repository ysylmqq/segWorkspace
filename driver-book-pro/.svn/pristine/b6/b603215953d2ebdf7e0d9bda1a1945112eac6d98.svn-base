package com.chinagps.driverbook.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chinagps.driverbook.pojo.News;

public interface NewsMapper extends BaseSqlMapper<News> {
	
	public News findById(Long id);
	
	public int saigeCount(Map<String, String> params);
	
	public List<News> saigeNews(Map<String, String> params, RowBounds rowBounds);
	
	public int notSaigeCount(Map<String, String> params);
	
	public List<News> notSaigeNews(Map<String, String> params, RowBounds rowBounds);
	
}
