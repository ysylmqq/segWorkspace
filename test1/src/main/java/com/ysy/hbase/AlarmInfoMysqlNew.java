package com.ysy.hbase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.ysy.hbase.LeaseDataBuff.LeaseAlarmInfo;

public class AlarmInfoMysqlNew {
	
	final static Logger logger = Logger.getLogger(AlarmInfoMysqlNew.class);
	private static int START_ALARM_ID = 113572907;  
	private static int END_ALARM_ID = 123572907;  // 和hbase当中的保持一次 结束id:113572907
	private ExecutorService  executorService = Executors.newFixedThreadPool(4);

	/**
	 * 线程安全的并发队列
	 */
	final ConcurrentLinkedQueue<LeaseAlarmInfo> queue = new ConcurrentLinkedQueue<LeaseAlarmInfo>();

	private Map<Integer,String> callMap;

	private Connection conn;
	private Statement statement;
	private ResultSet rs;
	
	
	public AlarmInfoMysqlNew(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://90.0.8.228/gboss2016","gboss", "123456");
		    statement = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public  long getMilSeconds(Timestamp date){
		if(date != null){
			return date.getTime();
		}
		return 0;
	}
	
	/**
	 * 同一个呼号 判断list当中是否存在该警情在一个小时之内
	 * @return
	 */
	public List<LeaseDataBuff.LeaseAlarmInfo> alarmInfoInHour( List<LeaseDataBuff.LeaseAlarmInfo> list,LeaseDataBuff.LeaseAlarmInfo alarmInfo,int alarmType ){
		
		boolean  flag = true;
		long gpsTime2New =  alarmInfo.getGpsTime2(); 

		for (int i = 0; i < list.size(); i++) {
			LeaseDataBuff.LeaseAlarmInfo temp = list.get(i);
			if( alarmInfo.getAlarmType() == temp.getAlarmType() && alarmInfo.getAlarmType() == alarmType ){
				//判断是否在同一个时间段之内
				long gpsTime2 = temp.getGpsTime2(); 
				if( Math.abs(gpsTime2New - gpsTime2 ) > 1000*60*60){ //在1小时之外
					flag = true;
				}else{ //在1小时之内
					flag = false;
					break;
				}
			}
		}
		
		//知道循环最后 如果为true
		if(flag){
			list.add(alarmInfo);
		}
		return list;
	}
	
	/**
	 * 把reslt 结果封装成一个Map
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	public Map<String,List<LeaseDataBuff.LeaseAlarmInfo>> resultMap (ResultSet rs) throws Exception{
		Map<String,List<LeaseDataBuff.LeaseAlarmInfo>> resultMap = new HashMap<String,List<LeaseDataBuff.LeaseAlarmInfo>>();
		
		while (rs.next()){
	    	int unitId = rs.getInt("unit_id");
	    	String callLetter = callMap.get(unitId);
	    	if("".equalsIgnoreCase(callLetter) || callLetter == null ){
				continue;
	    	}
	    	 
		    int alarmId = rs.getInt("alarm_id");
	    	int deelType = rs.getInt("deel_type");
	    	double lon2 = rs.getDouble("lon2");
	    	double lat2 = rs.getDouble("lat2");
	    	String refer2 = rs.getString("refer2")== null ? "": rs.getString("refer2");
	    	
	    	Timestamp gpsTime2 = rs.getTimestamp("gpsTime2");
	    	int course2 = rs.getInt("course2");
	    	int speed = rs.getInt("speed");
	    	int alarmType = rs.getInt("alarm_type");
	    	Timestamp addStamp = rs.getTimestamp("add_stamp");
	    	int areaId = rs.getInt("area_id");
	    	int isDeel = rs.getInt("is_deel");
	    	int isDelete = rs.getInt("is_delete");
	    		
			LeaseDataBuff.LeaseAlarmInfo.Builder alarmInfobuilder= LeaseDataBuff.LeaseAlarmInfo.newBuilder();
			alarmInfobuilder.setAlarmId(alarmId);
			alarmInfobuilder.setDeelType(deelType);
			alarmInfobuilder.setRemark("");
			alarmInfobuilder.setCusId(0);
			alarmInfobuilder.setCusName("");
			alarmInfobuilder.setIsDeel(isDeel);
			alarmInfobuilder.setUnitId(unitId);
			alarmInfobuilder.setNumberPlate("");
		    alarmInfobuilder.setLon1(0);
			alarmInfobuilder.setLon2((int)(lon2*1000000));
			alarmInfobuilder.setLat1(0);
			alarmInfobuilder.setLat2((int)(lat2*1000000));
			
			alarmInfobuilder.setRefer1("");
			alarmInfobuilder.setRefer2(refer2);
			alarmInfobuilder.setGpsTime1(0);
			alarmInfobuilder.setGpsTime2(getMilSeconds(gpsTime2));
			alarmInfobuilder.setBeginStamp(0);
			alarmInfobuilder.setEndStamp(0);
			alarmInfobuilder.setCourse1(0);
			alarmInfobuilder.setCourse2(course2);
			alarmInfobuilder.setSpeed(speed);
			alarmInfobuilder.setAlarmType(alarmType);
			alarmInfobuilder.setIsDelete(isDelete);
			alarmInfobuilder.setAddStamp(getMilSeconds(addStamp));
			alarmInfobuilder.setAreaId(areaId);
			
			List<LeaseDataBuff.LeaseAlarmInfo> list = resultMap.get(callLetter);
			// 3或4是要处理的警情
			if( alarmType == 3 || alarmType == 4){
				//比较复杂
				//判断map当中是否存在该呼号的警情信息    要分别判断是3 还是4  
				if(alarmType == 3){
					if( list != null ){ // alarmType = 3  判断 list当中 type = 3 的记录是不是在一个小时之间
						list = alarmInfoInHour(list,alarmInfobuilder.build(),3);
			    		resultMap.put(callLetter, list);
					}else{
						List<LeaseDataBuff.LeaseAlarmInfo> newlist = new ArrayList<LeaseDataBuff.LeaseAlarmInfo>();
			    		newlist.add(alarmInfobuilder.build());
			    		resultMap.put(callLetter, newlist);
					}
				}else if(alarmType == 4){
					if( list != null ){ // alarmType = 4  判断 list当中 type = 4的记录是不是在一个小时之间
						list = alarmInfoInHour(list,alarmInfobuilder.build(),4);
			    		resultMap.put(callLetter, list);
					}else{
						List<LeaseDataBuff.LeaseAlarmInfo> newlist = new ArrayList<LeaseDataBuff.LeaseAlarmInfo>();
			    		newlist.add(alarmInfobuilder.build());
			    		resultMap.put(callLetter, newlist);
					}
				}
			}else{
				// 不是3或4 判断是否存在该呼号，存在添加到map对应的list当中，不存在直接添加到map当中
		    	if( list != null ){ // 
		    		list.add(alarmInfobuilder.build());
		    	}else{
		    		List<LeaseDataBuff.LeaseAlarmInfo> newlist = new ArrayList<LeaseDataBuff.LeaseAlarmInfo>();
		    		newlist.add(alarmInfobuilder.build());
		    		resultMap.put(callLetter, newlist);
		    	}
			}
			//判断
		}
		return resultMap;
	}
	
	
	public void getCallLetter() throws Exception{
		callMap= new HashMap<Integer,String>();
		StringBuilder sb1 = new StringBuilder(" SELECT unit_id,call_letter FROM t_ba_unit ");
		ResultSet rs1 = statement.executeQuery(sb1.toString());
		while(rs1.next()){
			int unitId = rs1.getInt("unit_id");
			String callLetter = rs1.getString("call_letter");
			callMap.put(unitId, callLetter);
		}
	}
	
	/**
	 * @param tableName total = 1618426
	 * @throws Exception
	 */
	public void  addAlarmInfo() throws Exception{
		long start = System.currentTimeMillis();
		Map<String, List<LeaseAlarmInfo>> map = new HashMap<String, List<LeaseAlarmInfo>> ();
		int count = 0; 
		
		for (int i = START_ALARM_ID; i < END_ALARM_ID; ) {
			int temp = i;
			i = i + 300000;
			StringBuilder sb = new StringBuilder(
					" SELECT alarm_id,deel_type, unit_id,lon2,lat2,refer2 , gpsTime2,course2,speed,alarm_type" +
	        		",add_stamp,area_id,is_deel,is_delete FROM t_wg_alarm_old  where alarm_id >=  ");
			sb.append(temp).append(" and alarm_id < ").append(i).append(" ORDER BY gpsTime2 ASC  ");
			logger.debug("[ query sql ] "+sb.toString());
			System.out.println("[ query sql ] "+sb.toString());
			
			rs = statement.executeQuery(sb.toString());
			map = resultMap(rs);
			
			 for (Entry<String, List<LeaseAlarmInfo>> entry : map.entrySet()) {
				List<LeaseAlarmInfo> tempList = entry.getValue();
				count += tempList.size();
				for (int k = 0; k < tempList.size(); k++) {
					queue.offer(tempList.get(k));
				}
				executorService.execute(new InsertMysql());
				executorService.execute(new InsertMysql());
				executorService.execute(new InsertMysql());
				executorService.execute(new InsertMysql());
			 }
		}
        rs.close();
        conn.close();
		long end = System.currentTimeMillis();
		logger.debug("totalTime = "+(end-start)/60000+"  count= "+count);
		System.out.println("totalTime = "+(end-start)/60000+"  count= "+count);
		return ;
	}
	
	public class InsertMysql implements Runnable{

		private Connection conn1;
		private PreparedStatement ps1;
		
        public void run() {
        	try {
				Class.forName("com.mysql.jdbc.Driver");
				conn1 = DriverManager.getConnection("jdbc:mysql://90.0.8.228/gboss2016","gboss", "123456");
	        	conn1.setAutoCommit(false);
	    		ps1 =  conn1.prepareStatement(" insert into t_wg_alarm(alarm_id,unit_id,lon2,lat2,refer2,gpstime2,course2,speed" +
	    				",alarm_type,add_stamp,area_id,is_deel,is_delete,deel_type)values(?,?,?,?,?,?,?,?,?,?,?,?,?,0)");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			int count = 0;
        	while(true){
    	    	try {
    	    		LeaseAlarmInfo tempAlamrInfo = queue.poll();
    	    		if(tempAlamrInfo == null){
    	    			break;
    	    		}
    	    		ps1.setLong(1,tempAlamrInfo.getAlarmId());
					ps1.setLong(2,tempAlamrInfo.getUnitId());
					ps1.setFloat(3, tempAlamrInfo.getLon2()/1000000);
					ps1.setFloat(4, tempAlamrInfo.getLat2()/1000000);
					ps1.setString(5,tempAlamrInfo.getRefer2());
					ps1.setTimestamp(6, new Timestamp(tempAlamrInfo.getGpsTime2()));
					ps1.setInt(7, tempAlamrInfo.getCourse2());
					ps1.setInt(8, tempAlamrInfo.getSpeed());
					ps1.setInt(9, tempAlamrInfo.getAlarmType());
					ps1.setTimestamp(10, new Timestamp(tempAlamrInfo.getAddStamp()));
					ps1.setInt(11, tempAlamrInfo.getAreaId());
					ps1.setInt(12, tempAlamrInfo.getIsDeel());
					ps1.setInt(13, tempAlamrInfo.getIsDelete());
    	    		count++;
    	    		ps1.executeUpdate();
    	    		if(count % 500 == 0){
    	    			conn1.commit();
    	    		}
				} catch (Exception e) {
					e.printStackTrace();
				}
           }
		    try {
				conn1.commit();
				conn1.close();
        		ps1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
	}
	
	public static void main(String[] args) throws Exception {
		final AlarmInfoMysqlNew util = new AlarmInfoMysqlNew();
		util.getCallLetter();
		util.addAlarmInfo();
	}
	
}
