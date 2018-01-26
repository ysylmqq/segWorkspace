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
import cc.chinagps.gateway.unit.beforemarket.BeforeMarketPackageMaker;
import cc.chinagps.gateway.unit.common.UnitCommon;
import cc.chinagps.gateway.unit.kelx.KlxTransitionPackageMaker;
import cc.chinagps.gateway.unit.leopaard.define.LeopaardDefines;
import cc.chinagps.gateway.unit.leopaard.upload.BigDataDisposer;
import cc.chinagps.gateway.unit.leopaard.upload.LeopaardStreamHandler;
import cc.chinagps.gateway.unit.mult.OX7EPackageMaker;
import cc.chinagps.gateway.unit.seg.upload.SegStreamHandler;
import cc.chinagps.gateway.unit.useat.upload.USeatStreamHandler;
import cc.chinagps.gateway.util.HexUtil;
import cc.chinagps.gateway.util.IOUtil;

public class NettyHandler extends SimpleChannelUpstreamHandler{
	private Logger logger_others = Logger.getLogger(LogManager.LOGGER_NAME_OTHERS);
	//private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	
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
		
		/*ByteBuffer buff = ByteBuffer.allocate(length);
		byte bufferData[] = new byte[length];
		byteBuffer.get(bufferData);
		buff.put(bufferData);
		buff.flip();*/
		
		
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
				//判断协议类型, 设定packageMaker
				byte[] data = buff.array();
				byte first = data[0];
				if(first == (byte) 0x5B){
					//seg
					InputStreamController pm = new SegStreamHandler(server, session);
					session.setPackageMaker(pm);
					session.setPackageEncoder(UnitCommon.segDownloadCmdEncoder);
					session.setProtocolType("seg");
					pm.dealData(buff, length);
				}else if(first == (byte) 0xAA){
					//klx
					KlxTransitionPackageMaker pm = new KlxTransitionPackageMaker(server, session);
					session.setPackageMaker(pm);
					session.setPackageEncoder(UnitCommon.klxDownloadHandlers);
					session.setProtocolType("klx");
					pm.dealData(buff, length);
				}else if(first == (byte) 0x7D){
					//haima
					BeforeMarketPackageMaker itp = new BeforeMarketPackageMaker(server, session);
					session.setPackageMaker(itp);
					itp.dealData(buff, length);
				}else if(first == (byte) 0x7E){
					//db44 or bubiao or 鹏奥达
					//EndFlagTransitionPackageMaker pm = new EndFlagTransitionPackageMaker(server, session);
					//session.setPackageMaker(pm);
					//pm.dealData(buff, length);
					OX7EPackageMaker pm = new OX7EPackageMaker(server, session);
					session.setPackageMaker(pm);
					pm.dealData(buff, length);
				}else if(first == LeopaardDefines.STX){
					//leopaard
					InputStreamController pm = new LeopaardStreamHandler(server, session);
					session.setPackageMaker(pm);
					session.setPackageEncoder(UnitCommon.leopaardDownloadCmdEncoder);
					session.setProtocolType(LeopaardDefines.PROTOCOLTYPE);
					if (session.getBigDataDisposer() == null) {
						BigDataDisposer bigDataDisposer = new BigDataDisposer();
						session.setBigDataDisposer(bigDataDisposer);
					}
					pm.dealData(buff, length);
				}else if(first == (byte) 0xEF){
					//useat
					InputStreamController pm = new USeatStreamHandler(server, session);
					session.setPackageMaker(pm);
					session.setProtocolType("useat");
					
					if(length > 1){
						ByteBuffer left = ByteBuffer.wrap(data, 1, length - 1);
						pm.dealData(left, length - 1);
					}
				}else{
					//未知协议类型
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