package com.org.netty.myselfcode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.org.netty.myselfcode.modal.Constant;
import com.org.netty.myselfcode.modal.RequestResponse;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
/**
 * 基于netty5.00
 * @author ysy
 *
 */
public class TimeServerHandler extends ChannelHandlerAdapter {

	public static final Logger  logger = Logger.getLogger(TimeServerHandler.class);
	
	/**
	 * 业务线程池  cpu*2
	 */
	public static  ExecutorService executorService =  Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2);
	
	public static List<ChannelHandlerContext> list = new ArrayList<ChannelHandlerContext>();
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
	/*	for (int i = 0; i < list.size(); i++) {
			RequestResponse result = new RequestResponse();
			result.setHead(0x01);
			result.setModule((short)0x02);
			result.setCmd((short)0x03);
			result.setLength(("总共有"+list.size()+"个").getBytes().length);
			result.setBody(("总共有"+list.size()+"个").getBytes());
			ctx.writeAndFlush(result);
		}
		list.add(ctx);
		super.channelActive(ctx);*/
	}

	/**
	 * 读取客户端发送的消息(业务处理过程) 需要用多线程来进行处理
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception {
		handler(ctx,msg); //单线程来处理
		//用多线程处理   会有问题，因为同一个请求不一定有序 必须保证有序
		//executorService.execute(handlerThread(ctx,msg));
	}
	
    public Runnable handlerThread(final ChannelHandlerContext ctx,final Object msg){
    	return new Runnable() {
			@Override
			public void run() {
				System.err.println(Thread.currentThread().getName());
				RequestResponse result = (RequestResponse)msg;
				System.err.println("result "+result+"   "+new String(result.getBody()));
				result.setBody("123456789".getBytes());
				result.setLength(9);
				ctx.writeAndFlush(result);
			}
		};
    }
	
	public void handler(ChannelHandlerContext ctx,Object msg) throws InterruptedException{
		RequestResponse result = (RequestResponse)msg;
		short cmd = result.getCmd();

		if(cmd == Constant.LOGINCMD){
			System.out.println("登录包");
			Thread.sleep(2*1000);
			result.setCmd(Constant.LOGINACKCMD);
			result.setLength(8);
			result.setBody("loginACK".getBytes());
		}else if(cmd == Constant.QUERYCMD){
			System.out.println("查车指令");
			Thread.sleep(2*1000);
			result.setCmd(Constant.QUERYACKCMD);
			result.setLength(8);
			result.setBody("queryACK".getBytes());
		}else if(cmd == Constant.CLOSECMD){
			System.out.println("关闭省流量指令");
			Thread.sleep(2*1000);
			result.setCmd(Constant.CLOSEACKCMD);
			result.setLength(8);
			result.setBody("closeACK".getBytes());
		}
		
		ctx.writeAndFlush(result);
		//如果msg是 ByteBuffer类型,就手动释放，其实不用手动释放netty也会自动释放
		//ReferenceCountUtil.release(msg);//必须释放
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.err.println("[TimeServerHandler]  channelReadComplete");
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
	//IdleStateHandler 根据客户端设置的时间来触发
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
				ChannelFuture cf =  ctx.writeAndFlush("close");
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

	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise)
			throws Exception {
		super.close(ctx, promise);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		list.remove(ctx);
		for (int i = 0; i < list.size(); i++) {
			RequestResponse result = new RequestResponse();
			result.setHead(0x01);
			result.setModule((short)0x02);
			result.setCmd((short)0x03);
			result.setLength(("总共有"+list.size()+"个").getBytes().length);
			result.setBody(("总共有"+list.size()+"个").getBytes());
			ctx.writeAndFlush(result);
		}
		super.channelInactive(ctx);
	}
	
	
}
