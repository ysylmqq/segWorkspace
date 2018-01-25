package com.gboss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Setup;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service
 * @ClassName:StockSetupService
 * @Description:库存设置业务层接口
 * @author:zfy
 * @date:2013-8-29 下午3:24:17
 */
public interface StockSetupService extends BaseService {
	/**
	 * @Title:findSetups
	 * @Description:查询所有库存设置
	 * @param map
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findSetups(Map<String, Object> map)throws SystemException;
	
	/**
	 * @Title:findStock
	 * @Description:分页查询
	 * @param pageSelect
	 * @return
	 * @throws SystemException
	 */
	public Page<HashMap<String, Object>> findStocks(PageSelect<HashMap<String, Object>> pageSelect)throws SystemException;
	/**
	 * @Title:addSetups
	 * @Description:批量添加库存设置
	 * @param productIds
	 * @param whsId
	 * @param minStock
	 * @param overstockTime
	 * @param userId
	 * @return
	 * @throws SystemException
	 * @throws批量添加库存设置
	 */
	public int addSetups(List<Long> productIds,Long whsId,String whsName,Integer minStock,Integer overstockTime,Long userId,String userName)throws SystemException;
	/**
	 * @Title:deleteSetup
	 * @Description:删除库存设置
	 * @param id
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public int deleteSetup(Long id) throws SystemException;
	/**
	 * @Title:updateSetups
	 * @Description:修改库存设置
	 * @param ids
	 * @param minStock
	 * @param overstockTime
	 * @param userId
	 * @return
	 * @throws SystemException
	 */
	public int updateSetups(List<Long> ids,Integer minStock,Integer overstockTime,Long userId) throws SystemException;
	/**
	 * @Title:deleteSetups
	 * @Description:批量删除库存设置
	 * @param idList
	 * @return
	 * @throws SystemException
	 */
	public int deleteSetups(List<Long> idList) throws SystemException;
}

