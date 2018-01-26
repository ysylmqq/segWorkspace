package com.chinaGPS.gtmp.util;

/**
 * 使用精确到毫秒的数字生成文件名
 */
public class RandomFileName{
	/**
	 * 生成新的带后缀的文件名
	 * @param fileName 带后缀的文件名
	 * @return 新的文件名
	 */
	public static String newFileName(String fileName){
		//return getRandomFileName()+fileName.substring(fileName.lastIndexOf(".")); 
          return fileName.substring(0,fileName.lastIndexOf("."))+getRandomFileName()+fileName.substring(fileName.lastIndexOf("."));
	}
	/**
	 * 精确到毫秒的时间，不会出现重复
	 */
	private static String getRandomFileName(){
		return  String.valueOf(System.currentTimeMillis());
	}
	
	
	public static void main(String[] args){
		System.out.println(newFileName("test.test.est.txt"));
	}
}