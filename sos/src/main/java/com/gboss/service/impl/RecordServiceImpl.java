package com.gboss.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.RecordDao;
import com.gboss.pojo.Record;
import com.gboss.service.RecordService;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:RecordServiceImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-8-13 下午3:22:13
 */
@Service("recordService")
@Transactional
public class RecordServiceImpl extends BaseServiceImpl implements RecordService {

	@Autowired
	@Qualifier("recordDao")
	private RecordDao recordDao;

	@Override
	public Page<HashMap<String, Object>> findRecord(Long companyno,
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total = recordDao.countRecord(companyno, pageSelect.getFilter());
		List<HashMap<String, Object>> list = recordDao.findRecord(companyno,
				pageSelect.getFilter(), pageSelect.getOrder(),
				pageSelect.getIs_desc(), pageSelect.getPageNo(),
				pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list,
				pageSelect.getPageSize());
	}

	@Override
	public boolean is_exist(Record record) {
		return recordDao.is_exist(record);
	}

	
	@Override
	public int delete(List<Long> ids) throws SystemException {
		int result=1;
		if(ids==null || ids.isEmpty()){
			result=-1;
		}else{
			recordDao.delete(ids);
		}
		return result;
	}

}
