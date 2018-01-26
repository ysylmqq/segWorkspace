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
package cc.chinagps.gboss.comcenter.interprotocolsocket;


import io.netty.channel.ChannelHandlerContext;
//import java.util.logging.Level;

import cc.chinagps.gboss.alarmarray.AlarmManager;
import cc.chinagps.gboss.alarmarray.SeatClientInfo;
import cc.chinagps.gboss.comcenter.WebsocketClientInfo;
import cc.chinagps.gboss.comcenter.CenterClientManager;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.websocket.ClientBaseHandler;
import cc.chinagps.gboss.comcenter.websocket.ClientHandlerFactory;
import cc.chinagps.interprotocol.Constant;
import cc.chinagps.interprotocol.InterProtocolInfo;
import cc.chinagps.interprotocol.InterProtocolServerHandler;

public class InterProtocolComcenterHandler extends InterProtocolServerHandler {

    protected WebsocketClientInfo clientinfo = null;

    public InterProtocolComcenterHandler() {
    	super();
    	clientinfo = new InterProtocolClientInfo();
    	clientinfo.clienttype1 = "interclient";	//内部协议客户端
    }
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	super.channelActive(ctx);
    	clientinfo.interhandler = this;
    	clientinfo.setClosed(false);
    	clientinfo.ipaddr = ctx.channel().remoteAddress().toString();
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    	super.channelInactive(ctx);
    	clientinfo.setClosed(true);	
    	
    	CenterClientManager.clientManager.removeClient(clientinfo);
		if (clientinfo instanceof SeatClientInfo) {
			SeatClientInfo seat = (SeatClientInfo)clientinfo;
			seat.setZkClosed();
		}
    }

    /*
	 * 判断登录是否成功，用户名和密码是否相同
	 * @see cc.chinagps.interprotocol.InterProtocolServerHandler#OnLogin(java.lang.String, byte[], byte[])
	 */
	@Override
	protected short OnLogin(String username, byte[] stamp,	byte[] encryptpassword) {
    	CenterClientManager.clientManager.addClient(clientinfo);
    	clientinfo.username = username;
		//如果客户端是坐席
		if (clientinfo instanceof SeatClientInfo) {
			SeatClientInfo seatinfo = (SeatClientInfo)clientinfo;
			seatinfo.setstatus(SeatClientInfo.LOGINSTATUS); 
			int retcode = AlarmManager.alarmmanager.appendSeat(seatinfo);
			clientinfo.setLogin(retcode == ResultCode.OK);
			switch(retcode) {
			case ResultCode.SeatExist_Error: 
				break;
			case ResultCode.ComCenter_Error:
				break;
			case ResultCode.ZooKeeper_Error:
				break;
			}
			return (short)(retcode);
		}
		clientinfo.setLogin(true);
		return ResultCode.OK;
	}

	/*
	 * 退录（表示不是临时断开，不再重连, 分配的警情要重新分配） 
	 */
	@Override
	protected void ProcessLogout() {
		
	}
	
    protected ClientBaseHandler CreateMessageHandler(ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage msg) {
		return ClientHandlerFactory.CreateClientHandler(msg, this.clientinfo);
    }
    
	/*
	 *处理业务报文 
	 */
	@Override
	protected void ProcessNormal(InterProtocolInfo recv) {
		//如果是通信中心内部协议的PROTOBUF报文体格式
		//和WEBSOCKET协议处理类似
    	clientinfo.setClosed(false);
		if (recv.msgId == Constant.MESSAGE_ID_COMCENTER_PROTOBUF) {
			try {
	        	ComCenterDataBuff.ComCenterMessage messages = ComCenterDataBuff.ComCenterMessage.parseFrom(recv.content);
	        	for(ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage msg : messages.getMessagesList()) {
	        		ClientBaseHandler handler = CreateMessageHandler(msg);
	        		//所有事件都分三步处理，如果没有三步，不用overwrite基类
	        		if (handler != null) {
	        			//先解码
	        			int retcode = handler.decode(); 
	        			if (retcode == ResultCode.OK) {
	    	        		//if (handler instanceof SendCommandHandler) {
	    	        		//	SendCommandHandler cmdhandler = (SendCommandHandler)handler;
	    	        		//	System.out.println("Interal Send Command: sn=" + cmdhandler.cmdsn + ", cmdid=" + cmdhandler.cmdId + ", callletter count=" + cmdhandler.callletterlist.size());
	    	        		//}
		        			handler.run(); 
	        			//} else {
	        	        //    System.out.println("run failed[" + retcode + "]:" + msg.getId() + ", " + handler.toString());
	        	        }
	        			//最后编写应答内容
	        			byte[] response = handler.response();
	        			if (response != null) {
	        				InterProtocolInfo send = new InterProtocolInfo();
	        				send.msgId = Constant.MESSAGE_ID_COMCENTER_PROTOBUF;
	        				send.sequenceNo = recv.sequenceNo;
	        				send.result = ResultCode.OK;
	        				send.content = response;
	        				this.chctx.writeAndFlush(send);
	        	        }
	        		}
	        	}
            } catch(Exception e) {
    			e.printStackTrace();
        	}
		}
	}
}
