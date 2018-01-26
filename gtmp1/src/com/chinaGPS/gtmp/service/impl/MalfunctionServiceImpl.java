package com.chinaGPS.gtmp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.chinaGPS.gtmp.entity.DicMalfunctionCode;
import com.chinaGPS.gtmp.entity.DynamicMalfunctionPOJO;
import com.chinaGPS.gtmp.entity.MalfunctionPOJO;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.mapper.MalfunctionMapper;
import com.chinaGPS.gtmp.service.IMalfunctionService;

/**
 * @Package:com.chinaGPS.gtmp.service.impl
 * @ClassName:MalfunctionServiceImpl
 * @Description:故障诊断Service实现类
 * @author:zfy
 * @date:2013-5-3 上午10:16:38
 */
@Service
public class MalfunctionServiceImpl extends BaseServiceImpl<MalfunctionPOJO>
		implements IMalfunctionService {
	@Resource
	private MalfunctionMapper<MalfunctionPOJO> malfunctionMapper;

	@Override
	protected BaseSqlMapper<MalfunctionPOJO> getMapper() {
		return this.malfunctionMapper;
	}

	@Override
	public List<DicMalfunctionCode> getDicMalfunctionCode(
			DicMalfunctionCode dicMalfunctionCode) throws Exception {
		return malfunctionMapper.getDicMalfunctionCode(dicMalfunctionCode);
	}

	@Override
	public List<Object> statisticMalfunction(HashMap map, HashMap propertyMap)
			throws Exception {
		List<HashMap> result = malfunctionMapper.statisticMalfunction(map);
		List<Object> list = new ArrayList<Object>();
		Object object = null;
		for (HashMap hashMap : result) {
			Set<String> set = hashMap.keySet();
			DynamicMalfunctionPOJO work = new DynamicMalfunctionPOJO(propertyMap);
			Iterator<String> it = set.iterator();
			while (it.hasNext()) {
				String column = it.next();
				work.setValue(replace_toUpperCase(column.toLowerCase()), String.valueOf(hashMap.get(column)));
			}
			object = work.getObject();
			list.add(object);
		}
		return list;
	}

	private String replace_toUpperCase(String str) throws Exception {
		String result = null;
		if (StringUtils.isNotEmpty(str)) {
			StringBuffer sb = new StringBuffer();
			sb.append(str);
			int count = sb.indexOf("_");
			while (count != 0) {
				int num = sb.indexOf("_", count);
				count = num + 1;
				if (num != -1) {
					char ss = sb.charAt(count);
					char ia = (char) (ss - 32);
					sb.replace(count, count + 1, ia + "");
				}
			}
			result = sb.toString().replaceAll("_", "");
		}
		return result;
	}

	@Override
	public List<DynamicMalfunctionPOJO> statisticMalfunctionToPOJO(HashMap map, HashMap propertyMap) throws Exception {
		List<HashMap> result = malfunctionMapper.statisticMalfunction(map);
		List<DynamicMalfunctionPOJO> list = new ArrayList<DynamicMalfunctionPOJO>();
		for (HashMap hashMap : result) {
			Set<String> set = hashMap.keySet();
			DynamicMalfunctionPOJO work = new DynamicMalfunctionPOJO(propertyMap);
			Iterator<String> it = set.iterator();
			while (it.hasNext()) {
				String column = it.next();
				work.setValue(replace_toUpperCase(column.toLowerCase()), String.valueOf(hashMap.get(column)));
			}
			list.add(work);
		}
		return list;
	}

}
