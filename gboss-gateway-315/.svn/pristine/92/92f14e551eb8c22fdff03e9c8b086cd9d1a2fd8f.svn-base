package cc.chinagps.gateway.unit;

import java.io.IOException;
import java.net.SocketAddress;

import org.seg.lib.net.server.tcp.CommonSocketSession;
import org.seg.lib.net.server.tcp.SocketSession;

import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.stream.OutputPackageEncoder;
import cc.chinagps.gateway.stream.InputStreamController;
import cc.chinagps.gateway.unit.beans.UnitInfo;
import cc.chinagps.gateway.unit.update.UpdateFileInfo;

public interface UnitSocketSession extends CommonSocketSession{
	//public void close();
	
	//public void sendData(byte[] data);
	
	//public void setAttribute(String key, Object obj);
	
	//public Object removeAttribute(String key);
	
	//public Object getAttribute(String key);
	
	//public SocketAddress getRemoteAddress();
	
	//public SocketAddress getLocalAddress();
	
	//public long getCreateTime();
	
	//public boolean isLogin();
	
	//public void setLogin(boolean isLogin);
	
	public String getSocketType();
	
	public void setSocketType(String socketType);
	
	public void setUdpSocketAddress(SocketAddress udpSocketAddress);
	
	public SocketAddress getUdpSocketAddress();
	
	public long getLastActiveTime();
	
	public void setLastActiveTime(long lastActiveTime);
	
	public long getMaxKeepAliveTime();
	
	public void setMaxKeepAliveTime(long maxKeepAliveTime);
	
	public InputStreamController getPackageMaker();
	
	public void setPackageMaker(InputStreamController packageMaker);
	
	public OutputPackageEncoder getPackageEncoder();
	
	public void setPackageEncoder(OutputPackageEncoder packageEncoder);
	
	public void sendCommandToUnit(CommandBuff.Command cmd, SocketSession userSession);
	
	public void sendCommandToUnit(CommandBuff.Command cmd, UnitSocketSession userSession);
	
	public void setUnitInfo(UnitInfo unitInfo);
	
	public UnitInfo getUnitInfo();
	
	/**
	 * 获取上行包数量
	 */
	public long getUploadPackageCount();
	
	public void IncreaseUploadPackageCount();
	
	/**
	 * 获取下行包数量
	 */
	public long getDownloadPackageCount();
	
	public void IncreaseDownloadPackageCount();
	
	public void setProtocolType(String protocolType);
	
	public String getProtocolType();
	
	/**
	 * 车台升级
	 */
	public boolean isUpdating();
	
	public void startSendUpdateFile(UpdateFileInfo fileInfo);
	
	public int sendNextFileData() throws IOException;
	
	public void endSendUpdateFile();
	
	/**
	 * 清理
	 */
	public void clearResource();
	
	public long getLoginTime();
	
	public void setLoginTime(long time);
}