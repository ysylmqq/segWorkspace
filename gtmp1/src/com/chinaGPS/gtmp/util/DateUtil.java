package com.chinaGPS.gtmp.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

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

//	public static Date fromSegIceDate(SegIceDate sd) {
//		Calendar calendar = new GregorianCalendar();
//		calendar.set(sd.year, sd.month, sd.day, sd.hour, sd.minite, sd.second);
//		return calendar.getTime();
//	}
//
//	public static SegIceDate toSegIceDate(Date date) {
//		Calendar calendar = new GregorianCalendar();
//		calendar.setTime(date);
//		SegIceDate segIceDate = new SegIceDate();
//		segIceDate.year = (short) calendar.get(Calendar.YEAR);
//		segIceDate.month = (short) calendar.get(Calendar.MONTH);
//		segIceDate.day = (short) calendar.get(Calendar.DAY_OF_MONTH);
//		segIceDate.hour = (short) calendar.get(Calendar.HOUR_OF_DAY);
//		segIceDate.minite = (short) calendar.get(Calendar.MINUTE);
//		segIceDate.second = (short) calendar.get(Calendar.SECOND);
//		return segIceDate;
//	}
	public static String parseLog(String source){
		//Date d=parse(source,YMD_DASH_WITH_FULLTIME);
		//String result = parseDateString(d);
		//String source="2011-12-05 10:00:34.0";
			String result = source.substring(0, source.indexOf("."));
			return result;
	}
//	  public static Date iceDateFormated(SEGSYSTEMTIME time){
//			Date lastGpsDate=null;
//			try {
//			  lastGpsDate=getFormat(YMD_DASH_WITH_FULLTIME24).parse(time.nYear+"-"+time.nMonth+"-"+time.nDay+" "+time.nHour+":"+time.nMinute+":"+time.nSecond);
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//	       return lastGpsDate;
//	    	
//	    }
	public static void main(String[] args) {
		System.out.println(addDay(lastDayOfMonth(new Date()), 131));
		String source="2011-12-05 10:00:34.0";
		//parseLog(source);
		System.out.println(parseLog(source));
		//System.out.println(lastDayOfYear(new Date()).toLocaleString());
		//System.out.println(firstDayOfYear(new Date()).toLocaleString());
	}
	public static String renderJson(final Object data) {
		JsonConfig jsonConfig=null;
		jsonConfig = new JsonConfig();
		//jsonConfig.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor(YMD_DASH));
		JSONArray ja=JSONArray.fromObject(data, jsonConfig);
		return ja.toString();
	}
	
	public static LinkedHashSet<String> getAllYearMonthList(String start, String end) {
		LinkedHashSet<String> lhs = new LinkedHashSet<String>();
		if (start.compareTo(end) > 0) {
			String temp = start;
			start = end;
			end = temp;
		}

		String temp = start.replaceAll("-", "");
		String tempEnd = end.replaceAll("-", "");
		while (temp.compareTo(start) >= 0 && temp.compareTo(tempEnd) <= 0) {
			lhs.add(temp.substring(0, 6));
			int year = Integer.valueOf(temp.substring(0, 4));
			int month = Integer.valueOf(temp.substring(4, 6)) + 1;
			if (month > 12) {
				month = 1;
				year++;
			}
			if (month < 10) {
				temp = year + "0" + month;
			} else {
				temp = year + "" + month;
			}
		}
		return lhs;
	}
	
	 public static LinkedHashSet<String> getAllMonthList(String start, String end){
	        String splitSign="-";
	       // String regex="\\d{4}"+splitSign+"(([0][1-9])|([1][012]))"; //�ж�YYYY-MMʱ���ʽ��������ʽ
	       // if(!start.matches(regex) || !end.matches(regex)) return null;
	        
	        LinkedHashSet<String> list=new LinkedHashSet<String>();
	        String endDate=end;
	        if(start.compareTo(end)>0){
	            String temp=start;
	            start=end;
	            end=temp;
	        }
	        String temp=start; 
	        while(temp.compareTo(start)>=0 && temp.compareTo(end)<=0){
	            list.add(temp.substring(temp.lastIndexOf("-")+1)); //���ȼ�����С�·�,���ż�����һ���·�
	            String[] arr=temp.split(splitSign);
	            int year=Integer.valueOf(arr[0]);
	            int month=Integer.valueOf(arr[1])+1;
	            if(month>12){
	                month=1;
	                year++;
	            }
	            if(month<10){
	                temp=year+splitSign+"0"+month;
	            }else{
	                temp=year+splitSign+month;
	            }
	        }
	        
	        return list;
	    }
	 public static  LinkedHashSet getAllDays(String date1, String date2){
	        String tmp;
	        LinkedHashSet arrayList=new LinkedHashSet();
	        if(date1.compareTo(date2) > 0){  //ȷ�� date1�����ڲ�����date2
	            tmp = date1; 
	            date1 = date2;
	            date2 = tmp;
	        }
	        arrayList.add(DateUtil.getDay(str2Date(date1))+"");
	        tmp = format.format(str2Date(date1).getTime() + 3600*24*1000);
	        
	        int num = 0; 
	        System.out.println(tmp);    
	        while(tmp.compareTo(date2) <=0){                    
	            arrayList.add(DateUtil.getDay(str2Date(tmp))+"");
	            num++;
	            tmp = format.format(str2Date(tmp).getTime() + 3600*24*1000);
	        }
	        if(num == 0){
	            System.out.println(tmp); 
	        }
	        return arrayList;
	    }
	 private static Date str2Date(String str) {
	        if (str == null) return null;
	        
	            try {
	                return format.parse(str);
	            } catch (ParseException e) {
	                e.printStackTrace();
	            }
	        return null;
	    }
	 public static int getDay(Date date) {

	        calendar = Calendar.getInstance();

	        calendar.setTime(date);

	        return calendar.get(Calendar.DAY_OF_MONTH);

	    }
}