package cc.chinagps.lib.util;

import java.io.IOException;

public class Base64 {
	private static final char[] CODE_TABLE = new char[]{
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
		'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
		'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
		'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
	};
	
	private static byte getCode(char a) throws IOException{
		for(byte i = 0; i < CODE_TABLE.length; i++){
			if(a == CODE_TABLE[i]){
				return i;
			}
		}
		
		throw new IOException("error base64 code(3):" + Integer.toHexString(a));
	}
	
	private static int getCharLen(String str){
		int sum = 0;
		for(int i = 0; i < str.length(); i++){
			char c = str.charAt(i);
			if(c == '\n' || c == '\r' || c == '='){
				continue;
			}
			
			sum++;
		}
		
		return sum;
	}
	
	public static byte[] decode(String str) throws IOException{
		int charLen = getCharLen(str);
		int left = charLen % 4;
		int tailBytesLen = 0;
		if(left == 1){
			//throw new IOException("error base64 str!");
			tailBytesLen = 1;
		}else if(left == 2 || left == 3){
			tailBytesLen = left - 1;
		}
		
		int byteLen = charLen / 4 * 3 + tailBytesLen;
		byte[] buff = new byte[byteLen];

		int base64codeIndex = 0;
		byte lastCode = 0;
		for(int i = 0; i < str.length(); i++){
			char c = str.charAt(i);
			if(c == '\n' || c == '\r' || c == '='){
				continue;
			}
				
			byte currentCode = getCode(c);
			
			int serno = base64codeIndex / 4;
			int index = base64codeIndex % 4;
			
			switch (index) {
				case 0: {
					buff[3 * serno] = (byte) ((buff[3 * serno] & 0x3) | (currentCode << 2));
					break;
				}
				case 1: {
					buff[3 * serno] = (byte) ((buff[3 * serno] & 0xFC) | ((currentCode >> 4) & 0x3));
					lastCode = currentCode;
					break;
				}
				case 2: {
					buff[3 * serno + 1] = (byte) ((lastCode << 4) | ((currentCode >> 2) & 0xF));
					lastCode = currentCode;
					break;
				}
				default: {
					buff[3 * serno + 2] = (byte) ((lastCode << 6) | (currentCode & 0x3F));
					break;
				}
			}
			
			base64codeIndex ++;
		}
		
		return buff;
	}
	
	public static String encode(byte[] data, boolean ln){
		int tailByteLen = data.length % 3;
		int lnCharLen = 0;
		StringBuilder sb = new StringBuilder();
		byte lastByte = 0;
		char c, c2;
		for(int i = 0; i < data.length; i++){
			int index = i % 3;
			byte currentByte = data[i];
			switch (index) {
				case 0: {
					c = CODE_TABLE[(currentByte >> 2) & 0x3F];
					sb.append(c);
					lnCharLen ++;
					
					lastByte = currentByte;
					break;
				}
				case 1: {
					c = CODE_TABLE[(lastByte << 4 | ((currentByte >> 4) & 0xF)) & 0x3F];
					sb.append(c);
					lnCharLen ++;
					
					lastByte = currentByte;
					break;
				}
				default: {
					c = CODE_TABLE[(lastByte << 2 | ((currentByte >> 6) & 0x3)) & 0x3F];
					sb.append(c);
					lnCharLen ++;
					
					c2 = CODE_TABLE[currentByte & 0x3F];
					sb.append(c2);
					lnCharLen ++;
					break;
				}
			}
			
			if(ln && lnCharLen == 76){
				sb.append("\r\n");
				lnCharLen = 0;
			}
		}
		
		if(tailByteLen != 0){
			if(tailByteLen == 1){
				c = CODE_TABLE[(lastByte << 4) & 0x3F];
			}else{
				c = CODE_TABLE[(lastByte << 2) & 0x3F];
			}
			
			sb.append(c);
			lnCharLen ++;
			if(ln && lnCharLen == 76){
				sb.append("\r\n");
				lnCharLen = 0;
			}
			
			int tailEqualLen = 3 - tailByteLen;
			for(int i = 0; i < tailEqualLen; i++){
				sb.append("=");
			}
		}
		
		return sb.toString();
	}
	
	/*
	public static byte[] base64decode(String str) throws IOException{
		int endLen = getLengthOfEnd(str);
		if(endLen != 0 && endLen != 1 && endLen != 2){
			throw new IOException("error base64 code(endLen)!");
		}
		
		int maxByteLength = str.length() / 4 * 3 - endLen;
		//System.out.println("maxByteLength:" + maxByteLength);
		int malloclength = maxByteLength;
		if(endLen > 0){
			malloclength = maxByteLength + 1;
		}
		byte[] buff = new byte[malloclength];
		int base64codeIndex = 0;
		for(int i = 0; i < str.length() - endLen; i++){
			char c = str.charAt(i);
			if(c == '\n' || c == '\r'){
				continue;
			}
				
			byte code = getCode(c);
			
			int serno = base64codeIndex / 4;
			int index = base64codeIndex % 4;

			switch (index) {
				case 0: {
					buff[3 * serno] = (byte) ((buff[3 * serno] & 0x3) | (code << 2));
					break;
				}
				case 1: {
					buff[3 * serno] = (byte) ((buff[3 * serno] & 0xFC) | ((code >> 4) & 0x3));
					buff[3 * serno + 1] = (byte) ((buff[3 * serno + 1] & 0xF) | ((code & 0xF) << 4));
					break;
				}
				case 2: {
					buff[3 * serno + 1] = (byte) ((buff[3 * serno + 1] & 0xF0) | ((code & 0x3C) >> 2));
					buff[3 * serno + 2] = (byte) ((buff[3 * serno + 2] & 0x3F) | ((code & 3) << 6));
					break;
				}
				default: {
					buff[3 * serno + 2] = (byte) ((buff[3 * serno + 2] & 0xC0) | (code & 0x3F));
					break;
				}
			}
			
			base64codeIndex ++;
		}
		int charLength = base64codeIndex;
		int left = charLength % 4;
		int buffLength = (charLength / 4 * 3) + (left != 0? left - 1: 0);
		if(buffLength != malloclength){
			byte[] data = new byte[buffLength];
			System.arraycopy(buff, 0, data, 0, buffLength);
			return data;
		}
		
		return buff;
	}
	
	private static int getLengthOfEnd(String str){
		int i = str.length() - 1;
		while(i >= 0 && str.charAt(i) == '='){
			i--;
		}
		
		return str.length() - i -1;
	}
	*/
}