package com.chinaGPS.gtmp.util;


import java.text.*;
import java.io.*;

public class FormatUtil {


    //生成长时间字符串，形如：2005-01-01 01:01:01
    public static String getLongTimeString() {
        java.util.Date myDate = new java.util.Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //计算前一年
        //long myTime = (myDate.getTime() / 1000) - 60 * 60 * 24 * 365;
        //myDate.setTime(myTime * 1000);
        String mDate = formatter.format(myDate);
        return mDate;
    }

   
    public static String joinUnitid(String unitids,String sqlColumn){
		String strp[] = unitids.split(",");
		StringBuffer ustr = new StringBuffer();
		ustr.append(sqlColumn);
		ustr.append(" in (");
		for (int i = 0; i < strp.length; i++) {
			ustr.append(strp[i]);
			ustr.append(",");
			if (i % 900 == 0 && i != 0) {
				ustr.append(strp[i] + ")");
				if (i != strp.length - 1) {
					ustr.append(" or ");
					ustr.append(sqlColumn);
					ustr.append(" in (");
				}
			}
		}
		if (ustr.substring(ustr.length() - 1, ustr.length()).equals(",")) {
			ustr.replace(ustr.length() - 1, ustr.length(), ")");
		}
		return ustr.toString();
    }

   

    //返回格式：2004-05-14 14:10:32
    public String GetStringDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date currentTime_1 = new java.util.Date();
        String dateString = formatter.format(currentTime_1);
        return dateString;
    }





    public static String isoconversionutf8(String isostr){
    	/****为兼容火狐浏览器不进行编码***/
    	/*
    	  if(isostr==null||isostr.equals("")){
    		return isostr;
    	}
    	String utf=null;
		try {
			utf = new String(isostr.getBytes("ISO8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/
    	/*String s = null;
    	try {
			s = new String(isostr.getBytes("ISO8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/
    	return isostr;
    }
    
    public static String isoconverutf8(String isostr){
    	if(isostr==null||isostr.equals("")){
    		return isostr;
    	}
    	String utf=null;
		try {
			utf = new String(isostr.getBytes("ISO8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return utf;
    }
    
    public static String ISO2Utf8(String str) {
		String temp = str;/*
		try {
			if ((temp == null) || (temp.equals("null"))) {
				temp = "";
			}
			byte[] b = temp.getBytes("ISO8859-1"); //按双字节将串拆分
			str = new String(b, "UTF-8"); //按ISO8859-1编码方式将字节组合
		} catch (Exception ex) {
		}*/
		return str;
	}
    
    public static String strToFormat(String str){
		String[] strs = str.split(",");
		StringBuffer sb = new StringBuffer();
	    for(int i=0;i<strs.length;i++){
	    	if(!strs[i].equals("")){
	    		sb.append(strs[i]);
	    		if(i<strs.length-1){
	    			sb.append(",");
	    		}
	    	}
	    }
	    return sb.toString();
	}
    
}

  





   

   

    
    
   



   
   
   
