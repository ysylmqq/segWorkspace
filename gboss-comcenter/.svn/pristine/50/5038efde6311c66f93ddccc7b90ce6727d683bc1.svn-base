/*
********************************************************************************************
Discription:  Master服务器收到 Slaver通知新警情
			  
			  
Written By:   ZXZ
Date:         2014-09-05
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.alarmarray.UdpHandler;

import cc.chinagps.gboss.alarmarray.AlarmManager;
import cc.chinagps.gboss.alarmarray.ZooKeeperAlarm;
import cc.chinagps.gboss.comcenter.buff.AlarmDistributeDataBuff.UDPNewAlarm;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.NewAlarm_ACK;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsSimpleInfo;
import cc.chinagps.gboss.comcenter.websocket.NewAlarmHandler;

public class UDPNewAlarmHandler extends NewAlarmHandler {

    public int existalarm;			//是否已经存在警1:新警情，2:要添加警情ID

    public UDPNewAlarmHandler(ComCenterBaseMessage basemsg) {
		super(basemsg, null);
	}

	@Override
	public int decode() {
		try {
			UDPNewAlarm newalarm = UDPNewAlarm.parseFrom(msgcontent);
			this.slaver = newalarm.getSlaver();
			this.callLetter = newalarm.getCallLetter();
			this.alarmsn = newalarm.getAlarmsn();
			this.alarmtime = newalarm.getAlarmTime();
			this.alarmid = newalarm.getAlarmid();
			this.level = newalarm.getLevel();
			if (newalarm.hasGpsinfo()) {
		        this.gpsinfo = GpsSimpleInfo.newBuilder(newalarm.getGpsinfo()).build();	//复制
			}
			this.existalarm = newalarm.getExistalarm();
        } catch(Exception e) {
			e.printStackTrace();
			retcode = ResultCode.Decode_Error;
			retmsg = "解码失败";
		}
		return retcode;
	}

	@Override
	public void run() {
		AlarmManager.alarmmanager.appendAlarm(this);
	}

	/*
	 * Master返回应答，表示新警情是否添加到警情队列
	 * 
	 */
	@Override
	public byte[] response() {
		//不是广播消息，或者是Master（应答）
		if (ZooKeeperAlarm.zookeeperalarm.isMasterd) {
			NewAlarm_ACK.Builder newalarmack = NewAlarm_ACK.newBuilder();
			newalarmack.setRetcode(retcode);
			newalarmack.setRetmsg(retmsg);
			newalarmack.setAlarmsn(alarmsn);
			newalarmack.setCallLetter(callLetter);
			newalarmack.setSlaver(slaver);
			return Serialize(MessageType.NewAlarm_ACK, newalarmack.build().toByteString());
		}
		return null;
	}

}
