/*
********************************************************************************************
Discription:  内部协议编码过程
			  
Written By:   ZXZ
Date:         2014-05-13
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.interprotocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import cc.chinagps.lib.util.Util;
import cc.chinagps.interprotocol.InterProtocolInfo;
import cc.chinagps.interprotocol.Constant;

public class InterProtocolEncoder extends MessageToByteEncoder<InterProtocolInfo> {

	private final boolean compress;		//通信过程中是否压缩
	private final boolean encrypt;		//通信过程中是否加密
	
	public InterProtocolEncoder(boolean bcompress, boolean bencrypt) {
		super();
		compress = bcompress;
		encrypt = bencrypt; 
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, InterProtocolInfo in, ByteBuf out) throws Exception {
		//如果长度大于256才压缩
		boolean tmpcompress = this.compress;
		if (in.content.length < 256) {
			tmpcompress = false;
		}
		if (tmpcompress) {
			in.content = Util.ZlibCompress(in.content);
		}

		boolean tmpencrypt = this.encrypt;
		//以下几种报文不加密
		if (tmpencrypt &&
			(in.msgId == Constant.MESSAGE_ID_LOGIN ||
			in.msgId == Constant.MESSAGE_ID_LOGIN_ACK ||
			in.msgId == Constant.MESSAGE_ID_ACTIVETEST ||
			in.msgId == Constant.MESSAGE_ID_ACTIVETEST_ACK)) {
			tmpencrypt = false;
		}
		
		if (tmpencrypt) {
			InterProtocolHandler handler = (InterProtocolHandler) ctx.channel().pipeline().last();
			in.content = Util.encodeDESC(handler.getDESKey(), in.content);
		}
		//生成临时编码缓冲区
		int totallen = in.content.length + 22;
		byte tmpout[] = new byte[totallen];
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
		
		tmpout[0] = Constant.STX;
		tmpout[1] = (byte)0x10;
		tmpout[2] = 0;
 		if (tmpcompress)
			tmpout[2] |= (byte)0x80;
		if (tmpencrypt)
			tmpout[2] |= (byte)0x01;
		tmpout[3] = 0;
		tmpout[4] = (byte) ((in.msgId >>> 24) & 0xFF);
		tmpout[5] = (byte) ((in.msgId >>> 16) & 0xFF);
		tmpout[6] = (byte) ((in.msgId >>>  8) & 0xFF);
		tmpout[7] = (byte) (in.msgId & 0xFF);
		
		tmpout[8] = (byte) ((in.sequenceNo >>> 24) & 0xFF);
		tmpout[9] = (byte) ((in.sequenceNo >>> 16) & 0xFF);
		tmpout[10] = (byte) ((in.sequenceNo >>>  8) & 0xFF);
		tmpout[11] = (byte) (in.sequenceNo & 0xFF);

		tmpout[12] = (byte) ((totallen >>> 24) & 0xFF);
		tmpout[13] = (byte) ((totallen >>> 16) & 0xFF);
		tmpout[14] = (byte) ((totallen >>>  8) & 0xFF);
		tmpout[15] = (byte) (totallen & 0xFF);
		
		tmpout[16] = (byte) ((in.result >>>  8) & 0xFF);
		tmpout[17] = (byte) (in.result & 0xFF);
		
		//计算报文头校验
		byte xor = 0;
		for(int i=0; i<18; i++) {
			xor ^= tmpout[i];
		}
		tmpout[3] = xor;
		//复制内容
		System.arraycopy(in.content, 0, tmpout, 18, in.content.length);
		//计算crc32校验
		int crc32 = (int)Util.CRC32C(tmpout, 0, totallen-4);
		tmpout[totallen-4] = (byte) ((crc32 >>> 24) & 0xFF);
		tmpout[totallen-3] = (byte) ((crc32 >>> 16) & 0xFF);
		tmpout[totallen-2] = (byte) ((crc32 >>>  8) & 0xFF);
		tmpout[totallen-1] = (byte) (crc32 & 0xFF);
		
		out.writeBytes(tmpout);
	}
}
