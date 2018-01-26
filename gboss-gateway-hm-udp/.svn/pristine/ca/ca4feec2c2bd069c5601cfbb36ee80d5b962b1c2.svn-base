package cc.chinagps.gateway.unit.pengaoda.upload.beans;

import java.util.ArrayList;
import java.util.List;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.util.HexUtil;

public class PengAoDaFaultInfo {
	private int bodyLength;
	
	private byte flag; 
	
	private byte faultType;
	
	private List<PengAoDaFaultCode> faultCodeList = new ArrayList<PengAoDaFaultCode>();

	public int getBodyLength() {
		return bodyLength;
	}

	public void setBodyLength(int bodyLength) {
		this.bodyLength = bodyLength;
	}

	public byte getFlag() {
		return flag;
	}

	public void setFlag(byte flag) {
		this.flag = flag;
	}

	public byte getFaultType() {
		return faultType;
	}

	public void setFaultType(byte faultType) {
		this.faultType = faultType;
	}

	public List<PengAoDaFaultCode> getFaultCodeList() {
		return faultCodeList;
	}

	public void setFaultCodeList(List<PengAoDaFaultCode> faultCodeList) {
		this.faultCodeList = faultCodeList;
	}
	
	public static PengAoDaFaultInfo parse(byte[] data, int offset){
		PengAoDaFaultInfo faultInfo = new PengAoDaFaultInfo();
		int position = offset;
		
		int bodyLength = Util.getShort(data, position) & 0xFFFF;
		if(bodyLength == 0){
			return faultInfo;
		}
		
		position += 2;
		faultInfo.setBodyLength(bodyLength);
		
		byte flag = data[position];
		faultInfo.setFlag(flag);
		
		byte faultType = data[position + 1];
		faultInfo.setFaultType(faultType);
		
		int sumBodyLen = 2;
		while(sumBodyLen < bodyLength){
			handleFault(faultInfo, data, position + sumBodyLen);
			sumBodyLen += 3;
		}
		
		return faultInfo;
	}
	
	private static void handleFault(PengAoDaFaultInfo faultInfo, byte[] data, int dataOffset){
		String faultCode = cc.chinagps.gateway.util.Util.getFaultCodeString(data, dataOffset, 2);
		byte faultCodeType = data[dataOffset + 2];
		
		PengAoDaFaultCode code = new PengAoDaFaultCode();
		code.setFaultCode(faultCode);
		code.setFaultCodeType(faultCodeType);
		
		faultInfo.getFaultCodeList().add(code);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{bodyLength:").append(bodyLength);
		sb.append(", flag:0x").append(HexUtil.toHexStr(flag).toUpperCase());
		sb.append(", faultType:0x").append(HexUtil.toHexStr(faultType).toUpperCase());
		sb.append(", faultCodeList:").append(faultCodeList);
		sb.append("}");
		
		return sb.toString();
	}
}