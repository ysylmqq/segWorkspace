package com.chinaGPS.gtmp.mapper;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.chinaGPS.gtmp.entity.UnitReplacePOJO;

/**
 * 终端换装DAO接口
 * @author Ben
 *
 * @param <T>
 */
@Component
public interface UnitReplaceMapper<T extends UnitReplacePOJO> extends BaseSqlMapper<T> {
	
	/**
	 * 终端换装DAO
	 * @param unitReplacePOJO
	 * @return
	 * @throws DataAccessException
	 */
	public int unitReplace(UnitReplacePOJO unitReplacePOJO) throws Exception;
}