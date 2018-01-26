/*
********************************************************************************************
Discription:  Master应答同步警情
			  
Written By:   ZXZ
Date:         2015-01-21
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.alarmarray.UdpHandler;

import java.util.List;

import cc.chinagps.gboss.alarmarray.AlarmManager;
import cc.chinagps.gboss.comcenter.ComCenter;
import cc.chinagps.gboss.comcenter.buff.AlarmDistributeDataBuff.AskSyncAlarmList_ACK;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.websocket.ClientBaseHandler;

public class UDPAskSyncAlarmListACKHandler extends ClientBaseHandler {
    private String slaver = "";	//Slave服务器名称
    public String sn = "";    	//序号
    public List<AskSyncAlarmList_ACK.SyncAlarmInfo> syncalarmlist;
    
    public UDPAskSyncAlarmListACKHandler(ComCenterBaseMessage basemsg) {
		super(basemsg, null);
	}

	@Override
	public int decode() {
		try {
			AskSyncAlarmList_ACK syncack = AskSyncAlarmList_ACK.parseFrom(msgcontent);
			this.slaver = syncack.getSlaver();
			this.sn = syncack.getSn();
			this.syncalarmlist = syncack.getAlarmlistList();
        } catch(Exception e) {
			e.printStackTrace();
			retcode = ResultCode.Decode_Error;
			retmsg = "解码失败";
		}
		return retcode;
	}

	@Override
	public void run() {
		//如果是自己广播的新警情消息, 消除广播记录
		if (this.slaver.equals(ComCenter.systemname)) {
			AlarmManager.alarmmanager.SyncAlarmListACK(this);
		}
	}

	/*
	 * Master返回，不用再应答
	 * 
	 */
	@Override
	public byte[] response() {
		return null;
	}

}
