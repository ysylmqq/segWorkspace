/*
********************************************************************************************
Discription:  通信中心单元测试工具，写Hbase线程基类
			  
			  
Written By:   ZXZ
Date:         2014-05-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package testtools;

import java.io.IOException;

import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTableInterface;

public abstract class WriteHbase implements Runnable {
	protected String[] callletterlist;
	protected long starttime;
	protected long stoptime;
	protected int interval;
	protected HConnection connection = null;
	protected HTableInterface table = null;
	
	public WriteHbase(String tablename, String callletters, long starttime, long stoptime, int interval) throws IOException {
		this.callletterlist = callletters.split(";");
		this.starttime = (starttime / 1000) * 1000;
		this.stoptime = (stoptime / 1000) * 1000;
		this.interval = (interval / 1000) * 1000;
		if (this.starttime > this.stoptime) {
			long ltmp = this.starttime;
			this.starttime = this.stoptime;
			this.stoptime = ltmp;
		}
		if (this.interval <= 0)
			this.interval = 10000;
		
		connection = HConnectionManager.createConnection(HbaseClientTestTool.config);
		table = connection.getTable(tablename);
		table.setAutoFlush(false, true);
	}
	
	/*public static void main(String[] args) throws IOException {
		byte[] family = "operate".getBytes();
		
		Configuration configuration = HBaseConfiguration.create();
		//鑾峰彇杩炴帴
		HConnection connection = HConnectionManager.createConnection(configuration);
		//鑾峰彇琛�
		HTableInterface table = connection.getTable("t_operate");
		
		Put put = new Put("abc".getBytes());
		put.add(family, "key1".getBytes("utf-8"), "value1".getBytes());
		put.add(family, "key2".getBytes(), "value2".getBytes());
		put.add(family, "key3".getBytes(), "value3".getBytes());
		//put.add(family, qualifier, value)
		
		table.put(put);
		table.flushCommits();
	}
	*/
}
