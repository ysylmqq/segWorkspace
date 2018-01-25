package com.gboss.service;

import java.util.HashMap;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service
 * @ClassName:PaymentSimService
 * @Description:TODO
 * @author:bzhang
 * @date:2014-12-29 下午4:30:23
 */
public interface PaymentSimService extends BaseService {
	
	public Page<HashMap<String, Object>> findRecordsPage(PageSelect<Map<String, Object>> pageSelect) throws SystemException;
	
	public Page<HashMap<String, Object>> findPaymentSimPage(Long companyId, PageSelect<Map<String, Object>> pageSelect) throws SystemException;

}

