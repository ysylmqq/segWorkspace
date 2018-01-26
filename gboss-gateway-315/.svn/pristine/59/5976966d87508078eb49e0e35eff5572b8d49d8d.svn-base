package cc.chinagps.gateway.unit;

import java.nio.ByteBuffer;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.stream.InputStreamController;
import cc.chinagps.gateway.unit.common.UnitCommon;
import cc.chinagps.gateway.unit.kelong.define.KeLongDefines;
import cc.chinagps.gateway.unit.kelong.upload.KeLongStreamHandler;
import cc.chinagps.gateway.unit.seg.upload.SegStreamHandler;
import cc.chinagps.gateway.unit.useat.upload.USeatStreamHandler;
import cc.chinagps.gateway.util.HexUtil;
import cc.chinagps.gateway.util.IOUtil;

public class NettyHandler extends SimpleChannelUpstreamHandler{
	private Logger logger_others = Logger.getLogger(LogManager.LOGGER_NAME_OTHERS);
	
	private UnitServer server;
	
	public NettyHandler(){
		
	}
	public NettyHandler(UnitServer server){
		this.server = server;
	}
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		Channel channel = e.getChannel();
		ChannelBuffer buff0 = (ChannelBuffer) e.getMessage();
		int length = buff0.readableBytes();
		ByteBuffer buff = buff0.toByteBuffer();
		
		UnitSocketSession session = server.getSessionManager().getSession(channel);
		if(session == null){
			return;
		}
		session.setLastActiveTime(System.currentTimeMillis());
		
		try{
			InputStreamController packageMaker = session.getPackageMaker();
			if(packageMaker != null){
				packageMaker.dealData(buff, length);
			}else{
				byte[] data = buff.array();
				byte first = data[0];
				if(first == (byte) 0x5B){
					InputStreamController pm = new SegStreamHandler(server, session);
					session.setPackageMaker(pm);
					session.setPackageEncoder(UnitCommon.segDownloadCmdEncoder);
					session.setProtocolType("seg");
					pm.dealData(buff, length);
				}else if(first == (byte) 0xEF){
					InputStreamController pm = new USeatStreamHandler(server, session);
					session.setPackageMaker(pm);
					session.setProtocolType("useat");
					if(length > 1){
						ByteBuffer left = ByteBuffer.wrap(data, 1, length - 1);
						pm.dealData(left, length - 1);
					}
				}else if(first == KeLongDefines.STX){
					InputStreamController pm = new KeLongStreamHandler(server, session);
					session.setPackageMaker(pm);
					session.setPackageEncoder(UnitCommon.keLongDownloadCmdEncoder);
					session.setProtocolType(KeLongDefines.PROTOCOLTYPE);
					pm.dealData(buff, length);
				}else{
					String hexStr = HexUtil.byteToHexStr(data, length);
					logger_others.info("未知车台类型, 地址:" + session.getRemoteAddress() + ", 源码:" + hexStr);	
					session.close();
				}
			}
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
		//setAttachment
		LinkedList<byte[]> atm = new LinkedList<byte[]>();
		channel.setAttachment(atm);
		
		//addSession
		UnitSocketSession session = new DefaultUnitSocketSession(server, channel);
		session.setMaxKeepAliveTime(server.getUnitSessionTimeout());
		server.getSessionManager().addSession(channel, session);
		
		super.channelConnected(ctx, e);
	}
	
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) throws Exception {		
		//System.out.println("e-dis[" + Thread.currentThread().getId() + "](" + e.getChannel().getRemoteAddress() + ")" + System.currentTimeMillis());	
		server.getSessionManager().removeSession(e.getChannel());
		super.channelDisconnected(ctx, e);
	}
	
	@Override
	public void channelInterestChanged(ChannelHandlerContext ctx,
			ChannelStateEvent e) throws Exception {
		Channel channel = e.getChannel();
		UnitSocketSession session = server.getSessionManager().getSession(channel);
		
		@SuppressWarnings("unchecked")
		LinkedList<byte[]> list = (LinkedList<byte[]>) channel.getAttachment();
		synchronized (list) {
			while(list.size() > 0){
				if(channel.isWritable()){
					byte[] data = list.poll();
					ChannelBuffer tosend = ChannelBuffers.wrappedBuffer(data);
					channel.write(tosend);
					
					//add trace					
					UnitCommon.sendDownloadTrace(data, server, session);
				}else{
					break;
				}
			}
		}
		
		//升级
		if(session.isUpdating()){
			while(channel.isWritable()){
				int len = session.sendNextFileData();
				if(len == -1){
					session.endSendUpdateFile();
					break;
				}
			}
		}
	}
}