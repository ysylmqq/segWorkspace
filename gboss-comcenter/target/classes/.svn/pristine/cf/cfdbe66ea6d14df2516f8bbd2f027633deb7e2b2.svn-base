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

import java.util.ArrayList;

import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.Result;

import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverUnitLoginOut;
import cc.chinagps.gboss.hbase.UnitLoginoutTable;

public class LoginoutHistoryInfo extends DemoHbaseHistoryInfo {

	public class UnitLoginoutTimestamp {
		public DeliverUnitLoginOut loginout;
		public long inserttime;
	};
	
	public ArrayList<UnitLoginoutTimestamp> unitloginoutlist;
	
	public LoginoutHistoryInfo(HConnection connection) {
		super(connection);
		super.tablename = "t_unitloginout";
		unitloginoutlist = new  ArrayList<UnitLoginoutTimestamp>();
	}

	@Override
	protected boolean GetRecordInfo(Result rs) {
		try {
			//判断呼号是否正常
			String callletter1 = new String(rs.getValue(UnitLoginoutTable.family, UnitLoginoutTable.qualifier_callLetter));
			if (!callletter1.equals(this.callletter)) {
				return false;
			}
			UnitLoginoutTimestamp unitloginout = new UnitLoginoutTimestamp();
			byte[] data = rs.getValue(UnitLoginoutTable.family, UnitLoginoutTable.qualifier_data);
			unitloginout.loginout = DeliverUnitLoginOut.parseFrom(data);
			unitloginout.inserttime = rs.getColumnLatestCell(UnitLoginoutTable.family, UnitLoginoutTable.qualifier_data).getTimestamp();
			unitloginoutlist.add(unitloginout);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
