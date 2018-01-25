package com.gboss.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 日期工具类.
 * <p>
 * 一些常用的日期计算和格式化
 * 
 * @author chenzh
 * @version 1.0
 * 
 */
public class DateUtil {

	public static final String YMD = "yyyyMMdd";
	public static final String YMD_SLASH = "yyyy/MM/dd";
	public static final String YMD_DASH = "yyyy-MM-dd";
	public static final String YM_DASH = "yyyy-MM";
	public static final String YMD_DASH_WITH_TIME = "yyyy-MM-dd H:m";
	public static final String YMD_DASH_WITH_FULLTIME = "yyyy-MM-dd HH:mm:ss";
	public static final String YMD_DASH_WITH_FULLTIME24 = "yyyy-MM-dd HH:mm:ss";
	public static final String YDM_SLASH = "yyyy/MM/dd";
	public static final String YDM_DASH = "yyyy-MM-dd";
	public static final String HM = "HHmm";
	public static final String HM_COLON = "HH:mm";
	public static final String YMD_DASH_IMPORT = "yy-MM-dd";
	private static SimpleDateFormat format = new SimpleDateFormat(YMD_DASH);
	public static Calendar calendar = null; 
	public static final long DAY = 24 * 60 * 60 * 1000L;

	private static final Map<String, DateFormat> DFS = new HashMap<String, DateFormat>();

	private static final Log log = LogFactory.getLog(DateUtil.class);

	private DateUtil() {
	}

	public static DateFormat getFormat(String pattern) {
		DateFormat format = DFS.get(pattern);
		if (format == null) {
			format = new SimpleDateFormat(pattern);
			DFS.put(pattern, format);
		}
		return format;
	}

	public static Date parse(String source, String pattern) {
		if (StringUtils.isBlank(source)) {
			return null;
		}
		Date date;
		try {
			date = getFormat(pattern).parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
			if (log.isDebugEnabled()) {
				log.debug(source + " doesn't match " + pattern);
			}
			return null;
		}
		return date;
	}
	public static Date parseAddMonth(String source, String pattern) {
		if (StringUtils.isBlank(source)) {
			return null;
		}
		Date date;
		try {
			date = getFormat(pattern).parse(source);
			date.setMonth(date.getMonth()+1);
		} catch (ParseException e) {
			if (log.isDebugEnabled()) {
				log.debug(source + " doesn't match " + pattern);
			}
			return null;
		}
		return date;
	}
	public static String parseDateString(String source) {
		if (StringUtils.isBlank(source)) {
			return null;
		}
		String[] t = source.split(" ");

		return t[0];
	}

	public static String format(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		return getFormat(pattern).format(date);
	}
	
	public static Date getEndDate(Date s_date, Integer s_month, Integer s_days){
		if(null !=s_date ){
			Calendar cl = Calendar.getInstance();
			cl.setTime(s_date);
			cl.add(Calendar.MONTH, s_month);
			cl.add(Calendar.DATE, s_days);
			return cl.getTime();
		}
		return null;
	}
	
	public static String dateforSearch(Map conditionMap, String date) {
		String time = "";
		if(date.contains("start") && StringUtils.isNotNullOrEmpty(conditionMap.get(date))){
			time = conditionMap.get(date).toString() +" 00:00:00";
		}
		if(date.contains("end") && StringUtils.isNotNullOrEmpty(conditionMap.get(date))){
			time = conditionMap.get(date).toString() +" 23:59:59";
		}
		return time;
	}

	/**
	 * @param year
	 *            年
	 * @param month
	 *            月(1-12)
	 * @param day
	 *            日(1-31)
	 * @return 输入的年、月、日是否是有效日期
	 */
	public static boolean isValid(int year, int month, int day) {
		if (month > 0 && month < 13 && day > 0 && day < 32) {
			// month of calendar is 0-based
			int mon = month - 1;
			Calendar calendar = new GregorianCalendar(year, mon, day);
			if (calendar.get(Calendar.YEAR) == year
					&& calendar.get(Calendar.MONTH) == mon
					&& calendar.get(Calendar.DAY_OF_MONTH) == day) {
				return true;
			}
		}
		return false;
	}
	
