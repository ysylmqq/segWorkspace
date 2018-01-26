package cc.chinagps.gateway.unit.seg.upload.cmds;

import cc.chinagps.gateway.seat.cmd.CmdResponseUtil;
import cc.chinagps.gateway.seat.cmd.CmdUtil;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.seg.pkg.SegPackage;
import cc.chinagps.gateway.unit.seg.util.SegPkgUtil;

/**
 * 恢复电话功能应答
 */
public class Cmd74 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(SegPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception {
		byte[] body = pkg.getBody();
		String strBody = new String(body, SegPkgUtil.PKG_CHAR_ENCODING);
		if(strBody.equals("(FNS,DUL)")){
			//恢复电话功能
			String callLetter = session.getUnitInfo().getCallLetter();
			String sn = CmdUtil.getCmdCacheSn(callLetter, CmdUtil.CMD_ID_RESUME_TEL);
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
}