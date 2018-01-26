package cc.chinagps.gboss.comcenter.buff;


public class HBaseKeyUtil {
	public static byte[] getKey(String callLetter, long gpsTime){
		long hash1 = BigHash.bigHash.hash(callLetter.getBytes());
		char mod1 = (char) ('0' + (hash1 % 16));
		
		StringBuilder callToHash = new StringBuilder();
		callToHash.append(mod1);
		callToHash.append(callLetter);
		
		long hash2 = BigHash.bigHash.hash(callToHash.toString().getBytes());
		byte mod2 = (byte) ('0' + (hash2 % 64));
		
		byte[] bscall = getLongByte(Long.valueOf(callLetter));
		byte[] bsTime = getLongByte(Long.MAX_VALUE - gpsTime);
		
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
		
		byte[] bscall = getLongByte(Long.valueOf(callLetter));
		
		byte[] head = new byte[8];
		head[0] = mod2;
		head[1] = (byte) mod1;
		System.arraycopy(bscall, 2, head, 2, 6);
		
		return head;
	}

	private static byte[] getLongByte(long v){
		byte[] bs = new byte[8];
		bs[0] = (byte)(v >>> 56);
		bs[1] = (byte)(v >>> 48);
		bs[2] = (byte)(v >>> 40);
		bs[3] = (byte)(v >>> 32);
		bs[4] = (byte)(v >>> 24);
		bs[5] = (byte)(v >>> 16);
		bs[6] = (byte)(v >>>  8);
		bs[7] = (byte)(v >>>  0);
		
		return bs;
	}
}