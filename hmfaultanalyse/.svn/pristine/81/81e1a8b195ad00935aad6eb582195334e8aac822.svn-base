/*
********************************************************************************************
Discription: 数据库工具类
			  
Written By:   ZXZ
Date:         2014-08-28
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import utils.SystemConfig;
import utils.Util;

public class DBUtil {
	/********************************************************************************
	 * 调用c3p0, 数据库连接池
	 */
    private static ComboPooledDataSource pool = null;
    
    private static void Init() {
    	if (pool == null) {
			pool = new ComboPooledDataSource();
			//Util.initObjectByProperties(pool, SystemConfig.getDBproperties());
			Properties properties = SystemConfig.getDBproperties();
			Util.initObjectByProperties(pool, properties);
    	}
	}
	
	public static Connection openConnection() throws SQLException {
		Init();
		Connection con = pool.getConnection();
		con.setAutoCommit(true);
		return con;
	}
	
	/********************************************************************************
	 * 一般的JDBC获取数据库连接
	 * @throws Exception 
	 */
	public static Connection getConnection() throws Exception{
		Init();
		String url = SystemConfig.getDBProperties("JdbcUrl");
		String user = SystemConfig.getDBProperties("User");
		String password = SystemConfig.getDBProperties("Password");
		if (url.startsWith("jdbc:mysql:")) {
			//mysql
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(url, user, password);
		} else if (url.startsWith("jdbc:oracle:")) {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			return DriverManager.getConnection(url, user, password);
		}
		return null;
	}
	
	/**
	 * 关闭数据库资源
	 */
	public static void closeDB(ResultSet rs, Statement st, Connection con){
		if(rs != null){
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(st != null){
			try {
				st.close();
				st = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(con != null){
			try {
				con.close();
				con = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 关闭数据库资源
	 */
	public static void closeDB(Statement st, Connection con){
		if(st != null){
			try {
				st.close();
				st = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(con != null){
			try {
				con.close();
				con = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void closeDB(Connection con){
		if(con != null){
			try {
				con.close();
				con = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void closeDB(ResultSet rs, Statement st){
		if(rs != null){
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(st != null){
			try {
				st.close();
				st = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static long GetLongFromTimeColumn(ResultSet rs, int col){
		try {
			java.sql.Timestamp time = rs.getTimestamp(col);
			return time.getTime();
		} catch (Exception e) {
		}
		return 0;
	}

	public static String GetStringFromColumn(ResultSet rs, int col){
		try {
			String strret = rs.getString(col);
			if (strret != null) {
				return strret.trim();
			}
		} catch (Exception e) {
		}
		return Util.emptyString;
	}
}
