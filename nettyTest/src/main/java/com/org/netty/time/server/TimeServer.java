package com.org.netty.time.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 *笔记:
 *	1、在server端，创建两个线程池，一个workGroup线程池是用来说接受客户端的连接请求;
 *		bossGroup线程池是用来进行业务处理。
 *  2、在启动的时候要先启动服务器端,在启动客户端
 *  3、添加StringDecoder编码器时  发送(writeAndFlush的时候)的必须使用ByteBuf类型来进行封装，
 *  	不能直接是的String，但是在read的时候可以直接把msg转为String
 *    客户端(服务端)发送Unpooled.wrappedBuffer("");
 *    服务器端(客户端)接受是 String req = (String)msg;
 *    StringDecoder直接把Bytebuf转换成String
 *    
 *  
 *
 */
public class TimeServer {
	
	private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{
		@Override
		protected void initChannel(SocketChannel socketChannel) throws Exception {
	/*		socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
			socketChannel.pipeline().addLast(new StringDecoder());*/
			socketChannel.pipeline().addLast("timeServerHandler", new TimeServerHandler());
		}
	}
	
	public void bind(int port){
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workGroup)
			.channel(NioServerSocketChannel.class) //服务器端使用的是同步非阻塞通道
			.option(ChannelOption.SO_BACKLOG, 4096)  // 队列长度
			.childHandler(new ChildChannelHandler());
		try {
			//绑定端口
			ChannelFuture future = bootstrap.bind(port).sync();
			//服务端一直阻塞，监听该端口 相当于Thread.sleep(Integer.MAX);
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally{
			//关闭服务器时，释放服务器端的线程资源,进行优雅的关闭
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) {
		new TimeServer().bind(9876);
	}
}
