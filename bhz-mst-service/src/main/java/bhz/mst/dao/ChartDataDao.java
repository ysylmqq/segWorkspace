package bhz.mst.dao;

import java.util.List;
import java.util.Map;

/**
 * @Package:com.gboss.dao
 * @ClassName:VehicleDao
 * @Description:TODO 从门店copy过来，以后可能会改成静态数据（搜索引擎）
 * @author:xiaoke
 * @date:2014-3-24 下午2:56:45
 */
public interface ChartDataDao extends BaseDao{
	
	public  List<Map<String, Object>> getChartData(int year,int month,int type);

}

