/*
********************************************************************************************
Discription:  通信中心单元测试工具，往Hbase写数据
			  
			  
Written By:   ZXZ
Date:         2014-05-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package testtools;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

public class HbaseClientTestTool {
	public static Configuration config = null;
	public static void init() {
		try {
			config = HBaseConfiguration.create();
			System.out.println("Hbase initialized.");
		} catch (Exception e) {
			System.out.println("Hbase uninitialized:");
			e.printStackTrace();
		}
	}
	
	public static void WriteGPS(String callletters, long startTime, long stoptime, int interval) {
		try {
			startTime /= 10000;
			startTime *= 10000;
			stoptime /= 10000;
			stoptime *= 10000;
			WriteHbaseGPS writehbase = new WriteHbaseGPS(callletters, startTime, stoptime, interval);
			Thread thread = new Thread(writehbase, "WriteHbaseGPS");
			thread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void WriteAlarm(String callletters, long startTime, long stoptime, int interval) {
		try {
			startTime /= 10000;
			startTime *= 10000;
			stoptime /= 10000;
			stoptime *= 10000;
			WriteHbaseAlarm writehbase = new WriteHbaseAlarm(callletters, startTime, stoptime, interval);
			Thread thread = new Thread(writehbase, "WriteHbaseAlarm");
			thread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void WriteTravel(String callletters, long startTime, long stoptime, int interval) {
		try {
			startTime /= 10000;
			startTime *= 10000;
			stoptime /= 10000;
			stoptime *= 10000;
			WriteHbaseTravel writehbase = new WriteHbaseTravel(callletters, startTime, stoptime, interval);
			Thread thread = new Thread(writehbase, "WriteHbaseTravel");
			thread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void WriteFault(String callletters, long startTime, long stoptime, int interval) {
		try {
			startTime /= 10000;
			startTime *= 10000;
			stoptime /= 10000;
			stoptime *= 10000;
			WriteHbaseFault writehbase = new WriteHbaseFault(callletters, startTime, stoptime, interval);
			Thread thread = new Thread(writehbase, "WriteHbaseFault");
			thread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void WriteOperateData(String callletters, long startTime, long stoptime, int interval) {
		try {
			startTime /= 10000;
			startTime *= 10000;
			stoptime /= 10000;
			stoptime *= 10000;
			WriteHbaseOperateData writehbase = new WriteHbaseOperateData(callletters, startTime, stoptime, interval);
			Thread thread = new Thread(writehbase, "WriteHbaseOperateData");
			thread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void WriteSms(String callletters, long startTime, long stoptime, int interval) {
		try {
			startTime /= 10000;
			startTime *= 10000;
			stoptime /= 10000;
			stoptime *= 10000;
			WriteHbaseSms writehbase = new WriteHbaseSms(callletters, startTime, stoptime, interval);
			Thread thread = new Thread(writehbase, "WriteHbaseSms");
			thread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void WriteOBD(String callletters, long startTime, long stoptime, int interval) {
		try {
			startTime /= 10000;
			startTime *= 10000;
			stoptime /= 10000;
			stoptime *= 10000;
			WriteHbaseOBD writehbase = new WriteHbaseOBD(callletters, startTime, stoptime, interval);
			Thread thread = new Thread(writehbase, "WriteHbaseOBD");
			thread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
