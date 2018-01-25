package bhz.mst.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bhz.mst.dao.ChartDataDao;
import bhz.mst.facade.ChartDataService;


@Service("chartDataService")
@com.alibaba.dubbo.config.annotation.Service(interfaceClass=bhz.mst.facade.ChartDataService.class,
	protocol = {"dubbo"},retries=0,timeout=60000)
public class ChartDataServiceImpl extends BaseServiceImpl implements ChartDataService {

	@Autowired
	private ChartDataDao chartDataDao;

	@Override
	public  List<Map<String, Object>> getChartData(int year,int month,int type){
		return chartDataDao.getChartData(year, month, type);
	};
}

