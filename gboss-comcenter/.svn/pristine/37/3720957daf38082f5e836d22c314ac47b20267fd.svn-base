/*
********************************************************************************************
Discription:  大平台对外接口协议TCP连接客户端基类
			  
Written By:   ZXZ
Date:         2015-02-10
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.outinterface;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.ReadTimeoutException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

import cc.chinagps.gboss.comcenter.buff.ResultCode;

public abstract class OutInterfaceClientHandler extends OutInterfaceHandler {
	static final private int RSAKeyLength = 512;	//512或1024
	protected String userName = "";		//登录用户名
	protected String password = "";		//登录密码
	protected String serverNodeId = "";	//服务器编号
	
	
	protected RSAPublicKey rsaPublicKey = null;	//登录时的RSA公钥, 登录成功后释放
	protected PrivateKey rsaPrivateKey = null;	//登录时的RSA私钥, 登录成功后释放
	
	public void setUserName(String strName) {
		userName = strName;
	}
	
	public void setPassword(String strPwd) {
		password = strPwd;
	}
	
	public void setServerNodeId(String strId) {
		serverNodeId = strId;
	}
	
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
    	this.chctx = ctx;
    	Login();
    }
    
    /*
	typedef struct _PACKET_LOGON_ACK
	{
	    SEGString<16> SrcNodeNo;    //服务器节点编号
	    SEGString<16> DstNodeNo;    //登录方节点编号
	    BYTE DesKey[128];           //动态Des加密密钥，经公钥加密, 不够128字节，前补0(0x00)
	}PACKET_LOGON_ACK;
	@Override
	protected void ProcessLoginAck(OutInterfaceInfo recv){
		if (recv.result != ResultCode.OK){
			//执行登录失败回调函数
			OnLoginAck(recv.result);
		} else if (recv.content.length >= 160) {
			byte desKeyByte[] = Util.decodeRSA(rsaPrivateKey, recv.content, 32, RSAKeyLength/8);
			rsaPublicKey = null;	//释放资源
			rsaPrivateKey = null;	//释放资源
			if (desKeyByte.length != 8){
				System.err.println("return des key is wrong,decrypted key bytes is:" + Util.getBytesString(desKeyByte));
				//执行解码失败回调函数
				OnLoginAck(ResultCode.Decode_Error);
				return;
			}
			try {
				initDES(desKeyByte);
				this.islogined = true;
				//执行登录成功回调函数
				OnLoginAck(ResultCode.OK);
			} catch (Exception e) {
				e.printStackTrace();
				OnLoginAck(ResultCode.Decode_Error);
				return;
			}
			
		} else{
			OnLoginAck(ResultCode.Format_Error);
		}
	}
     */
	protected abstract void OnLoginAck(short retcode);

	/*
	 * typedef struct _PACKET_LOGON
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
	//初始化RSAKEY
	private void initRSA() throws Exception {
		KeyPairGenerator kpg;
		kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(RSAKeyLength);
		KeyPair kp = kpg.generateKeyPair();
		rsaPublicKey = (RSAPublicKey) kp.getPublic();
		rsaPrivateKey = kp.getPrivate();
	};
	public short Login() {
		try {
			initRSA();
			/*OutInterfaceInfo send = new OutInterfaceInfo();
			send.msgId = Constant.MESSAGE_ID_LOGIN;
			send.result = 0;
			send.sequenceNo = (int)(++this.sequenceNo);
			send.version = 0x10;
			send.content = new byte[230];
			
			byte[] sourceNoBytes = this.nodeId.getBytes();
			byte[] destNoBytes = serverNodeId.getBytes();
			byte[] userNameBytes = userName.getBytes();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			byte[] stamp = Util.str2bcd(sdf.format(date));
			byte[] passwordBytes = password.getBytes();
			
			System.arraycopy(sourceNoBytes, 0, send.content, 0, sourceNoBytes.length > 16 ? 16 : sourceNoBytes.length);
			System.arraycopy(destNoBytes, 0, send.content, 16, destNoBytes.length > 16 ? 16 : destNoBytes.length);
			System.arraycopy(userNameBytes, 0, send.content, 32, userNameBytes.length > 15 ? 15 : userNameBytes.length);
			System.arraycopy(stamp, 0, send.content, 47, stamp.length);
			System.arraycopy(passwordBytes, 0, send.content, 54, passwordBytes.length > 16 ? 16 : passwordBytes.length);
			passwordBytes = Util.MD5C(send.content, 47, 23);
			System.arraycopy(passwordBytes, 0, send.content, 54, passwordBytes.length > 16 ? 16 : passwordBytes.length);
			byte[] rsaModuleBytes = Util.clearForeZero(rsaPublicKey.getModulus().toByteArray());
			byte[] rsaPublicKeyBytes = Util.clearForeZero(rsaPublicKey.getPublicExponent().toByteArray());
			System.arraycopy(rsaModuleBytes, 0, send.content, 198-rsaModuleBytes.length, rsaModuleBytes.length);
			System.arraycopy(rsaPublicKeyBytes, 0, send.content, 230-rsaPublicKeyBytes.length, rsaPublicKeyBytes.length);
			//synchronized(chctx) {
			this.chctx.writeAndFlush(send);
			//}
			 * */
			return ResultCode.OK;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultCode.Encode_Error;
		}
	};
	
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	if (cause instanceof ReadTimeoutException) {
    		//如果超时
        	if (this.isLogin())
        		ActiveLink();
        	else
        		this.Login();
        } else {
            //cause.printStackTrace();
            ctx.close();
        }
    }
}
