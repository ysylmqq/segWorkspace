/*
********************************************************************************************
Discription:  Master应答新警情
			  
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
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.NewAlarm_ACK;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.websocket.ClientBaseHandler;

public class UDPNewAlarmACKHandler extends ClientBaseHandler {
    //private String slaver = "";   	//Slave服务器名称
    //private String callLetter = ""; //呼号
    private String alarmsn = "";    //警情唯一序列号

    public UDPNewAlarmACKHandler(ComCenterBaseMessage basemsg) {
		super(basemsg, null);
	}

	@Override
	public int decode() {
		try {
			NewAlarm_ACK newalarmack = NewAlarm_ACK.parseFrom(msgcontent);
			retcode = newalarmack.getRetcode();
			if (newalarmack.hasRetmsg()) {
				retmsg = newalarmack.getRetmsg();
			}
			//slaver = newalarmack.getSlaver();
			//callLetter = newalarmack.getCallLetter();
			alarmsn = newalarmack.getAlarmsn();
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
		AlarmManager.alarmmanager.removeMulticast(this.alarmsn, MessageType.NewAlarm);
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
