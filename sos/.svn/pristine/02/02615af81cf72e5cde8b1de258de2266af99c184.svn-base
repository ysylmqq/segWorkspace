package com.gboss.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.NewsDao;
import com.gboss.pojo.NewsImages;
import com.gboss.service.NewsService;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:NewsServiceImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-6-17 上午9:10:37
 */
@Repository("newsService")  
@Transactional
public class NewsServiceImpl  extends BaseServiceImpl implements NewsService{

	@Autowired  
	@Qualifier("newsDao")
	private NewsDao newsDao;

	@Override
	public List<HashMap<String, Object>> getNewsList(Long companyId) throws SystemException {
		return newsDao.getNewsList(companyId);
	}

	@Override
	public Page<HashMap<String, Object>> findNewsList(Long companyId,
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total = newsDao.countNews(companyId, pageSelect.getFilter());
		List<HashMap<String, Object>> list=newsDao.findNews(companyId, pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

	@Override
	public Page<HashMap<String, Object>> findNewsSaigeList(
			PageSelect<Map<String, Object>> pageSelect) {
		
		int total = newsDao.countNews(pageSelect.getFilter());
		List<HashMap<String, Object>> list=newsDao.findNews(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	
	}
	@Override
	public int operateNews(List<Long> ids, Integer type) throws SystemException {
		int result=1;
		if(ids==null || ids.isEmpty()){
			result=-1;
		}else{
			newsDao.operateNews(ids, type);
		}
		return result;
	}

	@Override
	public int delete(List<Long> ids) throws SystemException {
		int result=1;
		if(ids==null || ids.isEmpty()){
			result=-1;
		}else{
			newsDao.delete(ids);
		}
		return result;
	}

	@Override
	public List<NewsImages> getNewsImages(Long newsId) {
		return newsDao.getNewsImages(newsId);
	}

	@Override
	public int operateNewsByType(List<Long> ids, Integer type,
			Integer updateType,Map<String,Object> param) throws SystemException {
		int result=1;
		if(ids==null || ids.isEmpty()){
			result=-1;
		}else{
			newsDao.operateNews(ids, type, updateType, param);
		}
		return result;
	}


}

