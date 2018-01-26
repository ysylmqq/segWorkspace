package cc.chinagps.gateway.unit.oem.util;

import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.oem.pkg.OEMPackage;
import cc.chinagps.gateway.util.HexUtil;

public class OEMPkgUtil {

	/**
	 * 获取指令sn
	 */
	public static byte getSn(UnitSocketSession unitSession){
		synchronized (unitSession) {
			Object obj = unitSession.getAttribute(OEMConstants.OEM_SESSION_KEY_SN);
			Byte v;
			if(obj == null){
				v = 1;
			}else{
				v = (Byte) obj;
				v++;
			}
			
			unitSession.setAttribute(OEMConstants.OEM_SESSION_KEY_SN, v);
			return v;
		}
	}
	
	/**
	 * 获取下发指令缓存sn
	 */
	public static String getCmdCacheSn(String callLetter, byte headSN){
		return callLetter + "_" + headSN;
	}
	
	public static byte[] getCommonResponseToUnit(UnitSocketSession session, byte responseCmdId,OEMPackage pkg) throws Exception{
		pkg.setData(new byte[0]);
		pkg.setMainCmdId(responseCmdId);
		pkg.setBodyLen((short) 6);
		byte[] source = pkg.encode();
		
		return source;
	}
	
	public static byte[] getCommonResponseToUnit(UnitSocketSession session, byte mainCmdId,byte responseCmdId,OEMPackage pkg) throws Exception{
		pkg.setData(new byte[]{responseCmdId});
		pkg.setMainCmdId(mainCmdId);
		pkg.setBodyLen((short) 7);
		byte[] source = pkg.encode();
		
		return source;
	}
	
	public static void main(String[] args) throws Exception {
		OEMPackage pkg = new OEMPackage();
		String s = "292992001E00808C49160817113759000093880005369500000346800042000142C60D";
		pkg = OEMPackage.parse(HexUtil.hexToBytes(s));
		System.out.println(HexUtil.byteToHexStr(getCommonResponseToUnit(null, (byte)0x20, (byte)0x92, pkg)));
	}
	
	
}