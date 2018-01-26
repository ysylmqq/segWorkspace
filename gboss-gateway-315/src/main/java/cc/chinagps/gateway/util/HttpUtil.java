package cc.chinagps.gateway.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import cc.chinagps.gateway.StartServer;

public class HttpUtil {
	private static int httpTimeOut = 10000;

	static {
		String timeOut = SystemConfig.getSystemProperty("http_time_out");
		if (timeOut != null) {
			httpTimeOut = Integer.valueOf(timeOut);
		}
	}

	public static String doPost(String url, String params) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse result = null; 
		try {
			httpClient = HttpClients.createDefault();
			HttpPost method = new HttpPost(url);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(httpTimeOut)
					.setConnectTimeout(httpTimeOut).setConnectionRequestTimeout(httpTimeOut).build();
			method.setConfig(requestConfig);
			if (params != null && params.trim().length() != 0) {
				// …Ë÷√≤Œ ˝
				StringEntity stringEntity = new StringEntity(params, "UTF-8");
				stringEntity.setContentType("application/json");
				stringEntity.setContentEncoding("UTF-8");
				method.setEntity(stringEntity);
			}
			result = httpClient.execute(method);
			String resData = EntityUtils.toString(result.getEntity());
			return resData;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			StartServer.instance.getUnitServer().exceptionCaught(e);
		} catch (UnsupportedEncodingException e) {
			StartServer.instance.getUnitServer().exceptionCaught(e);
		} catch (ConnectTimeoutException e) {
			StartServer.instance.getUnitServer().exceptionCaught(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			StartServer.instance.getUnitServer().exceptionCaught(e);
		} finally {
			try {
				if (result != null)
					result.close();
				if (httpClient != null)
					httpClient.close();
			} catch (IOException e) {
				StartServer.instance.getUnitServer().exceptionCaught(e);
			}
		}
		return null;
	}
	
	public static String request(String address, String method, String contentType, String data) {
		String responseText = "";
		HttpURLConnection conn = null;
		try {
			URL url = new URL(address);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(httpTimeOut);
			conn.setReadTimeout(httpTimeOut);
			conn.setDoOutput(true);
			if (data != null) {
				conn.setDoInput(true);

			}
			conn.setRequestMethod(method);
			conn.setRequestProperty("Content-Type", contentType);
			conn.setRequestProperty("User-Agent", "");
			// conn.setRequestProperty("Accept", "application/json");
			// conn.setRequestProperty("Accept-Encoding", "gzip");
			if (data != null) {
				conn.setRequestProperty("Content-Length", data.getBytes().length + "");
			}
			conn.setUseCaches(false);
			OutputStreamWriter wr = null;
			if (data != null) {
				wr = new OutputStreamWriter(conn.getOutputStream());
				wr.write(data);
				wr.flush();
			}

			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String currentLine = "";
			StringBuffer sb = new StringBuffer();
			while ((currentLine = rd.readLine()) != null) {
				sb.append(currentLine);
				sb.append("\n");
			}
			responseText = sb.toString();

			if (data != null) {
				wr.close();
			}
			rd.close();

		} catch (Exception ex) {
			if (conn != null) {
				conn.disconnect();
			}
			StartServer.instance.getUnitServer().exceptionCaught(ex);
		}
		return responseText;
	}
}
