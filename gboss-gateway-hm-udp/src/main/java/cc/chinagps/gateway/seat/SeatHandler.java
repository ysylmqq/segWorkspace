package cc.chinagps.gateway.seat;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.seg.lib.buff.LoginBuff.Login;
import org.seg.lib.net.Constant;
import org.seg.lib.net.data.Head;
import org.seg.lib.net.data.SegPackage;
import org.seg.lib.net.server.tcp.SocketSession;
import org.seg.lib.net.server.tcp.TCPServer;
import org.seg.lib.net.server.tcp.handler.BaseTCPServerHandler;
import org.seg.lib.util.SegPackageUtil;

import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.buff.InnerDataBuff;
import cc.chinagps.gateway.buff.InnerDataBuff.MapEntry;
import cc.chinagps.gateway.buff.InnerDataBuff.Units.Builder;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.util.Constants;

public class SeatHandler extends BaseTCPServerHandler{
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	
	private UnitServer unitServer;
	
	public SeatHandler(UnitServer unitServer){
		this.unitServer = unitServer;
	}
	
	@Override
	protected int checkLogin(Login login) throws Exception {
		return Constant.RESULT_SUCCESS;
	}
	
	@Override
	protected void handleOthers(SegPackage pkg, TCPServer server,
			SocketSession session) throws Exception {
		Head head = pkg.getHead();
		byte[] realBody = pkg.getRealBody();
		if(head.getMessageId() == Constants.INNER_CMD_ID_CMD){
			CommandBuff.Command cmd = CommandBuff.Command.parseFrom(realBody);
			unitServer.sendCommand(cmd, session);
		}else if(head.getMessageId() == Constants.INNER_CMD_ID_LOAD_VEHICLE){
			byte flag = realBody[0];
			session.setAttribute(Constants.SESSION_KEY_RECEIVE_UNIT_LOGIN_CHANGE, flag == 1);	
			
			Map<Channel, UnitSocketSession> map = unitServer.getSessionManager().getSocketSessionMap();
			Builder builder = InnerDataBuff.Units.newBuilder();
			Iterator<UnitSocketSession> it = map.values().iterator();
			while(it.hasNext()){
				UnitSocketSession unitSocketSession = it.next();
				if(unitSocketSession.getUnitInfo() != null){
					builder.addCallLetters(unitSocketSession.getUnitInfo().getCallLetter());
				}
			}
			
			session.sendPackage(Constants.INNER_CMD_ID_LOAD_VEHICLE_ACK, SegPackageUtil.getSerialNumber(), builder.build().toByteArray());
		}else if(head.getMessageId() == Constants.INNER_CMD_ID_SHOW_STATUS){
			cc.chinagps.gateway.buff.InnerDataBuff.Status.Builder builder = InnerDataBuff.Status.newBuilder();
			if(!unitServer.getExportMQ().isEnabled()){
				builder.addInfos(MapEntry.newBuilder().setKey("enabled").setValue("false"));
				session.sendPackage(Constants.INNER_CMD_ID_SHOW_STATUS_ACK, SegPackageUtil.getSerialNumber(), builder.build().toByteArray());
				return;
			}
			
			int cacheSize = unitServer.getExportMQ().getDataCount();
			int savePosition = unitServer.getExportMQ().getPosition();
			int readPosition = unitServer.getCmdReader().getPosition();

			builder.addInfos(MapEntry.newBuilder().setKey("cacheSize").setValue(String.valueOf(cacheSize)));
			builder.addInfos(MapEntry.newBuilder().setKey("savePosition").setValue(String.valueOf(savePosition)));
			builder.addInfos(MapEntry.newBuilder().setKey("readPosition").setValue(String.valueOf(readPosition)));
			
			session.sendPackage(Constants.INNER_CMD_ID_SHOW_STATUS_ACK, SegPackageUtil.getSerialNumber(), builder.build().toByteArray());
		}else if(head.getMessageId() == Constants.INNER_CMD_ID_ADD_TRACE){
			String callLetter = new String(realBody);
			boolean success = unitServer.addTrace(callLetter, session);
			byte[] ack = new byte[1];
			ack[0] = (byte) (success? 1: 0);
			
			session.sendPackage(Constants.INNER_CMD_ID_ADD_TRACE_ACK, SegPackageUtil.getSerialNumber(), ack);
		}else if(head.getMessageId() == Constants.INNER_CMD_ID_REMOVE_TRACE){
			unitServer.removeTrace(session);
			session.sendPackage(Constants.INNER_CMD_ID_REMOVE_TRACE_ACK, SegPackageUtil.getSerialNumber(), Constants.ZERO_BYTES_DATA);
		}else{
			if(logger_debug.isDebugEnabled()){
				logger_debug.debug("receive seat message:" + head.getMessageId());
			}
		}
	}
	
	@Override
	public void exceptionCaught(Throwable e) {
		unitServer.exceptionCaught(e, "[I]");
	}
	
	@Override
	public void clientDisconnected(TCPServer server, SocketSession session) {
		unitServer.removeTrace(session);
	}
}