/*
********************************************************************************************
Discription:  WebSocket客户端事件处理, 参考netty的例子
			     模拟手机APP客户端，用websocket连接通信中心
			  
Written By:   ZXZ
Date:         2014-08-19
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/

package testtools;


import java.util.UUID;

import com.google.protobuf.ByteString;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.GetLastInfo;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.GetHistoryInfo;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.GetHistoryInfoNextPage;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.Login;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.AddMonitor;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage;
import cc.chinagps.gboss.comcenter.serverHandler.*;
import cc.chinagps.gboss.comcenter.websocket.CipherTool;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.timeout.ReadTimeoutException;

public class WebSocketClientHandler extends SimpleChannelInboundHandler<Object> {

    private final WebSocketClientHandshaker handshaker;
    private ChannelPromise handshakeFuture;
    public boolean islogined = false;
	private ChannelHandlerContext chctx = null;

	//测试用
	private long startcallletter = 28922819616L;
	private int calllettercount = 8192;
	private int seatindex = 0;

	private int historyindex = 0;
	private int lastindex = 0;
	
	//取历史记录下一面用
	private String historycaller = "";
	public String historysn = "";
    
	public void SetCallerInfo(long startcallletter, int calllettercount, int seatindex) {
		this.startcallletter = startcallletter;
		this.calllettercount = calllettercount;
		this.seatindex = seatindex;
	}
	
    public WebSocketClientHandler(WebSocketClientHandshaker handshaker) {
   		this.handshaker = handshaker;
    }

    public ChannelFuture handshakeFuture() {
        return handshakeFuture;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    	chctx = ctx;
        handshakeFuture = ctx.newPromise();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	chctx = ctx;
        handshaker.handshake(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    	chctx = ctx;
        System.out.println("WebSocket Client disconnected!");
        islogined = false;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) { //throws Exception {
    	chctx = ctx;
    	if (msg instanceof WebSocketFrame) {
    		handleWebSocketFrame(ctx, (WebSocketFrame) msg);	//WEBSOCKET协议
    		return;
    	}
    	
        Channel ch = ctx.channel();
        if (!handshaker.isHandshakeComplete()) {
            handshaker.finishHandshake(ch, (FullHttpResponse) msg);
            System.out.println("WebSocket Client connected!");
            handshakeFuture.setSuccess();
            Login();
            return;
        }
        
        //if (msg instanceof FullHttpResponse) {
            //FullHttpResponse response = (FullHttpResponse) msg;
            //throw new Exception("Unexpected FullHttpResponse (getStatus=" + response.getStatus() + ", content="
            //        + response.content().toString(CharsetUtil.UTF_8) + ')');
        //}
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	if (cause instanceof ReadTimeoutException) {
        	if (this.islogined) {
        		ActiveLink();
        		if ((((System.currentTimeMillis() + seatindex) % 100) > 30) &&
        				(historysn == null || historysn.isEmpty())) {
        			this.LastGPS();
        		} else {
        			this.HistoryGPS();
        		}
        	} else {
        		this.Login();
        	}
        	return;
        } else {
        	if (!handshakeFuture.isDone()) {
        		handshakeFuture.setFailure(cause);
            }
        	cause.printStackTrace();
        	ctx.close();
        }
    }
    //发送一般内容（字节串）
    public void WriteByteArray(byte[] content) {
		if (content == null || content.length <= 0 || !(chctx.channel().isWritable()))
			return;
		CipherTool tool = new CipherTool(content);
    	int code = tool.Encode(true, true);
    	if (code == ResultCode.OK) {
        	BinaryWebSocketFrame responseframe = new BinaryWebSocketFrame();
        	ByteBuf contentbuff = responseframe.content();
        	contentbuff.writeBytes(tool.destion);
    		synchronized(chctx) {
    			chctx.writeAndFlush(responseframe);
    		}
    	}
    }

    /*
     * 发送心跳报文
     */
	public void ActiveLink() {
		ComCenterMessage.ComCenterBaseMessage.Builder basemsg = ComCenterMessage.ComCenterBaseMessage.newBuilder();
		basemsg.setId(MessageType.ActiveLink);
		basemsg.setContent(ByteString.EMPTY);

		ComCenterMessage.Builder msg = ComCenterMessage.newBuilder();
		msg.addMessages(basemsg.build());
		WriteByteArray(msg.build().toByteArray());
	}
	
	/*
	 * 登录报文
	 */
	public void Login() {
		Login.Builder login = Login.newBuilder();
		login.setUsername("zhangxz");
		login.setPassword("123456");
		for(int i=0; i<30; i++) {
			Long lv = new Long(startcallletter + ((seatindex * 50 + i) % calllettercount) );
			String caller = lv.toString();
			login.addCallLetters(caller);
		}
		
		ComCenterMessage.ComCenterBaseMessage.Builder basemsg = ComCenterMessage.ComCenterBaseMessage.newBuilder();
		basemsg.setId(MessageType.Login);
		ByteString bytestring = login.build().toByteString(); 
		basemsg.setContent(bytestring);

		ComCenterMessage.Builder msg = ComCenterMessage.newBuilder();
		msg.addMessages(basemsg.build());
		WriteByteArray(msg.build().toByteArray());
	}
	
	public void AddMonitor() {
		AddMonitor.Builder addmonitor = AddMonitor.newBuilder();
		for(int i=0; i<30; i++) {
			addmonitor.addCallLetters(String.format("%d", startcallletter + seatindex * 50 + i));
		}
		addmonitor.addInfotypes(-1);
		
		ComCenterMessage.ComCenterBaseMessage.Builder basemsg = ComCenterMessage.ComCenterBaseMessage.newBuilder();
		basemsg.setId(MessageType.AddMonitor);
		ByteString bytestring = addmonitor.build().toByteString(); 
		basemsg.setContent(bytestring);
		ComCenterMessage.Builder msg = ComCenterMessage.newBuilder();
		msg.addMessages(basemsg.build());
		WriteByteArray(msg.build().toByteArray());
	}
	
	/*
	 * 取最后位置
	 */
	public void LastGPS() {
		GetLastInfo.Builder lastinfo = GetLastInfo.newBuilder();
		lastinfo.setInfoType(MessageType.DeliverGPS);
		UUID uuid = UUID.randomUUID();
	    lastinfo.setSn(uuid.toString());
		for(int i=0; i<10; i++) {
			Long lv = new Long(startcallletter + ((lastindex + seatindex * 17) % calllettercount));
			String caller = lv.toString();
			lastinfo.addCallLetters(caller);
			lastindex += 91;
		}
		ComCenterMessage.ComCenterBaseMessage.Builder basemsg = ComCenterMessage.ComCenterBaseMessage.newBuilder();
		basemsg.setId(MessageType.GetLastInfo);
		ByteString bytestring = lastinfo.build().toByteString(); 
		basemsg.setContent(bytestring);

		ComCenterMessage.Builder msg = ComCenterMessage.newBuilder();
		msg.addMessages(basemsg.build());
		WriteByteArray(msg.build().toByteArray());
	}
    
	/*
	 * 取历史位置
	 */
	public void HistoryGPS() {
		if (historysn == null || historysn.isEmpty()) {
			UUID uuid = UUID.randomUUID();
			historysn = uuid.toString();
			GetHistoryInfo.Builder historyinfo = GetHistoryInfo.newBuilder();
			historyinfo.setInfoType(MessageType.DeliverGPS);
			historyinfo.setSn(uuid.toString());
			Long lv = new Long(startcallletter + ((historyindex + seatindex * 23) % calllettercount));
			historycaller = lv.toString();
			historyinfo.setCallLetter(historycaller);
			historyindex += 103;
			historyinfo.setAutonextpage((historyindex % 2 ) == 0);
			historyinfo.setPageNumber(100 + (historyindex % 97) + seatindex);
			historyinfo.setTotalNumber(1000 + (historyindex % 100) + seatindex);
			historyinfo.setEndtime(System.currentTimeMillis());
			historyinfo.setStarttime(System.currentTimeMillis() - 3600000 * 24);	//一天前
		
			ComCenterMessage.ComCenterBaseMessage.Builder basemsg = ComCenterMessage.ComCenterBaseMessage.newBuilder();
			basemsg.setId(MessageType.GetHistoryInfo);
			ByteString bytestring = historyinfo.build().toByteString(); 
			basemsg.setContent(bytestring);

			ComCenterMessage.Builder msg = ComCenterMessage.newBuilder();
			msg.addMessages(basemsg.build());
			WriteByteArray(msg.build().toByteArray());
		} else {
			GetHistoryInfoNextPage.Builder nextpage = GetHistoryInfoNextPage.newBuilder();
			nextpage.setCallLetter(historycaller);
			nextpage.setSn(historysn);
			nextpage.setInfoType(MessageType.DeliverGPS);
			
			ComCenterMessage.ComCenterBaseMessage.Builder basemsg = ComCenterMessage.ComCenterBaseMessage.newBuilder();
			basemsg.setId(MessageType.GetHistoryInfoNextPage);
			ByteString bytestring = nextpage.build().toByteString(); 
			basemsg.setContent(bytestring);

			ComCenterMessage.Builder msg = ComCenterMessage.newBuilder();
			msg.addMessages(basemsg.build());
			WriteByteArray(msg.build().toByteArray());
		}
	}

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        //如果是字节串, 则先判断报文的合法性，是否有效
        if (frame instanceof BinaryWebSocketFrame) {
        	try {
	        	BinaryWebSocketFrame binaryFrame=(BinaryWebSocketFrame)frame;
	        	ByteBuf request = binaryFrame.content();
	        	byte[] recv = new byte[request.writerIndex()]; // .capacity()];	//最好不要用capacity, 用writerIndex
	        	request.readBytes(recv);	//getBytes(0, recv);
	        	
	        	CipherTool tool = new CipherTool(recv);
	        	int ret = tool.Decode();
	        	//如果报文不合法，则关闭连接
	        	if (ret != ResultCode.OK) {
	        		ctx.close();
	        		return;
	        	}
	        	ComCenterDataBuff.ComCenterMessage messages = ComCenterDataBuff.ComCenterMessage.parseFrom(tool.destion);
	        	for(ComCenterBaseMessage msg : messages.getMessagesList()) {
					serverBaseHandler handler = cc.chinagps.gboss.comcenter.serverHandler.serverHandlerFactory..CreateServerHandler(msg, this);
	        		if (handler != null) {
	        			//先解码
	        			int retcode = handler.decode(); 
	        			if (retcode == ResultCode.OK) {
	        				handler.run();
	        			}
	        		}
	        	}
            } catch(Exception e) {
    			e.printStackTrace();
        	}
        	return;
        }
        else if (frame instanceof TextWebSocketFrame) {
            TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
            System.out.println("WebSocket Client received message: " + textFrame.text());
        } else if (frame instanceof PongWebSocketFrame) {
            System.out.println("WebSocket Client received pong");
        } else if (frame instanceof CloseWebSocketFrame) {
            System.out.println("WebSocket Client received closing");
        	if (handshaker != null) {
        		//第一次申请时，已经生成
        		handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
        	} else {
        		ctx.close();
        	}
            return;
        }
    }
}
