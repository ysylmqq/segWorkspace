package com.gboss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.SysValue;
import com.gboss.pojo.UnitType;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service
 * @ClassName:UnitTypeService
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-6-30 上午11:28:25
 */
public interface UnitTypeService extends BaseService {

	public Page<UnitType> findUnitType(PageSelect<UnitType> pageSelect)throws SystemException;

	public Boolean isExist(String name)throws SystemException;
	
	public Boolean isExist(String name, Long id)throws SystemException;
	
	public String getUnittypeByid(Long id)throws SystemException;

	public List<SysValue> getSysValueList(Integer typeId)throws SystemException;
	
	public Page<HashMap<String, Object>> findUnitTypes(PageSelect<Map<String, Object>> pageSelect) throws SystemException;
}
