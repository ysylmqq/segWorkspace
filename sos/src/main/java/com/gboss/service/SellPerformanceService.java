package com.gboss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.BorrowDetails;
import com.gboss.pojo.SellPerformance;


/**
 * @Package:com.gboss.service
 * @ClassName:SellPerformanceService
 * @Description:销售业绩业务层接口
 * @author:zfy
 * @date:2013-8-6 上午8:41:41
 */
public interface SellPerformanceService {
	/**
	 * @Title:findSellPerformances
	 * @Description:查询销售业绩
	 * @param sellPerformance
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public List<SellPerformance> findSellPerformances(SellPerformance sellPerformance) throws SystemException;
	
	/**
	 * @Title:getSellProductDetail
	 * @Description:获得销售商品明细
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public List<Map<String, Object>> getSellProductDetail(HashMap<String, String> conditionMap) throws SystemException;
	/**
	 * @Title:findBorrows
	 * @Description:查询销售经理、部门、分公司借用情况
	 * @param borrow
	 * @return
	 * @throws
	 */
	public List<Map<String, Object>> findBorrows(BorrowDetails borrowDetails) throws SystemException;
	
	/**
	 * @Title:getSellProductAction
	 * @Description:获得销售商品明细的动作（出库、入库、销账）
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public List<Map<String, Object>> getSellProductAction(HashMap<String, String> conditionMap) throws SystemException;
	/**
	 * @Title:statSellPerformance
	 * @Description:统计部门或者分公司的销售完成比例
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<Map<String,Object>> statSellPerformance(HashMap<String,Object> conditionMap) throws SystemException;
	
	public List<SellPerformance> statAllSellPerformance(String year) throws SystemException;
	
}

