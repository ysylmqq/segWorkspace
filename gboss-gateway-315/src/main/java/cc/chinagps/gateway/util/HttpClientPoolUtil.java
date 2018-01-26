package cc.chinagps.gateway.util;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import cc.chinagps.gateway.log.LogManager;

/**
 *@todo：使用Httpclient连接池实现工具类； 
 *@author：cj
 *@time：2017年3月29日
 *
 */
public class HttpClientPoolUtil {
	private final static int CONNECTION_TIMEOUT = 10 * 1000;//10秒
	private final static int CONNECTION_MAX_NUM = 200;//最大连接数
	private final static int MAX_PERROUT = 40;
	private final static int MAX_ROUTe = 100;//最大路由
	private static CloseableHttpClient httpClient = null;
	private final static Object syncLock = new Object();
	private static Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	
	/**
	 * @todo:获取CloseableHttpClient对象
	 * @author:cj
	 * @param:
	 * @return:
	 * @remark:
	 */
	public static CloseableHttpClient getHttpClient(String url) {
        if (httpClient == null) {
            synchronized (syncLock) {
                if (httpClient == null) {
                    httpClient = createHttpClient(CONNECTION_MAX_NUM, MAX_PERROUT, MAX_ROUTe,url);
                }
            }
        }
        return httpClient;
    }

    /**
     * @todo:创建HttpClient对象
     * @author:cj
     * @param:
     * @return:
     * @remark:
     */
    public static CloseableHttpClient createHttpClient(int maxTotal,int maxPerRoute, int maxRoute, String url) {
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create().register("http", plainsf).register("https", sslsf).build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
        // 将最大连接数增加
        cm.setMaxTotal(maxTotal);
        // 将每个路由基础的连接增加
        cm.setDefaultMaxPerRoute(maxPerRoute);
        URL tranUrl=null;
		try {
			tranUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
        HttpHost httpHost = new HttpHost(tranUrl.getHost(),tranUrl.getPort()!= -1?tranUrl.getPort():80);
        // 将目标主机的最大连接数增加
        cm.setMaxPerRoute(new HttpRoute(httpHost), maxRoute);
        // 请求重试处理
        HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception,
                    int executionCount, HttpContext context) {
                if (executionCount >= 5) {// 如果已经重试了5次，就放弃
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                    return false;
                }
                if (exception instanceof InterruptedIOException) {// 超时
                    return false;
                }
                if (exception instanceof UnknownHostException) {// 目标服务器不可达
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                    return false;
                }
                if (exception instanceof SSLException) {// SSL握手异常
                    return false;
                }

                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                // 如果请求是幂等的，就再次尝试
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }
                return false;
            }
        };
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).setRetryHandler(httpRequestRetryHandler).build();
        return httpClient;
    }
    
    /**
     * @todo:配置请求头信息
     * @author:cj
     * @param:
     * @return:
     * @remark:
     */
    private static void config(HttpRequestBase httpRequestBase,String contentType,Integer contLen) {
        //设置头信息
    	httpRequestBase.setHeader("Content-Type",contentType);
    	if(contLen != null){
    		httpRequestBase.setHeader("Content-Length",contLen.intValue()+"");
    	}
        // 配置请求的超时设置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(CONNECTION_TIMEOUT)
                .setConnectTimeout(CONNECTION_TIMEOUT).setSocketTimeout(CONNECTION_TIMEOUT).build();
        httpRequestBase.setConfig(requestConfig);
    }
    
    /**
     * @todo:get请求方法
     * @author:cj
     * @param:
     * @return:
     * @remark:
     */
    public static String getRequest(CloseableHttpClient httpClient,String url, String contentType, String data) {
    	String newUrl = url;//组装参数
    	if(data !=null){
    		if(url.endsWith("?")){
    			newUrl = url+"&"+data;//组装参数
    		}else{
    			newUrl = url+"?"+data;//组装参数
    		}
        }
    	HttpGet httpget = new HttpGet(newUrl);
    	//配置HttpGet头消息
    	config(httpget,contentType,data==null?null:data.length());
    	
        CloseableHttpResponse response = null;
        //打印调试信息
        logger_debug.debug("[getRequest-->URI]:"+httpget.getURI());
//        logger_debug.debug("[getRequest-->Config]:"+httpget.getConfig());
//        Header[] headers = httpget.getAllHeaders();
//    	for (Header header : headers) {
//    		logger_debug.debug("[getRequest-->header]:"+header);
//		}
    	
        try {
            response = httpClient.execute(httpget,HttpClientContext.create());
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "utf-8");
//            logger_debug.debug("[getRequest-->result]:"+result);
            EntityUtils.consume(entity);//关闭流
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    /**
     * @todo:Post请求URL获取内容
     * @param url
     * @return
     * @throws IOException 
     */
    public static String postRequest(CloseableHttpClient httpClient,String url,String contentType,Map<String, Object> params)throws Exception {
        HttpPost httppost = new HttpPost(url);
        config(httppost,contentType,null);
        //设置请求参数
        setPostParams(httppost, params);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httppost,HttpClientContext.create());
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * @todo:设置post方法参数
     * @author:cj
     * @param:
     * @return:
     * @remark:
     */
    private static void setPostParams(HttpPost httpost,Map<String, Object> params) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key).toString()));
        }
        try {
            httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
    	CloseableHttpClient httpClient = HttpClientPoolUtil.getHttpClient("www.baidu.com");
    	
    	String result1 = HttpClientPoolUtil.getRequest(httpClient,"http://blog.csdn.net/catoop/article/details/38849497","plain/html","abc");
    	System.out.println(result1);
    	/*String result2 = HttpClientPoolUtil.getRequest(httpClient,"http://blog.csdn.net/catoop/article/details/38849497","applicatopm/json","abc");
    	System.out.println(result2);
    	String result3 = HttpClientPoolUtil.getRequest(httpClient,"http://blog.csdn.net/catoop/article/details/38849497","applicatopm/json","abc");
    	System.out.println(result3);
    	String result4 = HttpClientPoolUtil.getRequest(httpClient,"http://blog.csdn.net/catoop/article/details/38849497","applicatopm/json","abc");
    	System.out.println(result4);
    	String result5 = HttpClientPoolUtil.getRequest(httpClient,"http://blog.csdn.net/catoop/article/details/38849497","applicatopm/json","abc");
    	System.out.println(result5);
    	String result6 = HttpClientPoolUtil.getRequest(httpClient,"http://blog.csdn.net/catoop/article/details/38849497","applicatopm/json","abc");
    	System.out.println(result6);*/
	}
}
