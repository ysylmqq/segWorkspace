package com.gboss.service;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.gboss.comm.SystemException;
import com.gboss.pojo.WhJhWeather;


/**
 * @Package:com.gboss.service
 * @ClassName:ServicepackService
 * @Description:TODO
 * @author:yusongya
 * @date:2017-6-9 下午4:24:37
 */
public interface JhWeatherService extends BaseService {
	/**
	 * 
	 * @throws SystemException
	 * 插入t_wh_jhweather
	 */
	public void save(WhJhWeather whJhWeather)throws SystemException;

	/**
	 * 批量保存 
	 * @param list
	 * @throws SystemException
	 */
	public void batchSave(List<WhJhWeather> list) throws SystemException;
	
	
	/**
	 * 根据city获取city天气 
	 * @param list
	 * @throws SystemException
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public WhJhWeather getWeatherByCity(String city) throws SystemException, ClientProtocolException, IOException;
	

	/**
	 * 根据经纬度获取city天气 
	 * @param list
	 * @throws SystemException
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public WhJhWeather getWeatherByLngLat(String lng,String lat) throws SystemException, ClientProtocolException, IOException;


}

