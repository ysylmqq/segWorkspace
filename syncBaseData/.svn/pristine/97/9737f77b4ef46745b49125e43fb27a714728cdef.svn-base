package com.gboss.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.ComboDao;
import com.gboss.pojo.Combo;
import com.gboss.service.ComboService;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:ComboServiceImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-9-18 下午2:53:05
 */
@Service("comboService")
public class ComboServiceImpl extends BaseServiceImpl implements ComboService {
	
	@Autowired
	@Qualifier("comboDao")
	private ComboDao comboDao;

	 
	public int addCombo(Combo combo) throws SystemException {
		int result=1;
		if(comboDao.isExist(combo)){
			result = 2;
		}else{
			try {
				comboDao.save(combo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	 
	public Page<HashMap<String, Object>> findComboes(Long conpanyId,
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		return null;
	}

	 
	public boolean isExist(Combo combo) throws SystemException {
		return comboDao.isExist(combo);
	}

	 
	public int delete(List<Long> ids) throws SystemException {
		int result=1;
		if(ids==null || ids.isEmpty()){
			result=-1;
		}else{
			comboDao.delete(ids);
		}
		return result;
	}

	public List<HashMap<String, Object>> findComboes(Long conpanyId,
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pn, int pageSize) throws SystemException {
		return null;
	}

	 
	public Combo getComboBySync_id(Long sync_id) {
		return comboDao.getComboBySync_id(sync_id);
	}

	 
	public Combo getComboByName(String name) throws SystemException {
		return comboDao.getComboByname(name);
	}

}

