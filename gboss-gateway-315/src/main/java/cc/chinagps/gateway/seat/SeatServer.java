package cc.chinagps.gateway.seat;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.seg.lib.net.server.tcp.TCPServer;
import org.seg.lib.net.server.tcp.handler.TCPServerHandler;
import org.seg.lib.net.server.tcp.netty.NettyTCPServer;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.udp.UdpServer;

public class SeatServer {
	private int port;
	private UnitServer unitServer;
	
	public UnitServer getUnitServer() {
		return unitServer;
	}

	public void setUnitServer(UnitServer unitServer) {
		this.unitServer = unitServer;
	}

	public SeatServer(int port){
		this.port = port;
	}
	
	private TCPServer server;
	
	public void startService() {
		server = new NettyTCPServer(port);
		TCPServerHandler handler = new SeatHandler(unitServer);
		server.setHandler(handler);
		server.startService();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("[" + sdf.format(new Date()) + "]seat server ready!(" + port + ")");
	}
	
	public TCPServer getTCPServer(){
		return server;
	}

	private UdpServer udpServer;
	public void setUdpServer(UdpServer udpServer) {
		// TODO Auto-generated method stub
		this.udpServer = udpServer;
	}

	public UdpServer getUdpServer() {
		return udpServer;
	}
	
}