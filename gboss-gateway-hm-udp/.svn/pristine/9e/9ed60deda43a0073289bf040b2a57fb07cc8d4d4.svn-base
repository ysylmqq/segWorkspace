package cc.chinagps.gateway.unit.seg.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class SegTimeFormatUtil {
	private static final TimeZone gmt_timezone;
	private static final TimeZone gmt8_timezone;
	
	private static final SimpleDateFormat sdf_gmt;
	private static final SimpleDateFormat sdf_gmt_bcd;
	private static final SimpleDateFormat sdf_gmt8;
	
	static{
		gmt_timezone = TimeZone.getTimeZone("GMT");
		gmt8_timezone = TimeZone.getTimeZone("GMT+8");
		
		sdf_gmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf_gmt_bcd = new SimpleDateFormat("yyyyMMddHHmmss");
		sdf_gmt8 = new SimpleDateFormat("yyMMddHHmmss");
		
		sdf_gmt.setTimeZone(gmt_timezone);
		sdf_gmt_bcd.setTimeZone(gmt_timezone);
		sdf_gmt8.setTimeZone(gmt8_timezone);
	}
	
	public static Date parseGMT(String time) throws ParseException{
		synchronized (sdf_gmt) {
			return sdf_gmt.parse(time);
		}
	}
	
	public static Date parseGMTBCD(String time) throws ParseException{
		synchronized (sdf_gmt_bcd) {
			return sdf_gmt_bcd.parse(time);
		}
	}
	
	public static String getCurrentTimeGMT8(){
		synchronized (sdf_gmt8) {
			return sdf_gmt8.format(System.currentTimeMillis());
		}
	}
	
	public static void main(String[] args) {
		System.out.println(getCurrentTimeGMT8());
	}
}