package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.BorrowDetails;
import com.gboss.pojo.Plan;
import com.gboss.pojo.SellPerformance;

/**
 * @Package:com.gboss.dao
 * @ClassName:SellPerformaceDao
 * @Description:销售业绩持久化层接口
 * @author:zfy
 * @date:2013-8-6 上午8:39:14
 */
public interface SellPerformanceDao extends BaseDao {
	/**
	 * @Title:getPlan
	 * @Description:获得每个月的销售计划
	 * @param conditionMapn
	 * @return
	 * @throws
	 */
	public List<Plan> getPlan(HashMap<String,String> conditionMapn) throws SystemException;
	/**
	 * @Title:getSaleProducts
	 * @Description:获得上一个月的实际销售数量
	 * @param conditionMap
	 * @return
	 * @throws
	 */
	public List<Map<String, Object>> getSaleProducts(HashMap<String,String> conditionMap) throws SystemException;
	
	/**
	 * @Title:getNetIn
	 * @Description:获得上一个月的实际入网数量
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public List<Map<String, Object>> getNetIn(HashMap<String,String> conditionMap) throws SystemException;
	
	/**
	 * @Title:getReturnMoney
	 * @Description:获得上一个月的实际回款额度
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public List<Map<String, Object>> getReturnMoney(HashMap<String,String> conditionMap) throws SystemException;
	
	/**
	 * @Title:getSellPerformance
	 * @Description:得到销售业绩记录
	 * @param sellPerformance
	 * @return
	 * @throws
	 */
	public SellPerformance getSellPerformance(SellPerformance sellPerformance) throws SystemException;
	/**
	 * @Title:findSellPerformances
	 * @Description:查询销售业绩
	 * @param conditionMap
	 * @return
	 * @throws
	 */
	public List<SellPerformance> findSellPerformances(SellPerformance sellPerformance) throws SystemException;
	/**
	 * @Title:getSaleProducts
	 * @Description:根据条件获实际销售的商品和数量
	 * @param conditionMap
	 * @return
	 * @throws
	 */
	public List<Map<String, Object>> getSaleProductsDetail(HashMap<String,String> conditionMap) throws SystemException;
	
	/**
	 * @Title:getNetInDetail
	 * @Description:根据条件获实际销售的商品和入网数量
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public List<Map<String, Object>> getNetInDetail(HashMap<String,String> conditionMap) throws SystemException;
	
	/**
	 * @Title:getReturnMoneyDetail
	 * @Description:根据条件获实际销售的商品和回款额度
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public List<Map<String, Object>> getReturnMoneyDetail(HashMap<String,String> conditionMap) throws SystemException;
	
	/**
	 * @Title:findBorrows
	 * @Description:查询销售经理、部门、分公司借用情况
	 * @param borrow
	 * @return
	 * @throws
	 */
	public List<Map<String, Object>> findBorrows(BorrowDetails borrowDetails) throws SystemException;
	
	/**
	 * @Title:findStockInDetail
	 * @Description:查询仓库入库商品情况
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<Map<String, Object>> findStockInDetail(HashMap<String,String> conditionMap) throws SystemException;
	/**
	 * @Title:findStockOutDetail
	 * @Description:查询仓库出库商品情况
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<Map<String, Object>> findStockOutDetail(HashMap<String,String> conditionMap) throws SystemException;
	/**
	 * @Title:findWriteoffDetails
	 * @Description:查询销账明细情况
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<Map<String, Object>> findWriteoffDetails(HashMap<String,String> conditionMap) throws SystemException;
	
	/**
	 * @Title:statSellPerformance
	 * @Description:统计部门或者分公司的销售完成比例
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<SellPerformance> statSellPerformance(HashMap<String,Object> conditionMap) throws SystemException;
	
	/**
	 * @Title:statAllSellPerformance
	 * @Description:统计总部的销售完成比例
	 * @param year
	 * @return
	 * @throws SystemException
	 */
	public List<SellPerformance> statAllSellPerformance(String year) throws SystemException;
	
}
