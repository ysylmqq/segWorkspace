package cc.chinagps.seat.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cc.chinagps.seat.bean.table.UnitTypeTable;
import cc.chinagps.seat.dao.UnitTypeTableDao;

@Repository("unitTypeDao")
public class UnitTypeTableDaoImpl extends BasicDao implements UnitTypeTableDao {

	@Override
	public List<UnitTypeTable> unitTypesList(Map<String, String> params) {
		@SuppressWarnings("unchecked")
		List<UnitTypeTable> lists = getSession().createQuery(" from UnitTypeTable t ").list();
		if(lists != null && lists.size() > 0){
			return lists;
		}
		return null;
	}

}
