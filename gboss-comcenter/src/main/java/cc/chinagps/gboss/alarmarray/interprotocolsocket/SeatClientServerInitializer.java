/*
********************************************************************************************
Discription:  内容协议服务端初始化, 复制于netty的例子
			  
			  
Written By:   ZXZ
Date:         2014-04-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/

package cc.chinagps.gboss.alarmarray.interprotocolsocket;

import cc.chinagps.interprotocol.InterProtocolDecoder;
import cc.chinagps.interprotocol.InterProtocolEncoder;
import cc.chinagps.interprotocol.InterProtocolTimeoutHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 */
public class SeatClientServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("decoder", new InterProtocolDecoder());
        pipeline.addLast("encoder", new InterProtocolEncoder(true, true));
        pipeline.addLast("readTimeoutHandler", new InterProtocolTimeoutHandler(30));
        pipeline.addLast(new SeatClientServerHandler());
    }
}
