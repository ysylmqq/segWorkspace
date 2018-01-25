package com.gboss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Task;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * 
 * @ClassName:TaskService.java
 * @Description: TODO
 * @author: bzhang
 * @date :2014-3-21
 */
public interface TaskService extends BaseService {
	/**
	 * 
		 * @Title:getTaskNo
		 * @Description:获取随机生成接单单号
		 * @param @param companyId
		 * @param @return
		 * @param @throws SystemException 
		 * @return String
	 */
	public String getTaskNo(Long orgId) throws SystemException;
	
	public Page<HashMap<String, Object>> findTasks(PageSelect<Map<String, Object>> pageSelect) throws SystemException;
	
	public Page<HashMap<String, Object>> findTasksforReservation(PageSelect<Map<String, Object>> pageSelect) throws SystemException;
	
	public List<HashMap<String, Object>> getTaskReHistory(Long id) throws SystemException;
	
	public int deleteTasks(List<Long> ids) throws SystemException;
	
	public int endTasks(List<Long> ids) throws SystemException;
	
	public Task findTaskByDistach(Long id) throws SystemException;
	
	public List<Task> getTaskByElctrician(Long id) throws SystemException;
	
	public HashMap<String,Object> getTaskDetailMsg(Long id)throws SystemException;
	
	public Page<HashMap<String, Object>> findTasksPage(PageSelect<Map<String, Object>> pageSelect, Long orgId, Long userId) throws SystemException;

	
	public Page<HashMap<String, Object>> findTasksBycl(PageSelect<Map<String, Object>> pageSelect) throws SystemException;
	
	
	public List<Task> getTaskByVId(Long vehicle_id, Integer type) throws SystemException;
}
