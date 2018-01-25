package com.gboss.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.BorrowDetails;
import com.gboss.pojo.Stock;
import com.gboss.pojo.Stockin;
import com.gboss.pojo.Stockout;
import com.gboss.pojo.Writeoffs;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service
 * @ClassName:StockService
 * @Description:库存业务层接口
 * @author:zfy
 * @date:2013-8-30 上午11:19:34
 */
public interface StockService extends BaseService {
	/**
	 * @Title:findCurrentStocks
	 * @Description:查询库存状态
	 * @param pageSelect
	 * @param onlyStock 是否只查询库存，1:是：0：否
	 * @return
	 * @throws SystemException
	 */
	public Page<HashMap<String, Object>> findCurrentStocks(PageSelect<Map<String, Object>> pageSelect,boolean onlyStock) throws SystemException;
	/**
	 * @Title:findAllCurrentStocks
	 * @Description:查询所有库存状态
	 * @param map
	 * @param onlyStock 是否只查询库存，1:是：0：否
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findAllCurrentStocks(Map<String, Object> map,boolean onlyStock) throws SystemException ;
	/**
	 * @Title:findStockLtSetup
	 * @Description:库存预警，查询出库存数量小于库存设置的商品
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findStockLtSetup(
			Map<String, Object> conditionMap) throws SystemException;
	
	/**
	 * @Title:addStockOut
	 * @Description:添加出库信息
	 * @param stockout
	 * @param companyId 
	 * @param whsCode
	 * @param isNotChangeNum 是否没有在盘点调账
	 * @return
	 * @throws SystemException
	 */
	public HashMap<String, Object> addStockOut(Stockout stockout,String whsCode,boolean isNotChangeNum)throws SystemException;
	
	/**
	 * @Title:addStockIn
	 * @Description:添加入库信息
	 * @param stockout
	 * @return
	 * @throws SystemException
	 */
	public HashMap<String, Object> addStockIn(Stockin stockin,String whsCode)throws SystemException;
	/**
	 * @Title:saveBorrowTo
	 * @Description:保存挂账转移信息
	 * @param borrowDetails
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public int saveBorrowTo(List<Long> ids,Long borrowerId,String borrowerName)throws SystemException;
	
	/**
	 * @Title:findStockInOutDetailsPage
	 * @Description:分页查询出入库明细
	 * @param pageSelect
	 * @return
	 * @throws SystemException
	 */
	public Page<HashMap<String, Object>> findStockInOutDetailsPage(PageSelect<HashMap<String, Object>> pageSelect) throws SystemException;
	
	/**
	 * @Title:findStockInOutDetails
	 * @Description:分页查询出入库明细
	 * @param pageSelect
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findStockInOutDetails(HashMap<String, Object> map) throws SystemException;
	
	/**
	 * @Title:addWriteoffInfo
	 * @Description:核销
	 * @param writeoffs
	 * @return
	 * @throws SystemException
	 */
	public HashMap<String, Object> addWriteoffInfo(Writeoffs writeoffs,String whsCode) throws SystemException;
	
	/**
	 * @Title:findStockInDetails3
	 * @Description:查询入库详细,用于报表（新产品入库表）
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findAllStockInDetails3(Map<String, Object> conditionMap) throws SystemException;
	
	/**
	 * @Title:findStockInDetails3
	 * @Description:查询入库详细,用于报表（新产品入库表）
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public Page<HashMap<String, Object>> findStockInDetails3ByPage(PageSelect<Map<String, Object>> conditionMap) throws SystemException;
	
	/**
	 * @Title:findAllProducts
	 * @Description:按条件查询所有分公司商品
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findAllProducts(Map<String, Object> conditionMap) throws SystemException ;
	/**
	 * @Title:findStockInsPage
	 * @Description:分页查询入库信息
	 * @param pageSelect
	 * @return
	 * @throws SystemException
	 */
	public Page<HashMap<String, Object>> findStockInsPage(PageSelect<HashMap<String, Object>> pageSelect) throws SystemException;
	/**
	 * @Title:findStockOutsPage
	 * @Description:分页查询出库信息
	 * @param pageSelect
	 * @return
	 * @throws SystemException
	 */
	public Page<HashMap<String, Object>> findStockOutsPage(PageSelect<HashMap<String, Object>> pageSelect) throws SystemException;
	/**
	 * @Title:findStockInDetails4
	 * @Description:查询入库详细,用于入库主页
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findStockInDetails4(Map<String,Object> conditionMap) throws SystemException;
	/**
	 * @Title:findAllStockOuts
	 * @Description:查询所有出库信息
	 * @param map
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findAllStockOuts(Map<String, Object> map) throws SystemException;
	/**
	 * @Title:findAllStockINs
	 * @Description:查询所有入库信息
	 * @param map
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findAllStockIns(Map<String, Object> map) throws SystemException;

	/**
	 * @Title:findStockOutDetails4
	 * @Description:查询出库详细,用于出库主页
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findStockOutDetails4(Map<String,Object> conditionMap) throws SystemException;
	
	/**
	 * @Title:findBorrows
	 * @Description:查询用户借用信息
	 * @param conditionMap
	 * @return
	 * @throws
	 */
	public List<Map<String, Object>> findBorrows(Map<String,Object> conditionMap) throws SystemException;
	/**
	 * @Title:findOperates
	 * @Description:查询所有操作(出入库、核销、销账)，用于员工借用处
	 * @param conditionMap
	 * @return
	 * @throws
	 */
	public List<HashMap<String, Object>> findOperates(Map<String,Object> conditionMap) throws SystemException;
	
