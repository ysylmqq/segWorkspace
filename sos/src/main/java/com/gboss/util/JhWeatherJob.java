package com.gboss.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gboss.comm.SystemConfig;
import com.gboss.comm.SystemException;
import com.gboss.pojo.WhJhWeather;
import com.gboss.service.JhCityService;
import com.gboss.service.JhWeatherService;

@Repository
public class JhWeatherJob {
	
	@Autowired
	private JhCityService jhCityService;
	
	@Autowired
	private JhWeatherService jhWeatherService;
	
	@Autowired
	private SystemConfig systemConfig;
	
	public void flushWeather() throws SystemException, ClientProtocolException, IOException{
		System.out.println("开始同步天气");
		long start = System.currentTimeMillis();
		List<Map<String, Object>> list = jhCityService.findTodayFlushCity();
		List<WhJhWeather> weatherList = new ArrayList<WhJhWeather>();
		if(list != null && list.size() !=0 ){
			CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse httpResponse = null;
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map<String, Object> map = (Map<String, Object>) iterator.next();
				String city = (String) map.get("city");
	        	HttpGet get = new HttpGet(systemConfig.getJhWeatherUrl()+"?cityname="+city+"&key="+systemConfig.getJhAk()+"&format=2");
	            httpResponse = httpClient.execute(get);
	            HttpEntity entity = httpResponse.getEntity();
	            if (null != entity){
	            	int statusCode = httpResponse.getStatusLine().getStatusCode();
	                if( statusCode == 200){
	                	String context = EntityUtils.toString(entity);
	                	JSONObject jSONObject = JSONObject.fromObject(context);
	                	String resultcode = (String) jSONObject.get("resultcode");
	                	if("200".equals(resultcode)){
		                	Map<String,Object> result = (Map<String, Object>) jSONObject.get("result");
		                	String nowWeather =  ((JSONObject) result.get("sk")).toString();
		                	String todayWeather =  ((JSONObject) result.get("today")).toString();
		                	String futureWeather =  ((JSONArray) result.get("future")).toString();
		                	WhJhWeather whJhWeather = new WhJhWeather();
		        			whJhWeather.setCity(city);
		        			whJhWeather.setNowWeather(nowWeather);
		        			whJhWeather.setTodayWeather(todayWeather);
		        			whJhWeather.setFutureWeather(futureWeather);
		        			weatherList.add(whJhWeather);
	                	}
	                }
	            }
	            httpResponse.close();
	        }
	        if (httpClient != null){
	        	httpClient.close();
	        }
	        jhWeatherService.batchSave(weatherList);
		}
		long end = System.currentTimeMillis();
		System.err.println("Totaltime "+(end-start)/1000);
	}
	
	public void requestByGetMethodTest() throws ClientProtocolException, IOException{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        for(int i =0 ; i< 1;i++){
        	//HttpGet get = new HttpGet("http://v.juhe.cn/weather/index?cityname=深圳&key=34ebf2d5e7a31821351e36c1254547b9&format=2");
        	HttpGet get = new HttpGet("http://www.baidu.com");
            CloseableHttpResponse httpResponse = null;
            httpResponse = httpClient.execute(get);
            HttpEntity entity = httpResponse.getEntity();
            if (null != entity){
            	int statusCode = httpResponse.getStatusLine().getStatusCode();
                System.out.println("响应状态码:"+ statusCode);
                if( statusCode == 200){
                	String context = EntityUtils.toString(entity);
                	//JSONObject jSONObject = JSONObject.fromObject(context);
                	JSONObject jSONObject = JSONObject.fromObject("{'resultcode':'200','reason':'查询成功','result':{'sk':{'temp':'29','wind_direction':'东北风','wind_strength':'4级','humidity':'26%','time':'18:31'},'today':{'temperature':'21℃~35℃','weather':'多云转晴','weather_id':{'fa':'01','fb':'00'},'wind':'北风微风','week':'星期四','city':'朝阳','date_y':'2017年06月08日','dressing_index':'炎热','dressing_advice':'天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。','uv_index':'中等','comfort_index':'','wash_index':'较适宜','travel_index':'适宜','exercise_index':'适宜','drying_index':''},'future':[{'temperature':'21℃~35℃','weather':'多云转晴','weather_id':{'fa':'01','fb':'00'},'wind':'北风微风','week':'星期四','date':'20170608'},{'temperature':'21℃~37℃','weather':'晴转多云','weather_id':{'fa':'00','fb':'01'},'wind':'南风3-4 级','week':'星期五','date':'20170609'},{'temperature':'18℃~31℃','weather':'阴','weather_id':{'fa':'02','fb':'02'},'wind':'东风微风','week':'星期六','date':'20170610'},{'temperature':'18℃~29℃','weather':'阴转阵雨','weather_id':{'fa':'02','fb':'03'},'wind':'东风微风','week':'星期日','date':'20170611'},{'temperature':'18℃~28℃','weather':'多云','weather_id':{'fa':'01','fb':'01'},'wind':'南风微风','week':'星期一','date':'20170612'},{'temperature':'21℃~37℃','weather':'晴转多云','weather_id':{'fa':'00','fb':'01'},'wind':'南风3-4 级','week':'星期二','date':'20170613'},{'temperature':'18℃~31℃','weather':'阴','weather_id':{'fa':'02','fb':'02'},'wind':'东风微风','week':'星期三','date':'20170614'}]},'error_code':0}");
                	Map<String,Object> map = (Map<String, Object>) jSONObject.get("result");
                	//{"sk":{"temp":"29","wind_direction":"东北风","wind_strength":"4级","humidity":"26%","time":"18:31"},"today":{"temperature":"21℃~35℃","weather":"多云转晴","weather_id":{"fa":"01","fb":"00"},"wind":"北风微风","week":"星期四","city":"朝阳","date_y":"2017年06月08日","dressing_index":"炎热","dressing_advice":"天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。","uv_index":"中等","comfort_index":"","wash_index":"较适宜","travel_index":"适宜","exercise_index":"适宜","drying_index":""},"future":[{"temperature":"21℃~35℃","weather":"多云转晴","weather_id":{"fa":"01","fb":"00"},"wind":"北风微风","week":"星期四","date":"20170608"},{"temperature":"21℃~37℃","weather":"晴转多云","weather_id":{"fa":"00","fb":"01"},"wind":"南风3-4 级","week":"星期五","date":"20170609"},{"temperature":"18℃~31℃","weather":"阴","weather_id":{"fa":"02","fb":"02"},"wind":"东风微风","week":"星期六","date":"20170610"},{"temperature":"18℃~29℃","weather":"阴转阵雨","weather_id":{"fa":"02","fb":"03"},"wind":"东风微风","week":"星期日","date":"20170611"},{"temperature":"18℃~28℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"南风微风","week":"星期一","date":"20170612"},{"temperature":"21℃~37℃","weather":"晴转多云","weather_id":{"fa":"00","fb":"01"},"wind":"南风3-4 级","week":"星期二","date":"20170613"},{"temperature":"18℃~31℃","weather":"阴","weather_id":{"fa":"02","fb":"02"},"wind":"东风微风","week":"星期三","date":"20170614"}]}
                	System.out.println("响应内容:" + map);
                }
            }
            httpResponse.close();
        }
        if (httpClient != null){
        	httpClient.close();
        }
    }
	
	public static void main (String args[]){
		JhWeatherJob jhWeather = new JhWeatherJob();
		try {
			jhWeather.requestByGetMethodTest();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
