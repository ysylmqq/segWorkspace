package com.gboss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.BankcodeDao;
import com.gboss.pojo.Bankcode;
import com.gboss.service.BankcodeService;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:BankcodeServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-6-4 下午4:30:19
 */
@Service("BankcodeService")
@Transactional
public class BankcodeServiceImpl extends BaseServiceImpl implements BankcodeService {

	@Autowired  
	@Qualifier("BankcodeDao")
	private BankcodeDao bankcodeDao;
	
	@Override
	public List<Bankcode> getBankcodeList(Long subco_no, Integer agency)throws SystemException{
		return bankcodeDao.getBankcodeList(subco_no, agency);
	}

	@Override
	public List<Bankcode> getBankcodeList(Long subco_no)throws SystemException{
		return bankcodeDao.getBankcodeList(subco_no);
	}

}

