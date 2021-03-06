package cc.chinagps.gateway.unit.oem.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.seg.lib.util.Util;

public class OEMLatLngUtil {
	public static final int SCALE = 6;
	public static final BigDecimal D60 = new BigDecimal(60);
	public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

	/**
	 * ����
	 * 
	 * @param data
	 * @param offset
	 * @return
	 */
	public static double getLng(byte[] data, int offset) {
		String str = Util.bcd2str(data, offset, 4);
		String str_d = str.substring(0, 3);
		String str_m_int = str.substring(3, 5);
		String str_m_float = str.substring(5);

		BigDecimal dec_d = new BigDecimal(str_d);
		BigDecimal dec_m = new BigDecimal(str_m_int + "." + str_m_float);
		dec_m = dec_m.divide(D60, SCALE, ROUNDING_MODE);
		dec_d = dec_d.add(dec_m);

		return dec_d.doubleValue();
	}

	public static double getLng(byte[] data) {
		String str = Util.bcd2str(data, 0, 4);
		String str_d = str.substring(0, 3);
		String str_m_int = str.substring(3);

		BigDecimal dec_d = new BigDecimal(str_d + "." + str_m_int);

		return dec_d.doubleValue();
	}

	/**
	 * γ��
	 * 
	 * @param data
	 * @param offset
	 * @return
	 */
	public static double getLat(byte[] data, int offset) {
		String str = Util.bcd2str(data, offset, 4);
		String str_d = str.substring(0, 3);
		String str_m_int = str.substring(3, 5);
		String str_m_float = str.substring(5);

		BigDecimal dec_d = new BigDecimal(str_d);
		BigDecimal dec_m = new BigDecimal(str_m_int + "." + str_m_float);
		dec_m = dec_m.divide(D60, SCALE, ROUNDING_MODE);
		dec_d = dec_d.add(dec_m);

		return dec_d.doubleValue();
	}

	public static double getLat(byte[] data) {
		String str = Util.bcd2str(data, 0, 4);
		String str_d = str.substring(0, 3);
		String str_m_int = str.substring(3);

		BigDecimal dec_d = new BigDecimal(str_d + "." + str_m_int);

		return dec_d.doubleValue();
	}

	public static void main(String[] args) {
		byte[] data_lng = { 0x11, 0x60, 0x40, 0x00 };
		data_lng = new byte[] { 0x13, 0x04, 0x56, 0x08 };
		double lng = getLng(data_lng, 0);
		double lng1 = getLng(data_lng);
		System.out.println(lng);
		System.out.println(lng1);

		byte[] data_lat = { 0x33, 0x32, 0x00, 0x00 };
		double lat = getLat(data_lat, 0);
		System.out.println(lat);
	}
}