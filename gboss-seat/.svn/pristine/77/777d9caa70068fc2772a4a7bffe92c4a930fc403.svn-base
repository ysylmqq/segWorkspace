package cc.chinagps.seat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.chinagps.seat.bean.table.GroupUserTable;
import cc.chinagps.seat.dao.GroupUserTableDao;
import cc.chinagps.seat.service.GroupUserTableService;

@Service("groupUserTableService")
public class GroupUserTableServiceImpl implements GroupUserTableService {
	
	@Autowired
	private GroupUserTableDao groupUserTableDao;

	@Override
	public void save(GroupUserTable gut) {
		groupUserTableDao.save(gut);
	}
	
	
	
}
