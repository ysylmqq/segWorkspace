/*
********************************************************************************************
Discription:  坐席设置忙或闲
			  
			  
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
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.SetAlarmBusy;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.SetAlarmBusy_ACK;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.websocket.ClientBaseHandler;

public class SetAlarmBusyHandler extends ClientBaseHandler {

    private String username = "";   //坐席登录名
    private String seatid = "";
    public boolean busy = true;    //true:忙（默认），false:闲
    public String callLetter = null; 

    public SetAlarmBusyHandler(ComCenterBaseMessage basemsg, SeatClientInfo info) {
		super(basemsg, info);
	}

	@Override
	public int decode() {
		try {
			SetAlarmBusy setalarmbusy = SetAlarmBusy.parseFrom(msgcontent);
			this.username = setalarmbusy.getUsername().trim();
			assert this.username.equals(((SeatClientInfo)clientinfo).username);
			this.seatid = setalarmbusy.getSeatid().trim();
			this.busy = setalarmbusy.getBusy();
			if (setalarmbusy.hasCallLetter()) {
				callLetter = setalarmbusy.getCallLetter().trim();
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
		if (clientinfo instanceof SeatClientInfo) {
			SeatClientInfo seat = (SeatClientInfo)clientinfo;
			seat.userid = this.seatid;
			seat.setstatus(busy ? SeatClientInfo.BUSYSTATUS : SeatClientInfo.IDLESTATUS, this.callLetter);
			if (!busy) {
				//如果置闲，判断是否已经分配警情，如果分配，则再分本一次
				AlarmManager.alarmmanager.reallocSeatAlarm(seat);
			}
		}
	}

	/*
	 * 返回设置忙闲结果
	 * 
	 */
	@Override
	public byte[] response() {
		SetAlarmBusy_ACK.Builder setbusyack = SetAlarmBusy_ACK.newBuilder();
		setbusyack.setRetcode(retcode);
		setbusyack.setRetmsg(retmsg);
		SeatClientInfo seatinfo = (SeatClientInfo)clientinfo;
		setbusyack.setUsername(seatinfo.username);
		setbusyack.setBusy(busy);
		if (seatinfo.busycallLetter != null) {	//取正在处理此辆车的其他坐席
			
		}
		return Serialize(MessageType.SetAlarmBusy_ACK, setbusyack.build().toByteString()); 
	}

}
