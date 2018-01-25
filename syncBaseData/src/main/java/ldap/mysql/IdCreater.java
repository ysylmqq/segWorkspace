package ldap.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class IdCreater {
	
	public static String getCompanyId(){
		int companyid = 1;
		Connection con = SimpleConnetionManager.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String sqlquery = "select max(id) from seq_company";
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlquery);
			while(rs.next()){
				companyid = rs.getInt("max(id)") + 1;
			}
			String sqlinsert = "insert into seq_company values()";
			stmt.execute(sqlinsert);
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
		return String.valueOf(companyid);
	}
	
	public static String getOperatorId(){
		int operatorid = 1;
		Connection con = SimpleConnetionManager.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String sqlquery = "select max(id) from seq_operator";
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlquery);
			while(rs.next()){
				operatorid = rs.getInt("max(id)") + 1;
			}
			String sqlinsert = "insert into seq_operator values()";
			stmt.execute(sqlinsert);
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
		return String.valueOf(operatorid);
	}

}
