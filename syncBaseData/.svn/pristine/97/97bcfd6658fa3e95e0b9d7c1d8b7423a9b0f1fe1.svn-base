package com.gboss.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gboss.comm.SystemConfig;
import com.gboss.comm.SystemConst;
//import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;

public abstract class DataFetcher {

	/**
	 * 获取原始请求地址
	 * 
	 * @param url
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public static String getUrl(String url, Long start_date, Long end_date) {
		if (start_date != null) {
			start_date = start_date / 1000;
			url = url.concat("&start_date=").concat(start_date.toString());
		}
		if (end_date != null) {
			end_date = end_date / 1000;
			url = url.concat("&end_date=").concat(end_date.toString());
		}
		return url;
	}

	@SuppressWarnings("unchecked")
	protected static List<HashMap<String, Object>> parseJSON2List(Object jsonStr) {
		JSONArray jsonArr = JSONArray.fromObject(jsonStr);
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		Iterator<JSONObject> it = jsonArr.iterator();
		while (it.hasNext()) {
			JSONObject json2 = it.next();
			list.add(parseJSON2Map(json2.toString()));
		}
		return list;
	}

	/**
	 * 解析返回数据
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static HashMap<String, Object> parseJSON2Map(String jsonStr) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		// 最外层解析
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			// 如果内层还是数组的话，继续解析
			if (v instanceof JSONArray) {
				List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
				@SuppressWarnings("unchecked")
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(parseJSON2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}

	/**
	 * 就算是{'a:a{dsa}':"{fdasf[dd]}"} 这样的也可以处理
	 * 当然{a:{b:{c:{d:{e:[1,2,3,4,5,6]}}}}}更可以处理
	 * 
	 * @param jsonstring
	 *            合法格式的json字符串
	 * @return 有可能map有可能是list
	 */
	@SuppressWarnings("unchecked")
	public static Object json2Map2(String jsonstring) {
		SystemConst.D_LOG.debug(">>>>>> json2Map2-start");
		Stack<Map> maps = new Stack<Map>(); // 用来表示多层的json对象
		Stack<List> lists = new Stack<List>(); // 用来表示多层的list对象
		Stack<Boolean> islist = new Stack<Boolean>();// 判断是不是list
		Stack<String> keys = new Stack<String>(); // 用来表示多层的key

		boolean hasyinhao = false;
		String keytmp = null;
		Object valuetmp = null;
		StringBuilder builder = new StringBuilder();
		char[] cs = jsonstring.toCharArray();

		for (int i = 0; i < cs.length; i++) {

			if (hasyinhao) {
				if (cs[i] != '\"' && cs[i] != '\'')
					builder.append(cs[i]);
				else
					hasyinhao = false;

				continue;
			}
			switch (cs[i]) {
			case '{': // 如果是{map进栈

				maps.push(new HashMap());
				islist.push(false);
				continue;
			case '\'':
			case '\"':
				hasyinhao = true;
				continue;
			case ':':// 如果是：表示这是一个属性建，key进栈

				keys.push(builder.toString());
				builder = new StringBuilder();
				continue;
			case '[':

				islist.push(true);
				lists.push(new ArrayList());
				continue;
			case ',':// 这是一个分割，因为可能是简单地string的键值对，也有可能是string=map
				// 的键值对，因此valuetmp 使用object类型；
				// 如果valuetmp是null 应该是第一次，如果value不是空有可能是string，那是上一个键值对，需要重新赋值
				// 还有可能是map对象，如果是map对象就不需要了

				boolean listis = islist.peek();

				if (builder.length() > 0)
					valuetmp = builder.toString();
				builder = new StringBuilder();
				if (!listis) {
					keytmp = keys.pop();
					maps.peek().put(keytmp, valuetmp);
				} else
					lists.peek().add(valuetmp);

				continue;
			case ']':

				islist.pop();

				if (builder.length() > 0)
					valuetmp = builder.toString();
				builder = new StringBuilder();
				lists.peek().add(valuetmp);
				valuetmp = lists.pop();
				continue;
			case '}':

				islist.pop();
				// 这里做的和，做的差不多，只是需要把valuetmp=maps.pop();把map弹出栈
				keytmp = keys.pop();

				if (builder.length() > 0)
					valuetmp = builder.toString();

				builder = new StringBuilder();
				maps.peek().put(keytmp, valuetmp);
				valuetmp = maps.pop();
				continue;
			default:
				builder.append(cs[i]);
				continue;
			}

		}
		SystemConst.D_LOG.debug(">>>>>> json2Map2-end");
		return valuetmp;
	}

