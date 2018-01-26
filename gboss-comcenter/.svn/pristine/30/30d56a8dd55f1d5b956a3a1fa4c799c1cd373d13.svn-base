/*
********************************************************************************************
Discription: 数据库Update例子
			  
Written By:   ZXZ
Date:         2014-08-28
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Edit {
	/**
	 * 修改(或删除)
	 * @throws Exception 
	 */
	public static void testEdit() throws Exception{
		//修改sql
		String sql = "update t_vehicle set number_plate=? where vehicleid=?";
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try{
			//获取数据库连接
			con = DBUtil.getConnection();
			//创建PreparedStatement
			pst = con.prepareStatement(sql);
			//设置参数
			pst.setString(1, "粤YQY258");
			pst.setInt(2, 77129);
			//修改
			pst.executeUpdate();
			System.out.println("edit ok");
		}finally{
			//关闭数据库资源
			DBUtil.closeDB(rs, pst, con);
		}
	}
}
