/*
********************************************************************************************
Discription:  通信中心单元测试工具，用线程向Hbase写位置信息
			  
			  
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
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.apache.hadoop.hbase.client.Put;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsBaseInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsPointInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsReferPosition;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsRoadInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.OBDInfo;
import cc.chinagps.gboss.hbase.GpsLastInfo;
import cc.chinagps.lib.util.HBaseKeyUtil;

public class WriteHbaseGPS extends WriteHbase {

	public WriteHbaseGPS(String callletters, long starttime, long stoptime, int interval) throws IOException {
		super("t_history", callletters, starttime, stoptime, interval);
	}

	@Override
	public void run() {
		ArrayList<Put> putlist = new ArrayList<Put>();
		try {
			for(long l=starttime; l<=stoptime; l+=interval) {
				for(int i=0; i<callletterlist.length; i++) {
					byte[] key = HBaseKeyUtil.getKey(callletterlist[i], l);
					for(int n=0; n<10; n++) {
						Put put = new Put(key);
						GpsInfo.Builder gpsinfo = GpsInfo.newBuilder();
						gpsinfo.setCallLetter(callletterlist[i]);
						GpsBaseInfo.Builder baseinfo = gpsinfo.getBaseInfoBuilder();
						baseinfo.setGpsTime(l);
						baseinfo.setLoc(true);
						baseinfo.setLat(22345000 + i*10000 + n);
						baseinfo.setLng(113230000 + i*10000 + n);
						baseinfo.setSpeed(605+i*10);
						baseinfo.setCourse(30 + i);
						baseinfo.setTotalDistance(1050000 + i*1000 + n);
						baseinfo.setOil(12050 + i*1000);
						baseinfo.addStatus(21);
						baseinfo.addStatus(22);
						baseinfo.addStatus(23);
						baseinfo.addStatus(24);
		
						if ((i % 10) == 1) {
							OBDInfo.Builder obdinfo = baseinfo.getObdInfoBuilder();
							obdinfo.setRemainOil(12050);
							obdinfo.setRemainPercentOil(501);
							obdinfo.setAverageOil(800);
							obdinfo.setHourOil(1050);
							obdinfo.setTotalDistance(1050000);
							obdinfo.setWaterTemperature(65);
						}
						put.add(GpsLastInfo.family, GpsLastInfo.qualifier_callLetter, callletterlist[i].getBytes());
						byte[] baseInfo = baseinfo.build().toByteArray();
						put.add(GpsLastInfo.family, GpsLastInfo.qualifier_base, baseInfo);
		
						if ((i % 3) == 1) {
							GpsReferPosition.Builder referpos = gpsinfo.getReferPositionBuilder();
							referpos.setProvince("广东省");
							referpos.setCity("深圳市");
							referpos.setCounty("南山区");
							for (int j = 0; j < 2; j++) {
								GpsRoadInfo.Builder road = referpos.addRoadsBuilder();
								road.setName("井冈山路");
								road.setLevel(1);
								road.setDistance(10);
								road.setId(10001);
								road.setLatOnRoad(22345678);
								road.setLngOnRoad(113231101);
							}
							for (int j = 0; j < 3; j++) {
								GpsPointInfo.Builder point = referpos.addPointsBuilder();
								point.setName("井冈山酒店");
								point.setType(2);
								point.setDistance(50);
							}
							byte[] referposition = referpos.build().toByteArray();
							put.add(GpsLastInfo.family, GpsLastInfo.qualifier_referposition, referposition);
						}
		
						byte[] content = callletterlist[i].getBytes();
						put.add(GpsLastInfo.family, GpsLastInfo.qualifier_content, content);
						putlist.add(put);
					}
					if (putlist.size() >= 1000) {
						table.put(putlist);
						table.flushCommits();
						putlist.clear();
					}
				} //for(callletterlist)
			} //for(long)
			if (putlist.size() > 0) {
				table.put(putlist);
				table.flushCommits();
				putlist.clear();
			}
			JOptionPane.showMessageDialog(null, "插入GPS到Hbase结束", "提示", JOptionPane.WARNING_MESSAGE );
		/*} catch (RetriesExhaustedWithDetailsException e) {
			e.printStackTrace();
			//This subclass of RetriesExhaustedException is thrown when we have more information 
			//about which rows were causing which exceptions on what servers.
			//You can call mayHaveClusterIssues() and if the result is false, you have input error problems,
			//otherwise you may have cluster issues. 
			//You can iterate over the causes, rows and last known server addresses
			//via getNumExceptions() and getCause(int), getRow(int) and getHostnamePort(int).
			/*boolean b = e.mayHaveClusterIssues();
			if (b) {
				System.out.println("you have input right");
				int ie = e.getNumExceptions();
				System.out.println(e.getCause(ie).toString());
				System.out.println(e.getHostnamePort(ie));
			} else {
				System.out.println("you have input error problems");
			}
			JOptionPane.showMessageDialog(null, "插入GPS到Hbase失败", "提示", JOptionPane.WARNING_MESSAGE );*/
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "插入GPS到Hbase失败", "提示", JOptionPane.WARNING_MESSAGE );
		}
	}
}
