package cc.chinagps.gateway.unit.useat.upload;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.RSAPublicKeySpec;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.jboss.netty.channel.Channel;
import org.seg.lib.buff.LoginAckBuff;
import org.seg.lib.buff.LoginBuff;
import org.seg.lib.net.Constant;
import org.seg.lib.net.data.Head;
import org.seg.lib.net.data.SegPackage;
import org.seg.lib.util.SegPackageUtil;
import org.seg.lib.util.Util;

import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.buff.InnerDataBuff;
import cc.chinagps.gateway.buff.InnerDataBuff.MapEntry;
import cc.chinagps.gateway.buff.InnerDataBuff.Units.Builder;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.useat.util.USeatUtil;
import cc.chinagps.gateway.util.Constants;
import cc.chinagps.gateway.web.util.WebUtil;

import com.google.protobuf.ByteString;

public class SegPkgHandler {
	public void handlePackage(SegPackage pkg, UnitServer server, UnitSocketSession session, USeatStreamHandler useatHandler) throws Exception {
		if(pkg.getHead().getMessageId() == Constant.MESSAGE_ID_LOGIN){
			handleLogin(pkg, server, session, useatHandler);
		}else if(pkg.getHead().getMessageId() == Constant.MESSAGE_ID_LOGOUT){
			handleLogout(pkg, server, session, useatHandler);
		}else if(pkg.getHead().getMessageId() == Constant.MESSAGE_ID_ACTIVETEST){		
			handleActiveTest(pkg, server, session, useatHandler);
		}else{
			handleOthers(pkg, server, session, useatHandler);
		}
	}
	
	protected void handleOthers(SegPackage pkg, UnitServer unitServer, UnitSocketSession session, USeatStreamHandler useatHandler) throws Exception{
		Head head = pkg.getHead();
		byte[] realBody = pkg.getRealBody();
		if(head.getMessageId() == Constants.INNER_CMD_ID_CMD){
			CommandBuff.Command cmd = CommandBuff.Command.parseFrom(realBody);
			unitServer.sendCommandByUSeat(cmd, session);
		}else if(head.getMessageId() == Constants.INNER_CMD_ID_LOAD_VEHICLE){
			//byte flag = realBody[0];
			//session.setAttribute(Constants.SESSION_KEY_RECEIVE_UNIT_LOGIN_CHANGE, flag == 1);	
			Map<Channel, UnitSocketSession> map = unitServer.getSessionManager().getSocketSessionMap();
			Builder builder = InnerDataBuff.Units.newBuilder();
			Iterator<UnitSocketSession> it = map.values().iterator();
			while(it.hasNext()){
				UnitSocketSession unitSocketSession = it.next();
				if(unitSocketSession.getUnitInfo() != null){
					builder.addCallLetters(unitSocketSession.getUnitInfo().getCallLetter());
				}
			}
			
			byte[] data = USeatUtil.segSeatPackageEncoder.encode(false, false, false, Constants.SYSTEM_ID_INT, Constants.INNER_CMD_ID_LOAD_VEHICLE_ACK, SegPackageUtil.getSerialNumber(), builder.build().toByteArray(), null);
			session.sendData(data);
		}else if(head.getMessageId() == Constants.INNER_CMD_ID_SHOW_STATUS){
			cc.chinagps.gateway.buff.InnerDataBuff.Status.Builder builder = InnerDataBuff.Status.newBuilder();
			
			Map<String, Object> map = WebUtil.getUnitServerBaseInfo(unitServer);
			Iterator<Entry<String, Object>> it = map.entrySet().iterator();
			while(it.hasNext()){
				Entry<String, Object> entry = it.next();
				builder.addInfos(MapEntry.newBuilder().setKey(entry.getKey()).setValue(String.valueOf(entry.getValue())));
			}
			
			byte[] data = USeatUtil.segSeatPackageEncoder.encode(false, false, false, 
					Constants.SYSTEM_ID_INT, Constants.INNER_CMD_ID_SHOW_STATUS_ACK, 
					SegPackageUtil.getSerialNumber(), builder.build().toByteArray(), null);
			session.sendData(data);
		}else if(head.getMessageId() == Constants.INNER_CMD_ID_ADD_TRACE){
			String callLetter = new String(realBody);
			boolean success = unitServer.addTrace(callLetter, session);
			byte[] ack = new byte[1];
			ack[0] = (byte) (success? 1: 0);
			
			byte[] data = USeatUtil.segSeatPackageEncoder.encode(false, false, false, Constants.SYSTEM_ID_INT, Constants.INNER_CMD_ID_ADD_TRACE_ACK, SegPackageUtil.getSerialNumber(), ack, null);
			session.sendData(data);
		}else if(head.getMessageId() == Constants.INNER_CMD_ID_REMOVE_TRACE){
			unitServer.removeTrace(session);
			byte[] data = USeatUtil.segSeatPackageEncoder.encode(false, false, false, Constants.SYSTEM_ID_INT, Constants.INNER_CMD_ID_REMOVE_TRACE_ACK, SegPackageUtil.getSerialNumber(), Constants.ZERO_BYTES_DATA, null);
			session.sendData(data);
		}
	}
	
