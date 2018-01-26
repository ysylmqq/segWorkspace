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
package cc.chinagps.gboss.comcenter.outinterface;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import cc.chinagps.lib.util.Util;

public class OutInterfaceNoEncryptEncoder extends MessageToByteEncoder<OutInterfaceInfo> {
	private final boolean compress;		//通信过程中是否压缩
	public OutInterfaceNoEncryptEncoder(boolean bcompress) {
		super();
		compress = bcompress;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, OutInterfaceInfo sendinfo, ByteBuf out) throws Exception {
		//如果长度大于256才压缩
		boolean tmpcompress = this.compress;
		if (sendinfo.body.length < 256) {
			tmpcompress = false;
		}
		if (tmpcompress) {
			sendinfo.body = Util.ZlibCompress(sendinfo.body);
		}

		//生成临时编码缓冲区
		int totallen = sendinfo.body.length + 12;
		byte tmpout[] = new byte[totallen];
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
		tmpout[0] = (byte)0xFE;
		tmpout[1] = (byte)0x10;
		tmpout[2] = (byte)0x00;
		if (tmpcompress) {
			tmpout[2] |= (byte)0x80;
		}
		tmpout[3] = (byte)0x00;
		
		tmpout[4] = (byte) ((totallen >>> 24) & 0xFF);
		tmpout[5] = (byte) ((totallen >>> 16) & 0xFF);
		tmpout[6] = (byte) ((totallen >>>  8) & 0xFF);
		tmpout[7] = (byte) (totallen & 0xFF);
		//计算报文头校验
		byte xor = 0;
		for(int i=0; i<8; i++) {
			xor ^= tmpout[i];
		}
		tmpout[3] = xor;
		//复制内容
		System.arraycopy(sendinfo.body, 0, tmpout, 8, sendinfo.body.length);
		//计算crc32校验
		int crc32 = (int)Util.CRC32C(tmpout, 0, totallen-4);
		tmpout[totallen-4] = (byte) ((crc32 >>> 24) & 0xFF);
		tmpout[totallen-3] = (byte) ((crc32 >>> 16) & 0xFF);
		tmpout[totallen-2] = (byte) ((crc32 >>>  8) & 0xFF);
		tmpout[totallen-1] = (byte) (crc32 & 0xFF);
		
		out.writeBytes(tmpout);
	}
}
