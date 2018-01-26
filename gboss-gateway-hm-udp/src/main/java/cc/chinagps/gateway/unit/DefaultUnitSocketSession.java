package cc.chinagps.gateway.unit;

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
import cc.chinagps.gateway.unit.beans.UnitInfo;
import cc.chinagps.gateway.unit.common.UnitCommon;
import cc.chinagps.gateway.unit.leopaard.upload.BigDataDisposer;
import cc.chinagps.gateway.unit.update.UpdateFileInfo;
import cc.chinagps.gateway.util.Constants;
import cc.chinagps.gateway.util.HexUtil;

public class DefaultUnitSocketSession implements UnitSocketSession {
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);

	private InputStreamController packageMaker;

	private OutputPackageEncoder packageEncoder;
	
	private BigDataDisposer bigDataDisposer;

	private long lastActiveTime = System.currentTimeMillis();

	private long maxKeepAliveTime = 300000L;

	private long createTime = System.currentTimeMillis();

	public UnitInfo unitInfo;

	private UnitServer server;

	private Channel channel;

	private Map<String, Object> attribute = new HashMap<String, Object>();

	public DefaultUnitSocketSession(UnitServer server, Channel channel) {
		this.server = server;
		this.channel = channel;
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
				ChannelBuffer tosend = ChannelBuffers.wrappedBuffer(data);
				channel.write(tosend);

				logger_debug.debug("DefaultUnitSocketSession send data:" + HexUtil.byteToHexStr(data));
				// add trace
				UnitCommon.sendDownloadTrace(data, server, this);
			} else {
				list.add(data);
			}
		}
	}

	@Override
	public void sendCommandToUnit(CommandBuff.Command cmd, SocketSession userSession) {
		try {
			ServerToUnitCommand serverToUnitCommand;
			try {
				serverToUnitCommand = getPackageEncoder().encode(cmd, this);
			} catch (WrongFormatException e) {
				CmdResponseUtil.simpleCommandResponse(userSession, server, getUnitInfo().getCallLetter(), cmd,
						Constants.RESULT_WRONG_FORMAT, e.getMessage());
				return;
			} catch (Exception e) {
				if (logger_debug.isDebugEnabled()) {
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					e.printStackTrace(new PrintStream(bos));
					logger_debug.debug(bos.toString());
				}

				CmdResponseUtil.simpleCommandResponse(userSession, server, getUnitInfo().getCallLetter(), cmd,
						Constants.RESULT_FAIL_OTHERS, e.getMessage());
				return;
			}

			if (serverToUnitCommand.getResponseType() == OutputPackageEncoder.SUCCESS_AFTER_SEND) {
				// 发送成功即成功
				sendData(serverToUnitCommand.getData());
				CmdResponseUtil.simpleCommandResponse(userSession, server, getUnitInfo().getCallLetter(), cmd,
						Constants.RESULT_SUCCESS, null);
			} else {
				// 缓存指令
				String callLetter = getUnitInfo().getCallLetter();
				serverToUnitCommand.setUserSession(userSession);
				serverToUnitCommand.setSendTime(System.currentTimeMillis());
				serverToUnitCommand.setUnitServer(server);
				serverToUnitCommand.setCallLetter(callLetter);

				// int cache_cmdId = cmd.getCmdId();
				// server.getServerToUnitCommandCache().addCache(CmdUtil.getCmdCacheSn(callLetter,
				// cache_cmdId), serverToUnitCommand);
				server.getServerToUnitCommandCache().addCache(serverToUnitCommand.getCachedSn(), serverToUnitCommand);

				sendData(serverToUnitCommand.getData());
			}
			logger_debug.debug("DefaultUnitSocketSession send data:"
					+ HexUtil.byteToHexStr(serverToUnitCommand.getData()) + ";cmd:" + cmd);
		} catch (Throwable e) {
			server.exceptionCaught(e);
		}
	}

	/**
	 * USeat send command to unit
	 */
	@Override
	public void sendCommandToUnit(CommandBuff.Command cmd, UnitSocketSession userSession) {
		try {
			ServerToUnitCommand serverToUnitCommand;
			try {
				serverToUnitCommand = getPackageEncoder().encode(cmd, this);
			} catch (WrongFormatException e) {
				CmdResponseUtil.simpleCommandResponse(userSession, server, getUnitInfo().getCallLetter(), cmd,
						Constants.RESULT_WRONG_FORMAT, e.getMessage());
				return;
			} catch (Exception e) {
				CmdResponseUtil.simpleCommandResponse(userSession, server, getUnitInfo().getCallLetter(), cmd,
						Constants.RESULT_FAIL_OTHERS, e.getMessage());
				return;
			}

			if (serverToUnitCommand.getResponseType() == OutputPackageEncoder.SUCCESS_AFTER_SEND) {
				// 发送成功即成功
				sendData(serverToUnitCommand.getData());
				CmdResponseUtil.simpleCommandResponse(userSession, server, getUnitInfo().getCallLetter(), cmd,
						Constants.RESULT_SUCCESS, null);
			} else {
				// 缓存指令
				String callLetter = getUnitInfo().getCallLetter();
				serverToUnitCommand.setUserSession(userSession);
				serverToUnitCommand.setSendTime(System.currentTimeMillis());
				serverToUnitCommand.setUnitServer(server);
				serverToUnitCommand.setCallLetter(callLetter);

				// int cache_cmdId = cmd.getCmdId();
				// server.getServerToUnitCommandCache().addCache(CmdUtil.getCmdCacheSn(callLetter,
				// cache_cmdId), serverToUnitCommand);
				server.getServerToUnitCommandCache().addCache(serverToUnitCommand.getCachedSn(), serverToUnitCommand);

				sendData(serverToUnitCommand.getData());
			}
		} catch (Throwable e) {
			server.exceptionCaught(e);
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
		server.getSessionManager().removeSession(channel);
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
		sb.append("{callLetter:").append(unitInfo == null ? null : unitInfo.getCallLetter());
		sb.append(", createTime:").append(sdf.format(createTime));
		sb.append(", lastActiveTime:").append(sdf.format(lastActiveTime));
		sb.append(", remoteAddress:").append(getRemoteAddress());
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
			server.exceptionCaught(e);
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

	@Override
	public String getSocketType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSocketType(String socketType) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUdpSocketAddress(SocketAddress udpSocketAddress) {
		// TODO Auto-generated method stub

	}

	@Override
	public SocketAddress getUdpSocketAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBigDataDisposer(BigDataDisposer bigDataDisposer) {
		// TODO Auto-generated method stub
		this.bigDataDisposer = bigDataDisposer;
	}

	@Override
	public BigDataDisposer getBigDataDisposer() {
		// TODO Auto-generated method stub
		return bigDataDisposer;
	}
}