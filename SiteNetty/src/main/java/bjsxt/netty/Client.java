package bjsxt.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class Client {
	
	private static class SingletonHolder {
		static final Client instance = new Client();
	}
	
	public static Client getInstance(){
		return SingletonHolder.instance;
	}
	
	private EventLoopGroup group;
	private Bootstrap b;
	private ChannelFuture cf ;
	private Client(){
			group = new NioEventLoopGroup();
			b = new Bootstrap();
			b.group(group)
			 .channel(NioSocketChannel.class)
			 .handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel sc) throws Exception {
						sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
						sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
						sc.pipeline().addLast(new ReadTimeoutHandler(60)); // 60s服务器端没有返回时，就会断开
						sc.pipeline().addLast(new ClientHandler());
					}
		    });
	}
	
	public void connect(){
		try {
			this.cf = b.connect("localhost", 8765).sync();
			System.out.println("远程服务器已经连接, 可以进行数据交换..");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	public ChannelFuture getChannelFuture(){
		if(this.cf == null) {
			this.connect();
		}
		if(!this.cf.channel().isActive()){
			this.connect();
		}
		return this.cf;
	}
	
	public void close(){
		try {
			this.cf.channel().closeFuture().sync();
			this.group.shutdownGracefully();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
