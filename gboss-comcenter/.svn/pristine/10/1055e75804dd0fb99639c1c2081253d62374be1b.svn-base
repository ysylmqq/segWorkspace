/*
********************************************************************************************
Discription:  添加监控列表
			  
			  
Written By:   ZXZ
Date:         2014-05-09
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.websocket;

import java.util.HashSet;
import java.util.List;

import cc.chinagps.gboss.comcenter.WebsocketClientInfo;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.AddMonitor;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.AddMonitor_ACK;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.interprotocolsocket.InterProtocolClientInfo;

public class AddMonitorHandler extends ClientBaseHandler {

	private HashSet<String> callletterlist = new HashSet<String>();	//要添加的监控列表
	private List<Integer> infotypelist;
	private boolean clearold = false;
	
	public AddMonitorHandler(ComCenterBaseMessage basemsg, WebsocketClientInfo info) {
		super(basemsg, info);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int decode() {
		try {
			AddMonitor addmonitor = AddMonitor.parseFrom(msgcontent);
			for(String callLetter : addmonitor.getCallLettersList()) {
				if (callLetter.equals(WebsocketClientInfo.ALLUNIT) && !(this.clientinfo instanceof InterProtocolClientInfo)) {
					 //只有内部协议才支持ALLUNIT
					retcode = ResultCode.Parameters_Error;
					retmsg = "不支持ALLUNIT";
					return retcode;
				}
				callletterlist.add(callLetter.trim());
			}
			infotypelist = addmonitor.getInfotypesList();
			if (addmonitor.hasClearold()) {
				clearold = addmonitor.getClearold();
			}
        } catch(Exception e) {
			//e.printStackTrace();
			retcode = ResultCode.Decode_Error;
			retmsg = "解码失败";
		}
		return retcode;
	}

	@Override
	public void run() {
		if (this.clientinfo.isLogin()) {
			if (callletterlist.size() > 0) {
				this.clientinfo.addMonitor(callletterlist, infotypelist, clearold);
			}
		} else {
			retcode = ResultCode.NoLogin_Error;
			retmsg = "未登录";
		}
	}

	/*
	 * 返回全部监控的车辆列表（包括以前添加的）
	 */
	@Override
	public byte[] response() {
		//打登录返回的报文
		AddMonitor_ACK.Builder addmonitorack = AddMonitor_ACK.newBuilder();
		addmonitorack.setRetcode(retcode);
		addmonitorack.setRetmsg(retmsg);
		addmonitorack.addAllCallLetters(clientinfo.getMonitorList());
		if (clientinfo.hasAllUnit()) {
			addmonitorack.addCallLetters(WebsocketClientInfo.ALLUNIT);
		}
		return Serialize(MessageType.AddMonitor_ACK, addmonitorack.build().toByteString()); 
	}
}
