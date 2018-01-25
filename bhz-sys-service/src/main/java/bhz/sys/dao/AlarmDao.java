package bhz.sys.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bhz.com.util.Page;
import bhz.com.util.PageSelect;
import bhz.sys.entity.Alarm;


public interface AlarmDao extends BaseDao{
	
	
	public Page<HashMap<String, Object>> search(PageSelect<Alarm> pageSelect, Long subco_no);
	
	public List<HashMap<String, Object>> search();
	
	/**
	 *查询所有的警情
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> findAllAlarms(Map<String, Object> map);

	/**
	 * 警情报表统计
	 * @param map
	 * @return
	 */
	public  List<Map<String, Object>> alarmCount(Map<String, Object> map);

	/**
	 * 故障报表统计
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> faultCount(Map<String, Object> map);
	
	
}

