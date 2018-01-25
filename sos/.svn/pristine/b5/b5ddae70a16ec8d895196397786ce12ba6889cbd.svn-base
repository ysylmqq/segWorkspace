package com.gboss.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.SysDao;
import com.gboss.pojo.Operatelog;
import com.gboss.pojo.SysValue;
import com.gboss.service.SysService;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.chinagps.fee.service.impl
 * @ClassName:SysServiceImpl
 * @Description:系统参数类型、值 业务层实现类
 * @author:zfy
 * @date:2014-6-11 上午9:37:47
 */
@Service("sysService")
@Transactional
public class SysServiceImpl extends BaseServiceImpl implements SysService {
	
	@Autowired  
	@Qualifier("sysDao")
	private SysDao sysDao;
	
	@Override
	public List<SysValue> findSysValue(SysValue sysValue)
			throws SystemException {
		return sysDao.findSysValue(sysValue);
	}

	@Override
	public List<SysValue> findAllBank() {
		return sysDao.findAllBank();
	}
	
	@Override
	public Page<Map<String,Object>> findOperatelogPage(PageSelect<Operatelog> pageSelect)
			throws SystemException {
		int total=sysDao.countOplogs(pageSelect.getFilter());
		List<Map<String,Object>> operatelogs=sysDao.findOplogs(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), operatelogs, pageSelect.getPageSize());
	}

	@Override
	public List<Map<String, Object>> findOperatelog(
			Map<String, Object> conditionMap) throws SystemException {
		List<Map<String, Object>> list = sysDao.findOplogs(conditionMap,null,false,0,0);
		return list;
	}

}

