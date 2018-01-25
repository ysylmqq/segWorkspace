package cc.chinagps.gps;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import cc.chinagps.gps.pojo.CarTempInfo;
import cc.chinagps.gps.service.VehicleService;
import cc.chinagps.gps.util.HbaseUtils;
import cc.chinagps.gps.util.SqlSessionFactoryUtil;

public class GPSMain {
	private static final String startPrefix = " 00:00:00";
	private static final String endPrefix = " 23:59:59";
	//
	private static final Logger log = Logger.getLogger(SqlSessionFactoryUtil.class);
	//正则表达式规定时间格式
	private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void main(String[] args) {
		//输入两个时间参数
		String start = args[0];
		String end = args[1];
		
		/*String start="2016-01-01";
		String end="2016-02-01";*/
		
		
		if ((start != null && !"".equals(start)) && (end != null && !"".equals(end))) {
			//通过给定起始时间和结束时间进行筛选
			new GPSMain().saveGpsToDBByMonth(start, end);
		}
		
	}
	
	/*private void saveGpsToDB(String start, String end){
		String startTime = start + startPrefix;//将日期和时间相接
		String endTime = end + endPrefix;
		log.info("startTime: " + startTime + ", endTime: " + endTime);
		try {
			Date startDateTime = datetimeFormat.parse(startTime);
			Date endDateTime = datetimeFormat.parse(endTime);
			
			//定义车辆的一些信息
			VehicleService vehicleService = new VehicleService();
			
			Map<String,String> map = new HashMap<String,String>();
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			// 1.随机取100条海马车
			List<CarTempInfo> cars = vehicleService.selectHaimaCars(map);
			
			System.out.println("CarTempInfo.size --> " + cars.size());
			log.info("CarTempInfo.size --> " + cars.size());
			
			int count = 0;
			// 2.根据车载呼号取GPS信息并存库（4.1-4.30）
			for (CarTempInfo c : cars) {
				// 考虑到一次从Hbase获取的数据量有可能过大，分次获取Hbase中GPS信息
				// Calendar cstart = Calendar.getInstance();
				// cstart.setTime(startDateTime);

				Calendar cend = Calendar.getInstance();
				cend.setTime(endDateTime);

				// 每次循环的开始时间和结束时间（每次取一天的数据进行计算）
				Calendar cs = Calendar.getInstance();
				cs.setTime(startDateTime);

				Calendar ce = Calendar.getInstance();
				ce.setTime(startDateTime);
				ce.add(Calendar.DAY_OF_YEAR, 1);

				for (; cs.before(cend);) {
					HbaseUtils.readGPSFromHbase(c.getCallLetter(), cs.getTimeInMillis(), ce.getTimeInMillis());
					cs.add(Calendar.DAY_OF_YEAR, 1);
					ce.add(Calendar.DAY_OF_YEAR, 1);
				}
			}
			
		} catch (ParseException e) {
			log.error(null, e);
			e.printStackTrace();
		}
	}*/
	
	private void saveGpsToDBByMonth(String start, String end){
		//将时间补全为"2016-01-01 00:00:00"
		System.out.println("起始时间和结束时间");
		String startTime = start + startPrefix;
		String endTime = end + endPrefix;	
		
		log.info("startTime: " + startTime + ", endTime: " + endTime);
		
		try {
			Date startDateTime = datetimeFormat.parse(startTime);
			Date endDateTime = datetimeFormat.parse(endTime);
			System.out.println(startDateTime);//Fri Jan 01 00:00:00 CST 2016
			System.out.println(endDateTime);//Mon Feb 01 23:59:59 CST 2016
			
			VehicleService vehicleService = new VehicleService();
			
			Map<String,String> map = new HashMap<String,String>();
			/*map.put("startTime", "2016-01-01 00:00:00");
			map.put("endTime", "2016-01-03 08:31:00");*/
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			System.out.println(map);
			// 随机取海马车
			List<CarTempInfo> cars = vehicleService.selectHaimaCars(map);
			System.out.println("输出车的信息："+cars.size());
			
			
			for (CarTempInfo c : cars) {
				Calendar cend = Calendar.getInstance();
				cend.setTime(endDateTime);

				Calendar cstart = Calendar.getInstance();
				cstart.setTime(startDateTime);

				Calendar cendTmp = Calendar.getInstance();
				cendTmp.setTime(startDateTime);
				cendTmp.add(Calendar.DAY_OF_YEAR, 3);

				for (; cstart.before(cend);) {
					if (cendTmp.before(cend)) {
						HbaseUtils.readGPSFromHbase(c.getCallLetter(), cstart.getTimeInMillis(), cendTmp.getTimeInMillis());
						cstart.add(Calendar.DAY_OF_YEAR, 3);
						cendTmp.add(Calendar.DAY_OF_YEAR, 3);
					} else {
						HbaseUtils.readGPSFromHbase(c.getCallLetter(), cstart.getTimeInMillis(), cend.getTimeInMillis());
						cstart.add(Calendar.DAY_OF_YEAR, 3);
					}
				}
			}
			
		} catch (ParseException e) {
			log.error(null, e);
		}
	}
	
	/*private void countGpsInHbase(String start, String end){
		String startTime = start + startPrefix;
		String endTime = end + endPrefix;
		log.info("startTime: " + startTime + ", endTime: " + endTime);
		try {
			Date startDateTime = datetimeFormat.parse(startTime);
			Date endDateTime = datetimeFormat.parse(endTime);
			
			VehicleService vehicleService = new VehicleService();
			// 1.查询所有入网海马车辆信息
			List<CarTempInfo> cars = vehicleService.selectEnterNetHaimaCars();
			
			System.out.println("CarInfo.size --> " + cars.size());
			log.info("CarTempInfo.size --> " + cars.size());
			
			long finalCount = 0l;
			
			for (CarTempInfo c : cars) {
				// 每次循环的开始时间和结束时间（每次取一天的数据进行计算）
				Calendar cs = Calendar.getInstance();
				cs.setTime(startDateTime);
				
				Calendar cend = Calendar.getInstance();
				cend.setTime(endDateTime);

				Calendar ce = Calendar.getInstance();
				ce.setTime(startDateTime);
				ce.add(Calendar.DAY_OF_YEAR, 30);
				
				for (; cs.before(cend);) {
					if (ce.before(cend)) {
						long tempCount = HbaseUtils.countGPSFromHbase(c.getCallLetter(), cs.getTimeInMillis(), ce.getTimeInMillis());
						finalCount += tempCount;
						cs.add(Calendar.DAY_OF_YEAR, 30);
						ce.add(Calendar.DAY_OF_YEAR, 30);
						log.info("temp finalCount: " + finalCount);
					} else {
						long tempEndCount = HbaseUtils.countGPSFromHbase(c.getCallLetter(), cs.getTimeInMillis(), ce.getTimeInMillis());
						log.info("startTT: " + datetimeFormat.format(new Date(cs.getTimeInMillis())));
						log.info("endTT: " + datetimeFormat.format(new Date(ce.getTimeInMillis())));
						finalCount += tempEndCount;
						cs.add(Calendar.DAY_OF_YEAR, 30);
					}
				}
				log.info("callLetter: " + c.getCallLetter() + ", finalCount: " + finalCount);
			}
			
			log.info("*********总数：" + finalCount+ "**********");
			
		} catch (ParseException e) {
			log.error(null, e);
		}
	}*/
}
