/*
********************************************************************************************
Discription:  读历史信息
			  
			  
Written By:   ZXZ
Date:         2014-05-09
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.websocket;

import cc.chinagps.gboss.comcenter.WebsocketClientInfo;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.GetCommandHistory;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.GetCommandHistory_ACK;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.hbase.HbaseClientManager;

public class GetCommandHistoryHandler extends ClientBaseHandler {

    private String callletter = "";	//终端呼号
    private long starttime = 0;	//开始时间
    private long endtime = 0;	//结束时间
	
	public GetCommandHistoryHandler(ComCenterBaseMessage basemsg, WebsocketClientInfo info) {
		super(basemsg, info);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int decode() {
		try {
			GetCommandHistory history = GetCommandHistory.parseFrom(msgcontent);
			callletter = history.getCallLetter().trim();
			starttime = history.getStarttime(); 
			endtime = history.getEndtime(); 
        } catch(Exception e) {
			e.printStackTrace();
			retcode = ResultCode.Decode_Error;
			retmsg = "解码失败";
		}
		return retcode;
	}

	@Override
	public void run() {
		if (!clientinfo.isLogin()) {
			retcode = ResultCode.NoLogin_Error;
			retmsg = "未登录";
			return;
		}
		//如果没有呼号
		if (callletter.length() == 0) {
			retcode = ResultCode.CallLetterExist_Error;
			retmsg = "不存在车载终端呼号";
			return;
		}
		retcode = HbaseClientManager.hbaseclientmanager.GetCommandHistory(clientinfo, callletter, starttime, endtime);
	}

	@Override
	public byte[] response() {
		//如果前面正常，表示已经加入HBASE取历史信息的队列，等待HBASE线程读
		if (retcode == ResultCode.OK) {
			return null;
		}
		//如果有错误，则直接返回失败
		GetCommandHistory_ACK.Builder historyack = GetCommandHistory_ACK.newBuilder();
		historyack.setRetcode(retcode);
		historyack.setRetmsg(retmsg);
		historyack.setCallLetter(callletter);
		return Serialize(MessageType.GetCommandHistory_ACK, historyack.build().toByteString());
	}
}
