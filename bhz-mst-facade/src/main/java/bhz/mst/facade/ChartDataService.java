package bhz.mst.facade;

import java.util.List;
import java.util.Map;

public interface ChartDataService extends BaseService {
	
	public  List<Map<String, Object>> getChartData(int year,int month,int type);

}

