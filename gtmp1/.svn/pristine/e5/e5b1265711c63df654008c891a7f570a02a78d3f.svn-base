package com.chinaGPS.gtmp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinaGPS.gtmp.entity.DepartPOJO;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.mapper.DepartMapper;
import com.chinaGPS.gtmp.service.IDepartService;

/**
 * @Package:com.chinaGPS.gtmp.service.impl
 * @ClassName:DepartServiceImpl
 * @Description:机构Service层实现类
 * @author:zfy
 * @date:2012-12-17 下午04:11:08
 * 
 */
@Service
public class DepartServiceImpl extends BaseServiceImpl<DepartPOJO> implements IDepartService {
	@Resource
	private DepartMapper<DepartPOJO> departMapper;

	@Override
	protected BaseSqlMapper<DepartPOJO> getMapper() {
		return this.departMapper;
	}

	@Override
	public boolean deleteDepartById(Long id) throws Exception {
		return departMapper.removeById(id) > 0 ? true : false;
	}

	@Override
	public DepartPOJO getById(Long id) throws Exception {
		return departMapper.getById(id);
	}

	@Override
	public List<DepartPOJO> getDeparts(DepartPOJO departPOJO) throws Exception {
		return departMapper.getList(departPOJO);
	}

	@Override
	public boolean saveOrUpdate(DepartPOJO departPOJO) throws Exception {
		boolean flag = false;
		if (departPOJO.getDepartId() == null) {
			if (departMapper.add(departPOJO) > 0) {
				flag = true;
			}
		} else {
			if (departMapper.edit(departPOJO) > 0) {
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public int hasDepartByName(DepartPOJO departPOJO) throws Exception {
		return departMapper.hasDepartByName(departPOJO);
	}

	@Override
	public List<DepartPOJO> getList4User(DepartPOJO departPOJO) throws Exception {
		return departMapper.getList4User(departPOJO);
	}

	@Override
	public int removeBySupperierSn(String supperierSn) throws Exception {
		return departMapper.removeBySupperierSn(supperierSn);
	}

}
