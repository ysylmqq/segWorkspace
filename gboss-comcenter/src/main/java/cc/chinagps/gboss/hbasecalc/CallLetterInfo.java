/*
 ********************************************************************************************
Discription: 读某枫树的所有呼号
			 
			  
Written By:   ZXZ
Date:         2015-08-13
Version:      1.0

Modified by:
Modified Date:
Version:
 ********************************************************************************************
*/
package cc.chinagps.gboss.hbasecalc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import cc.chinagps.gboss.database.DBUtil;

public class CallLetterInfo extends Thread {
	//终端信息列表

	public CallLetterInfo() {
	}
	
	
	// [start] 从数据库读更新的终端信息
	/******************************************************************************************
	 * 从数据库读更新的终端信息
	 */
	public static boolean readCallletter(int orgno, int custtype, ArrayList<String> listcallletter) {
		Connection readcon = null; // 使用连接池
		PreparedStatement pst = null;
		ResultSet rs = null;
		String strSQL = "SELECT u.call_letter FROM t_ba_unit u WHERE u.customer_id IN (SELECT c.customer_id FROM t_ba_customer c WHERE c.subco_no=? AND c.cust_type=?) order by call_letter";
		try {
			// 创建PreparedStatement
			readcon = DBUtil.getConnection();
			pst = readcon.prepareStatement(strSQL);
			// 设置参数
			pst.setInt(1, orgno);
			pst.setInt(2, custtype);
			// 查询
			rs = pst.executeQuery();
			while (rs.next()) {
				String callletter = DBUtil.GetStringFromColumn(rs, 1);
				listcallletter.add(callletter);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			DBUtil.closeDB(rs, pst, readcon);
		}
	}
}
