package com.hm.bigdata.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

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
	public static final String UTC="UTC";
	public static final String YMD = "yyyyMMdd";
	public static final String YMD_SLASH = "yyyy/MM/dd";
	public static final String YMD_DASH = "yyyy-MM-dd";
	public static final String YM_DASH = "yyyy-MM";
	public static final String YMD_DASH_WITH_TIME = "yyyy-MM-dd H:m";
	public static final String YMD_DASH_WITH_FULLTIME = "yyyy-MM-dd HH:mm:ss";
	public static final String YMD_DASH_WITH_FULLTIME24 = "yyyy-MM-dd HH:mm:ss";
	public static final String YDM_SLASH = "yyyy/dd/MM";
	public static final String YDM_DASH = "yyyy-dd-MM";
	public static final String HM = "HHmm";
	public static final String HM_COLON = "HH:mm";
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
		if (source == null) {
			return null;
		}
		Date date;
		try {
			date = getFormat(pattern).parse(source);
		} catch (ParseException e) {
			if (log.isDebugEnabled()) {
				log.debug(source + " doesn't match " + pattern);
			}
			return null;
		}
		return date;
	}
	public static Date parseAddMonth(String source, String pattern) {
		if (source == null) {
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
		if (source == null) {
			return null;
		}
		String[] t = source.split(" ");

		return t[0];
	}
	public static String format(Date date) {
		if (date == null) {
			return null;
		}
		return getFormat(YMD_DASH_WITH_FULLTIME24).format(date);
	}

	public static String format(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		return getFormat(pattern).format(date);
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
	 * 返回月末最后一天
	 */
	public static String getMaxMonthDate(String date) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(format.parse(date));
		} catch (ParseException e) {
		}
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
        return format.format(calendar.getTime());
    }
	
	/**
	 * 获取上一个月末最后一天
	 */
	public static String getPreMaxMonthDate(String date){
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(format.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.add(Calendar.MONTH, -1);
		date =  format.format(calendar.getTime());
		return getMaxMonthDate(date);
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
	/**
	 * @Title:formatNowTime
	 * @Description:获得当前系统时间 格式如"2013-04-12 12:23:12"
	 * @return
	 * @throws
	 */
	public static String formatNowTime() {
		return getFormat(YMD_DASH_WITH_FULLTIME24).format(new Date());
	}
	
	
	/**
	 * @Title:utc2Local
	 * @Description:UTC转本地时间
	 * @param utcTime
	 * @param utcTimePatten
	 * @param localTimePatten
	 * @return
	 * @throws
	 */
	public static String utc2Local(String utcTime, String utcTimePatten,
			String localTimePatten) {
		SimpleDateFormat utcFormater = new SimpleDateFormat(utcTimePatten);
		utcFormater.setTimeZone(TimeZone.getTimeZone(UTC));
		Date gpsUTCDate = null;
		String localTime=null;
		try {
			gpsUTCDate = utcFormater.parse(utcTime);
			SimpleDateFormat localFormater = new SimpleDateFormat(localTimePatten);
			localFormater.setTimeZone(TimeZone.getDefault());
			localTime = localFormater.format(gpsUTCDate.getTime());
			//System.out.println("utcTime="+utcTime+",localTime="+localTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return localTime;
	}
	
	/**
	 * @Title:utc2Local
	 * @Description:UTC转本地时间
	 * @param utcTime
	 * @param utcTimePatten
	 * @param localTimePatten
	 * @return
	 * @throws
	 */
	public static String utc2Local(Date utcTime, 
			String localTimePatten) {
		SimpleDateFormat localFormater = new SimpleDateFormat(localTimePatten);
		localFormater.setTimeZone(TimeZone.getDefault());
		String localTime = localFormater.format(utcTime.getTime());
		//System.out.println("utcTime="+utcTime+",localTime="+localTime);
		return localTime;
	}

	/**
	 * @Title:local2utc
	 * @Description:本地时间转成UTC时间
	 * @param localTime
	 * @param localTimePatten
	 * @param utcTimePatten
	 * @return
	 * @throws
	 */
	public static String local2utc(String localTime, String localTimePatten,
			String utcTimePatten){
		SimpleDateFormat localFormater = new SimpleDateFormat(localTimePatten);
		localFormater.setTimeZone(TimeZone.getDefault());
		Date localDate = null;
		String utcTime=null;
		try {
			localDate = localFormater.parse(localTime);
			SimpleDateFormat utcFormater = new SimpleDateFormat(utcTimePatten);
			utcFormater.setTimeZone(TimeZone.getTimeZone(UTC));
			utcTime = utcFormater.format(localDate.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//System.out.println("localTime="+localTime+",utcTime="+utcTime);
		return utcTime;
	}
	
	/**
	 * @Title:local2utc
	 * @Description:本地时间转成UTC时间
	 * @param localTime
	 * @param localTimePatten
	 * @param utcTimePatten
	 * @return
	 * @throws
	 */
	public static String local2utc(Date localTime, 
			String utcTimePatten){
		SimpleDateFormat utcFormater = new SimpleDateFormat(utcTimePatten);
		utcFormater.setTimeZone(TimeZone.getTimeZone(UTC));
		String utcTime = utcFormater.format(localTime.getTime());
		//System.out.println("localTime="+localTime+",utcTime="+utcTime);
		return utcTime;
	}
	public static int getmonth(Date date1,Date date2){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date1);
		int year1=calendar.get(Calendar.YEAR);
		int month1=calendar.get(Calendar.MONTH);

		calendar.setTime(date2);
		int year2=calendar.get(Calendar.YEAR);
		int month2=calendar.get(Calendar.MONTH);
		return (year2-year1)*12+month2-month1;  
		}
	/**
	 * @Title:getActualMaximum
	 * @Description:获得某月的最后一天
	 * @param date
	 * @return
	 * @throws
	 */
	public static int getActualMaximum(Date date){
		if(date!=null){
			 Calendar cal = Calendar.getInstance();
		     cal.setTime(date);
		     return cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 结束天数
		}else{
			return 28;
		}
	}
	
	/**
	 * 根据起始日期和间隔时间计算结束日期
	 * 
	 * @param sDate开始时间
	 * 
	 * @param days间隔时间
	 * 
	 * @return 结束时间
	 * */
	public static Date calculateEndDate(Date sDate, int days) {
		// 将开始时间赋给日历实例
		Calendar sCalendar = Calendar.getInstance();
		sCalendar.setTime(sDate);
		// 计算结束时间
		sCalendar.add(Calendar.DATE, days);
		// 返回Date类型结束时间
		return sCalendar.getTime();
	}

	/**
	 * 计算两个日期的时间间隔
	 * 
	 * @param sDate开始时间
	 * 
	 * @param eDate结束时间
	 * 
	 * @param type间隔类型
	 *            ("Y/y"--年 "M/m"--月 "D/d"--日)
	 * 
	 * @return interval时间间隔
	 * */
	public static int calInterval(Date sDate, Date eDate, String type) {
		// 时间间隔，初始为0
		int interval = 0;

		/* 比较两个日期的大小，如果开始日期更大，则交换两个日期 */
		// 标志两个日期是否交换过
		boolean reversed = false;
		if (compareDate(sDate, eDate) > 0) {
			Date dTest = sDate;
			sDate = eDate;
			eDate = dTest;
			// 修改交换标志
			reversed = true;
		}

		/* 将两个日期赋给日历实例，并获取年、月、日相关字段值 */
		Calendar sCalendar = Calendar.getInstance();
		sCalendar.setTime(sDate);
		int sYears = sCalendar.get(Calendar.YEAR);
		int sMonths = sCalendar.get(Calendar.MONTH);
		int sDays = sCalendar.get(Calendar.DAY_OF_YEAR);

		Calendar eCalendar = Calendar.getInstance();
		eCalendar.setTime(eDate);
		int eYears = eCalendar.get(Calendar.YEAR);
		int eMonths = eCalendar.get(Calendar.MONTH);
		int eDays = eCalendar.get(Calendar.DAY_OF_YEAR);

		// 年
		if (cTrim(type).equals("Y") || cTrim(type).equals("y")) {
			interval = eYears - sYears;
			if (eMonths < sMonths) {
				--interval;
			}
		}
		// 月
		else if (cTrim(type).equals("M") || cTrim(type).equals("m")) {
			interval = 12 * (eYears - sYears);
			interval += (eMonths - sMonths);
		}
		// 日
		else if (cTrim(type).equals("D") || cTrim(type).equals("d")) {
			interval = 365 * (eYears - sYears);
			interval += (eDays - sDays);
			// 除去闰年天数
			while (sYears < eYears) {
				if (isLeapYear(sYears)) {
					--interval;
				}
				++sYears;
			}
		}
		// 如果开始日期更大，则返回负值
		if (reversed) {
			interval = -interval;
		}
		// 返回计算结果
		return interval;
	}

	/**
	 * 判定某个年份是否是闰年
	 * 
	 * @param year待判定的年份
	 * 
	 * @return 判定结果
	 * */
	private static boolean isLeapYear(int year) {
		return (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0));
	}

	/**
	 * 
	 * 字符串去除两头空格，如果为空，则返回""，如果不空，则返回该字符串去掉前后空格
	 * 
	 * @param tStr输入字符串
	 * 
	 * @return 如果为空，则返回""，如果不空，则返回该字符串去掉前后空格
	 * 
	 */
	public static String cTrim(String tStr) {
		String ttStr = "";
		if (tStr == null) {
		} else {
			ttStr = tStr.trim();
		}
		return ttStr;
	}

	/**
	 * 比较两个Date类型的日期大小
	 * 
	 * @param sDate开始时间
	 * 
	 * @param eDate结束时间
	 * 
	 * @return result返回结果(0--相同 1--前者大 2--后者大)
	 * */
	public static int compareDate(Date sDate, Date eDate) {
		int result = 0;
		// 将开始时间赋给日历实例
		Calendar sC = Calendar.getInstance();
		sC.setTime(sDate);
		// 将结束时间赋给日历实例
		Calendar eC = Calendar.getInstance();
		eC.setTime(eDate);
		// 比较
		result = sC.compareTo(eC);
		// 返回结果
		return result;
	}
}