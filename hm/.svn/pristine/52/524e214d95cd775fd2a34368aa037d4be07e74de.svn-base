/*
********************************************************************************************
Discription:  内容协议客户端初始化
			  
			  
Written By:   ZXZ
Date:         2014-04-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.interprotocol.clienttest;

import com.gboss.comm.SystemConst;

import cc.chinagps.gboss.comcenter.interprotocol.clienttest.CenterClientHandler;
import cc.chinagps.interprotocol.InterProtocolDecoder;
import cc.chinagps.interprotocol.InterProtocolEncoder;
import cc.chinagps.interprotocol.InterProtocolTimeoutHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class CenterClientInitializer extends ChannelInitializer<SocketChannel> {

	
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //添加内部解码（解密、解压缩）协议
        pipeline.addLast("decoder", new InterProtocolDecoder());
        //添加内容编码（压缩、加密）协议
        pipeline.addLast("encoder", new InterProtocolEncoder(true, true));
        //添加超时发送链路检测报文
    	//20秒没收到数据，断开连接,要放在加解密后。表示没有收到内部协议报文, 超时发送链路检测报文
        pipeline.addLast("readTimeoutHandler", new InterProtocolTimeoutHandler(20));
        
        SystemConst.clienthandler = new CenterClientHandler();
        
        //下面四项要从配置文件读
        SystemConst.clienthandler.setNodeId("0010");   		//自己的节点ID
        SystemConst.clienthandler.setUserName("seggps");	//登录名
        SystemConst.clienthandler.setPassword("123456");	//密码
        SystemConst.clienthandler.setServerNodeId("0001");	//服务器端节点ID

        
        pipeline.addLast( SystemConst.clienthandler);
	}
}
