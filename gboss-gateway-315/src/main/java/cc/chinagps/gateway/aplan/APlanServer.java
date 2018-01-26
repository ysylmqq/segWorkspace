package cc.chinagps.gateway.aplan;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import cc.chinagps.gateway.aplan.pkg.RouteTable.Node;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.udp.UdpServer;
import cc.chinagps.gateway.util.Constants;
import cc.chinagps.gateway.util.HexUtil;
import cc.chinagps.gateway.util.SystemConfig;

public class APlanServer {
	private Logger logger_exception = Logger.getLogger(LogManager.LOGGER_NAME_EXCEPTION);
	
	private int port;
	
	private long session_timeout;
	
	private UnitServer unitServer;
	
	private int sendQueueMaxSize;
	
	public int getSendQueueMaxSize() {
		return sendQueueMaxSize;
	}

	public UnitServer getUnitServer() {
		return unitServer;
	}

	public void setUnitServer(UnitServer unitServer) {
		this.unitServer = unitServer;
	}

	public long getAplanSessionTimeout() {
		return session_timeout;
	}
	
	private int serverId = 0;
	
	public int getServerId(){
		return serverId;
	}
	
	private Node serverNode;
	
	public Node getServerNode(){
		return serverNode;
	}
	
	private String serverName;

	public APlanServer(int port, String serverName){
		this.port = port;
		this.serverName = serverName;
		
		session_timeout = Long.valueOf(SystemConfig.getAPlanProperty("session_timeout"));
		long check_session_inteval = Long.valueOf(SystemConfig.getAPlanProperty("check_session_inteval"));
		long send_hear_beat_interval = Long.valueOf(SystemConfig.getAPlanProperty("send_hear_beat_interval"));
		sendQueueMaxSize = Integer.valueOf(SystemConfig.getAPlanProperty("send_queue_max_size"));
		
		//serverId
		serverId = Constants.SYSTEM_ID_INT;
		serverNode = new Node();
		serverNode.setNodeType(Node.NODE_INTERFACE);
		serverNode.setNodeId(serverId);
		serverNode.setNodeIP("127.0.0.1");
				
		sessionManager = new APlanSessionManager(this, check_session_inteval, send_hear_beat_interval);
	}
	
	private APlanSessionManager sessionManager;

	public APlanSessionManager getSessionManager() {
		return sessionManager;
	}
	
	public void exceptionCaught(Throwable e) {
		unitServer.exceptionCaught(e, "[A]");
	}
	
	public void exceptionCaught(Throwable e, byte[] data, APlanSocketSession session) {
		String hexStr = HexUtil.byteToHexStr(data);
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		e.printStackTrace(new PrintStream(bos));
		
		StringBuilder info = new StringBuilder();				
		info.append("[A]远程地址:").append(session != null? session.getRemoteAddress(): "").append(",\n");
		info.append("源码:").append(hexStr).append(",\n");
		info.append("异常信息:").append(bos.toString());
		
		logger_exception.info(info.toString());
	}
	
	private APlanNettyHandler nettyHandler = new APlanNettyHandler(this);
	
	public void startService() {
		ServerBootstrap bootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(Executors
						.newCachedThreadPool(), Executors.newCachedThreadPool()));		
		bootstrap.setPipelineFactory(new ChannelPipelineFactory(){
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				return Channels.pipeline(nettyHandler);
			}
		});
		
		bootstrap.bind(new InetSocketAddress(port));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("[" + sdf.format(new Date()) + "]" + serverName + " ready!(" + port +")");
	}
	
	public void broadcastData(byte[] data){
		sessionManager.broadcastData(data);
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