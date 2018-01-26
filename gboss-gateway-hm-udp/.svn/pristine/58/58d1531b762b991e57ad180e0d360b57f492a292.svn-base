package cc.chinagps.gateway.hbase.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.log4j.Logger;

import cc.chinagps.gateway.log.LogManager;

public class HBaseClient {
	private HConnection connection;		//HBASE表池
	
	private String tableName;			//HBASE表名
	
	private int threadCount = 10;		//存储线程数量

	private int maxFetchSize = 10000;	//存储线程从数据缓存中一次最多取出数据数量

	private int batchSize = 2000;		//批量存储一次提交数量
	
	private int maxCacheSize = 200000;	//客户端最大缓存数据个数
	
	private Logger logger_others = Logger.getLogger(LogManager.LOGGER_NAME_OTHERS);

	public HBaseClient(HConnection connection, String tableName){
		this.connection = connection;
		this.tableName = tableName;
	}
	
	public HBaseClient(int threadCount, HConnection connection, String tableName){
		this.connection = connection;
		this.tableName = tableName;
		
		this.threadCount = threadCount;
	}
	
	public HBaseClient(int threadCount, int maxFetchSize, int batchSize, int maxCacheSize, HConnection connection, String tableName){
		this.connection = connection;
		this.tableName = tableName;
		
		this.threadCount = threadCount;
		this.maxFetchSize = maxFetchSize;
		this.batchSize = batchSize;
		this.maxCacheSize = maxCacheSize;
	}
	
	private SaveThreads[] saveThreads;
	
	public SaveThreads[] getSaveThreads(){
		return saveThreads;
	}
	
	public void init() throws IOException{
		//init threads
		saveThreads = new SaveThreads[threadCount];
		for(int i = 0; i < threadCount; i++){
			//HTableInterface table = pool.getTable(tableName);
			//table.setAutoFlush(false);
			HTableInterface table = connection.getTable(tableName);
			table.setAutoFlush(false, true);
			
			saveThreads[i] = new SaveThreads(dataLock, this, maxFetchSize, batchSize, table);
			saveThreads[i].start();
		}
	}
	
	private Object dataLock = new Object();
	
	public Object getDataLock() {
		return dataLock;
	}

	private List<Put> dataList = new ArrayList<Put>();
	
	public List<Put> getDataList() {
		return dataList;
	}

	private long throwDataCount = 0;
	
	public long getThrowDataCount() {
		return throwDataCount;
	}

	public boolean addData(Put put){
		synchronized (dataLock) {
			if(dataList.size() >= maxCacheSize){
				throwDataCount ++;
				return false;
			}
			
			dataList.add(put);
			dataLock.notifyAll();
			return true;
		}
	}
	
	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}
	
	public int getMaxFetchSize() {
		return maxFetchSize;
	}

	public void setMaxFetchSize(int maxFetchSize) {
		this.maxFetchSize = maxFetchSize;
	}

	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	public int getMaxCacheSize() {
		return maxCacheSize;
	}
	
	public void setMaxCacheSize(int maxCacheSize) {
		this.maxCacheSize = maxCacheSize;
	}
	
	public void exceptionCaught(Throwable e){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		e.printStackTrace(new PrintStream(bos));
		logger_others.info(bos.toString());
	}
}