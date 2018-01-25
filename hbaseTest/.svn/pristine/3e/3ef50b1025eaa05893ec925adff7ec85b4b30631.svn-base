package org.ysy.com.mapreduce.wordCount;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.StringTokenizer;

import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * 四个参数分别是 Mapper的输入的key, value; mapper输入结果的key,value
 * @author ysy
 *   LongWritable：每一行的开头字母在文件当中的其实偏移量
 *   Text:每个单词
 *   Text：输入结果当中，map当中的key，以单词为key，单词在这一行出现的次数作为value
 *   
 *   mapper当中的input：<1,'java'>,<20,'test'>.....<30,'c++'>
 *   mapper当中的output:<'java',1>,<'test',1>,<'test',1>,<'c++',1>,<'c++',1>,<'c++',1>
 *   
 *   shuffle过当是对，mapper结果进行合并
 *   shuffle的结束，<'java',1>,<'test',<1,1>>,<'c++',<1,1,1>>
 */
public class CountMaper  extends Mapper<LongWritable, Text, Text, IntWritable>{

	final IntWritable one =  new IntWritable(1);
	
	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		 Text word =  new Text();
		String s = value.toString();
		StringTokenizer ss = new StringTokenizer(s);
		while (ss.hasMoreElements()) {
			String str = (String) ss.nextElement();
			word.set(str);
			context.write(word, one);
		}
	}
	
	public static void main (String ars[]) throws IOException{
		/*URL url  = new URL("http://www.baidu.com");
		InputStream in = url.openStream();
		IOUtils.copyBytes(in, System.out, 4096,true);*/
		//hdfs 
		URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
		URL url  = new URL("hdfs://192.168.139.128:9000/logs/hadoop-root-datanode-master.log");
		InputStream in = url.openStream();
		IOUtils.copyBytes(in, System.out, 4096,true);
		
	}
	
}
