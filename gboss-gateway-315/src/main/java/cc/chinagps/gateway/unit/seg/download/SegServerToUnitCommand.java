package cc.chinagps.gateway.unit.seg.download;

import org.seg.lib.net.server.tcp.SocketSession;

import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.udp.UdpServer;

public class SegServerToUnitCommand implements ServerToUnitCommand{
	//private String serverToUnitSn;
	
	private String cachedSn;
	
	@Override
	public String getCachedSn() {
		return cachedSn;
	}

	public void setCachedSn(String cachedSn) {
		this.cachedSn = cachedSn;
	}

	private byte[] data;
	
	private int responseType;
	
	private SocketSession userSession;
	
	private UnitSocketSession userSession2;
	
	private String callLetter;
	
	public UnitSocketSession getUSeatSession(){
		return userSession2;
	}
	
	//public void setServerToUnitSn(String serverToUnitSn) {
	//	this.serverToUnitSn = serverToUnitSn;
	//}

	public void setData(byte[] data) {
		this.data = data;
	}

	public void setResponseType(int responseType) {
		this.responseType = responseType;
	}

	public void setUserCommand(Command userCommand) {
		this.userCommand = userCommand;
	}

	private Command userCommand;
	
	//@Override
	//public String getServerToUnitSn() {
	//	return serverToUnitSn;
	//}

	@Override
	public byte[] getData() {
		return data;
	}

	@Override
	public int getResponseType() {
		return responseType;
	}

	@Override
	public Command getUserCommand() {
		return userCommand;
	}

	@Override
	public void setUserSession(SocketSession userSession) {
		this.userSession = userSession;
	}
	
	@Override
	public void setUserSession(UnitSocketSession userSession){
		this.userSession2 = userSession;
	}

	@Override
	public SocketSession getUserSession() {
		return userSession;
	}

	private long sendTime = System.currentTimeMillis();
	
	@Override
	public long getSendTime() {
		return sendTime;
	}

	@Override
	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}

	private UnitServer unitServer;
	
	@Override
	public void setUnitServer(UnitServer unitServer) {
		this.unitServer = unitServer;
	}

	@Override
	public UnitServer getUnitServer() {
		return unitServer;
	}

	@Override
	public void setCallLetter(String callLetter) {
		this.callLetter = callLetter;
	}

	@Override
	public String getCallLetter() {
		return callLetter;
	}
	
	private UdpServer udpServer;

	public UdpServer getUdpServer() {
		return udpServer;
	}

	public void setUdpServer(UdpServer udpServer) {
		this.udpServer = udpServer;
	}
	
}