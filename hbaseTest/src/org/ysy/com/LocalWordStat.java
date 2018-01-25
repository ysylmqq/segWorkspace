package org.ysy.com;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.DependentColumnFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
public class LocalWordStat {
    
public static class MyMapper extends TableMapper<Text,IntWritable>{
        
        private static IntWritable one = new IntWritable(1);
        private static Text word = new Text();
        
        @Override
        protected void map(ImmutableBytesWritable key, Result value,
                Context context)
                throws IOException, InterruptedException {
            //表里面只有一个列族，所以我就直接获取每一行的值
            String age = Bytes.toString(value.list().get(0).getValue());
            System.err.println("age "+age);
            word.set(age);
        	context.write(word, one);
          /*  StringTokenizer st = new StringTokenizer(words); 
            while (st.hasMoreTokens()) {
                 String s = st.nextToken();
                 word.set(s);
                 context.write(word, one);
            }*/
        }
    }
    
public static class MyReducer extends TableReducer<Text,IntWritable,ImmutableBytesWritable>{
   
   @Override
   protected void reduce(Text key, Iterable<IntWritable> values,
           Context context)
           throws IOException, InterruptedException {
	   
       int sum = 0;
       for(IntWritable val:values) {
           sum+=val.get();
       }
       //添加一行记录，每一个单词作为行键
       Put put = new Put(Bytes.toBytes(key.toString()));
       put.add(Bytes.toBytes("basic_info"), Bytes.toBytes("count"),Bytes.toBytes(String.valueOf(sum)));
       context.write(new ImmutableBytesWritable(Bytes.toBytes(key.toString())),put);
   }
}
    
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
    	long start = System.currentTimeMillis();
		System.setProperty("hadoop.home.dir","E:\\soft\\hadoop\\hadoop-2.6.1"); //这个是window上面的
    	Configuration conf = HBaseConfiguration.create();
		/*conf.set("hbase.zookeeper.quorum", "hbasestudy");
		conf.set("hbase.master", "hbasestudy:9000"); */
		Scan scan = new Scan();
		scan.addColumn(Bytes.toBytes("basic_info"), Bytes.toBytes("age"));

        Job job = new Job(conf,"wordstat");
        job.setJarByClass(LocalWordStat.class);
        FileOutputFormat.setOutputPath(job, new Path("E:\\hbaseOutput")); // map的过程当中 产生的输出文件
        /*只有map过程时,必须设置 输入的结果存放的位置  , 如果有map和procedure两个过程时,不用设置map的输出文件位置,因为输出的结果直接
        	传递到了reducer当中
         */
        
        TableMapReduceUtil.initTableMapperJob("customer", scan, MyMapper.class, Text.class, IntWritable.class, job);
        //job.setNumReduceTasks(0);  //只是执行map过程，没有reducer过程
        TableMapReduceUtil.initTableReducerJob("student", MyReducer.class, job);

        System.exit(job.waitForCompletion(true)?0:1);
        long end = System.currentTimeMillis();
        System.err.println("totalTime "+(end-start)/1000);
    }
}