	/**
	 * @Title:getStockInOutNo
	 * @Description:获得入、出库单号
	 * @param companyId
	 * @param whsId
	 * @param whsCode
	 * @param isIn
	 * @return
	 * @throws SystemException
	 */
	public String getStockInOutNo(Long companyId,Long whsId,String whsCode,boolean isIn,Date today) throws SystemException;
	
	/**
	 * @Title:getWriteOffNo
	 * @Description:获得核销、销账单号
	 * @param companyId
	 * @param whsId
	 * @param isHx
	 * @return
	 * @throws SystemException
	 */
	public String getWriteOffsNo(Long companyId,Long orgId,Long whsId,String whsCode,boolean isHx) throws SystemException;
	/**
	 * @Title:getBorrowTotalNum
	 * @Description:获得用户借用商品总数量
	 * @param borrowerId
	 * @param channelId
	 * @param whsId
	 * @return
	 * @throws SystemException
	 */
	public Long getBorrowTotalNum(Long borrowerId,Long channelId,List<Long> whsIds) throws SystemException;
	/**
	 * @Title:saveBorrows
	 * @Description:保存挂账信息(初始化)
	 * @param borrowDetails
	 * @return
	 * @throws SystemException
	 */
	public int saveBorrows(BorrowDetails borrowDetails) throws SystemException ;
	/**
	 * @Title:deleteBorrows
	 * @Description:删除挂账信息(初始化)
	 * @param borrowDetails
	 * @return
	 * @throws SystemException
	 */
	public int deleteBorrows(List<Long> ids) throws SystemException ;
	
	/**
	 * @Title:saveStock
	 * @Description:保存库存信息
	 * @param stock
	 * @return
	 * @throws SystemException
	 */
	public int saveStock(Stock stock)throws SystemException;
	/**
	 * @Title:deleteStocks
	 * @Description:删除库存信息(初始化)
	 * @param borrowDetails
	 * @return
	 * @throws SystemException
	 */
	public int deleteStocks(List<Long> ids) throws SystemException ;
	/**
	 * @Title:findProductStock
	 * @Description:查询商品库存，汇总
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findProductStock(Map<String, Object> conditionMap) throws SystemException;
	/**
	 * @Title:findAllWriteoffs
	 * @Description:查询核销明细
	 * @param pageSelect
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findAllWriteoffs(Map<String, Object> map) throws SystemException;
	/**
	 * @Title:findWriteoffs
	 * @Description:分页查询核销明细
	 * @param map
	 * @return
	 * @throws SystemException
	 */
	public Page<HashMap<String, Object>> findWriteoffs(PageSelect<Map<String, Object>> pageSelect) throws SystemException;
	
	/**
	 * @Title:findWriteoffsXz
	 * @Description:分页查询销账明细
	 * @param pageSelect
	 * @return
	 * @throws SystemException
	 */
	public Page<HashMap<String, Object>> findWriteoffsXz(PageSelect<Map<String, Object>> pageSelect) throws SystemException;
	
	/**
	 * @Title:findAllWriteoffsXz
	 * @Description:查询销账明细
	 * @param map
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findAllWriteoffsXz(Map<String, Object> map) throws SystemException;
	/**
	 * @Title:findSaleOutDetails
	 * @Description:分页查询销售出库信息
	 * @param pageSelect
	 * @return
	 * @throws SystemException
	 */
	public Page<HashMap<String, Object>> findSaleOutDetails(PageSelect<Map<String, Object>> pageSelect) throws SystemException;
	/**
	 * @Title:findAllSaleOutDetails
	 * @Description:查询销售出库信息
	 * @param map
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findAllSaleOutDetails(Map<String, Object> map) throws SystemException;
	/**
	 * @Title:findWriteoffsDetails
	 * @Description:查询核销详细,用于核销主页
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findWriteoffsDetails(Map<String,Object> conditionMap) throws SystemException;
	/**
	 * @Title:findWriteoffsDetailsXz
	 * @Description:查询销账详细,用销账主页
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findWriteoffsDetailsXz(Map<String,Object> conditionMap) throws SystemException;
	/**
	 * @Title:findBorrowDetailsByPage
	 * @Description:查询借用挂账明细
	 * @param pageSelect
	 * @return
	 * @throws SystemException
	 */
	public Page<HashMap<String, Object>> findBorrowDetailsByPage(PageSelect<Map<String, Object>> pageSelect) throws SystemException;
	/**
	 * @Title:findAllBorrowDetails
	 * @Description:查询所有借用挂账明细
	 * @param pageSelect
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findAllBorrowDetails(Map<String, Object> map) throws SystemException;


	/**
	 * @Title:findWriteoffsAndDetails4Xz
	 * @Description:查询销账和明细（主要用于进销存明细报表）
	 * @param map
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String,Object>> findWriteoffsAndDetails4Xz(Map<String, Object> map) throws SystemException;

	/**
	 * @Title:findWriteoffsAndDetails
	 * @Description:查询核销和明细（主要用于经销存明细报表）
	 * @param map
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String,Object>> findWriteoffsAndDetails(Map<String, Object> map) throws SystemException;
}

