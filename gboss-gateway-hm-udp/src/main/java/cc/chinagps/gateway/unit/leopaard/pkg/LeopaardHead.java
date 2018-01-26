package cc.chinagps.gateway.unit.leopaard.pkg;

import java.util.Arrays;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.unit.leopaard.define.LeopaardDefines;

public class LeopaardHead {
	private byte[] startFlag = LeopaardDefines.HEAD_STX;

	private byte cmdId;

	private byte responseFlag;

	private String ic;// 识别码

	private byte encryptFlag;// 加密标识

	private short serialNo;// 流水号

	private int dataUnitLen;

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

	public byte getCmdId() {
		return cmdId;
	}

	public void setCmdId(byte cmdId) {
		this.cmdId = cmdId;
	}

	public byte getResponseFlag() {
		return responseFlag;
	}

	public void setResponseFlag(byte responseFlag) {
		this.responseFlag = responseFlag;
	}

	public String getIc() {
		return ic;
	}

	public void setIc(String ic) {
		this.ic = ic;
	}

	public byte getEncryptFlag() {
		return encryptFlag;
	}

	public void setEncryptFlag(byte encryptFlag) {
		this.encryptFlag = encryptFlag;
	}

	public int getDataUnitLen() {
		return dataUnitLen;
	}

	public void setDataUnitLen(int dataUnitLen) {
		this.dataUnitLen = dataUnitLen;
	}

	public static LeopaardHead parse(byte[] source, int offset) throws Exception {
		LeopaardHead head = new LeopaardHead();
		int position = 0;
		position += offset;
		if (source[0] != LeopaardDefines.HEAD_STX[0] || source[1] != LeopaardDefines.HEAD_STX[1]) {
			throw new Exception("[Leopaard] pkg head stx error");
		}

		byte[] stx = new byte[2];
		System.arraycopy(source, 0, stx, 0, stx.length);
		head.setStartFlag(stx);
		position += 2;

		byte cmdId = source[position];
		head.setCmdId(cmdId);
		position++;

		byte responseFlag = source[position];
		head.setResponseFlag(responseFlag);
		position++;

		byte[] icBytes = new byte[10];
		System.arraycopy(source, position, icBytes, 0, icBytes.length);
		head.setIc(Util.bcd2str(icBytes));
		
		position += icBytes.length;

		byte encryptFlag = source[position];
		head.setEncryptFlag(encryptFlag);
		position++;

		short serialNo = Util.getShort(source, position);
		head.setSerialNo(serialNo);
		position += 2;

		int dataUnitLen = Util.getShort(source, position);
		head.setDataUnitLen(dataUnitLen);

		return head;
	}

	public byte[] encode(int dataLen) throws Exception {
		byte[] data = new byte[LeopaardDefines.HEAD_SIZE];
		int pos = 0;
		System.arraycopy(startFlag, 0, data, pos, startFlag.length);
		pos += startFlag.length;

		data[pos] = cmdId;
		pos++;

		data[pos] = responseFlag;
		pos++;

		byte[] icBytes = Util.str2bcd(ic);
		System.arraycopy(icBytes, 0, data, pos, icBytes.length);
		pos += icBytes.length;

		data[pos] = encryptFlag;
		pos++;
		
		byte[] serialNoBytes = Util.getShortByte(serialNo);
		System.arraycopy(serialNoBytes, 0, data, pos, serialNoBytes.length);
		pos+=2;

		byte[] dataUnitLenBytes = Util.getShortByte((short) dataLen);
		System.arraycopy(dataUnitLenBytes, 0, data, pos, dataUnitLenBytes.length);
		pos+=2;
		
		return data;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LeopaardHead [startFlag=");
		builder.append(Arrays.toString(startFlag));
		builder.append(", cmdId=");
		builder.append(cmdId);
		builder.append(", responseFlag=");
		builder.append(responseFlag);
		builder.append(", ic=");
		builder.append(ic);
		builder.append(", encryptFlag=");
		builder.append(encryptFlag);
		builder.append(", dataUnitLen=");
		builder.append(dataUnitLen);
		builder.append("]");
		return builder.toString();
	}
}
