package com.chinagps.driverbook.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.chinagps.driverbook.dao.StatisticsMapper;
import com.chinagps.driverbook.pojo.ReturnValue;
import com.chinagps.driverbook.pojo.Score;
import com.chinagps.driverbook.pojo.Statistics;
import com.chinagps.driverbook.service.IStatisticsService;
import com.chinagps.driverbook.util.SGErrorInfoConstants;

@Service
@Scope("prototype")
public class StatisticsServiceImpl extends BaseServiceImpl<Statistics> implements IStatisticsService {
	@Autowired
	private StatisticsMapper statisticsMapper;

	public ReturnValue summary(HashMap<String, String> params, ReturnValue rv) throws Exception {
		
		if ("1".equals(params.get("tag"))) {
			// 周油耗排名
			Score score = statisticsMapper.vehicleOilRank(params.get("callLetter"));
			// 周统计数据
			Statistics stat = statisticsMapper.weekSummary(params.get("callLetter"));
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("score", score);
			resultMap.put("summary", stat);
			rv.setDatas(resultMap);
		} else if ("2".equals(params.get("tag"))) {
			// 月统计
			Statistics stat = statisticsMapper.monthSummary(params);
			rv.setDatas(stat);
		} else {
			rv.setErrorCode(SGErrorInfoConstants.SG_CODE_403);
			rv.setErrorMsg(SGErrorInfoConstants.SG_MSG_403);
		}
		return rv;
	}

}
