package cc.chinagps.gateway.unit.seg.upload.cmds;

import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.seg.pkg.SegPackage;
import cc.chinagps.gateway.unit.seg.upload.UploadHandler;
import cc.chinagps.gateway.unit.seg.util.SegPkgUtil;
import cc.chinagps.gateway.util.HexUtil;

/**
 * 链路检测
 */
public class CmdF0 implements UploadHandler{
	@Override
	public boolean handlerPackage(byte[] source, UnitServer server,
			UnitSocketSession session) throws Exception {
		byte resType = (byte) 0xFF;
		// res;
		byte[] es;
		if(source.length == 3){
			//短报文
			byte[] res = new byte[3];
			res[0] = SegPkgUtil.START_FLAG;
			res[1] = resType;
			res[2] = SegPkgUtil.END_FLAG;
			
			es = SegPkgUtil.escape(res);
		}else if(source.length >= 14){
			//长报文
			SegPackage p = SegPackage.parse(source);
			es = SegPkgUtil.encode(resType, p.getSerialNumberBytes(), p.getBody());
		}else{
			return false;
		}
		
		session.sendData(es);
		return true;
	}
	
	public static void main(String[] args) {
		String hex = "5bf05e5bf05d";
		byte[] bs = HexUtil.hexToBytes(hex);
		CmdF0 f0 = new CmdF0();
		try {
			boolean r = f0.handlerPackage(bs, null, null);
			System.out.println(r);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}