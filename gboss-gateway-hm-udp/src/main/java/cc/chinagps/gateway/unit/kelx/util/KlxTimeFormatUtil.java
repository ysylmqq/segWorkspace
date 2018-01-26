package cc.chinagps.gateway.unit.kelx.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.util.HexUtil;

public class KlxTimeFormatUtil {
	private static final TimeZone gmt8_timezone;
	
	private static final SimpleDateFormat sdf_gmt8_bcd;
	private static final SimpleDateFormat sdf_gmt8_full;
	
	static{
		gmt8_timezone = TimeZone.getTimeZone("GMT+8");
		
		sdf_gmt8_bcd = new SimpleDateFormat("yyMMddHHmmss");
		sdf_gmt8_bcd.setTimeZone(gmt8_timezone);
		
		sdf_gmt8_full = new SimpleDateFormat("yyyyMMddHHmmss");
		sdf_gmt8_full.setTimeZone(gmt8_timezone);
	}
	
	public static byte[] getCurrentTimeBCD() throws ParseException{
		synchronized (sdf_gmt8_bcd) {
			String str = sdf_gmt8_bcd.format(System.currentTimeMillis());
			return Util.str2bcd(str);
		}
	}
	
	public static Date parseGMT8(String time) throws ParseException{
		synchronized (sdf_gmt8_full) {
			return sdf_gmt8_full.parse(time);
		}
	}
	
	public static byte[] getTimeBCD(long time) throws ParseException{
		synchronized (sdf_gmt8_bcd) {
			String str = sdf_gmt8_bcd.format(time);
			return Util.str2bcd(str);
		}
	}
	
	public static void main(String[] args) throws ParseException {
		byte[] bs = getCurrentTimeBCD();
		System.out.println(HexUtil.byteToHexStr(bs));
	}
}
