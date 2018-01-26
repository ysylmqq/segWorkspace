/*
********************************************************************************************
Discription:  WebSocket事件处理, 参考netty的例子
			  
			  
Written By:   ZXZ
Date:         2014-04-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.ReadTimeoutException;

import java.util.logging.Logger;

import com.google.protobuf.ByteString;

import cc.chinagps.gboss.alarmarray.SeatClientInfo;
import cc.chinagps.gboss.comcenter.WebsocketClientInfo;
import cc.chinagps.gboss.comcenter.CenterClientManager;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff; 
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import static io.netty.handler.codec.http.HttpHeaders.Names.*;

/**
 * Handles handshakes and messages
 * 服务器端，具体的处理客户端请求的handler
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {
    protected static final Logger logger = Logger.getLogger(WebSocketServerHandler.class.getName());
	private static final String WEBSOCKET_PATH = "/websocket";
	private WebSocketServerHandshaker handshaker = null;
	protected ChannelHandlerContext chctx = null;
    protected WebsocketClientInfo clientinfo = null;
    private int timeouttimes = 0;	//超时次数

    public WebSocketServerHandler() {
    	clientinfo = new WebsocketClientInfo();
    }
    
	private static String getWebSocketLocation(FullHttpRequest req) {
        return "ws://" + req.headers().get(HOST) + WEBSOCKET_PATH;
    }
  
	/**
	 * 客户端连接成功时，调用
	 */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	super.channelActive(ctx);
    	chctx = ctx;
    	clientinfo.webhandler = this;
    	clientinfo.setClosed(false);
    	clientinfo.ipaddr = ctx.channel().remoteAddress().toString();
    }
    
    /**
     * 客户端断开连接时，调用
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    	super.channelInactive(ctx);
    	clientinfo.setClosed(true);
    	clientinfo.setLogin(false);
    	//断开时，删除客户端
    	CenterClientManager.clientManager.removeClient(clientinfo);
		if (clientinfo instanceof SeatClientInfo) {
			SeatClientInfo seat = (SeatClientInfo)clientinfo;
			seat.setZkClosed();
		}
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
    	chctx = ctx;
    	if (msg instanceof WebSocketFrame) {
    		handleWebSocketFrame(ctx, (WebSocketFrame) msg);	//WEBSOCKET协议
    		return;
    	}
    	if (msg instanceof FullHttpRequest) {
            try {
	    		//第一次Websocket申请
	    		FullHttpRequest req = (FullHttpRequest)msg;
	            String uri = req.getUri();
	            if (uri.equals(WEBSOCKET_PATH) || uri.equals(WEBSOCKET_PATH + "/")) {
	                // Handshake
	                WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
	                        getWebSocketLocation(req), null, false);
	                handshaker = wsFactory.newHandshaker(req);
	                if (handshaker == null) {
	                    WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
	                } else {
	                    handshaker.handshake(ctx.channel(), req);
	                }
	            } else {
		            //处理客户端的HTTP请求
		            FullHttpRequestHandler reqhandler = new FullHttpRequestHandler(ctx, (FullHttpRequest)msg);
	            	reqhandler.handle();
	            }
            } catch(Throwable e) {
            	ctx.close();
    			e.printStackTrace();
    		}
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (!(evt instanceof IdleStateEvent)) {
            return;
        }
        IdleStateEvent e = (IdleStateEvent) evt;
        if (e.state() == IdleState.READER_IDLE) {
            System.out.println("Disconnecting due to no inbound traffic");
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof ReadTimeoutException) {
        	if ((!clientinfo.isLogin()) || (++timeouttimes) >= 3) {
        		ctx.close();  //超过3次断开连接
        	} else {
        		ActiveLink();
        	}
        //} else if (cause instanceof java.io.IOException) {
        //	//屏蔽Connection reset by peer消息
        //	IOException ex = (IOException)cause;
        //	if (!ex.getMessage().startsWith("Connection")) {
        //   	//cause.printStackTrace();
        //	}
        //	ctx.close();
        } else {
        	//cause.printStackTrace();
        	ctx.close();
        }
    }

    protected void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
    	try {
	        //如果是字节串, 则先判断报文的合法性，是否有效
	        if (frame instanceof BinaryWebSocketFrame) {
	        	try {
	            	clientinfo.setClosed(false);
	            	timeouttimes = 0;
	            	
		        	BinaryWebSocketFrame binaryFrame=(BinaryWebSocketFrame)frame;
		        	ByteBuf request = binaryFrame.content();
					byte[] recv = new byte[request.writerIndex()]; // .capacity()];	//最好不要用capacity, 用writerIndex
		        	request.readBytes(recv);	//getBytes(0, recv);
		        	
		        	//测试原内容返回
		        	//if (logger.isLoggable(Level.CONFIG)) {
		            //    logger.info(String.format("received[%s]: %s", ctx.channel(), Util.bcd2str(recv)));
		            //}
		        	
		        	//临时打印测试用例
		        	//System.out.println("receive:");
		        	//System.out.println(Util.bcd2str(recv));
		        	
		        	CipherTool tool = new CipherTool(recv);
		        	int ret = tool.Decode();
		        	//如果报文不合法，则关闭连接
		        	if (ret != ResultCode.OK) {
		        		ctx.close();
		        		return;
		        	}
		        	ComCenterDataBuff.ComCenterMessage messages = ComCenterDataBuff.ComCenterMessage.parseFrom(tool.destion);
		        	for(ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage msg : messages.getMessagesList()) {
		        		ClientBaseHandler handler = CreateMessageHandler(msg);
		        		//所有事件都分三步处理，如果没有三步，不用overwrite基类
		        		if (handler != null) {
		        			//先解码
		        			int retcode = handler.decode(); 
		        			if (retcode == ResultCode.OK) {
		        				handler.run();
		        			//} else if (logger.isLoggable(Level.WARNING)) {
		                    //	logger.warning(String.format("encode failed[%s]: %s, %s", retcode, msg.getId(), msg.getContent()));
		        			}
		        			//最后编写应答内容
		        			byte[] response = handler.response();
		        			if (response != null) {
		        				WriteByteArray(response);
		        			}
		        			//如果是退录报文，则关闭连接
		        			if (msg.getId() == MessageType.Logout) {
			    				ctx.close();
			        			return;
		        			}
		        		}
		        	}
	            } catch(Throwable e) {
	    			e.printStackTrace();
	        	}
	        	return;
	        }
	        // Check for closing frame
	    	// 收到连接关闭帧
	        if (frame instanceof CloseWebSocketFrame) {
	        	if (handshaker != null) {
	        		//第一次申请时，已经生成
	        		handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
	        	}
	            return;
	        }
	        //收到Ping帧, 返回Pong帧
	        if (frame instanceof PingWebSocketFrame) {
	            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
	            return;
	        }
	        //测试用，如果收到文本帧，则将小写改为大写，返回
	        if (frame instanceof TextWebSocketFrame) {
	            // 如果收到文本信息，则返回Send the uppercase string back.
	            String request = ((TextWebSocketFrame) frame).text();
	            //System.out.println(request.length());
	            //if (logger.isLoggable(Level.FINE)) {
	            //    logger.fine(String.format("%s received %s", ctx.channel(), request));
	            //}
	            ctx.channel().write(new TextWebSocketFrame(request.toUpperCase()));
	        	return;
	        }
	        //收到Pong帧，不处理
	        if (frame instanceof PongWebSocketFrame) {
	            return;
	        }
    	} catch (Throwable t) {
    		t.printStackTrace();
    	}
    }
    
    /**********************************************************************************
     * 子类重构此方法
     **********************************************************************************/  
    protected ClientBaseHandler CreateMessageHandler(ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage msg) {
		return ClientHandlerFactory.CreateClientHandler(msg, this.clientinfo);
    }
    
    public boolean isWritable() {
    	return this.chctx.channel().isWritable();
    }
    
    //发送一般内容（字节串）
    public void WriteByteArray(byte[] content) {
		if (content == null || content.length <= 0 || !(chctx.channel().isWritable()))
			return;
		CipherTool tool = new CipherTool(content);
    	int code = tool.Encode(true, true);
    	if (code == ResultCode.OK) {
    		//临时打印测试用例
    		//System.out.println("send:");
    		//System.out.println(Util.bcd2str(tool.destion));
    		
    		BinaryWebSocketFrame responseframe = new BinaryWebSocketFrame();
        	ByteBuf contentbuff = responseframe.content();
        	contentbuff.writeBytes(tool.destion);
        	synchronized(chctx) {
        		chctx.writeAndFlush(responseframe);
        	}
    	}
    }
    
    private void ActiveLink() {
    	WriteByteArray(ClientBaseHandler.Serialize(MessageType.ActiveLink, ByteString.EMPTY));
    }
}
