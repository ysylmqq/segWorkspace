package com.chinagps.driverbook.service;

import java.util.Map;

import com.chinagps.driverbook.protobuf.GBossDataBuff.AppNoticeInfo;

public interface IPushService {

	public void push(Map<String, Object> params) throws Exception;
	
	public void notifyCenter(AppNoticeInfo appNoticeInfo) throws Exception;
	
	public Map<String,Object> pushOffLine(Map<String, Object> params) throws Exception;
	
}
