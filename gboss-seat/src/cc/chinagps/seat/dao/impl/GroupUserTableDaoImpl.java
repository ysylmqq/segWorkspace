package cc.chinagps.seat.dao.impl;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import cc.chinagps.seat.bean.table.GroupUserTable;
import cc.chinagps.seat.dao.GroupUserTableDao;

@Repository("groupUserTableDao")
public class GroupUserTableDaoImpl extends BasicDao implements GroupUserTableDao {

	@Override
	public void save(GroupUserTable gut) {
		SQLQuery  sql = getSession().createSQLQuery(" insert into t_seat_seg_group_user(group_id,phonebook_id) values("+gut.getGroup_id()+","+gut.getPhonebook_id()+") ");
		sql.executeUpdate();
	}

}
