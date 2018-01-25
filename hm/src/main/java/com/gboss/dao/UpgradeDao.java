package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Preload;
import com.gboss.pojo.Upgrade;
import com.gboss.pojo.UpgradeFile;

/**
 * @Package:com.gboss.dao
 * @ClassName:UpgradeDao
 * @Description:TODO
 * @author:bzhang
 * @date:2015-1-27 上午8:52:09
 */
public interface UpgradeDao extends BaseDao {
	
	public List<HashMap<String, Object>> getSimUpgradeList(Long companyId, Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;

	public int countSimUpgrade(Long companyId, Map<String, Object> conditionMap) throws SystemException;
	
	public String getSimUpgradeletters(Long companyId, Map<String, Object> conditionMap) throws SystemException;
	
	public List<Upgrade> getUpgradeList();
	
	public List<Upgrade> getUpgradeAllList();
	
	public List<Upgrade> getUpgradeListforCur_ver();
	
	public Upgrade getUpgradeBycall_letter(String call_letter);
	
	public Preload getSimInfo(String vin,String barcode);	
	
	public List<Preload> getSimAll();
	
}

