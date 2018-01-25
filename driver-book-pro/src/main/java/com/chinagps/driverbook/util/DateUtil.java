package com.chinagps.driverbook.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class DateUtil {
	public static String orderSerial() {
		DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Random random = new Random();
		int r = random.nextInt(9999) % (9999-1000+1) + 1000;
		return sdf.format(Calendar.getInstance().getTime())  + r;
	}
	
	public static int distanceInYear(Date beginDate, Date endDate) {
		return (int) ((endDate.getTime() - beginDate.getTime())/31536000000l);
	}
	
	public static Date addYear(Date date, int year) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, year);
		return cal.getTime();
	}
}
