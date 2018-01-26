/*
********************************************************************************************
Discription:  服务器分配转警, 坐席应答是否接受分配
			  
			  
Written By:   ZXZ
Date:         2014-08-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.alarmarray.websocket;

import cc.chinagps.gboss.alarmarray.AlarmManager;
import cc.chinagps.gboss.alarmarray.SeatClientInfo;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.AllotAlarm_ACK;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.websocket.ClientBaseHandler;

public class AllotAlarmACKHandler extends ClientBaseHandler {

    public String username = "";   //坐席登录名
    public String callLetter = ""; //警情呼号
    public String alarmsn = "";    //警情唯一序列号

    public AllotAlarmACKHandler(ComCenterBaseMessage basemsg, SeatClientInfo info) {
		super(basemsg, info);
	}

	@Override
	public int decode() {
		try {
			AllotAlarm_ACK allocack = AllotAlarm_ACK.parseFrom(msgcontent);
			retcode = allocack.getRetcode();
			if (allocack.hasRetmsg()) {
				retmsg = allocack.getRetmsg();
			}
			username = allocack.getUsername();
			assert username.equals(((SeatClientInfo)clientinfo).username);
			callLetter = allocack.getCallLetter().trim();
			alarmsn = allocack.getAlarmsn();
        } catch(Exception e) {
			e.printStackTrace();
			retcode = ResultCode.Decode_Error;
			retmsg = "解码失败";
		}
		//retcode 坐席可能返回拒绝，所以都要执行下一步run
		return ResultCode.OK;
	}

	@Override
	public void run() {
		System.out.println("AllotAlarmACK: " + alarmsn + ", " + callLetter + ", " + username);
		AlarmManager.alarmmanager.allotAlarmACK(this);
	}

	/*
	 * 坐席返回是否接受处警（一般都是接受），不用再应答，只要更新警情状态
	 * 
	 */
	@Override
	public byte[] response() {
		return null;
	}

}
