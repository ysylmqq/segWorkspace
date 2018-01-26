package cc.chinagps.gateway.unit.beforemarket.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cc.chinagps.gateway.unit.UnitSocketSession;

public class BeforeMarketPkgUtil {
	public static final String EN_CHAR_ENCODING = "ascii";
	
	public static final byte START_FLAG = 0x7D;
	
	private static final byte flagD = START_FLAG;
	private static final byte flagE = 0x7E;
	
	private static byte MASK_BYTE = 0x7C;

	private static final byte ESCAPE_CODE_D = (byte) (flagD ^ MASK_BYTE);
	private static final byte ESCAPE_CODE_E = (byte) (flagE ^ MASK_BYTE);
	
	private static boolean isEscapeCode(byte c){
		return (c == ESCAPE_CODE_D || c == ESCAPE_CODE_E);
	}
	
	/**
	 * 反转义
	 */
	public static byte[] unescape(byte[] data){
		List<Integer> positions = new ArrayList<Integer>();
		for(int i = 1; i < data.length - 1; i++){
			if(data[i] == flagE && isEscapeCode(data[i + 1])){
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
			if(data[i] == flagD || data[i] == flagE){
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
			copy[tarPos] = flagE;
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
	 * 加密 解密
	 */
	public static byte[] encrypt(int c1, int d1, int d2, byte[] buff, int offset, int length){
		for(int i = offset; i < offset + length; i++){
			d2 = (int) (((c1 * d2) & 0xFFFFFFFFL) % (d1 & 0xFFFFFFFFL));
			buff[i] ^= (d2 & 0xFF);
		}
		
		return buff;
	}
	
	/**
	 * 加密 解密
	 */
	public static byte[] encrypt(int c1, int d1, int d2, byte[] buff){
		return encrypt(c1, d1, d2, buff, 0, buff.length);
	}
	
	/**
	 * 获取D1(随机数)
	 */
	private static Random random = new Random();
	
	public static int getRandomD1(){
		return random.nextInt();
	}
	
	/**
	 * 获取指令sn
	 */
	public static short getSn(UnitSocketSession unitSession){
		synchronized (unitSession) {
			Object obj = unitSession.getAttribute(BeforeMarketConstants.BM_SESSION_KEY_SN);
			Short v;
			if(obj == null){
				v = 1;
			}else{
				v = (Short) obj;
				v++;
			}
			
			unitSession.setAttribute(BeforeMarketConstants.BM_SESSION_KEY_SN, v);
			return v;
		}
	}
	
	/**
	 * 获取加解密参数C1
	 */
	public static int getC1(UnitSocketSession session){
		Object attr = session.getAttribute(BeforeMarketConstants.BM_SESSION_KEY_ENCRYPT_C1);
		if(attr == null){
			return 0x73656778;
		}
		
		return (Integer) attr;
	}
	
	/**
	 * 获取加解密参数D1
	 */
	public static int getD1(UnitSocketSession session){
		Object attr = session.getAttribute(BeforeMarketConstants.BM_SESSION_KEY_ENCRYPT_D1);
		if(attr == null){
			return 0x5354475A;
		}
		
		return (Integer) attr;
	}
	
	/**
	 * 获取下发指令缓存sn
	 */
	public static String getCmdCacheSn(String callLetter, short headSN){
		return callLetter + "_" + headSN;
	}
	
	/*
	private static final byte VERSION = 0x10;
	private static final byte TERMINAL_TYPE = 0x1;
	
	public static byte[] encode(boolean isCompress, boolean isEncrypt, int c1, int d1, short sn, String imei, byte msgType, byte[] body) {
		byte[] b_body = body;
		//压缩
		if(isCompress){
			b_body = Util.ZlibCompress(b_body);
		}
		
		//加密
		if(isEncrypt){
			int d2 = (sn << 16) | (b_body.length & 0xFFFF);
			b_body = encrypt(c1, d1, d2, b_body);
		}
		
		HMPackageHead head = new HMPackageHead();
		head.setCompress(isCompress);
		head.setEncrypt(isEncrypt);
		head.setVersion(VERSION);
		head.setTerminalType(TERMINAL_TYPE);
		head.setTerminalId(imei);
		head.setMsgType(msgType);
		head.setSn(sn);
		head.setBodyLength(b_body.length);
		byte[] b_head = head.encode();
		
		byte[] to_escape = new byte[b_head.length + b_body.length + 2];
		System.arraycopy(b_head, 0, to_escape, 0, b_head.length);
		System.arraycopy(b_body, 0, to_escape,  b_head.length, b_body.length);
		
		//CRC16
		int crc16 = CRC16.crc16(to_escape, 0, b_head.length + b_body.length);
		byte[] b_crc16 = Util.getShortByte((short) crc16);
		System.arraycopy(b_crc16, 0, to_escape, b_head.length + b_body.length, 2);
		
		//转义
		byte[] escaped = escape(to_escape);
		byte[] to_send = new byte[escaped.length + 2];
		System.arraycopy(escaped, 0, to_send, 1, escaped.length);
		to_send[0] = START_FLAG;
		to_send[to_send.length - 1] = START_FLAG;
		
		return to_send;
	}
	*/
}