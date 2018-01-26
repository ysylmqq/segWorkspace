package cc.chinagps.seat.dao;

import java.util.List;

import cc.chinagps.seat.bean.table.StatusTable;

public interface UtilDao {

	/**
	 * 获取车辆状态表数据
	 * @return
	 */
	List<StatusTable> getStatusList();

}
