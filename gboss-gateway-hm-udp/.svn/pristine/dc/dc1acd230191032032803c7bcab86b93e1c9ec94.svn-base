package cc.chinagps.gateway.unit.db44.test;

import cc.chinagps.gateway.unit.db44.pkg.DB44Package;
import cc.chinagps.gateway.unit.db44.upload.DB44GPSInfo;
import cc.chinagps.gateway.util.HexUtil;

public class TestDB44 {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String hex = "7e522f5157445177586c5463414141414951554a44524141677338414155346e7a4c57734b586c63346838743464344a485869707659627269347061336b415a6b62376e5634773d3d7f";
		byte[] bs = HexUtil.hexToBytes(hex);
		DB44Package pkg = DB44Package.parse(bs);
		
		System.out.println(pkg);
		
		byte[] protocol = pkg.getProtocol();
		
		DB44GPSInfo gps = DB44GPSInfo.parse(protocol, 0);
		System.out.println(gps);
	}

}
