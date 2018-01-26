package cc.chinagps.seat.service;

import java.util.List;
import java.util.Map;

import cc.chinagps.seat.bean.table.UnitTypeTable;

public interface UnitTypeTableService {
	/**
	 * 获取所有类型
	 * @param params
	 * @return
	 */
	public List<UnitTypeTable> unitTypesList( Map<String,String> params );
}
