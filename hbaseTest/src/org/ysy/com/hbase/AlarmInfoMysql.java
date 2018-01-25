package org.ysy.com.hbase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ysy.com.hbase.LeaseDataBuff.LeaseAlarmInfo;

public class AlarmInfoMysql {
	private Map<Integer,String> callMap;

	
	private Connection conn;
	private Statement statement;
	private ResultSet rs;
	
	public AlarmInfoMysql(){
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
		
		for (int i = 0; i < list.size(); i++) {
			LeaseDataBuff.LeaseAlarmInfo temp = list.get(i);
			if( alarmInfo.getAlarmType() == temp.getAlarmType() && alarmInfo.getAlarmType() == alarmType ){
				//判断是否在同一个时间段之内
				long gpsTime2 = temp.getGpsTime2(); 
				long gpsTime2New =  alarmInfo.getGpsTime2(); 
				if( Math.abs(gpsTime2New - gpsTime2 )/(1000*60*60) > 1){ //在1小时之外
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
	    	
	    	/*" SELECT alarm_id,deel_type, unit_id,lon2,lat2,refer2 , gpsTime2,course2,speed,alarm_type," +
    		",add_stamp,area_id 
*/			int alarmId = rs.getInt("alarm_id");
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
	
	
	/**
	 * 把list转map  gpsTime2作为key
	 * @param list
	 * @return
	 */
	public Map<String,List<LeaseAlarmInfo>> transListToMap(List<LeaseAlarmInfo> list){
		
		Map<String,List<LeaseAlarmInfo>> map = new HashMap<String,List<LeaseAlarmInfo>>();
		
		// 1484150400000   1484150400000
		for (int i = 0; i < list.size(); i++) {
			LeaseAlarmInfo alarmInfo = list.get(i);
			List<LeaseAlarmInfo> temp = map.get(alarmInfo.getGpsTime2()+"");
			if(temp == null){
				temp = new ArrayList<LeaseAlarmInfo>();
				temp.add(alarmInfo);
				map.put(String.valueOf(alarmInfo.getGpsTime2()), temp);
			}else{
				//判断temp当中是否存在 重复的数据
				temp.add(alarmInfo);
			}
		}
		
		return map;
	}
	
	/**
	 * 把Map转换成List
	 * @param map
	 * @return
	 */
	public List<LeaseDataBuff.DeliverLeaseAlarmInfo> transMapToList (Map<String, List<LeaseAlarmInfo>> map){
		List<LeaseDataBuff.DeliverLeaseAlarmInfo> list = new ArrayList<LeaseDataBuff.DeliverLeaseAlarmInfo>();
		
		 for (Entry<String, List<LeaseAlarmInfo>> entry : map.entrySet()) {
			 List<LeaseAlarmInfo> temp = entry.getValue();
			 String callLetter = entry.getKey();
			 
             Map<String,List<LeaseAlarmInfo>> tempMap =  transListToMap(temp); // key 为gpstime2
             
    		 for (Entry<String, List<LeaseAlarmInfo>> entry2 : tempMap.entrySet()) {
    			 List<LeaseAlarmInfo> temp2 = entry2.getValue(); // 包含alarmType =3 or 4 的
    			 String gpsTime2 = entry2.getKey();

    			 LeaseDataBuff.DeliverLeaseAlarmInfo.Builder builder = LeaseDataBuff.DeliverLeaseAlarmInfo.newBuilder();
                 builder.setCallLetter(callLetter).setGpsTime(Long.parseLong(gpsTime2));
                 
                 for (int i = 0; i < temp2.size(); i++) {
                	 builder.addLeaseAlarmInfos(temp2.get(i));
				 }
        		 list.add(builder.build());
    		 }
		}
		 
		return list;
	}
	
	/**
	 * @param oldAlarmInfo
	 * @param newAlarmInfo
	 * @return
	 */
	public LeaseDataBuff.DeliverLeaseAlarmInfo.Builder delRepeatAlarmInfo(List<LeaseDataBuff.LeaseAlarmInfo> oldAlarmInfo,
			List<LeaseDataBuff.LeaseAlarmInfo> newAlarmInfo,
			LeaseDataBuff.DeliverLeaseAlarmInfo.Builder builder){
		
		boolean flag = true;
		for (int i = 0; i < newAlarmInfo.size(); i++) {
			LeaseDataBuff.LeaseAlarmInfo newAlarm = newAlarmInfo.get(i);
			flag = true;
			for (int j = 0; j < oldAlarmInfo.size(); j++) {
				LeaseDataBuff.LeaseAlarmInfo oldAlarm = oldAlarmInfo.get(j);
				if( newAlarm.getUnitId() == oldAlarm.getUnitId() &&
					newAlarm.getGpsTime2() == oldAlarm.getGpsTime2() &&
					newAlarm.getAlarmType() == oldAlarm.getAlarmType()){
					//old当中已经存在了
					flag = false;
					break;
				}else{
					flag = true;
				}
			}
			if(flag){
				builder.addLeaseAlarmInfos(newAlarm);
			}
		}
		return builder;
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
	 * 99580179   2717190
	 * @param tableName
	 * @throws Exception
	 */
	public void  addAlarmInfo(String tableName) throws Exception{
		int count = 0;
		long start = System.currentTimeMillis();
		Map<String, List<LeaseAlarmInfo>> map = new HashMap<String, List<LeaseAlarmInfo>> ();
		/*conn.setAutoCommit(false);
		PreparedStatement ps = conn.prepareStatement(" insert into tt(alarm_id,unit_id,lon2,lat2,refer2,gpstime2,course2,speed" +
				",alarm_type,add_stamp,area_id,is_deel,is_delete)values(?,?,?,?,?,?,?,?,?,?,?,?,?)");*/
		for (int i = 2717190; i < 99580179; ) {
			int temp = i;
			i = i + 200000;
			StringBuilder sb = new StringBuilder(
					" SELECT alarm_id,deel_type, unit_id,lon2,lat2,refer2 , gpsTime2,course2,speed,alarm_type" +
	        		",add_stamp,area_id,is_deel,is_delete FROM t_wg_alarm  where alarm_id >=  ");
			sb.append(temp).append(" and alarm_id < ").append(i);
			System.err.println("[ query sql ] "+sb.toString());
			
			rs = statement.executeQuery(sb.toString());
			map = resultMap(rs);
			List<LeaseDataBuff.DeliverLeaseAlarmInfo> resultList = transMapToList(map);
			for (int j = 0; j < resultList.size(); j++) {
				List<LeaseAlarmInfo> listAlamrInfo = resultList.get(j).getLeaseAlarmInfosList();
				for (int k = 0; k < listAlamrInfo.size(); k++) {
					LeaseAlarmInfo tempAlamrInfo  = listAlamrInfo.get(k);
					System.err.println(tempAlamrInfo.getRefer2());
					/*ps.setLong(1,tempAlamrInfo.getAlarmId());
					ps.setLong(2,tempAlamrInfo.getUnitId());
					ps.setFloat(3, tempAlamrInfo.getLon2()/1000000);
					ps.setFloat(4, tempAlamrInfo.getLat2()/1000000);
					ps.setString(5,tempAlamrInfo.getRefer2());
					ps.setTimestamp(6, new Timestamp(tempAlamrInfo.getGpsTime2()));
					ps.setInt(7, tempAlamrInfo.getCourse2());
					ps.setInt(8, tempAlamrInfo.getSpeed());
					ps.setInt(9, tempAlamrInfo.getAlarmType());
					ps.setTimestamp(10, new Timestamp(tempAlamrInfo.getAddStamp()));
					ps.setInt(11, tempAlamrInfo.getAreaId());
					ps.setInt(12, tempAlamrInfo.getIsDeel());
					ps.setInt(13, tempAlamrInfo.getIsDelete());*/
					count++;
					/*ps.executeUpdate();
					if( count%50 == 0 ){ //每50次提交一次
						conn.commit();
					}*/
				}
			}
		}
		//conn.commit();
        rs.close();
        conn.close();
		long end = System.currentTimeMillis();
		System.err.println("totalTime "+(end-start)/60000);
	}
	
	
	
	public static void main(String[] args) throws Exception {
		AlarmInfoMysql util = new AlarmInfoMysql();
		util.getCallLetter();
		util.addAlarmInfo("t_lease_alarm_info");
		//util.getHbaseByKey();
	}
	
}
