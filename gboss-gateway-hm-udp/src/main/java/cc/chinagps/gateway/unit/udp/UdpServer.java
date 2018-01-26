package cc.chinagps.gateway.unit.udp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ConnectionlessBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.DatagramChannelFactory;
import org.jboss.netty.channel.socket.nio.NioDatagramChannelFactory;
import org.jboss.netty.util.internal.ConcurrentHashMap;
import org.seg.lib.net.server.tcp.CommonSocketSession;
import org.seg.lib.net.server.tcp.SocketSession;

import cc.chinagps.gateway.aplan.APlanServer;
import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.buff.CommandBuff.Command.Builder;
import cc.chinagps.gateway.hbase.export.ExportHBase;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.memcache.MemcacheUpdator;
import cc.chinagps.gateway.mq.CmdReader;
import cc.chinagps.gateway.mq.export.ExportMQInf;
import cc.chinagps.gateway.seat.SeatServer;
import cc.chinagps.gateway.seat.cmd.CmdResponseUtil;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommandCache;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.common.UnitCommon;
import cc.chinagps.gateway.unit.useat.util.USeatUtil;
import cc.chinagps.gateway.util.Constants;
import cc.chinagps.gateway.util.HexUtil;
import cc.chinagps.gateway.util.SystemConfig;

public class UdpServer {

	public UdpServer(int port) {
		this.port = port;

		// trace
		max_trace_unit_count = Integer.valueOf(SystemConfig
				.getSystemProperty("max_trace_unit_count"));

		// command
		long clear_cmd_interval = Long.valueOf(SystemConfig
				.getSystemProperty("command_cache_clear_thread_run_inteval"));
		long timeout = Long.valueOf(SystemConfig
				.getSystemProperty("command_cache_timeout"));
		serverToUnitCommandCache = new ServerToUnitCommandCache(
				clear_cmd_interval, timeout);

		// session
		long clear_unit_interval = Long.valueOf(SystemConfig
				.getSystemProperty("clear_unit_run_inteval"));
		if(SystemConfig
				.getSystemProperty("udp_unit_session_timeout")==null){
			unitSessionTimeout = 300000;
		}else{
			unitSessionTimeout = Long.valueOf(SystemConfig
					.getSystemProperty("udp_unit_session_timeout"));
		}
		
		sessionManager = new UdpSessionManager(this, clear_unit_interval);
	}

	private UdpEventHandler udpEventHandler = new UdpEventHandler(this);

