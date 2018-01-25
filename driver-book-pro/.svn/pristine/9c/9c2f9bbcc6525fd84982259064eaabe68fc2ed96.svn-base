package com.chinagps.driverbook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.chinagps.driverbook.dao.NoticeConfigMapper;
import com.chinagps.driverbook.pojo.NoticeConfig;
import com.chinagps.driverbook.pojo.ReturnValue;
import com.chinagps.driverbook.service.INoticeConfigService;

@Service
@Scope("prototype")
public class NoticeConfigServiceImpl extends BaseServiceImpl<NoticeConfig> implements INoticeConfigService {
	@Autowired
	private NoticeConfigMapper noticeConfigMapper;
	
	public void addOrEditNoticeConfig(NoticeConfig nc, ReturnValue rv) throws Exception {
		NoticeConfig noticeConfig = noticeConfigMapper.findById(nc.getCustomerId());
		if (noticeConfig == null) {
			int addCount = noticeConfigMapper.add(nc);
			if (addCount > 0) {
				rv.setSuccess(true);
			} else {
				rv.saveErrror();
			}
		} else {
			int editCount = noticeConfigMapper.edit(nc);
			if (editCount > 0) {
				rv.setSuccess(true);
			} else {
				rv.editError();
			}
		}
	}

	@Override
	public NoticeConfig findById(Long id) throws Exception {
		return noticeConfigMapper.findById(id);
	}
	
}
