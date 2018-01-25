package org.ysy.com.mapreduce.wordCount;

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
public class CountReduce  extends Reducer<Text, IntWritable, Text, LongWritable> {

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
		
		File file  = new File("E:/logs/test.txt");
		
		FileWriter fw =  new FileWriter(file);
		
	    BufferedWriter out = new BufferedWriter(fw);
	    
		String [] xyz = new String[]{"a","b","c","d","e","f","g","h","i","g","k","l","m","n","q","p","q","r","s","t","u","v","w","x","y","z","w","x"};
	    long start = System.currentTimeMillis();
	    long end = System.currentTimeMillis();
	    while( (end-start)/1000 < 30){
		    int val1 = (int)(Math.random()*26+1);
		    int val2 = (int)(Math.random()*26+1);
		    
		    int val3 = (int)(Math.random()*26+1);
		    int val4 = (int)(Math.random()*26+1);
		    
		    int val5 = (int)(Math.random()*26+1);
		    int val6 = (int)(Math.random()*26+1);
		    
		    int val7 = (int)(Math.random()*26+1);
		    int val8 = (int)(Math.random()*26+1);
	    	end = System.currentTimeMillis();
	    	out.newLine();
	    	out.write(xyz[val1]+xyz[val2]+" "+xyz[val3]+xyz[val4]+" "+xyz[val5]+xyz[val6]+" "+xyz[val7]+xyz[val8]);
	    	if((end-start)/1000%60 == 0){
	    		out.flush();
	    	}
	    }
 	    out.close();
	}

	
}
