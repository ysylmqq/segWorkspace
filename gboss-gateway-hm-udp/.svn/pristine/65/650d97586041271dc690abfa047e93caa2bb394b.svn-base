package cc.chinagps.gateway.hbase.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;

public class SaveThreads extends Thread{
	private Object dataLock;
	
	private HBaseClient client;
	
	private int maxFetchSize;
	
	private int batchSize;
	
	private HTableInterface table;
	
	public SaveThreads(Object dataLock, HBaseClient client, int maxFetchSize, int batchSize, HTableInterface table){
		this.dataLock = dataLock;
		this.client = client;
		this.maxFetchSize = maxFetchSize;
		this.batchSize = batchSize;
		this.table = table;
	}
	
	private List<Put> temp_fetch;
	private List<Put> temp_batch;
	@Override
	public void run() {
		temp_fetch = new ArrayList<Put>(maxFetchSize);
		temp_batch = new ArrayList<Put>(batchSize);
		
		while(true){
			try{
				fetchAndSave();
			}catch (Throwable e) {
				//e.printStackTrace();
				client.exceptionCaught(e);
			}
		}
	}
	
	private long total = 0;
	
	private long time_sum = 0;
	
	private void fetchAndSave(){
		//fetch
		synchronized (dataLock) {
			while(client.getDataList().size() == 0){
				try {
					dataLock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			List<Put> list = client.getDataList();
			int fetchSize = Math.min(maxFetchSize, list.size());
			temp_fetch.clear();
			for(int i = 0; i < fetchSize; i++){
				temp_fetch.add(list.remove(0));
			}
		}		
		
		//save
		int add_count = temp_fetch.size();
		long t1 = System.currentTimeMillis();
		for(int i = 0; i < temp_fetch.size(); i++){
			Put put = temp_fetch.get(i);
			try {
				temp_batch.add(put);
				if(temp_batch.size() >= batchSize){						
					table.put(temp_batch);
					table.flushCommits();
					
					total += temp_batch.size();
					temp_batch.clear();						
				}
			} catch (IOException e) {
				//e.printStackTrace();
				client.exceptionCaught(e);
			}
		}
		
		if(temp_batch.size() > 0){
			try {
				table.put(temp_batch);
				table.flushCommits();
				
				total += temp_batch.size();
				temp_batch.clear();
			} catch (IOException e) {
				//e.printStackTrace();
				client.exceptionCaught(e);
			}
		}
		
		//count info
		long t2 = System.currentTimeMillis();
		long t = t2 - t1;
		time_sum += t;
		
		if(t != 0){
			add_speed = 1000.0 * add_count / t;
			if(min_add_speed == null || add_speed < min_add_speed){
				min_add_speed = add_speed;
			}
			
			if(add_speed > max_add_speed){
				max_add_speed = add_speed;
			}
		}
		
		if(time_sum != 0){
			total_speed = 1000.0 * total / time_sum;
			
			if(min_total_speed == null || total_speed < min_total_speed){
				min_total_speed = total_speed;
			}
			
			if(total_speed > max_total_speed){
				max_total_speed = total_speed;
			}
		}
	}
	
	private Double min_add_speed = null;
	private double max_add_speed = 0;
	
	private Double min_total_speed = null;
	private double max_total_speed = 0;
	
	private double add_speed; 
	private double total_speed;
	
	public long getTotal() {
		return total;
	}

	public Double getMin_add_speed() {
		return min_add_speed;
	}

	public double getMax_add_speed() {
		return max_add_speed;
	}

	public Double getMin_total_speed() {
		return min_total_speed;
	}

	public double getMax_total_speed() {
		return max_total_speed;
	}

	public double getAdd_speed() {
		return add_speed;
	}

	public double getTotal_speed() {
		return total_speed;
	}
}