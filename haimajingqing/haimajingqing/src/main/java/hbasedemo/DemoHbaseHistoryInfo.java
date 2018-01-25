/*
********************************************************************************************
Discription:  读HBASE表历史信息-基类 
			  
Written By:   ZXZ
Date:         2014-06-04
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package hbasedemo;

import java.util.*;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.log4j.Logger;
import cc.chinagps.lib.util.HBaseKeyUtil;

public abstract class DemoHbaseHistoryInfo {
	protected HConnection connection;		//HBASE连接
	protected String tablename = "";
	protected String callletter;
	
	public DemoHbaseHistoryInfo(HConnection connection) {
		this.connection = connection;
	}
	//根据表不同，不同的子类实例内容不同
	protected abstract boolean GetRecordInfo(Result rs);
	
	private static Logger logger = Logger.getLogger(DemoHbaseHistoryInfo.class);
	
	//
	/* 读历史记录公共接口
	 * 返回记录数， -1表示失败
	 */
	public boolean GetHistoryInfo(String callletter, long starttime, long endtime) {
		ResultScanner rsscan = null;
		this.callletter = callletter;
 
		try {
			HTableInterface table = connection.getTable(tablename);
			byte[] startKey = HBaseKeyUtil.getKey(callletter, starttime);	//开始时间
			byte[] endKey = HBaseKeyUtil.getKey(callletter, endtime);		//结束时间
			
	
			Scan scan = new Scan(startKey, endKey);
			scan.setReversed(true);	//因为hbase存储是按时间逆序的, 所以要倒序输出才是正常时间顺序
			
			rsscan = table.getScanner(scan);
		
			if (rsscan == null) {
				logger.warn("rsscan is null");
				return false;
			}
			Iterator<Result> iter = rsscan.iterator();
			while(iter.hasNext()){
				Result rs = iter.next();
				if (!GetRecordInfo(rs)) {
					break;
				}
			}
			rsscan.close();
			table.close();
			rsscan = null;
			table = null;
			return true;
		} catch (Exception e) {
			logger.error(null, e);
		}
		if (rsscan != null) {
			rsscan.close();
		}
		return false;
	}
}
