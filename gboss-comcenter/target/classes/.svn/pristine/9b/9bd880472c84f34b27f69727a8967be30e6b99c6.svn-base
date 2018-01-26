/*
********************************************************************************************
Discription:  结束读历史信息
			  
			  
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
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.StopHistoryInfo;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.StopHistoryInfo_ACK;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.hbase.HbaseClientManager;

public class StopHistoryInfoHandler extends ClientBaseHandler {

	private String callletter;
	private int infotype = 0;	//取历史信息类型
	private String sn = "";
		
	public StopHistoryInfoHandler(ComCenterBaseMessage basemsg, WebsocketClientInfo info) {
		super(basemsg, info);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int decode() {
		try {
			StopHistoryInfo stop = StopHistoryInfo.parseFrom(msgcontent);
			callletter = stop.getCallLetter().trim();
			if (stop.hasInfoType())
				infotype = stop.getInfoType();
			if (stop.hasSn()) {
				sn = stop.getSn(); 
			}
		} catch (Exception e) {
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
			retcode = HbaseClientManager.hbaseclientmanager.stopHistoryInfo(clientinfo, callletter, infotype, sn);
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
		//直接返回应答，不是其他线程处理
		//如果有错误，则直接返回失败
		StopHistoryInfo_ACK.Builder stopack = StopHistoryInfo_ACK.newBuilder();
		stopack.setRetcode(retcode);
		stopack.setRetmsg(retmsg);
		stopack.setSn(sn);
		stopack.setCallLetter(callletter);
		stopack.setInfoType(infotype);
		return Serialize(MessageType.StopHistoryInfo_ACK, stopack.build().toByteString());
	}
}