	protected int checkLogin(LoginBuff.Login login) throws Exception{
		return Constant.RESULT_SUCCESS;
	}
	
	//处理登录包
	protected void handleLogin(SegPackage pkg, UnitServer server, UnitSocketSession session, USeatStreamHandler useatHandler) throws Exception{
		Head head = pkg.getHead();
		int sequenceNo = head.getSequenceNo();
		
		byte[] realBody = pkg.getRealBody();
		LoginBuff.Login loginBuff = LoginBuff.Login.parseFrom(realBody);

		boolean isCRC = false;
		int loginResult = checkLogin(loginBuff);
		LoginAckBuff.LoginAck.Builder loginAckBuilder = LoginAckBuff.LoginAck.newBuilder();
		if(loginResult != Constant.RESULT_SUCCESS){
			//登录失败
			loginAckBuilder.setResult(loginResult);
			byte[] loginAckBodyBuff = loginAckBuilder.build().toByteArray();
			
			byte[] data = USeatUtil.segSeatPackageEncoder.encode(false, false, isCRC, Constants.SYSTEM_ID_INT, Constant.MESSAGE_ID_LOGIN_ACK, sequenceNo, loginAckBodyBuff, useatHandler.getDesKey());
			session.sendData(data);
			return;
		}
		
		//登录成功
		loginAckBuilder.setResult(Constant.RESULT_SUCCESS);
		//session.setLogin(true);
		
		if(loginBuff.hasRsaModule() && loginBuff.hasRsaKey()){
			//rsa加密
			byte[] rsaModuleBytes = loginBuff.getRsaModule().toByteArray();
			byte[] rsaPublicExponentBytes = loginBuff.getRsaKey().toByteArray();
			
			BigInteger rsaModule = new BigInteger(1, rsaModuleBytes);
			BigInteger publicExponent = new BigInteger(1, rsaPublicExponentBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(rsaModule, publicExponent);
			PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
			
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
			SecureRandom random = new SecureRandom();
			byte[] desKeyBytes = random.generateSeed(8);
			//System.out.println("server-desKey:" + Util.getBytesString(desKeyBytes));
			DESKeySpec desKeySpec = new DESKeySpec(desKeyBytes);
			SecretKey DESKey = secretKeyFactory.generateSecret(desKeySpec);
			//记录DESKey
			useatHandler.setDesKey(DESKey);
			//ack
			byte[] rsaDesKeyBytes = Util.encodeRSA(publicKey, desKeyBytes);
			loginAckBuilder.setDesKey(ByteString.copyFrom(rsaDesKeyBytes));
		}
		
		byte[] bodyBytes = loginAckBuilder.build().toByteArray();
		
		byte[] data = USeatUtil.segSeatPackageEncoder.encode(false, false, isCRC, Constants.SYSTEM_ID_INT, Constant.MESSAGE_ID_LOGIN_ACK, sequenceNo, bodyBytes, useatHandler.getDesKey());
		session.sendData(data);
	}
	
	//处理链路检测
	protected void handleActiveTest(SegPackage pkg, UnitServer server, UnitSocketSession session, USeatStreamHandler useatHandler) throws Exception{
		Head head = pkg.getHead();
		int sequenceNo = head.getSequenceNo();
		
		byte[] data = USeatUtil.segSeatPackageEncoder.encode(false, false, false, Constants.SYSTEM_ID_INT, Constant.MESSAGE_ID_ACTIVETEST_ACK, sequenceNo, null, useatHandler.getDesKey());
		session.sendData(data);
	}
	
	protected void handleLogout(SegPackage pkg, UnitServer server, UnitSocketSession session, USeatStreamHandler useatHandler){
		session.close();
	}
}