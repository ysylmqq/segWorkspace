package cc.chinagps.gateway.unit.beforemarket.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.seg.lib.util.Util;

public class BeforeMarketTimeFormatUtil {
	private static final TimeZone gmt0_timezone;
	
	private static final SimpleDateFormat sdf_gmt0_bcd;
	private static final SimpleDateFormat sdf_gmt0_full;
	
	static{
		gmt0_timezone = TimeZone.getTimeZone("GMT");
		
		sdf_gmt0_bcd = new SimpleDateFormat("yyMMddHHmmss");
		sdf_gmt0_bcd.setTimeZone(gmt0_timezone);
		
		sdf_gmt0_full = new SimpleDateFormat("yyyyMMddHHmmss");
		sdf_gmt0_full.setTimeZone(gmt0_timezone);
	}
	
	public static byte[] getCurrentTimeBCD() throws ParseException{
		synchronized (sdf_gmt0_bcd) {
			String str = sdf_gmt0_bcd.format(System.currentTimeMillis());
			return Util.str2bcd(str);
		}
	}
	
	public static Date parseGMT0(String time) throws ParseException{
		synchronized (sdf_gmt0_full) {
			return sdf_gmt0_full.parse(time);
		}
	}
	
	public static byte[] getTimeBCD(long time) throws ParseException{
		synchronized (sdf_gmt0_bcd) {
			String str = sdf_gmt0_bcd.format(time);
			return Util.str2bcd(str);
		}
	}
	
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String str = sdf_gmt0_bcd.format(System.currentTimeMillis());
		byte[] bs = Util.str2bcd(str);
		
		String a_str = Util.bcd2str(bs);
		
		System.out.println(a_str);
		String newStr = "20" + a_str;
		
		try {
			Date d = parseGMT0(newStr);
			System.out.println(sdf.format(d));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
