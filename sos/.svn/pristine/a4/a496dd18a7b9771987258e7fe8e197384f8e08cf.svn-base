package com.gboss.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.FeeInfoDao;
import com.gboss.pojo.FeeInfo;
import com.gboss.service.FeeInfoService;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:FeeInfoServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-5-28 上午9:00:05
 */

@Repository("FeeInfoService")  
@Transactional  
public class FeeInfoServiceImpl extends BaseServiceImpl implements FeeInfoService {

	@Autowired
	@Qualifier("FeeInfoDao")
	private FeeInfoDao feeInfoDao;
	
	@Override
	public Long add(FeeInfo feeInfo) {
		save(feeInfo);
		return feeInfo.getFee_id();
	}

	@Override
	public void delete(Long id) {
		delete(FeeInfo.class, id);
	}

	@Override
	public FeeInfo getFeeInfo(Long unit_id, Integer feetype_id) {
		return feeInfoDao.getFeeInfo(unit_id, feetype_id);
	}

	@Override
	public void deleteByUnitid(Long unit_id) {
		feeInfoDao.deleteByUnitid(unit_id);
	}

	@Override
	public List<HashMap<String, Object>> getFeeInfoList(Long vehicle_id)
			throws SystemException {
		return feeInfoDao.getFeeInfoList(vehicle_id);
	}

	@Override
	public FeeInfo getinsuranceInfo(Long vehicle_id) {
		return feeInfoDao.getinsuranceInfo(vehicle_id);
	}

	@Override
	public List<FeeInfo> getListByid(Long unit_id) {
		return feeInfoDao.getListByid(unit_id);
	}

	@Override
	public void deleteFeeInfo(FeeInfo feeInfo) {
		feeInfoDao.deleteFeeInfo(feeInfo);
	}

	@Override
	public FeeInfo findFeeInfo(Long vehicle_id, Integer feetype_id) {
		return feeInfoDao.findFeeInfo(vehicle_id, feetype_id);
	}

	@Override
	public List<FeeInfo> getListBycustId(Long cust_id, String lockTime) {
		return feeInfoDao.getListBycustId(cust_id, lockTime);
	}

}

