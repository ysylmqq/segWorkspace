package cc.chinagps.gateway.unit.seg.upload.cmds;

import cc.chinagps.gateway.seat.cmd.CmdResponseUtil;
import cc.chinagps.gateway.seat.cmd.CmdUtil;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.seg.pkg.SegPackage;
import cc.chinagps.gateway.unit.seg.util.SegPkgUtil;

/**
 * 跟踪/停止跟踪 应答
 */
public class Cmd11 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(SegPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception {
		String strBody = new String(pkg.getBody(), SegPkgUtil.PKG_CHAR_ENCODING);
		if(strBody.length() == 15 && strBody.startsWith("(FNS,002,")){
			int count = Integer.valueOf(strBody.substring(9, 11), 16);
			//int interval = Integer.valueOf(strBody.substring(12, 14), 16);
			
			//String sn = pkg.getSerialNumber();
			String callLetter = session.getUnitInfo().getCallLetter();
			int cmdId = count == 0? CmdUtil.CMD_ID_STOP_TRACE: CmdUtil.CMD_ID_TRACE;
			String sn = CmdUtil.getCmdCacheSn(callLetter, cmdId);
			ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(sn);
			if(cache != null) {
//				Command usercmd = cache.getUserCommand();
//				Builder builder = CommandBuff.CommandResponse.newBuilder();
//				//设置回应的通用部分
//				CmdResponseUtil.updateResponseProtoSuccessByUserCommand(builder, callLetter, usercmd);
//				
//				byte[] data = builder.build().toByteArray();				
//				CmdResponseUtil.simpleCommandResponse(cache, data);
				
				CmdResponseUtil.simpleResponseSuccessByCachedCommand(cache);
			}
			
			return true;
		}
		
		return false;
	}
	
	public static void main(String[] args) {
		String str = "(FNS,002,0A,02)";
		System.out.println(str.length());
		System.out.println(str.substring(9, 11));
		System.out.println(str.substring(12, 14));
	}
}