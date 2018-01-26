package cc.chinagps.gateway.unit.pengaoda.pkg;

import cc.chinagps.gateway.unit.pengaoda.util.PengAoDaPkgUtil;
import cc.chinagps.gateway.util.HexUtil;

public class PengAoDaPackage {
	private byte start = PengAoDaPkgUtil.START_FLAG;
	
	private byte[] body;
	
	private byte xor;
	
	private byte end = PengAoDaPkgUtil.START_FLAG;

	private byte[] source;
	
	public byte getStart() {
		return start;
	}

	public void setStart(byte start) {
		this.start = start;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

	public byte getXor() {
		return xor;
	}

	public void setXor(byte xor) {
		this.xor = xor;
	}

	public byte getEnd() {
		return end;
	}

	public void setEnd(byte end) {
		this.end = end;
	}
	
	public byte[] getSource() {
		return source;
	}

	public void setSource(byte[] source) {
		this.source = source;
	}

	public static PengAoDaPackage parse(byte[] source) throws Exception{
		//·´×ªÒå
		byte[] unescape = PengAoDaPkgUtil.unescape(source);
		
		PengAoDaPackage pkg = new PengAoDaPackage();
		
		byte start = unescape[0];
		byte[] body = new byte[unescape.length - 3];
		System.arraycopy(unescape, 1, body, 0, body.length);
		byte xor = unescape[unescape.length - 2];
		byte end = unescape[unescape.length - 1];
		
		byte calXor = calXOR(body);
		if(xor != calXor){
			throw new Exception("xor:" + xor + ", calXor:" + calXor);
		}
		
		pkg.setStart(start);
		pkg.setBody(body);
		pkg.setXor(xor);
		pkg.setEnd(end);
		pkg.setSource(source);
		
		return pkg;
	}
	
	private static byte calXOR(byte[] body){
		byte xor = 0x7E;
		for(int i = 0; i < body.length; i++){
			xor ^= body[i];
		}
		
		return xor;
	}
	
	public byte[] encode(){
		byte xor = calXOR(body);
		
		byte[] to_escape = new byte[body.length + 1];
		System.arraycopy(body, 0, to_escape, 0, body.length);
		to_escape[to_escape.length - 1] = xor;
		
		byte[] escape = PengAoDaPkgUtil.escape(to_escape);
		
		byte[] data = new byte[escape.length + 2];
		data[0] = start;
		System.arraycopy(escape, 0, data, 1, escape.length);
		data[data.length - 1] = end;
		
		return data;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{start:0x").append(HexUtil.toHexStr(start));
		sb.append(", body:").append(HexUtil.byteToHexStr(body));
		sb.append(", xor:0x").append(HexUtil.toHexStr(xor));
		sb.append(", end:").append(HexUtil.toHexStr(end));
		sb.append("}");
		
		return sb.toString();
	}
	
	public static void main(String[] args) throws Exception {
		String hex = "7E24885600000107572126041423103570C6113259970C000000FFFBDFFFEF413000520000000663070103500101CC00257D02EF710047C20133445566043344556605AA0000000C4E2000000DAA0000001F334455664230303030443344556646334455664C33445566880000000089000000028A000805E38B0000008701D17E";
		byte[] bytes = HexUtil.hexToBytes(hex);
		PengAoDaPackage pkg = PengAoDaPackage.parse(bytes);
		System.out.println(pkg);
	}
}