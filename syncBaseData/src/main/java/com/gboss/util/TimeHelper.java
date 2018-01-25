package com.gboss.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeHelper {

	/**
	 * 秒级别数字型时间字符串转Date
	 * @param args
	 */
	public static Date getDate(String numStr){
		if(numStr != null && !"".equals(numStr)){
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(Long.valueOf(numStr)*1000);
			return c.getTime();
		}
		return null;
	}
	
	public static Date getDateFromStr(String fmtstr){
		if(fmtstr != null && !"".equals(fmtstr)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return sdf.parse(fmtstr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(getDate("1428950996"));
	}

	public static Date getGMTBeginDate() {
		return new Date(0);
	}

}
