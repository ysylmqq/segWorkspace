/*
 ********************************************************************************************
Discription: 终端信息管理类，
			 
			  
Written By:   ZXZ
Date:         2016-11-09
Version:      1.0

Modified by:
Modified Date:
Version:
 ********************************************************************************************
*/
package analyse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;
import database.DBUtil;

public class UnitInfo {

	//终端信息列表
	private static ConcurrentHashMap<String, Boolean> unitinfomap = new ConcurrentHashMap<String, Boolean>();
	private static int readday = 0;	//读海马呼号，只要一天更一次
	
	// [start] 从数据库读更新的终端信息
	/******************************************************************************************
	 * 从数据库读更新的终端信息
	 */
	public static ConcurrentHashMap<String, Boolean> getUnitInfo(boolean bforce) {
		Calendar calendar = Calendar.getInstance();
		int today = calendar.get(Calendar.DAY_OF_MONTH);
		if (!bforce && (today == readday)) { //一天读一次呼号信息
			return unitinfomap;
		}
		Connection readcon = null; // 使用连接池
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "SELECT call_letter FROM t_ba_sim WHERE subco_no=201";
		String callletter = "";
		try {
			// 创建PreparedStatement
			readcon = DBUtil.getConnection();
			pst = readcon.prepareStatement(sql);
			// 查询
			rs = pst.executeQuery();
			while (rs.next()) {
				callletter = DBUtil.GetStringFromColumn(rs, 1);
				unitinfomap.put(callletter, true);
			}
			DBUtil.closeDB(rs, pst, readcon);
			readday = today;
			return unitinfomap;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeDB(rs, pst, readcon);
		}
		return null;
	}

	/*
    public static void main(String[] args) {
    	getUnitInfo();	
    }*/
}
