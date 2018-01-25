package com.gboss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.InsurerDao;
import com.gboss.pojo.Insurer;
import com.gboss.service.InsurerService;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:InsurerServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-12-24 下午2:09:44
 */
@Repository("InsurerService")  
@Transactional
public class InsurerServiceImpl extends BaseServiceImpl implements InsurerService {

	@Autowired
	@Qualifier("InsurerDao")
	private InsurerDao insurerDao;
	
	@Override
	public List<Insurer> getInsurersByname(String insurer_name) {
		return insurerDao.getInsurersByname(insurer_name);
	}

}
