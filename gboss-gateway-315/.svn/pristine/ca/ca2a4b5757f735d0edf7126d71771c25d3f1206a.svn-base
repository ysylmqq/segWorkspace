package cc.chinagps.gateway.unit.seg.upload.cmds;

import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.buff.CommandBuff.CommandResponse.Builder;
import cc.chinagps.gateway.seat.cmd.CmdResponseUtil;
import cc.chinagps.gateway.seat.cmd.CmdUtil;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.seg.pkg.SegPackage;
import cc.chinagps.gateway.unit.seg.upload.SegUploadUtil;
import cc.chinagps.gateway.unit.seg.util.SegPkgUtil;
import cc.chinagps.gateway.util.Constants;
import cc.chinagps.gateway.util.HexUtil;
import cc.chinagps.gateway.util.Util;

/**
 * 开车门应答
 * 锁车门应答
 * (*)定时报告请求确认
 * 
 * 找车应答(OBD)
 * 读取车型应答(OBD)
 * 标定车型应答(OBD)
 * 清除故障码应答(OBD)
 */
public class Cmd10 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(SegPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception {
		String strBody = new String(pkg.getBody(), SegPkgUtil.PKG_CHAR_ENCODING);
		if(strBody.startsWith("(DCC")){
			//锁车门应答
			Cmd90.handlerLockDoorAck(pkg, server, session, strBody);
			return true;
		}else if(strBody.startsWith("(DOC")){
			//开车门应答
			Cmd90.handlerOpenDoorAck(pkg, server, session, strBody);
			return true;
		}else if(strBody.startsWith("(FNS,ITV,FF") || strBody.startsWith("(FNS,ITV,00")){
			//开启/关闭定时报告应答
			Cmd93.handlerUploadGPSByIntervalSetting(pkg, server, session, strBody);
			return true;
		}else if(strBody.startsWith("(FDC")){
			//找车应答(OBD)
			SegUploadUtil.commonResponseWithGPS(pkg, server, session, strBody, CmdUtil.CMD_ID_SEARCH_VEHICLE);
			return true;
		}else if(strBody.startsWith("(SAF,162,")){
			//读取车型应答(OBD)
			String callLetter = session.getUnitInfo().getCallLetter();
			String sn = CmdUtil.getCmdCacheSn(callLetter, CmdUtil.CMD_ID_READ_DEFINED_TYPE);
			ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(sn);
			if(cache != null) {
				Command usercmd = cache.getUserCommand();
				
				Builder builder = CommandBuff.CommandResponse.newBuilder();
				String resStatus = strBody.substring(9, 11);
				boolean success = "00".equals(resStatus);
				
				CmdResponseUtil.updateResponseProtoByUserCommand(builder, callLetter, usercmd, success? Constants.RESULT_SUCCESS: Constants.RESULT_UNIT_ACK_FAIL);				
				if(success){
					String resSn = strBody.substring(12, 36);
					String resVer = strBody.substring(36, 38);
					String resType = strBody.substring(38, 42);
					String resRes = strBody.substring(42, 44);
					String resSupDis = strBody.substring(44, 46);
					String resSupTPMS = strBody.substring(46, 48);
					
					builder.addParams(resSn);
					builder.addParams(resVer);
					builder.addParams(resType);
					builder.addParams(resRes);
					builder.addParams(resSupDis);
					builder.addParams(resSupTPMS);
				}
				
				//byte[] data = builder.build().toByteArray();
				CmdResponseUtil.simpleCommandResponse(cache, builder);
			}
			
			return true;
		}else if(strBody.startsWith("(SAF,164,")){
			//标定车型应答(OBD)
			String callLetter = session.getUnitInfo().getCallLetter();
			String sn = CmdUtil.getCmdCacheSn(callLetter, CmdUtil.CMD_ID_DEFINED_TYPE);
			ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(sn);
			if(cache != null) {
				Command usercmd = cache.getUserCommand();
				
				Builder builder = CommandBuff.CommandResponse.newBuilder();
				String resStatus = strBody.substring(9, 11);
				boolean success = "00".equals(resStatus);
				CmdResponseUtil.updateResponseProtoByUserCommand(builder, callLetter, usercmd, success? Constants.RESULT_SUCCESS: Constants.RESULT_UNIT_ACK_FAIL);				
				if(success){
					String resType = strBody.substring(12, 16);
					builder.addParams(resType);
				}
				
				//byte[] data = builder.build().toByteArray();
				CmdResponseUtil.simpleCommandResponse(cache, builder);
			}
			
			return true;
		}else if(strBody.startsWith("(SAF,141,")){
			//清除故障码应答(OBD)
			String callLetter = session.getUnitInfo().getCallLetter();
			String sn = CmdUtil.getCmdCacheSn(callLetter, CmdUtil.CMD_ID_DEFINED_TYPE);
			ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(sn);
			if(cache != null) {
				Command usercmd = cache.getUserCommand();				
				Builder builder = CommandBuff.CommandResponse.newBuilder();
				String resStatus = strBody.substring(9, 11);				
				CmdResponseUtil.updateResponseProtoByUserCommand(builder, callLetter, usercmd, "00".equals(resStatus)? Constants.RESULT_SUCCESS: Constants.RESULT_UNIT_ACK_FAIL);
				
				//byte[] data = builder.build().toByteArray();
				CmdResponseUtil.simpleCommandResponse(cache, builder);
			}
			
			return true;
		}else if(strBody.startsWith("(FNS,UPS,")){
			String callLetter = session.getUnitInfo().getCallLetter();
			String sn = CmdUtil.getCmdCacheSn(callLetter, CmdUtil.CMD_ID_SET_UPGRADE_PARAM);
			ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(sn);
			if(cache != null) {
				Command usercmd = cache.getUserCommand();				
				Builder builder = CommandBuff.CommandResponse.newBuilder();
				String res = strBody.substring(9, strBody.length() - 1);
				String params[] = res.split(",");
				if (params.length == 11) {
					String flag = params[0];
					flag = Integer.valueOf(flag, 16)+"";
					String protocolType = params[1];
					String type = params[2];
					flag = Integer.valueOf(type, 16)+"";
					String auto = params[3];
					flag = Integer.valueOf(auto, 16)+"";
					String serverIp = params[4];
					serverIp = Util.getIP(HexUtil.hexToBytes(serverIp), 0);
					String apn = params[5];
					String serverPort = params[6];
					String localPort = params[7];
					String userName = params[8];
					String pwd = params[9];
					String fileName = params[10];
					CmdResponseUtil.updateResponseProtoByUserCommand(builder,
							callLetter, usercmd, Constants.RESULT_SUCCESS);
					builder.addParams(flag);
					builder.addParams(protocolType);
					builder.addParams(type);
					builder.addParams(auto);
					builder.addParams(serverIp);
					builder.addParams(apn);
					builder.addParams(serverPort);
					builder.addParams(localPort);
					builder.addParams(userName);
					builder.addParams(pwd);
					builder.addParams(fileName);
				} else {
					CmdResponseUtil
							.updateResponseProtoByUserCommand(builder,
									callLetter, usercmd,
									Constants.RESULT_UNIT_ACK_FAIL);
				}
				
				CmdResponseUtil.simpleCommandResponse(cache, builder);
			}
			
			return true;
		}else if(strBody.startsWith("(OPO")){
			//关闭维修模式应答(OBD)
			SegUploadUtil.commonResponseWithGPS(pkg, server, session, strBody, CmdUtil.CMD_ID_CLOSE_REPAIR_MODE);
			return true;
		}else if(strBody.startsWith("(CLO")){
			//开启维修模式应答(OBD)
			SegUploadUtil.commonResponseWithGPS(pkg, server, session, strBody, CmdUtil.CMD_ID_OPEN_REPAIR_MODE);
			return true;
		}
		
		return false;
	}
}