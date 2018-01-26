/*
********************************************************************************************
Discription:  某呼号一天GPS数量 
			  
Written By:   ZXZ
Date:         2015-08-13
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.hbasecalc;

import java.util.Date;
import java.util.Iterator;

import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;

import cc.chinagps.lib.util.HBaseKeyUtil;

public class GpsCountInfo {

	protected HConnection connection;		//HBASE连接
	protected String tablename;
	
	public GpsCountInfo(HConnection connection) {
		this.connection = connection;
		tablename = "t_history";
	}

	public int getGpsCount(String callletter, Date begindate) {
		ResultScanner rsscan = null;
		try {
			long starttime = begindate.getTime();
			long endtime = starttime + 3600000*24;
			HTableInterface table = connection.getTable(tablename);
			byte[] startKey = HBaseKeyUtil.getKey(callletter, starttime); //二天后
			byte[] endKey = HBaseKeyUtil.getKey(callletter, endtime); //2010年
			Scan scan = new Scan(startKey, endKey);
			scan.setReversed(true);
			scan.setBatch(200000);
			rsscan = table.getScanner(scan);
			Iterator<Result> iter = rsscan.iterator();
			if (!iter.hasNext()) {
				return 0;
			}
			int retnumber = 0;
			while(iter.hasNext()){
				retnumber ++;
				iter.next();
			}
			return retnumber;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			if (rsscan != null) {
				rsscan.close();
			}
		}
	}
}
