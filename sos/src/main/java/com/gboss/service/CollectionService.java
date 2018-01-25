package com.gboss.service;

import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Collection;

/**
 * @Package:com.gboss.service
 * @ClassName:CollectionService
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-4-2 下午1:44:01
 */
public interface CollectionService extends BaseService {
	
	/**
	 * 添加托收信息
	 * @param collection
	 * @return
	 */
	public Long add(Collection collection)throws SystemException;
	
	/**
	 * 根据托收id删除资料
	 * @param id
	 */
	public void delete(Long id)throws SystemException;
	
	/**
	 * 根据客户id获取客户托收资料（第一个）
	 * @param cust_id
	 * @return
	 */
	public Collection getCollectionByCustId(Long cust_id)throws SystemException;
	
	/**
	 * 根据客户id获取客户托收资料(所有)
	 * @param cust_id
	 * @return
	 */
	public List<Collection> getCollections(Long cust_id)throws SystemException;
	
	/**
	 * 通过合同号获取托收资料(第一个)
	 * @param collection
	 * @return
	 */
	public Collection getCollectionByctno(Collection collection)throws SystemException;

	/**
	 * 验证合同号是否重复
	 * @param param
	 * @return
	 * @throws SystemException
	 */
	public Map<String, Object> checkPayCtNo(Map<String, Object> param)throws SystemException;

	/**
	 * 通过车辆计费信息关联托收资料
	 * @param unit_id
	 * @param customer_id
	 * @return
	 * @throws SystemException
	 */
	public Collection getCollectionByUnit(Long unit_id, Long customer_id)throws SystemException;

}

