package com.chinaGPS.gtmp.util.typeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class JSONTypeHandler implements TypeHandler {

	@Override
	public Object getResult(ResultSet rs, String cName) throws SQLException {
		
		return null;
	}

	@Override
	public Object getResult(CallableStatement cs, int cIndex)
			throws SQLException {
		
		return null;
	}

	@Override
	public void setParameter(PreparedStatement ps, int i, Object param,
			JdbcType ARRAY) throws SQLException {
		String json = null;
		if (param == null) {
			ps.setString(i,null);
			return;
		}
		json = JSONObject.fromObject(param).toString();
		json = JSONSerializer.toJSON(param).toString();
		json = JSONArray.fromObject(param).toString();
		ps.setString(i,json);
	}

}
