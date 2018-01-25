package bhz.sys.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bhz.com.util.Page;
import bhz.com.util.PageSelect;
import bhz.sys.entity.Alarm;


/**
 * @Package:com.gboss.service
 * @ClassName:VehicleService
 * @Description:从门店copy过来，以后可能会改成静态数据（搜索引擎）
 * @author:xiaoke
 * @date:2014-3-24 下午3:13:03
 */
public interface AlarmService extends BaseService {
	
	public Page<HashMap<String, Object>> search(PageSelect<Alarm> pageSelect, Long subco_no);
	
	public List<HashMap<String, Object>> search();
	
	/**
	 * 导出所有的警情信息
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> findAllAlarms(Map<String,Object> map);
	
	/**
	 * 导出所有的警情信息
	 * @param map
	 * @return
	 */
	public void saveAlarmInfo(Alarm alarm);
	
	/**
	 * 警情信息统计
	 * @param map
	 * @return
	 */
	public  List<Map<String, Object>> alarmCount(Map<String,Object> map);
	
	/**
	 * 故障信息统计
	 * @param map
	 * @return
	 */
	public  List<Map<String, Object>> faultCount(Map<String,Object> map);

}

