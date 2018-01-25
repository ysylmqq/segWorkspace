package com.gboss.service;

import java.lang.reflect.InvocationTargetException;
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
	
	public List<HashMap<String, Object>> findSimList(Long companyId, Map<String, Object> conditionMap) throws SystemException;
	
	public int operate(List<Long> ids, Integer type) throws Exception;
	
	public int addSim(Preload sim)throws SystemException;
	
	public Map<String, Object> importSim(List<String[]> dataList,
			Long compannyId, Long userId,Integer type,Integer telco) throws SystemException;
	
	/**
	 * 修改之后的导入SIM的功能
	 * @param dataList
	 * @param compannyId
	 * @param userId
	 * @param type
	 * @param telco   运营商
	 * @return
	 * @throws SystemException
	 */
	public Map<String, Object> importSimNew(List<String[]> dataList,
			Long compannyId, Long userId,Integer type,Integer telco) throws SystemException;
	
	
	
	/**
	 * 修改之后的导入SIM的功能
	 * @param dataList
	 * @param compannyId
	 * @param userId
	 * @param type
	 * @return
	 * @throws SystemException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public Map<String, Object> batchUpdateSimFlag(List<String[]> dataList,
			Long compannyId, Long userId,Integer type) throws SystemException, IllegalAccessException, InvocationTargetException;
	
	/**
	 * 修改之后的导入SIM的功能
	 * @param dataList
	 * @param compannyId
	 * @param userId
	 * @param type
	 * @return
	 * @throws SystemException
	 */
	public Map<String, Object> batchUpdateSimFlagThread(List<String[]> dataList,
			Long compannyId, Long userId,Integer type) throws SystemException;
	
	public Map<String, Object> importUBI(List<String[]> dataList,
		Long compannyId, Long userId,Integer type,Integer telco) throws SystemException;
	
	public int operateCombo(List<Long> ids, Integer type) throws SystemException; 
	
	public Preload getPreloadByCl(String call_letter)throws SystemException;
	
	public List<Preload> getAll()throws SystemException;

	public Page<Map<String, Object>> findUbiByPage(PageSelect<Map<String, Object>> pageSelect)throws SystemException;

	public Map<String, Object> verifySim(List<String[]> dataList, Long compannyId, Long userId)throws SystemException;
}

