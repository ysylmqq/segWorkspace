package cc.chinagps.gateway.unit.beans;

public class UnitInfo {
	private String callLetter;
	
	private String IMEI;
	
	public String getIMEI() {
		return IMEI;
	}

	public void setIMEI(String iMEI) {
		IMEI = iMEI;
	}

	private long lastUpdateRouteTime;

	public long getLastUpdateRouteTime() {
		return lastUpdateRouteTime;
	}

	public void setLastUpdateRouteTime(long lastUpdateRouteTime) {
		this.lastUpdateRouteTime = lastUpdateRouteTime;
	}

	public String getCallLetter() {
		return callLetter;
	}

	public void setCallLetter(String callLetter) {
		this.callLetter = callLetter;
	}
}