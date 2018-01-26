package cc.chinagps.gboss.hbase;

import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.OBDInfo;
import cc.chinagps.lib.util.HBaseKeyUtil;


public class OBDTable {
	public static byte[] family = "obd".getBytes();
	public static byte[] qualifier_callLetter = "callletter".getBytes();
	public static byte[] qualifier_data = "data".getBytes();
	private HConnection connection;
	
	public OBDTable(HConnection connection) throws IOException {
		this.connection = connection;
	}

	protected ResultScanner BeginScan(String callletter, boolean reversed) {
		try {
			HTableInterface table = connection.getTable("t_obd");
			if (reversed) {
				//按时间倒序
				byte[] startKey = HBaseKeyUtil.getKey(callletter, 24l*3600000l*40*365); //2010年
				byte[] endKey = HBaseKeyUtil.getKey(callletter, System.currentTimeMillis() + 24*3600000l); //二天后
				Scan scan = new Scan(startKey, endKey);
				scan.setReversed(true);
				ResultScanner rsscan = table.getScanner(scan);
				return rsscan;
			} else {
				byte[] startKey = HBaseKeyUtil.getKey(callletter, System.currentTimeMillis() + 24*3600000l); //二天后
				byte[] endKey = HBaseKeyUtil.getKey(callletter, 24l*3600000l*40*365); //2010年
				Scan scan = new Scan(startKey, endKey);
				scan.setReversed(false);
				ResultScanner rsscan = table.getScanner(scan);
				return rsscan;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public OBDInfo GetLastOBDInfo(String callletter) throws IOException {
		ResultScanner rsscan = null;
		try {
			rsscan = BeginScan(callletter, false);
			if (rsscan == null) {
				return null;
			}
			Iterator<Result> iter = rsscan.iterator();
			if (!iter.hasNext()) {
				return null;
			}
			Result rs = iter.next();
			String callletter1 = new String(rs.getValue(family, qualifier_callLetter));
			if (!callletter1.equals(callletter)) {
				return null;
			}
			//OBD data
			byte[] data = rs.getValue(family, qualifier_data);
			rsscan.close();
			OBDInfo obdinfo = OBDInfo.parseFrom(data);
			return obdinfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (rsscan != null) rsscan.close();
		return null;
	}
}
