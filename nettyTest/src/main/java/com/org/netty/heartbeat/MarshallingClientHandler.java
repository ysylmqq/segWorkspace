package com.org.netty.heartbeat;

import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class MarshallingClientHandler extends ChannelHandlerAdapter{
	
	public static char []STR =  new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	
	public static final Logger logger = Logger.getLogger(MarshallingClientHandler.class);
	
	public static long startTime =0;
	
	/*
	 * 客户端连接成功之后;以1分钟1次的频率进行心跳检测
	 */
	public ScheduledExecutorService sendBeartHeart = Executors.newScheduledThreadPool(1);
	
	/**
	 * 心跳包检测
	 */
	public ScheduledExecutorService beatHeartAck = Executors.newScheduledThreadPool(1);
	
	/**
	 * 客户端连接服务器之后，调用这个方法
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		MarshallingRequest result = new MarshallingRequest();
		result.setHead(0x01);
		result.setModule((short)0x02);
		result.setCmd((short)0x02);
		result.setLength(10);
		Scanner scanner = new Scanner(System.in);
		result.setBody(scanner.next().getBytes());
		ctx.writeAndFlush(result);
	}

	
/*	public String getString(int length){
		StringBuilder result = new StringBuilder(""); 
		for (int i = 0; i < length; i++) {
			result.append(STR[RandomUtils.nextInt(20)]);
		}
		return result.toString();
	}*/
	
	/**
	 * 读取服务器返回的数据
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		MarshallingRequest result = (MarshallingRequest)msg;
		String ack = new String(result.getBody());
		if("1".equals(ack)){
			//服务器端返回客户端连接成功接下来客户端发送心跳包
			System.err.println("客户端登录成功");
			sendBeartHeart.scheduleAtFixedRate(new BeanHeart(ctx), 1, 1, TimeUnit.MINUTES);
			//30s之后开始启动线程，每隔5s检测一次，
			beatHeartAck.scheduleAtFixedRate(new BeanHeartACK(ctx), 30, 5, TimeUnit.SECONDS); 
		}else{
			//通过一个线程，来判断心跳包是否有应答或延时
			System.err.println("心跳检测结果: "+ack);
			startTime =0;
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
	/**
	 * 发送心跳的线程
	 * @author ysy
	 *
	 */
	public class BeanHeart implements Runnable{
		
		private  ChannelHandlerContext ctx;
		
		BeanHeart(ChannelHandlerContext ctx){
			this.ctx = ctx;
		}
		
		@Override
		public void run() {
			MarshallingRequest result = new MarshallingRequest();
			result.setHead(0x01);
			result.setModule((short)0x03);
			result.setCmd((short)0x03);
			result.setLength(10);
			long time = System.currentTimeMillis();
			result.setBody( (String.valueOf(time) ).getBytes() );
			ctx.writeAndFlush(result);
			startTime = System.currentTimeMillis();
		}
	}
	
	/**
	 * 检测心跳包是否超时 
	 *
	 */
	public class BeanHeartACK implements Runnable{
		
		private  ChannelHandlerContext ctx;
		
		BeanHeartACK(ChannelHandlerContext ctx){
			this.ctx = ctx;
		}
		
		@Override
		public void run() {
			long now = System.currentTimeMillis();
			//超过40s就，说明是网络延时或服务器端连接不上 (可以考虑重试机制)
			if( MarshallingClientHandler.startTime != 0){
				if( now - MarshallingClientHandler.startTime > 40*1000 ){
					System.err.println("心跳检测超时");
					ctx.close();
				}
			}
		}
	}
	
}
