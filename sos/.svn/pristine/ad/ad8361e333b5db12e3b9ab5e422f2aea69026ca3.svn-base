package com.gboss.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ldap.objectClasses.CommonCompany;
import ldap.objectClasses.CommonOperator;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.SellPerformanceDao;
import com.gboss.pojo.BorrowDetails;
import com.gboss.pojo.SellPerformance;
import com.gboss.service.SellPerformanceService;
import com.gboss.util.StringUtils;
import com.gboss.util.Utils;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:SellPerformanceServiceImpl
 * @Description:销售业绩业务层实现类
 * @author:zfy
 * @date:2013-8-6 上午8:42:54
 */
@Service("sellPerformanceService")
@Transactional
public class SellPerformanceServiceImpl  extends BaseServiceImpl implements SellPerformanceService {
	protected static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired  
	@Qualifier("sellPerformanceDao")
	private SellPerformanceDao sellPerformanceDao;

	@Override
	public List<SellPerformance> findSellPerformances(
			SellPerformance sellPerformance) throws SystemException {
		return sellPerformanceDao.findSellPerformances(sellPerformance);
	}

	@Override
	public List<Map<String, Object>> getSellProductDetail(
			HashMap<String, String> conditionMap) throws SystemException {
		//实际销售商品详细
		List<Map<String, Object>> saleProductDetails=sellPerformanceDao.getSaleProductsDetail(conditionMap);
		//实际商品入网详细
		List<Map<String, Object>> netInDetails=null;//sellPerformanceDao.getNetInDetail(conditionMap);
		//实际商品回款详细
		List<Map<String, Object>> retMoneyDetails=sellPerformanceDao.getReturnMoneyDetail(conditionMap);
		String productId=null;
		Object netInNum=0;
		Object retMoney=0f;
		for (Map<String, Object> map : saleProductDetails) {
			//商品id
			productId=String.valueOf(map.get("productId"));
			//商品名称
			//商品数量
			//商品入网数量
			if(netInDetails!=null){
				for (Map<String, Object> netInMap : netInDetails) {
					if(netInMap.get("productId")!=null&&productId.equals(String.valueOf(netInMap.get("productId")))){
						netInNum=netInMap.get("netNum");
						break;
					}			
				}
			}
			map.put("netInNum", netInNum);
			//商品回款数量
			for (Map<String, Object> retMoneyMap : retMoneyDetails) {
				if(retMoneyMap.get("productId")!=null&&productId.equals(String.valueOf(retMoneyMap.get("productId")))){
					retMoney=retMoneyMap.get("retMoney");
					break;
				}			
			}
			map.put("retMoney", retMoney);
		}
		return saleProductDetails;
	}

	@Override
	public List<Map<String, Object>> findBorrows(BorrowDetails borrowDetails) throws SystemException {
		List<Map<String, Object>> borrows = sellPerformanceDao.findBorrows(borrowDetails);
		String borrowerName=null;//借用人名称
		CommonOperator commonOperator=null;
		OpenLdap openLdap=new OpenLdap();
		
		if(borrows!=null){
			String whsId = null;
			String whsName= null;//仓库名称
			HashMap<String, String> wareHouseMap = new HashMap<String, String>();
			CommonCompany commonCompany = null;
			for (Map<String, Object> borrow: borrows) {
				borrowerName = null;
				whsId = null;
				whsName= null;//仓库名称
				
				if (borrow.get("borrowerId") !=null) {
					commonOperator=openLdap.getOperatorById(borrow.get("borrowerId").toString());
					if(commonOperator!=null){
						borrowerName=commonOperator.getOpname();
					}
				}
				
				//设置仓库名称
				if (borrow.get("whsId") != null) {
					whsId=borrow.get("whsId").toString();
					if (wareHouseMap.get(whsId) != null) {
						whsName = wareHouseMap.get(whsId) ;
					} else {
						commonCompany = openLdap.getCompanyById(whsId);
						if (commonCompany != null) {
							wareHouseMap.put(whsId, commonCompany.getCompanyname());
							whsName = commonCompany.getCompanyname() ;
						}
					}
				}
				borrow.put("borrowerName", borrowerName);
				borrow.put("whsName", whsName);//仓库名称
			}
		}
		return borrows;
	}

