/*
********************************************************************************************
Discription:  客户端请求基类
			  
			  
Written By:   ZXZ
Date:         2014-04-28
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.websocket;
import cc.chinagps.gboss.comcenter.WebsocketClientInfo;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage;

import com.google.protobuf.ByteString;

public abstract class ClientBaseHandler {
	protected ByteString msgcontent = null;		//消息内容
	protected int retcode = ResultCode.OK;		//返回ID
	protected String retmsg = "成功";			//返回消息
	public WebsocketClientInfo clientinfo = null;	//客户端信息，处理过程中要保留
	
	public ClientBaseHandler(ComCenterMessage.ComCenterBaseMessage basemsg, WebsocketClientInfo info) {
		//发送透传指令时，都为空
		if (basemsg != null) msgcontent = basemsg.getContent();
		if (info != null) clientinfo = info;
	}
	//解码
	public abstract int decode();
	//执行
	public abstract void run();
	//返回
	public abstract byte[] response();
	
	public int getRetcode() {
		return retcode;
	}
	public String getRetmsg() {
		return retmsg;
	}
	public void setRetcode(int code) {
		retcode = code;
	}
	public void setRetmsg(String msg) {
		retmsg = msg;
	}

	/*
	 * 将一个基础包（protobuf）,序列化字节串
	 */
	public static byte[] Serialize(int msgtype, ByteString bytestring) {
		ComCenterMessage.ComCenterBaseMessage.Builder basemsg = ComCenterMessage.ComCenterBaseMessage.newBuilder();
		basemsg.setId(msgtype);
		basemsg.setContent(bytestring);
		ComCenterMessage.Builder msg = ComCenterMessage.newBuilder();
		msg.addMessages(basemsg.build());
		return msg.build().toByteArray();
	}
}
