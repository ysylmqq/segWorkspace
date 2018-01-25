package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.BorrowDetails;
import com.gboss.pojo.Stock;
import com.gboss.pojo.Stockin;
import com.gboss.pojo.Stockout;
import com.gboss.pojo.Writeoffs;

/**
 * @Package:com.gboss.dao
 * @ClassName:StockDao
 * @Description:库存数据持久层接口
 * @author:zfy
 * @date:2013-8-30 上午11:05:58
 */
public interface StockDao extends BaseDao {
	/**
	 * @Title:findCurrentStocks
	 * @Description:查询库存
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public List<HashMap<String, Object>> findCurrentStocks(Map<String, Object> conditionMap, String order,boolean isDesc, int pageNo, int pageSize) throws SystemException;
	/**
	 * @Title:findCurrentStocks2
	 * @Description:查询库存、借用数量、销售未还库数量
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public List<HashMap<String, Object>> findCurrentStocks2(Map<String, Object> conditionMap, String order,boolean isDesc, int pageNo, int pageSize) throws SystemException;

	/**
	 * @Title:countStockInOutDetails2
	 * @Description:查询库存、借用数量、销售未还库数量记录数
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public int countCurrentStocks2(Map<String,Object> conditionMap) throws SystemException;
	
	/**
	 * @Title:countStockInOutDetails
	 * @Description:查询库存记录数
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public int countCurrentStocks(Map<String,Object> conditionMap) throws SystemException;
	
	/**
	 * @Title:findStockLtSetup
	 * @Description:库存预警，查询出库存数量小于库存设置的商品
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findStockLtSetup(Map<String,Object> conditionMap) throws SystemException;
	
	/**
	 * @Title:findStockOverTime
	 * @Description:库存预警，查询库存积压时长大于预警设置积压时长的商品
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findStockOverTime(Map<String,Object> conditionMap) throws SystemException;
	
	/**
	 * @Title:getStockByWhsAndProduct
	 * @Description:根据仓库和商品查询库存表中的记录
	 * @param stock
	 * @return
	 * @throws SystemException
	 */
	public Stock getStockByWhsAndProduct(Stock stock) throws SystemException;
	
	/**
	 * @Title:updateStockNum
	 * @Description:修改库存数量
	 * @param stock
	 * @return
	 * @throws SystemException
	 */
	public int updateStockNum(Stock stock) throws SystemException;
	
	/**
	 * @Title:findStockInOutDetailsPage
	 * @Description:分页查询出入库明细
	 * @param conditionMap
	 * @param order
	 * @param isDesc
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findStockInOutDetailsPage(Map<String,Object> conditionMap, String order,boolean isDesc,int pageNo,int pageSize) throws SystemException;
	
	/**
	 * @Title:countStockInOutDetails
	 * @Description:查询出入库明细记录数
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public int countStockInOutDetails(Map<String,Object> conditionMap) throws SystemException;
	
	/**
	 * @Title:findStockInDetails3
	 * @Description:查询入库详细,用于报表（新产品入库表）
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findStockInDetails3(Map<String, Object> conditionMap, String order,boolean isDesc, int pageNo, int pageSize) throws SystemException;
	
	/**
	 * @Title:findStockInDetails3
	 * @Description:查询入库详细,用于报表（新产品入库表）
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public int countStockInDetails3(Map<String,Object> conditionMap) throws SystemException;
	
	/**
	 * @Title:findAllProducts
	 * @Description:按条件查询所有分公司商品
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findAllProducts(Map<String, Object> conditionMap) throws SystemException ;

	/**
	 * @Title:findStockIns
	 * @Description:分页查询入库信息
	 * @param conditionMap
	 * @param order
	 * @param isDesc
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findStockIns(Map<String, Object> conditionMap , String order, boolean isDesc, int pageNo, int pageSize)throws SystemException;

	/**
	 * @Title:countStockIns
	 * @Description:插入入库记录条数
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public int countStockIns(Map<String, Object> conditionMap)throws SystemException;
	/**
	 * @Title:findStockOuts
	 * @Description:分页查询出库信息
	 * @param conditionMap
	 * @param order
	 * @param isDesc
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findStockOuts(Map<String, Object> conditionMap , String order, boolean isDesc, int pageNo, int pageSize)throws SystemException;

	/**
	 * @Title:countStockOuts
	 * @Description:插入出库记录条数
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public int countStockOuts(Map<String, Object> conditionMap)throws SystemException;
	
	/**
	 * @Title:findStockInDetails4
	 * @Description:查询入库详细,用于入库主页
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findStockInDetails4(Map<String,Object> conditionMap) throws SystemException;
	/**
	 * @Title:findStockOutDetails4
	 * @Description:查询出库详细,用于出库主页
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findStockOutDetails4(Map<String,Object> conditionMap) throws SystemException;
	/**
	 * @Title:getBorrowByWhsAndProduct
	 * @Description:根据借用人和商品查询借用表中的记录
	 * @param borrowDetails
	 * @return
	 * @throws SystemException
	 */
	public List<BorrowDetails> getBorrowByWhsAndProduct(BorrowDetails borrowDetails) throws SystemException;
	
	/**
	 * @Title:findBorrows
	 * @Description:查询用户借用情况
	 * @param conditionMap
	 * @return
	 * @throws
	 */
	public List<Map<String, Object>> findBorrows(Map<String,Object> conditionMap) throws SystemException;
    
	/**
	 * @Title:getMaxStockNo
	 * @Description:入库单、出库最大条数
	 * @param whsId
	 * @param date
	 * @return
	 * @throws SystemException
	 */
	public int getMaxStockNo(Long whsId,String date,boolean isStockIn) throws SystemException;
	
