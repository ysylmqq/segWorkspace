package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Check;
import com.gboss.pojo.CheckDetails;
import com.gboss.pojo.Order;

/**
 * @Package:com.gboss.dao
 * @ClassName:CheckDao
 * @Description:盘点数据持久层接口
 * @author:zfy
 * @date:2013-9-16 上午11:23:23
 */
public interface CheckDao extends BaseDao {
	/**
	 * @Title:getCheck
	 * @Description:获得盘点信息
	 * @param map
	 * @return
	 * @throws SystemException
	 */
	public Check getCheck(Map<String,Object> map) throws SystemException;
	
	/**
	 * @Title:findCheckDetails
	 * @Description:查询盘点详细
	 * @param map
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findCheckDetails (Map<String,Object> map) throws SystemException;
	
	/**
	 * @Title:findCheckDetails4Reprot
	 * @Description:查询盘点详细(用于分公司、或总部)
	 * @param map
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findCheckDetails4Reprot (Map<String,Object> map) throws SystemException;
	
	/**
	 * @Title:findStockInDetails
	 * @Description:查询入库明细
	 * @param map
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public List<HashMap<String, Object>> findStockInDetails (Map<String, Object> map) throws SystemException;
	
	/**
	 * @Title:findStockOutDetails
	 * @Description:查询出库明细,只查销售安装的
	 * @param map
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findStockOutDetails (Map<String, Object> map) throws SystemException;
	
	/**
	 * @Title:deleteDetailsByCheckId
	 * @Description:根据checkId删除盘点明细
	 * @param checkId
	 * @return
	 * @throws SystemException
	 */
	public int deleteDetailsByCheckId(Long checkId) throws SystemException;
	/**
	 * @Title:updateChangeNumByCheckId
	 * @Description:根据checkId修改盘点明细的调账数
	 * @param checkId
	 * @return
	 * @throws SystemException
	 */
	public int updateChangeNumByCheckId(Long checkId) throws SystemException;
	
	/**
	 * @Title:checkName
	 * @Description:判断盘点名称是否存在
	 * @param check
	 * @return
	 * @throws SystemException
	 */
	public boolean checkName(Check check) throws SystemException;
	
	/**
	 * @Title:findChecks
	 * @Description:分页查询盘点记录
	 * @param map
	 * @param order
	 * @param isDesc
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findChecks(Map<String, Object> map, String order,boolean isDesc, int pageNo, int pageSize) throws SystemException;
	/**
	 * @Title:countChecks
	 * @Description:查询盘点记录数
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public int countChecks(Map<String, Object> conditionMap) throws SystemException;
	/**
	 * @Title:checkName
	 * @Description:判断是否有盘点已审核（待调账、2）,已调账(盘点正式结束)
	 * @param companyId
	 * @param whsIds
	 * @param status
	 * @param isEqlStatus
	 * @return
	 * @throws SystemException
	 */
	public boolean checkStatus(Long companyId,List<Long> whsIds,Integer status,Boolean isEqlStatus) throws SystemException;
}

