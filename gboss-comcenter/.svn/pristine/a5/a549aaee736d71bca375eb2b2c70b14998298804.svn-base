package testtools;

import javax.jms.BytesMessage;
import com.google.protobuf.ByteString;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverGPS;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsBaseInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsPointInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsReferPosition;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsRoadInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.OBDInfo;
import cc.chinagps.lib.util.SystemConfig;
import cc.chinagps.lib.util.Util;

public class WriteActiveMQGPS extends WriteActiveMQ {

	private long startcallletter = 28922819616L;
	private int calllettercount = 8192;
	private int gpsinterval = 0;
	private int BATCH_SIZE_WRITE = 50;
	
	public WriteActiveMQGPS(long startcallletter, int calllettercount, int gpsinterval) {
		super("gps");
		this.startcallletter = startcallletter;
		this.calllettercount = calllettercount;
		this.gpsinterval = gpsinterval;
		BATCH_SIZE_WRITE = Integer.valueOf(SystemConfig.getActiveMQProperty("batch_size_write"));
	}
	
	private byte[] CreateDeliverGPS(long callletter) {
		
		DeliverGPS.Builder delivergps = DeliverGPS.newBuilder();
		delivergps.setGatewayid(0);
		delivergps.setGatewaytype(0);
		
		GpsInfo.Builder gpsinfo = delivergps.getGpsinfoBuilder();
		gpsinfo.setCallLetter("" + callletter);
		byte[] content = Util.str2bcd("5B903030303030303030303236284F4E4530333334313041323233322E373933344E31313430352E35383737453034372E3330303139303831343030303030303030295D");
		gpsinfo.setContent(ByteString.copyFrom(content));
		
		GpsBaseInfo.Builder baseinfo = gpsinfo.getBaseInfoBuilder();
		baseinfo.setGpsTime((System.currentTimeMillis() / 1000) * 1000);	//变成整数秒
		baseinfo.setLoc(true);
		baseinfo.setLat(22345000);
		baseinfo.setLng(113230000);
		baseinfo.setSpeed(605);
		baseinfo.setCourse(30);
		baseinfo.setTotalDistance(10500000);
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
		return delivergps.build().toByteArray();
	}
	
	/*
	 * 向ActiveMQ内写GPS
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		long starttime = System.currentTimeMillis();
		int count = calllettercount * 1000 / gpsinterval;	//每秒钏写多少条
		int index = 0;
		while(calllettercount > 0 && gpsinterval > 0) {
			for(int i=1; i<=count; i++) {
				try {
					BytesMessage sendmsg;
					sendmsg = session.createBytesMessage();
					sendmsg.writeBytes(CreateDeliverGPS(startcallletter + index));
					producer.send(sendmsg);
					if ((++index) >= calllettercount) {
						index = 0;
					}
					if ((i % BATCH_SIZE_WRITE) == 0) {
						try {
							session.commit();
							StressTestDlg.writegpscount.addAndGet(BATCH_SIZE_WRITE);
						} catch (Exception ex) {
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			try {
				session.commit();
				StressTestDlg.writegpscount.addAndGet(count%BATCH_SIZE_WRITE);
			} catch (Exception ex) {
			}
			long stoptime = System.currentTimeMillis();
			if (stoptime - starttime < 100) {
				try {
					sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			stoptime = System.currentTimeMillis();
			count = (int)(calllettercount * (stoptime - starttime) / gpsinterval);	//每秒钏写多少条
			starttime = stoptime;
		}
	}
	
	public void Stop() {
		calllettercount = 0;
	}
}
