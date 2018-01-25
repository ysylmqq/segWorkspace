package com.gboss.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.PaymentSimDao;
import com.gboss.service.PaymentSimService;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:PaymentServiceImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-12-29 下午4:30:55
 */
@Service("paymentSimService")
@Transactional
public class PaymentServiceImpl extends BaseServiceImpl implements
		PaymentSimService {

	@Autowired  
	@Qualifier("paymentSimDao")
	private PaymentSimDao paymentSimDao;

	@Override
	public Page<HashMap<String, Object>> findRecordsPage(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total = paymentSimDao.countRecords(pageSelect.getFilter());
		List<HashMap<String, Object>> list=paymentSimDao.findRecordsPage( pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

	@Override
	public Page<HashMap<String, Object>> findPaymentSimPage(Long companyId,
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total = paymentSimDao.countPaymentSimPage(companyId,pageSelect.getFilter());
		List<HashMap<String, Object>> list=paymentSimDao.findPaymentSimPage(companyId, pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}
	


}

