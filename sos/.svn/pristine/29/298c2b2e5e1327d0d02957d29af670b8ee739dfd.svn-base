package com.gboss.pojo.weather.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * @Package:com.gboss.pojo
 * @ClassName:Answer
 * @Description:TODO
 * @author:yusongya
 * @date:2017-6-15 下午2:20:03
 */
@XStreamAlias("CityWeatherResponse")
public class CityWeatherResponseXml implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 返回状态 
	 */
	private String error;
	/**
	 * 返回状态 success
	 */
	private String status;
	
	/**
	 * 今天的日期  2017-06-15
	 */
	private String date;
	
	/**
	 * 包含当前城市  未来7天的天气  如果支持多个城市用List返回
	 */
	private ResultXml results;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public ResultXml getResults() {
		return results;
	}

	public void setResults(ResultXml results) {
		this.results = results;
	}
}
