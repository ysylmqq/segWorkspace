package com.chinaGPS.gtmp.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinaGPS.gtmp.entity.SystemParamPOJO;
import com.chinaGPS.gtmp.mapper.SystemParamMapper;
import com.chinaGPS.gtmp.service.ISystemParamService;

/**
 * @Package:com.chinaGPS.gtmp.service.impl
 * @ClassName:SystemParamServiceImpl
 * @Description:系统参数配置业务层实现类
 * @author:zfy
 * @date:2013-5-22 下午05:31:54
 *
 */
@Service
public class SystemParamServiceImpl extends BaseServiceImpl<SystemParamPOJO> implements ISystemParamService{
    
    @Resource
    private SystemParamMapper<SystemParamPOJO> systemParamMapper;
    
    @Override
    protected SystemParamMapper<SystemParamPOJO> getMapper() {
		return this.systemParamMapper;
    }
}