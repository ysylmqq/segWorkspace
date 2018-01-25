package com.gboss.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.UnitTypeDao;
import com.gboss.pojo.Product;
import com.gboss.pojo.SysValue;
import com.gboss.pojo.UnitType;
import com.gboss.service.UnitTypeService;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:UnitTypeServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-6-30 上午11:29:44
 */
@Service("UnitTypeService")
@Transactional
public class UnitTypeServiceImpl extends BaseServiceImpl implements UnitTypeService {

	@Autowired
	@Qualifier("UnitTypeDao")
	private UnitTypeDao unitTypeDao;
	
	@Override
	public Page<UnitType> findUnitType(PageSelect<UnitType> pageSelect)throws SystemException{
		return unitTypeDao.findUnitType(pageSelect);
	}

	@Override
	public Boolean isExist(String name)throws SystemException{
		return unitTypeDao.isExist(name);
	}

	@Override
	public List<SysValue> getSysValueList(Integer typeId)throws SystemException{
		return unitTypeDao.getSysValueList(typeId);
	}

	@Override
	public Page<HashMap<String, Object>> findUnitTypes(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total=unitTypeDao.countUnitTypes(pageSelect.getFilter());
		List<HashMap<String, Object>> list=unitTypeDao.findUnitTypes(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());	}

	@Override
	public Boolean isExist(String name, Long id)throws SystemException{
		return unitTypeDao.isExist(name, id);
	}

	@Override
	public String getUnittypeByid(Long id)throws SystemException{
		return unitTypeDao.getUnittypeByid(id);
	}
}

