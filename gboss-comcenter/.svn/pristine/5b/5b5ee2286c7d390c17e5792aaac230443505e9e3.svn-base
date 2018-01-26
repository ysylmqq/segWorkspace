/*
********************************************************************************************
Discription:  读终端OBD历史信息 
			  
Written By:   ZXZ
Date:         2014-11-06
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package hbasedemo;

import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.Result;

public class UserScoreHistoryInfo extends DemoHbaseHistoryInfo {
	private static byte[] family = "userscore".getBytes();
	private static byte[] qualifier_callLetter = "callletter".getBytes();
	private static byte[] qualifier_userscore = "scoreinfo".getBytes();
	
	public UserScoreHistoryInfo(HConnection connection) {
		super(connection);
		super.tablename = "t_userscore";
	}

	@Override
	protected boolean GetRecordInfo(Result rs) {
		try {
			//判断呼号是否正常
			byte[] callletter = rs.getValue(family, qualifier_callLetter);
			String rec_callletter = new String(callletter);
			if (!rec_callletter.equals(super.callletter)) {
				return false;
			}
			byte[] data = rs.getValue(family, qualifier_userscore);
			if (data != null) {
				System.out.println("取得userscore");//obdlist.add(OBDInfo.parseFrom(data));
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
