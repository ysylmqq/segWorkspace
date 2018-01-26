package cc.chinagps.gateway.unit.db44;

import java.io.IOException;

import cc.chinagps.gateway.unit.db44.pkg.DB44Package;
import cc.chinagps.gateway.util.CRC16;
import cc.chinagps.gateway.util.HexUtil;

public class Test {
	public static void testCRC16(){
		//byte[] data = new byte[]{0x1, 0x2, 0x3};
		byte[] data = new byte[]{0x47, (byte) 0xb9, 0x16, 0x0d, 0x0c, 0x1d, (byte) 0xc3, (byte) 0xf3, 0x00, 0x00,
				0x00, 0x08, 0x41, 0x42, 0x43, 0x44, 0x00, 0x20, (byte) 0xb3, (byte) 0xc0, 
				0x06, 0x55, (byte) 0x9f, (byte) 0xe5, 0x7d, 0x7c, 0x0a, 0x78, 0x40, 0x5d, 
				(byte) 0x87, (byte) 0xfb, 0x52, 0x61, (byte) 0x88, 0x38, 0x5e, 0x03, 0x6f, 0x1e, 
				(byte) 0xad, 0x23, (byte) 0xe6, (byte) 0xb6, (byte) 0xb7, (byte) 0x90, 0x06, 0x64, 0x65, 0x5c};
		//data = "123456789".getBytes();
		int crc16 = CRC16.crc16(data);
		System.out.println(Integer.toHexString(crc16));
	}
	
	public static void main(String[] args) throws IOException {
		//车台上报
		//companyCode:160D, terminalId:0C1DC3F3, centerId:00000008, password:41424344
		//String hex = "7E52376B5744517764772F4D414141414951554A445241416773384147565A2F6C66563138436E684158596637556D47494F46344462783674492B6132743541475A4756637459343D7F";
		String hex = "7E5237305744517350516873414141414951554A445241416773384147565A764762306B4B574A67366838707155346734586C4E76624B6973347261336B415A6B5A62313848413D3D7F";
		showDetail(hex);
	}

	private static void showDetail(String hex) throws IOException{
		byte[] source = HexUtil.hexToBytes(hex);
		
		DB44Package pkg = DB44Package.parse(source);
		System.out.println(pkg);
		
		//byte[] encryptionBody = pkg.getEncryptionBody();
		//System.out.println("encryptionBody:" + Util.byteToHexStr(encryptionBody).toUpperCase());
		
		byte[] decryptionBody = pkg.getDecryptionBody();
		//checkcrc(decryptionBody);
		System.out.println("decryptionBody:" + HexUtil.byteToHexStr(decryptionBody).toUpperCase());
		System.out.println("ProtocolId:" + Integer.toHexString(pkg.getProtocolId()));
	}
	
	//查车7E5235565452774141414141414141466451554A44524141436F73467166413D3D7F
	//复位7E52356454527741414141414141414A6451554A44524141446F7477557834633D7F
	public static void showDownLoad(String hex) throws IOException{
		byte[] source = HexUtil.hexToBytes(hex);
		
		DB44Package pkg = DB44Package.parse(source);
		System.out.println(pkg);
	}
	
	public static void down(){
		//复位
		//companyCode:5347, terminalId:00000000, centerId:0000025D, password:41424344,	
		//String hex = "7E52356454527741414141414141414A6451554A44524141446F7477557834633D7F";
		
		//查车
		//companyCode:5347, terminalId:00000000, centerId:0000015D, password:41424344
		//String hex = "7E5235565452774141414141414141466451554A44524141436F73467166413D3D7F";
		
		//断油路
		//companyCode:5347, terminalId:00000000, centerId:0000065D, password:41424344
		//String hex = "7E52793954527741414141414141415A6451554A445241414A6F746B56515A2F7461487849636C303D7F";
		
		//整机复位
		//companyCode:5347, terminalId:00000000, centerId:0000025D, password:41424344,
		//String hex = "7E52796C54527741414141414141414A6451554A44524141446F747755416C553D7F";
		
		//恢复出厂设置
		//companyCode:5347, terminalId:00000000, centerId:0000025D, password:41424344
		//String hex = "7E52797054527741414141414141414A6451554A44524141446F747758717A383D7F";
	}
}