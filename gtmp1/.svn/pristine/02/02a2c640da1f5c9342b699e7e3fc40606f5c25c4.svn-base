package com.chinaGPS.gtmp.mapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import com.chinaGPS.gtmp.entity.ReseachAnswerPOJO;
@Component
public interface ReseachAnswerMapper<T extends ReseachAnswerPOJO> extends BaseSqlMapper<T> {
	
	//public UserInfoPOJO selectQuestion(T entity) throws Exception;
	
	public List<T> selectQuestion(Map<String, Serializable> map,RowBounds rowBounds) throws Exception;
	
	
	
	public T getByIdReseachAnswer(ReseachAnswerPOJO reseachAnswerPOJO) throws Exception;
	
	
}