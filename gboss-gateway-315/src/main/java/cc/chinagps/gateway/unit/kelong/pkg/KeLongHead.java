package cc.chinagps.gateway.unit.kelong.pkg;

import java.util.Arrays;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.unit.kelong.define.KeLongDefines;

public class KeLongHead {
	private byte[] startFlag = KeLongDefines.HEAD_STX;

	private short msgLen;

	private short dataLen;

	private short serialNo;// Á÷Ë®ºÅ

	private String deviceId;

	private short msgId;

	public short getDataLen() {
		return dataLen;
	}

	public void setDataLen(short dataLen) {
		this.dataLen = dataLen;
	}

	public short getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(short serialNo) {
		this.serialNo = serialNo;
	}

	public byte[] getStartFlag() {
		return startFlag;
	}

	public void setStartFlag(byte[] startFlag) {
		this.startFlag = startFlag;
	}

	public short getMsgLen() {
		return msgLen;
	}

	public void setMsgLen(short msgLen) {
		this.msgLen = msgLen;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public short getMsgId() {
		return msgId;
	}

	public void setMsgId(short msgId) {
		this.msgId = msgId;
	}

	public static KeLongHead parse(byte[] source, int offset) throws Exception {
		KeLongHead head = new KeLongHead();
		int position = 0;
		position += offset;
		if (source[0] != KeLongDefines.HEAD_STX[0] || source[1] != KeLongDefines.HEAD_STX[1]) {
			throw new Exception("[KeLong] pkg head stx error");
		}

		byte[] stx = new byte[2];
		System.arraycopy(source, 0, stx, 0, stx.length);
		head.setStartFlag(stx);
		position += 2;

		short msgLen = Util.getShort(source, position);
		head.setMsgLen(msgLen);
		head.setDataLen((short) (msgLen - 11));
		position += 2;

		short serialNo = Util.getShort(source, position);
		head.setSerialNo(serialNo);
		position += 2;

		byte[] deviceIdByte = new byte[7];
		System.arraycopy(source, position, deviceIdByte, 0, deviceIdByte.length);
		String type = new String(new byte[] { deviceIdByte[0] }, KeLongDefines.CHARSET);
		byte bcd[] = new byte[6];
		System.arraycopy(deviceIdByte, 1, bcd, 0, bcd.length);
		String id = type + Util.bcd2str(bcd, 0, 6);
		head.setDeviceId(id);
		position += 7;

		short msgId = Util.getShort(source, position);
		head.setMsgId(msgId);
		position += 2;

		return head;
	}

	public byte[] encode(int dataLen) throws Exception {
		byte[] data = new byte[KeLongDefines.HEAD_SIZE];
		int pos = 0;
		System.arraycopy(startFlag, 0, data, pos, startFlag.length);
		pos += startFlag.length;

		byte[] msgLenByte = Util.getShortByte((short) (dataLen + 11));
		System.arraycopy(msgLenByte, 0, data, pos, msgLenByte.length);
		pos += 2;

		byte[] serialNoByte = Util.getShortByte(serialNo);
		System.arraycopy(serialNoByte, 0, data, pos, serialNoByte.length);
		pos += 2;

		byte type = deviceId.substring(0, 1).getBytes(KeLongDefines.CHARSET)[0];
		data[pos++] = type;
		byte idBytes[] = Util.str2bcd(deviceId.substring(1, 13));
		System.arraycopy(idBytes, 0, data, pos, idBytes.length);
		pos += 6;

		byte[] msgIdByte = Util.getShortByte(msgId);
		System.arraycopy(msgIdByte, 0, data, pos, msgIdByte.length);
		pos += 2;

		return data;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("KeLongHead [startFlag=");
		builder.append(Arrays.toString(startFlag));
		builder.append(", msgLen=");
		builder.append(msgLen);
		builder.append(", serialNo=");
		builder.append(serialNo);
		builder.append(", deviceId=");
		builder.append(deviceId);
		builder.append(", msgId=");
		builder.append(msgId);
		builder.append("]");
		return builder.toString();
	}
}
