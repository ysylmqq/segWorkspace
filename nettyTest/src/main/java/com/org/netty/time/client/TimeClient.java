package com.org.netty.time.client;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeClient {

	public void connet(int port,String host){
		//客户端Nio线程组 客户端只有一个workGroup线程组，用来进行客户端的业务处理的
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(group).channel(NioSocketChannel.class) //用的也是同步非阻塞通过
			.option(ChannelOption.TCP_NODELAY,true) // 发送缓存区当中 只要有数据就发送
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
				/*	ch.pipeline().addLast(new LineBasedFrameDecoder(1024)); 
					ch.pipeline().addLast(new StringDecoder());*/
					//StringDecoder 发送的必须是String类型，不能是ByteBuf
					ch.pipeline().addLast(new TimeClientHandler());
				}
			});
		
		try {
			//异步连接
			ChannelFuture future = bootstrap.connect(host, port).sync();
			//发送消息
			byte resq[] = ("query time"+System.getProperty("line.separator")).getBytes();
			ByteBuf firstMsg =Unpooled.buffer(resq.length);
			firstMsg.writeBytes(resq);
			future.channel().writeAndFlush(firstMsg);
			
			//由于future是异步连接，所以要阻塞主线程(main)
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			//优雅退出，释放NIO线程组
			group.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) {
		new TimeClient().connet(9876,"127.0.0.1");
	}
}
