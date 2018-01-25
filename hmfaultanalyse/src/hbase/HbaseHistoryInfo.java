/*
********************************************************************************************
Discription:  读HBASE表历史信息-基类 
			  
Written By:   ZXZ
Date:         2016-06-04
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package hbase;

import java.util.Iterator;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import utils.HBaseKeyUtil;

public abstract class HbaseHistoryInfo {
	protected Connection connection;		//HBASE连接
	protected String tablename = "";
	protected String callletter;

	public HbaseHistoryInfo(Connection connection) {
		this.connection = connection;
	}
	
	//根据表不同，不同的子类实例内容不同
	protected abstract boolean GetRecordInfo(Result rs);
	
	//
	/* 读历史记录公共接口
	 */
	public boolean GetHistoryInfo(String callletter, long starttime, long endtime) {
		ResultScanner rsscan = null;
		this.callletter = callletter;
		try {
			Table table = connection.getTable(TableName.valueOf(tablename));
			byte[] startKey = HBaseKeyUtil.getKey(callletter, starttime);	//开始时间
			byte[] endKey = HBaseKeyUtil.getKey(callletter, endtime);		//结束时间
			Scan scan = new Scan(startKey, endKey);
			scan.setReversed(true);	//因为hbase存储是按时间逆序的, 所以要倒序输出才是正常时间顺序
			scan.setBatch(100);
			scan.setCaching(100);
			rsscan = table.getScanner(scan);
			if (rsscan == null) {
				return false;
			}
			Iterator<Result> iter = rsscan.iterator();
			while(iter.hasNext()){
				Result rs = iter.next();
				if (!GetRecordInfo(rs)) {
					break;
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rsscan != null) {
				rsscan.close();
			}
		}
		return false;
	}
}
