package cc.chinagps.seat.bean;

import java.util.HashMap;
import java.util.Map;

public class ReportCommonResponse {
	private long recordsTotal;
	private long recordsFiltered;
	public long getRecordsTotal() {
		return recordsTotal;
	}
	public long getRecordsFiltered() {
		return recordsFiltered;
	}
	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	public void setRecordsFiltered(long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
	public Map<String, Long> getCommonRespMap() {
		Map<String, Long> commonRespMap = new HashMap<String, Long>(2);
		commonRespMap.put("recordsTotal", getRecordsTotal());
		commonRespMap.put("recordsFiltered", getRecordsFiltered());
		return commonRespMap;
	}
}
