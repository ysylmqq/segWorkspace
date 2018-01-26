package cc.chinagps.seat.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.beans.factory.InitializingBean;

public class HBaseConfg implements InitializingBean {
	
	private static Configuration conf = HBaseConfiguration.create();
	
	public Configuration getConf() {
		return conf;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
		System.out.println(conf.get("hbase.rootdir"));
		System.out.println(conf.get("hbase.zookeeper.quorum"));
		
		conf.set("hbase.rootdir", "hdfs://90.0.12.201:9000/hbase");
		conf.set("hbase.master", "90.0.12.201:60000");
		conf.set("hbase.zookeeper.quorum", "90.0.12.201,90.0.12.202,90.0.12.203");
		System.out.println("================");
		
		System.out.println(conf.get("hbase.rootdir"));
		System.out.println(conf.get("hbase.zookeeper.quorum"));
	}
}
