package cc.chinagps.gateway.unit.kelong.util;

import java.io.UnsupportedEncodingException;

public class KeLongParseUtil {
	public static String getString(byte[] data, int offset, int length, String charset)
			throws UnsupportedEncodingException {
		return new String(data, offset, length, charset);
	}

	public static int getIntFrom3Byte(byte[] data, int start) {

		int a1 = data[start] & 0xff;
		int a2 = data[start + 1] & 0xff;
		int a3 = data[start + 2] & 0xff;

		return (a1 << 16) + (a2 << 8) + (a3 << 0);
	}
}
