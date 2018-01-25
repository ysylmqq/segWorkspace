package com.gboss.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ldap.objectClasses.CommonCompany;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.StockSetupDao;
import com.gboss.pojo.Setup;
import com.gboss.service.StockSetupService;
import com.gboss.util.DateUtil;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.UUIDCreater;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:StockSetupServiceImpl
 * @Description:库存设置业务层实现了
 * @author:zfy
 * @date:2013-8-29 下午3:26:40
 */
@Service("stockSetupService")
@Transactional
public class StockSetupServiceImpl extends BaseServiceImpl implements StockSetupService {
	protected static final Logger LOGGER = LoggerFactory.getLogger(StockSetupServiceImpl.class);
	
	@Autowired  
	@Qualifier("stockSetupDao")
	private StockSetupDao stockSetupDao;
	@Override
	public Page<HashMap<String, Object>> findStocks(
			PageSelect<HashMap<String, Object>> pageSelect) throws SystemException {
		if(pageSelect==null){
			return null;
		}else{
	        int total=stockSetupDao.countStocks(pageSelect.getFilter());
	        List<HashMap<String, Object>> list=stockSetupDao.findStocks(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(), pageSelect.getPageNo(),pageSelect.getPageSize());
	       /* if (list != null && !list.isEmpty()) {
	        	OpenLdap openLdap = OpenLdapManager.getInstance();
	        	HashMap<String, String> wareHouseMap = new HashMap<String, String>();
	        	CommonCompany commonCompany = null;
	        	String whsId = null;
	        	String whsName= null;
	        	for (HashMap<String, Object> hashMap : list) {
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
	        }*/
	        return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
		}
	}
	@Override
	public int addSetups(List<Long> productIds,Long whsId,String whsName,Integer minStock,Integer overstockTime,Long userId,String userName) throws SystemException {
		if(productIds==null || productIds.isEmpty()){
			return -1;
		}
		int result=1;
		Setup setup = null;
		for (Long productId : productIds) {
			setup = new Setup();
			setup.setProductId(productId);
			setup.setWhsId(whsId);
			setup.setWhsName(whsName);
			setup.setMinStock(minStock);
			setup.setOverstockTime(overstockTime);
			setup.setUserId(userId);
			//setup.setUserName(userName);
			stockSetupDao.save(setup);
		}
		return result;
	}

	@Override
	public int deleteSetup(Long id) throws SystemException {
		if(id==null){
			return -1;
		}
		int result=1;
		//操作之前，判断存在不存在
		if(stockSetupDao.get(Setup.class, id)!=null){
			stockSetupDao.delete(Setup.class, id);
		}else{
			result=0;
		}
		return result;
	}

	@Override
	public int updateSetups(List<Long> ids,Integer minStock,Integer overstockTime,Long userId) throws SystemException {
		if(ids==null || ids.isEmpty()){
			return -1;
		}
		stockSetupDao.updateSetups(ids, minStock, overstockTime, userId);
		return 1;
	}
	@Override
	public int deleteSetups(List<Long> idList) throws SystemException {
		if (idList == null || idList.isEmpty()) {
			return -1;
		}else{
			stockSetupDao.deleteSetups(idList);
			return 1;
		}
	}
	@Override
	public List<HashMap<String, Object>> findSetups(Map<String, Object> map)
			throws SystemException {
		return stockSetupDao.findStocks(map, null, true, 0, 0);
	}

}

