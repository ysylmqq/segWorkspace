package cc.chinagps.gateway.unit.seg.upload.cmds;

import org.apache.log4j.Logger;

import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.buff.CommandBuff.CommandResponse.Builder;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.seat.cmd.CmdResponseUtil;
import cc.chinagps.gateway.seat.cmd.CmdUtil;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.seg.pkg.SegPackage;
import cc.chinagps.gateway.unit.seg.util.SegPkgUtil;

/**
 * 限速/撤销限速应答
 */
public class CmdB1 extends CheckLoginHandler{
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	@Override
	public boolean handlerPackageSessionChecked(SegPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception {
		byte[] body = pkg.getBody();
		String strBody = new String(body, SegPkgUtil.PKG_CHAR_ENCODING);
		if(strBody.startsWith("(FNS,SPD,")){
			//if(logger_debug.isDebugEnabled()){
			//	logger_debug.debug("限速/撤销限速应答...:" + pkg.getSerialNumber());
			//}
			
			//String sn = pkg.getSerialNumber();
			String callLetter = session.getUnitInfo().getCallLetter();
			int flag = Integer.valueOf(strBody.substring(9, 12), 16);
			int cmdId = flag == 0? CmdUtil.CMD_ID_CANCEL_LIMIT_SPEED: CmdUtil.CMD_ID_LIMIT_SPEED;
			String sn = CmdUtil.getCmdCacheSn(callLetter, cmdId);
			ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(sn);
			if(cache != null) {
				Command usercmd = cache.getUserCommand();
				Builder builder = CommandBuff.CommandResponse.newBuilder();
				//设置回应的通用部分
				CmdResponseUtil.updateResponseProtoSuccessByUserCommand(builder, callLetter, usercmd);
				//speed
				int jieSpeed = Integer.valueOf(strBody.substring(9, 12));
				int speed = SegPkgUtil.jieToSpeed(jieSpeed);
				builder.addParams(String.valueOf(speed));
				
				//byte[] data = builder.build().toByteArray();
				CmdResponseUtil.simpleCommandResponse(cache, builder);
			}
			return true;
		}else if(strBody.startsWith("(FNS,CRS")){
			//北汽结束订单回应
			String callLetter = session.getUnitInfo().getCallLetter();
			int cmdId = CmdUtil.CMD_ID_FINISH_ORDER;
			String sn = CmdUtil.getCmdCacheSn(callLetter, cmdId);
			ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(sn);
			if(cache != null) {
				Command usercmd = cache.getUserCommand();
				Builder builder = CommandBuff.CommandResponse.newBuilder();
				String res = strBody.substring(9, 10);
				if ("0".equals(res))
					// 设置回应的通用部分
					CmdResponseUtil.updateResponseProtoSuccessByUserCommand(builder, callLetter, usercmd);
				else {
					if (res != null)
						CmdResponseUtil.updateResponseProtoByUserCommand(builder, callLetter, usercmd,
								Integer.valueOf(res));
					else
						CmdResponseUtil.updateResponseProtoFailedByUserCommand(builder, callLetter, usercmd);
				}
				CmdResponseUtil.simpleCommandResponse(cache, builder);
				logger_debug.debug("[seg][B1] CmdResponse:"+builder.build());
			}
			return true;
		}
		
		return false;
	}
}