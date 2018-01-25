package org.ysy.com.mapreduce.wordCount;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class CountMain {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws ClassNotFoundException 
	 */
	//Exception in thread "main" java.lang.UnsatisfiedLinkError: org.apache.hadoop.io.nativeio.NativeIO$Windows.access0(Ljava/lang/String;I)Z
	//报错 把把其中的hadoop.dll复制到c:\windows\system32目录下
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		long start = System.currentTimeMillis();
		Configuration conf = new Configuration();
		
		conf.set("fs.defaultFS", "hdfs://master:9000");
		 
		// 设置mapreduce在linux上执行  
		//conf.set("mapreduce.job.jar", "count.jar");
		//如果把这个程序手放到linux上执行 ，下面的配置也不用配置
		/*conf.set("mapreduce.framework.name", "yarn");
		conf.set("yarn.resourcemanager.hostname", "master");
		conf.set("mapreduce.app-submission.cross-platform", "true");*/
		
		Job job = Job.getInstance(conf, "word count");
		job.setJarByClass(CountMain.class);
		//设置mapper和reduce类 ，在(集群)执行的过程当中，(ResourceManager)分配执行
		job.setMapperClass(CountMaper.class);
		job.setReducerClass(CountReduce.class);
		
		//设置map过程输出的key，value的数据类型
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		//设置reduce过程当中输出的key，value的数据类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		
		//设置输入输出文件的路径
		//FileInputFormat.setInputPaths(job, "E:/logs/test.txt");
		//FileOutputFormat.setOutputPath(job, new Path("E:/hadoopoutput/wordCount"));
		
		//这种方式是把文件从服务器拿到window的机器上进行计算   同时这样的运行方式是不需要yarn的(自己停掉yarn服务做实验)
		FileInputFormat.setInputPaths(job, "/input/wordCount.txt");
		FileOutputFormat.setOutputPath(job, new Path("/output/wordcount2/"));
		
		//true 在控制台输出执行过程当中的信息，false不输出
		job.waitForCompletion(true);
		long end = System.currentTimeMillis();
		System.err.println("total "+(end-start)/1000);
	}

}
