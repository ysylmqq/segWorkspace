package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;

/**
 * @Package:com.gboss.dao
 * @ClassName:UserRemarkDao
 * @Description:备忘录数据持久层接口
 * @author:zfy
 * @date:2013-11-18 上午10:15:05
 */
public interface UserRemarkDao extends BaseDao {
	/**
	 * @Title:findUserRemarks
	 * @Description:查询用户备忘录
	 * @param conditionMap
	 * @param order
	 * @param isDesc
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findUserRemark(Map<String,Object> conditionMap, String order,boolean isDesc, int pageNo,int pageSize) throws SystemException;
	/**
	 * @Title:countUserRemarks
	 * @Description:获得用户备忘录的记录数
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public int countUserRemarks(Map<String,Object> conditionMap)throws SystemException;
	
	/**
	 * @Title:deleteUserRemark
	 * @Description:批量删除用户备忘录
	 * @param idList
	 * @return
	 * @throws SystemException
	 */
	public int deleteUserRemark(List<Long> idList) throws SystemException;
	
	/**
	 * @Title:updateRemarkIsAlert
	 * @Description:修改是否登录后提示,除开id以外的其他数据为不提示
	 * @param id
	 * @param userId
	 * @return
	 * @throws SystemException
	 */
	public int updateRemarkIsAlert(Long id,Long userId)throws SystemException;
}

