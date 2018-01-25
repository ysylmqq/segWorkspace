package com.gboss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Pack;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service
 * @ClassName:PackService
 * @Description:TODO
 * @author:bzhang
 * @date:2014-11-5 下午3:56:14
 */
public interface PackService extends BaseService {

	public Page<HashMap<String, Object>> findPackPage(Long companyno,
			PageSelect<Map<String, Object>> pageSelect) throws SystemException;
			
	public int addPack(Pack pack);	
	
	public Boolean isExist(Pack pack)throws SystemException;
	
	public int delete(List<Long> ids) throws SystemException;

}
