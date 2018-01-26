/*
********************************************************************************************
Discription:  如果行程信息已经补充了开始位置，则写回Hbase 
			  
Written By:   ZXZ
Date:         2014-06-11
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.hbase;

import java.io.IOException;

import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.TravelInfo;
import cc.chinagps.lib.util.HBaseKeyUtil;

public class TravelTable {

	private HTableInterface table = null;
	
	public TravelTable(HConnection connection) throws IOException {
		table = connection.getTable("t_travel");
	}
	
	public void WriteTravel(TravelInfo travelinfo) throws IOException {
		byte[] key = HBaseKeyUtil.getKey(travelinfo.getCallLetter(), travelinfo.getStartTime());
		Put put = new Put(key);
		put.add(TravelLastInfo.family, TravelLastInfo.qualifier_callLetter, travelinfo.getCallLetter().getBytes());
		byte[] data = travelinfo.toByteArray();
		put.add(TravelLastInfo.family, TravelLastInfo.qualifier_data, data);
		table.put(put);
		table.flushCommits();
	}
}
