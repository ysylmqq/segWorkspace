package com.gboss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Combo;
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
	
	/**
	 * 验证套餐名是否已存在，true:存在，false:不存在
	 * @param combo
	 * @return
	 * @throws SystemException
	 */
	public boolean isExist(Combo combo)throws SystemException ;
	
	/**
	 * 添加套餐
	 * @param combo
	 * @return
	 * @throws SystemException
	 */
	public int addCombo(Combo combo)throws SystemException;
	
	/**
	 * 分页查询套餐
	 * @param conpanyId
	 * @param pageSelect
	 * @return
	 * @throws SystemException
	 */
	public Page<HashMap<String, Object>> findComboes(Long conpanyId, PageSelect<Map<String, Object>> pageSelect) throws SystemException;

	/**
	 * 批量删除套餐
	 * @param ids
	 * @return
	 * @throws SystemException
	 */
	public int delete(List<Long> ids) throws SystemException;
	
	/**
	 * 查询套餐列表
	 * @param conpanyId
	 * @param conditionMap
	 * @param order
	 * @param isDesc
	 * @param pn
	 * @param pageSize
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findComboes(Long conpanyId, Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;
}

