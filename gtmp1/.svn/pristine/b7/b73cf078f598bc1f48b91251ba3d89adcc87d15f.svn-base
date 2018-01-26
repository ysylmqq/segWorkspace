package com.chinaGPS.gtmp.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinaGPS.gtmp.entity.OperateLogPOJO;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.mapper.OperateLogMapper;
import com.chinaGPS.gtmp.service.IOperateLogService;

/**
 * @Package:com.chinaGPS.gtmp.service.impl
 * @ClassName:OperateLogServiceImpl
 * @Description:操作日志
 * @author:zfy
 * @date:Dec 11, 2012 2:43:44 PM
 *
 */
@Service
public class OperateLogServiceImpl extends BaseServiceImpl<OperateLogPOJO> implements IOperateLogService{
    @Resource
    private OperateLogMapper<OperateLogPOJO> operateLogMapper;
    
    @Override
    protected BaseSqlMapper<OperateLogPOJO> getMapper() {
		return this.operateLogMapper;
    }

	@Override
	public List<Map<String, Object>> getSelectData() throws Exception {
		return operateLogMapper.getSelectData();
	}

}