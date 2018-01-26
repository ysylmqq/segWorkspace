package cc.chinagps.gateway.unit.oem.pkg;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.seg.lib.util.Util;

import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.util.HexUtil;

public class OEMPackage {
	private static Logger logger_debug = Logger
			.getLogger(LogManager.LOGGER_NAME_DEBUG);

	private byte[] head = new byte[] { 0x29, 0x29 };

	private byte mainCmdId;// 主信令id

	private short bodyLen;

	private byte[] vip = new byte[4];

	private byte[] data;

	private byte crc;

	private byte tail = (byte) 0x0D;

	private byte[] source;

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{head:").append(HexUtil.byteToHexStr(head).toUpperCase());
		sb.append(", mainCmdId:0x").append(
				HexUtil.toHexStr(mainCmdId).toUpperCase());
		sb.append(", bodyLen:").append(bodyLen);
		sb.append(", vip:").append(HexUtil.byteToHexStr(vip).toUpperCase());
		sb.append(", data:").append(HexUtil.byteToHexStr(data).toUpperCase());
		sb.append(", crc:0x").append(HexUtil.toHexStr(crc).toUpperCase());
		sb.append(", tail:0x").append(HexUtil.toHexStr(tail).toUpperCase());
		sb.append(", source:").append(
				HexUtil.byteToHexStr(source).toUpperCase());
		sb.append("}");

		return sb.toString();
	}

	public byte[] getHead() {
		return head;
	}

	public void setHead(byte[] head) {
		this.head = head;
	}

	public byte getMainCmdId() {
		return mainCmdId;
	}

	public void setMainCmdId(byte mainCmdId) {
		this.mainCmdId = mainCmdId;
	}

	public short getBodyLen() {
		return bodyLen;
	}

	public void setBodyLen(short bodyLen) {
		this.bodyLen = bodyLen;
	}

	public byte[] getVip() {
		return vip;
	}

	public void setVip(byte[] vip) {
		this.vip = vip;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public byte getCrc() {
		return crc;
	}

	public void setCrc(byte crc) {
		this.crc = crc;
	}

	public byte getTail() {
		return tail;
	}

	public void setTail(byte tail) {
		this.tail = tail;
	}

	public byte[] getSource() {
		return source;
	}

	public void setSource(byte[] source) {
		this.source = source;
	}

	public static OEMPackage parse(byte[] source) throws IOException {
		OEMPackage pkg = new OEMPackage();
		byte[] head = new byte[2];
		int pos = 0;
		System.arraycopy(source, 0, head, 0, 2);
		if (head[0] != (byte) 0x29 || head[1] != (byte) 0x29) {
			logger_debug.error("[oem] pkg head error");
			return null;
		}
		byte mainCmdId = source[2];

		byte bodyLenByte[] = new byte[2];
		pos += 3;
		System.arraycopy(source, pos, bodyLenByte, 0, 2);

		short bodyLen = Util.getShort(bodyLenByte);
		if (bodyLen != source.length - 5) {
			logger_debug.error("[oem] pkg len error");
			return null;
		}

		pos += 2;
		byte[] vip = new byte[4];
		System.arraycopy(source, pos, vip, 0, 4);
		pos += 4;
		int dataLen = bodyLen - 1 - 1 - 4;
		byte data[] = new byte[dataLen];

		System.arraycopy(source, pos, data, 0, dataLen);
		byte crc = source[source.length - 2];
		byte xor = 0;
		for (int i = 0; i < source.length - 2; i++) {
			xor ^= source[i];
		}
		if (xor != crc) {
			logger_debug.error("[oem] pkg crc error,original crc byte:"+crc+";computed crc byte:"+xor);
			return null;
		}
		byte tail = source[source.length - 1];
		if (tail != (byte) 0x0D) {
			logger_debug.error("[oem] pkg tail error");
			return null;
		}

		pkg.setBodyLen(bodyLen);
		pkg.setCrc(crc);
		pkg.setData(data);
		pkg.setHead(head);
		pkg.setMainCmdId(mainCmdId);
		pkg.setSource(source);
		pkg.setTail(tail);
		pkg.setVip(vip);

		return pkg;
	}

	public byte[] encode() throws Exception {
		byte[] res = new byte[data.length + 11];

		int pos = 0;
		System.arraycopy(head, 0, res, pos, 2);
		res[2] = mainCmdId;
		pos += 3;
		byte[] bodyLenBytes = Util.getShortByte((short) bodyLen);

		System.arraycopy(bodyLenBytes, 0, res, pos, 2);
		pos += 2;

		System.arraycopy(vip, 0, res, pos, 4);

		pos += 4;
		
		if(data.length!=0){
			System.arraycopy(data, 0, res, pos, data.length);
		}

		byte crc = 0;
		for (int i = 0; i < res.length - 2; i++) {
			crc ^= res[i];
		}

		res[res.length - 1] = tail;
		res[res.length - 2] = crc;

		return res;

	}

	public static void main(String[] args) throws Exception {
		try {
			LogManager.init();
			String hex = "292990001B0CD3C04E080722135517022327571135581000000000E000502B0D";
			//hex = "2929300006c2002d12cb0d";//点名查车
			byte[] data = HexUtil.hexToBytes(hex);
			OEMPackage pkg = OEMPackage.parse(data);
			System.out.println(pkg);

			System.out.println("encode:"
					+ HexUtil.byteToHexStr(pkg.encode()).toUpperCase());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}