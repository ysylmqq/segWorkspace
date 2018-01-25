package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Setup;


/**
 * @Package:com.gboss.dao
 * @ClassName:StockSetupDao
 * @Description:库存设置数据持久层接口
 * @author:zfy
 * @date:2013-8-29 下午2:51:53
 */
public interface StockSetupDao extends BaseDao {
	/**
	 * @Title:findStocks
	 * @Description:查询库存设置
	 * @param conditionMap
	 * @param order
	 * @param isDesc
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findStocks(Map<String,Object> conditionMap, String order,boolean isDesc, int pageNo,int pageSize) throws SystemException;
	/**
	 * @Title:countStocks
	 * @Description:获得库存设置的记录数
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public int countStocks(Map<String,Object> conditionMap)throws SystemException;
	
	/**
	 * @Title:checkProductInSetup
	 * @Description:判断仓库中是否已设置了商品的最小库存
	 * @param setup
	 * @return
	 * @throws SystemException
	 */
	public boolean checkProductInSetup(Setup setup) throws SystemException;
	
	/**
	 * @Title:deleteSetups
	 * @Description:批量删除库存设置
	 * @param idList
	 * @return
	 * @throws SystemException
	 */
	public int deleteSetups(List<Long> idList) throws SystemException;
	/**
	 * @Title:updateSetups
	 * @Description:批量修改库存
	 * @param ids
	 * @param minStock
	 * @param overstockTime
	 * @param userId
	 * @return
	 * @throws SystemException
	 */
	public int updateSetups(List<Long> ids,Integer minStock,Integer overstockTime,Long userId) throws SystemException;
	
}

