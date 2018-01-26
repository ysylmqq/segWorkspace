package com.chinaGPS.gtmp.util;


import java.util.List;
import java.util.ArrayList;

public class StringUtils {
	public static String[] split(String str,char splitchar){ 
		  if (str == null) {
	          return null;
	      }
	      int len = str.length();
	      if (len == 0) {
	          return null;
	      }
	      List<String> list = new ArrayList<String>();
	      int i = 0, start = 0; 
	      while (i < len) {
	          if (str.charAt(i) == splitchar) { 
	             list.add(str.substring(start, i)); 
	              start = ++i;
	              continue;
	          } 
	          i++;
	      }
	      if (start!=i ) {
	          list.add(str.substring(start, i));
	      }
	      
	      return (String[]) list.toArray(new String[list.size()]);
		}
	
	public static boolean isNotBlank(String str) {
		if(str != null && !"".equals(str.trim())) {
			return true;
		}
		return false;
	}
	
	public static boolean isBlank(String str) {
		return !isNotBlank(str);
	}
	
	public static String trim(String str) {
		if(str!=null) {
			return str.trim();
		}
		
		return "";
	}
	
	/**
	 * 字符串转成integer数组
	 * @param str String str="0，1";
	 * @return  {0，1}
	 */
	public static Integer[] stringToIntArray(String str) {
		String[] strArrayStrings=str.split(",");
		Integer restult[] = new Integer[str.length()];
		for(int i=0;i<strArrayStrings.length;i++){
			restult[i]=Integer.parseInt(strArrayStrings[i]);
			//输出测试一下：
			//System.out.println(restult[i]);
		}	
		return restult;
	}
	
	public static Integer getCommandTypeId(String param){
		int result = 52;		
		if(param != null && param.length()>0){
			if(param.equals("5A0000")){
				result = 61;
			}else if(param.equals("A50000")){
				result = 62;
			}else if(param.equals("005BC5")){
				result = 63;
			}else if(param.equals("00B500")){
				result = 64;
			}else if(param.equals("00B55C")){
				result = 65;
			}else if(param.equals("0000C5")){
				result = 66;
			}
		}
		return result;
	}	
}
