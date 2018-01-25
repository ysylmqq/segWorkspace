package com.org.netty.myselfcode;


import com.org.netty.myselfcode.modal.RequestResponseDecode;
import com.org.netty.myselfcode.modal.RequestResponseEncode;

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
import io.netty.handler.timeout.IdleStateHandler;

public class TimeServer {
	
	/**
	 * 
	 * @author ysy
	 *
	 */
	private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{
		
		/**
		 * 业务线程池
		 */
		private  EventLoopGroup busGroup;
		
		public ChildChannelHandler(EventLoopGroup busGroup) {
			this.busGroup = busGroup;
		}

		@Override
		protected void initChannel(SocketChannel socketChannel) throws Exception {
			//下面两个编解码器，和顺序无关,因为在执行handler的过程当中，会自动判断是 upStream和downStream
			//其他的handler和顺序有关
			socketChannel.pipeline().addLast(new RequestResponseEncode());
			socketChannel.pipeline().addLast(new RequestResponseDecode());
			socketChannel.pipeline().addLast(new IdleStateHandler(30*60, 30*60, 5));// 用来检测客户端的心跳
			socketChannel.pipeline().addLast(busGroup, new TimeServerHandler()); // netty4,5 业务线程池(有序)
		}
		
	}
	
	public void bind(int port){
		// 线程池 用来处理 注册到select上的channel的事件监听
		EventLoopGroup bossGroup = new NioEventLoopGroup(); //接受请求的线程池
		EventLoopGroup workGroup = new NioEventLoopGroup();//处理IO的线程池
		
		//业务线程池
		EventLoopGroup busGroup = new NioEventLoopGroup();
		
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 1024)  // TCP缓冲区
			.handler(new LoggingHandler(LogLevel.INFO))
			.childHandler(new ChildChannelHandler(busGroup));
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
		new TimeServer().bind(8080);
	}
}
