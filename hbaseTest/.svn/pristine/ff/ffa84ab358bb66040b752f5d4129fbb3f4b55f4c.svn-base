package org.ysy.com;

import java.io.IOException;
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

public class Util {
	private Configuration conf = null;
	private HBaseAdmin hbaseAdmin = null;
	
	public Util(){
		System.setProperty("hadoop.home.dir","E:\\soft\\hadoop\\hadoop-2.6.1"); //这个是window上面的
		conf = HBaseConfiguration.create();
	/*	conf.set("hbase.zookeeper.quorum", "hbasestudy");
		conf.set("hbase.master", "hbasestudy:9000"); */

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
	 */
	public void  addRecord(String tableName) throws IOException{
		long start = System.currentTimeMillis();
		boolean isExist = hbaseAdmin.tableExists(tableName);
		if(isExist){
			HTable table = new HTable(conf, tableName); //获取hbase当中的表
			List<Put> puts = new ArrayList<Put>();
			for(int i =0; i < 600000; i++){
				Put put = new Put(Bytes.toBytes("id_"+i)); //创建一个put对象  必须有id
				//往basic_info 列族当中添加列
				int val = (int)(Math.random()*26+1);
				String [] xyz = new String[]{"a","b","c","d","e","f","g","h","i","g","k","l","m","n","q","p","q","r","s","t","u","v","w","x","y","z","w","x"};
				put.add(Bytes.toBytes("basic_info"), Bytes.toBytes("name"), Bytes.toBytes("zh"+i));
				put.add(Bytes.toBytes("basic_info"), Bytes.toBytes("age"), Bytes.toBytes(xyz[val]));
				put.add(Bytes.toBytes("basic_info"), Bytes.toBytes("call"), Bytes.toBytes("150149892"));
				
				//往other_info 列族当中添加列
				put.add(Bytes.toBytes("other_info"), Bytes.toBytes("money"), Bytes.toBytes("2000"));
				put.add(Bytes.toBytes("other_info"), Bytes.toBytes("salary"), Bytes.toBytes("15000"));
				put.add(Bytes.toBytes("other_info"), Bytes.toBytes("score"), Bytes.toBytes("100"));
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
		Scan scan = new Scan(); //通过构造scan来不断添加条件进行赛选
		scan.addColumn(Bytes.toBytes("basic_info"), Bytes.toBytes("name"));
		/*scan.addFamily(Bytes.toBytes("basic_info"));
		scan.addFamily(Bytes.toBytes("other_info"));*/
		//Filter filter = new RowFilter(CompareFilter.CompareOp.LESS_OR_EQUAL,new BinaryComparator(Bytes.toBytes("id_20")));
		Filter filter = new RowFilter(CompareFilter.CompareOp.EQUAL,new RegexStringComparator("14910200184*"));
		scan.setFilter(filter);
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
		Filter filter = new DependentColumnFilter(Bytes.toBytes("gps"),Bytes.toBytes("callletter")
				,false,CompareFilter.CompareOp.EQUAL,new BinaryComparator(Bytes.toBytes(value)));
		scan.setFilter(filter);
		ResultScanner resultScanner = table.getScanner(scan);
		int i = 0;
		Result result = resultScanner.next();
		for (KeyValue keyValue : result.raw()) {
			System.err.println("  family="+new String(keyValue.getFamily())
							  +"  column="+new String(keyValue.getQualifier()) 
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
		Filter filter = new SingleColumnValueFilter(Bytes.toBytes("gps"),Bytes.toBytes("callletter")
				,CompareFilter.CompareOp.EQUAL,new BinaryComparator(Bytes.toBytes(value)));
		/*Filter filter = new SingleColumnValueExcludeFilter(Bytes.toBytes("gps"),Bytes.toBytes("callletter")
				,CompareFilter.CompareOp.NOT_EQUAL,new BinaryComparator(Bytes.toBytes(value)));*/
		scan.setFilter(filter);
		ResultScanner resultScanner = table.getScanner(scan);
		for (Result result : resultScanner) {
			String callLetter = new String(result.getValue(Bytes.toBytes("gps"),Bytes.toBytes("callLetter")));
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
	
	public static void main(String args[]) throws IOException{
		Util util = new Util(); // 14910201871
		//util.createTable("t_gps_ysytest", new String[]{"gps"});
		//util.addRecord("customer");
		//util.getRow("customer", "id_9");
		//util.scanTable("customer");
		//util.familyFilterScan("customer");
		//util.valueFilterScan("t_gps_yimingtest1");
		//util.deleteRow("customer","id_8");
		//util.dependentColumnFilterScan("t_gps_yimingtest1","14910200184");
		//util.singleColumnFilterScan("t_gps_yimingtest1","14910200184");
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
