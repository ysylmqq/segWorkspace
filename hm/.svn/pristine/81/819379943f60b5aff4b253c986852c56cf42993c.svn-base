/*
********************************************************************************************
Discription:  大平台内部协议，解码
			  
			  
Written By:   ZXZ
Date:         2014-04-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.interprotocol;

import java.util.List;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import cc.chinagps.lib.util.Util;

public class InterProtocolDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		//报文头和报文尾长度至少22字节
		int len = in.readableBytes(); 
        if (len < 22) {
        	//不够报文头和校验尾
            //in.resetReaderIndex();
            return;
        }
        /*报文头
        typedef struct _SEGPROTOCOLHEADER
        {
        	BYTE STX;		    //开始符=0xFE
        	BYTE Version;	    //版本号(1.0)
        	BYTE Encrypt;	    //B7位表示是否压缩(0:不, 1:压缩),最高位
                                //B6位表示节点编号是否包含节点编号(0:不包含，1:包含)
                                //B0位表示是否加密(0:不, 1:加密),最低位
        	BYTE XOR;		    //包头异或校验
            NUINT32 MSGID;      //消息类型
        	NUINT32 SequenceNo;	//流水号
        	NUINT32 Length;	    //报文总长度(包括报文头、体、CRC32校验尾, 压缩后的总长度)
        	NUINT16 Result;		//返回值(或者状态)
        }SEGPROTOCOLHEADER;
        */
        //读报文头
        byte header[] = new byte[18];
        in.resetReaderIndex();
        in.readBytes(header);
        //计算报文头校验
        byte stx = 0;
        for(int i=0; i<18; i++) {
        	stx ^= header[i];
        }
        if (stx != 0) {	//包头异或校验错
        	in.clear();
        	ctx.close();
        	return;
        }
        
        //没有接收完
        int totallen = Util.getInt(header, 12); 
       	if (totallen > len) {
            in.resetReaderIndex();
       		return;
       	}
        InterProtocolInfo info = new InterProtocolInfo();
        info.version = header[1];	//VERSO——
       	info.msgId = Util.getInt(header, 4);
       	info.sequenceNo = Util.getInt(header, 8);
        info.result = Util.getShort(header, 16);
        info.content = new byte[totallen - 22];
        in.readBytes(info.content);
        //读crc值
        int crc32s = in.readInt();
		in.markReaderIndex();
        //计算crc32值
        int crc32c = (int)Util.CRC32C(header);
        crc32c = (int)Util.CRC32C(info.content, crc32c);
        if (crc32c != crc32s) {	//crc校验错误
        	in.clear();
        	ctx.close();
        	return;
        }
       	boolean encrypt = ((header[2] & 0x01) != 0);	//最低位为1
        if (encrypt) {
        	InterProtocolHandler channelhandler = (InterProtocolHandler)ctx.channel().pipeline().last();
        	info.content = Util.decodeDESC(channelhandler.DESKey, info.content);
        }
       	boolean compress = (header[2] < 0);	//最高位为1
        if (compress) {
        	info.content = Util.ZlibDecompress(info.content);
        }
        out.add(info);
	}
}
