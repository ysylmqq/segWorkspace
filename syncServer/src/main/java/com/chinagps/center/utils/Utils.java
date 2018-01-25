package com.chinagps.center.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
  
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
  
        if(obj == null){  
            return null;  
        }          
        Map<String, Object> map = new HashMap<String, Object>();  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
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
    
    public static String getNowTimeString(){
    	SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	Date date = new Date();
    	return df.format(date);
    }
    
    public static String getFileNo(){
    	long time = new Date().getTime();
    	String fileNo = "CTFN-"+time;
    	return fileNo;
    }
    
    public static String list2String(List<String> list){
    	if(list==null||list.size()==0){
    		return "";
    	}
    	String result = "";
    	StringBuffer sb = new StringBuffer();
    	for(String str:list){
    		sb.append("'").append(str).append("'").append(",");
    	}
    	result = sb.toString();
    	return result.substring(0, result.length()-1);
    }
    
    public static String getDayByDate(String date){
    	String day = date.substring(8);
    	if(day.startsWith("0")){
    		day = day.substring(1);
    	}
    	return day;
    }
    
    public static String getStringDate(String date){
    	if(date.length()==4){
    		return date+"年";
    	}
    	String year = date.substring(0, 4);
    	String month = date.substring(5, 7);
    	String result = year+"年"+month+"月";
    	return result;
    }
	
}
