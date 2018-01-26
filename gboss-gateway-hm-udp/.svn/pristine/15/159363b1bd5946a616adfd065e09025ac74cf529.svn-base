package cc.chinagps.gateway.unit.db44.upload.cmd;

import org.seg.lib.util.Util;

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

/**
 * 开启/关闭 定距监控设置应答
 */
public class Cmd04 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(DB44Package pkg,
			UnitServer server, UnitSocketSession session) throws Exception {
		byte[] protocol = pkg.getProtocol();
		DB44GPSInfo gps = DB44GPSInfo.parse(protocol, 0);
		
		//定距上报参数
		//距离间隔, 发送条数, 发送包数
		int distance = Util.getShort(protocol, 30) & 0xFFFF;
		int number = protocol[32] & 0xFF;
		int packages = Util.getShort(protocol, 33) & 0xFFFF;
		
		String callLetter = session.getUnitInfo().getCallLetter();
		int cmdId = (packages == 0? CmdUtil.CMD_ID_STOP_UPLOAD_BY_DISTANCE: CmdUtil.CMD_ID_START_UPLOAD_BY_DISTANCE);
		String sn = CmdUtil.getCmdCacheSn(callLetter, cmdId);
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(sn);
		if(cache != null){
			Command usercmd = cache.getUserCommand();
			
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			//设置回应的通用部分
			CmdResponseUtil.updateResponseProtoSuccessByUserCommand(builder, callLetter, usercmd);
			//参数
			builder.addParams(String.valueOf(distance));
			builder.addParams(String.valueOf(number));
			builder.addParams(String.valueOf(packages));
			
			//gpsInfo
			DB44UploadUtil.setUpResponseByGPSInfo(builder, gps);
			
			//byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		}
		
		DB44UploadUtil.handleGPS(pkg, server, session, gps);
		return true;
	}
}