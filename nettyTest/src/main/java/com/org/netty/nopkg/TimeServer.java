package com.org.netty.nopkg;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 没有使用编码解码器，所以出现了 发送了5次请求，只是得到了2次应答。
 *
 */
public class TimeServer {
	
	/**
	 * 
	 * @author ysy
	 *
	 */
	private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

		@Override
		protected void initChannel(SocketChannel socketChannel) throws Exception {
		/*	socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
			socketChannel.pipeline().addLast(new StringDecoder());*/
			socketChannel.pipeline().addLast("timeServerHandler", new TimeServerHandler());
		}
		
	}
	
	public void bind(int port){
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 4096)  // 队列长度
			.childHandler(new ChildChannelHandler());
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
