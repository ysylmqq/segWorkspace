/*
********************************************************************************************
Discription:  坐席上传警情处理结果
			  
			  
Written By:   ZXZ
Date:         2014-08-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.alarmarray.websocket;

import cc.chinagps.gboss.alarmarray.AlarmInfo;
import cc.chinagps.gboss.alarmarray.AlarmManager;
import cc.chinagps.gboss.alarmarray.SeatClientInfo;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.HandleAlarm;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.HandleAlarm_ACK;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.websocket.ClientBaseHandler;

public class HandleAlarmHandler extends ClientBaseHandler {

    public String username = "";       		//坐席登录名
    public String callLetter = "";     		//终端呼号
    public String alarmsn = "";        		//警情唯一序列号
    public int handlecode = ResultCode.OK;  //处理结果(ResultCode)
    public String handlemsg = "";      		//处理信息
    public String slaver = "";				//应用程序或通信中心(Slaver)名称, 分布式通信中心用

    public HandleAlarmHandler(ComCenterBaseMessage basemsg, SeatClientInfo info) {
		super(basemsg, info);
	}

	@Override
	public int decode() {
		try {
			HandleAlarm handlealarm = HandleAlarm.parseFrom(msgcontent);
			username = handlealarm.getUsername().trim();
			assert username.equals(((SeatClientInfo)clientinfo).username);
			callLetter = handlealarm.getCallLetter().trim();
			handlecode = handlealarm.getHandlecode();
			if (handlealarm.hasHandlemsg()) {
				handlemsg = handlealarm.getHandlemsg().trim();
			}
			alarmsn = handlealarm.getAlarmsn().trim();
			if (handlealarm.hasSlaver()) {
				slaver = handlealarm.getSlaver().trim();
			}
        } catch(Exception e) {
			e.printStackTrace();
			retcode = ResultCode.Decode_Error;
			retmsg = "解码失败";
		}
		//如果解码失败，不能往下执行
		return retcode;
	}

	@Override
	public void run() {
		System.out.println("HandleAlarm: " + alarmsn + ", " + callLetter + ", " + username);
		if (handlecode == ResultCode.OK) {
			AlarmInfo alarminfo = AlarmManager.alarmmanager.findAlarmInfo(callLetter); 
			if (alarminfo == null) {
				retcode = ResultCode.AlarmNoExist_Error;
				retmsg = "警单不存在或已经被处理";
				return;
			}
			SeatClientInfo seat = alarminfo.getseat();
			if (seat == null) {
				retcode = ResultCode.Seat_Error;
				retmsg = "警单未分配";
				return;
			}
			if (!seat.username.equals(clientinfo.username)) {
				retcode = ResultCode.Seat_Error;
				retmsg = "处警坐席非本坐席";
				return;
			}
			AlarmManager.alarmmanager.handleAlarm(this);
		}
	}

	@Override
	public byte[] response() {
		HandleAlarm_ACK.Builder handleack = HandleAlarm_ACK.newBuilder();
		handleack.setRetcode(retcode);
		handleack.setRetmsg(retmsg);
		handleack.setUsername(((SeatClientInfo)clientinfo).username);
		handleack.setCallLetter(callLetter);
		handleack.setAlarmsn(alarmsn);
		return Serialize(MessageType.HandleAlarm_ACK, handleack.build().toByteString()); 
	}

}
