package com.org.netty.heartbeat;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class MarshallingClient {

	public void connet(int port,String host){
		
		//客户端Nio线程组  用这个线程组 连接 ，接受数据
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		//门面模式   通过对外提供的方法进行统一的调用  builder模式
		bootstrap.group(group).channel(NioSocketChannel.class) // 指定通道类型
			.option(ChannelOption.TCP_NODELAY,true)
			.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT) // netty默认的bytebuffer是使用的池
			
			.handler(new ChannelInitializer<SocketChannel>() { // channel双向
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(MarshallingCodeCFactory.builderMarshallingDecoder());
					ch.pipeline().addLast(MarshallingCodeCFactory.builderMarshallingEncoder());
					//客户端和服务器多久没有通信之后断开连接  服务器断和客户端都要加 一句话搞定
					ch.pipeline().addLast(new ReadTimeoutHandler(5));
					ch.pipeline().addLast(new MarshallingClientHandler());
				}
			});
		
		try {
			//异步连接
			ChannelFuture future = bootstrap.connect(host, port).sync();
			//等待客户端关闭
			future.channel().closeFuture().sync();
			// future.channel()  通过channel来进行数据的发送
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			//优雅退出，释放NIO线程组
			group.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) {
		new MarshallingClient().connet(8088,"127.0.0.1");
	}
}
