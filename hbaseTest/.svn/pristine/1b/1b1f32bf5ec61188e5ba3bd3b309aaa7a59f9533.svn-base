package org.ysy.com.hbase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.ClusterStatus;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ServerLoad;
import org.apache.hadoop.hbase.ServerName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.DependentColumnFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.QualifierFilter;
import org.apache.hadoop.hbase.filter.RandomRowFilter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.ValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.ysy.com.util.HBaseKeyUtil;

public class HbaseApi {
	private Configuration conf = null;
	private HBaseAdmin hbaseAdmin = null;
	
	public HbaseApi(){
		conf = HBaseConfiguration.create();

		try {
			hbaseAdmin = new HBaseAdmin(conf);
		} catch (MasterNotRunningException e) {
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 创建表并且添加列族
	 * @param tableName
	 * @param familys
	 * @throws IOException 
	 */
	public void  createTable(String tableName ,String [] familys) throws IOException{
		boolean isExist = hbaseAdmin.tableExists(tableName);
		if(isExist){
			System.err.println(tableName+" 已经存在");
		}else{
			HTableDescriptor desc = new HTableDescriptor(tableName);
			for (int i = 0; i < familys.length; i++) {
				String string = familys[i];
				HColumnDescriptor hCol = new HColumnDescriptor(string);
				desc.addFamily(hCol);
			}
			hbaseAdmin.createTable(desc);
			System.err.println(tableName+" 创建成功");
		}
	}
	
	/**
	 * 往表当中添加记录
	 * @param tableName
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void  addRecord(String tableName) throws IOException, InterruptedException{
		long start = System.currentTimeMillis();
		boolean isExist = hbaseAdmin.tableExists(tableName);
		if(isExist){
			HTable table = new HTable(conf, tableName); //获取hbase当中的表
			List<Put> puts = new ArrayList<Put>();
			for(int i =0; i < 100; i++){
				String id = (Long.MAX_VALUE-System.currentTimeMillis())+"";
				Put put = new Put(Bytes.toBytes(id)); //创建一个put对象  必须有id
				//往basic_info 列族当中添加列
				Thread.sleep(10);
				int val = (int)(Math.random()*26+1);
				String [] xyz = new String[]{"a","b","c","d","e","f","g","h","i","g","k","l","m","n","q","p","q","r","s","t","u","v","w","x","y","z","w","x"};
				put.add(Bytes.toBytes("basic_info"), Bytes.toBytes("name"), Bytes.toBytes("zh"+i));
				put.add(Bytes.toBytes("basic_info"), Bytes.toBytes("age"), Bytes.toBytes(xyz[val]));
				put.add(Bytes.toBytes("basic_info"), Bytes.toBytes("call"), Bytes.toBytes("150149892"));
				
				/*//往other_info 列族当中添加列
				put.add(Bytes.toBytes("other_info"), Bytes.toBytes("money"), Bytes.toBytes("2000"));
				put.add(Bytes.toBytes("other_info"), Bytes.toBytes("salary"), Bytes.toBytes("15000"));
				put.add(Bytes.toBytes("other_info"), Bytes.toBytes("score"), Bytes.toBytes("100"));*/
				//remark_info 列族当中添加列
				//put.add(Bytes.toBytes("remark_info"), Bytes.toBytes("stamp"), Bytes.toBytes(System.currentTimeMillis()));
				//添加不存在的列族 会包异常
				//put.add(Bytes.toBytes("test_info"), Bytes.toBytes("test"), Bytes.toBytes(System.currentTimeMillis()));

				puts.add(put);
			}
			table.put(puts);
			table.close();
			System.err.println("添加成功");
		}else{
			System.err.println(tableName+" 不存在");
		}
		long end = System.currentTimeMillis();
		System.err.println("totalTime "+(end-start)/1000);
	}
	
	
	public void  addAlarmInfo(String tableName) throws Exception{
		long start = System.currentTimeMillis();
		boolean isExist = hbaseAdmin.tableExists(tableName);
		int  count = 0;
		if(isExist){
			HTable table = new HTable(conf, tableName);
			table.setAutoFlush(false);
			table.setWriteBufferSize(20971520);
			List<Put> puts = new ArrayList<Put>();

			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://90.0.25.7/gboss2016","gboss", "123456");
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(" SELECT alarm_id, deel_type,remark,cus_id,cus_name,is_deel," +
            		" unit_id,call_letter,numbr_plate,lon1,lon2,lat1,lat2,refer1,refer2 ,gpsTime1 ,"+
            		" gpsTime2, begin_stamp,end_stamp,course1,course2,speed,alarm_type," +
            		"is_delete,add_stamp,area_id FROM t_wg_alarm limit 1000 ");
            while (rs.next()) {
            	count++;
            	int alarmId = rs.getInt("alarm_id");
            	int deelType = rs.getInt("deel_type");
            	String remark = rs.getString("remark")== null ? "": rs.getString("remark");
            	int cusId = rs.getInt("cus_id");
            	String cusName = rs.getString("cus_name") == null ? "": rs.getString("cus_name");
            	int isDeel = rs.getInt("is_deel");
            	int unitId = rs.getInt("unit_id");
            	String callLetter = rs.getString("call_letter") == null ? "": rs.getString("call_letter");
            	String numberPlate = rs.getString("numbr_plate")== null ? "": rs.getString("numbr_plate");;
            	float lon1 = rs.getFloat("lon1");
            	float lon2 = rs.getFloat("lon2");
            	float lat1 = rs.getFloat("lat1");
            	float lat2 = rs.getFloat("lat2");
            	String refer1 = rs.getString("refer1") == null ? "": rs.getString("refer1");
            	String refer2 = rs.getString("refer2")== null ? "": rs.getString("refer2");
            	int gpsTime1 = rs.getInt("gpsTime1");
            	int gpsTime2 = rs.getInt("gpsTime2");
            	int beginStamp = rs.getInt("begin_stamp");
            	int endStamp = rs.getInt("end_stamp");
            	int course1 = rs.getInt("course1");
            	int course2 = rs.getInt("course2");
            	int speed = rs.getInt("speed");
            	int alarmType = rs.getInt("alarm_type");
            	int isDelete = rs.getInt("is_delete");
            	int addStamp = rs.getInt("add_stamp");
            	int areaId = rs.getInt("area_id");
            		
            	int tempCall = unitId+count;
            	System.err.println("tempCall "+tempCall);
            	byte keyArry[] = HBaseKeyUtil.getKey(tempCall+"", gpsTime2);
            	
            	for (int i = 0; i < keyArry.length; i++) {
					byte b = keyArry[i];
					System.err.print(" "+b);
				}
            	System.err.println("");
				Put put = new Put(keyArry); //创建一个put对象  必须有id
				
				LeaseDataBuff.DeliverLeaseAlarmInfo.Builder  builder = LeaseDataBuff.DeliverLeaseAlarmInfo.newBuilder();
				builder.setCallLetter(unitId+""); //call_letter里面存放的是unitId
				builder.setGpsTime(gpsTime2);
				
				LeaseDataBuff.LeaseAlarmInfo.Builder alarmInfobuilder= LeaseDataBuff.LeaseAlarmInfo.newBuilder();
				alarmInfobuilder.setAlarmId(alarmId);
				alarmInfobuilder.setDeelType(deelType);
				alarmInfobuilder.setRemark(remark);
				alarmInfobuilder.setCusId(cusId);
				alarmInfobuilder.setCusName(cusName);
				alarmInfobuilder.setIsDeel(isDeel);
				alarmInfobuilder.setUnitId(unitId);
				alarmInfobuilder.setNumberPlate(numberPlate);
			    alarmInfobuilder.setLon1((int)lon1*1000000);
				alarmInfobuilder.setLon2((int)lon2*1000000);
				alarmInfobuilder.setLat1((int)lat1*1000000);
				alarmInfobuilder.setLat2((int)lat2*1000000);
				
				alarmInfobuilder.setRefer1(refer1);
				alarmInfobuilder.setRefer2(refer2);
				alarmInfobuilder.setGpsTime1(gpsTime1);
				alarmInfobuilder.setGpsTime2(gpsTime2);
				alarmInfobuilder.setBeginStamp(beginStamp);
				alarmInfobuilder.setEndStamp(endStamp);
				alarmInfobuilder.setCourse1(course1);
				alarmInfobuilder.setCourse2(course2);
				alarmInfobuilder.setSpeed(speed);
				alarmInfobuilder.setAlarmType(alarmType);
				alarmInfobuilder.setIsDelete(isDelete);
				alarmInfobuilder.setAddStamp(addStamp);
				alarmInfobuilder.setAreaId(areaId);
				
				
				builder.addLeaseAlarmInfos(alarmInfobuilder);
				put.add(Bytes.toBytes("leasealarm"), Bytes.toBytes("callletter"), Bytes.toBytes(unitId));
				put.add(Bytes.toBytes("leasealarm"), Bytes.toBytes("leasealarminfo"), builder.build().toByteArray());
				puts.add(put);
				if(count % 1000 ==0){
					table.put(puts);
					puts.clear();
					table.flushCommits();
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
	 * 根据id查询记录
	 * @param tableName
	 * @param key
	 * @throws IOException
	 */
	public void getRow(String tableName,String key) throws IOException{
		HTable table = new HTable(conf,tableName);
		Get get = new Get(Bytes.toBytes(key));
		Result result = table.get(get);
		KeyValue[] keyV =  result.raw();
		if(keyV.length == 0){
			System.err.println(key+" 不存在");
		}else{
			for (KeyValue keyValue : keyV) {
				System.err.println(" getFamily "+new String(keyValue.getFamily())
								  +" getQualifier "+new String(keyValue.getQualifier()) 
				 				  +" getValue "+new String(keyValue.getValue())
								  );
			}
		}
		table.close();
	}
	
	
	/**
	 * 根据id查询警情记录
	 * @param tableName
	 * @param key
	 * @throws IOException
	 */
	public void getRowAlarmInfo(String tableName,String key) throws IOException{
		HTable table = new HTable(conf,tableName);
		byte keyArry[] = new byte[]{ 109, 48, 0, 0, 0, 0, -95, 62, -1, -1, -1, -1, -8, 47};
		Get get = new Get(keyArry);
		Result result = table.get(get);
		KeyValue[] keyV =  result.raw();
		if(keyV.length == 0){
			System.err.println(key+" 不存在");
		}else{
			KeyValue keyValue1 = keyV[0];
			System.err.println(" getFamily "+new String(keyValue1.getFamily())
			  +" getQualifier "+new String(keyValue1.getQualifier()) 
			  +" alarmInfo "+new String(keyValue1.getValue()) 
			  );
			
			KeyValue keyValue2 = keyV[1];
			LeaseDataBuff.DeliverLeaseAlarmInfo alarmInfo =  LeaseDataBuff.DeliverLeaseAlarmInfo.parseFrom(keyValue2.getValue());

			System.err.println(" getFamily "+new String(keyValue2.getFamily())
							  +" getQualifier "+new String(keyValue2.getQualifier()) 
			 				  +" alarmInfo "+alarmInfo
							  );
			
			/*for (KeyValue keyValue : keyV) {
				//LeaseDataBuff.DeliverLeaseAlarmInfo alarmInfo =  LeaseDataBuff.DeliverLeaseAlarmInfo.parseFrom(keyValue.getValue());
				System.err.println(" getFamily "+new String(keyValue.getFamily())
			}*/
		}
		table.close();
	}
	/**
	 * rowFilter  = where  id   > ( <, !=, between and 等)
	 * @param tableName
	 * @throws IOException 
	 */
	public void rowFilter(String tableName) throws IOException{
		HTable table = new HTable(conf,tableName);
		Scan scan = new Scan(); //通过构造scan来不断添加条件进行赛选
		Filter filter = new RowFilter(CompareFilter.CompareOp.EQUAL,new RegexStringComparator("14910200184*"));
		scan.setFilter(filter);
		ResultScanner resultScanner = table.getScanner(scan);
		for (Result result : resultScanner) {
			for (KeyValue keyValue : result.raw()) {
				System.err.println(new String(keyValue.getKey())+" "+new String(keyValue.getFamily())
								  +" "+new String(keyValue.getQualifier()) 
				 				  +" "+new String(keyValue.getValue())
								  );
			}
		}
		table.close();
	}
	
	/**
	 * 通过列(名)族进行过滤
	 * @param tableName
	 * @throws IOException 
	 */
	public void familyFilterScan(String tableName,String value ) throws IOException{
		HTable table = new HTable(conf,tableName);
		Scan scan = new Scan(); //通过构造scan来不断添加条件进行赛选
		//Filter filter = new FamilyFilter(CompareFilter.CompareOp.EQUAL,new BinaryComparator(Bytes.toBytes("other_info")));
		Filter filter = new QualifierFilter(CompareFilter.CompareOp.EQUAL,new BinaryComparator(Bytes.toBytes("stamp")));
		scan.setFilter(filter);
		ResultScanner resultScanner = table.getScanner(scan);
		for (Result result : resultScanner) {
			long total = result.getTotalSizeOfCells(result);
			System.err.println("total "+total );
			for (KeyValue keyValue : result.raw()) {
				System.err.println(new String(keyValue.getKey())+" "+new String(keyValue.getFamily())
								  +" "+new String(keyValue.getQualifier()) 
				 				  +" "+new String(keyValue.getValue())
								  );
			}
			System.out.println("##########################################################################################");
		}
		table.close();
	}
	
	/**
	 * valueFilter过滤
	 * @param tableName
	 * @throws IOException 
	 */
	public void valueFilterScan(String tableName) throws IOException{
		HTable table = new HTable(conf,tableName);
		Scan scan = new Scan(); //通过构造scan来不断添加条件进行赛选
		Filter filter = new ValueFilter(CompareFilter.CompareOp.EQUAL,new BinaryComparator(Bytes.toBytes("14910201871")));
		scan.setFilter(filter);
		ResultScanner resultScanner = table.getScanner(scan);
		for (Result result : resultScanner) {
			long total = result.getTotalSizeOfCells(result);
			System.err.println("total "+total );
			for (KeyValue keyValue : result.raw()) {
				System.err.println(new String(keyValue.getKey())+" "+new String(keyValue.getFamily())
								  +" "+new String(keyValue.getQualifier()) 
				 				  +" "+new String(keyValue.getValue())
								  );
			}
			System.out.println("##########################################################################################");
		}
		table.close();
	}
	
	/**
	 * 遍历整个表
	 * @param tableName
	 * @throws IOException 
	 */
	public void scanTable(String tableName) throws IOException{
		HTable table = new HTable(conf,tableName);
		long start = System.currentTimeMillis();
		//Scan scan = new Scan(); //通过构造scan来不断添加条件进行赛选
		
		String call = "14910200167"; 
		long startTime = System.currentTimeMillis() - 360000000;
		long endTime = System.currentTimeMillis();
		byte[] startKey = HBaseKeyUtil.getKey(call, startTime);
		byte[] endKey = HBaseKeyUtil.getKey(call, endTime);
		Scan scan = new Scan(endKey, startKey);
		
		//scan.addColumn(Bytes.toBytes("basic_info"), Bytes.toBytes("name"));
		/*scan.addFamily(Bytes.toBytes("basic_info"));
		scan.addFamily(Bytes.toBytes("other_info"));*/
		//Filter filter = new RowFilter(CompareFilter.CompareOp.LESS_OR_EQUAL,new BinaryComparator(Bytes.toBytes("id_20")));
		//Filter filter = new RowFilter(CompareFilter.CompareOp.EQUAL,new RegexStringComparator("14910200184*"));
		//scan.setFilter(filter);
		/*scan.setStartRow(Bytes.toBytes("id_4"));
		scan.setStopRow(Bytes.toBytes("id_8"));*/
		ResultScanner resultScanner = table.getScanner(scan);
		for (Result result : resultScanner) {
			long total = result.getTotalSizeOfCells(result);
			System.err.println("total "+total );
			for (KeyValue keyValue : result.raw()) {
				System.err.println(new String(keyValue.getKey())+" "+new String(keyValue.getFamily())
								  +" "+new String(keyValue.getQualifier()) 
				 				  +" "+new String(keyValue.getValue())
								  );
			}
			System.out.println("##########################################################################################");
		}
		long end = System.currentTimeMillis();
		System.err.println("totalTime "+(end-start)/1000/60);
		table.close();
	}
	
	/**
	 * 删除表当中的row
	 * @param tableName
	 * @param key
	 * @throws IOException 
	 */
	public void deleteRow(String tableName,String key) throws IOException{
		HTable table = new HTable(conf, tableName);
		Delete delete = new Delete(Bytes.toBytes(key));
		//delete.deleteColumns(Bytes.toBytes("basic_info"),Bytes.toBytes("age"));  删除列族
		delete.deleteFamily(Bytes.toBytes("other_info"));
		table.delete(delete);
		System.err.println("删除成功");
		table.close();
	}
	
	/**
	 * 参考列过滤
	 * @param tableName
	 * @throws IOException 
	 */
	public void dependentColumnFilterScan(String tableName,String value) throws IOException{
		HTable table = new HTable(conf,tableName);
		Scan scan = new Scan(); //通过构造scan来不断添加条件进行赛选
		Filter filter = new DependentColumnFilter(Bytes.toBytes("basic_info"),Bytes.toBytes("age")
				,false,CompareFilter.CompareOp.EQUAL,new BinaryComparator(Bytes.toBytes(value)));
		scan.setFilter(filter);
		ResultScanner resultScanner = table.getScanner(scan);
		int i = 0;
		Result result = resultScanner.next();
		for (KeyValue keyValue : result.raw()) {
			System.err.println("  family="+new String(keyValue.getFamily())
							  +"  column="+new String(keyValue.getQualifier()) 
							  +"  key="+new String(keyValue.getKey()) 
			 				  +"  value="+new String(keyValue.getValue())
					);
		}
		/*for (Result result : resultScanner) {
			i++;
			for (KeyValue keyValue : result.raw()) {
				System.err.println("  family="+new String(keyValue.getFamily())
								  +"  column="+new String(keyValue.getQualifier()) 
				 				  +"  value="+new String(keyValue.getValue())
								  +"  stampTime="+keyValue.getTimestamp()
				);
			}
		}*/
		System.err.println("count  "+i);
		table.close();
	}
	
	/**
	 * 单列值过滤
	 * @param tableName
	 * @throws IOException 
	 */
	public void singleColumnFilterScan(String tableName,String value ) throws IOException{
		HTable table = new HTable(conf,tableName);
		Scan scan = new Scan(); //通过构造scan来不断添加条件进行赛选
		Filter filter = new SingleColumnValueFilter(Bytes.toBytes("basic_info"),Bytes.toBytes("age")
				,CompareFilter.CompareOp.EQUAL,new BinaryComparator(Bytes.toBytes(value)));
		/*Filter filter = new SingleColumnValueExcludeFilter(Bytes.toBytes("gps"),Bytes.toBytes("callletter")
				,CompareFilter.CompareOp.NOT_EQUAL,new BinaryComparator(Bytes.toBytes(value)));*/
		scan.setFilter(filter);
		ResultScanner resultScanner = table.getScanner(scan);
		for (Result result : resultScanner) {
			String callLetter = new String(result.getValue(Bytes.toBytes("basic_info"),Bytes.toBytes("age")));
			System.err.println("callLetter "+callLetter);
		}
		table.close();
	}
	
	/**
	 * 分页过滤
	 * @param tableName
	 * @throws IOException 
	 */
	public void pageFilterScan(String tableName) throws IOException{
		HTable table = new HTable(conf,tableName);
		Scan scan = new Scan(); //通过构造scan来不断添加条件进行赛选
		//Filter filter = new PageFilter(20);
		//Filter filter = new KeyOnlyFilter(true);
		//Filter filter = new ColumnCountGetFilter(3);
		//Filter filter = new ColumnPaginationFilter(2,3);
		Filter filter = new RandomRowFilter((float) 0.5);
		scan.setFilter(filter);
		ResultScanner resultScanner = table.getScanner(scan);
		for (Result result : resultScanner) {
			long total = result.getTotalSizeOfCells(result);
			System.err.println("total "+total );
			for (KeyValue keyValue : result.raw()) {
				System.err.println(new String(keyValue.getKey())+" "+new String(keyValue.getFamily())
								  +" "+new String(keyValue.getQualifier()) 
				 				  +" "+new String(keyValue.getValue())
								  );
			}
			System.out.println("##########################################################################################");
		}
		table.close();
	}
	
	/**
	 * 组合过滤器
	 * @param tableName
	 * @throws IOException 
	 */
	public void filterListScan(String tableName) throws IOException{
		HTable table = new HTable(conf,tableName);
		Scan scan = new Scan(); //通过构造scan来不断添加条件进行赛选
		List<Filter> filterList = new ArrayList<Filter>();
		
		Filter filter1 = new ValueFilter(CompareFilter.CompareOp.EQUAL,new BinaryComparator(Bytes.toBytes("zh1038")));
		filterList.add(filter1);
		Filter filter2 = new ValueFilter(CompareFilter.CompareOp.EQUAL,new BinaryComparator(Bytes.toBytes("zh1039")));
		filterList.add(filter2);
		
		FilterList list = new FilterList(FilterList.Operator.MUST_PASS_ONE,filterList);  // or 
		scan.setFilter(list);
		ResultScanner resultScanner = table.getScanner(scan);
		for (Result result : resultScanner) {
			String row = new String(result.getRow());
			System.err.print("rowKey "+row +"  ");
			for (KeyValue keyValue : result.raw()) {
				System.err.println(new String(keyValue.getKey())+" "+new String(keyValue.getFamily())
								  +" "+new String(keyValue.getQualifier()) 
				 				  +" "+new String(keyValue.getValue())
								  );
			}
			System.out.println("##########################################################################################");
		}
		table.close();
	}
	
	/**
	 * 查看所有表
	 * @param tableName
	 * @throws IOException 
	 */
	public void allTable() throws IOException{
		HTableDescriptor[] tables  =  hbaseAdmin.listTables();
		for (HTableDescriptor hTableDescriptor : tables) {
			System.err.println(hTableDescriptor);
		}
	}
	
	/**
	 * 修改表结构
	 * @param tableName
	 * @throws IOException 
	 */
	public void alterTable(String tableName) throws IOException{
		//修改表结构  这种方法 会把原来的列族删除掉，然后在重新添加  
		/*HTableDescriptor htd = new HTableDescriptor(Bytes.toBytes(tableName));
		htd.addFamily(new HColumnDescriptor("basic_info"));
	    hbaseAdmin.modifyTable(Bytes.toBytes(tableName), htd);*/
	    
		//添加列族
	    hbaseAdmin.addColumn(Bytes.toBytes(tableName), new HColumnDescriptor(Bytes.toBytes("other_info")));
		System.err.println("修改表成功");
	}
	
	/**
	 *删除表
	 * @param tableName
	 * @throws IOException 
	 */
	public void delTableByName(String tableName) throws IOException{
		hbaseAdmin.disableTable(Bytes.toBytes(tableName));
		hbaseAdmin.deleteTable(Bytes.toBytes(tableName));
		System.err.println(Bytes.toBytes(tableName)+"删除成功");
	}
	
	/**
	 * 获取region数量
	 * @param tableName
	 * @throws IOException 
	 */
	public void countRegion(String tableName) throws IOException{
		ClusterStatus clusterStatus = hbaseAdmin.getClusterStatus();
		int count = clusterStatus.getServersSize();
		System.err.println("region数量 "+count);
		Collection<ServerName> collection = clusterStatus.getServerInfo();
		Iterator<ServerName> iterator = collection.iterator();
		while (iterator.hasNext()) {
			ServerName serverName = (ServerName) iterator.next();
			ServerLoad serverLoad = clusterStatus.getLoad(serverName);
			System.err.println("hostAndPort  "+serverName.getHostAndPort()
					+"  getUsedHeapMB "+serverLoad.getUsedHeapMB()
					+"  getMaxHeapMB "+serverLoad.getMaxHeapMB()
			);
		}
	}
	
	public LeaseDataBuff.DeliverLeaseAlarmInfo  getAlarmInfo(){
		LeaseDataBuff.DeliverLeaseAlarmInfo.Builder  builder = LeaseDataBuff.DeliverLeaseAlarmInfo.newBuilder();
		builder.setCallLetter("");
		return builder.build();
	}
	
	public static void main(String args[]) throws Exception{
		HbaseApi util = new HbaseApi(); // 14910201871
		//util.addAlarmInfo("t_lease_alarm_info");
		util.getRowAlarmInfo("t_lease_alarm_info","41278");
		//util.createTable("gps", new String[]{"basic_info"});
		//util.addRecord("gps");
		//util.getRow("customer", "id_9");
		//util.scanTable("t_lease_alarm_info");
		//util.familyFilterScan("customer");
		//util.valueFilterScan("gps");
		//util.deleteRow("customer","id_8");
	  // util.dependentColumnFilterScan("gps","l");
		//util.singleColumnFilterScan("gps","l");
		//util.rowFilter("t_gps_yimingtest1");
		//util.pageFilterScan("customer");
		//util.filterListScan("customer");
		//util.allTable();
		//util.delTableByName("student5");
		//util.alterTable("customer");
		//util.countRegion("customer");
		
	}
	
	/*public boolean beginScan(String callletter) {
		try {
			HTableInterface table = connection.getTable(tablename);
			byte[] startKey = HBaseKeyUtil.getKey(callletter, System.currentTimeMillis() + 24 * 3600 * 1000l);
			20170608
			byte[] endKey = HBaseKeyUtil.getKey(callletter, 1000 * 3600 * 24 * 365 * 42l);
			20120000
			Scan scan = new Scan(startKey, endKey);
			scan.setBatch(3);
			scan.setCaching(2); // 扫描缓存 遍历Scan结果集时，一次next循环 会调用一次RPC，一次RPC获取一条记录，通过扫描缓冲，一次RPC可以获取多条结果集
			ResultScanner rsscan = table.getScanner(scan);
			if (rsscan == null) {
				logger.warn("rsscan is null");
				return false;
			} else {
				Iterator<Result> iter = rsscan.iterator();
				if (iter.hasNext()) {
					setGPSInfo(iter.next());
				}
				rsscan.close();
				table.close();
				rsscan = null;
				table = null;
			}
		} catch (Exception e) {
			logger.error(null, e);
			return false;
		}
		return true;
	}*/
	
	
}
