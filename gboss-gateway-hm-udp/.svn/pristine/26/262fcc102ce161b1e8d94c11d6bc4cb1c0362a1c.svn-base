package cc.chinagps.gateway.unit.db44.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DB44TimeFormatUtil {
	private static final TimeZone gmt8_timezone;
	private static final SimpleDateFormat sdf_gmt8;
	
	private static final SimpleDateFormat sdf_gmt8_user;
	private static final SimpleDateFormat sdf_gmt8_bcd;
	
	static{
		gmt8_timezone = TimeZone.getTimeZone("GMT+8");
		sdf_gmt8 = new SimpleDateFormat("yyyyMMddHHmmss");
		sdf_gmt8.setTimeZone(gmt8_timezone);
		
		sdf_gmt8_user = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf_gmt8_user.setTimeZone(gmt8_timezone);
		
		sdf_gmt8_bcd = new SimpleDateFormat("yyMMddHHmmss");
		sdf_gmt8_bcd.setTimeZone(gmt8_timezone);
	}
	
	public static Date parseGMT8(String time) throws ParseException{
		synchronized (sdf_gmt8) {
			return sdf_gmt8.parse(time);
		}
	}
	
	/**
	 * 2013-01-02 05:06:07
	 * ==>130102050607
	 * @throws ParseException 
	 */
	public static synchronized String userTimeToBCDStr(String time) throws ParseException{
		Date date = sdf_gmt8_user.parse(time);
		return sdf_gmt8_bcd.format(date);
	}
	
	public static void main(String[] args) {
		String time = "2013-11-12 17:25:30";
		try {
			System.out.println(userTimeToBCDStr(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
