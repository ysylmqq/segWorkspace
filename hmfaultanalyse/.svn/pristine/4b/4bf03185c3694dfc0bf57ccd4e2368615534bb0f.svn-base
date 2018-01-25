/*
********************************************************************************************
Discription:  海马体检分析主程序
			  功能：从hbase读海马的故障信息，保存到数据库中t_hm_vehiclefault
			  
Written By:   ZXZ
Date:         2016-11-08
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package analyse;

import logs.LogManager;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import hbase.HbaseManager;
import jmxtool.JmxManager;
import utils.SystemConfig;
import utils.Util;

public class hmfaultanalyse 
{
	public static String starttime = null;
	//系统名称
	public static String systemname = "海马故障数据分析";
	//jmx端口
	public static int jmxport = 8081;
	
	public static void init() throws UnsupportedEncodingException {
		starttime = Util.DatetoString(new Date(System.currentTimeMillis()));
		LogManager.init();	//日志初始化
		
		systemname = SystemConfig.getSystemProperties("system_name");
		//systemname = new String(systemname.getBytes("ISO8859_1"),"UTF8");
		systemname = systemname.replace('/', '_');	//名称不能带/字符
		jmxport = Integer.valueOf(SystemConfig.getSystemProperties("jmxport"));

    	System.out.println("systemname: " + systemname);
    	System.out.println("jmxport: " + jmxport);

    	//初始化从数据库读未结束的故障
    	DBFaultInfoManager.Init();
    	
   		HbaseManager.Init();
	}
	
    public static void main(String[] args) {
	    try {
	    	hmfaultanalyse.init();	//系统初始化
			
	    	AnalyseThread.analysethread = new AnalyseThread();
	    	
    		//jmx监控器
	    	new JmxManager(jmxport).start();	//启动jmx监控器

	    	AnalyseThread.analysethread.start();
	    	
	    	while(true) {
	    		Thread.sleep(60000);
	    	}
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
    	System.out.println("HMFaultAnalyse Exit");
	}
}
