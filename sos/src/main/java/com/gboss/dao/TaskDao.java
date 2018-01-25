package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Task;

/**
 * 
 * @ClassName:TaskDao.java
 * @Description :任务单管理数据持久层接口
 * @author bzhang
 * @date 2014-3-21
 */
public interface TaskDao extends BaseDao {

	public int getMaxTaskNo(Long orgId, String date) throws SystemException;
	
	public List<HashMap<String, Object>> findTasks(Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;

	public int countTasks(Map<String, Object> conditionMap) throws SystemException;
	
	public List<HashMap<String, Object>> findTasksForReservation(Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;
	
	public int countTasksforReservation(Map<String, Object> conditionMap) throws SystemException;

	public List<HashMap<String, Object>> getTaskReHistory(Long id)throws SystemException;
	
	public int deleteTasks(List<Long> ids) throws SystemException;
	
	public int endTasks(List<Long> ids) throws SystemException;
	
	public Task findTaskByDistach(Long id) throws SystemException;
	
	public List<Task> getTaskByElctrician(Long id) throws SystemException;
	
	public HashMap<String,Object> getTaskDetailMsg(Long id)throws SystemException;
	
	
	public List<HashMap<String, Object>> findTasksPage(Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize, Long orgId, Long userId) throws SystemException;
	

	public int countTasksPage(Map<String, Object> conditionMap, Long orgId, Long userId) throws SystemException;
	
	public int countTasksBycl(Map<String, Object> conditionMap) throws SystemException;
	
	public List<HashMap<String, Object>> findTasksBycl(Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;
	
	public List<Task> getTaskByVId(Long vehicle_id, Integer type) throws SystemException;
	
}
