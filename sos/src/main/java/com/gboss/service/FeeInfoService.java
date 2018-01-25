package com.gboss.service;

import java.util.HashMap;
import java.util.List;

import com.gboss.comm.SystemException;
import com.gboss.pojo.FeeInfo;

/**
 * @Package:com.gboss.service
 * @ClassName:FeeInfoService
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-5-27 下午7:53:46
 */
public interface FeeInfoService extends BaseService {
	
	/**
	 * 添加计费信息
	 * @param feeInfo
	 * @return
	 */
	public Long add(FeeInfo feeInfo);
	
	/**
	 * 获取车辆计费信息
	 * @param unit_id
	 * @param feetype_id
	 * @return
	 */
	public FeeInfo getFeeInfo(Long unit_id, Integer feetype_id);
	
	/**
	 * 获取车辆保险计费信息
	 * @param vehicle_id
	 * @return
	 */
	public FeeInfo getinsuranceInfo(Long vehicle_id);
	
	/**
	 * 根据计费id删除计费项
	 * @param id
	 */
	public void delete(Long id);
	
	/**
	 * 删除信息
	 * @param feeInfo
	 */
	public void deleteFeeInfo(FeeInfo feeInfo);
	
	/**
	 * 根据车台id删除计费信息
	 * @param unit_id
	 */
	public void deleteByUnitid(Long unit_id);
	
	/**
	 * 根据车台id获取计费列表
	 * @param unit_id
	 * @return
	 */
	public List<FeeInfo> getListByid(Long unit_id);
	
	/**
	 * 根据车辆id获取计费列表
	 * @param vehicle_id
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> getFeeInfoList(Long vehicle_id)throws SystemException;
	
	/**
	 * 查询车辆某类别计费信息
	 * @param vehicle_id
	 * @param feetype_id
	 * @return
	 */
	public FeeInfo findFeeInfo(Long vehicle_id, Integer feetype_id);
	
	/**
	 * 获取用户下计费信息
	 * @param cust_id
	 * @param lockTime
	 * @return
	 */
	public List<FeeInfo> getListBycustId(Long cust_id, String lockTime);

}

