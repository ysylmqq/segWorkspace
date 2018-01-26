package cc.chinagps.gateway.seat.cmd;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.seg.lib.net.server.tcp.SocketSession;
import org.seg.lib.util.SegPackageUtil;

import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.buff.CommandBuff.CommandResponse;
import cc.chinagps.gateway.buff.CommandBuff.CommandResponse.Builder;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.udp.UdpServer;
import cc.chinagps.gateway.unit.useat.util.USeatUtil;
import cc.chinagps.gateway.util.Constants;

public class CmdResponseUtil {
	private static final SimpleDateFormat sdf_local = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 转为本地时间
	 */
	public static String formatToLocal(Date date){
		synchronized (sdf_local) {
			return sdf_local.format(date);
		}
	}
	
	/**
	 * 常用的应答成功
	 */
	public static void simpleResponseSuccessByCachedCommand(ServerToUnitCommand cache){
		Command usercmd = cache.getUserCommand();
		
		Builder builder = CommandBuff.CommandResponse.newBuilder();
		builder.setSn(usercmd.getSn());
		builder.setCallLetter(cache.getCallLetter());
		builder.setCmdId(usercmd.getCmdId());
		builder.setResult(Constants.RESULT_SUCCESS);
		
		//byte[] data = builder.build().toByteArray();
		CmdResponseUtil.simpleCommandResponse(cache, builder);
	}
	
	/**
	 * 设置指令回应的统用部分(sn. callLetter, cmdId, result)
	 */
	public static void updateResponseProtoSuccessByUserCommand(Builder builder, String callLetter, Command usercmd){
		updateResponseProtoByUserCommand(builder, callLetter, usercmd, Constants.RESULT_SUCCESS);
	}
	
	/**
	 * 设置指令回应的统用部分(sn. callLetter, cmdId, result为失败)
	 */
	public static void updateResponseProtoFailedByUserCommand(Builder builder, String callLetter, Command usercmd){
		updateResponseProtoByUserCommand(builder, callLetter, usercmd, Constants.RESULT_FAIL_OTHERS);
	}
	
	/**
	 * 设置指令回应的统用部分(sn. callLetter, cmdId, result)
	 */
	public static void updateResponseProtoByUserCommand(Builder builder, String callLetter, Command usercmd, int result){
		builder.setSn(usercmd.getSn());
		builder.setCallLetter(callLetter);
		builder.setCmdId(usercmd.getCmdId());
		builder.setResult(result);
	}
	
	/**
	 * 根据用户指令信息及返回给用户的结果、信息生成指令返回数据
	 */
	private static Builder getCommandResponse(String callLetter, CommandBuff.Command userCmd, int result, String info){
		Builder builder = CommandBuff.CommandResponse.newBuilder();
		builder.setSn(userCmd.getSn());
		builder.setCallLetter(callLetter);
		builder.setCmdId(userCmd.getCmdId());
		builder.setResult(result);
		
		if(info != null){
			//builder.addParams(info);
			builder.setMsg(info);
		}
		
		//return builder.build().toByteArray();
		return builder;
	}
	
	/**
	 * 简单-通用 指令回应
	 * (缓存之前回应)
	 */
	public static void simpleCommandResponse(SocketSession userSession, UnitServer unitServer, String callLetter, CommandBuff.Command userCmd, int result, String info){
		//byte[] bodyData = getCommandResponse(callLetter, userCmd, result, info);
		Builder builder = getCommandResponse(callLetter, userCmd, result, info);
		if(userSession != null){
			//socket
			byte[] bodyData = builder.build().toByteArray();
			userSession.sendPackage(Constants.INNER_CMD_ID_RESPONSE, SegPackageUtil.getSerialNumber(), bodyData);
		}else{
			//mq
			unitServer.getExportMQ().addCommandResponse(builder.build());
		}
	}
	
