package com.chinaGPS.gtmp.mapper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaGPS.gtmp.entity.DicMalfunctionCode;
import com.chinaGPS.gtmp.entity.MalfunctionPOJO;
@Component
public interface MalfunctionMapper<T extends MalfunctionPOJO> extends BaseSqlMapper<T> {
    public List<DicMalfunctionCode> getDicMalfunctionCode(DicMalfunctionCode dicMalfunctionCode) throws Exception;
    
    public List<HashMap> statisticMalfunction(Map<String, Serializable> map) throws Exception;
}