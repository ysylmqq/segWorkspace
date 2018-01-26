/*
********************************************************************************************
Discription:  通信中心单元测试工具，用线程向Hbase写终端警情
			  
			  
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
import cc.chinagps.gboss.hbase.AlarmLastInfo;
import cc.chinagps.lib.util.HBaseKeyUtil;

public class WriteHbaseAlarm extends WriteHbase {

	public WriteHbaseAlarm(String callletters, long starttime, long stoptime, int interval) throws IOException {
		super("t_alarm", callletters, starttime, stoptime, interval);
	}

	@Override
	public void run() {
		ArrayList<Put> putlist = new ArrayList<Put>();
		try {
			for(long l=starttime; l<=stoptime; l+=interval) {
				for(int i=0; i<callletterlist.length; i++) {
					GpsInfo.Builder gpsinfo = GpsInfo.newBuilder();
					gpsinfo.setCallLetter(callletterlist[i]);
					GpsBaseInfo.Builder baseinfo = gpsinfo.getBaseInfoBuilder();
					baseinfo.setGpsTime(l);
					baseinfo.setLoc(true);
					baseinfo.setLat(22345000 + i*10000);
					baseinfo.setLng(113230000 + i*10000);
					baseinfo.setSpeed(605+i*10);
					baseinfo.setCourse(30 + i);
					baseinfo.setTotalDistance(1050000 + i*1000);
					baseinfo.setOil(12050 + i*1000);
					baseinfo.addStatus(1);
					baseinfo.addStatus(2);
					baseinfo.addStatus(3);
					baseinfo.addStatus(4);
	
					OBDInfo.Builder obdinfo = baseinfo.getObdInfoBuilder();
					obdinfo.setRemainOil(12050);
					obdinfo.setRemainPercentOil(501);
					obdinfo.setAverageOil(800);
					obdinfo.setHourOil(1050);
					obdinfo.setTotalDistance(1050000);
					obdinfo.setWaterTemperature(65);
	
					GpsReferPosition.Builder referpos = gpsinfo
							.getReferPositionBuilder();
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
	
					byte[] key = HBaseKeyUtil.getKey(callletterlist[i], l);
					Put put = new Put(key);
					put.add(AlarmLastInfo.family, AlarmLastInfo.qualifier_callLetter, callletterlist[i].getBytes());
					byte[] baseInfo = baseinfo.build().toByteArray();
					put.add(AlarmLastInfo.family, AlarmLastInfo.qualifier_base, baseInfo);
					byte[] referposition = referpos.build().toByteArray();
					put.add(AlarmLastInfo.family, AlarmLastInfo.qualifier_referposition, referposition);
					byte[] content = callletterlist[i].getBytes();
					put.add(AlarmLastInfo.family, AlarmLastInfo.qualifier_content, content);
					putlist.add(put);
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
			JOptionPane.showMessageDialog(null, "插入警情到Hbase结束");
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "插入警情到Hbase失败");
		}
	}
}
