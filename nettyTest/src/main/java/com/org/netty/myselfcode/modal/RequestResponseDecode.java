package com.org.netty.myselfcode.modal;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 讲字节码转换为对象
 * @author ysy
 *
 */
public class RequestResponseDecode extends ByteToMessageDecoder {

	public static int BASE_INT = 12; // 数据包当中  最小的数据包长度 4+2+2+4
	
	/**
	 * ByteBuf是netty创建的,netty会自动释放  release 使用的是池对象还是非池对象，可以设置
	 */
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buffer,
			List<Object> list) throws Exception {
		
		System.out.println("decode=="+Thread.currentThread().getName());
		
		//刻度长度必须大于基本长度
		if( buffer.readableBytes() >= BASE_INT){
			
			//防止body数据过长 产生socket攻击 数据包大于1k 就丢弃
			if(buffer.readableBytes() > 1024){
				buffer.skipBytes(buffer.readableBytes());
			}
			//说明是数据包的开始
			while(true){
				//对readerIndex进行标记
				buffer.markReaderIndex();
				
				if( buffer.readInt() == Constant.HEAD ){
					//直到头部来完了，才跳出，否则一直进行等待
					break;
				}
				//没有找到头，一个字节一个字节的找
				buffer.resetReaderIndex();
				buffer.readByte();
				//长度不够基本长度之后，等待
				if( buffer.readableBytes() < BASE_INT){
					return ;
				}
			}
			
			//buffer当中可以读取的开始位置
			int beginIndex = buffer.readerIndex();
			
			short module = buffer.readShort();
			short cmd = buffer.readShort();
			int bodyLengh = buffer.readInt(); 
			
			// body当中的数据还没有来完，回复readindex的位置 ,等待
			if(buffer.readableBytes() < bodyLengh){
				buffer.readerIndex(beginIndex);
			}else {
				byte[] body = new byte[bodyLengh];
				buffer.readBytes(body); // 从buffer当中读取bodyLenth长度个字节的数据
				
				RequestResponse requestResponse = new RequestResponse();
				requestResponse.setHead(Constant.HEAD);
				requestResponse.setModule(module);
				requestResponse.setCmd(cmd);
				requestResponse.setLength(bodyLengh);
				requestResponse.setBody(body);
				list.add(requestResponse); // 服务器没有读取的数据，是因为没有添加这个
			}
		}
	}
 
}
