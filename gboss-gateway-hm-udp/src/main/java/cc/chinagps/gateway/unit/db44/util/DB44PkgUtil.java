package cc.chinagps.gateway.unit.db44.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cc.chinagps.gateway.unit.db44.pkg.DB44Package;
import cc.chinagps.gateway.util.Base64;
import cc.chinagps.gateway.util.CRC16;
import cc.chinagps.gateway.util.Constants;
import cc.chinagps.gateway.util.HexUtil;

public class DB44PkgUtil {
	public static final String CN_CHAR_ENCODING = "gb2312";
	public static final String EN_CHAR_ENCODING = "ascii";
	
	public static final byte START_FLAG = 0x7E;
	public static final byte FLAG_CODE = 0x47;
	public static final byte END_FLAG = 0x7F;

	private static final byte flagB = START_FLAG;
	private static final byte flagC = 0x7D;
	private static final byte flagD = END_FLAG;
	
	private static byte MASK_BYTE = 0x20;
	
	private static final byte ESCAPE_CODE_B = (byte) (flagB ^ MASK_BYTE);
	private static final byte ESCAPE_CODE_C = (byte) (flagC ^ MASK_BYTE);
	private static final byte ESCAPE_CODE_D = (byte) (flagD ^ MASK_BYTE);
	
	private static boolean isEscapeCode(byte c){
		return (c == ESCAPE_CODE_B || c == ESCAPE_CODE_C || c == ESCAPE_CODE_D);
	}
	
	/**
	 * 反转义
	 */
	public static byte[] unescape(byte[] data){
		List<Integer> positions = new ArrayList<Integer>();
		for(int i = 1; i < data.length - 1; i++){
			if(data[i] == flagC && isEscapeCode(data[i + 1])){
				positions.add(i);
			}
		}
		
		if(positions.size() == 0){
			return data;
		}
		
		byte[] copy = new byte[data.length - positions.size()];
		
		int srcPos = 0;
		int tarPos = 0;
		int copyLength;
		for(int i = 0; i < positions.size(); i++){
			int uespos = positions.get(i);
			copyLength = uespos - srcPos;
			System.arraycopy(data, srcPos, copy, tarPos, copyLength);
			
			srcPos = uespos + 2;
			tarPos += copyLength;
			copy[tarPos] = (byte) (MASK_BYTE ^ data[uespos + 1]);
			tarPos += 1;
		}
		
		if(srcPos < data.length){
			copyLength = data.length - srcPos;
			System.arraycopy(data, srcPos, copy, tarPos, copyLength);
		}
		
		return copy;
	}
	
	/**
	 * 转义
	 */
	public static byte[] escape(byte[] data){
		List<Integer> positions = new ArrayList<Integer>();
		for(int i = 0; i < data.length; i++){
			if(data[i] == flagB || data[i] == flagC || data[i] == flagD){
				positions.add(i);
			}
		}
		
		if(positions.size() == 0){
			return data;
		}
		
		byte[] copy = new byte[data.length + positions.size()];
		int srcPos = 0;
		int tarPos = 0;
		int copyLength;
		for(int i = 0; i < positions.size(); i++){
			int espos = positions.get(i);
			copyLength = espos - srcPos;
			System.arraycopy(data, srcPos, copy, tarPos, copyLength);
			
			srcPos = espos + 1;
			tarPos += copyLength;
			copy[tarPos] = flagC;
			copy[tarPos + 1] = (byte) (MASK_BYTE ^ data[espos]);
			tarPos += 2;
		}
		
		if(srcPos < data.length){
			copyLength = data.length - srcPos;
			System.arraycopy(data, srcPos, copy, tarPos, copyLength);
		}
		
		return copy;
	}
	
	
	/**
	 * 协议加解密
	 */
	private static final int M1 = 0xFAFAFAFA;
	private static final int IA1 = 0xF7F7F7F7;
	private static final int IC1 = 0xF5F5F5F5;
	private static final int key = 0x41424344;
	
	public static byte[] db44encodeCommon(byte[] buff, int offset, int length){
		return DB44PkgUtil.db44encode(M1, IA1, IC1, key, buff, offset, length);
	}
	
	public static byte[] db44encode(int M1, int IA1, int IC1, int key, byte[] buff){
		return db44encode(M1, IA1, IC1, key, buff, 0, buff.length);
	}
	
	public static byte[] db44encode(int M1, int IA1, int IC1, int key, byte[] buff, int offset, int length){
		if(key == 0){
			key = 1;
		}
		
		for(int i = offset; i < offset + length; i++){
			key = (int) (IA1 * ((key & 0xFFFFFFFFL) % (M1 & 0xFFFFFFFFL)) + IC1);
			buff[i] ^= ((key>>20) & 0xFF);
		}
		
		return buff;
	}
	
	/**
	 * 组包下发数据
	 * @throws IOException 
	 */
	private static final short companyCode = 0x5347;	//SG
	private static final int terminalId = 0;
	
	/**
	 * protocolIdNumber: 协议号
	 * protocol: 协议内容
	 */
	public static byte[] encode(byte protocolIdNumber, byte[] protocol) throws IOException{
		return encode((byte) 0, protocolIdNumber, protocol);
	}
	
