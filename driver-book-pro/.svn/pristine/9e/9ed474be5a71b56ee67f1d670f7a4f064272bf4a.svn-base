package com.chinagps.driverbook.service;

import java.util.Map;

import com.chinagps.driverbook.pojo.News;
import com.chinagps.driverbook.pojo.NewsRecord;
import com.chinagps.driverbook.pojo.ReturnValue;

public interface INewsService extends BaseService<News> {
	
	public News findById(Long id) throws Exception;
	
	/**
	 * 列举机构下的资讯信息
	 * @param news 资讯实例
	 * @param rv ReturnValue实例
	 * @return ReturnValue返回值实例
	 * @throws Exception
	 */
	public ReturnValue listNews(Map<String, String> params, ReturnValue rv) throws Exception;
	
	public int addRecord(NewsRecord newsRecord) throws Exception;
	
}
