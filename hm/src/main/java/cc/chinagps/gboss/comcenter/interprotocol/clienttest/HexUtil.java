package cc.chinagps.gboss.comcenter.interprotocol.clienttest;

/**
 * ������
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
}