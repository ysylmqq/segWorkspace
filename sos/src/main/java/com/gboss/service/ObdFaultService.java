package com.gboss.service;

import java.util.HashMap;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service
 * @ClassName:ObdFaultService
 * @Description:TODO
 * @author:bzhang
 * @date:2014-12-25 上午11:15:06
 */
public interface ObdFaultService extends BaseService {
	
	public Page<HashMap<String, Object>> findObdFaultByPage(Long companyId, PageSelect<Map<String, Object>> pageSelect) throws SystemException;

}

