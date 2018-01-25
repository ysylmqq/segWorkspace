package com.gboss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.MaxidDao;
import com.gboss.service.MaxidService;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:MaxidServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-8-6 下午2:55:44
 */
@Service("MaxidService")
@Transactional
public class MaxidServiceImpl extends BaseServiceImpl implements MaxidService {

	@Autowired
	@Qualifier("MaxidDao")
	private MaxidDao maxidDao;
	
	@Override
	public Long getAndAddMaxid(Long subco_no) throws SystemException{
		return maxidDao.getAndAddMaxid(subco_no);
	}

	@Override
	public String getPayCtNo(Long subco_no) throws SystemException {
		Long maxId = maxidDao.getAndAddMaxid(subco_no);
		String pay_ct_no = String.valueOf(maxId);
		while(pay_ct_no.length()<10){
			pay_ct_no = "0" + pay_ct_no;
		}
		return pay_ct_no;
	}

}

