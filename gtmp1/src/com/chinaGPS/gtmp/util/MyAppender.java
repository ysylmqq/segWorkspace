package com.chinaGPS.gtmp.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.log4j.jdbc.JDBCAppender;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;



public class MyAppender extends JDBCAppender{
	
@Override
protected Connection getConnection() throws SQLException {
	
	
	//ApplicationContext ac = new FileSystemXmlApplicationContext("/src/spring/applicationContext*.xml");
	 DataSource ds=(DataSource) SpringContext.getContext().getBean("dataSource");
	this.connection=ds.getConnection();
	return connection;
}
@Override
	protected void closeConnection(Connection con) {
	//con=this.connection;
	try {
		getConnection().close();
	} catch (SQLException e) {
		
	}
	//con=null;
   // this.connection=null;	
		
	}
	public static void main(String[] args) throws SQLException {
		
		MyAppender a=new MyAppender();
		Connection conn=null;try{
		conn=a.getConnection();
		Statement st = conn.createStatement();
		   String sql = "select * from T_P_DEPART";
		   ResultSet rs = st.executeQuery(sql);
		   while(rs.next()){
		    System.out.println( rs.getString("DEPART_NAME"));
		   }}
		catch(SQLException e)
		{
			System.out.println("ruzhicharuyichang"+e.getMessage());
		}
		 finally{
			 if(conn!=null)
				 conn.close();
		 }
	}
}
