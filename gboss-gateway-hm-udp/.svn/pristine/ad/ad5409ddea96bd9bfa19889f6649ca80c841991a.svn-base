package cc.chinagps.gateway.aplan;

import java.nio.ByteBuffer;
import java.util.List;

import org.apache.log4j.Logger;

import cc.chinagps.gateway.aplan.pkg.APlanHead;
import cc.chinagps.gateway.aplan.pkg.APlanPackage;
import cc.chinagps.gateway.aplan.pkg.RouteTable;
import cc.chinagps.gateway.aplan.pkg.bodys.LoginAckBody;
import cc.chinagps.gateway.aplan.pkg.bodys.LoginBody;
import cc.chinagps.gateway.aplan.pkg.bodys.SubmitAckBody;
import cc.chinagps.gateway.aplan.pkg.bodys.SubmitBody;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.stream.InputPackageHandler;
import cc.chinagps.gateway.stream.InputStreamController;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.util.Constants;
import cc.chinagps.gateway.util.HexUtil;

public class APlanPackageMaker implements InputStreamController, InputPackageHandler{
	private APlanPackageDecoder decoder = new APlanPackageDecoder(this);
	
	private APlanServer server;
	
	private APlanSocketSession session;
	
	public APlanPackageMaker(APlanServer server, APlanSocketSession session){
		this.server = server;
		this.session = session;
	}
	
	@Override
	public void dealData(ByteBuffer buff, int len) throws Exception {
		decoder.handleStream(buff, len);
	}

	private Logger logger_others = Logger.getLogger(LogManager.LOGGER_NAME_OTHERS);
	private Logger logger_cmd = Logger.getLogger(LogManager.LOGGER_NAME_CMD);
	
	@Override
	public void packageReceived(byte[] pkg) {
		try{
			APlanPackage aplanPkg = APlanPackage.parseFrom(pkg);
			APlanHead head = aplanPkg.getHead();
			if(head.getCommandId() == APlanHead.CMD_ID_LOGIN){
				//登录
				LoginBody loginBody = LoginBody.parseFrom(pkg, aplanPkg.getBodyStart());
				
				//回复
				APlanHead resHead = new APlanHead();
				resHead.setCommandId(APlanHead.CMD_ID_LOGIN_ACK);
				resHead.setCommandStatus(APlanHead.CMD_STATUS_SUCCESS);
				resHead.setSequenceNo(head.getSequenceNo());
				
				LoginAckBody ackBody = new LoginAckBody();
				ackBody.setNodeId(loginBody.getNodeId());
				ackBody.setNodeType(loginBody.getNodeType());
				
				RouteTable rt = aplanPkg.getRouteTable();
				rt.getNodeList().add(server.getServerNode());
				
				byte[] encode = APlanPackage.encode(resHead, rt, ackBody.encode());
				session.sendData(encode);
			}else if(head.getCommandId() == APlanHead.CMD_ID_SUBMIT){
				//提交数据
				SubmitBody submitBody = SubmitBody.parseFrom(pkg, aplanPkg.getBodyStart());
				List<byte[]> msgs = submitBody.getMsgs();
				String callLetter = submitBody.getCallLetter();
				UnitSocketSession unitSession = server.getUnitServer().searchUnitByCallLetter(callLetter);
				
				StringBuilder sb = new StringBuilder();
				if(unitSession != null){
					for(int i = 0; i < msgs.size(); i++){
						byte[] msg = msgs.get(i);
						unitSession.sendData(msg);
						
						sb.delete(0, sb.length());
						sb.append("remoteAddress(A):").append(session.getRemoteAddress());
						sb.append(", callLetter:").append(callLetter);
						sb.append(", cmd:").append(HexUtil.byteToHexStr(msg));
						logger_cmd.info(sb.toString());
					}
				}

				APlanHead resHead = new APlanHead();
				resHead.setCommandId(APlanHead.CMD_ID_SUBMIT_ACK);
				resHead.setCommandStatus(unitSession == null? APlanHead.CMD_STATUS_FAIL: APlanHead.CMD_STATUS_SUCCESS);
				resHead.setSequenceNo(head.getSequenceNo());
				
				RouteTable rt = aplanPkg.getRouteTable();
				rt.getNodeList().add(server.getServerNode());
				
				SubmitAckBody submitAckBody = new SubmitAckBody();
				submitAckBody.setCallLetter(callLetter);
				byte[] encode = APlanPackage.encode(resHead, rt, submitAckBody.encode());
				session.sendData(encode);
			}else if(head.getCommandId() == APlanHead.CMD_ID_LINK_TEST_ACK){
				//CMD_ID_LINK_TEST_ACK
			}else if(head.getCommandId() == APlanHead.CMD_ID_DELIVER_ACK){
				//CMD_ID_DELIVER_ACK
			}else if(head.getCommandId() == APlanHead.CMD_ID_LINK_TEST){
				//回复
				APlanHead resHead = new APlanHead();
				resHead.setCommandId(APlanHead.CMD_ID_LINK_TEST_ACK);
				resHead.setCommandStatus(APlanHead.CMD_STATUS_SUCCESS);
				resHead.setSequenceNo(head.getSequenceNo());
				
				RouteTable rt = aplanPkg.getRouteTable();
				rt.getNodeList().add(server.getServerNode());
				
				byte[] encode = APlanPackage.encode(resHead, rt, Constants.ZERO_BYTES_DATA);
				session.sendData(encode);
			}else{
				logger_others.info("receive APlan other cmd, cmdId:" + head.getCommandId());
			}
		}catch (Throwable e) {
			server.exceptionCaught(e);
		}
	}

	@Override
	public byte[] getCachedData() {
		return decoder.getCachedData();
	}
}