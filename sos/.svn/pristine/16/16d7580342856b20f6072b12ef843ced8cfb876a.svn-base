package com.gboss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.SupplyBranch;
import com.gboss.pojo.SupplyDetails;
import com.gboss.pojo.Supplycontracts;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service
 * @ClassName:SupplyContractService
 * @Description:总部供货合同管理业务层接口
 * @author:zfy
 * @date:2013-8-20 下午4:04:12
 */
public interface SupplyContractService extends BaseService {
	/**
	 * @Title:addSupplyContractBranchDetai
	 * @Description:添加总部供货合同信息，包括产品详细、与子公司的关系
	 * @param supplycontracts
	 * @return 
	 * @throws SystemException
	 */
	public HashMap<String, Object> addSupplyContractBranchDetail(Supplycontracts supplycontracts) throws SystemException;
	/**
	 * @Title:updateSupplyContractBranchDetai
	 * @Description:修改总部供货合同信息，包括产品详细、与子公司的关系
	 * @param supplycontracts
	 * @return 1:成功，2:编号已存在，3：合同名称已存在
	 * @throws SystemException
	 */
	public int updateSupplyContractBranchDetail(Supplycontracts supplycontracts) throws SystemException;
	
	/**
	 * @Title:findSupperContracts
	 * @Description:查询总部供货合同信息
	 * @param supplycontracts
	 * @return
	 * @throws SystemException
	 */
	public List<Map<String, Object>> findSupplyContracts(Map<String, Object> map) throws SystemException;
	
	/**
	 * @Title:findSupplyContractsByPage
	 * @Description:分页查询总部供货合同信息
	 * @param supplycontracts
	 * @return
	 * @throws SystemException
	 */
	public Page<Map<String, Object>> findSupplyContractsByPage(PageSelect<Map<String, Object>> pageSelect) throws SystemException;

	/**
	 * @Title:deleteSupplyContractBranchDetails
	 * @Description:根据合同ID删除合同以及合同相关的信息
	 * @param contractId
	 * @return
	 * @throws SystemException
	 */
	public int deleteSupplyContractBranchDetails(List<Long> contractIds)throws SystemException;
	
	/**
	 * @Title:copySupplyContractBranchDetails
	 * @Description:克隆供货合同
	 * @param contractId
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public Long copySupplyContractBranchDetails(Long contractId)throws SystemException;
	/**
	 * @Title:findContractProductsByContractId
	 * @Description:根据合同ID查找商品明细
	 * @param contractId
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findContractProductsByContractId(Long contractId) throws SystemException;
	/**
	 * @Title:getSupplyContractNo
	 * @Description:采购合同最大条数
	 * @return
	 * @throws SystemException
	 */
	public String getSupplyContractNo() throws SystemException;
	
	/**
	 * @Title:getSupplyContract
	 * @Description:查询单个合同
	 * @param contractId
	 * @return
	 * @throws SystemException
	 */
	public Supplycontracts getSupplyContract(Long contractId) throws SystemException;
	/**
	 * @Title:updateStatus
	 * @Description:修改合同状态
	 * @param supplycontracts
	 * @return
	 * @throws SystemException
	 */
	public int updateStatus(Supplycontracts supplycontracts) throws SystemException;
	
	/**
	 * @Title:updateDetails
	 * @Description:批量修改商品明细
	 * @param id
	 * @param supplyDetails
	 * @return
	 * @throws SystemException
	 */
	public int updateDetails(Long id,List<SupplyDetails> supplyDetails) throws SystemException;
}

