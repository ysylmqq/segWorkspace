package com.org.netty.myselfcode.modal;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 自定义编码器:把对象编码成字节数组
 * @author ysy
 *
 */
public class RequestResponseEncode extends MessageToByteEncoder<RequestResponse> {

	@Override
	protected void encode(ChannelHandlerContext ctx, RequestResponse obj,
			ByteBuf buffer) throws Exception {
		buffer.writeInt(obj.getHead());  // 0x01
		buffer.writeShort(obj.getModule()); //模块号
		buffer.writeShort(obj.getCmd()); // 命令
		buffer.writeInt(obj.getLength());
		if( obj.getBody() != null ){
			buffer.writeBytes(obj.getBody());
		}
	}
}
