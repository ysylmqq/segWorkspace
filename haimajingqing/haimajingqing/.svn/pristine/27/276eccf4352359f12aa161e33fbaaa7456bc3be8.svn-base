package cc.chinagps.lib.util;


public class HBaseKeyUtil {
	public static byte[] getKey(String callLetter, long gpsTime){
		long hash1 = BigHash.bigHash.hash(callLetter.getBytes());
		char mod1 = (char) ('0' + (hash1 % 16));
		
		StringBuilder callToHash = new StringBuilder();
		callToHash.append(mod1);
		callToHash.append(callLetter);
		
		long hash2 = BigHash.bigHash.hash(callToHash.toString().getBytes());
		byte mod2 = (byte) ('0' + (hash2 % 64));
		
		byte[] bscall = Util.getLongByte(Long.valueOf(callLetter));
		byte[] bsTime = Util.getLongByte(Long.MAX_VALUE - gpsTime);
		
		byte[] key = new byte[14];
		key[0] = mod2;
		key[1] = (byte) mod1;
		System.arraycopy(bscall, 2, key, 2, 6);
		System.arraycopy(bsTime, 2, key, 8, 6);
		
		return key;
	}
	
	public static byte[] getHead(String callLetter){
		long hash1 = BigHash.bigHash.hash(callLetter.getBytes());
		char mod1 = (char) ('0' + (hash1 % 16));
		
		StringBuilder callToHash = new StringBuilder();
		callToHash.append(mod1);
		callToHash.append(callLetter);
		
		long hash2 = BigHash.bigHash.hash(callToHash.toString().getBytes());
		byte mod2 = (byte) ('0' + (hash2 % 64));
		
		byte[] bscall = Util.getLongByte(Long.valueOf(callLetter));
		
		byte[] head = new byte[8];
		head[0] = mod2;
		head[1] = (byte) mod1;
		System.arraycopy(bscall, 2, head, 2, 6);
		
		return head;
	}

	/*
	 * 为了和第一版KEY分开，至少不同，想
	 */
	/*public static byte[] getNewKey(String callLetter, long gpsTime){
		byte[] bscaller = HexStrToByte(callLetter);
		byte[] key = new byte[bscaller.length + 9];		//第一、二位是Hash值, 第三位是callLetter的长度 + 0x80，后6位是时间
		
		//计算第一次Hash, 这次Hash放在第二位
		long hash1 = BigHash.bigHash.hash(callLetter.getBytes());
		key[1] = (byte) ('0' + (hash1 % 64));
		
		//计算第二次Hash, 这次Hash放在第一位
		StringBuilder callToHash = new StringBuilder();
		callToHash.append((char)(key[1]));
		callToHash.append(callLetter);
		long hash2 = BigHash.bigHash.hash(callToHash.toString().getBytes());
		key[0] = (byte) (0x70 + (hash2 % 64));	//原来是0x30 - 0x69
		
		//第三位，长度（+ 0x80）
		key[2] = (byte)((callLetter.length() + 0x80) & 0xFF);
		//第四位后，呼号
		System.arraycopy(bscaller, 0, key, 3, bscaller.length);
		//最后6位，时间(倒序), 刚开始HBASE不支持逆序查询，查最后位置只好用倒序
		byte[] bsTime = getLongByte(Long.MAX_VALUE - gpsTime);
		System.arraycopy(bsTime, 2, key, bscaller.length + 3, 6);
		return key;
	}*/
	
	public static void main(String[] args) {
		long call0 = 13603025013L;
		long time = System.currentTimeMillis();
		for(int i = 0; i < 10; i++){
			String callLetter = String.valueOf(call0 + i);
			byte[] key = getKey(callLetter, time);
			String strkey = Util.ByteToHexStr(key);
			System.out.println("key : " + strkey);
			byte[] key1 = Util.HexStrToByte(strkey);
			String strkey1 = Util.ByteToHexStr(key1);
			System.out.println("key1: " + strkey1);
		}
	}
}