package cc.chinagps.gateway.unit.db44.upload.cmd;

import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.buff.CommandBuff.CommandResponse.Builder;
import cc.chinagps.gateway.seat.cmd.CmdResponseUtil;
import cc.chinagps.gateway.seat.cmd.CmdUtil;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.db44.pkg.DB44Package;
import cc.chinagps.gateway.unit.db44.upload.DB44GPSInfo;
import cc.chinagps.gateway.unit.db44.upload.DB44UploadUtil;
import cc.chinagps.gateway.unit.db44.util.DB44PkgUtil;

/**
 * 下发短信应答
 */
public class Cmd15 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(DB44Package pkg,
			UnitServer server, UnitSocketSession session) throws Exception {
		byte[] protocol = pkg.getProtocol();
		DB44GPSInfo gps = DB44GPSInfo.parse(protocol, 0);
		
		String callLetter = session.getUnitInfo().getCallLetter();
		String sn = CmdUtil.getCmdCacheSn(callLetter, CmdUtil.CMD_ID_SEND_SM);
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(sn);
		if(cache != null) {
			Command usercmd = cache.getUserCommand();
			
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			//设置回应的通用部分
			CmdResponseUtil.updateResponseProtoSuccessByUserCommand(builder, callLetter, usercmd);
			//参数
			String msg = new String(protocol, 30, protocol.length - 30, DB44PkgUtil.CN_CHAR_ENCODING);
			
			builder.addParams(session.getUnitInfo().getCallLetter());
			builder.addParams(msg);
			//gpsInfo
			DB44UploadUtil.setUpResponseByGPSInfo(builder, gps);
			
			//byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		}
		
		DB44UploadUtil.handleGPS(pkg, server, session, gps);
		return true;
	}
}