package com.chinaGPS.gtmp.util;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtil{
	private CalendarUtil(){
		
	}
	/**
	 * 输出当前系统时间。格式为：2011-1-15
	 */
	public static String getDate(){
		Calendar now=Calendar.getInstance();
		String dateStr=now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DAY_OF_MONTH);
		
		return dateStr;
	}
	
	/**
	 * 输出当前系统时间。格式为：2011-1-15 12:12:12
	 */
	public static String getDateTime(){
		Calendar now=Calendar.getInstance();
		String dateStr=now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DAY_OF_MONTH)+" "+now.get(Calendar.HOUR_OF_DAY)+":"+now.get(Calendar.MINUTE)+":"+now.get(Calendar.SECOND);
		
		return dateStr;
	}
	
	/**
	 * 获取系统时间N月后的时间。N为正数，表示月后，N为负数，表示月前。
	 */
	public static Date getThreeMonthAgoTime(int n){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, n);
		Date date = calendar.getTime();
		return date;
	}
}