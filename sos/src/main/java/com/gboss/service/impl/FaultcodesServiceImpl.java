package com.gboss.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.FaultcodesDao;
import com.gboss.service.FaultcodesService;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:FaultcodesServiceImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-4-28 上午10:17:51
 */
@Repository("faultcodesService")  
@Transactional  
public class FaultcodesServiceImpl extends BaseServiceImpl implements
		FaultcodesService {

	@Autowired  
	@Qualifier("faultcodesDao")
	private FaultcodesDao faultcodesDao;

	@Override
	public Page<HashMap<String, Object>> findFaultcodesPage(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total=faultcodesDao.countFaultcodes(pageSelect.getFilter());
		List<HashMap<String, Object>> list=faultcodesDao.findFaultcodesPage(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}
	
	
	
}

