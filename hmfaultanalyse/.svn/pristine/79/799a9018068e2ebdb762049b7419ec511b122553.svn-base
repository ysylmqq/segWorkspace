/*
********************************************************************************************
Discription:  读终端故障最后信息 
			  
Written By:   ZXZ
Date:         2016-11-06
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package hbase;

import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Result;

import hbase.FaultDataBuff.FaultInfo;

public class FaultLastInfo extends HbaseLastInfo {
	public static byte[] family = "fault".getBytes();
	public static byte[] qualifier_callLetter = "callletter".getBytes();
	public static byte[] qualifier_data = "data".getBytes();
	
	//对外最后OBD信息, 
	public FaultInfo faultinfo;
	
	public FaultLastInfo(Connection connection) {
		super(connection);
		super.tablename = "t_fault";
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
				faultinfo = FaultInfo.parseFrom(data);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
