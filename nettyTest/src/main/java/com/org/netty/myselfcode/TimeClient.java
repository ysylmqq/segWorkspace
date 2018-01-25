package com.org.netty.myselfcode;



import com.org.netty.myselfcode.modal.RequestResponseDecode;
import com.org.netty.myselfcode.modal.RequestResponseEncode;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeClient {

	private ChannelFuture future;
	
	public void getChannel(){
		System.err.println("从新连接  "+future);
		if(future == null ){
			System.err.println("从新连接");
			connet(8080,"127.0.0.1");
		}
	}
	
	
	public void connet(int port,String host){
		
		//客户端Nio线程组  用这个线程组 连接 ，接受数据
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		//门面模式   通过对外提供的方法进行统一的调用  builder模式
		bootstrap.group(group).channel(NioSocketChannel.class) // 指定通道类型  NioSocketChannel
			.option(ChannelOption.TCP_NODELAY,true)
			.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT) // netty默认的bytebuffer是使用的池
			.handler(new ChannelInitializer<SocketChannel>() { // channel双向
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new RequestResponseEncode());
					ch.pipeline().addLast(new RequestResponseDecode());
					ch.pipeline().addLast(new TimeClientHandler());
				}
			});
		
		try {
			future = bootstrap.connect(host, port).sync();
			future.channel().closeFuture().sync(); //阻塞
			future = null;
			//断开之后重新连接
	/*		new Thread(new Runnable() {
				@Override
				public void run() {
					System.err.println("连接断开，子线程重新连接");
					for (int i = 3; i < 5; i++) {
						RequestResponse result = new RequestResponse();
						result.setHead(0x01);
						result.setModule((short)0x02);
						result.setCmd((short)0x02);
						result.setLength(1);
						result.setBody("1".getBytes());
						getChannel();
						future.channel().writeAndFlush(result);
					}
				}
			}).start();*/
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			//优雅退出，释放NIO线程组
			group.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) {
		new TimeClient().connet(8080,"127.0.0.1");
	}
}
