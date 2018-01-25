package com.gboss.dao;

import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.SysValue;

/**
 * @Package:com.chinagps.fee.dao
 * @ClassName:SysDao
 * @Description:系统参数类型、值  数据持久层接口
 * @author:zfy
 * @date:2014-6-11 上午9:27:15
 */
public interface SysDao{
	/**
	 * @Title:findSysValue
	 * @Description:查询系统参数值
	 * @param sysValue
	 * @return
	 * @throws SystemException
	 */
	public List<SysValue> findSysValue(SysValue sysValue) throws SystemException;
	
	public List<SysValue> findAllBank();
	
	/**
	 * @Title:findOplogs
	 * @Description:分页查询操作日志
	 * @param conditionMap
	 * @param order
	 * @param isDesc
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws SystemException
	 */
	public List<Map<String,Object>> findOplogs(Map conditionMap, String order,boolean isDesc,int pageNo,int pageSize) throws SystemException;
	/**	
	 * 
	 * @Title:countOplogs
	 * @Description:获得操作日期记录数
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public int countOplogs(Map conditionMap) throws SystemException;

}