	public void startService() {
		ConnectionlessBootstrap udpBootstrap;
		DatagramChannelFactory channelFactory = new NioDatagramChannelFactory();
		udpBootstrap = new ConnectionlessBootstrap(channelFactory);
		udpBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() throws Exception {
				return Channels.pipeline(udpEventHandler);
			}
		});
		udpBootstrap.bind(new InetSocketAddress(port));

		serverStartTime = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("[" + sdf.format(new Date()) + "]udp server ready!("
				+ port + ")");
	}

	private int port;

	private SeatServer seatServer;

	private APlanServer APlanServer;

	private APlanServer APlanAlarmServer;

	public APlanServer getAPlanAlarmServer() {
		return APlanAlarmServer;
	}

	public void setAPlanAlarmServer(APlanServer aPlanAlarmServer) {
		APlanAlarmServer = aPlanAlarmServer;
	}

	private MemcacheUpdator memcacheUpdator;

	private int max_trace_unit_count;

	private Map<String, Map<CommonSocketSession, Long>> traceMap = new ConcurrentHashMap<String, Map<CommonSocketSession, Long>>();

	public Map<CommonSocketSession, Long> getTraceList(String callLetter) {
		return traceMap.get(callLetter);
	}

	public Map<String, Map<CommonSocketSession, Long>> getTraceMap() {
		return traceMap;
	}

	private void clearOldTrace(CommonSocketSession session) {
		Object obj = session.getAttribute(Constants.SESSION_KEY_TRACE_UNIT);
		if (String.class.isInstance(obj)) {
			String old_callLetter = (String) obj;
			removeTrace(old_callLetter, session);
			session.removeAttribute(Constants.SESSION_KEY_TRACE_UNIT);
		}
	}

	public boolean addTrace(String callLetter, CommonSocketSession session) {
		clearOldTrace(session);

		Map<CommonSocketSession, Long> list = traceMap.get(callLetter);
		if (list != null) {
			session.setAttribute(Constants.SESSION_KEY_TRACE_UNIT, callLetter);
			list.put(session, System.currentTimeMillis());
			return true;
		}

		if (traceMap.size() >= max_trace_unit_count) {
			return false;
		}

		Map<CommonSocketSession, Long> new_list = new ConcurrentHashMap<CommonSocketSession, Long>();
		session.setAttribute(Constants.SESSION_KEY_TRACE_UNIT, callLetter);
		new_list.put(session, System.currentTimeMillis());
		traceMap.put(callLetter, new_list);
		return true;
	}

	private void removeTrace(String callLetter, CommonSocketSession session) {
		Map<CommonSocketSession, Long> list = traceMap.get(callLetter);
		if (list != null) {
			list.remove(session);

			if (list.size() == 0) {
				traceMap.remove(callLetter);
			}
		}
	}

	public void removeTrace(CommonSocketSession session) {
		Object obj = session.getAttribute(Constants.SESSION_KEY_TRACE_UNIT);
		if (String.class.isInstance(obj)) {
			String callLetter = (String) obj;
			removeTrace(callLetter, session);
		}
	}

	public MemcacheUpdator getMemcacheUpdator() {
		return memcacheUpdator;
	}

	public void setMemcacheUpdator(MemcacheUpdator memcacheUpdator) {
		this.memcacheUpdator = memcacheUpdator;
	}

	public APlanServer getAPlanServer() {
		return APlanServer;
	}

	public void setAPlanServer(APlanServer aPlanServer) {
		this.APlanServer = aPlanServer;
	}

	private ExportMQInf exportMQ;

	private ExportHBase exportHBase;

	public ExportHBase getExportHBase() {
		return exportHBase;
	}

	public void setExportHBase(ExportHBase exportHBase) {
		this.exportHBase = exportHBase;
	}

	private CmdReader cmdReader;

	public CmdReader getCmdReader() {
		return cmdReader;
	}

	public void setCmdReader(CmdReader cmdReader) {
		this.cmdReader = cmdReader;
	}

	private ServerToUnitCommandCache serverToUnitCommandCache;

	public ServerToUnitCommandCache getServerToUnitCommandCache() {
		return serverToUnitCommandCache;
	}

	public ExportMQInf getExportMQ() {
		return exportMQ;
	}

	public void setExportMQ(ExportMQInf exportMQ) {
		this.exportMQ = exportMQ;
	}

	public SeatServer getSeatServer() {
		return seatServer;
	}

	public void setSeatServer(SeatServer seatServer) {
		this.seatServer = seatServer;
	}

	private long unitSessionTimeout;

	public long getUnitSessionTimeout() {
		return unitSessionTimeout;
	}

	public void setUnitSessionTimeout(int unitSessionTimeout) {
		this.unitSessionTimeout = unitSessionTimeout;
	}

	private long serverStartTime;

	public long getServerStartTime() {
		return serverStartTime;
	}

	private UdpSessionManager sessionManager;

	public UdpSessionManager getSessionManager() {
		return sessionManager;
	}

	private Logger logger_exception = Logger
			.getLogger(LogManager.LOGGER_NAME_EXCEPTION);
	private Logger logger_other = Logger
			.getLogger(LogManager.LOGGER_NAME_OTHERS);
	private Logger logger_cmd = Logger.getLogger(LogManager.LOGGER_NAME_CMD);
	private Logger logger_debug = Logger
			.getLogger(LogManager.LOGGER_NAME_DEBUG);

	public void exceptionCaught(Throwable e, String from) {
		if (e.getMessage() != null) {
			if (e.getMessage().startsWith("远程主机强迫关闭了一个现有的连接")
					|| e.getMessage().startsWith("Connection reset by peer")) {
				return;
			}
		}

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(bos);
		ps.print(from);
		e.printStackTrace(ps);
		logger_exception.info(bos.toString());
	}

	public void exceptionCaught(Throwable e) {
		exceptionCaught(e, "[U]");
	}

	public void exceptionCaught(Throwable e, byte[] data,
			UnitSocketSession session) {
		String hexStr = HexUtil.byteToHexStr(data);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		e.printStackTrace(new PrintStream(bos));

		StringBuilder info = new StringBuilder();
		info.append("[U]呼号:")
				.append((session != null && session.getUnitInfo() != null) ? session
						.getUnitInfo().getCallLetter() : "").append(",\n");
		info.append("远程地址:")
				.append(session != null ? session.getRemoteAddress() : "")
				.append(",\n");
		info.append("源码:").append(hexStr).append(",\n");
		info.append("异常信息:").append(bos.toString());

		logger_exception.info(info.toString());
		// 发送trace数据(udp去掉)
		UnitCommon.sendExceptionTraceFromUdpServer(data, this, session);
	}

	// 发送指令
	public void sendCommand(CommandBuff.Command cmd, SocketSession userSession) {

		List<String> list = cmd.getCallLetterList();
		for (int i = 0; i < list.size(); i++) {
			String callLetter = list.get(i);
			UnitSocketSession session = searchUnitByCallLetter(callLetter);
			if (session != null) {
				// 找到车辆
				logger_other.info("find unit callLetter:" + callLetter);
				if (session.isUpdating()) {
					// 正在升级
					CmdResponseUtil
							.simpleCommandResponse(userSession, this,
									callLetter, cmd,
									Constants.RESULT_FAIL_OTHERS, null);
					return;
				}

				StringBuilder sb = new StringBuilder();
				sb.append("remoteAddress:").append(
						userSession == null ? "MQ" : userSession
								.getRemoteAddress());
				sb.append(", cmd:").append(cmd.toString());
				logger_cmd.info(sb.toString());
				logger_other.info(sb.toString());
				session.sendCommandToUnit(cmd, userSession);

				if (userSession == null) {
					// mq增加发送回应
					cc.chinagps.gateway.buff.CommandBuff.SendCommandResult.Builder builder = CommandBuff.SendCommandResult
							.newBuilder();
					builder.setSn(cmd.getSn());
					builder.setCallLetter(callLetter);
					builder.setCmdId(cmd.getCmdId());
					builder.setResult(Constants.RESULT_SUCCESS);

					getExportMQ().addSendCommandResult(builder.build());
				}
			} else {
				// 未找到车辆
				logger_other.info("未找到车辆:" + callLetter);
				logger_other.info("sorry,not find unit callLetter:"
						+ callLetter);
				CmdResponseUtil.simpleCommandResponse(userSession, this,
						callLetter, cmd, Constants.RESULT_UNIT_OFFLINE, null);
			}
		}
	}
	
	// 发送指令到指定callLetter
	public void sendCommandByCallLetter(String callLetter,
			CommandBuff.Command cmd, UnitSocketSession unitSocketSession,
			SocketSession userSession) {
		if (unitSocketSession.isUpdating()) {
			// 正在升级
			CmdResponseUtil.simpleCommandResponse(userSession, this,
					callLetter, cmd, Constants.RESULT_FAIL_OTHERS, null);
			return;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("remoteAddress:").append(
				userSession == null ? "MQ" : userSession.getRemoteAddress());
		sb.append(", cmd:").append(cmd.toString());
		logger_cmd.info(sb.toString());
		unitSocketSession.sendCommandToUnit(cmd, userSession);

		if (userSession == null) {
			// mq增加发送回应
			cc.chinagps.gateway.buff.CommandBuff.SendCommandResult.Builder builder = CommandBuff.SendCommandResult
					.newBuilder();
			builder.setSn(cmd.getSn());
			builder.setCallLetter(callLetter);
			builder.setCmdId(cmd.getCmdId());
			builder.setResult(Constants.RESULT_SUCCESS);
			logger_debug.debug("[udpserver] SendCommandResult to MQ callLetter:"+callLetter+";cmdId:"+cmd.getCmdId()+";sn:"+cmd.getSn());
			getExportMQ().addSendCommandResult(builder.build());
		}
	}

	// 发送指令(useat)
	public void sendCommandByUSeat(CommandBuff.Command cmd,
			UnitSocketSession ueatSession) throws Exception {
		int cmdId = cmd.getCmdId();
		if (cmdId == USeatUtil.CMD_ID_ALL_CMD) {
			int rCmdId = Integer.valueOf(cmd.getCallLetter(0));
			sendUSeatAllCmd(rCmdId, cmd, ueatSession);
		} else if (cmdId == USeatUtil.CMD_ID_ALL_DATA) {
			String protoType = cmd.getCallLetter(0);
			if ("all".equals(protoType)) {
				protoType = null;
			}

			byte[] data = HexUtil.hexToBytes(cmd.getParams(0));
			sendUSeatAllData(protoType, data);
		} else {
			List<String> list = cmd.getCallLetterList();
			for (int i = 0; i < list.size(); i++) {
				String callLetter = list.get(i);
				UnitSocketSession session = searchUnitByCallLetter(callLetter);
				if (session != null) {
					// 找到车辆
					session.sendCommandToUnit(cmd, ueatSession);
				} else {
					// 未找到车辆
					CmdResponseUtil.simpleCommandResponse(ueatSession, this,
							callLetter, cmd, Constants.RESULT_UNIT_OFFLINE,
							null);
				}
			}
		}
	}

	private void sendUSeatAllCmd(int cmdId, CommandBuff.Command cmd,
			UnitSocketSession ueatSession) throws IOException {
		Iterator<UnitSocketSession> it = getSessionManager()
				.getSocketSessionMap().values().iterator();
		while (it.hasNext()) {
			UnitSocketSession unitSocketSession = it.next();
			if (unitSocketSession.getUnitInfo() != null) {
				String call = unitSocketSession.getUnitInfo().getCallLetter();

				Command copyCmd = copyCmd(call, cmdId, cmd);
				unitSocketSession.sendCommandToUnit(copyCmd, ueatSession);
			}
		}
	}

	private void sendUSeatAllData(String protoType, byte[] data) {
		Iterator<UnitSocketSession> it = getSessionManager()
				.getSocketSessionMap().values().iterator();
		while (it.hasNext()) {
			UnitSocketSession unitSocketSession = it.next();
			if (unitSocketSession.getUnitInfo() != null
					&& (protoType == null || protoType.equals(unitSocketSession
							.getProtocolType()))) {
				unitSocketSession.sendData(data);
			}
		}
	}

	private CommandBuff.Command copyCmd(String call, int cmdId,
			CommandBuff.Command cmd) {
		Builder builder = CommandBuff.Command.newBuilder();
		builder.setSn(cmd.getSn());
		builder.addCallLetter(call);
		builder.setCmdId(cmdId);

		for (int i = 0; i < cmd.getParamsCount(); i++) {
			builder.addParams(cmd.getParams(i));
		}

		return builder.build();
	}

	public UnitSocketSession searchUnitByCallLetter(String callLetter) {

		return sessionManager.getSessionByCallLetter(callLetter);
	}
}
