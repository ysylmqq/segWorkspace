/*
********************************************************************************************
Discription:  对外接口协议
			  
			  
Written By:   ZXZ
Date:         2014-04-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.outinterface;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class OutInterfaceServer {

    private final int port;

    public OutInterfaceServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();		//因为有很多手机APP连接，所以用多线程 侦听
        EventLoopGroup workerGroup = new NioEventLoopGroup();	//连接通信也用多线程， 默认是处理器数量*2
        try {
            ServerBootstrap sbs = new ServerBootstrap();
            sbs.group(bossGroup, workerGroup);
            sbs.channel(NioServerSocketChannel.class);
            sbs.handler(new LoggingHandler(LogLevel.INFO));
            sbs.childHandler(new OutInterfaceServerInitializer());
            //Channel ch = sbs.bind(port).sync().channel();
            sbs.bind(port);//.sync();//.channel();
            System.out.println("OutInterface Server started at port " + port + '.');
            //ch.closeFuture().sync();	//等待消息同步, 程序不退出
        } finally {
            //bossGroup.shutdownGracefully();
            //workerGroup.shutdownGracefully();
        }
    }
}
