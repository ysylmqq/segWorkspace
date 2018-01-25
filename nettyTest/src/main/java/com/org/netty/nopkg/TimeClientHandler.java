package com.org.netty.nopkg;

import org.apache.log4j.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeClientHandler extends ChannelHandlerAdapter{
	
	public static final Logger logger = Logger.getLogger(TimeClientHandler.class);
	
	private ByteBuf firstMsg;
	
	private byte resq[];
	
	public TimeClientHandler(){
		resq = ("query time"+System.getProperty("line.separator")).getBytes();
	}

	/**
	 * 发送请求
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.err.println("[TimeClientHandler] channelActive");
		for (int i = 0; i < 5; i++) {
			firstMsg = Unpooled.buffer(resq.length);
			firstMsg.writeBytes(resq);
			Thread.sleep(5*1000);
			ctx.writeAndFlush(firstMsg);
		}
	}

	/**
	 * 读取客户端的请求
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		byte req[] = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req,"UTF-8");
		System.err.println("服务端发送 " +body);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}
	
	
}
