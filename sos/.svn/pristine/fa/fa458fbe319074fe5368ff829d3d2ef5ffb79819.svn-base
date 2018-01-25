package com.gboss.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import ldap.objectClasses.CommonOperator;
import ldap.objectClasses.CommonSystem;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

public class LdapTest {
	
	public static void main(String[] args) {
		OpenLdap ldap = OpenLdapManager.getInstance();
		String loginname = "深圳市展鸿达投资管理有限公司";
		String plate_no = "不可能出现的车牌号码";
		String userFilter = "(&(objectclass=CommonSystem))";
		
		List<CommonSystem> listUser = ldap.searchSystem("", userFilter);
		if (listUser != null && listUser.size() > 0) {
			System.out.println("test");
		}
//		CommonOperator op = new CommonOperator();
//		OpenLdap ldap = OpenLdapManager.getInstance();
//		op.setUserPassword("981813");
//		op.setOpid("66597");
//		op.setCustomerid("52561");
//		op.setLoginname("粤BP865P");
//		op.setNumberplate("粤BP865P");
//		op.setOpname("潘素珍");
//		op.setStatus("11");
//		ldap.add(op);
	}
	/*
	public static void main(String[] args){
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost
		HttpPost httppost = new HttpPost("http://localhost:8080/ris/hm/updateservicepwd");
		String returnStr = "";
		try {
			JSONObject param = new JSONObject();
			param.put("vin", "123412351245");
			param.put("call_letter", "1234125");
			param.put("password", "123456");
			StringEntity uefEntity = new StringEntity(param.toString(), "UTF-8");
			httppost.setEntity(uefEntity);
			System.out.println("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					System.out.println("--------------------------------------");
					returnStr = EntityUtils.toString(entity, "UTF-8");
					System.out.println("Response content: " + returnStr);
					System.out.println("--------------------------------------");
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}*/
}