	@Override
	public List<Map<String, Object>> getSellProductAction(
			HashMap<String, String> conditionMap) throws SystemException {
		//商品入库信息
		List<Map<String, Object>> stockInList=sellPerformanceDao.findStockInDetail(conditionMap);
		HashMap<String, String> wareHouseMap = new HashMap<String, String>();
		OpenLdap openLdap = OpenLdapManager.getInstance();
    	CommonCompany commonCompany = null;
    	String whsId = null;
    	String whsName= null;
		if (stockInList != null && !stockInList.isEmpty()) {
	        	for (Map<String, Object> hashMap : stockInList) {
	        		whsId = null;
	        		whsName = null;
					if (hashMap.get("whsId") != null) {
						whsId=hashMap.get("whsId").toString();
						if (wareHouseMap.get(whsId) != null) {
							whsName = wareHouseMap.get(whsId) ;
						} else {
							commonCompany = openLdap.getCompanyById(hashMap.get("whsId").toString());
							if (commonCompany != null) {
								wareHouseMap.put(hashMap.get("whsId").toString(), commonCompany.getCompanyname());
								whsName = commonCompany.getCompanyname() ;
							}
						}
						
					}
					hashMap.put("whsName", whsName);
				}
	        }
		
		//商品出库信息
		List<Map<String, Object>> stockOutList=sellPerformanceDao.findStockOutDetail(conditionMap);
		if (stockOutList != null && !stockOutList.isEmpty()) {
        	for (Map<String, Object> hashMap : stockOutList) {
        		whsId = null;
        		whsName = null;
				if (hashMap.get("whsId") != null) {
					whsId=hashMap.get("whsId").toString();
					if (wareHouseMap.get(whsId) != null) {
						whsName = wareHouseMap.get(whsId) ;
					} else {
						commonCompany = openLdap.getCompanyById(hashMap.get("whsId").toString());
						if (commonCompany != null) {
							wareHouseMap.put(hashMap.get("whsId").toString(), commonCompany.getCompanyname());
							whsName = commonCompany.getCompanyname() ;
						}
					}
					
				}
				hashMap.put("whsName", whsName);
			}
        }
		
		//商品销账信息
		List<Map<String, Object>> writeoffList=sellPerformanceDao.findWriteoffDetails(conditionMap);
		if(stockInList!=null){
			stockInList.addAll(stockOutList);
			stockInList.addAll(writeoffList);
		}
		return stockInList;
	}

	@Override
	public List<Map<String, Object>> statSellPerformance(
			HashMap<String, Object> conditionMap) throws SystemException {
		//月份 + 全年
		if(conditionMap.get("month")!=null){
			String[] months={(String)conditionMap.get("month"),SellPerformanceStatisJob.TOTAL_YEAR};
			conditionMap.put("month",months);
		}
		//部门下有哪些用户 或者 分公司下有哪些部门
		List<Long> userOrgIds=new ArrayList<Long>();
		List<CommonOperator> commonOperators=null;//某部门下的所有销售经理
		List<CommonCompany> commonCompanies=null;//某分公司下的所有部门
	    int type=0;
		if(conditionMap.get("type")!=null){
			type=Integer.parseInt(String.valueOf(conditionMap.get("type")));
			//部门经理
			if(type==2){
				if(conditionMap.get("userOrgIds")!=null){
					commonOperators=(List<CommonOperator>)conditionMap.get("userOrgIds");
					for (CommonOperator commonOperator : commonOperators) {
						if(commonOperator.getOpid()!=null){
							userOrgIds.add(Long.valueOf(commonOperator.getOpid()));
						}
					}
					conditionMap.put("userOrgIds",userOrgIds);
				}
			}else{//销售总监
				if(conditionMap.get("userOrgIds")!=null){
					commonCompanies=(List<CommonCompany>)conditionMap.get("userOrgIds");
					for (CommonCompany commonCompany : commonCompanies) {
						if(StringUtils.isNotBlank(commonCompany.getCompanyno())){
							userOrgIds.add(Long.valueOf(commonCompany.getCompanyno()));
						}
					}
					conditionMap.put("userOrgIds",userOrgIds);
				}
			}
		}
		
		List<SellPerformance> sellPerformances=sellPerformanceDao.statSellPerformance(conditionMap);
		List<Map<String, Object>> mapList=new ArrayList<Map<String,Object>>();
		Map<String, Object> map=null;
		String userOrOrgName=null;
		for (SellPerformance sellPerformance : sellPerformances) {
			userOrOrgName=null;
			map=new HashMap<String, Object>();
			map.put("id", sellPerformance.getUserOrgId());
			map.put("year", sellPerformance.getYear());
			map.put("month", sellPerformance.getMonth());
			map.put("sellNumRate", sellPerformance.getSellNumRate());
			//设置部门名称或者销售经理名称
			if(conditionMap.get("type")!=null){
				//部门经理
				if(type==2){
					if(conditionMap.get("userOrgIds")!=null){
						for (CommonOperator commonOperator : commonOperators) {
							if(StringUtils.isNotNullOrEmpty(sellPerformance.getUserOrgId()) && sellPerformance.getUserOrgId().toString().equals(commonOperator.getOpid())){
								userOrOrgName=commonOperator.getOpname();
								break;
							}
						}
					}
				}else{//销售总监
					if(conditionMap.get("userOrgIds")!=null){
						for (CommonCompany commonCompany : commonCompanies) {
							if(sellPerformance.getUserOrgId()!=null && sellPerformance.getUserOrgId().toString().equals(commonCompany.getCompanyno())){
								userOrOrgName=commonCompany.getCompanyname();
								break;
							}
						}
					}
				}
			}
			map.put("name", userOrOrgName);
			
			mapList.add(map);
		}
		return mapList;
	}

	@Override
	public List<SellPerformance> statAllSellPerformance(String year) throws SystemException {
		return sellPerformanceDao.statAllSellPerformance(year);
	}

}