	/**
	 * protocolIdNumber: 协议号
	 * loop: 指令循环码
	 * protocol: 协议内容
	 */
	public static byte[] encode(byte loop, byte protocolIdNumber, byte[] protocol) throws IOException{
		return encode(loop, companyCode, terminalId, 
				Constants.SYSTEM_ID_INT, key, (byte) 'D', protocolIdNumber, protocol);
	}
	
	/**
	 * 组包下发数据
	 * @throws IOException 
	 */
	public static byte[] encode(byte loop , short companyCode, int terminalId, 
			int centerId, int password, byte protocolIdMode, 
			byte protocolIdNumber, byte[] protocol) throws IOException{
		byte[] noHeadTail = new byte[22 + protocol.length];
		noHeadTail[0] = FLAG_CODE;
		noHeadTail[1] = loop;
		copyShort(noHeadTail, 2, companyCode);
		copyInt(noHeadTail, 4, terminalId);
		copyInt(noHeadTail, 8, centerId);
		copyInt(noHeadTail, 12, password);

		int protocolLength = protocol.length + 2;
		copyShort(noHeadTail, 16, protocolLength);
		
		byte[] toEncode = new byte[protocol.length + 2];
		toEncode[0] = protocolIdMode;
		toEncode[1] = protocolIdNumber;
		System.arraycopy(protocol, 0, toEncode, 2, protocol.length);
		
		//加密
		byte[] encodeProtocol  = db44encodeCommon(toEncode, 0, toEncode.length);
		System.arraycopy(encodeProtocol, 0, noHeadTail, 18, encodeProtocol.length);
		//CRC
		int crc = CRC16.crc16(noHeadTail, 0, 18 + encodeProtocol.length);
		copyShort(noHeadTail, 18 + encodeProtocol.length, crc);
		//转义
		byte[] es = escape(noHeadTail);
		//BASE64
		String str = Base64.encode(es, false);
		byte[] body = str.getBytes(EN_CHAR_ENCODING);
		//包头包尾
		byte[] all = new byte[body.length + 2];
		all[0] = START_FLAG;
		System.arraycopy(body, 0, all, 1, body.length);
		all[all.length - 1] = END_FLAG;
		
		return all;
	}
	
	public static void copyInt(byte[] data, int offset, int v){
		data[offset] = (byte) (v >> 24);
		data[offset + 1] = (byte) (v >> 16);
		data[offset + 2] = (byte) (v >> 8);
		data[offset + 3] = (byte) v;
	}
	
	public static void copyShort(byte[] data, int offset, int v){
		data[offset] = (byte) (v >> 8);
		data[offset + 1] = (byte) v;
	}
	
	/**
	 * 从第一个不是0的地方开始读
	 */
	public static String readString(byte[] data, int offset, int length) throws IOException{
		int nonezeroOffset = -1;
		for(int i = offset; i < offset + length; i++){
			if(data[i] != 0){
				nonezeroOffset = i;
				break;
			}
		}
		
		if(nonezeroOffset == -1){
			return null;
		}
		
		int len = length - (nonezeroOffset - offset);
		return new String(data, nonezeroOffset, len, EN_CHAR_ENCODING);
	}
	
	/**
	 * 从第一个不是0xFF的地方开始读
	 */
	public static String readSegString(byte[] data, int offset, int length) throws IOException{
		int nonezeroOffset = -1;
		for(int i = offset; i < offset + length; i++){
			if(data[i] != 0xFF){
				nonezeroOffset = i;
				break;
			}
		}
		
		if(nonezeroOffset == -1){
			return null;
		}
		
		int len = length - (nonezeroOffset - offset);
		return new String(data, nonezeroOffset, len, EN_CHAR_ENCODING);
	}
	
	public static void main(String[] args) throws Exception {
		//协议打包
//		byte[] protocol = new byte[]{0xA, 0xB, 0xC, 0xD, 0xE, 0xF};
//		try {
//			byte[] source = encode((byte) 1, protocol);
//			System.out.println("source:" + Util.byteToHexStr(source));
//			
//			DB44Package pkg = DB44Package.parse(source);
//			System.out.println("pkg:" + pkg);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
//		byte[] data = new byte[45];
//		for(int i = 0; i < 30; i++){
//			data[i] = 0x1A;
//		}
//		
		//F6登录
//		byte[] call = "13912345001".getBytes();
//		System.arraycopy(call, 0, data, data.length - call.length, call.length);
//		System.out.println(Util.byteToHexStr(data));
//		
//		try {
//			String str = readString(data, 30, 15);
//			System.out.println("str:" + str);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		//80登录
//		String hex = "7E52785157445177576B5459414141414951554A44524141727330416B5A726E555432346A582F6B347450314E6359672F567A643863637932786562446B4D626C4A695A73554470324C65335366563775746351594F773D3D7F";
//		byte[] bs = Util.hexToBytes(hex);
//		DB44Package pkg = DB44Package.parse(bs);
//		byte[] protocol = pkg.getProtocol();
//		DB44GPSInfo gps = DB44GPSInfo.parse(protocol, 11);
//		System.out.println("pkg:" + pkg);
//		System.out.println("gps:" + gps);
		
		test();
	}
	
	public static void test(){
		String hex = "7e5277425452774141414141414141414351554a44524141436f6b436467513d3d7f";
		byte[] bs = HexUtil.hexToBytes(hex);
		try {
			DB44Package pkg = DB44Package.parse(bs);
			System.out.println("pkg:" + pkg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}