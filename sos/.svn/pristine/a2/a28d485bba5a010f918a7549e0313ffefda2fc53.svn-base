package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.gboss.comm.SystemException;
import com.gboss.pojo.SupplyBranch;
import com.gboss.pojo.SupplyDetails;
import com.gboss.pojo.Supplycontracts;

/**
 * @Package:com.gboss.dao
 * @ClassName:SupplyContractDao
 * @Description:总部供货合同管理数据持久层接口
 * @author:zfy
 * @date:2013-8-20 下午4:01:26
 */
public interface SupplyContractDao extends BaseDao {
	/**
	 * @Title:checkSupplyContractCode
	 * @Description:检查供货合同编号是否存在
	 * @param supplycontracts
	 * @return
	 * @throws SystemException
	 */
	public boolean checkSupplyContractCode(Supplycontracts supplycontracts) throws SystemException;
	
	/**
	 * @Title:checkSupplyContractName
	 * @Description:检查供货合同名称是否存在
	 * @param supplycontracts
	 * @return
	 * @throws SystemException
	 */
	public boolean checkSupplyContractName(Supplycontracts supplycontracts) throws SystemException;
	/**
	 * @Title:deleteDetailsByContractId
	 * @Description:根据合同ID删除合同与商品详细关系
	 * @return
	 * @throws SystemException
	 */
	public int deleteDetailsByContractId(Long contractId) throws SystemException;
	/**
	 * @Title:deleteBranchsByContractId
	 * @Description:根据合同ID删除合同子公司的关系
	 * @return
	 * @throws SystemException
	 */
	public int deleteBranchsByContractId(Long contractId) throws SystemException;
	
	/**
	 * @Title:findSupplyDetais
	 * @Description:根据合同ID查询相关的商品信息
	 * @param contractId
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public List<SupplyDetails> findSupplyDetais(Long contractId) throws SystemException;
	/**
	 * @Title:findSupplyBranchss
	 * @Description:根据合同ID查询相关的子公司信息
	 * @param contractIds
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public List<SupplyBranch> findSupplyBranchs(List<Long> contractIds) throws SystemException;
	
	/**
	 * @Title:findSupplyContracts
	 * @Description:查询总部供货合同
	 * @param supplycontracts
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public List<Map<String, Object>> findSupplyContracts(Map<String, Object> map, String order,boolean isDesc, int pageNo, int pageSize) throws SystemException;
	/**
	 * @Title:getSuppplyContractById
	 * @Description:根据ID查找总部供货合同
	 * @param contractId
	 * @return
	 * @throws
	 */
	public Supplycontracts getSuppplyContractById(Long contractId) throws SystemException;
	
	/**
	 * @Title:countSupplyContracts
	 * @Description:查询采购合同记录数
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public int countSupplyContracts(Map<String, Object> conditionMap) throws SystemException;
	/**
	 * @Title:findContractProductsByContractId
	 * @Description:根据合同ID查找商品明细
	 * @param contractId
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findContractProductsByContractId(Long contractId) throws SystemException;
	
	/**
	 * @Title:getMaxSupplyContractNo
	 * @Description:采购合同最大条数
	 * @param date
	 * @return
	 * @throws SystemException
	 */
	public int getMaxSupplyContractNo(String date) throws SystemException;
	
	/**
	 * @Title:updateStatusById
	 * @Description:修改合同状态
	 * @param id
	 * @param status
	 * @return
	 * @throws SystemException
	 */
	public int updateStatus(Supplycontracts supplycontracts) throws SystemException;

	/**
	 * @Title:updateDetaisStatusById
	 * @Description:修改合同明细状态
	 * @param supplycontracts
	 * @return
	 * @throws SystemException
	 */
	public int updateDetaisStatus(Supplycontracts supplycontracts) throws SystemException;
}

