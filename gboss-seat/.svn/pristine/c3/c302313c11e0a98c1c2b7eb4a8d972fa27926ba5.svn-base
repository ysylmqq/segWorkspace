/*
********************************************************************************************
Discription:  读GPS信息 
			  
Written By:   ZXZ
Date:         2014-06-04
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.seat.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;

import com.google.protobuf.ByteString;

import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsBaseInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsReferPosition;
import cc.chinagps.lib.util.HBaseKeyUtil;

public class GpsTable {
	private HTableInterface table = null;
	
	public GpsTable(HConnection connection) throws IOException {
		table = connection.getTable("t_history");
	}

	public GpsInfo GetGpsInfo(String callletter, long gpstime) throws IOException {
		//按时间倒序
		byte[] key = HBaseKeyUtil.getKey(callletter, gpstime);
		Result rs = table.get(new Get(key));
		if (rs.isEmpty())
			return null;

		//判断呼号是否正常
		String callletter1 = new String(rs.getValue(GpsLastInfo.family, GpsLastInfo.qualifier_callLetter));
		if (!callletter1.equals(callletter)) {
			return null;
		}
			
		//gps baseinfo
		byte[] baseData = rs.getValue(GpsLastInfo.family, GpsLastInfo.qualifier_base);
		//参考位置
		byte[] referposition = rs.getValue(GpsLastInfo.family, GpsLastInfo.qualifier_referposition);
		//源码
		byte[] content = rs.getValue(GpsLastInfo.family, GpsLastInfo.qualifier_content);
		
		GpsInfo.Builder gpsinfo = GpsInfo.newBuilder();
		gpsinfo.setCallLetter(callletter);
		gpsinfo.setBaseInfo(GpsBaseInfo.parseFrom(baseData));
		if (referposition != null && referposition.length > 0) {
			gpsinfo.setReferPosition(GpsReferPosition.parseFrom(referposition));
		}
		if (content != null && content.length > 0) {
			gpsinfo.setContent(ByteString.copyFrom(content));
		}
		return gpsinfo.build(); 
	}

	protected ResultScanner BeginScan(String callletter, long starttime, long stoptime, boolean reversed) {
		try {
			if (reversed) {
				//按时间倒序
				byte[] startKey = HBaseKeyUtil.getKey(callletter, starttime); //二天后
				byte[] endKey = HBaseKeyUtil.getKey(callletter, stoptime); //2010年
				Scan scan = new Scan(startKey, endKey);
				scan.setReversed(false);
				ResultScanner rsscan = table.getScanner(scan);
				return rsscan;
			} else {
				//按时间倒序
				byte[] startKey = HBaseKeyUtil.getKey(callletter, stoptime); //二天后
				byte[] endKey = HBaseKeyUtil.getKey(callletter, starttime); //2010年
				Scan scan = new Scan(startKey, endKey);
				scan.setReversed(true);
				ResultScanner rsscan = table.getScanner(scan);
				return rsscan;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public GpsInfo GetFirstGpsInfo(String callletter, long starttime, long stoptime,boolean reversed) throws IOException {
		ResultScanner rsscan = null;
		try {
			rsscan = BeginScan(callletter, starttime, stoptime,reversed);
			if (rsscan == null) {
				return null;
			}
			Iterator<Result> iter = rsscan.iterator();
			if (!iter.hasNext()) {
				return null;
			}
			Result rs = iter.next();
			//判断呼号是否正常
			String callletter1 = new String(rs.getValue(GpsLastInfo.family, GpsLastInfo.qualifier_callLetter));
			if (!callletter1.equals(callletter)) {
				return null;
			}
			
			//gps baseinfo
			byte[] baseData = rs.getValue(GpsLastInfo.family, GpsLastInfo.qualifier_base);
			//参考位置
			byte[] referposition = rs.getValue(GpsLastInfo.family, GpsLastInfo.qualifier_referposition);
			//源码
			byte[] content = rs.getValue(GpsLastInfo.family, GpsLastInfo.qualifier_content);
			rsscan.close();
			rsscan = null;
			
			GpsInfo.Builder gpsinfo = GpsInfo.newBuilder();
			gpsinfo.setCallLetter(callletter);
			gpsinfo.setBaseInfo(GpsBaseInfo.parseFrom(baseData));
			if (referposition != null && referposition.length > 0) {
				gpsinfo.setReferPosition(GpsReferPosition.parseFrom(referposition));
			}
			if (content != null && content.length > 0) {
				gpsinfo.setContent(ByteString.copyFrom(content));
			}
			return gpsinfo.build(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (rsscan != null) rsscan.close();
		return null;
	}
	
	public List<GpsInfo> GetGpsInfoList(String callletter, long starttime, long stoptime,boolean reversed) throws IOException {
		ResultScanner rsscan = null;
		List<GpsInfo> list = new ArrayList<GpsInfo>();
		
		try {
			rsscan = BeginScan(callletter, starttime, stoptime,reversed);
			if (rsscan == null) {
				return null;
			}
			Iterator<Result> iter = rsscan.iterator();
			if (!iter.hasNext()) {
				return null;
			}
			while (iter.hasNext()) {				
				Result rs = iter.next();
				//判断呼号是否正常
				String callletter1 = new String(rs.getValue(GpsLastInfo.family, GpsLastInfo.qualifier_callLetter));
				if (!callletter1.equals(callletter)) {
					return null;
				}
				
				//gps baseinfo
				byte[] baseData = rs.getValue(GpsLastInfo.family, GpsLastInfo.qualifier_base);
				//参考位置
				byte[] referposition = rs.getValue(GpsLastInfo.family, GpsLastInfo.qualifier_referposition);
				//源码
				byte[] content = rs.getValue(GpsLastInfo.family, GpsLastInfo.qualifier_content);
				
				
				GpsInfo.Builder gpsinfo = GpsInfo.newBuilder();
				gpsinfo.setCallLetter(callletter);
				gpsinfo.setBaseInfo(GpsBaseInfo.parseFrom(baseData));
				if (referposition != null && referposition.length > 0) {
					gpsinfo.setReferPosition(GpsReferPosition.parseFrom(referposition));
				}
				if (content != null && content.length > 0) {
					gpsinfo.setContent(ByteString.copyFrom(content));
				}
				list.add(gpsinfo.build());
			}
			return list; 
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (rsscan != null) rsscan.close();
		}		
		return null;
	}
}
