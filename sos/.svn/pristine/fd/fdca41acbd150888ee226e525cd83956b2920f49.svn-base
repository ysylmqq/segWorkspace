package com.gboss.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.TaskFlowDao;
import com.gboss.pojo.TaskFlow;
import com.gboss.service.TaskFlowService;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:TaskFlowServiceImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-4-18 上午10:08:44
 */
@Service("taskFlowService")
@Transactional
public class TaskFlowServiceImpl extends BaseServiceImpl implements
		TaskFlowService {
	
	@Autowired  
	@Qualifier("taskFlowDao")
	private TaskFlowDao taskFlowDao;

	@Override
	public List<String> getDispatchers(Long taskId) {
		List<String> list = new ArrayList<String>();
		TaskFlow taskFlow = taskFlowDao.getTaskFlow(taskId);
		if(null != taskFlow){
			String [] dispatchers = taskFlow.getCharger_id().split(",");
			for (String str : dispatchers) {
				list.add(str);
			}
		}
		return list;
	}

	@Override
	public List<HashMap<String, Object>> getTaskFlowsDetail(Long task_id)
			throws SystemException {
		return taskFlowDao.getTaskFlowsDetail(task_id);
	}

}

