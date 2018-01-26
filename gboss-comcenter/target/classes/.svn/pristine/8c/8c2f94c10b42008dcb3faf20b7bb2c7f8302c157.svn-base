/*
********************************************************************************************
Discription:  服务器转警给某坐席, 坐席应答是否接受转警(一般必须接受)
			  
			  
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
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.AllotTransferAlarm_ACK;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.websocket.ClientBaseHandler;

public class AllotTransferAlarmACKHandler extends ClientBaseHandler {

    public String username = "";   		//坐席登录名
    public String callLetter = ""; 		//终端呼号
    public String alarmsn = "";    		//警情唯一序列号
    public String srcusername = "";    	//转出坐席登录名
    public String slaver = "";			//应用程序或通信中心(Slaver)名称, 分布式通信中心用

    public AllotTransferAlarmACKHandler(ComCenterBaseMessage basemsg, SeatClientInfo info) {
		super(basemsg, info);
	}

	@Override
	public int decode() {
		try {
			AllotTransferAlarm_ACK alloctransack = AllotTransferAlarm_ACK.parseFrom(msgcontent);
			retcode = alloctransack.getRetcode();
			if (alloctransack.hasRetmsg()) {
				retmsg = alloctransack.getRetmsg();
			}
			username = alloctransack.getUsername().trim();
			callLetter = alloctransack.getCallLetter().trim();
			alarmsn = alloctransack.getAlarmsn().trim();
			srcusername = alloctransack.getSrcusername().trim();
			if (alloctransack.hasSlaver()) {
				slaver = alloctransack.getSlaver().trim();
			}
        } catch(Exception e) {
			e.printStackTrace();
			retcode = ResultCode.Decode_Error;
			retmsg = "解码失败";
		}
		return ResultCode.OK;
	}

	@Override
	public void run() {
		System.out.println("AllotTransferAlarmACK: " + alarmsn + ", " + callLetter + ", " + username + " from " + srcusername);
		AlarmManager.alarmmanager.allotTransferAlarmACK(this);
	}

	@Override
	public byte[] response() {
		//不用应答
		return null;
	}

}
