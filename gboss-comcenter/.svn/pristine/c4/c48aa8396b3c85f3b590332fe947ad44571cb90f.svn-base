/*
********************************************************************************************
Discription:  内部协议TCP连接服务器端基类
			  
Written By:   ZXZ
Date:         2014-05-15
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.interprotocol;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.RSAPublicKeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.lib.util.Util;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.ReadTimeoutException;

public abstract class InterProtocolServerHandler extends InterProtocolHandler {

	//服务器来用处理
	@Override
	protected void ProcessLoginAck(InterProtocolInfo recv) {}
	
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof ReadTimeoutException) {
        	if ((!islogined)  || (++readtimeoutTimes) >= 3) {
        		ctx.close();	//超时断开连接，朝野设置，请参考        pipeline.addLast("readTimeoutHandler", new ReadTimeoutHandler(60));
        	} else {
        		ActiveLink();
        	}
        } else if (cause instanceof java.io.IOException) {
        	//屏蔽Connection reset by peer消息
        	IOException ex = (IOException)cause;
        	if (!ex.getMessage().startsWith("Connection")) {
            	//cause.printStackTrace();
        	}
        	ctx.close();
        } else {
            //cause.printStackTrace();
            ctx.close();
        }
    }
    
    /*
	typedef struct _PACKET_LOGON
	{
	    SEGString<16> SrcNodeNo;    //登录方节点编号
	    SEGString<16> DstNodeNo;    //服务器节点编号
		SEGString<15> UserName;     //登录名
	    BYTE  Stamp[7];             //登录时间
		BYTE  Password[16];	        //把密码原文复制到Password, 如果长于16字节，只取前16字节, 如果不够16字节，则后补0(0x00)，
	                                //再用MD5加密(从STAMP开始, 共23字节长)，再将加密结果（16字节）复制到此字段
	    BYTE  Modulue[128];         //RSA模数，不够128字节，前补0(0x00)
	    BYTE  PublicKey[32];        //RSA公钥，不够32字节，前补0(0x00), 一般公钥都小于32字节
	}PACKET_LOGON;
     */
	@Override
	protected void ProcessLogin(InterProtocolInfo recv) throws Exception {
		this.islogined = false;
		remoteNodeId = Util.getByteZeroEndString(recv.content, 0, 16);
		String username = Util.getByteZeroEndString(recv.content, 32, 15);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String stamp = Util.bcd2str(recv.content, 47, 7);
		Date date;
		try {
			date = sdf.parse(stamp);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LoginAck(ResultCode.Time_Error, recv);
			return;
		}
		long ltime = date.getTime() - System.currentTimeMillis();
		//登录时间进过2小时误差
		if (ltime < -7200000 || ltime > 7200000) {
			LoginAck(ResultCode.Time_Error, recv);
			return;
		}
		byte[] stamppwd = new byte[7];
		byte[] password = new byte[16];
		System.arraycopy(recv.content, 47, stamppwd, 0, 7);
		System.arraycopy(recv.content, 54, password, 0, 16);
		//判断登录条件
		short retcode = OnLogin(username, stamppwd, password);
		LoginAck(retcode, recv);
	}
	
	//继承类，必须解决登录事件
	protected abstract short OnLogin(String username, byte[] stamp, byte[] encryptpassword); 
	/*
	typedef struct _PACKET_LOGON_ACK
	{
	    SEGString<16> SrcNodeNo;    //服务器节点编号
	    SEGString<16> DstNodeNo;    //登录方节点编号
	    BYTE DesKey[128];           //动态Des加密密钥，经公钥加密, 不够128字节，前补0(0x00)
	}PACKET_LOGON_ACK;
	 */
	private void LoginAck(short retcode, InterProtocolInfo recv) throws Exception {
		InterProtocolInfo send = new InterProtocolInfo();
		send.msgId = Constant.MESSAGE_ID_LOGIN_ACK;
		send.result = retcode;
		send.sequenceNo = recv.sequenceNo;
		//System.out.println("SequenceNo: " + recv.sequenceNo);
		send.version = recv.version;
		if (retcode == ResultCode.OK) {
			send.content = new byte[160];
		} else {
			send.content = new byte[32];
		}
		//互换节点编号
		System.arraycopy(recv.content, 0, send.content, 16, 16);
		System.arraycopy(recv.content, 16, send.content, 0, 16);
		//如果登录成功，生成加密DESKEY
		if (retcode == ResultCode.OK) {
			//复制RSA模
			int i = 0;
			for(i=70; i<198; i++) {
				if (recv.content[i] != 0) break;
			}
			int rsakeylen = 198-i;
			rsakeylen = (((rsakeylen - 1) / 64) + 1) * 64;
			byte[] rsaModuleBytes = new byte[rsakeylen];
			System.arraycopy(recv.content, 198-rsakeylen, rsaModuleBytes, 0, rsakeylen);
			
			//复制RSA公钥
			for(i=198; i<230; i++) {
				if (recv.content[i] != 0) break;
			}
			int rsapublickeylen = 230-i;
			byte[] rsaPublicBytes = new byte[rsapublickeylen];
			System.arraycopy(recv.content, i, rsaPublicBytes, 0, rsapublickeylen);
			
			try {
				BigInteger rsaModule = new BigInteger(1, rsaModuleBytes);
				BigInteger publicExponent = new BigInteger(1, rsaPublicBytes);
				
				KeyFactory keyFactory = KeyFactory.getInstance("RSA");
				RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(rsaModule, publicExponent);
				PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
				
				SecureRandom random = new SecureRandom();
				byte[] desKeyBytes = random.generateSeed(8);
				for(i=0; i<8; i++) {
					desKeyBytes[i] ^= recv.content[i+54];
				}
				this.initDES(desKeyBytes);
				byte[] rsaDesKeyBytes = Util.encodeRSA(publicKey, desKeyBytes);
				//System.arraycopy(rsaDesKeyBytes, 0, send.content, 160 - rsaDesKeyBytes.length, rsaDesKeyBytes.length);
				//返回是后补0，接收是前补0
				System.arraycopy(rsaDesKeyBytes, 0, send.content, 32, rsaDesKeyBytes.length);
				this.islogined = true;
			} catch (Exception e) {
				this.islogined = false;
				System.out.println("rsakeylen=" + rsakeylen);
				System.out.println("rsapublickeylen=" + rsapublickeylen);
				System.out.println("clientIP=" + this.chctx.channel().remoteAddress());
				e.printStackTrace();
			}
		}
		//synchronized(chctx) {
		this.chctx.writeAndFlush(send);
		//}
	}
}
