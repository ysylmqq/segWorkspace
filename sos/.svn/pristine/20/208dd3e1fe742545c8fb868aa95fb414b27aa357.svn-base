package com.gboss.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ldap.objectClasses.CommonCompany;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.dao.SupplyContractDao;
import com.gboss.pojo.SupplyBranch;
import com.gboss.pojo.SupplyDetails;
import com.gboss.pojo.Supplycontracts;
import com.gboss.service.SupplyContractService;
import com.gboss.util.DateUtil;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.UUIDCreater;
import com.gboss.util.Utils;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:SupplyContractServiceImp
 * @Description:总部供货合同管理业务层实现类
 * @author:zfy
 * @date:2013-8-20 下午4:05:08
 */
@Service("supplyContractService")
@Transactional
public class SupplyContractServiceImp extends BaseServiceImpl implements
		SupplyContractService {
	protected static final Logger LOGGER = LoggerFactory.getLogger(SupplyContractServiceImp.class);
	
	@Autowired  
	@Qualifier("supplyContractDao")
	private SupplyContractDao supplyContractDao;

	@Override
	public HashMap<String, Object> addSupplyContractBranchDetail(Supplycontracts supplycontracts)
			throws SystemException {
		HashMap<String, Object> result=new HashMap<String, Object>();
		boolean flag=true;
		String msg=SystemConst.OP_SUCCESS;
		if(supplycontracts==null){
			flag=false;
			msg="参数不正确!";
		}else{
			//先判断供货合同编号存不存在
			if(StringUtils.isNotBlank(supplycontracts.getCode())&&supplyContractDao.checkSupplyContractCode(supplycontracts)){
				//自动生成合同编号
				flag=false;
				msg = "总部供货合同编号为[" + supplycontracts.getCode() + "]的已存在,将自动生成新的合同编号!";
				result.put("code", getSupplyContractNo());
			}else{
				//再判断供货合同名称存不存在
				if(StringUtils.isNotBlank(supplycontracts.getName())&&supplyContractDao.checkSupplyContractName(supplycontracts)){
					flag = false;
					msg = "总部供货合同名称为[" + supplycontracts.getName() + "]的已存在";
				}else{
					List<SupplyBranch> supplyBranchs=supplycontracts.getSupplyBranchs();
					//List<SupplyDetails> supplyDetails=supplycontracts.getSupplyDetails();
					//添加供货合同
					supplyContractDao.save(supplycontracts);
					Long contractId=supplycontracts.getId();
					
					//添加供货合同与子公司的关系
					if(supplyBranchs!=null&&!supplyBranchs.isEmpty()){
						for (SupplyBranch supplyBranch:supplyBranchs) {
							supplyBranch.setSupplyId(contractId);
							supplyContractDao.save(supplyBranch);
						}
					}
					/*//添加供货合同与商品的详细信息
					if(supplyDetails!=null&&!supplyDetails.isEmpty()){
						for (SupplyDetails supplyDetails2 : supplyDetails) {
							supplyDetails2.setId(UUIDCreater.getUUID());
							supplyDetails2.setSupplyId(contractId);
							supplyContractDao.save(supplyDetails2);
						}
					}*/
					
					result.put("contractId", contractId);
				}
			}
		}
		result.put(SystemConst.SUCCESS, flag);
		result.put(SystemConst.MSG, msg);
		return result;
	}

	@Override
	public int updateSupplyContractBranchDetail(Supplycontracts supplycontracts)
			throws SystemException {
		if(supplycontracts==null){
			return -1;
		}
		int result=1;
		//修改供货合同
		Long contractId=supplycontracts.getId();
		//操作之前，判断存在不存在
		if(supplyContractDao.get(Supplycontracts.class, contractId)!=null){
			//先判断供货合同编号存不存在
			if(StringUtils.isNotBlank(supplycontracts.getCode())&&supplyContractDao.checkSupplyContractCode(supplycontracts)){
				result=2;
			}else{
				//再判断供货合同名称存不存在
				if(StringUtils.isNotBlank(supplycontracts.getName())&&supplyContractDao.checkSupplyContractName(supplycontracts)){
					result=3;
				}else{
					List<SupplyBranch> supplyBranchs=supplycontracts.getSupplyBranchs();
					//List<SupplyDetails> supplyDetails=supplycontracts.getSupplyDetails();
				
					supplyContractDao.merge(supplycontracts);
					//先删除供货合同与子公司的关系
					supplyContractDao.deleteBranchsByContractId(contractId);
					//再添加供货合同与子公司的关系
					if(supplyBranchs!=null&&!supplyBranchs.isEmpty()){
						for (SupplyBranch supplyBranch:supplyBranchs) {
							supplyBranch.setSupplyId(contractId);
							supplyContractDao.save(supplyBranch);
						}
					}
					/*//先删除供货合同与商品的详细信息
					supplyContractDao.deleteDetailsByContractId(contractId);
					//再添加供货合同与商品的详细信息
					if(supplyDetails!=null&&!supplyDetails.isEmpty()){
						for (SupplyDetails supplyDetails2 : supplyDetails) {
							supplyDetails2.setId(UUIDCreater.getUUID());
							supplyDetails2.setSupplyId(contractId);
							supplyContractDao.save(supplyDetails2);
						}
					}*/
			}
			}
				
		}else{
			result=0;
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> findSupplyContracts(
			Map<String, Object> conditionMap) throws SystemException{
		//查询合同信息
		List<Map<String,Object>> supplycontracts2=supplyContractDao.findSupplyContracts(conditionMap,null,true,0,0);
		/*List<String> contractIds=new ArrayList<String>();
		if(supplycontracts2!=null&&!supplycontracts2.isEmpty()){
			for (Map<String,Object> supplycontracts3 : supplycontracts2) {
				contractIds.add(supplycontracts3.get("id").toString());
			}
		}
		
		//查询合同与子公司的关系
		List<SupplyBranch> supplyBranchs=supplyContractDao.findSupplyBranchs(contractIds);
		Map<String, Object> map=null;
		OpenLdap openLdap=new OpenLdap();
		CommonCompany commonCompany=null;
		String orgName=null;
		if(supplyBranchs!=null&&!supplyBranchs.isEmpty()){
			for (Map<String,Object> supplycontracts3 : supplycontracts2) {
				if(supplyBranchs!=null&&!supplyBranchs.isEmpty()){
					for (SupplyBranch supplyBranch : supplyBranchs) {
						if(supplycontracts3.get("id").toString().equals(supplyBranch.getSupplyId())){
							 commonCompany=openLdap.getCompanyByOrgId(supplyBranch.getOrgId());
							 if(commonCompany!=null){
								orgName=commonCompany.getCompanyname();
							 }
							 supplycontracts3.put("branchName", orgName);
							 //只显示第一个
							 break;
						}
					}
				}
			}
		}*/
		return supplycontracts2;
	}

	@Override
	public int deleteSupplyContractBranchDetails(List<Long> contractIds) throws SystemException{
		int result=1;
		if(contractIds==null || contractIds.isEmpty()){
			result=-1;
		}else{
			for (Long contractId : contractIds) {
				//操作之前，判断存在不存在
				if(supplyContractDao.get(Supplycontracts.class, contractId)!=null){
					//先删除合同与商品信息关系
					supplyContractDao.deleteDetailsByContractId(contractId);
					//再删除合同与子公司直接的关系
					supplyContractDao.deleteBranchsByContractId(contractId);
					//最后删除合同
					supplyContractDao.delete(Supplycontracts.class,contractId);
				}
			}
			
		}
		return result;
	}

	@Override
	public Long copySupplyContractBranchDetails(Long contractId)
			throws SystemException {
		if(contractId!=null){
			return null;
		}
		Long result=null;
		//查询合同信息
		//Supplycontracts supplycontracts=supplyContractDao.get(Supplycontracts.class,contractId);
		Supplycontracts supplycontracts=supplyContractDao.getSuppplyContractById(contractId);
		if(supplycontracts!=null){
			List<Long> contractIds=new ArrayList<Long>();
			if(contractId!=null){
				contractIds.add(contractId);
			}
			
			//查询合同与子公司的关系
			List<SupplyBranch> supplyBranchs=supplyContractDao.findSupplyBranchs(contractIds);
			
			//查询合同与商品信息的关系
			List<SupplyDetails> supplyDetails=supplyContractDao.findSupplyDetais(contractId);
			
			//添加合同
			supplyContractDao.save(supplycontracts);
			result=supplycontracts.getId();
			
			SupplyBranch supplyBranch2=null;
			SupplyDetails supplyDetails3=null;
			//添加合同与子公司的关系
			for (SupplyBranch supplyBranch : supplyBranchs) {
				supplyBranch2=new SupplyBranch();
				//将持久状态对象变成瞬间状态对象
				//避免出现错误：identifier of an instance of com.gboss.pojo.Supplycontracts was altered from xx to yy
				BeanUtils.copyProperties(supplyBranch, supplyBranch2);
				supplyBranch2.setSupplyId(result);
				supplyContractDao.save(supplyBranch2);
			}
			//添加合同与商品信息的关系
			for (SupplyDetails supplyDetails2 : supplyDetails) {
				supplyDetails3=new SupplyDetails();
				//将持久状态对象变成瞬间状态对象
				//避免出现错误：identifier of an instance of com.gboss.pojo.Supplycontracts was altered from xx to yy
				BeanUtils.copyProperties(supplyDetails2, supplyDetails3);
				supplyDetails3.setSupplyId(result);
				supplyContractDao.save(supplyDetails3);
			}
		}
		
		return result;
	}

	@Override
	public Page<Map<String, Object>> findSupplyContractsByPage(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total=supplyContractDao.countSupplyContracts(pageSelect.getFilter());
		//查询合同信息
		List<Map<String, Object>> supplycontracts2=supplyContractDao.findSupplyContracts(pageSelect.getFilter(),pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		/*List<String> contractIds=new ArrayList<String>();
		if(supplycontracts2!=null&&!supplycontracts2.isEmpty()){
			for (Map<String,Object> supplycontracts3 : supplycontracts2) {
				contractIds.add(supplycontracts3.get("id").toString());
			}
		}
		
		//查询合同与子公司的关系
		List<SupplyBranch> supplyBranchs=supplyContractDao.findSupplyBranchs(contractIds);
		Map<String, Object> map=null;
		OpenLdap openLdap=new OpenLdap();
		CommonCompany commonCompany=null;
		String orgName=null;
		if(supplyBranchs!=null&&!supplyBranchs.isEmpty()){
			for (Map<String,Object> supplycontracts3 : supplycontracts2) {
				if(supplyBranchs!=null&&!supplyBranchs.isEmpty()){
					for (SupplyBranch supplyBranch : supplyBranchs) {
						if(supplycontracts3.get("id").toString().equals(supplyBranch.getSupplyId())){
							 commonCompany=openLdap.getCompanyByOrgId(supplyBranch.getOrgId());
							 if(commonCompany!=null){
								orgName=commonCompany.getCompanyname();
							 }
							 supplycontracts3.put("branchName", orgName);
							 //只显示第一个
							 break;
						}
					}
				}
			}
		}*/
		return PageUtil.getPage(total, pageSelect.getPageNo(), supplycontracts2, pageSelect.getPageSize());
	
	}

	@Override
	public List<HashMap<String, Object>> findContractProductsByContractId(
			Long contractId) throws SystemException {
		if(contractId==null){
			return null;
		}else{
			return supplyContractDao.findContractProductsByContractId(contractId);
		}
	}

	@Override
	public String getSupplyContractNo() throws SystemException {
		//流水号加1，前面不足4位，用0补充
		String serialNoStr=Utils.formatSerial(supplyContractDao.getMaxSupplyContractNo(DateUtil.formatToday()));
		return SystemConst.SUPPLY_NO_PREFIX+DateUtil.format(new Date(), DateUtil.YMD)+serialNoStr;
	}

	@Override
	public int updateStatus(Supplycontracts supplycontracts)
			throws SystemException {
		if(supplycontracts==null || StringUtils.isNullOrEmpty(supplycontracts.getId())){
			return 0;
		}
		supplycontracts.setCheckStamp(new Date());
		//修改合同明细的状态
		supplyContractDao.updateDetaisStatus(supplycontracts);
		//修改合同状态
		return supplyContractDao.updateStatus(supplycontracts);
	}

	@Override
	public int updateDetails(Long contractId, List<SupplyDetails> supplyDetails)
			throws SystemException {
		if(contractId==null){
			return -1;
		}
		int result=1;
		//操作之前，判断存在不存在
		Supplycontracts supplycontract=supplyContractDao.get(Supplycontracts.class, contractId);
		if(supplycontract!=null){
			//查询生效日期、到期日期
			String validDate=supplycontract.getValidDate();
			String natureDate=supplycontract.getMatureDate();
			Integer status=supplycontract.getStatus();
			//先删除供货合同与商品的详细信息
			supplyContractDao.deleteDetailsByContractId(contractId);
			//再添加供货合同与商品的详细信息
			if(supplyDetails!=null&&!supplyDetails.isEmpty()){
				for (SupplyDetails supplyDetails2 : supplyDetails) {
					supplyDetails2.setSupplyId(contractId);
					supplyDetails2.setStatus(status);
					supplyDetails2.setValidDate(validDate);
					supplyDetails2.setMatureDate(natureDate);
					supplyContractDao.save(supplyDetails2);
				}
			}
				
		}else{
			result=0;
		}
		return result;
	}

	@Override
	public Supplycontracts getSupplyContract(Long contractId)
			throws SystemException {
		if(contractId==null){
			return null;
		}
		Supplycontracts	result=supplyContractDao.get(Supplycontracts.class, contractId);
		if(result!=null){
			result.setSuppplyDetailsMap(supplyContractDao.findContractProductsByContractId(contractId));
			List<Long> contractIds=new ArrayList<Long>();
			contractIds.add(contractId);
			result.setSupplyBranchs(supplyContractDao.findSupplyBranchs(contractIds));
		}
		return result;
	}

}

