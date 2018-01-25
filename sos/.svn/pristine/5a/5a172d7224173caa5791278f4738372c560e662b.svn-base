package com.gboss.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gboss.comm.SystemConfig;
import com.gboss.comm.SystemException;
import com.gboss.pojo.WhJhWeather;
import com.gboss.pojo.weather.json.CityWeatherResponseJson;
import com.gboss.pojo.weather.json.IndexJson;
import com.gboss.pojo.weather.json.ResultJson;
import com.gboss.pojo.weather.json.WeatherDataJson;
import com.gboss.pojo.weather.xml.CityWeatherResponseXml;
import com.gboss.pojo.weather.xml.IndexXml;
import com.gboss.pojo.weather.xml.ResultXml;
import com.gboss.pojo.weather.xml.WeatherDataXml;
import com.gboss.service.JhWeatherService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @Package:com.gboss.controller
 * @ClassName:WeatgherController
 * @Description:TODO
 * @author:yusongya
 * @date:2017-6-13 下午3:39:01
 */
@Controller
public class WeatherController extends BaseController {
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(WeatherController.class);
	
	@Autowired
	private JhWeatherService jhWeatherService;
	
	@Autowired
	private SystemConfig  systemConfig;
	
	@RequestMapping(value = "/v3/weather",method = RequestMethod.GET)
	public void findWeather(
			HttpServletRequest request,HttpServletResponse response) throws SystemException, IOException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("根据经纬度查询天气");
		}
		String pathPic = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/weather/";
		System.err.println("path "+pathPic);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String location = request.getParameter("location");
		String ak = request.getParameter("ak");
		String output = request.getParameter("output");
		//http://api.map.baidu.com/telematics/v3/weather?location=116.43,40.75&output=json&ak=rKImGPUE3GQ2s5RkmgxOtFic
		
		PrintWriter out = response.getWriter();
		//返回xml
		if("xml".equalsIgnoreCase(output)){
			response.setContentType("application/xml;charset=utf-8");
			//先判断ak是否正确  
			if(systemConfig.getAk().equals(ak)){
				//正确继续
				if("".equalsIgnoreCase(location)  || location == null){  //是否输入了地址
					/**
					 * <CityWeatherResponse>
						<error>-2</error>
						<status>Missing required parameter</status>
						<date>2017-06-16</date>
						<results></results>
						</CityWeatherResponse>
					 */
					String resStr = "<CityWeatherResponse><error>-2</error><status>Missing required parameter</status><date>"+sdf.format(new Date())+"</date><results></results></CityWeatherResponse>";
					out.write(resStr);
					return;
				}else{
					//输入的地址不为空时 判断输入的是地址还是经纬度   城,市
					if(location.contains(",")){
						//输入的是经纬度
						String longitude = location.split(",")[0]; // 经度
						String latitude  = location.split(",")[1];//纬度
						System.err.println("longitude "+longitude +"  latitude "+latitude);
						WhJhWeather whJhWeather = jhWeatherService.getWeatherByLngLat(longitude, latitude);
						//whJhWeather 转换成xml
						String resStr = objectToXml(whJhWeather,pathPic);
						out.write(resStr);
					}else{
						//输入的是城市
						System.err.println("city "+location);
						WhJhWeather whJhWeather = jhWeatherService.getWeatherByCity(location);
						//whJhWeather 转换成xml
						String resStr = objectToXml(whJhWeather,pathPic);
						if( !"".equals(resStr) && resStr != null ){
							resStr = resStr.replace("weather__data", "weather_data");
						}
						out.write(resStr);
					}
				}
			}else{
				//ak 不正确
				/**
				 * <Response>
					<status>200</status>
					<message>APP不存在，AK有误请检查再重试</message>
					</Response>
				 */
				String resStr = "<Response><status>200</status><message>APP不存在，AK有误请检查再重试</message></Response>";
				out.write(resStr);
				return;
			}
		}else{
			response.setContentType("application/json;charset=utf-8");

			if(systemConfig.getAk().equals(ak)){
				//正确继续
				if( "".equalsIgnoreCase(location) || location == null ){  //是否输入了地址
					/**
					 * {"error":-2,"status":"Missing required parameter","date":"2017-06-16"}
					 */
					String resStr = "{'error':-2,'status':'Missing required parameter','date':"+sdf.format(new Date())+"}";
					out.write(resStr);
					return;
				}else{
					//输入的地址不为空时 判断输入的是地址还是经纬度   城,市
					if(location.contains(",")){
						//输入的是经纬度
						String longitude = location.split(",")[0]; // 经度
						String latitude  = location.split(",")[1];//纬度
						System.err.println("longitude "+longitude +"  latitude "+latitude);
						WhJhWeather whJhWeather = jhWeatherService.getWeatherByLngLat(longitude, latitude);
						//whJhWeather 转换成xml
						String resStr = objectToJson(whJhWeather,pathPic);
						out.write(resStr);
					}else{
						//输入的是城市
						System.err.println("city "+location);
						WhJhWeather whJhWeather = jhWeatherService.getWeatherByCity(location);
						//whJhWeather 转换成xml
						String resStr = objectToJson(whJhWeather,pathPic);
						out.write(resStr);
					}
				}
			}else{
				//ak 不正确
				String resStr = "{'status':200,'message':'APP不存在，AK有误请检查再重试'}";
				out.write(resStr);
				return;
			}
		}
	}
	
	public String objectToXml(WhJhWeather whJhWeather,String rootPath){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(whJhWeather == null ){
			System.err.println("<CityWeatherResponse><error>-3</error><status>No result available</status><date>"+sdf.format(new Date())+"</date><results></results></CityWeatherResponse");
			return "<CityWeatherResponse><error>-3</error><status>No result available</status><date>"+sdf.format(new Date())+"</date><results></results></CityWeatherResponse>";
		}
		String nowWeather = whJhWeather.getNowWeather();
		String todayWeather = whJhWeather.getTodayWeather();
		String futureWeather = whJhWeather.getFutureWeather();
		//{"temp":"34","wind_direction":"东南风","wind_strength":"3级","humidity":"59%","time":"14:52"}
		JSONObject nowWeatherJson = JSONObject.fromObject(nowWeather);
		JSONObject todayWeatherJson = JSONObject.fromObject(todayWeather);
		JSONArray futureWeatherJson = JSONArray.fromObject(futureWeather);
		
		CityWeatherResponseXml cityWeatherResponse = new CityWeatherResponseXml();
		IndexXml index = new IndexXml();
		index.setTitle1("穿衣");
		index.setZs1(todayWeatherJson.getString("dressing_index"));
		index.setTipt1("穿衣指数");
		index.setDes1(todayWeatherJson.getString("dressing_advice"));
		
		index.setTitle2("洗车");
		index.setZs2(todayWeatherJson.getString("wash_index"));
		index.setTipt2("洗车指数");
		index.setDes2("");

		index.setTitle3("晨练");
		index.setZs3(todayWeatherJson.getString("exercise_index"));
		index.setTipt3("晨练指数");
		index.setDes3("");

		index.setTitle4("干燥");
		index.setZs4(todayWeatherJson.getString("drying_index"));
		index.setTipt4("干燥指数");
		index.setDes4("");

		index.setTitle5("紫外线");
		index.setZs5(todayWeatherJson.getString("uv_index"));
		index.setTipt5("紫外线指数");
		index.setDes5("");
		
		WeatherDataXml weatherDataXml = new WeatherDataXml();
		
		Map<String,Object> map1 = (Map<String, Object>) futureWeatherJson.get(0);
		weatherDataXml.setDate1(todayWeatherJson.getString("date_y")+" "+todayWeatherJson.getString("week"));
		Map<String,Object> weatherIdMap1 = (Map<String, Object>) map1.get("weather_id"); // 拼接天气图片
		weatherDataXml.setDayPictureUrl1( getPictureUrl(rootPath,(String)weatherIdMap1.get("fa"),1));
		weatherDataXml.setNightPictureUrl1(getPictureUrl(rootPath,(String)weatherIdMap1.get("fb"),0));
		weatherDataXml.setWeather1((String)map1.get("weather"));
		weatherDataXml.setWind1((String)map1.get("wind"));
		weatherDataXml.setTemperature1((String)map1.get("temperature"));
		
		Map<String,Object> map2 = (Map<String, Object>) futureWeatherJson.get(1);
		weatherDataXml.setDate2((String)map2.get("week"));
		Map<String,Object> weatherIdMap2 = (Map<String, Object>) map2.get("weather_id"); // 拼接天气图片
		weatherDataXml.setDayPictureUrl2(getPictureUrl(rootPath,(String)weatherIdMap2.get("fa"),1));
		weatherDataXml.setNightPictureUrl2(getPictureUrl(rootPath,(String)weatherIdMap2.get("fb"),0));
		weatherDataXml.setWeather2((String)map2.get("weather"));
		weatherDataXml.setWind2((String)map2.get("wind"));
		weatherDataXml.setTemperature2((String)map2.get("temperature"));
		
		Map<String,Object> map3 = (Map<String, Object>) futureWeatherJson.get(2);
		weatherDataXml.setDate3((String)map3.get("week"));
		Map<String,Object> weatherIdMap3 = (Map<String, Object>) map3.get("weather_id"); // 拼接天气图片
		weatherDataXml.setDayPictureUrl3(getPictureUrl(rootPath,(String)weatherIdMap3.get("fa"),1));
		weatherDataXml.setNightPictureUrl3(getPictureUrl(rootPath,(String)weatherIdMap3.get("fb"),0));
		weatherDataXml.setWeather3((String)map3.get("weather"));
		weatherDataXml.setWind3((String)map3.get("wind"));
		weatherDataXml.setTemperature3((String)map3.get("temperature"));

		Map<String,Object> map4 = (Map<String, Object>) futureWeatherJson.get(3);
		weatherDataXml.setDate4((String)map4.get("week"));
		Map<String,Object> weatherIdMap4 = (Map<String, Object>) map4.get("weather_id"); // 拼接天气图片
		weatherDataXml.setDayPictureUrl4(getPictureUrl(rootPath,(String)weatherIdMap4.get("fa"),1));
		weatherDataXml.setNightPictureUrl4(getPictureUrl(rootPath,(String)weatherIdMap4.get("fb"),0));
		weatherDataXml.setWeather4((String)map4.get("weather"));
		weatherDataXml.setWind4((String)map4.get("wind"));
		weatherDataXml.setTemperature4((String)map4.get("temperature"));

		Map<String,Object> map5 = (Map<String, Object>) futureWeatherJson.get(4);
		weatherDataXml.setDate5((String)map5.get("week"));
		Map<String,Object> weatherIdMap5 = (Map<String, Object>) map5.get("weather_id"); // 拼接天气图片
		weatherDataXml.setDayPictureUrl5(getPictureUrl(rootPath,(String)weatherIdMap5.get("fa"),1));
		weatherDataXml.setNightPictureUrl5(getPictureUrl(rootPath,(String)weatherIdMap5.get("fb"),0));
		weatherDataXml.setWeather5((String)map5.get("weather"));
		weatherDataXml.setWind5((String)map5.get("wind"));
		weatherDataXml.setTemperature5((String)map5.get("temperature"));
		
		Map<String,Object> map6 = (Map<String, Object>) futureWeatherJson.get(5);
		weatherDataXml.setDate6((String)map6.get("week"));
		Map<String,Object> weatherIdMap6 = (Map<String, Object>) map6.get("weather_id"); // 拼接天气图片
		weatherDataXml.setDayPictureUrl6(getPictureUrl(rootPath,(String)weatherIdMap6.get("fa"),1));
		weatherDataXml.setNightPictureUrl6(getPictureUrl(rootPath,(String)weatherIdMap6.get("fb"),0));
		weatherDataXml.setWeather6((String)map6.get("weather"));
		weatherDataXml.setWind6((String)map6.get("wind"));
		weatherDataXml.setTemperature6((String)map6.get("temperature"));
		
		Map<String,Object> map7 = (Map<String, Object>) futureWeatherJson.get(6);
		weatherDataXml.setDate7((String)map7.get("week"));
		Map<String,Object> weatherIdMap7 = (Map<String, Object>) map7.get("weather_id"); // 拼接天气图片
		weatherDataXml.setDayPictureUrl7(getPictureUrl(rootPath,(String)weatherIdMap7.get("fa"),1));
		weatherDataXml.setNightPictureUrl7(getPictureUrl(rootPath,(String)weatherIdMap7.get("fb"),0));
		weatherDataXml.setWeather7((String)map7.get("weather"));
		weatherDataXml.setWind7((String)map7.get("wind"));
		weatherDataXml.setTemperature7((String)map7.get("temperature"));
		
		ResultXml resultXml = new ResultXml();
		resultXml.setCurrentCity(todayWeatherJson.getString("city"));
		resultXml.setIndex(index);
		resultXml.setPm25("");
		resultXml.setWeather_data(weatherDataXml);
		
		
		cityWeatherResponse.setDate(todayWeatherJson.getString("date_y"));
		cityWeatherResponse.setError("0");
		cityWeatherResponse.setResults(resultXml);
		cityWeatherResponse.setStatus("success"); 
		
		XStream xstream  =new XStream(new DomDriver()); 
		xstream.processAnnotations(new Class[]{CityWeatherResponseXml.class,IndexXml.class,WeatherDataXml.class});
		String str = xstream.toXML(cityWeatherResponse);
		System.err.println("xml "+str);
		return str;
	}
	
	public static void main(String args[]) throws ParseException{
		
		WeatherController weatherController = new WeatherController();
		WhJhWeather  whJhWeather = new WhJhWeather();
		whJhWeather.setCity("南宁");
		whJhWeather.setFutureWeather("[{'temperature':'26℃~35℃','weather':'阵雨','weather_id':{'fa':'03','fb':'03'},'wind':'东南风微风','week':'星期一','date':'20170612'},{'temperature':'26℃~34℃','weather':'阵雨','weather_id':{'fa':'03','fb':'03'},'wind':'南风微风','week':'星期二','date':'20170613'},{'temperature':'25℃~32℃','weather':'阵雨','weather_id':{'fa':'03','fb':'03'},'wind':'南风微风','week':'星期三','date':'20170614'},{'temperature':'25℃~32℃','weather':'阵雨转中雨','weather_id':{'fa':'03','fb':'08'},'wind':'南风微风','week':'星期四','date':'20170615'},{'temperature':'24℃~30℃','weather':'中雨转阵雨','weather_id':{'fa':'08','fb':'03'},'wind':'东北风微风','week':'星期五','date':'20170616'},{'temperature':'26℃~34℃','weather':'阵雨','weather_id':{'fa':'03','fb':'03'},'wind':'南风微风','week':'星期六','date':'20170617'},{'temperature':'26℃~34℃','weather':'阵雨','weather_id':{'fa':'03','fb':'03'},'wind':'南风微风','week':'星期日','date':'20170618'}]");
		whJhWeather.setTodayWeather("{\"temperature\":\"26℃~35℃\",\"weather\":\"阵雨\",\"weather_id\":{\"fa\":\"03\",\"fb\":\"03\"},\"wind\":\"东南风微风\",\"week\":\"星期一\",\"city\":\"南宁\",\"date_y\":\"2017年06月12日\",\"dressing_index\":\"炎热\",\"dressing_advice\":\"天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。\",\"uv_index\":\"中等\",\"comfort_index\":\"\",\"wash_index\":\"不宜\",\"travel_index\":\"较不宜\",\"exercise_index\":\"较不宜\",\"drying_index\":\"\"}");
		whJhWeather.setNowWeather("{\"temp\":\"34\",\"wind_direction\":\"东南风\",\"wind_strength\":\"\3级\",\"humidity\":\"59%\",\"time\":\"14:52\"}");
		//weatherController.objectToXml(null);
		//weatherController.objectToJson(whJhWeather);
		System.err.println("date "+String.format("大家好，我叫：%s", "ysy"));
	} 
	
	public String objectToJson(WhJhWeather whJhWeather,String rootPath){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(whJhWeather == null ){
			System.err.println("{\"error\":-3,\"status\":\"No result available\",\"date\":"+sdf.format(new Date())+"}");
			return "{\"error\":-3,\"status\":\"No result available\",\"date\":"+sdf.format(new Date())+"}";
		}
		
		String nowWeather = whJhWeather.getNowWeather();
		String todayWeather = whJhWeather.getTodayWeather();
		String futureWeather = whJhWeather.getFutureWeather();
		JSONObject nowWeatherJson = JSONObject.fromObject(nowWeather);
		JSONObject todayWeatherJson = JSONObject.fromObject(todayWeather);
		JSONArray futureWeatherJson = JSONArray.fromObject(futureWeather);
		
		CityWeatherResponseJson cityWeatherResponse = new CityWeatherResponseJson();
		
		IndexJson index1 = new IndexJson();
		index1.setTitle("穿衣");
		index1.setZs(todayWeatherJson.getString("dressing_index"));
		index1.setTipt("穿衣指数");
		index1.setDes(todayWeatherJson.getString("dressing_advice"));
		
		IndexJson index2 = new IndexJson();
		index2.setTitle("洗车");
		index2.setZs(todayWeatherJson.getString("wash_index"));
		index2.setTipt("洗车指数");
		index2.setDes("");
		
		IndexJson index3 = new IndexJson();
		index3.setTitle("晨练");
		index3.setZs(todayWeatherJson.getString("exercise_index"));
		index3.setTipt("晨练指数");
		index3.setDes("");
		
		IndexJson index4 = new IndexJson();
		index4.setTitle("干燥");
		index4.setZs(todayWeatherJson.getString("drying_index"));
		index4.setTipt("干燥指数");
		index4.setDes("");
		
		
		IndexJson index5 = new IndexJson();
		index5.setTitle("紫外线");
		index5.setZs(todayWeatherJson.getString("uv_index"));
		index5.setTipt("紫外线指数");
		index5.setDes("");
		
		WeatherDataJson weather_data1 = new WeatherDataJson();
		
		Map<String,Object> map1 = (Map<String, Object>) futureWeatherJson.get(0);
		weather_data1.setDate(todayWeatherJson.getString("date_y")+" "+todayWeatherJson.getString("week"));
		Map<String,Object> weatherIdMap1 = (Map<String, Object>) map1.get("weather_id"); // 拼接天气图片
		weather_data1.setDayPictureUrl(getPictureUrl(rootPath,(String)weatherIdMap1.get("fa"),1));
		weather_data1.setNightPictureUrl(getPictureUrl(rootPath,(String)weatherIdMap1.get("fb"),0));
		weather_data1.setWind((String)map1.get("wind"));
		weather_data1.setWeather((String)map1.get("weather"));
		weather_data1.setTemperature((String)map1.get("temperature"));
		
		WeatherDataJson weather_data2 = new WeatherDataJson();
		Map<String,Object> map2 = (Map<String, Object>) futureWeatherJson.get(1);
		weather_data2.setDate((String)map2.get("week"));
		Map<String,Object> weatherIdMap2 = (Map<String, Object>) map2.get("weather_id"); // 拼接天气图片
		weather_data2.setDayPictureUrl(getPictureUrl(rootPath,(String)weatherIdMap2.get("fa"),1));
		weather_data2.setNightPictureUrl(getPictureUrl(rootPath,(String)weatherIdMap2.get("fb"),0));
		weather_data2.setWind((String)map2.get("wind"));
		weather_data2.setWeather((String)map2.get("weather"));
		weather_data2.setTemperature((String)map2.get("temperature"));
		
		WeatherDataJson weather_data3 = new WeatherDataJson();
		Map<String,Object> map3 = (Map<String, Object>) futureWeatherJson.get(2);
		weather_data3.setDate((String)map3.get("week"));
		Map<String,Object> weatherIdMap3 = (Map<String, Object>) map3.get("weather_id"); // 拼接天气图片
		weather_data3.setDayPictureUrl(getPictureUrl(rootPath,(String)weatherIdMap3.get("fa"),1));
		weather_data3.setNightPictureUrl(getPictureUrl(rootPath,(String)weatherIdMap3.get("fb"),0));
		weather_data3.setWind((String)map3.get("wind"));
		weather_data3.setWeather((String)map3.get("weather"));
		weather_data3.setTemperature((String)map3.get("temperature"));
		
		WeatherDataJson weather_data4 = new WeatherDataJson();
		Map<String,Object> map4 = (Map<String, Object>) futureWeatherJson.get(3);
		weather_data4.setDate((String)map4.get("week"));
		Map<String,Object> weatherIdMap4 = (Map<String, Object>) map4.get("weather_id"); // 拼接天气图片
		weather_data4.setDayPictureUrl(getPictureUrl(rootPath,(String)weatherIdMap4.get("fa"),1));
		weather_data4.setNightPictureUrl(getPictureUrl(rootPath,(String)weatherIdMap4.get("fb"),0));
		weather_data4.setWind((String)map4.get("wind"));
		weather_data4.setWeather((String)map4.get("weather"));
		weather_data4.setTemperature((String)map4.get("temperature"));
		
		WeatherDataJson weather_data5 = new WeatherDataJson();
		Map<String,Object> map5 = (Map<String, Object>) futureWeatherJson.get(4);
		weather_data5.setDate((String)map5.get("week"));
		Map<String,Object> weatherIdMap5 = (Map<String, Object>) map5.get("weather_id"); // 拼接天气图片
		weather_data5.setDayPictureUrl(getPictureUrl(rootPath,(String)weatherIdMap5.get("fa"),1));
		weather_data5.setNightPictureUrl(getPictureUrl(rootPath,(String)weatherIdMap5.get("fb"),0));
		weather_data5.setWind((String)map5.get("wind"));
		weather_data5.setWeather((String)map5.get("weather"));
		weather_data5.setTemperature((String)map5.get("temperature"));
		
		WeatherDataJson weather_data6 = new WeatherDataJson();
		Map<String,Object> map6 = (Map<String, Object>) futureWeatherJson.get(5);
		weather_data6.setDate((String)map6.get("week"));
		Map<String,Object> weatherIdMap6 = (Map<String, Object>) map6.get("weather_id"); // 拼接天气图片
		weather_data6.setDayPictureUrl(getPictureUrl(rootPath,(String)weatherIdMap6.get("fa"),1));
		weather_data6.setNightPictureUrl(getPictureUrl(rootPath,(String)weatherIdMap6.get("fb"),0));
		weather_data6.setWind((String)map6.get("wind"));
		weather_data6.setWeather((String)map6.get("weather"));
		weather_data6.setTemperature((String)map6.get("temperature"));
		
		WeatherDataJson weather_data7 = new WeatherDataJson();
		Map<String,Object> map7 = (Map<String, Object>) futureWeatherJson.get(6);
		weather_data7.setDate((String)map7.get("week"));
		Map<String,Object> weatherIdMap7 = (Map<String, Object>) map7.get("weather_id"); // 拼接天气图片
		weather_data7.setDayPictureUrl(getPictureUrl(rootPath,(String)weatherIdMap7.get("fa"),1));
		weather_data7.setNightPictureUrl(getPictureUrl(rootPath,(String)weatherIdMap7.get("fb"),0));
		weather_data7.setWind((String)map7.get("wind"));
		weather_data7.setWeather((String)map7.get("weather"));
		weather_data7.setTemperature((String)map7.get("temperature"));
		
		List<WeatherDataJson> list = new ArrayList<WeatherDataJson>();
		list.add(weather_data1);
		list.add(weather_data2);
		list.add(weather_data3);
		list.add(weather_data4);
		list.add(weather_data5);
		list.add(weather_data6);
		list.add(weather_data7);
		
		List<IndexJson> listIndex = new ArrayList<IndexJson>();
		listIndex.add(index1);
		listIndex.add(index2);
		listIndex.add(index3);
		listIndex.add(index4);
		listIndex.add(index5);
		
		ResultJson result = new ResultJson ();
		result.setCurrentCity(whJhWeather.getCity());
		result.setPm25("");
		result.setWeather_data(list);
		result.setIndex(listIndex);
		List<ResultJson> resultList = new ArrayList<ResultJson>();
		resultList.add(result);
		
		cityWeatherResponse.setError("0");
		cityWeatherResponse.setStatus("success");
		cityWeatherResponse.setResults(resultList);
		cityWeatherResponse.setDate(sdf.format(new Date()));
		
		XStream xstream  =new XStream(new JsonHierarchicalStreamDriver()); 
		xstream.processAnnotations(CityWeatherResponseJson.class);
		String str = xstream.toXML(cityWeatherResponse);
		System.err.println(str);
		return str;
	}
	
	public String getPictureUrl(String rootPath,String wid,int isDay){
		//白天
		StringBuilder picUrl = new StringBuilder(rootPath);
		if(isDay == 1){
			picUrl.append("day/");
		}else{
			picUrl.append("night/");
		}
		if(wid.trim().equals("00")){
			picUrl.append("qing.png");
		}else if(wid.trim().equals("01")){
			picUrl.append("duoyun.png");
		}else if(wid.trim().equals("02")){
			picUrl.append("yin.png");
		}else if(wid.trim().equals("03")){
			picUrl.append("zhenyu.png");
		}else if(wid.trim().equals("04")){
			picUrl.append("leizhenyu.png");
		}else if(wid.trim().equals("05")){
			picUrl.append("leizhenyubanyoubingbao.png");
		}else if(wid.trim().equals("06")){
			picUrl.append("yujiaxue.png");
		}else if(wid.trim().equals("07")){
			picUrl.append("xiaoyu.png");
		}else if(wid.trim().equals("08")){
			picUrl.append("zhongyu.png");
		}else if(wid.trim().equals("09")){
			picUrl.append("dayu.png");
		}else if(wid.trim().equals("10")){
			picUrl.append("baoyu.png");
		}else if(wid.trim().equals("11")){
			picUrl.append("dabaoyu.png");
		}else if(wid.trim().equals("12")){
			picUrl.append("tedabaoyu.png");
		}else if(wid.trim().equals("13")){
			picUrl.append("zhenxue.png");
		}else if(wid.trim().equals("14")){
			picUrl.append("xiaoxue.png");
		}else if(wid.trim().equals("15")){
			picUrl.append("zhongxue.png");
		}else if(wid.trim().equals("16")){
			picUrl.append("daxue.png");
		}else if(wid.trim().equals("17")){
			picUrl.append("baoxue.png");
		}else if(wid.trim().equals("18")){
			picUrl.append("wu.png");
		}else if(wid.trim().equals("19")){
			picUrl.append("dongyu.png");
		}else if(wid.trim().equals("20")){
			picUrl.append("shachenbao.png");
		}else if(wid.trim().equals("21")){
			picUrl.append("xiaoyuzhuanzhongyu.png");
		}else if(wid.trim().equals("22")){
			picUrl.append("zhongyuzhuandayu.png");
		}else if(wid.trim().equals("23")){
			picUrl.append("dayuzhuanbaoyu.png");
		}else if(wid.trim().equals("24")){
			picUrl.append("baoyuzhuandabaoyu.png");
		}else if(wid.trim().equals("25")){
			picUrl.append("dabaoyuzhuantedabaoyu.png");
		}else if(wid.trim().equals("26")){
			picUrl.append("xiaoxuezhuanzhongxue.png");
		}else if(wid.trim().equals("27")){
			picUrl.append("zhongxuezhuandaxue.png");
		}else if(wid.trim().equals("28")){
			picUrl.append("daxuezhuanbaoxue.png");
		}else if(wid.trim().equals("29")){
			picUrl.append("fuchen.png");
		}else if(wid.trim().equals("30")){
			picUrl.append("yangsha.png");
		}else if(wid.trim().equals("31")){
			picUrl.append("qiangshachenbao.png");
		}else if(wid.trim().equals("53")){
			picUrl.append("mai.png");
		}
		
		return picUrl.toString();
	}
}

