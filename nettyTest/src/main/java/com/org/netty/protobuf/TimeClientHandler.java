package com.org.netty.protobuf;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import org.apache.log4j.Logger;

public class TimeClientHandler extends ChannelHandlerAdapter{
	
	public static final Logger logger = Logger.getLogger(TimeClientHandler.class);
	
	
	
	public TimeClientHandler(){
	}

	/**
	 * 客户端连接服务器之后，调用这个方法
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.err.println("[TimeClientHandler] channelActive");
		for (int i = 0; i < 1; i++) {
			UserDataBuff.UserInfo userinfo = resp(i);
			ctx.write(userinfo);
			System.err.println("客户端发送 :  "+userinfo.toString()+"  "+System.currentTimeMillis());
		}
		ctx.flush();
	}

	
	/**
	 * 读取客户端的请求
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		UserDataBuff.UserInfo userInfo = (UserDataBuff.UserInfo) msg;
		System.out.println("客户端收到服务器返回   " +userInfo.toString()+"  "+System.currentTimeMillis());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
	public UserDataBuff.UserInfo resp(int id){
		 UserDataBuff.UserInfo.Builder builder =  UserDataBuff.UserInfo.newBuilder();
		 builder.setId(id+1);
		 builder.setDesc("client send info");
		 builder.setRespCode(7070);
		 return builder.build();
	}
	
}
