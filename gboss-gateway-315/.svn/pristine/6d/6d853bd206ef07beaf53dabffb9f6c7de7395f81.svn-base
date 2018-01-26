package cc.chinagps.gateway.unit.kelong.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.util.HexUtil;

public class KeLongTimeFormatUtil {
	private static final TimeZone gmt0_timezone;
	private static final TimeZone gmt8_timezone;

	private static final SimpleDateFormat sdf_gmt0_bcd;
	private static final SimpleDateFormat sdf_gmt8_bcd;
	private static final SimpleDateFormat sdf_gmt0_full;
	private static final SimpleDateFormat sdf_gmt8_full;

	static {
		gmt0_timezone = TimeZone.getTimeZone("GMT");
		gmt8_timezone = TimeZone.getTimeZone("GMT+8");

		sdf_gmt0_bcd = new SimpleDateFormat("yyMMddHHmmss");
		sdf_gmt0_bcd.setTimeZone(gmt0_timezone);

		sdf_gmt8_bcd = new SimpleDateFormat("yyMMddHHmmss");
		sdf_gmt8_bcd.setTimeZone(gmt8_timezone);

		sdf_gmt0_full = new SimpleDateFormat("yyyyMMddHHmmss");
		sdf_gmt0_full.setTimeZone(gmt0_timezone);

		sdf_gmt8_full = new SimpleDateFormat("yyyyMMddHHmmss");
		sdf_gmt8_full.setTimeZone(gmt8_timezone);

	}

	public static byte[] getCurrentTimeBCD() throws ParseException {
		synchronized (sdf_gmt0_bcd) {
			String str = sdf_gmt0_bcd.format(System.currentTimeMillis());
			return Util.str2bcd(str);
		}
	}

	public static byte[] getCurrentTimeBCD8() throws ParseException {
		synchronized (sdf_gmt8_bcd) {
			String str = sdf_gmt8_bcd.format(System.currentTimeMillis());
			return Util.str2bcd(str);
		}
	}

	public static byte[] getCurrentTime0() throws ParseException {
		synchronized (sdf_gmt0_bcd) {
			String str = sdf_gmt0_bcd.format(System.currentTimeMillis());
			byte[] time = new byte[6];
			for (int i = 0; i < time.length; i++) {
				time[i] = Byte.valueOf(str.substring(2 * i, 2 * i + 2)).byteValue();
			}
			return time;
		}
	}

	public static Date parseGMT0(String time) throws ParseException {
		synchronized (sdf_gmt0_full) {
			return sdf_gmt0_full.parse(time);
		}
	}

	public static Date parseGMT8(String time) throws ParseException {
		synchronized (sdf_gmt8_full) {
			return sdf_gmt8_full.parse(time);
		}
	}

	public static byte[] getTimeBCD(long time) throws ParseException {
		synchronized (sdf_gmt0_bcd) {
			String str = sdf_gmt0_bcd.format(time);
			return Util.str2bcd(str);
		}
	}

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(System.currentTimeMillis());
		String str = sdf_gmt8_bcd.format(System.currentTimeMillis());
		// 1487838378237
		// 1476963121000
		// str = sdf_gmt8_bcd.format(Long.valueOf(1476963121000L));
		System.out.println(str);

		byte[] bs = Util.str2bcd(str);

		String a_str = Util.bcd2str(bs);

		System.out.println(a_str);
		String newStr = "20" + a_str;

		try {
			Date d = parseGMT8(newStr);
			System.out.println(sdf.format(d));
			System.out.println(HexUtil.byteToHexStr(getCurrentTime0()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
