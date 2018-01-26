package cc.chinagps.gateway.unit.beforemarket.common.pkg;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.util.HexUtil;

public class BeforeMarketPackageHead {
	private boolean isEncrypt;
	
	private boolean isCompress;
	
	private byte version;
	
	private byte terminalType;
	
	private String terminalId;
	
	private byte msgType;
	
	private short sn;
	
	private int bodyLength;

	public boolean isEncrypt() {
		return isEncrypt;
	}

	public void setEncrypt(boolean isEncrypt) {
		this.isEncrypt = isEncrypt;
	}

	public boolean isCompress() {
		return isCompress;
	}

	public void setCompress(boolean isCompress) {
		this.isCompress = isCompress;
	}

	public byte getVersion() {
		return version;
	}

	public void setVersion(byte version) {
		this.version = version;
	}

	public byte getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(byte terminalType) {
		this.terminalType = terminalType;
	}
	
	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public byte getMsgType() {
		return msgType;
	}

	public void setMsgType(byte msgType) {
		this.msgType = msgType;
	}

	public short getSn() {
		return sn;
	}

	public void setSn(short sn) {
		this.sn = sn;
	}

	public int getBodyLength() {
		return bodyLength;
	}

	public void setBodyLength(int bodyLength) {
		this.bodyLength = bodyLength;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{isEncrypt:").append(isEncrypt);
		sb.append(", isCompress:").append(isCompress);
		sb.append(", version:").append(version);
		sb.append(", terminalType:").append(terminalType);
		sb.append(", terminalId:").append(terminalId);
		sb.append(", msgType:").append(msgType);
		sb.append(", sn:").append(sn);
		sb.append(", bodyLength:").append(bodyLength);
		sb.append("}");
		
		return sb.toString();
	}
	
	public static BeforeMarketPackageHead parse(byte[] headData, int offset){
		BeforeMarketPackageHead head = new BeforeMarketPackageHead();
		byte d0 = headData[offset];
		boolean isEncrypt = (d0 & 0x80) != 0;
		boolean isCompress = (d0 & 0x40) != 0;
		byte version = (byte) (d0 & 0x3F);
		
		head.setEncrypt(isEncrypt);
		head.setCompress(isCompress);
		head.setVersion(version);
		head.setTerminalType(headData[offset + 1]);
		head.setTerminalId(Util.bcd2str(headData, offset + 2, 7));
		head.setMsgType(headData[offset + 9]);
		
		short sn = Util.getShort(headData, offset + 10);
		head.setSn(sn);
		
		int bodyLength = Util.getShort(headData, offset + 12) & 0xFFFF;
		head.setBodyLength(bodyLength);
		
		return head;
	}
	
	public byte[] encode(int bodyLength){
		byte[] data = new byte[14];
		
		byte d0 = (byte) (version & 0x3F);
		if(isEncrypt){
			d0 |= 0x80;
		}
		
		if(isCompress){
			d0 |= 0x40;
		}
		
		data[0] = d0;
		data[1] = terminalType;
		
		byte[] b_terminalId = Util.str2bcd(terminalId);
		System.arraycopy(b_terminalId, 0, data, 2, Math.min(b_terminalId.length, 7));
		
		data[9] = msgType;
		byte[] b_sn = Util.getShortByte(sn);
		System.arraycopy(b_sn, 0, data, 10, 2);
		
		byte[] b_bodyLength = Util.getShortByte((short) bodyLength);
		System.arraycopy(b_bodyLength, 0, data, 12, 2);
		
		return data;
	}
	
	public static void main(String[] args) {
		byte[] headData = new byte[]{(byte) 0xD0, 0x01, 0x11, 0x22, 0x33, 
				0x44, 0x55, 0x66, 0x77, 0x4, 0x00, 0x05, 0x00, 0x06};
		BeforeMarketPackageHead head = BeforeMarketPackageHead.parse(headData, 0);
		System.out.println(head);
		
		byte[] encode = head.encode(6);
		System.out.println(HexUtil.byteToHexStr(encode).toUpperCase());
	}
}