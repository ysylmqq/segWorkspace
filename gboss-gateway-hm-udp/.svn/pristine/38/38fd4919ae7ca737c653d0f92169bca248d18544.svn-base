package cc.chinagps.gateway.test;

import com.google.protobuf.InvalidProtocolBufferException;

import cc.chinagps.gateway.buff.GBossDataBuff;
import cc.chinagps.gateway.buff.GBossDataBuff.GpsReferPosition.Builder;

public class Test {
	public static void main(String[] args) {
		//1507933116
		cc.chinagps.gateway.buff.GBossDataBuff.YDWInfo.Builder b = GBossDataBuff.YDWInfo.newBuilder();
		b.setTemperature(52);
		b.setDrivingTime(909127728);
		b.setTotalDistance(1507933116 * 10000);
		b.setUnitPower(0);
		b.setBluetoothPower(0);
		
		System.out.println(b.build());
	}
	
	public static void testRef(){
		byte[] data = initRefData();
		
		int count = 1000000;
		long t1 = System.currentTimeMillis();
		for(int i = 0; i < count; i++){
			try {
				//GBossDataBuff.GpsReferPosition ref = 
						GBossDataBuff.GpsReferPosition.parseFrom(data);
			} catch (InvalidProtocolBufferException e) {
				e.printStackTrace();
			}
		}
		long t2 = System.currentTimeMillis();
		long t = t2 - t1;
		System.out.println("time:" + t + ", speed:" + (1000.0 * count / t) + "rps");
	}
	
	private static byte[] initRefData(){
		Builder refBuilder = GBossDataBuff.GpsReferPosition.newBuilder();
		refBuilder.setProvince("广东省");
		refBuilder.setCity("深圳市");
		refBuilder.setCounty("南山区");
		
		cc.chinagps.gateway.buff.GBossDataBuff.GpsRoadInfo.Builder road1 = GBossDataBuff.GpsRoadInfo.newBuilder();
		road1.setName("广东省深圳市南山区AA路YYYYYYYYYYYYYYYY");
		road1.setLevel(1);
		road1.setDistance(100);
		road1.setId(10);
		road1.setLatOnRoad(23123456);
		road1.setLngOnRoad(113123456);
		refBuilder.addRoads(road1);
		
		cc.chinagps.gateway.buff.GBossDataBuff.GpsRoadInfo.Builder road2 = GBossDataBuff.GpsRoadInfo.newBuilder();
		road2.setName("广东省深圳市南山区BB路YYYYYYYYYYYYYYYY");
		road2.setLevel(2);
		road2.setDistance(150);
		road2.setId(12);
		road2.setLatOnRoad(24123456);
		road2.setLngOnRoad(114123456);
		refBuilder.addRoads(road2);
		
		
		cc.chinagps.gateway.buff.GBossDataBuff.GpsPointInfo.Builder point1 = GBossDataBuff.GpsPointInfo.newBuilder();
		point1.setName("火车站1");
		point1.setType(1);
		point1.setDistance(100);
		point1.setId(102);
		refBuilder.addPoints(point1);
		
		cc.chinagps.gateway.buff.GBossDataBuff.GpsPointInfo.Builder point2 = GBossDataBuff.GpsPointInfo.newBuilder();
		point2.setName("火车站2");
		point2.setType(1);
		point2.setDistance(150);
		point2.setId(103);
		refBuilder.addPoints(point2);
		
		return refBuilder.build().toByteArray();
	}
	
	public static void testBase(){
		byte[] data = initData();
		
		int count = 100000;
		long t1 = System.currentTimeMillis();
		for(int i = 0; i < count; i++){
			try {
				//GpsBaseInfo base = 
						cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo.parseFrom(data);
			} catch (InvalidProtocolBufferException e) {
				e.printStackTrace();
			}
		}
		long t2 = System.currentTimeMillis();
		long t = t2 - t1;
		System.out.println("time:" + t + ", speed:" + (1000.0 * count / t) + "rps");
	}
	
	public static byte[] initData(){
		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo.Builder baseBuilder = GBossDataBuff.GpsBaseInfo.newBuilder();
		baseBuilder.setGpsTime(System.currentTimeMillis());
		baseBuilder.setLoc(true);
		baseBuilder.setLat(123123456);
		baseBuilder.setLng(23123456);
		baseBuilder.setSpeed(567);
		baseBuilder.setCourse(125);
		baseBuilder.addStatus(23).addStatus(59).addStatus(60).addStatus(80);
		baseBuilder.setTotalDistance(123456);
		baseBuilder.setOilPercent(356);
		baseBuilder.setTemperature1(123);
		baseBuilder.setTemperature2(-31);
		baseBuilder.addAppendParams(cc.chinagps.gateway.buff.GBossDataBuff.MapEntry
				.newBuilder().setKey("tempreture3").setValue("23.4"));
		
		byte[] data = baseBuilder.build().toByteArray();
		return data;
	}
}