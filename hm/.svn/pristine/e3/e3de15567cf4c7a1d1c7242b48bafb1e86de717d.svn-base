/*
********************************************************************************************
Discription:  内部协议TCP连接处理基类
			  
Written By:   ZXZ
Date:         2014-05-15
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.interprotocol;

import java.util.logging.Logger;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import cc.chinagps.gboss.comcenter.buff.ResultCode;

@Sharable
public abstract class InterProtocolHandler extends SimpleChannelInboundHandler<InterProtocolInfo> {
    protected static final Logger logger = Logger.getLogger(InterProtocolHandler.class.getName());
	protected ChannelHandlerContext chctx;
	protected SecretKey DESKey;		//DES加密Key
	protected String nodeId = "";	//节点编号
    //protected long lasttime = System.currentTimeMillis();	//最后接收报文时间
	protected long sequenceNo = 0;	//私有流水号(循环加1)	
    protected boolean islogined = false;	//是否登录
	protected int readtimeoutTimes = 0;		//读超时次数
	protected String remoteNodeId = "";
	
    //取加密KEY，编码时要用
	public SecretKey getDESKey() {
		return DESKey;
	}
	
	//设置节点编号
	public void setNodeId(String strId) {
		nodeId = strId;
	}
	public String getNodeId() {
		return nodeId;
	}
	
	//是否登录
	public boolean isLogined() {
		return islogined;
	}
	
	//初始化DES加密KEY
	public void initDES(byte[] keyBytes) throws Exception {
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
		DESKey = keyFactory.generateSecret(desKeySpec);
	}

    public boolean isWritable() {
    	return this.chctx.channel().isWritable();
    }
    //发送一般内容（字节串）
    public boolean WriteContent(byte[] content) {
    	try {
	    	InterProtocolInfo send = new InterProtocolInfo();
	    	send.content = content;
	    	send.msgId = Constant.MESSAGE_ID_COMCENTER_PROTOBUF;
	    	send.result = ResultCode.OK;
	    	send.sequenceNo = (int)(this.sequenceNo++);
	    	send.version = 0x10;
			chctx.writeAndFlush(send);
			return true;
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	return false;
    }
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.chctx = ctx;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    	try {
    		super.channelInactive(ctx);
    		this.islogined = false;
    		onClose();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, InterProtocolInfo recv) throws Exception {
		//System.out.println("InterProtocol Read MsgID:" + recv.msgId);
		//this.lasttime = System.currentTimeMillis();
		this.readtimeoutTimes = 0;
		this.chctx = ctx;
		switch(recv.msgId) {
		case Constant.MESSAGE_ID_LOGIN:
			ProcessLogin(recv);
			break;
		case Constant.MESSAGE_ID_LOGIN_ACK:
			ProcessLoginAck(recv);
			break;
		case Constant.MESSAGE_ID_LOGOUT:
			ProcessLogout();
			break;
		case Constant.MESSAGE_ID_LOGOUT_ACK:
			ctx.close();	//断开连接
			break;
		case Constant.MESSAGE_ID_ACTIVETEST:
			ProcessActiveTest(recv);
			break;
		case Constant.MESSAGE_ID_ACTIVETEST_ACK:
			//不用处理
			break;
		default:
			if (this.islogined) {
				ProcessNormal(recv);
			} else {
				
			}
			break;
		}
	}

	/*
	typedef struct _PACKET_ACTIVETEST
	{
	    SEGString<16> SrcNodeNo;    //登录方节点编号
	    SEGString<16> DstNodeNo;    //服务器节点编号
	}PACKET_ACTIVETEST;
	 * 
	 */
	protected void ActiveLink() {
		try {
			InterProtocolInfo send = new InterProtocolInfo();
			send.msgId = Constant.MESSAGE_ID_ACTIVETEST;
			send.result = 0;
			send.sequenceNo = (int)(++this.sequenceNo);
			send.version = 0x10;
			send.content = new byte[32];
			
			byte[] sourceNoBytes = this.nodeId.getBytes();
			byte[] destNoBytes = remoteNodeId.getBytes();
			System.arraycopy(sourceNoBytes, 0, send.content, 0, sourceNoBytes.length > 16 ? 16 : sourceNoBytes.length);
			System.arraycopy(destNoBytes, 0, send.content, 16, destNoBytes.length > 16 ? 16 : destNoBytes.length);
			synchronized(chctx) {
				this.chctx.writeAndFlush(send);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};
	
	//接收到链路检测报文，返回链路检测应答
	private void ProcessActiveTest(InterProtocolInfo recv) {
		InterProtocolInfo send = new InterProtocolInfo();
		send.content = new byte[recv.content.length];
		send.msgId = Constant.MESSAGE_ID_ACTIVETEST_ACK;
		send.result = 0;
		send.sequenceNo = recv.sequenceNo;
		send.version = recv.version;
		send.content = new byte[recv.content.length];
		int len = recv.content.length / 2;
		//将源节点编号和目的节点编号互换
		System.arraycopy(recv.content, 0, send.content, len, len);
		System.arraycopy(recv.content, len, send.content, 0, len);
		synchronized(chctx) {
			chctx.writeAndFlush(send);
		}
	}
	
	//返回未登录应答
	protected void ProcessNoLogined(InterProtocolInfo recv) {
		InterProtocolInfo send = new InterProtocolInfo();
		send.msgId = recv.msgId;
		send.result = ResultCode.NoLogin_Error;
		send.sequenceNo = recv.sequenceNo;
		synchronized(chctx) {
			chctx.writeAndFlush(send);
		}
	}
	
	//服务器处理
	protected abstract void ProcessLogin(InterProtocolInfo recv) throws Exception;
	
	//客户端处理
	protected abstract void ProcessLoginAck(InterProtocolInfo recv) throws Exception;
	
	//处理一般业务程序
	protected abstract void ProcessNormal(InterProtocolInfo recv) throws Exception;

	//连接断开事件
	protected void onClose() {
		this.islogined = false;
	}
	
	//处理退录报文
	protected void ProcessLogout() {
	}
	
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /*
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.log(Level.WARNING, "Unexpected exception from downstream.", cause);
        ctx.close();
    }
    */
    /*@Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (!(evt instanceof IdleStateEvent)) {
            return;
        }
        IdleStateEvent e = (IdleStateEvent) evt;
        if (e.state() == IdleState.READER_IDLE) {
            // The connection was OK but there was no traffic for last period.
            System.out.println("InterProtocolServer Disconnecting due to no inbound traffic");
            ctx.close();
        }
    }*/
}
