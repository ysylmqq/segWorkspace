package cc.chinagps.seat.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public abstract class HttpUtil {
	
	
	public static String smsRequest(Map<String,String> params){
		CloseableHttpClient httpclient = HttpClientBuilder.create().build(); 
		
		//请求参数
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		if(params!=null&& params.size() > 0 ){
			Set<String> keys = params.keySet();
			Iterator<String> it =  keys.iterator();
			while(it.hasNext()){
				String key = it.next();
				String val = (params.get(key)==null ? "" : params.get(key));
				nameValuePairs.add(new BasicNameValuePair(key,val));
			}
		}
		
		HttpPost httppost = new HttpPost((String) params.get(Consts.SMS_API_URL));
		
		UrlEncodedFormEntity uefEntity = null;
		CloseableHttpResponse response = null;
		try {
			uefEntity = new UrlEncodedFormEntity(nameValuePairs,"UTF-8");
			httppost.setEntity(uefEntity);
			response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity, "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println("#############UnsupportedEncodingException#############");
			e.printStackTrace();
			return null;
		} catch (ParseException e) {
			System.out.println("#############ParseException#############");
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			System.out.println("#############IOException#############");
//			e.printStackTrace();
			return null;
		} finally {
			try {
				if(response!=null){
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		params.put(Consts.SMS_API_URL, "http://120.24.241.49/statusApi.aspx");//状态
		Map<String,String> request_params = new HashMap<String, String>();
		request_params.put(Consts.SMS_API_URL, "http://localhost:8088/sms/send/");//发送
		request_params.put("mobile", "13142095086");
		request_params.put("content", "测试内容");
		System.out.println(smsRequest(request_params));

	}

}
