package com.chinagps.center.utils;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * @Package:com.chinagps.center.utils
 * @ClassName:HomeControllerTest
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-5-21 下午4:22:44
 */
public class HomeControllerTest extends BaseTest {
	

	public static void jsonTest()throws Exception {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("user_id", "327");
		params.put("title", "asdf");
		params.put("content", "爱上看到富利卡！");
		jtestAPI("/pushMsg2Single", params);
	}
	
	public static void msgTest()throws Exception {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("title", "测试标题");	//标题--android需要
		params.put("content", "内容");	//内容--android需要
		Map<String, Object> custom = new HashMap<String, Object>();
		custom.put("key1", "v1");
		custom.put("key2", "v2");
		custom.put("title", "title,title");
		custom.put("lat", "25.1245445");
		custom.put("lon", "25.1245445");
		String jsonStr = JSONObject.fromObject(custom).toString();
		params.put("custom", jsonStr);
		params.put("alert", "卡萨丁卡飞了卡洛斯的福克斯卡萨丁卡飞了卡洛斯的福克斯卡萨丁卡飞了卡洛斯的福");
		params.put("user_ids", "7151");
		jtestAPI("/pushMessage2Device", params);
	}
	
	public static void main(String[] args) throws Exception {
		//paramsTest();
		msgTest();
	}
	
}

