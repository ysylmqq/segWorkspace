package com.chinaGPS.gtmp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.chinaGPS.gtmp.entity.AlarmPOJO;
import com.chinaGPS.gtmp.entity.AlarmTypePOJO;
import com.chinaGPS.gtmp.entity.DynamicMalfunctionPOJO;
import com.chinaGPS.gtmp.entity.UserAlarmTypesPOJO;
import com.chinaGPS.gtmp.mapper.AlarmMapper;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.service.IAlarmService;

/**
 * @Package:com.chinaGPS.gtmp.service.impl
 * @ClassName:HistoryServiceImpl
 * @Description:警情Service实现类
 * @author:zfy
 * @date:2013-4-9 上午10:16:38
 */
@Service
public class AlarmServiceImpl extends BaseServiceImpl<AlarmPOJO> implements IAlarmService {
	@Resource
	private AlarmMapper<AlarmPOJO> alarmMapper;

	@Override
	protected BaseSqlMapper<AlarmPOJO> getMapper() {
		return this.alarmMapper;
	}

	@Override
	public int editAlarms(List<String> idList) throws Exception {
		return alarmMapper.editAlarms(idList);
	}

	@Override
	public List<AlarmTypePOJO> getAllAlarmType(HashMap map) throws Exception {
	    return alarmMapper.getAllAlarmType(map);
	}

	@Override
	public List<Object> statisticAlarm(HashMap map, HashMap propertyMap)
			throws Exception {
		List<HashMap> result = alarmMapper.statisticAlarm(map);
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
	public List<DynamicMalfunctionPOJO> statisticAlarmToPOJO(HashMap map, HashMap propertyMap) throws Exception {
		List<HashMap> result = alarmMapper.statisticAlarm(map);
		List<DynamicMalfunctionPOJO> list = new ArrayList<DynamicMalfunctionPOJO>();
		for (HashMap<String, Object> hashMap : result) {
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
	@Override
	public List<UserAlarmTypesPOJO> getUserAlarmTypes(Long userId) throws Exception {
		return alarmMapper.getUserAlarmTypes(userId);
	}

	@Override
	public int insertUserAlarmTypes(UserAlarmTypesPOJO userAlarmTypesPOJO) throws Exception {
		return alarmMapper.insertUserAlarmTypes(userAlarmTypesPOJO);
	}

	@Override
	public int deleteUserAlarmTypes(Long userId) throws Exception {
		return alarmMapper.deleteUserAlarmTypes(userId);
	}
	
	@Override
	public boolean setUserAlarmTypes(UserAlarmTypesPOJO userAlarmTypesPOJO) throws Exception {
		int count = alarmMapper.getUserAlarmTypesCount(userAlarmTypesPOJO);
		if (count > 0) {
			return alarmMapper.updateUserAlarmTypes(userAlarmTypesPOJO) > 0 ? true : false;
		} else {
			return alarmMapper.insertUserAlarmTypes(userAlarmTypesPOJO) > 0 ? true : false;
		}
	}
}
