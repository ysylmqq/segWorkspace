package com.org.netty.time.server;

import java.util.Date;

import org.apache.log4j.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
/**
 * 笔记：
 * 1、项目当中所有的Demo都是基于netty5.00，并且在maven当中引入时，无法下载，通过手动你的方式进行安装jar
 * 
 *
 */
public class TimeServerHandler extends ChannelHandlerAdapter {

	public static final Logger  logger = Logger.getLogger(TimeServerHandler.class);
	
	/** 不添加编解码 客户端发送的是ByteBuf对象
	 * 读取客户端发送的消息  msg直接可以转为ByteBuf,因为客户端发送的就是ByteBuf
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
		//处理关闭之后，服务器端断开连接
		ctx.close();
	}
	

	/**
	 * channelRead 这个方法执行完毕之后  执行下面的方法
	 */
	/*public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.err.println("[TimeServerHandler]  channelReadComplete");
		ctx.flush();
	}*/

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
	
}
