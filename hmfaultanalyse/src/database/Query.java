/*
********************************************************************************************
Discription: 数据库查询例子
			  
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Query {
	/**
	 * 查询
	 * @throws Exception 
	 */
	public static void testQuery1() throws Exception{
		//查询sql
		String sql = "select * from t_user";
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try{
			//获取数据库连接
			con = DBUtil.getConnection();
			//创建PreparedStatement
			pst = con.prepareStatement(sql);
			//查询
			rs = pst.executeQuery();
			
			while(rs.next()){
				//遍历结果集
				
				//取数据
				int userId = rs.getInt(1);
				
				System.out.println("userId:" + userId);
			}
		}finally{
			//关闭数据库资源
			DBUtil.closeDB(rs, pst, con);
		}
	}
	
	/**
	 * 查询(带查询参数)
	 * @throws Exception 
	 */
	public static void testQuery2() throws Exception{
		//查询sql
		String sql = "select * from t_vehicle tv where tv.vehicleid>=? and tv.vehicleid<=?";
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try{
			//获取数据库连接
			con = DBUtil.getConnection();
			//创建PreparedStatement
			pst = con.prepareStatement(sql);
			//设置参数
			pst.setInt(1, 77120);
			pst.setInt(2, 77123);
			//查询
			rs = pst.executeQuery();
			
			while(rs.next()){
				//遍历结果集
				
				//按序号取数据
				String numberPlate = rs.getString(3);
				
				//按列名称取数据
				String vehicleColor = rs.getString("vehicleColor");
				
				System.out.println("numberPlate:" + numberPlate + ", vehicleColor:" + vehicleColor);
			}
		}finally{
			//关闭数据库资源
			DBUtil.closeDB(rs, pst, con);
		}
	}
}