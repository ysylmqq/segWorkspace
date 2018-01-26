 package com.chinaGPS.gtmp.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinaGPS.gtmp.entity.UnitPOJO;
import com.chinaGPS.gtmp.entity.UnitReplacePOJO;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.mapper.UnitMapper;
import com.chinaGPS.gtmp.mapper.UnitReplaceMapper;
import com.chinaGPS.gtmp.service.IUnitReplaceService;
import com.chinaGPS.gtmp.util.page.PageSelect;

/**
 * @Package:com.chinaGPS.gtmp.service.impl
 * @ClassName:HistoryServiceImpl
 * @Description:终端换装Service实现类
 * @author:zfy
 * @date:2013-4-9 上午10:16:38
 */
@Service @Scope("prototype")
public class UnitReplaceServiceImpl extends BaseServiceImpl<UnitReplacePOJO> implements IUnitReplaceService {
	@Resource
	private UnitMapper<UnitPOJO> unitMapper;
	@Resource
    private UnitReplaceMapper<UnitReplacePOJO> unitReplaceMapper;
	
	@Override
	public HashMap<String, Object> getUnitReplaceLogs(UnitReplacePOJO unitReplacePOJO, PageSelect pageSelect) throws Exception {
		Map<String, Serializable> map = new HashMap<String, Serializable>();
		map.put("unitReplace", unitReplacePOJO);
		
		int total = unitReplaceMapper.countAll(map); // 获取总记录数
		List<UnitReplacePOJO> datalist = unitReplaceMapper.getByPage(map, new RowBounds(pageSelect.getOffset(), pageSelect.getRows())); // 获取翻页记录列表
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", datalist);
		return result;
	}
	
	@Override
	@Transactional
	public boolean unitReplace(UnitReplacePOJO unitReplacePOJO) throws Exception {
		int i = unitReplaceMapper.unitReplace(unitReplacePOJO); // 修改机械和终端的绑定关系
		
		UnitPOJO unitPOJO = new UnitPOJO();
		unitPOJO.setUnitId(unitReplacePOJO.getOldUnitId());
		unitPOJO.setState(9);
		int j = unitMapper.edit(unitPOJO); // 修改原终端状态为返修
		
		unitPOJO.setUnitId(unitReplacePOJO.getNewUnitId());
		unitPOJO.setState(2);
		int k = unitMapper.edit(unitPOJO); // 修改现终端状态为已安装
		
		int l = unitReplaceMapper.add(unitReplacePOJO); // 记录日志
		
		if(i>0 && j>0 && k>0 && l>0) {
			return true;
		} else {
			return false;
		}
		
	}

	@Override
	protected BaseSqlMapper<UnitReplacePOJO> getMapper() {
		return unitReplaceMapper;
	}

}
