/*
********************************************************************************************
Discription:  对外接口协议TCP连接处理基类
			  
Written By:   ZXZ
Date:         2014-05-15
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.outinterface;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage;
import cc.chinagps.gboss.comcenter.buff.seginterfaceDataBuff.ActiveTest;

@Sharable
public abstract class OutInterfaceHandler extends SimpleChannelInboundHandler<byte[]> {
	protected ChannelHandlerContext chctx;
	protected SecretKey DESKey;		//DES加密Key
	protected String nodeId = "";		//节点编号
	protected String remoteNodeId = "";	//对方节点编号
    //protected boolean islogined = false;	//是否登录
	protected int readtimeoutTimes = 0;		//读超时次数
	
    //DESKEY 初始化默认为CHINAGPS
    public OutInterfaceHandler() {
    	try {
    		initDES("CHINAGPS".getBytes());
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    	try {
    		super.channelInactive(ctx);
    		//this.islogined = false;
    		onClose();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, byte[] body) throws Exception {
		this.chctx = ctx;
		this.readtimeoutTimes = 0;
		try {
	    	ComCenterDataBuff.ComCenterMessage messages = ComCenterDataBuff.ComCenterMessage.parseFrom(body);
	    	for(ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage msg : messages.getMessagesList()) {
	    		onReceiveMessage(msg);
	    	}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
    
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
	
	//初始化DES加密KEY
	protected void initDES(byte[] keyBytes) throws Exception {
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
		DESKey = keyFactory.generateSecret(desKeySpec);
	}

    public boolean isWritable() {
    	return this.chctx.channel().isWritable();
    }
    
    //发送报文体（字节串）
    public boolean WriteBody(byte[] body) {
    	try {
    		if (chctx.channel().isWritable()) {
    			chctx.writeAndFlush(body);
    			return true;
    		}
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	return false;
    }
    public boolean WriteBody(OutInterfaceInfo sendinfo) {
    	try {
    		if (chctx.channel().isWritable()) {
    			chctx.writeAndFlush(sendinfo);
    			return true;
    		}
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	return false;
    }
    
	/*message ActiveTest {
	    required string srcnode = 1;        //客户端(接口程序)编号，编号由赛格导航统一分配，一个编号只能用于一个客户端
	    required string dstnode = 2;        //服务端编号（接口中心为COMCENTER）
	};
	message ActiveTest_ACK {
	    required string srcnode = 1;        //服务端编号（接口中心为COMCENTER）
	    required string dstnode = 2;        //客户端(接口程序)编号，编号由赛格导航统一分配，一个编号只能用于一个客户端
	};*/
	protected void ActiveLink() {
		try {
			ActiveTest.Builder activetest = ActiveTest.newBuilder();
			activetest.setDstnode(remoteNodeId);
			activetest.setSrcnode(nodeId);
			ComCenterMessage.ComCenterBaseMessage.Builder basemsg = ComCenterMessage.ComCenterBaseMessage.newBuilder();
			basemsg.setId(MessageType.ActiveLink);
			basemsg.setContent(activetest.build().toByteString());
			ComCenterMessage.Builder msg = ComCenterMessage.newBuilder();
			msg.addMessages(basemsg.build());
			WriteBody(msg.build().toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
	};

	//是否登录
	public abstract boolean isLogin();
	
	//连接断开事件
	protected abstract void onClose();
	
	/*
	 * 将一个基础包（protobuf）,序列化字节串
	 */
	/*protected byte[] Serialize(int msgtype, ByteString bytestring) {
		ComCenterMessage.ComCenterBaseMessage.Builder basemsg = ComCenterMessage.ComCenterBaseMessage.newBuilder();
		basemsg.setId(msgtype);
		basemsg.setContent(bytestring);
		ComCenterMessage.Builder msg = ComCenterMessage.newBuilder();
		msg.addMessages(basemsg.build());
		return msg.build().toByteArray();
	}*/
	
	//连接断开事件
	protected abstract void onReceiveMessage(ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage msg);

}
