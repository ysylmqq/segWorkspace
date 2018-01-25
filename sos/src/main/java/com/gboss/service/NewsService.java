package com.gboss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.NewsImages;
import com.gboss.pojo.SysValue;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service
 * @ClassName:NewsService
 * @Description:TODO
 * @author:lxb
 * @date 2014-10-30
 *
 */
public interface NewsService extends BaseService {
	
	public List<HashMap<String, Object>> getNewsList(Long companyId)throws SystemException;
	
	public Page<HashMap<String, Object>> findNewsList(Long companyId,PageSelect<Map<String, Object>> pageSelect) throws SystemException;

	public int operateNews(List<Long> ids, Integer type) throws SystemException;
	
	public int delete(List<Long> ids) throws SystemException;
	//可以删掉
	public List<NewsImages> getNewsImages(Long newsId);

	//修改几个状态：updateType 1：提交状态；2：审核状态；3:上架状态；4：下架
	public int operateNewsByType(List<Long> ids, Integer type, Integer updateType, Map<String, Object> param) throws SystemException;

	//查询所有提交审核的未删除的资讯
	public Page<HashMap<String, Object>> findNewsSaigeList(
			PageSelect<Map<String, Object>> pageSelect);
	
}
