package com.gboss.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ldap.objectClasses.CommonCompany;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.dao.TaskDao;
import com.gboss.pojo.Task;
import com.gboss.service.TaskService;
import com.gboss.util.DateUtil;
import com.gboss.util.PageSelect;
import com.gboss.util.Utils;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * 
 * @ClassName:TaskServiceImpl.java
 * @Description: TODO
 * @author: bzhang
 * @date :2014-3-21
 */
@Service("taskService")
@Transactional
public class TaskServiceImpl extends BaseServiceImpl implements TaskService {
	
	
	@Autowired  
	@Qualifier("taskDao")
	private TaskDao taskDao;

	@Override
	public String getTaskNo(Long orgId) throws SystemException {
		OpenLdap openLdap=OpenLdapManager.getInstance();
		CommonCompany commonCompany=null;
		String number=null;
		
		if(orgId!=null){
			commonCompany=openLdap.getCompanyById(orgId.toString());
			number=commonCompany.getCompanycode();
		}
		//流水号加1，前面不足4位，用0补充
		String serialNoStr=Utils.formatSerial(taskDao.getMaxTaskNo(orgId, DateUtil.formatToday()));
		//return SystemConst.TASK_NO_PREFIX+number+DateUtil.format(new Date(), DateUtil.YMD)+serialNoStr;
		return SystemConst.TASK_NO_PREFIX+DateUtil.format(new Date(), DateUtil.YMD)+serialNoStr;
	}

	@Override
	public Page<HashMap<String, Object>> findTasks(PageSelect<Map<String, Object>> pageSelect)
			throws SystemException {
		int total=taskDao.countTasks(pageSelect.getFilter());
		List<HashMap<String, Object>> list=taskDao.findTasks(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}
	
	
	@Override
	public Page<HashMap<String, Object>> findTasksPage(
			PageSelect<Map<String, Object>> pageSelect, Long companyId, Long userId)
			throws SystemException {
		int total=taskDao.countTasksPage(pageSelect.getFilter(), companyId, userId);
		List<HashMap<String, Object>> list=taskDao.findTasksPage(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize(), companyId, userId);
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}
	
	@Override
	public Page<HashMap<String, Object>> findTasksforReservation(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total=taskDao.countTasksforReservation(pageSelect.getFilter());
		List<HashMap<String, Object>> list=taskDao.findTasksForReservation(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

	@Override
	public List<HashMap<String, Object>> getTaskReHistory(Long id)
			throws SystemException {
		return taskDao.getTaskReHistory(id);
	}

	@Override
	public int deleteTasks(List<Long> ids) throws SystemException {
		int result=1;
		if(ids==null || ids.isEmpty()){
			result=-1;
		}else{
			taskDao.deleteTasks(ids);
		}
		return result;
	}

	@Override
	public int endTasks(List<Long> ids) throws SystemException {
		int result=1;
		if(ids==null || ids.isEmpty()){
			result=-1;
		}else{
			taskDao.endTasks(ids);
		}
		return result;
	}

	@Override
	public Task findTaskByDistach(Long id) throws SystemException {
		return taskDao.findTaskByDistach(id);
	}

	@Override
	public List<Task> getTaskByElctrician(Long id) throws SystemException {
		return taskDao.getTaskByElctrician(id);
	}

	@Override
	public HashMap<String, Object> getTaskDetailMsg(Long id)
			throws SystemException {
		return taskDao.getTaskDetailMsg(id);
	}

	@Override
	public List<Task> getTaskByVId(Long vehicle_id, Integer type)
			throws SystemException {
		return taskDao.getTaskByVId(vehicle_id, type);
	}

	@Override
	public Page<HashMap<String, Object>> findTasksBycl(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total=taskDao.countTasksBycl(pageSelect.getFilter());
		List<HashMap<String, Object>> list=taskDao.findTasksBycl(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

}
