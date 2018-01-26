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
package cc.chinagps.gboss.comcenter.websocket;

import cc.chinagps.gboss.alarmarray.AlarmManager;
import cc.chinagps.gboss.comcenter.WebsocketClientInfo;
import cc.chinagps.gboss.comcenter.ComCenter;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsSimpleInfo;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.NewAlarm;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.NewAlarm_ACK;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;

public class NewAlarmHandler extends ClientBaseHandler {

	public static int EXIST = 0;
	public static int NEW   = 1;
	public static int APPEND = 2;

    public String slaver;         	//应用程序或通信中心(Slaver)名称
    public String callLetter;     	//终端呼号
    public String alarmsn;        	//警情唯一序列号
    public long alarmtime;       	//警情发生时间，不一定是GPS时间, 以系统判断时间为准
    public int alarmid;           	//警情类别
    public int level;           	//警情级别(1-3)
    public GpsSimpleInfo gpsinfo; 	//gps位置

    public NewAlarmHandler(ComCenterBaseMessage basemsg, WebsocketClientInfo info) {
		super(basemsg, info);
	}

	@Override
	public int decode() {
		try {
			NewAlarm newalarm = NewAlarm.parseFrom(msgcontent);
			this.slaver = newalarm.getSlaver().trim();
			this.callLetter = newalarm.getCallLetter().trim();
			this.alarmsn = newalarm.getAlarmsn().trim();
			this.alarmtime = newalarm.getAlarmTime();
			this.alarmid = newalarm.getAlarmid();
			this.level = newalarm.getLevel();
			if (newalarm.hasGpsinfo()) {
		        this.gpsinfo = GpsSimpleInfo.newBuilder(newalarm.getGpsinfo()).build();	//复制
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
		if (!ComCenter.hasalarm) {
			//不支持警情队列
			retcode = ResultCode.RefuseAlarm_Error;
			retmsg = "不支持警情分析";
			return;
		}
		
		if (this.callLetter.isEmpty() || this.alarmsn.isEmpty()) {
			retcode = ResultCode.AppendAlarm_Error;
			retmsg = "警情呼号或警情唯一ID号为空";
			return;
		}
		//改变警情状态
		//如果坐席返回OK，则表示坐席开始处理，不能再重新分配, 如果返回失败，再必须再重新分配
		if (!AlarmManager.alarmmanager.appendAlarm(this)) {
			retcode = ResultCode.AppendAlarm_Error;
			retmsg = "添加警情到警情队列失败";
		}
	}

	/*
	 * Master返回应答，表示新警情是否添加到警情队列
	 * 
	 */
	@Override
	public byte[] response() {
		NewAlarm_ACK.Builder newalarmack = NewAlarm_ACK.newBuilder();
		newalarmack.setRetcode(retcode);
		newalarmack.setRetmsg(retmsg);
		newalarmack.setAlarmsn(alarmsn);
		newalarmack.setCallLetter(callLetter);
		newalarmack.setSlaver(slaver);
		return Serialize(MessageType.NewAlarm_ACK, newalarmack.build().toByteString());
	}
}
