package com.gboss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service
 * @ClassName:FaultcodesService
 * @Description:TODO
 * @author:bzhang
 * @date:2014-4-28 上午10:15:59
 */
public interface FaultcodesService extends BaseService {
	
	public Page<HashMap<String, Object>> findFaultcodesPage(PageSelect<Map<String, Object>> pageSelect) throws SystemException;

}

