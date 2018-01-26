package cc.chinagps.gateway.aplan;

import java.nio.ByteBuffer;
import java.util.LinkedList;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import cc.chinagps.gateway.stream.InputStreamController;
import cc.chinagps.gateway.util.IOUtil;

public class APlanNettyHandler extends SimpleChannelUpstreamHandler{
	private APlanServer server;
	
	public APlanNettyHandler(APlanServer server){
		this.server = server;
	}
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		Channel channel = e.getChannel();
		ChannelBuffer buff0 = (ChannelBuffer) e.getMessage();
		int length = buff0.readableBytes();
		ByteBuffer buff = buff0.toByteBuffer();
		
		APlanSocketSession session = server.getSessionManager().getSession(channel);
		if(session == null){
			return;
		}
		session.setLastActiveTime(System.currentTimeMillis());
		
		try{
			InputStreamController packageMaker = session.getPackageMaker();
			packageMaker.dealData(buff, length);
		}catch (Throwable e1) {
			server.exceptionCaught(e1, buff.array(), session);
			IOUtil.closeChannel(e.getChannel());
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		server.exceptionCaught(e.getCause());
		IOUtil.closeChannel(e.getChannel());
	}
	
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		Channel channel = e.getChannel();
		//atm
		LinkedList<byte[]> atm = new LinkedList<byte[]>();
		channel.setAttachment(atm);
		
		//session
		APlanSocketSession session = new DefaultAPlanSocketSession(server, channel);
		session.setPackageMaker(new APlanPackageMaker(server, session));
		
		session.setMaxKeepAliveTime(server.getAplanSessionTimeout());
		server.getSessionManager().addSession(channel, session);
		
		super.channelConnected(ctx, e);
	}
	
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) throws Exception {
		server.getSessionManager().removeSession(e.getChannel());
		super.channelDisconnected(ctx, e);
	}
	
	@Override
	public void channelInterestChanged(ChannelHandlerContext ctx,
			ChannelStateEvent e) throws Exception {
		Channel channel = e.getChannel();
		@SuppressWarnings("unchecked")
		LinkedList<byte[]> list = (LinkedList<byte[]>) channel.getAttachment();
		synchronized (list) {
			while(list.size() > 0){
				if(channel.isWritable()){
					byte[] data = list.poll();
					ChannelBuffer tosend = ChannelBuffers.wrappedBuffer(data);
					channel.write(tosend);
				}else{
					break;
				}
			}
		}
	}
}