/*
********************************************************************************************
Discription:  坐席请求已经登录的坐席列表
			  
			  
Written By:   ZXZ
Date:         2014-08-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.alarmarray.websocket;

/*
********************************************************************************************
Discription:  坐席转警前，请求全部坐席列表
			  
			  
Written By:   ZXZ
Date:         2014-08-25
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/

import java.util.ArrayList;

import cc.chinagps.gboss.alarmarray.AlarmManager;
import cc.chinagps.gboss.alarmarray.SeatClientInfo;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.AskSeatList;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.AskSeatList_ACK;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.websocket.ClientBaseHandler;

public class AskSeatListHandler extends ClientBaseHandler {

    private String username = "";       //坐席登录名
    private String callLetter = "";     //终端呼号（如果有终端呼号，则表示能处理这终端呼号的坐席）
    private boolean isidle = true;      //是否只返回空闲坐席（true:是(默认), false:全部坐席）
    private boolean noself = true;      //不包括自己（true:是(默认), false:全部坐席）

    public AskSeatListHandler(ComCenterBaseMessage basemsg, SeatClientInfo info) {
		super(basemsg, info);
	}

	@Override
	public int decode() {
		try {
			AskSeatList askseat = AskSeatList.parseFrom(msgcontent);
			username = askseat.getUsername().trim();
			assert username.equals(((SeatClientInfo)clientinfo).username);
			callLetter = askseat.getCallLetter().trim();
			if (askseat.hasIsidle()) {
				isidle = askseat.getIsidle();
			}
			if (askseat.hasNoself()) {
				noself = askseat.getNoself();
			}
        } catch(Exception e) {
			e.printStackTrace();
			retcode = ResultCode.Decode_Error;
			retmsg = "解码失败";
		}
		//不要坐席返回什么，都要执行下一步run
		return retcode;
	}

	@Override
	public void run() {
	}

	/*
	message AskSeatList_ACK {
	    message SeatBaseInfo {
	        required string username = 1;   //坐席登录名
	        required string seatid = 2;     //坐席编号
	        required bool   isidle = 3;     //是否空闲（当时，也许请求转警后就不空闲）
	    };
	    required int32 retcode = 1;         //结果(ResultCode)
	    optional string retmsg = 2;         //结果说明
	    repeated SeatBaseInfo seats = 3;    //坐席列表
	};
	*/
	@Override
	public byte[] response() {
		AskSeatList_ACK.Builder ack = AskSeatList_ACK.newBuilder();
		ack.setRetcode(retcode);
		ack.setRetmsg(retmsg);
		//如果解码成功
		if (retcode == ResultCode.OK) {
			ArrayList<SeatClientInfo> seatlist = AlarmManager.alarmmanager.askSeatList(username, callLetter, isidle, noself);
			//添加坐席列表
			for(SeatClientInfo seat: seatlist) {
				AskSeatList_ACK.SeatBaseInfo.Builder base = ack.addSeatsBuilder();
				base.setUsername(seat.username);
				base.setSeatid(seat.userid);
				base.setIsidle(!seat.isbusy());
			}
		}
		return Serialize(MessageType.AskSeatList_ACK, ack.build().toByteString()); 
	}

}
