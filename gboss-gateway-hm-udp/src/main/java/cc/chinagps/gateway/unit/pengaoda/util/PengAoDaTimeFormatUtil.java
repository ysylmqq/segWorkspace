package cc.chinagps.gateway.unit.pengaoda.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class PengAoDaTimeFormatUtil {
	private static final TimeZone gmt_timezone;
	
	private static final SimpleDateFormat sdf_gmt;
	
	private static final SimpleDateFormat sdf_gmt_full;
	
	static{
		gmt_timezone = TimeZone.getTimeZone("GMT");
		
		sdf_gmt = new SimpleDateFormat("yyyyddMM HHmmss");
		sdf_gmt.setTimeZone(gmt_timezone);
		
		sdf_gmt_full = new SimpleDateFormat("yyyyMMddHHmmss");
		sdf_gmt_full.setTimeZone(gmt_timezone);
	}
	
	public static Date parseGMT(String time) throws ParseException{
		synchronized (sdf_gmt) {
			return sdf_gmt.parse(time);
		}
	}
	
	public static String getTime(long time){
		synchronized (sdf_gmt_full) {
			return sdf_gmt_full.format(time);
		}
	}
}
