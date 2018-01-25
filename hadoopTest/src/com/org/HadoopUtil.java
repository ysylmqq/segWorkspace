package com.org;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class HadoopUtil {

   /*public static void main(String[] args) throws Exception {
        String uri = "hdfs://192.168.139.128:9000/input/NOTICE.txt";
        Configuration conf = new Configuration();
        conf.set("fs.hdfs.impl",org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());  
     // first check the Dflag hadoop.home.dir with JVM scope
        String home = System.getProperty("hadoop.home.dir");
        System.err.println("home "+home);

        // fall back to the system/user-global env variable
        if (home == null) {
          home = System.getenv("HADOOP_HOME");
        }
        
        System.err.println("home "+home);
	    FileSystem fs = FileSystem. get(URI.create (uri), conf);
	    InputStream in = null;
	    try {
	         in = fs.open( new Path(uri));
	         IOUtils.copyBytes(in, System.out, 4096, false);
	        } finally {
	         IOUtils.closeStream(in);
	    }
    }*/
	
	/*public static void main (String args[]) throws UnsupportedEncodingException{
		Locale defaultLocale = Locale.getDefault(); //包名.文件名
		//Locale defaultLocale = new Locale("en");  
		String country = defaultLocale.getCountry();
		String lang = defaultLocale.getLanguage();
		System.err.println("country "+country+" lang " +lang );
		ResourceBundle res = ResourceBundle.getBundle("conf.ApplicationResources", defaultLocale);
		String value = res.getString("Authenticate_"+2);
		String encode = "UTF-8";    
		 System.err.println("value "+value);
		 if (value.equals(new String(value.getBytes(encode), encode))) {    
             String s1 = encode;    
            System.err.println("code "+s1);   
         }  else{
        	 System.err.println("ooo");
         }
	}*/
}
