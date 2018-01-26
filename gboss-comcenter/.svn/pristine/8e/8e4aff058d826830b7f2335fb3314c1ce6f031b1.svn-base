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
import hbasedemo.DemoOBDBuff.OBDInfo;

public class DemoOBDLastInfo extends DemoHbaseLastInfo {
	private static byte[] family = "obd".getBytes();
	private static byte[] qualifier_callLetter = "callletter".getBytes();
	private static byte[] qualifier_data = "data".getBytes();
	
	//对外最后OBD信息, 
	public OBDInfo obdinfo;
	
	public DemoOBDLastInfo(HConnection connection) {
		super(connection);
		super.tablename = "t_obd";
	}

	@Override
	protected boolean GetRecordInfo(Result rs) {
		try {
			//判断呼号是否正常
			String rec_callletter = new String(rs.getValue(family, qualifier_callLetter));
			if (!rec_callletter.equals(super.callletter)) {
				return false;
			}
			//Fault data
			byte[] data = rs.getValue(family, qualifier_data);
			if (data != null) {
				obdinfo = OBDInfo.parseFrom(data);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
