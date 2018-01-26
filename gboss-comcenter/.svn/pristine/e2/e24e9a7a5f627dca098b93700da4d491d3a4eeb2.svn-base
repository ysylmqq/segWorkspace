package cc.chinagps.gboss.alarmarray.UdpHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import cc.chinagps.gboss.alarmarray.AlarmManager;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.websocket.ClientBaseHandler;

//import io.netty.buffer.Unpooled;
public class AlarmUDPHandler extends SimpleChannelInboundHandler<DatagramPacket> {
	//public static AlarmUDPHandler UDPHandler = null;
    @Override
    public void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
		try {
			//byte[] content = packet.content().array();	//会抛出异常
        	ByteBuf request = packet.content();
			int len = request.writerIndex();
        	byte[] content = new byte[len];
			packet.content().readBytes(content);
				
        	ComCenterDataBuff.ComCenterMessage messages = ComCenterDataBuff.ComCenterMessage.parseFrom(content);
        	for(ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage msg : messages.getMessagesList()) {
        		ClientBaseHandler handler = UdpHandlerFactory.CreateSeatHandler(msg);
        		//所有事件都分三步处理，如果没有三步，不用overwrite基类
        		if (handler != null) {
        			//先解码
        			int retcode = handler.decode(); 
        			if (retcode == ResultCode.OK) {
	        			handler.run(); 
        	        }
        			//最后编写应答内容
        			byte[] response = handler.response();
        			if (response != null) {
        		        AlarmManager.alarmmanager.multicast(response);
        	        }
        		}
        	}
        } catch(Exception e) {
			e.printStackTrace();
    	}
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //cause.printStackTrace();
    }
}
