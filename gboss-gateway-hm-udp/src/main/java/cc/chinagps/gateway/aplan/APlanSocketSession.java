package cc.chinagps.gateway.aplan;

import java.net.SocketAddress;

import cc.chinagps.gateway.stream.InputStreamController;

public interface APlanSocketSession {
	public long getCreateTime();
	
	public void close();
	
	public boolean isLogin();
	
	public void setLogin(boolean isLogin);
	
	public long getLastActiveTime();
	
	public void setLastActiveTime(long lastActiveTime);
	
	public long getMaxKeepAliveTime();
	
	public void setMaxKeepAliveTime(long maxKeepAliveTime);
	
	public InputStreamController getPackageMaker();
	
	public void setPackageMaker(InputStreamController packageMaker);
	
	public void sendData(byte[] data);
	
	public SocketAddress getRemoteAddress();
	
	public SocketAddress getLocalAddress();
}