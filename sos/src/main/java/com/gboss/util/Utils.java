package com.gboss.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class Utils {

	public static boolean isIPMatches(String ip, String fregex) {
		String[] fregs = fregex.split(",");
		for (int i = 0; i < fregs.length; i++) {
			if (fregs[i].indexOf("-") != -1) {
				if (isRangeMatch(ip, fregs[i])) {
					return true;
				}
			} else {
				if (isAnyMatch(ip, fregs[i])) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 验证字符串是否为纯数字
	 */
	public static boolean isNumeric(String str) {
		if (str == null || str == "")
			return false;
		int sz = str.length();
		for (int i = 0; i < sz; i++)
			if (!Character.isDigit(str.charAt(i)))
				return false;

		return true;
	}

	/**
	 * 验证范围类型的IP 192.168.2.1-192.168.2.125
	 */
	private static boolean isRangeMatch(String ip, String fregex) {
		String[] fregexs = fregex.split("-");
		if (fregexs.length != 2) {
			return false;
		}
		long v, v1, v2;
		try {
			v = getIPValue(ip);
			v1 = getIPValue(fregexs[0]);
			v2 = getIPValue(fregexs[1]);
		} catch (Exception e) {
			return false;
		}

		return (v >= v1 && v <= v2) || (v >= v2 || v <= v1);
	}

	/**
	 * 验证*类型的IP
	 */
	private static boolean isAnyMatch(String ip, String fregex) {
		String regex = fregex.replace(".", "\\.").replace("*", "[\\d, \\.]+");
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(ip);

		return m.matches();
	}

	/**
	 * 获取IP数值
	 */
	private static long getIPValue(String ip) {
		String[] scode = ip.split("\\.");
		StringBuilder sb = new StringBuilder();
		for (String s : scode) {
			int k = Integer.valueOf(s);
			sb.append(getBinaryString(k, 8));
		}
		return Long.valueOf(sb.toString(), 2);
	}
	
	/**
	 * 获取访问用户的客户端IP（适用于公网与局域网）.
	 */
	public static final String getIpAddr(final HttpServletRequest request)
			throws Exception {
		String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
    	  ip = request.getHeader("http_client_ip");  
    	}  
    	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
    	  ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
    	}  
    	if (ip != null && ip.indexOf(",") != -1) {  
		  ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();  
		} 
        if("0:0:0:0:0:0:0:1".equals(ip))
        {
        	ip="127.0.0.1";
        }
        return ip;
	}

	private static String getBinaryString(int k, int length) {
		String end = Integer.toBinaryString(k);
		int need0 = length - end.length();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < need0; i++) {
			sb.append("0");
		}
		sb.append(end);
		return sb.toString();
	}

	// Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean
	public static void transMap2Bean(Map<String, Object> map, Object obj) {

		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();

			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				if (map.containsKey(key)) {
					Object value = map.get(key);
					// 得到property对应的setter方法
					Method setter = property.getWriteMethod();
					setter.invoke(obj, value);
				}

			}

		} catch (Exception e) {
			System.out.println("transMap2Bean Error " + e);
		}

		return;

	}

	// Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
	public static Map<String, Object> transBean2Map(Object obj) {

		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);

					map.put(key, value);
				}

			}
		} catch (Exception e) {
			System.out.println("transBean2Map Error " + e);
		}

		return map;

	}

	public static String getNowTimeString() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		return df.format(date);
	}

	public static String getNowDateString() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Date date = new Date();
		return df.format(date);
	}

	/**
	 * 
	 * @Title:getDateString
	 * @Description:排班只需到年月日
	 * @return
	 * @throws
	 */
	public static String getDateString() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return df.format(date);
	}

	/**
	 * 获取结束 时间
	 * 
	 * @Title:getEndTime
	 * @Description:TODO
	 * @param startTime
	 * @param duration
	 * @return
	 * @throws
	 */
	public static String getEndTime(String startTime, String duration) {
		String[] a = startTime.split(":");
		String[] b = duration.split(":");
		int ahour = Integer.valueOf(a[0].trim());
		int amin = Integer.valueOf(a[1].trim());
		int bhour = Integer.valueOf(b[0].trim());
		int bmin = Integer.valueOf(b[1].trim());
		ahour = ahour + bhour + (amin + bmin) / 60;
		amin = (amin + bmin) % 60;
		String h = ahour > 10 ? ahour + "" : "0" + ahour;
		String m = amin > 10 ? amin + "" : "0" + amin;
		return h + ":" + m;

	}

	public static String getFileNo() {
		long time = new Date().getTime();
		String fileNo = "CTFN-" + time;
		return fileNo;
	}

	public static String list2String(List<String> list) {
		if (list == null || list.size() == 0) {
			return "";
		}
		String result = "";
		StringBuffer sb = new StringBuffer();
		for (String str : list) {
			sb.append("'").append(str).append("'").append(",");
		}
		result = sb.toString();
		return result.substring(0, result.length() - 1);
	}

	public static String getDayByDate(String date) {
		String day = date.substring(8);
		if (day.startsWith("0")) {
			day = day.substring(1);
		}
		return day;
	}

	public static String getStringDate(String date) {
		if (date.length() == 4) {
			return date + "年";
		}
		String year = date.substring(0, 4);
		String month = date.substring(5, 7);
		String result = year + "年" + month + "月";
		return result;
	}

	/**
	 * @Title:formatSerial
	 * @Description:流水号加1，前面不足4位，用0补充
	 * @param serialNo
	 * @return
	 * @throws
	 */
	public static String formatSerial(int serialNo) {
		serialNo++;
		DecimalFormat df = new DecimalFormat("0000");
		return df.format(serialNo);
	}

	/**
	 * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null)
			return true;

		try {
			if (obj instanceof CharSequence)
				return ((CharSequence) obj).length() == 0;

			if (obj instanceof Collection)
				return ((Collection) obj).isEmpty();

			if (obj instanceof Map)
				return ((Map) obj).isEmpty();

			if (obj instanceof Object[]) {
				Object[] object = (Object[]) obj;
				if (object.length == 0) {
					return true;
				}
				boolean empty = true;
				for (int i = 0; i < object.length; i++) {
					if (!isNullOrEmpty(object[i])) {
						empty = false;
						break;
					}
				}
				return empty;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean isNotNullOrEmpty(Object obj) {
		return !isNullOrEmpty(obj);
	}

	public static double objToDouble(Object obj) {
		if (obj == null) {
			return 0d;
		} else {
			return Double.valueOf(obj.toString());
		}
	}
	
	public static String clearNull(Object obj){
		if(obj == null){
			return "";
		}
		
		return obj.toString();
	}
    
	/*public static void putSimMap(Preload preload) {
		if (preload.getCall_letter() != null)
			SystemConst.SIM_MAP.put(preload.getCall_letter(), preload);
		if (preload.getImei() != null)
			SystemConst.IMEI_MAP.put(preload.getImei(), preload);
		if (preload.getBarcode() != null)
			SystemConst.BARCODE_MAP.put(preload.getBarcode(), preload);
	}*/
}
