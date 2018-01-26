/*
********************************************************************************************
Discription:  内容协议服务端接收报文处理
			  
			  
Written By:   ZXZ
Date:         2014-04-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.alarmarray.interprotocolsocket;


import io.netty.channel.ChannelHandlerContext;
import cc.chinagps.gboss.alarmarray.AlarmManager;
import cc.chinagps.gboss.alarmarray.SeatClientInfo;
import cc.chinagps.gboss.alarmarray.websocket.SeatHandlerFactory;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff;
import cc.chinagps.gboss.comcenter.interprotocolsocket.InterProtocolComcenterHandler;
import cc.chinagps.gboss.comcenter.websocket.ClientBaseHandler;

public class SeatClientServerHandler extends InterProtocolComcenterHandler {

	public SeatClientServerHandler() {
		super();
		clientinfo = new SeatClientInfo();		//重构为坐席信息
    	clientinfo.clienttype1 = "interseat";   //内部协议坐席(标准版坐席)
    	clientinfo.setLogin(true);
	}

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    	super.channelInactive(ctx);
    	//断开时，删除此坐席
    	AlarmManager.alarmmanager.closeSeat((SeatClientInfo)(clientinfo));
    }

    @Override
    protected ClientBaseHandler CreateMessageHandler(ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage msg) {
    	ClientBaseHandler handler = super.CreateMessageHandler(msg);
		//如果不是一般客户端事件，则继续判断是否是坐席事件
		if (handler == null) {
	    	handler = SeatHandlerFactory.CreateSeatHandler(msg, (SeatClientInfo)clientinfo);
		}
		return handler;
    }
}
