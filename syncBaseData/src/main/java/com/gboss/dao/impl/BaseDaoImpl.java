package com.gboss.dao.impl;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.gboss.comm.SystemConst;
import com.gboss.dao.BaseDao;
import com.gboss.pojo.BaseEntity;
import com.gboss.util.SQLUtil;

@Repository("BaseDao")
public class BaseDaoImpl implements BaseDao {
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected NamedParameterJdbcTemplate namedJdbcTemplate ;

	
	public <T extends BaseEntity> Long save(T model) throws Exception{
		SystemConst.D_LOG.info("保存开始" + model);
		KeyHolder holder = new GeneratedKeyHolder();
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(model);  
		namedJdbcTemplate.update(SQLUtil.createInsertSQL(model.getClass()), paramSource, holder);
		SystemConst.D_LOG.info(model.getClass() + "主键:" + holder.getKeys());
		SystemConst.D_LOG.info(model.getClass() + "保存结束" );
		return holder.getKey().longValue();
	}

	
	 
	public <T extends BaseEntity> void saveOrUpdate(T model) {
		
	}

	
	 
	public <T extends BaseEntity> void update(T model) {
		SystemConst.D_LOG.info("开始更新:"+model);
		model.setStamp(null);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(model);  
		namedJdbcTemplate.update(SQLUtil.createUpdateSQL(model.getClass()), paramSource);
		SystemConst.D_LOG.info("更新结束"+model);
	}

	 
	public <T extends BaseEntity> void merge(T model) {}
	 
	public <T extends BaseEntity, PK extends Serializable> void delete(Class<T> entityClass, PK id,T model) {
		SystemConst.D_LOG.info(model.getClass()+"删除开始" +id);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(model);  
		namedJdbcTemplate.update(SQLUtil.createDeleteSQL(entityClass), paramSource);
		SystemConst.D_LOG.info(model.getClass()+"删除结束");
	}
	
	public <T extends BaseEntity, PK extends Serializable> void delete(T model) {
		
		if(model == null ){
			SystemConst.E_LOG.error("参数为空!");
			return;
		}
		
		SystemConst.D_LOG.info(model.getClass()+"删除开始");
		Class<? extends BaseEntity> entityClass = model.getClass();
		String deleteSQL = SQLUtil.createDeleteSQL(entityClass);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(model);  
		namedJdbcTemplate.update(deleteSQL, paramSource);
		SystemConst.D_LOG.info(model.getClass()+"删除结束");
	}

	
	 
	public <T extends BaseEntity> void deleteObject(T model) {
//		sessionFactory.getCurrentSession().delete(model);
	}

	
	public <T extends BaseEntity, PK extends Serializable> T get(
			Class<T> entityClass, PK id) {
		T t = null;
		try {
			t = entityClass.newInstance();
			t = SQLUtil.setPrimaryKey(t,id);
			SqlParameterSource paramSource = new BeanPropertySqlParameterSource(t);  
			List<T> list = namedJdbcTemplate.query(SQLUtil.createQueryByIdSQL(entityClass), paramSource,new BeanPropertyRowMapper<T>(entityClass));
			if(list != null && list.size() > 0){
				return list.get(0);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	
	public <T extends BaseEntity> int countAll(Class<T> entityClass) {
//		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(entityClass);
//        criteria.setProjection(Projections.rowCount());
        return 0;
	}

	
	public <T extends BaseEntity> List<T> listAll(Class<T> entityClass) {
//		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(entityClass);
//		@SuppressWarnings("unchecked")
//		List<T> list = criteria.list();
        return null;
	}

	
	public <T extends BaseEntity> List<T> listAll(Class<T> entityClass, int pn) {
		return listAll(entityClass, pn, SystemConst.DEFAULT_PAGE_SIZE);
	}

	
	public <T extends BaseEntity> List<T> listAll(Class<T> entityClass, int pn,
			int pageSize) {
//		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(entityClass);
//        criteria.setFirstResult(PageUtil.getPageStart(pn, pageSize));
//        criteria.setMaxResults(pageSize);
//		List<T> list = criteria.list();
        return null;
	}	

	
	public <T extends BaseEntity> int countAll(String hql) {
		// TODO Auto-generated method stub  sessionFactory.getCurrentSession().createQuery(hql).list().size()
		return 0;
	}

	
	public <T extends BaseEntity> List<T> listAll(String hql) {
		// TODO Auto-generated method stubsessionFactory.getCurrentSession().createQuery(hql).list()
		return null;
	}

	
	public <T extends BaseEntity> List<T> listAll(String hql, int pn) {
		// TODO Auto-generated method stub this.listAll(hql, pn, SystemConst.DEFAULT_PAGE_SIZE)
		return null;
	}

	
	public <T extends BaseEntity> List<T> listAll(final String hql , int pn, int pageSize) {
		// TODO Auto-generated method stub
		//sessionFactory.getCurrentSession().createQuery(hql).setFirstResult(PageUtil.getPageStart(pn, pageSize)).setMaxResults(pageSize).list()
		return null;
	}



	@Override
	public <T extends BaseEntity, PK extends Serializable> void delete(
			Class<T> entityClass, final PK id) {
		jdbcTemplate.update(SQLUtil.createDeleteSQL(entityClass), new Object[]{id});
	}



	@Override
	public <T extends BaseEntity> void batchDelete(Class<T> entityClass,final List<T> lists) {
		String sql = SQLUtil.createDeleteSQL(entityClass);
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i)throws SQLException {
				T t = lists.get(i);
				Serializable id = SQLUtil.getIdVal(t);
				ps.setObject(1, id);
			}
			public int getBatchSize() {
				return lists.size();
			}
		});

	}

	@Override
	public <T extends BaseEntity> void batchSave(Class<T> entityClass,List<T> lists) {
		String sql = SQLUtil.createInsertSQL(entityClass);
		excuteBatch(sql,lists);
	}



	@Override
	public <T extends BaseEntity> void batchUpdate(Class<T> entityClass,
			List<T> updates) {
		String sql = SQLUtil.createUpdateSQL(entityClass);
		excuteBatch(sql,updates);
	}
	
	
	public <T extends BaseEntity> void excuteBatch(String sql,
			List<T> updates) {
		SqlParameterSource[] batchArgs = SqlParameterSourceUtils.createBatch(updates.toArray());
		namedJdbcTemplate.batchUpdate(sql, batchArgs);
	}
}
