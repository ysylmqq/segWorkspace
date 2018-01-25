package com.gboss.service.impl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.CollectionDao;
import com.gboss.pojo.Collection;
import com.gboss.service.CollectionService;
import com.gboss.util.Utils;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:CollectionServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-4-2 下午1:45:43
 */

@Service("CollectionService")
@Transactional
public class CollectionServiceImpl extends BaseServiceImpl implements
		CollectionService {

	@Autowired
	@Qualifier("CollectionDao")
	private CollectionDao collectionDao;

	@Override
	public Long add(Collection collection) {
		save(collection);
		return collection.getCollection_id();
	}

	@Override
	public Collection getCollectionByCustId(Long cust_id)throws SystemException{
		return collectionDao.getCollectionByCustId(cust_id);
	}

	@Override
	public void delete(Long id)throws SystemException{
		delete(Collection.class, id);
	}

	@Override
	public Collection getCollectionByctno(Collection collection)throws SystemException{
		return collectionDao.getCollectionByctno(collection);
	}

	@Override
	public List<Collection> getCollections(Long cust_id)throws SystemException{
		return collectionDao.getCollections(cust_id);
	}

	@Override
	public Map<String, Object> checkPayCtNo(Map<String, Object> param)
			throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Collection co = collectionDao.getCollectionByCondition(param);
			if (co == null) {
				map.put("msg", null);
			} else {
				map.put("msg", "has");
			}
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", e.getMessage());
		}
		return map;
	}

	@Override
	public Collection getCollectionByUnit(Long unitId, Long customerId)
			throws SystemException {
		Map<String, Object> map = collectionDao.getCollectionByUnit(unitId, customerId);
		Collection collection = new Collection();
		if(Utils.isNotNullOrEmpty(map)){
			transMap2Bean(map, collection);
		}else{
			collection = collectionDao.getCollectionByCustId(customerId);
		}
		return collection;
	}

	private static void transMap2Bean(Map<String, Object> map, Object obj) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				if (map.containsKey(key)) {
					Object value = map.get(key);
					// 得到property对应的setter方法
					Method setter = property.getWriteMethod();
					if(value instanceof BigInteger){
						if(value != null){
							BigInteger bg1 = new BigInteger(value.toString());
							setter.invoke(obj, bg1.longValue());
						}
					}else{
						setter.invoke(obj, value);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("transMap2Bean Error " + e);
		}
	}
}
