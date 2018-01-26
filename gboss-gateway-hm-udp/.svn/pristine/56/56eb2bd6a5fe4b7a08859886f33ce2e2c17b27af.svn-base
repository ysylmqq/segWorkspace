package cc.chinagps.gateway.hbase.export;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.Put;

import cc.chinagps.gateway.hbase.client.HBaseClient;
import cc.chinagps.gateway.util.HBaseKeyUtil;
import cc.chinagps.gateway.util.SystemConfig;

public class ExportHBase {
	private HBaseClient client;
	
	public HBaseClient getClient() {
		return client;
	}
	
	private boolean enabled;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void init() throws IOException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("init hbase-export start[" + sdf.format(new Date()) + "]");
		
		int maxCacheSize = Integer.valueOf(SystemConfig.getHBaseProperty("maxCacheSize"));
		int threadCount = Integer.valueOf(SystemConfig.getHBaseProperty("threadCount"));
		int maxFetchSize = Integer.valueOf(SystemConfig.getHBaseProperty("maxFetchSize"));
		int batchSize = Integer.valueOf(SystemConfig.getHBaseProperty("batchSize"));
		String tableName = SystemConfig.getHBaseProperty("history_table_name");
		family = SystemConfig.getHBaseProperty("history_family_name").getBytes();
		qualifier_callLetter = SystemConfig.getHBaseProperty("history_qualifier_callLetter").getBytes();
		qualifier_baseInfo = SystemConfig.getHBaseProperty("history_qualifier_baseInfo").getBytes();
		qualifier_content = SystemConfig.getHBaseProperty("history_qualifier_content").getBytes();
		
		Configuration config = new Configuration();
		config.addResource(new Path("config/hbase-site.xml"));
		Configuration configuration = HBaseConfiguration.create(config);
		
		//HTablePool pool = new HTablePool(configuration, 1000);
		//client = new HBaseClient(pool, tableName);
		HConnection connection = HConnectionManager.createConnection(configuration);
		client = new HBaseClient(connection, tableName);
		
		client.setMaxCacheSize(maxCacheSize);
		client.setThreadCount(threadCount);
		client.setMaxFetchSize(maxFetchSize);
		client.setBatchSize(batchSize);
		
		client.init();
		System.out.println("init hbase-export end[" + sdf.format(new Date()) + "]");
	}
	
	private byte[] family;
	private byte[] qualifier_callLetter;
	private byte[] qualifier_baseInfo;
	private byte[] qualifier_content;
	
//	public void addGPS(String callLetter, GPSInfos gps, SegPackage pkg){
//		if(!enabled){
//			return;
//		}
//		
//		byte[] key = HBaseKeyUtil.getKey(callLetter, gps.getGpsTime().getTime());
//		Put put = new Put(key);
//		put.add(family, qualifier_callLetter, callLetter.getBytes());
//		byte[] baseInfo = SegPkgUtil.transformGPSInfo(gps).toByteArray();
//		put.add(family, qualifier_baseInfo, baseInfo);
//		put.add(family, qualifier_content, pkg.getAllData());
//		
//		client.addData(put);
//	}
	
	public void addGPS(String callLetter, cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo gpsBase, byte[] source){
		if(!enabled){
			return;
		}

		byte[] key = HBaseKeyUtil.getKey(callLetter, gpsBase.getGpsTime());
		Put put = new Put(key);
		put.add(family, qualifier_callLetter, callLetter.getBytes());
		byte[] baseInfo = gpsBase.toByteArray();
		put.add(family, qualifier_baseInfo, baseInfo);
		put.add(family, qualifier_content, source);
		
		client.addData(put);
	}
}