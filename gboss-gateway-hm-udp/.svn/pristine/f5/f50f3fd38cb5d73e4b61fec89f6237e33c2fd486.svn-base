package cc.chinagps.gateway.unit.seg.upload.beans;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Ä£¿é¹ÊÕÏÐÅÏ¢(OBD)
 */
public class SegFaultInfo {
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