package cc.chinagps.gateway.unit.beforemarket.kaiyi.upload.cmd;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.aplan.pkg.APlanUtil;
import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.buff.CommandBuff.CommandResponse.Builder;
import cc.chinagps.gateway.seat.cmd.CmdResponseUtil;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beforemarket.common.CheckLoginHandler;
import cc.chinagps.gateway.unit.beforemarket.common.pkg.BeforeMarketPackage;
import cc.chinagps.gateway.unit.beforemarket.common.util.BeforeMarketConstants;
import cc.chinagps.gateway.unit.beforemarket.common.util.BeforeMarketPkgUtil;
import cc.chinagps.gateway.unit.beforemarket.kaiyi.upload.KaiyiUploadUtil;
import cc.chinagps.gateway.unit.beforemarket.kaiyi.upload.beans.RequestFileInfo;
import cc.chinagps.gateway.util.Constants;
import cc.chinagps.gateway.util.IOUtil;
import cc.chinagps.gateway.util.SystemConfig;

public class Cmd1A extends CheckLoginHandler{
	//private boolean is_update_server;
	private String update_file_name_encoding;
	private String update_file_root;
	
	public Cmd1A(){
		//is_update_server = Boolean.valueOf(SystemConfig.getSystemProperty("is_update_server"));
		update_file_name_encoding = SystemConfig.getSystemProperty("update_file_name_encoding");
		update_file_root = SystemConfig.getSystemProperty("update_file_root");
	}
	
	@Override
	public boolean handlerPackageSessionChecked(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session) throws Exception {
		byte[] body = ppkg.getBody();
		int sumCmdId = body[0] & 0xFF;
		
		if(sumCmdId == 0x11){
			//通知终端升级应答
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;
			KaiyiUploadUtil.commonResponseUseHeadSN(ppkg, server, session, unit_ack_result, 0x00A5);
			return true;
		}else if(sumCmdId == 0x12){
			//终端申请文件信息
			//if(!is_update_server){
			if(!Constants.IS_UPDATE_SERVER){
				//不是车台升级服务器
				ackRequestFileInfoFail(ppkg, server, session);
				return true;
			}
			
			//文件不存在
			String fileName = APlanUtil.getCString(body, 1, 20, update_file_name_encoding);
			File file = new File(update_file_root, fileName);
			if(!file.exists() || !file.isFile()){
				ackRequestFileInfoFail(ppkg, server, session);
				return true;
			}
			
			//返回文件信息
			int buffSize = Util.getShort(body, 21) & 0xFFFF;
			long length = file.length();
			long pkgs = length / buffSize + (length % buffSize == 0? 0: 1);
			int crc16 = cc.chinagps.gateway.util.Util.getCRC16File(file);
			
			byte[] ack_body = new byte[29];
			ack_body[0] = 0x2;
			System.arraycopy(body, 1, ack_body, 1, 20);
			
			byte[] bs_length = Util.getIntByte((int) length);
			byte[] bs_pkgs = Util.getShortByte((short) pkgs);
			byte[] bs_crc16 = org.seg.lib.util.Util.getShortByte((short) crc16);
			
			System.arraycopy(bs_length, 0, ack_body, 21, bs_length.length);
			System.arraycopy(bs_pkgs, 0, ack_body, 25, bs_pkgs.length);
			System.arraycopy(bs_crc16, 0, ack_body, 27, bs_crc16.length);
			
			sendDataByBody(ppkg, server, session, ack_body);
			
			//缓存文件信息
			RequestFileInfo requestFileInfo = new RequestFileInfo();
			requestFileInfo.setFileName(fileName);
			requestFileInfo.setBuffLength(buffSize);
			requestFileInfo.setMaxPkgs((int) pkgs);
			session.setAttribute(BeforeMarketConstants.BM_SESSION_KEY_REQUEST_FILE_INFO, requestFileInfo);
			
			return true;
		}else if(sumCmdId == 0x13){
			//终端申请文件数据包
			//if(!is_update_server){
			if(!Constants.IS_UPDATE_SERVER){
				//不是车台升级服务器
				ackRequestFileDataFail(ppkg, server, session);
				return true;
			}
			
			Object obj = session.getAttribute(BeforeMarketConstants.BM_SESSION_KEY_REQUEST_FILE_INFO);
			if(obj == null){
				//没有文件信息
				ackRequestFileDataFail(ppkg, server, session);
				return true;
			}
			
			RequestFileInfo fileInfo = (RequestFileInfo) obj;
			int pkgIndex = Util.getShort(body, 1) & 0xFFFF;
			
			if(pkgIndex <= 0 || pkgIndex > fileInfo.getMaxPkgs()){
				//文件序号错误
				ackRequestFileDataFail(ppkg, server, session);
				return true;
			}
			
			long startPosition = 1L * fileInfo.getBuffLength() * (pkgIndex - 1);
			File file = new File(update_file_root, fileInfo.getFileName());
			long len2 = file.length() - startPosition;
			int len = (int) Math.min(len2, fileInfo.getBuffLength());
			byte[] ack_body = new byte[len + 3];
			ack_body[0] = 0x3;
			System.arraycopy(body, 1, ack_body, 1, 2);
			
			FileInputStream fis = null;
			try{
				fis = new FileInputStream(file);
				fis.skip(startPosition);
				fis.read(ack_body, 3, len);
			}finally{
				IOUtil.closeIS(fis);
			}
			
			sendDataByBody(ppkg, server, session, ack_body);
			return true;
		}else if(sumCmdId == 0x14){
			//升级成功,上报版本号
			int success = Util.getShort(body, 1) & 0xFFFF;
			String version = APlanUtil.getCString(body, 3, 4);
			KaiyiUploadUtil.handleVersion(server, session.getUnitInfo().getCallLetter(), version, success);
			
			byte[] ack_body = new byte[3];
			ack_body[0] = 0x4;
			sendDataByBody(ppkg, server, session, ack_body);
			
			return true;
		}else if(sumCmdId == 0x15){
			//查询升级状态应答
			queryUpdateStatusAck(ppkg, server, session);
			return true;
		}
		
		return false;
	}
	
