package cc.chinagps.seat.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.googlecode.jsonplugin.JSONException;
import com.googlecode.jsonplugin.JSONUtil;

public class JsonUtil {
	//返回操作是否成功消息
	public static void responseSuccess(HttpServletResponse response, boolean isSuccess) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", isSuccess);
		responseJsonObject(response, map);
	}
	
	//返回操作是否成功消息
	public static void responseSuccess(HttpServletResponse response, boolean isSuccess, String reason) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", isSuccess);
		map.put("msg", reason);
		responseJsonObject(response, map);
	}
	
	//返回操作是否成功消息
	public static void responseSuccess(HttpServletResponse response, boolean isSuccess, int reason) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", isSuccess);
		map.put("reason", reason);
		responseJsonObject(response, map);
	}
	
	//返回操作是否成功消息
	public static void responseSuccess(HttpServletResponse response, boolean isSuccess, int reason, double param) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", isSuccess);
		map.put("reason", reason);
		map.put("param", param);
		responseJsonObject(response, map);
	}
	
	//返回操作是否成功消息
	public static void responseSuccess(HttpServletResponse response, boolean isSuccess, double msg) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", isSuccess);
		map.put("msg", msg);
		responseJsonObject(response, map);
	}
	
	public static void responseJsonObject(HttpServletResponse response, Object obj) throws IOException{ 
		String json;
		PrintWriter out = null;
		try {
			json = JSONUtil.serialize(obj);
			//System.out.println("json:" + json);
			out = response.getWriter();
			out.print(json);
			out.flush();		
		} catch (JSONException e) {
			e.printStackTrace();
		}finally{
			if(out != null){
				out.close();
			}
		}
	}
}