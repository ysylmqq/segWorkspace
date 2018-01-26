package cc.chinagps.gateway.unit.db44.upload.cmd;

import java.io.IOException;

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
 * 远程参数设置应答
 * 1)设置TCP/UDP通信参数	
 * 2)设置短消息中心服务号码
 */
public class Cmd1B extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(DB44Package pkg,
			UnitServer server, UnitSocketSession session) throws Exception {
		byte[] protocol = pkg.getProtocol();
		DB44GPSInfo gps = DB44GPSInfo.parse(protocol, 0);
		
		byte type = protocol[30];
		if(type == 0xA || type == 0xB){
			//设置TCP/UDP通信参数应答(0xA: 通信参数,  0xB:备用通信参数)
			handlerTCPSettingAck(pkg, server, session);
			DB44UploadUtil.handleGPS(pkg, server, session, gps);
			return true;
		}else if(type == 0xC){
			//设置短消息中心服务号码应答
			handlerSetSmCenterAck(pkg, server, session);
			DB44UploadUtil.handleGPS(pkg, server, session, gps);
			return true;
		}
		
		DB44UploadUtil.handleGPS(pkg, server, session, gps);
		return false;
	}
	
	/**
	 * 设置TCP/UDP通信参数应答
	 */
	private void handlerTCPSettingAck(DB44Package pkg,
			UnitServer server, UnitSocketSession session){
		String callLetter = session.getUnitInfo().getCallLetter();
		String sn = CmdUtil.getCmdCacheSn(callLetter, CmdUtil.CMD_ID_SET_TCP_UDP_PARAMS);
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(sn);
		if(cache != null) {
//			Command usercmd = cache.getUserCommand();			
//			Builder builder = CommandBuff.CommandResponse.newBuilder();
//			//设置回应的通用部分
//			CmdResponseUtil.updateResponseProtoSuccessByUserCommand(builder, callLetter, usercmd);
//			//回应参数
//			//IP, 端口
//			byte[] protocol = pkg.getProtocol();
//			
//			StringBuilder ip = new StringBuilder();
//			for(int i = 0; i < 4; i++){
//				ip.append(protocol[1 + i]).append(".");
//			}
//			
//			int port = Util.getShort(protocol, 5) & 0xFFFF;
//			
//			builder.addParams(ip.toString());
//			builder.addParams(String.valueOf(port));
//			
//			byte[] data = builder.build().toByteArray();
//			CmdResponseUtil.simpleCommandResponse(cache, data);
			
			CmdResponseUtil.simpleResponseSuccessByCachedCommand(cache);
		}
	}
	
	/**
	 * 设置短消息中心服务号码应答
	 * @throws IOException 
	 */
	private void handlerSetSmCenterAck(DB44Package pkg,
			UnitServer server, UnitSocketSession session) throws IOException{
		String callLetter = session.getUnitInfo().getCallLetter();
		String sn = CmdUtil.getCmdCacheSn(callLetter, CmdUtil.CMD_ID_SET_SM_CENTER_NUM);
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(sn);		
		if(cache != null) {
			Command usercmd = cache.getUserCommand();			
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			//设置回应的通用部分
			CmdResponseUtil.updateResponseProtoSuccessByUserCommand(builder, callLetter, usercmd);
			//回应参数
			//号码
			byte[] protocol = pkg.getProtocol();
			String call = DB44PkgUtil.readString(protocol, 1, 15);
			builder.addParams(call);

			//byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		}
	}
}