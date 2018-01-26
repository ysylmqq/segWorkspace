/*
********************************************************************************************
Discription:  内部协议通信中心客户端服务器（通信中心还有一个网关服务器,也是内部协议）
			  
			  
Written By:   ZXZ
Date:         2014-04-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.interprotocolsocket;

import java.util.Date;

import cc.chinagps.lib.util.Util;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class InterProtocolComcenter {

    private final int port;

    public InterProtocolComcenter(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup bossGroup = null;
        EventLoopGroup workerGroup = null;
    	//String osname = System.getProperty("os.name").toLowerCase().trim();
    	//System.out.println(osname);
    	//if (osname.contains("linux")) {
        //    bossGroup = new EpollEventLoopGroup();		//因为有很多手机APP连接，所以用多线程 侦听
        //    workerGroup = new EpollEventLoopGroup();	//连接通信也用多线程， 默认是处理器数量*2
    	//} else {
    	bossGroup = new NioEventLoopGroup();	//因为有很多手机APP连接，所以用多线程 侦听
    	workerGroup = new NioEventLoopGroup();	//连接通信也用多线程， 默认是处理器数量*2
    	//}
        try {
            ServerBootstrap sbs = new ServerBootstrap();
            sbs.group(bossGroup, workerGroup);
            sbs.channel(NioServerSocketChannel.class);
            sbs.handler(new LoggingHandler(LogLevel.INFO));
            sbs.childHandler(new InterProtocolComcenterInitializer());
            Channel ch = sbs.bind(port).sync().channel();
            System.out.println(Util.DatetoString(new Date(System.currentTimeMillis())) + " Inter Client Server started at port " + port + '.');
            ch.closeFuture().sync();	//等待消息同步, 程序不退出
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
