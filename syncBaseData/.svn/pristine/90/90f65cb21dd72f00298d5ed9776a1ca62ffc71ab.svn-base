package com.gboss.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/*
 * 使用精确到毫秒的数字+字母生成文件名
 */
public class RandomFileName {

	// 生成新的带后缀的文件名
	public static String randomFileName(String fileName) {
		return randomFileName(8)
				+ fileName.substring(fileName.lastIndexOf("."));
	}

	public static String randomFileName(int sLen, int dLen) {
		String date = new SimpleDateFormat("MMddHHmmssSSS").format(Calendar
				.getInstance().getTime());
		String base = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuffer temp = new StringBuffer();

		if (dLen > 0) {
			temp.append(date.substring(date.length() - dLen, date.length()));
		} else {
			temp.append(date);
		}
		for (int i = 0; i < sLen; i++) {
			int p = (int) (Math.random() * 37);
			if (p > 35) {
				p = 35;
			}
			temp.append(base.substring(p, p + 1));
		}
		return temp.toString();
	}

	public static String randomFileName(int sLen) {
		return randomFileName(sLen, 7);
	}

	public static void main(String[] args) {
		System.out.println(randomFileName("test.test.est.txt"));
		/*System.out.println(randomFileName("test.test.est.txt"));
		System.out.println(randomFileName("test.test.est.txt"));
		System.out.println(randomFileName("test.test.est.txt"));
		System.out.println(randomFileName("test.test.est"));*/
        System.out.println(randomFileName(2,4));
	}
}