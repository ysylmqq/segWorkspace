/*
********************************************************************************************
Discription: 管理HBASE
                          

Written By:   ZXZ
Date:         2016-11-07
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package hbase;

import org.apache.hadoop.conf.Configuration;

//import java.util.ArrayList;
//import org.apache.hadoop.hbase.TableName;
//import org.apache.hadoop.hbase.client.Connection;
//import org.apache.hadoop.hbase.client.ConnectionFactory;
//import org.apache.hadoop.hbase.client.Put;
//import logs.LogManager;
//import utils.HBaseKeyUtil;
//import org.apache.hadoop.hbase.client.Table;

public class HbaseManager {
	public static Configuration config = null;

	public static void Init() {
		try {
			config = new Configuration();
			config.addResource("hbase-site.xml");
			//config.addResource("hdfs-site.xml");
			System.out.println("Hbase initialized.");
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Hbase Uninitialized.");
		}
	}
	
	/*
	public static void main(String[] args) throws Exception {
		try {
			LogManager.init();
			HbaseManager.Init();
			Connection connection = ConnectionFactory.createConnection(HbaseManager.config);
			Table table = connection.getTable(TableName.valueOf("t_fault"));
			byte[] family = "fault".getBytes();
			byte[] qualifier_callLetter = "callletter".getBytes();
			byte[] qualifier_data = "data".getBytes();
			byte[] key = HBaseKeyUtil.getKey("12345678", 0);
			ArrayList<Put> putlist = new ArrayList<Put>();
			Put put = new Put(key);
			byte[] callletterbytes = "12345678".getBytes();
			byte[] databytes = "[]".getBytes();
			put.addColumn(family, qualifier_callLetter, callletterbytes);
			put.addColumn(family, qualifier_data, databytes);
			putlist.add(put);
			table.put(putlist);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}*/
}
