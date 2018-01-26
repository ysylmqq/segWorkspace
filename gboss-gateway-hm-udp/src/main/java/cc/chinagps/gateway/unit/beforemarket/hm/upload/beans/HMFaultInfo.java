package cc.chinagps.gateway.unit.beforemarket.hm.upload.beans;

import java.util.ArrayList;
import java.util.List;

import org.seg.lib.util.Util;

public class HMFaultInfo {
	/*
	private Date faultTime;
	
	private List<String> faultCodeList = new ArrayList<String>();

	public List<String> getFaultCodeList() {
		return faultCodeList;
	}

	public void addFaultCode(String faultCode) {
		faultCodeList.add(faultCode);
	}

	public Date getFaultTime() {
		return faultTime;
	}

	public void setFaultTime(Date faultTime) {
		this.faultTime = faultTime;
	}

	public static HMFaultInfo parse(byte[] data, int offset){
		HMFaultInfo faultInfo = new HMFaultInfo();
		
		int len = Util.getShort(data, offset) & 0xFFFF;
		int position = offset + 2;
		for(int i = 0; i < len; i++){
			String code = APlanUtil.getCString(data, position, 5);
			faultInfo.addFaultCode(code);
			position += 5;
		}
		
		faultInfo.setFaultTime(new Date());
		return faultInfo;
	}
	
	public static void main(String[] args) {
		String hex = "010200045030323031503034303350303630355031313037";
		byte[] bs = HexUtil.hexToBytes(hex);
		HMFaultInfo f = HMFaultInfo.parse(bs, 2);
		System.out.println(f.getFaultCodeList());
	}
	*/
	
	//数据长度(字节)
	private int dataLength;

	private List<HMFaultDefine> faults = new ArrayList<HMFaultDefine>();

	public int getDataLength() {
		return dataLength;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}
	
	public List<HMFaultDefine> getFaults() {
		return faults;
	}

	public void setFaults(List<HMFaultDefine> faults) {
		this.faults = faults;
	}
	
	public static HMFaultInfo parse(byte[] data, int offset){
//		HMFaultInfo faultInfo = new HMFaultInfo();
//		
//		int len = Util.getShort(data, offset) & 0xFFFF;
//		int position = offset + 2;
//		for(int i = 0; i < len; i++){
//			String code = APlanUtil.getCString(data, position, 5);
//			faultInfo.addFaultCode(code);
//			position += 5;
//		}
//		
//		faultInfo.setFaultTime(new Date());
//		return faultInfo;
		
		HMFaultInfo faultInfo = new HMFaultInfo();
		int len = Util.getShort(data, offset) & 0xFFFF;
		int position = offset + 2;
		
		while(position - offset < len){
			int faultType = data[position ++] & 0xFF;
			short count = (short) (Util.getShort(data, position) & 0xFFFF);
			position += 2;
			
			HMFaultDefine df = new HMFaultDefine();
			df.setFaultType(faultType);
			
			for(int i = 0; i < count; i++){
				String code = cc.chinagps.gateway.util.Util.getFaultCodeString(data, position, 3);
				position += 3;
				
				df.getFaultCode().add(code);
			}
			
			faultInfo.getFaults().add(df);
		}
		
		faultInfo.setDataLength(len);
		return faultInfo;
	}
}