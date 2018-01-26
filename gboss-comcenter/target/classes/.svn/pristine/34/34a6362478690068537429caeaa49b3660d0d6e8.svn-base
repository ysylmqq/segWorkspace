/*
********************************************************************************************
Discription:  Ｍaster应答坐席状态
			  
			  
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
import cc.chinagps.gboss.comcenter.ComCenter;
import cc.chinagps.gboss.comcenter.buff.AlarmDistributeDataBuff.SeatStatus_ACK;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.websocket.ClientBaseHandler;

public class SeatStatusACKHandler extends ClientBaseHandler {
    //private String slaver = "";     //通信中心名称(ID)
    private String seatname = "";   //坐席登录名
    private short statustype = 0;	//坐席状态(1:登录、2:退录、3:忙碌、4:空闲(Slaver通知Master)
    private short allot = 1;

    public SeatStatusACKHandler(ComCenterBaseMessage basemsg) {
		super(basemsg, null);
	}

	@Override
	public int decode() {
		try {
			SeatStatus_ACK statusack = SeatStatus_ACK.parseFrom(msgcontent);
			retcode = statusack.getRetcode();
			if (statusack.hasRetmsg()) {
				retmsg = statusack.getRetmsg();
			}
			//slaver = statusack.getSlaver();
			seatname = statusack.getSeatname();
			statustype = (short)statusack.getStatustype();
			allot = (short)statusack.getAllot();
        } catch(Exception e) {
			e.printStackTrace();
		}
		//不要坐席返回什么，都要执行下一步run
		return ResultCode.OK;
	}

	@Override
	public void run() {
		if (retcode == ResultCode.OK) {
			String strsn = sn(seatname, statustype, allot); 
			AlarmManager.alarmmanager.removeMulticast(strsn,  MessageType.SeatStatus);
		}
	}

	static public String sn(String seatname, short statustype, short allot) {
		return ComCenter.systemname + "_" + seatname + "_" + statustype + "_" + allot; 
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
