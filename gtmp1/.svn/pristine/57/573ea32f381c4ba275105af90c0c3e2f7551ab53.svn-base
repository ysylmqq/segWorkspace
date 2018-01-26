package com.chinaGPS.gtmp.util.typeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.sql.ARRAY;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * @Package:com.chinaGPS.gtmp.util.typeHandler
 * @ClassName:ARRAYTypeHandler
 * @Description:jdbcType中自定义ARRAY处理类
 * @author:lxj
 * @date:Dec 17, 2012 2:45:44 PM
 *
 */
public class ARRAYTypeHandler implements TypeHandler {

	@Override
	public Object getResult(ResultSet rs, String cName) throws SQLException {
		return rs.getObject(cName);
	}

	@Override
	public Object getResult(CallableStatement cs, int cIndex)
			throws SQLException {
		return cs.getObject(cIndex);
	}

	@Override
	public void setParameter(PreparedStatement ps, int i, Object param,
			JdbcType ARRAY) throws SQLException {
		if (param == null) {
			ps.setArray(i,null);
			return;
		}
		ps.setArray(i,(ARRAY)param);
	}

}
