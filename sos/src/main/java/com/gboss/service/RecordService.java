package com.gboss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Record;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service
 * @ClassName:RecordService
 * @Description:TODO
 * @author:bzhang
 * @date:2014-8-13 下午3:20:33
 */
public interface RecordService extends BaseService {
	
	public Page<HashMap<String, Object>> findRecord(Long companyno, 
			PageSelect<Map<String, Object>> pageSelect) throws SystemException;

	public boolean is_exist(Record record);
	
	public int delete(List<Long> ids) throws SystemException;
}

