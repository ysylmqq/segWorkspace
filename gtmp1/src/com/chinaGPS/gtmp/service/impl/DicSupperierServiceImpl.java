package com.chinaGPS.gtmp.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinaGPS.gtmp.entity.DicSupperierPOJO;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.mapper.DicSupperierMapper;
import com.chinaGPS.gtmp.service.IDicSupperierService;

/**
 * @Package:com.chinaGPS.gtmp.service.impl
 * @ClassName:DicSupperierServiceImpl
 * @Description:终端供应商字典Service实现类
 * @author:zfy
 * @date:2013-4-9 上午10:16:38
 */
@Service
public class DicSupperierServiceImpl extends BaseServiceImpl<DicSupperierPOJO> implements IDicSupperierService {
	@Resource
	private DicSupperierMapper<DicSupperierPOJO> dicSupperierMapper;

	@Override
	protected BaseSqlMapper<DicSupperierPOJO> getMapper() {
		return this.dicSupperierMapper;
	}

}
