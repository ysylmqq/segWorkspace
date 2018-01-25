/*
********************************************************************************************
Discription:  读HBASE表最后信息-基类 
			  
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

public abstract class HbaseLastInfo {
	protected Connection connection;		//HBASE连接
	protected String tablename = "";
	protected String callletter;

	public HbaseLastInfo(Connection connection) {
		this.connection = connection;
	}
	
	//根据表不同，不同的子类实例内容不同
	protected abstract boolean GetRecordInfo(Result rs);
	
	//
	/* 读历史记录公共接口
	 * 返回记录数， -1表示失败
	 */
	public boolean GetLastInfo(String callletter) {
		ResultScanner rsscan = null;
		this.callletter = callletter;
		boolean ret = false;
		try {
			Table table = connection.getTable(TableName.valueOf(tablename));
			//因为hbase存储是按时间逆序的, 所以把结束时间作为开始，开始作为结束
			byte[] startKey = HBaseKeyUtil.getKey(callletter, System.currentTimeMillis() + 24*3600000l); //二天后
			byte[] endKey = HBaseKeyUtil.getKey(callletter, 24l*3600000l*40*365); //2010年
			Scan scan = new Scan(startKey, endKey);
			rsscan = table.getScanner(scan);
			if (rsscan != null) {
				Iterator<Result> iter = rsscan.iterator();
				if (iter.hasNext()){
					Result rs = iter.next();
					//读第一条记录，即最后一条记录
					ret = GetRecordInfo(rs);
				}
				rsscan.close();
			}
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (rsscan != null) {
			rsscan.close();
		}
		return false;
	}
}
