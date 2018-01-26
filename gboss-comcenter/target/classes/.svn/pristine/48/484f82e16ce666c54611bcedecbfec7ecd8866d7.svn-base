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
package cc.chinagps.gboss.comcenter.interprotocol.clienttest;

import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.SendCommandSend_ACK;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;

public class SendCommandSendACKHandler extends ClientBaseHandler {

	public String callLetter = "";	//车辆呼号
	public String sn = ""; 		    //序列号
	public int cmdId = 0;		    //命令编号
	public int cmdretcode = 0;      //发送结果(ResultCode)
	public String cmdretmsg = "";   //结果说明

	public SendCommandSendACKHandler(ComCenterBaseMessage basemsg, CenterClientHandler handler) {
		super(basemsg, handler);
	}

	@Override
	public int decode() {
		try {
			SendCommandSend_ACK sendcmdsendack = SendCommandSend_ACK.parseFrom(msgcontent);
			callLetter = sendcmdsendack.getCallLetter();
			sn = sendcmdsendack.getSn();
			cmdId = sendcmdsendack.getCmdId();
			cmdretcode = sendcmdsendack.getCmdId();
			if (sendcmdsendack.hasRetmsg()) {
				cmdretmsg = sendcmdsendack.getRetmsg();
			}
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
}
