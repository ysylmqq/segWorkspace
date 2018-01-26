package cc.chinagps.gateway.unit.udp;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.seg.lib.util.SegPackageUtil;

import cc.chinagps.gateway.buff.InnerDataBuff;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.memcache.MemcacheManager;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beans.Loginout;
import cc.chinagps.gateway.unit.beans.UnitInfo;
import cc.chinagps.gateway.unit.common.UnitCommon;
import cc.chinagps.gateway.unit.common.UploadUtil;
import cc.chinagps.gateway.unit.oem.pkg.OEMPackage;
import cc.chinagps.gateway.unit.oem.upload.OEMUploadCmdHandlers;
import cc.chinagps.gateway.util.Constants;
import cc.chinagps.gateway.util.ConvertUtil;
import cc.chinagps.gateway.util.HexUtil;

public class UdpEventHandler extends SimpleChannelUpstreamHandler {
	private Logger logger_others = Logger
			.getLogger(LogManager.LOGGER_NAME_OTHERS);
	private Logger logger_debug = Logger
			.getLogger(LogManager.LOGGER_NAME_DEBUG);
	private UdpServer server;

	public UdpEventHandler(UdpServer server) {
		this.server = server;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext arg0, ExceptionEvent arg1)
			throws Exception {
		super.exceptionCaught(arg0, arg1);

	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		Channel channel = e.getChannel();
		// setAttachment
		LinkedList<byte[]> atm = new LinkedList<byte[]>();
		channel.setAttachment(atm);

		ChannelBuffer buff0 = (ChannelBuffer) e.getMessage();
		int length = buff0.readableBytes();
		ByteBuffer buff = buff0.toByteBuffer();
		byte[] data = buff.array();
		
		UnitSocketSession session = null;
		try {
			if (data[0] == (byte) 0x29 && data[1] == (byte) 0x29) {
				OEMPackage pkg = OEMPackage.parse(data);
				
				if (pkg != null) {
					String vip = HexUtil.byteToHexStr(pkg.getVip());
					
					String imei = ConvertUtil.getIMEIFromVip(vip);
					Object objCall = MemcacheManager.getMemcachedClient().get(MemcacheManager.IMEI_CALLLETTER_KEY_HEAD + imei);
					
					if(objCall == null){
						//udp不用给车台回应失败，而且也无法进行
						//ackLoginFail(pkg, session, (short) 0xFFFF);
						if(logger_debug.isDebugEnabled()){
							StringBuilder logInfo = new StringBuilder();
							logInfo.append("unit login failed(oem), imei:").append(imei);
							logInfo.append(", source code:").append(HexUtil.byteToHexStr(pkg.getSource()));
							logger_debug.debug(logInfo.toString());
						}
						return;
					}
					String callLetter = objCall.toString();	
					session = server.getSessionManager().getSessionByCallLetter(callLetter);
					if(session == null){
						session = new UdpUnitSocketSession(server,
								channel);
						session.setMaxKeepAliveTime(server.getUnitSessionTimeout());
						session.setSocketType("udp");
					}
					session.setUdpSocketAddress(e.getRemoteAddress());
					session.setLastActiveTime(System.currentTimeMillis());
					if(session.getUnitInfo()==null){
						UnitInfo unitInfo = new UnitInfo();
						unitInfo.setCallLetter(callLetter);
						unitInfo.setIMEI(imei);
						// addSession
						session.setUnitInfo(unitInfo);
						session.setPackageEncoder(UnitCommon.oemDownloadCmdEncoder);
						session.setProtocolType("oem");
						server.getSessionManager().addCallSession(callLetter,
								session);
						
						//登录退录信息记录
						Loginout loginout = new Loginout();
						loginout.setIsLogin(Loginout.LOGIN);
						//loginout.setLoginTime(isNew? session.getCreateTime(): System.currentTimeMillis());
						loginout.setLoginTime(session.getLoginTime());
						loginout.setCallLetter(callLetter);
						UploadUtil.handleLoginout(server, session, loginout);
						
						//通知内部客户端
						byte[] bodyData = InnerDataBuff.Unit.newBuilder().setCallLetter(callLetter).build().toByteArray();
						server.getSeatServer().getTCPServer().broadcastPackage(false, false, false,
								Constants.INNER_CMD_ID_VEHICLE_ONLINE_NOTIFY, SegPackageUtil.getSerialNumber(),
								bodyData, UnitCommon.unitLoginChangeFilter);
						
						//更新memcache
						//升级服务器不更新routing
						if(!Constants.IS_UPDATE_SERVER){
							long now = System.currentTimeMillis();
							Date expDate = new Date(now + MemcacheManager.ROUTING_EXPIRED);
							boolean success = MemcacheManager.getMemcachedClient().set(MemcacheManager.ROUTING_KEY_HEAD + callLetter, Constants.SYSTEM_ID, expDate);
							if(success){
								session.getUnitInfo().setLastUpdateRouteTime(now);
							}
						}
						
						if(logger_debug.isDebugEnabled()){
							StringBuilder logInfo = new StringBuilder();
							logInfo.append("[oem]unit login in,callLetter:").append(callLetter);
							logInfo.append(", source code:").append(HexUtil.byteToHexStr(pkg.getSource()));
							logger_debug.debug(logInfo.toString());
						}
					}
					OEMUploadCmdHandlers handlers = new OEMUploadCmdHandlers();
					handlers.handleUpload(pkg, server, session);
				}
			} else {
				// 未知协议类型
				String hexStr = HexUtil.byteToHexStr(data, length);
				logger_others.info("未知车台类型, 地址:" + e.getRemoteAddress()
						+ ", 源码:" + hexStr);
				server.getSessionManager().removeSession(channel);
			}

		} catch (Exception e2) {
			// TODO: handle exception
			logger_others.error(e2.getMessage());
			server.exceptionCaught(e2, buff.array(), session);
			//IOUtil.closeChannel(e.getChannel());
		}

	}

}
