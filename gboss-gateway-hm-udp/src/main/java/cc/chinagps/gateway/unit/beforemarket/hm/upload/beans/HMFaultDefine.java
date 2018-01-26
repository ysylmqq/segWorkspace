package cc.chinagps.gateway.unit.beforemarket.hm.upload.beans;

import java.util.ArrayList;
import java.util.List;

public class HMFaultDefine {
	private int faultType;
	
	private List<String> faultCode = new ArrayList<String>();

	public int getFaultType() {
		return faultType;
	}

	public void setFaultType(int faultType) {
		this.faultType = faultType;
	}

	public List<String> getFaultCode() {
		return faultCode;
	}

	public void setFaultCode(List<String> faultCode) {
		this.faultCode = faultCode;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\"faultType\":").append(faultType);
		sb.append(", \"faultCode\":").append(getListString(faultCode));
		sb.append("}");
		
		return sb.toString();
	}
	
	private String getListString(List<String> list){
		if(list == null){
			return "null";
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		if(list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				sb.append("\"").append(list.get(i)).append("\",");
			}
			
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]");
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		HMFaultDefine df = new HMFaultDefine();
		df.setFaultType(123);
		df.getFaultCode().add("P000A00");
		df.getFaultCode().add("P000B00");
		df.getFaultCode().add("P000C00");
		
		System.out.println(df);
	}
}