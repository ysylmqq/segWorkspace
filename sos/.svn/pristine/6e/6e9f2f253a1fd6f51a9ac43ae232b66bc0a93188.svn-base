package com.gboss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.SimpackDao;
import com.gboss.pojo.Combo;
import com.gboss.pojo.Simpack;
import com.gboss.pojo.Subcoft;
import com.gboss.service.SimpackService;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:SimpackServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-12-11 下午5:19:31
 */
@Service("SimpackService")
@Transactional
public class SimpackServiceImpl extends BaseServiceImpl implements SimpackService {
	
	@Autowired  
	@Qualifier("SimpackDao")
	private SimpackDao simpackDao;

	@Override
	public List<Subcoft> getSubsofts(Long subco_no) {
		return simpackDao.getSubsofts(subco_no);
	}

	@Override
	public List<Simpack> getSimpacks(Long subco_no, Integer feetype_id) {
		return simpackDao.getSimpacks(subco_no, feetype_id);
	}

	@Override
	public Combo getComboByid(Long combo_id) {
		return simpackDao.getComboByid(combo_id);
	}

	@Override
	public Simpack getSimpackByid(Long pack_id) {
		return simpackDao.getSimpackByid(pack_id);
	}

}

