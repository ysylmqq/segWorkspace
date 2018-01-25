package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.SalesPack;
import com.gboss.pojo.SalesProduct;
import com.gboss.pojo.Salescontract;

/**
 * @Package:com.gboss.dao
 * @ClassName:SalesContractDao
 * @Description:销售合同数据持久层接口
 * @author:zfy
 * @date:2013-8-27 上午11:21:29
 */
public interface SalesContractDao extends BaseDao {
	/**
	 * @Title:findSalesContracts
	 * @Description:分页查询销售合同
	 * @param conditionMap
	 * @param order
	 * @param isDesc
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findSalesContracts(Map<String,Object> conditionMap, String order,boolean isDesc,int pageNo,int pageSize)throws SystemException;
	
	/**
	 * @Title:countSalesContracts
	 * @Description:获得销售合同记录数
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public int countSalesContracts(Map<String, Object> conditionMap) throws SystemException;
	/**
	 * @Title:findSalesContractProducts
	 * @Description:分页查询销售合同和商品
	 * @param conditionMap
	 * @param order
	 * @param isDesc
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findSalesContractProducts(Map<String,Object> conditionMap, String order,boolean isDesc,int pageNo,int pageSize)throws SystemException;
	/**
	 * @Title:countSalesContractProducts
	 * @Description:获得查询销售合同和商品记录数
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public int countSalesContractProducts(Map<String, Object> conditionMap) throws SystemException;

	/**
	 * @Title:checkSalescontractCode
	 * @Description:判断编号是否存在
	 * @param salescontract
	 * @return true:已存在；false:不存在
	 * @throws SystemException
	 */
	public boolean checkSalescontractCode(Salescontract salescontract) throws SystemException;
	/**
	 * @Title:checkSalescontractCode
	 * @Description:判断编号是否存在
	 * @param salescontract
	 * @return true:已存在；false:不存在
	 * @throws SystemException
	 */
	public boolean checkSalescontractName(Salescontract salescontract) throws SystemException;
	/**
	 * @Title:deleteSalesProductsByContractId
	 * @Description:根据销售合同id删除合同与商品的关系
	 * @param contractId
	 * @return
	 * @throws SystemException
	 */
	public int deleteSalesProductsByContractId(Long contractId) throws SystemException;
	/**
	 * @Title:deleteSalesPacksByContractId
	 * @Description:根据销售合同id删除合同与套餐的关系
	 * @param contractId
	 * @return
	 * @throws SystemException
	 */
	public int deleteSalesPacksByContractId(Long contractId) throws SystemException;
	/**
	 * @Title:findSalesProducts
	 * @Description:根据合同ID查询销售合同与商品的关系
	 * @param contractId
	 * @return
	 * @throws SystemException
	 */
	public List<SalesProduct> findSalesProducts(Long contractId)throws SystemException ;
	/**
	 * @Title:findSalesPacks
	 * @Description:根据合同ID查询销售合同与套餐的关系
	 * @param contractId
	 * @return
	 * @throws SystemException
	 */
	public List<SalesPack> findSalesPacks(Long contractId)throws SystemException ;

	/**
	 * @Title:getSalesProductByProductId
	 * @Description:根据商品ID得到合同中的价格
	 * @param productId
	 * @param channelId
	 * @param companyId
	 * @return
	 * @throws SystemException
	 */
	public SalesProduct getSalesProductByProductId(Long productId,Long channelId,Long companyId) throws SystemException;
	
	/**
	 * @Title:getSalesContract
	 * @Description:根据合同ID查找商品明细
	 * @param contractId
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findSalesProductsByContractId(Long contractId) throws SystemException;
	/**
	 * @Title:findSalesPacksByContractId
	 * @Description:根据合同ID查找套餐明细
	 * @param contractId
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findSalesPacksByContractId(Long contractId) throws SystemException;
	/**
	 * @Title:getMaxSalesContractNo
	 * @Description:采购合同最大条数
	 * @param companyId
	 * @param date
	 * @return
	 * @throws SystemException
	 */
	public int getMaxSalesContractNo(Long companyId,String date) throws SystemException;
	/**
	 * @Title:updateStatusById
	 * @Description:修改合同状态
	 * @param salescontracts
	 * @return
	 * @throws SystemException
	 */
	public int updateStatus(Salescontract salescontracts) throws SystemException;

	/**
	 * @Title:updateDetaisStatusById
	 * @Description:修改合同明细状态
	 * @param salescontracts
	 * @return
	 * @throws SystemException
	 */
	public int updateDetaisStatus(Salescontract salescontract) throws SystemException;
}