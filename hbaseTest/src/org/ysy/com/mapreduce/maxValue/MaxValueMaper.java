package org.ysy.com.mapreduce.maxValue;

import java.io.IOException;
import java.util.StringTokenizer;

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
public class MaxValueMaper  extends Mapper<LongWritable, Text, Text, IntWritable>{

	
	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		Text word =  new Text();
		//具体获取那个值，在mapper当中
		if( !"".equalsIgnoreCase(value.toString()) ){
			String year = value.toString().split("=")[0]; // 2017-01
			String max = value.toString().split("=")[1]; // 45
			word.set(year);
			context.write(word, new IntWritable(Integer.parseInt(max)));
		}
	}

	
}
