package com.chinaGPS.gtmp.mapper;

import org.springframework.stereotype.Component;

import com.chinaGPS.gtmp.entity.HistoryPOJO;
@Component
public interface HistoryMapper<T extends HistoryPOJO> extends BaseSqlMapper<T> {
}