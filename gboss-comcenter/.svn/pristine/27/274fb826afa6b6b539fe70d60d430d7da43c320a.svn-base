/*
********************************************************************************************
Discription:  处警坐席WebSocket服务器, 接收WEB坐席，并通信
			  
			  
Written By:   ZXZ
Date:         2014-08-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.alarmarray.websocket;

import io.netty.bootstrap.ServerBootstrap;
//import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class SeatWebSocketServer {
    
	private final int port;		//侦听端口
    EventLoopGroup bossGroup = null;
    EventLoopGroup workerGroup = null;

    public SeatWebSocketServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
    	
        bossGroup = new NioEventLoopGroup();	//因为有很多坐席连接，所以用多线程 侦听
        workerGroup = new NioEventLoopGroup();	//连接通信也用多线程， 默认是处理器数量*2
        try {
            ServerBootstrap sbs = new ServerBootstrap();
            sbs.group(bossGroup, workerGroup);
            sbs.channel(NioServerSocketChannel.class);
            sbs.childHandler(new SeatWebSocketServerInitializer());
            //Channel ch = sbs.bind(port).sync().channel();
            sbs.bind(port).sync();
            System.out.println("Seat Web socket server started at port " + port + '.');
            //ch.closeFuture().sync();	//等待消息同步, 程序不退出
        } finally {
            //bossGroup.shutdownGracefully();
            //workerGroup.shutdownGracefully();
        }
    }
}
