package cc.chinagps.gateway.unit.beforemarket.common.pkg;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.exceptions.WrongPackageException;
import cc.chinagps.gateway.unit.beforemarket.common.util.BeforeMarketPkgUtil;
import cc.chinagps.gateway.util.CRC16;
import cc.chinagps.gateway.util.HexUtil;

public class BeforeMarketPackage {
	private BeforeMarketPackageHead head;
	
	private byte[] body;
	
	private short crc16;
	
	private byte[] source;
	
	public byte[] getSource() {
		return source;
	}

	public void setSource(byte[] source) {
		this.source = source;
	}

	public BeforeMarketPackageHead getHead() {
		return head;
	}

	public void setHead(BeforeMarketPackageHead head) {
		this.head = head;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

	public short getCrc16() {
		return crc16;
	}

	public void setCrc16(short crc16) {
		this.crc16 = crc16;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{head:").append(head);
		sb.append(", body:").append(HexUtil.byteToHexStr(body).toUpperCase());
		sb.append(", crc16:0x").append(HexUtil.toHexStr(crc16).toUpperCase());
		sb.append("}");
		
		return sb.toString();
	}
	
	public static BeforeMarketPackage parse(byte[] source, int c1, int d1) throws Exception{
		BeforeMarketPackage pkg = new BeforeMarketPackage();
		byte[] unescape = BeforeMarketPkgUtil.unescape(source);
		
		BeforeMarketPackageHead phead = BeforeMarketPackageHead.parse(unescape, 1);
		
		//check length
		int len1 = phead.getBodyLength();
		int bodyLength = unescape.length - 18;
		if(len1 != bodyLength){
			throw new WrongPackageException("length in head:" + len1 + ", body length is:" + bodyLength+", source:"+HexUtil.byteToHexStr(source));
		}
		
		//check crc16
		short cal_crc16 = (short) CRC16.crc16(unescape, 1, unescape.length - 4);
		short pkg_crc16 = Util.getShort(unescape, unescape.length - 3);
		if(cal_crc16 != pkg_crc16){
			throw new WrongPackageException("cal_crc16:" + cal_crc16 + ", pkg_crc16:" + pkg_crc16);
		}
		
		//拷贝出body,防止后续出现问题找不到源码
		byte[] b_body = new byte[bodyLength];
		System.arraycopy(unescape, 15, b_body, 0, bodyLength);
		
		//解密
		if(phead.isEncrypt()){
			int d2 = (phead.getSn() << 16) | (bodyLength & 0xFFFF);
			b_body = BeforeMarketPkgUtil.encrypt(c1, d1, d2, b_body);
		}
		
		//解压缩
		if(phead.isCompress()){
			b_body = Util.ZlibDecompress(b_body);
		}
		
		pkg.setHead(phead);
		pkg.setBody(b_body);
		pkg.setCrc16(cal_crc16);
		pkg.setSource(source);
		
		return pkg;
	}
	
	public byte[] encode(int c1, int d1){
		byte[] b_body = new byte[body.length];
		System.arraycopy(body, 0, b_body, 0, body.length);
		//压缩
		if(head.isCompress()){
			b_body = Util.ZlibCompress(b_body);
		}
		
		//加密
		if(head.isEncrypt()){
			int d2 = (head.getSn() << 16) | (b_body.length & 0xFFFF);
			b_body = BeforeMarketPkgUtil.encrypt(c1, d1, d2, b_body);
		}
		
		byte[] b_head = head.encode(b_body.length);
		
		byte[] to_escape = new byte[b_head.length + b_body.length + 2];
		System.arraycopy(b_head, 0, to_escape, 0, b_head.length);
		System.arraycopy(b_body, 0, to_escape,  b_head.length, b_body.length);
		
		//CRC16
		int crc16 = CRC16.crc16(to_escape, 0, b_head.length + b_body.length);
		byte[] b_crc16 = Util.getShortByte((short) crc16);
		System.arraycopy(b_crc16, 0, to_escape, b_head.length + b_body.length, 2);
		
		//转义
		byte[] escaped = BeforeMarketPkgUtil.escape(to_escape);
		byte[] to_send = new byte[escaped.length + 2];
		System.arraycopy(escaped, 0, to_send, 1, escaped.length);
		to_send[0] = BeforeMarketPkgUtil.START_FLAG;
		to_send[to_send.length - 1] = BeforeMarketPkgUtil.START_FLAG;
		
		return to_send;
	}
	
	public static void main(String[] args) throws Exception {
		/*int c1 = 0x123;
		int d1 = 0x456;
		boolean isCompress = false;
		boolean isEncrypt = true;
		short sn = 2;
		String imei = "12345678901234";
		byte msgType = 1;
		//byte[] body = new byte[]{1, 2, 3, 4, 5, 6};
		byte[] body = new byte[0];
		
		BeforeMarketPackage pkg = new BeforeMarketPackage();
		BeforeMarketPackageHead head = new BeforeMarketPackageHead();
		head.setCompress(isCompress);
		head.setEncrypt(isEncrypt);
		head.setMsgType(msgType);
		head.setSn(sn);
		head.setTerminalId(imei);
		head.setTerminalType((byte) 1);
		head.setVersion((byte) 0x10);
		
		
		pkg.setHead(head);
		pkg.setBody(body);
		
		System.out.println("ppkg1:" + pkg);
		
		byte[] source = pkg.encode(c1, d1);
		String hex_source = HexUtil.byteToHexStr(source).toUpperCase();
		System.out.println("source1:" + hex_source);
		
		BeforeMarketPackage ppkg = BeforeMarketPackage.parse(source, c1, d1);
		System.out.println("ppkg2:" + ppkg);
		//String hex_source2 = HexUtil.byteToHexStr(source).toUpperCase();
		//System.out.println("source2:" + hex_source2);
		//System.out.println("source-eq:" + hex_source2.equals(hex_source));
		
		String hex = hex_source.substring(2, hex_source.length() - 6);
		byte[] bs = HexUtil.hexToBytes(hex);
		short ccrc16 = (short) CRC16.crc16(bs);
		System.out.println("ccrc16:" + HexUtil.toHexStr(ccrc16).toUpperCase());*/
		
		String s = "7d11023553330585814741000200320028c616071806515382000d76dcc243786af20000006400000000000004343630303025f2a32200000004bc0000119464647cc57d";
		
		s = "7D1102355333058580264100E600320028C616071806574482000D8A658A437F6E92000000640000000000000434363030302677FF920000001DBE0001110C646485AE7D";
		
		s = "7d1102355333058528030100010027014342422d31303000000000000000000000000000000000000000000000000000000031353033e0b97d";
		//没问题
		s = "7d11023553330585275141004a00320028c6160719010823818c101869e64254f4ec005c0064000000000000003436303031739928ab0000000918000013ec6464b7367d";
		System.out.println(BeforeMarketPackage.parse(HexUtil.hexToBytes(s), 1, 2));
	
	}
	
//	private static int calCrc16(HMPackage pkg, int c1, int d1){
//		HMPackageHead phead = pkg.getHead();
//		byte[] b_head = phead.encode();
//		byte[] b_body = pkg.getBody();
//		
//		if(phead.isCompress()){
//			b_body = Util.ZlibCompress(b_body);
//		}
//		
//		//加密
//		if(phead.isEncrypt()){
//			int d2 = (phead.getSn() << 16) | (b_body.length & 0xFFFF);
//			b_body = HMPkgUtil.encrypt(c1, d1, d2, b_body);
//		}
//		
//		byte[] to_crc = new byte[b_head.length + b_body.length];
//		System.arraycopy(b_head, 0, to_crc, 0, b_head.length);
//		System.arraycopy(b_body, 0, to_crc,  b_head.length, b_body.length);
//		
//		return CRC16.crc16(to_crc);
//	}
}