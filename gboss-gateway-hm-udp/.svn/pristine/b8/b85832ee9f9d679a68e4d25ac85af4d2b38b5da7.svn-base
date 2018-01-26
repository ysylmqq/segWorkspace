package cc.chinagps.gateway.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class Util {
	/**
	 * 加载属性文件
	 */
	public static Properties loadProperties(String fileName) throws IOException{
		Properties properties = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream(fileName);
			properties.load(is);
			return properties;
		}finally{
			IOUtil.closeIS(is);
		}
	}
	
	public static String getMapInfo(Map<String, Object> map){
		if(map == null){
			return "null";
		}
		
		StringBuilder sb = new StringBuilder();
		Iterator<Entry<String, Object>> it = map.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, Object> entry = it.next();
			sb.append(getString(entry.getKey())).append("=").append(getString(entry.getValue())).append("|");
		}
		
		return sb.toString();
	}
	
	private static String getString(Object v){
		if(v == null){
			return null;
		}
		
		return v.toString().replaceAll("\\s", "_");
	}
	
	public static String getIP(byte[] data, int offset){
		StringBuilder sb = new StringBuilder();
		for(int i = offset; i < offset + 4; i++){
			int v = data[i] & 0xFF;
			sb.append(v).append(".");
		}
		
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
	
	/**
	 * 16进制故障码转成字符串
	 * 000A00 -> P000A00
	 */
	private static String[] FAULT_CODE_HEADS = {"P0", "P1", "P2", "P3", 
		"C0", "C1", "C2", "C3", 
		"B0", "B1", "B2", "B3", 
		"U0", "U1", "U2", "U3", };
	
	public static String getFaultCodeString(byte[] data, int offset, int length){
		int idx = (data[offset] >> 4) & 0xF;
		String head = FAULT_CODE_HEADS[idx];
		String bcd = org.seg.lib.util.Util.bcd2str(data, offset, length);
		
		return head + bcd.substring(1).toUpperCase();
	}
	
	/**
	 * 获取文件CRC16值
	 * @throws IOException 
	 */
	public static int getCRC16File(File file) throws IOException{
		CRC16Flow crc16Flow = new CRC16Flow();
		FileInputStream fis = null;
		try{
			fis = new FileInputStream(file);
			byte[] buff = new byte[1024];
			int len;
			
			while((len = fis.read(buff)) != -1){
				crc16Flow.update(buff, 0, len);
			}
			
			int crc16 = crc16Flow.getValue();
			return crc16;
		}finally{
			IOUtil.closeIS(fis);
		}
	}
	
	/**
	 * IP(格式:127.0.0.1)转成字节数组
	 * [0x7F, 0, 0, 1]
	 */
	public static byte[] getIPBytes(String sip){
		String[] sip_a = sip.split("\\.");
		byte[] bs = new byte[4];
		for(int i = 0 ; i < 4; i++){
			int v = Integer.valueOf(sip_a[i]);
			bs[i] = (byte) v;
		}
		
		return bs;
	}
	
	public static int getIPInt(String sip){
		byte[] bs = getIPBytes(sip);
		return org.seg.lib.util.Util.getInt(bs);
	}
	
	public static int booleaToInt(boolean b){
		return b? 1: 0;
	}
	
	public static String getDateTime(byte[] data, int offset, int len) {
		if (len != 6)
			return null;
		if (data.length < offset + len)
			return null;
		String s = "";
		for (int i = 0; i < len; i++) {
			s += getString(data[offset + i]);
		}

		return s;
	}
	
	private static String getString(int i) {
		String str = "";
		if (i < 10)
			str = "0" + i;
		else {
			str = "" + i;
		}
		return str;
	}
	
	public static int getShort(byte[] buff, int start) {
		return org.seg.lib.util.Util.getShort(buff, start)&0xFFFF;
	}
	
	public static int getInt(byte[] buff, int start){
		return org.seg.lib.util.Util.getInt(buff, start);
	}
	
	public static void main(String[] args) {
		byte[] faultData = {(byte) 0x11, 0x31};
		String str = getFaultCodeString(faultData, 0, 2);
		System.out.println(str);
		String s = "110109110b12";
		byte bb[]  = HexUtil.hexToBytes(s);
		System.out.println(getDateTime(bb, 0, 6));
	}
}
