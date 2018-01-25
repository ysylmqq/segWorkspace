package com.gboss.dao;

import java.util.HashMap;
import java.util.List;

import com.gboss.comm.SystemException;
import com.gboss.pojo.FeeInfo;

/**
 * @Package:com.gboss.dao
 * @ClassName:FeeInfoDao
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-5-29 上午9:50:41
 */
public interface FeeInfoDao extends BaseDao {
	
	public FeeInfo getFeeInfo(Long unit_id, Integer feetype_id);
	
	public FeeInfo getinsuranceInfo(Long vehicle_id);
	
	public void deleteByUnitid(Long unit_id);
	
	public List<FeeInfo> getListByid(Long unit_id);
	
	public List<HashMap<String, Object>> getFeeInfoList(Long vehicle_id)throws SystemException;
	
	public void deleteFeeInfo(FeeInfo feeInfo);
	
	public FeeInfo findFeeInfo(Long vehicle_ids, Integer feetype_id);
	
	public List<FeeInfo> getListBycustId(Long cust_id, String lockTime); 
	
	public void updateFeeInfo(Long newCust_id, Long oldCust_id);

}

