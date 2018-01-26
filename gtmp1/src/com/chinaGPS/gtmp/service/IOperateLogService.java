package com.chinaGPS.gtmp.service;

import java.util.List;
import java.util.Map;

import com.chinaGPS.gtmp.entity.OperateLogPOJO;

/**
 * @Package:com.chinaGPS.gtmp.service
 * @ClassName:IOperateLogService
 * @Description:操作日志接口
 * @author:lxj
 * @date:Dec 11, 2012 2:25:45 PM
 * 
 */
public interface IOperateLogService extends BaseService<OperateLogPOJO> {
	public List<Map<String, Object>> getSelectData() throws Exception;
}