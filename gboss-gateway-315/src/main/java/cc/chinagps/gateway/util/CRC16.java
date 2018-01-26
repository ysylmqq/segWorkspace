package cc.chinagps.gateway.util;

public class CRC16 {
	private static int[] crc_ta_4 = new int[] { /* CRC 半字节余式表 */
		0x0000, 0x1021, 0x2042, 0x3063, 
		0x4084, 0x50a5, 0x60c6, 0x70e7, 
		0x8108, 0x9129, 0xa14a, 0xb16b, 
		0xc18c, 0xd1ad, 0xe1ce, 0xf1ef
	};

	/**
	 * 多项式采用CRC-CCITT 0x1021
	 */
	public static int crc16(byte[] bs) {
		return crc16(bs, 0, bs.length);
	}
	
	public static int crc16(byte[] bs, int offset, int len){
		int crc = 0;

		for (int i = offset; i < offset + len; i++) {
			byte b = bs[i];
			int high = (int) ((crc >> 12) & 0xF); // 暂存CRC的高4位
			crc <<= 4;
			crc ^= crc_ta_4[(high ^ (b >> 4)) & 0xF];

			high = (int) ((crc >> 12) & 0xF);
			crc <<= 4;
			crc ^= crc_ta_4[(high ^ b) & 0xF];
		}
		
		return crc & 0xFFFF;
	}

	public static void main(String[] args) {
		byte[] data = new byte[] {0x7E, 0x00, 0x05, 0x60, 0x31, 0x32, 0x33};//
		int crc = crc16(data);
		System.out.println("crc:" + crc + ", hex:" + Integer.toHexString(crc));
	}
}