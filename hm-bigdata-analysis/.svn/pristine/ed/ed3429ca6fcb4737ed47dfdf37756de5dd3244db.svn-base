package com.hm.bigdata.util.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.log4j.Logger;

public class HbaseUtils {
	private static Logger log = Logger.getLogger(HbaseUtils.class);
	 
	private static HBaseAdmin hbaseAdmin;
	private static Configuration conf;
	private static HConnection connection;
	
	static {
		  conf = HBaseConfiguration.create();
		try {
			hbaseAdmin = new HBaseAdmin(conf);
			connection = HConnectionManager.createConnection(conf);

		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

	}

	public static HBaseAdmin getHBaseAdmin() {
		return hbaseAdmin;
	}
	
	public static Configuration getConfiguration() {
		return conf;
	}
	
	public static HConnection getHConnection() {
		return connection;
	}
}
