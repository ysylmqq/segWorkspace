package hbasedemo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;

import cc.chinagps.lib.util.Util;

public class CHbaseDemo {
    public static void main(String[] args) {
    	try {
	    	String callletter = "18964059013";
    		if (Util.isNumeric(callletter)) {
    			System.out.println(callletter + " is Numeric!");
    		} else {
    			System.out.println(callletter + " is't Numeric!");
    		}
    		callletter += "123";
    		if (Util.isNumeric(callletter)) {
    			System.out.println(callletter + " is Numeric!");
    		} else {
    			System.out.println(callletter + " is't Numeric!");
    		}
    		callletter += "a";
    		if (Util.isNumeric(callletter)) {
    			System.out.println(callletter + " is Numeric!");
    		} else {
    			System.out.println(callletter + " is't Numeric!");
    		}
    		
    		//生成Hbase配制文件，hbase.xml要放在正确的文件夹
	    	//Configuration config = HBaseConfiguration.create();
	    	//HConnection connection = HConnectionManager.createConnection(config);
	    	//开始时间为
			long starttime = StringToDate("2016-05-24 00:00:00").getTime();
	    	//结束时间为当前
	    	//long endtime = System.currentTimeMillis();
			long endtime = StringToDate("2016-05-31 00:00:00").getTime();
			
			if (args.length > 0) {
				callletter = args[0];
			}
			if (args.length > 1) {
				starttime = StringToDate(args[1]).getTime();
			}
			if (args.length > 2) {
				endtime = StringToDate(args[2]).getTime();
			}
	    	//读历史信息，按时间先后顺序排序
	    	/*LoginoutHistoryInfo history = new LoginoutHistoryInfo(connection);
	    	if (history.GetHistoryInfo(callletter, starttime, endtime)) {
	    		for(LoginoutHistoryInfo.UnitLoginoutTimestamp loginouttime : history.unitloginoutlist) {
	    			System.out.print(loginouttime.loginout.getCallLetter() + ", ");
	    			System.out.print(Util.DatetoString((new Date(loginouttime.inserttime))));

	    			System.out.print(", type:" + loginouttime.loginout.getInorout());
	    			
	    			if (loginouttime.loginout.hasLoginTime()) {
		    			System.out.print(", login:" + Util.DatetoString((new Date(loginouttime.loginout.getLoginTime()))));
	    			}
	    			if (loginouttime.loginout.hasLogoutTime()) {
		    			System.out.print(", logout:" + Util.DatetoString((new Date(loginouttime.loginout.getLogoutTime()))));
	    			}
	    			System.out.println("");
	    		}
	    	} else {
	    		System.out.println("读OBD历史记录失败!");
	    	}*/
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    public static Date StringToDate(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date(0l);
    }
}
