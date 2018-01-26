/*
********************************************************************************************
Discription:  对外接口协议TCP连接服务器端基类
			  
Written By:   ZXZ
Date:         2014-05-15
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.outinterface;

import java.io.IOException;

import cc.chinagps.gboss.comcenter.CenterClientManager;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.websocket.ClientBaseHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.ReadTimeoutException;

public class OutInterfaceServerHandler extends OutInterfaceHandler {

	protected OutInterfaceClientInfo clientinfo = null;

    public OutInterfaceServerHandler() {
    	super();
    	clientinfo = new OutInterfaceClientInfo();
    	clientinfo.clienttype1 = "outinterfaceclient";	//对外接口客户端
    }
	
    /**
     * 客户端连接成功时调用
     * clientinfo:封装客户端的基本信息
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	super.channelActive(ctx);
    	clientinfo.outinterfacehandler = this; // 客户端通信通道, 发送时要用(对外接口协议通道)
    	clientinfo.setClosed(false);
    	clientinfo.ipaddr = ctx.channel().remoteAddress().toString(); // 客户端IP地址
    }
    
	public boolean isLogin() {
		return clientinfo.isLogin();
	}
	
	//连接断开事件
	protected void onClose() {
    	clientinfo.setClosed(true);	
    	CenterClientManager.clientManager.removeClient(clientinfo);
	}
	
    //@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof ReadTimeoutException) {
        	if ((!clientinfo.isLogin()) || (++readtimeoutTimes) >= 3) {
        		ctx.close();	//超时断开连接，朝野设置，请参考 pipeline.addLast("readTimeoutHandler", new ReadTimeoutHandler(60));
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
    
    @Override
	protected void onReceiveMessage(ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage msg) {
    	try {
        	clientinfo.setClosed(false);
			ClientBaseHandler handler = OutInterfaceServerHandlerFactory.CreateClientHandler(this, msg, clientinfo);
			//所有事件都分三步处理，如果没有三步，不用overwrite基类
			if (handler != null) {
				//先解码
				int retcode = handler.decode(); 
				if (retcode == ResultCode.OK) {
					handler.run();
				}
				//最后编写应答内容
				byte[] response = handler.response();
				if (response != null) {
					if (msg.getId() == MessageType.Login) {
						//如果是登录报文，则不加密
						OutInterfaceInfo sendinfo = new OutInterfaceInfo();
						sendinfo.msgId = MessageType.Login_ACK;
						sendinfo.body = response;
						WriteBody(sendinfo);
					} else {
						WriteBody(response);
					}
				}
				//如果是退录报文，则关闭连接
				if (msg.getId() == MessageType.Logout) {
					this.chctx.close();
	    			return;
				}
			}
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
	}

}
