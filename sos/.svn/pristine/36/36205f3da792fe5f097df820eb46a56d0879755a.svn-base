package com.gboss.dao;

import java.util.List;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Serviceitem;

/**
 * @Package:com.gboss.dao
 * @ClassName:ServiceItemDao
 * @Description:服务项管理数据持久层接口
 * @author:zfy
 * @date:2013-8-9 下午2:20:47
 */
public interface ServiceItemDao extends BaseDao {
	/**
	 * @Title:findServiceitem
	 * @Description:查询服务项
	 * @param serviceitem
	 * @return
	 * @throws SystemException
	 */
	public List<Serviceitem> findServiceitem(Serviceitem serviceitem) throws SystemException;
	
	/**
	 * @Title:checkSeviceItemCode
	 * @Description:判断服务项编号是否存在
	 * @param serviceitem
	 * @return true:已存在；false:不存在
	 * @throws
	 */
	public boolean checkSeviceItemCode(Serviceitem serviceitem) throws SystemException;
	
	/**
	 * @Title:checkSeviceItemName
	 * @Description:判断服务项名称是否存在
	 * @param serviceitem
	 * @return true:已存在；false:不存在
	 * @throws
	 */
	public boolean checkSeviceItemName(Serviceitem serviceitem) throws SystemException;
	
	/**
	 * @Title:checkItemIsUsing
	 * @Description:判断服务项是否在用，用于删除前的校验
	 * @param serviceItemId
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public boolean checkItemIsUsing(Long serviceItemId) throws SystemException;
}

