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
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.GetHistoryInfo;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.GetHistoryInfo_ACK;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.outinterface.OutInterfaceClientInfo;
import cc.chinagps.gboss.hbase.HbaseClientManager;
import cc.chinagps.lib.util.Util;

public class GetHistoryInfoHandler extends ClientBaseHandler {

	private int infotype = 0;	//取历史信息类型
    private String callletter = "";	//终端呼号
    private long starttime = 0;	//开始时间
    private long endtime = 0;	//结束时间
    private int pagenumber = 100;	//每页数量、默认100
    private int totalnumber = 5000;	//总数控制在5000条
    private boolean autonextpage = true;	//自动发送下一页
	private String sn = "";
	private boolean reversed = false;	//是否按时间逆序输出
	private boolean norepeat = false;	//不回重复信息(只支持简单GPS)
	
	public GetHistoryInfoHandler(ComCenterBaseMessage basemsg, WebsocketClientInfo info) {
		super(basemsg, info);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int decode() {
		try {
			GetHistoryInfo history = GetHistoryInfo.parseFrom(msgcontent);
			infotype = history.getInfoType();
			callletter = history.getCallLetter().trim();
			if (history.hasStarttime()) {
				starttime = history.getStarttime(); 
			}
			if (history.hasEndtime()) {
				endtime = history.getEndtime(); 
			}
			if (history.hasPageNumber()) {
				pagenumber = history.getPageNumber(); 
				if (pagenumber <= 0) {
					pagenumber = 100;
				}
			}
			if (history.hasTotalNumber()) {
				totalnumber = history.getTotalNumber(); 
				if (totalnumber <= 0) {
					totalnumber = 5000;
				}
			}
			if (history.hasAutonextpage()) {
				autonextpage = history.getAutonextpage(); 
			}
			if (history.hasSn()) {
				sn = history.getSn(); 
			}
			if (history.hasReversed()) {
				reversed = history.getReversed(); 
			}
			if (history.hasNorepeat()) {
				norepeat = history.getNorepeat();
			}
			if (starttime < endtime) {
			//if (starttime > endtime) {
				long tmp = endtime;
				endtime = starttime;
				starttime = tmp;
			}
			//最多5千条 
			if (totalnumber > 5000)
				totalnumber = 5000;
			//设置totalnumber是pagenumber的整数倍
			totalnumber = (((totalnumber - 1) / pagenumber) + 1) * pagenumber;
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
		if ((callletter.length() == 0) || (!Util.isNumeric(callletter))) {
			retcode = ResultCode.CallLetterExist_Error;
			retmsg = "不存在车载终端呼号";
			return;
		}
		// 外部接口只能取属于他的车
		if (clientinfo instanceof OutInterfaceClientInfo) {
			if (!((OutInterfaceClientInfo)clientinfo).hasCallLetter(callletter)) {
				retcode = ResultCode.CallLetterExist_Error;
				retmsg = "不存在车载终端呼号";
				return;
			}
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
			HbaseClientManager.hbaseclientmanager.appendHistoryInfo(clientinfo, callletter, infotype,
					starttime, endtime, pagenumber, totalnumber, autonextpage, sn, reversed, norepeat);
			
		} else {
			retcode = ResultCode.Parameters_Error;
			retmsg = "历史信息类型暂不支持";
		}
	}

	@Override
	public byte[] response() {
		//如果前面正常，表示已经加入HBASE取历史信息的队列，等待HBASE线程读
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
