package cc.chinagps.gateway.aplan;

import java.net.SocketAddress;
import java.util.LinkedList;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;

import cc.chinagps.gateway.stream.InputStreamController;

public class DefaultAPlanSocketSession implements APlanSocketSession{
	private InputStreamController packageMaker;
	
	private long lastActiveTime = System.currentTimeMillis();;
	
	private long maxKeepAliveTime = 300000L;
	
	private APlanServer server;
	
	private Channel channel;
	
	public DefaultAPlanSocketSession(APlanServer server, Channel channel){
		this.server = server;
		this.channel = channel;
	}

	@Override
	public long getLastActiveTime() {
		return lastActiveTime;
	}

	@Override
	public void setLastActiveTime(long lastActiveTime) {
		this.lastActiveTime = lastActiveTime;
	}

	@Override
	public long getMaxKeepAliveTime() {
		return maxKeepAliveTime;
	}

	@Override
	public void setMaxKeepAliveTime(long maxKeepAliveTime) {
		this.maxKeepAliveTime = maxKeepAliveTime;
	}
	
	@Override
	public InputStreamController getPackageMaker() {
		return packageMaker;
	}

	@Override
	public void setPackageMaker(InputStreamController packageMaker) {
		this.packageMaker = packageMaker;
	}
	
	private boolean isLogin;
	
	@Override
	public boolean isLogin() {
		return isLogin;
	}

	@Override
	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
	
	@Override
	public void close(){
		server.getSessionManager().removeSession(channel);
	}
	
	@Override
	public void sendData(byte[] data){
		//ChannelBuffer tosend = ChannelBuffers.wrappedBuffer(data);
		//channel.write(tosend);
		@SuppressWarnings("unchecked")
		LinkedList<byte[]> list = (LinkedList<byte[]>) channel.getAttachment();
		synchronized (list) {
			if(channel.isWritable()){
				ChannelBuffer tosend = ChannelBuffers.wrappedBuffer(data);
				channel.write(tosend);
			}else{
				if(list.size() < server.getSendQueueMaxSize()){
					list.add(data);
				}
			}
		}
	}

	@Override
	public SocketAddress getRemoteAddress() {
		return channel.getRemoteAddress();
	}

	@Override
	public SocketAddress getLocalAddress() {
		return channel.getLocalAddress();
	}

	private long createTime = System.currentTimeMillis();
	
	@Override
	public long getCreateTime() {
		return createTime;
	}
}