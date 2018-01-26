/*
********************************************************************************************
Discription: 数据库存储过程例子
			  
Written By:   ZXZ
Date:         2014-08-28
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import cc.chinagps.gboss.database.DBUtil;
import oracle.jdbc.OracleTypes;


public class Procedures {
	/**
	 * 调用存储过程
	 * @throws Exception 
	 */
	public static void testProcedures() throws Exception{
		//分页查询历史数据sql
		String sql = "{? = call fn_query_history_page(?, ?, ?, ?, ?, ?)}";
		Connection con = null;
		CallableStatement cst = null;
		ResultSet rs = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp startTime = new Timestamp(sdf.parse("2014-6-12 00:00:00").getTime());
		Timestamp endTime = new Timestamp(sdf.parse("2014-6-15 23:59:59").getTime());
		try{
			//获取数据库连接
			con = DBUtil.getConnection();
			//创建CallableStatement
			cst = con.prepareCall(sql);
			//设置存储过程参数
			cst.registerOutParameter(1, OracleTypes.CURSOR);
			cst.setString(2, "13201272050");
			cst.setTimestamp(3, startTime);
			cst.setTimestamp(4, endTime);
			cst.setInt(5, 0);
			cst.setInt(6, 20);
			cst.registerOutParameter(7, OracleTypes.NUMBER);
			//运行
			cst.execute();
			
			//获取存储过程返回值(结果集类型)
			rs = (ResultSet) cst.getObject(1);
			//获取存储过程 输出类型参数
			int count = cst.getInt(7);
			System.out.println("total-count:" + count);
			while(rs.next()){
				//遍历结果集
				
				//按序号取数据
				String numberPlate = DBUtil.GetStringFromColumn(rs, 2);
				
				//按列名称取数据
				double lon = rs.getDouble("lon");
				double lat = rs.getDouble("lat");
				
				System.out.println("numberPlate:" + numberPlate + ", lon:" + lon + ", lat:" + lat);
			}
		}finally{
			//关闭数据库资源
			DBUtil.closeDB(rs, cst, con);
		}
	}
}