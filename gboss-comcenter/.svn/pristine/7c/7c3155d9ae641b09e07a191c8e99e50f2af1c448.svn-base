/*
********************************************************************************************
Discription:  WebSocket服务器, 接收WEB坐席， 手机APP的连接，并通信
			  
			  
Written By:   ZXZ
Date:         2014-04-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.websocket;

import java.util.Date;

import cc.chinagps.lib.util.Util;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class WebSocketServer {

    private final int port;		//侦听端口
    EventLoopGroup bossGroup = null;
    EventLoopGroup workerGroup = null;

    public WebSocketServer(int port) {
        this.port = port;
    }

    public void start(boolean bsync) throws Exception {
    	
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
            sbs.childHandler(new WebSocketServerInitializer());
            Channel ch = sbs.bind(port).sync().channel();
            //sbs.bind(port);//.sync();//.channel();
            System.out.println(Util.DatetoString(new Date(System.currentTimeMillis())) + " Web socket server started at port " + port + '.');
            if (bsync) { //只包含websocket
            	ch.closeFuture().sync();	//等待消息同步, 程序不退出
            }
        } finally {
        	if (bsync) {
        		bossGroup.shutdownGracefully();
        		workerGroup.shutdownGracefully();
        	}
        }
    }
}
