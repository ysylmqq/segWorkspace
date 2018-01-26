package cc.chinagps.gateway.unit.kelx.upload.beans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cc.chinagps.gateway.unit.kelx.util.KlxPkgUtil;

public class KlxFaultInfo {
	private Date faultTime;
	
	private List<String> faultCode;

	public Date getFaultTime() {
		return faultTime;
	}

	public void setFaultTime(Date faultTime) {
		this.faultTime = faultTime;
	}

	public List<String> getFaultCode() {
		return faultCode;
	}

	public void setFaultCode(List<String> faultCode) {
		this.faultCode = faultCode;
	}
	
	public static KlxFaultInfo parse(byte[] data, int offset) throws Exception{
		KlxFaultInfo faultInfo = new KlxFaultInfo();
		faultInfo.setFaultTime(new Date());
		
		List<String> codeList = new ArrayList<String>();
		faultInfo.setFaultCode(codeList);
		
		int position = offset;
		int count = data[position++] & 0xFF;
		for(int i = 0; i < count; i++){
			String code = new String(data, position, 5, KlxPkgUtil.EN_CHAR_ENCODING);
			position += 5;
			
			codeList.add(code);
		}
		
		return faultInfo;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuilder sb = new StringBuilder();
		sb.append("{faultTime:").append(sdf.format(faultTime));
		sb.append(", faultCode:").append(faultCode);
		sb.append("}");
		
		return sb.toString();
	}
}
