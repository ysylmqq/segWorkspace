package hbase.test2;

import java.io.IOException;       
import java.util.ArrayList;       
import java.util.List;          
import org.apache.hadoop.conf.Configuration;       
import org.apache.hadoop.hbase.HBaseConfiguration;       
import org.apache.hadoop.hbase.HColumnDescriptor;       
import org.apache.hadoop.hbase.HTableDescriptor;       
import org.apache.hadoop.hbase.KeyValue;       
import org.apache.hadoop.hbase.MasterNotRunningException;       
import org.apache.hadoop.hbase.ZooKeeperConnectionException;       
import org.apache.hadoop.hbase.client.Delete;       
import org.apache.hadoop.hbase.client.Get;       
import org.apache.hadoop.hbase.client.HBaseAdmin;       
import org.apache.hadoop.hbase.client.HTable;       
import org.apache.hadoop.hbase.client.Result;       
import org.apache.hadoop.hbase.client.ResultScanner;       
import org.apache.hadoop.hbase.client.Scan;       
import org.apache.hadoop.hbase.client.Put;       
import org.apache.hadoop.hbase.util.Bytes;
import com.mysql.jdbc.log.Log;       
import org.apache.log4j.Logger;
        
public class HBaseTest{    
	private static double sum=0;
	private static int counteachtime=0;
	private static int count=0;       
    private static Configuration conf =null; 
    private static String rowkey="";
    private static String latitude="";
    private static String longitude="";
    private static String startlatitude="";
    private static String startlongitude="";
    private static String endlatitude="";
    private static String endlongitude="";
    private static String callletter="";
    private static String disEuclid="";
    private static String gpstime="";
    private static String predictionDiff="";
     /**  
      * 初始化配置  
     */    
     static {    
         conf = HBaseConfiguration.create();   //HBase的配置文件添加到配置信息中
     }    
         
    /**    
     * 创建一张表    
     */      
    public static void creatTable(String tableName, String[] familys) throws Exception {       
        HBaseAdmin admin = new HBaseAdmin(conf);       
        if (admin.tableExists(tableName)) {       
            System.out.println("table already exists!");       
        } else {       
        	//获取表名
            HTableDescriptor tableDesc = new HTableDescriptor(tableName);       
            for(int i=0; i<familys.length; i++){    
            	//添加列族，因为创建表时，至少指定一个列族
                tableDesc.addFamily(new HColumnDescriptor(familys[i]));       
            }       
            //创建表，含表名和列族
            admin.createTable(tableDesc);       
            System.out.println("create table " + tableName + " ok.");       
        }        
    }       
           
    /**    
     * 删除表    
     */      
    public static void deleteTable(String tableName) throws Exception {       
       try {       
           HBaseAdmin admin = new HBaseAdmin(conf);       
           admin.disableTable(tableName);       
           admin.deleteTable(tableName);       
           System.out.println("delete table " + tableName + " ok.");       
       } catch (MasterNotRunningException e) {       
           e.printStackTrace();       
       } catch (ZooKeeperConnectionException e) {       
           e.printStackTrace();       
       }       
    }       
            
    /**    
     * 插入一行记录    
     */      
    public static void addRecord (String tableName, String rowKey, String family, String qualifier, String value)       
            throws Exception{       
        try {       
            HTable table = new HTable(conf, tableName);       
            Put put = new Put(Bytes.toBytes(rowKey)); //Bytes.toBytes()为hadoop方法      
            put.add(Bytes.toBytes(family),Bytes.toBytes(qualifier),Bytes.toBytes(value));       
            table.put(put);       
            System.out.println("insert recored " + rowKey + " to table " + tableName +" ok.");       
        } catch (IOException e) {       
            e.printStackTrace();       
        }       
    }       
        
    /**    
     * 删除一行记录    
     */      
    public static void delRecord (String tableName, String rowKey) throws IOException{       
        HTable table = new HTable(conf, tableName);       
        List list = new ArrayList();       
        Delete del = new Delete(rowKey.getBytes());       
        list.add(del);       
        table.delete(list);       
        System.out.println("del recored " + rowKey + " ok.");       
    }       
            
    /**    
     * 查找一行记录    
     */      
    public static void getOneRecord (String tableName, String rowKey) throws IOException{       
        HTable table = new HTable(conf, tableName);       
        Get get = new Get(rowKey.getBytes());       
        Result rs = table.get(get);       
        for(KeyValue kv : rs.raw()){       
            System.out.print(new String(kv.getRow()) + " " );       
            System.out.print(new String(kv.getFamily()) + ":" );       
            System.out.print(new String(kv.getQualifier()) + " " );       
            System.out.print(kv.getTimestamp() + " " );       
            System.out.println(new String(kv.getValue()));       
        }       
    }       
            
