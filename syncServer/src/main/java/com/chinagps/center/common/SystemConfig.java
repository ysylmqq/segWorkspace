package com.chinagps.center.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("SystemConfig")
public class SystemConfig {

	@Value("${startPage}")
	private String startPage;

	@Value("${pageSize}")
	private String pageSize;

	@Value("${unit_id}")
	private String unit_id;

	@Value("${http_time_out}")
	private String httpTimeOut;
	
	@Value("${sync_interval}")
	private String syncInterval;

	public String getSyncInterval() {
		return syncInterval;
	}

	public void setSyncInterval(String syncInterval) {
		this.syncInterval = syncInterval;
	}

	public String getHttpTimeOut() {
		return httpTimeOut;
	}

	public void setHttpTimeOut(String httpTimeOut) {
		this.httpTimeOut = httpTimeOut;
	}

	public String getStartPage() {
		return startPage;
	}

	public void setStartPage(String startPage) {
		this.startPage = startPage;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getUnit_id() {
		return unit_id;
	}

	public void setUnit_id(String unit_id) {
		this.unit_id = unit_id;
	}

}
