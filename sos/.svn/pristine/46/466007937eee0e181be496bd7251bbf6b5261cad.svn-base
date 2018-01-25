package com.gboss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Check;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service
 * @ClassName:CheckService
 * @Description:盘点业务层接口
 * @author:zfy
 * @date:2013-9-16 上午11:26:21
 */
public interface CheckService extends BaseService {
	/**
	 * @Title:getCheck
	 * @Description:查询盘点
	 * @param map
	 * @return
	 * @throws SystemException
	 */
	public Check getCheck(Map<String,Object> map) throws SystemException;
	
	/**
	 * @Title:getCheckAndDetails
	 * @Description:查询盘点详细
	 * @param map
	 * @return
	 * @throws SystemException
	 */
	public Check getCheckAndDetails(Map<String,Object> map) throws SystemException;
	
	/**
	 * @Title:getCheckAndDetails4Report
	 * @Description:查询盘点详细（用于报表）
	 * @param map
	 * @return
	 * @throws SystemException
	 */
	public Check getCheckAndDetails4Report(Map<String,Object> map) throws SystemException;
	
	/**
	 * @Title:addCheck
	 * @Description:添加盘点信息
	 * @param check
	 * @return
	 * @throws SystemException
	 */
	public int addCheck(Check check) throws SystemException;
	
	/**
	 * @Title:updateCheck
	 * @Description:修改盘点信息
	 * @param check
	 * @return
	 * @throws SystemException
	 */
	public int updateCheck(Check check) throws SystemException;
	/**
	 * @Title:updateCheckChangeNum
	 * @Description:修改盘点调账
	 * @param check
	 * @return
	 * @throws SystemException
	 * @throws Exception 
	 */
	public HashMap<String, Object> updateCheckChangeNum(Check check,String whsCode) throws SystemException, Exception;
	
	/**
	 * @Title:updateCheckStatus
	 * @Description:修改盘点状态
	 * @param check
	 * @return
	 * @throws SystemException
	 * @throws Exception 
	 */
	public int updateCheckStatus(Check check) throws SystemException, Exception;
	
	/**
	 * @Title:findChecksByPage
	 * @Description:分页查找盘点记录
	 * @param pageSelect
	 * @return
	 * @throws SystemException
	 */
	public Page<HashMap<String, Object>> findChecksByPage(PageSelect<Map<String, Object>> pageSelect) throws SystemException;

	/**
	 * @Title:findAllChecks
	 * @Description:查询所有盘点记录
	 * @param map
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findAllChecks(Map<String, Object> map) throws SystemException;
	
	/**
	 * @Title:deleteChecks
	 * @Description:删除盘点
	 * @param ids
	 * @return
	 * @throws SystemException
	 */
	public int deleteChecks(List<Long> ids) throws SystemException;
	
	/**
	 * @Title:checkStatus
	 * @Description:判断是否还有盘点未完成
	 * @param companyId
	 * @param whsIds
	 * @param status
	 * @param isEqlStatus
	 * @return
	 * @throws SystemException
	 */
	public Boolean checkStatus(Long companyId, List<Long> whsIds, Integer status,
			Boolean isEqlStatus) throws SystemException;
	/**
	 * @Title:findCheckDetails4Reprot
	 * @Description:查询盘点详细(用于分公司、或总部)
	 * @param map
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findCheckDetails4Reprot(Map<String,Object> map) throws SystemException;
	
}

