package com.gboss.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemConfig;
import com.gboss.comm.SystemException;
import com.gboss.dao.JhCityDao;
import com.gboss.dao.JhWeatherDao;
import com.gboss.pojo.WhJhCity;
import com.gboss.pojo.WhJhWeather;
import com.gboss.service.JhWeatherService;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:CarTypeServiceImpl
 * @Description:
 * @author:yusongya
 * @date:2017-6-9 下午5:22:45
 */
@Service
@Transactional
public class JhWeatherServiceImpl extends BaseServiceImpl implements
JhWeatherService {
	
protected static final Logger LOGGER = LoggerFactory.getLogger(JhWeatherServiceImpl.class);
	
	@Autowired  
	private JhWeatherDao jhWeatherDao;
	
	@Autowired  
	private JhCityDao jhCityDao;
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Autowired
	private SystemConfig systemConfig;

	@Override
	public void save(WhJhWeather whJhWeather) throws SystemException {
		jhWeatherDao.save(whJhWeather);
	}
	
	@Override
	public void batchSave(List<WhJhWeather> list) {
		/**
		 * openSession一定要手动控制事物,不然不会执行sql getcurrentSession一定要包含事物,不然会报错
		 */
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		int c = 0;
		try{
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				WhJhWeather whJhWeather = (WhJhWeather) iterator.next();
				session.save(whJhWeather);
				if( c%30 == 0 ){
					session.flush();
					session.clear();
					tx.commit();
		            tx = session.beginTransaction();
				}
				c++;
			}
			tx.commit(); // 提交事物  
		}catch (Exception e) {  
            e.printStackTrace(); // 打印错误信息  
            tx.rollback(); // 出错将回滚事物  
        } finally {  
        	session.close(); // 关闭Session  
        }  
	}

	@Override
	public WhJhWeather getWeatherByCity(String city) throws SystemException, ClientProtocolException, IOException {
		//先判断t_wh_weather当中是否有city的天气  如有 直接返回 
		WhJhWeather whJhWeather = jhWeatherDao.getJhWeatherByCity(city);
		if( whJhWeather != null ){
			return whJhWeather;
		}else{
			//没有时，直接去聚合查找 ,
			whJhWeather = getJhWeatherinsert(city);
			if( whJhWeather != null && !"".equals(whJhWeather.getCity()) && !"".equals(whJhWeather.getNowWeather())){
				return whJhWeather;
			}else{
				return null;
			}
		}
	}
	
	/**
	 * 根据城市名去聚合当中查询天气，并且判断t_wh_jhcity当中是否有个这个城市，没有了就重新加入
	 * 防止聚合添加某些城市的天气查询
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public  WhJhWeather getJhWeatherinsert(String city) throws ClientProtocolException, IOException{
    	WhJhWeather whJhWeather = new WhJhWeather();
        CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet get = new HttpGet(systemConfig.getJhWeatherUrl()+"?cityname="+city+"&key="+systemConfig.getJhAk()+"&format=2");
        CloseableHttpResponse httpResponse = null;
        httpResponse = httpClient.execute(get);
        HttpEntity entity = httpResponse.getEntity();
        if (null != entity){
        	int statusCode = httpResponse.getStatusLine().getStatusCode();
            if( statusCode == 200){
            	String context = EntityUtils.toString(entity);
            	JSONObject jSONObject = JSONObject.fromObject(context);
            	String resultcode = (String) jSONObject.get("resultcode");
            	//聚合返回的状态码
            	if("200".equals(resultcode)){
            		Map<String,Object> result = (Map<String, Object>) jSONObject.get("result");
                	String nowWeather =  ((JSONObject) result.get("sk")).toString();
                	String todayWeather =  ((JSONObject) result.get("today")).toString();
                	String futureWeather =  ((JSONArray) result.get("future")).toString();
        			whJhWeather.setCity(city);
        			whJhWeather.setNowWeather(nowWeather);
        			whJhWeather.setTodayWeather(todayWeather);
        			whJhWeather.setFutureWeather(futureWeather);
        			//把whJhWeather 插入到数据库当中
        			jhWeatherDao.save(whJhWeather);
        			//判断t_wh_jhcity当中是否存在    存在了 不用插入,直接更新last_query_time 为now  ，不存在就插入
        			WhJhCity whJhCity = jhCityDao.findWhJhCityByCity(city);
        			if(whJhCity == null){
        				WhJhCity whJhCity1 = new WhJhCity();
        				whJhCity1.setCity(city);
        				whJhCity1.setLastQueryTime(new Date());
        				jhCityDao.save(whJhCity1);
        			}else{
        				whJhCity.setLastQueryTime(new Date());
        				jhCityDao.update(whJhCity);
        			}
            	}
            }
        }
        httpResponse.close();
    	httpClient.close();
    	return whJhWeather;
	}
	
	@Override
	public WhJhWeather getWeatherByLngLat(String lng, String lat)
			throws SystemException, ClientProtocolException, IOException {
		String  city = getCityByLngLat(lng,lat);
		if( city != null && !"".equals(city)){
			if(city.contains("市")){
				city = city.substring(0, city.length()-1);
			}else if(city.contains("地区")){
				city = city.substring(0, city.length()-2);
			}
			System.err.println("city "+city);
			WhJhWeather whJhWeather = getWeatherByCity(city);
			return whJhWeather;
		}else{
			return null;
		}
	}
	
	/**
	 * 根据经纬度获取城市
	 * @param lng 经度
	 * @param lat 纬度
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public String getCityByLngLat(String lng,String lat) throws ClientProtocolException, IOException{
		String url =systemConfig.getBaiduMapUrl()+"?output=json&location="+lat+","+lng+"&pois=0&ak="+systemConfig.getBaiduAk();
		HttpEntity entity = httpGet(url);
		if (null != entity){
			String entityStr = EntityUtils.toString(entity);
			JSONObject  json = JSONObject.fromObject(entityStr);
			String status = json.getString("status");
			if( "0".equals(status) ){
				String result = json.getString("result");
				JSONObject resultJson = JSONObject.fromObject(result);
				JSONObject addressJson = resultJson.getJSONObject("addressComponent");
				String city = addressJson.getString("city");
				return city;
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	/**
	 * 发送get请求
	 * @param url
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
    public HttpEntity httpGet(String url) throws ClientProtocolException, IOException{
    	CloseableHttpClient closeableHttpClient =  HttpClients.createDefault();
    	HttpGet get = new HttpGet(url);
    	CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(get);
    	int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
    	if( statusCode == 200){
        	HttpEntity entity = closeableHttpResponse.getEntity();
        	closeableHttpResponse.close();
        	closeableHttpClient.close();
    		return entity;
    	}else{
    		closeableHttpResponse.close();
        	closeableHttpClient.close();
    		return null;
    	}
    }
    
    
	public static void main (String args[]) throws ClientProtocolException, SystemException, IOException{
		/*ApplicationContext cap = new ClassPathXmlApplicationContext("applicationContext.xml");
		JhWeatherServiceImpl jhWeatherService = cap.getBean(JhWeatherServiceImpl.class);*/
	/*	WhJhWeather whJhWeather = jhWeatherService.getWeatherByCity("深圳");
		System.err.println("whJhWeather "+whJhWeather.getFutureWeather());
		
		JhWeatherServiceImpl jhWeatherServiceImpl = new JhWeatherServiceImpl();*/
		//WhJhWeather whJhWeather = jhWeatherService.getWeatherByLngLat("108.324340","22.800766");
		//String city = jhWeatherService.getCityByLngLat("115.508391","35.684760");
		//System.err.println("whJhWeather "+whJhWeather.getFutureWeather());
	}
}

