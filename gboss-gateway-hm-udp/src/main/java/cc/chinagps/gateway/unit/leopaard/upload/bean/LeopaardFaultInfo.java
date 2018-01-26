package cc.chinagps.gateway.unit.leopaard.upload.bean;

import java.util.ArrayList;
import java.util.List;

import org.seg.lib.util.Util;

public class LeopaardFaultInfo {
	
	//数据长度(字节)
	private int dataLength;

	private List<LeopaardFaultDefine> faults = new ArrayList<LeopaardFaultDefine>();

	public int getDataLength() {
		return dataLength;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}
	
	public List<LeopaardFaultDefine> getFaults() {
		return faults;
	}

	public void setFaults(List<LeopaardFaultDefine> faults) {
		this.faults = faults;
	}
	
	public static LeopaardFaultInfo parse(byte[] data, int offset){
		
		LeopaardFaultInfo faultInfo = new LeopaardFaultInfo();
		int len = Util.getShort(data, offset) & 0xFFFF;
		int position = offset + 2;
		
		while(position - offset < len){
			int faultType = data[position ++] & 0xFF;
			short count = (short) (Util.getShort(data, position) & 0xFFFF);
			position += 2;
			
			LeopaardFaultDefine df = new LeopaardFaultDefine();
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LeopaardFaultInfo [dataLength=");
		builder.append(dataLength);
		builder.append(", faults=");
		builder.append(faults);
		builder.append("]");
		return builder.toString();
	}
	
}