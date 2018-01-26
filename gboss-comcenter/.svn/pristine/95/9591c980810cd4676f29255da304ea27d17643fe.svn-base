/*
********************************************************************************************
Discription:  处理坐席websocket请求，继承于App的websocket请求，只处理和警情相关的报文
			  
			  
Written By:   ZXZ
Date:         2014-04-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.alarmarray.websocket;

import io.netty.channel.ChannelHandlerContext;

import cc.chinagps.gboss.alarmarray.AlarmManager;
import cc.chinagps.gboss.alarmarray.SeatClientInfo;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff;
import cc.chinagps.gboss.comcenter.websocket.WebSocketServerHandler;
import cc.chinagps.gboss.comcenter.websocket.ClientBaseHandler;

public class SeatWebSocketServerHandler extends WebSocketServerHandler {

	public SeatWebSocketServerHandler() {
		super();
		clientinfo = new SeatClientInfo();		//重构为坐席信息
    	clientinfo.clienttype1 = "seatclient";	//大平台坐席
	}

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	super.channelActive(ctx);
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
