package com.chinaGPS.gtmp.business.command;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import com.chinaGPS.gtmp.service.IMaintainService;
import com.chinaGPS.gtmp.util.PropertyUtil;
import com.chinaGPS.gtmp.util.SpringContext;
import com.tencent.xinge.XingeApp;

/**
 * 
*      
* 类名称：Push   
* 类描述：   推送用
* 创建人：dengyan  
* 创建时间：2016-2-18 下午2:40:06    
* 修改备注：   
* @version    
*
 */
public class Push {
	private static Properties property = PropertyUtil.getProperty("push.properties");
	private static Long accessId = Long.valueOf((String) property.get("accessId"));
	private static String secretKey = (String) property.get("secretKey");
	private static Long ios_accessId = Long.valueOf((String) property.get("ios_accessId"));
	private static String ios_secretKey = (String) property.get("ios_secretKey");
	

	/*@SuppressWarnings("deprecation")
	public static void pushMsg(Map<String, String> map,String type) throws Exception {
        HttpClient httpClient = new DefaultHttpClient();
        Properties property = PropertyUtil.getProperty("activemq.properties");
        String url = (String) property.get("PushURL");
		HttpPost method = new HttpPost(url+type);
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = mapper.writeValueAsString(map);
		StringEntity entity = new StringEntity(jsonStr, "utf-8");//解决中文乱码问题    
        entity.setContentEncoding("UTF-8");    
        entity.setContentType("application/json");    
        method.setEntity(entity);
        HttpResponse result = httpClient.execute(method);
        System.out.println(result.getEntity());
        String resData = EntityUtils.toString(result.getEntity());
        System.out.println("json请求服务端返回的内容：" + resData);
     
	}

	public static void pushMsg2All(String title,String content)throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("title", title);
		map.put("content", content);
		pushMsg(map, "pushMsg2All");
	}

	public static void pushMsg2Multiple(String title,String content,String user_ids)throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("title", title);
		map.put("content", content);
		map.put("user_ids", user_ids);
		pushMsg(map,"pushMsg2Multiple");
	}*/
	/**
	 * 推所有设备
	 * @param title
	 * @param content
	 */
	public static String pushMsg2All(String title, String content) {
		String result = "";
		org.json.JSONObject json = null;
		json = XingeApp.pushAllAndroid(accessId, secretKey, title, content);
		result =json+"###" + XingeApp.pushAllIos(ios_accessId, title, content, 1);
		System.out.println(result);
		return result;
	}
	/**
	 * 推特定设备
	 * @param title
	 * @param content
	 * @param user_ids
	 * @return
	 * @throws Exception
	 */
	public static String pushMsg2Multiple(String title,String content,ArrayList<String> user_ids)throws Exception {
		String result = "";
		org.json.JSONObject json = null;
		IMaintainService maintainService = (IMaintainService)SpringContext.getContext().getBean("maintainServiceImpl");
		List<HashMap<String, Object>> list = maintainService.getPushBind(user_ids);
		if(list !=null &&list.size()>0){			
			for(HashMap<String, Object> pb:list){
				if (pb.get("DEVICE_TYPE") == null) {
					result = "-100";
				    continue;
			    }
				if(pb.get("DEVICE_TYPE").equals(BigDecimal.ZERO)){	//Android
					json = XingeApp.pushTokenAndroid(accessId, secretKey, title, content, pb.get("TOKEN").toString());
				}else if(pb.get("DEVICE_TYPE").equals(BigDecimal.ONE)){	//IOS					
					json = XingeApp.pushTokenIos(ios_accessId, ios_secretKey, content, pb.get("TOKEN").toString(), 1);
				}
				result += json+"###";
			}
			
		}
		System.out.println(result);
	    return result;
		}
}
