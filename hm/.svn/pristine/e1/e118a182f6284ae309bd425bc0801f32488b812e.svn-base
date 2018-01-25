package com.gboss.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Upgrade;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service
 * @ClassName:UpgradeService
 * @Description:TODO
 * @author:bzhang
 * @date:2015-1-27 上午8:54:57
 */
public interface UpgradeService extends BaseService {

	public Page<HashMap<String, Object>> getSimUpgradePage(HttpServletRequest request,Long companyId, PageSelect<Map<String, Object>> pageSelect) throws SystemException;
	
	public List<HashMap<String, Object>> getSimUpgradeList(Long companyId, Map<String, Object> conditionMap) throws SystemException;
	
	public List<Upgrade> getUpgradeList();
	
	public List<Upgrade> getUpgradeAllList();
	
	public List<Upgrade> getUpgradeListforCur_ver();
	
	public Upgrade getUpgradeBycall_letter(String call_letter);
	
	public void removeAndInputupgrade();
	
	public String importUpgrade(List<String[]> dataList,
			Long compannyId, Long userId,String upgradeName,ArrayList<String> params) throws SystemException;
}


