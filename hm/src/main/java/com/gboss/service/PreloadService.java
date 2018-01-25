package com.gboss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Preload;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service
 * @ClassName:SimService
 * @Description:TODO
 * @author:bzhang
 * @date:2014-9-23 上午11:11:13
 */
public interface PreloadService extends BaseService {
	
	public void testTask() throws Exception; 
	
	public void gethmCall_letter() throws Exception; 

	public Page<HashMap<String, Object>> findSimByPage(Long companyId, PageSelect<Map<String, Object>> pageSelect) throws SystemException;
	
	public int operate(List<Long> ids, Integer type) throws Exception;
	
	public int addSim(Preload sim)throws SystemException;
	
	public Map<String, Object> importSim(List<String[]> dataList,
			Long compannyId, Long userId,Integer type,Integer telco) throws SystemException;
	
	public int operateCombo(List<Long> ids, Integer type) throws SystemException; 
	
	public Preload getPreloadByCl(String call_letter)throws SystemException;
	
	public List<Preload> getSims(Map<String,Object> params)throws SystemException;
	
}
