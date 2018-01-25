package com.org.netty.time.client;

import org.apache.log4j.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeClientHandler extends ChannelHandlerAdapter{
	
	public static final Logger logger = Logger.getLogger(TimeClientHandler.class);
	
	private ByteBuf firstMsg; // 用来发送数据的工具
	
	private byte resq[]; //发送内容
	
	public TimeClientHandler(){
		resq = ("query time"+System.getProperty("line.separator")).getBytes();
	}

	/**
	 * 发送请求  客户端发送的ByteBuf对象
	 * 注意:
	 * 		1、如果在handler当中没有编码器解码器，channelActive发送5次请求，得到的相应不一定是五次。
	 * 		2、添加编解码器之后，就会一次请求一次相应
	 */
/*	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for (int i = 0; i < 5; i++) {
			firstMsg = Unpooled.buffer(resq.length);
			firstMsg.writeBytes(resq);
			Thread.sleep(5*1000);
			ctx.writeAndFlush(firstMsg);//客户端发送的和服务器端接受的 必须是同一个数据类型,有很大原因就出现在这个地方
		}
	}
*/
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
		System.out.println("服务端发送 " +body);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}
	
	
}
