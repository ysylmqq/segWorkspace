package org.com.springcloud;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpClientTest {
	
	public static String sendGet(String url, String param) {
		 //httpClient
	    HttpClient httpClient = new DefaultHttpClient();

	    // get method
	    HttpGet httpGet = new HttpGet(url);
	    
	    // set header
	    String Au="Bearer ";
	    httpGet.setHeader("Authorization",Au);  
	  
	    //response
	    HttpResponse response = null;  
	    try{
	        response = httpClient.execute(httpGet);
	    }catch (Exception e) {} 

	    //get response into String
	    String temp="";
	    try{
	        HttpEntity entity = response.getEntity();
	        temp=EntityUtils.toString(entity,"UTF-8");
	    }catch (Exception e) {} 
	    System.err.println("temp "+temp);
	    return temp;
    }
	
	public static void main(String[] args) {
		
		
		for (int i = 0; i < 500; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < 10000; i++) {
						HttpClientTest.sendGet("http://localhost:9091/getUserById/"+i, "");
					}
				}
			}).start();
		}
		
	}
}
