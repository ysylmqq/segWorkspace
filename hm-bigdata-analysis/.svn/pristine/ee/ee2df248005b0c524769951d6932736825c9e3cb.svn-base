package com.hm.bigdata.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hm.bigdata.dao.ChartDataDao;
import com.hm.bigdata.service.ChartDataService;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:VehicleServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-3-24 下午3:22:09
 */

@Service("chartDataService")
@Transactional(value="mysql1TxManager")
public class ChartDataServiceImpl extends BaseServiceImpl implements ChartDataService {

	@Autowired
	private ChartDataDao chartDataDao;

	@Override
	public  List<Map<String, Object>> getChartData(int year,int month,int type){
		return chartDataDao.getChartData(year, month, type);
	};
}

