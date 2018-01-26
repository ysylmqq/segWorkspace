package cc.chinagps.gateway.unit.beforemarket.kaiyi.upload.cmd;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.buff.CommandBuff.CommandResponse.Builder;
import cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo;
import cc.chinagps.gateway.seat.cmd.CmdResponseUtil;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beforemarket.common.CheckLoginHandler;
import cc.chinagps.gateway.unit.beforemarket.common.pkg.BeforeMarketPackage;
import cc.chinagps.gateway.unit.beforemarket.common.util.BeforeMarketPkgUtil;
import cc.chinagps.gateway.unit.beforemarket.kaiyi.upload.KaiyiUploadUtil;
import cc.chinagps.gateway.unit.beforemarket.kaiyi.upload.beans.KaiyiGPSInfo;
import cc.chinagps.gateway.unit.beforemarket.kaiyi.upload.beans.KaiyiStatus;
import cc.chinagps.gateway.unit.beforemarket.kaiyi.util.KaiyiProtobufUtil;
import cc.chinagps.gateway.util.Constants;

/**
 * 控制指令应答
 */
public class Cmd05 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session) throws Exception {
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = BeforeMarketPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSn());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);
		
		if(cache != null){
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;
			
			//make protobuf
			Command usercmd = cache.getUserCommand();
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn(usercmd.getSn());
			builder.setCallLetter(callLetter);
			builder.setCmdId(usercmd.getCmdId());
			if(unit_ack_result == 0){
				builder.setResult(Constants.RESULT_SUCCESS);
				
				int position = 3;
				KaiyiGPSInfo gps = KaiyiGPSInfo.parse(body, position);
				KaiyiStatus status = KaiyiStatus.parse(body, position + KaiyiGPSInfo.DATA_LENGTH);
				
				GpsInfo pgps = KaiyiProtobufUtil.transformGPSInfo(callLetter, ppkg, gps, status, null, false);
				builder.addGpsInfos(pgps);
				
				//保存gps
				KaiyiUploadUtil.handleGPS(ppkg, server, session, gps, status, null, false);
			}else{
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}
			
			//byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		}else{
			//未找到缓存，可能是唤醒后的数据
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;
			
			int subCmdId = body[0] & 0xFF;
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn("");
			builder.setCallLetter(callLetter);
			builder.setCmdId(getInnerCmdId(subCmdId));
			
			if(unit_ack_result == 0){
				builder.setResult(Constants.RESULT_SUCCESS);
				
				int position = 3;
				KaiyiGPSInfo gps = KaiyiGPSInfo.parse(body, position);
				KaiyiStatus status = KaiyiStatus.parse(body, position + KaiyiGPSInfo.DATA_LENGTH);
				
				GpsInfo pgps = KaiyiProtobufUtil.transformGPSInfo(callLetter, ppkg, gps, status, null, false);
				builder.addGpsInfos(pgps);
				
				//保存gps
				KaiyiUploadUtil.handleGPS(ppkg, server, session, gps, status, null, false);
			}else{
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}
			
			builder.setUnitsn(ppkg.getHead().getSn() & 0xFFFF);
			server.getExportMQ().addCommandResponse(builder.build());
		}
		
		return true;
	}
	
	private static int getInnerCmdId(int subCmdId){
		switch (subCmdId) {
			case 0x1:		
				return 0x0004;
			case 0x3:
				return 0x000E;
			case 0x6:
				return 0x0013;
			case 0x9:
				return 0x00A3;
			default:
				return 0;
		}
	}
}