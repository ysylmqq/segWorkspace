package cc.chinagps.gateway.unit.udp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.seg.lib.net.server.tcp.SocketSession;

import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.exceptions.WrongFormatException;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.seat.cmd.CmdResponseUtil;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.stream.InputStreamController;
import cc.chinagps.gateway.stream.OutputPackageEncoder;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beans.UnitInfo;
import cc.chinagps.gateway.unit.common.UnitCommon;
import cc.chinagps.gateway.unit.update.UpdateFileInfo;
import cc.chinagps.gateway.util.Constants;
import cc.chinagps.gateway.util.HexUtil;

public class UdpUnitSocketSession implements UnitSocketSession {
	private Logger logger_debug = Logger
			.getLogger(LogManager.LOGGER_NAME_DEBUG);

	private InputStreamController packageMaker;

	private OutputPackageEncoder packageEncoder;

	private long lastActiveTime = System.currentTimeMillis();

	private long maxKeepAliveTime = 300000L;

	private long createTime = System.currentTimeMillis();

	public UnitInfo unitInfo;

	private UdpServer udpServer;

	private Channel channel;

	private SocketAddress udpSocketAddress;

	private String socketType = "tcp";

	public SocketAddress getUdpSocketAddress() {
		return udpSocketAddress;
	}

	public void setUdpSocketAddress(SocketAddress udpSocketAddress) {
		this.udpSocketAddress = udpSocketAddress;
	}

	public String getSocketType() {
		return socketType;
	}

	public void setSocketType(String socketType) {
		this.socketType = socketType;
	}

	private Map<String, Object> attribute = new HashMap<String, Object>();

	public UdpUnitSocketSession(UdpServer udpServer, Channel channel) {
		this.udpServer = udpServer;
		this.channel = channel;
		this.socketType = "udp";
	}

	public void setUnitInfo(UnitInfo unitInfo) {
		this.unitInfo = unitInfo;
	}

