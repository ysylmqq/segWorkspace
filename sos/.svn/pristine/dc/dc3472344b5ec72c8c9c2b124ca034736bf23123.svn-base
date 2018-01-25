package com.gboss.service;

import java.util.HashMap;
import java.util.List;

import com.gboss.comm.SystemException;
import com.gboss.pojo.SalesProduct;
import com.gboss.pojo.Salescontract;
import com.gboss.pojo.SupplyDetails;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service
 * @ClassName:SalesContractService
 * @Description:销售合同业务层接口
 * @author:zfy
 * @date:2013-8-27 上午11:23:28
 */
public interface SalesContractService extends BaseService {
	/**
	 * @Title:findSalesContracts
	 * @Description:查询销售合同
	 * @param pageSelect
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public Page<HashMap<String, Object>> findSalesContracts(PageSelect<HashMap<String, Object>> pageSelect) throws SystemException;
	/**
	 * findSalesContractProducts
	 * @Description:查询销售合同和商品
	 * @param pageSelect
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public Page<HashMap<String, Object>> findSalesContractProducts(PageSelect<HashMap<String, Object>> pageSelect) throws SystemException;

	/**
	 * @Title:findAllSalesContracts
	 * @Description:查询所有销售合同
	 * @param map
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public List<HashMap<String, Object>> findAllSalesContracts(HashMap<String, Object> map) throws SystemException;
	
	/**
	 * @Title:addSalesContract
	 * @Description:添加销售合同
	 * @param salescontract
	 * @return
	 * @throws SystemException
	 */
	public HashMap<String, Object> addSalesContract(Salescontract salescontract) throws SystemException;
	/**
	 * @Title:updateSalesContract
	 * @Description:修改销售合同
	 * @param salescontract
	 * @return
	 * @throws SystemException
	 */
	public int updateSalesContract(Salescontract salescontract) throws SystemException;
	/**
	 * @Title:getSalesContract
	 * @Description:查找销售合同
	 * @param contractId
	 * @return
	 * @throws SystemException
	 */
	public Salescontract getSalesContract(Long contractId) throws SystemException;
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
	 * @Title:getSalesContractNo
	 * @Description:采购合同最大条数
	 * @param companyId
	 * @param date
	 * @return
	 * @throws SystemException
	 */
	public String getSalesContractNo(Long companyId,Long userId) throws SystemException;
	
	/**
	 * @Title:updateStatusById
	 * @Description:修改合同状态
	 * @param salescontract
	 * @return
	 * @throws SystemException
	 */
	public int updateStatus(Salescontract salescontract) throws SystemException;
	
	/**
	 * @Title:updateFillingById
	 * @Description:修改合同是否归档
	 * @param salescontract
	 * @return
	 * @throws SystemException
	 */
	public int updateFillingById(Salescontract salescontract) throws SystemException;
	
	/**
	 * @Title:updateDetails
	 * @Description:批量修改商品明细
	 * @param id
	 * @param salesProducts
	 * @return
	 * @throws SystemException
	 */
	public int updateDetails(Long id,List<SalesProduct> salesProducts) throws SystemException;
	
	/**
	 * @Title:deleteSalesContracts
	 * @Description:根据合同ID删除合同以及合同相关的信息
	 * @param contractId
	 * @return
	 * @throws SystemException
	 */
	public int deleteSalesContracts(List<Long> contractIds)throws SystemException;
	/**
	 * @Title:getSalesProductByProductId
	 * @Description:根据商品ID得到销售合同中的价格
	 * @param productId
	 * @param channelId
	 * @param companyId
	 * @return
	 * @throws SystemException
	 */
	public SalesProduct getSalesProductByProductId(Long productId,Long channelId,Long companyId) throws SystemException;
}

