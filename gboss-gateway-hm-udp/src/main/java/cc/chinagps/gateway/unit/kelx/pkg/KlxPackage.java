package cc.chinagps.gateway.unit.kelx.pkg;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.unit.kelx.util.KlxPkgUtil;
import cc.chinagps.gateway.util.HexUtil;

public class KlxPackage {
	private byte start = KlxPkgUtil.START_FLAG;
	
	private byte[] id;
	
	private byte sn;
	
	private int bodyLength;
	
	private byte[] body;
	
	private byte xor;
	
	private byte end = KlxPkgUtil.START_FLAG;
	
	private byte[] source;

	public byte getStart() {
		return start;
	}

	public void setStart(byte start) {
		this.start = start;
	}

	public byte[] getId() {
		return id;
	}

	public void setId(byte[] id) {
		this.id = id;
	}

	public byte getSn() {
		return sn;
	}

	public void setSn(byte sn) {
		this.sn = sn;
	}

	public int getBodyLength() {
		return bodyLength;
	}

	public void setBodyLength(int bodyLength) {
		this.bodyLength = bodyLength;
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
	
	public static KlxPackage parse(byte[] source) throws Exception{
		//反转义
		byte[] unescape = KlxPkgUtil.unescape(source);
		
		KlxPackage pkg = new KlxPackage();
		byte start = unescape[0];
		
		byte[] id = new byte[10];
		System.arraycopy(unescape, 1, id, 0, 10);
		
		byte sn = unescape[11];
		
		int bodyLength = Util.getShort(unescape, 12) & 0xFFFF;
		
//		int calBodyLength = unescape.length - 16;
//		if(calBodyLength != bodyLength){
//			throw new Exception("bodyLength:" + bodyLength + ", calBodyLength:" + calBodyLength);
//		}
		
		byte[] body = new byte[unescape.length - 16];
		System.arraycopy(unescape, 14, body, 0, body.length);
		
		byte xor = unescape[unescape.length - 2];
		
//		byte calXor = 0;
//		for(int i = 1; i < unescape.length - 2; i++){
//			calXor ^= unescape[i];
//		}
//		if(calXor != xor){
//			throw new Exception("xor:" + xor + ", calXor:" + calXor);
//		}
		
		byte end = unescape[unescape.length - 1];
		
		pkg.setBody(body);
		pkg.setBodyLength(bodyLength);
		pkg.setEnd(end);
		pkg.setId(id);
		pkg.setSn(sn);
		pkg.setSource(source);
		pkg.setStart(start);
		pkg.setXor(xor);
		
		return pkg;
	}
	
	/**
	 * xor --> 转义 --> 加头尾
	 * need:
	 * id,sn,body
	 */
	public byte[] encode() throws Exception{
		byte[] to_escape = new byte[body.length + 14];
		int position = 0;
		
		System.arraycopy(id, 0, to_escape, position, 10);
		position += 10;
		
		to_escape[position ++] = sn;
		
		int body_length = body.length;
		int max_length = 0xFFFF;
		if(body_length > max_length){
			throw new Exception("big body, max is " + max_length + ", current:" + body_length);
		}
		byte[] bodyLengthBytes = Util.getShortByte((short) body_length);
		System.arraycopy(bodyLengthBytes, 0, to_escape, position, 2);
		position += 2;
		
		System.arraycopy(body, 0, to_escape, position, body.length);
		position += body.length;
		
		byte xor = 0;
		for(int i = 0; i < to_escape.length - 1; i++){
			xor ^= to_escape[i];
		}
		
		to_escape[position ++] = xor;

		byte[] escape = KlxPkgUtil.escape(to_escape);
		byte[] data = new byte[escape.length + 2];
		data[0] = start;
		System.arraycopy(escape, 0, data, 1, escape.length);
		data[data.length - 1] = end;
		
		return data;
	}
	
	public static void main(String[] args) throws Exception {
//		KLXPackage pkg = new KLXPackage();
//		byte[] body = {0x55, (byte) 0xaa, 0x55};
//		pkg.setBody(body);
//		
//		byte[] call = Util.str2bcd("013912345001");
//		byte[] id = new byte[10];
//		System.arraycopy(call, 0, id, 4, 6);
//		
//		pkg.setId(id);
//		
//		byte[] en = pkg.encode();
//		System.out.println(HexUtil.byteToHexStr(en));
//		
//		KLXPackage rpkg = KLXPackage.parse(en);
//		System.out.println(rpkg);
		
		String hex = "AA000000000136000000036E0022000413112512071800004F0311300753281129055C08005B000602C2000400000004B3AA";
		byte[] data = HexUtil.hexToBytes(hex);
		KlxPackage rpkg = KlxPackage.parse(data);
		System.out.println(rpkg);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{start:0x").append(HexUtil.toHexStr(start).toUpperCase());
		sb.append(", id:").append(HexUtil.byteToHexStr(id).toUpperCase());
		sb.append(", sn:0x").append(HexUtil.toHexStr(sn).toUpperCase());
		sb.append(", bodyLength:").append(bodyLength);
		sb.append(", body:").append(HexUtil.byteToHexStr(body).toUpperCase());
		sb.append(", xor:0x").append(HexUtil.toHexStr(xor).toUpperCase());
		sb.append(", end:0x").append(HexUtil.toHexStr(end).toUpperCase());
		sb.append(", source:").append(HexUtil.byteToHexStr(source).toUpperCase());
		sb.append("}");
		
		return sb.toString();
	}
}