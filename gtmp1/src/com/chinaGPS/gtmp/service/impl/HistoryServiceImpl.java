package com.chinaGPS.gtmp.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinaGPS.gtmp.entity.HistoryPOJO;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.mapper.HistoryMapper;
import com.chinaGPS.gtmp.service.IHistoryService;

/**
 * @Package:com.chinaGPS.gtmp.service.impl
 * @ClassName:HistoryServiceImpl
 * @Description:历史位置Service实现类
 * @author:zfy
 * @date:2013-4-9 上午10:16:38
 */
@Service
public class HistoryServiceImpl extends BaseServiceImpl<HistoryPOJO> implements IHistoryService {
	@Resource
	private HistoryMapper<HistoryPOJO> historyMapper;

	@Override
	protected BaseSqlMapper<HistoryPOJO> getMapper() {
		return this.historyMapper;
	}

}
