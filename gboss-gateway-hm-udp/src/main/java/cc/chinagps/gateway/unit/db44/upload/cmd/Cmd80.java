package cc.chinagps.gateway.unit.db44.upload.cmd;

import org.apache.log4j.Logger;

import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.db44.pkg.DB44Package;
import cc.chinagps.gateway.unit.db44.upload.DB44GPSInfo;
import cc.chinagps.gateway.unit.db44.upload.DB44UploadUtil;
import cc.chinagps.gateway.unit.db44.upload.UploadHandler;
import cc.chinagps.gateway.unit.db44.util.DB44PkgUtil;
import cc.chinagps.gateway.util.Constants;
import cc.chinagps.gateway.util.HexUtil;

/**
 * 登录
 */
public class Cmd80 implements UploadHandler{
	private Logger logger_exception = Logger.getLogger(LogManager.LOGGER_NAME_EXCEPTION);
	
	@Override
	public boolean handlerPackage(DB44Package pkg, UnitServer server,
			UnitSocketSession session) throws Exception {
		byte[] protocol = pkg.getProtocol();
		//call+gps(30)
		int callBytesLength = protocol.length - 30;
		String callLetter = DB44PkgUtil.readSegString(protocol, 0, callBytesLength);
		DB44GPSInfo gps = DB44GPSInfo.parse(protocol, callBytesLength);
		
		if(callLetter == null){
			logger_exception.info("call is null, pkg is:" + pkg);
			return true;
		}
		
		//处理登录
		CmdF6.handlerLogin(pkg, server, session, callLetter, (byte) 0x80);
		
		//处理GPS
		DB44UploadUtil.handleGPS(pkg, server, session, gps);
		return true;
	}
	
	public static void main(String[] args) {
		String hex = "7E52785157445177576B5459414141414951554A44524141727330416B5A726E555432346A582F6B347450314E6359672F567A643863637932786562446B4D626C4A695A73554470324C65335366563775746351594F773D3D7F";
		byte[] bs = HexUtil.hexToBytes(hex);
		try {
			DB44Package pkg = DB44Package.parse(bs);
			
			System.out.println("pkg:" + pkg);
			//Cmd80 cmd80 = new Cmd80();
			//cmd80.handlerPackage(pkg, null, null);
			
			
			byte[] hb = DB44PkgUtil.encode((byte) 0, (short) 0x5347, 0, 
					Constants.SYSTEM_ID_INT, 0x41424344, (byte) 'U', (byte) 0xFD, Constants.ZERO_BYTES_DATA);
			System.out.println("hb:" + HexUtil.byteToHexStr(hb));
			
			String hbHex = "7e5277425452774141414141414141414351554a4452414143737a30432b513d3d7f";
			byte[] hbBs = HexUtil.hexToBytes(hbHex);
			DB44Package pkgHB = DB44Package.parse(hbBs);
			
			System.out.println("pkgHB:" + pkgHB);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}