	public static void main(String[] args) {
		try {
			getSyncData(1465799820000L, 1465800120001L, "http://101.200.233.150/sync/car/bindInfo?1=1", false, 0);
		} catch (ConnectTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static String formatDateParam(Long date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return date + "[" + dateFormat.format(new Date(date)) + "]";
	}

	/**
	 * 获取同步数据
	 * 
	 * @param start_date
	 * @param end_date
	 * @param url
	 * @param flag
	 * @param i
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws SystemException
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public static List<HashMap<String, Object>> getSyncData(Long start_date, Long end_date, String url, boolean flag,
			int i) throws ConnectTimeoutException, ClientProtocolException, IOException, SystemException {
		String aheadOfTime = PropertyUtil.getSystemProperty("ahead_of_time");
		long aheadTime = Long.valueOf(aheadOfTime);
		String requestTimeOut = PropertyUtil.getSystemProperty("request_time_out");
		int timeout = Integer.valueOf(requestTimeOut);
		timeout = timeout*1000;
		start_date = start_date - aheadTime * 1000;
		SystemConst.SYNCDATA_LOG.debug(">>>> getSyncData-start url:" + url + ";start_date:"
				+ formatDateParam(start_date) + ";end_date:" + formatDateParam(end_date));
		CloseableHttpClient httpclient = HttpClients.createDefault();
		url = getUrl(url, start_date, end_date);
		Map<String, Object> ret_map = ParamsHelper.getParams(url);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		if (ret_map != null) {
			url = (String) ret_map.get("url");
			nameValuePairs = (List<NameValuePair>) ret_map.get("nameValuePairs");
		}
		RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
				.setConnectionRequestTimeout(timeout).setStaleConnectionCheckEnabled(true).build();
		HttpPost httppost = new HttpPost(url);
		httppost.setConfig(defaultRequestConfig);
		httppost.setHeader("Accept-Encoding", "gzip, deflate");
		UrlEncodedFormEntity uefEntity;
		String returnStr = "";
		CloseableHttpResponse response = null;
		String hmDBDate = null;
		try {
			uefEntity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
			httppost.setEntity(uefEntity);
			response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				if (entity instanceof GzipDecompressingEntity) {
					GzipDecompressingEntity gEntity = (GzipDecompressingEntity) entity;
					returnStr = EntityUtils.toString(gEntity, "UTF-8");
				} else {
					returnStr = EntityUtils.toString(entity, "UTF-8");
				}

				JSONObject sobj = new JSONObject();
				sobj = sobj.fromObject(returnStr);
				if (sobj.containsKey("date")) {
					hmDBDate = sobj.getString("date");
				}
				if (sobj.containsKey("data")) {
					String jsonstring = sobj.getString("data");
					if (null != jsonstring) {
						return (List<HashMap<String, Object>>) json2Map2(jsonstring);
					}
				}
			}
			EntityUtils.consume(entity);
			return null;
		} catch (ClientProtocolException e) {
			printException(start_date, end_date, url, e);
			throw new ClientProtocolException(" ClientProtocolException");
		} catch (UnsupportedEncodingException e) {
			printException(start_date, end_date, url, e);
			throw new UnsupportedEncodingException(" 不支持的编码.");
		} catch (ConnectTimeoutException e) {
			printException(start_date, end_date, url, e);
			throw new ConnectTimeoutException(" 链接:" + url + " 超时 .");
		} catch (IOException e) {
			printException(start_date, end_date, url, e);
			throw new IOException(" IO异常.");
		} finally {
			try {
				if (response != null)
					response.close();
				if (httpclient != null)
					httpclient.close();
				SystemConst.SYNCDATA_LOG.debug(">>>> getSyncData-end url:" + url + ";start_date:"
						+ formatDateParam(start_date) + ";end_date:" + formatDateParam(end_date) + ";returnStr:"
						+ returnStr.trim() + ";hmDBDate:" + hmDBDate);
			} catch (IOException e) {
				printException(start_date, end_date, url, e);
				throw new IOException(" 获取数据关闭客户端异常. ");
			}
		}
	}
	
	public static void printException(Long start_date, Long end_date, String url, Exception e ){
		SystemConst.SYNCDATA_LOG.error(">>>> getSyncData ERROR url:" + url + ";start_date:"
				+ formatDateParam(start_date) + ";end_date:" + formatDateParam(end_date)+";error:"+e);
	}

}
