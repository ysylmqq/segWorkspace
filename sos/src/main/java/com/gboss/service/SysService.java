package com.gboss.service;

import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Operatelog;
import com.gboss.pojo.SysValue;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.chinagps.fee.service
 * @ClassName:SysService
 * @Description:系统参数类型、值 业务层接口
 * @author:zfy
 * @date:2014-6-11 上午9:36:52
 */
public interface SysService extends BaseService{
	/**
	 * @Title:findSysValue
	 * @Description:查询系统参数值
	 * @param sysValue
	 * @return
	 * @throws SystemException
	 */
	public List<SysValue> findSysValue(SysValue sysValue) throws SystemException;
	
	public List<SysValue> findAllBank();
	
	public Page<Map<String,Object>> findOperatelogPage(PageSelect<Operatelog> pageSelect) throws SystemException;
	
	public List<Map<String,Object>> findOperatelog(Map<String, Object> conditionMap) throws SystemException;
	
}