	public static Date getDate(Date date){
		
		Calendar calendar = convert(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	private static Calendar convert(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * 返回指定年数位移后的日期
	 */
	public static Date yearOffset(Date date, int offset) {
		return offsetDate(date, Calendar.YEAR, offset);
	}

	/**
	 * 返回指定月数位移后的日期
	 */
	public static Date monthOffset(Date date, int offset) {
		return offsetDate(date, Calendar.MONTH, offset);
	}

	/**
	 * 返回指定天数位移后的日期
	 */
	public static Date dayOffset(Date date, int offset) {
		return offsetDate(date, Calendar.DATE, offset);
	}

	/**
	 * 返回指定日期相应位移后的日期
	 * 
	 * @param date
	 *            参考日期
	 * @param field
	 *            位移单位，见 {@link Calendar}
	 * @param offset
	 *            位移数量，正数表示之后的时间，负数表示之前的时间
	 * @return 位移后的日期
	 */
	public static Date offsetDate(Date date, int field, int offset) {
		Calendar calendar = convert(date);
		calendar.add(field, offset);
		return calendar.getTime();
	}

	/**
	 * 返回当月第一天的日期
	 */
	public static Date firstDay(Date date) {
		Calendar calendar = convert(date);
		calendar.set(Calendar.DATE, 1);
		return calendar.getTime();
	}

	/**
	 * 返回当月第一天的日期,时间为零时零分零秒
	 */
	public static Date firstDayOfMonth(Date date) {
		Calendar calendar = convert(firstDay(date));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 返回当年第一天的日期,时间为零时零分零秒
	 */
	public static Date firstDayOfYear(Date date) {
		Calendar calendar = convert(date);
		calendar.set(Calendar.MONTH, calendar.getActualMinimum(Calendar.MONTH));
		return firstDayOfMonth(calendar.getTime());
	}

	/**
	 * 返回当年最后一天的日期,时间为零时零分零秒
	 */
	public static Date lastDayOfYear(Date date) {
		Calendar calendar = convert(date);
		calendar.set(Calendar.MONTH, calendar.getActualMaximum(Calendar.MONTH));
		return lastDayOfMonth(calendar.getTime());
	}

	/**
	 * 返回当月最后一天的日期
	 */
	public static Date lastDay(Date date) {
		Calendar calendar = convert(date);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		return calendar.getTime();
	}

	/**
	 * 返回当月最后一天的日期,时间零时零分零秒
	 */
	public static Date lastDayOfMonth(Date date) {
		Calendar calendar = convert(lastDay(date));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 返回两个日期间的差异天数
	 * 
	 * @param date1
	 *            参照日期
	 * @param date2
	 *            比较日期
	 * @return 参照日期与比较日期之间的天数差异，正数表示参照日期在比较日期之后，0表示两个日期同天，负数表示参照日期在比较日期之前
	 */
	public static int dayDiff(Date date1, Date date2) {
		long diff = date1.getTime() - date2.getTime();
		return (int) (diff / DAY);
	}

	public static long secondeDiff(Date date1, Date date2){
		long diff = date1.getTime() - date2.getTime();
		return diff/1000L;
	}
	
	public static Date addDay(Date date, int num) {
		Calendar calendar = convert(date);
		calendar.add(Calendar.DAY_OF_MONTH, num);
		return calendar.getTime();
	}

	public static Date addMonth(Date date, int num) {
		Calendar calendar = convert(date);
		calendar.add(Calendar.MONTH, num);
		return calendar.getTime();
	}

	public static Date addYear(Date date, int num) {
		Calendar calendar = convert(date);
		calendar.add(Calendar.YEAR, num);
		return calendar.getTime();
	}

	public static Date addHour(Date date, int num) {
		Calendar calendar = convert(date);
		calendar.add(Calendar.HOUR_OF_DAY, num);
		return calendar.getTime();
	}

	public static Date addMinute(Date date, int num) {
		Calendar calendar = convert(date);
		calendar.add(Calendar.MINUTE, num);
		return calendar.getTime();
	}

	public static Date addSecond(Date date, int num) {
		Calendar calendar = convert(date);
		calendar.add(Calendar.SECOND, num);
		return calendar.getTime();
	}

	public static String parseLog(String source){
		//Date d=parse(source,YMD_DASH_WITH_FULLTIME);
		//String result = parseDateString(d);
		//String source="2011-12-05 10:00:34.0";
			String result = source.substring(0, source.indexOf("."));
			//System.out.println("invoke"+re[0]);
			return result;
	}
	/**
	 * @Title:formatPreMonth
	 * @Description:获得上一个月年月 ,格式如"2013-02"
	 * @return
	 * @throws
	 */
	public static String formatPreMonth() {
		Date date=new Date();
		date.setMonth(date.getMonth()-1);
		return getFormat(YM_DASH).format(date);
	}
	/**
	 * @Title:formatToday
	 * @Description:获得今天的日期 格式如 "2013-04-12"
	 * @return
	 * @throws
	 */
	public static String formatToday() {
		return getFormat(YMD_DASH).format(new Date());
	}
	
	public static String formatBeforeday() {
		Calendar cl = Calendar.getInstance();
		cl.setTime(new Date());
		cl.add(Calendar.DATE, -1);
		Date date  = cl.getTime();
		return getFormat(YMD).format(date);
	}
	
	public static String formatlastBeforeday() {
		Calendar cl = Calendar.getInstance();
		cl.setTime(new Date());
		cl.add(Calendar.DATE, -1);
		Date date  = cl.getTime();
		return getFormat(YMD_DASH).format(date);
	}
	
	
	public static String getBeforeTenDay() {
		Calendar cl = Calendar.getInstance();
		cl.setTime(new Date());
		cl.add(Calendar.DATE, -11);
		Date date  = cl.getTime();
		return getFormat(YMD_DASH).format(date);
	}
	
	

	public static Date getBeforeDay() {
		Calendar cl = Calendar.getInstance();
		cl.setTime(new Date());
		cl.add(Calendar.DATE, -1);
		Date date  = cl.getTime();
		return date;
	}
	
	
	
	
	
	/**
	 * @Title:formatNowTime
	 * @Description:获得当前系统时间 格式如"2013-04-12 12:23:12"
	 * @return
	 * @throws
	 */
	public static String formatNowTime() {
		return getFormat(YMD_DASH_WITH_FULLTIME24).format(new Date());
	}
	public static void main(String[] args) {
		System.out.println(addDay(lastDayOfMonth(new Date()), 131));
		String source="2011-12-05 10:00:34.0";
		//parseLog(source);
		System.out.println(formatPreMonth());
		//System.out.println(lastDayOfYear(new Date()).toLocaleString());
		//System.out.println(firstDayOfYear(new Date()).toLocaleString());
	}
	
	public static int daysBetween(Date date1,Date date2)  

    {  
        Calendar cal = Calendar.getInstance();  

        cal.setTime(date1);  

        long time1 = cal.getTimeInMillis();               

        cal.setTime(date2);  

        long time2 = cal.getTimeInMillis();       

        long between_days=(time2-time1)/(1000*3600*24);  

       return Integer.parseInt(String.valueOf(between_days));         

    }  
}