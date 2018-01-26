package com.chinaGPS.gtmp.mapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import com.chinaGPS.gtmp.entity.ProjectInfoPOJO;
import com.chinaGPS.gtmp.entity.ReseachPOJO;
import com.chinaGPS.gtmp.entity.TLastConditionsPOJO;
import com.chinaGPS.gtmp.entity.TLastPositionPOJO;
import com.chinaGPS.gtmp.entity.TestCommandPOJO;
import com.chinaGPS.gtmp.entity.UnitPOJO;
import com.chinaGPS.gtmp.entity.UserInfoPOJO;
import com.chinaGPS.gtmp.entity.VehiclePOJO;
@Component
public interface ReseachMapper<T extends ReseachPOJO> extends BaseSqlMapper<T> {
	
	//public UserInfoPOJO selectQuestion(T entity) throws Exception;
	
	public List<T> selectQuestion(Map<String, Serializable> map,RowBounds rowBounds) throws Exception;
	
	public List<T> selectReseachList(Map<String, Serializable> map,RowBounds rowBounds) throws Exception;
	
	public int countReseachList(Map<String, Serializable> map) throws Exception;
	
	
	
	
}