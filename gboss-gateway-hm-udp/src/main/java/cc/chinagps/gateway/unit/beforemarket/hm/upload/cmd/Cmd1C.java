package cc.chinagps.gateway.unit.beforemarket.hm.upload.cmd;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.seg.lib.util.Util;

import cc.chinagps.gateway.aplan.pkg.APlanUtil;
import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.buff.CommandBuff.CommandResponse.Builder;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.memcache.MemcacheManager;
import cc.chinagps.gateway.seat.cmd.CmdResponseUtil;
import cc.chinagps.gateway.seat.cmd.CmdUtil;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beforemarket.common.CheckLoginHandler;
import cc.chinagps.gateway.unit.beforemarket.common.pkg.BeforeMarketPackage;
import cc.chinagps.gateway.unit.beforemarket.common.util.BeforeMarketConstants;
import cc.chinagps.gateway.unit.beforemarket.common.util.BeforeMarketPkgUtil;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.HMUploadUtil;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.beans.RequestFileInfo;
import cc.chinagps.gateway.util.Constants;
import cc.chinagps.gateway.util.HexUtil;
import cc.chinagps.gateway.util.IOUtil;
import cc.chinagps.gateway.util.SystemConfig;

/** ���У��ն���������������Ϣ
 * ����Զ������  ��Ϣ����0x1c
 * @author ysy
 *
 */