	/**
	 * udp添加
	 * 简单-通用 指令回应
	 * (缓存之前回应)
	 */
	public static void simpleCommandResponse(SocketSession userSession, UdpServer udpServer, String callLetter, CommandBuff.Command userCmd, int result, String info){
		//byte[] bodyData = getCommandResponse(callLetter, userCmd, result, info);
		Builder builder = getCommandResponse(callLetter, userCmd, result, info);
		if(userSession != null){
			//socket
			byte[] bodyData = builder.build().toByteArray();
			userSession.sendPackage(Constants.INNER_CMD_ID_RESPONSE, SegPackageUtil.getSerialNumber(), bodyData);
		}else{
			//mq
			udpServer.getExportMQ().addCommandResponse(builder.build());
		}
	}
	
	/**
	 * 简单-通用 指令回应
	 * (缓存之前回应)
	 * @throws IOException 
	 */
	public static void simpleCommandResponse(UnitSocketSession userSession, UnitServer unitServer, String callLetter, CommandBuff.Command userCmd, int result, String info) throws IOException{
		//byte[] bodyData = getCommandResponse(callLetter, userCmd, result, info);
		Builder builder = getCommandResponse(callLetter, userCmd, result, info);
		if(userSession != null){
			//socket
			byte[] bodyData = builder.build().toByteArray();
			byte[] data = USeatUtil.segSeatPackageEncoder.encode(false, false, false, Constants.SYSTEM_ID_INT, Constants.INNER_CMD_ID_RESPONSE, SegPackageUtil.getSerialNumber(), bodyData, null);
			userSession.sendData(data);
		}else{
			//mq
			unitServer.getExportMQ().addCommandResponse(builder.build());
		}
	}
	
	/**
	 * udp添加
	 * 简单-通用 指令回应
	 * (缓存之前回应)
	 * @throws IOException 
	 */
	public static void simpleCommandResponse(UnitSocketSession userSession, UdpServer udpServer, String callLetter, CommandBuff.Command userCmd, int result, String info) throws IOException{
		//byte[] bodyData = getCommandResponse(callLetter, userCmd, result, info);
		Builder builder = getCommandResponse(callLetter, userCmd, result, info);
		if(userSession != null){
			//socket
			byte[] bodyData = builder.build().toByteArray();
			byte[] data = USeatUtil.segSeatPackageEncoder.encode(false, false, false, Constants.SYSTEM_ID_INT, Constants.INNER_CMD_ID_RESPONSE, SegPackageUtil.getSerialNumber(), bodyData, null);
			userSession.sendData(data);
		}else{
			//mq
			udpServer.getExportMQ().addCommandResponse(builder.build());
		}
	}
	
	/**
	 * 简单-通用 指令回应
	 * (缓存之后回应)
	 * @throws  
	 */
	public static void simpleCommandResponse(ServerToUnitCommand cache, CommandResponse.Builder builder){
		/*
		SocketSession userSession = cache.getUserSession();
		if(userSession == null){
			//回应到mq
			cache.getUnitServer().getExportMQ().addCommandResponse(data);
		}else{
			//回应到socket
			userSession.sendPackage(Constants.INNER_CMD_ID_RESPONSE, SegPackageUtil.getSerialNumber(), data);
		}*/
		SocketSession userSession = cache.getUserSession();
		if(userSession != null){
			byte[] data = builder.build().toByteArray();
			userSession.sendPackage(Constants.INNER_CMD_ID_RESPONSE, SegPackageUtil.getSerialNumber(), data);
			return;
		}
		
		UnitSocketSession useatSession = cache.getUSeatSession();
		if(useatSession != null){
			try {
				byte[] data = builder.build().toByteArray();
				byte[] pkg = USeatUtil.segSeatPackageEncoder.encode(false, false, false, Constants.SYSTEM_ID_INT, Constants.INNER_CMD_ID_RESPONSE, SegPackageUtil.getSerialNumber(), data, null);
				useatSession.sendData(pkg);
			} catch (IOException e) {
				cache.getUnitServer().exceptionCaught(e, "[I]");
			}
			
			return;
		}
		
		//回应到mq
		cache.getUnitServer().getExportMQ().addCommandResponse(builder.build());
	}
}