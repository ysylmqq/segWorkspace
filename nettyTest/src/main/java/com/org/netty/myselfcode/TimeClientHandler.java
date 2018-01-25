package com.org.netty.myselfcode;

import org.apache.log4j.Logger;

import com.org.netty.myselfcode.modal.Constant;
import com.org.netty.myselfcode.modal.RequestResponse;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeClientHandler extends ChannelHandlerAdapter{
	
	public static char []STR =  new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	
	public static final Logger logger = Logger.getLogger(TimeClientHandler.class);
	
	public TimeClientHandler(){
	}

	/**
	 * 客户端连接服务器之后，调用这个方法
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//Scanner scaner = new Scanner(System.in);
		//客户端发送登录包
		RequestResponse result = new RequestResponse();
		result.setHead(0x01);
		result.setModule((short)0x02);
		result.setCmd(Constant.LOGINCMD);
		result.setLength(5);
		result.setBody("login".getBytes());
		ctx.writeAndFlush(result);
	}

	
	/*public String getString(int length){
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
		RequestResponse serverResult = (RequestResponse)msg;
		short cmd = serverResult.getCmd();
		
		RequestResponse result = new RequestResponse();
		result.setHead(0x01);
		result.setModule((short)0x02);
		
		if(cmd == Constant.LOGINACKCMD){
			System.out.println("登录包ACK");
			result.setCmd(Constant.QUERYCMD);
			result.setLength(8);
			result.setBody("queryCar".getBytes());
		}else if(cmd == Constant.QUERYACKCMD){
			System.out.println("查车指令ACK");
			result.setCmd(Constant.CLOSECMD);
			result.setLength(8);
			result.setBody("closeCar".getBytes());
		}else if(cmd == Constant.CLOSEACKCMD){
			System.out.println("关闭省流量指令ACK");
			return ;
		}
		ctx.writeAndFlush(result);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
	
}
