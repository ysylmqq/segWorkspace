package com.gboss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Setup;
import com.gboss.pojo.UserRemark;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;
/**
 * @Package:com.gboss.service
 * @ClassName:UserRemarkService
 * @Description:备忘录业务层接口
 * @author:zfy
 * @date:2013-11-18 上午10:19:27
 */
public interface UserRemarkService extends BaseService {
	
	/**
	 * @Title:findUserRemark
	 * @Description:分页查询
	 * @param pageSelect
	 * @return
	 * @throws SystemException
	 */
	public Page<HashMap<String, Object>> findUserRemarks(PageSelect<HashMap<String, Object>> pageSelect)throws SystemException;
	/**
	 * @Title:addUserRemark
	 * @Description:批量添加用户备忘录
	 * @param userRemark
	 * @return
	 * @throws SystemException
	 * @throws批量添加用户备忘录
	 */
	public int addUserRemark(UserRemark userRemark)throws SystemException;
	/**
	 * @Title:updateUserRemark
	 * @Description:修改用户备忘录
	 * @param userRemark
	 * @return
	 * @throws SystemException
	 */
	public int updateUserRemark(UserRemark userRemark) throws SystemException;
	/**
	 * @Title:deleteUserRemarks
	 * @Description:批量删除用户备忘录
	 * @param idList
	 * @return
	 * @throws SystemException
	 */
	public int deleteUserRemarks(List<Long> idList) throws SystemException;
	
	/**
	 * @Title:getUserRemarkById
	 * @Description:根据ID获得用户备注
	 * @param id
	 * @return
	 * @throws SystemException
	 */
	public UserRemark getUserRemarkById(Long id) throws SystemException;
}

