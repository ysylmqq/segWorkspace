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
@Transactional
public class ComboServiceImpl extends BaseServiceImpl implements ComboService {
	
	@Autowired
	@Qualifier("comboDao")
	private ComboDao comboDao;

	@Override
	public int addCombo(Combo combo) throws SystemException {
		int result=1;
		if(comboDao.isExist(combo)){
			result = 2;
		}else{
			comboDao.save(combo);
		}
		return result;
	}

	@Override
	public Page<HashMap<String, Object>> findComboes(Long conpanyId,
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total=comboDao.countComboes(conpanyId, pageSelect.getFilter());
		List<HashMap<String, Object>> list=comboDao.findComboes(conpanyId, pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

	@Override
	public boolean isExist(Combo combo) throws SystemException {
		return comboDao.isExist(combo);
	}

	@Override
	public int delete(List<Long> ids) throws SystemException {
		int result=1;
		if(ids==null || ids.isEmpty()){
			result=-1;
		}else{
			comboDao.delete(ids);
		}
		return result;
	}

	@Override
	public List<HashMap<String, Object>> findComboes(Long conpanyId,
			Map<String, Object> conditionMap, String order, boolean isDesc,
			int pn, int pageSize) throws SystemException {
		return comboDao.findComboes(conpanyId, conditionMap, order, isDesc, pn, pageSize);
	}

}

