package cc.chinagps.seat.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cc.chinagps.seat.bean.table.StatusTable;
import cc.chinagps.seat.dao.UtilDao;

@Repository
public class UtilDaoImpl extends BasicDao implements UtilDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<StatusTable> getStatusList() {		
		return getSession().getNamedQuery("SelectStatus")
				.list();
	}

}
