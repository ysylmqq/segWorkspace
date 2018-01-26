/*
********************************************************************************************
Discription:  读历史信息下一页
			  
			  
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
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.GetHistoryInfoNextPage;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.GetHistoryInfo_ACK;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.hbase.HbaseClientManager;

public class GetHistoryInfoNextPageHandler extends ClientBaseHandler {

    private String callletter = "";	//终端呼号
	private int infotype = 0;	//取历史信息类型
	private String sn = "";

    public GetHistoryInfoNextPageHandler(ComCenterBaseMessage basemsg, WebsocketClientInfo info) {
		super(basemsg, info);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int decode() {
		try {
			GetHistoryInfoNextPage nextpage = GetHistoryInfoNextPage.parseFrom(msgcontent);
			callletter = nextpage.getCallLetter().trim();
			if (nextpage.hasInfoType())
				infotype = nextpage.getInfoType();
			if (nextpage.hasSn()) {
				sn = nextpage.getSn(); 
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

		if (infotype == MessageType.DeliverGPS ||
			infotype == MessageType.DeliverTravel ||
			infotype == MessageType.DeliverOperateData ||
			infotype == MessageType.DeliverAlarm ||
			infotype == MessageType.DeliverSMS ||
			infotype == MessageType.DeliverSimpleGPS ||
			infotype == MessageType.DeliverFault ||
			infotype == MessageType.DeliverUnitLoginOut ||
			infotype == MessageType.DeliverFaultLight ||
			infotype == MessageType.DeliverOBD) {
			//根据呼号不同。插入到Hbase取最扣信息的不同队列
			retcode = HbaseClientManager.hbaseclientmanager.nextHistoryInfo(clientinfo, callletter, infotype, sn);
			if (retcode != ResultCode.OK) {
				retmsg = "没有找到对应的历史查询请求";
			}
		} else {
			retcode = ResultCode.Parameters_Error;
			retmsg = "历史信息类型暂不支持";
		}
	}

	@Override
	public byte[] response() {
		//如果前面正常，表示已经加入HBASE取历史信息(下一页)的队列，等待HBASE线程读
		if (retcode == ResultCode.OK)
			return null;
		//如果有错误，则直接返回失败
		GetHistoryInfo_ACK.Builder historyack = GetHistoryInfo_ACK.newBuilder();
		historyack.setRetcode(retcode);
		historyack.setRetmsg(retmsg);
		historyack.setSn(sn);
		historyack.setLastPage(true);
		return Serialize(MessageType.GetHistoryInfo_ACK, historyack.build().toByteString());
	}
}
