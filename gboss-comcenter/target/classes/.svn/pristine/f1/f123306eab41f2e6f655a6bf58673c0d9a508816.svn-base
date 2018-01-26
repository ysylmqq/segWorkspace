/*
********************************************************************************************
Discription:  客户端发送链路检测报文，回复链路检测应答
			  
			  
Written By:   ZXZ
Date:         2014-04-28
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.outinterface;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.RSAPublicKeySpec;
import com.google.protobuf.ByteString;
import cc.chinagps.gboss.comcenter.CenterClientManager;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage;
import cc.chinagps.gboss.comcenter.buff.seginterfaceDataBuff.Login;
import cc.chinagps.gboss.comcenter.buff.seginterfaceDataBuff.Login_ACK;
import cc.chinagps.gboss.comcenter.websocket.ClientBaseHandler;
import cc.chinagps.lib.util.SystemConfig;
import cc.chinagps.lib.util.Util;

public class LoginHandler extends ClientBaseHandler {
	private OutInterfaceServerHandler serverhandler;
	private String username = "";	//登录名
	private String srcnodeid = "";
	private String dstnodeid = "";
	private String password = "";	//登录密码
	private long logintime = 0;
	private ByteString rsamodules = null;
	private ByteString rsapublickey = null;
	
	public LoginHandler(OutInterfaceServerHandler handler, ComCenterMessage.ComCenterBaseMessage basemsg, OutInterfaceClientInfo info) {
		super(basemsg, info);
		this.serverhandler = handler;
	}

	@Override
	public int decode() {
		try {
			Login login = Login.parseFrom(msgcontent);
			username = login.getUsername();
			username = username.replace('/', '_');	//名称不能带/字符
			srcnodeid = login.getSrcnode();
			dstnodeid = login.getDstnode();
			password = login.getPassword().trim();
			logintime = login.getLogintime();
			rsamodules = login.getRsamodules();
			rsapublickey = login.getRsapublickey();
        } catch(Exception e) {
			e.printStackTrace();
			retcode = ResultCode.Decode_Error;
			retmsg = "解码失败";
		}
		return retcode;
	}

	@Override
	public void run() {
		//判断时间是否是现在，前后不能相差2小时
		long nowtime = System.currentTimeMillis() - logintime;
		if (nowtime > 7200000 || nowtime < -7200000) {
			retcode = ResultCode.Time_Error;
			retmsg = "时间错误";
			return;
		}
		//从配置文件customer.properties读此用户信息
		String strproperties = SystemConfig.getCustomerproperties(username);
		if (strproperties == null) {
			retcode = ResultCode.UserName_Error;
			retmsg = "登录名错误";
			return;
		}
		//密码，固定IP，机构编号
		String strfield[] = strproperties.split(";");
		if (strfield.length < 3) {
			retcode = ResultCode.Parameters_Error;
			retmsg = "参数错误";
			return;
		}
		//判断IP是否正确, 多个IP地址用
		//0.0.0.0表示不校验IP
		boolean bfind = strfield[1].contains("0.0.0.0");
		if (!bfind) {
			String strips[] = strfield[1].trim().split(",");
			for(int i=0; i<strips.length; i++) {
				bfind = clientinfo.ipaddr.contains(strips[i].trim());
				if (bfind) {
					break;
				}
			}
		}
		if (!bfind) {
			retcode = ResultCode.Other_Error;
			retmsg = "IP地址校验错误";
			return;
		}
		//判断密码是否正确
		String strpassword = (new Long(logintime)).toString() + strfield[0].trim();
		strpassword = Util.MD5(strpassword).substring(10, 20).toUpperCase();
		if (!strpassword.equalsIgnoreCase(this.password)) {
			retcode = ResultCode.Password_Error;
			retmsg = "密码错误";
			return;
		}
		
		//先不判断登录名和密码, 直接返回成功
		clientinfo.setLogin(true);
		clientinfo.username = username.trim();
		//添加到客户端管理容器
		String orgnos = strfield[2].trim();
		((OutInterfaceClientInfo)clientinfo).setOrgnos(orgnos);
    	CenterClientManager.clientManager.addOutClient((OutInterfaceClientInfo)(clientinfo), orgnos);
    	strfield = null;
	}

	@Override
	public byte[] response(){
		//打登录返回的报文
		Login_ACK.Builder loginack = Login_ACK.newBuilder();
		loginack.setRetcode(retcode);
		loginack.setRetmsg(retmsg);
		loginack.setDstnode(srcnodeid);
		loginack.setSrcnode(dstnodeid);
		//如果登录成功, 则返回加密Key
		if (retcode == ResultCode.OK) {
			try {
				BigInteger rsaModule = new BigInteger(1, rsamodules.toByteArray());
				BigInteger publicExponent = new BigInteger(1, rsapublickey.toByteArray());
				
				KeyFactory keyFactory = KeyFactory.getInstance("RSA");
				RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(rsaModule, publicExponent);
				PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
				
				SecureRandom random = new SecureRandom();
				byte[] desKeyBytes = random.generateSeed(8);
				for(int i=0; i<8; i++) {
					desKeyBytes[i] ^= (byte)((logintime >> i) & 0xFF);
				}
				byte[] rsaDesKeyBytes = Util.encodeRSA(publicKey, desKeyBytes);
				loginack.setDeskey(ByteString.copyFrom(rsaDesKeyBytes));
				serverhandler.initDES(desKeyBytes);
				//serverhandler.islogined = true;
			} catch(Exception ex) {
				loginack.setRetcode(ResultCode.Other_Error);
				loginack.setRetmsg("创建加密密钥失败");
			}
		}
		return Serialize(MessageType.Login_ACK, loginack.build().toByteString()); 
	}

}
