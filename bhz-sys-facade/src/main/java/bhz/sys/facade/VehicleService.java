package bhz.sys.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bhz.com.util.Page;
import bhz.com.util.PageSelect;
import bhz.sys.entity.Vehicle;


/**
 * @Package:com.gboss.service
 * @ClassName:VehicleService
 * @Description:从门店copy过来，以后可能会改成静态数据（搜索引擎）
 * @author:xiaoke
 * @date:2014-3-24 下午3:13:03
 */
public interface VehicleService extends BaseService {
	
	public Page<HashMap<String, Object>> search(PageSelect<Vehicle> pageSelect, Long subco_no);
	
	/**
	 * 导出所有的车辆信息
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> findAllVehicles(Map<String,Object> map);
	
	/**
	 * 查询海马车辆
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> findHmVehicles(Map<String,Object> map);

}

