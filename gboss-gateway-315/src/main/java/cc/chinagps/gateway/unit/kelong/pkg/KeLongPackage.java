package cc.chinagps.gateway.unit.kelong.pkg;

import java.util.Arrays;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.unit.kelong.define.KeLongDefines;
import cc.chinagps.gateway.util.HexUtil;

public class KeLongPackage {
	private KeLongHead head;

	private byte[] data;

	private short crc16;

	private byte[] source;

	public KeLongHead getHead() {
		return head;
	}

	public void setHead(KeLongHead head) {
		this.head = head;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public short getCrc16() {
		return crc16;
	}

	public void setCrc16(short crc16) {
		this.crc16 = crc16;
	}

	public byte[] getSource() {
		return source;
	}

	public void setSource(byte[] source) {
		this.source = source;
	}

	public static KeLongPackage parse(byte[] source) throws Exception {
		KeLongPackage keLongPackage = new KeLongPackage();
		keLongPackage.setSource(source);
		KeLongHead head = new KeLongHead();
		int pos = 0;
		head = KeLongHead.parse(source, 0);
		keLongPackage.setHead(head);
		pos += KeLongDefines.HEAD_SIZE;
		int dataLen = head.getDataLen();
		if (dataLen != source.length - KeLongDefines.HEAD_SIZE - 2) {
			throw new Exception("[KeLong] pkg len error");
		}
		byte[] data = new byte[dataLen];
		System.arraycopy(source, pos, data, 0, dataLen);
		keLongPackage.setData(data);
		pos += dataLen;

		Short crc16 = Util.getShort(source, pos);
		pos += 2;

		byte[] toCrcByte = new byte[source.length - 2];
		System.arraycopy(source, 0, toCrcByte, 0, toCrcByte.length);
		short crcCal = calCrc16(toCrcByte);

		if (crc16 != crcCal) {
			throw new Exception("[KeLong] pkg crc16 error,original crc :" + crc16 + ";computed crc :" + crcCal);
		}
		keLongPackage.setCrc16(crc16);
		return keLongPackage;
	}

	private static short calCrc16(byte[] msg) {
		short crc = (short) 0xFFFF;
		int i, j;
		boolean c15, bit;
		for (i = 0; i < msg.length; i++) {
			for (j = 0; j < 8; j++) {
				c15 = ((crc >> 15 & 1) == 1);
				bit = ((msg[i] >> (7 - j) & 1) == 1);
				crc <<= 1;
				if (c15 ^ bit)
					crc ^= 0x1021;
			}
		}
		return crc;
	}

	public byte[] encode() throws Exception {
		if (data == null) {
			data = new byte[0];
		}
		byte[] source = new byte[data.length + KeLongDefines.HEAD_SIZE + 2];
		int pos = 0;
		byte[] headBytes = head.encode(data.length);
		System.arraycopy(headBytes, 0, source, pos, headBytes.length);
		pos += KeLongDefines.HEAD_SIZE;
		if (data != null) {
			System.arraycopy(data, 0, source, pos, data.length);
			pos += data.length;
		}

		byte[] toCrcByte = new byte[source.length - 2];
		System.arraycopy(source, 0, toCrcByte, 0, toCrcByte.length);

		short crc16 = calCrc16(toCrcByte);
		System.arraycopy(Util.getShortByte(crc16), 0, source, pos, 2);
		pos+=2;
		return source;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("KeLongPackage [head=");
		builder.append(head);
		builder.append(", data=");
		builder.append(Arrays.toString(data));
		builder.append(", crc16=");
		builder.append(crc16);
		builder.append(", source=");
		builder.append(Arrays.toString(source));
		builder.append("]");
		return builder.toString();
	}

}