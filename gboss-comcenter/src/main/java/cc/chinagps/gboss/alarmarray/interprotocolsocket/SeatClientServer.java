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
package cc.chinagps.gboss.alarmarray.interprotocolsocket;

import java.util.Date;

import cc.chinagps.lib.util.Util;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class SeatClientServer {

    private final int port;

    public SeatClientServer(int port) {
        this.port = port;
    }

    public void start(boolean bsync) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();		//因为有很多手机APP连接，所以用多线程 侦听
        EventLoopGroup workerGroup = new NioEventLoopGroup();	//连接通信也用多线程， 默认是处理器数量*2
        try {
            ServerBootstrap sbs = new ServerBootstrap();
            sbs.group(bossGroup, workerGroup);
            sbs.channel(NioServerSocketChannel.class);
            sbs.handler(new LoggingHandler(LogLevel.INFO));
            sbs.childHandler(new SeatClientServerInitializer());
            Channel ch = sbs.bind(port).sync().channel();
            System.out.println(Util.DatetoString(new Date(System.currentTimeMillis())) + " SeatClient Server started at port " + port + '.');
            if (bsync) { //如果只是警情队列
            	ch.closeFuture().sync();	//等待消息同步, 程序不退出
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            if (bsync) { //如果只是警情队列
            	bossGroup.shutdownGracefully();
            	workerGroup.shutdownGracefully();
            }
        }
    }
}
