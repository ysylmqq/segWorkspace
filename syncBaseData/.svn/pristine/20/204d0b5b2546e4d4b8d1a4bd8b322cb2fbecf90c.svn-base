package com.gboss.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gboss.dao.FeeInfoDao;
import com.gboss.pojo.FeeInfo;
import com.gboss.service.FeeInfoService;

@Service("feeInfoService")
public class FeeInfoServiceImpl extends BaseServiceImpl implements
		FeeInfoService {

	@Autowired
	private FeeInfoDao feeInfoDao;
	
	public FeeInfo getFeeInfo(FeeInfo fi) throws Exception {
		return feeInfoDao.getFeeInfo(fi);
	}

	@Override
	public List<FeeInfo> getFeeInfo(Map<String, Long> queryCondition)
			throws Exception {
		return feeInfoDao.getFeeInfo(queryCondition);
	}
	
	public List<FeeInfo> queryFeeInfo(Map<String, Long> queryCondition)
			throws Exception {
		return feeInfoDao.queryFeeInfo(queryCondition);
	}

}
