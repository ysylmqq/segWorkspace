package org.ysy.com.hdfs;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class Hdfs {
	
	public static void main (String ars[]) throws IOException{
		/*URL url  = new URL("http://www.baidu.com");
		InputStream in = url.openStream();
		IOUtils.copyBytes(in, System.out, 4096,true);*/
		
		
		//hdfs 协议的 默认是http  要支持hdfs协议  需要添加handler
		/*URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
		URL url  = new URL("hdfs://master:9000/logs/hadoop-root-datanode-master.log");
		InputStream in = url.openStream();
		IOUtils.copyBytes(in, System.out, 4096,true);*/
		
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://master:9000");
		FileSystem fileSystem = FileSystem.get(conf);
		//fileSystem 获取fileSystem以后根据这个对象可以操作hdfs
		
		//创建目录
		/*boolean res = fileSystem.mkdirs(new Path("/ysy"));
		*/

		//boolean res =  fileSystem.exists(new Path("/ysy"));
		
		boolean res =  fileSystem.delete(new Path("/output/"));
		System.err.println("mkdir "+res);

		/*FSDataOutputStream out =  fileSystem.create(new Path("/input/wordCount.txt"),true);
		FileInputStream in = new FileInputStream("E:/logs/test.txt");
		IOUtils.copyBytes(in, out, 4096,true);*/
	}
}