	/**
	 * @Title:getBorrowTotalNum
	 * @Description:查询用户借用商品总数量
	 * @param borrowerId
	 * @param channelId
	 * @param whsId
	 * @return
	 * @throws SystemException
	 */
	public Long getBorrowTotalNum(Long borrowerId,Long channelId,List<Long> whsIds) throws SystemException;
	
	/**
	 * @Title:getMaxWriteoffsNo
	 * @Description:核销、销账单最大条数
	 * @param whsId
	 * @param date
	 * @return
	 * @throws SystemException
	 */
	public int getMaxWriteoffsNo(Long whsId,Long orgId,String date,boolean isHx) throws SystemException;
	/**
	 * @Title:checkStockInCode
	 * @Description:检查入库单号是否存在
	 * @param stockin
	 * @return
	 * @throws SystemException
	 */
	public boolean checkStockInCode(Stockin stockin) throws SystemException;
	/**
	 * @Title:checkStockOutCode
	 * @Description:检查出库单号是否存在
	 * @param stockout
	 * @return
	 * @throws SystemException
	 */
	public boolean checkStockOutCode(Stockout stockout) throws SystemException;
	/**
	 * @Title:checkWriteoffsCode
	 * @Description:检查核销单号是否存在
	 * @param writeoffs
	 * @return
	 * @throws SystemException
	 */
	public boolean checkWriteoffsCode(Writeoffs writeoffs) throws SystemException;
	/**
	 * @Title:deleteBorrowDetails
	 * @Description:删除挂账信息(初始化)
	 * @param idList
	 * @return
	 * @throws SystemException
	 */
	public int deleteBorrowDetails(List<Long> idList) throws SystemException;
	/**
	 * @Title:deleteStocks
	 * @Description:删除库存信息(初始化)
	 * @param idList
	 * @return
	 * @throws SystemException
	 */
	public int deleteStocks(List<Long> idList) throws SystemException;
	
	/**
	 * @Title:findWriteoffs
	 * @Description:查询核销明细,用于员工借用处
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findWriteoffs(Map<String, Object> conditionMap, String order,boolean isDesc, int pageNo, int pageSize) throws SystemException;
	/**
	 * @Title:findWriteoffsXz
	 * @Description:查询销账明细,，用于员工借用处
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findWriteoffsXz(Map<String, Object> conditionMap, String order,boolean isDesc, int pageNo, int pageSize) throws SystemException;
	/**
	 * @Title:findProductStock
	 * @Description:查询商品库存，汇总
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findProductStock(Map<String, Object> conditionMap, String order,boolean isDesc, int pageNo, int pageSize) throws SystemException;
	/**
	 * @Title:countWriteoffs
	 * @Description:查询核销明细记录数,用于员工借用处
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public int countWriteoffs(Map<String,Object> conditionMap) throws SystemException;
	/**
	 * @Title:countWriteoffsXz
	 * @Description:查询销账明细记录数,用于员工借用处
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public int countWriteoffsXz(Map<String,Object> conditionMap) throws SystemException;
	/**
	 * @Title:findSaleOutDetails
	 * @Description:查询销售出库信息
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findSaleOutDetails(Map<String, Object> conditionMap, String order,boolean isDesc, int pageNo, int pageSize) throws SystemException;
	/**
	 * @Title:countSaleOutDetails
	 * @Description:查询销售出库记录数
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public int countSaleOutDetails(Map<String,Object> conditionMap) throws SystemException;
	
	/**
	 * @Title:findWriteoffsDetailsXz
	 * @Description:查询销账明细信息
	 * @param conditionMap
	 * @param order
	 * @param isDesc
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findWriteoffsDetailsXz(Map<String, Object> conditionMap)throws SystemException;

	/**
	 * @Title:findWriteoffsDetails
	 * @Description:查询核销明细信息
	 * @param conditionMap
	 * @param order
	 * @param isDesc
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findWriteoffsDetails(Map<String, Object> conditionMap)throws SystemException;
	/**
	 * @Title:findWriteoffsOperates
	 * @Description:查询核销明细信息,用于借用查询，查询所有操作
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findWriteoffsOperates(
			Map<String, Object> conditionMap) throws SystemException;
	/**
	 * @Title:findWriteoffsXzOperates
	 * @Description:查询销账明细信息,用于借用查询，查询所有操作
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findWriteoffsXzOperates(
			Map<String, Object> conditionMap) throws SystemException;
	/**
	 * @Title:findStockOutDetailsOperates
	 * @Description:查询出库明细信息,用于借用查询，查询所有操作
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findStockOutDetailsOperates(
			Map<String, Object> conditionMap) throws SystemException;
	
	/**
	 * @Title:findStockIntDetailsOperates
	 * @Description:查询入库明细信息,用于借用查询，查询所有操作
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findStockInDetailsOperates(
			Map<String, Object> conditionMap) throws SystemException;
	/**
	 * @Title:findBorrowDetails
	 * @Description:查询借用明细
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findBorrowDetails(Map<String, Object> conditionMap, String order,boolean isDesc, int pageNo, int pageSize) throws SystemException;

	/**
	 * @Title:countBorrowDetails
	 * @Description:查询借用明细记录数
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public int countBorrowDetails(Map<String,Object> conditionMap) throws SystemException;
	
	/**
	 * @Title:checkAllocateDetailsNotCompleted
	 * @Description:判断是否有未完成的调拨出库明细（针对某一个调拨单）
	 * @param stockoutId
	 * @return
	 * @throws SystemException
	 */
	public boolean checkAllocateDetailsNotCompleted(Long stockoutId) throws SystemException;
}

