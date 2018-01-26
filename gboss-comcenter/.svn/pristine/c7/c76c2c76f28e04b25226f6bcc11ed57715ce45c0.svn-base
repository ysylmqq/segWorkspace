package cc.chinagps.gboss.comcenter.interprotocol.clienttest;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ClientTest {
	
    public static void main(String[] args) {
    	try {
	    	ClientTest client = new ClientTest();
	    	client.start(args);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }
    	
    private void start(String[] args) throws Exception { 
        EventLoopGroup workerGroup = new NioEventLoopGroup();	//连接通信也用多线程， 默认是处理器数量*2
        String serverip = "90.0.13.113";  
        int serverport = 17002;
        
        try {
            if (args.length > 0) {
            	serverip = args[0].trim();
            }
            if (args.length > 1) {
            	serverport = Integer.valueOf(args[1].trim());
            }
        	Bootstrap bs = new Bootstrap();
            bs.group(workerGroup)
              .channel(NioSocketChannel.class)
              .option(ChannelOption.TCP_NODELAY, true)
              //.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)	//10秒连接超时
              .handler(new CenterClientInitializer());
            
            // Start the client.
            while(true) {
    			try {
    	            ChannelFuture f = bs.connect(serverip, serverport);
    	            f.sync();
    	            f.channel().closeFuture().sync();
    	            //如果断开，则隔5秒重连
    			} catch (Throwable e) {
    			}
    			try {
    				Thread.sleep(5000);
    			} catch(Exception e) {
    				
    			}
            }
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
