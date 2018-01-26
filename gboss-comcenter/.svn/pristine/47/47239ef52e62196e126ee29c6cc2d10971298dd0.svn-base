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
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.GetHistoryInfo_ACK;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;

public class GetHistoryInfoACKHandler extends ClientBaseHandler {

	public boolean lastpage = false;
	public String callLetter = "";
	public long historycount = 0;
	
	public GetHistoryInfoACKHandler(ComCenterBaseMessage basemsg, CenterClientHandler handler) {
		super(basemsg, handler);
	}

	@Override
	public int decode() {
		try {
			GetHistoryInfo_ACK historyack = GetHistoryInfo_ACK.parseFrom(msgcontent);
			retcode = historyack.getRetcode();
			retmsg = historyack.getRetmsg();
			lastpage = historyack.getLastPage();
			historycount = historyack.getGpsesCount() + historyack.getAlarmsCount() + historyack.getFaultsCount() + 
					historyack.getOperatesCount() + historyack.getSmsCount() + historyack.getTravelsCount();
			if (historyack.getGpsesCount() > 0) {
				callLetter = historyack.getGpses(0).getCallLetter();
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
		if (retcode == ResultCode.OK) {
			System.out.println(String.format("get history: %s %d, %B", callLetter, historycount, lastpage));
			if (lastpage) {
			}
		}
	}

}
