package cc.chinagps.gateway.unit.kelx.pkg;

import org.seg.lib.util.Util;

public class KlxAck {
	private short cmdId = (short) 0x8001;
	
	private short receivedCmdId;
	
	private byte receivedSn;
	
	private byte success;

	public short getCmdId() {
		return cmdId;
	}

	public void setCmdId(short cmdId) {
		this.cmdId = cmdId;
	}

	public short getReceivedCmdId() {
		return receivedCmdId;
	}

	public void setReceivedCmdId(short receivedCmdId) {
		this.receivedCmdId = receivedCmdId;
	}

	public byte getReceivedSn() {
		return receivedSn;
	}

	public void setReceivedSn(byte receivedSn) {
		this.receivedSn = receivedSn;
	}

	public byte getSuccess() {
		return success;
	}

	public void setSuccess(byte success) {
		this.success = success;
	}
	
	public byte[] encode(){
		byte[] data = new byte[6];
		byte[] cmdId_bytes = Util.getShortByte(cmdId);
		byte[] receivedCmdId_bytes = Util.getShortByte(receivedCmdId);
		
		System.arraycopy(cmdId_bytes, 0, data, 0, 2);
		System.arraycopy(receivedCmdId_bytes, 0, data, 2, 2);
		data[4] = receivedSn;
		data[5] = success;
		
		return data;
	}
}
