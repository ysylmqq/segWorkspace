package com.chinaGPS.gtmp.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinaGPS.gtmp.entity.DicUnitTypePOJO;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.mapper.DicUnitTypeMapper;
import com.chinaGPS.gtmp.service.IDicUnitTypeService;

/**
 * @Package:com.chinaGPS.gtmp.service.impl
 * @ClassName:DicUnitTypeServiceImpl
 * @Description:终端供应商字典Service实现类
 * @author:zfy
 * @date:2013-4-9 上午10:16:38
 */
@Service
public class DicUnitTypeServiceImpl extends BaseServiceImpl<DicUnitTypePOJO> implements IDicUnitTypeService {
	@Resource
	private DicUnitTypeMapper<DicUnitTypePOJO> dicUnitTypeMapper;

	@Override
	protected BaseSqlMapper<DicUnitTypePOJO> getMapper() {
		return this.dicUnitTypeMapper;
	}

}
