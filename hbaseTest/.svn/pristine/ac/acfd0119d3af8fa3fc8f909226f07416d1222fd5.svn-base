package org.ysy.com.mapreduce.maxValue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * reduce过程
 * @author ysy
 * 
 * reduce的input过程  
 *   input也是<key,value>  只不过是value是一个Iterable容器，改容器当中的数据类型是IntWritable类型的
 *   <'java',1>,<'test',<1,1>>,<'c++',<1,1,1>>
 *
 */
public class MaxValueReduce  extends Reducer<Text, IntWritable, Text, LongWritable> {

	@Override
	protected void reduce(Text text, Iterable<IntWritable> iterable,Context context)
			throws IOException, InterruptedException {
		int sum = 0;
		for(IntWritable c : iterable){
			sum += c.get();
		}
		context.write(text, new LongWritable(sum));
	}
	
	
	public static  void  main(String agrs []) throws IOException{
		
		File file = new File("E:/logs/maxValue/maxValue3.txt");
		
		FileWriter fw =  new FileWriter(file);
		
	    BufferedWriter out = new BufferedWriter(fw);
	    
		// 28 
		String [] month = new String[]{"01","02","03","04","05","06","07","08","09","10","11","12"};
		String [] year = new String[]{"1989","1990","1991","1992","19993","1994","1995","1996","1997","1998","1999",
				"2000","2001","2002","2003","2004","2005","2006","2007","2008","2010","2011","2012","2013","2014","2015","2016","2017"};
	    long start = System.currentTimeMillis();
	    long end = System.currentTimeMillis();
	    while( (end-start)/1000 <5){
		    int val1 = (int)(Math.random()*10+1);
		    int val2 = (int)(Math.random()*26+1);
		    //任意一个随机数
		    int max = (int)(Math.random()*300+1);
		    
	    	end = System.currentTimeMillis();
	    	out.newLine();
	    	out.write(year[val2]+"年"+month[val1]+"="+max);
	    	if((end-start)/1000%60 == 0){
	    		out.flush();
	    	}
	    }
 	    out.close();
	}

	
}
