package cc.chinagps.gateway.unit.seg.pkg;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cc.chinagps.gateway.unit.seg.util.SegPkgUtil;
import cc.chinagps.gateway.util.HexUtil;

public class SegPackage {
	private byte start;
	
	private byte type;
	
	private byte[] serialNumberBytes;
	
	public byte[] getSerialNumberBytes() {
		return serialNumberBytes;
	}

	public void setSerialNumberBytes(byte[] serialNumberBytes) {
		this.serialNumberBytes = serialNumberBytes;
	}

	private String serialNumber;
	
	private int length;
	
	private byte[] body;
	
	private byte end;
	
	private byte[] source;
	
//	private byte[] allData;
//
//	public byte[] getAllData() {
//		return allData;
//	}
//
//	public void setAllData(byte[] allData) {
//		this.allData = allData;
//	}

	public byte[] getSource() {
		return source;
	}

	public void setSource(byte[] source) {
		this.source = source;
	}

	public byte getStart() {
		return start;
	}

	public void setStart(byte start) {
		this.start = start;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

	public byte getEnd() {
		return end;
	}

	public void setEnd(byte end) {
		this.end = end;
	}
	
	private static final byte REPLACE_5C = 0x43;
	
	/**
	 * 替换流水号中的0x5C
	 */
	private static byte[] clear5cInSn(byte[] source){
		List<Integer> positions = new ArrayList<Integer>();
		for(int i = 2; i < 12; i++){
			if(source[i] == 0x5C){
				positions.add(i);
			}
		}
		
		if(positions.size() > 0){
			//有0x5C
			byte[] clear5c = new byte[source.length];
			System.arraycopy(source, 0, clear5c, 0, source.length);
			for(int i = 0; i < positions.size(); i++){
				int pos = positions.get(i);
				clear5c[pos] = REPLACE_5C;
			}
			
			return clear5c;
		}
		
		//没有0x5C
		return source;
	}
	
	public static SegPackage parse(byte[] source) throws Exception{
		//流水号中有5C的处理
		byte[] clear5c = clear5cInSn(source);
		//反转义
		byte[] bs = SegPkgUtil.unescape(clear5c);
		
		SegPackage pkg = new SegPackage();
		byte cmdId = bs[1];
		pkg.setStart(bs[0]);
		pkg.setType(cmdId);
		byte[] serialNumberBytes = new byte[10];
		System.arraycopy(bs, 2, serialNumberBytes, 0, 10);
		pkg.setSerialNumberBytes(serialNumberBytes);
		pkg.setSerialNumber(new String(serialNumberBytes, SegPkgUtil.PKG_CHAR_ENCODING));
		int plen = bs[12] & 0xFF;
		
		if(cmdId == (byte) 0x92){
			plen = plen * 20;
		}
		
		if(plen != bs.length - 14 && plen != source.length - 14){
			throw new Exception("unescape len:" + (bs.length - 14)
					+ ", source len:" + (source.length - 14) + ", len in data:" + plen+", source:"+HexUtil.byteToHexStr(source));
		}
		
		//pkg.setLength(len);
		//byte[] body = new byte[len];
		//长度统一设定为转义之后的长度,根据协议中的长度可能是转义前也可能是转义后，这里统一处理
		int elen = bs.length - 14;
		pkg.setLength(elen);
		byte[] body = new byte[elen];
		System.arraycopy(bs, 13, body, 0, elen);
		pkg.setBody(body);
		pkg.setEnd(bs[bs.length - 1]);
		
		pkg.setSource(source);
		
		return pkg;
	}
	
	public static void main(String[] args) {
//		String hex = "5b9001005c0d0d303030303036284f4e4530333435333641323234372e373237314e31313334352e37373931453030302e3831333138313031333030303030363030295d";
//		System.out.println(Util.hexToString(hex));
//		byte[] bs = Util.hexToBytes(hex);
//		System.out.println("bs:" + bs.length);
//		try {
//			SegPackage.parse(bs);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		/*try {
			testLog();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
	
	private static void testLog() throws IOException{
		String fileName = "E:\\test\\logs\\exception.log";
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "utf-8"));
		String flag1 = "源码:";
		String line;
		while((line = br.readLine()) != null){
			int s_start = line.indexOf(flag1);
			if(s_start != -1){
				int s_end = line.indexOf(",", s_start);
				String hex = line.substring(s_start + flag1.length(), s_end);
				byte[] bs = HexUtil.hexToBytes(hex);
				try {
					SegPackage.parse(bs);
				} catch (Exception e) {
					//e.printStackTrace();
					System.out.println(hex);
				}
			}
		}
	}
}