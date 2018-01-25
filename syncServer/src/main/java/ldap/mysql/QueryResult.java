package ldap.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.RowSetDynaClass;

public class QueryResult {

	public static List<Map<String, Object>> listBySql(String sqlquery) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection con = SimpleConnetionManager.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlquery);
			ResultSetMetaData md = rs.getMetaData();
			int num = md.getColumnCount();
			while (rs.next()) {
				Map mapOfColValues = new HashMap(num);
				for (int i = 1; i <= num; i++) {
					mapOfColValues.put(md.getColumnName(i), rs.getObject(i));
				}
				list.add(mapOfColValues);
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (Exception e2) {
				System.out.println(e2.toString());
			}
		}
		return list;
	}
	
	public static String getAsStr(String querySql){
		Connection con = SimpleConnetionManager.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String str = "";
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(querySql);
			ResultSetMetaData md = rs.getMetaData();
			while (rs.next()) {
				str = rs.getObject(1).toString();
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (Exception e2) {
				System.out.println(e2.toString());
			}
		}
		return str;
	}
}
