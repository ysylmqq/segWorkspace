package cc.chinagps.gateway.util;

public class CRC16Flow {
	private static int[] crc_ta_4 = new int[] { /* CRC 半字节余式表 */
		0x0000, 0x1021, 0x2042, 0x3063, 
		0x4084, 0x50a5, 0x60c6, 0x70e7, 
		0x8108, 0x9129, 0xa14a, 0xb16b, 
		0xc18c, 0xd1ad, 0xe1ce, 0xf1ef
	};
	
	private int crc = 0;
	
	public void update(byte[] data){
		update(data, 0, data.length);
	}

	public void update(byte[] data, int offset, int length){
		for (int i = offset; i < offset + length; i++) {
			byte b = data[i];
			int high = (int) ((crc >> 12) & 0xF); // 暂存CRC的高4位
			crc <<= 4;
			crc ^= crc_ta_4[(high ^ (b >> 4)) & 0xF];

			high = (int) ((crc >> 12) & 0xF);
			crc <<= 4;
			crc ^= crc_ta_4[(high ^ b) & 0xF];
		}
	}
	
	public int getValue(){
		return crc & 0xFFFF;
	}
}