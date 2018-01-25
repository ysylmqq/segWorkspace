package com.chinagps.driverbook.service;

import com.chinagps.driverbook.pojo.NoticeConfig;
import com.chinagps.driverbook.pojo.ReturnValue;

public interface INoticeConfigService extends BaseService<NoticeConfig> {

	/**
	 * 新增/修改消息设置
	 * @param nc NoticeConfig对象
	 * @param rv 返回值对象
	 * @throws Exception
	 */
	public void addOrEditNoticeConfig(NoticeConfig nc, ReturnValue rv) throws Exception;
	
	public NoticeConfig findById(Long id) throws Exception;
	
}
