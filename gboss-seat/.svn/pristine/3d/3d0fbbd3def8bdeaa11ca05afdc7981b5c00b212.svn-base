package cc.chinagps.seat.bean;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cc.chinagps.seat.util.Consts;

import com.googlecode.jsonplugin.annotations.JSON;

public class ReportResponseRatio extends ReportCommon {
	private Date beginTime;
	private Date endTime;
	private long sum;
	private Map<String, Long> statItems = new HashMap<String, Long>();
	@JSON(format = Consts.DATE_FORMAT_PATTERN)
	public Date getBeginTime() {
		return beginTime;
	}
	@JSON(format = Consts.DATE_FORMAT_PATTERN)
	public Date getEndTime() {
		return endTime;
	}
	public long getSum() {
		return sum;
	}
	public Map<String, Long> getStatItems() {
		return statItems;
	}
	public Long getStatItem(String name) {
		return this.statItems.get(name);
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setSum(long sum) {
		this.sum = sum;
	}
	public void putStatisticItem(String name, Long value) {
		this.statItems.put(name, value);
	}
}
