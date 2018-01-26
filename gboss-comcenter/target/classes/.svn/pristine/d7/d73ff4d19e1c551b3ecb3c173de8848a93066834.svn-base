/*
********************************************************************************************
Discription:  对外接口协议服务端初始化, 复制于netty的例子
			  
			  
Written By:   ZXZ
Date:         2014-04-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/

package cc.chinagps.gboss.comcenter.outinterface;

import cc.chinagps.interprotocol.InterProtocolTimeoutHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
/**
 */
public class OutInterfaceServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("decoder", new OutInterfaceDecoder());
        pipeline.addLast("encoder", new OutInterfaceEncoder(true, true));
        pipeline.addLast("noencryptencoder", new OutInterfaceNoEncryptEncoder(true));
    	//60(3*20)秒没收到数据，断开连接,要放在加解密后。表示没有收到内部协议报文
        pipeline.addLast("readTimeoutHandler", new InterProtocolTimeoutHandler(30));
        pipeline.addLast(new OutInterfaceServerHandler()); //new LoggingHandler(LogLevel.INFO), 
    }
}
