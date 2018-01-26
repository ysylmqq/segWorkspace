/*
********************************************************************************************
Discription:  WebSocket通信事件初始化, 复制于netty的例子
			  
			  
Written By:   ZXZ
Date:         2014-04-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/

package cc.chinagps.gboss.comcenter.websocket;

import cc.chinagps.interprotocol.InterProtocolTimeoutHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 */
public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(
        		new InterProtocolTimeoutHandler(40),
        		new HttpServerCodec(),
        		new HttpObjectAggregator(65536),
        		new WebSocketServerHandler());
    }
}
