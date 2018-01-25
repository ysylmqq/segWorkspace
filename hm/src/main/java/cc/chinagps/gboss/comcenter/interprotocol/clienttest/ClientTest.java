package cc.chinagps.gboss.comcenter.interprotocol.clienttest;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cc.chinagps.lib.util.Config;

public class ClientTest{
	
	private EventLoopGroup workerGroup = new NioEventLoopGroup(); // 连接通信也用多线程， 默认是处理器数量*2
	private ExecutorService taskPool = Executors.newSingleThreadExecutor();

	public static void main(String[] args) {
		ClientTest client = new ClientTest();
		client.startTask();
//		client.shutdown();
	}

	public ClientTest() {
	}

	/**
	 * 开始执行监控
	 * 创建netty的服务端程序
	 */
	public void startTask() {
		taskPool.submit(new Runnable() {
			/**
			 * 定义任务
			 */
			@Override
			public void run() {
				System.out.println("------------------------------通信连接线程启动------------------------------");
					String url = Config.getCmdProperties().getProperty("center_host");
					int port = Integer.valueOf(Config.getCmdProperties().getProperty("center_port"));
					Bootstrap bs = new Bootstrap();
					bs.group(workerGroup).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true).handler(new CenterClientInitializer());
					// Start the client.
					
					while(true) {
		    			try {
		    	            ChannelFuture f = bs.connect(url, port);
		    	            f.sync();
		    	            f.channel().closeFuture().sync();
		    	            //隔5秒重连
		    				Thread.sleep(5000);
		    			} catch(Throwable e) {
		    				//e.printStackTrace();
		    			}
		            }
				/*	
					ChannelFuture f = bs.connect(url, port);
					f.sync();
					// Wait until the connection is closed.
					f.channel().closeFuture().sync();
					System.out.println("run end");*/
			}
		});
	}

	/**
	 * 关闭对象
	 */
	public void shutdown() {
		workerGroup.shutdownGracefully();
		taskPool.shutdown();
		System.out.println("client shutdown");
	}

}