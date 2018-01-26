package cc.chinagps.gateway.util;

import java.math.BigInteger;

public class ConvertUtil {
	/**
	 * 设备id转伪ip
	 * 
	 * @param deviceId
	 * @return
	 */
	public static String deviceIdToWIP(String deviceId) {
		byte n = 0;
		byte n1 = 0;
		byte n2 = 0;
		byte n3 = 0;
		byte n4 = 0;
		if (deviceId.length() == 11) {
			n = (byte) (Integer.valueOf(deviceId.substring(0, 3)) - 130);
			n1 = Integer.valueOf(deviceId.substring(3, 5)).byteValue();
			n2 = Integer.valueOf(deviceId.substring(5, 7)).byteValue();
			n3 = Integer.valueOf(deviceId.substring(7, 9)).byteValue();
			n4 = Integer.valueOf(deviceId.substring(9, 11)).byteValue();
			if ((n & 0x8) != 0)
				n1 = (byte) (n1 | 0x80);
			if ((n & 0x4) != 0)
				n2 = (byte) (n2 | 0x80);
			if ((n & 0x2) != 0)
				n3 = (byte) (n3 | 0x80);
			if ((n & 0x1) != 0)
				n4 = (byte) (n4 | 0x80);
		}
		StringBuilder sb = new StringBuilder();
		sb.append(HexUtil.toHexStr(n1).length() == 1 ? "0"
				+ HexUtil.toHexStr(n1) : HexUtil.toHexStr(n1));
		sb.append(HexUtil.toHexStr(n2).length() == 1 ? "0"
				+ HexUtil.toHexStr(n2) : HexUtil.toHexStr(n2));
		sb.append(HexUtil.toHexStr(n3).length() == 1 ? "0"
				+ HexUtil.toHexStr(n3) : HexUtil.toHexStr(n3));
		sb.append(HexUtil.toHexStr(n4).length() == 1 ? "0"
				+ HexUtil.toHexStr(n4) : HexUtil.toHexStr(n4));
		return sb.toString().toUpperCase();
	}

	/**
	 * 输入伪IP四个字节0xC2002D12，转为设备id（13866004518）
	 */
	public static String getIMEIFromVip(String vip) {
		byte wip[] = HexUtil.hexToBytes(vip);
		short n = 0;
		byte n1 = wip[0];
		byte n2 = wip[1];
		byte n3 = wip[2];
		byte n4 = wip[3];
		if ((n1 & (byte) (0x80)) != 0) {
			n += 8;
			n1 -= (byte) 0x80;
		}
		if ((n2 & (byte) (0x80)) != 0) {
			n += 4;
			n2 -= (byte) 0x80;
		}
		if ((n3 & (byte) (0x80)) != 0) {
			n += 2;
			n3 -= (byte) 0x80;
		}
		if ((n4 & (byte) (0x80)) != 0) {
			n += 1;
			n4 -= (byte) 0x80;
		}
		StringBuilder sb = new StringBuilder();
		if (n < 10) {
			n += 130;
		} else {
			n += 146;
		}
		sb.append(String.valueOf(n));
		if (n1 < 10) {
			sb.append("0");
		}
		sb.append(String.valueOf(n1));

		if (n2 < 10) {
			sb.append("0");
		}
		sb.append(String.valueOf(n2));

		if (n3 < 10) {
			sb.append("0");
		}
		sb.append(String.valueOf(n3));

		if (n4 < 10) {
			sb.append("0");
		}
		sb.append(String.valueOf(n4));
		return sb.toString();

	}
	
	public String test(String vip){
		int[] wip = HexUtil.hexToIntArr(vip);
		int n1 = wip[0];
		int n2 = wip[1];
		int n3 = wip[2];
		int n4 = wip[3];
		int binaryArr[] = new int[] { 0, 0, 0, 0 };

		if ((n1 & (int) (0x80)) != 0) {
			binaryArr[0] = 1;
			n1 -= 128;
		}
		if ((n2 & (int) (0x80)) != 0) {
			binaryArr[1] = 1;
			n2 -= 128;
		}
		if ((n3 & (int) (0x80)) != 0) {
			binaryArr[2] = 1;
			n3 -= 128;
		}
		if ((n4 & (int) (0x80)) != 0) {
			binaryArr[3] = 1;
			n4 -= 128;
		}

		String s = "";
		for (int i = 0; i < 4; i++) {
			s = s + binaryArr[i];
		}

		BigInteger bigInt = new BigInteger(s, 2);
		Integer ii = Integer.valueOf(bigInt.toString());
		if (ii < 10) {
			ii += 30;
		} else {
			ii += 46;
		}

		String ss = "";
		if (n1 < 10) {
			ss = ss + "0" + n1;
		} else {
			ss = ss + n1;
		}
		if (n2 < 10) {
			ss = ss + "0" + n2;
		} else {
			ss = ss + n2;
		}
		if (n3 < 10) {
			ss = ss + "0" + n3;
		} else {
			ss = ss + n3;
		}
		if (n4 < 10) {
			ss = ss + "0" + n4;
		} else {
			ss = ss + n4;
		}

		return "1" + ii + ss;
	}

	public static void main(String[] args) {
		System.out.println(getIMEIFromVip("00808502"));
		System.out.println(deviceIdToWIP("13600000502"));
	}
}
