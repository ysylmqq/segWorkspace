package com.org.netty.nopkg;

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
		ByteBuf buf = (ByteBuf) msg; // 类似于nio当中的byteBuffer
		byte req[] = new byte[buf.readableBytes()]; //创建一个buf长度的字节数组
		buf.readBytes(req); // 把msg读取到req字节数组当中
		String  line = System.getProperty("line.separator"); //获取操作系统的分隔符
		String body = new String(req,"UTF-8").substring(0,req.length-line.length());
		System.err.println("客户端发送的消息是 "+body);
	    String nowTime = (new Date()).toString()+line;
	    
	    //向客户端返回消息
		ByteBuf resp = Unpooled.copiedBuffer( "query Time".equalsIgnoreCase(body.trim()) ? nowTime.getBytes() : ("bad date"+line).getBytes());
		ctx.writeAndFlush(resp);
	}

	/**
	 * channelRead 这个方法执行完毕之后  执行下面的方法
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.err.println("[TimeServerHandler]  channelReadComplete");
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}
	
	
}
