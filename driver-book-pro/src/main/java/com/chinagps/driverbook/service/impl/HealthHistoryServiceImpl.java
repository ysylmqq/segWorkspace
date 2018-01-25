package com.chinagps.driverbook.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.chinagps.driverbook.dao.HealthHistoryMapper;
import com.chinagps.driverbook.pojo.HealthHistory;
import com.chinagps.driverbook.pojo.ReturnValue;
import com.chinagps.driverbook.service.IHealthHistoryService;
import com.chinagps.driverbook.util.pagination.Page;

@Service
@Scope("prototype")
public class HealthHistoryServiceImpl extends BaseServiceImpl<HealthHistory> implements IHealthHistoryService {
	
	@Autowired
	private HealthHistoryMapper healthHistoryMapper;

	@Override
	public ReturnValue healthHistory(Map<String, Object> params, ReturnValue rv) throws Exception {
		Page page = new Page();
		List<HealthHistory> datas = null;
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		if (params.get("pageNum") == null) {
			page.setPageNum(1);
		} else {
			page.setPageNum((Integer) params.get("pageNum"));
		}
		if (params.get("numPerPage") == null) {
			page.setNumPerPage(20);
		} else {
			page.setNumPerPage((Integer)params.get("numPerPage"));
		}
		HealthHistory healthHistory = new HealthHistory();
		healthHistory.setCallLetter(params.get("callLetter").toString());
		int count = healthHistoryMapper.countAll(healthHistory);
		page.setTotalCount(count);
		datas = healthHistoryMapper.findByPage(healthHistory, new RowBounds(page.getOffset(), page.getNumPerPage()));
		resultMap.put("page", page);
		resultMap.put("health", datas);
		rv.setDatas(resultMap);
		return rv;
	}
	
	
	
}
