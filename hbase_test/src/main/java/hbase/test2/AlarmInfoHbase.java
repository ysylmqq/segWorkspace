package hbase.test2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.ysy.com.hbase.LeaseDataBuff.LeaseAlarmInfo;
import org.ysy.com.util.HBaseKeyUtil;

public class AlarmInfoHbase {
	private Configuration conf = null;
	private HBaseAdmin hbaseAdmin = null;
	private Map<Integer,String> callMap;

	
	private Connection conn;
	private Statement statement;
	private ResultSet rs;
	
	public AlarmInfoHbase(){
		conf = HBaseConfiguration.create();
		try {
			hbaseAdmin = new HBaseAdmin(conf);
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://90.0.25.7/gboss2016","gboss", "123456");
		    statement = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * 转换成时间戳
	 * @param date
	 * @return
	 */
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
	 * 把reslt 结果封装成一个Map key为呼号，对应的List存放的是该呼号的去掉重复数据之后的警情数据
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
	    	String remark = rs.getString("remark")== null ? "": rs.getString("remark");
	    	int cusId = rs.getInt("cus_id");
	    	String cusName = rs.getString("cus_name") == null ? "": rs.getString("cus_name");
	    	int isDeel = rs.getInt("is_deel");
	    	String numberPlate = rs.getString("numbr_plate")== null ? "": rs.getString("numbr_plate");;
	    	double lon1 = rs.getDouble("lon1");
	    	double lon2 = rs.getDouble("lon2");
	    	double lat1 = rs.getDouble("lat1");
	    	double lat2 = rs.getDouble("lat2");
	    	String refer1 = rs.getString("refer1") == null ? "": rs.getString("refer1");
	    	String refer2 = rs.getString("refer2")== null ? "": rs.getString("refer2");
	    	Timestamp gpsTime1 = rs.getTimestamp("gpsTime1");
	    	
	    	Timestamp gpsTime2 = rs.getTimestamp("gpsTime2");
	    	Timestamp beginStamp = rs.getTimestamp("begin_stamp");
	    	Timestamp endStamp = rs.getTimestamp("end_stamp");
	    	int course1 = rs.getInt("course1");
	    	int course2 = rs.getInt("course2");
	    	int speed = rs.getInt("speed");
	    	int alarmType = rs.getInt("alarm_type");
	    	int isDelete = rs.getInt("is_delete");
	    	Timestamp addStamp = rs.getTimestamp("add_stamp");
	    	int areaId = rs.getInt("area_id");
	    		
			LeaseDataBuff.LeaseAlarmInfo.Builder alarmInfobuilder= LeaseDataBuff.LeaseAlarmInfo.newBuilder();
			alarmInfobuilder.setAlarmId(alarmId);
			alarmInfobuilder.setDeelType(deelType);
			alarmInfobuilder.setRemark(remark);
			alarmInfobuilder.setCusId(cusId);
			alarmInfobuilder.setCusName(cusName);
			alarmInfobuilder.setIsDeel(isDeel);
			alarmInfobuilder.setUnitId(unitId);
			alarmInfobuilder.setNumberPlate(numberPlate);
		    alarmInfobuilder.setLon1((int)(lon1*1000000));
			alarmInfobuilder.setLon2((int)(lon2*1000000));
			alarmInfobuilder.setLat1((int)(lat1*1000000));
			alarmInfobuilder.setLat2((int)(lat2*1000000));
			
			alarmInfobuilder.setRefer1(refer1);
			alarmInfobuilder.setRefer2(refer2);
			alarmInfobuilder.setGpsTime1(getMilSeconds(gpsTime1));
			alarmInfobuilder.setGpsTime2(getMilSeconds(gpsTime2));
			alarmInfobuilder.setBeginStamp(getMilSeconds(beginStamp));
			alarmInfobuilder.setEndStamp(getMilSeconds(endStamp));
			alarmInfobuilder.setCourse1(course1);
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
	 * 把gpsTime2相同的放到一个list当中
	 * @param list
	 * @return
	 */
	public Map<String,List<LeaseAlarmInfo>> transListToMap(List<LeaseAlarmInfo> list){
		
		Map<String,List<LeaseAlarmInfo>> map = new HashMap<String,List<LeaseAlarmInfo>>();
		
		for (int i = 0; i < list.size(); i++) {
			LeaseAlarmInfo alarmInfo = list.get(i);
			List<LeaseAlarmInfo> temp = map.get(String.valueOf(alarmInfo.getGpsTime2()));
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
	 * Map当中key为呼号，List为该呼号所对应的警情数据
	 * 转成的目标List对应DeliverLeaseAlarmInfo, DeliverLeaseAlarmInfo当中包含多个LeaseAlarmInfo
	 * @param map
	 * @return
	 */
	public List<LeaseDataBuff.DeliverLeaseAlarmInfo> transMapToList (Map<String, List<LeaseAlarmInfo>> map){
		List<LeaseDataBuff.DeliverLeaseAlarmInfo> list = new ArrayList<LeaseDataBuff.DeliverLeaseAlarmInfo>();
		
		 for (Entry<String, List<LeaseAlarmInfo>> entry : map.entrySet()) {
			 List<LeaseAlarmInfo> temp = entry.getValue(); // 呼号对应的警情数据;List当中存在gpstime2一样 alamType不一样的记录
			 String callLetter = entry.getKey(); // 呼号
			 
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
	 * @param oldAlarmInfo：hbase当中存在的
	 * @param newAlarmInfo：新添加的
	 * @return 去掉重复之后的数据
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
	
	/**
	 * 获取呼号 
	 * @throws Exception
	 */
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
	 * @param tableName
	 * @throws Exception
	 */
	public void  addAlarmInfo(String tableName) throws Exception{
		int count = 0;
		long start = System.currentTimeMillis();
		boolean isExist = hbaseAdmin.tableExists(tableName);
		if(isExist){
			HTable table = new HTable(conf, tableName);
			table.setAutoFlush(false);
			table.setWriteBufferSize(20971520);
			List<Put> puts = new ArrayList<Put>();
			 
			Map<String, List<LeaseAlarmInfo>> map = new HashMap<String, List<LeaseAlarmInfo>> ();
			
			for (int i = 2717190; i < 99580179; ) {
				int temp = i;
				i = i + 100000;
				StringBuilder sb = new StringBuilder(
						" SELECT alarm_id, deel_type,remark,cus_id,cus_name,is_deel," +
		        		" unit_id,call_letter,numbr_plate,lon1,lon2,lat1,lat2,refer1,refer2 ,gpsTime1 ,"+
		        		" gpsTime2, begin_stamp,end_stamp,course1,course2,speed,alarm_type," +
		        		"is_delete,add_stamp,area_id FROM t_wg_alarm  where alarm_id >=  ");
				sb.append(temp).append(" and alarm_id < ").append(i);
				System.err.println("[ query sql ] "+sb.toString());
				
				rs = statement.executeQuery(sb.toString());
				map = resultMap(rs);
				
				List<LeaseDataBuff.DeliverLeaseAlarmInfo> resultList =  transMapToList(map);
				//日志输出
				/*for (int k = 0; k < resultList.size(); i++) {
					LeaseDataBuff.DeliverLeaseAlarmInfo tt = resultList.get(k);
					List<LeaseAlarmInfo> tg = tt.getLeaseAlarmInfosList();
					
					StringBuilder alarmIds =new StringBuilder("");
					StringBuilder gpsTimes2 =new StringBuilder("");
					
					for (int j = 0; j < tg.size(); j++) {
						alarmIds.append(tg.get(j).getAlarmId()+",");
						gpsTimes2.append(tg.get(j).getGpsTime2()+",");
					}
					System.err.println("unit_id =  "+tt.getCallLetter()+" ;alarmIds =  "+alarmIds+" ;gpsTimes2 = "+gpsTimes2+" ; count = "+tt.getLeaseAlarmInfosList().size());
				}*/
				count = resultList.size()+count;

				System.err.println("save hbase  "+resultList.size());
				LeaseDataBuff.DeliverLeaseAlarmInfo deliver;
				for (int j = 0; j < resultList.size(); j++) {
					deliver = resultList.get(j);
					String callLetter = deliver.getCallLetter();
					byte keyArry[] = HBaseKeyUtil.getKey(callLetter, deliver.getGpsTime());
					Put put = new Put(keyArry); //创建一个put对象  必须有id
					
					//线判断hbase当中是否存在，如果存在就更新，不存在就插入
					LeaseDataBuff.DeliverLeaseAlarmInfo oldDeliver = getRowAlarmInfo("t_lease_alarm_info",keyArry);
					
					if(oldDeliver != null){
						/*StringBuilder byteStr = new StringBuilder();
						for (int k = 0; k < keyArry.length; k++) {
							byteStr.append(keyArry[k]+" ,");
						}
						System.err.println(byteStr);*/
						LeaseDataBuff.DeliverLeaseAlarmInfo.Builder tempBuilder = oldDeliver.newBuilder(oldDeliver);
						
						//需要判断之前的当中是否已经存在了
						List<LeaseDataBuff.LeaseAlarmInfo> oldAlarmInfo = oldDeliver.getLeaseAlarmInfosList();
						List<LeaseDataBuff.LeaseAlarmInfo> newAlarmInfo = deliver.getLeaseAlarmInfosList();
						tempBuilder = delRepeatAlarmInfo(oldAlarmInfo,newAlarmInfo,tempBuilder);
						
						//tempBuilder.addAllLeaseAlarmInfos(oldDeliver.getLeaseAlarmInfosList());
						
						deliver = tempBuilder.build();
					}
					
					put.add(Bytes.toBytes("leasealarm"), Bytes.toBytes("callletter"), Bytes.toBytes(callLetter));
					put.add(Bytes.toBytes("leasealarm"), Bytes.toBytes("leasealarminfo"),deliver.toByteArray());
					puts.add(put);
					if(j % 1000 == 0){
						table.put(puts);
						puts.clear();
						table.flushCommits();
					}
				}
			}
			
			table.put(puts);
			table.flushCommits();
            rs.close();
            conn.close();
			table.close();
			System.err.println("添加成功  count "+count);
		}else{
			System.err.println(tableName+" 不存在");
		}
		long end = System.currentTimeMillis();
		System.err.println("totalTime "+(end-start)/60000);
	}
	
	
	/**
	 * 根据id查询警情记录 
	 * @param tableName
	 * @param key
	 * @throws IOException
	 */
	public LeaseDataBuff.DeliverLeaseAlarmInfo getRowAlarmInfo(String tableName,byte keyArry[]) throws IOException{
		HTable table = new HTable(conf,tableName);
		Get get = new Get(keyArry);
		
		Result result = table.get(get);
		KeyValue[] keyV =  result.raw();
		if(keyV.length == 0){
			table.close();
			return null;
		}else{
			KeyValue keyValue2 = keyV[1];
			LeaseDataBuff.DeliverLeaseAlarmInfo alarmInfo =  LeaseDataBuff.DeliverLeaseAlarmInfo.parseFrom(keyValue2.getValue());
			return alarmInfo;
		}
	}
	
	/**
	 * 根据key从hbase当中获取
	 * @throws Exception
	 */
	public void getHbaseByKey() throws Exception{
		AlarmInfoHbase util = new AlarmInfoHbase();

		/*68 ,57 ,0 ,15 ,25 ,-93 ,84 ,4 ,-2 ,-90 ,106 ,-126 ,30 ,-97 ,
		89 ,57 ,0 ,15 ,26 ,106 ,-115 ,-106 ,-2 ,-90 ,106 ,114 ,60 ,55 ,
		79 ,61 ,0 ,15 ,25 ,48 ,49 ,13 ,-2 ,-90 ,106 ,112 ,-5 ,-25 ,
		79 ,61 ,0 ,15 ,25 ,48 ,49 ,13 ,-2 ,-90 ,107 ,78 ,52 ,-73 ,
		79 ,61 ,0 ,15 ,25 ,48 ,49 ,13 ,-2 ,-90 ,107 ,-68 ,-55 ,79 ,
		79 ,61 ,0 ,15 ,25 ,48 ,49 ,13 ,-2 ,-90 ,108 ,43 ,105 ,-97 ,
*/
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		long time = sdf.parse("2017-09-11 12:43:37").getTime();
		System.err.println("time "+time);
		byte keyArry[] =new byte[]{68 ,57 ,0 ,15 ,25 ,-93 ,84 ,4 ,-2 ,-90 ,106 ,-126 ,30 ,-97};
		
		LeaseDataBuff.DeliverLeaseAlarmInfo tt = util.getRowAlarmInfo("t_lease_alarm_info",keyArry);
	    System.err.println("tt "+tt);
	}
	
	public static void main(String[] args) throws Exception {
		AlarmInfoHbase util = new AlarmInfoHbase();
		util.getCallLetter();
		util.addAlarmInfo("t_lease_alarm_info");
	}
	
}
