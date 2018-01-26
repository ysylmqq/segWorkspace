/*
********************************************************************************************
Discription:  通信中心单元测试工具用
			  
			  
Written By:   ZXZ
Date:         2014-05-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.serverHandler;

import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage;
import cc.chinagps.gboss.comcenter.testtools.WebSocketClientHandler;

import com.google.protobuf.ByteString;

public abstract class serverBaseHandler {

	protected ByteString msgcontent = null;	//消息内容
	protected int retcode = ResultCode.OK;	//返回ID
	protected String retmsg = "成功";			//返回消息
	public WebSocketClientHandler handler = null;	//客户端信息，处理过程中要保留
	
	public serverBaseHandler(ComCenterMessage.ComCenterBaseMessage basemsg, WebSocketClientHandler handler) {
		//发送透传指令时，都为空
		if (basemsg != null) msgcontent = basemsg.getContent();
		if (handler != null) this.handler = handler;
	}
	//解码
	public abstract int decode();
	//执行
	public abstract void run();
	//返回

}