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
package cc.chinagps.gboss.comcenter.websocket;

import com.google.protobuf.ByteString;

import cc.chinagps.gboss.comcenter.WebsocketClientInfo;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;

public class ActiveLinkHandler extends ClientBaseHandler {
	
	public ActiveLinkHandler(ComCenterMessage.ComCenterBaseMessage requestmsg, WebsocketClientInfo info) {
		super(requestmsg, info);
	}

	@Override
	public int decode() {
		return ResultCode.OK;
	}

	@Override
	public void run() {
	}
	
	/*
	 * 直接返回链路检测报文
	 */
	@Override
	public byte[] response() {
		return Serialize(MessageType.ActiveLink_ACK, ByteString.EMPTY);
	}
}
