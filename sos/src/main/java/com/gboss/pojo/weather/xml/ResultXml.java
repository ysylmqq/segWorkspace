package com.gboss.pojo.weather.xml;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

/**
 * @Package:com.gboss.pojo
 * @ClassName:Answer
 * @Description:TODO
 * @author:yusongya
 * @date:2017-6-15 下午2:20:03
 */
public class ResultXml implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 当前城市
	 */
	private String currentCity;
	
	/**
	 * 连续7天的天气
	 */
	private WeatherDataXml weather_data;
	
	/**
	 * 温馨提示
	 */
	private IndexXml index;
	
	/**
	 * PM2.5
	 */
	private String pm25;
	
	public String getCurrentCity() {
		return currentCity;
	}

	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}

	public WeatherDataXml getWeather_data() {
		return weather_data;
	}

	public void setWeather_data(WeatherDataXml weather_data) {
		this.weather_data = weather_data;
	}

	public IndexXml getIndex() {
		return index;
	}

	public void setIndex(IndexXml index) {
		this.index = index;
	}

	public String getPm25() {
		return pm25;
	}

	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}
	
}