	public UnitInfo getUnitInfo() {
		return unitInfo;
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
	public void sendData(byte[] data) {
		@SuppressWarnings("unchecked")
		LinkedList<byte[]> list = (LinkedList<byte[]>) channel.getAttachment();
		synchronized (list) {
			if (channel.isWritable()) {
				logger_debug.debug("updUnitSocketSession sendData data:"
						+ HexUtil.byteToHexStr(data)+" to "+udpSocketAddress.toString());
				ChannelBuffer tosend = ChannelBuffers.wrappedBuffer(data);
				channel.write(tosend, udpSocketAddress);

				// add trace
				// udp去掉
				 UnitCommon.sendDownloadTrace(data, udpServer, this);
			} else {
				list.add(data);
			}
		}
	}

	@Override
	public void sendCommandToUnit(CommandBuff.Command cmd,
			SocketSession userSession) {
		try {
			ServerToUnitCommand serverToUnitCommand;
			try {
				serverToUnitCommand = getPackageEncoder().encode(cmd, this);
				logger_debug
						.debug("UdpUnitSocketSession sendCommandToUnit serverToUnitCommand.callLetter : "
								+ serverToUnitCommand.getCallLetter());
			} catch (WrongFormatException e) {
				CmdResponseUtil.simpleCommandResponse(userSession, udpServer,
						getUnitInfo().getCallLetter(), cmd,
						Constants.RESULT_WRONG_FORMAT, e.getMessage());
				return;
			} catch (Exception e) {
				if (logger_debug.isDebugEnabled()) {
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					e.printStackTrace(new PrintStream(bos));
					logger_debug.debug(bos.toString());
				}

				CmdResponseUtil.simpleCommandResponse(userSession, udpServer,
						getUnitInfo().getCallLetter(), cmd,
						Constants.RESULT_FAIL_OTHERS, e.getMessage());
				return;
			}

			if (serverToUnitCommand.getResponseType() == OutputPackageEncoder.SUCCESS_AFTER_SEND) {
				// 发送成功即成功
				sendData(serverToUnitCommand.getData());
				logger_debug
						.debug("UdpUnitSocketSession sendCommandToUnit success.... ");
				CmdResponseUtil.simpleCommandResponse(userSession, udpServer,
						getUnitInfo().getCallLetter(), cmd,
						Constants.RESULT_SUCCESS, null);
			} else {
				// 缓存指令
				String callLetter = getUnitInfo().getCallLetter();
				serverToUnitCommand.setUserSession(userSession);
				serverToUnitCommand.setSendTime(System.currentTimeMillis());
				serverToUnitCommand.setUdpServer(udpServer);
				serverToUnitCommand.setCallLetter(callLetter);

				udpServer.getServerToUnitCommandCache().addCache(
						serverToUnitCommand.getCachedSn(), serverToUnitCommand);
				logger_debug.debug("udp send data to unit:"+HexUtil.byteToHexStr(serverToUnitCommand.getData()));
				sendData(serverToUnitCommand.getData());
			}
		} catch (Throwable e) {
			udpServer.exceptionCaught(e);
		}
	}

	/**
	 * USeat send command to unit
	 */
	@Override
	public void sendCommandToUnit(CommandBuff.Command cmd,
			UnitSocketSession userSession) {
		try {
			ServerToUnitCommand serverToUnitCommand;
			try {
				serverToUnitCommand = getPackageEncoder().encode(cmd, this);
			} catch (WrongFormatException e) {
				CmdResponseUtil.simpleCommandResponse(userSession, udpServer,
						getUnitInfo().getCallLetter(), cmd,
						Constants.RESULT_WRONG_FORMAT, e.getMessage());
				return;
			} catch (Exception e) {
				CmdResponseUtil.simpleCommandResponse(userSession, udpServer,
						getUnitInfo().getCallLetter(), cmd,
						Constants.RESULT_FAIL_OTHERS, e.getMessage());
				return;
			}

			if (serverToUnitCommand.getResponseType() == OutputPackageEncoder.SUCCESS_AFTER_SEND) {
				// 发送成功即成功
				sendData(serverToUnitCommand.getData());
				CmdResponseUtil.simpleCommandResponse(userSession, udpServer,
						getUnitInfo().getCallLetter(), cmd,
						Constants.RESULT_SUCCESS, null);
			} else {
				// 缓存指令
				String callLetter = getUnitInfo().getCallLetter();
				serverToUnitCommand.setUserSession(userSession);
				serverToUnitCommand.setSendTime(System.currentTimeMillis());
				serverToUnitCommand.setUdpServer(udpServer);
				serverToUnitCommand.setCallLetter(callLetter);

				// int cache_cmdId = cmd.getCmdId();
				// server.getServerToUnitCommandCache().addCache(CmdUtil.getCmdCacheSn(callLetter,
				// cache_cmdId), serverToUnitCommand);
				udpServer.getServerToUnitCommandCache().addCache(
						serverToUnitCommand.getCachedSn(), serverToUnitCommand);

				sendData(serverToUnitCommand.getData());
			}
		} catch (Throwable e) {
			udpServer.exceptionCaught(e);
		}
	}

	@Override
	public InputStreamController getPackageMaker() {
		return packageMaker;
	}

	@Override
	public void setPackageMaker(InputStreamController packageMaker) {
		this.packageMaker = packageMaker;
	}

	@Override
	public OutputPackageEncoder getPackageEncoder() {
		return packageEncoder;
	}

	@Override
	public void setPackageEncoder(OutputPackageEncoder packageEncoder) {
		this.packageEncoder = packageEncoder;
	}

	// private boolean isLogin;
	//
	// @Override
	// public boolean isLogin() {
	// return isLogin;
	// }
	//
	// @Override
	// public void setLogin(boolean isLogin) {
	// this.isLogin = isLogin;
	// }

	@Override
	public void close() {
		udpServer.getSessionManager().removeSession(channel);
	}

	@Override
	public long getCreateTime() {
		return createTime;
	}

	private long uploadPackageCount = 0;

	private long downloadPackageCount = 0;

	@Override
	public long getUploadPackageCount() {
		return uploadPackageCount;
	}

	@Override
	public void IncreaseUploadPackageCount() {
		uploadPackageCount++;
	}

	@Override
	public long getDownloadPackageCount() {
		return downloadPackageCount;
	}

	@Override
	public void IncreaseDownloadPackageCount() {
		downloadPackageCount++;
	}

	@Override
	public SocketAddress getRemoteAddress() {
		return channel.getRemoteAddress();
	}

	@Override
	public SocketAddress getLocalAddress() {
		return channel.getLocalAddress();
	}

	private String protocolType;

	@Override
	public void setProtocolType(String protocolType) {
		this.protocolType = protocolType;
	}

	@Override
	public String getProtocolType() {
		return protocolType;
	}

	@Override
	public void setAttribute(String key, Object obj) {
		attribute.put(key, obj);
	}

	@Override
	public Object removeAttribute(String key) {
		return attribute.remove(key);
	}

	@Override
	public Object getAttribute(String key) {
		return attribute.get(key);
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		StringBuilder sb = new StringBuilder();
		sb.append("{callLetter:").append(
				unitInfo == null ? null : unitInfo.getCallLetter());
		sb.append(", createTime:").append(sdf.format(createTime));
		sb.append(", lastActiveTime:").append(sdf.format(lastActiveTime));
		sb.append(", remoteAddress:").append(getRemoteAddress());
		sb.append(", udpSocketAddress:").append(getUdpSocketAddress());
		sb.append(", uploadPackageCount:").append(uploadPackageCount);
		sb.append(", downloadPackageCount:").append(downloadPackageCount);
		sb.append(", protocolType:").append(protocolType);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public boolean isUpdating() {
		return fileInfo != null;
	}

	private UpdateFileInfo fileInfo;

	@Override
	public void startSendUpdateFile(UpdateFileInfo fileInfo) {
		// System.out.println("startSendUpdateFile:" +
		// System.currentTimeMillis());
		this.fileInfo = fileInfo;
		// send data
		try {
			while (channel.isWritable()) {
				int len = sendNextFileData();
				if (len == -1) {
					endSendUpdateFile();
					break;
				}
			}
		} catch (Exception e) {
			endSendUpdateFile();
			udpServer.exceptionCaught(e);
		}
	}

	@Override
	public void endSendUpdateFile() {
		// System.out.println("endSendUpdateFile:" +
		// System.currentTimeMillis());
		this.fileInfo.clearResource();
		this.fileInfo = null;
	}

	@Override
	public int sendNextFileData() throws IOException {
		// System.out.println("sendNextFileData");
		InputStream is = fileInfo.getInputStream();
		byte[] temp = new byte[1024];
		int len = is.read(temp);
		if (len != -1) {
			ChannelBuffer tosend = ChannelBuffers.wrappedBuffer(temp, 0, len);
			channel.write(tosend);
		}

		return len;
	}

	@Override
	public void clearResource() {
		if (this.fileInfo != null) {
			this.fileInfo.clearResource();
			this.fileInfo = null;
		}
	}

	private long loginTime;

	@Override
	public long getLoginTime() {
		return loginTime;
	}

	@Override
	public void setLoginTime(long time) {
		this.loginTime = time;
	}
}