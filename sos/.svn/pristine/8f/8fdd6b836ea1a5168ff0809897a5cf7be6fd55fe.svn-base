package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.NewsImages;

/**
 * @Package:com.gboss.dao
 * @ClassName:NewsDao
 * @Description:TODO
 * @author:bzhang
 * @date:2014-6-16 下午3:53:14
 */
public interface NewsDao extends BaseDao {
	
	public List<HashMap<String, Object>> getNewsList(Long companyId)throws SystemException;
	
    public int countNews(Long companyId,Map<String, Object> conditionMap) throws SystemException;
    
    public int operateNews(List<Long> ids, Integer type) throws SystemException;
    
    public int delete(List<Long> ids) throws SystemException;
    
    public List<NewsImages> getNewsImages(Long newsId);
	
	public List<HashMap<String, Object>> findNews(Long companyId,Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;

	public int operateNews(List<Long> ids, Integer type, Integer updateType,Map<String,Object> map);

	public int countNews(Map filter);

	public List<HashMap<String, Object>> findNews(Map filter, String order,
			boolean is_desc, int pageNo, int pageSize);

}
