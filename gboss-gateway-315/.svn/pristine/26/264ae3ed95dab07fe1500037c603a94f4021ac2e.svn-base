package cc.chinagps.gateway.seat.cmd;

import org.seg.lib.net.server.tcp.SocketSession;

import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.udp.UdpServer;

public interface ServerToUnitCommand {
	public String getCachedSn();
	
	//public String getServerToUnitSn();
	
	public byte[] getData();
	
	public int getResponseType();
	
	public CommandBuff.Command getUserCommand();
	
	public void setUserSession(SocketSession userSession);
	
	public void setUserSession(UnitSocketSession userSession);
	
	public SocketSession getUserSession();
	
	public UnitSocketSession getUSeatSession();
	
	public void setSendTime(long sendTime);
	
	public long getSendTime();
	
	public void setUnitServer(UnitServer unitServer);
	
	public UnitServer getUnitServer();
	
	public void setCallLetter(String callLetter);
	
	public String getCallLetter();
	
	public UdpServer getUdpServer() ;

	public void setUdpServer(UdpServer udpServer);
}