package ldap.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
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
public class DateUtils {

	public static final String YMD = "yyyyMMdd";
	public static final String YMD_SLASH = "yyyy/MM/dd";
	public static final String YMD_DASH = "yyyy-MM-dd";
	public static final String YMD_DASH_WITH_TIME = "yyyy-MM-dd H:m";
	public static final String YMD_DASH_WITH_FULLTIME = "yyyy-MM-dd H:m:s";
	public static final String YMD_DASH_WITH_FULLTIME24 = "yyyy-MM-dd HH:mm:ss";
	public static final String YDM_SLASH = "yyyy/dd/MM";
	public static final String YDM_DASH = "yyyy-dd-MM";
	public static final String FORMAT_YEAR_CN = "yyyy年";
	public static final String FORMAT_MONTH_CN = "yyyy年MM月";
	public static final String FORMAT_DAY_CN = "yyyy年MM月dd日";
	public static final String HM = "HHmm";
	public static final String HM_COLON = "HH:mm";
	public static final long DAY = 24 * 60 * 60 * 1000L;

	private static final Map<String, DateFormat> DFS = new HashMap<String, DateFormat>();

	private static final Log log = LogFactory.getLog(DateUtils.class);

	private DateUtils() {
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

	public static String parseLog(String source) {
		
		String result = source.substring(0, source.indexOf("."));
		return result;
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

	public static String format(Date date) {
		return format(date, YMD_DASH_WITH_FULLTIME);
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
		if (month > 0 && month <= 12 && day > 0 && day < 32) {
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

	public static Date getDate(Date date) {

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

	public static long secondeDiff(Date date1, Date date2) {
		long diff = date1.getTime() - date2.getTime();
		return diff / 1000L;
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

	public static Date getFormalMonth() {
		// Date date =new Date();
		Calendar cd = Calendar.getInstance();
		// cd.setTime(date);
		cd.add(Calendar.MONTH, -1);
		return cd.getTime();
	}

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat format =   new SimpleDateFormat( "yyyy-MM-dd" );
		Date start = format.parse( "2012-05-12 00:00:00" );
		Date end = format.parse( "2111-01-01" );
	}
	
	public static Calendar parseDateToCalendar(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}
	public static List getAmong(Calendar _begin,Calendar _end,String format){
		List result = new ArrayList();
		while (!_begin.after(_end)) {
			result.add(parseDateToString(_begin, format));
			if(FORMAT_YEAR_CN.equals(format)){
				_begin.add(Calendar.YEAR, 1);
			}else if(FORMAT_MONTH_CN.equals(format)){
				_begin.add(Calendar.MONTH, 1);
			}else if(FORMAT_DAY_CN.equals(format)){
				_begin.add(Calendar.DATE, 1);
			}
		}
		if(FORMAT_MONTH_CN.equals(format)){
			result.add(parseDateToString(_end, format));
		}
		return result;
	}
	
	public static List<String> getAmongWeek(Date start,Date end){
		int startInt = getWeekOfYear(start);
		int endInt = getWeekOfYear(end);
		List<String> result = new ArrayList<String>();
		for(int i=startInt;i<=endInt;i++){
			result.add("" + i);
		}
		return result;
	}
	
	public static List getAmong(Date start,Date end,String format){
		return getAmong(parseDateToCalendar(start), parseDateToCalendar(end), format);
	}
	
	public static String parseDateToString(Calendar date, String parseFormat) {
		return new  SimpleDateFormat(parseFormat).format(date.getTime());
	}
	/**
	 * 
	 * getZeroHHZeroMMZeroSS(得到当天零时零分零秒的时间戳)
	 * @Description: TODO
	 * @param @return    设定文件
	 * @return Calendar    返回类型
	 * @throws
	 */
	public static Calendar getZeroHHZeroMMZeroSS() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		return cal;
	}
	public static String parseDateToString(Date date,String parseFormat){
		return parseDateToString(parseDateToCalendar(date), parseFormat);
	}
	public static int getWeekOfYear(Date date){
		try {  

	         Calendar cal = Calendar.getInstance();  

	         cal.setFirstDayOfWeek(Calendar.MONDAY); // 设置每周的第一天为星期一  

	         //cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// 每周从周一开始  

	         cal.setMinimalDaysInFirstWeek(1); // 设置每周最少为1天  

	         cal.setTime(date);
	         
	         return cal.get(Calendar.WEEK_OF_YEAR);  

	     } catch (Exception e) {  

	         e.printStackTrace();  

	     }  

	     return 0;  
	}
	 /**  

	  * 根据日期计算属于第几周  

	  * @param date 格式 yyyy-MM-dd  

	  * @throws ParseException  

	  */ 

	 public static int getWeekOfYear(String date){  
   

     try {  

         SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  


         return getWeekOfYear(df.parse(date)); 

     } catch (Exception e) {  

         e.printStackTrace();  

     }  

     return 0;  

 } 
public static int getYear(Date date){
	return parseDateToCalendar(date).get(Calendar.YEAR);
}
public static int getMonth(Date date){
	return parseDateToCalendar(date).get(Calendar.MONTH) + 1;
}

public static int getDay(Date date){
	return parseDateToCalendar(date).get(Calendar.DAY_OF_MONTH);
}

	
}