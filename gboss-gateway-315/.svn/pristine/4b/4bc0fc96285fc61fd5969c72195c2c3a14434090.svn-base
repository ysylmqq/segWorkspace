package cc.chinagps.gateway.util;

/**
 * π§æﬂ¿‡
 * @author Arvin
 * 2011-11-28
 */
public class HexUtil {
	private static String clearBlank(String str){
		StringBuilder s = new StringBuilder(str);
		for(int i = 0; i < s.length(); i++){
			if(s.charAt(i) == ' '){
				s.deleteCharAt(i);
				i--;
			}
		}
		
		return s.toString();
	}
	
	/**
	 * "5B" --> "["
	 */
	public static String hexToString(String hex){
		byte[] bs = hexToBytes(hex);
		
		return new String(bs);
	}
	
	/**
	 * "[" --> "5B"
	 */
	public static String stringToHexString(String str){
		StringBuilder sb = new StringBuilder(str.length() * 2);
		for(int i = 0; i < str.length(); i++){
			int v = str.charAt(i);
			sb.append(Integer.toHexString(v));
		}
		
		return sb.toString().toUpperCase();
	}
	
	/**
	 * "5B5D"  --> [0x5B, 0x5D]
	 */
	public static byte[] hexToBytes(String hex){
		hex = clearBlank(hex);
		int len = hex.length() / 2;
		byte[] bs = new byte[len];
		for(int i = 0; i < len; i++){
			String sub = hex.substring(2 * i, 2 * i + 2);
			int v = Integer.valueOf(sub, 16);
			bs[i] = (byte) v;
		}
		
		return bs;
	}
	
	public static int[] hexToIntArr(String hex){
		hex = clearBlank(hex);
		int len = hex.length() / 2;
		int[] bs = new int[len];
		for(int i = 0; i < len; i++){
			String sub = hex.substring(2 * i, 2 * i + 2);
			int v = Integer.valueOf(sub, 16);
			bs[i] = v;
		}
		return bs;
	}
	
	public static String byteToHexStr(byte[] bs){
		return byteToHexStr(bs, bs.length);
	}
	
	public static String byteToHexStr(byte[] bs, int len){
		StringBuilder sb = new StringBuilder(len * 2);
		for(int i = 0; i < len; i++){
			byte b = bs[i];
			String str = Integer.toHexString(b);
			if(str.length() == 1){
				sb.append("0").append(str);
			}else if(str.length() > 2){
				sb.append(str.substring(str.length() - 2));
			}else{
				sb.append(str);
			}
		}
		
		return sb.toString();
	}
	
	public static boolean equals(byte[] bs1, byte[] bs2){
		if(bs1.length != bs2.length){
			return false;
		}
		
		for(int i = 0; i < bs1.length; i++){
			if(bs1[i] != bs2[i]){
				return false;
			}
		}
		
		return true;
	}
	
	public static String toHexStr(byte b){
		String str = Integer.toHexString(b);
		if(str.length() == 1){
			return "0" + str;
		}
		
		if(str.length() > 2){
			return str.substring(str.length() - 2);
		}

		return str;
	}
	
	public static String toHexStr(short v){
		byte[] bs = new byte[2];
		bs[0] = (byte) ((v >>>  8) & 0xFF);
		bs[1] = (byte) ((v >>>  0) & 0xFF);

		return byteToHexStr(bs);
	}
	
	public static String toHexStr(int v){
		byte[] bs = new byte[4];
		bs[0] = (byte) ((v >>> 24) & 0xFF);
		bs[1] = (byte) ((v >>> 16) & 0xFF);
		bs[2] = (byte) ((v >>>  8) & 0xFF);
		bs[3] = (byte) ((v >>>  0) & 0xFF);

		return byteToHexStr(bs);
	}
	
	public static boolean isOneByteBinaryString(String str){
		if(str.length() != 8){
			return false;
		}
		
		for(int i = 0; i < str.length(); i++){
			char c = str.charAt(i);
			if(c != '0' && c != '1'){
				return false;
			}
		}
		
		return true;
	}
	
	public static String byteToString(byte b){
		String v = Integer.toBinaryString(b);
		if(v.length() > 8){
			v = v.substring(v.length() - 8);
		}
		
		return get8str(v);
	}
	
	private static String get8str(String info){
		int len = info.length();
		if(len < 8){
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < 8 - len; i++){
				sb.append("0");
			}
			
			info = sb.toString() + info;
		}
		
		return info;
	}
	
	//025857 A 22 32.0729 N 113 56.0030 E 000.0 21 091101 00000000
	//28     12 0C 1E 1E  15 20 07 1D   71  38 00 1D 10  51         35     0E 00 00 00 00
	//2005-8-18 12:30:30  21^32.07 29   113^56.00 29 160 150.01+0.5556 
	public static void main(String[] args) {
		String str = "00000000";
		System.out.println(isOneByteBinaryString(str));
	}
}