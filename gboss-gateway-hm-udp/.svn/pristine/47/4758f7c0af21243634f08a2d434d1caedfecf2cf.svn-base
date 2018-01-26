package cc.chinagps.gateway.unit.beforemarket.hm.upload.cmd;

import java.io.File;

import cc.chinagps.gateway.aplan.pkg.APlanUtil;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beforemarket.common.CheckLoginHandler;
import cc.chinagps.gateway.unit.beforemarket.common.pkg.BeforeMarketPackage;
import cc.chinagps.gateway.unit.beforemarket.common.util.BeforeMarketPkgUtil;
import cc.chinagps.gateway.unit.update.DefaultUpdateFileInfo;
import cc.chinagps.gateway.util.SystemConfig;
import cc.chinagps.gateway.util.Util;

public class Cmd1B extends CheckLoginHandler{
	private boolean is_update_server;
	private String update_file_name_encoding;
	private String update_file_root;
	
	public Cmd1B(){
		is_update_server = Boolean.valueOf(SystemConfig.getSystemProperty("is_update_server"));
		update_file_name_encoding = SystemConfig.getSystemProperty("update_file_name_encoding");
		update_file_root = SystemConfig.getSystemProperty("update_file_root");
	}
	
	@Override
	public boolean handlerPackageSessionChecked(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session) throws Exception {
		byte[] body = ppkg.getBody();
		int sumCmdId = body[0] & 0xFF;
		
		if(sumCmdId == 0x12){
			//终端申请文件信息
			if(!is_update_server){
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
			long length = file.length();
			int crc16 = Util.getCRC16File(file);
			byte[] ack_body = new byte[27];
			ack_body[0] = 0x2;
			System.arraycopy(body, 1, ack_body, 1, 20);
			
			byte[] bs_length = org.seg.lib.util.Util.getIntByte((int) length);
			byte[] bs_crc16 = org.seg.lib.util.Util.getShortByte((short) crc16);
			
			System.arraycopy(bs_length, 0, ack_body, 21, bs_length.length);
			System.arraycopy(bs_crc16, 0, ack_body, 25, bs_crc16.length);
			
			sendDataByBody(ppkg, server, session, ack_body);
			return true;
		}else if(sumCmdId == 0x13){
			//终端传送文件请求
			if(!is_update_server){
				//不是车台升级服务器
				ackRequestFileDataFail(ppkg, server, session);
				return true;
			}
			
			//正在升级中
			if(session.isUpdating()){
				ackRequestFileDataFail(ppkg, server, session);
				return true;
			}
			
			//文件不存在
			String fileName = APlanUtil.getCString(body, 1, 20, update_file_name_encoding);
			File file = new File(update_file_root, fileName);
			if(!file.exists() || !file.isFile()){
				ackRequestFileInfoFail(ppkg, server, session);
				return true;
			}
			
			//起始位置超出文件范围
			long startPosition = org.seg.lib.util.Util.getInt(body, 21) & 0xFFFFFFFF;
			if(startPosition >= file.length()){
				ackRequestFileDataFail(ppkg, server, session);
				return true;
			}
			
			//应答成功
			byte[] ack_body = new byte[27];
			ack_body[0] = 0x3;
			System.arraycopy(body, 1, ack_body, 1, 20);
			byte[] bs_len = org.seg.lib.util.Util.getIntByte((int) (file.length() - startPosition));
			System.arraycopy(bs_len, 0, ack_body, 23, bs_len.length);
			sendDataByBody(ppkg, server, session, ack_body);
			
			//开始传送文件数据
			DefaultUpdateFileInfo updateFileInfo = new DefaultUpdateFileInfo(file, startPosition);
			session.startSendUpdateFile(updateFileInfo);
			return true;
		}
		
		return false;
	}
	
	/**
	 * 应答请求文件信息失败
	 */
	private void ackRequestFileInfoFail(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session){
		byte[] body = ppkg.getBody();
		
		byte[] ack_body = new byte[27];
		ack_body[0] = 0x2;
		System.arraycopy(body, 1, ack_body, 1, 20);
		
		sendDataByBody(ppkg, server, session, ack_body);
	}
	
	/**
	 * 应答获取文件数据失败
	 */
	private void ackRequestFileDataFail(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session){
		byte[] body = ppkg.getBody();
		
		byte[] ack_body = new byte[27];
		ack_body[0] = 0x3;
		System.arraycopy(body, 1, ack_body, 1, 20);
		ack_body[21] = (byte) 0xFF;
		ack_body[22] = (byte) 0xFF;
		
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
}