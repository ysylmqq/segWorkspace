package cc.chinagps.gateway.unit.leopaard.pkg;

import java.util.Arrays;

import cc.chinagps.gateway.unit.leopaard.define.LeopaardDefines;
import cc.chinagps.gateway.util.HexUtil;

public class LeopaardPackage {
	private LeopaardHead head;

	private byte[] dataUnit;

	private byte xor;

	private byte[] source;

	public LeopaardHead getHead() {
		return head;
	}

	public void setHead(LeopaardHead head) {
		this.head = head;
	}

	public byte[] getDataUnit() {
		return dataUnit;
	}

	public void setDataUnit(byte[] dataUnit) {
		this.dataUnit = dataUnit;
	}

	public byte getXor() {
		return xor;
	}

	public void setXor(byte xor) {
		this.xor = xor;
	}

	public byte[] getSource() {
		return source;
	}

	public void setSource(byte[] source) {
		this.source = source;
	}

	public static LeopaardPackage parse(byte[] source) throws Exception {
		LeopaardPackage leopaardPackage = new LeopaardPackage();
		leopaardPackage.setSource(source);
		LeopaardHead head = new LeopaardHead();
		int pos = 0;
		head = LeopaardHead.parse(source, 0);
		leopaardPackage.setHead(head);
		pos += LeopaardDefines.HEAD_SIZE;
		int dataUnitLen = head.getDataUnitLen();
		if (dataUnitLen != source.length - LeopaardDefines.HEAD_SIZE - 1) {
			throw new Exception("[leopaard] pkg len error");
		}
		byte[] dataUnit = new byte[dataUnitLen];
		System.arraycopy(source, pos, dataUnit, 0, dataUnitLen);
		leopaardPackage.setDataUnit(dataUnit);
		pos += dataUnitLen;

		byte xor = source[pos];
		byte xorCal = 0;
		for (int i = 2; i < source.length - 1; i++) {
			xorCal ^= source[i];
		}
		if (xor != xorCal) {
			throw new Exception("[leopaard] pkg crc error,original crc byte:" + xor + ";computed crc byte:" + xorCal);
		}
		leopaardPackage.setXor(xor);
		return leopaardPackage;
	}

	public byte[] encode() throws Exception {
		if (dataUnit == null) {
			dataUnit = new byte[0];
		}
		byte[] source = new byte[dataUnit.length + LeopaardDefines.HEAD_SIZE + 1];
		int pos = 0;
		byte[] headBytes = head.encode(dataUnit.length);
		System.arraycopy(headBytes, 0, source, pos, headBytes.length);
		pos += LeopaardDefines.HEAD_SIZE;
		if (dataUnit != null) {
			System.arraycopy(dataUnit, 0, source, pos, dataUnit.length);
			pos += dataUnit.length;
		}
		byte xorCal = 0;
		for (int i = 2; i < source.length - 1; i++) {
			xorCal ^= source[i];
		}
		source[pos] = xorCal;
		return source;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LeopaardPackage [head=");
		builder.append(head);
		builder.append(", dataUnit=");
		builder.append(Arrays.toString(dataUnit));
		builder.append(", xor=");
		builder.append(xor);
		builder.append("]");
		return builder.toString();
	}

	public static void main(String[] args) {
		String s = "2e2e06fe89860116627100174186000002002d4c656f70616172642043533900000000000000000000000000000000004150503053472e4c544454423031000025";
		
		try {
			LeopaardPackage leopaardPackage = LeopaardPackage.parse(HexUtil.hexToBytes(s));
			
			System.out.println(leopaardPackage);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}