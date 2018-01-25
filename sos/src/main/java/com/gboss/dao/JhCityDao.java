package com.gboss.dao;

import java.util.List;
import java.util.Map;

import com.gboss.pojo.WhJhCity;


/**
 * @Package:com.gboss.dao
 * @ClassName:ChannelDao
 * @Description:聚合天气dao
 * @author:yusongya
 * @date:2017-6-9 下午2:38:17
 */
public interface JhCityDao extends BaseDao {
	
	/**
	 * 获取当天需要刷新的城市数据
	 * @return
	 */
	public List<Map<String, Object>> findTodayFlushCity();
	
	/**
	 * 根据city查询  t_wh_jhcity
	 * @return
	 */
	public WhJhCity findWhJhCityByCity(String city);

}

