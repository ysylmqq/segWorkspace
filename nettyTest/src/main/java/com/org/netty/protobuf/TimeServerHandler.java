package com.org.netty.protobuf;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import org.apache.log4j.Logger;
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
		UserDataBuff.UserInfo req = (UserDataBuff.UserInfo)msg;
		System.err.println("收到client data "+req.toString()+"  "+System.currentTimeMillis());
		String remoteAddress = ctx.channel().remoteAddress().toString();
		System.err.println("remoteAddress "+remoteAddress);
		ctx.writeAndFlush(resp(req.getId())); // 回复客户端消息
	}
	
	public UserDataBuff.UserInfo resp(int id){
		 UserDataBuff.UserInfo.Builder builder =  UserDataBuff.UserInfo.newBuilder();
		 builder.setId(id+1);
		 if(id == -1 ){ // 读写超时时，可以返回-1
			 builder.setDesc("time out,you will close!"+System.currentTimeMillis());
		 }else{
			 builder.setDesc("send client data!"+System.currentTimeMillis());
		 }
		 builder.setRespCode(8080);
		 return builder.build();
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
	
	@Override
	public void userEventTriggered(final ChannelHandlerContext ctx, Object evt)
			throws Exception {
		//心跳检测事件
		if(evt instanceof IdleStateEvent){
			IdleStateEvent e = (IdleStateEvent) evt;
			if( e.state() == IdleState.READER_IDLE){
				//ctx.close();
                System.out.println("READER_IDLE 读超时");  
			}else if( e.state() == IdleState.WRITER_IDLE){
				//ctx.close();
                System.out.println("WRITER_IDLE 写超时");  
			}else if( e.state() == IdleState.ALL_IDLE){
                System.out.println("ALL_IDLE 读写超时");  
				ChannelFuture cf =  ctx.writeAndFlush(resp(-1));
				cf.addListener(new ChannelFutureListener() {
					@Override
					public void operationComplete(ChannelFuture future) throws Exception {
						Thread.sleep(5000);
						ctx.channel().close();
					}
				});
			}
		}else{
			super.userEventTriggered(ctx, evt);
		}
	}
	
	
}
