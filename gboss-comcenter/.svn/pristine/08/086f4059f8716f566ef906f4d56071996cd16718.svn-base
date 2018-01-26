/*
********************************************************************************************
Discription:  坐席请求服务器返回所有警情列表
			  
			  
Written By:   ZXZ
Date:         2014-08-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.alarmarray.websocket;

import java.util.ArrayList;

import cc.chinagps.gboss.alarmarray.AlarmInfo;
import cc.chinagps.gboss.alarmarray.AlarmManager;
import cc.chinagps.gboss.alarmarray.SeatClientInfo;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.AskAlarmList;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.AskAlarmList_ACK;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.websocket.ClientBaseHandler;

public class AskAlarmListHandler extends ClientBaseHandler {

    private String username = "";       //坐席登录名
    private boolean onlyself = true;    //true: 只是自己处理的（默认），false: 全部警情
    private boolean onlycount = true;	//true: 只是返回数量(默认)，false:返回明细
    
	public AskAlarmListHandler(ComCenterBaseMessage basemsg, SeatClientInfo info) {
		super(basemsg, info);
	}

	@Override
	public int decode() {
		try {
			AskAlarmList askalarm = AskAlarmList.parseFrom(msgcontent);
			username = askalarm.getUsername().trim();
			if (!username.equals(((SeatClientInfo)clientinfo).username)) {
				retcode = ResultCode.Seat_Error;
				retmsg = "坐席名不相同";
			}
			if (askalarm.hasOnlyself()) {
				onlyself = askalarm.getOnlyself();
			}
			if (askalarm.hasOnlycount()) {
				onlycount = askalarm.getOnlycount();
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
	}

	@Override
	public byte[] response() {
		AskAlarmList_ACK.Builder ack = AskAlarmList_ACK.newBuilder();
		ack.setRetcode(retcode);
		ack.setRetmsg(retmsg);
		//如果解码成功
		if (retcode == ResultCode.OK) {
			if (onlycount) {
				int count = AlarmManager.alarmmanager.askAlarmCount((SeatClientInfo)(clientinfo), onlyself);
				ack.setAlarmscount(count);
			} else {
				ArrayList<AlarmInfo> alarmlist = AlarmManager.alarmmanager.askAlarmList((SeatClientInfo)(clientinfo), onlyself);
				ack.setAlarmscount(alarmlist.size());
				int i = 0;
				//添加警情列表
				for(AlarmInfo alarm: alarmlist) {
					AskAlarmList_ACK.AlarmBaseInfo.Builder base = ack.addAlarmsBuilder();
					base.setCallLetter(alarm.callLetter);
					base.setAlarmTime(alarm.alarmtime);
					base.setAlarmid(alarm.alarmid);
					base.setAlarmname(alarm.alarmname);
					base.setStatus(alarm.getstatus());
					String seatname = alarm.getseatname();
					if (seatname != null && !seatname.isEmpty()) {
						base.setSeatname(seatname);
					}
					if ((++i) >= 500) {
						//最多只返回500
						break;
					}
				}
			}
		}
		return Serialize(MessageType.AskAlarmList_ACK, ack.build().toByteString()); 
	}
}
