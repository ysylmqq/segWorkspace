package com.gboss.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.ObdFaultDao;
import com.gboss.service.ObdFaultService;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:ObdFaultServiceImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-12-25 上午11:16:55
 */
@Repository("obdFaultService")
@Transactional
public class ObdFaultServiceImpl extends BaseServiceImpl implements
		ObdFaultService {

	@Autowired
	@Qualifier("obdFaultDao")
	private ObdFaultDao obdFaultDao;

	@Override
	public Page<HashMap<String, Object>> findObdFaultByPage(Long companyId,
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total = obdFaultDao
				.countObdFault(companyId, pageSelect.getFilter());
		List<HashMap<String, Object>> list = obdFaultDao.findobdFaultList(
				companyId, pageSelect.getFilter(), pageSelect.getOrder(),
				pageSelect.getIs_desc(), pageSelect.getPageNo(),
				pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list,
				pageSelect.getPageSize());
	}

}
