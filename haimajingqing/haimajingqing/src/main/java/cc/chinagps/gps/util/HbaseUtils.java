package cc.chinagps.gps.util;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.log4j.Logger;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsBaseInfo;
import cc.chinagps.gps.pojo.GpsTempInfo;
import cc.chinagps.gps.service.VehicleService;
import hbasedemo.GpsHistoryInfo;

public class HbaseUtils {

	private static Logger log = Logger.getLogger(HbaseUtils.class);

	private static HConnection connection;
	static {
		//获取配置信息
		Configuration config = HBaseConfiguration.create();
		log.info("config: " + config);
		try {
			connection = HConnectionManager.createConnection(config);
		} catch (IOException e) {
			log.error(null, e);
		}
	}

	public static HConnection getHConnection() {
		return connection;
	}

	public static void readGPSFromHbase(String callLetter, long startTime, long endTime) {
		CopyOnWriteArrayList<GpsBaseInfo> tempTravelList = null;
		//log.info("startTime: " + startTime + ", endTime: " + endTime + ", callLetter: " + callLetter);
		{
			GpsHistoryInfo obdhistory = new GpsHistoryInfo(getHConnection());
			obdhistory.GetHistoryInfo(callLetter, startTime, endTime);
			tempTravelList = obdhistory.getUserPositionList();
			
			/*if(tempTravelList!=null && tempTravelList.size()>0)
				System.out.println("tempTravelList size："+tempTravelList.size());*/
			
			//System.out.println("tempTravelList size"+tempTravelList);
		}
		List<GpsTempInfo> positionResult = new CopyOnWriteArrayList<GpsTempInfo>();
		if (tempTravelList != null && tempTravelList.size() > 0) {
			//log.info("callLetter: " + callLetter + " , tempTravelList size: " + tempTravelList.size());
			for (GpsBaseInfo tf : tempTravelList) {
				GpsTempInfo ust = new GpsTempInfo();
				if (tf.getStatusList() != null) {
					List<Integer> liststatus= tf.getStatusList();
					//4:车载主电被切断 6：盗警 12：碰撞报警
					for (int i = 0; i < liststatus.size(); i++) {
						if (liststatus.get(i)==12) {
							String str=liststatus.toString();
							str = str.substring(1, str.length() - 1);
							ust.setStatus(str);
							ust.setCallLetter(callLetter);								
							ust.setCourse(tf.getCourse());
							ust.setGpsTime(new Date(tf.getGpsTime()));
							ust.setLat(tf.getLat());
							ust.setLng(tf.getLng());
							ust.setLoc(tf.getLoc() == true ? 1 : 0);
							ust.setOil(tf.getOil());
							ust.setOilPercent(tf.getOilPercent());
							ust.setSpeed(tf.getSpeed());						
							ust.setTemperature1(tf.getTemperature1());
							ust.setTemperature2(tf.getTemperature2());
							ust.setTotalDistance(tf.getTotalDistance());
							positionResult.add(ust);
							break;
						}
					}
					
				}
				/*GpsTempInfo ust = new GpsTempInfo();
				ust.setCallLetter(callLetter);			
				
				ust.setCourse(tf.getCourse());
				ust.setGpsTime(new Date(tf.getGpsTime()));
				ust.setLat(tf.getLat());
				ust.setLng(tf.getLng());
				ust.setLoc(tf.getLoc() == true ? 1 : 0);
				ust.setOil(tf.getOil());
				ust.setOilPercent(tf.getOilPercent());
				ust.setSpeed(tf.getSpeed());
				if (tf.getStatusList() != null) {
					String str = tf.getStatusList().toString();
					str = str.substring(1, str.length() - 1);
					ust.setStatus(str);
				}
				ust.setTemperature1(tf.getTemperature1());
				ust.setTemperature2(tf.getTemperature2());
				ust.setTotalDistance(tf.getTotalDistance());

				positionResult.add(ust);
				System.out.println("positionResult:"+positionResult);*/
			}
			try {
				new VehicleService().saveHaimaTempGPS(positionResult);
			} catch (Exception e) {
				log.error(null, e);
			}
		} else {
			//log.info("gps data null --> " + callLetter);
		}
	}
	
	/**
	 * 统计一个呼号的所有gps数据总数
	 * @param callLetter
	 * @param startTime
	 * @param endTime
	 * @return 
	 */
	public static Long  countGPSFromHbase(String callLetter, long startTime, long endTime) {
		CopyOnWriteArrayList<GpsBaseInfo> tempTravelList = null;
		{
			GpsHistoryInfo obdhistory = new GpsHistoryInfo(getHConnection());
			obdhistory.GetHistoryInfo(callLetter, startTime, endTime);
			tempTravelList = obdhistory.getUserPositionList();
		}
		long count = 0l;
		if (tempTravelList != null && tempTravelList.size() > 0) {
			int size = tempTravelList.size();
			count = size;
			log.info("callLetter: " + callLetter + " , tempTravelList size: " + count);
		} else {
			log.info("gps data null --> " + callLetter);
		}
		return count;
	}
}
