package bhz.mst.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bhz.com.constant.SystemConst;
import bhz.com.util.Page;
import bhz.com.util.PageUtil;
import bhz.mst.dao.BaseDao;
import bhz.mst.entity.BaseEntity;
import bhz.mst.facade.BaseService;

@Service("BaseService")
public class BaseServiceImpl implements BaseService {
	
	@Autowired
    @Qualifier("BaseDao")
	protected BaseDao baseDao;

	public <T extends BaseEntity> void save(T model) {
		
		baseDao.save(model);
	}

	public <T extends BaseEntity> void saveOrUpdate(T model) {
		
		baseDao.saveOrUpdate(model);
	}

	public <T extends BaseEntity> void update(T model) {
		
		baseDao.update(model);
	}

	public <T extends BaseEntity> void merge(T model) {
		
		baseDao.merge(model);
	}

	public <T extends BaseEntity, PK extends Serializable> void delete(
			Class<T> entityClass, PK id) {
		
		baseDao.delete(entityClass, id);
	}

	public <T extends BaseEntity> void deleteObject(T model) {
		
		baseDao.deleteObject(model);
	}

	public <T extends BaseEntity, PK extends Serializable> T get(
			Class<T> entityClass, PK id) {
		
		return baseDao.get(entityClass, id);
	}

	public <T extends BaseEntity> int countAll(Class<T> entityClass) {
		
		return baseDao.countAll(entityClass);
	}

	public <T extends BaseEntity> List<T> listAll(Class<T> entityClass) {
		
		return baseDao.listAll(entityClass);
	}

	public <T extends BaseEntity> Page<T> listAll(Class<T> entityClass, int pn) {
		
		return listAll(entityClass, pn, SystemConst.DEFAULT_PAGE_SIZE);
	}

	public <T extends BaseEntity> Page<T> listAll(Class<T> entityClass, int pn,
			int pageSize) {
		
		int total = countAll(entityClass);
        List<T> items = baseDao.listAll(entityClass, pn, pageSize);
        return PageUtil.getPage(total, pn, items, pageSize);
	}

	public <T extends BaseEntity> int countAll(String hql) {
		
		return baseDao.countAll(hql);
	}

	public <T extends BaseEntity> List<T> listAll(String hql) {
		
		return baseDao.listAll(hql);
	}

	public <T extends BaseEntity> Page<T> listAll(String hql, int pn) {
		
		return listAll(hql, pn, SystemConst.DEFAULT_PAGE_SIZE);
	}
	
	public <T extends BaseEntity> Page<T> listAll(String hql, int pn,
			int pageSize) {
		
		int total = countAll(hql);
        List<T> items = baseDao.listAll(hql, pn, pageSize);
        return PageUtil.getPage(total, pn, items, pageSize);
	}

	@Override
	public <T extends BaseEntity> void flush() {
		baseDao.getMysql1CurrentSession().flush();
		//baseDao.getMysql1CurrentSession().clear();
	}

	@Override
	public <T extends BaseEntity> void evict(T model) {
		baseDao.getMysql1CurrentSession().evict(model);
	}

	@Override
	public <T extends BaseEntity> void clear() {
		baseDao.getMysql1CurrentSession().clear();
	}

}
