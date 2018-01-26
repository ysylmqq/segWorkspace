/*
********************************************************************************************
Discription:  删除监控列表
			  
			  
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
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.RemoveMonitor_ACK;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.RemoveMonitor;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;

public class RemoveMonitorHandler extends ClientBaseHandler {

	private HashSet<String> callletterlist = new HashSet<String>();	//要添加的监控列表
	protected List<Integer> infotypelist;
	
	public RemoveMonitorHandler(ComCenterBaseMessage basemsg, WebsocketClientInfo info) {
		super(basemsg, info);
	}

	@Override
	public int decode() {
		try {
			RemoveMonitor removemonitor = RemoveMonitor.parseFrom(msgcontent);
			for(String callLetter : removemonitor.getCallLettersList()) {
				callletterlist.add(callLetter.trim());
			}
			infotypelist = removemonitor.getInfotypesList();
			//infotypelist.clear();	//删除监控列表，类型暂时不起作用
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
		this.clientinfo.removeMonitor(callletterlist);
	}

	/*
	 * 返回还在监控的车辆列表
	 */
	@Override
	public byte[] response() {
		//打登录返回的报文
		RemoveMonitor_ACK.Builder removemonitorack = RemoveMonitor_ACK.newBuilder();
		removemonitorack.setRetcode(retcode);
		removemonitorack.setRetmsg(retmsg);
		removemonitorack.addAllCallLetters(clientinfo.getMonitorList());
		if (clientinfo.hasAllUnit()) {
			removemonitorack.addCallLetters(WebsocketClientInfo.ALLUNIT);
		}
		return Serialize(MessageType.RemoveMonitor_ACK, removemonitorack.build().toByteString()); 
	}
}
