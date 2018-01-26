package cc.chinagps.seat.bean;

import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsBaseInfo;

public class GpsBasicInformation {
	private GpsBaseInfo baseInfo;
	private String referPosition;
	private String status;
	public GpsBaseInfo getBaseInfo() {
		return baseInfo;
	}
	public String getReferPosition() {
		return referPosition;
	}
	public String getStatus() {
		return status;
	}
	public void setBaseInfo(GpsBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}
	public void setReferPosition(String referPosition) {
		this.referPosition = referPosition;
	}
	public void setStatus(String[] status) {
		StringBuilder sBuilder = new StringBuilder();
		for (String tmp : status) {
			sBuilder.append(tmp).append(",");
		}
		if (sBuilder.length() > 0) {
			sBuilder.deleteCharAt(sBuilder.length() - 1);
		}
		this.status = sBuilder.toString();
	}
}
