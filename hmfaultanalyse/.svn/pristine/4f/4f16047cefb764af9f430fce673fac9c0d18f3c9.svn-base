/*
********************************************************************************************
Discription:  读终端故障历史信息 
			  
Written By:   ZXZ
Date:         2016-11-06
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package hbase;

import java.util.ArrayList;

import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Result;
import hbase.FaultDataBuff.FaultInfo;

public class FaultHistoryInfo extends HbaseHistoryInfo {
	
	//对外OBD队列, 读取历史的OBD信息都保存在这队列中
	private ArrayList<FaultInfo> faultlist;
	
	public FaultHistoryInfo(Connection connection) {
		super(connection);
		super.tablename = "t_fault";
		faultlist = new ArrayList<FaultInfo>();
	}

	@Override
	protected boolean GetRecordInfo(Result rs) {
		try {
			//判断呼号是否正常
			String rec_callletter = new String(rs.getValue(FaultLastInfo.family, FaultLastInfo.qualifier_callLetter));
			if (!rec_callletter.equals(super.callletter)) {
				return false;
			}
			//Fault data
			byte[] data = rs.getValue(FaultLastInfo.family, FaultLastInfo.qualifier_data);
			if (data != null) {
				faultlist.add(FaultInfo.parseFrom(data));
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<FaultInfo> getFaultList() {
		return faultlist;
	}
}
