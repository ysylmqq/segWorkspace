package com.chinaGPS.gtmp.action.common;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

import com.chinaGPS.gtmp.util.Constants;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 封装一些基本的Action功能.
 * @author xk
 *
 */
public abstract class BaseAction extends ActionSupport {
	private static final long serialVersionUID = -5317160356624506011L;
	private static Logger logger = Logger.getLogger(BaseAction.class); 
	
	// -- Content Type 定义 --//
	public static final String TEXT_TYPE = "text/plain;charset=UTF-8";
	public static final String JSON_TYPE = "application/json;charset=UTF-8";
	public static final String XML_TYPE = "text/xml;charset=UTF-8";
	public static final String HTML_TYPE = "text/html;charset=UTF-8";
	public static final String JS_TYPE = "text/javascript;charset=UTF-8";
	
	public static final String OP_SUCCESS="操作成功";
	public static final String OP_FAIL="操作失败";
	/**
	 * 读取表单参数
　　  * @param name
　　  */
	public String getParameter(String name) {
		return ServletActionContext.getRequest().getParameter(name);
	}
	/**
　　  * 将表单参数作为整数返回.
　　  * @param name 表单参数名
　　  */
	public int getParameterInt(String name) {
		String s = getParameter(name);
		if(s == null) {
			return 0;
		} else {
			try {
				return Integer.parseInt(s);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	/**
　　  * 将表单参数作为长整数返回.
　　  * @param name 表单参数名
　　  */
	public long getParameterLong(String name) {
		String s = getParameter(name);
		if(s == null) {
			return 0L;
		} else {
			try {
				return Long.parseLong(s);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return 0L;
	}
	/**
　　  * 设置 request 的属性.
　　  * @param name 属性名
　　  * @param value 属性值
　　  */
	public void setAttribute(String name, Object value) {
		ServletActionContext.getRequest().setAttribute(name, value);
	}
	/**
　　  * 获取 request 的属性.
　　  * @param name 属性名
　　  */
	public Object getAttribute(String name) {
		return ServletActionContext.getRequest().getAttribute(name);
	}
	/**
　　  * 读取 session
　　  */
	public static Map<String, Object> getSession() {
		ActionContext ctx = ActionContext.getContext();
		return ctx.getSession();
	}
	/**
　　  * 获取 application 对象.
　　  */
	public static ServletContext getApplication() {
		return ServletActionContext.getServletContext();
	}
	/**
　　	 * 获取请求对象.
　　  */
	public static HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
	/**
	 * 生成json字符串.
	 * @param object
	 *            Java对象,可以是List<POJO>, POJO[], POJO ,也可以Map名值对, 将被转化为json字符串.
	 * @throws JSONException 
	 */
	public static String objectToJson(Object object){
		String json = "";
		try {
			json =  JSONUtil.serialize(object).toString();
		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
		}
		return json;
	}
	
	/**
	 * 返回字符串（json字符串）
	 */
	public static void renderString(String str){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/javascript;charset=UTF-8");
		try {
			response.getWriter().write(str);
		} catch (IOException e) {
			logger.error("数据发送异常，发送内容："+str.substring(0, str.length()>200?50:str.length()));
		}
	}
	
	/**
	 * 
	 * 返回字符串（json字符串）
	 * @param str
	 * @param contentType 如"text/html;charset=UTF-8"
	 */
	public static void renderString(String str,String contentType){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType(contentType);
		try {
			response.getWriter().write(str);
		} catch (IOException e) {
		}
	}
	
	public static void renderJsonString(String str){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType(JSON_TYPE);
		try {
			response.getWriter().write(str);
		} catch (IOException e) {
			logger.error("数据发送异常，发送内容："+str.substring(0, str.length()>200?50:str.length()));
			e.printStackTrace();
		}
	}
	public static void renderHtmlString(String str){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType(HTML_TYPE);
		try {
			response.getWriter().write(str);
		} catch (IOException e) {
			logger.error("数据发送异常，发送内容："+str.substring(0, str.length()>200?50:str.length()));
		}
	}
	/**
	 * 输入对象，返回json字符串
	 * @param object
	 *            Java对象,可以是List<POJO>, POJO[], POJO ,也可以Map名值对, 将被转化为json字符串.
	 * @throws JSONException 
	 */
	public static void renderObject(Object object){
		renderHtmlString(objectToJson(object));
	}
	/**
	 * @Title:renderMsgJson
	 * @Description:
	 * @param result
	 * @param msg
	 * @throws 返回json
	 */
	public static void renderMsgJson(boolean result,String msg) {
	      Map<String,Object> map=new HashMap<String,Object>();
	      map.put("msg", msg);
	      map.put("success", result);
	      renderHtmlString(objectToJson(map));
	}
	/**
	 * 保存操作日志，此方法只在action层中调用
	 * @param session 用户Session信息
	 * @param message 日志信息
	 *//*
	public static void logger(String message){
		Map map = getSession();
		MDC.put("logId",UUID.randomUUID().toString());
		Object obj=map.get(Constants.USER_ID);
		if(obj!=null){
		    MDC.put("userId",(Long)obj);
		}
		String ip=null;
		if(map.get(Constants.REMOTE_IP)!=null){
		    ip=(String)map.get(Constants.REMOTE_IP);
		}else{
		    ip=getIpAddr(getRequest());
		}
		MDC.put("remoteIp",ip);
		logger.info(message);
	}*/
	/**
	 * 保存异常日志，此方法只在action层中调用
	 * @param session 用户Session信息
	 * @param message 异常信息
	 */
	public static void error(String message){
		Map<String, Object> map = getSession();
		MDC.put("logId",UUID.randomUUID().toString());
		Object obj=map.get(Constants.USER_ID);
		if(obj!=null){
		    MDC.put("userId",(Long)obj);
		}
		logger.error(message);
	}
	/** 
	     * 替换文件上传中出现的错误信息 引用 import java.util.regex.Matcher; import 
	     * java.util.regex.Pattern; 
	     *  
	     */  
	    @Override  
	    public void addActionError(String anErrorMessage) {  
	        // 这里要先判断一下，是我们要替换的错误，才处理  
	        if (anErrorMessage  
	                .startsWith("the request was rejected because its size")) {  
	            Matcher m = Pattern.compile("\\d+").matcher(anErrorMessage);  
	            String s1 = "";  
	            if (m.find())  
	                s1 = m.group();  
	            String s2 = "";  
	            if (m.find())  
	                s2 = m.group(); 
	            this.clearErrorsAndMessages();
	           // 偷梁换柱，将信息替换掉 
	             super.addActionError("你上传的文件大小（" + s1 + "字节）超过允许的大小（" + s2 + "）字节");
	              /*// this.renderExtMesssageJson(false, "你上传的文件大小！");
	            this.addActionError("你上传的文件大小（" + s1 + "）超过允许的大小（" + s2 + "）");  
	            // 也可以改为在Field级别的错误  
	             super.addFieldError("file","你上传的文件大小（" + s1 + "）超过允许的大小（" + s2 +  
	             "）");  */
	        } else {// 否则按原来的方法处理  
	            super.addActionError(anErrorMessage);  
	        }  
	    }  
	    public static String getIpAddr(HttpServletRequest request)  {
	            String ip  =  request.getHeader( " x-forwarded-for " );
	             if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {
	                ip  =  request.getHeader( " Proxy-Client-IP " );
	            } 
	             if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {
	               ip  =  request.getHeader( " WL-Proxy-Client-IP " );
	            } 
	             if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {
	               ip  =  request.getRemoteAddr();
	          } 
	            return  ip;
	       }
	    /**
		 * 生成excel格式的下载文件
		 * 
		 * @param filename
		 *            下载文件名
		 * @param heads
		 *            列名
		 * @param datalist
		 *            数据列表，对应列头
		 */
		public String renderExcel(String filename, Object[] heads,
				List<Object[]> datalist) {
			try {
				String characterEncoding = getResponse().getCharacterEncoding();
				String filenameRes=new String(filename.getBytes("GBK"), characterEncoding);
				String contentTypeRes="application/vnd.ms-excel";
				
				HttpServletResponse response = getResponse();
				OutputStream os = response.getOutputStream();
				if (!response.isCommitted()) {
					response.reset();
				}
				// 设置缓冲区为512KB，避免输出Transfer-Encoding:chunked头
				response.setBufferSize(524288);
				// 设置headers参数
				if (StringUtils.isNotBlank(contentTypeRes)) {
					response.setContentType(contentTypeRes);
				}
				// Http 1.1 header
				// response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
				if (StringUtils.isNotBlank(filenameRes)) {
					response.setHeader("Content-disposition", "attachment;"
							+ "filename=" + filenameRes);
				}
				
				WritableWorkbook wbook = Workbook.createWorkbook(os);
				WritableSheet wsheet = wbook.createSheet(filename, 0);
				String value = null;
				for (int i = 0; i < heads.length; i++) {
					if (heads[i] != null) {
						value = heads[i].toString();
					} else {
						value = "";
					}
					Label label = new Label(i, 0, value);
					wsheet.addCell(label);
				}
				for (int i = 0; i < datalist.size(); i++) {
					for (int j = 0; j < datalist.get(i).length; j++) {
						if (datalist.get(i)[j] != null) {
							value = datalist.get(i)[j].toString();
						} else {
							value = "";
						}
						Label label = new Label(j, i + 1, value);
						wsheet.addCell(label);
					}
				}
				wbook.write();
				wbook.close();
				os.flush();
				os.close();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			return NONE;
		}
		 /**
		 * 生成excel格式的下载文件
		 * 
		 * @param filename
		 *            下载文件名
		 * @param heads
		 *            列名
		 * @param datalist
		 *            数据列表，对应列头
		 */
		public String renderExcel2(String filename, Object[] heads,
				List<Map<String,Object>> datalist) {
			try {
				String characterEncoding = getResponse().getCharacterEncoding();
				String filenameRes=new String(filename.getBytes("GBK"), characterEncoding);
				String contentTypeRes="application/vnd.ms-excel";
				
				HttpServletResponse response = getResponse();
				OutputStream os = response.getOutputStream();
				if (!response.isCommitted()) {
					response.reset();
				}
				// 设置缓冲区为512KB，避免输出Transfer-Encoding:chunked头
				response.setBufferSize(524288);
				// 设置headers参数
				if (StringUtils.isNotBlank(contentTypeRes)) {
					response.setContentType(contentTypeRes);
				}
				// Http 1.1 header
				// response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
				if (StringUtils.isNotBlank(filenameRes)) {
					response.setHeader("Content-disposition", "attachment;"
							+ "filename=" + filenameRes);
				}
				
				WritableWorkbook wbook = Workbook.createWorkbook(os);
				WritableSheet wsheet = wbook.createSheet(filename, 0);
				String value = null;
				for (int i = 0; i < heads.length; i++) {
					if (heads[i] != null) {
						value = heads[i].toString();
					} else {
						value = "";
					}
					Label label = new Label(i, 0, value);
					wsheet.addCell(label);
				}
				
				for (int i = 0; i < datalist.size(); i++) {
					for (int j = 0; j < datalist.get(i).size(); j++) {
						if (datalist.get(i).get(j+"") != null) {
							value = datalist.get(i).get(j+"").toString();
						} else {
							value = "";
						}
						Label label = new Label(j, i + 1, value);
						wsheet.addCell(label);
					}
				}
				
				wbook.write();
				wbook.close();
				os.flush();
				os.close();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			return NONE;
		}
		/**
		 * 获取response对象
		 * 
		 * @return {@link javax.servlet.http.HttpServletResponse}
		 */
		public static HttpServletResponse getResponse() {
			return ServletActionContext.getResponse();
		}
}

