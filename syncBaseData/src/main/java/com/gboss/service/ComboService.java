package com.gboss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Combo;
import com.gboss.pojo.Series;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service
 * @ClassName:ComboService
 * @Description:TODO
 * @author:bzhang
 * @date:2014-9-18 下午2:50:03
 */
public interface ComboService extends BaseService {
	
	public boolean isExist(Combo combo)throws SystemException ;
	
	public int addCombo(Combo combo)throws SystemException;
	
	public Page<HashMap<String, Object>> findComboes(Long conpanyId, PageSelect<Map<String, Object>> pageSelect) throws SystemException;

	public int delete(List<Long> ids) throws SystemException;
	
	public List<HashMap<String, Object>> findComboes(Long conpanyId, Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;

	public Combo getComboBySync_id(Long sync_id);
	
	public Combo getComboByName(String name)throws SystemException;
}

