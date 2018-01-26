/*
********************************************************************************************
Discription:  读最后信息
			  
			  
Written By:   ZXZ
Date:         2014-05-09
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.websocket;

import java.util.List;
import cc.chinagps.gboss.comcenter.WebsocketClientInfo;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.GetLastInfo_ACK;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.GetLastInfo;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.outinterface.OutInterfaceClientInfo;
import cc.chinagps.gboss.hbase.HbaseClientManager;
import cc.chinagps.lib.util.Util;


public class GetLastInfoHandler extends ClientBaseHandler {
	private int infotype = 0;
	private List<String> callletterlist;
	private String sn = "";
	
	public GetLastInfoHandler(ComCenterBaseMessage basemsg, WebsocketClientInfo info) {
		super(basemsg, info);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int decode() {
		try {
			GetLastInfo lastinfo = GetLastInfo.parseFrom(msgcontent);
			infotype = lastinfo.getInfoType();
			callletterlist = lastinfo.getCallLettersList();
			sn = lastinfo.getSn();
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
		if (callletterlist.size() == 0) {
			retcode = ResultCode.CallLetterExist_Error;
			retmsg = "不存在车载终端呼号";
			return;
		}
		//判断呼号是否属于外部接口这个公司
		if (clientinfo instanceof OutInterfaceClientInfo) {
			for(String callletter : callletterlist) {
				if (!((OutInterfaceClientInfo)clientinfo).hasCallLetter(callletter)) {
					retcode = ResultCode.CallLetterExist_Error;
					retmsg = "不存在车载终端呼号";
					return;
				}
			}
		}

		if (infotype == MessageType.DeliverGPS ||
			infotype == MessageType.DeliverTravel ||
			infotype == MessageType.DeliverOperateData ||
			infotype == MessageType.DeliverAlarm ||
			infotype == MessageType.DeliverSMS ||
			infotype == MessageType.DeliverFault ||
			infotype == MessageType.DeliverUnitLoginOut ||
			infotype == MessageType.DeliverFaultLight ||
			infotype == MessageType.DeliverOBD) {
			//根据呼号不同。插入到Hbase取最扣信息的不同队列
			for(String callletter: callletterlist) {
				callletter = callletter.trim();
				if (!Util.isNumeric(callletter)) {
					retcode = ResultCode.UnitNoExist_Error1;
					retmsg = "呼号不是数字";
				}
				if (callletter.length() > 0 ) {
					HbaseClientManager.hbaseclientmanager.appendLastInfo(clientinfo, callletter, infotype, sn);
				}
			}
		} else {
			retcode = ResultCode.Parameters_Error;
			retmsg = "最后信息类型暂不支持";
		}
	}

	@Override
	public byte[] response() {
		//如果前面正常，表示已经加入HBASE取最扣信息的队列，等待HBASE线程读
		if (retcode == ResultCode.OK)
			return null;
		//如果有错误，则直接返回失败
		GetLastInfo_ACK.Builder lastack = GetLastInfo_ACK.newBuilder();
		lastack.setRetcode(retcode);
		lastack.setRetmsg(retmsg);
		lastack.setSn(sn);
		return Serialize(MessageType.GetLastInfo_ACK, lastack.build().toByteString());
	}
}
