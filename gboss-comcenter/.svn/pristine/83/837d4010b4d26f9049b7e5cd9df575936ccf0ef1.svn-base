/*
********************************************************************************************
Discription:  Slave通知坐席状态改变
			  
			  
Written By:   ZXZ
Date:         2014-09-05
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.alarmarray.UdpHandler;

import java.util.List;
import cc.chinagps.gboss.alarmarray.AlarmManager;
import cc.chinagps.gboss.alarmarray.ZooKeeperAlarm;
import cc.chinagps.gboss.comcenter.ComCenter;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.AlarmDistributeDataBuff.SeatStatus;
import cc.chinagps.gboss.comcenter.buff.AlarmDistributeDataBuff.SeatStatus_ACK;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.websocket.ClientBaseHandler;

public class SeatStatusHandler extends ClientBaseHandler {
	
	/**************************************************************************************
	 * 下面是坐席状态常量定义
	 */
    public String slaver = "";      //通信中心名称(ID)
    public String seatname = "";    //坐席登录名
    public String seatid = "";		//操作员ID
    public String clienttype = "";		//客户端类型
    public String clientversion = "";	//客户端版本号
    public String ipaddr = "";			//客户端IP地址
    public String busycallLetter = ""; 	//车辆呼号（忙碌时，可能有）
    public short status = 0;    		//登录忙闲状态(1:登录、2:退录、3:忙碌、4:空闲(Slaver通知Master)
    public short allot = 0;				//警情分配状态	
	public long idlestarttime = 0;		//空闲开始时间, 如果为0表示忙碌
	public boolean closed = false;		//是否关闭
    public List<String> alarmcallLetterlist; //处警呼号(取消挂警后可能有多个)

    public SeatStatusHandler(ComCenterBaseMessage basemsg) {
		super(basemsg, null);
	}

	@Override
	public int decode() {
		try {
			SeatStatus seatstatus = SeatStatus.parseFrom(msgcontent);
			this.slaver = seatstatus.getSlaver();
			this.seatname = seatstatus.getSeatname();
		    this.seatid = seatstatus.getSeatid();
		    this.clienttype = seatstatus.getClienttype();
		    this.clientversion = seatstatus.getClientversion();
		    this.ipaddr = seatstatus.getIpaddr();
		    this.status = (short)(seatstatus.getStatustype());
		    this.allot = (short)(seatstatus.getAllot());	
		    this.idlestarttime = seatstatus.getIdlestarttime();
		    this.closed = seatstatus.getClosed();
		    if (seatstatus.hasBusycallLetter()) {
		    	this.busycallLetter = seatstatus.getBusycallLetter();
		    }
		    alarmcallLetterlist = seatstatus.getAlarmcallLetterList();
        } catch(Exception e) {
			e.printStackTrace();
			retcode = ResultCode.Decode_Error;
			retmsg = "解码失败";
		}
		return retcode;
	}

	@Override
	public void run() {
		//只有不是发送广播的通信中心, 都要执行
		if (slaver.equals(ComCenter.systemname))
			return;
		AlarmManager.alarmmanager.setSeatStatus(this);
	}

	@Override
	public byte[] response() {
		if (ZooKeeperAlarm.zookeeperalarm.isMasterd) {
			SeatStatus_ACK.Builder seatstatusack = SeatStatus_ACK.newBuilder();
			seatstatusack.setRetcode(retcode);
			seatstatusack.setRetmsg(retmsg);
			seatstatusack.setSlaver(slaver);
			seatstatusack.setStatustype(status);
			seatstatusack.setSeatname(seatname);
			seatstatusack.setAllot(allot);
			return Serialize(MessageType.SeatStatus_ACK, seatstatusack.build().toByteString());
		}
		return null;
	}
}
