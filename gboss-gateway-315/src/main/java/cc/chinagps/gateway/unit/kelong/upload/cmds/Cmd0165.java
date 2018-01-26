package cc.chinagps.gateway.unit.kelong.upload.cmds;

import org.apache.log4j.Logger;

import cc.chinagps.gateway.StartServer;
import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.buff.CommandBuff.CommandResponse.Builder;
import cc.chinagps.gateway.seat.cmd.CmdResponseUtil;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.kelong.define.KeLongDefines;
import cc.chinagps.gateway.unit.kelong.pkg.KeLongPackage;
import cc.chinagps.gateway.unit.kelong.upload.bean.DataStorage;
import cc.chinagps.gateway.unit.kelong.upload.bean.KeLongBaseStationInfo;
import cc.chinagps.gateway.unit.kelong.upload.bean.KeLongCANInfo;
import cc.chinagps.gateway.unit.kelong.upload.bean.KeLongGPSInfo;
import cc.chinagps.gateway.unit.kelong.util.KeLongPkgUtil;
import cc.chinagps.gateway.unit.kelong.util.KeLongUploadUtil;
import cc.chinagps.gateway.util.Constants;

/**
 * 
* @ClassName: Cmd0165
* @Description:查车指令后收到的位置数据
* @author dy
* @date 2017年5月23日 下午2:58:05
*
 */
public class Cmd0165 extends CheckLoginHandler {
	private Logger logger_debug = Logger.getLogger(Cmd0165.class);
	@Override
	public boolean handlerPackageSessionChecked(KeLongPackage pkg, UnitServer server, UnitSocketSession session)
			throws Exception {
		try {
			byte data[] = pkg.getData();
			int pos = 0 ;
			KeLongGPSInfo keLongGPSInfo = KeLongGPSInfo.parse(data, pos);
			pos += 49;
			if (!keLongGPSInfo.isLoc()) {
				KeLongBaseStationInfo baseStation = KeLongBaseStationInfo.parse(data, pos);
				keLongGPSInfo.setKeLongBaseStationInfo(baseStation);
			}
			
			boolean isHistory = keLongGPSInfo.isHistory();
			String callLetter = session.getUnitInfo().getCallLetter();
			//从缓存中取CAN中的数据 
			if(null != DataStorage.getCanMap().get(callLetter)){				
				KeLongCANInfo can= DataStorage.getCanMap().get(callLetter);
				//can缓存中的时间和GPS时间相差3分钟之内
				if(Math.abs(keLongGPSInfo.getGpsTime()-can.getCanTime())<=KeLongDefines.CONSTANT){
				    keLongGPSInfo.getKeLongOBDInfo().setRemainOil(can.getRemainingFuel());
				}
			}
			logger_debug.debug("[KeLong][cmd0165]KeLongGPSInfo:" + keLongGPSInfo);
			// 保存gps
			KeLongUploadUtil.handleGPS(pkg, server, session, keLongGPSInfo, isHistory);	
			
			String cacheSN = KeLongPkgUtil.getCmdCacheSn(callLetter, pkg.getHead().getSerialNo());
			ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setCallLetter(callLetter);
			int unit_ack_result = 0;
			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);
			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}
			
			if (cache != null) {
				Command usercmd = cache.getUserCommand();
				logger_debug.debug("[KeLong] cmd0165 get command from cache,cmd[" + usercmd.getCmdId()
						+ "] unit_ack_result:" + unit_ack_result);
				builder.setSn(usercmd.getSn());
				builder.setCmdId(usercmd.getCmdId());
				CmdResponseUtil.simpleCommandResponse(cache, builder);
			} else {
				builder.setSn("");
				builder.setCmdId(0x0001);
				builder.setUnitsn(pkg.getHead().getSerialNo() & 0xFFFF);
				server.getExportMQ().addCommandResponse(builder.build());
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return true;
	}
}