public class Cmd1C extends CheckLoginHandler{
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);

	//private boolean is_update_server;
	private String update_file_name_encoding;
	private String update_file_root;
	
	public Cmd1C(){
		//is_update_server = Boolean.valueOf(SystemConfig.getSystemProperty("is_update_server"));
		update_file_name_encoding = SystemConfig.getSystemProperty("update_file_name_encoding");
		update_file_root = SystemConfig.getSystemProperty("update_file_root");
	}
	
	@Override
	public boolean handlerPackageSessionChecked(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session) throws Exception {
		byte[] body = ppkg.getBody(); //��Ϣ�� 
		//����Ϣ����
		int sumCmdId = body[0] & 0xFF;
		logger_debug.debug("[cmd1C] recv data:"+HexUtil.byteToHexStr(ppkg.getSource()));
		// �ն�Ӧ������
		if(sumCmdId == 0x11){
			logger_debug.debug("cmd 0x11   start");
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF; // short 2���ֽ�  Ӧ����      0x00A5֪ͨ�ն�����
			int unit_canid_result = Util.getShort(body, 3) & 0xFFFF; // short 2���ֽ�  ����Ŀ��CAN ID  0X758
			// ���� ֪ͨ�ն�������Ӧ    
			HMUploadUtil.commonResponseUpgrade(ppkg, server, session, unit_ack_result, CmdUtil.CMD_ID_ENTIRE_CAR_OTA_UPGRADE,unit_canid_result);
			logger_debug.debug("cmd 0x11   end");
			return true;
		}else if(sumCmdId == 0x12){
			//�ն�������������ļ���Ϣ
			logger_debug.debug("cmd 0x12  start");
			if(!Constants.IS_UPDATE_SERVER){
				//���ǳ�̨����������
				ackRequestFileInfoFail(ppkg, server, session);
				return true;
			}
			
			//�ļ�������
			String fileName = APlanUtil.getCString(body, 1, 30, update_file_name_encoding);
			
			logger_debug.debug("fileName: "+fileName);

			File file = new File(update_file_root, fileName);
			if(!file.exists() || !file.isFile()){
				logger_debug.debug("file is not exist");
				ackRequestFileInfoFail(ppkg, server, session);
				return true;
			}
			
			//�����ļ���Ϣ
			int buffSize = Util.getShort(body, 31) & 0xFFFF; // ���ݰ���С
			
			long length = file.length(); // �ļ���С
			long pkgs = length / buffSize + (length % buffSize == 0? 0: 1); // �ܰ���
			
			int canId = Util.getShort(body, 33) & 0xFFFF; // canId 2���ֽ�
			
			//buffSize  512 length 126976  pkgs 248
			logger_debug.debug("buffSize  "+buffSize +" length "+length +"  pkgs "+pkgs+"   canId  "+canId);
			
			String memcacheKey = "ota_file_desc:"+fileName+":"+canId;
			String otaFileDesc = (String) MemcacheManager.getMemcachedClient().get(memcacheKey.trim());
			
			if("".equals(otaFileDesc) || null == otaFileDesc){
				logger_debug.debug("memcache is not file describe info,  key = "+memcacheKey+"; describe =  "+otaFileDesc);
				ackRequestFileInfoFail(ppkg, server, session);
				return true;
			}
			String fileDescs[] = otaFileDesc.split(","); // ��Ӧ��ID,�㲿��Id,�����汾��,Ӳ���汾��,��������,����˵��,MD5
			logger_debug.debug("memcache  file describe info:  "+otaFileDesc);
			
			byte[] bs_length = Util.getIntByte((int) length); // �ļ���С  ռ��4���ֽ� 
			byte[] bs_pkgs = Util.getShortByte((short) pkgs); // �ܱ��� ռ��2���ֽ�  
			
			//��Ӧ��ID�ӻ��浱�л�ȡ  12�ֽ�   CA_3_38247  
			String supplierId = fileDescs[0];
			byte[] bs_supplierId = APlanUtil.getBytesAddZero(supplierId,12); 
			
			//�㲿��ID 12�ֽ�   FA1A-66-9S1  
			String partsId = fileDescs[1];
			byte[] bs_partsId = APlanUtil.getBytesAddZero(partsId,12);
			
			//�����汾�� 4�ֽ�  H255   
			String softVersion = fileDescs[2];
			byte[] bs_softVersion = APlanUtil.getBytesAddZero(softVersion,4);
			
			//Ӳ���汾��  4�ֽ�  V130  Ĭ��   
			String hardWareVersion = fileDescs[3];
			byte[] bs_hardWareVersion = APlanUtil.getBytesAddZero(hardWareVersion,4);
			
			//�������� 1�ֽ�   Ĭ�ϵ�  0X01
			int updateType = Integer.parseInt(fileDescs[4]);
			byte bs_updateType = (byte) updateType;
			
			//����˵�� 64�ֽ� 
			String updateRemark = fileDescs[5];
			byte[] bs_updateRemark = APlanUtil.getBytesAddZero(updateRemark,64);
			
			//�ļ�У��MD5  32���ֽ�   133D494D8477A63C8E58D87162AD71A6
			String md5 =  fileDescs[6]; //�ӻ��浱���ó���
			byte[] bs_md5 = md5.getBytes(); // ���32���ֽ�
			if(bs_md5.length != 32){
				throw new Exception("md5 length is not 32 byte");
			}
			
			/*
			 * fileName: BCM_SC00_V130_H240_FABB_.bin
			   memcache  file describe info  key = 101,101,v1.0,v1.0,1,����OTA����,133D494D8477A63C8E58D87162AD71A6
			   bs_md5.length  32  bs_md5 133D494D8477A63C8E58D87162AD71A6
			 */
			logger_debug.debug("bs_md5.length  "+bs_md5.length+"  bs_md5 "+md5+" bs_updateType "+bs_updateType);
			
			byte[] ack_body = new byte[168];
			//��Ĭ����
			
			if( bs_updateType == 0X01 || bs_updateType == 0X02 || bs_updateType == 0X03 || bs_updateType == 0X04){
				ack_body = new byte[104];
			}
			
			ack_body[0] = 0x02;
			System.arraycopy(body, 1, ack_body, 1, 30); // �����ļ���
			System.arraycopy(bs_length, 0, ack_body, 31, 4);  // 4 
			System.arraycopy(bs_pkgs, 0, ack_body, 35, 2); // 2 
			System.arraycopy(body, 33, ack_body, 37, 2); // ��body���л�ȡ ����Ŀ��CAN ID   2  
			System.arraycopy(bs_supplierId, 0, ack_body, 39,12); // 12
			System.arraycopy(bs_partsId, 0, ack_body, 51, 12); // 12 
			System.arraycopy(bs_softVersion, 0, ack_body, 63, 4);//4
			System.arraycopy(bs_hardWareVersion, 0, ack_body, 67, 4); // 4
			ack_body[71] =  bs_updateType;
			
			if( bs_updateType == 0X01 || bs_updateType == 0X02 || bs_updateType == 0X03 || bs_updateType == 0X04){
				logger_debug.debug("1");
				System.arraycopy(bs_md5, 0, ack_body, 72, 32); // 32
			}else{
				logger_debug.debug("2");
				System.arraycopy(bs_updateRemark, 0, ack_body, 72, 64); // 64 
				System.arraycopy(bs_md5, 0, ack_body, 136, 32); // 32
			}
			logger_debug.debug("[cmd 0x12 ] send data:"+HexUtil.byteToHexStr(ack_body));

			sendDataByBody(ppkg, server, session, ack_body);
			
			//�����ļ���Ϣ
			RequestFileInfo requestFileInfo = new RequestFileInfo();
			requestFileInfo.setFileName(fileName);
			requestFileInfo.setBuffLength(buffSize);
			requestFileInfo.setMaxPkgs((int) pkgs);
			session.setAttribute(BeforeMarketConstants.HM_OTA_SESSION_KEY_REQUEST_FILE_INFO, requestFileInfo);
			logger_debug.debug("cmd 0x12    end");
			return true;
		}else if(sumCmdId == 0x13){
			//�ն������ļ����ݰ�
			logger_debug.debug("cmd 0x13   start");
			if(!Constants.IS_UPDATE_SERVER){
				//���ǳ�̨����������
				ackRequestFileDataFail(ppkg, server, session);
				return true;
			}
			
			Object obj = session.getAttribute(BeforeMarketConstants.HM_OTA_SESSION_KEY_REQUEST_FILE_INFO);
			if(obj == null){
				//û���ļ���Ϣ
				ackRequestFileDataFail(ppkg, server, session);
				return true;
			}
			
			RequestFileInfo fileInfo = (RequestFileInfo) obj;
			logger_debug.debug("[cmd1C] OTA upgrade file info:"+fileInfo);
			int pkgIndex = Util.getShort(body, 1) & 0xFFFF; // ����
			logger_debug.debug("[cmd1C] Tbox req pkg index:"+pkgIndex);
			if(pkgIndex <= 0 || pkgIndex > fileInfo.getMaxPkgs()){
				//�ļ���Ŵ���
				ackRequestFileDataFail(ppkg, server, session);
				return true;
			}
			
			long startPosition = 1L * fileInfo.getBuffLength() * (pkgIndex - 1);
			File file = new File(update_file_root, fileInfo.getFileName());
			long len2 = file.length() - startPosition;
			int len = (int) Math.min(len2, fileInfo.getBuffLength());
			byte[] ack_body = new byte[len + 5];
			ack_body[0] = 0x3;
			System.arraycopy(body, 1, ack_body, 1, 2);  //Ӧ�����ݰ�  ����
			System.arraycopy(body, 3, ack_body, 3, 2);  //CANʶ�� ID
			
			FileInputStream fis = null;
			try{
				fis = new FileInputStream(file);
				fis.skip(startPosition);
				fis.read(ack_body,5, len);
			}finally{
				IOUtil.closeIS(fis);
			}
			
			sendDataByBody(ppkg, server, session, ack_body);
			logger_debug.debug("cmd 0x13  end");
			return true;
		}else if(sumCmdId == 0x14){
			//�����ɹ�,�ϱ��汾��
			logger_debug.debug("cmd 0x14 start");
			int success = Util.getShort(body, 1) & 0xFFFF;
			logger_debug.debug("success "+success);
			String version = APlanUtil.getCString(body, 3, 4);
			int canId = Util.getShort(body, 7) & 0xFFFF;
			HMUploadUtil.handleVersion(server, session.getUnitInfo().getCallLetter(), version, success ,canId);
			
			//����Ӧ���ն���������ϱ�
			byte[] ack_body = new byte[3];
			ack_body[0] = 0x4;
			sendDataByBody(ppkg, server, session, ack_body);
			
			logger_debug.debug("cmd 0x14  end");
			return true;
		}else if(sumCmdId == 0x15){
			//ͨ�����Ĳ�ѯ�������  �ն�Ӧ���ѯ����״̬ 
			logger_debug.debug("cmd 0x15  start");
			queryUpdateStatusAck(ppkg, server, session);
			logger_debug.debug("cmd 0x15  end");
			return true;
		}else if(sumCmdId == 0x16){
			//�ն��ϱ��ļ��������״̬ 
			logger_debug.debug("cmd 0x16  start");
			logger_debug.debug("unit uploads file transmission is complete! ");
			int transCompSt = body[3] & 0xFFFF; // �������״̬
			if(transCompSt == 0){
				logger_debug.debug(" transmission result success ");
			}else{
				logger_debug.debug(" transmission result failed ");
			}
			
			//ͬʱд��activemq���У�ͨ�����Ĵ�mq���ж�ȡ�������״̬��Ȼ����֪ͨ��OTA����ƽ̨
			
			//����Ӧ���ļ��������״̬ 
			byte[] ack_body = new byte[3];
			ack_body[0] = 0x06;
			System.arraycopy(body, 1, ack_body, 1, 2); // CANID
			sendDataByBody(ppkg, server, session, ack_body);
			logger_debug.debug("ack_body data"+HexUtil.byteToHexStr(ack_body));
			logger_debug.debug("cmd 0x16  end");
			return true;
		}
		
		return false;
	}
	
	/**
	 * Ӧ���������ݰ�ʧ��
	 */
	private void ackRequestFileDataFail(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session){
		byte[] body = ppkg.getBody();
		
		byte[] ack_body = new byte[5];
		ack_body[0] = 0x3;
		System.arraycopy(body, 1, ack_body, 1, 2);
		System.arraycopy(body, 3, ack_body, 3, 2);
		
		sendDataByBody(ppkg, server, session, ack_body);
	}
	
	/**
	 * Ӧ�������ļ���Ϣʧ�ܣ��������Ĳ�����Ҫ�������ļ� ����Ҫ�������ն˷�������
	 * ���� �����ļ���
	 */
	private void ackRequestFileInfoFail(BeforeMarketPackage ppkg,
			UnitServer server, UnitSocketSession session){
		byte[] body = ppkg.getBody();
		
		byte[] ack_body = new byte[104];
		ack_body[0] = 0x2;
		System.arraycopy(body, 1, ack_body, 1, 30);
		
		sendDataByBody(ppkg, server, session, ack_body);
	}
	
	/**
	 * �������� �����ն�Ӧ��
	 * @param ppkg
	 * @param server
	 * @param session
	 * @param ack_body
	 */
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
	 * �ն�Ӧ��TSP�Ĳ�ѯ����״̬
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
			
			int result = Util.getShort(body, 1) & 0xFFFF;  // Ӧ���������
			logger_debug.debug("update result "+result);
			String currentVersion = APlanUtil.getCString(body, 3, 4);
			String updateVersion = APlanUtil.getCString(body, 7, 4);
			long totalSize = Util.getInt(body, 11) & 0xFFFFFFFFL;
			long receivedSize = Util.getInt(body, 15)& 0xFFFFFFFFL;
			String updateFileName = APlanUtil.getCString(body, 19, 30);
			long totalPak = Util.getShort(body, 49)& 0xFFFFFFFFL;
			int canId = Util.getInt(body, 51) & 0xFFFF;
			long updateType = body[53] & 0xFFFFFFFFL;
			//String updateRemark = APlanUtil.getCString(body, 53, 64);
			/*String md5 = APlanUtil.getCString(body, 56, 32);*/

			builder.addParams(String.valueOf(result));
			builder.addParams(currentVersion);
			builder.addParams(updateVersion);
			builder.addParams(String.valueOf(totalSize));
			builder.addParams(String.valueOf(receivedSize));
			builder.addParams(updateFileName);
			builder.addParams(String.valueOf(totalPak));
			builder.addParams(String.valueOf(canId));
			builder.addParams(String.valueOf(updateType));
			//builder.addParams(updateRemark);
			//builder.addParams(md5);

			//byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		}
	}
}