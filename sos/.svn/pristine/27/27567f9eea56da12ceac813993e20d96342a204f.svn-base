package com.gboss.dao;

import java.util.HashMap;
import java.util.List;

import com.gboss.comm.SystemException;
import com.gboss.pojo.DispatchTask;
import com.gboss.pojo.Task;

/**
 * @Package:com.gboss.dao
 * @ClassName:DispatchTaskDao
 * @Description:派工单数据持久层接口
 * @author:bzhang
 * @date:2014-3-27 下午6:24:08
 */
public interface DispatchTaskDao extends BaseDao {

	public List<HashMap<String,Object>> getElectricians(Long taskId)throws SystemException;
	
	public DispatchTask findTaskByDistach(Long id) throws SystemException; 
}


