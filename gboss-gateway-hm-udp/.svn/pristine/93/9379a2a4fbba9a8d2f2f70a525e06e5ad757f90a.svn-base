package cc.chinagps.gateway.unit.db44.upload.cmd;

import java.util.ArrayList;
import java.util.List;

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
 * 开启/关闭 限速应答
 */
public class Cmd06 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(DB44Package pkg,
			UnitServer server, UnitSocketSession session) throws Exception {
		byte[] protocol = pkg.getProtocol();
		DB44GPSInfo gps = DB44GPSInfo.parse(protocol, 0);
		
		//开启/关闭 不可区分
		String callLetter = session.getUnitInfo().getCallLetter();
		int cmdId1 = CmdUtil.CMD_ID_LIMIT_SPEED;
		String sn1 = CmdUtil.getCmdCacheSn(callLetter, cmdId1);
		ServerToUnitCommand cache1 = server.getServerToUnitCommandCache().getAndRemoveCommand(sn1);
		if(cache1 != null){
			response(cache1, callLetter, gps);
		}else{
			int cmdId2 = CmdUtil.CMD_ID_CANCEL_LIMIT_SPEED;
			String sn2 = CmdUtil.getCmdCacheSn(callLetter, cmdId2);
			ServerToUnitCommand cache2 = server.getServerToUnitCommandCache().getAndRemoveCommand(sn2);
			if(cache2 != null){
				response(cache2, callLetter, gps);
			}
		}
		
		DB44UploadUtil.handleGPS(pkg, server, session, gps);
		return true;
	}
	
	private void response(ServerToUnitCommand cache, String callLetter, DB44GPSInfo gps){
		Command usercmd = cache.getUserCommand();
		Builder builder = CommandBuff.CommandResponse.newBuilder();
		//设置回应的通用部分
		CmdResponseUtil.updateResponseProtoSuccessByUserCommand(builder, callLetter, usercmd);
		
		//参数, 将cache参数返回(车台无返回参数)
		String p1 = usercmd.getParams(0);
		builder.addParams(p1);
		
		if(usercmd.getParamsCount() >= 2){
			String p2 = usercmd.getParams(1);
			builder.addParams(p2);
		}else{
			builder.addParams("1");
		}
		
		//gpsInfo
		DB44UploadUtil.setUpResponseByGPSInfo(builder, gps);
		
		//byte[] data = builder.build().toByteArray();
		CmdResponseUtil.simpleCommandResponse(cache, builder);
	}
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		System.out.println(list.get(3));
	}
}