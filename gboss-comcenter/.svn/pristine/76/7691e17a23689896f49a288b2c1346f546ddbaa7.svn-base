/*
********************************************************************************************
Discription:  客户端发送链路检测报文，回复链路检测应答
			  
			  
Written By:   ZXZ
Date:         2014-04-28
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.outinterface;

import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.seginterfaceDataBuff.ActiveTest;
import cc.chinagps.gboss.comcenter.buff.seginterfaceDataBuff.ActiveTest_ACK;
import cc.chinagps.gboss.comcenter.websocket.ClientBaseHandler;

public class ActiveLinkHandler extends ClientBaseHandler {
	private String srcnodeid;
	private String dstnodeid;
	
	public ActiveLinkHandler(ComCenterMessage.ComCenterBaseMessage requestmsg, OutInterfaceClientInfo info) {
		super(requestmsg, info);
	}

	@Override
	public int decode() {
		try {
			ActiveTest activetest = ActiveTest.parseFrom(msgcontent);
			srcnodeid = activetest.getSrcnode();
			dstnodeid = activetest.getDstnode();
        } catch(Exception e) {
			e.printStackTrace();
			retcode = ResultCode.Decode_Error;
			retmsg = "解码失败";
		}
		return retcode;
	}

	@Override
	public void run() {
	}
	
	/*
	 * 直接返回链路检测报文
	 */
	@Override
	public byte[] response() {
		ActiveTest_ACK.Builder activetestack = ActiveTest_ACK.newBuilder();
		activetestack.setDstnode(srcnodeid);
		activetestack.setSrcnode(dstnodeid);
		return Serialize(MessageType.ActiveLink_ACK, activetestack.build().toByteString());
		/*ComCenterMessage.ComCenterBaseMessage.Builder basemsg = ComCenterMessage.ComCenterBaseMessage.newBuilder();
		basemsg.setId(MessageType.ActiveLink_ACK);
		basemsg.setContent(activetestack.build().toByteString());
		return clientinfo.appendSendMessage(basemsg.build());*/
	}
}
