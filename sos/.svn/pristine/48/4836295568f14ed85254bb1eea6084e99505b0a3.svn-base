package com.gboss.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ldap.objectClasses.CommonCompany;
import ldap.objectClasses.CommonOperator;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.dao.SalesContractDao;
import com.gboss.pojo.Channel;
import com.gboss.pojo.SalesPack;
import com.gboss.pojo.SalesProduct;
import com.gboss.pojo.Salescontract;
import com.gboss.pojo.SupplyDetails;
import com.gboss.pojo.Supplycontracts;
import com.gboss.service.SalesContractService;
import com.gboss.util.DateUtil;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.UUIDCreater;
import com.gboss.util.Utils;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:SalesContractServiceImpl
 * @Description:销售合同业务层实现类
 * @author:zfy
 * @date:2013-8-27 上午11:24:30
 */
@Service("salesContractService")
@Transactional
public class SalesContractServiceImpl extends BaseServiceImpl implements
		SalesContractService {
	protected static final Logger LOGGER = LoggerFactory.getLogger(SalesContractServiceImpl.class);
	
	@Autowired  
	@Qualifier("salesContractDao")
	private SalesContractDao salesContractDao;
	
	@Override
	public Page<HashMap<String, Object>> findSalesContracts(
			PageSelect<HashMap<String, Object>> pageSelect)
			throws SystemException {
		if(pageSelect==null){
			return null;
		}else{
	        int total=salesContractDao.countSalesContracts(pageSelect.getFilter());
	        List<HashMap<String, Object>> channelList=salesContractDao.findSalesContracts(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
	     /*   OpenLdap openLdap=new OpenLdap();
	        String contractorName=null;
	        CommonOperator commonOperator=null;
	        if(channelList!=null&&!channelList.isEmpty()){
	        	 for (HashMap<String, Object> hashMap : channelList) {
	        		 if(hashMap.get("contractorId")!=null){
	        			commonOperator=openLdap.getOperatorById(hashMap.get("contractorId").toString());
	        		    if(commonOperator!=null){
	        		    	contractorName=commonOperator.getOpname();
	        		    }
	        		 }
	        		 //设置签约人姓名
	        		 hashMap.put("contractorName", contractorName);
	 			}
	        }*/
	        return PageUtil.getPage(total, pageSelect.getPageNo(), channelList, pageSelect.getPageSize());
		}
	}
	@Override
	public HashMap<String, Object> addSalesContract(Salescontract salescontract)
			throws SystemException {
		HashMap<String, Object> result=new HashMap<String, Object>();
		boolean flag=true;
		String msg=SystemConst.OP_SUCCESS;
		if(salescontract==null){
			flag=false;
			msg="参数不正确!";
		}else{
			//先判断供货合同编号存不存在
			if(StringUtils.isNotBlank(salescontract.getCode())&&salesContractDao.checkSalescontractCode(salescontract)){
				//自动生成合同编号
				flag=false;
				msg = "销售合同编号为[" + salescontract.getCode() + "]的已存在,将自动生成新的合同编号!";
				result.put("code", getSalesContractNo(salescontract.getCompanyId(),salescontract.getUserId()));
			}else{
				//再判断销售合同名称存不存在
				if(StringUtils.isNotBlank(salescontract.getName())&&salesContractDao.checkSalescontractName(salescontract)){
					flag=false;
					msg = "合同名称为[" + salescontract.getName() + "]的已存在!";
				}else{
					List<SalesProduct> salesProducts=salescontract.getSalesProducts();
					List<SalesPack> salesPacks=salescontract.getSalesPacks();
					//添加合同
					Integer status=0;
					//salescontract.setValidDate(salescontract.getSignDate()==null?"":salescontract.getSignDate());
					salescontract.setStatus(status);//无效
					salescontract.setIsFiling(0);//未归档
					salesContractDao.save(salescontract);
					Long contractId=salescontract.getId();
					
					//查询生效日期、到期日期、代理商
					Date validDate=salescontract.getValidDate();
					Date matureDate=salescontract.getMatureDate();
					Long channelId=salescontract.getChannelId();
					//添加合同与商品的关系
					if(salesProducts!=null&&!salesProducts.isEmpty()){
						for (SalesProduct salesProduct:salesProducts) {
							salesProduct.setContractId(contractId);
							salesProduct.setStatus(status);
							salesProduct.setValidDate(validDate);
							salesProduct.setMatureDate(matureDate);
							salesProduct.setChannelId(channelId);
							salesContractDao.save(salesProduct);
						}
					}
					//添加合同与套餐的关系
					if(salesPacks!=null&&!salesPacks.isEmpty()){
						for (SalesPack salesPack : salesPacks) {
							salesPack.setContractId(contractId);
							salesContractDao.save(salesPack);
						}
					}
				}
		}
		}
		result.put(SystemConst.SUCCESS, flag);
		result.put(SystemConst.MSG, msg);
		return result;
	}
	@Override
	public int updateSalesContract(Salescontract salescontract)
			throws SystemException {
		if(salescontract==null || StringUtils.isNullOrEmpty(salescontract.getId())){
			return -1;
		}
		int result=1;
		//修改合同
		Long contractId=salescontract.getId();
		Salescontract oldSalescontract=salesContractDao.get(Salescontract.class, contractId);
		//操作之前，判断存在不存在
		if(contractId!=null && oldSalescontract!=null){
			//先判断销售合同编号存不存在
			if(StringUtils.isNotBlank(salescontract.getCode())&&salesContractDao.checkSalescontractCode(salescontract)){
				result=2;
			}else{
				//再判断销售合同名称存不存在
				if(StringUtils.isNotBlank(salescontract.getName())&&salesContractDao.checkSalescontractName(salescontract)){
					result=3;
				}else{
					List<SalesProduct> salesProducts=salescontract.getSalesProducts();
					List<SalesPack> salesPacks=salescontract.getSalesPacks();
					//修改合同
					//salescontract.setValidDate(salescontract.getSignDate()==null?"":salescontract.getSignDate());
					salescontract.setStatus(0);
					salescontract.setIsFiling(oldSalescontract.getIsFiling());
					salescontract.setUserId(oldSalescontract.getUserId());
					salescontract.setUserName(oldSalescontract.getUserName());
					salesContractDao.merge(salescontract);
					
					//查询生效日期、到期日期
					Date validDate=salescontract.getValidDate();
					Date matureDate=salescontract.getMatureDate();
					Integer status=salescontract.getStatus();
					Long channelId=salescontract.getChannelId();
					//合同与商品的关系:先删后加
					salesContractDao.deleteSalesProductsByContractId(contractId);
					if(salesProducts!=null&&!salesProducts.isEmpty()){
						for (SalesProduct salesProduct:salesProducts) {
							salesProduct.setContractId(contractId);
							salesProduct.setStatus(status);
							salesProduct.setValidDate(validDate);
							salesProduct.setMatureDate(matureDate);
							salesProduct.setChannelId(channelId);
							salesContractDao.save(salesProduct);
						}
					}
					//合同与套餐的关系:先删后加
					salesContractDao.deleteSalesPacksByContractId(contractId);
					if(salesPacks!=null&&!salesPacks.isEmpty()){
						for (SalesPack salesPack : salesPacks) {
							salesPack.setContractId(contractId);
							salesContractDao.save(salesPack);
						}
					}
				}
			}
		}else{	
			result=0;
		}
		return result;
	}
	@Override
	public Salescontract getSalesContract(Long contractId)
			throws SystemException {
		if(contractId==null){
			return null;
		}
		Salescontract salescontract=salesContractDao.get(Salescontract.class, contractId);
		if(salescontract!=null && StringUtils.isNotNullOrEmpty(salescontract.getChannelId())){
			//查询代理商名称
			Channel channel=salesContractDao.get(Channel.class, salescontract.getChannelId());
			if(channel!=null){
				salescontract.setChannelName(channel.getName());
			}
		}
		//获得代理商名称
		/*List<SalesProduct> salesProducts=null;
		List<SalesPack> salesPacks=null;
		if(salescontract!=null){
			salesProducts=salesContractDao.findSalesProducts(contractId);
			salesPacks=salesContractDao.findSalesPacks(contractId);
			salescontract.setSalesProducts(salesProducts);
			salescontract.setSalesPacks(salesPacks);
		}*/
		return salescontract;
	}
	@Override
	public List<HashMap<String, Object>> findSalesProductsByContractId(
			Long contractId) throws SystemException {
		return salesContractDao.findSalesProductsByContractId(contractId);
	}
	@Override
	public List<HashMap<String, Object>> findSalesPacksByContractId(
			Long contractId) throws SystemException {
		return salesContractDao.findSalesPacksByContractId(contractId);
	}
	@Override
	public String getSalesContractNo(Long companyId,Long userId)
			throws SystemException {
		OpenLdap openLdap=OpenLdapManager.getInstance();
		CommonCompany commonCompany=null;
		String companyNo=null;
		
		if(companyId!=null){
			commonCompany=openLdap.getCompanyById(companyId.toString());
			companyNo=commonCompany.getCompanycode();
			int length=companyNo.length();
			if(length>=2){
			   companyNo=companyNo.substring(length-2, length);
			}
		}
		//流水号加1，前面不足4位，用0补充
		String serialNoStr=Utils.formatSerial(salesContractDao.getMaxSalesContractNo(companyId, DateUtil.formatToday()));
		String userIdStr=null;
		if(userId==null){
			userIdStr="";
		}else{
			if(userId>100000){//大于百万，只取模
				userIdStr=String.valueOf(userId%100000);
			}else{
				userIdStr=String.valueOf(userId);
			}
		}
		return SystemConst.SALES_NO_PREFIX+companyNo+userIdStr+DateUtil.format(new Date(), DateUtil.YMD)+serialNoStr;
	}
	@Override
	public int updateStatus(Salescontract salescontract)
			throws SystemException {
		if(salescontract==null || StringUtils.isNullOrEmpty(salescontract.getId())){
			return 0;
		}
		salescontract.setCheckStamp(new Date());
		//修改合同明细的状态
		salesContractDao.updateDetaisStatus(salescontract);
		//修改合同状态
		return salesContractDao.updateStatus(salescontract);
	}

	@Override
	public int updateDetails(Long contractId, List<SalesProduct> salesProducts)
			throws SystemException {
		if(contractId==null){
			return -1;
		}
		int result=1;
		//操作之前，判断存在不存在
		Salescontract salescontract=salesContractDao.get(Salescontract.class, contractId);
		if(salescontract!=null){
			//查询生效日期、到期日期
			Date validDate=salescontract.getValidDate();
			Date natureDate=salescontract.getMatureDate();
			//先删除供货合同与商品的详细信息
			salesContractDao.deleteSalesProductsByContractId(contractId);
			//再添加供货合同与商品的详细信息
			if(salesProducts!=null&&!salesProducts.isEmpty()){
				for (SalesProduct salesProducts2 : salesProducts) {
					salesProducts2.setContractId(contractId);
					salesProducts2.setStatus(1);//生效
					salesProducts2.setValidDate(validDate);
					salesProducts2.setMatureDate(natureDate);
					salesContractDao.save(salesProducts2);
				}
			}
				
		}else{
			result=0;
		}
		return result;
	}
	@Override
	public List<HashMap<String, Object>> findAllSalesContracts(
			HashMap<String, Object> map) throws SystemException {
		List<HashMap<String, Object>> dataList=salesContractDao.findSalesContracts(map,null,false,0,0);
        return dataList;
	}
	@Override
	public int deleteSalesContracts(List<Long> contractIds)
			throws SystemException {
		int result=1;
		if(contractIds==null || contractIds.isEmpty()){
			result=-1;
		}else{
			for (Long contractId : contractIds) {
				//操作之前，判断存在不存在
				if(salesContractDao.get(Salescontract.class, contractId)!=null){
					//先删除合同与商品信息关系
					salesContractDao.deleteSalesProductsByContractId(contractId);
					//再删除合同与套餐的关系
					salesContractDao.deleteSalesPacksByContractId(contractId);
					//最后删除合同
					salesContractDao.delete(Salescontract.class,contractId);
				}
			}
			
		}
		return result;
	}
	@Override
	public SalesProduct getSalesProductByProductId(Long productId,Long channelId,
			Long companyId) throws SystemException {
		return salesContractDao.getSalesProductByProductId(productId,channelId, companyId);
	}
	@Override
	public Page<HashMap<String, Object>> findSalesContractProducts(
			PageSelect<HashMap<String, Object>> pageSelect)
			throws SystemException {
		if(pageSelect==null){
			return null;
		}else{
	        int total=salesContractDao.countSalesContractProducts(pageSelect.getFilter());
	        List<HashMap<String, Object>> dataList=salesContractDao.findSalesContractProducts(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
	        return PageUtil.getPage(total, pageSelect.getPageNo(), dataList, pageSelect.getPageSize());
		}
	}
	@Override
	public int updateFillingById(Salescontract salescontract)
			throws SystemException {
		if(salescontract==null || StringUtils.isNullOrEmpty(salescontract.getId())){
			return 0;
		}
		int result=0;
		Salescontract salescontract2=salesContractDao.get(Salescontract.class, salescontract.getId());
		if(salescontract2!=null){
			salescontract2.setIsFiling(salescontract.getIsFiling());
			//salescontract2.setUserId(salescontract.getUserId());
			//salescontract2.setUserName(salescontract.getUserName());
			salesContractDao.merge(salescontract2);
			result=1;
		}
		return result;
	}
}

