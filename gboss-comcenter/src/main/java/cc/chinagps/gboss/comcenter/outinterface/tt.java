package cc.chinagps.gboss.comcenter.outinterface;


import cc.chinagps.gboss.comcenter.CenterClientManager;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class tt {
	
    public static void main(String[] args) {
    	try {
	    	tt client = new tt();
	    	client.start(8090);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }
    	
    private void start(int port) throws Exception { 
        EventLoopGroup bossGroup = new NioEventLoopGroup();		//因为有很多手机APP连接，所以用多线程 侦听
        EventLoopGroup workerGroup = new NioEventLoopGroup();	//连接通信也用多线程， 默认是处理器数量*2
        try {
            ServerBootstrap sbs = new ServerBootstrap();
            sbs.group(bossGroup, workerGroup);
            sbs.channel(NioServerSocketChannel.class);
            sbs.handler(new LoggingHandler(LogLevel.INFO));
            sbs.childHandler(new OutInterfaceServerInitializer());
            Channel ch = sbs.bind(port).sync().channel();
            //sbs.bind(port);//.sync();//.channel();
            System.out.println("OutInterface Server started at port " + port + '.');
            ch.closeFuture().sync();	//等待消息同步, 程序不退出
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
