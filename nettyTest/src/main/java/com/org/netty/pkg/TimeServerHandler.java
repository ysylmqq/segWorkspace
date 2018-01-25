package com.org.netty.pkg;

import java.util.Date;

import org.apache.log4j.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
/**
 * 基于netty5.00
 * @author ysy
 *
 */
public class TimeServerHandler extends ChannelHandlerAdapter {

	public static final Logger  logger = Logger.getLogger(TimeServerHandler.class);
	/**
	 * 读取客户端发送的消息
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception {
		//解析客户端发送的消息
		String  line = System.getProperty("line.separator");
		String body = (String) msg;
		System.err.println("客户端发送的消息是 "+body);
	    String nowTime = (new Date()).toString()+line;
	    
	    //向客户端返回消息
		ByteBuf resp = Unpooled.copiedBuffer( "query Time".equalsIgnoreCase(body.trim()) ? nowTime.getBytes() : ("bad date"+line).getBytes());
		ctx.writeAndFlush(resp);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.err.println("[TimeServerHandler]  channelReadComplete");
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		System.err.println("[TimeServerHandler]  exceptionCaught");
		cause.printStackTrace();
		ctx.close();
	}
	
	
}
