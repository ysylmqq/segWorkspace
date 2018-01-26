/*
 ********************************************************************************************
Discription: 统计某机构一天的GPS数量（按呼号统计）
			 
			  
Written By:   ZXZ
Date:         2015-08-13
Version:      1.0

Modified by:
Modified Date:
Version:
 ********************************************************************************************
*/
package cc.chinagps.gboss.hbasecalc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.log4j.Logger;

import cc.chinagps.gboss.Log.LogManager;

public class HbaseCalc {
    public static void main(String[] args) {
    	try {
    		int orgno = 201;
    		int custtype = 0;
    		Calendar calendar = Calendar.getInstance();
    		calendar.setTimeInMillis(System.currentTimeMillis());
    		calendar.clear(Calendar.HOUR);
    		calendar.clear(Calendar.MINUTE);
    		calendar.clear(Calendar.SECOND);
    		calendar.clear(Calendar.MILLISECOND);
    		Date calcdate = calendar.getTime();
    		
    		if (args.length > 0) {
    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    			calcdate = sdf.parse(args[0]);
    		}
    		if (args.length > 1 && args[1].length() > 0) {
    			orgno = Integer.parseInt(args[1]);
    		}
    		if (args.length > 2 && args[2].length() > 0) {
    			custtype = Integer.parseInt(args[2]);
    		}
    		ArrayList<String> listcallletter = new ArrayList<String>();
    		if (!CallLetterInfo.readCallletter(orgno, custtype, listcallletter))  {
    			return;
    		}
    		
    		Configuration config = HBaseConfiguration.create();
			HConnection connection = HConnectionManager.createConnection(config);
			GpsCountInfo gpscount = new GpsCountInfo(connection);

			LogManager.init();
			Logger logger = Logger.getLogger("nameCalcGPS");
			long totalcount = 0;
			int  calllettercount = 0;
			String strmsg;
			String strlog4 = "\r\n";
    		for(String callletter : listcallletter) {
    			int count = gpscount.getGpsCount(callletter, calcdate);
    			strmsg = callletter + ",  " + count;
    			System.out.println(strmsg);
    			if (count > 0) {
    				calllettercount ++;
    				totalcount += count;
    			}
    			strlog4 += strmsg + "\r\n";
    		}
    		strmsg = "vehcile count:" + listcallletter.size() + ", deliver count:" + calllettercount + ", gpscount:" + totalcount;
			System.out.println(strmsg);
			strlog4 += strmsg + "\r\n";
			logger.info(strlog4);
		} catch (Exception e) {
			System.out.println("using: HbaseCalc yyyy-MM-dd orgno custtype");
			e.printStackTrace();
		}
    }
}
