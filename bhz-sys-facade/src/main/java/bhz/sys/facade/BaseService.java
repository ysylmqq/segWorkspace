package bhz.sys.facade;

import java.io.Serializable;
import java.util.List;

import bhz.com.util.Page;
import bhz.sys.entity.BaseEntity;


public interface BaseService{
	
	public <T extends BaseEntity> void save(T model);

	public <T extends BaseEntity> void flush();
	
	public <T extends BaseEntity> void clear();
	
	public <T extends BaseEntity> void evict(T model);

    public <T extends BaseEntity> void saveOrUpdate(T model);
    
    public <T extends BaseEntity> void update(T model);
    
    public <T extends BaseEntity> void merge(T model);

    public <T extends BaseEntity, PK extends Serializable> void delete(Class<T> entityClass, PK id);

    public <T extends BaseEntity> void deleteObject(T model);

    public <T extends BaseEntity, PK extends Serializable> T get(Class<T> entityClass, PK id);
    
    public <T extends BaseEntity> int countAll(Class<T> entityClass);
    
    public <T extends BaseEntity> List<T> listAll(Class<T> entityClass);
    
    public <T extends BaseEntity> Page<T> listAll(Class<T> entityClass, int pn);
    
    public <T extends BaseEntity> Page<T> listAll(Class<T> entityClass, int pn, int pageSize);
    
    public <T extends BaseEntity> int countAll(final String hql);
    
    public <T extends BaseEntity> List<T> listAll(final String hql);
    
    public <T extends BaseEntity> Page<T> listAll(final String hql, int pn);
    
    public <T extends BaseEntity> Page<T> listAll(final String hql, int pn, int pageSize);

}