	/**
	 * 应答申请数据包失败
	 */
	private void ackRequestFileDataFail(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session){
		byte[] body = ppkg.getBody();
		
		byte[] ack_body = new byte[3];
		ack_body[0] = 0x3;
		System.arraycopy(body, 1, ack_body, 1, 2);
		
		sendDataByBody(ppkg, server, session, ack_body);
	}
	
	/**
	 * 应答请求文件信息失败
	 */
	private void ackRequestFileInfoFail(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session){
		byte[] body = ppkg.getBody();
		
		byte[] ack_body = new byte[29];
		ack_body[0] = 0x2;
		System.arraycopy(body, 1, ack_body, 1, 20);
		
		sendDataByBody(ppkg, server, session, ack_body);
	}
	
	private void sendDataByBody(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session, byte[] ack_body){
		BeforeMarketPackage rpkg = new BeforeMarketPackage();
		rpkg.setHead(ppkg.getHead());
		rpkg.setBody(ack_body);			
		int c1 = BeforeMarketPkgUtil.getC1(session);
		int d1 = BeforeMarketPkgUtil.getD1(session);
		byte[] source = rpkg.encode(c1, d1);
		
		session.sendData(source);
	}
	
	/**
	 * 查询升级状态应答
	 * @throws IOException 
	 */
	private void queryUpdateStatusAck(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session) throws IOException{
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = BeforeMarketPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSn());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);
		
		if(cache != null){
			byte[] body = ppkg.getBody();
			//make protobuf
			Command usercmd = cache.getUserCommand();
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn(usercmd.getSn());
			builder.setCallLetter(callLetter);
			builder.setCmdId(usercmd.getCmdId());
			builder.setResult(Constants.RESULT_SUCCESS);
			
			int result = Util.getShort(body, 1) & 0xFFFF;
			String currentVersion = APlanUtil.getCString(body, 3, 4);
			String updateVersion = APlanUtil.getCString(body, 7, 4);
			long totalSize = Util.getInt(body, 11) & 0xFFFFFFFFL;
			long receivedSize = Util.getInt(body, 15)& 0xFFFFFFFFL;
			
			builder.addParams(String.valueOf(result));
			builder.addParams(currentVersion);
			builder.addParams(updateVersion);
			builder.addParams(String.valueOf(totalSize));
			builder.addParams(String.valueOf(receivedSize));
			
			//byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		}
	}
}