package com.org.netty.protobuf;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
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

		@Override
		protected void initChannel(SocketChannel socketChannel) throws Exception {
			/**
			 * 服务器端,先解码，应答时在编码
			 */
			socketChannel.pipeline().addLast(new ProtobufVarint32FrameDecoder());// 解码时的半包处理
			socketChannel.pipeline().addLast(new IdleStateHandler(5, 5, 15));// 用来检测客户端的心跳
			socketChannel.pipeline().addLast(new ProtobufDecoder(UserDataBuff.UserInfo.getDefaultInstance())); // pojo 类
			socketChannel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender()); //编码时的半包处理
			socketChannel.pipeline().addLast(new ProtobufEncoder()); //编码
			
			socketChannel.pipeline().addLast("timeServerHandler", new TimeServerHandler());
		}
		
	}
	
	public void bind(int port){
		// 线程池 用来处理 注册到select上的channel的事件监听
		EventLoopGroup bossGroup = new NioEventLoopGroup(); 
		EventLoopGroup workGroup = new NioEventLoopGroup();
		
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 4096)  // 队列长度
			.handler(new LoggingHandler(LogLevel.INFO))
			.childHandler(new ChildChannelHandler());
		try {
			//绑定端口
			ChannelFuture future = bootstrap.bind("127.0.0.1",port).sync();
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
