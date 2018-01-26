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
package cc.chinagps.gboss.comcenter.outinterface;

import java.util.List;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import cc.chinagps.lib.util.Util;

public class OutInterfaceDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		//报文头和报文尾长度至少22字节
		int len = in.readableBytes(); 
        if (len < 12) {
        	//没读出来一直在循环
            //in.resetReaderIndex();
            return;
        }
        //读报文头
        /*报文头
		typedef struct _SEGINTERFACEHEADER
		{
			BYTE STX;		    //开始符=0xFE
			BYTE Version;	    //版本号(1.0)
			BYTE Encrypt;	    //压缩加密标志,B7位表示是否压缩(0:不, 1:压缩),最高位, B0位表示是否加密(0:不, 1:加密),最低位
			BYTE XOR;		    //包头异或校验
			NUINT32 Length;	    //报文总长度(包括报文头、体、CRC32校验尾, 压缩后的总长度)
		}SEGINTERFACEHEADER, *PSEGINTERFACEHEADER;
        */
        byte header[] = new byte[8];
        in.resetReaderIndex();
        in.readBytes(header);
        //计算报文头校验
        byte stx = 0;
        for(int i=0; i<8; i++) {
        	stx ^= header[i];
        }
        if (stx != 0) {	//包头异或校验错
        	in.clear();
        	ctx.close();
        	return;
        }
        
        //没有接收完
        int totallen = Util.getInt(header, 4); 
       	if (totallen > len) {
            in.resetReaderIndex();
       		return;
       	}
        byte body[] = new byte[totallen - 12];
        in.readBytes(body);
        //读crc值
        int crc32s = in.readInt();
		in.markReaderIndex();
        //计算crc32值
        int crc32c = (int)Util.CRC32C(header);
        crc32c = (int)Util.CRC32C(body, crc32c);
        if (crc32c != crc32s) {	//crc校验错误
        	in.clear();
        	ctx.close();
        	return;
        }
       	boolean encrypt = ((header[2] & 0x01) != 0);	//最低位为1
        if (encrypt) {
        	OutInterfaceHandler channelhandler = (OutInterfaceHandler)ctx.channel().pipeline().last();
        	body = Util.decodeDESC(channelhandler.DESKey, body);
        }
       	boolean compress = (header[2] < 0);	//最高位为1
        if (compress) {
        	body = Util.ZlibDecompress(body);
        }
        out.add(body);
	}
}
