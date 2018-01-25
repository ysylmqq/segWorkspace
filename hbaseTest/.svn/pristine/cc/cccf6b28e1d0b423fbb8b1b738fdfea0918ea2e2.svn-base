package org.ysy.com.mapreduce.maxValue;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class MaxValueMain {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		long start = System.currentTimeMillis();
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "word count");
		
		//设置mapper和reduce类 ，在(集群)执行的过程当中，(ResourceManager)分配执行
		job.setMapperClass(MaxValueMaper.class);
		job.setReducerClass(MaxValueReduce.class);
		
		//设置map过程输出的key，value的数据类型
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		//设置reduce过程当中输出的key，value的数据类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		
		//设置输入输出文件的路径
		FileInputFormat.setInputPaths(job, "E:/logs/maxValue/");
		FileOutputFormat.setOutputPath(job, new Path("E:/hadoopoutput/maxValue/"));
		
		//true 在控制台输出执行过程当中的信息，false不输出
		job.waitForCompletion(true);
		long end = System.currentTimeMillis();
		System.err.println("total "+(end-start)/1000);
	}

}
