package com.org.netty.heartbeat;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class MarshallingServerHandler extends ChannelHandlerAdapter {

	/**
	 * 如果客户端连接成功之后; 以1分钟1次的频率向客户端进行数据同步
	 */
	public ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		MarshallingRequest result = (MarshallingRequest)msg;
		String token = new String(result.getBody());
		//token验证，验证成功，返回客户端成功连接  否则，关闭连接 
		/**
		 * 1、客户端进行连接时，一定要进行登录验证，不然每一个客户端都可以连接
		 * 		把允许连接的客户端存放到数据库当中进行判断
		 * 2、在心跳检测的时候，客户端发送请求包到服务器端，服务器端进行更新相应的记录
		 * 	  同时服务器端有一个定时任务，去判断哪些客户端在规定的时间内没有发送请求数据到服务器，
		 * 	 超时就断开连接。
		 */
		if( "1234567890".equals(token) ){
			System.err.println("登录包: "+token);
			result.setBody("1".getBytes());
			ctx.writeAndFlush(result);
			executorService.scheduleAtFixedRate(new SyncMasterData(ctx), 10, 1, TimeUnit.MINUTES);
		} else{
			System.err.println("心跳包: "+token);
			//模拟网络延时
			if(Math.random()>0.5){
				System.err.println();
				Thread.sleep(50*1000);
			}
			result.setBody("ok".getBytes());
			ctx.writeAndFlush(result);
			//ctx.channel().close();
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		super.exceptionCaught(ctx, cause);
	}

	/**
	 * 服务器端向客户端同步数据
	 * @author ysy
	 *
	 */
	public class SyncMasterData implements Runnable{
		
		private  ChannelHandlerContext ctx;
		
		SyncMasterData(ChannelHandlerContext ctx){
			this.ctx = ctx;
		}
		
		@Override
		public void run() {
			MarshallingRequest result = new MarshallingRequest();
			result.setHead(0x01);
			result.setModule((short)0x03);
			result.setCmd((short)0x03);
			result.setLength(10);
			result.setBody( ("服务器端1分钟同步一次数据").getBytes() );
			ctx.writeAndFlush(result);
		}
	}
	
	
}
