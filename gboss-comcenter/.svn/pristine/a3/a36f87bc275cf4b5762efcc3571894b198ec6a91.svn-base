/*
********************************************************************************************
Discription:  通信中心单元测试工具，用线程向Hbase写行程
			  
			  
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
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsPointInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsReferPosition;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsRoadInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.OBDInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.TravelInfo;
import cc.chinagps.gboss.hbase.TravelLastInfo;
import cc.chinagps.lib.util.HBaseKeyUtil;

public class WriteHbaseTravel extends WriteHbase {

	public WriteHbaseTravel(String callletters, long starttime, long stoptime, int interval) throws IOException {
		super("t_travel", callletters, starttime, stoptime, interval);
	}

	@Override
	public void run() {
		ArrayList<Put> putlist = new ArrayList<Put>();
		try {
			for(long l=starttime; l<=stoptime; l+=interval) {
				for(int i=0; i<callletterlist.length; i++) {
					TravelInfo.Builder travelinfo = TravelInfo.newBuilder();
					travelinfo.setCallLetter(callletterlist[i]);
					travelinfo.setStartTime(l);
					travelinfo.setEndTime(l);
					travelinfo.setDistance(100000);
					travelinfo.setMaxSpeed(900);
					travelinfo.setOverSpeedTime(10);
					travelinfo.setQuickBrakeCount(7);
					travelinfo.setEmergencyBrakeCount(8);
					travelinfo.setQuickSpeedUpCount(9);
					travelinfo.setEmergencySpeedUpCount(10);
					travelinfo.setAverageSpeed(11);
					travelinfo.setMaxWaterTemperature(12);
					travelinfo.setMaxRotationSpeed(13);
					travelinfo.setVoltage(14);
					travelinfo.setTotalOil(15);
					travelinfo.setAverageOil(16);
					travelinfo.setTiredDrivingTime(17);
					travelinfo.setSerialNumber(18);
					travelinfo.setAverageRotationSpeed(19);
					travelinfo.setMaxOil(20);
					travelinfo.setIdleTime(21);
					GpsBaseInfo.Builder baseinfo = travelinfo.getEndGpsBuilder();
					baseinfo.setGpsTime(l + 3600000);
					baseinfo.setLoc(true);
					baseinfo.setLat(22345000);
					baseinfo.setLng(113230000);
					baseinfo.setSpeed(605);
					baseinfo.setCourse(30);
					baseinfo.setTotalDistance(1050000);
					baseinfo.setOil(12050);
					baseinfo.addStatus(21);
					baseinfo.addStatus(22);
					baseinfo.addStatus(23);
					baseinfo.addStatus(24);

					OBDInfo.Builder obdinfo = baseinfo.getObdInfoBuilder();
					obdinfo.setRemainOil(12050);
					obdinfo.setRemainPercentOil(501);
					obdinfo.setAverageOil(800);
					obdinfo.setHourOil(1050);
					obdinfo.setTotalDistance(1050000);
					obdinfo.setWaterTemperature(65);

					GpsReferPosition.Builder referpos = travelinfo.getEndReferPosBuilder();
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
					put.add(TravelLastInfo.family, TravelLastInfo.qualifier_callLetter, callletterlist[i].getBytes());
					byte[] data = travelinfo.build().toByteArray();
					put.add(TravelLastInfo.family, TravelLastInfo.qualifier_data, data);
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
			JOptionPane.showMessageDialog(null, "插入行程到Hbase结束");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "插入行程到Hbase失败");
		}
	}

}
