package com.gboss.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 * 参数处理
 */
public class ParamsHelper {
	//获取参数
	public static Map<String,Object> getParams(String url){
		if(url==null || "".equals(url)){
			System.out.println("url地址为空!");
			return null;
		}
		String tem_url = url;
		Map<String,Object> ret_map = new HashMap<String,Object>();
		url = tem_url.split("[?]")[0];
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		for(String temp : tem_url.split("[?]")[1].split("[&]")){
			if(temp!=null && !"".equals(temp)){
				if(!temp.equals("1=1")){
					String[] kv = temp.split("=");
					if(kv.length==2){
						nameValuePairs.add(new BasicNameValuePair(kv[0],kv[1]));
					}
				}
			}
		}
		ret_map.put("nameValuePairs", nameValuePairs);
		ret_map.put("url", url);
		System.err.println("从海马那边获取数据");
		System.out.println(DateUtil.formatNowTime() + " 请求参数:"+ret_map);
		return ret_map;
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(ParamsHelper.getParams("http://www.baidu.com?1=1&start_date=120545450121&end_data=135547855&vin=dfdfdf245&plant_no=苏H123439"));
	}
}