    /**    
     * 显示所有数据    
     */      
    public static void getAllRecord (String tableName) {       
        try{       
             HTable table = new HTable(conf, tableName);       
             Scan s = new Scan();       
             ResultScanner ss = table.getScanner(s);       
             for(Result r:ss){   
            	 
                 for(KeyValue kv : r.raw()){
                	 rowkey=new String(kv.getRow());
                	 
                	 counteachtime++;
                	// System.out.print(new String(kv.getQualifier()) + " ");
                	 
                	 if ((new String(kv.getQualifier())).equals("Latitude")) {
                		 latitude=new String(kv.getValue());
                		 //System.out.println(latitude);
                		 
                	 }
                	 if ((new String(kv.getQualifier())).equals("Longitude")) {
                		 longitude=new String(kv.getValue());
                		 //System.out.println(longitude);
                	 }
                	 if ((new String(kv.getQualifier())).equals("callletter")) {
                		 callletter=new String(kv.getValue());
                		 //System.out.println(callletter);
                	 }
                	 if ((new String(kv.getQualifier())).equals("disEuclid")) {
                		 disEuclid=new String(kv.getValue());
                		 
                		// System.out.println(disEuclid);
                		// System.out.println("第"+count+"个数为："+disEuclid);
                		 //sum2+=Double.parseDouble(disEuclid);
                		 //System.out.println("sum2总和："+sum2);
						
					}
                	 if ((new String(kv.getQualifier())).equals("gpstime")) {
                		 gpstime=new String(kv.getValue());
                		// System.out.println(gpstime);
                	 }
                	 if ((new String(kv.getQualifier())).equals("predictionDiff")) {
                		 predictionDiff=new String(kv.getValue());
                		 //System.out.println(predictionDiff);
                	 }
                	 
                //    System.out.print(new String(kv.getRow()) + " ");       
                //    System.out.print(new String(kv.getFamily()) + ":");                        
                //    break;
                //    System.out.print(kv.getTimestamp() + " ");       
                //    System.out.println(new String(kv.getValue()));       
                 }
                 if (predictionDiff.equals("1")) {
                	 System.out.print("汽车启动：");
                	 startlatitude=latitude;
                	 startlongitude=longitude;
                	 System.out.println(startlatitude+","+startlongitude+","+callletter+","+gpstime);
				 }
                 
                 sum+=Double.parseDouble(disEuclid);
               
                 if (predictionDiff.equals("-1")) {
                	 if (counteachtime>=3) {
                		 endlatitude=latitude;
                		 endlongitude=longitude;
                		 System.out.print("汽车熄火：");
                    	 System.out.println(endlatitude+","+endlongitude+","+callletter+","+gpstime);
    				     System.out.println("里程总和："+sum);
                		 count++;
					}
                	 
				     //count++;
				     sum=0;
				     counteachtime=0;
                 }
                /* try {
					addRecord ("t_gps_xingcheng", rowkey, "gps", "startlatitude",startlatitude );
					addRecord ("t_gps_xingcheng", rowkey, "gps", "startlongitude",startlongitude );
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
                 
             }       
        } catch (IOException e){       
            e.printStackTrace();       
        }       
    }       
           
    public static void  main (String [] agrs) {       
        try {   
        	Logger log = Logger.getLogger(HBaseTest.class);
        	//log.info("I love you");
            String tablename = "t_gps_count";       
            String[] familys = {"gps"};       
           // HBaseTest.creatTable(tablename, familys);       
                    
            //add record zkb       
           /* HBaseTest.addRecord(tablename,"zkb","grade","","5");       
            HBaseTest.addRecord(tablename,"zkb","course","","90");       
            HBaseTest.addRecord(tablename,"zkb","course","math","97");       
            HBaseTest.addRecord(tablename,"zkb","course","art","87");       
            //add record  baoniu       
            HBaseTest.addRecord(tablename,"baoniu","grade","","4");       
            HBaseTest.addRecord(tablename,"baoniu","course","math","99");       
                    
            System.out.println("===========get one record========");       
            HBaseTest.getOneRecord(tablename, "zkb");       
                    
            System.out.println("===========show all record========");       
            HBaseTest.getAllRecord(tablename);       
                    
            System.out.println("===========del one record========");       
           // HBaseTest.delRecord(tablename, "baoniu");       
           // HBaseTest.getAllRecord(tablename);       
           */                    
            System.out.println("===========show all record========");       
            HBaseTest.getAllRecord(tablename);  
            System.out.println("行程次数："+count);
        } catch (Exception e) {       
            e.printStackTrace();       
        }       
    }       
}