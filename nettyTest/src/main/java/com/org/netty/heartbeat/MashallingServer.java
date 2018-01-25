package com.org.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * 
 *1、 该测试方法进行了 心跳检测，master/slave节点之间的数据传输，以及心跳检测的超时情况
 *   在实际的应用场景当中，都会就不会使用长连接也不会使用短连接，会折中，几秒时间内，客户端和
 *   服务器没有数据通信时，客户端就会断开
 *2、虽然netty可以实现文件服务器，但是在实际项目当中不会用的，只是说明netty有这个功能
 */
public class MashallingServer {
	
	public void bind(int port){
		// 线程池 用来处理 注册到select上的channel的事件监听
		EventLoopGroup bossGroup = new NioEventLoopGroup(); //接受请求的线程池
		EventLoopGroup workGroup = new NioEventLoopGroup();//处理IO的线程池
		
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 1024)  // TCP缓冲区
			.handler(new LoggingHandler(LogLevel.INFO))
			.childHandler(new ChannelInitializer<SocketChannel>(){
				@Override
				protected void initChannel(SocketChannel socketChannel) throws Exception {
				    socketChannel.pipeline().addLast(MarshallingCodeCFactory.builderMarshallingDecoder());
					socketChannel.pipeline().addLast(MarshallingCodeCFactory.builderMarshallingEncoder());
					//客户端和服务器多久没有通信之后断开连接  服务器断和客户端尽量都要加 一句话搞定
					socketChannel.pipeline().addLast(new ReadTimeoutHandler(5));
					socketChannel.pipeline().addLast( new MarshallingServerHandler()); // netty4,5 业务线程池(有序)
				}
			});
		try {
			//绑定端口
			ChannelFuture future = bootstrap.bind(port).sync();
			//是服务端一直阻塞，监听该端口
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally{
			//关闭服务器时，释放服务器端的线程资源
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) {
		new MashallingServer().bind(8088);
	}
}
