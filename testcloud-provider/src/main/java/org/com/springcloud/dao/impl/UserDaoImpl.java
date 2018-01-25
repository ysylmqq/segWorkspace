package org.com.springcloud.dao.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.com.springcloud.dao.UserDao;
import org.com.springcloud.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl  implements UserDao{

	  @Autowired
	  private JdbcTemplate jdbcTemplate;
	
	@Override
	public User getUserById(Long id) {
		User user = new User();
		user.setName("ysy");
		user.setAge(id.toString());
		user.setSex("m");
		return user;
	}
	
	 @Override
	 public List<Map<String, Object>> getDbType(){
	        String sql = "select * from user";
	        List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql);
	        for (Map<String, Object> map : list) {
	            Set<Entry<String, Object>> entries = map.entrySet( );
	                if(entries != null) {
	                    Iterator<Entry<String, Object>> iterator = entries.iterator( );
	                    while(iterator.hasNext( )) {
	                    Entry<String, Object> entry =(Entry<String, Object>) iterator.next( );
	                    Object key = entry.getKey( );
	                    Object value = entry.getValue();
	                }
	            }
	        }
	        return list;
	    }
}
