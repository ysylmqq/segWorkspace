package com.chinaGPS.gtmp.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaGPS.gtmp.entity.OperateLogPOJO;

@Component
public interface OperateLogMapper<T extends OperateLogPOJO> extends BaseSqlMapper<T> {
	public List<Map<String, Object>> getSelectData() throws Exception;
}