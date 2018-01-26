/*
********************************************************************************************
Discription:  通信中心单元测试工具，用线程向Hbase写运营数据
			  
			  
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
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.OperateData;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.OperateDataBaseInfo;
import cc.chinagps.gboss.hbase.OperateDataLastInfo;
import cc.chinagps.lib.util.HBaseKeyUtil;

public class WriteHbaseOperateData extends WriteHbase {

	public WriteHbaseOperateData(String callletters,	long starttime, long stoptime, int interval) throws IOException {
		super("t_operate", callletters, starttime, stoptime, interval);
	}

	@Override
	public void run() {
		ArrayList<Put> putlist = new ArrayList<Put>();
		try {
			for(long l=starttime; l<=stoptime; l+=interval) {
				for(int i=0; i<callletterlist.length; i++) {
					OperateData.Builder operatedatainfo = OperateData.newBuilder();
					operatedatainfo.setCallLetter(callletterlist[i]);
				
				    OperateDataBaseInfo.Builder baseInfo = operatedatainfo.getBaseInfoBuilder();
				    baseInfo.setPrice(240);               //价格（单位：分）
				    baseInfo.setCountTime(300);           	//计时(单位：分钟）
				    baseInfo.setOperateMoney(300);        //收费(单位：分）
				    baseInfo.setOperateMile(300000);     //运营里程（单位：米）
				    baseInfo.setNullMile(500);            //空载里程（单位：米）
				    baseInfo.setSequenceNo(100);         //流水号
				    
				    GpsBaseInfo.Builder startGps = operatedatainfo.getStartGpsBuilder();
				    startGps.setGpsTime(l);
				    startGps.setLoc(true);
				    startGps.setLat(22345000);
				    startGps.setLng(113230000);
				    startGps.setSpeed(605);
				    startGps.setCourse(30);
				    startGps.setTotalDistance(1050000);
				    startGps.setOil(12050);
				    startGps.addStatus(21);
				    startGps.addStatus(22);
				    startGps.addStatus(23);
				    startGps.addStatus(24);

					GpsReferPosition.Builder startPoi = operatedatainfo.getStartPoiBuilder();
					startPoi.setProvince("广东省");
					startPoi.setCity("深圳市");
					startPoi.setCounty("南山区");
					for(int j=0; j<2; j++) {
						GpsRoadInfo.Builder road = startPoi.addRoadsBuilder();
						road.setName("井冈山路");
						road.setLevel(1);
						road.setDistance(10);
						road.setId(10001);
						road.setLatOnRoad(22345678);
						road.setLngOnRoad(113231101);
					}
					for(int j=0; j<3; j++) {
						GpsPointInfo.Builder point = startPoi.addPointsBuilder();
						point.setName("井冈山酒店");
						point.setType(2);
						point.setDistance(50);
					}
					
					
				    GpsBaseInfo.Builder stopGps = operatedatainfo.getStopGpsBuilder();
				    stopGps.setGpsTime(l + 60*1000);
				    stopGps.setLoc(true);
				    stopGps.setLat(23345000);
				    stopGps.setLng(114230000);
				    stopGps.setSpeed(1605);
				    stopGps.setCourse(130);
				    stopGps.setTotalDistance(1150000);
				    stopGps.setOil(22050);
				    stopGps.addStatus(31);
				    stopGps.addStatus(32);
				    stopGps.addStatus(33);
				    stopGps.addStatus(34);
				    
				    GpsReferPosition.Builder stopPoi = operatedatainfo.getStopPoiBuilder();
					stopPoi.setProvince("湖南省");
					stopPoi.setCity("长沙市");
					stopPoi.setCounty("岳麓区");
					for(int j=0; j<2; j++) {
						GpsRoadInfo.Builder road = stopPoi.addRoadsBuilder();
						road.setName("韶山路");
						road.setLevel(2);
						road.setDistance(20);
						road.setId(20001);
						road.setLatOnRoad(23345678);
						road.setLngOnRoad(114231101);
					}
					for(int j=0; j<3; j++) {
						GpsPointInfo.Builder point = stopPoi.addPointsBuilder();
						point.setName("韶山酒店");
						point.setType(2);
						point.setDistance(25);
					}
					
					byte[] key = HBaseKeyUtil.getKey(callletterlist[i], l);
					Put put = new Put(key);
					put.add(OperateDataLastInfo.family, OperateDataLastInfo.qualifier_callLetter, callletterlist[i].getBytes());
					byte[] data = operatedatainfo.build().toByteArray();
					put.add(OperateDataLastInfo.family, OperateDataLastInfo.qualifier_data, data);
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
			JOptionPane.showMessageDialog(null, "插入运营数据到Hbase结束", "提示", JOptionPane.WARNING_MESSAGE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "插入运营数据到Hbase失败", "提示", JOptionPane.WARNING_MESSAGE);
		}
	}

}
