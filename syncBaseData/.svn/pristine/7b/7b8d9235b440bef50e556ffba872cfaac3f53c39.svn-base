package com.gboss.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.gboss.dao.SyncDateDao;
import com.gboss.pojo.SyncDate;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:SyncDateDaoImpl
 * @Description:TODO
 * @date:2015-3-18 上午11:14:04
 */
@Repository("syncDateDao")  
public class SyncDateDaoImpl extends BaseDaoImpl implements SyncDateDao {
 
	public SyncDate getSyncDateByName(String name) throws RecoverableDataAccessException,CannotGetJdbcConnectionException {
		String sql = " select * from t_if_sync t where 1=1 ";
		sql += " and if_name = '"+name+"' ";
		sql +=" ORDER BY stamp DESC ";
		System.out.println("==>"+sql);
		List<SyncDate> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<SyncDate>(SyncDate.class));
		if(list.size() > 0){
			return  list.get(0);
		}
		return null;
	}

	public List<SyncDate> getSyncDates() {
		String sql = " select * from t_if_sync t ORDER BY stamp DESC ";
		List<SyncDate> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<SyncDate>(SyncDate.class));
		if(list != null && list.size() > 0 ){
			return list;
		}
		return null;
	}

	public void updateSybcDate(SyncDate sd) {
		String sql = " update t_if_sync t set t.begin_time = ? ,t.end_time = ? where sync_id = ? ";
		jdbcTemplate.update(sql, new Object[]{sd.getBegin_time(),sd.getEnd_time(),sd.getSync_id()});
	}

	public void saveSybcDate(SyncDate sd) {
		String sql = " insert into t_if_sync(if_name,subco_no,begin_time,end_time) values(?,?,?,?) ";
		jdbcTemplate.update(sql, new Object[]{sd.getIf_name(),201L,sd.getBegin_time(),sd.getEnd_time()});
	}

	
	public static void main(String[] args) {
		ApplicationContext beanFactory = new ClassPathXmlApplicationContext("applicationContext.xml");
		SyncDateDao dao = (SyncDateDao)beanFactory.getBean("syncDateDao");
		SyncDate sd = new SyncDate();
		sd.setBegin_time(new Date());
		sd.setEnd_time(new Date());
//		dao.saveSybcDate(sd);
		sd.setSync_id(9L);
		dao.updateSybcDate(sd);
	}
	
}

