package cc.chinagps.gateway.unit.db44.pkg;

import java.io.IOException;

import cc.chinagps.gateway.unit.db44.util.DB44PkgUtil;
import cc.chinagps.gateway.util.Base64;
import cc.chinagps.gateway.util.HexUtil;

public class DB44Package {
	private byte head;
	
	private byte flag;
	
	private byte loop;
	
	private short companyCode;
	
	private int terminalId;
	
	private int centerId;
	
	private int password;
	
	private int protocolLength;
	
	private short protocolId;
	
	private byte protocolIdMode;
	
	private byte protocolIdNumber;
	
	private byte[] protocol;
	
	private short crc16;
	
	private byte tail;
	
	private byte[] source;
	
	//private byte[] encryptionBody;
	
	private byte[] decryptionBody;
	
	
	public byte getProtocolIdMode() {
		return protocolIdMode;
	}

	public void setProtocolIdMode(byte protocolIdMode) {
		this.protocolIdMode = protocolIdMode;
	}

	public byte getProtocolIdNumber() {
		return protocolIdNumber;
	}

	public void setProtocolIdNumber(byte protocolIdNumber) {
		this.protocolIdNumber = protocolIdNumber;
	}

	//public byte[] getEncryptionBody() {
	//	return encryptionBody;
	//}

	//public void setEncryptionBody(byte[] encryptionBody) {
	//	this.encryptionBody = encryptionBody;
	//}

	public byte[] getSource() {
		return source;
	}

	public void setSource(byte[] source) {
		this.source = source;
	}

	public byte[] getDecryptionBody() {
		return decryptionBody;
	}

	public void setDecryptionBody(byte[] decryptionBody) {
		this.decryptionBody = decryptionBody;
	}

	public byte getHead() {
		return head;
	}

	public void setHead(byte head) {
		this.head = head;
	}

	public byte getFlag() {
		return flag;
	}

	public void setFlag(byte flag) {
		this.flag = flag;
	}

	public byte getLoop() {
		return loop;
	}

	public void setLoop(byte loop) {
		this.loop = loop;
	}

	public short getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(short companyCode) {
		this.companyCode = companyCode;
	}

	public int getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(int terminalId) {
		this.terminalId = terminalId;
	}

	public int getCenterId() {
		return centerId;
	}

	public void setCenterId(int centerId) {
		this.centerId = centerId;
	}

	public int getPassword() {
		return password;
	}

	public void setPassword(int password) {
		this.password = password;
	}

	public int getProtocolLength() {
		return protocolLength;
	}

	public void setProtocolLength(int protocolLength) {
		this.protocolLength = protocolLength;
	}

	public short getProtocolId() {
		return protocolId;
	}

	public void setProtocolId(short protocolId) {
		this.protocolId = protocolId;
	}

	public byte[] getProtocol() {
		return protocol;
	}

	public void setProtocol(byte[] protocol) {
		this.protocol = protocol;
	}

	public short getCrc16() {
		return crc16;
	}

	public void setCrc16(short crc16) {
		this.crc16 = crc16;
	}

	public byte getTail() {
		return tail;
	}

	public void setTail(byte tail) {
		this.tail = tail;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{head:").append(HexUtil.toHexStr(head).toUpperCase());
		sb.append(", flag:").append(HexUtil.toHexStr(flag).toUpperCase());
		sb.append(", loop:").append(HexUtil.toHexStr(loop).toUpperCase());
		sb.append(", companyCode:").append(HexUtil.toHexStr(companyCode).toUpperCase());
		sb.append(", terminalId:").append(HexUtil.toHexStr(terminalId).toUpperCase());
		sb.append(", centerId:").append(HexUtil.toHexStr(centerId).toUpperCase());
		sb.append(", password:").append(HexUtil.toHexStr(password).toUpperCase());
		sb.append(", protocolLength(").append(protocolLength).append("):").append(HexUtil.toHexStr((short) protocolLength).toUpperCase());
		sb.append(", protocolId:").append(HexUtil.toHexStr(protocolId).toUpperCase());
		sb.append(", protocolIdMode:").append(HexUtil.toHexStr(protocolIdMode));
		sb.append(", protocolIdNumber:").append(HexUtil.toHexStr(protocolIdNumber));
		sb.append(", protocol(").append(protocol.length).append("):").append(HexUtil.byteToHexStr(protocol).toUpperCase());
		sb.append(", crc16:").append(HexUtil.toHexStr(crc16).toUpperCase());
		sb.append(", tail:").append(HexUtil.toHexStr(tail).toUpperCase());
		sb.append("}");
		
		return sb.toString();
	}
	
	public static DB44Package parse(byte[] source) throws IOException{
		String base64 = new String(source, 1, source.length - 2, "ascii");
		//base64解码
		byte[] encryptionBody = Base64.decode(base64);
		
		//反转义
		byte[] unescapeBody = DB44PkgUtil.unescape(encryptionBody);
		
		//byte[] toCrc = new byte[unescapeBody.length - 2];
		//System.arraycopy(unescapeBody, 0, toCrc, 0, toCrc.length);
		//String toCrcHex = Util.byteToHexStr(toCrc).toUpperCase();
		
//		StringBuilder sb = new StringBuilder();
//		for(int i = 0; i < toCrcHex.length() / 2; i++){
//			sb.append("#$").append(toCrcHex.substring(i * 2, i * 2 + 2));
//		}
//		System.out.println("sb:" + sb);
		
		//System.out.println("toCrc(" + toCrcHex.length() + "):" + toCrcHex);
		//int crc = CRC16.crc16(toCrc);
		//System.out.println("crc:" + Integer.toHexString(crc).toUpperCase());
		
		
		//解密
		byte[] body = DB44PkgUtil.db44encodeCommon(unescapeBody, 18, unescapeBody.length - 20);
		byte[] protocol = new byte[body.length - 22];
		System.arraycopy(body, 20, protocol, 0, protocol.length);
		
		DB44Package pkg = new DB44Package();
		pkg.setHead(source[0]);
		pkg.setSource(source);
		//pkg.setEncryptionBody(encryptionBody);
		pkg.setDecryptionBody(body);
		pkg.setFlag(body[0]);
		pkg.setLoop(body[1]);
		pkg.setCompanyCode(org.seg.lib.util.Util.getShort(body, 2));
		pkg.setTerminalId(org.seg.lib.util.Util.getInt(body, 4));
		pkg.setCenterId(org.seg.lib.util.Util.getInt(body, 8));
		pkg.setPassword(org.seg.lib.util.Util.getInt(body, 12));
		pkg.setProtocolLength(org.seg.lib.util.Util.getShort(body, 16) & 0xFFFF);
		short protocolId = org.seg.lib.util.Util.getShort(body, 18);
		pkg.setProtocolId(protocolId);
		
		pkg.setProtocolIdMode((byte) ((protocolId >> 8) & 0xFF));
		pkg.setProtocolIdNumber((byte) (protocolId & 0xFF));
		
		pkg.setProtocol(protocol);
		pkg.setCrc16(org.seg.lib.util.Util.getShort(body, body.length - 2));
		pkg.setTail(source[source.length - 1]);

		return pkg;
	}
}