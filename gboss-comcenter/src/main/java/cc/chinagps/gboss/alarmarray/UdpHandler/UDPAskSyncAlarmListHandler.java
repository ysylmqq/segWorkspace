/*
********************************************************************************************
Discription:  Slaver请求同步警情
			  
			  
Written By:   ZXZ
Date:         2015-01-21
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.alarmarray.UdpHandler;

import com.google.protobuf.ByteString;

import cc.chinagps.gboss.alarmarray.AlarmManager;
import cc.chinagps.gboss.comcenter.buff.AlarmDistributeDataBuff.AskSyncAlarmList;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.websocket.NewAlarmHandler;

public class UDPAskSyncAlarmListHandler extends NewAlarmHandler {

    public String slaver; //Slaver通信中心名称
    public String sn;     //唯一序列号
    public ByteString response = null;

    public UDPAskSyncAlarmListHandler(ComCenterBaseMessage basemsg) {
		super(basemsg, null);
	}

	@Override
	public int decode() {
		try {
			AskSyncAlarmList ask = AskSyncAlarmList.parseFrom(msgcontent);
			this.slaver = ask.getSlaver();
			this.sn = ask.getSn();
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
			try {
				AlarmManager.alarmmanager.AskSyncAlarmList(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * 
	 */
	@Override
	public byte[] response() {
		if (this.response != null) {
			return Serialize(MessageType.AskSyncAlarmList_ACK, this.response); 
		}
		return null;
	}
}
